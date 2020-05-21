package ru.etu.aai.image.processing;

import ru.etu.aai.image.processing.utility.Options;
import ru.etu.aai.image.processing.utility.Histogram;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import ru.etu.aai.image.processing.utility.ComplexNumber;
import ru.etu.aai.image.processing.utility.FFT;

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
        //BufferedImage median = adaptor.process(bw, TransformType.MEDIAN);
        //BufferedImage laplacian = adaptor.process(median, TransformType.LAPLACIAN);
        ComplexNumber[][] fft = FFT.forward(bw);
        ComplexNumber[][] fftFiltred = adaptor.process(fft, TransformType.FFT_LOW);
        BufferedImage fftSpectrumBefore = FFT.toImage(fft, true);
        BufferedImage fftSpectrumAfter = FFT.toImage(fftFiltred, true);
        BufferedImage afterFFT = FFT.inverse(fftFiltred);
        //Save results
        ImageIO.write(bw, "png", new File(options.getOutput() + in.getName() + "_bw.png"));
        ImageIO.write(hist, "png", new File(options.getOutput() + in.getName() + "_hist.png"));
        ImageIO.write(thres, "png", new File(options.getOutput() + in.getName() + "_thres.png"));
        ImageIO.write(eq, "png", new File(options.getOutput() + in.getName() + "_eq.png"));
        ImageIO.write(hist_eq, "png", new File(options.getOutput() + in.getName() + "_hist_eq.png"));
        ImageIO.write(fftSpectrumBefore, "png", new File(options.getOutput() + in.getName() + "_fft.png"));
        ImageIO.write(fftSpectrumAfter, "png", new File(options.getOutput() + in.getName() + "_fft_after.png"));
        ImageIO.write(afterFFT, "png", new File(options.getOutput() + in.getName() + "_fft_filtred.png"));
        //ImageIO.write(median, "png", new File(options.getOutput() + in.getName() + "_median.png"));
        //ImageIO.write(laplacian, "png", new File(options.getOutput() + in.getName() + "_laplacian.png"));
    }
}