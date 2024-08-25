import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import '../models/chess_board_model.dart';

class ChessBoard extends StatefulWidget {
  const ChessBoard({super.key});

  @override
  _ChessBoardState createState() => _ChessBoardState();
}

class _ChessBoardState extends State<ChessBoard> {
  ChessBoardModel? chessBoard;

  @override
  void initState() {
    super.initState();
    fetchBoard();
  }

  Future<void> fetchBoard() async {
    final response = await http.get(Uri.parse("http://localhost:8080/api/chess/board"));

    if(response.statusCode == 200){
      setState(() {
        chessBoard = ChessBoardModel.fromJson(jsonDecode(response.body));
      });
    } else {
      throw Exception("Failed to load chess board");
    }
  }

  @override
  Widget build(BuildContext context) {
    final double boardSize = MediaQuery.of(context).size.height * 0.8;

    return Scaffold(
      appBar: AppBar(
        title: const Text("Chess Game"),
      ),
      body: Center(
        child: chessBoard != null
        ? SizedBox(
          width: boardSize,
          height: boardSize,
          child: GridView.builder(
            itemCount: 64,
            gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(crossAxisCount: 8),
            itemBuilder: (context, index) {
              int row = index ~/ 8;
              int col = index % 8;
              String piece = chessBoard!.board[row][col];
              final isWhite = (row + col) % 2 == 0;
              return Container(
                color: isWhite ? Colors.white : Colors.black,
                child: Center(
                  child: Text(
                    piece.isNotEmpty ? piece : "",
                    style: TextStyle(
                      fontSize: 24,
                      color: isWhite ? Colors.black : Colors.white,
                    ),
                  )
                )
              );
            },
          ),
        )
        : const CircularProgressIndicator(),
      ),
    );
  }
}