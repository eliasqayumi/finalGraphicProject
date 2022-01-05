package arkadaslarFinal;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Fires {

    private double xBall = 0;
    private double yBall = 0;
    private double width;
    private double height;
    private double degree;
    private boolean visible;
    private double timeCounter = 0;

    public Fires(double width, double height, double degree) {
        this.width = width;
        this.height = height;
        this.degree = degree;
        visible = true;
        initFires();
    }

    public void initFires() {
//        topun ilk konumlari
        xBall = width / 2 - 5 + 40 * Math.cos(Math.PI / 180 * degree);
        yBall = height - 75 - 40 * Math.sin(Math.PI / 180 * degree);
    }

    public void move() {
//        topun hareket yonu ve her seferinde bu degerleri toplayark gidiyor
        xBall += Math.cos(Math.PI / 180 * degree) * 10;
        yBall -= 10 * Math.sin(Math.PI / 180 * degree) - 9.81 * (timeCounter += 0.01);
        if (xBall >= width || xBall <= 0 || yBall <= 0 || yBall >= height) {
//            eger top herhangi pencere kenarindan cikarsa bu nesnenin gorunumunu false et
            visible = false;
        }
    }

    public double getXBall() {
        return xBall;
    }

    public double getYBall() {
        return yBall;
    }

    public Boolean isVisible() {
        return visible;
    }
//  nensenin  sinirlarini almak icin kullandim
    public Rectangle2D.Double getBound() {
        return new Rectangle2D.Double(xBall, yBall, 10, 10);
    }

    protected void setVisible(boolean visible) {
        this.visible = visible;
    }

}
