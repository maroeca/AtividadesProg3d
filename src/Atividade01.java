import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by maiki on 24/03/2017.
 * Converter rgb em ega
 */



public class Atividade01 {

    public static BufferedImage EGA(BufferedImage img)
    {
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < img.getHeight(); y++)
        {
            for (int x = 0; x < img.getWidth(); x++)
            {
                Color cor = new Color(img.getRGB(x,y));

                int r = Convert(cor.getRed());
                int g = Convert(cor.getGreen());
                int b = Convert(cor.getBlue());

                Color outColor = new Color(r,g,b);
                out.setRGB(x,y,outColor.getRGB());
            }
        }
        return out;
    }
    public static int Convert(int value)
    {
        if(value <= 64)
            return 63;
        else if(value > 63 && value <= 127)
            return 127;
        else if(value > 127 && value <= 191)
            return 191;

        return 255;
    }

    public static void run() throws IOException {
        String PATH = "C:\\Users\\maiki\\Pictures\\img\\cor";
        BufferedImage img = ImageIO.read(new File(PATH,"puppy.png"));
        BufferedImage EGAimg = EGA(img);

        ImageIO.write(EGAimg,"png", new File("puppyEGA.png"));
        System.out.println("Pronto!");
    }

    public static void main(String[] args) throws IOException {
        new Atividade01().run();
    }
}
