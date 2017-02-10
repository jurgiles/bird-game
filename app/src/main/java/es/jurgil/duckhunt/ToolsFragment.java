package es.jurgil.duckhunt;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class ToolsFragment extends Fragment {

    private ToolsInterface toolsCallBack;
    private View layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.tools_main, container);

        Button resetButton = (Button) layout.findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolsCallBack.reset();
            }
        });

        SeekBar seekBar = (SeekBar) layout.findViewById(R.id.seek_bar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                toolsCallBack.setMultiplier(progress / 100f);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        toolsCallBack = (ToolsInterface) context;
    }

    public void setFps(final float fps) {
        final String format = String.format("%.2f", fps);
        final TextView fpsView = (TextView) layout.findViewById(R.id.fps_text);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fpsView.setText(format);
            }
        });
    }

    public interface ToolsInterface {
        void reset();
        void setMultiplier(float multiplier);
    }
}
