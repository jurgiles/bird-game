package es.jurgil.duckhunt;


import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Crosshair {
    private final ShortBuffer drawListBuffer;
    private FloatBuffer vertexBuffer;

    private int mMVPMatrixHandle;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 2;
    static final int COLORS_PER_VERTEX = 3;

    static float squareCoords[] = {   // in counterclockwise order:
            0.0f,    0.04f,    1f, 0f, 0f, // top
            -0.025f,    0f,    1f, 0f, 0f, // bottom left
            0.025f,     0f,    1f, 0f, 0f,// bottom right

            0.0f,   -0.04f,     0f, 0f, 1f,// top
            -0.025f,    0f,     0f, 0f, 1f,// bottom left
            0.025f,     0f,     0f, 0f, 1f,// bottom right

    };

    private final short drawOrder[] = { 0, 1, 2, 0, 2, 3 };

    // Set color with red, green, blue and alpha (opacity) values
    float color[] = {1f, 0f, 0f, 5.0f};

    public Crosshair() {
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                squareCoords.length * 4);
        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer
        vertexBuffer = bb.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        vertexBuffer.put(squareCoords);
        // set the buffer to read the first coordinate
        vertexBuffer.position(0);

        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 2 bytes per short)
                drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);
    }

    public void draw(float[] mvpMatrix, int program, int mPositionHandle, int mColorHandle) {
        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, (COLORS_PER_VERTEX + COORDS_PER_VERTEX) * 4, vertexBuffer);

        // Set color for drawing the triangle
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix");

        // Pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, drawOrder.length);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}