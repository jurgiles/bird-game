package es.jurgil.duckhunt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements ToolsFragment.ToolsInterface {
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
}
