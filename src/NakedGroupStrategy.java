/*
 * NakedGroupStrategy.java
 * Strategy that ...
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
import java.util.List;

public class NakedGroupStrategy implements Strategy {

	// When two cells have exactly two the same possibilities, remove those as
	// possibilities everywhere else.
	@Override
	public boolean removePossibilities(Sudoku puzzle) throws SudokuException,
			InvalidSudokuException {
		boolean result = false;

		for (CellContainer container : puzzle.getAllContainers()) {
			if (findNakedGroups(2, container)) {
				result = true;
			}
		}

		if (!result) {
			for (CellContainer container : puzzle.getAllContainers()) {
				if (findNakedGroups(3, container)) {
					result = true;
				}
			}
		}

		return result;
	}

	private boolean findNakedGroups(int n, CellContainer container)
			throws SudokuException, InvalidSudokuException {
		List<Cell> cells = findNPossibilityCells(container, n);
		if (cells.size() < n)
			return false;
		List<Cell> found = new ArrayList<Cell>();

		for (int i = cells.size() - 1; i >= 0; i--) {
			Cell c1 = cells.get(i);
			found.add(c1);

			for (int j = 0; j < cells.size(); j++) {
				if (j == i)
					continue;
				Cell c2 = cells.get(j);

				if (c1.getPossibilities().containsAll(c2.getPossibilities())) {
					found.add(c2);
				}
			}
			if (found.size() == n) {
				return removeFromOthers(container, found);
			}
			found.clear();
		}

		return false;
	}

	private boolean removeFromOthers(CellContainer container,
			List<Cell> preserve) throws SudokuException, InvalidSudokuException {
		boolean result = false;
		for (Cell c : container.getCells()) {
			if (!c.hasValue() && !preserve.contains(c)) {
				if (c.removeAllPossibilities(preserve.get(0).getPossibilities()))
					result = true;
			}
		}

		return result;
	}

	private List<Cell> findNPossibilityCells(CellContainer container, int n) {
		List<Cell> doubles = new ArrayList<Cell>();

		for (Cell c : container.getCells()) {
			if (!c.hasValue() && c.getPossibilities().size() == n)
				doubles.add(c);
		}

		return doubles;
	}

}
