package net.slimevoid.towers.entity;

import android.graphics.Canvas;

import net.slimevoid.math.Mat;
import net.slimevoid.math.Vec2;
import net.slimevoid.towers.GameActivity;

public abstract class Entity {

    // TODO animating

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

    }

    public void enterGame(GameActivity game) {
        this.game = game;
    }

    public void delete() {

    }
}
