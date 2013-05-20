package com.hoyle.tiltpong;

import java.util.ArrayList;
import java.util.List;

import shapes.Drawable;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.View;

public class TiltPongView extends View {
	private Paint mPaint;
	private List<Drawable> drawListeners;

	public TiltPongView(Context context) {
		super(context);
		mPaint = new Paint();
		mPaint.setStyle(Style.FILL);
		drawListeners = new ArrayList<Drawable>();
	}
	
	public void registerDrawListener(Drawable d) {
		if (!drawListeners.contains(d))	drawListeners.add(d);
	}
	
	public void removeDrawListener(Drawable d) {
		drawListeners.remove(d);
	}
	
	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w,h,oldw,oldh);
		// initialize the coordinate converter
		if(w != 0 & oldw == 0) {
			CoordConverter.init(this);
		}
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawColor(0xff333333); // Fill screen w/ dark grey
		mPaint.setColor(Color.WHITE);
		mPaint.setPathEffect(new DashPathEffect(new float[] {10,10}, 5));
	    canvas.drawLine(0, getHeight()/2, getWidth(), getHeight()/2, mPaint);
	    mPaint.setPathEffect(null);
	    //draw all shapes
		for (Drawable d : drawListeners) {
			d.draw(canvas, mPaint);
		}
		
	}
}
