package net.slimevoid.towers.entity;

public class TowerProps {

    public final double range;
    public final double firerate;
    public final double aoe;
    public final double projSpeed;
    public final ProjEffect effect;

    public TowerProps(double range, double firerate, double aoe, double projSpeed, ProjEffect effect) {
        this.range = range;
        this.firerate = firerate;
        this.aoe = aoe;
        this.projSpeed = projSpeed;
        this.effect = effect;
    }
}
