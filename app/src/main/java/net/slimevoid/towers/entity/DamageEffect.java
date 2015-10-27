package net.slimevoid.towers.entity;

public class DamageEffect extends ProjEffect {

    public final int damage;

    public DamageEffect(int damage) {
        this.damage = damage;
    }

    @Override
    public void effect(Creep c) {
        c.health -= damage;
    }
}
