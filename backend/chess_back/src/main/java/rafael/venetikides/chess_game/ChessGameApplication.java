package rafael.venetikides.chess_game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import rafael.venetikides.chess_game.model.Board;

@SpringBootApplication
public class ChessGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChessGameApplication.class, args);

		Board board = new Board();

		board.initializeBoard();

		System.out.println(board.toString());

		
	}

}
