package com.hoyle.tiltpong;

import shapes.Ball;
import shapes.GameText;
import shapes.Paddle;
import shapes.ScoreKeeper;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Toast;

public class TiltPongMainActivity extends Activity implements SensorEventListener, OnClickListener {

	private TiltPongView mView;
	private TiltPongModel mModel;

	private SensorManager sensorManager = null;
	private Float accelerometerX;

	private GameText centerText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		mView = new TiltPongView(this);
		setContentView(mView);

		// Set up accelerometer
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		accelerometerX = 0.0f;

		// Wake lock
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		// Create game objects
		Paddle p1Paddle = new Paddle(140 - Paddle.PADDLE_HEIGHT);
		Paddle p2Paddle = new Paddle(10);
		Ball ball = new Ball();
		ScoreKeeper scores = new ScoreKeeper();
		this.centerText = new GameText("Touch the screen to begin", CoordConverter.COORD_WIDTH / 2,
				CoordConverter.COORD_HEIGHT / 2 + 35, 30, Paint.Align.CENTER);

		// set listeners
		mView.registerDrawListener(p1Paddle);
		mView.registerDrawListener(p2Paddle);
		mView.registerDrawListener(ball);
		mView.registerDrawListener(scores);
		mView.registerDrawListener(centerText);
		mView.setOnClickListener(this);

		// create model
		mModel = new TiltPongModel(p1Paddle, p2Paddle, ball, scores, this);
	}

	public void update() {
		mView.invalidate();
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			// accelerometerX = (float) -(Math.pow(event.values[0],3) / 10);
			accelerometerX = -event.values[0];
			// Toast.makeText(this, ((Float) event.values[0]).toString(),
			// Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
		// sensorManager.registerListener(this,
		// sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
		// SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onStop() {
		sensorManager.unregisterListener(this);
		mModel.setPaused(true);
		centerText.setText("Paused");
		mView.registerDrawListener(centerText);
		mView.invalidate();
		super.onStop();
	}

	public float getAccelerometerX() {
		return accelerometerX;
	}

	// Handles pausing when screen is touched
	public void onClick(View v) {
		if (mModel.isPaused()) {
			mModel.setPaused(false);
			mView.removeDrawListener(centerText);
			mModel.update();
		} else {
			mModel.setPaused(true);
			centerText.setText("Paused");
			mView.registerDrawListener(centerText);
			mView.invalidate();
		}
	}

}