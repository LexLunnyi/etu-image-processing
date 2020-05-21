package ru.etu.aai.image.processing;

import ru.etu.aai.image.processing.utility.ComplexNumber;

/**
 *
 * @author Aleksey.Berdnikov
 */
public class ProccessorFFTLowPass implements ImageProcessor<ComplexNumber, ComplexNumber[][]> {
    @Override
    public void before(ComplexNumber[][] src) {
    }

    @Override
    public ComplexNumber process(int i, int j, ComplexNumber[][] src) {
        return src[i][j];
    }
}
