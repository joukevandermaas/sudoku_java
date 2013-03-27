import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Represents a cell within the sudoku. Each cell is in one of two possible states:
 * 1. The cell has a value
 * 2. The cell has a set of possible values.
 * 
 * In state 1, the Cell object is immutable; it cannot be changed to state 2 and
 * the value is fixed. In state 2, possibilities can be removed (but not added) and
 * the state can be changed to state 1.
 * 
 * @version 1.0
 * @author Jouke van der Maas & Koen Keune
 * 
 */
public class Cell {
	private int value = 0;
	private List<Integer> possibilities;
	private int row, col, sqr;

	public Cell(int row, int column, int square) {
		Integer[] possibilities = new Integer[Sudoku.SUDOKU_SIZE];
		for (int i = 0; i < possibilities.length; i++)
			possibilities[i] = i + 1;
		
		this.possibilities = new RemoveOnlyList<Integer>(possibilities);
		this.row = row;
		this.col = column;
		this.sqr = square;
	}

	public Cell(int row, int column, int square, int value) throws SudokuException {
		possibilities = null;
		if (!isInRange(value))
			throw new SudokuException("Invalid input (" + value + ")");

		this.row = row;
		this.col = column;
		this.sqr = square;
		this.value = value;
	}
	
	private boolean isInRange(int value) {
		return value > 0 && value <= Sudoku.SUDOKU_SIZE;
	}

	public int getRow() { return row; }
	public int getColumn() { return col; }
	public int getSquare() { return sqr; }
	
	public List<Integer> getPossibilities() {
		return possibilities;
	}

	public boolean hasValue() {
		return value != 0;
	}

	public boolean removePossibility(int value) throws SudokuException, InvalidSudokuException {
		if (hasValue())
			throw new SudokuException("This cell has a value");
		
		boolean result = possibilities.remove((Integer) value); 
		if(possibilities.isEmpty())
			throw new InvalidSudokuException("Cell cannot have an empty set of possible values.");
		
		return result;
	}

	public boolean removeAllPossibilities(Collection<Integer> values)
			throws SudokuException, InvalidSudokuException {
		if (hasValue())
			throw new SudokuException("This cell has a value");
		
		boolean result = possibilities.removeAll(values);
		if(possibilities.isEmpty())
			throw new InvalidSudokuException("Cell cannot have an empty set of possible values.");
		
		return result;
	}

	public boolean removeOtherPossibilities(int value) throws SudokuException, InvalidSudokuException {
		return removeOtherPossibilities(Arrays.asList(value));
	}
	
	public boolean removeOtherPossibilities(Collection<Integer> values) {
		return possibilities.retainAll(values);
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
