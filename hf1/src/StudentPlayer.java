public class StudentPlayer extends Player{
    public StudentPlayer(int playerIndex, int[] boardSize, int nToConnect) {
        super(playerIndex, boardSize, nToConnect);
    }

    @Override
    public int step(Board board) {
        int[][] boardLocal = board.getState();
        /*
        1-x, 2-o, 0-emtpy
        
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 6; j++)
            System.out.print(boardLocal[i][j]);
            System.out.println();
        }
        */
        return 0;
    }
}
