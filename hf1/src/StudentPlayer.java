public class StudentPlayer extends Player{
    public StudentPlayer(int playerIndex, int[] boardSize, int nToConnect) {
        super(playerIndex, boardSize, nToConnect);
    }

    @Override
    public int step(Board board) {        
        int bestColumn = (int)(Math.random() * 7);
        //3System.out.print("Control: ");
        int bestScore = score(board);

        for(int i = 0; i < 7; i++){ //each column
            Board bCopy = new Board(board);
            bCopy.step(2, i);
            int tempScore = score(bCopy);
            if(tempScore > bestScore){
                bestScore = tempScore;
                bestColumn = i;
            }
        }

        return bestColumn;
    }

    final int EMPTY = 0;
    final int PLAYER = 1;
    final int COMPUTER = 2;

    final int ROWS = 6;
    final int COLS = 7;

    private int score(Board b) {
        int[][] board = b.getState();
        int score = 0;

        //horizontal
        for(int y = 0; y < ROWS; y++){//each row
            for(int i = 0; i < 4; i++){//subsets of 4
                int pieceCount = 0;
                int emptyCount = 0;
                for(int j = 0; j < 4; j++){//individual items of a subset
                    int x = i + j;
                    if(board[y][x] == COMPUTER){
                        pieceCount++;
                    } else if(board[y][x] == EMPTY){
                        emptyCount++;
                    }
                }
                if(pieceCount == 4)
                    score += 100;
                    else if(pieceCount == 3 && emptyCount == 1)
                    score += 50;
                else if(pieceCount == 2 && emptyCount == 2)
                    score += 10;
            }
        }

        //vertical
        for(int x = 0; x < COLS; x++){//each col
            for(int i = 0; i < 3; i++){//subsets of 4
                int pieceCount = 0;
                int emptyCount = 0;
                for(int j = 0; j < 4; j++){//individual items of a subset
                    int y = i + j;
                    if(board[y][x] == COMPUTER){
                        pieceCount++;
                    } else if(board[y][x] == EMPTY){
                        emptyCount++;
                    }
                }
                if(pieceCount == 4)
                    score += 100;
                else if(pieceCount == 3 && emptyCount == 1)
                    score += 50;
                else if(pieceCount == 2 && emptyCount == 2)
                    score += 10;
            }
        }
        
        //4 diagonals possible in a row
        //3 diagonals possible in a column
        //Left leaning diagonals
        for(int y = 0; y < 3; y++){
            for(int x = 0; x < 4; x++){
                int pieceCount = 0;
                int emptyCount = 0;

                for(int i = 0; i < 4; i++){
                    if(board[y + i][x + i] == COMPUTER){
                        pieceCount++;
                    } else if(board[y + i][x + i] == EMPTY){
                        emptyCount++;
                    }
                }

                if(pieceCount == 4)
                    score += 100;
                else if(pieceCount == 3 && emptyCount == 1)
                    score += 50;
                else if(pieceCount == 2 && emptyCount == 2)
                    score += 10;
            }
        }

        //Right leaning diagonals
        for(int y = 0; y < 3; y++){
            for(int x = 6; x >= 3; x--){
                int pieceCount = 0;
                int emptyCount = 0;

                for(int i = 0; i < 4; i++){
                    if(board[y + i][x - i] == COMPUTER){
                        pieceCount++;
                    } else if(board[y + i][x - i] == EMPTY){
                        emptyCount++;
                    }
                }

                if(pieceCount == 4)
                    score += 100;
                else if(pieceCount == 3 && emptyCount == 1)
                    score += 50;
                else if(pieceCount == 2 && emptyCount == 2)
                    score += 10;
            }
        }

        //System.out.println(score);
        return score;
    }
}
