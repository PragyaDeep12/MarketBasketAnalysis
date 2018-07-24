package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class SearchProduct extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldConfidence;
	private JTextField textFieldLift;
	private StringBuilder currentStatus;
	private JTextArea textArea;
	private WaitDialog dialog;
	private String outFolder;
	private JTextField textFieldOutDir;
	private JScrollPane jScrollPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchProduct frame = new SearchProduct();
					frame.setTitle("Search Input");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SearchProduct() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 745, 606);
		currentStatus=new StringBuilder();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(102, 102, 153));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textFieldConfidence = new JTextField();
		textFieldConfidence.setFont(new Font("Tahoma", Font.BOLD, 15));
		textFieldConfidence.setText("0.0");
		textFieldConfidence.setBounds(197, 53, 173, 22);
		contentPane.add(textFieldConfidence);
		textFieldConfidence.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Confidence :");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(197, 12, 220, 28);
		contentPane.add(lblNewLabel);
		
		JLabel lblLift = new JLabel("Lift :");
		lblLift.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblLift.setBounds(12, 12, 166, 28);
		lblLift.setForeground(new Color(255, 255, 255));
		contentPane.add(lblLift);
		
		textFieldLift = new JTextField();
		textFieldLift.setText("0.0");
		textFieldLift.setFont(new Font("Tahoma", Font.BOLD, 15));
		textFieldLift.setColumns(10);
		textFieldLift.setBounds(12, 54, 173, 22);
		contentPane.add(textFieldLift);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 190, 703, 356);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		
		textArea = new JTextArea();
		//panel.add(textArea, BorderLayout.CENTER);
		textArea.setEditable(false);
		textArea.setForeground(new Color(0, 0, 51));
		textArea.setFont(new Font("Monospaced", Font.BOLD, 15));
		textArea.setBackground(new Color(153, 153, 204));
		
		JScrollPane jScrollPane=new JScrollPane(textArea);
		jScrollPane.setPreferredSize(new Dimension(450, 200));
		panel.add(jScrollPane);
		
		
		JButton btnSearchResult = new JButton("Search");
		btnSearchResult.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnSearchResult.setBounds(398, 51, 173, 25);
		contentPane.add(btnSearchResult);
		
		textFieldOutDir = new JTextField();
		textFieldOutDir.setFont(new Font("Tahoma", Font.BOLD, 15));
		textFieldOutDir.setBounds(12, 102, 559, 22);
		contentPane.add(textFieldOutDir);
		textFieldOutDir.setColumns(10);
		
		JButton btnChoose = new JButton("Choose Output Directory");
		btnChoose.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnChoose.setBounds(12, 152, 390, 25);
		contentPane.add(btnChoose);
		
		btnSearchResult.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					dialog=new WaitDialog();
					dialog.setVisible(true);
					textArea.setText("");
					currentStatus.append("Started Execution ... " +"\n");
					 textArea.setText(currentStatus.toString());
					 
					doMapReduce();
		
				
			}
		});
		
		btnChoose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser chooser=new JFileChooser();
				
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.showOpenDialog(contentPane);
				File f=chooser.getSelectedFile();
				textFieldOutDir.setText(f.getAbsolutePath()+"/SearchOutputFolder");
				outFolder=textFieldOutDir.getText();
			}
		});
		
	}
	
	
	public void doMapReduce(){
		Thread thread=new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
			//	super.run();
				
				  try {
					  JOptionPane.showMessageDialog(contentPane, "Starting File Processing ...", "Status", JOptionPane.INFORMATION_MESSAGE);
					  Double confidence=Double.parseDouble(textFieldConfidence.getText());
					  Double lift = Double.parseDouble(textFieldLift.getText());
						Process p=Runtime.getRuntime().exec("sh required_files/search.sh "+confidence+" "+lift+" "+outFolder+" required_files/productprob.jar");
						if(p.waitFor()==0){
							dialog.setVisible(false);
							dialog.dispose();
							BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
							 String line = null;
							 while ((line = reader.readLine()) != null)
							 {
								 currentStatus.append(line+"\n");
								 textArea.setText(currentStatus.toString());
							    //System.out.println(line);
							 }
							 }else{
								 System.out.println("Error in streem"+p.getErrorStream());
								 BufferedReader reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
								 String line = null;
								 while ((line = reader.readLine()) != null)
								 {
									 currentStatus.append(line+"\n");
									 textArea.setText(currentStatus.toString());
								    //System.out.println(line);
								 }
							 }
							
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
			}
		};
		thread.start();
	}
	
}
