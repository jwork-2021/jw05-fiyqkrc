package com.pFrame;

import java.awt.Color;

import com.pFrame.pgraphic.PGraphicItem;
import com.pFrame.pgraphic.PImage;
import imageTransFormer.ObjectTransFormer;

import java.awt.image.*;

public class Pixel {
    private Color color;
    private char ch;

    private Pixel(Color color, char ch) {
        this.ch = ch;
        this.color = color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public char getCh() {
        return this.ch;
    }

    public Pixel copy() {
        return new Pixel(color, ch);
    }

    public static Pixel getPixel(Color color, char ch) {
        return new Pixel(color, ch);
    }


    /**
     * add the pixel of dest to src
     * @param src the src pixel array
     * @param dest the dest pixel array
     * @param position  the start position of copy
     * @return  return the src pixel array after add operation
     */
    static public Pixel[][] pixelsAdd(Pixel[][] src, Pixel[][] dest, Position position) {
        if (src == null) {
            return null;
        } else if (dest == null) {
            return src;
        } else {
            int h = dest.length;
            int w = dest[0].length;
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    if ((position.getX() + i < src.length && position.getX() + i >= 0)
                            && (position.getY() + j < src[0].length && position.getY() + j >= 0)) {
                        if (dest[i][j] != null)
                            src[position.getX() + i][position.getY() + j] = dest[i][j];
                    }
                }
            }
            return src;
        }
    }

    static public Pixel[][] emptyPixels(int width, int height) {
        if(width>0&&height>0) {
            Pixel[][] pixels = new Pixel[height][width];
            for (int i = 0; i < height; i++)
                for (int j = 0; j < width; j++)
                    pixels[i][j] = null;
            return pixels;
        }
        else
            return null;
    }

    static public Pixel[][] valueOf(PGraphicItem item) {
        return item.getPixels();
    }

    static public Pixel[][] valueOf(PImage item) {
        return item.getPixels();
    }

    static public Pixel[][] subPixels(Pixel[][] pixels, Position p, int width, int height) {
        Pixel[][] res = Pixel.emptyPixels(width, height);
        if (pixels == null)
            return null;
        int h = pixels.length;
        int w = pixels[0].length;
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                if ((p.getX() + i < h && p.getX() + i >= 0) && (p.getY() + j < w && p.getY() + j > 0)) {
                    res[i][j] = pixels[p.getX() + i][p.getY() + j];
                }
            }
        return res;
    }

    static  public Pixel[][] getPixelsScaleInstance(Pixel[][] pixels,int width,int height){
        BufferedImage image=Pixel.toBufferedImage(pixels);
        BufferedImage scaledImage= ObjectTransFormer.toBufferedImage(image.getScaledInstance(width,height,BufferedImage.SCALE_SMOOTH));
        return Pixel.valueOf(scaledImage);
    }

    static public Pixel[][] pixelsScaleLarger(Pixel[][] pixels, int scale) {
        if (pixels == null || scale == 1)
            return Pixel.pixelsCopy(pixels);
        else {
            int originHeight = pixels.length;
            int originWidth = pixels[0].length;
            Pixel[][] res = Pixel.emptyPixels(originWidth * scale, originHeight * scale);
            for (int i = 0; i < originHeight; i++) {
                for (int j = 0; j < originWidth; j++) {
                    for (int a = 0; a < scale; a++) {
                        for (int b = 0; b < scale; b++) {
                            res[i * scale + a][j * scale + b] = pixels[i][j];
                        }
                    }
                }
            }
            return res;
        }
    }

    static public Pixel[][] pixelsCopy(Pixel[][] pixels) {
        if (pixels == null)
            return null;
        else {
            int width = pixels[0].length;
            int height = pixels.length;
            Pixel[][] res = Pixel.emptyPixels(width, height);
            for (int i = 0; i < height; i++)
                System.arraycopy(pixels[i], 0, res[i], 0, width);
            return res;
        }
    }

    static public Pixel[][] pixelsSetColor(Pixel[][] pixels, Color color) {
        if (pixels == null || color == null)
            return null;
        else {
            int w = pixels[0].length;
            int h = pixels.length;
            for (int i = 0; i < h; i++)
                for (int j = 0; j < w; j++) {
                    if (pixels[i][j] != null)
                        pixels[i][j].setColor(color);
                }
            return pixels;
        }
    }

    static public PGraphicItem toItem(Pixel[][] pixels) {
        return new PGraphicItem(pixels);
    }

    static public PImage toImage(Pixel[][] pixels) {
        return new PImage(null, null,pixels);
        // TODO
    }

    static public BufferedImage toBufferedImage(Pixel[][] pixels) {
        if (pixels == null) {
            return null;
        } else {
            int width = pixels[0].length;
            int height = pixels.length;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (pixels[i][j] != null) {
                        int rgb = 0;
                        Pixel pixel = pixels[i][j];
                        rgb = pixel.getColor().getRGB();
                        image.setRGB(j, i, rgb);
                    } else {
                        image.setRGB(j, i, 0x00000000);

                    }
                }
            }
            return image;
        }
    }

    static public Pixel[][] valueOf(BufferedImage bufferimage) {
        if (bufferimage == null) {
            return null;
        } else {
            int height = bufferimage.getHeight();
            int width = bufferimage.getWidth();
            Pixel[][] pixels = new Pixel[height][width];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if(bufferimage.getRGB(j, i)>>24==0)
                        pixels[i][j]=null;
                    else
                        pixels[i][j] = Pixel.getPixel(new Color(bufferimage.getRGB(j, i)), (char) 0xf0);
                }
            }
            return pixels;
        }
    }
}
