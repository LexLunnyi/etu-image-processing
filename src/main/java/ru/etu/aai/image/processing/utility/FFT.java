package ru.etu.aai.image.processing.utility;

import java.awt.Color;
import java.awt.image.BufferedImage;

public final class FFT {

    public static ComplexNumber[][] forward(BufferedImage img) {
        return compute(build2DArray(img), false);
    }

    public static BufferedImage inverse(ComplexNumber[][] spectrum) {
        return toImage(compute(spectrum, true), false);
    }

    public static BufferedImage toImage(ComplexNumber[][] spectrum, boolean log) {
        int w = spectrum.length;
        int h = spectrum[0].length;
        BufferedImage spectrumImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        double maxValue = 0.;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                ComplexNumber c = spectrum[i][j];
                if (c == null) {
                    continue;
                }
                maxValue = Math.max(maxValue, c.getMagnitude());
            }
        }
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                ComplexNumber c = spectrum[i][j];
                if (c == null) {
                    c = new ComplexNumber();
                }
                double value = c.getMagnitude();
                double intensity = (log) ? Math.log(value) / Math.log(maxValue) : value / maxValue;
                int rgb = Color.HSBtoRGB(0f, 0f, (float) intensity);
                spectrumImg.setRGB(i, j, rgb);
            }
        }
        return spectrumImg;
    }

    private static float getBrightness(int rgb) {
        int red = (rgb >> 16) & 0xff;
        int green = (rgb >> 8) & 0xff;
        int blue = (rgb) & 0xff;
        float brightness = (float) ((.2126 * red + .7152 * green + .0722 * blue) / 255);
        return brightness;
    }

    private static ComplexNumber getComplexNumber(int rgb) {
        return new ComplexNumber(getBrightness(rgb), 0.);
    }

    private static ComplexNumber[][] build2DArray(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        ComplexNumber[][] result = new ComplexNumber[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                result[i][j] = getComplexNumber(image.getRGB(i, j));
            }
        }
        return result;
    }

    private static void setRow(ComplexNumber[][] matrix, int row, ComplexNumber[] data) {
        for (int i = 0; i < matrix[0].length; i++) {
            matrix[i][row] = data[i];
        }
    }

    private static ComplexNumber[] getRow(ComplexNumber[][] matrix, int row) {
        ComplexNumber[] result = new ComplexNumber[matrix[0].length];
        for (int i = 0; i < result.length; i++) {
            result[i] = matrix[i][row];
        }
        return result;
    }

    private static ComplexNumber[][] compute(ComplexNumber[][] input, boolean inverse) {
        long start = System.currentTimeMillis();

        int w = input.length;
        int h = input[0].length;

        ComplexNumber[][] intermediate = new ComplexNumber[w][h];
        ComplexNumber[][] output = new ComplexNumber[w][h];

        for (int i = 0; i < input.length; i++) {
            intermediate[i] = recursiveFFT(input[i], inverse);
        }
        for (int i = 0; i < intermediate[0].length; i++) {
            setRow(output, i, recursiveFFT(getRow(intermediate, i), inverse));
        }

        System.out.println((System.currentTimeMillis() - start) + " ms");
        return output;
    }

    public static ComplexNumber[][] convolve(ComplexNumber[][] a, ComplexNumber[][] b) {
        ComplexNumber[][] result = new ComplexNumber[a.length][a[0].length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                result[i][j] = a[i][j].mul(b[i][j]);
            }
        }
        return result;
    }

    private static ComplexNumber[] recursiveFFT(ComplexNumber[] x, boolean inverse) {
        int n = x.length;
        ComplexNumber[] result = new ComplexNumber[n];
        ComplexNumber[] even;
        ComplexNumber[] odd;

        if (n == 1) {
            result[0] = x[0];
        } else {
            even = recursiveFFT(evenValues(x), inverse);
            odd = recursiveFFT(oddValues(x), inverse);
            for (int k = 0; k < n / 2; k++) {
                ComplexNumber factor
                        = inverse ? ComplexNumber.fromPolar(1., 2. * Math.PI * k / n) : ComplexNumber.fromPolar(1., -2.
                                * Math.PI * k / n);
                result[k] = even[k].add(odd[k].mul(factor));
                result[k + n / 2] = even[k].sub(odd[k].mul(factor));
            }
        }
        return result;
    }

    private static ComplexNumber[] evenValues(ComplexNumber[] x) {
        ComplexNumber[] result = new ComplexNumber[x.length / 2];
        for (int i = 0; i < result.length; i++) {
            result[i] = x[2 * i];
        }
        return result;
    }

    private static ComplexNumber[] oddValues(ComplexNumber[] x) {
        ComplexNumber[] result = new ComplexNumber[x.length / 2];
        for (int i = 0; i < result.length; i++) {
            result[i] = x[2 * i + 1];
        }
        return result;
    }
}