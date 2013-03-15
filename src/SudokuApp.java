import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Arrays;

import javax.swing.*;


public class SudokuApp extends JApplet implements ActionListener {
	private static final long serialVersionUID = 3028028885712932036L;
	private int cellSize = 50;
	private ButtonGroup numberGroup;
	private JSudokuViewer viewer;
	private Sudoku puzzle;

	@Override
	public void init() {
		super.init();
		this.setSize((Sudoku.SUDOKU_SIZE + 2) * cellSize, (Sudoku.SUDOKU_SIZE + 2) * cellSize);
		
		setLayout(null);
		loadSudoku();
		
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        viewer = new JSudokuViewer(puzzle);
        viewer.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewer.setAlignmentY(Component.CENTER_ALIGNMENT);
               
        add(Box.createRigidArea(new Dimension(30, 30)));
        add(viewer);
        
        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
        
        numberGroup = new ButtonGroup();
        for(int i = 1; i <= Sudoku.SUDOKU_SIZE; i++) {
        	JToggleButton b = new JToggleButton(Integer.toString(i));
        	numberGroup.add(b);
        	b.addActionListener(this);
        	buttons.add(b);
        }
        add(buttons);
    }
	
	private void loadSudoku() {
		Loader l = null;
		try {
			l = Loader.fromFile("../sudokus.txt");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		int[][] loadedSudoku = l.getNext();
		try {
			Solver s;
			while(loadedSudoku != null) {
				puzzle = new Sudoku(loadedSudoku);
				s = new Solver(this.puzzle);
				s.solve();
				
				if(puzzle.isSolved()) {
					loadedSudoku = l.getNext();
				}
				else {
					for(int i = 0; i < 9; i++)
					System.out.println(Arrays.toString(loadedSudoku[i]));
					break;
				}
			}
			
		} catch (SudokuException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JToggleButton) {
			JToggleButton b = (JToggleButton) e.getSource();
			int value = Integer.parseInt(b.getText());
			
			if(b.isSelected()) {
				viewer.setSpecial(value);
			}
		}
	}
	
	
	
}

























