package game.graphic.creature.monster;

import com.alibaba.fastjson.JSONObject;
import com.pFrame.Pixel;
import game.Location;
import game.controller.AlgorithmController;
import game.controller.CreatureController;
import game.graphic.Direction;
import game.graphic.StatedSavable;
import game.graphic.creature.Creature;
import game.graphic.effect.Dialog;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;

abstract public class Monster extends Creature implements StatedSavable {
    private CreatureController oldController;
    protected Creature aim;

    public Monster(String path, int width, int height) {
        super(path, width, height);
        group = 1;
        speed = 2;
    }

    @Override
    public void pause() {
        oldController = controller;
        if (controller instanceof AlgorithmController)
            ((AlgorithmController) controller).stop();
    }

    @Override
    public void Continue() {
        try {
            controller = oldController.getClass().getDeclaredConstructor().newInstance();
            controller.setThing(this);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Creature searchAim() {
        try {
            if (aim == null) {
                Location location = world.searchNearestEnemy(this, 7);
                if (location != null && world.findThing(location) instanceof Creature) {
                    aim = (Creature) world.findThing(location);
                    Dialog dialog = new Dialog("Ya!!!", this.getPosition());
                    world.addItem(dialog);
                    return aim;
                } else
                    return null;
            } else {
                if (Math.abs(aim.getLocation().y() - this.getLocation().y()) < 7 && Math.abs(aim.getLocation().x() - getLocation().x()) < 7) {
                    return aim;
                } else {
                    aim = null;
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            aim = null;
            return aim;
        }
    }

    @Override
    public void whenBeAddedToScene() {
        super.whenBeAddedToScene();
    }

    public void tryMoveToEnemy(){
        if(aim!=null){
            try{
                double direction= Direction.calDirection(getCentralPosition(),aim.getCentralPosition());
                if(!move(direction)){
                    move(direction-Math.PI/2);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /*
    重写scene获取Item图像的方法，为怪物加上血量条
    */
    @Override
    public Pixel[][] getPixels() {
        Pixel[][] pixels = super.getPixels();
        int length = (int) (health * width / healthLimit);
        if (height > 1) {
            for (int b = 0; b < width; b++) {
                if (b < length) {
                    pixels[0][b] = Pixel.getPixel(Color.GREEN, (char) 0xf0);
                } else
                    pixels[0][b] = null;
            }
        }
        return pixels;
    }


}
