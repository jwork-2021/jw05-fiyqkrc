package game.graphic.creature.monster;

import com.pFrame.Position;
import game.Location;
import game.world.World;

import java.util.Date;

public class Master extends Monster {
    protected long lastAttack;
    protected long codeTime = 12000;

    public Master() {
        super(Pangolin.class.getClassLoader().getResource("image/monster/Master/").getPath(), World.tileSize, World.tileSize);
        health = 500;
        attack = 20;
        resistance = 0.2;
        speed = 4;
        speedLimit = speed;
        healthLimit = health;
        resistanceLimit = resistance;
        attackLimit = attack;
    }

    @Override
    public void responseToEnemy() {
        super.responseToEnemy();
        if (new Date().getTime() - lastAttack > this.codeTime) {
            if ((Math.abs(p.getX() - aim.getPosition().getX()) > 2 * World.tileSize || Math.abs(p.getY() - aim.getPosition().getY()) > 2 * World.tileSize))
                tryMoveToEnemy();
            else {
                int x = this.world.getTileByLocation(getCentralPosition()).x();
                int y = this.world.getTileByLocation(getCentralPosition()).y();
                for (int i = x - 2; i <= x + 2; i++) {
                    for (int j = y - 2; j <= y + 2; j++) {
                        if (!getWorld().locationOutOfBound(new Location(i, j)) && world.findThing(new Location(i, j)) == null) {
                            Vine vine = new Vine(this, Position.getPosition(i * World.tileSize, j * World.tileSize));
                            world.addItem(vine);
                        }
                    }
                }
                lastAttack = new Date().getTime();
            }
        }
    }
}