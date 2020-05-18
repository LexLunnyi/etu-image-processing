package ru.etu.aai.image.processing;

import java.awt.image.BufferedImage;

/**
 *
 * @author Aleksey.Berdnikov
 */
public class ProcessorStub implements ImageProcessor {
    @Override
    public void before(BufferedImage src) {
    }
    
    @Override
    public PixelARGB process(int i, int j, BufferedImage src) {
        return new PixelARGB(src.getRGB(j, i));
    }
}
