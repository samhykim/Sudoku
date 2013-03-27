

public class Solver {
    private Sudoku game;

    public Solver() {
        game = new Sudoku();
    }

    public void init() {
        try {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("insert a 3 digit number: ");   //first digit is xcor, second digit is ycor, third digit is value
        String input;
        do {
        input = reader.readLine();
        if (input.length() != 3 && !input.equals("stop"))  {
            System.out.println("input is not a 3 digit number");
            System.exit(0);
        }
        if (!input.equals("stop")) {
            int val = Integer.parseInt(input);
            int xcor = val/100;
            val = val%100;
            int ycor = val/10;
            val = val%10;
            SList s = new SList();
            s.insertFront(val);
            game.gameboard[ycor][xcor] = s;
            game.gameboard[ycor][xcor].occupied = true;
        }
        }
        while (!input.equals("stop"));

        }
        catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(e);
            System.exit(0);
        }
    }



    public void drawBoard(SList[][] board) {
        for (int k = 1; k < 26; k++) {
            System.out.print("-");
        }
        System.out.println();
        for (int y = 0; y < 9; y++) {
            System.out.print("| ");
            for (int x = 0; x < 9; x++) {
                if (board[y][x].occupied) {
                    System.out.print(board[y][x].front().item + " ");
                }
                else {
                    System.out.print(0 + " ");
                }
                if (x%3 == 2) {
                    System.out.print("| ");
                }
            }
            if (y%3 == 2) {
                System.out.println();
                for (int z = 1; z < 26; z++) {
                    System.out.print("-");
                }
            }
            System.out.println();
        }
    }

    public void startSolve(SList[][] board) {
        boolean added;
        boolean contradiction;
        do {
            added = false;
            contradiction = false;
            for (int k = 1; k <= 9; k++) {
                for (int value = 1; value <= 9; value++) {

                    added = added || clusterIterator(board, k, value);
                }
            }
            if (isSolved(board)) {


                game.gameboard = game.copyBoard(board);
                System.out.println("success");
                return;
            }



        }

        while (added);

        Solve(board);



    }
    public boolean Solve(SList[][] board) {
       // drawBoard(board);
        if (!isValid(board)) {
            return false;
        }
        if (isSolved(board)) {
            game.gameboard = game.copyBoard(board);
            System.out.println("success");
            return true;
        }


        boolean solved = true;
        SList currlist = new SList();
        int[] randcoor = new int[2];

            loop:
            for (int k = 1; k <= 9; k++) {
                 randcoor = game.getRandCluster(board, k);
                 if (randcoor != null) {
                     currlist = board[randcoor[1]][randcoor[0]];
                        break loop;
                 }

            }
            SListNode curr = currlist.head;
            while (curr != null) {
                if (curr.item != 0) {
                    int temp = curr.item;
                    SListNode copycurr = curr.next;

                    currlist.removeItem(curr.item);
                    currlist.insertFront(temp);
                    currlist.occupied = true;       //set it temporarily
                    SList[][] copy = game.copyBoard(board);
                    removeSameVal(copy, randcoor[0], randcoor[1], curr.item);

                    boolean next = Solve(copy);
                    solved = solved && next;
                    if (next == true) {
                        break;
                    }
                    for (int x = 0; x < 9; x++) {
                        for (int y = 0; y < 9; y++) {
                            if (!board[y][x].occupied && (board[y][x].length() == 2)) {
                                board[y][x].occupied = true;
                            }
                        }
                    }
                    curr = copycurr;
                }
                else {
                    curr = curr.next;
                }
            }
        return solved;

    }

    public boolean isSolved(SList[][] board) {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (!board[y][x].occupied || (board[y][x].front().item == 0)) {
                    return false;
                }
            }
        }
        return true;

    }

    public boolean isValid(SList[][] board) {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (board[y][x].occupied && !game.isValid(board, x, y, board[y][x].front().item)) {
                    return false;
                }
            }
        }
        return true;
    }


    public void removeSameVal(SList[][] board, int x1, int y1, int value) {
        int[] mid = game.getCluster(x1, y1);
        for (int x = mid[0]-1; x <= mid[0]+1; x++) {                  //remove same values in cluster
            for (int y = mid[1]-1; y <= mid[1]+1; y++) {
                    if (!board[y][x].occupied && board[y][x].contains(value)) {
                        board[y][x].removeItem(value);
                    }
                }
            }

        for (int k = 0; k < 9; k++) {                                            //remove same values in same row
            if (!board[k][x1].occupied && board[k][x1].contains(value)) {

                    board[k][x1].removeItem(value);
                }
            }
        for (int k = 0; k < 9; k++) {                                            //remove same values in same row
            if (!board[y1][k].occupied && board[y1][k].contains(value)) {
                board[y1][k].removeItem(value);
            }
        }
    }


    public boolean clusterIterator(SList[][] board, int k, int value) {  //generates all possible values a cell can have
        int possibleposition = 0;
        int xpos = 0;
        int ypos = 0;
        if (!game.inCluster(board, k, value)) {

            int[] mid;
            switch (k) {
                case 1: mid = game.getCluster(1,1); break;
                case 2: mid = game.getCluster(4,1); break;
                case 3: mid = game.getCluster(7,1); break;
                case 4: mid = game.getCluster(1,4); break;
                case 5: mid = game.getCluster(4,4); break;
                case 6: mid = game.getCluster(7,4); break;
                case 7: mid = game.getCluster(1,7); break;
                case 8: mid = game.getCluster(4,7); break;
                default: mid = game.getCluster(7,7);
            }
            for (int x = mid[0]-1; x <= mid[0]+1; x++) {
                for (int y = mid[1]-1; y <= mid[1]+1; y++) {
                    if (!board[y][x].occupied) {
                        if (!board[y][x].contains(value))  {

                            if (game.isValid(board, x, y, value)) {

                                board[y][x].insertFront(value);
                                possibleposition++;
                                xpos = x;
                                ypos = y;
                            }


                        }
                    }
                }
            }

        if (possibleposition == 1) {
            board[ypos][xpos].occupied = true;
            return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Solver s = new Solver();
        s.init();
        s.drawBoard(s.game.gameboard);
        s.startSolve(s.game.gameboard);
        s.drawBoard(s.game.gameboard);

    }
}
