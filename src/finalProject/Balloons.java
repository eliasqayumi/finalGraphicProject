package finalProject;
public class Balloons extends Sprite {
    private boolean touch = false;
    private int[] rgb;
    private int speed = 1;

    public Balloons(double width, double height, double xPoint, double yPoint) {
        super(width, height, xPoint, yPoint);
    }

    protected void move() {
        if (yPoint >= height - 48)
            this.touch = true;
        if (yPoint <= 0)
            this.touch = false;
        if (!this.touch)
            yPoint += this.speed;
        else
            yPoint -= this.speed;
    }

    protected void setSpeed(int speed) {
        this.speed = speed;
    }

    protected void setRGB(int red, int green, int blue) {
        this.rgb = new int[]{red, green, blue};
    }

    protected int[] getRGB() {
        return this.rgb;
    }
}