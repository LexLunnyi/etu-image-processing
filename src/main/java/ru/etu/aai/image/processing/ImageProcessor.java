package ru.etu.aai.image.processing;

import java.awt.image.BufferedImage;

/**
 *
 * @author Aleksey.Berdnikov
 */
public interface ImageProcessor {
    PixelARGB process(int i, int j, BufferedImage src);
}
