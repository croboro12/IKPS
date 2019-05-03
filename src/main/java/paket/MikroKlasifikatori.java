package paket;

import weka.classifiers.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.filters.supervised.attribute.NominalToBinary;

public class MikroKlasifikatori {
	static void obradaNormalnihiAnomalnih() throws Exception {
		J48(NormalAnomal.normal);
		J48(NormalAnomal.anomal);
			}
	private static void J48(Instances dataset) throws Exception {
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
		Evaluation eval = new Evaluation(dataset);
		eval.evaluateModel(fc, dataset);
		System.out.println(eval.toMatrixString());
	}
}
