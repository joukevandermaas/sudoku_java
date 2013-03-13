import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.FileNotFoundException;

import javax.swing.JApplet;


public class SudokuApp extends JApplet {
	private static final long serialVersionUID = 3028028885712932036L;
	
	private Sudoku puzzle;

	@Override
	public void init() {
		super.init();
		this.setSize(500, 500);
		
		Loader l = null;
		try {
			l = Loader.fromFile("sudokus.txt");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		int[][] puzzle = l.getNext();
		try {
			this.puzzle = new Sudoku(puzzle);
		} catch (SudokuException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
	}
	
	private void drawFrame(Graphics g, int width, int height, Point start) {
		g.setColor(Color.black);
		
		for(int i = 0; i < Sudoku.SUDOKU_SIZE; i++) {
			g.drawLine(start.x, start.y, width, start.y + i * 5);
		}
		
	}
	
}

























