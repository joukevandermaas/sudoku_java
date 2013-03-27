import java.util.ArrayList;
import java.util.List;

/**
 * Strategy that removes finds groups of cells that contain the same possibilities,
 * and no others. For example two cells that can only hold a 3 or a 4. These possibilities
 * can be removed from all other cells in the container. The strategy only looks for groups
 * of 2 and 3 cells.
 * 
 * @version 1.0
 * @author Jouke van der Maas & Koen Keune
 * 
 */
public class NakedGroupStrategy implements Strategy {

	@Override
	public boolean removePossibilities(Sudoku puzzle) throws SudokuException,
			InvalidSudokuException {
		boolean result = false;

		for (CellContainer container : puzzle.getAllContainers()) {
			if (findNakedGroups(2, container)) {
				result = true;
			}
		}
		
		// only do 3 cells if no result from doing 2
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
		// Each cell with the same possibilities that is found
		// will be added to this list. The number of elements in the
		// list determines if we've found a group.
		List<Cell> found = new ArrayList<Cell>();

		for (int i = cells.size() - 1; i >= 0; i--) {
			Cell c1 = cells.get(i);
			found.add(c1);

			for (int j = 0; j < cells.size(); j++) {
				if (j == i)
					continue;
				Cell c2 = cells.get(j);

				// findNPossibilityCells guarantees c1 and c2 have the same
				// number of possibilities.
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
