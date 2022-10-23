import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.Scanner;

/* Nabin Shah()  */

public class ChessBoard extends Calculation {
    private JButton[][] jB;
    private int slowDown;
    private JFrame frame;

    public ChessBoard(int queens){
        super(queens);
        frame=new JFrame();
        frame.setSize(100*queens+50,100*queens+80);
        frame.setTitle("ChessBoard");
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        Container con= frame.getContentPane();
        //add sliders
        final int maxWait= 1000; //  time in milliseconds
        final JSlider s= new JSlider(0,maxWait-20);
        slowDown=s.getValue();
        s.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                slowDown=maxWait - s.getValue(); }
        });
        JPanel panel=new JPanel();
        panel.add(new JLabel("Slow"));
        panel.add(s);
        panel.add(new JLabel("Fast"));
        con.add(panel, "South");
        //add Buttons
        panel=new JPanel(new GridLayout(queens,queens,0,0));
        con.add(panel,"Center");
        jB= new JButton[queens][queens];
        Font font=new Font("Serif",Font.BOLD ,8);
        for(int i=0; i<queens;i++)
            for(int j=0;j<queens;j++){
                JButton squares= new JButton();
                if ((i+j)%2==0) {squares.setBackground(Color.gray);}
                else {squares.setBackground(Color.white);}
                squares.setFont(font);
                panel.add(squares);
                jB[i][j]=squares;
            }

        frame.setVisible(true);
        frame.toFront();
    }
    public void doPlace(int row, int column){
        super.doPlace(row,column);
        jB[row-1][column-1].setIcon(new ImageIcon("image/Queen_black.png"));
        waitTime();
    }
    public boolean isSafe(int row, int column){
        jB[row-1][column-1].setText("check");
        waitTime();
        jB[row-1][column-1].setText("");
        return super.isSafe(row,column);
    }
    public void remove(int row, int column){
        super.remove(row, column);
        jB[row-1][column-1].setText("undo");
        waitTime();
        jB[row-1][column-1].setText("");
        jB[row-1][column-1].setIcon(null);
    }
    public void waitTime(){ waitTime(slowDown);}
    private void waitTime(int timeDelay) {
        try {Thread.sleep(timeDelay);}
        catch(Exception e) { throw new InternalError();}
        ;}

    public void plotQueen(){
        for (int i=0; i<6;i++){
            for(int column=0; column<size(); column++){
                int row=getQueenRFC(column);
                jB[row][column].setText("✔");
            }
            waitTime(800);
            for (int column=0; column<size();column++){
                int row=getQueenRFC(column);
                jB[row][column].setIcon(new ImageIcon("image/Queen_black.png"));
            }
        }
    waitTime(800);
    }
    public static void solve(Calculation calc){  explore((ChessBoard) calc, 1); }
    private static void explore(ChessBoard calc,int column){  ///Board to BoardFrame
        if(column== calc.size()+1) {calc.plotQueen();}
        else { //recursive
            for ( int row=1; row<= calc.size();row++){
                if (calc.isSafe(row, column)){
                    calc.doPlace(row, column);
                    explore(calc,column+1);
                    calc.remove(row, column);
                }
            }

        }
    }

    public static void main(String[] args) {
        System.out.println("N-Queens problem. The size of\n" +
        "chessboard is based on number of queens.\n"+
        "For eg-for 4 queens 4x4 chessboard is created\n\n");
        Scanner scanner=new Scanner(System.in);
        System.out.print("Enter the number of Queens: ");
        int queens=scanner.nextInt();
        ChessBoard chessboard =new ChessBoard(queens);
        solve(chessboard);
    }
}
