package rafael.venetikides.chess_game.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rafael.venetikides.chess_game.dto.MoveRequest;
import rafael.venetikides.chess_game.model.Board;
import rafael.venetikides.chess_game.model.Piece;
import rafael.venetikides.chess_game.model.PieceType;
import rafael.venetikides.chess_game.model.Position;

@RestController
@RequestMapping("/api/chess")
@CrossOrigin(origins = "http://localhost:8081")

public class ChessController {
    
    private final Board board;
    private boolean isWhiteTurn;

    public ChessController(){
        this.board = new Board();
        this.isWhiteTurn = false;
    }

    @GetMapping("/board")
    public String[][] getBoard() {
        String[][] boardRepresentation = new String[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPieceAt(new Position(row, col));
                boardRepresentation[row][col] = (piece != null) ? piece.toString() : "";
            }
        }
        return boardRepresentation;
    }

    @PostMapping("/move")
    public ResponseEntity<String> movePiece(@RequestBody MoveRequest moveRequest){
        Position from = moveRequest.getFrom();
        Position to = moveRequest.getTo();

        Piece piece = board.getPieceAt(from);
        if (piece == null) {
            return ResponseEntity.badRequest().body("No piece at position");
        }

        if (from.equals(to)) {
            return ResponseEntity.badRequest().body("Invalid move: Source and destination are the same");
        }

        boolean isWhitePiece = Character.isUpperCase(piece.toString().charAt(0));
        if ((isWhiteTurn  && !isWhitePiece) || (!isWhiteTurn && isWhitePiece)) {
            return ResponseEntity.badRequest().body("Not your turn");
        }

        boolean validMove = validateMove(from, to);
        
        if (validMove) {
            updateBoard(from, to);
            isWhiteTurn = !isWhiteTurn;
            return ResponseEntity.ok("Move successful");
        } else {
            return ResponseEntity.badRequest().body("Invalid move");
        }
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetBoard() {
        board.initializeBoard();
        isWhiteTurn = false;
        System.out.println(board);
        return ResponseEntity.ok("Board reset");
    }

    private boolean validateMove(Position from, Position to) {
        Piece piece = board.getPieceAt(from);
        if (piece == null) {
            return false;
        }

        if (piece.getType().equals(PieceType.KNIGHT)){
            return piece.isMoveValid(from, to, board);
        }

        if (isPathBlocked(from, to)) {
            return false;
        }

        return piece.isMoveValid(from, to, board);
    }

    private boolean isPathBlocked(Position from, Position to) {
        int rowDiff = to.getRow() - from.getRow();
        int colDiff = to.getCol() - from.getCol();

        if (from.getRow() == to.getRow()) {
            int startCol = Math.min(from.getCol(), to.getCol());
            int endCol = Math.max(from.getCol(), to.getCol());
            for (int col = startCol + 1; col < endCol; col++) {
                if (board.getPieceAt(new Position(from.getRow(), col)) != null) {
                    return true;  // Há uma peça bloqueando o caminho
                }
            }
        }
        // Movimento vertical (Torre ou Rainha)
        else if (from.getCol() == to.getCol()) {
            int startRow = Math.min(from.getRow(), to.getRow());
            int endRow = Math.max(from.getRow(), to.getRow());
            for (int row = startRow + 1; row < endRow; row++) {
                if (board.getPieceAt(new Position(row, from.getCol())) != null) {
                    return true;  // Há uma peça bloqueando o caminho
                }
            }
        }
        // Movimento diagonal (Bispo ou Rainha)
        else if (rowDiff == colDiff) {
            int rowDirection = (to.getRow() - from.getRow()) / rowDiff;
            int colDirection = (to.getCol() - from.getCol()) / colDiff;
            int row = from.getRow() + rowDirection;
            int col = from.getCol() + colDirection;
            while (row != to.getRow() && col != to.getCol()) {
                if (board.getPieceAt(new Position(row, col)) != null) {
                    return true;  // Há uma peça bloqueando o caminho
                }
                row += rowDirection;
                col += colDirection;
            }
        }
    
        return false;
    }

    private void updateBoard(Position from, Position to) {
        if (board.movePiece(from, to)){
            System.out.println(board);
        } else {
            System.out.println("Invalid move");
        }
    }
}
