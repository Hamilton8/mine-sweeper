package com.wisemind8h.ms;

import com.wisemind8h.ms.model.Board;
import com.wisemind8h.ms.view.ConsoleBoard;

public class Application {
	public static void main(String[] args) {
		Board board = new Board(6, 6, 8);
		
		 new ConsoleBoard(board);
	}
}
