/**
 * Created by jamie on 01/04/17.
 */
public class board {

    private static final int ROW = 8;
    private static final int COL = 8;
    private char[][] b;
    private static final String BLUE = "\u001b[34m"; /* represents a blue coloured output */
    private static final String RESET = "\u001b[0m"; /* represents the default coloured output */
    private static final String GREEN = "\u001B[32m"; /* represents a red coloured output */

    board() {
        b = new char[ROW][COL];
        makeBoard();
        showBoard();
    }

    public int getRowNum() {
        return this.ROW;
    }

    public int getColNum() {
        return this.COL;
    }

    public void setCell(int i, int j, char val) {
        this.b[i][j] = val;
    }

    public boolean getCell(int i, int j, char val) {
        return (this.b[i][j]==val);
    }


    private void makeBoard() {
        for (int i = 0; i < this.ROW; i++) {
            for (int j = 0; j < this.COL; j++) {
                this.b[i][j] = 'O';
            } //inner loop fills each element in a row
        } //outer loop to iterate through each row

        makeInitialState();
    }

    private void makeInitialState() {

        this.b[3][3] = 'W';
        this.b[3][4] = 'B';
        this.b[4][3] = 'B';
        this.b[4][4] = 'W';

    }

    public void showBoard() {
        String dash;
        String pipeline;

        for (int i = 0; i < this.ROW; i++) { /* iterates through all rows */
            for (int j = 0; j < this.COL; j++) { /* iterates through all columns */
                if (j == (this.COL - 1)) {dash = "";} /* if the inner loop counter is on the last column then don't output a dash (-) */
                else {dash = " - ";} /* otherwise output a dash */

                if (this.b[i][j] == 'W') {System.out.print('W' + dash + RESET);} /* if the value of the current location represents the adventurer then output 'A' */
                else if (b[i][j]=='B') {System.out.print(BLUE + 'B' + dash + RESET);}
                else {System.out.print(GREEN + b[i][j] + dash + RESET);} /* remove this when deploying the program --only used for testing purposes */

            } /* end of inner loop */
            System.out.print("\n"); /* new line */
            if (i == (this.ROW - 1)) {pipeline = "";} /* if the outer loop counter is on the last row then don't output a pipeline */
            else {pipeline = "|   ";} /* otherwise output a pipeline */
            for (int z = 0; z < this.COL; z++) {System.out.print(pipeline);} /* outputs a pipeline beneath each element in the playing field */
            System.out.print("\n"); /* new line */
        } /* end of outer loop */
    }

}
