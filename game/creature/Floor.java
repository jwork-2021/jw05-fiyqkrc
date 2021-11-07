package game.creature;

import java.awt.Color;

import game.world.GameWorld;

public class Floor extends Thing {

    public Floor(GameWorld world) {
        super(Color.GRAY, (char) 0 , world);
    }

}
