package paket;

import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.SMOreg;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.supervised.attribute.NominalToBinary;
import weka.filters.unsupervised.attribute.Remove;

public class Regresija {
	private String path;
	private static Instances dataset,testDataset;
	private static SMOreg smo;
	private static double actualValue,predSMO;
	public static void regresija(Instances podaci, Instances test) throws Exception {
		
		dataset = podaci; // prima podatke od UCitajPodatke
			
		//build model
		/*smo = new SMOreg(); //gradi model smo
		smo.buildClassifier(dataset);*/
		J48 tree = new J48();
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
		//ispisi
		//System.out.println(smo);
		//load new dataset
				testDataset = test;	 // uzima od objekta test podatke ucitane
			
				//loop through the new dataset and make predictions
				Izvjestaj.Pisi("===================");
				Izvjestaj.Pisi("Actual Class, SMO Predicted");
				for (int i = 0; i < testDataset.numInstances(); i++) {
					//get class double value for current instance
					actualValue = testDataset.instance(i).value(testDataset.numAttributes()-1); //vrijednost instance u class indeksu
					
					//get Instance object of current instance
					Instance newInst = testDataset.instance(i);
					//call classifyInstance, which returns a double value for the class
					predSMO = fc.classifyInstance(newInst);
				//	testDataset.instance(i).setClassValue(predSMO);
					//System.out.println(actualValue+", "+predSMO);
					Izvjestaj.Pisi(String.valueOf(actualValue)+" "+String.valueOf(predSMO)+"\n");
				}	
				
				
				
				Evaluation eval = new Evaluation(dataset);
				Random rand = new Random(1);
				int folds = 2;
				
				//Notice we build the classifier with the training dataset
		        //we initialize evaluation with the training dataset and then
		        //evaluate using the test dataset

				//test dataset for evaluation

			
				
				//now evaluate model
				eval.evaluateModel(fc, testDataset);
			//	eval.crossValidateModel(fc, testDataset, folds, rand);
				//System.out.println(eval.toSummaryString("Evaluation results:\n", false));
				Izvjestaj.Pisi(eval.toSummaryString("Rezultati\n",false));
				Izvjestaj.Pisi(String.valueOf(eval.pctCorrect()));
				Izvjestaj.Pisi(String.valueOf(eval.pctIncorrect()));
				//System.out.println("Correct % = "+eval.pctCorrect());
				//System.out.println("Incorrect % = "+eval.pctIncorrect());
				
				Guii.poljetocnosti[Guii.brojactoc] = eval.pctCorrect();
				Guii.geomtocnosto[Guii.brojactoc++] = Math.sqrt(eval.truePositiveRate(0)*eval.trueNegativeRate(0));
				//the confusion matrix
				System.out.println( Math.sqrt(eval.truePositiveRate(0)*eval.falsePositiveRate(0)));
				Izvjestaj.Pisi(eval.toMatrixString("Confusion Matrix\n"));
	}
}
