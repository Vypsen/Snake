import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;


public class GameField extends JPanel implements ActionListener {

    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private Image dot, apple;
    private int appleX, appleY;
    private final int[] x = new int[ALL_DOTS];
    private final int[] y = new int[ALL_DOTS];
    private int dots = 3;
    private Timer timer;
    private final String[]orientation = {"left", "right", "up", "down"};
    private boolean inGame = true;
    private int delay = 250;
    private String current_orientation;



    public GameField(){
        setBackground(Color.black);
        loadImage();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);

    }

    public void initGame(){

        dots = 3;
        delay = 250;

        int start_position_x = (new Random().nextInt(21-3) + 3);
        int start_position_y =  new Random().nextInt(20)*DOT_SIZE;
        current_orientation = orientation[new Random().nextInt(4)];
        int flag = current_orientation.equals("left")?-1:1;
        for (int i = 0; i < dots; i++) {
            x[i] = Math.abs(16*i*flag - start_position_x*DOT_SIZE);
            y[i] = start_position_y;
        }
//        System.out.println(Arrays.toString(x));
//        System.out.println(Arrays.toString(y));

        timer = new Timer(delay,this);

        timer.start();
        createApple();
        System.out.println(delay);



    }

    public void createApple(){
        appleX = new Random().nextInt(20)*DOT_SIZE;
        appleY = new Random().nextInt(20)*DOT_SIZE;
    }

    public void loadImage(){

        ImageIcon imageIconApple = new ImageIcon("apple.png");
        apple = imageIconApple.getImage();

        ImageIcon imageIconDot = new ImageIcon("dot.png");
        dot = imageIconDot.getImage();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame){
            g.drawImage(apple,appleX,appleY,this);
            for (int i = 0; i < dots; ++i) {
                g.drawImage(dot,x[i],y[i], this);
            }
        }


    }

    public void move(){

        for (int i = dots-1; i!=0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }


        switch (current_orientation) {
            case "left" -> x[0] -= DOT_SIZE;
            case "right" -> x[0] += DOT_SIZE;
            case "up" -> y[0] -= DOT_SIZE;
            default -> y[0] += DOT_SIZE;
        }

//        System.out.println(Arrays.toString(x));
//        System.out.println(Arrays.toString(y));
    }

    public void checkCollisions(){
        for (int i = dots; i > 0 ; i--) {
            if (i > 3 && x[0] == x[i] && y[0] == y[i]) {
                restartGame();
                break;
            }
        }
        if(x[0]>SIZE) x[0] = 0;
        else if (x[0]<0) x[0] = SIZE;
        else if (y[0]>SIZE) y[0] = 0;
        else if (y[0]<0) y[0] = SIZE;

    }



    public void checkApple(){
        if(x[0] == appleX && y[0] == appleY){
            System.out.println(timer.getDelay());
            dots++;
            if(delay!=10) timer.setDelay(delay-=5);
            createApple();

        }

    }

//    public void createCounter() {
//        JLabel counter = new JLabel("0");
//        counter.setForeground(Color.WHITE);
//        add(counter);
//        counter.setFont(new Font("Serif", Font.PLAIN, 14));
//    }

    public void restartGame(){
        inGame = false;
        JButton button = new JButton("Restart game");
        button.setBackground(Color.RED);
        add(button);
        button.setBounds(120, 50,150, 50 );
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inGame = true;
                button.setVisible(false);
                MainWindow mw = new MainWindow();
            }
        });




    }

    class FieldKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !current_orientation.equals("right")){
                current_orientation = "left";
            }
            if(key == KeyEvent.VK_RIGHT && !current_orientation.equals("left")){
                current_orientation = "right";
            }
            if(key == KeyEvent.VK_UP && !current_orientation.equals("down")){
                current_orientation = "up";
            }
            if(key == KeyEvent.VK_DOWN && !current_orientation.equals("up")){
                current_orientation = "down";
            }
        }


    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if(inGame){
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }
}
