package ru.etu.aai.image.processing;

import java.awt.image.BufferedImage;

/**
 *
 * @author Aleksey.Berdnikov
 */
public class Histogram {
    static final int MAX_BRIGHTNESS = 256;
    final int[] hist = new int[MAX_BRIGHTNESS];
    int max = 0;
    
    public BufferedImage process(BufferedImage src) {
        int w = src.getWidth();
        int h = src.getHeight();
        //clear current histogram
        for(int i = 0; i < MAX_BRIGHTNESS; i++) {
            hist[i] = 0;
        }
        //define new histogram
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                PixelARGB pixel = new PixelARGB(src.getRGB(j, i));
                if (pixel.getRed() >= MAX_BRIGHTNESS) {
                    System.err.println("Warning: Brightness -> " + pixel.getRed());
                } else {
                    hist[pixel.getRed()] += 1;
                    max = (hist[pixel.getRed()] > max) ? hist[pixel.getRed()] : max;
                }
            }
        }
        //save histogram
        BufferedImage dst = new BufferedImage(MAX_BRIGHTNESS, MAX_BRIGHTNESS, src.getType());
        for(int i = 0; i < MAX_BRIGHTNESS; i++) {
            int cur = hist[i];
            for(int j = MAX_BRIGHTNESS-1; j >= 0; j--) {
                PixelARGB pixel = new PixelARGB(255, 0, 0, 0);
                if ((double)cur /(double)max < (double)j/(double)MAX_BRIGHTNESS) {
                    pixel = new PixelARGB(255, 255, 255, 255);
                }
                dst.setRGB(i, 255-j, pixel.getPixel());
            }
        }
        return dst;
    }
}
