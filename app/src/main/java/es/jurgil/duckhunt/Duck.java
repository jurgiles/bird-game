package es.jurgil.duckhunt;


import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.SystemClock;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Duck {
    public static final double VELOCITY_BUMP_ON_KILL = .002;
    private FloatBuffer vertexBuffer;

    private float xVelocity = .01f;
    private float yVelocity = .01f;

    private int mMVPMatrixHandle;

    private final int VERTEX_COUNT = coords.length / (COORDS_PER_VERTEX + COLORS_PER_VERTEX);

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 2;
    static final int COLORS_PER_VERTEX = 3;

    static float coords[] = {   // in counterclockwise order:
            -0.05f,  0.1f,    1f, 0f, 0f,
             0.05f,  0.1f,    0f, 1f, 0f,
             0.05f,  0.0f,    0f, 0f, 1f,

            -0.05f,  0.1f,    1f, 0f, 0f,
            -0.05f,  0.0f,    1f, 0f, 0f,
             0.05f,  0.0f,    1f, 0f, 0f,

            -0.15f,  0.0f,    1f, 0f, 0f,
             0.15f,  0.0f,    0f, 1f, 0f,
             0.15f, -0.1f,    0f, 0f, 1f,

            -0.15f,   0.0f,   1f, 0f, 0f,
            -0.15f,  -0.1f,   1f, 0f, 0f,
             0.15f,  -0.1f,   1f, 0f, 0f,
    };
    private float y;
    private float x;
    private long deathTime;
    private float scale = 2f;
    private long lastTurn;

    public Duck() {
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                coords.length * 4);
        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer
        vertexBuffer = bb.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        vertexBuffer.put(coords);

        x = 0;
        y = 0;
    }

    public float y() {
        return y;
    }

    public float x() {
        return x;
    }

    public void draw(float[] mvpMatrix, int program, int mPositionHandle, int aColorLocation) {
        step();

        if(!isAlive()){return;}

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glEnableVertexAttribArray(aColorLocation);

        vertexBuffer.position(0);
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, (COLORS_PER_VERTEX + COORDS_PER_VERTEX) * 4, vertexBuffer);

        vertexBuffer.position(COORDS_PER_VERTEX);
        GLES20.glVertexAttribPointer(aColorLocation, COLORS_PER_VERTEX, GLES20.GL_FLOAT, false, (COORDS_PER_VERTEX + COLORS_PER_VERTEX) * 4, vertexBuffer);

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix");

        // Pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, VERTEX_COUNT);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }

    void shift(float[] scratch, float x, float y, float[] mMVPMatrix) {
        this.x = x;
        this.y = y;
        Matrix.translateM(scratch, 0, mMVPMatrix, 0, this.x, this.y, 0);
    }

    public void scale(float[] buffer, float scale) {
        this.scale = scale;
        Matrix.scaleM(buffer, 0, scale, scale, 0);
    }

    private void step() {
        long now = System.currentTimeMillis();

        if (isAlive()) {
            long timeSinceTurn = now - lastTurn;

            if (timeSinceTurn > 2000) {
                lastTurn = now;

                if (Math.random() < .5f) {
                    if (Math.random() < .5f) {
                        turnAround();
                    } else {
                        flipOver();
                    }
                }
            }

        } else {
            long timeSinceDeath = now - deathTime;

            if (timeSinceDeath > 1500) {
                restoreToLife();
            }
        }
    }

    private boolean isAlive() {
        return deathTime == 0;
    }

    private void restoreToLife() {
        deathTime = 0;
        x = (float)(Math.random()* Math.random());
    }

    public void die() {
        deathTime = System.currentTimeMillis();
    }

    public void speedUp() {
        if (xVelocity > 0) {
            xVelocity += VELOCITY_BUMP_ON_KILL;
        } else {
            xVelocity -= VELOCITY_BUMP_ON_KILL;
        }

        if (yVelocity > 0) {
            yVelocity += VELOCITY_BUMP_ON_KILL;
        } else {
            yVelocity -= VELOCITY_BUMP_ON_KILL;
        }
    }


    public float xVelocity() {
        return xVelocity;
    }

    public float yVelocity() {
        return yVelocity;
    }

    public void turnAround() {
        lastTurn = SystemClock.currentThreadTimeMillis();
        xVelocity *= -1;
    }

    public void flipOver() {
        lastTurn = SystemClock.currentThreadTimeMillis();
        yVelocity *= -1;
    }


    public float scale() {
        return scale;
    }

    public void shrink() {
        scale = scale > 1f ? scale - 0.1f : 1;
    }
}