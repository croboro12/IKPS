package paket;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.LibSVM;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class NormalAnomal {
	static Instances normal;
	static Instances anomal;
	static LibSVM libsvm;
	static Instances vecinske;
	static {
		libsvm = new LibSVM();
		String[] options = new String[4];
		options[0] = "-S";
		options[1] = "2";
		options[2] = "-N";
		options[3] = "0.25";
		
		//options[6] = "-C"; options[7] = "1.0";
	    try {
			libsvm.setOptions(options);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	static void treningAllocatora(Instances vecinskeKlase, Instances treningData, Instances test) throws Exception {
		
		NormalAnomal.vecinske = vecinskeKlase;
	 	libsvm.buildClassifier(vecinskeKlase); //trneing na vecinskim
	 	Instances dodavanje = new Instances(test); // ovo da ne izgubimo pravu klasnu vrijednost
		MajorityMin.changeLabel(test); // da imamo jednu nominalnu vrijednost
		Evaluation eval = new Evaluation(test);
		
		eval.evaluateModel(libsvm, test);//evaluacija sa trneingom pocetnim
		//System.out.println(eval3.toSummaryString());
		
		normal = new Instances(dodavanje); //jer imamo onaj pocetni pravi trening, vise nemamo ovaj trenin jer smo promjenili labele
		anomal = new Instances(dodavanje);
		normal.delete();
		anomal.delete();//prazan skup
		
		for(int i = 0; i < test.numInstances(); i++) {
	
			double resIns = eval.evaluateModelOnce(libsvm, test.instance(i));
			if(resIns == 0.0) {
				
				normal.add(dodavanje.instance(i));
				
			}else {
				anomal.add(dodavanje.instance(i));
			
			}
			
			

		}
		dodavanje = null;
		normal.setClassIndex(normal.numAttributes() - 1);
		anomal.setClassIndex(anomal.numAttributes() - 1);
		//System.out.println("Broj vecinski" + vecinskeKlase.numInstances());
		/*System.out.println("BROJ test" + test.numInstances());
		System.out.println("BROJ noorm" + normal.numInstances());
		System.out.println("BROJ ano" + anomal.numInstances());
		ispis();
		*/
		
		MikroKlasifikatori.obradaNormalnihiAnomalnih();
		
	}

	static void ispis() {
		System.out.println("========== ISPIS NORMALNIH =========");
		for(int i = 0; i < normal.numInstances(); i++) {
			System.out.println(normal.instance(i));
		}
		System.out.println("========== ISPIS ANOMALNIH =========");
		for(int i = 0; i < anomal.numInstances(); i++) {
			System.out.println(anomal.instance(i));
		}
	}
}
