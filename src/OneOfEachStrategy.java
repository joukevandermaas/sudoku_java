/*
 * OneOfEachStrategy.java
 * 
 * Version information
 *
 * Date
 * 
 * Copyright notice
 */
import java.util.List;


public class OneOfEachStrategy implements Strategy {

	@Override
	public boolean removePossibilities(Sudoku puzzle) throws SudokuException {
		boolean changed = false;
		
		for(CellContainer container : puzzle.getAllContainers()) {
			List<Integer> values = container.getValues();
			for(Cell c : container.getCells()) {
				if(!c.hasValue()) {
					changed = c.removeAllPossibilities(values);
				}
			}
		}
		
		return changed;
	}

}
