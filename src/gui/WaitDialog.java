package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WaitDialog extends JFrame {

	private JPanel contentPane;
	private Thread thread1;
	private JLabel lblMapReduceRunning;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WaitDialog frame = new WaitDialog();
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
	public WaitDialog() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 892, 207);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(102, 102, 153));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblMapReduceRunning = new JLabel("Map Reduce Running , Please Wait ...");
		lblMapReduceRunning.setForeground(new Color(255, 255, 255));
		lblMapReduceRunning.setFont(new Font("Calibri", Font.BOLD, 24));
		lblMapReduceRunning.setBounds(113, 59, 724, 40);
		contentPane.add(lblMapReduceRunning);
		
		try {
			BufferedImage img = ImageIO.read(new File("images/clocks.png"));
			JLabel lblNewLabel = new JLabel(new ImageIcon(img));
			lblNewLabel.setBounds(33, 44, 68, 73);
			contentPane.add(lblNewLabel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		thread1=new Thread(){
			int i=0;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//super.run();
				try {
					this.sleep(1000);;
					//System.out.println("Map Reduce Running , Please Wait ... "+(++i)+" secs");
					lblMapReduceRunning.setText("Map Reduce Running , Please Wait ... "+(++i)+" secs");
					//lblMapReduceRunning.repaint();
					run();	
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
				//currentStatus.append("Map Reduce Running ... PLease Wait ..."+(++i)+"\n");
				
			}
		};
		thread1.start();
	}

}
