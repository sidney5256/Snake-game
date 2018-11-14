import javax.swing.JFrame; //imports the JFrame class from swingx package


public class Snake extends JFrame {

    public Snake() {

        add(new Board());//adds a new Board to the frame 

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(320, 340);//sets the size of the frame
        setLocationRelativeTo(null); //sets the frame to center of the screen
        setTitle("Snake"); //sets the title of the frame

        setResizable(false); //can not change tthe size
        setVisible(true); //makes the frame visible
    }

    public static void main(String[] args) {
        new Snake();//runs the constructor
        int sum = 155;
        System.out.printf("Your sum is: %d and your result is %d\n", sum, sum);
    }
}
