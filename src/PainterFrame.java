import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;


public class PainterFrame extends JFrame{
	
	private DrawingPanel drawingPanel;
	private JMenuItem saveMenuItem, saveAsMenuItem, exitMenuItem, cutMenuItem, copyMenuItem, pasteMenuItem, undoMenuItem, redoMenuItem, aboutMenuItem, recordMenuItem, pauseMenuItem, stopMenuItem;
	private String currentSaveName;
	private ButtonBar buttons;
	private LayersButtonBar layerButtons;
	/*private FileButtonBar fileButtons;
	private EditButtonBar editButtons;
	private BrushButtonBar brushButtons;
	private ShapesButtonBar shapesButtons;
	private SizesButtonBar sizesButtons;*/
	
	public PainterFrame(){
		super("Da Vinci Simulator! Version_1.2");
		this.setVisible(true);
		/*fileButtons = new FileButtonBar();
		editButtons = new EditButtonBar();
		brushButtons = new BrushButtonBar();
		shapesButtons = new ShapesButtonBar();
		sizesButtons = new SizesButtonBar();
		buttons = new ButtonBar(fileButtons, editButtons, brushButtons, shapesButtons, sizesButtons);*/
		layerButtons = new LayersButtonBar();
		buttons = new ButtonBar();
		ReferenceHolder.buttonBar = buttons;
		ReferenceHolder.size = 1;
		drawingPanel = new DrawingPanel(buttons, this);
		ReferenceHolder.drawingPanel = drawingPanel;
		buttons.setDrawingPanel(drawingPanel);
		JMenuBar menuBar = createMenuBar();
		this.setJMenuBar(menuBar);
		
		JPanel painterPanel = ReferenceHolder.painterPanel;
		painterPanel.setLayout(new BorderLayout());
		painterPanel.add(drawingPanel, BorderLayout.CENTER);
		painterPanel.add(buttons, BorderLayout.NORTH);
		painterPanel.add(layerButtons, BorderLayout.EAST);
		/*painterPanel.add(fileButtons, BorderLayout.WEST);
		painterPanel.add(editButtons, BorderLayout.WEST);
		painterPanel.add(brushButtons, BorderLayout.WEST);
		painterPanel.add(shapesButtons, BorderLayout.WEST);
		painterPanel.add(sizesButtons, BorderLayout.WEST);*/
		this.getContentPane().add(painterPanel);
		this.addWindowListener(new WindowCloser());
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	}
	
