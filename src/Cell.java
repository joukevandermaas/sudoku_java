/*
 * Cell.java
 * 
 * Version information
 *
 * Date
 * 
 * Copyright notice
 */

import java.util.Collection;
import java.util.List;

public class Cell {
	private int value = 0;
	private List<Integer> possibilities;

	public Cell() {
		Integer[] possibilities = new Integer[Sudoku.SUDOKU_SIZE];
		for (int i = 0; i < possibilities.length; i++)
			possibilities[i] = i + 1;
		this.possibilities = new RemoveOnlyList<Integer>(possibilities);
	}

	public Cell(int value) throws SudokuException {
		possibilities = null;
		if (!isInRange(value))
			throw new SudokuException("Invalid input (" + value + ")");

		this.value = value;
	}

	private boolean isInRange(int value) {
		return value > 0 && value <= Sudoku.SUDOKU_SIZE;
	}

	public List<Integer> getPossibilities() {
		return possibilities;
	}

	public boolean hasValue() {
		return value != 0;
	}

	public boolean removePossibility(int value) throws SudokuException {
		if (hasValue())
			throw new SudokuException("This cell has a value");
		return possibilities.remove((Integer) value);
	}

	public boolean removeAllPossibilities(Collection<Integer> values)
			throws SudokuException {
		if (hasValue())
			throw new SudokuException("This cell has a value");
		return possibilities.removeAll(values);
	}

	public void setValue(int value) throws SudokuException {
		if (hasValue())
			throw new SudokuException("This cell already has a value");
		if (!isInRange(value))
			throw new SudokuException("Invalid value");

		this.value = value;
		possibilities = null;
	}

	public int getValue() throws SudokuException {
		if (!hasValue())
			throw new SudokuException("This cell has no value");
		return value;
	}

	public String toString() {
		if (hasValue())
			return Integer.toString(value);
		else
			return possibilities.toString();
	}

}
