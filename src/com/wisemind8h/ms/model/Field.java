package com.wisemind8h.ms.model;

import java.util.ArrayList;
import java.util.List;

import com.wisemind8h.ms.exception.ExplosionException;

public class Field {
	private int row;
	private int column;
	
	private boolean open;
	private boolean mined;
	private boolean marked;
	
	private List<Field> neighbours = new ArrayList<Field>();
	
	public Field(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public boolean addNeighbours(Field neigh) {
		
		boolean diffX= row != neigh.row;
		boolean diffY = column != neigh.column;
		boolean diagonal = diffX && diffY;
		
		int dx = Math.abs(row-neigh.row);
		int dy = Math.abs(column-neigh.column);
		int dxy = dx+dy;
		
		if(dxy == 1 && !diagonal) {
			this.neighbours.add(neigh);
			return true;
		}
		
		if(dxy == 2 && diagonal) {
			this.neighbours.add(neigh);
			return true;
		}
		
		return false;
	}
	
	public void toggleMarking() { 
		if(!open) {
			marked = !marked;
		}
	}
	
	public boolean open() {
		if(!open && !marked) {
			this.open = true;
			
			if(mined) {
				throw new ExplosionException("");
			}
			
			if(secureNeighbourhood()) {
				this.neighbours.forEach(n -> n.open());
			}
			return true;
		}
		return false;
	}
	private boolean secureNeighbourhood() {
		return this.neighbours.stream().noneMatch(n -> n.mined);
	}

	public void mine() {
		if(!mined) {
			mined = true;
		}
	}
	

	
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
	public boolean isOpen() {
		return open;
	}
	public boolean isMined() {
		return mined;
	}
	public boolean isMarked() {
		return marked;
	}
	
	boolean goalReached() {
		boolean unveiled = open && !mined;
		boolean protectedd = marked && mined;
		return unveiled || protectedd;
	}
	
	long minesAround() {
		return this.neighbours.stream().filter(n -> n.mined).count();
	}
	
	void restart() {
		open = false;
		marked = false;
		mined = false;
	}
	
	public String toString() {
		if(marked) {
			return "x";
		}else if(open && mined) {
			return "*";
		}else if(open && minesAround()>0) {
			return Long.toString(minesAround());
		}else if(open) {
			return " ";
		}else {
			return "?";
		}
	}
	
}
