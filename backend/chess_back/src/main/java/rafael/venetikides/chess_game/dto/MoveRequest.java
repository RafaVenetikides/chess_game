package rafael.venetikides.chess_game.dto;
import rafael.venetikides.chess_game.model.Position;

public class MoveRequest {
    private Position from;
    private Position to;

    public Position getFrom(){
        return from;
    }

    public void setFrom(Position from){
        this.from = from;
    }

    public Position getTo(){
        return to;
    }

    public void setTo(Position to){
        this.to = to;
    }
}
