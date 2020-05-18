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
        File in = new File(options.getImage());
        BufferedImage src = ImageIO.read(in);
        System.out.println(src.toString());
        //Perform image processing
        Histogram histmaker = new Histogram();
        ImageProcessorAdaptor adaptor = new ImageProcessorAdaptor(options);
        BufferedImage bw = adaptor.process(src, TransformType.BLACK_WHITE);
        BufferedImage hist = histmaker.process(bw);
        BufferedImage thres = adaptor.process(bw, TransformType.THRESHOLD);
        BufferedImage eq = adaptor.process(bw, TransformType.EQUALIZATION);
        BufferedImage hist_eq = histmaker.process(eq);
        BufferedImage median = adaptor.process(bw, TransformType.MEDIAN);
        BufferedImage laplacian = adaptor.process(median, TransformType.LAPLACIAN);
        //Save results
        ImageIO.write(bw, "png", new File(options.getOutput() + in.getName() + "_bw.png"));
        ImageIO.write(hist, "png", new File(options.getOutput() + in.getName() + "_hist.png"));
        ImageIO.write(thres, "png", new File(options.getOutput() + in.getName() + "_thres.png"));
        ImageIO.write(eq, "png", new File(options.getOutput() + in.getName() + "_eq.png"));
        ImageIO.write(hist_eq, "png", new File(options.getOutput() + in.getName() + "_hist_eq.png"));
        ImageIO.write(median, "png", new File(options.getOutput() + in.getName() + "_median.png"));
        ImageIO.write(laplacian, "png", new File(options.getOutput() + in.getName() + "_laplacian.png"));
    }
}
