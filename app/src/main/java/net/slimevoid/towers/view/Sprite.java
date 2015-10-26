package net.slimevoid.towers.view;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import net.slimevoid.math.Vec2;

public class Sprite {

    // TODO Anim

    public final Vec2 size;
    public final Bitmap bmp;

    private Sprite(Bitmap bmp, Vec2 size) {
        this.size = size;
        this.bmp = bmp;
    }

    public void draw(Canvas can, Vec2 pos) {
        can.drawBitmap(bmp,  (float) (pos.x - size.x/2) * GameView.PIXEL_PER_METER,
                             (float) (pos.y - size.y/2) * GameView.PIXEL_PER_METER, new Paint());
    }

    protected static Sprite loadSprite(Resources res, int id, Vec2 size) {
        Bitmap bmp = BitmapFactory.decodeResource(res, id);
        bmp = Bitmap.createScaledBitmap(bmp, (int) (size.x * GameView.PIXEL_PER_METER), (int) (size.y * GameView.PIXEL_PER_METER), true);
        return new Sprite(
                bmp,
                size
                );
    }
}
