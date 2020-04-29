package ru.etu.aai.etu.image.processing;

import java.awt.image.BufferedImage;

/**
 *
 * @author Aleksey.Berdnikov
 */
public class ProcessorStub implements ImageProcessor {
    @Override
    public PixelARGB process(int i, int j, BufferedImage src) {
        return new PixelARGB(src.getRGB(j, i));
    }
}
