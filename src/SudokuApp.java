import java.awt.*;
import java.io.FileNotFoundException;

import javax.swing.JApplet;


public class SudokuApp extends JApplet {
	private static final long serialVersionUID = 3028028885712932036L;
	private int cellSize = 50;
	private int puzzleDim = cellSize * Sudoku.SUDOKU_SIZE;
	
	private Sudoku puzzle;

	@Override
	public void init() {
		super.init();
		this.setSize((Sudoku.SUDOKU_SIZE + 2) * cellSize, (Sudoku.SUDOKU_SIZE + 2) * cellSize);
		
		Loader l = null;
		try {
			l = Loader.fromFile("../sudokus.txt");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		int[][] puzzle = l.getNext();
		try {
			this.puzzle = new Sudoku(puzzle);
			Solver s = new Solver(this.puzzle);
			s.solve();
		} catch (SudokuException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		int xoffset = (this.getWidth() - puzzleDim) / 2;
		int yoffset = (this.getHeight() - puzzleDim) / 2;
		
		drawFrame((Graphics2D)g, new Point(xoffset, yoffset));
		
		for(int i = 0; i < Sudoku.SUDOKU_SIZE; i++) {
			for(int j = 0; j < Sudoku.SUDOKU_SIZE; j++) {
				try {
					drawCell((Graphics2D)g, puzzle.getCell(i, j), new Point(xoffset + cellSize * j, yoffset + cellSize * i));
				} catch (SudokuException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void drawCell(Graphics2D g, Cell c, Point p) throws SudokuException {		
		if(c.hasValue()) {
			String value = Integer.toHexString(c.getValue());
			Font f = new Font(g.getFont().getFontName(), 0, 36);
			
			g.setColor(Color.black);
			g.setFont(f);
			g.drawString(value, p.x + cellSize / 2 - cellSize/4, p.y + cellSize - cellSize/4);
		} else {
			Font f = new Font(g.getFont().getFontName(), 0, 10);
			
			g.setColor(Color.darkGray);
			g.setFont(f);
			int rowSize = cellSize/Sudoku.SQUARE_COLUMNS;
			int colSize = cellSize/Sudoku.SQUARE_ROWS;
			for(int i : c.getPossibilities()) {
				String value = Integer.toHexString(i);
				int row = (i-1) / Sudoku.SQUARE_COLUMNS;
				int col = (i-1) % Sudoku.SQUARE_COLUMNS;
				int xpos = p.x + col * rowSize;
				int ypos = p.y + row * colSize;
				
				g.drawString(value, xpos + colSize/2 - colSize/4, ypos + rowSize - rowSize/4);
				//g.drawRect(xpos, ypos, rowSize, colSize);
			}
		}
	}

	private void drawFrame(Graphics2D g, Point p) {
		g.setColor(Color.white);
		g.fillRect(p.x, p.y, puzzleDim, puzzleDim);
		
		for(int i = 0; i <= Sudoku.SUDOKU_SIZE; i++) {
			if(i % Sudoku.SQUARE_ROWS == 0) {
				g.setStroke(new BasicStroke(2));
				g.setColor(Color.black);
			} else {
				g.setColor(Color.darkGray);
				g.setStroke(new BasicStroke(1));
			}
			g.drawLine(p.x, p.y + i * cellSize, p.x + puzzleDim, p.y + i * cellSize);
			
			if(i % Sudoku.SQUARE_COLUMNS == 0) {
				g.setStroke(new BasicStroke(2));
				g.setColor(Color.black);
			} else {
				g.setColor(Color.darkGray);
				g.setStroke(new BasicStroke(1));
			}
			g.drawLine(p.x + i * cellSize, p.y, p.x  + i * cellSize, p.y + puzzleDim);
		}
		
	}
	
}

























