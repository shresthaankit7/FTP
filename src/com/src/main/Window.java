package com.src.main;




import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;



import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

public class Window extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JFileChooser chooser;
	public JFrame dialogChooser;
	public File file;
	
	public SocketClient sc;
	
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/
	
	
	
	/**
	 * Create the frame.
	 */
	
	public Window(SocketClient s){
		this.sc = s;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.setBounds(3, 81, 116, 25);
		contentPane.add(btnBrowse);
		
		
		//textField.addActionListener(this);
		btnBrowse.addActionListener(this);
	}
	
			
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object src = e.getSource();
		
		if( src instanceof JButton ){	//Browse clicked
			JFileChooser fc = new JFileChooser(); 
			int returnVal = fc.showDialog(null, "SELECT FILE");
			if( returnVal == JFileChooser.APPROVE_OPTION){
					this.file = fc.getSelectedFile();
					try {
						this.sc.getFile(this.file.getName());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}else{
				//unable to select file 
			}
		}
	}
}
