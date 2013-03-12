/*
 * SudokuReadException.java
 * 
 * Version information
 *
 * Date
 * 
 * Copyright notice
 */

public class SudokuReaderException extends Exception{
	private static final long serialVersionUID = -152735031103016681L;
	
	public SudokuReaderException() {
		super();
	}
	public SudokuReaderException(String message) {
		super(message);
	}
	public SudokuReaderException(Throwable cause) {
		super(cause);
	}
	public SudokuReaderException(String message, Throwable cause) {
		super(message, cause);
	}

}
