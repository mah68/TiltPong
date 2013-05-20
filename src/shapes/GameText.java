package shapes;

import com.hoyle.tiltpong.CoordConverter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class GameText implements Drawable {
	String text;
	float x;
	float y;
	Paint.Align align = Paint.Align.LEFT; // Default align value
	float textSize;
	
	public GameText(String text, float x, float y, float textSize) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.textSize = textSize;
	}
	
	public GameText(String text, float x, float y, float textSize, Paint.Align align) {
		this.align = align;
		this.text = text;
		this.x = x;
		this.y = y;
		this.textSize = textSize;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	public void draw(Canvas canvas, Paint paint) {
		Log.d("CENTER TEXT", "text was drawn");
		paint.setTextAlign(align);
		paint.setTextSize(textSize);
		paint.setColor(Color.WHITE);
		canvas.drawText(text, CoordConverter.convertX(x), CoordConverter.convertY(y), paint);
	}
	
}
