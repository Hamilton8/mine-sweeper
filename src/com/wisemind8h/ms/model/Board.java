package com.wisemind8h.ms.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import com.wisemind8h.ms.exception.ExplosionException;

public class Board {
	private int rows;
	private int columns;
	private int mines;
	
	private List<Field> fields = new ArrayList<>();
	
	public Board(int rows, int columns, int mines) {
		this.rows = rows;
		this.columns = columns;
		this.mines = mines;
		
		generateFields();
		associateNeighbours();
		spreadMines();
	}
	
	public void open(int row, int column) {
		try {
			fields.parallelStream()
			.filter(f -> f.getRow()==row && f.getColumn()==column)
			.findFirst()
			.ifPresent(f -> f.open());
		}catch(ExplosionException e) {
			fields.forEach(n->n.setOpen(true));
			throw e;
		}
	}
	
	public void toggleMarking(int row, int column) {
		fields.parallelStream()
		.filter(f -> f.getRow()==row && f.getColumn()==column)
		.findFirst()
		.ifPresent(f -> f.toggleMarking());
	}

	private void generateFields() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				this.fields.add(new Field(i, j));
			}
		}
	}
	
	private void associateNeighbours() {
		for(Field f1: fields) {
			for(Field f2: fields) {
				f1.addNeighbours(f2);
			}
		}
	}

	private void spreadMines() {
		long armedMines = 0;
		Predicate<Field> mined = n -> n.isMined();
		
		while(armedMines< mines){
			armedMines = fields.stream().filter(mined).count();
			int random = (int) (Math.random()*fields.size());
			fields.get(random).mine();
		}
	}
	
	public boolean goalReached() {
		return fields.stream().allMatch(f -> f.goalReached());
	}
	
	public void restart() {
		fields.stream().forEach(n -> n.restart());
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int n = 0;
		
		sb.append("  ");
		for (int c = 0; c<columns; c++) {
			sb.append(" ");
			sb.append(c);
			sb.append(" ");
		}
		sb.append("\n");
		for (int i = 0; i < rows; i++) {
			sb.append(" ");
			sb.append(i);
			for (int j = 0; j <columns; j++) {
				sb.append(" ");
				sb.append(fields.get(n));
				sb.append(" ");
				n++;
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
