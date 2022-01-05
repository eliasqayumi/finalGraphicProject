package arkadaslarFinal;

public class Sprite {

    protected double xPoint;
    protected double yPoint;
    protected double width;
    protected double height;
    protected double degree;
    protected boolean visible;

    public Sprite(double width, double height, double degree) {
        this.width = width;
        this.height = height;
        this.degree = degree;
        visible = true;
    }
    public Sprite(double xPoint,double yPoint){
        this.xPoint=xPoint;
        this.xPoint=yPoint;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    public double getDegree() {
        return this.degree;
    }

    public boolean isVisible() {
        return visible;
    }


    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}