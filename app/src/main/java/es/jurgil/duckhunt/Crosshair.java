package es.jurgil.duckhunt;


import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Crosshair {
    private FloatBuffer vertexBuffer;

    private int mMVPMatrixHandle;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 2;
    static final int COLORS_PER_VERTEX = 3;

    static float squareCoords[] = {   // in counterclockwise order:
            0.0f,    0.04f,    1f, 0f, 0f, // top
            -0.025f,    0f,    0f, 1f, 0f, // bottom left
            0.025f,     0f,    0f, 0f, 1f,// bottom right

            0.0f,   -0.04f,     0f, 0f, 1f,// top
            -0.025f,    0f,     0f, 1f, 0f,// bottom left
            0.025f,     0f,     1f, 0f, 0f,// bottom right
    };

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
    }

    public void draw(float[] mvpMatrix, int program, int mPositionHandle, int aColorLocation) {
        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glEnableVertexAttribArray(aColorLocation);

        vertexBuffer.position(0);
        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, (COLORS_PER_VERTEX + COORDS_PER_VERTEX) * 4, vertexBuffer);

        vertexBuffer.position(COORDS_PER_VERTEX);
        GLES20.glVertexAttribPointer(aColorLocation, COLORS_PER_VERTEX, GLES20.GL_FLOAT, false, (COORDS_PER_VERTEX + COLORS_PER_VERTEX) * 4, vertexBuffer);

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix");

        // Pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, squareCoords.length / (COLORS_PER_VERTEX+COORDS_PER_VERTEX));

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}