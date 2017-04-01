import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by jh306 on 31/03/17.
 */
public class othello {

    private char[][] board; /* char array that stores the values of each cell of the play area */
    private ArrayList<move> legalMoves = new ArrayList<move>();
    private static final int X = 8; /* represents row number */
    private static final int Y = 8;

    private static final String BLUE = "\u001b[34m"; /* represents a blue coloured output */
    private static final String RESET = "\u001b[0m"; /* represents the default coloured output */
    private static final String GREEN = "\u001B[32m"; /* represents a red coloured output */

    othello() {

        this.board = new char[X][Y];
        makeBoard();
        showBoard();
    }

    private void startGame() {

        char player = 'B'; /* It is black's turn first */
        System.out.println("Black's turn");
        String x = "";

        while (!this.gameOver()) { /* Game loop that ensures the game continues while it isn't over */

            if (player=='W') {
                int[] co_ords = agent2();
                this.validMove('W', co_ords[0], co_ords[1]);
            }

              //  boolean valid; /* boolean value that represents if the desired move is valid or not *

              //  System.out.println("Please enter the location you wish to place your counter");
               // Scanner sc = new Scanner(System.in);
                //x = sc.next();
                //valid = this.validMove(player, Character.getNumericValue(x.charAt(0)), Character.getNumericValue(x.charAt(1)));

              //  while (!valid) {
                 //   System.out.println("Invalid location, please enter another");
                  //  Scanner scan = new Scanner(System.in);
                   // x = scan.next();
                    //valid = this.validMove(player, Character.getNumericValue(x.charAt(0)), Character.getNumericValue(x.charAt(1)));
               // }
            //}

            else {
                int[] co_ords = agent();
                this.validMove('B', co_ords[0], co_ords[1]);
            }

            showBoard();

            if (player == 'B') {
                System.out.println("White's turn");
                player = 'W';
            } else if (player == 'W'){
                System.out.println("Black's turn");
                player = 'B';
            }
            legalMoves.clear();
        }

        this.stopGame();
    }

    private boolean validLine(char player, int i, int j, int i_dir, int j_dir) {

        return checkLine(i, j, player, i_dir, j_dir);
    }

