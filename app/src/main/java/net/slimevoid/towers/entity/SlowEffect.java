package net.slimevoid.towers.entity;

public class SlowEffect extends ProjEffect {

    @Override
    public void effect(Creep c) {
        c.speed = 0;
    }
}
