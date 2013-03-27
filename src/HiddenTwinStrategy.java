/*
 * HiddenTwinStrategy.java
 * Strategy that searches for two numbers that can go in exactly two cells, if
 * these two cells are the same, all other possibilities should be removed.
 * 
 * Version information
 * v1
 *
 * Date
 * 27/03/2013
 * 
 * Author
 * Jouke van der Maas & Koen Keune
 * 
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class HiddenTwinStrategy implements Strategy {

	@Override
	public boolean removePossibilities(Sudoku puzzle) throws SudokuException,
			InvalidSudokuException {
		boolean result = false;

		for (CellContainer c : puzzle.getAllContainers()) {
			if (findHiddenTwins(c))
				result = true;
		}

		return result;
	}

	private boolean findHiddenTwins(CellContainer container)
			throws SudokuException, InvalidSudokuException {
		if (container.getValues().size() >= Sudoku.SUDOKU_SIZE - 2)
			return false;

		Hashtable<Integer, List<Cell>> cells = getPossibilityMap(container);
		List<Integer> done = new ArrayList<Integer>();
		boolean result = false;

		for (int i : cells.keySet()) {
			List<Cell> possibleCells = cells.get(i);
			done.add(i);
			for (int j : cells.keySet()) {
				if (done.contains(j))
					continue;
				List<Cell> otherCells = cells.get(j);
				if (possibleCells.containsAll(otherCells)) {
					if (removeOtherPossibilities(possibleCells, i, j))
						result = true;
				}
			}
		}

		return result;

	}

	private boolean removeOtherPossibilities(List<Cell> possibleCells, int n1,
			int n2) throws SudokuException, InvalidSudokuException {
		boolean result = false;

		for (Cell c : possibleCells) {
			if (c.removeOtherPossibilities(Arrays.asList(n1, n2)))
				result = true;
		}

		return result;
	}

	private Hashtable<Integer, List<Cell>> getPossibilityMap(
			CellContainer container) {
		Hashtable<Integer, List<Cell>> cells = new Hashtable<Integer, List<Cell>>();

		for (int i = 1; i <= Sudoku.SUDOKU_SIZE; i++) {
			cells.put(i, new ArrayList<Cell>());

			for (Cell c : container.getCells()) {
				if (!c.hasValue()) {
					if (c.getPossibilities().contains(i))
						cells.get(i).add(c);
				}
			}
			if (cells.get(i).size() != 2)
				cells.remove(i);
		}
		return cells;
	}

}
