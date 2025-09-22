package com.wisemind8h.ms;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

import com.wisemind8h.ms.exception.ExplosionException;
import com.wisemind8h.ms.model.Field;

class Test {

private Field field;

@BeforeEach
void startField() {

	field = new Field(3, 3);

}

@org.junit.jupiter.api.Test
void realNeighbourTest() {
	
	Field neigh = new Field(2, 4);
	boolean result = field.addNeighbours(neigh);
	
	assertTrue(result);
}

@org.junit.jupiter.api.Test
void notNeighbourTest() {
	Field neigh = new Field(5, 4);
	boolean result = field.addNeighbours(neigh);
	
	assertFalse(result);
}

@org.junit.jupiter.api.Test
void toggleMarkingDefaultTest() {
	assertFalse(field.isMarked());
}

@org.junit.jupiter.api.Test
void toggleMarkingTest() {
	field.toggleMarking();
	assertTrue(field.isMarked());
}

@org.junit.jupiter.api.Test
void toggleMarking2CallsTest() {
	field.toggleMarking();
	field.toggleMarking();
	assertFalse(field.isMarked());
}

@org.junit.jupiter.api.Test
void openTest() {
	assertTrue(field.open());
}

@org.junit.jupiter.api.Test
void openNotMinedMarkedTest() {
	field.toggleMarking();
	assertFalse(field.open());
}

@org.junit.jupiter.api.Test
void openMinedMarkedTest() {
	field.mine();
	field.toggleMarking();
	assertFalse(field.open());
}

@org.junit.jupiter.api.Test
void openMinedNotMarkedTest() {
	field.mine();
	assertThrows(ExplosionException.class, () -> {
		field.open();
	});
}

@org.junit.jupiter.api.Test
void openWithNeighTest() {
	Field neigh1 = new Field(2, 2);
	Field neigh2 = new Field(2, 3);
	//Field neigh3 = new Field(2, 4);
	
	neigh1.addNeighbours(neigh2);
	
	field.addNeighbours(neigh1);
	field.open();
	assertTrue(neigh1.isOpen() && neigh2.isOpen());
}
	
	
}
