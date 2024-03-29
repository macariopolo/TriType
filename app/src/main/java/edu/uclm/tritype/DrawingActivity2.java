package edu.uclm.tritype;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class DrawingActivity2 extends ActionBarActivity {
	private Paint mPaint;
	private float length;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DrawingView mv = new DrawingView(this);
		mv.setDrawingCacheEnabled(true);
		setContentView(mv);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setColor(0xFFFF0000);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(20);
	}

	public class DrawingView extends View {
		public int width;
		public  int height;
		private Bitmap  mBitmap;
		private Canvas  mCanvas;
		private Path    mPath;
		private Paint   mBitmapPaint;
		Context context;
		private Paint circlePaint;
		private Path circlePath;

		public DrawingView(Context c) {
			super(c);
			context=c;
			mPath = new Path();
			mBitmapPaint = new Paint(Paint.DITHER_FLAG);  
			circlePaint = new Paint();
			circlePath = new Path();
			circlePaint.setAntiAlias(true);
			circlePaint.setColor(Color.BLUE);
			circlePaint.setStyle(Paint.Style.STROKE);
			circlePaint.setStrokeJoin(Paint.Join.MITER);
			circlePaint.setStrokeWidth(4f); 
		}

		@Override
		protected void onSizeChanged(int w, int h, int oldw, int oldh) {
			super.onSizeChanged(w, h, oldw, oldh);

			mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
			mCanvas = new Canvas(mBitmap);

		}
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			canvas.drawBitmap( mBitmap, 0, 0, mBitmapPaint);
			canvas.drawPath( mPath,  mPaint);
			canvas.drawPath( circlePath,  circlePaint);
			String text="" + (int) length;
			circlePaint.setTextSize(40);
			canvas.drawText(text, 50, 50, circlePaint);
		}

		private float mX, mY;
		private static final float TOUCH_TOLERANCE = 4;

		private void touch_start(float x, float y) {
			mPath.reset();
			mPath.moveTo(x, y);
			mX = x;
			mY = y;
		}
		private void touch_move(float x, float y) {
			float dx = Math.abs(x - mX);
			float dy = Math.abs(y - mY);
			if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
				mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
				length=length + x -mX + y - mY;
				mX = x;
				mY = y;
				circlePath.reset();
				circlePath.addCircle(mX, mY, 30, Path.Direction.CW);
			}
		}
		private void touch_up() {
			mPath.lineTo(mX, mY);
			circlePath.reset();
			// commit the path to our offscreen
			mCanvas.drawPath(mPath,  mPaint);
			// kill this so we don't double draw
			mPath.reset();
			Intent returnIntent=new Intent();
			returnIntent.putExtra("length", length);
			setResult(RESULT_OK, returnIntent);
			finish();
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			float x = event.getX();
			float y = event.getY();

			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					touch_start(x, y);
					invalidate();
					break;
				case MotionEvent.ACTION_MOVE:
					touch_move(x, y);
					invalidate();
					break;
				case MotionEvent.ACTION_UP:
					touch_up();
					invalidate();
					break;
			}
			return true;
		}  
	}
}
