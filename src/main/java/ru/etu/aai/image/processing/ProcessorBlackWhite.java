package ru.etu.aai.image.processing;

import java.awt.image.BufferedImage;

/**
 *
 * @author Aleksey.Berdnikov
 */
public class ProcessorBlackWhite implements ImageProcessor {
    @Override
    public PixelARGB process(int i, int j, BufferedImage src) {
        PixelARGB pixel = new PixelARGB(src.getRGB(j, i));
        pixel.makeBlackWhite();
        return pixel;
    }
    
}
