package net.slimevoid.towers.view;

import static java.lang.Math.*;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import net.slimevoid.math.Mat;
import net.slimevoid.math.Vec2;
import net.slimevoid.towers.GameActivity;
import net.slimevoid.towers.entity.Entity;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

	public static final float VIEW_W_METER = 16;
	public static float PIXEL_PER_METER = 0;

	public static boolean isInitialized() {return PIXEL_PER_METER > 0;}

	public GameActivity game;
	public Matrix mat;
	public Matrix invmat;
	public Paint smallTextPaint;
	public sprite background;
//	public Paint textPaint;

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
		return Vec2.NULL.add(s[0], s[1]);
	}
	
	public void gameDraw(Canvas canvas) {
		canvas.save();
		Paint paint = new Paint();
		paint.setColor(0xFF202020);
		canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
		mat.setTranslate(getWidth() / 2, 0);
		mat.invert(invmat);
		canvas.setMatrix(mat);
		synchronized (game.entities) {
			for(Entity e : game.entities) {
				Mat isometric = Mat.scale(1, sin(PI / 6)).mul(Mat.rot(PI / 4));
				e.draw(canvas, isometric);
			}
		}
		canvas.restore();
		//Determine background
		switch(game.level){
			case 1:
				background = SpriteManager.getSprite(game.getResources(), R.drawable.level1, Vec2.NULL.add(1,  1));
				break;
			case 2:
				background = SpriteManager.getSprite(game.getResources(), R.drawable.level2, Vec2.NULL.add(1,  1));
				break;
			case 3:
				background = SpriteManager.getSprite(game.getResources(), R.drawable.level3, Vec2.NULL.add(1,  1));
				break;
			case 4:
				background = SpriteManager.getSprite(game.getResources(), R.drawable.level4, Vec2.NULL.add(1,  1));
				break;
			default:
				background = SpriteManager.getSprite(game.getResources(), R.drawable.level1, Vec2.NULL.add(1,  1));
				break;
		}
		//Draw background
		background.draw(canvas,Vec2.NULL.add(0,  0));
		
		canvas.drawText("fps: "+game.fps, 10, 10, smallTextPaint);
		canvas.drawText("tps: "+game.tps, 10, 25, smallTextPaint);
		canvas.drawText("difficulty: "+game.difficulty, 10, 40, smallTextPaint);
		canvas.drawText("level: "+game.level, 10, 55, smallTextPaint);
		game.frameCT++;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		System.out.println("Surface Changed");
		PIXEL_PER_METER = width / VIEW_W_METER;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		setWillNotDraw(true);
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	}
}
