import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by maiki on 24/03/2017.
 */
public class Atividade02 {


    static float contraste[][] = { //kernel de contaste
            { 0, -1,  0},
            {-1,  5, -1},
            { 0, -1,  0}
    };

    public static int saturate(int value){ //pra não estourar a cor
        if (value > 255) {
            return 255;
        }
        if(value < 0){
            return 0;
        }
        return value;
    }

    static Color applyKernel(Color[][] colors, float[][] kernel) //aplica o kernel
    {
        Color newColors[][] = new Color[3][3];
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) { //lê os pixels e multiplica pelo kernel
                int r = saturate((int) (colors[x][y].getRed() * kernel[x][y]));
                int g = saturate((int) (colors[x][y].getGreen() * kernel[x][y]));
                int b = saturate((int) (colors[x][y].getBlue() * kernel[x][y]));
                newColors[x][y] = new Color(r, g, b);
            }

        }
        int sr = 0;
        int sg = 0;
        int sb = 0;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) { //soma tudo nas variasveis pra retornar
                sr +=  newColors[x][y].getRed();
                sg +=  newColors[x][y].getGreen();
                sb +=  newColors[x][y].getBlue();
            }
        }
        return new Color(sr, sg, sb);


    }

    static Color getColor (BufferedImage img, int x, int y)
    {
       //não deixa passar os limites das bordas
        if(x < 0 || x >= img.getWidth() || y < 0 || y >= img.getHeight())
            return new Color(0,0,0);

        return new Color (img.getRGB(x, y));

    }

    static Color[][] getPixelColor(BufferedImage img, int x, int y) //lê os pixels vizinhos
    {
        return new Color[][]{
                {getColor(img,x-1, y-1), getColor(img, x, y-1), getColor(img, x+1, y-1)},
                {getColor(img,x-1, y+0), getColor(img, x, y+0), getColor(img, x+1, y+0)},
                {getColor(img,x-1, y+1), getColor(img, x, y+1), getColor(img, x+1, y+1)}

        };

    }


    static BufferedImage convolve(BufferedImage img, float[][] kernel, int pixelSize) //aplica o filtro na imagem
    {
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < img.getHeight(); y+=pixelSize) {
            for (int x = 0; x < img.getWidth() ; x+=pixelSize) {
                Color colors = new Color(applyKernel(getPixelColor(img, x, y), kernel).getRGB());
                setProxCorContraste(img,out,x,y,pixelSize,colors);
                out.setRGB(x, y, colors.getRGB());
            }

        }
        return out;
    }


    public static BufferedImage pixelate(BufferedImage img, int pixelSize)
    {

        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(),img.getType());

        for (int y = 0; y < img.getHeight();  y += pixelSize) {
            for (int x = 0; x < img.getWidth(); x += pixelSize) {

                setProxCor(img, out, x, y, pixelSize);
            }
        }

        return out;
    }

    public static void setProxCorContraste(BufferedImage img, BufferedImage out, int x, int y, int pixelSize, Color cor)
    { //aplica o contraste pro tamanho certo dos pixels
        for (int h = y; h < y + pixelSize ; h++) {
            for (int w = x; w < x + pixelSize ; w++) {
                if(h >= img.getHeight() || w >= img.getWidth())
                {
                    continue;
                }
                out.setRGB(w,h, cor.getRGB());
            }
        }
    }

    public static void setProxCor(BufferedImage img,BufferedImage out, int x, int y, int pixelSize)
    {//seta a cor por tamanho dos pixels da função pixelate
        Color newColor = new Color(img.getRGB(x,y));

        for (int h = y; h < y + pixelSize; h++) {
            for (int w = x; w < x + pixelSize; w++) {

                if(h >= img.getHeight() || w >= img.getWidth())
                {
                    continue;
                }
                out.setRGB(w, h, newColor.getRGB());
            }
        }
    }

    public static void run() throws IOException {
        String PATH = "C:\\Users\\maiki\\Pictures\\img\\cor";
        BufferedImage img = ImageIO.read(new File(PATH,"metroid1.jpg"));
        BufferedImage pixelateImg = pixelate(img, 15); //faz o pixel
        BufferedImage pixelTraste = convolve(pixelateImg, contraste, 15); // aplica o contraste


        ImageIO.write(pixelateImg, "png", new File("pixelMetroid.png"));
        ImageIO.write(pixelTraste, "png", new File("pixelConMetroid.png"));

    }

    public static void main(String[] args) throws IOException {
        new Atividade02().run();
    }
}
