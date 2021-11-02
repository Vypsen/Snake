import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow(){
        setTitle("Snake");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400,400);
        setResizable(false);
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args) {
        MainWindow mw = new MainWindow();

    }
}
