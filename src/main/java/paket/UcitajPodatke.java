package paket;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.swing.JFileChooser;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

public class UcitajPodatke {
	private BufferedReader breader;
	private String srctrening,srctest;
	Instances train;
	Instances test;
	private static CSVLoader loader;
	private static ArffSaver saver;
	public UcitajPodatke(String sourcetrening, String sourcetest) {
		this.srctrening = sourcetrening;
		this.srctest = sourcetest;
		try {
			ucitaj();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void ucitaj() throws Exception {
		breader = new BufferedReader(new FileReader(this.srctrening)); // ucitava trening podatke
		train = new Instances(breader);
		train.setClassIndex(train.numAttributes()-1);//odredi klass atribut
		//System.out.println(train.instance(1).value(train.numAttributes()-1));
		breader = new BufferedReader(new FileReader(this.srctest));//test podaci
		test = new Instances(breader);
		test.setClassIndex(train.numAttributes()-1); // klass atribut
		System.out.println("haha"); // zastavica za provjeru nista obrisat cemo
		breader.close();
	}
	public static void pretvorba(String csvpath,String csvdir, String ime) throws Exception {
		loader = new CSVLoader(); //ucita csv
		loader.setSource(new File(csvpath)); // uzima path od fajla 
		Instances data = loader.getDataSet(); //uzme podatke
	
		saver = new ArffSaver(); // saver kao arff
		saver.setInstances(data); //postavi podatke
		saver.setFile(new File(csvdir+"/"+ime+".arff"));//path od direktorija i ime koje zelimo sa nastavkom arff spremamo
		saver.writeBatch();
		
	}
}
