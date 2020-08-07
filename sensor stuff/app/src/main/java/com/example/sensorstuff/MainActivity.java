package com.example.sensorstuff;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor mSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView mTextView = (TextView) findViewById(R.id.textView);
        TextView mTextView2 = (TextView) findViewById(R.id.textView2);
        this.mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        List<Sensor> mList = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        for (int i = 1; i < mList.size(); i++) {
            mTextView.append( mList.get(i).getName() +
                    "--" + mList.get(i).getResolution() +
                    "--" + mList.get(i).getMaximumRange() +
                    "--" + mList.get(i).getPower() + "\n");
        }

        if(mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            this.mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            mTextView2.append("accelerometer found!");
        } else {
            mTextView2.append("No accelerometer found!");
        }
    }
}
