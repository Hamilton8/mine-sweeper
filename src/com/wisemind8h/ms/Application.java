package com.wisemind8h.ms;

import com.wisemind8h.ms.model.Board;

public class Application {
	public static void main(String[] args) {
		Board board = new Board(10, 10, 10);
		
		board.open(3, 3);
		board.open(3, 4);
		board.toggleMarking(4, 4);
		board.toggleMarking(4, 5);
		System.out.println(board);
	}
}
