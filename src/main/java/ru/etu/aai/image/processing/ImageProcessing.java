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
        BufferedImage fftSpectrumBefore = FFT.toImage(fft, true);
        
        ComplexNumber[][] fftHighFiltred = adaptor.process(fft, TransformType.FFT_HIGH);
        BufferedImage fftHighSpectrumAfter = FFT.toImage(fftHighFiltred, true);
        BufferedImage afterFFTHigh = FFT.inverse(fftHighFiltred);
        
        ComplexNumber[][] fftLowFiltred = adaptor.process(fft, TransformType.FFT_LOW);
        BufferedImage fftLowSpectrumAfter = FFT.toImage(fftLowFiltred, true);
        BufferedImage afterFFTLow = FFT.inverse(fftLowFiltred);
        
        //Save results
        ImageIO.write(bw, "png", new File(options.getOutput() + in.getName() + "_bw.png"));
        ImageIO.write(hist, "png", new File(options.getOutput() + in.getName() + "_hist.png"));
        ImageIO.write(thres, "png", new File(options.getOutput() + in.getName() + "_thres.png"));
        ImageIO.write(eq, "png", new File(options.getOutput() + in.getName() + "_eq.png"));
        ImageIO.write(hist_eq, "png", new File(options.getOutput() + in.getName() + "_hist_eq.png"));
        ImageIO.write(fftSpectrumBefore, "png", new File(options.getOutput() + in.getName() + "_fft.png"));
        ImageIO.write(fftHighSpectrumAfter, "png", new File(options.getOutput() + in.getName() + "_fft_high_after.png"));
        ImageIO.write(afterFFTHigh, "png", new File(options.getOutput() + in.getName() + "_fft_high_filtred.png"));
        ImageIO.write(fftLowSpectrumAfter, "png", new File(options.getOutput() + in.getName() + "_fft_low_after.png"));
        ImageIO.write(afterFFTLow, "png", new File(options.getOutput() + in.getName() + "_fft_low_filtred.png"));
        //ImageIO.write(median, "png", new File(options.getOutput() + in.getName() + "_median.png"));
        //ImageIO.write(laplacian, "png", new File(options.getOutput() + in.getName() + "_laplacian.png"));
    }
}