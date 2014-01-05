package com.example.dicedistributions;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class BubbleSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

	private SurfaceHolder sh;
	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Context ctx;
	
	BubbleThread thread;
	
	public BubbleSurfaceView(Context context) {
		
		super(context);
		sh = getHolder();
		sh.addCallback(this);
		paint.setColor(Color.BLUE);
		paint.setStyle(Style.FILL);
		
		ctx = context;
		setFocusable(true); // make sure we get key events
		
	}
	
	public BubbleThread getThread() {
		return thread;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
		thread.setSurfaceSize(width, height);
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
		thread = new BubbleThread(sh, ctx, new Handler());
		thread.setRunning(true);
		thread.start();
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
		boolean retry = true;
		thread.setRunning(false);
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				
			}
		
		}
	}

	
	class BubbleThread extends Thread {
		
		private int canvasWidth = 200;
		private int canvasHeight = 400;
		private static final int SPEED = 5;
		private boolean run = false;
		
		private float bubbleX;
		private float bubbleY;
		private float headingX;
		private float headingY;
		
		public BubbleThread(SurfaceHolder surfaceHolder, Context context, Handler handler) {
			
			sh = surfaceHolder;
			handler = handler;
			ctx = context;
			
		}
		
		public void doStart() {
			
			synchronized (sh) {
				// Start bubble in center and create some random motion
				
				bubbleX = canvasWidth/2;
				bubbleY = canvasHeight/2;
				headingX = (float) (-1 + (Math.random()*2));
				headingY = (float) (-1 + (Math.random()*2));
				
			}
			
		}
		
		public void run() {
			
			while (run) {
				
				Canvas c = null;
				try {
					c = sh.lockCanvas(null);
					synchronized (sh) {
						doDraw(c);
					}
				} finally {
					if (c!=null) {
						sh.unlockCanvasAndPost(c);
					}
				}
			}
		}
		
		public void setRunning(boolean b) {
			run = b;
		}
		
		public void setSurfaceSize(int width, int height) {
			
			synchronized(sh) {
				canvasWidth = width;
				canvasHeight=height;
				doStart();
			}
			
		}

		private void doDraw(Canvas canvas) {
			
			bubbleX = bubbleX + (headingX*SPEED);
			bubbleY = bubbleY + (headingY*SPEED);
			canvas.restore();
			canvas.drawColor(Color.BLACK);
			canvas.drawCircle(bubbleX, bubbleY, 50, paint);
			
		}
	}
}


		

