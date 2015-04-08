package com.example.android_shake;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener{

	private SensorManager manager;
	private Sensor sensor;
	private TextView textView;
	private Button button;
	//定义传感器的数据：比如晃动的幅度，初始值等
	//最后需要晃动的幅度
	private float lastX = 0.f;
	//每一个需要晃动的幅度
	private float shake = 30.0f;
	private boolean isShake = false;//是否需要晃动
	private int level = 3;//连续晃动达到3次
	private int count = 0;//累加达到每次的次数
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        button = (Button)this.findViewById(R.id.button1);
        textView = (TextView)this.findViewById(R.id.textView1);
        button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isShake = false;
				count = 0;
				lastX = 0.0f;
				textView.setText("一共摇了"+count+"次");
			}
		});
    }

    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	manager.unregisterListener(this);
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		final float x = event.values[0];
		if (Math.abs(lastX - x) > shake){
			count++;
			isShake = false;
		}
		lastX = x;
		textView.setText("一共摇了"+count+"次");
		if (!isShake && count >= level && (count % level == 0)) {
			isShake = true;
			Toast.makeText(MainActivity.this, "成功摇了3次", 1).show();
//成功摇了count次			
		}
	}
    
}
