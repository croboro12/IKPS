package paket;
import java.util.ArrayList;
import java.util.List;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.meta.Bagging;
import weka.classifiers.meta.Stacking;
import weka.classifiers.meta.Vote;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.trees.RandomTree;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;
import weka.core.ProtectedProperties;
import weka.core.converters.ConverterUtils.DataSource;



public class MajorityMin {
	static String skupKlasa[];
	static int pozicijamaxa = 0;
	static Instances vecinskeKlase;
	public static void alloc(String path) throws Exception {
		//load dataset
				DataSource source = new DataSource(path);
				
				Instances trainingData = source.getDataSet();
				trainingData.setClassIndex(trainingData.numAttributes() - 1);
				vecinskeKlase = vratiMajoriti(trainingData, source);
				
				vecinskeKlase = changeLabel(vecinskeKlase);
				Instances pocetnSkup = source.getDataSet();
				
				pocetnSkup = changeLabel(pocetnSkup);
				pocetnSkup.setClassIndex(pocetnSkup.numAttributes() - 1);
				
				NormalAnomal.treningAllocatora(vecinskeKlase, pocetnSkup, source);

			/*	SMO svm = new SMO();
				svm.buildClassifier(vecinskeKlase);
				Evaluation eval2 = new Evaluation(trainingData);
				eval2.evaluateModel(svm, trainingData);
				System.out.println(eval2.toSummaryString());
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
				fc.setClassifier(svm);
				//Build the meta-classifier
				fc.buildClassifier(vecinskeKlase);
				double actualValue;
				double predSMO;
				for(int i = 0; i < trainingData.numInstances(); i++) {
					actualValue = trainingData.instance(i).value(trainingData.numAttributes()-1);
					
					predSMO = fc.distributionForInstance(trainingData.instance(i));
					System.out.println(String.valueOf(actualValue)+" "+String.valueOf(predSMO)+"\n");
				}*/
				/*for(int i = 0; i < vecinskeKlase.numInstances(); i++) {
					System.out.println(vecinskeKlase.instance(i).stringValue(vecinskeKlase.numAttributes()-1));
				}*/
				
				
				/* Boosting a weak classifier using the Adaboost M1 method
				 * for boosting a nominal class classifier
				 * Tackles only nominal class problems
				 * Improves performance
				 * Sometimes overfits.
				 */
			
				//AdaBoost .. 
				AdaBoostM1 m1 = new AdaBoostM1();
				m1.setClassifier(new DecisionStump());//needs one base-classifier
				m1.setNumIterations(20);
				m1.buildClassifier(trainingData);
				
				/* Bagging a classifier to reduce variance.
				 * Can do classification and regression (depending on the base model)
				 */
				//Bagging .. 
				Bagging bagger = new Bagging();
				bagger.setClassifier(new RandomTree());//needs one base-model
				bagger.setNumIterations(25);
				bagger.buildClassifier(trainingData);		
				
				/*
				 * The Stacking method combines several models
				 * Can do classification or regression. 
				 */
				//Stacking ..
				Stacking stacker = new Stacking();
				stacker.setMetaClassifier(new J48());//needs one meta-model
				Classifier[] classifiers = {				
						new J48(),
						new NaiveBayes(),
						new RandomForest()
				};
				stacker.setClassifiers(classifiers);//needs one or more models
				stacker.buildClassifier(trainingData);
				
				/*
				 * Class for combining classifiers.
				 * Different combinations of probability estimates for classification are available. 
				 */
				//Vote .. 
				Vote voter = new Vote();
				voter.setClassifiers(classifiers);//needs one or more classifiers
				voter.buildClassifier(trainingData);
}
private static Instances vratiMajoriti(Instances trainingData, DataSource source) throws Exception {
	//set class index .. as the last attribute
	if (trainingData.classIndex() == -1) {
		trainingData.setClassIndex(trainingData.numAttributes() - 1);
	}
	skupKlasa = new String[trainingData.numClasses()];
	//System.out.println(trainingData.instance(0).getClass() +" "+ trainingData.instance(22).);
	for(int i = 0, l = skupKlasa.length; i < l; i++) {
		System.out.println("ulaz");
		for(int j = 0, h = trainingData.numInstances(); j < h; j++) {
			if(i == 0) {
				skupKlasa[i] = trainingData.instance(j).stringValue(trainingData.classAttribute());
				break;
			}
			boolean izlaz = false;
			for(int k = 0; k < i; k++) {
				if(skupKlasa[k].equals(trainingData.instance(j).stringValue(trainingData.classAttribute()))){
					izlaz = true;
					break;
				}
			}
			if(!izlaz) {
				skupKlasa[i] = trainingData.instance(j).stringValue(trainingData.classAttribute());
				break;
			}
			
		}
	}
	/*for(String skup : skupKlasa) {
		System.out.println(skup);
	}*/
	int brojKlasa[] = new int[skupKlasa.length];
	for(int i = 0, h = trainingData.numInstances(); i < h; i++) {
		for(int k = 0; k < skupKlasa.length; k++) {
			if(skupKlasa[k].equals(trainingData.instance(i).stringValue(trainingData.numAttributes() - 1))){
				brojKlasa[k]++;
			}
		}
	}
	int max = brojKlasa[0];
	
	for(int i = 1; i < brojKlasa.length; i++) {
		if(brojKlasa[i] > max) {
			max = brojKlasa[i];
			pozicijamaxa = i;
		}
		
	}
	int polovica;
	if(trainingData.numInstances() / 2 == 1) {
		polovica = trainingData.numInstances() / 2 +1;
	}else {
		polovica = trainingData.numInstances() / 2;
	}
	
	Instances vecinskeKlase = source.getDataSet();
	vecinskeKlase.delete();
	
	for(int i = 0, j = 0; i < polovica; i++) {
		while(true) {
			if(skupKlasa[pozicijamaxa].equals(trainingData.instance(j).stringValue(trainingData.numAttributes() - 1))){
				vecinskeKlase.add(trainingData.instance(j));
				j++;
				break;
			}
			j++;
		}
				
	}
	return vecinskeKlase;
	}
public static Instances changeLabel(Instances skup){

	FastVector values = new FastVector(); /* FastVector is now deprecated. Users can use any java.util.List */
    values.addElement(skupKlasa[pozicijamaxa]);               /* implementation now */
    skup.deleteAttributeAt(skup.numAttributes() - 1);
    skup.insertAttributeAt(new weka.core.Attribute("class", values), skup.numAttributes());
	for(int i = 0; i < skup.numInstances(); i++) {
		skup.instance(i).setValue(skup.numAttributes() - 1, skupKlasa[pozicijamaxa]);
	}
	skup.setClassIndex(skup.numAttributes() - 1);
	return skup;
	}
}
