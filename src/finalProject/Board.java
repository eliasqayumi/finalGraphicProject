package finalProject;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.util.Random;

public class Board extends JPanel implements ActionListener {
    private double width;
    private double height;
    private Timer timer;
    private Graphics2D g2d;
    private final int DELAY = 50;
    private int degree = 90;
    private FireGun fireGun;
    private List<Balloons> balloons;
    private boolean startGame = true;
    private int lifeCounter = 1;
    private List<Double> xList;
    private List<Double> yList;
    private Random random;


    public Board() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        this.timer = new Timer(DELAY, this);
        this.timer.start();
        this.fireGun = new FireGun(500, 400, degree);
        initBalloons();
    }

    private void initBalloons() {
        this.balloons = new ArrayList<>();
        this.random = new Random();
        double number = Math.abs(random.nextInt() % 450);
        xList = new ArrayList();
        yList = new ArrayList();
        while (xList.size() != 10) {
            for (double i = 0; i <= 20; i++)
                while (xList.contains(i + number) || xList.contains(number - i)) {
                    number = Math.abs(random.nextInt() % 450);
                    i = -1;
                }
            xList.add(number);
        }
        while (yList.size() != 10) {
            while (yList.contains(number = -(Math.abs(random.nextInt() % 400)))) ;
            yList.add(number);
        }
        for (int i = 0; i < xList.size(); i++) {
            int numb;
            Balloons balloons = new Balloons(500, 400, xList.get(i), yList.get(i));
            balloons.setSpeed((numb = Math.abs(random.nextInt()) % 5) == 0 ? 1 : numb);
            balloons.setRGB(Math.abs(random.nextInt()) % 256, Math.abs(random.nextInt()) % 256, Math.abs(random.nextInt()) % 256);
            this.balloons.add(balloons);
        }
    }

    public Timer getTimer() {
        return this.timer;
    }

    private void doDrawing() {
        width = getWidth();
        height = getHeight();
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2d.draw(new Rectangle2D.Double(width-100,5,98,40));
        g2d.drawString("Degree: " + fireGun.getDegree() + "˚", (int)(width-90), 20);
        g2d.drawString("Score : " + fireGun.getScore() , (int)(width-90), 40);

        g2d.fill(fireGun.fireGunDesign());
        g2d.setPaint(Color.BLUE);
        g2d.draw(fireGun.fireGunDesign());
        g2d.setPaint(Color.BLACK);
        g2d.fill(fireGun.body());
        g2d.setPaint(Color.RED);
        g2d.draw(fireGun.body());
        if (!fireGun.getGameStart())
            drawGameOver();
        for (Fires fire : fireGun.getFireList())
            if (fire.isVisible())
                this.g2d.fill(new Ellipse2D.Double(fire.getXPoint(), fire.getYPoint(), 10, 10));
        for (Balloons balloons : this.balloons) {
            if (balloons.isVisible())
                this.g2d.setPaint(new Color(balloons.getRGB()[0], balloons.getRGB()[1], balloons.getRGB()[2]));
            this.g2d.fill(new Ellipse2D.Double(balloons.getXPoint(), balloons.getYPoint(), 20, 20));
        }
    }

    private void drawGameOver() {
        String msg;
        if (balloons.isEmpty())
            msg = "Game Won! with " + fireGun.getScore() + " score";
        else {
            if (lifeCounter > 2)
                msg = "Game Over";
            else
                msg = "Wait, " + (lifeCounter + 1) + ". Chance Will Start! ";
        }
        Font small = new Font("Helvetica", Font.BOLD, 32);
        FontMetrics fm = getFontMetrics(small);
        this.g2d.setColor(Color.ORANGE);
        this.g2d.setFont(small);
        this.g2d.drawString(msg, (int) (width / 2 - fm.stringWidth(msg) / 2), (int) (height / 2));
        this.g2d.setColor(Color.RED);
    }

    private void newLife() {
        lifeCounter++;
        fireGun.setScore();
        fireGun.setGameStart();
        initBalloons();
    }

    public void startGame() {
        if (!startGame)
            this.timer.stop();
    }

    private void updateFires() {
        List<Fires> fires = this.fireGun.getFireList();
        for (int i = 0; i < fires.size(); i++) {
            Fires fire = fires.get(i);
            if (fire.isVisible())
                fire.move();
            else
                fires.remove(i);
        }
    }

    private void updateBalloons() {
        if (this.balloons.isEmpty()) {
            this.startGame = false;
            return;
        }
        for (int i = 0; i < this.balloons.size(); i++) {
            Balloons balloon = this.balloons.get(i);
            if (balloon.isVisible())
                balloon.move();
            else
                this.balloons.remove(i);
        }
    }

    public void checkCollisions() {
        for (Fires fire : fireGun.getFireList()) {
            Area r1 = fire.getBound();
            for (Balloons balloons : this.balloons)
                if (r1.intersects(balloons.getXPoint(), balloons.getYPoint(), 20, 20)) {
                    fireGun.incrementScore();
                    fire.setVisible(false);
                    balloons.setVisible(false);
                }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.g2d = (Graphics2D) g;
        if (startGame)
            doDrawing();
        else
            drawGameOver();
        Toolkit.getDefaultToolkit().sync();
        if (fireGun.getScore() < 15 && lifeCounter < 3) {
            for (Fires fires : fireGun.getFireList())
                if (fires.isVisible())
                    return;
            newLife();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        startGame();
        updateFires();
        updateBalloons();
        checkCollisions();
        repaint();
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            fireGun.keyPressed(e);
        }
    }
}
