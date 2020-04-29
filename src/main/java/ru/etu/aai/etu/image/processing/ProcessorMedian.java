package ru.etu.aai.etu.image.processing;

import java.awt.image.BufferedImage;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author Aleksey.Berdnikov
 */
public class ProcessorMedian implements ImageProcessor {
    private final int medianSize;
    
    ProcessorMedian(int size) {
        this.medianSize = size;
    }
    
    @Override
    public PixelARGB process(int i, int j, BufferedImage src) {
        //Mare sorted set
        SortedSet<Integer> pixels = getWindow(i, j, src);
        //Get median value
        Integer median = findMedian(pixels);
        //return median pixel
        return new PixelARGB(255, median, median, median);
    }
    
    
    private SortedSet<Integer> getWindow(int i, int j, BufferedImage src) {
        SortedSet<Integer> res = new TreeSet<>();
        
        int w = src.getWidth();
        int h = src.getHeight();
        
        int leftX = j - medianSize / 2;
        int rightX = leftX + medianSize;
        int topY = i - medianSize / 2;
        int bottomY = topY + medianSize;
        
        leftX = (leftX < 0) ? 0 : leftX;
        rightX = (rightX >= w) ? w-1 : rightX;
        topY = (topY < 0) ? 0 : topY;
        bottomY = (bottomY >= h) ? h-1 : bottomY;
        
        for(int jw = leftX; jw <= rightX; jw++) {
            for(int iw = topY; iw <= bottomY; iw++) {
                PixelARGB pixel = new PixelARGB(src.getRGB(jw, iw));
                //For now it works only for black-white image
                res.add(pixel.getRed());
            }
        }
        
        return res;
    }
    
    
    private Integer findMedian(SortedSet<Integer> pixels) {
        int size = pixels.size();
        
        int half = size / 2;
        boolean odd = (1 == size % 2);
        
        int index = 0;
        Integer cur = 0;
        Integer prev = 0;
        for (Integer pixel : pixels) {
            index++;
            if (index > half) {
                break;
            } else {
                prev = cur;
                cur = pixel;                
            }
        }
        
        if (odd) {
            return cur;
        } else {
            return (cur + prev) / 2;
        }
    }
}
