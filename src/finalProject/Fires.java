package finalProject;

public class Fires extends Sprite {
    private double timeCounter = 0;

    public Fires(double width, double height, double degree) {
        super(width, height, degree);
        initFires();
    }

    private void initFires() {
        xPoint = width / 2 - 5 + 40 * Math.cos(Math.PI / 180 * degree);
        yPoint = height - 75 - 40 * Math.sin(Math.PI / 180 * degree);
    }

    protected void move() {
        xPoint += Math.cos(Math.PI / 180 * degree) * 10;
        yPoint -= 10 * Math.sin(Math.PI / 180 * degree) - 9.81 * (this.timeCounter += 0.01);
        if (xPoint >= width || xPoint <= 0 || yPoint <= 0 || yPoint >= height)
            visible = false;
    }

}
