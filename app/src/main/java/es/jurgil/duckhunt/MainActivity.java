package es.jurgil.duckhunt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements ToolsFragment.ToolsInterface, GLFragment.IFpsViewer, GLFragment.IPointViewer {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    @Override
    public void reset() {
        GLFragment glFragment = (GLFragment) getFragmentManager().findFragmentById(R.id.gl_fragment);
        glFragment.reset();
    }

    @Override
    public void setMultiplier(float multiplier) {
        GLFragment glFragment = (GLFragment) getFragmentManager().findFragmentById(R.id.gl_fragment);
        glFragment.setMultiplier(multiplier);
    }

    @Override
    public void setFps(float fps) {
        ToolsFragment toolsFragment = (ToolsFragment) getFragmentManager().findFragmentById(R.id.tools_fragment);

        // Happens when gl app is shutting down
        if (toolsFragment != null) {
            toolsFragment.setFps(fps);
        }
    }

    @Override
    public void setPoints(int points) {
        ToolsFragment toolsFragment = (ToolsFragment) getFragmentManager().findFragmentById(R.id.tools_fragment);

        toolsFragment.setPoints(points);
    }
}
