package paket;

import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;

public class KlasaZaOsnovnoRac {
private static Evaluation eval;
private static String[] options;
//double rezultatiPrecision[] = new double[6];

static Rezulat[] rezultati= new Rezulat[6];
static {
	rezultati[0] = new Rezulat("J48","org");
	rezultati[1] = new Rezulat("NaiveBayes","org");
	rezultati[2] = new Rezulat("SMO","org");
	rezultati[3] = new Rezulat("NBTree","org");
	rezultati[4] = new Rezulat("OneR","org");
	rezultati[5] = new Rezulat("IBk","org");

	
}
public static int brojInst = 0;
public static void baznoRac(Instances dataset) throws Exception {
	dataset.setClassIndex(dataset.numAttributes() - 1);
	KlasaZaOsnovnoRac.brojInst = dataset.numInstances();
	
	eval = MikroKlasifikatori.J48(dataset, true);
	ispisiTocnost(eval, dataset.numClasses(), "J48", "os");
	
	eval = MikroKlasifikatori.NaiveBayees(dataset, true);
	ispisiTocnost(eval, dataset.numClasses(), "Bayes", "os");
	
	eval = MikroKlasifikatori.SiMO(dataset, true);
	ispisiTocnost(eval, dataset.numClasses(), "SMO", "os");
	
	eval = MikroKlasifikatori.NBTree(dataset, true);
	ispisiTocnost(eval, dataset.numClasses(), "NBTree", "os");
	
	eval = MikroKlasifikatori.IBk(dataset, true);
	ispisiTocnost(eval, dataset.numClasses(), "IBk", "os");
	
	eval = MikroKlasifikatori.OneR(dataset, true);
	ispisiTocnost(eval, dataset.numClasses(), "OneR", "os");
	
	
}
static void ispisiTocnost(Evaluation eval, int len,String ime,String ada){
	double sumf = 0, sump = 0, sumacc = 0;
	System.out.println("========================");

	for(int i = 0; i < len; i++) {
		sumf += eval.fMeasure(i);
		sump += eval.precision(i);
		if(ada.contentEquals("ab")) {
			sumacc += (eval.numTruePositives(i) + eval.numTrueNegatives(i)) / (AdaBoost.brojInst);
			
		}else if(ada.contentEquals("ba")) {
			sumacc += (eval.numTruePositives(i) + eval.numTrueNegatives(i)) / (Baging.brojInst);
		}
		else {
			sumacc += (eval.numTruePositives(i) + eval.numTrueNegatives(i)) / (KlasaZaOsnovnoRac.brojInst);
			
		}
		//System.out.println(sumf + " "+ sump);
		}
	switch(ime) {
	case "Bayes":
		System.out.println("Rezultati za Bayes\n");
		if(ada.contentEquals("ab")) {
			System.out.println("racuna adabo");
			AdaBoost.rezultati[1].fscore = sumf / len;
			AdaBoost.rezultati[1].accuracy = sumacc / len;
		}else if(ada.contentEquals("ba")) {
			System.out.println("racuna ba");
			Baging.rezultati[1].fscore = sumf / len;
			Baging.rezultati[1].accuracy = sumacc / len;
		}
		else {
			System.out.println("racuna os");
			rezultati[1].fscore = sumf / len;
			rezultati[1].accuracy = sumacc / len;
		}
		
		break;
	
	case "SMO":
		System.out.println("Rezultati za SMO\n");
		if(ada.contentEquals("ab")) {
			System.out.println("racuna adabo");
			AdaBoost.rezultati[2].fscore = sumf / len;
			AdaBoost.rezultati[2].accuracy = sumacc / len;
		}else if(ada.contentEquals("ba")) {
			System.out.println("racuna ba");
			Baging.rezultati[2].fscore = sumf / len;
			Baging.rezultati[2].accuracy = sumacc / len;
		}
		else {
			System.out.println("racuna os");
			rezultati[2].fscore = sumf / len;
			rezultati[2].accuracy = sumacc / len;
		}
		
		break;
	
	case "NBTree":
		System.out.println("Rezultati za NBTree\n");
		if(ada.contentEquals("ab")) {
			System.out.println("racuna adabo");
			AdaBoost.rezultati[3].fscore = sumf / len;
			AdaBoost.rezultati[3].accuracy = sumacc / len;
		}else if(ada.contentEquals("ba")) {
			System.out.println("racuna ba");
			Baging.rezultati[3].fscore = sumf / len;
			Baging.rezultati[3].accuracy = sumacc / len;
		}
		else {
			System.out.println("racuna os");
			rezultati[3].fscore = sumf / len;
			rezultati[3].accuracy = sumacc / len;
		}
		
		break;
	
	case "J48":
		System.out.println("Rezultati za J48\n");
		if(ada.contentEquals("ab")) {
			AdaBoost.rezultati[0].fscore = sumf / len;
			AdaBoost.rezultati[0].accuracy = sumacc / len;
		}else if(ada.contentEquals("ba")) {
			System.out.println("racuna ba");
			Baging.rezultati[0].fscore = sumf / len;
			Baging.rezultati[0].accuracy = sumacc / len;
		}
		else {
			System.out.println("racuna os");
			rezultati[0].fscore = sumf / len;
			rezultati[0].accuracy = sumacc / len;
		}
		
				break;
		
	case "OneR":
		System.out.println("Rezultati za OneR\n");
		if(ada.contentEquals("ab")) {
			System.out.println("racuna adabo");
			AdaBoost.rezultati[4].fscore = sumf / len;
			AdaBoost.rezultati[4].accuracy = sumacc / len;
		}else if(ada.contentEquals("ba")) {
			System.out.println("racuna ba");
			Baging.rezultati[4].fscore = sumf / len;
			Baging.rezultati[4].accuracy = sumacc / len;
		}
		else {
			System.out.println("racuna os");
			rezultati[4].fscore = sumf / len;
			rezultati[4].accuracy = sumacc / len;

		}
		
		break;
		
	case "IBk":
		System.out.println("Rezultati za IBk\n");
		if(ada.contentEquals("ab")) {
			System.out.println("racuna adabo");
			AdaBoost.rezultati[5].fscore = sumf / len;
			AdaBoost.rezultati[5].accuracy = sumacc / len;
		}else if(ada.contentEquals("ba")) {
			System.out.println("racuna ba");
			Baging.rezultati[5].fscore = sumf / len;
			Baging.rezultati[5].accuracy = sumacc / len;
		}
		else {
			System.out.println("racuna os");
			rezultati[5].fscore = sumf / len;
			rezultati[5].accuracy = sumacc / len;
		}
		break;
}
	System.out.println("FMeasure: "+ sumf / len +"\nPrecision: "+ sump / len);
	}

}
