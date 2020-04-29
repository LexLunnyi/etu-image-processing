package ru.etu.aai.image.processing;

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
        //Load application options
        Options options = new Options(args[0]);
        //Read source image
        BufferedImage src = ImageIO.read(new File(options.getImage()));
        System.out.println(src.toString());
        //Perform image processing
        ImageProcessorAdaptor adaptor = new ImageProcessorAdaptor(options);
        BufferedImage bw = adaptor.process(src, TransformType.BLACK_WHITE);
        BufferedImage median = adaptor.process(bw, TransformType.MEDIAN);
        BufferedImage laplacian = adaptor.process(median, TransformType.LAPLACIAN);
        //Save results
        ImageIO.write(bw, "png", new File(options.getOutput() + "1_bw.png"));
        ImageIO.write(median, "png", new File(options.getOutput() + "1_median.png"));
        ImageIO.write(laplacian, "png", new File(options.getOutput() + "1_laplacian.png"));
    }
}
