package ru.etu.aai.image.processing;

import ru.etu.aai.image.processing.utility.ComplexNumber;

/**
 *
 * @author Aleksey.Berdnikov
 */
public class ProccessorFFTPass implements ImageProcessor<ComplexNumber, ComplexNumber[][]> {
    final int threshold;
    int center = 0;
    double radius = 0;
    int border = 0;

    public ProccessorFFTPass(int threshold) {
        this.threshold = threshold;
    }
    
    @Override
    public void before(ComplexNumber[][] src) {
        border = src.length;
        center = src.length / 2;
        radius = (double)center * ((threshold)/100.0);
    }
    
    private double getLength(int x, int y, int tx, int ty) {
        return Math.sqrt(Math.pow((x - tx), 2) + 
                         Math.pow((y - ty), 2));        
    }
    
    protected ComplexNumber inside(ComplexNumber pixel) {
        return new ComplexNumber();
    }
    
    protected ComplexNumber outside(ComplexNumber pixel) {
        return new ComplexNumber();
    }

    @Override
    public ComplexNumber process(int i, int j, ComplexNumber[][] src) {
        //distance to the center
        double minLen = getLength(i, j, center, center);
        //distance to the up-left corner
        minLen = Math.min(minLen, getLength(i, j, 0, 0));
        //distance to the up-right corner
        minLen = Math.min(minLen, getLength(i, j, border, 0));
        //distance to the bottom-left corner
        minLen = Math.min(minLen, getLength(i, j, 0, border));
        //distance to the bottom-right corner
        minLen = Math.min(minLen, getLength(i, j, border, border));

        if (minLen > radius) {
            return outside(src[i][j]);
        } else {
            return inside(src[i][j]);
        }
    }
}