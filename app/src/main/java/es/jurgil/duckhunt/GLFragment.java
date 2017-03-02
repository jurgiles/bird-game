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
        SfxLibrary sfxLibrary = new SfxLibrary(getActivity());

        renderer = new MyGLRenderer(this.getActivity(), (IFpsViewer) this.getActivity(), (IPointViewer) this.getActivity(), new Game(sfxLibrary));

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

    public interface IPointViewer {
        void setPoints(int points);
    }


    @Override
    public void onPause() {
        super.onPause();
        myGLSurfaceView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        myGLSurfaceView.onResume();
    }
}
