package game.graphic.creature;


import com.pFrame.Pixel;
import com.pFrame.Position;
import game.controller.AlogrithmController;
import game.controller.CreatureController;
import game.graphic.Controllable;
import game.graphic.Thing;
import game.graphic.effect.BloodChange;
import game.graphic.effect.Dialog;
import game.graphic.effect.Swoon;
import imageTransFormer.GraphicItemGenerator;

import java.util.HashMap;

public abstract class Creature extends Thing implements Controllable {
    protected static HashMap<String,Body[]> SourceMap=new HashMap<>();

    protected Body[] Bodys;
    protected int lastImageIndex;

    protected CreatureController controller;

    protected int speed = 5;

    public Creature(String path, int width, int height) {
        super(null);

        beCoverAble=false;

        Bodys=new Body[8];
        if(!SourceMap.containsKey(path)) {
            for (int i = 0; i < 8; i++) {
                Pixel[][] pixels = Pixel.valueOf(GraphicItemGenerator.generateItem(path + String.format("/%d.png", i + 1), width,height));
                Bodys[i] = new Body(pixels, width, height);
            }
            SourceMap.put(path,Bodys);
        }
        else{
            Bodys=SourceMap.get(path);
        }
        switchImage(1);
    }

    public void speak(String text){
        Dialog dialog=new Dialog(text,this.getPosition());
        this.world.addItem(dialog);
    }

    @Override
    public void setController(CreatureController controller) {
        this.controller = controller;
        controller.setThing(this);
    }

    @Override
    public CreatureController getController() {
        return this.controller;
    }

    @Override
    public void attack() {
        Swoon swoon=new Swoon(this.getPosition());
        world.addItem(swoon);
    }

    @Override
    public void move(double direction) {
        double y = Math.sin(direction) * speed;
        double x = Math.cos(direction) * speed;

        int nextImage=1;
        int nextDirection=(int)Math.floor((direction+Math.PI/4)*2/Math.PI);
        nextImage=((lastImageIndex%2)+1)%2+nextDirection*2;
        switchImage(nextImage);
        lastImageIndex=nextImage;

        if(world==null||world.isLocationReachable(Position.getPosition(this.p.getX() - (int) y, this.p.getY() + (int) x)))
            this.setPosition(Position.getPosition(this.p.getX() - (int) y, this.p.getY() + (int) x));
    }

    private void switchImage(int i){
        if(Bodys[i]!=null){
            graphic=Bodys[i].pixels();
            width=Bodys[i].width();
            height=Bodys[i].height();
        }
    }

    abstract public void pause();
    abstract public void Continue();

    record Body(Pixel[][] pixels, int width, int height) {
    }
}


