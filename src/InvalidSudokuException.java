/**
 * This exception should be thrown whenever and invalid sudoku is encountered.
 * 
 * @version 1.0
 * @author Jouke van der Maas & Koen Keune
 * 
 */

public class InvalidSudokuException extends SudokuException {
	private static final long serialVersionUID = -152735031103016681L;

	public InvalidSudokuException() {
		super();
	}

	public InvalidSudokuException(String message) {
		super(message);
	}

	public InvalidSudokuException(Throwable cause) {
		super(cause);
	}

	public InvalidSudokuException(String message, Throwable cause) {
		super(message, cause);
	}

}
