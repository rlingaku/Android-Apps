package com.game.rakeshbalan.connectfour;

import android.annotation.TargetApi;
import android.os.Build;

// Created by Rakesh Balan Lingakumar

public class Board {
	public final byte NOBODY = 0;
	public final byte PLAYER = 1;
	public final byte AI = 2;
	int height;
	int width;
	int winLength;
	byte[][] board;
	int[] columnCounts;

	public Board(int height, int width, int winLength) {
		this.height = height;
		this.width = width;
		this.winLength = winLength;

		this.board = new byte[width][height];
		this.columnCounts = new int[width];
	}

	public boolean isValidMove(int column) {
		return columnCounts[column] < height;
	}

	public boolean makeMovePlayer(int column) {
		return makeMove(column, true);
	}

	public boolean makeMoveAI(int column) {
		return makeMove(column, false);
	}

	public boolean undoMovePlayer(int column) {
		return undoMove(column, true);
	}

	public boolean undoMoveAI(int column) {
		return undoMove(column, false);
	}

	boolean makeMove(int column, boolean player) {
		if (columnCounts[column] < height) {
			byte sign = player ? PLAYER : AI;
			board[column][columnCounts[column]++] = sign;
			return true;
		}
		return false;
	}

	boolean undoMove(int column, boolean player) {
		if (columnCounts[column] > 0) {
			byte sign = player ? PLAYER : AI;
			if (board[column][columnCounts[column] - 1] == sign) {
				board[column][columnCounts[column] - 1] = NOBODY;
				columnCounts[column]--;
				return true;
			}
		}
		return false;
	}

	public int getWidth() {
		return width;
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		for (int x = 0; x < width; x++) {
			result.append((x + 1) + " ");
		}
		result.append(System.lineSeparator());
		for (int y = height - 1; y >= 0; y--) {
			for (int x = 0; x < width; x++) {
				if (board[x][y] == PLAYER) {
					result.append("X ");
				} else if (board[x][y] == AI) {
					result.append("O ");
				} else {
					result.append(". ");
				}
			}
			result.append(System.lineSeparator());
		}
		return result.toString();
	}

	public boolean hasWinner() {
		return getWinner() != NOBODY;
	}

	public byte getWinner() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y <= height - winLength; y++) {
				boolean playerWin = true;
				boolean aiWin = true;
				for (int o = 0; o < winLength; o++) {
					if (playerWin
							&& board[x][y + o] != PLAYER) {
						playerWin = false;
					}
					if (aiWin
							&& board[x][y + o] != AI) {
						aiWin = false;
					}
				}
				if (playerWin) {
					return PLAYER;
				} else if (aiWin) {
					return AI;
				}
			}
		}

		for (int y = 0; y < height; y++) {
			for (int x = 0; x <= width
					- winLength; x++) {
				boolean playerWin = true;
				boolean aiWin = true;
				for (int o = 0; o < winLength; o++) {
					if (playerWin && board[x + o][y] != PLAYER) {
						playerWin = false;
					}
					if (aiWin && board[x + o][y] != AI) {
						aiWin = false;
					}
				}
				if (playerWin) {
					return PLAYER;
				} else if (aiWin) {
					return AI;
				}
			}
		}

		for (int x = 0; x <= width - winLength; x++) {
			for (int y = 0; y <= height - winLength; y++) {
				boolean playerWin = true;
				boolean aiWin = true;
				for (int o = 0; o < winLength; o++) {
					if (playerWin && board[x + o][y + o] != PLAYER) {
						playerWin = false;
					}
					if (aiWin && board[x + o][y + o] != AI) {
						aiWin = false;
					}
				}
				if (playerWin) {
					return PLAYER;
				} else if (aiWin) {
					return AI;
				}
			}
		}

		for (int x = width - 1; x >= winLength - 1; x--) {
			for (int y = 0; y <= height - winLength; y++) {
				boolean playerWin = true;
				boolean aiWin = true;
				for (int o = 0; o < winLength; o++) {
					if (playerWin && board[x - o][y + o] != PLAYER) {
						playerWin = false;
					}
					if (aiWin && board[x - o][y + o] != AI) {
						aiWin = false;
					}
				}
				if (playerWin) {
					return PLAYER;
				} else if (aiWin) {
					return AI;
				}
			}
		}

		return NOBODY;
	}

	public boolean playerIsWinner() {
		return getWinner() == PLAYER;
	}

	public boolean isTie() {
		return isBoardFull() && getWinner() == NOBODY;
	}

	boolean isBoardFull() {
		boolean emptyColumnFound = false;
		for (int x = 0; x < width; x++) {
			if (columnCounts[x] < height) {
				emptyColumnFound = true;
				break;
			}
		}
		return !emptyColumnFound;
	}
}
