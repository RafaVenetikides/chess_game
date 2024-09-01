import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import '../models/chess_board_model.dart';
import '../widgets/draggable_chess_piece.dart';

class ChessBoard extends StatefulWidget {
  const ChessBoard({super.key});

  @override
  _ChessBoardState createState() => _ChessBoardState();
}

class _ChessBoardState extends State<ChessBoard> {
  List<List<String>>? chessBoard;

  String? draggingPiece;
  int? draggingPieceRow;
  int? draggingPieceCol;

  @override
  void initState() {
    super.initState();
    fetchBoard();
  }

  Future<void> fetchBoard() async {
    final response = await http.get(Uri.parse("http://localhost:8080/api/chess/board"));

    if(response.statusCode == 200){
      setState(() {
        chessBoard = List<List<String>>.from(
          jsonDecode(response.body).map(
            (row) => List<String>.from(row),),
          );
        });
    } else {
      throw Exception("Failed to load chess board");
    }
  }

  void handlePieceDropped(int startRow, int startCol, int endRow, int endCol) async{
    // TODO: Send piece to backend
    print('Moving piece from ($startRow, $startCol) to ($endRow, $endCol)');

    final moveData = jsonEncode({
      'fromRow': startRow,
      'fromCol': startCol,
      'toRow': endRow,
      'toCol': endCol,
    });

    final response = await http.post(
      Uri.parse("http://localhost:8080/api/chess/move"),
      headers: {"Content-Type": "application/json"},
      body: moveData,
    );

    if (response.statusCode == 200){
      setState(() {
        chessBoard![endRow][endCol] = chessBoard![startRow][startCol];
        chessBoard![startRow][startCol] = '';
      });
      print('Move successful sent to backend');
    } else{
      print('Failed to send move');
      throw Exception("Failed to send move");
    }

    print('Piece moved from ($startRow, $startCol) to ($endRow, $endCol)');
  }

@override
  Widget build(BuildContext context) {
    final double boardSize = MediaQuery.of(context).size.height * 0.8;

    // Chess UI
    return Scaffold(
      appBar: AppBar(
        title: const Text("Chess Game"),
      ),
      body: Center(
        child: chessBoard != null
            ? buildChessBoard(boardSize)
            : const CircularProgressIndicator(),
      ),
    );
  }

  Widget buildChessBoard(double boardSize) {
    // Build the chess board UI, a 8x8 grid

    return SizedBox(
      width: boardSize,
      height: boardSize,
      child: GridView.builder(
        itemCount: 64,
        gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
          crossAxisCount: 8,),
        itemBuilder: (context, index) {
          int row = index ~/ 8;
          int col = index % 8;
          String piece = chessBoard![row][col];
          return buildChessSquare(row, col, piece);
        },
      ),
    );
  }

  Widget buildChessSquare(int row, int col, String piece) {
    // Build a single square of the chess board, which may contain a piece

    final isWhite = (row + col) % 2 == 0;
    return DragTarget<Map<String, int>>(
      onAccept: (data) {
        handlePieceDropped(data['row']!, data['col']!, row, col);
      },
      builder: (context, candidateData, rejectedData) {
        return Container(
          color: isWhite ? const Color(0xFFEBECD0) : const Color(0xFF779556),
          child: Center(
                  child: piece.isNotEmpty
                  ? DraggableChessPiece(
                    piece: piece, 
                    row: row, 
                    col: col, 
                    onDragStarted: (startRow, startCol){
                      setState(() {
                        draggingPiece = piece;
                        draggingPieceRow = startRow;
                        draggingPieceCol = startCol;
                      });
                    },
                    buildPiece: buildPiece,
                  )
                : Container(),
          ),
        );
      },
    );
  }

  Widget buildPiece(String piece) {
    // Build the UI for a single chess piece, with outline and main piece layers

    final pieceSymbol = getPieceSymbol(piece);
    return Stack(
      children: [
        // Outline layer
        Text(
          pieceSymbol,
          style: TextStyle(
            fontSize: 32,
            foreground: Paint()
              ..style = PaintingStyle.stroke
              ..strokeWidth = 3
              ..color = piece == piece.toUpperCase() ? Colors.white : Colors.black,
          ),
        ),
        // Main piece layer
        Text(
          pieceSymbol,
          style: TextStyle(
            fontSize: 32,
            color: piece == piece.toUpperCase() ? Colors.black : Colors.white,
          ),
        ),
      ],
    );
  }


  String getPieceSymbol(String piece) {
    switch (piece.toLowerCase()){
      case 'k':
        return '\u265A';
      case 'q':
        return '\u265B';
      case 'r':
        return '\u265C';
      case 'b':
        return '\u265D';
      case 'n':
        return '\u265E';
      case 'p':
        return '\u265F';
      default:
        return '';
    }
  }
}