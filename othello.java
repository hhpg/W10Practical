import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by jh306 on 31/03/17.
 */
public class othello {

    private board b; /* char array that stores the values of each cell of the play area */
    private ArrayList<move> legalMoves = new ArrayList<move>(); /* arraylist of move objects that stores legal moves */

    othello() {
        this.b = new board();
    }

    private void startGame() {

        char player = 'B'; /* It is black's turn first */
        System.out.println("Black's turn");
        String x = "";

        while (!this.gameOver()) { /* Game loop that ensures the game continues while it isn't over */

            if (player=='W') {
                int[] co_ords = agent2();
                if (legalMoves.size() > 0 ) {
                    this.validMove('W', co_ords[0], co_ords[1]);
                } else if (legalMoves.size() == 0) {
                    co_ords=agent();
                    if(legalMoves.size() > 0) {
                        System.out.println("You can't make a move... switching to other player");
                        legalMoves.clear();
                    } else {
                        this.stopGame();
                    }

                }
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
                if (legalMoves.size() > 0) {
                    this.validMove('B', co_ords[0], co_ords[1]);
                } else if (legalMoves.size() == 0) {
                    co_ords=agent2();
                    if (legalMoves.size() > 0) {
                        System.out.println("You can't make a move... switching to other player");
                        legalMoves.clear();
                    } else {
                        this.stopGame();
                    }
                    }
                }


            this.b.showBoard();

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

        if(this.b.getCell(row, col, 'O')) {
            return false;
        } else if (row==8 || row<0 || col==8 || col<0) {
            return false;
        }

        while (this.b.getCell(row, col, 'B') || this.b.getCell(row, col, 'W')) {

            if (this.b.getCell(row, col, player)) { /* If we are at the end of the line */
                validLine = true; /* The line is a valid one  */
                while(!(i==row && j==col)) { /* This ensures that we backtrack through the line */
                    this.b.setCell(i, j, player); /* Line is valid thus cell is now the player's */
                    this.b.setCell(row, col, player); /* Essentially "flips" the other player's counters */
                    row = row - rowDir; /* -rowDir as we are "backtracking" through the line */
                    col = col - colDir; /* --colDir as we are "backtracking" through the line */
                }
                break;
            } else {
                row=row + rowDir; /* Otherwise we must continue to go along the line; hence the addition */
                col=col + colDir;
            } if (row<0 || col<0 || row==8 || col==8 || this.b.getCell(row, col, 'O')) {
                break;
            }
        }

        return validLine;
    }
    private boolean gameOver() {
        int total=0;
        for (int i = 0; i < this.b.getRowNum(); i++) {
            for (int j = 0; j < this.b.getColNum(); j++) {
                if (this.b.getCell(i, j, 'B') || this.b.getCell(i, j, 'W')) {
                    total++;
                }
            }
        }
        return(total==64);
    }

    private void stopGame() {

        int c = 0;

        for (int i=0; i < this.b.getRowNum(); i++) {
            for (int j=0; j < this.b.getColNum(); j++) {
                if (this.b.getCell(i, j, 'B')) {

                    c++;
                } else if (this.b.getCell(i, j, 'W')){
                    c--;
                }
            }
        }

        if (c>0) {
            System.out.println("BLACK WINS");
            System.exit(0);
        } else if (c<0) {
            System.out.println("WHITE WINS");
            System.exit(0);

        } else {
            System.out.println("TIE");
            System.exit(0);

        }

    }

    private boolean validMove(char player, int i, int j) {

        boolean valid = false;

        /* Invalid co-ordinates (i.e. these values don't even exist on the board */
        if(i==this.b.getRowNum() || j == this.b.getColNum() || i < 0 || j < 0) {
            return false; /* Hence the move is invalid */
        }

        char other_player = 'W';

        if(player=='B') {
            other_player='W';
        } else if (player=='W') {
            other_player='B';
        }

        if (this.b.getCell(i, j, 'O')) { /* Only if the current cell is empty */

            if (i+1<8 && j+1<8 && this.b.getCell(i+1, j+1, other_player) && validLine(player,i,j,1,1)) {
                valid = true;
            } if (i+1<8 && this.b.getCell(i+1, j, other_player) && validLine(player, i, j, 1, 0)) {
                valid = true;
            } if(j+1<8 && this.b.getCell(i, j+1, other_player) && validLine(player,i,j,0,1)) {
                valid = true;
            } if (j-1>-1 && this.b.getCell(i, j-1, other_player) && validLine(player, i, j, 0, -1)) {
                valid = true;
            } if (i-1>-1 && j-1>-1 && this.b.getCell(i-1, j-1, other_player) && validLine(player,i,j,-1,-1)) {
                valid = true;
            } if (i-1>-1 && this.b.getCell(i-1, j, other_player) && validLine(player,i,j,-1,0)) {
                valid = true;
            } if(i-1>-1 && j+1<8 && this.b.getCell(i-1, j+1, other_player) && validLine(player,i,j,-1,1)) {
                valid = true;
            } if (i+1<8 && j-1>-1 && this.b.getCell(i+1, j-1, other_player) && validLine(player,i,j,1,-1)) {
                valid = true;
            }
        }

        return valid;
    }

    private boolean legalMove(char player, int i, int j ) {
        boolean valid = false;
        /* Invalid co-ordinates (i.e. these values don't even exist on the board */
        if(i==this.b.getRowNum() || j == this.b.getColNum() || i < 0 || j < 0) {
            return false; /* Hence the move is invalid */
        }

        char other_player = 'W';

        if(player=='B') {
            other_player='W';
        } else if (player=='W') {
            other_player='B';
        }

        if (this.b.getCell(i, j, 'O')) { /* Only if the current cell is empty */

            if (i+1<8 && j+1<8 && this.b.getCell(i+1, j+1, other_player) && legalLine(player,i,j,1,1)) {
                valid = true;
            } if (i+1<8 && this.b.getCell(i+1, j, other_player) && legalLine(player, i, j, 1, 0)) {
                valid = true;
            } if(j+1<8 && this.b.getCell(i, j+1, other_player) && legalLine(player,i,j,0,1)) {
                valid = true;
            } if (j-1>-1 && this.b.getCell(i, j-1, other_player) && legalLine(player, i, j, 0, -1)) {
                valid = true;
            } if (i-1>-1 && j-1>-1 && this.b.getCell(i-1, j-1, other_player) && legalLine(player,i,j,-1,-1)) {
                valid = true;
            } if (i-1>-1 && this.b.getCell(i-1, j, other_player) && legalLine(player,i,j,-1,0)) {
                valid = true;
            } if(i-1>-1 && j+1<8 && this.b.getCell(i-1, j+1, other_player) && legalLine(player,i,j,-1,1)) {
                valid = true;
            } if (i+1<8 && j-1>-1 && this.b.getCell(i+1, j-1, other_player) && legalLine(player,i,j,1,-1)) {
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

        if(this.b.getCell(row, col, 'O')) {
            return false;
        } else if (row==8 || row<0 || col==8 || col<0) {
            return false;
        }

        while (this.b.getCell(row, col, 'B') || this.b.getCell(row, col, 'W')) {

            if (this.b.getCell(row, col, player)) { /* If we are at the end of the line */
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
            } if (row<0 || col<0 || row==8 || col==8 || this.b.getCell(row, col, 'O')) {
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

    private void getLegalMoves(char player) {

        for (int i = 0; i < this.b.getRowNum(); i++) {
            for (int j = 0; j < this.b.getColNum(); j++) {
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
        if(legalMoves.size() > 0) {
            x[0] = legalMoves.get(0).getRow();
            x[1] = legalMoves.get(0).getCol();
        }

        return x;



    }

    private int[] agent2() {
        //agent is always white counter

        getLegalMoves('W');
       // sortCounters();

        for (move mv : legalMoves) {
            System.out.println(mv.getRow() + "" + mv.getCol() + ": " + mv.getCounters());
        }

        int[] x = new int[2];
        if(legalMoves.size() > 0) {
            x[0] = legalMoves.get(0).getRow();
            x[1] = legalMoves.get(0).getCol();
        }


        return x;
    }

    public static void main(String[] args) {

        othello game = new othello();
        game.startGame();
    }
}
