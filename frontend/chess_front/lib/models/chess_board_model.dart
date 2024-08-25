class ChessBoardModel {
  final List<List<String>> board;

  ChessBoardModel(this.board);

  factory ChessBoardModel.fromJson(List<dynamic> json) {
    return ChessBoardModel(
      json.map((row) => List<String>.from(row)).toList(),
    );
  }
}