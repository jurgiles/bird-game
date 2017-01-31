package es.jurgil.duckhunt;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor gyroSensor;
    private TextView xView;
    private TextView yView;
    private TextView zView;
    private TextView duckView;

    private SeekBar redSeekView;
    private TextView redSeekLabelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        xView = (TextView) this.findViewById(R.id.x);
        yView = (TextView) this.findViewById(R.id.y);
        zView = (TextView) this.findViewById(R.id.z);

        duckView = (TextView) this.findViewById(R.id.duck);

        redSeekLabelView = (TextView) this.findViewById(R.id.red_value_label);
        redSeekView = (SeekBar) this.findViewById(R.id.red_control);

        redSeekView.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                redSeekLabelView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        duckView.setX(300);
        duckView.setY(400);
    }

    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(this, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int[] pos = new int[2];

        duckView.getLocationOnScreen(pos);
        float newXPos = pos[0] + (.5f * (event.values[0] + Float.valueOf(redSeekLabelView.getText().toString())));
        duckView.setX(newXPos);

        Log.i("position", String.format("old x: %d, new x: %f", pos[0], newXPos));

        xView.setText(String.format("%-8.6f", event.values[0]));
        yView.setText(String.format("%-8.6f", event.values[1]));
        zView.setText(String.format("%-8.6f", event.values[2]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.i("Sensor", String.format("Accuracy changed to %d", accuracy));
    }
}
