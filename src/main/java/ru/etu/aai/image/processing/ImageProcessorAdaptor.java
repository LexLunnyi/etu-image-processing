package ru.etu.aai.image.processing;

import ru.etu.aai.image.processing.utility.Options;
import java.awt.image.BufferedImage;
import ru.etu.aai.image.processing.utility.ComplexNumber;
import ru.etu.aai.image.processing.utility.PixelARGB;

/**
 *
 * @author Aleksey.Berdnikov
 */
public class ImageProcessorAdaptor {
    private final Options options;
    
    ImageProcessorAdaptor(Options options) {
        this.options = options;
    }
    
    private ImageProcessor getProcessor(TransformType type) {   
        switch(type) {
            case BLACK_WHITE:
                return new ProcessorBlackWhite();
            case MEDIAN:
                return new ProcessorMedian(options.getMedianSize());
            case LAPLACIAN:
                return new ProcessorLaplacian(options.getLaplacianSize());
            case THRESHOLD:
                return new ProcessorThreshold(options.getThreshold());
            case EQUALIZATION:
                return new ProcessorEqualization();
            case FFT_LOW:
                return new ProccessorFFTLowPass(options.getFftLowThreshold());
            case FFT_HIGH:
                return new ProccessorFFTHighPass(options.getFftHighThreshold());
        }
        return new ProcessorStub();
    }
    
    public BufferedImage process(BufferedImage src, TransformType type) {
        ImageProcessor proc = getProcessor(type);
        proc.before(src);
        
        int w = src.getWidth();
        int h = src.getHeight();
        BufferedImage dst = new BufferedImage(w, h, src.getType());
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                PixelARGB p = (PixelARGB)proc.process(i, j, src);
                dst.setRGB(j, i, p.getPixel());
            }
        }
        return dst;
    }
    
    public ComplexNumber[][] process(ComplexNumber[][] src, TransformType type) {
        ImageProcessor proc = getProcessor(type);
        proc.before(src);
        
        int w = src.length;
        int h = src[0].length;
        
        ComplexNumber[][] dst = new ComplexNumber[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                ComplexNumber p = (ComplexNumber)proc.process(i, j, src);
                dst[i][j] = p;
            }
        }
        return dst;
    }
}