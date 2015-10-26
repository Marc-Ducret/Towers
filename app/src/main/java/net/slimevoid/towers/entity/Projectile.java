package net.slimevoid.towers.entity;

import net.slimevoid.math.Vec2;

public class Projectile extends Entity {

    public final TowerProps props;
    public final Creep target;

    public Projectile(TowerProps props, Creep target, Vec2 pos) {
        this.props = props;
        this.target = target;
        this.pos = pos;
    }

    @Override
    public void tick(double dt) {
        super.tick(dt);
        double dp = dt * props.projSpeed;
        if(Vec2.dsq(target.pos, this.pos) <= dp*dp) {
            if(props.aoe > 0) {
                for(Entity e : game.entities) { // TODO Quadtree opti
                    if(e instanceof Creep && Vec2.dsq(e.pos, target.pos) <= props.aoe*props.aoe) {
                        props.effect.effect((Creep) e);
                    }
                }
            } else {
                props.effect.effect(target);
            }
        } else {
            pos = pos.add(target.pos.subst(this.pos).normalize().mul(dp));
        }
    }
}
