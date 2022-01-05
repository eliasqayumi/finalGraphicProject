package arkadaslarFinal;

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
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.util.Random;

public class Board extends JPanel implements ActionListener {
    //    pencere boyutu
    private double width = 500;
    // pencere genişliği
    private double height = 400;
    //    Timmer degiskten tanimlanma
    private Timer timer;
    //    graphic 2d ile kalemimizi global ettik
    private Graphics2D g2d;
    //    tetikleme sure
    private final int DELAY = 50;
    //    ilk namlonun acisi
    private int degree = 90;
    //    namlu icin tanimlanan nesne degiskeni
    private FireGun fireGun;
    //    dusman nesnelerini tutabilmek icin list icine atmamiz gerekiyor ve listi dusman siniftan olusturdum
    private List<Alien> aliens;
    //    oyun basladi mi diye kontrol degiskeni
    private boolean startGame = true;
    //    uc kez oyun oynayabilmek icin tanimlanan degisken
    private int lifeCounter = 1;
    //    dusman nesneler ya da balonlarin x yerini ve y yerini tutmak icin tanimladim array list
    private ArrayList<Double> xList;
    private ArrayList<Double> yList;
    //    rastgele sayi uretmek icin random degisken tanimladim
    private Random random;


    public Board() {
        // bir kez calisan bizim yapilandirici method initBoard methodu tetikler
        initBoard();
    }

    private void initBoard() {
//        klavye dinleyici ekledim ve asagida olusturan TAdapter inner classi referanssiz parametre olarak atadim
        addKeyListener(new TAdapter());
//        ve dinle dikkatini dogru yaptim
        setFocusable(true);
//        zaman atama ve calistirma
        this.timer = new Timer(DELAY, this);
        this.timer.start();
//        top nesnesini olusturuma
        this.fireGun = new FireGun(500, 400, degree);
//        dusman nesnesi yada balonlar icin yazilan metodunu tetikledim
        initAliens();
    }

    private void initAliens() {
//        basta tanimladim dusman nesnesi ya balonlar icin bir liste tutacam ve bu listenin nesnesini turettim
        this.aliens = new ArrayList<>();
//        rastgele sayi uretmek icin bunun nesnesini turettim
        this.random = new Random();

//        topun ust uste basilmamasi icin ya ayni konumdan uretilmemesi icin liste olusturdum
        double number;
        xList = new ArrayList();
        yList = new ArrayList();
//        dusman nesnesi ya balonlar 10 tane olmasi lazim onun icin while dongu icine kontrol ettim
        while (xList.size() != 10) {
//          eger rastgele elde olusan deger listede yoksa at eger varsa yeni bir deger uret  x konum icin
            while (xList.contains(number = Math.abs(random.nextInt() % 450))) ;
            xList.add(number);
        }
//        dusman nesnesi ya balonlar 10 tane olmasi lazim onun icin while dongu icine kontrol ettim
        while (yList.size() != 10) {
//            eger rastgele elde olusan deger listede yoksa at eger varsa yeni bir deger uret y konum icin
//             nenselerin y konumu rastgele gelen sayinin eksisinden baslatiyoruz pencerenin disarisindan gelmesini sagliyoruz ve boylece farkli zamanlara gelmesini saglamis oluruz
            while (yList.contains(number = -(Math.abs(random.nextInt() % 400)))) ;
            yList.add(number);
        }
//         elde olusan konum boyutu yani 10 tane dongu dolassin
        for (int i = 0; i < xList.size(); i++) {
            int numb;
//            her tekrarda yeni bir rastgele dusman nesnesi ya balon olustursun
            Alien alien = new Alien(400, 500, xList.get(i), yList.get(i));
//            ve o olusan nesnenin hizini rastgele belirle eger rastgel gelen sayi 0 ise 1 yap elde olusan sayiyi at
            alien.setSpeed((numb = Math.abs(random.nextInt()) % 5) == 0 ? 1 : numb);
//            ve olusan nesnenin rastegle deger ile boya (R G B) rengini rastegele belirliyoruz
            alien.setRGB(Math.abs(random.nextInt()) % 256, Math.abs(random.nextInt()) % 256, Math.abs(random.nextInt()) % 256);
//            olusan nesneyi listede at
            aliens.add(alien);
        }
    }

    public Timer getTimer() {
        return this.timer;
    }

