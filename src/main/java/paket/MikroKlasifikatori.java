package paket;

import weka.classifiers.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.filters.supervised.attribute.NominalToBinary;

import java.util.Random;

import weka.classifiers.*;
import weka.classifiers.lazy.IBk;
import weka.classifiers.rules.OneR;
import weka.classifiers.trees.NBTree;
public class MikroKlasifikatori {
	
	static double novin[][];
	static double novia[][];
	static void obradaNormalnihiAnomalnih() throws Exception {
		//J48 Obrada
		//VELICINA MATRICE JE BROJ KLASNIH OZNAKA U IRIRS.ARFF TO JE 3
		double[][] rezultati = new double[NormalAnomal.normal.numClasses()][NormalAnomal.normal.numClasses()];
		//OBRADA NORMALNIH I ANORMALNIH
		novin = J48(NormalAnomal.normal, false).confusionMatrix();
		novia = J48(NormalAnomal.anomal, false).confusionMatrix();
		//OVDJE SPREMAM REZULTAT POMOCU ZBROJI MATRICE
		//TO ZbRAjA MATRICE OD NORMALNE I ANORMALNE KLASIFIKACIJE
		//TREBALO BI VALJAT 
		MajorityMin.rezultati[0].racunaj(zbrojiMatrice(rezultati));
		

		novin = NaiveBayees(NormalAnomal.normal, false ).confusionMatrix();
		novia =  NaiveBayees(NormalAnomal.anomal, false).confusionMatrix();
		MajorityMin.rezultati[1].racunaj(zbrojiMatrice(rezultati));
		
		
		
		
		novin = SiMO(NormalAnomal.normal, false).confusionMatrix();
		novia =  SiMO(NormalAnomal.anomal, false).confusionMatrix();
		MajorityMin.rezultati[2].racunaj(zbrojiMatrice(rezultati));
		
		novin = NBTree(NormalAnomal.normal, false).confusionMatrix();
		novia =  NBTree(NormalAnomal.anomal, false).confusionMatrix();
		MajorityMin.rezultati[3].racunaj(zbrojiMatrice(rezultati));
		
		novin = OneR(NormalAnomal.normal, false).confusionMatrix();
		novia =  OneR(NormalAnomal.anomal, false).confusionMatrix();
		MajorityMin.rezultati[4].racunaj(zbrojiMatrice(rezultati));
		
		novin = IBk(NormalAnomal.normal, false).confusionMatrix();
		novia =  IBk(NormalAnomal.anomal, false).confusionMatrix();
		MajorityMin.rezultati[5].racunaj(zbrojiMatrice(rezultati));
		
		}
	static Evaluation NBTree(Instances dataset, boolean org) throws Exception {
		NBTree nbt = new NBTree();
		Evaluation eval =  vratiMatricu(nbt, dataset, "NBTree", org);
		/*for(int i = 0; i < NormalAnomal.normal.numClasses(); i++) {
			System.out.println(eval.fMeasure(i));
			MajorityMin.rezultati[1].fscore += eval.fMeasure(i);
		}*/
		return eval;
	}
	static Evaluation IBk(Instances dataset, boolean org) throws Exception {
		IBk ibk = new IBk();
		Evaluation eval =  vratiMatricu(ibk, dataset, "IBk", org);
		/*for(int i = 0; i < NormalAnomal.normal.numClasses(); i++) {
			System.out.println(eval.fMeasure(i));
			MajorityMin.rezultati[1].fscore += eval.fMeasure(i);
		}*/
		return eval;
	}
	static Evaluation OneR(Instances dataset, boolean org) throws Exception {
		OneR one = new OneR();
		Evaluation eval =  vratiMatricu(one, dataset, "OneR", org);
		/*for(int i = 0; i < NormalAnomal.normal.numClasses(); i++) {
			System.out.println(eval.fMeasure(i));
			MajorityMin.rezultati[1].fscore += eval.fMeasure(i);
		}*/
		return eval;
	}
	static Evaluation NaiveBayees(Instances dataset, boolean org) throws Exception {
		NaiveBayes bayes = new NaiveBayes();
		Evaluation eval =  vratiMatricu(bayes, dataset, "NaiveBayes", org);
		/*for(int i = 0; i < NormalAnomal.normal.numClasses(); i++) {
			System.out.println(eval.fMeasure(i));
			MajorityMin.rezultati[1].fscore += eval.fMeasure(i);
		}*/
		return eval;
	}
	static Evaluation J48(Instances dataset, boolean org) throws Exception {
		
		J48 tree = new J48();
		Evaluation eval = vratiMatricu(tree, dataset, "J48", org);
		/*for(int i = 0; i < NormalAnomal.normal.numClasses(); i++) {
			MajorityMin.rezultati[0].fscore += eval.fMeasure(i);
		}*/
		
		return eval;
	}
	
	static Evaluation SiMO(Instances dataset, boolean org) throws Exception {
		SMO smo = new SMO();
		Evaluation eval = vratiMatricu(smo, dataset, "SMO", org); 
		/*for(int i = 0; i < NormalAnomal.normal.numClasses(); i++) {
			MajorityMin.rezultati[2].fscore += eval.fMeasure(i);
		}*/
		return eval;
	}
	static double[][] zbrojiMatrice(double[][] rezultati) {
		
		for(int i = 0; i < NormalAnomal.normal.numClasses(); i++) {
			for(int j = 0; j < NormalAnomal.normal.numClasses(); j++) {
				rezultati[i][j] = (novin[i][j] + novia[i][j]);
			}
		}
	
		return rezultati;
	}
	static Evaluation vratiMatricu(Object klasifajer, Instances dataset, String imeObjekta, boolean org ) throws Exception {
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
			switch(imeObjekta) {
			case "NaiveBayes":
				fc.setClassifier((NaiveBayes)klasifajer);
				break;
			case "J48":
				fc.setClassifier((J48)klasifajer);
				break;
			case "SMO":
				fc.setClassifier((SMO)klasifajer);
				break;
			case "IBk":
				fc.setClassifier((IBk)klasifajer);
				break;
			case "NBTree":
				fc.setClassifier((NBTree)klasifajer);
				break;
			case "OneR":
				fc.setClassifier((OneR)klasifajer);
				break;
			}
			//Build the meta-classifier
			fc.buildClassifier(dataset);
			Evaluation eval = new Evaluation(dataset);
			if(org) {
				eval.crossValidateModel(fc, dataset, 5, new Random(1));
			}else {
				eval.evaluateModel(fc, dataset);
			}
			
			
			
			return eval;
	}
}
