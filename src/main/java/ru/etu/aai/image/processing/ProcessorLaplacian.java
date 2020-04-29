package ru.etu.aai.image.processing;

/**
 *
 * @author Aleksey.Berdnikov
 */
public class ProcessorLaplacian extends ProcessorConvolution {
    ProcessorLaplacian(int size) {
        switch (size) {
            case 3:
                kernel = new int[][]{
                    { 0, -1,  0},
                    {-1,  4, -1},
                    { 0, -1,  0}
                };  break;
            case 5:
                kernel = new int[][]{
                    {0,   0, -1,  0,  0},
                    {0,  -1, -2, -1,  0},
                    {-1, -2, 17, -2, -1},
                    {0,  -1, -2, -1,  0},
                    {0,   0, -1,  0,  0}
                };  break;
            default:
                throw new IllegalArgumentException("Size of the Laplacian's kernel must be 3 or 5");
        }
    }
}
