package paket;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Objects;
import java.awt.event.ActionEvent;

public class CSVBiranje extends JFrame {

	protected static final String File = null;
	private JPanel contentPane;
	private JTextField textField;
	private int nevalja = 0;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public CSVBiranje(final String putcsv,final String pathdir) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 328, 135);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);

		JLabel label = new JLabel("");
		
		JLabel lblNewLabel = new JLabel("Unesi ime novog arff fajla");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JButton btnSpremi = new JButton("Spremi");
		btnSpremi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File folder = new File(pathdir); //otvara direktorij u kojem je csv fajl
				File[] listOfFiles = folder.listFiles(); //uzima listu fajlova

				    for (int i = 0; i < listOfFiles.length; i++) {
				    	//System.out.println(listOfFiles[i].getName());
				    	//System.out.println(textField.getText()+".arff");
				      if (Objects.equals(listOfFiles[i].getName() , (textField.getText()+".arff"))) { // gledmao dal imamo sa istim imenom ako ga nece pretovrit, ako ne pretvara
				        nevalja = 1; // ako je ovdje imamo fajl sa istim imenom
				     //   System.out.println("nasao");
				      }
				      //System.out.println("uso");
				    }
				    if(nevalja == 0) {
				    	try {
				    	//	System.out.println("pretvordba");
							
				    		UcitajPodatke.pretvorba(putcsv,pathdir,textField.getText()); // salje ime fajla, ime direktorija, i ime novog fajla
				    		setVisible(false); // gasenje prozora
				    	} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				    }else{
				    Guii.Pisi("Ime zauzeto");	//pisanje po textboxu drugog jframea
				    }
				   
				    
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 424, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(86)
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(105)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(btnSpremi, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(textField, Alignment.LEADING))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnSpremi)
					.addGap(46)
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))
		);
		contentPane.setLayout(gl_contentPane);
	}

}
