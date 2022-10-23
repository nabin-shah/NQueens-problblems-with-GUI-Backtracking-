
/* Nabin Shah()  */

public class Calculation {

    private int[] board; 
    private static final int UNASSIGNED=100;

    public Calculation(int queens){
        if (queens <0) throw new IllegalArgumentException();
        board=new int[queens];
        for(int i=0; i<queens; i++) board[i]=UNASSIGNED;
    }
    public boolean isSafe(int row, int column){
        row--;
        column--;
        //check row and column
        if(!isPossible(row,column)) throw new IllegalArgumentException();
        //check if column is empty
        if(board[column]!=UNASSIGNED) return  false;
        //check position with remaining columns
        for(int currentColumn=0; currentColumn<board.length; currentColumn++){
            int dis= column- currentColumn;
            //check diagonal
            if(board[currentColumn] == row -dis ) return false;
            //check row
            if (board[currentColumn] == row ) return false;
            //check right diagonal
            if (board[currentColumn]== row+dis) return false;
        }
        return true;
    }
    public void doPlace(int row, int column){
        if(!isSafe(row, column)) throw new IllegalArgumentException();
        board[column-1]=row-1;
    }
    public void remove(int row, int column){
        if (!isPossible(row-1, column-1) || board[column-1]!=row-1) throw new IllegalArgumentException();
        board[column-1]=UNASSIGNED;
    }
    public int size(){ return board.length; }

    protected int getQueenRFC(int column){return board[column];}
    
    private boolean isPossible(int row, int column){
        return row>=0 && row< board.length && column >=0 && column< board.length;
    }
}
