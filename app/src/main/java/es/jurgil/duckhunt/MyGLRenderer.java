package es.jurgil.duckhunt;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.opengles.GL10;

public class MyGLRenderer implements GLSurfaceView.Renderer {

    private Triangle triangle;
    private Crosshair crosshair;

    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    private float[] rotationMatrix = new float[16];

    private float x = 0;
    private float y = 0;

    private float deltaX = 0;
    private float deltaY = 0;

    private long lastDrawNanoTime = 0;

    private Sensor gyroSensor;
    private final SensorManager sensorManager;
    private final GLFragment.IFpsViewer fpsViewer;
    private float multiplier;

    private boolean flashing = false;
    private float screenRatio;

    public MyGLRenderer(Context context, GLFragment.IFpsViewer fpsViewer) {
        this.fpsViewer = fpsViewer;

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
    }

    public void onDrawFrame(GL10 unused) {
        updateFps();

        if(flashing){
            GLES20.glClearColor(1.0f, 1.0f, 1.0f, .1f);
            flashing = false;
        } else {
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        }

        float[] scratch = new float[16];

        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // Combine the rotation matrix with the projection and camera view
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, rotationMatrix, 0);

        x += deltaX;
        y += deltaY;

        x = Math.max(-screenRatio, Math.min(screenRatio, x));
        y = Math.max(-1, Math.min(1, y));

        Matrix.translateM(scratch, 0, mMVPMatrix, 0, x, y, 0);

        // Draw shape
        triangle.draw(scratch);
        crosshair.draw(scratch);
    }

    public void onSurfaceCreated(GL10 gl, javax.microedition.khronos.egl.EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        triangle = new Triangle();
        crosshair = new Crosshair();

        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] oldPos = {x, y};

                deltaX = (-1 * event.values[0] * multiplier);
                deltaY = (event.values[1] * multiplier);

//                Log.i("position", String.format("x[old: %f, new: %f, rot: %f] y[old: %f, new: %f, rot: %f] mult: %f", oldPos[0], x, event.values[0], oldPos[1], y, event.values[1], multiplier));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        }, gyroSensor, SensorManager.SENSOR_DELAY_GAME);


    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        screenRatio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -screenRatio, screenRatio, -1, 1, 3, 7);
    }

    public static int loadShader(int type, String shaderCode) {
        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    public void reset() {
        x = 0;
        y = 0;
    }

    public void setMuliplier(float multiplier) {
        this.multiplier = multiplier;
    }

    private void updateFps() {
        long nanoTime = System.nanoTime();
        float fps = 1000000000f / (nanoTime - lastDrawNanoTime);
        lastDrawNanoTime = nanoTime;
        fpsViewer.setFps(fps);
    }

    public void flashScreen() {
        flashing = true;
    }
}