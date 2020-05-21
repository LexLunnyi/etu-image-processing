package ru.etu.aai.image.processing;

import ru.etu.aai.image.processing.utility.PixelARGB;
import java.awt.image.BufferedImage;

/**
 *
 * @author Aleksey.Berdnikov
 */
public class ProcessorStub implements ImageProcessor<PixelARGB, BufferedImage> {
    @Override
    public void before(BufferedImage src) {
    }
    
    @Override
    public PixelARGB process(int i, int j, BufferedImage src) {
        return new PixelARGB(src.getRGB(j, i));
    }
}
