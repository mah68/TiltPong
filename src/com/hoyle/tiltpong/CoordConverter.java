package com.hoyle.tiltpong;

import android.view.View;
import android.widget.Toast;

public class CoordConverter {
	public static int screenWidth;
	public static int screenHeight;
	public static final float COORD_WIDTH = 100;
	public static final float COORD_HEIGHT = 150;

	private CoordConverter(View view) {
		CoordConverter.screenWidth = view.getWidth();
		CoordConverter.screenHeight = view.getHeight();

//		Toast.makeText(view.getContext(), "Width: " + screenWidth + "\nHeight: " + screenHeight, Toast.LENGTH_LONG)
//				.show();
	}

	public static void init(View view) {
		new CoordConverter(view);
	}

	public static float convertX(float x) {
		return (x / COORD_WIDTH) * screenWidth;
	}

	public static float convertY(float y) {
		return (y / COORD_HEIGHT) * screenHeight;
	}

}
