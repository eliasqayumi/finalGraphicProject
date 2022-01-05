package arkadaslarFinal;

import java.awt.event.KeyEvent;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;

public class FireGun {
    private int score = 150;
    private Boolean gameStart = true;
    private List<Fires> fireList;
    private double width;
    private double height;
    private double degree;

    public FireGun(double width, double height, double degree) {
        this.width = width;
        this.height = height;
        this.degree = degree;
        fireGunDesign();
        fireList = new ArrayList<>();
    }

//  namlu cizmek icin method
    public GeneralPath fireGunDesign() {
//        bir dortgen olusturdum
        Area mainPartRect = new Area(new Rectangle2D.Double(0, 0, 70, 20));
//        bir tane bu dortgen sag ucunda ellips olusturup ekledim
        mainPartRect.add(new Area(new Ellipse2D.Double(50, -5, 20, 30)));
//        bi tane de genisligi uzun olan bir ellips olusturup soldan gobege kadar uzattim
        mainPartRect.add(new Area(new Ellipse2D.Double(-5, -5, 50, 30)));
//        bu nesneyi rotate yapmak icik AffinTransform nesnesini turettim
        AffineTransform tx = new AffineTransform();
//        bu nesnenin neresinden rotate yapmasini belirlemek icin kullandim
        tx.rotate(-Math.toRadians(degree), width / 2, height - 68);
//        bu nesnemizin konumunu belirlemek icin kullandim
        tx.translate(width / 2 - 15, height - 78);
        GeneralPath path = new GeneralPath();
        path.append(tx.createTransformedShape(mainPartRect), false);
//        ana sayfaya basmak icin return olarak GeneratlPath tipinde path adli nesnesini gonderdirm
        return path;
    }

    protected double getDegree() {
        return degree;
    }

    private void fire() {
//        basilan tus yukari yon ise bu method calisir
//        eger gameStart=true puan >0 ise yeni bir atis yap ve puani azalt
        if (gameStart && score > 0) {
//            atis yap yani listeye ekle
            fireList.add(new Fires(width, height, degree));
//            puani  azalt
            score -= 15;
        } else
            gameStart = false;
    }
//    puani cek
    protected int getScore() {
        return score;
    }
//  3 kez oynanmasi ici bu method kullanilir
    protected void setScore(int score) {
        this.score = score;
    }
//  eger dusman nesnesini vurabilse 10 puan ekleniyor
    protected void incrementScore(int increment) {
        this.score += increment;
    }
//oyun bittimi yoksa devam ediyor mu diyor kontrol etmek icin kullanildi
    protected boolean gameStart() {
        return gameStart;
    }

//  atis listesini dondurmek icin kullandim
    public List<Fires> getFireList() {
        return fireList;
    }
//     hangi butun basildigini kontrol eder
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT && degree < 180)
//            eger basilan buton sol ise ve acimiz 180den buyuk degilse o zaman aciyi artir
            degree += 5;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && degree > 0)
//            eger basilan buton sag ise acimiz 0 dan kucuk degilse o zaman aciyi azalt
            degree -= 5;
        if (e.getKeyCode() == KeyEvent.VK_UP)
//            eger basilan tus okun ustu ise fire() methodunu calistir
            fire();
    }

}
