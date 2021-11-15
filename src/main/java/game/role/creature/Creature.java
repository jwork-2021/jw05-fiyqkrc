package game.role.creature;


import com.pFrame.Pixel;
import com.pFrame.Position;
import game.controller.CreatureController;
import game.role.Controllable;
import game.role.Thing;
import imageTransFormer.GraphicItemGenerator;

import java.io.File;
import java.util.HashMap;

public class Creature extends Thing implements Controllable {
    protected static HashMap<String,Body[]> SourceMap=new HashMap<>();

    protected Body[] Bodys;
    protected int lastImageIndex;

    protected CreatureController controller;

    protected int speed = 5;

    public Creature(String path, int width, int height) {
        super((Pixel[][]) null);

        controller=new CreatureController();

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

        if(world==null||world.isLocationReachable(this,Position.getPosition(this.p.getX() - (int) y, this.p.getY() + (int) x)))
            this.setPosition(Position.getPosition(this.p.getX() - (int) y, this.p.getY() + (int) x));
    }

    private void switchImage(int i){
        if(Bodys[i]!=null){
            graphic=Bodys[i].pixels();
            width=Bodys[i].width();
            height=Bodys[i].height();
        }
    }

}

record Body(Pixel[][] pixels, int width, int height) {
}
