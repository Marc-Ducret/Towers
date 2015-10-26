package net.slimevoid.towers.view;

import android.content.res.Resources;

import net.slimevoid.math.MathUtils;
import net.slimevoid.math.Vec2;

import java.util.HashMap;
import java.util.Map;

public class SpriteManager {

    private static Map<Integer, Sprite> sprites = new HashMap<>();

    public static Sprite getSprite(Resources res, int id, Vec2 size) {
        int bmpW = (int) (size.x * GameView.PIXEL_PER_METER);
        int bmpH = (int) (size.y * GameView.PIXEL_PER_METER);
        int key = MathUtils.pow(2, id) + MathUtils.pow(3, bmpW) + MathUtils.pow(5, bmpH);
        if(!sprites.containsKey(key)) {
           sprites.put(key, Sprite.loadSprite(res, id, size));
        }
        return sprites.get(key);
    }
}
