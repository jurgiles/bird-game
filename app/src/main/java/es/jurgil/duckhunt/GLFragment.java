package es.jurgil.duckhunt;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class GLFragment extends Fragment {

    private MyGLSurfaceView myGLSurfaceView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myGLSurfaceView = new MyGLSurfaceView(this.getActivity());

        return myGLSurfaceView;
    }

    public void reset() {
        myGLSurfaceView.reset();
    }
}
