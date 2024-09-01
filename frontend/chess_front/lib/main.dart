import 'package:flutter/material.dart';
import 'screens/chess_board.dart'; // Import the ChessBoard screen

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Chess Game',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.white),
        useMaterial3: true,
      ),
      home: const ChessBoard(), // Set ChessBoard as the home screen
    );
  }
}