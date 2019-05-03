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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	static void treningAllocatora(Instances vecinskeKlase, Instances pocetnSkup, DataSource source) throws Exception {
		
		NormalAnomal.vecinske = vecinskeKlase;
	    vecinskeKlase.setClassIndex(vecinskeKlase.numAttributes() - 1);
		libsvm.buildClassifier(vecinskeKlase);
		
		//pogonskiNormalAnomal(pocetnSkup, source);
		
	//	ispis();
		
		
	//	MikroKlasifikatori.obradaNormalnihiAnomalnih();
	}
	static void pogonskiNormalAnomal(Instances pocetnSkup, DataSource source) throws Exception {
		MajorityMin.changeLabel(pocetnSkup);
		Evaluation eval = new Evaluation(pocetnSkup);
		libsvm.buildClassifier(pocetnSkup);
		eval.evaluateModel(libsvm, pocetnSkup);
		//System.out.println(eval3.toSummaryString());
		
		normal = source.getDataSet();
		anomal = source.getDataSet();
		normal.delete();
		anomal.delete();
		Instances dodavanje = source.getDataSet();
		for(int i = 0; i < pocetnSkup.numInstances(); i++) {
	
			double resIns = eval.evaluateModelOnce(libsvm, pocetnSkup.instance(i));
			if(resIns == 0.0) {
				
				normal.add(dodavanje.instance(i));
			}else {
				anomal.add(dodavanje.instance(i));
			}
		//	System.out.print(String.valueOf(resIns)+" ");
			

		}
		normal.setClassIndex(normal.numAttributes() - 1);
		anomal.setClassIndex(anomal.numAttributes() - 1);
		
		ispis();
		
		
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
