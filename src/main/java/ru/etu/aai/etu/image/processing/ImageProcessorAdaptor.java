package ru.etu.aai.etu.image.processing;

import java.awt.image.BufferedImage;

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
        }
        return new ProcessorStub();
    }
    
    public BufferedImage process(BufferedImage src, TransformType type) {
        ImageProcessor proc = getProcessor(type);
        
        int w = src.getWidth();
        int h = src.getHeight();
        
        BufferedImage dst = new BufferedImage(w, h, src.getType());

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                dst.setRGB(j, i, proc.process(i, j, src).getPixel());
            }
        }
        
        return dst;
    }
}
