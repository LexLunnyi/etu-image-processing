package ru.etu.aai.etu.image.processing;

import java.awt.image.BufferedImage;

/**
 *
 * @author Aleksey.Berdnikov
 */
abstract class ProcessorConvolution implements ImageProcessor {
    protected int[][] kernel;
    
    @Override
    public PixelARGB process(int i, int j, BufferedImage src) {
        int w = src.getWidth();
        int h = src.getHeight();
        int kernelSize = kernel.length;
        int half = kernelSize / 2;
        
        if ((h-half <= i) || (half-1 >= i) || (w-half <= j) || (half-1 >= j)) {
            return new PixelARGB(src.getRGB(j, i));
        }
        
        int leftX = j - half;
        int rightX = j + half;
        int topY = i - half;
        int bottomY = i + half;
        
        int newVal = 0;
        for(int jw = leftX; jw <= rightX; jw++) {
            for(int iw = topY; iw <= bottomY; iw++) {
                PixelARGB pixel = new PixelARGB(src.getRGB(jw, iw));
                int koeff = kernel[jw-leftX][iw-topY];
                //It can be any color ad we work with black-white
                newVal += koeff * pixel.getRed();
            }
        }
        newVal = newVal / (kernelSize*kernelSize);
        
        return new PixelARGB(255, newVal, newVal, newVal);
    }
}