package finalProject;

import java.awt.event.KeyEvent;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;

public class FireGun extends Sprite {
    private int score;
    private Boolean gameStart;
    private List<Fires> fireList;

    public FireGun(double width, double height, double degree) {
        super(width, height, degree);
        this.fireList = new ArrayList<>();
        this.gameStart = true;
        this.score = 150;
    }

    protected GeneralPath fireGunDesign() {
        Area mainPartRect = new Area(new Rectangle2D.Double(0, 0, 70, 20));
        mainPartRect.add(new Area(new Ellipse2D.Double(50, -5, 20, 30)));
        mainPartRect.add(new Area(new Ellipse2D.Double(-5, -5, 50, 30)));

        AffineTransform tx = new AffineTransform();
        tx.rotate(-Math.toRadians(degree), width / 2, height - 68);
        tx.translate(width / 2 - 15, height - 78);

        GeneralPath path = new GeneralPath();
        path.append(tx.createTransformedShape(mainPartRect), false);

        return path;
    }

    protected Area body() {
        Area area = new Area(new Arc2D.Double(width / 2 - 55, height - 80, 110, 110, 0, 180, 1));
        area.subtract(new Area(new Arc2D.Double(width / 2 - 40, height - 65, 80, 70, 0, 180, 1)));
        area.add(new Area(new Rectangle2D.Double(width/2-40,height-45,80,20)));
        return area;
    }

    private void fire() {
        if (this.gameStart && this.score >= 15) {
            fireList.add(new Fires(width, height, degree));
            this.score -= 15;
        } else
            this.gameStart = false;
    }

    protected int getScore() {
        return this.score;
    }

    protected void setScore() {
        this.score = 150;
    }

    protected void incrementScore() {
        this.score += 10;
    }

    protected boolean getGameStart() {
        return this.gameStart;
    }

    protected void setGameStart() {
        this.gameStart = true;
    }

    protected List<Fires> getFireList() {
        return this.fireList;
    }

    protected void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT && degree < 180)
            degree += 5;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && degree > 0)
            degree -= 5;
        if (e.getKeyCode() == KeyEvent.VK_UP)
            fire();
    }

}
