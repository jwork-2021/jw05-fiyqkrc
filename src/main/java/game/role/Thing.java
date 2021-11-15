package game.role;

import com.pFrame.Pixel;
import com.pFrame.pgraphic.PGraphicItem;
import game.world.Tile;
import game.world.World;

import java.io.File;

public class Thing extends PGraphicItem{
    protected Tile<? extends Thing> tile;
    protected boolean beCoverAble;
    protected World world;

    public void setWorld(World world){
        this.world=world;
    }

    public boolean isBeCoverAble(){
        return beCoverAble;
    }

    public Thing(File file, int width, int height) {
        super(file, width, height);
        beCoverAble=true;
    }
    
    public Thing(String path,int width,int height){
        super(path, width, height);
        beCoverAble=true;
    }

    public Thing(Pixel[][] pixels){
        super(pixels);
        beCoverAble=true;
    }

    public void setTile(Tile<? extends Thing> tile){
        this.tile=tile;
    }

    public void whenBeAddedToScene(){

    }
}
