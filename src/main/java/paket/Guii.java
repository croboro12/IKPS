package paket;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.awt.TextArea;
import java.awt.Canvas;
import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import weka.core.FastVector;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import javax.swing.JTextField;

public class Guii extends JFrame {
	private int testvalja=0,treningvalja=0,returnVal;
	private String pathtrening,pathtest,pathcsv,pathdir, pathalloc;
	private JPanel contentPane;
	private  File file;
	private JFileChooser fc;
	private static TextArea textArea;
	private UcitajPodatke ucitavanje;
	public static Double[] poljetocnosti = new Double[10];
	public static Double[] xos = new Double[10];
	public static Double[] geomtocnosto = new Double[10];
	public static int brojactoc = 0, brojactresh = 8;
	JButton btnReset = new JButton("Reset");
	/**
	 * Launch the application.t
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Guii frame = new Guii(); // ovo je od buildera nista bitno samo pokretanje 
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
	public Guii() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);

		textArea = new TextArea();
		textArea.setBounds(16, 34, 293, 115);
		
		
		
		Canvas canvas = new Canvas();
		canvas.setBounds(5, 5, 0, 0);
		
		JPanel panel = new JPanel();
		panel.setBounds(412, 90, 22, 175);
		contentPane.setLayout(null);
		contentPane.add(textArea);
		
		contentPane.add(canvas);
		contentPane.add(panel);
		
		JButton btnCsvarff = new JButton("CSV-ARFF");//pretvorba csv u arff format
		btnCsvarff.setBounds(16, 161, 113, 23);
		contentPane.add(btnCsvarff);
		
		final JButton btntocnosti = new JButton("Graf Fmeasure");
		btntocnosti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					Graph("fmeasure");

					
				} catch (Exception e1) {
				
					e1.printStackTrace();
				}
			}
		});
		btntocnosti.setEnabled(false);
		btntocnosti.setBounds(139, 161, 113, 23);
		contentPane.add(btntocnosti);
		
		JButton btnOcistiProzor = new JButton("Ocisti prozor");
		btnOcistiProzor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
			}
		});
		btnOcistiProzor.setBounds(315, 39, 89, 23);
		contentPane.add(btnOcistiProzor);
		
		JButton btnMajoriti = new JButton("MajoritiMinority");
		btnMajoriti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
			}
		});
		btnMajoriti.setBounds(315, 10, 89, 23);
		contentPane.add(btnOcistiProzor);
		
		
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					/*dispose();
			        Guii game = new Guii();
			        game.setVisible(true);*/
				try {
					restartApplication();
				} catch (URISyntaxException | IOException e1) {
					
					e1.printStackTrace();
				}
			}
		});
		btnReset.setBounds(138, 195, 89, 23);
		contentPane.add(btnReset);
		
		JButton btnAlokacija = new JButton("Alokacijska Metoda");
		btnAlokacija.setBounds(16, 5, 132, 23);
		btnAlokacija.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				  fc = new JFileChooser();
				  returnVal = fc.showOpenDialog(fc);
				
				    if (returnVal == JFileChooser.APPROVE_OPTION) {
				        file = fc.getSelectedFile();
				        // What to do with the file, e.g. display it in a TextArea
				       pathalloc = file.getAbsolutePath().toString(); //uzima path od fajla
				      
				        try {
				        		if (pathalloc.endsWith(".arff")) { // ako zavrsava sa arff onda je priznat 
					        		
					        		textArea.append("\nSkup podataka odabran: " + pathalloc); // ispis puta testa
					        		MajorityMin.alloc(pathalloc);
					        		DataSource source = new DataSource(pathalloc);
									KlasaZaOsnovnoRac.baznoRac(new Instances(source.getDataSet()));
									btntocnosti.setEnabled(true);
					        		AdaBoost.AdaBoo(new Instances(source.getDataSet()));
				        		}else {
				        			textArea.append("\nFormat podataka nije valjan\n");
				        			
				        		}
							
						} catch (Exception e1) {
							
							e1.printStackTrace();
						}
				    } else {
				        System.out.println("File access cancelled by user.");
				    }
				    
				  } 
				} );
		contentPane.add(btnAlokacija);
		
		JButton btnGrafAccuracy = new JButton("Graf Accuracy");
		btnGrafAccuracy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					Graph("accuracy");

					
				} catch (Exception e1) {
				
					e1.printStackTrace();
				}
			}
		});
		btntocnosti.setEnabled(false);
		btnGrafAccuracy.setBounds(264, 161, 89, 23);
		contentPane.add(btnGrafAccuracy);
		
		btnCsvarff.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  fc = new JFileChooser();
				  returnVal = fc.showOpenDialog(fc);
				
				    if (returnVal == JFileChooser.APPROVE_OPTION) {
				        file = fc.getSelectedFile();
				        // What to do with the file, e.g. display it in a TextArea
				       pathcsv = file.getAbsolutePath().toString(); //uzima path od fajla
				       pathdir = file.getParentFile().toString(); // uzima path od direktorija tako da zna di ce spremit poslije
				        try {
				        	if (pathcsv.endsWith(".csv")) { // ako zavrsava sa csv
				        		
				        		CSVBiranje prozor = new CSVBiranje(pathcsv,pathdir); // pozivanje novog jframea sa putevima fajla i direkotija
				        		//textArea.setText(pathcsv+pathdir);
				        		prozor.setVisible(true); // posatavi da vidimo novi jframe
				        		}else {
				        			textArea.append("\nFormat CSV nije valjan\n"); // format za pretvorbu ne valja
				        		
				        		}
							
						} catch (Exception e1) {
							
							e1.printStackTrace();
						}
				    } else {
				        System.out.println("File access cancelled by user.");
				    }
				    
				  } 
				} );
		
		
	}
	public static void Graph(String ime) {
		if(ime == "fmeasure") {
			BarChart_AWT chart = new BarChart_AWT("Fmeasure", 
			        "Podaci Fmeasure");
			      chart.pack( );        
			      RefineryUtilities.centerFrameOnScreen( chart );        
			      chart.setVisible( true ); 
		}else if(ime == "accuracy"){
			BarChart_AWT chart = new BarChart_AWT("Accuracy", 
			         "Podaci Accuracy");
			      chart.pack( );        
			      RefineryUtilities.centerFrameOnScreen( chart );        
			      chart.setVisible( true ); 
		}
		
		
	}
	public static void Pisi(String tekst) {
		textArea.appendText(tekst); // sluzi za pisanje po textboxu iz drugih klasa
	}
	public void restartApplication() throws URISyntaxException, IOException
	{
	  final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
	  final File currentJar = new File(Guii.class.getProtectionDomain().getCodeSource().getLocation().toURI());

	  /* is it a jar file? */
	  if(!currentJar.getName().endsWith(".jar"))
	    return;

	  /* Build command: java -jar application.jar */
	  final ArrayList<String> command = new ArrayList<String>();
	  command.add(javaBin);
	  command.add("-jar");
	  command.add(currentJar.getPath());

	  final ProcessBuilder builder = new ProcessBuilder(command);
	  builder.start();
	  System.exit(0);
	}
}
