package com.wisemind8h.ms.view;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import com.wisemind8h.ms.exception.ExplosionException;
import com.wisemind8h.ms.exception.LeaveException;
import com.wisemind8h.ms.model.Board;

public class ConsoleBoard {
	private Board board;
	private Scanner input = new Scanner(System.in);
	
	public ConsoleBoard(Board board) {
		this.board = board;
		executeGame();
	}
	
	private void executeGame() {
		try {
			boolean continuing = true;
			
			while(continuing) {
				gameCycle();
				System.out.println("Again?(Y/n)");
				String answer = input.nextLine();
				
				if("n".equalsIgnoreCase(answer)) {
					continuing = false;
				}else {
					board.restart();
				}
			}
		}catch (LeaveException e) {
			System.out.println("Bye!");                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
		}finally {
			input.close(); 
		}
	}
	
	private void gameCycle() {
		try {
			while(!board.goalReached()) {
				System.out.println(board);
				
				String typed  = captureTypedValue("Type (x,y): ");
				Iterator<Integer> xy =Arrays
						.stream(typed.split(","))
						.map(v -> Integer.parseInt(v.trim())).iterator();
				
				typed = captureTypedValue("1 - Open or 2 - (un)mark: ");
				
				if("1".equalsIgnoreCase(typed)) {
					board.open(xy.next(), xy.next());
				}else if("2".equalsIgnoreCase(typed)) {
					board.toggleMarking(xy.next(), xy.next());
				}
			}
			System.out.println("You won!");
		}catch(ExplosionException e) {
			System.out.println(board);
			System.out.println("You lose!");
		}
	}
	 
	public String captureTypedValue(String text) {
		System.out.println(text);
		String typed = input.nextLine();
		
		if("leave".equalsIgnoreCase(typed)) {
			throw new LeaveException();
		}
		
		return typed;
	}
}