	public JMenuBar createMenuBar(){
		MenuListener menuListener = new MenuListener();
		
		// file menu button and its sub button
		JMenu fileMenu = new JMenu("File");
		JMenuItem newMenuItem = new JMenuItem("New");
		newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
		newMenuItem.addActionListener(menuListener);

		JMenuItem openMenuItem = new JMenuItem("Open...");
		openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		openMenuItem.addActionListener(menuListener);

		saveMenuItem = new JMenuItem("Save");
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveMenuItem.addActionListener(menuListener);
		saveMenuItem.setEnabled(false);

		saveAsMenuItem = new JMenuItem("Save As...");
		saveAsMenuItem.addActionListener(menuListener);
		saveAsMenuItem.setEnabled(false);

		aboutMenuItem = new JMenuItem("About...");
		aboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		aboutMenuItem.addActionListener(menuListener);

		exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		exitMenuItem.addActionListener(menuListener);

		fileMenu.add(newMenuItem);
		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(saveAsMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(aboutMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);
		
		//edit menu button and its sub butons
		JMenu editMenu = new JMenu("Edit");
		cutMenuItem = new JMenuItem("Cut");
		cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		cutMenuItem.addActionListener(menuListener);
		cutMenuItem.setEnabled(false);

		copyMenuItem = new JMenuItem("Copy");
		copyMenuItem.addActionListener(menuListener);
		copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		copyMenuItem.setEnabled(false);

		pasteMenuItem = new JMenuItem("Paste");
		pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		pasteMenuItem.addActionListener(menuListener);
		pasteMenuItem.setEnabled(false);

		undoMenuItem = new JMenuItem("Undo");
		undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		undoMenuItem.addActionListener(menuListener);
		undoMenuItem.setEnabled(false);
		
		redoMenuItem = new JMenuItem("Redo");
		redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		redoMenuItem.addActionListener(menuListener);
		redoMenuItem.setEnabled(false);

		editMenu.add(cutMenuItem);
		editMenu.add(copyMenuItem);
		editMenu.add(pasteMenuItem);
		editMenu.addSeparator ();
		editMenu.add(undoMenuItem);
		editMenu.add(redoMenuItem);
		
		JMenu recordMenu = new JMenu("Recorder");
		recordMenuItem = new JMenuItem("Start");
		recordMenuItem.addActionListener(menuListener);
		recordMenuItem.setEnabled(true);
		
		pauseMenuItem = new JMenuItem("Pause");
		pauseMenuItem.addActionListener(menuListener);
		pauseMenuItem.setEnabled(false);
		
		stopMenuItem = new JMenuItem("Stop");
		stopMenuItem.addActionListener(menuListener);
		stopMenuItem.setEnabled(false);
		
		recordMenu.add(recordMenuItem);
		recordMenu.add(pauseMenuItem);
		recordMenu.add(stopMenuItem);

		JMenuBar bar = new JMenuBar();
		bar.add(fileMenu);
		bar.add(editMenu);
		bar.add(recordMenu);

		return bar;
	}
	
	public void enableSaveAsMenuItem(){
		saveAsMenuItem.setEnabled(true);

		if (drawingPanel.isModified() && currentSaveName != null)
			saveMenuItem.setEnabled(true);
	}
	public void enableCutMenuItem(){
		cutMenuItem.setEnabled(true);
	}
	public void disableCutMenuItem(){
		cutMenuItem.setEnabled(false);
	}
	public void enableCopyMenuItem(){
		copyMenuItem.setEnabled(true);
	}
	public void disableCopyMenuItem(){
		copyMenuItem.setEnabled(false);
	}
	
	public void enableUndoMenuItem(){
		undoMenuItem.setEnabled(true);
	}
	public void disableUndoMenuItem(){
		undoMenuItem.setEnabled(false);
	}
	
	public void enableRedoButton(){
		redoMenuItem.setEnabled(true);;
	}
	public void disableRedoButton(){
		redoMenuItem.setEnabled(false);;
	}
	
	
	public class WindowCloser extends WindowAdapter{
		
		public void windowClosing(WindowEvent e){
			exitMenuItem.doClick();
		}
	}
	
	//listener for file and edit menu
	public class MenuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			
			JFileChooser fileChooser = new JFileChooser ();

			if (event.getActionCommand().equals("Exit"))
				switch (ifModifiedPrompt()){
				case JOptionPane.YES_OPTION:
					if (currentSaveName != null)
						drawingPanel.save(currentSaveName);
					else
						actionPerformed (new ActionEvent (event.getSource(), event.getID(), "Save As..."));

				case JOptionPane.NO_OPTION:
					System.exit (0);
					break;

				case JOptionPane.CANCEL_OPTION:
					break;
				}

			else if (event.getActionCommand().equals("Save As...")){
				fileChooser.rescanCurrentDirectory();

				if (fileChooser.showSaveDialog ((Component) event.getSource()) == JFileChooser.APPROVE_OPTION){
					currentSaveName = fileChooser.getSelectedFile().getPath();
					drawingPanel.save(currentSaveName);
					saveMenuItem.setEnabled(false);
				}
			}

			else if (event.getActionCommand().equals("Save")){
				drawingPanel.save (currentSaveName);
				saveMenuItem.setEnabled (false);
			}

			else if (event.getActionCommand().equals("Open...")){
				switch (ifModifiedPrompt ()){
				case JOptionPane.YES_OPTION:
					if (currentSaveName != null)
						drawingPanel.save(currentSaveName);
					else
						actionPerformed (new ActionEvent(event.getSource(), event.getID(), "Save As..."));

				case JOptionPane.NO_OPTION:
					fileChooser.rescanCurrentDirectory ();
					int returnVal = fileChooser.showOpenDialog((Component) event.getSource());

					if (returnVal == JFileChooser.APPROVE_OPTION){
						currentSaveName = fileChooser.getSelectedFile().getPath();
						drawingPanel.load(currentSaveName);
						drawingPanel.paintComponent(drawingPanel.getGraphics());

						saveAsMenuItem.setEnabled(true);
					}
					break;

				case JOptionPane.CANCEL_OPTION:
					break;
				}
			}

			else if (event.getActionCommand().equals("New")){
				switch (ifModifiedPrompt()){
				case JOptionPane.YES_OPTION:
					if (currentSaveName != null)
						drawingPanel.save (currentSaveName);
					else
						actionPerformed (new ActionEvent (event.getSource (), event.getID (), "Save As..."));

				case JOptionPane.NO_OPTION:
					drawingPanel.newDrawing();
					saveAsMenuItem.setEnabled (false);
					currentSaveName = null;
					break;

				case JOptionPane.CANCEL_OPTION:
					break;
				}
				saveMenuItem.setEnabled (false);
			}

			else if (event.getActionCommand ().equals ("Cut")){
				drawingPanel.copySelectedShape ();
				drawingPanel.removeSelectedShape ();
				drawingPanel.repaintSelf ();
				pasteMenuItem.setEnabled (true);
				//unfillMenuItem.setEnabled (false);
				//buttons.disableFillButton ();
			}

			else if (event.getActionCommand ().equals ("Copy")){
				drawingPanel.copySelectedShape ();
				pasteMenuItem.setEnabled (true);
			}

			else if (event.getActionCommand ().equals ("Paste"))
				drawingPanel.pasteSelectedShape ();

			else if (event.getActionCommand ().equals ("Undo")){
				boolean vis = drawingPanel.undoAction();
				undoMenuItem.setEnabled(vis);
				redoMenuItem.setEnabled(true);
			}
			else if (event.getActionCommand ().equals ("Redo")){
				boolean vis = drawingPanel.redoAction();
				redoMenuItem.setEnabled(vis);
				undoMenuItem.setEnabled(true);
			}

			else if (event.getActionCommand ().equals ("About...")){
				String message = "Painter! A Brand New Paint Like Java Application by: \nAhmet, Muzaffer, Salih, Sedat, Talha \nDiogenes Copyright 2015® For CS 102";
				JOptionPane.showMessageDialog (PainterFrame.this, message);
			}
		}

		private int ifModifiedPrompt (){
			if (drawingPanel.isModified()){
				String message = "This drawing has been modified.\nWould you like to save it?";
				return JOptionPane.showConfirmDialog (PainterFrame.this, message, "Save current drawing?", JOptionPane.YES_NO_CANCEL_OPTION);
			}else
				return JOptionPane.NO_OPTION;
		}
	}

}
