/*
 * OneOfEachStrategy.java
 * 
 * Version information
 *
 * Date
 * 
 * Copyright notice
 */
import java.util.Set;


public class OneOfEachStrategy implements Strategy {

	@Override
	public boolean removePossibilities(Sudoku puzzle) throws SudokuException, InvalidSudokuException {
		boolean changed = false;
		
		for(CellContainer container : puzzle.getAllContainers()) {
			Set<Integer> values = container.getValues();
			for(Cell c : container.getCells()) {
				if(!c.hasValue() && c.removeAllPossibilities(values)) {
					changed = true;
				}
			}
		}
		
		return changed;
	}

}
