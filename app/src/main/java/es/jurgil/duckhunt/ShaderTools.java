package es.jurgil.duckhunt;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ShaderTools {
    public static String loadShaderResourceToString(Context context, int resourceId){

        InputStream inputStream = context.getResources().openRawResource(resourceId);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder fileContents = new StringBuilder();
        try {
            String s;
            while((s = bufferedReader.readLine()) != null){
                fileContents.append(s);
                fileContents.append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileContents.toString();
    }
}
