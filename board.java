//imports classes from the awt package
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//imports classes from the swingx package
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/*****
 * Board class 
 * extends JPanel
 * implements ActionListener
 * */
public class Board extends JPanel implements ActionListener {

    //declare and initialize constants
    private final int WIDTH = 300;
    private final int HEIGHT = 300;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
    private final int RAND_POS = 29;
    private final int DELAY = 140;

    //declare and initialize arrays
    private int x[] = new int[ALL_DOTS];
    private int y[] = new int[ALL_DOTS];

    //declare private ints
    private int dots;
    private int apple_x;
    private int apple_y;

    //declare private booleans
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;

    //declares private Timer
    private Timer timer;
    
    //declares private Images
    private Image ball;
    private Image apple;
    private Image head;

/*
 * Board constructor
 * */
    public Board() {
        addKeyListener(new TAdapter()); //adds new addKeyListener named TAdapter

        setBackground(Color.black); //sets the color of the background as black

        ImageIcon iid = new ImageIcon(this.getClass().getResource("GreenDot.gif")); //imports the BlueDot.gif
        ball = iid.getImage();//sets the ball image as the imported green dot

        ImageIcon iia = new ImageIcon(this.getClass().getResource("BlueDot.gif")); //imports the BlueDot.gif
        apple = iia.getImage();//sets the apple image as the imported blue dot

        ImageIcon iih = new ImageIcon(this.getClass().getResource("RedDot.png")); //imports the RedDot.png
        head = iih.getImage();//sets the head image as the imported red dot

        setFocusable(true); //sets the board to be focuable
        initGame();//runs meathod initGame
    }

/*
 * initGame
 * */
    public void initGame() {

        dots = 3; //sets dots = to 3

        //loops through as long as i is less that dots
        for (int i = 0; i < dots; i++) {
            x[i] = 50 - i*10;
            y[i] = 50;
        }

        locateApple();//runs meathod locateApple

        timer = new Timer(DELAY, this); //initializes timer to be a new timer with paramters DELAY and this
        timer.start(); //runs the timer
    }


    /*
     * paint
     * draws the stuff
     * */
    public void paint(Graphics g) {
        super.paint(g);

        //checks to see if inGame is true
        if (inGame) {

            g.drawImage(apple, apple_x, apple_y, this); //draws image of the apple

            //loops through
            for (int z = 0; z < dots; z++) {
              //checks to see if z is 0
                if (z == 0)
                    g.drawImage(head, x[z], y[z], this); //draws the head image 
                else g.drawImage(ball, x[z], y[z], this); //draws the ball image 
            }

            Toolkit.getDefaultToolkit().sync();
            g.dispose();

        } else {
            gameOver(g);//runs method gameOver
        }
    }

    /*
     * gameOver
     * */
    public void gameOver(Graphics g) {
        String msg = "Game Over"; //declares and initalizes string as Game Over 
        Font small = new Font("Helvetica", Font.BOLD, 14); //declare and initalizes a new font
        FontMetrics metr = this.getFontMetrics(small); 

        g.setColor(Color.white); //sets the color of the pen to white
        g.setFont(small); //set the size of the font to small
        g.drawString(msg, (WIDTH - metr.stringWidth(msg)) / 2, HEIGHT / 2);//draws the string
    }


    /*
     * checkApple
     * */
    public void checkApple() {

      
        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++;//increases the number of dots by one
            locateApple(); //runs locateApple
        }
    }

/*
 * move
 * */
    public void move() {

        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (left) {
            x[0] -= DOT_SIZE;
        }

        if (right) {
            x[0] += DOT_SIZE;
        }

        if (up) {
            y[0] -= DOT_SIZE;
        }

        if (down) {
            y[0] += DOT_SIZE;
        }
    }


    public void checkCollision() {

          for (int z = dots; z > 0; z--) {

              if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                  inGame = false;
              }
          }

        if (y[0] > HEIGHT) {
            inGame = false;
        }

        if (y[0] < 0) {
            inGame = false;
        }

        if (x[0] > WIDTH) {
            inGame = false;
        }

        if (x[0] < 0) {
            inGame = false;
        }
    }

    public void locateApple() {
        int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * DOT_SIZE));
        r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * DOT_SIZE));
    }

    public void actionPerformed(ActionEvent e) {

        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }

        repaint();
    }


    private class TAdapter extends KeyAdapter {

        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!right)) {
                left = true;
                up = false;
                down = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!left)) {
                right = true;
                up = false;
                down = false;
            }

            if ((key == KeyEvent.VK_UP) && (!down)) {
                up = true;
                right = false;
                left = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!up)) {
                down = true;
                right = false;
                left = false;
            }
        }
    }
}
