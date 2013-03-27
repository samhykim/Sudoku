import java.util.*;
import java.lang.*;

public class Sudoku {
    public SList[][] gameboard;
    public final static int HEIGHT = 9;
    public final static int WIDTH = 9;

    public Sudoku() {
        gameboard = new SList[HEIGHT][WIDTH];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                gameboard[y][x] = new SList();
                gameboard[y][x].insertFront(0);
            }
        }
    }

    public int[] getCluster(int x, int y) {            //gets middle coordinates of the subblock or cluster
        int[] coordinates = new int[2];
        if (x < 3 && y < 3) {
            coordinates[0] = 1;
            coordinates[1] = 1;
            return coordinates;
        }
        else if (x < 3 && y < 6) {
            coordinates[0] = 1;
            coordinates[1] = 4;
            return coordinates;
        }
        else if (x < 3 && y < 9) {
            coordinates[0] = 1;
            coordinates[1] = 7;
            return coordinates;
        }
        else if (x < 6 && y < 3) {
            coordinates[0] = 4;
            coordinates[1] = 1;
            return coordinates;
        }
        else if (x < 9 && y < 3) {
            coordinates[0] = 7;
            coordinates[1] = 1;
            return coordinates;
        }
        else if (x < 6 && y < 6) {
            coordinates[0] = 4;
            coordinates[1] = 4;
            return coordinates;
        }
        else if (x < 6 && y < 9) {
            coordinates[0] = 4;
            coordinates[1] = 7;
            return coordinates;
        }
        else if (x < 9 && y < 6) {
            coordinates[0] = 7;
            coordinates[1] = 4;
            return coordinates;
        }
        else {
            coordinates[0] = 7;
            coordinates[1] = 7;
            return coordinates;
        }

    }


    public boolean isValid(SList[][] board, int x, int y, int value) {
       if ((inRow(board, x, y, value) || inCol(board, x, y,  value) || inCluster(board, x, y, value))) {
            return false;
        }
        return true;
    }

    public boolean inRow(SList[][] board, int x, int y, int value) {
        for (int k = 0; k < 9; k++) {
            if (board[y][k].occupied && x != k) {
                if (board[y][k].front().item == value) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean inCol(SList[][] board, int x, int y, int value) {
        for (int k = 0; k < 9; k++) {
            if (board[k][x].occupied && y != k) {
                if (board[k][x].front().item == value) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean inCluster(SList[][] board, int x1, int y1, int value) {
        int[] mid = getCluster(x1, y1);
        for (int x = mid[0]-1; x <= mid[0]+1; x++) {
            for (int y = mid[1]-1; y <= mid[1]+1; y++) {
                if (board[y][x].occupied && !(x1 == x && y1 == y)) {
                    if (board[y][x].front().item == value) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean inCluster(SList[][] board, int k, int value) {
        int[] mid;
        switch (k) {
            case 1: mid = getCluster(1,1); break;
            case 2: mid = getCluster(4,1); break;
            case 3: mid = getCluster(7,1); break;
            case 4: mid = getCluster(1,4); break;
            case 5: mid = getCluster(4,4); break;
            case 6: mid = getCluster(7,4); break;
            case 7: mid = getCluster(1,7); break;
            case 8: mid = getCluster(4,7); break;
            default: mid = getCluster(7,7);
        }
        for (int x = mid[0]-1; x <= mid[0]+1; x++) {
            for (int y = mid[1]-1; y <= mid[1]+1; y++) {
                if (board[y][x].occupied && board[y][x].front().item == value) {

                        return true;

                }
            }
        }
        return false;
    }

    public int[] getRandCluster(SList[][] board, int k) {
        int[] mid;
        switch (k) {
            case 1: mid = getCluster(1,1); break;
            case 2: mid = getCluster(4,1); break;
            case 3: mid = getCluster(7,1); break;
            case 4: mid = getCluster(1,4); break;
            case 5: mid = getCluster(4,4); break;
            case 6: mid = getCluster(7,4); break;
            case 7: mid = getCluster(1,7); break;
            case 8: mid = getCluster(4,7); break;
            default: mid = getCluster(7,7);
        }
        for (int x = mid[0]-1; x <= mid[0]+1; x++) {
            for (int y = mid[1]-1; y <= mid[1]+1; y++) {
                if (!board[y][x].occupied && board[y][x].length() > 1) {
                    int[] coordinates = {x, y};
                    return coordinates;
                }
            }
        }
        return null;
    }

    public SList[][] copyBoard(SList[][] board) {
        SList[][] copy = new SList[HEIGHT][WIDTH];
        for (int x = 0; x < 9; x++) {
            for(int y = 0; y < 9; y++) {
                copy[y][x] = SList.copy(board[y][x]);
                if (board[y][x].occupied) {
                    copy[y][x].occupied = true;
                }
            }
        }

        return copy;
    }






}
