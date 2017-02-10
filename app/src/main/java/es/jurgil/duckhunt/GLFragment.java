package es.jurgil.duckhunt;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class GLFragment extends Fragment {

    private MyGLSurfaceView myGLSurfaceView;
    private MyGLRenderer renderer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        renderer = new MyGLRenderer(this.getActivity(), (IFpsViewer) this.getActivity());

        myGLSurfaceView = new MyGLSurfaceView(this.getActivity(), renderer);

        return myGLSurfaceView;
    }

    public void reset() {
        renderer.reset();
    }

    public void setMultiplier(float multiplier) {
        renderer.setMuliplier(multiplier);
    }

    public interface IFpsViewer {
        void setFps(float fps);
    }
}
