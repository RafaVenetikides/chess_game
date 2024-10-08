package rafael.venetikides.chess_game.model;

public class Piece {
    private PieceType type;
    private PieceColor color;

    public Piece(PieceType type, PieceColor color){
        this.type = type;
        this.color = color;
    }

    public PieceType getType() {
        return type;
    }

    public PieceColor getColor() {
        return color;
    }

    public boolean isMoveValid(Position from, Position to, Board board){
        return type.isMoveValid(from, to, board);
    }

    public String toString(){
        if (color == PieceColor.WHITE){
            return type.toString().toUpperCase();
        } else {
            return type.toString().toLowerCase();
        }
    }
}
