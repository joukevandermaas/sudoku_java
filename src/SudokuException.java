/**
 * This exception should be thrown when an invalid operation is attempted on a Sudoku object.
 * 
 * @version 1.0
 * @author Jouke van der Maas & Koen Keune
 * 
 */
public class SudokuException extends Exception {
	private static final long serialVersionUID = -152735031103016681L;

	public SudokuException() {
		super();
	}

	public SudokuException(String message) {
		super(message);
	}

	public SudokuException(Throwable cause) {
		super(cause);
	}

	public SudokuException(String message, Throwable cause) {
		super(message, cause);
	}

}
