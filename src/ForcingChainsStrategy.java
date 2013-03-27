import java.util.ArrayList;
import java.util.List;


public class ForcingChainsStrategy implements Strategy {
	// Don't allow too many levels of recursion or the solver
	// will get very slow.
	private static int maxLevel = 4;
	private static int currentLevel = 0;

	public static void setMaxDepth(int depth) {
		maxLevel = depth;
	}
	
	@Override
	public boolean removePossibilities(Sudoku puzzle) throws SudokuException, InvalidSudokuException {
		if(currentLevel >= maxLevel) {
			return false;
		}
		currentLevel++;
		
		// Only do doubles (cells with two possibilities) to avoid having too many chains.
		// Again, this strategy has a very high complexity so
		// everything should be done to make it cheaper.
		List<Cell> doubles = findDoublePossibilities(puzzle);
		int[][] copy = puzzle.toIntArray();
		
		for(Cell c : doubles) {
			// see if there's a cell that always takes the same value,
			// regardless of what is entered in c.
			Cell chainResult = finishChain(copy, c);
			
			if(chainResult != null) {
				// This guarantees that a new value can be entered, so
				// we should stop (to avoid doing more than necessary).
				Cell chainResultEquiv = puzzle.getCell(chainResult.getRow(), chainResult.getColumn());
				currentLevel--;
				return chainResultEquiv.removeOtherPossibilities(chainResult.getValue());
			}
		}
		
		currentLevel--;
		return false;
	}

	private Cell finishChain(int[][] copy, Cell c) throws SudokuException, InvalidSudokuException {
		Sudoku pos1 = new Sudoku(copy);
		Sudoku pos2 = new Sudoku(copy);
		Solver solver1;
		Solver solver2;
		
		// Try to solve the puzzle with both values.
		// note: one of these will be the correct value no matter what,
		// but we're not brute-forcing (i.e. throwing the other, wrong, 
		// possibility away), just looking if there is overlap somewhere.
		int val1 = c.getPossibilities().get(0);
		int val2 = c.getPossibilities().get(1);
		pos1.getCell(c.getRow(), c.getColumn()).setValue(val1);
		pos2.getCell(c.getRow(), c.getColumn()).setValue(val2);
		solver1 = new Solver(pos1);
		solver2 = new Solver(pos2);
		
		// One of these will likely throw (depending on the complexity of the
		// puzzle and the value of maxLevel), but never both (unless the puzzle
		// is in fact invalid).
		
		boolean pos1Done = false;
		boolean pos2Done = false;
		while(!pos1Done || !pos2Done) {
			try {
				if(!pos1Done) {
					pos1Done = solver1.takeStep() ? pos1.isSolved() : true;
				}
				
			} catch (InvalidSudokuException e) {
				pos1Done = true;
			}
			try {
				if(!pos2Done) {
					pos2Done = solver2.takeStep() ? pos2.isSolved() : true;
				}
				
			} catch (InvalidSudokuException e) {
				pos2Done = true;
			}
			
			Cell result = findNewOverlap(pos1, pos2, copy);
			if(result != null)
				return result;
		}
		
		return null;
	}

	private Cell findNewOverlap(Sudoku pos1, Sudoku pos2, int[][] copy) throws SudokuException {
		for(int row = 0; row < Sudoku.SUDOKU_SIZE; row++) {
			for(int col = 0; col < Sudoku.SUDOKU_SIZE; col++) {
				Cell c1 = pos1.getCell(row, col);
				Cell c2 = pos2.getCell(row, col);
				
				// The certain value is only useful if it did not have a value
				// in the original puzzle.
				if(copy[row][col] == 0 && c1.hasValue() && c2.hasValue() && c1.getValue() == c2.getValue())
					return c1;
			}
		}
		
		return null;
	}

	private List<Cell> findDoublePossibilities(Sudoku puzzle) {
		List<Cell> doubles = new ArrayList<Cell>();
		
		for(CellContainer container : puzzle.getRows()) {
			for(Cell c : container.getCells()) {
				if(!c.hasValue() && c.getPossibilities().size() == 2)
					doubles.add(c);
			}
		}
		return doubles;
	}

}
