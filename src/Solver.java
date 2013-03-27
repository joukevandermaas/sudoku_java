/*
 * Solver.java
 * This class attempts to solve a sudoku puzzle using various Strategy-
 * implementations in increasing complexity.
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

import java.util.*;

public class Solver {
	private Sudoku sudoku;
	private Strategy[] strategies = { new OneOfEachStrategy(),
			new LockedStrategy(), new DoubleLockedStrategy(),
			new NakedGroupStrategy(), new HiddenTwinStrategy(),
			new ForcingChainsStrategy() };

	public Solver(Sudoku puzzle) throws SudokuException, InvalidSudokuException {
		this.sudoku = puzzle;

		// strategies[0] fixes special case and ensures no wrong number will
		// be entered in a CellContainer with only one empty cell at the start.
		strategies[0].removePossibilities(sudoku);
	}

	// Solves the sudoku. Returns when the sudoku is solved,
	// or when no more steps could be taken (ie. the sudoku is
	// invalid or it was too hard).
	public void solve() throws SudokuException, InvalidSudokuException {
		while (takeStep() && !sudoku.isSolved())
			;
	}

	public boolean takeStep() throws InvalidSudokuException, SudokuException {
		boolean madeProgress = false;

		if (enterValue())
			madeProgress = true;

		for (Strategy strat : strategies) {
			// Only use more advanced strategies when the most simple
			// one fails and no more numbers can be entered directly.
			if (strat.removePossibilities(sudoku) || madeProgress) {
				madeProgress = true;
				break;
			}
		}

		return madeProgress;
	}

	// returns the sudoku this Solver is solving
	public Sudoku getSudoku() {
		return sudoku;
	}

	// Enters only one value, because possibilities change whenever a
	// number is entered. The possibilities should be updated before more
	// numbers
	// are entered.
	private boolean enterValue() throws SudokuException {
		boolean result = false;
		for (CellContainer c : sudoku.getSquares()) {
			if (enterSingles(c))
				result = true;
		}

		if (!result)
			for (CellContainer c : sudoku.getAllContainers())
				if (enterHiddenSingle(c))
					return true;
		return result;
	}

	// if there is one possibility left then that will be the value
	private boolean enterSingles(CellContainer container)
			throws SudokuException {
		boolean result = false;

		for (Cell c : container.getCells()) {
			if (!c.hasValue() && c.getPossibilities().size() == 1) {
				c.setValue(c.getPossibilities().get(0));
				result = true;
			}
		}
		return result;
	}

	// if a possible value can go in only one spot then that will be the value
	private boolean enterHiddenSingle(CellContainer container)
			throws SudokuException {
		List<ArrayList<Cell>> values = new ArrayList<ArrayList<Cell>>();

		for (int i = 1; i <= Sudoku.SUDOKU_SIZE; i++) {
			values.add(new ArrayList<Cell>());

			for (Cell c : container.getCells()) {
				if (!c.hasValue() && c.getPossibilities().contains(i))
					values.get(i - 1).add(c);
			}
		}

		for (int i = 1; i < Sudoku.SUDOKU_SIZE; i++) {
			List<Cell> l = values.get(i - 1);
			if (l.size() == 1) {
				l.get(0).setValue(i);
				return true;
			}
		}

		return false;
	}
}
