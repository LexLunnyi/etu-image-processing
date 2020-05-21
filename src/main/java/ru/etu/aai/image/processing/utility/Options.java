package ru.etu.aai.image.processing.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Aleksey.Berdnikov
 */
public class Options {
    private String image;
    private String output;
    private int medianSize;
    private int laplacianSize;
    private int threshold;
    
    public Options(String path) {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(path);
            // load a properties file
            prop.load(input);
            // get the property value and print it out
            image = prop.getProperty("image");
            output = prop.getProperty("output");
            medianSize = Integer.parseInt(prop.getProperty("median.size"));
            laplacianSize = Integer.parseInt(prop.getProperty("laplacian.size"));
            threshold = Integer.parseInt(prop.getProperty("threshold.value"));
            
            printProperties();
        } catch (IOException ex) {
            System.out.println("Error read options: " + ex);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    System.out.println("Error close config-file: " + e);
                }
            }
        }
    }

    private void printProperties() {
        System.out.println("-------------------------------------------------------");
        System.out.println("OPTIONS: ");
        System.out.println("  image -> " + image);
        System.out.println("  output -> " + output);
        System.out.println("  medianSize -> " + medianSize);
        System.out.println("  laplacianSize -> " + laplacianSize);
        System.out.println("  threshold -> " + threshold);
        System.out.println("-------------------------------------------------------");
    }

    public String getImage() {
        return image;
    }

    public String getOutput() {
        return output;
    }

    public int getMedianSize() {
        return medianSize;
    }

    public int getLaplacianSize() {
        return laplacianSize;
    }

    public int getThreshold() {
        return threshold;
    }
}