    private void doDrawing() {
        width = getWidth();
        height = getHeight();
//        randerlestir yani nesnelerimizin kenarlarini guzellestirir
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//        pencerede degisim gostermek icin etiket bastiriyor
        g2d.drawString("Aci " + fireGun.getDegree() + "˚", 440, 20);
        g2d.drawString("Puan " + fireGun.getScore() + "˚", 440, 40);
//       topu cizdiriyorum buradan
        g2d.fill(fireGun.fireGunDesign());
//        topun tekerligini ciziyorum
        g2d.fill(wheel());
//        kalemin boyasini kirmizi yaptim
        g2d.setPaint(Color.red);
//        verilen 150 puan bitiyse kontrol olur ve boylece oyun biter
        if (!fireGun.gameStart())
            drawGameOver();
//      topun tuttugu atis listesini liste tipini aliyorum
        List<Fires> firesList = fireGun.getFireList();
//        her birini atis nese ile for loop icinde dolasiyorum
        for (Fires fire : firesList)
//            eger o nesnenin gorunumu yani visible=true ise ekrana cizdiriyorum
            if (fire.isVisible())
//                ekrana cizdirirken o nesnenin x ve y konumu kendi nesnesinden aliyorum
                this.g2d.fill(new Ellipse2D.Double(fire.getXBall(), fire.getYBall(), 10, 10));
//            basta olusturdugum dusman gemisi veya balonlar listesine dolasmak icin for each loop olusturuyorum
//        ve her birini dolasmak icin dusman nesne tipinden degisken tanimliyorum ve o gelen listedeki nesneyi atiyorum
        for (Alien alien : aliens) {
//            eger o nesnenin gorunumu yani visible=true ise ekrana cizdiriyorum
            if (alien.isVisible())
//                cizdirmeden once basta tanimladigimiz her dusman nesne icin bir rastgele renklendirme atamistik
//                nesnenin ragtele renglendirmesini cekiyorum
                this.g2d.setPaint(new Color(alien.getColor()[0], alien.getColor()[1], alien.getColor()[2]));
//            basta tanimladigim rastege x ve rastgele y konumu nesne sinif icinden cekerek burada kullanarak nesneyi cizdirdim
            this.g2d.fill(new Ellipse2D.Double(alien.getXPoint(), alien.getYPoint(), 20, 20));
        }
    }

    //        oyun bitince bu methood calisir ve oyurn bittigini mesaj olarak gosterir
    private void drawGameOver() {
//        ekrana ne cikti verecegini belirledim
        String msg = "Game Over";
//        yukaridaki yazimin tipini kalinligini ve olcutunu belirledim
        Font small = new Font("Helvetica", Font.BOLD, 14);

//        yukarida yazilan degerin uzunlugunu almak icin font metrics kullandim
        FontMetrics fm = getFontMetrics(small);
//       kalemin rengini beyaz yaptim
        this.g2d.setColor(Color.white);
//        kalemi fontunu belirliyoruz  basta taninladigim font tipini atiyoruz
        this.g2d.setFont(small);
//        ve mesaj nerde basilsin
        this.g2d.drawString(msg, (int) (width / 2 - fm.stringWidth(msg) / 2), (int) (height / 2));
    }

    //    oyun kazaninca bu method calisir ve bu puanla oyunu kazandir dire mesaj verir
    private void drawGameWon() {
        //        ekrana ne cikti verecegini belirledim
        String msg = "Game Won! with " + fireGun.getScore() + " score";
        //        yukaridaki yazimin tipini kalinligini ve olcutunu belirledim
        Font small = new Font("Helvetica", Font.BOLD, 14);
        //        yukarida yazilan degerin uzunlugunu almak icin font metrics kullandim
        FontMetrics fm = getFontMetrics(small);
        //       kalemin rengini beyaz yaptim

        this.g2d.setColor(Color.white);
        //        kalemi fontunu belirliyoruz  basta taninladigim font tipini atiyoruz
        this.g2d.setFont(small);
        //        ve mesaj nerde basilsin
        this.g2d.drawString(msg, (int) (width / 2 - fm.stringWidth(msg) / 2), (int) (height / 2));
    }

