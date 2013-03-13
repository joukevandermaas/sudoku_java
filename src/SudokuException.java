/*
 * SudokuException.java
 * 
 * Version information
 *
 * Date
 * 
 * Copyright notice
 */

public class SudokuException extends Exception{
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
