package paket;

import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.rules.OneR;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.NBTree;
import weka.core.Instances;

public class AdaBoost {
	static Rezulat[] rezultati= new Rezulat[6];
	private static Evaluation eval;
	static {
		rezultati[0] = new Rezulat("J48","org");
		rezultati[1] = new Rezulat("NaiveBayes","org");
		rezultati[2] = new Rezulat("SMO","org");
		rezultati[3] = new Rezulat("NBTree","org");
		rezultati[4] = new Rezulat("OneR","org");
		rezultati[5] = new Rezulat("IBk","org");

		
	}
	public static int brojInst = 0;
	public static void AdaBoo(Instances dataset) throws Exception{//AdaBoost .. 
		AdaBoostM1 m1 = new AdaBoostM1();
		dataset.setClassIndex(dataset.numAttributes()  - 1);
		AdaBoost.brojInst = dataset.numInstances();
		m1.setNumIterations(20);
		m1.buildClassifier(dataset);
		eval = new Evaluation(dataset);
		
		System.out.println("=============================\n\n ========================ISPIS ADABOOST REZULTATA =============\n");
		m1.setClassifier(new J48());
		
		eval.crossValidateModel(m1, dataset, 5, new Random(1));	
		KlasaZaOsnovnoRac.ispisiTocnost(eval, dataset.numClasses(), "J48", "ab");
		


		m1.setClassifier(new NaiveBayes());
		m1.setNumIterations(20);
		m1.buildClassifier(dataset);
		eval = new Evaluation(dataset);
		eval.crossValidateModel(m1, dataset, 5, new Random(1));	
		KlasaZaOsnovnoRac.ispisiTocnost(eval, dataset.numClasses(), "Bayes", "ab");
		
		
		m1.setClassifier(new SMO());
		m1.setNumIterations(20);
		m1.buildClassifier(dataset);
		eval = new Evaluation(dataset);
		eval.crossValidateModel(m1, dataset, 5, new Random(1));	
		KlasaZaOsnovnoRac.ispisiTocnost(eval, dataset.numClasses(), "SMO", "ab");
		
		
		
		m1.setClassifier(new NBTree());
		m1.setNumIterations(20);
		m1.buildClassifier(dataset);
		eval = new Evaluation(dataset);
		eval.crossValidateModel(m1, dataset, 5, new Random(1));	
		KlasaZaOsnovnoRac.ispisiTocnost(eval, dataset.numClasses(), "NBTree", "ab");
		
		
		m1.setClassifier(new OneR());
		m1.setNumIterations(20);
		m1.buildClassifier(dataset);
		eval = new Evaluation(dataset);
		eval.crossValidateModel(m1, dataset, 5, new Random(1));	
		KlasaZaOsnovnoRac.ispisiTocnost(eval, dataset.numClasses(), "OneR", "ab");
		
		
		m1.setClassifier(new IBk());
		m1.setNumIterations(20);
		m1.buildClassifier(dataset);
		eval = new Evaluation(dataset);
		eval.crossValidateModel(m1, dataset, 5, new Random(1));	
		KlasaZaOsnovnoRac.ispisiTocnost(eval, dataset.numClasses(), "IBk", "ab");
	}
	
}
