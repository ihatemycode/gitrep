package AppN1main;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainFrame {

	private JFrame frame;
	private JTextArea textArea;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(
							UIManager.getSystemLookAndFeelClassName());
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainFrame() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.setJMenuBar(initMainMenu());
		frame.getContentPane().add(panel);
				
		textArea = new JTextArea();
		textArea.setFont(new Font(Font.MONOSPACED,0, 20));
		
		JScrollPane scrollPane = new JScrollPane(textArea);		
		frame.getContentPane().add(scrollPane);
	}
	
	private JMenuBar initMainMenu() {
		JMenuBar jMenuBar = new JMenuBar();
		
		JMenu jFile = new JMenu("File");
		JMenuItem jNew = new JMenuItem("New");
		JMenuItem jOpen = new JMenuItem("Open");
		JMenuItem jSave = new JMenuItem("Save");
		JMenuItem jSaveAs = new JMenuItem("Save as");
		JMenuItem jExit = new JMenuItem("Exit");
		
		jFile.add(jNew);
		jFile.add(jOpen);
		jFile.add(jSave);
		jFile.add(jSaveAs);
		jFile.addSeparator();
		jFile.add(jExit);

		jFile.setFont(new Font(Font.DIALOG, 1, 16));
		jNew.setFont(new Font(Font.DIALOG, 1, 14));
		jOpen.setFont(new Font(Font.DIALOG, 1, 14));
		jSave.setFont(new Font(Font.DIALOG, 1, 14));
		jSaveAs.setFont(new Font(Font.DIALOG, 1, 14));
		jExit.setFont(new Font(Font.DIALOG, 1, 14));

		jMenuBar.add(jFile);
		
		jNew.addActionListener(new ActionListener() {	
				
			public void actionPerformed(ActionEvent arg0) {
				if (textArea.getText().length() != 0) {
					int result = JOptionPane.showConfirmDialog(frame, "Don't save changes?", "Message", JOptionPane.YES_NO_OPTION);
					if (result == 0) textArea.setText(""); // YES_OPTION
				}
			}			
		});
				
		jOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Open file dialog is activated");
				JFileChooser fc = new JFileChooser();
				int codeResult = fc.showOpenDialog(frame);
				System.out.println("Code result - " + codeResult);
				if (codeResult == fc.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					FileReader inputStream = null;
					
					try {
						inputStream = new FileReader(file);
						BufferedReader inBuff = new BufferedReader(inputStream);
						String oneLine = null;						
						StringBuilder allText = new StringBuilder();
						oneLine = inBuff.readLine();
						while (oneLine != null) {							
							allText = allText.append(oneLine);
							allText = allText.append("\n");
							System.out.println(oneLine);
							oneLine = inBuff.readLine();
						}
						textArea.setText(allText.toString());
						
						inputStream.close();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(frame, "Can't open file");
						e.printStackTrace();
					}
					finally {
						try {
							inputStream.close();
						} catch (IOException e) {
							JOptionPane.showMessageDialog(frame, "Can't close inputStream");
							e.printStackTrace();
						}
					}
				}
			}		
		});
		
		jSaveAs.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				int codeResult = fc.showSaveDialog(frame);
				if (codeResult == fc.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					FileWriter outputStream = null;
					try {
						outputStream = new FileWriter(file);
						outputStream.write(textArea.getText());
						outputStream.close();
					}
					catch (IOException e) {
						JOptionPane.showMessageDialog(frame, "Can't write file");
						e.printStackTrace();
					}
					finally {
						try {
							outputStream.close();
						} catch (IOException e) {
							JOptionPane.showMessageDialog(frame, "Can't close outputStream");
							e.printStackTrace();
						}
					}
				}
			}
		});
		
		jExit.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				System.exit(0);
			}
		});
						
		return jMenuBar;
	}	
}
