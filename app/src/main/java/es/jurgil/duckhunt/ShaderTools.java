package es.jurgil.duckhunt;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ShaderTools {
    public static String loadShaderResourceToString(Context context, int resourceId) {

        InputStream inputStream = context.getResources().openRawResource(resourceId);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder fileContents = new StringBuilder();
        try {
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                fileContents.append(s);
                fileContents.append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileContents.toString();
    }

    public static int loadFragmentShader(String shaderCode) {
        return loadShader(GLES20.GL_FRAGMENT_SHADER, shaderCode);
    }

    public static int loadVertexShader(String shaderCode) {
        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        return loadShader(GLES20.GL_VERTEX_SHADER, shaderCode);
    }

    private static int loadShader(int type, String shaderCode) {
        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        if (shader == 0) {
            Log.e("unexpected", "failed to create shader");
        }

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

        if(compileStatus[0] == 0){
            GLES20.glDeleteShader(shader);
        }

        String compileMessage = GLES20.glGetShaderInfoLog(shader);
        Log.i(type == GLES20.GL_VERTEX_SHADER ? "GL_VERTEX_SHADER" : "GL_FRAGMENT_SHADER", compileMessage.isEmpty() ? "compiled successfully" : compileMessage);

        return shader;
    }

    static int setupGLProgram(int fragmentShader, int vertexShader) {
        // create empty OpenGL ES Program
        int programId = GLES20.glCreateProgram();

        // add the vertex shader to programId
        GLES20.glAttachShader(programId, vertexShader);

        // add the fragment shader to programId
        GLES20.glAttachShader(programId, fragmentShader);

        // creates OpenGL ES programId executables
        GLES20.glLinkProgram(programId);

        final int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(programId, GLES20.GL_LINK_STATUS, linkStatus, 0);

        if(linkStatus[0] == 0){
            GLES20.glDeleteProgram(programId);
            Log.e("unexpected", "Failed to link application");
        }

        String linkingMessage = GLES20.glGetProgramInfoLog(programId);
        Log.i("GL_linking", linkingMessage.trim().isEmpty() ? "linked successfully" : linkingMessage);

        return programId;
    }
}
