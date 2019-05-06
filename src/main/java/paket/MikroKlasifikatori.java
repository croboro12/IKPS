package paket;

import weka.classifiers.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.filters.supervised.attribute.NominalToBinary;

public class MikroKlasifikatori {
	static double[][] rezultatij48 = new double[2][2];
	static double novin[][];
	static double novia[][];
	static void obradaNormalnihiAnomalnih() throws Exception {
		//J48 Obrada
		rezultatij48 = new double[2][2];
		novin = J48(new Instances(NormalAnomal.normal));
		novia = J48(new Instances(NormalAnomal.anomal));
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 2; j++) {
				rezultatij48[i][j] = (novin[i][j] + novia[i][j]);
			}
		}
		MajorityMin.rezultati[0].racunaj(rezultatij48);
		
		/*double[][] rezultatijnb = new double[2][2];
		novin = NaiveBayees(NormalAnomal.normal);
		novia =  NaiveBayees(NormalAnomal.anomal);
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 2; j++) {
				rezultatijnb[i][j] = (novin[i][j] + novia[i][j]);
			}
		}
		MajorityMin.rezultati[1].racunaj(rezultatijnb);*/
		/*
		double[][] rezultatismo = new double[2][2];
		novin = SiMO(NormalAnomal.normal);
		novia =  SiMO(NormalAnomal.anomal);
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 2; j++) {
				rezultatismo[i][j] = (novin[i][j] + novia[i][j]);
			}
		}
		MajorityMin.rezultati[2].racunaj(rezultatismo);
		*/
		}
	private static double[][] NaiveBayees(Instances dataset) throws Exception {
		NaiveBayes bayes = new NaiveBayes();
		//System.out.println(dataset.toSummaryString());
		//the filter
		NominalToBinary remove = new NominalToBinary();
		//remove.setAttributeIndices("1");
		String[] opts = new String[]{ "-N"};
		//set the filter options
		remove.setOptions(opts);

		//Create the FilteredClassifier object
		FilteredClassifier fc = new FilteredClassifier();
		//specify filter
		fc.setFilter(remove);
		//specify base classifier
		fc.setClassifier(bayes);
		//Build the meta-classifier
		fc.buildClassifier(dataset);
		Evaluation eval = new Evaluation(dataset);
		eval.evaluateModel(fc, dataset);
		System.out.println(eval.toMatrixString());
		double rez[][] = eval.confusionMatrix();
		return rez;
	}
	private static double[][] J48(Instances dataset) throws Exception {
		System.out.println("BROJ instanca" + dataset.numInstances());
		J48 tree = new J48();
		//System.out.println(dataset.toSummaryString());
		//the filter
		NominalToBinary remove = new NominalToBinary();
		//remove.setAttributeIndices("1");
		String[] opts = new String[]{ "-N"};
		//set the filter options
		remove.setOptions(opts);

		//Create the FilteredClassifier object
		FilteredClassifier fc = new FilteredClassifier();
		//specify filter
		fc.setFilter(remove);
		//specify base classifier
		fc.setClassifier(tree);
		//Build the meta-classifier
		fc.buildClassifier(dataset);
		Evaluation eval = new Evaluation(dataset);
		eval.evaluateModel(fc, dataset);
		System.out.println(eval.toMatrixString());
		double rez[][] = eval.confusionMatrix();
		return rez;
	}
	
	private static double[][] SiMO(Instances dataset) throws Exception {
		SMO smo = new SMO();
		//System.out.println(dataset.toSummaryString());
		//the filter
		NominalToBinary remove = new NominalToBinary();
		//remove.setAttributeIndices("1");
		String[] opts = new String[]{ "-N"};
		//set the filter options
		remove.setOptions(opts);

		//Create the FilteredClassifier object
		FilteredClassifier fc = new FilteredClassifier();
		//specify filter
		fc.setFilter(remove);
		//specify base classifier
		fc.setClassifier(smo);
		//Build the meta-classifier
		fc.buildClassifier(dataset);
		Evaluation eval = new Evaluation(dataset);
		eval.evaluateModel(fc, dataset);
		double rez[][] = eval.confusionMatrix();
		return rez;
	}
}
