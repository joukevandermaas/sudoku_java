import java.util.ArrayList;
import java.util.List;


public class ForcingChainsStrategy implements Strategy {
	private static final int maxLevel = 1;
	private static int currentLevel = 0;
	
	@Override
	public boolean removePossibilities(Sudoku puzzle) throws SudokuException, InvalidSudokuException {
		if(currentLevel >= maxLevel) {
			return false;
		}
		currentLevel++;
		
		boolean result = false;
		
		List<Cell> doubles = findDoublePossibilities(puzzle);
		int[][] copy = puzzle.toIntArray();
		
		for(Cell c : doubles) {
			Cell chainResult = finishChain(copy, c);
			if(chainResult != null) {
				Cell chainResultEquiv = puzzle.getCell(chainResult.getRow(), chainResult.getColumn());
				currentLevel--;
				return chainResultEquiv.removeOtherPossibilities(chainResult.getValue());
			}
		}
		
		currentLevel--;
		return result;
	}

	private Cell finishChain(int[][] copy, Cell c) throws SudokuException, InvalidSudokuException {
		Sudoku pos1 = new Sudoku(copy);
		Sudoku pos2 = new Sudoku(copy);
		Solver solver1 = new Solver(pos1);
		Solver solver2 = new Solver(pos2);
		
		int val1 = c.getPossibilities().get(0);
		int val2 = c.getPossibilities().get(1);
		pos1.getCell(c.getRow(), c.getColumn()).setValue(val1);
		pos2.getCell(c.getRow(), c.getColumn()).setValue(val2);
		
		try {
			solver1.solve();
		} catch (InvalidSudokuException e) {
		}
		try {
			solver2.solve();
		} catch (InvalidSudokuException e) {
		}
		
		return findNewOverlap(pos1, pos2, copy);
	}

	private Cell findNewOverlap(Sudoku pos1, Sudoku pos2, int[][] copy) throws SudokuException {
		for(int row = 0; row < Sudoku.SUDOKU_SIZE; row++) {
			for(int col = 0; col < Sudoku.SUDOKU_SIZE; col++) {
				Cell c1 = pos1.getCell(row, col);
				Cell c2 = pos2.getCell(row, col);
				
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
