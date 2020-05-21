package ru.etu.aai.image.processing;

import ru.etu.aai.image.processing.utility.ComplexNumber;

/**
 *
 * @author Aleksey.Berdnikov
 */
public class ProccessorFFTLowPass extends ProccessorFFTPass {
    public ProccessorFFTLowPass(int threshold) {
        super(threshold);
    }

    @Override
    protected ComplexNumber inside(ComplexNumber pixel) {
        return pixel;
    }
}