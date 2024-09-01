import 'package:flutter/material.dart';
import '../models/chess_board_model.dart';

class DraggableChessPiece extends StatelessWidget {
  // This widget will be used to create draggable chess pieces.
  
  final String piece;
  final int row;
  final int col;
  final Function(int, int) onDragStarted;
  final Widget Function(String piece) buildPiece;

  const DraggableChessPiece({
    Key? key,
    required this.piece,
    required this.row,
    required this.col,
    required this.onDragStarted,
    required this.buildPiece,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Draggable<Map<String, int>>(
      data: {'row': row, 'col': col},
      feedback: Material(
        color: Colors.transparent,
        child: buildPiece(piece),
      ),
      childWhenDragging: Container(),  // This will make the piece disappear from its original position while dragging.
      onDragStarted: () {
        onDragStarted(row, col);
      },
      child: buildPiece(piece),
    );
  }
}