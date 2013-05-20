package com.hoyle.tiltpong;

import shapes.Ball;
import shapes.Paddle;
import shapes.ScoreKeeper;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class TiltPongModel {
	private Paddle p1Paddle;
	private Paddle p2Paddle;
	private Ball ball;
	private ScoreKeeper scores;
	private Context mainActivity;
	private FramerateHandler mHandler;
	
	private Boolean paused;
	
	private float AISpeed = .4f;
	
	public static final int FRAMERATE = 50;

	public TiltPongModel(Paddle p1Paddle, Paddle p2Paddle, Ball ball, ScoreKeeper scores, Context context) {
		this.p1Paddle = p1Paddle;
		this.p2Paddle = p2Paddle;
		this.ball = ball;
		this.scores = scores;
		this.mainActivity = context;
		this.paused = true; // require a touch to start the game
		
		mHandler = new FramerateHandler();
		update();
	}
	
	public void update() {
		if (paused) return;
		
		long delay = System.currentTimeMillis();
		
		// Game logic here
		float dx = ((TiltPongMainActivity) mainActivity).getAccelerometerX();
		p1Paddle.addX(dx);
		updateAI(p2Paddle);
		
		ball.checkCollisionBelow(p1Paddle);
		ball.checkCollisionAbove(p2Paddle);
		ball.update(FRAMERATE);

		checkRoundWin();
		// end game logic
		
		((TiltPongMainActivity) mainActivity).update();
		delay = System.currentTimeMillis() - delay;
		mHandler.nextFrame(Math.max((1000/FRAMERATE),0));
	}
	
	private void checkRoundWin() {
		if (ball.getY() <= 0) {
			scores.setPlayerScore(1, scores.getPlayerScore(1)+1);
			ball.reset(((float) (250+(Math.random()*40))), Ball.SPEED_MEDIUM);
		}
		if (ball.getY() + Ball.BALL_WIDTH >= CoordConverter.COORD_HEIGHT) {
			scores.setPlayerScore(2, scores.getPlayerScore(2)+1);
			ball.reset(((float) (70+(Math.random()*40))), Ball.SPEED_MEDIUM);
		}
	}
	
	public void setPaused(Boolean b) {
		paused = b;
	}
	
	public Boolean isPaused() {
		return paused;
	}

	private void updateAI(Paddle paddle) {
		if(ball.getX() > paddle.getX()+(Paddle.PADDLE_WIDTH/2)) paddle.addX(AISpeed);
		else													paddle.addX(-AISpeed);
	}
	
	public void changeBallSpeed(float newSpeed) {
		ball.setSpeed(newSpeed);
	}

	private class FramerateHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			update();
		}

		public void nextFrame(long delay) {
			this.removeMessages(0);
			this.sendMessageDelayed(obtainMessage(0), delay);
		}
	}
	
	
}
