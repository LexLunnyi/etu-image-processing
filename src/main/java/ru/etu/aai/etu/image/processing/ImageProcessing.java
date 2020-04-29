package ru.etu.aai.etu.image.processing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Aleksey.Berdnikov
 */
public class ImageProcessing {
        
    public static void main(String[] args) throws IOException {
        Options options = new Options(args[0]);
        
        BufferedImage src = ImageIO.read(new File(options.getImage()));
        System.err.println(src.toString());
        
        ImageProcessorAdaptor adaptor = new ImageProcessorAdaptor(options);
        
        BufferedImage bw = adaptor.process(src, TransformType.BLACK_WHITE);
        BufferedImage median = adaptor.process(bw, TransformType.MEDIAN);
        BufferedImage laplacian = adaptor.process(median, TransformType.LAPLACIAN);
        
        ImageIO.write(bw, "png", new File(options.getOutput() + "1_bw.png"));
        ImageIO.write(median, "png", new File(options.getOutput() + "1_median.png"));
        ImageIO.write(laplacian, "png", new File(options.getOutput() + "1_laplacian.png"));
    }
    
}
