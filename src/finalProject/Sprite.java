package finalProject;

import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Sprite {

    protected double xPoint;
    protected double yPoint;
    protected double width;
    protected double height;
    protected double degree;
    protected boolean visible;

    public Sprite(double width, double height, double degree) {
        this.xPoint = 0;
        this.yPoint = 0;
        this.width = width;
        this.height = height;
        this.degree = degree;
        this.visible = true;
    }

    public Sprite(double width, double height, double xPoint, double yPoint) {
        this.xPoint = xPoint;
        this.yPoint = yPoint;
        this.width = width;
        this.height = height;
        this.degree = 0;
        this.visible = true;
    }

    public double getXPoint() {
        return xPoint;
    }

    public double getYPoint() {
        return yPoint;
    }

    public double getDegree() {
        return degree;
    }

    public boolean isVisible() {
        return visible;
    }


    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Area getBound() {
        return new Area(new Ellipse2D.Double(xPoint, yPoint, 20, 20));
    }

}