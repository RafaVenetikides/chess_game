package rafael.venetikides.chess_game.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rafael.venetikides.chess_game.model.Board;
import rafael.venetikides.chess_game.model.Piece;

@RestController
@RequestMapping("/api/chess")
@CrossOrigin(origins = "http://localhost:8081")

public class ChessController {
    
    private final Board board;

    public ChessController(){
        this.board = new Board();
    }

    @GetMapping("/board")
    public String[][] getBoard() {
        String[][] boardRepresentation = new String[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPieceAt(row, col);
                boardRepresentation[row][col] = (piece != null) ? piece.toString() : "";
            }
        }
        return boardRepresentation;
    }
}
