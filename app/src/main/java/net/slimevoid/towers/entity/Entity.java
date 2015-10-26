package net.slimevoid.towers.entity;

import android.graphics.Canvas;

import net.slimevoid.math.Mat;
import net.slimevoid.math.Vec2;
import net.slimevoid.towers.GameActivity;
import net.slimevoid.towers.view.Sprite;
import net.slimevoid.towers.view.SpriteManager;

public abstract class Entity {

    protected Sprite sprite;

    public Vec2 pos;
    /***
     * Age in seconds
     */
    protected double age;
    public GameActivity game;

    public Entity() {
        pos = Vec2.NULL;
    }

    public void tick(double dt) {
        age += dt;
    }

    public void draw(Canvas canvas, Mat isometric) {
        Vec2 screenPos = isometric.mul(pos, 1);
        sprite.draw(canvas, screenPos);
    }

    public void enterGame(GameActivity game) {
        this.game = game;
        sprite = SpriteManager.getSprite(game.getResources(), getSpriteID(), getSpriteSize());
    }

    public void delete() {

    }

    protected abstract int getSpriteID();
    protected abstract Vec2 getSpriteSize();
}
