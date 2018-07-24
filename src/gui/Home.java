package gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Color;

import javax.swing.JTextArea;

public class Home extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldInput;
	private JTextField textFieldOutput;
	private JTextArea textArea;
	private String output;
	private String input;
	 StringBuilder currentStatus;
	 Thread thread;
	private WaitDialog dialog;

	 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
					frame.setTitle("Market Basket Analysis");
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
	public Home() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 860, 729);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(102, 102, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		currentStatus=new StringBuilder();
		textFieldInput = new JTextField();
		textFieldInput.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textFieldInput.setBounds(12, 43, 512, 24);
		contentPane.add(textFieldInput);
		textFieldInput.setColumns(10);
		
		JLabel lblChooseAFile = new JLabel("CHOOSE A FILE TO PROCESS : ");
		lblChooseAFile.setForeground(new Color(255, 255, 255));
		lblChooseAFile.setFont(new Font("Gabriola", Font.BOLD, 18));
		lblChooseAFile.setBounds(12, 13, 292, 24);
		contentPane.add(lblChooseAFile);
		
		JButton btnChooseFile = new JButton("Choose File");
		btnChooseFile.setForeground(new Color(255, 255, 255));
		btnChooseFile.setBackground(new Color(51, 0, 102));
		btnChooseFile.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnChooseFile.setBounds(12, 78, 136, 25);
		contentPane.add(btnChooseFile);
		
		JButton btnUpload = new JButton("Upload");
		btnUpload.setForeground(new Color(255, 255, 255));
		btnUpload.setBackground(new Color(51, 0, 102));
		btnUpload.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnUpload.setBounds(164, 79, 97, 25);
		contentPane.add(btnUpload);
		
		textFieldOutput = new JTextField();
		textFieldOutput.setBounds(12, 169, 512, 24);
		textFieldOutput.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(textFieldOutput);
		textFieldOutput.setColumns(10);
		
		JButton btnChooseOutputLocation = new JButton("Choose Output Location");
		btnChooseOutputLocation.setForeground(new Color(255, 255, 255));
		btnChooseOutputLocation.setBackground(new Color(51, 0, 102));
		btnChooseOutputLocation.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnChooseOutputLocation.setBounds(12, 206, 249, 25);
		contentPane.add(btnChooseOutputLocation);
		
		JLabel lblChooseAFolder = new JLabel("CHOOSE A FOLDER TO GET THE OUPTUT FILE : ");
		lblChooseAFolder.setForeground(new Color(255, 255, 255));
		lblChooseAFolder.setFont(new Font("Gabriola", Font.BOLD, 18));
		lblChooseAFolder.setBounds(12, 132, 499, 24);
		contentPane.add(lblChooseAFolder);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 291, 818, 378);;
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
		
		JLabel lblWorkingStatus = new JLabel("Working Status :");
		lblWorkingStatus.setForeground(Color.WHITE);
		lblWorkingStatus.setFont(new Font("Gabriola", Font.BOLD, 28));
		lblWorkingStatus.setBounds(12, 244, 354, 49);
		contentPane.add(lblWorkingStatus);
		
		JButton btnSearchOutput = new JButton("Search Output");
		btnSearchOutput.setForeground(Color.WHITE);
		btnSearchOutput.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnSearchOutput.setBackground(new Color(51, 0, 102));
		btnSearchOutput.setBounds(273, 207, 249, 25);
		contentPane.add(btnSearchOutput);
		
		
		btnChooseFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFileChooser chooser=new JFileChooser();
				chooser.showOpenDialog(contentPane);
				File file=chooser.getSelectedFile();
				textFieldInput.setText(file.getAbsolutePath());
				input=file.getAbsolutePath();
			}
		});
		btnUpload.addActionListener(new ActionListener() {
			
			

			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				 currentStatus.append("Started Execution ... " +"\n");
				 textArea.setText(currentStatus.toString());
				 dialog=new WaitDialog();
				 dialog.setVisible(true);
											
				 doactionMapreduce();
			}
		});
		btnChooseOutputLocation.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFileChooser chooser=new JFileChooser();
				
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.showOpenDialog(contentPane);
				File f=chooser.getSelectedFile();
				textFieldOutput.setText(f.getAbsolutePath()+"/OutputFolder");
				output=textFieldOutput.getText();
			}
		});
		
		btnSearchOutput.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!textFieldOutput.getText().equals(""))
				{
				SearchProduct product=new SearchProduct();
				product.setVisible(true);
				}else{
					 JOptionPane.showMessageDialog(contentPane, "Output Path Empty", "Status", JOptionPane.INFORMATION_MESSAGE);
					 
				}
			}
		});
	}
	
	private void doactionMapreduce() {
		Thread thread=new Thread(){
			  public void run() {
				  try {
						 
						 // currentStatus.append("Working Status : "+"\n");
						  //textArea.setText(currentStatus.toString());
						  JOptionPane.showMessageDialog(contentPane, "Starting File Processing ...", "Status", JOptionPane.INFORMATION_MESSAGE);
						  
						  Process p=Runtime.getRuntime().exec("sh required_files/com.sh "+input+" "+output+" required_files/productprob.jar");
						try {
						
							if(p.waitFor()==0) {
								dialog.setVisible(false);
								dialog.dispose();
								System.out.println("Entered wait for");
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
						} catch (InterruptedException e) {
							System.out.println("inside 1 "+e.getMessage());
							// TODO Auto-generated catch block
							currentStatus.append("Excution Failed !!! ...");
							textArea.setText(currentStatus.toString());
							JOptionPane.showMessageDialog(contentPane, e.getMessage().toString(), "Failed Map Reduce", JOptionPane.ERROR_MESSAGE);
							
						//	e.printStackTrace();
						}
					} catch (IOException e) {
						System.out.println("inside 2:"+e.getMessage());
						JOptionPane.showMessageDialog(contentPane, "Input File Does'nt Exist", "Input File Error", JOptionPane.ERROR_MESSAGE);
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			  
			  };
		  };
	
		  thread.start();
	}

}
