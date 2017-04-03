import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by maiki on 02/04/2017.
 */
public class Atividade03 {

    public static int saturate(int value)
    {
        if(value > 255)
            return 255;
        if(value < 0)
            return 0;
        return value;
    }

    public static BufferedImage equalize(BufferedImage img){
        int[] histograma = new int[256];
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        for(int i = 0; i < 255; i++){
            histograma[i] = 0;
        }

        //Monta o histograma
        for(int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {

                int cor = img.getRGB(x, y);

                Color pixel = new Color(cor);

                int r = pixel.getRed();
                histograma[r]++;
            }
        }
        //Monta o Histograma acomulado
        int[] hacumulado = new int[256];
        int hmin = 0;
        hacumulado[0] = histograma[0];

        for(int i = 1; i < 255; i++){
            hacumulado[i] = histograma[i] + hacumulado[i -1]; //elemento i + seu anterior
        }

        //Descobre minimo valor
        for(int i = 0; i < 255; i++){
            if(histograma[i] != 0)
            {
                hmin = histograma[i];
                break;
            }
        }
        //equaliza o histograma acumulado
        int[] hv= new int[256];
        for(int i = 0; i < 255; i++){

            hv[i] = saturate((Math.round(((hacumulado[i] - hmin) / ((float)img.getHeight() * img.getWidth()))* (256 - 1))));
        }

        //gera a imagem usando o histograma equalizado
        for(int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {

                int cor = img.getRGB(x, y);

                Color newPixel = new Color(cor);

                newPixel = new Color(hv[newPixel.getRed()],hv[newPixel.getRed()],hv[newPixel.getRed()]);

                out.setRGB(x,y,newPixel.getRGB());
            }
        }
        return out;
    }

    public static void run() throws IOException {
        File PATH = new File("C:\\Users\\maiki\\Pictures\\img\\gray");
        BufferedImage img = ImageIO.read(new File(PATH, "cars.jpg"));

        BufferedImage imgEqualized = equalize(img);
        ImageIO.write(imgEqualized, "png", new File("carsEqualized.png"));

    }

    public static void main (String[] args) throws IOException {
        new Atividade03().run();
    }
}
