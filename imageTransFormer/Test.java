package imageTransFormer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.pFrame.pgraphic.PGraphicItem;

public class Test {
    public static void main(String[] args){
        PGraphicItem item=GraphicItemGenerator.generateItem("/home/fiyqkrc/Icon-120.png",40,40);
        GraphicItemImageGenerator.toImage(item, "/home/fiyqkrc/testImage.png");
        try {
            BufferedImage image=ObjectTransFormer.Rotate(ImageIO.read(new File("/home/fiyqkrc/Icon-120.png")), 60);
            ImageIO.write(image, "png",new FileOutputStream(new File("/home/fiyqkrc/rotatedImage.png")) );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
