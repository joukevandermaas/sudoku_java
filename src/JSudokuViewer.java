import java.awt.*;
import javax.swing.JComponent;

/**
 * Component that draws a specific sudoku.
 * 
 * @version 1.0
 * @author Jouke van der Maas & Koen Keune
 * 
 */
public class JSudokuViewer extends JComponent {
	private static final long serialVersionUID = 1421004190537806983L;
	private Sudoku puzzle;
	private int cellSize = 50;
	private int puzzleDim = cellSize * Sudoku.SUDOKU_SIZE;
	private int special = -1;
	private Color highlight = Color.red;

	public JSudokuViewer() {
		this.setMinimumSize(new Dimension(puzzleDim, puzzleDim));
		this.setPreferredSize(this.getMinimumSize());
		this.setMaximumSize(new Dimension((int) (puzzleDim * 1.2),
				(int) (puzzleDim * 1.2)));
		this.setBounds(0, 0, puzzleDim, puzzleDim);
	}

	/**
	 * Sets the puzzle to draw and causes a repaint.
	 * @param puzzle
	 * The new puzzle to draw.
	 */
	public void setPuzzle(Sudoku puzzle) {
		this.puzzle = puzzle;
		this.repaint();
	}

	/**
	 * Sets the value to highlight.
	 * @param value
	 */
	public void setSpecial(int value) {
		this.special = value;
		this.repaint();
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);

		int bound = width < height ? width - 2 : height - 2;

		if (bound + 2 >= this.getMinimumSize().width) {
			this.cellSize = bound / Sudoku.SUDOKU_SIZE; // integer division
														// rounds down
			this.puzzleDim = cellSize * Sudoku.SUDOKU_SIZE; // always fits
		}
	}

	@Override
	public void setBounds(Rectangle r) {
		super.setBounds(r);
		setBounds(r.x, r.y, r.width, r.height);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		drawFrame((Graphics2D) g, new Point(1, 1)); // 1,1 for thicker border

		if (puzzle != null) {
			for (int i = 0; i < Sudoku.SUDOKU_SIZE; i++) {
				for (int j = 0; j < Sudoku.SUDOKU_SIZE; j++) {
					try {
						drawCell((Graphics2D) g, puzzle.getCell(i, j),
								new Point(cellSize * j, cellSize * i));
					} catch (SudokuException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * Draws a single cell at the specified position.
	 * @param g
	 * The graphics used for drawing.
	 * @param c
	 * The cell to draw.
	 * @param p
	 * The position of this cell on the canvas (not the sudoku).
	 * @throws SudokuException
	 */
	private void drawCell(Graphics2D g, Cell c, Point p) throws SudokuException {
		if (c.hasValue()) {
			String value = Integer.toHexString(c.getValue());
			Font f = new Font(g.getFont().getFontName(), 0, 36);

			if (c.getValue() == special)
				g.setColor(highlight);
			else
				g.setColor(Color.black);

			g.setFont(f);
			g.drawString(value, p.x + cellSize / 2 - cellSize / 4, p.y
					+ cellSize - cellSize / 4);
		} else {
			Font f = new Font(g.getFont().getFontName(), 0, 10);

			g.setFont(f);
			int rowSize = cellSize / Sudoku.SQUARE_COLUMNS;
			int colSize = cellSize / Sudoku.SQUARE_ROWS;
			for (int i : c.getPossibilities()) {
				String value = Integer.toHexString(i);
				int row = (i - 1) / Sudoku.SQUARE_COLUMNS;
				int col = (i - 1) % Sudoku.SQUARE_COLUMNS;
				int xpos = p.x + col * rowSize;
				int ypos = p.y + row * colSize;

				if (i == special) {
					g.setColor(highlight);
					g.drawRect(xpos, ypos, rowSize, colSize);
				}
				g.setColor(Color.black);

				g.drawString(value, xpos + colSize / 2 - colSize / 4, ypos
						+ rowSize - rowSize / 4);
			}
		}
	}

	/**
	 * Draws the frame of the sudoku.
	 * @param g
	 * the graphics to draw on.
	 * @param p
	 * The position on the canvas to start drawing.
	 */
	private void drawFrame(Graphics2D g, Point p) {
		g.setColor(Color.white);
		g.fillRect(p.x, p.y, puzzleDim, puzzleDim);

		for (int i = 0; i <= Sudoku.SUDOKU_SIZE; i++) {
			if (i % Sudoku.SQUARE_ROWS == 0) {
				g.setStroke(new BasicStroke(2));
				g.setColor(Color.black);
			} else {
				g.setColor(Color.darkGray);
				g.setStroke(new BasicStroke(1));
			}
			g.drawLine(p.x, p.y + i * cellSize, p.x + puzzleDim, p.y + i
					* cellSize);

			if (i % Sudoku.SQUARE_COLUMNS == 0) {
				g.setStroke(new BasicStroke(2));
				g.setColor(Color.black);
			} else {
				g.setColor(Color.darkGray);
				g.setStroke(new BasicStroke(1));
			}
			g.drawLine(p.x + i * cellSize, p.y, p.x + i * cellSize, p.y
					+ puzzleDim);
		}

	}
}
