package shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.hoyle.tiltpong.CoordConverter;

public class Paddle implements Drawable {

	private float x;
	private float y;
	public static final float PADDLE_WIDTH = 20;
	public static final float PADDLE_HEIGHT = 3;
	
	public Paddle(float height) {
		x = ((CoordConverter.COORD_WIDTH - PADDLE_WIDTH)/ 2);
		y = height;
	}

	public void draw(Canvas canvas, Paint paint) {
		paint.setColor(Color.WHITE);
		canvas.drawRect(CoordConverter.convertX(x), CoordConverter.convertY(y),
				CoordConverter.convertX(x + PADDLE_WIDTH), CoordConverter.convertY(y + PADDLE_HEIGHT), paint);

	}
	
	public void addX(float dx) {
		this.x += dx;
		if (x + PADDLE_WIDTH > CoordConverter.COORD_WIDTH) {
			this.x = CoordConverter.COORD_WIDTH - PADDLE_WIDTH;
		}
		if (x <0 ) {
			this.x = 0;
		}
		
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}

}
