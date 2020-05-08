package main;


public class Datas {
	
	// record last chess position, for judge win or not
	// [0] is horizon [1] is vertical
	private int[] lastChess = {-1,-1}; 
	
	// save the chess information
	// -1-empty 0-red 1-yellow
	public int[][] chessInfo;
	
	// chess order
	// 0-red 1-yellow
	public static int chessOrder = 0;
	
	// record  steps
	public static int steps = 0;
	
	public Datas() {
		// Initialize the chessBoard
		chessInfo = new int[Main.chessBoardHorizon][Main.chessBoardVertic];
		for (int i  = 0; i < Main.chessBoardHorizon; i++)
			for (int j = 0; j < Main.chessBoardVertic; j++)
				chessInfo[i][j] = -1;
	}
	
	// true-okay false-full
	public boolean updateChessInfo(int column) {
		int last = chessOrder;
		for (int i = Main.chessBoardVertic-1; i >= 0; i--) {
			// prevent out of bound
			try {
				if (chessInfo[column][i] == -1) {
					chessInfo[column][i] = chessOrder;
					chessOrder = Math.abs(chessOrder-1);
					lastChess[0] = column;
					lastChess[1] = i;
				}
			}catch (Exception e) {
				return false;
			}

			if (chessOrder != last) break;
		}
		
		// if chessOrder has not been changed, the line is full
		// therefore, return false
		if (chessOrder == last) return false;
		return true;
	}
	
	
	// -1-no winner 0-red win 1-yellow win
	public int checkWin() {
		// because chessOder has changed, we have to switch
		int judgeColor = Math.abs(chessOrder-1);
		int count = 1;
		
		// judge down
		if (Main.chessBoardVertic - lastChess[1] >= 4) {
			count = 1;
			for (int i = lastChess[1] + 1; i < Main.chessBoardVertic; i++) {
				if (chessInfo[lastChess[0]][i] == judgeColor)
					count++;
				else break;
			}
			if (count >= 4) return judgeColor;
		}
		
		// judge left
		if (lastChess[0] >= 3) {
			count = 1;
			for (int i = lastChess[0] - 1; i >= 0; i--) {
				if (chessInfo[i][lastChess[1]] == judgeColor)
					count++;
				else break;
			}
			if (count >= 4) return judgeColor;
		}
		
		// judge right
		if (Main.chessBoardHorizon - lastChess[0] >= 4) {
			count = 1;
			for (int i = lastChess[0] + 1; i < Main.chessBoardHorizon; i++) {
				if (chessInfo[i][lastChess[1]] == judgeColor)
					count++;
				else break;
			}
			if (count >= 4) return judgeColor;
		}
		
		// judge left-down
		if (Main.chessBoardVertic - lastChess[1] >= 4 && lastChess[0] >= 3) {
			count = 1;
			for (int i = lastChess[0] - 1, j = lastChess[1] + 1; i >= 0 && j < Main.chessBoardVertic ; i--, j++) {
				if (chessInfo[i][j] == judgeColor)
					count++;
				else break;
			}
			if (count >= 4) return judgeColor;
		}
		
		// judge right-down
		if (Main.chessBoardVertic - lastChess[1] >= 4 && Main.chessBoardHorizon - lastChess[0] >= 4) {
			for (int i = lastChess[0] + 1, j = lastChess[1] + 1; i < Main.chessBoardHorizon && j < Main.chessBoardVertic; i++, j++) {
				if (chessInfo[i][j] == judgeColor)
					count++;
				else break;
			}
			if (count >= 4) return judgeColor;
		}
		
		
		return -1;
	}
	
}
