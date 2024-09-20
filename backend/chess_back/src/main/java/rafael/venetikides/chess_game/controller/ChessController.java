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
        return piece.isMoveValid(from, to, board);
    }

    private void updateBoard(Position from, Position to) {
        if (board.movePiece(from, to)){
            System.out.println(board);
        } else {
            System.out.println("Invalid move");
        }
    }
}
