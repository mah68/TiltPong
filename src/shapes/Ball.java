package shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.hoyle.tiltpong.CoordConverter;

public class Ball implements Drawable {

	private float x;
	private float y;
	private float speed;
	private float angle;
	
	public static final float SPEED_EASY = 40;
	public static final float SPEED_MEDIUM = 55;
	public static final float SPEED_HARD = 70;
	public static final float SPEED_INSANE = 85;
	public static final float BALL_WIDTH = 3;

	public Ball() {
		x = CoordConverter.COORD_WIDTH / 2;
		y = CoordConverter.COORD_HEIGHT / 2;
		speed = SPEED_MEDIUM;
		angle = 270;
	}

	public void update(int framerate) {

		x = (float) (x + (speed / framerate) * Math.cos(angle * Math.PI / 180));
		y = (float) (y - (speed / framerate) * Math.sin(angle * Math.PI / 180));

		if (x + BALL_WIDTH >= CoordConverter.COORD_WIDTH || x <= 0) {
			angle = 180 - angle;
		}
		normalizeAngle();
	}

	private void normalizeAngle() {
		if (angle > 360)
			angle -= 360;
		if (angle < 0)
			angle += 360;
	}

	private void changeAngle(Paddle paddle, int offsetDirection) {
		float offset = offsetDirection * ((paddle.getX() + (Paddle.PADDLE_WIDTH / 2)) - (x + (BALL_WIDTH / 2)));
		angle = 360 - angle + (offset * 5);
		normalizeAngle();
	}

	public void checkCollisionAbove(Paddle paddle) {
		if (y < (paddle.getY() + Paddle.PADDLE_HEIGHT) & y > (paddle.getY() + Paddle.PADDLE_HEIGHT - 1.5)
				& x+BALL_WIDTH >= paddle.getX() & x <= (paddle.getX() + Paddle.PADDLE_WIDTH)) {
			y = (paddle.getY() + Paddle.PADDLE_HEIGHT);
			changeAngle(paddle, -1);
		}
	}

	public void checkCollisionBelow(Paddle paddle) {
		if (y + BALL_WIDTH > paddle.getY() & (y + BALL_WIDTH) < (paddle.getY() + 1.5) & x+BALL_WIDTH >= paddle.getX()
				& x <= (paddle.getX() + Paddle.PADDLE_WIDTH)) {
			y = paddle.getY() - BALL_WIDTH;
			changeAngle(paddle, 1);
		}
	}
	
	public void reset(Float angle, Float speed) {
		this.angle = angle;
		this.speed = speed;
		x = CoordConverter.COORD_WIDTH / 2;
		y = CoordConverter.COORD_HEIGHT / 2;
	}

	public void draw(Canvas canvas, Paint paint) {
		paint.setColor(Color.WHITE);
		canvas.drawRect(CoordConverter.convertX(x), CoordConverter.convertY(y),
				CoordConverter.convertX(x + BALL_WIDTH), CoordConverter.convertY(y + BALL_WIDTH), paint);

	}

	public float getSpeed() {
		return speed;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}

}
