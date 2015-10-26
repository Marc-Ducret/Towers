package net.slimevoid.towers.entity;

import net.slimevoid.math.Vec2;
import net.slimevoid.towers.R;

public class Creep extends Entity {


    @Override
    protected int getSpriteID() {
        return R.drawable.testrobot;
    }

    @Override
    protected Vec2 getSpriteSize() {
        return Vec2.NULL.add(.5,  .5);
    }
}
