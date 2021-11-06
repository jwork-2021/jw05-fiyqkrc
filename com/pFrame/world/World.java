package com.pFrame.world;

import java.awt.Color;

import com.pFrame.creature.Calabash;
import com.pFrame.creature.Floor;
import com.pFrame.creature.Thing;
import com.pFrame.creature.Wall;
import com.pFrame.widget.Position;

public class World {

    private int WIDTH ;
    private int HEIGHT ;

    private Tile<Thing>[][] tiles;

    public World(int width,int height) {

        this.HEIGHT=height;
        this.WIDTH=width;

        if (tiles == null) {
            tiles = new Tile[WIDTH][HEIGHT];
        }

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                tiles[i][j] = new Tile<>(i, j);
                tiles[i][j].setThing(new Floor(this));
            }
        }
        
    }

    public Thing get(int x, int y) {
        if(x<0||x>=this.WIDTH||y<0||y>=this.HEIGHT){
            return new Wall(this);
        }
        return this.tiles[x][y].getThing();
    }

    public void put(Thing t, int x, int y) {
        this.tiles[x][y].setThing(t);
    }

    public int getWidth(){
        return this.WIDTH;
    }

    public int getHeight(){
        return this.HEIGHT;
    }

    public boolean positionValid(Position p) {
        if (p.getX() >= 0 && p.getX() < this.WIDTH)
            if (p.getY() >= 0 && p.getY() < this.HEIGHT)
            {
                String name = tiles[p.getX()][p.getY()].getThing().getClass().getName();
                if (name.split("\\.")[name.split("\\.").length-1].compareTo("Floor")==0)
                    return true;
            }
        return false;
    }

    public static void main(String[] args){
        World world=new World(40, 40);
        System.out.println(world.positionValid(new Position(20, 20)));
        world.put(new Calabash(new Color(20,20,20), 1, world),10,10);
        System.out.println(world.positionValid(new Position(10, 10)));
    }
}
