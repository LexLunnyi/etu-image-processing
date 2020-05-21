package ru.etu.aai.image.processing;

/**
 * @author Aleksey.Berdnikov
 */
public interface ImageProcessor<P, I> {
    void before(I src);
    P process(int i, int j, I src);
}
