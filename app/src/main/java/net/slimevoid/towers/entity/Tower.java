package net.slimevoid.towers.entity;

import net.slimevoid.math.Vec2;

public class Tower extends Entity {

    public final TowerProps props;
    private double fireCD;

    public Tower(TowerProps props, Vec2 pos) {
        this.props = props;
        this.pos = pos;
        fireCD = 0;
    }

    @Override
    public void tick(double dt) {
        super.tick(dt);
        if(fireCD > 0) {
            fireCD -= dt;
        } else {
            double mindsq = Double.POSITIVE_INFINITY;
            Creep creep = null;
            for(Entity e : game.entities) { // TODO Quadtree opti
                if(e instanceof Creep) {
                    double dsq = Vec2.dsq(e.pos, this.pos);
                    if(dsq < mindsq) {
                        mindsq = dsq;
                        creep = (Creep) e;
                    }
                }
            }
            if(mindsq <= props.range) {
                game.addEntity(new Projectile(props, creep, pos));
            }
        }
    }
}
