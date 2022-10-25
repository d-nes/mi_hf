public class StudentPlayer extends Player{
    public StudentPlayer(int playerIndex, int[] boardSize, int nToConnect) {
        super(playerIndex, boardSize, nToConnect);
    }

    final int EMPTY = 0;
    final int PLAYER = 1;
    final int COMPUTER = 2;

    final int ROWS = 6;
    final int COLS = 7;

    @Override
    public int step(Board board) {        
        int bestColumn = (int)(Math.random() * 7);
        //int bestScore = minimax(board, 5, true);
        int bestScore = score(board);
        
        for(int i = 0; i < 7; i++){ //each column
            Board bCopy = new Board(board);
            bCopy.step(2, i);
            //int tempScore = minimax(bCopy, 5, true);
            int tempScore = score(bCopy);
            if(tempScore > bestScore){
                bestScore = tempScore;
                bestColumn = i;
            }
        }
        
        return bestColumn;
    }

    private int minimax(Board node, int depth, boolean maximizingPlayer){
        if(depth == 0 || node.gameEnded())
            return score(node);

        if (maximizingPlayer) {
            int value = Integer.MIN_VALUE;
            for(int i = 0; i < 7; i++){ //each column
                Board nCopy = new Board(node);
                nCopy.step(COMPUTER, i);
                int score = minimax(nCopy, depth - 1, false);
                if(score > value){
                    value = score;
                }

            }
            return value;
        }else {
            int value = Integer.MAX_VALUE;
            for(int i = 0; i < 7; i++){ //each column
                Board nCopy = new Board(node);
                nCopy.step(PLAYER, i);
                int score = minimax(nCopy, depth - 1, true);
                if(score < value){
                    value = score;
                }

            }
            return value;
        }
    }

    private int score(Board b) {
        int[][] board = b.getState();

        int score = 0;

        final int reward4 = 100;
        final int reward3 = 50;
        final int reward2 = 10;
        final int penalty = 80;


        //horizontal
        for(int y = 0; y < ROWS; y++){//each row
            for(int i = 0; i < 4; i++){//subsets of 4
                int pieceCount = 0;
                int enemyCount = 0;
                int emptyCount = 0;
                for(int j = 0; j < 4; j++){//individual items of a subset
                    int x = i + j;
                    if(board[y][x] == COMPUTER)
                        pieceCount++;
                    else if(board[y][x] == EMPTY)
                        emptyCount++;
                    else if(board[y][x] == PLAYER)
                        enemyCount++;
                }
                if(pieceCount == 4)
                    score += reward4;
                else if(pieceCount == 3 && emptyCount == 1)
                    score += reward3;
                else if(pieceCount == 2 && emptyCount == 2)
                    score += reward2;
                else if(enemyCount == 3 && emptyCount == 1)
                    score -= penalty;
            }
        }

        //vertical
        for(int x = 0; x < COLS; x++){//each col
            for(int i = 0; i < 3; i++){//subsets of 4
                int pieceCount = 0;
                int enemyCount = 0;
                int emptyCount = 0;
                for(int j = 0; j < 4; j++){//individual items of a subset
                    int y = i + j;
                    if(board[y][x] == COMPUTER)
                        pieceCount++;
                    else if(board[y][x] == EMPTY)
                        emptyCount++;
                    else if(board[y][x] == PLAYER)
                        enemyCount++;
                }
                if(pieceCount == 4)
                    score += reward4;
                else if(pieceCount == 3 && emptyCount == 1)
                    score += reward3;
                else if(pieceCount == 2 && emptyCount == 2)
                    score += reward2;
                else if(enemyCount == 3 && emptyCount == 1)
                    score -= penalty;
            }
        }
        
        //4 diagonals possible in a row
        //3 diagonals possible in a column
        //Left leaning diagonals
        for(int y = 0; y < 3; y++){
            for(int x = 0; x < 4; x++){
                int pieceCount = 0;
                int enemyCount = 0;
                int emptyCount = 0;

                for(int i = 0; i < 4; i++){
                    if(board[y + i][x + i] == COMPUTER)
                        pieceCount++;
                    else if(board[y + i][x + i] == EMPTY)
                        emptyCount++;
                    else if(board[y][x] == PLAYER)
                        enemyCount++;
                }

                if(pieceCount == 4)
                    score += reward4;
                else if(pieceCount == 3 && emptyCount == 1)
                    score += reward3;
                else if(pieceCount == 2 && emptyCount == 2)
                    score += reward2;
                else if(enemyCount == 3 && emptyCount == 1)
                    score -= penalty;
            }
        }

        //Right leaning diagonals
        for(int y = 0; y < 3; y++){
            for(int x = 6; x >= 3; x--){
                int pieceCount = 0;
                int enemyCount = 0;
                int emptyCount = 0;

                for(int i = 0; i < 4; i++){
                    if(board[y + i][x - i] == COMPUTER)
                        pieceCount++;
                    else if(board[y + i][x - i] == EMPTY)
                        emptyCount++;
                    else if(board[y][x] == PLAYER)
                        enemyCount++;
                }

                if(pieceCount == 4)
                    score += reward4;
                else if(pieceCount == 3 && emptyCount == 1)
                    score += reward3;
                else if(pieceCount == 2 && emptyCount == 2)
                    score += reward2;
                else if(enemyCount == 3 && emptyCount == 1)
                    score -= penalty;
            }
        }

        //System.out.println(score);
        return score;
    }
}
