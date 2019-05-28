
package paket;
import java.util.Random;

//import required classes
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.meta.Bagging;
import weka.classifiers.meta.Stacking;
import weka.classifiers.meta.Vote;
import weka.classifiers.rules.OneR;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.NBTree;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.trees.RandomTree;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Baging {
	static Rezulat[] rezultati= new Rezulat[6];
	private static Evaluation eval;
	static {
		rezultati[0] = new Rezulat("J48","bagg");
		rezultati[1] = new Rezulat("NaiveBayes","bagg");
		rezultati[2] = new Rezulat("SMO","bagg");
		rezultati[3] = new Rezulat("NBTree","bagg");
		rezultati[4] = new Rezulat("OneR","bagg");
		rezultati[5] = new Rezulat("IBk","bagg");

		
	}
	public static int brojInst = 0;
	public static void bagg(Instances dataset) throws Exception  {
		Bagging bagger = new Bagging();
		dataset.setClassIndex(dataset.numAttributes()  - 1);
		Baging.brojInst = dataset.numInstances();
		
		
		System.out.println("=============================\n\n ========================ISPIS BAGGING REZULTATA =============\n");
		
		bagger.setClassifier(new J48());
		bagger.setNumIterations(20);
		bagger.buildClassifier(dataset);
		eval = new Evaluation(dataset);
		eval.crossValidateModel(bagger, dataset, 5, new Random(1));	
		KlasaZaOsnovnoRac.ispisiTocnost(eval, dataset.numClasses(), "J48", "ba");
		


		bagger.setClassifier(new NaiveBayes());
		bagger.setNumIterations(20);
		bagger.buildClassifier(dataset);
		eval = new Evaluation(dataset);
		eval.crossValidateModel(bagger, dataset, 5, new Random(1));	
		KlasaZaOsnovnoRac.ispisiTocnost(eval, dataset.numClasses(), "Bayes", "ba");
		
		bagger.setClassifier(new SMO());
		bagger.setNumIterations(20);
		bagger.buildClassifier(dataset);
		eval = new Evaluation(dataset);
		eval.crossValidateModel(bagger, dataset, 5, new Random(1));	
		KlasaZaOsnovnoRac.ispisiTocnost(eval, dataset.numClasses(), "SMO", "ba");
		
		bagger.setClassifier(new NBTree());
		bagger.setNumIterations(20);
		bagger.buildClassifier(dataset);
		eval = new Evaluation(dataset);
		eval.crossValidateModel(bagger, dataset, 5, new Random(1));	
		KlasaZaOsnovnoRac.ispisiTocnost(eval, dataset.numClasses(), "NBTree", "ba");
		
		bagger.setClassifier(new OneR());
		bagger.setNumIterations(20);
		bagger.buildClassifier(dataset);
		eval = new Evaluation(dataset);
		eval.crossValidateModel(bagger, dataset, 5, new Random(1));	
		KlasaZaOsnovnoRac.ispisiTocnost(eval, dataset.numClasses(), "OneR", "ba");
		
		bagger.setClassifier(new IBk());
		bagger.setNumIterations(20);
		bagger.buildClassifier(dataset);
		eval = new Evaluation(dataset);
		eval.crossValidateModel(bagger, dataset, 5, new Random(1));	
		KlasaZaOsnovnoRac.ispisiTocnost(eval, dataset.numClasses(), "IBk", "ba");
		
		
	}
}