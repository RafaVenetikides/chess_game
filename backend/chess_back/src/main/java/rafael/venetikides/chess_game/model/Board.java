package rafael.venetikides.chess_game.model;

public class Board {
    private Piece[][] board;

    public Board(){
        this.board = new Piece[8][8];
        initializeBoard();
    }

    public void initializeBoard(){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                board[i][j] = null;
            }
        }
        for(int i = 0; i < 8; i++){
            board[1][i] = new Piece(PieceType.PAWN, PieceColor.BLACK);
            board[6][i] = new Piece(PieceType.PAWN, PieceColor.WHITE);
        }
        board[0][0] = new Piece(PieceType.ROOK, PieceColor.BLACK);
        board[0][7] = new Piece(PieceType.ROOK, PieceColor.BLACK);
        board[7][0] = new Piece(PieceType.ROOK, PieceColor.WHITE);
        board[7][7] = new Piece(PieceType.ROOK, PieceColor.WHITE);
        board[0][1] = new Piece(PieceType.KNIGHT, PieceColor.BLACK);
        board[0][6] = new Piece(PieceType.KNIGHT, PieceColor.BLACK);
        board[7][1] = new Piece(PieceType.KNIGHT, PieceColor.WHITE);
        board[7][6] = new Piece(PieceType.KNIGHT, PieceColor.WHITE);
        board[0][2] = new Piece(PieceType.BISHOP, PieceColor.BLACK);
        board[0][5] = new Piece(PieceType.BISHOP, PieceColor.BLACK);
        board[7][2] = new Piece(PieceType.BISHOP, PieceColor.WHITE);
        board[7][5] = new Piece(PieceType.BISHOP, PieceColor.WHITE);
        board[0][3] = new Piece(PieceType.QUEEN, PieceColor.BLACK);
        board[7][3] = new Piece(PieceType.QUEEN, PieceColor.WHITE);
        board[0][4] = new Piece(PieceType.KING, PieceColor.BLACK);
        board[7][4] = new Piece(PieceType.KING, PieceColor.WHITE);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[i][j] == null){
                    sb.append(" . ");
                }else{
                    sb.append(" " + board[i][j] + " ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public Piece[][] getBoard() {
        return board;
    }

    public Piece getPieceAt(Position position){
        return board[position.getRow()][position.getColumn()];
    }
    
    public boolean movePiece(Position from, Position to){
        Piece piece = board[from.getRow()][from.getColumn()];
        if(piece != null && isMoveValid(piece, from, to)){
            board[to.getRow()][to.getColumn()] = piece;
            board[from.getRow()][from.getColumn()] = null;
            return true;
        }
        return false;
    }

    private boolean isMoveValid(Piece piece, Position from, Position to){
        if(piece.isMoveValid(from, to, this)){
            return true;
        }
        return false;
    }

}
