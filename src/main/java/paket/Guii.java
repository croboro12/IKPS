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
	private JButton btnObradi = new JButton("Obradi");
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
		btnObradi.setEnabled(false);

		textArea = new TextArea();
		textArea.setBounds(16, 34, 293, 115);
		
		final JButton btnUcitaj = new JButton("Ucitaj");
		btnUcitaj.setBounds(291, 5, 89, 23);
		contentPane.add(btnUcitaj);
		btnUcitaj.setEnabled(false); // ovo stavi da je zatamnjeno dok se ne ucita i trening i test podaci
		
		btnUcitaj.addActionListener(new ActionListener() {  // ucitaj button
			  public void actionPerformed(ActionEvent e) { 
				 try {
					ucitavanje = new UcitajPodatke(pathtrening,pathtest); // poziva objekt i ucitava podatke
					textArea.setText("Podaci spremni"); //ispis da su podaci spremni
					btnUcitaj.setEnabled(false); // kad su podaci spremljeni ne moze se nista pozivati dok se novi odaberu
					btnObradi.setEnabled(true);
					
					
				 } catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}} );
		
		JButton btnUcitajPodatke = new JButton("Trening"); //ucitava trening podatke
		btnUcitajPodatke.setBounds(16, 5, 89, 23);
		btnUcitajPodatke.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				 fc = new JFileChooser();
				  returnVal = fc.showOpenDialog(fc);
				
				    if (returnVal == JFileChooser.APPROVE_OPTION) {
				        file = fc.getSelectedFile();
				        // What to do with the file, e.g. display it in a TextArea
				       	pathtrening = file.getAbsolutePath().toString(); //sprema path od treninga
				       
				        try {
				        	if (pathtrening.endsWith(".arff")) { // ako path treninga fajl podataka zavrsava sa arff
				        		
				        		textArea.append("\nTrening je odabran: "+pathtrening);//ispis puta treninga
				        		treningvalja = 1;//flag da li format trening podataka valja
				        		}else {
				        			textArea.append("\nFormat treninga nije valjan\n"); // oznaka da valja
				        			treningvalja = 0; // format nije zadovoljen
				        			btnObradi.setEnabled(false);
				        		}
							
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				    } else {
				        System.out.println("File access cancelled by user.");
				    }
				    if(testvalja==1 && treningvalja==1) {
						btnUcitaj.setEnabled(true);//gledal dal moze enablat buton
					}else {
						btnUcitaj.setEnabled(false);	//siv button
			  		}
				  } 
				} );
		
		
		
		Canvas canvas = new Canvas();
		canvas.setBounds(5, 5, 0, 0);
		
		JPanel panel = new JPanel();
		panel.setBounds(412, 90, 22, 175);
		contentPane.setLayout(null);
		contentPane.add(textArea);
		contentPane.add(btnUcitajPodatke);
		contentPane.add(canvas);
		contentPane.add(panel);
		
		JButton btnUcitajTest = new JButton("Test");
		btnUcitajTest.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  fc = new JFileChooser();
				  returnVal = fc.showOpenDialog(fc);
				
				    if (returnVal == JFileChooser.APPROVE_OPTION) {
				        file = fc.getSelectedFile();
				        // What to do with the file, e.g. display it in a TextArea
				       pathtest = file.getAbsolutePath().toString();
				    
				        try {
				        	if (pathtest.endsWith(".arff")) { // ako zavrsava sa arff onda je priznat 
				        		
				        		textArea.append("\nTest je odabran: "+pathtest); // ispis puta testa
				        		testvalja = 1; //format testa valja
				        		
				        		}else {
				        			textArea.append("\nFormat testa nije valjan\n");
				        			testvalja = 0; //format testa ne valja
				        			btnObradi.setEnabled(false);
				        		}
							
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				    } else {
				        System.out.println("File access cancelled by user.");
				    }
				    if(testvalja==1 && treningvalja==1) {
						btnUcitaj.setEnabled(true); // gleda dal moze enableat button za uctiavanjae
					}else{
						btnUcitaj.setEnabled(false); //ako ne valja gumb je siv
					}
				  } 
				} );
		btnUcitajTest.setBounds(161, 5, 89, 23);
		contentPane.add(btnUcitajTest);
		
		JButton btnCsvarff = new JButton("CSV-ARFF");//pretvorba csv u arff format
		btnCsvarff.setBounds(16, 161, 113, 23);
		contentPane.add(btnCsvarff);
		
		final JButton btntocnosti = new JButton("Prikazi graf tocnosti");
		btntocnosti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
						 Graph(poljetocnosti,geomtocnosto,brojactresh,xos);
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btntocnosti.setEnabled(false);
		btntocnosti.setBounds(138, 161, 113, 23);
		contentPane.add(btntocnosti);
		
		
		btnObradi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					 if(testvalja==1 && treningvalja==1) {
						 ThresholdRad.manipuliraj(ucitavanje);
						 btntocnosti.setEnabled(true);
						
					 }
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnObradi.setBounds(16, 195, 89, 23);
		contentPane.add(btnObradi);
		
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
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnReset.setBounds(138, 195, 89, 23);
		contentPane.add(btnReset);
		
		JButton btnAlokacija = new JButton("TreningAlokatora");
		btnAlokacija.setBounds(261, 161, 89, 23);
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
					        		
				        		}else {
				        			textArea.append("\nFormat podataka nije valjan\n");
				        			
				        		}
							
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				    } else {
				        System.out.println("File access cancelled by user.");
				    }
				    
				  } 
				} );
		contentPane.add(btnAlokacija);
		
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
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				    } else {
				        System.out.println("File access cancelled by user.");
				    }
				    
				  } 
				} );
		
		
	}
	public static void Graph(Double poljetocnosti[], Double geomtocnosto[],int brojactresh,Double xos[]) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
		for(int it = 0; it < brojactresh; it++) {
			dataset.setValue(new Double(poljetocnosti[it]), "Values", String.valueOf(it)); //spremanje rijednosti na y i x os
			dataset1.setValue(new Double(geomtocnosto[it]), "Values", String.valueOf(xos[it]));
		//	System.out.print(xos[it]);
		}
		
		//izrada i prikaz grafa
		
		JFreeChart chart = ChartFactory.createLineChart("Toènost", "Broj treshold granice", "Postotak toènosti", dataset,PlotOrientation.VERTICAL, false, true, false);
		JFreeChart chart1 = ChartFactory.createLineChart("Geometrijska tocnost", "Omjer jedinica", "Postotak toènosti", dataset1,PlotOrientation.VERTICAL, false, true, false);
		CategoryPlot p = chart.getCategoryPlot();
		p.setRangeGridlinePaint(Color.BLUE);
		ChartFrame frame = new ChartFrame("Graf tocnosti",chart);
		frame.setVisible(true);
		frame.setSize(450,350);
		
		CategoryPlot p1 = chart1.getCategoryPlot();
		p1.setRangeGridlinePaint(Color.BLUE);
		ChartFrame frame1 = new ChartFrame("Graf tocnosti",chart1);
		frame1.setVisible(true);
		frame1.setSize(450,350);
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
