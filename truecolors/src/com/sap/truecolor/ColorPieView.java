package com.sap.truecolor;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class ColorPieView extends View {
	
	private final static int PIE_WIDTH = 260;

	private final static int FIELD = 24;

	private int value;
	private ColorDrawable color;
	private TextPaint textPaint;
	private Paint mBgPaints;
	private RectF mOvals, mOvalsDonut;

	public ColorPieView(Context context, int color, int value) {
		super(context);
		this.value = value;
		this.color = new ColorDrawable(getResources().getColor(color));

		setLayoutParams(new LayoutParams(PIE_WIDTH, PIE_WIDTH));
		
		mBgPaints = new Paint();
		mBgPaints.setAntiAlias(true);
		mBgPaints.setStyle(Paint.Style.FILL);
		mBgPaints.setStrokeWidth(0.5f);
		
		textPaint = new TextPaint();
		textPaint.setColor(Color.DKGRAY);
		textPaint.setFakeBoldText(true);
		textPaint.setShadowLayer(0.5f, 1, 1, Color.GRAY);
		textPaint.setTextAlign(Align.CENTER);
		textPaint.setAntiAlias(true);
		
		mOvals = new RectF(14, 14, PIE_WIDTH - 14, PIE_WIDTH - 14);
		mOvalsDonut = new RectF(75, 75, PIE_WIDTH - 75, PIE_WIDTH - 75);
	}

	public ColorPieView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ColorPieView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		mBgPaints.setColor(color.getColor());
		int test = (value - 5) * FIELD;
		canvas.drawArc(mOvals, 270, test, true, mBgPaints);
		mBgPaints.setColor(Color.LTGRAY);
		canvas.drawArc(mOvals, 270 + test, 360 - test, true, mBgPaints);
		mBgPaints.setColor(Color.WHITE);
		canvas.drawArc(mOvalsDonut, 0, 360, true, mBgPaints);
		drawText(canvas, 70, 200, 120, 260, Integer.toString(value), textPaint, dipToPixels(40), 10);

	}

	private void drawText(Canvas canvas, int xStart, int yStart,
			int xWidth, int yHeigth, String textToDisplay,
			TextPaint paintToUse, float startTextSizeInPixels,
			float stepSizeForTextSizeSteps) {

		// Text view line spacing multiplier
		float mSpacingMult = 1.0f;
		// Text view additional line spacing
		float mSpacingAdd = 0.0f;
		
		StaticLayout l = null;
		do {
			paintToUse.setTextSize(startTextSizeInPixels);
			startTextSizeInPixels -= stepSizeForTextSizeSteps;
			l = new StaticLayout(textToDisplay, paintToUse, xWidth,
					Alignment.ALIGN_NORMAL, mSpacingMult, mSpacingAdd, true);
		} while (l.getHeight() > yHeigth);

		int textCenterX = xStart + (xWidth / 2);
		int textCenterY = (yHeigth - l.getHeight()) / 2;

		canvas.save();
		canvas.translate(textCenterX, textCenterY);
		l.draw(canvas);
		canvas.restore();
	}

	private int dipToPixels(int dipValue) {
		Resources r = getResources();
		int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, r.getDisplayMetrics());
		return px;
	}

}
