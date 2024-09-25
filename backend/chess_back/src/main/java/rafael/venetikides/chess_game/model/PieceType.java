package rafael.venetikides.chess_game.model;

public enum PieceType {
    KING {
        @Override
        public boolean isMoveValid(Position from, Position to, Board board) {
            return Math.abs(from.getRow() - to.getRow()) <= 1 && Math.abs(from.getCol() - to.getCol()) <= 1;
        }

        @Override
        public String toString() {
            return "K";
        }
    },
    QUEEN{
        @Override
        public boolean isMoveValid(Position from, Position to, Board board) {
            return from.getRow() == to.getRow() || from.getCol() == to.getCol() || Math.abs(from.getRow() - to.getRow()) == Math.abs(from.getCol() - to.getCol());
        }

        @Override
        public String toString() {
            return "Q";
        }
    },
    ROOK{
        @Override
        public boolean isMoveValid(Position from, Position to, Board board) {
            return from.getRow() == to.getRow() || from.getCol() == to.getCol();
        }

        @Override
        public String toString() {
            return "R";
        }
    },
    BISHOP{
        @Override
        public boolean isMoveValid(Position from, Position to, Board board) {
            return Math.abs(from.getRow() - to.getRow()) == Math.abs(from.getCol() - to.getCol());
        }

        @Override
        public String toString() {
            return "B";
        }
    },
    KNIGHT{
        @Override
        public boolean isMoveValid(Position from, Position to, Board board) {
            return (Math.abs(from.getRow() - to.getRow()) == 2 && Math.abs(from.getCol() - to.getCol()) == 1) || (Math.abs(from.getRow() - to.getRow()) == 1 && Math.abs(from.getCol() - to.getCol()) == 2);
        }

        @Override
        public String toString() {
            return "N";
        }
    },
    PAWN{
        @Override
        public boolean isMoveValid(Position from, Position to, Board board) {
            return from.getCol() == to.getCol() && Math.abs(from.getRow() - to.getRow()) == 1;
        }

        @Override
        public String toString() {
            return "P";
        }
    };

    public abstract boolean isMoveValid(Position from, Position to, Board board);

    public abstract String toString();
}

