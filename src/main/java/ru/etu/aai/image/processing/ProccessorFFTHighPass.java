package ru.etu.aai.image.processing;

import ru.etu.aai.image.processing.utility.ComplexNumber;

/**
 *
 * @author Aleksey.Berdnikov
 */
public class ProccessorFFTHighPass extends ProccessorFFTPass {
    public ProccessorFFTHighPass(int threshold) {
        super(threshold);
    }

    @Override
    protected ComplexNumber outside(ComplexNumber pixel) {
        return pixel;
    }
}