    private boolean checkLine(int i, int j, char player, int rowDir, int colDir) {

        boolean validLine = false;
        int row = i + rowDir;
        int col = j + colDir;

        if(this.board[row][col]=='O') {
            return false;
        } else if (row==8 || row<0 || col==8 || col<0) {
            return false;
        }

        while (this.board[row][col]=='B' || this.board[row][col]=='W') {

            if (this.board[row][col]==player) { /* If we are at the end of the line */

                while(!(i==row && j==col)) { /* This ensures that we backtrack through the line */
                    validLine = true; /* The line is a valid one  */
                    this.board[i][j]=player; /* Line is valid thus cell is now the player's */
                    this.board[row][col]=player; /* Essentially "flips" the other player's counters */
                    row = row - rowDir; /* -rowDir as we are "backtracking" through the line */
                    col = col - colDir; /* --colDir as we are "backtracking" through the line */
                }
                break;
            } else {
                row=row + rowDir; /* Otherwise we must continue to go along the line; hence the addition */
                col=col + colDir;
            } if (row<0 || col<0 || row==8 || col==8 || this.board[row][col]=='O') {
                break;
            }
        }

        return validLine;
    }
    private boolean gameOver()
    {


        int total=0;
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                if (this.board[i][j]=='B' || this.board[i][j]=='W') {
                    total++;
                }
            }
        }
        return(total==64);
    }

    private void stopGame() {

        int c = 0;

        for (int i=0; i < this.X; i++) {
            for (int j=0; j < this.Y; j++) {
                if (this.board[i][j]=='B')
                {
                    c++;
                }else if (this.board[i][j]=='W') {
                    c--;
                }
            }
        }

        if (c>0) {
            System.out.println("BLACK WINS");
        } else if (c<0) {
            System.out.println("WHITE WINS");
        } else {
            System.out.println("TIE");
        }

    }

    private boolean validMove(char player, int i, int j) {

        boolean valid = false;

        /* Invalid co-ordinates (i.e. these values don't even exist on the board */
        if(i==this.X || j == this.Y || i < 0 || j < 0) {
            return false; /* Hence the move is invalid */
        }

        char other_player = 'W';

        if(player=='B') {
            other_player='W';
        } else if (player=='W') {
            other_player='B';
        }

        if (this.board[i][j]=='O') { /* Only if the current cell is empty */

            if (i+1<8 && j+1<8 && board[i+1][j+1]==other_player && validLine(player,i,j,1,1)) {
                valid = true;
            } if (i+1<8 && board[i+1][j]==other_player && validLine(player, i, j, 1, 0)) {
                valid = true;
            } if(j+1<8 && board[i][j+1]==other_player && validLine(player,i,j,0,1)) {
                valid = true;
            } if (j-1>-1 && board[i][j-1]==other_player && validLine(player, i, j, 0, -1)) {
                valid = true;
            } if (i-1>-1 && j-1>-1 && board[i-1][j-1]==other_player && validLine(player,i,j,-1,-1)) {
                valid = true;
            } if (i-1>-1 && board[i-1][j]==other_player && validLine(player,i,j,-1,0)) {
                valid = true;
            } if(i-1>-1 && j+1<8 && board[i-1][j+1]==other_player && validLine(player,i,j,-1,1)) {
                valid = true;
            } if (i+1<8 && j-1>-1 && board[i+1][j-1]==other_player && validLine(player,i,j,1,-1)) {
                valid = true;
            }
        }

        return valid;
    }

    private boolean legalMove(char player, int i, int j ) {
        boolean valid = false;
        /* Invalid co-ordinates (i.e. these values don't even exist on the board */
        if(i==this.X || j == this.Y || i < 0 || j < 0) {
            return false; /* Hence the move is invalid */
        }

        char other_player = 'W';

        if(player=='B') {
            other_player='W';
        } else if (player=='W') {
            other_player='B';
        }

        if (this.board[i][j]=='O') { /* Only if the current cell is empty */

            if (i+1<8 && j+1<8 && board[i+1][j+1]==other_player && legalLine(player,i,j,1,1)) {
                valid = true;
            } if (i+1<8 && board[i+1][j]==other_player && legalLine(player, i, j, 1, 0)) {
                valid = true;
            } if(j+1<8 && board[i][j+1]==other_player && legalLine(player,i,j,0,1)) {
                valid = true;
            } if (j-1>-1 && board[i][j-1]==other_player && legalLine(player, i, j, 0, -1)) {
                valid = true;
            } if (i-1>-1 && j-1>-1 && board[i-1][j-1]==other_player && legalLine(player,i,j,-1,-1)) {
                valid = true;
            } if (i-1>-1 && board[i-1][j]==other_player && legalLine(player,i,j,-1,0)) {
                valid = true;
            } if(i-1>-1 && j+1<8 && board[i-1][j+1]==other_player && legalLine(player,i,j,-1,1)) {
                valid = true;
            } if (i+1<8 && j-1>-1 && board[i+1][j-1]==other_player && legalLine(player,i,j,1,-1)) {
                valid = true;
            }
        }

        return valid;
    }

    private boolean legalLine(char player, int i, int j, int i_dir, int j_dir) {
        return isLegalLine(i, j, player, i_dir, j_dir);

    }

    private boolean isLegalLine(int i, int j, char player, int rowDir, int colDir) {
        int counters = -1;
        move mv = new move();
        int row = i + rowDir;
        int col = j + colDir;

        if(this.board[row][col]=='O') {
            return false;
        } else if (row==8 || row<0 || col==8 || col<0) {
            return false;
        }

        while (this.board[row][col]=='B' || this.board[row][col]=='W') {

            if (this.board[row][col]==player) { /* If we are at the end of the line */
                while(!(i==row && j==col)) { /* This ensures that we backtrack through the line */
                    counters++;
                    row = row - rowDir; /* -rowDir as we are "backtracking" through the line */
                    col = col - colDir; /* --colDir as we are "backtracking" through the line */
                }
                if(!containsPos(i, j)) {
                    mv.setRow(i);
                    mv.setCol(j);
                    mv.setCounters(counters);
                    legalMoves.add(mv);
                }
                else if (containsPos(i, j)) {
                    int ind = getIndex(i, j);
                    int current_counters = legalMoves.get(ind).getCounters();
                    legalMoves.get(ind).setCounters(current_counters + counters);
                }
                return true;
            } else {
                row=row + rowDir; /* Otherwise we must continue to go along the line; hence the addition */
                col=col + colDir;
            } if (row<0 || col<0 || row==8 || col==8 || this.board[row][col]=='O') {
                break;
            }
        }

        return false;
    }

    private boolean containsPos(int row, int col) {
        for (move mv : legalMoves) {
            if (mv.getRow() == row && mv.getCol() == col) {
                return true;
            }
        }
        return false;
    }

    private int getIndex(int row, int col) {
        for (int i = 0; i < legalMoves.size(); i++) {
            if (legalMoves.get(i).getRow() == row && legalMoves.get(i).getCol() == col) {
                return i;
            }
        }
        return -1;
    }

    private void showBoard() {
        String dash;
        String pipeline;

        for (int i = 0; i < this.X; i++) { /* iterates through all rows */
            for (int j = 0; j < this.Y; j++) { /* iterates through all columns */
                if (j == (this.Y - 1)) {dash = "";} /* if the inner loop counter is on the last column then don't output a dash (-) */
                else {dash = " - ";} /* otherwise output a dash */

                if (board[i][j] == 'W') {System.out.print('W' + dash + RESET);} /* if the value of the current location represents the adventurer then output 'A' */
                else if (board[i][j]=='B') {System.out.print(BLUE + 'B' + dash + RESET);}
                else {System.out.print(GREEN + board[i][j] + dash + RESET);} /* remove this when deploying the program --only used for testing purposes */

            } /* end of inner loop */
            System.out.print("\n"); /* new line */
            if (i == (this.X - 1)) {pipeline = "";} /* if the outer loop counter is on the last row then don't output a pipeline */
            else {pipeline = "|   ";} /* otherwise output a pipeline */
            for (int z = 0; z < this.Y; z++) {System.out.print(pipeline);} /* outputs a pipeline beneath each element in the playing field */
            System.out.print("\n"); /* new line */
        } /* end of outer loop */
    }

    private void makeBoard() {
        for (int i = 0; i < this.X; i++) {
            for (int j = 0; j < this.Y; j++) {
                this.board[i][j] = 'O';
            } //inner loop fills each element in a row
        } //outer loop to iterate through each row

        makeInitialState();
    }

    private void makeInitialState() {

        this.board[3][3] = 'W';
        this.board[3][4] = 'B';
        this.board[4][3] = 'B';
        this.board[4][4] = 'W';
        
    }

    private void getLegalMoves(char player) {

        for (int i = 0; i < this.X; i++) {
            for (int j = 0; j < this.Y; j++) {
                if(legalMove(player, i, j)) {
                    continue;
                }
            }
        }
    }

    private void sortCounters() {

        for (int i = 0; i < legalMoves.size(); i++) {
            for (int j = 0; j < legalMoves.size() - 1; j++) {
                if (legalMoves.get(j).getCounters() < legalMoves.get(j+1).getCounters()) {
                    move temp = new move();
                    temp.setCounters(legalMoves.get(j).getCounters());
                    temp.setRow(legalMoves.get(j).getRow());
                    temp.setCol(legalMoves.get(j).getCol());
                    legalMoves.set(j, legalMoves.get(j+1));
                    legalMoves.set(j+1, temp);

                }
            }
        }

    }

    private int[] agent() {

        //agent is always black counter

        getLegalMoves('B');
        sortCounters();

        for (move mv : legalMoves) {
            System.out.println(mv.getRow() + "" + mv.getCol() + ": " + mv.getCounters());
        }

        int[] x = new int[2];

        x[0] = legalMoves.get(0).getRow();
        x[1] = legalMoves.get(0).getCol();

        legalMoves.clear();

        return x;



    }

    private int[] agent2() {
        //agent is always white counter

        getLegalMoves('W');
        sortCounters();

        for (move mv : legalMoves) {
            System.out.println(mv.getRow() + "" + mv.getCol() + ": " + mv.getCounters());
        }

        int[] x = new int[2];

        x[0] = legalMoves.get(0).getRow();
        x[1] = legalMoves.get(0).getCol();

        legalMoves.clear();

        return x;
    }


    public static void main(String[] args) {

       othello game = new othello();
        game.startGame();
    }
}
