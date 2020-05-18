package ru.etu.aai.image.processing;

import java.awt.image.BufferedImage;

/**
 *
 * @author Aleksey.Berdnikov
 */
public class ProcessorEqualization implements ImageProcessor {
    int lut[];

    @Override
    public void before(BufferedImage src) {
        int w = src.getWidth();
        int h = src.getHeight();
        int sqr = w * h;
        int[] histogram = new int[256];

        //read pixel intensities into histogram
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                PixelARGB pixel = new PixelARGB(src.getRGB(x, y));
                int valueBefore = pixel.getRed();
                histogram[valueBefore]++;
            }
        }

        int sum = 0;
        //build a Lookup table LUT containing scale factor
        lut = new int[sqr];
        for (int i = 0; i < 256; ++i) {
            sum += histogram[i];
            lut[i] = sum * 255 / sqr;
        }
    }

    @Override
    public PixelARGB process(int i, int j, BufferedImage src) {
        PixelARGB pixel = new PixelARGB(src.getRGB(j, i));
        int valueBefore = pixel.getRed();
        int valueAfter = lut[valueBefore];
        return new PixelARGB(255, valueAfter, valueAfter, valueAfter);
    }
}