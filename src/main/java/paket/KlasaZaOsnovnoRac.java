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
	ispisiTocnost(eval, dataset.numClasses(), "J48", false);
	
	eval = MikroKlasifikatori.NaiveBayees(dataset, true);
	ispisiTocnost(eval, dataset.numClasses(), "Bayes", false);
	
	eval = MikroKlasifikatori.SiMO(dataset, true);
	ispisiTocnost(eval, dataset.numClasses(), "SMO", false);
	
	eval = MikroKlasifikatori.NBTree(dataset, true);
	ispisiTocnost(eval, dataset.numClasses(), "NBTree", false);
	
	eval = MikroKlasifikatori.IBk(dataset, true);
	ispisiTocnost(eval, dataset.numClasses(), "IBk", false);
	
	eval = MikroKlasifikatori.OneR(dataset, true);
	ispisiTocnost(eval, dataset.numClasses(), "OneR", false);
	
	
}
static void ispisiTocnost(Evaluation eval, int len,String ime,boolean ada){
	double sumf = 0, sump = 0, sumacc = 0;
	System.out.println("========================");

	for(int i = 0; i < len; i++) {
		sumf += eval.fMeasure(i);
		sump += eval.precision(i);
		if(ada) {
			sumacc += (eval.numTruePositives(i) + eval.numTrueNegatives(i)) / (AdaBoost.brojInst);
			
		}else {
			sumacc += (eval.numTruePositives(i) + eval.numTrueNegatives(i)) / (KlasaZaOsnovnoRac.brojInst);
			
		}
		//System.out.println(sumf + " "+ sump);
		}
	switch(ime) {
	case "Bayes":
		System.out.println("Rezultati za Bayes\n");
		if(ada) {
			AdaBoost.rezultati[1].fscore = sumf / len;
			AdaBoost.rezultati[1].accuracy = sumacc / len;
		}else {
			rezultati[1].fscore = sumf / len;
			rezultati[1].accuracy = sumacc / len;
		}
		
		break;
	
	case "SMO":
		System.out.println("Rezultati za SMO\n");
		if(ada) {
			AdaBoost.rezultati[2].fscore = sumf / len;
			AdaBoost.rezultati[2].accuracy = sumacc / len;
		}else {
			rezultati[2].fscore = sumf / len;
			rezultati[2].accuracy = sumacc / len;
		}
		
		break;
	
	case "NBTree":
		System.out.println("Rezultati za NBTree\n");
		if(ada) {
			AdaBoost.rezultati[3].fscore = sumf / len;
			AdaBoost.rezultati[3].accuracy = sumacc / len;
		}else {
			rezultati[3].fscore = sumf / len;
			rezultati[3].accuracy = sumacc / len;
		}
		
		break;
	
	case "J48":
		System.out.println("Rezultati za J48\n");
		if(ada) {
			AdaBoost.rezultati[0].fscore = sumf / len;
			AdaBoost.rezultati[0].accuracy = sumacc / len;
		}else {
			rezultati[0].fscore = sumf / len;
			rezultati[0].accuracy = sumacc / len;
		}
		
				break;
		
	case "OneR":
		System.out.println("Rezultati za OneR\n");
		if(ada) {
			AdaBoost.rezultati[4].fscore = sumf / len;
			AdaBoost.rezultati[4].accuracy = sumacc / len;
		}else {
			rezultati[4].fscore = sumf / len;
			rezultati[4].accuracy = sumacc / len;

		}
		
		break;
		
	case "IBk":
		System.out.println("Rezultati za IBk\n");
		if(ada) {
			AdaBoost.rezultati[5].fscore = sumf / len;
			AdaBoost.rezultati[5].accuracy = sumacc / len;
		}else {
			rezultati[5].fscore = sumf / len;
			rezultati[5].accuracy = sumacc / len;
		}
		break;
}
	System.out.println("FMeasure: "+ sumf / len +"\nPrecision: "+ sump / len);
	}

}
