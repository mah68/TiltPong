package shapes;

import com.hoyle.tiltpong.CoordConverter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;

public class ScoreKeeper implements Drawable {
	private Integer p1Score;
	private Integer p2Score;
	
	public ScoreKeeper() {
		p1Score = 0;
		p2Score = 0;
	}

	public void draw(Canvas canvas, Paint paint) {
		paint.setTextSize(30);
		paint.setTextAlign(Align.RIGHT);
		paint.setTypeface(Typeface.MONOSPACE);
		canvas.drawText(p1Score.toString(), CoordConverter.screenWidth - 5 , CoordConverter.screenHeight/2 + 35, paint);		
		canvas.drawText(p2Score.toString(), CoordConverter.screenWidth - 5 , CoordConverter.screenHeight/2 - 10, paint);
	}

	public int getPlayerScore(int player) {
		return player == 1 ? p1Score : p2Score;
	}

	public void setPlayerScore(int player, int score) {
		switch(player) {
		case 1: this.p1Score = score; break;
		case 2: this.p2Score = score; break;
		}
	}

}
