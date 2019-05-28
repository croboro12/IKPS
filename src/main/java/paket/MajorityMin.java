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
	static Rezulat[] rezultati= new Rezulat[6];
	
		
	
		
	
	public static void alloc(String path) throws Exception {
		//load dataset
				rezultati[0] = new Rezulat("J48","alloc");
				rezultati[1] = new Rezulat("NaiveBayes","alloc");
				rezultati[2] = new Rezulat("SMO","alloc");
				rezultati[3] = new Rezulat("NBTree","alloc");
				rezultati[4] = new Rezulat("OneR","alloc");
				rezultati[5] = new Rezulat("IBk","alloc");
			
		
				DataSource source = new DataSource(path); // uzima path od dataseta
				//treba napraviti 5fold cros validaciju podataka 80:20
				Instances trainingData;
				
				Instances testData;
				Instances Podaci = source.getDataSet();
				Podaci.randomize(new java.util.Random(0)); // Pomijesaj podaci
			/*	for(int i = 0; i < Podaci.numInstances(); i++) {
					System.out.println(Podaci.instance(i));
				}*/
				for (int i = 0; i < 5; i++) { // 5 fold cross validation
					
					trainingData = Podaci.trainCV(5, i); // ovo uzima train podatke
					testData = Podaci.testCV(5, i);
					
	
					trainingData.setClassIndex(trainingData.numAttributes() - 1); // klasa je zadnji atribut
					vecinskeKlase = vratiMajoriti(new Instances(trainingData)); //majoritiy minority split
					/*System.out.println("ISPIS VECINSKIH");
					for(int j = 0; j < vecinskeKlase.numInstances(); j++) {
						System.out.println(vecinskeKlase.instance(j));
					}*/
					vecinskeKlase = changeLabel(vecinskeKlase);//mijenja oznaku zadnjeg atributa, mora imati jednu oznaku
				
				//System.out.println("vecinskih ima " + vecinskeKlase.numInstances());
				NormalAnomal.treningAllocatora(vecinskeKlase, trainingData, testData);//trening modela predvidjanja, allocatora
				//Rezulat.racunaj();
				
				}

				ispisRezultata();
				
				
				
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
			//	stacker.buildClassifier(trainingData);
				
				/*
				 * Class for combining classifiers.
				 * Different combinations of probability estimates for classification are available. 
				 */
				//Vote .. 
				Vote voter = new Vote();
				voter.setClassifiers(classifiers);//needs one or more classifiers
				//voter.buildClassifier(trainingData);
}
private static Instances vratiMajoriti(Instances trainingData) throws Exception {
	//set class index .. as the last attribute
	if (trainingData.classIndex() == -1) {//ako nema postavljenu klasnu oznaku
		trainingData.setClassIndex(trainingData.numAttributes() - 1);
	}
	skupKlasa = new String[trainingData.numClasses()]; //koliko klasa ima
	//System.out.println(trainingData.instance(0).getClass() +" "+ trainingData.instance(22).);
	for (int i=0;i<trainingData.classAttribute().numValues();i++) {
        skupKlasa[i] = trainingData.classAttribute().value(i);
        //NADJI POJEDINE KLASE
	}
	//System.out.println(trainingData.numInstances()); //ISPIS broja instanci i tih klasnih oznaka
	/*for(String skup : skupKlasa) {
		System.out.println(skup);//u slucaju iris.arff TRI SU
	}*/
	int brojKlasa[] = new int[skupKlasa.length];//sad prebroji koliko ima koje...
	for(int i = 0, h = trainingData.numInstances(); i < h; i++) {
		for(int k = 0; k < skupKlasa.length; k++) {
			if(skupKlasa[k].equals(trainingData.instance(i).stringValue(trainingData.numAttributes() - 1))){
				brojKlasa[k]++;
			}
		}
	}
	/*for(int i : brojKlasa) {
		System.out.println("BROJ ovih je " + i); //ispisi koliko je koje klase
	}*/
	int max = brojKlasa[0];
	pozicijamaxa = 0; // da nebi ostalo od zadnji put :)) 
	//SADA IDE MAJORITI MINORITI
	for(int i = 1; i < brojKlasa.length; i++) {//nadji koje ima najvise
		if(brojKlasa[i] > max) {
			max = brojKlasa[i];
			pozicijamaxa = i;
		}
		
	}
	//System.out.println("Najvise je " + skupKlasa[pozicijamaxa]);
	//OVO dole je iz algoritma da pomakne polovicu
	int polovica;
	if(trainingData.numInstances() / 2 == 1) {
		polovica = trainingData.numInstances() / 2 + 1;
	}else {
		polovica = trainingData.numInstances() / 2;
	}
	// stvori nove vecinske klase 
	Instances vecinskeKlase = new Instances(trainingData);
	vecinskeKlase.delete(); //isprazni ih jer cemo ponovno punit nekim svojima
	
	for(int i = 0, j = 0; i < polovica; i++) {//imamo polovicu treninga skupa pod
		while(j < trainingData.numInstances()) { // idi do kraja
			//ako klasa oznaka pripada vecinskoj idi tu i dodaj tu vecinsku
			
			//OVDJE MOZE DOCI DA VECINSKE IMAJU MANJE OD POLOVICE??!?!??
			//VIDITI MALO
			
			if(skupKlasa[pozicijamaxa].equals(trainingData.instance(j).stringValue(trainingData.numAttributes() - 1))){
				vecinskeKlase.add(trainingData.instance(j));
				//System.out.println(trainingData.instance(j));
				j++;
				break;
			}
			j++;
		}
				
	}
	trainingData = null;
	return vecinskeKlase;
	}
public static Instances changeLabel(Instances skup){
	//Dodaj attribut samo sa jednom vrijednoscu, i to onom koju ima oznaka koje je najvise...
	skup.setClassIndex(-1);
	FastVector values = new FastVector(); /* FastVector is now deprecated. Users can use any java.util.List */
    values.addElement(skupKlasa[pozicijamaxa]);               /* implementation now */
    skup.deleteAttributeAt(skup.numAttributes() - 1);
    skup.insertAttributeAt(new weka.core.Attribute("class", values), skup.numAttributes());
	for(int i = 0; i < skup.numInstances(); i++) {
		skup.instance(i).setValue(skup.numAttributes() - 1, skupKlasa[pozicijamaxa]);
	}
	skup.setClassIndex(skup.numAttributes() - 1);
	//odavde svaka instaca sada ima samo jednu nominalnu vrijednost
	return skup;
	}
public static String imeFajla() {
	return vecinskeKlase.relationName();//vracaj ime dataseta
}
public static void ispisRezultata() {
	System.out.println("Ispis rezultata:");
	for(Rezulat rez : rezultati) {
		System.out.println(rez);
	}
}

}

