package es.jurgil.duckhunt;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class MyGLSurfaceView extends GLSurfaceView {
    public MyGLSurfaceView(Context context, MyGLRenderer renderer) {
        super(context);

        setEGLContextClientVersion(2);

        setRenderer(renderer);
    }
}
