package net.slimevoid.towers.view;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import net.slimevoid.math.Vec2;
import net.slimevoid.towers.GameActivity;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
	
	public static float PIXEL_PER_METER = 70;

	public GameActivity game;
	public Matrix mat;
	public Matrix invmat;
	public Paint smallTextPaint;
	public Paint textPaint;
	
	public GameView(GameActivity game) {
		super(game);
		this.game = game;
		smallTextPaint = new Paint();
		smallTextPaint.setColor(0xFFFFFFFF);
		smallTextPaint.setTypeface(Typeface.create("Consolas", Typeface.NORMAL));
		smallTextPaint.setTextSize(13);
//		textPaint = new Paint();
//		textPaint.setColor(0xFFFFFFFF);
//		textPaint.setTextAlign(Align.CENTER);
//		textPaint.setTypeface(Typeface.create("Helvetica", Typeface.NORMAL));
//		textPaint.setTextSize(30);
//		textPaint.setAntiAlias(true);
		mat = new Matrix();
		invmat = new Matrix();
		setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		getHolder().addCallback(this);
	}

	public Vec2 getViewportCenter() {
		return Vec2.NULL;
	}
	
	public Vec2 getViewportHalfSize() {
		float[] s = new float[]{getWidth()/2, getHeight()/2};
		invmat.mapVectors(s);
		return new Vec2(s[0], s[1]);
	}
	
	public void gameDraw(Canvas canvas) {
		canvas.save();
		Paint paint = new Paint();
		paint.setColor(0xFF202020);
		canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
		mat.setTranslate(getWidth() / 2, getHeight()/2);
		mat.preScale(PIXEL_PER_METER, PIXEL_PER_METER);
//		mat.preTranslate(-(float)game.player.pos.x, -(float)game.player.pos.y);
		mat.invert(invmat);
		canvas.setMatrix(mat);
//		game.map.draw(canvas);
//		synchronized (game.entities) {
//			for(Entity e : game.entities) {
//				if(!(e instanceof Player)) e.draw(canvas);
//			}
//		}
//		if(game.player.game != null) game.player.draw(canvas);
		canvas.restore();
		canvas.drawText("fps: "+game.fps, 10, 10, smallTextPaint);
		canvas.drawText("tps: "+game.tps, 10, 25, smallTextPaint);
		game.frameCT++;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		setWillNotDraw(true);
		try {
			if(game.getPackageManager().getPackageInfo(game.getPackageName(), 0).versionCode >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
				densityCheck();
			}
		} catch (Exception e) {
		}
	}
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	public void densityCheck() {
		DisplayMetrics dm = new DisplayMetrics();
		getDisplay().getMetrics(dm);
		PIXEL_PER_METER = dm.xdpi * .3F;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
	}

}
