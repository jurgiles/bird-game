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

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LabelFormatter;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class ToolsFragment extends Fragment {

    private ToolsInterface toolsCallBack;
    private View layout;
    private GraphView graphX;
    private LineGraphSeries<DataPoint> series;

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

        graphX = (GraphView) layout.findViewById(R.id.graph_x);

        series = new LineGraphSeries<>();

        graphX.addSeries(series);
        graphX.getViewport().setXAxisBoundsManual(true);
        graphX.getViewport().setMaxX(2000);
        graphX.getViewport().setMinX(0);
        graphX.getGridLabelRenderer().setLabelFormatter(new LabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                return isValueX ? "" : String.valueOf(value);
            }

            @Override
            public void setViewport(Viewport viewport) {
            }
        });

        graphX.getViewport().setYAxisBoundsManual(true);
        graphX.getViewport().setMinY(0);
        graphX.getViewport().setMaxY(100);

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

        seekBar.setProgress(40);

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

        //can be avoided if fragment is paused when activity is paused.
        if(getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    fpsView.setText(format);
                    series.appendData(new DataPoint(System.currentTimeMillis(), fps), true, 10000, false);
                }
            });
        }
    }

    public interface ToolsInterface {
        void reset();

        void setMultiplier(float multiplier);
    }
}
