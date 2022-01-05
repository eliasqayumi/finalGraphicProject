package arkadaslarFinal;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Alien {
    private double xPoint = 0;
    private double yPoint = 0;
    private double width;
    private double height;
    private boolean visible;
    private boolean touch = false;
    private int red;
    private int green;
    private int blue;
    private int speed = 1;

    public Alien(double height, double width, double xPoint, double yPoint) {
        this.width = width;
        this.height = height;
        this.xPoint = xPoint;
        this.yPoint = yPoint;
        visible = true;
    }
// dusman nesnelerini hareket etmek icin kullaniyorum
    public void move() {
//        eger nesnenin y konumu pence disina tasdiyse touche=true yapar ve boylece top yukari yone hareket eder eger yukariya dedigin an asagi dogru hareket eder
        if (yPoint >= height - 48)
            touch = true;
        if (yPoint <= 0)
            touch = false;
        if (!touch)
            yPoint += speed;
        else
            yPoint -= speed;
    }
//  dusman nesnesini rastgele hiz almak icin kullandim
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    //    dusman nesnesinin rastgele rengini belirlemek icin kullandim
    public void setRGB(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
//  nesneyi ekrana ciktisini vermek icin kullanmam gereken renkleri geri almak icin kullandim
    public int[] getColor() {
        int[] rgb = new int[]{red, green, blue};
        return rgb;
    }

    public boolean isVisible() {
        return visible;
    }
//   nesnenin x konumunu almak icin kullandim
    public double getXPoint() {
        return xPoint;
    }
//   nesnenin y konumunu almak icin kullandim
    public double getYPoint() {
        return yPoint;
    }

    protected void setVisible(Boolean visible) {
        this.visible = visible;
    }
//nesnenin cakisip cakismadigini kontrol etmek icin nesnelerin sinirlari gerekiyor
    public Rectangle2D.Double getBound() {
        return new Rectangle2D.Double(xPoint, yPoint, 20, 20);
    }
}