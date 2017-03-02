package es.jurgil.duckhunt;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer renderer;

    public MyGLSurfaceView(Context context, MyGLRenderer renderer) {
        super(context);

        this.renderer = renderer;

        setEGLContextClientVersion(2);

        setRenderer(this.renderer);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                renderer.tapOn(e.getX(), e.getY());
                renderer.flashScreen();
                break;
        }

        return true;
    }
}