    private void newLife() {
        //        ekrana ne cikti verecegini belirledim
        String msg = lifeCounter + ". chance";
        //        yukaridaki yazimin tipini kalinligini ve olcutunu belirledim
        Font small = new Font("Helvetica", Font.BOLD, 14);
        //        yukarida yazilan degerin uzunlugunu almak icin font metrics kullandim
        FontMetrics fm = getFontMetrics(small);
        //       kalemin rengini beyaz yaptim
        this.g2d.setColor(Color.white);
        //        kalemi fontunu belirliyoruz  basta taninladigim font tipini atiyoruz
        this.g2d.setFont(small);
        //        ve mesaj nerde basilsin
        this.g2d.drawString(msg, (int) (width / 2 - fm.stringWidth(msg) / 2), (int) (height / 2));
//        puani yine 150 cektim
        fireGun.setScore(150);
//        yeni bir dusman nesne turettmek icin bu methodu cagirdim
        initAliens();

    }

    public void startGame() {
//        eger startGame=false ise zamanlamayi durdur
        if (!startGame)
            this.timer.stop();
    }

    private Area wheel() {
//        asagidaki namlonun tekerliklerini ciz
        Area area = new Area(new Arc2D.Double(width / 2 - 55, height - 50, 110, 100, 0, 180, 1));
        area.subtract(new Area(new Arc2D.Double(width / 2 - 40, height - 35, 80, 70, 0, 180, 1)));
        return area;
    }
//      her tetiklemede bu method calisir atisin konumu degistirmek yada silmek icin kullanilir
    private void updateFires() {
        List<Fires> fires = this.fireGun.getFireList();
//        atis yapilan oklari  listeden alip ve buna gore dongu olusturdum
        for (int i = 0; i < fires.size(); i++) {
//           listedeki gelen oku ok nesnesi ile al
            Fires m = fires.get(i);
//            eger okun gorunumu visible =true is o zaman hareket ettir.
            if (m.isVisible())
                m.move();
            else
//                eger okun gorunumu visible = false ise bunu listeden sil
                fires.remove(i);
        }
    }
//      her tetiklemede bu method calisir dusman nesnesinin konumu degistirmek yada silmek icin kullanilir
    private void updateAliens() {
//        eger dusman nesne listesi bos is oyunu bitir,yani  startGame=false yap
        if (this.aliens.isEmpty()) {
            this.startGame = false;
            return;
        }

        for (int i = 0; i < this.aliens.size(); i++) {
//            listeden gelen nesneyi arkadaslarFinal.Alien degisken tipi olarak tutmak icin kullandim
            Alien a = this.aliens.get(i);
//            eger nesenin gorunumu visible =true is o zaman hareket ettir.
            if (a.isVisible())
                a.move();
            else
//                eger nesnenin gorunumu visible = false ise bunu listeden sil
                this.aliens.remove(i);
        }
    }
//      atis ile dusman nesneleri cakisip cakismadigini kontrol ediyor
    public void checkCollisions() {
//         atisleri tumunu listeye at
        List<Fires> fireList = fireGun.getFireList();
//        her atis  okunu konumu bilmek icin for loop kullaniriz
        for (Fires fire : fireList) {
//            atisin sinirlarini aldik
            Rectangle2D r1 = fire.getBound();
            for (Alien alien : aliens) {
//                dusman nesnesinin sinirlarini aldik
                Rectangle2D r2 = alien.getBound();
//                ikisinin cakisip cakismadigini kontrol ettim
                if (r1.intersects(r2)) {
//                    eger caktisiyse o zaman 10 puan ekle
                    fireGun.incrementScore(10);
//                    ve iki nesnenin de gorunumunu, yani visible=false yap;
                    fire.setVisible(false);
                    alien.setVisible(false);
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.g2d = (Graphics2D) g;
//        eger oyun devam ediyorsa dro drawing calisir
        if (startGame) {
            doDrawing();
        } else {
            if (aliens.isEmpty())
//                startGame=false ise dusman nesne listesi bos olup olmadigini kontrol ediyor ve boyleve eger  bos ise drawGameWon yani kazandiniz diye method calisir.
                drawGameWon();
            else
//                eger bos degilse puan ve atis hakkiniz bitmistir diye bu method calisir
                drawGameOver();
        }
        Toolkit.getDefaultToolkit().sync();
//        eger kullanicinin 3 kez oynamadiyse uc kez oynama hakki verir
        if (fireGun.getScore() <= 0 && lifeCounter <= 3) {
            lifeCounter++;
            newLife();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        her tekrarda bu metodlari cagirir
        startGame();
        updateFires();
        updateAliens();
        checkCollisions();
        repaint();
    }

    private class TAdapter extends KeyAdapter {
        @Override
//        firgun sinifinda keypressed methoduna basilan tusun degerini gonderiyor
        public void keyPressed(KeyEvent e) {
            fireGun.keyPressed(e);
        }
    }
}
