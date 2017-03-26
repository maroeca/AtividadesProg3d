import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by maiki on 24/03/2017.
 */
public class Atividade02 {


    public static BufferedImage pixelate(BufferedImage img, int pixelSize)
    {
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(),img.getType());

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Color pixelColor = new Color(img.getRGB(x,y));

                Graphics pixelRect = img.getGraphics();
                pixelRect.setColor(pixelColor);
                pixelRect.fillRect(x,y,pixelSize, pixelSize);

                int r = pixelColor.getRed();
                int g = pixelColor.getGreen();
                int b = pixelColor.getBlue();

                Color outColor = new Color(r,g,b);
                out.setRGB(x,y,outColor.getRGB());
            }
        }

        return out;
    }

    public static void run()
    {


    }

    public static void main(String[] args)
    {
        new Atividade02().run();
    }
}
