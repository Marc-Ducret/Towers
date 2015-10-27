package net.slimevoid.towers.entity;

import static java.lang.Math.PI;

import net.slimevoid.math.Mat;
import net.slimevoid.math.Vec2;
import net.slimevoid.towers.R;

public class Creep extends Entity {

    public int health;
    //TEMP AI
    private Vec2 dest;
    // END TEMP AI
    private double maxSpeed;
    private double acceleration;
    public double speed;

    public Creep() {
        health = 20;
        maxSpeed = 1;
        acceleration = .25;
    }

    @Override
    public void tick(double dt) {
        super.tick(dt);
        if(health <= 0) {
            delete();
            return;
        }
        if(speed < maxSpeed) speed += acceleration * dt;
        if(speed > maxSpeed) speed = maxSpeed;
        double dp = speed * dt;
        if(dest == null) {
            double theta = game.rand.nextDouble() * PI * 2;
            double r = game.rand.nextGaussian() * 3 + 3;
            dest = pos.add(Mat.rot(theta).mul(Vec2.NULL.add(r, 0), 0));
        }
        if(Vec2.dsq(pos, dest) <= dp*dp) {
            pos = dest;
            dest = null;
        } else {
            pos = pos.add(dest.subst(pos).normalize().mul(dp));
        }
    }

    @Override
    protected int getSpriteID() {
        return R.drawable.testrobot;
    }

    @Override
    protected Vec2 getSpriteSize() {
        return Vec2.NULL.add(1,  1);
    }
}
