package ru.etu.aai.image.processing;

import ru.etu.aai.image.processing.utility.PixelARGB;
import java.awt.image.BufferedImage;

/**
 *
 * @author Aleksey.Berdnikov
 */
public class ProcessorThreshold implements ImageProcessor<PixelARGB, BufferedImage> {
    final int threshold;

    public ProcessorThreshold(int threshold) {
        this.threshold = threshold;
    }
    
    @Override
    public void before(BufferedImage src) {
    }
    
    @Override
    public PixelARGB process(int i, int j, BufferedImage src) {
        PixelARGB pixel = new PixelARGB(src.getRGB(j, i));
        if (pixel.getRed() > threshold) {
            pixel = new PixelARGB(255, 255, 0, 0);
        } else {
            pixel = new PixelARGB(255, 0, 0, 0);
        }
        return pixel;
    }
}
