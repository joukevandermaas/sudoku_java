/*
 * SudokuApp.java
 * JApplet that attempts to solve all sudokus in sudokus.txt and
 * displays either the last sudoku that could not be solved or the
 * solution to the last sudoku in the file.
 * 
 * Version information
 * v0.2 (alpha 1)
 *
 * Date
 * 15/03/2013
 * 
 * Author
 * Jouke van der Maas & Koen Keune
 * 
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class SudokuPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 3028028885712932036L;
	private ButtonGroup numberGroup;
	private JSudokuViewer viewer;
	private Sudoku puzzle;
	private Solver solver;
	private Loader loader;

	public SudokuPanel(Loader puzzleLoader) {
		super();
		
		this.loader = puzzleLoader;
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        viewer = new JSudokuViewer();
        viewer.setPuzzle(puzzle);
        viewer.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewer.setAlignmentY(Component.CENTER_ALIGNMENT);
               
        add(Box.createRigidArea(new Dimension(30, 30)));
        add(viewer);
        
        JPanel controls = new JPanel();
        JPanel numberButtons = new JPanel();
        
        controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
        numberButtons.setLayout(new BoxLayout(numberButtons, BoxLayout.Y_AXIS));
        
        numberGroup = new ButtonGroup();
        for(int i = 1; i <= Sudoku.SUDOKU_SIZE; i++) {
        	JToggleButton b = new JToggleButton(Integer.toString(i));
        	numberGroup.add(b);
        	b.addActionListener(this);
        	numberButtons.add(b);
        }
        
        JButton next = new JButton("Next puzzle");
        next.addActionListener(this);
        JButton solve = new JButton("Solve puzzle");
        solve.addActionListener(this);
        
        controls.add(numberButtons);
        controls.add(Box.createVerticalStrut(20));
        controls.add(next);
        controls.add(solve);

        add(controls);
	}
	
	// loads and solves as many sudokus as possible from
	// sudokus.txt and sets the final solved or unsolved sudoku to this.puzzle
	private boolean loadSudoku() throws SudokuException {
		int[][] loadedSudoku = loader.getNext();
		if(loadedSudoku != null) {
			puzzle = new Sudoku(loadedSudoku);
			solver = new Solver(this.puzzle);
			viewer.setPuzzle(puzzle);
			return true;
		}
		return false;
	}

	// handles the button presses
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JToggleButton) {
			JToggleButton b = (JToggleButton) e.getSource();
			int value = Integer.parseInt(b.getText());
			
			if(b.isSelected()) {
				viewer.setSpecial(value);
			}
		} else if(e.getSource() instanceof JButton) {
			JButton action = (JButton) e.getSource();
			try {
				if(action.getText().startsWith("Next")) {
					if(!loadSudoku())
						action.setEnabled(false);
				} else {
					solver.solve();
				}
			} catch(InvalidSudokuException ex) {
				showError("Invalid sudoku");
			} catch (SudokuException ex) {
				showError("Solver encountered a problem.");
			}
		}
		
		viewer.repaint();
	}
	
	private void showError(String message) {
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	
	
}

























