package arkadaslarFinal;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main extends JFrame {
    public Main() {
        initUI();
    }

    private void initUI() {
        final Board surface=new Board();
        setTitle("Final Project");
        setSize(500,400);
        add(surface);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Timer timer=surface.getTimer();
                timer.stop();
            }
        });

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        Main main=new Main();
        main.setVisible(true);
    }
}
