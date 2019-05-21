package paket;

public class Rezulat {
String ime, tip;
double accuracy = 0.;
double recall = 0.;
double fscore = 0.;
Rezulat(String i, String tip){
	ime = i;
	this.tip = tip;
	}

void racunaj(double matrica[][]) {
	// TODO PROVJERITI OVU METODU NA PAPIRU?! RADI LI
	// TODO NE DIRATI NISTA OSIM OVE KLASE, AKO SE DIRA DRUGO ZAKOMENTIRAJTE MI...
	double tp = 0;
	//ISPIS MATrICE KOJU DOBIJE OD REZULTATA
	/*for(int i = 0; i < 3; i++) {
		for(int j = 0; j < 3; j++) {
			System.out.println(matrica[i][j]);
		}
	}*/ 
	
	double fn = 0, fp = 0, tn = 0;
	//System.out.println("broj klasnih"+  NormalAnomal.normal.numClasses());
	for(int i = 0; i < NormalAnomal.normal.numClasses(); i++) {//ZA SVAKU RADI MATRICU TRI KLASE TRI MATRICE
		
		for(int red = 0; red <  NormalAnomal.normal.numClasses(); red++) {
		
			for(int stup = 0; stup <  NormalAnomal.normal.numClasses(); stup++) {
				if(i == red && i == stup && red == stup) {
					tp += matrica[red][stup];//ZBRAJA NJIHOVE TP, FN, FP, TN
				
				}else if(i == red && stup != i) {
				
					fn += matrica[red][stup];
				}else if(i == stup && red != i) {
					fp += matrica[red][stup];
					
				}else {
					tn += matrica[red][stup];
				
				}
				
			}
			
		}
	
	}
	//PODIJELI S BROJEM KLASNIH OZNAKA
	tp /=  NormalAnomal.normal.numClasses();
	tn /=  NormalAnomal.normal.numClasses();
	fn /=  NormalAnomal.normal.numClasses();
	fp /=  NormalAnomal.normal.numClasses();
	//NJIHOV ZBROJ MORA DAVATI UKUPNI BROJ INSTANCA; DAJE!!! :D
	//System.out.println("tp "+ tp +" tn "+ tn+" fn "+fn+" fp "+fp);
	double zbroj = tp + tn +fn +fp;
	//System.out.println(zbroj + " "+NormalAnomal.normal.numInstances()+" "+NormalAnomal.anomal.numInstances());
	
	accuracy += (tp + tn) / (tp + tn + fp + fn); //ZBRAJAM SVE JER OVDJE ULAZIM 5 puta... za svaku 5 fold cross validation
	recall += tp/ (tp + fn);
	fscore += (2 * tp) / (2 * tp + fp + fn);
	
}
public String toString() {
	//ispis();
	// TODO OVDJE PROVJERIT OVO / 5 MISLIM DA VALJA?? OVISI KAKO MANIPULIRATE OVIM GORE
	//DIJELIM SA 5 zbog 5 fold cros validation
	
	return "========================\n"+this.ime + " ==> rezultati Allocation metode za " + MajorityMin.imeFajla() + ".arff:\nAccuracy: "+this.accuracy / 5+"\nFScore: "+this.fscore / 5;
}
/*static void ispis(){
	System.out.println("TrueP, FalseP, FalseN, TrueN");
	for(int i = 0; i < 2; i++) {
		for(int j = 0; j < 2; j++) {
			System.out.print(matrica[i][j] + " ");
		}
		System.out.println("");
	}
	System.out.println("");
}
*/


}
