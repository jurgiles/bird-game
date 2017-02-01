package es.jurgil.duckhunt;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    private GLSurfaceView mGLView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);
//
//        setContentView(R.layout.activity_main);
//        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//
//        gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
//
//        xView = (TextView) this.findViewById(R.id.x);
//        yView = (TextView) this.findViewById(R.id.y);
//        zView = (TextView) this.findViewById(R.id.z);
//
//        duckView = (TextView) this.findViewById(R.id.duck);
//
//        redSeekLabelView = (TextView) this.findViewById(R.id.red_value_label);
//        redSeekView = (SeekBar) this.findViewById(R.id.red_control);
//
//        redSeekView.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                redSeekLabelView.setText(String.valueOf(progress));
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//            }
//        });
//
//        Button resetButton = (Button) this.findViewById(R.id.reset_button);
//        resetButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                duckView.setX(300f);
//                duckView.setY(300f);
//            }
//        });
//
//        duckView.setX(300f);
//        duckView.setY(300f);
//    }

}

    @Override
    protected void onResume() {
        super.onResume();

//        sensorManager.registerListener(this, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
//        int multiplier = redSeekView.getProgress() * 10;
//
//        float[] oldPos = new float[]{duckView.getX(), duckView.getY()};
//
//        float newXPos = duckView.getX() + (event.values[0] * multiplier);
//        duckView.setX(newXPos);
//
//        float newYPos = duckView.getY() + (-1 * event.values[1] * multiplier);
//        duckView.setY(newYPos);
//
////        Log.i("position", String.format("x[old: %f, new: %f, rot: %f] y[old: %f, new: %f, rot: %f] mult: %d", oldPos[0], newXPos, event.values[0], oldPos[1], newYPos, event.values[1], multiplier));
//
//        xView.setText(String.format("%-8.6f", event.values[0]));
//        yView.setText(String.format("%-8.6f", event.values[1]));
//        zView.setText(String.format("%-8.6f", event.values[2]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.i("Sensor", String.format("Accuracy changed to %d", accuracy));
    }
}
