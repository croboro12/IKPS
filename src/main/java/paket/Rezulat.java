package paket;

public class Rezulat {
String ime;
double accuracy = 0.;
double recall = 0.;
double fscore = 0.;
Rezulat(String i){
	ime = i;
	}

void racunaj(double[][] matrica) {
	double nazivnik;
	nazivnik = (matrica[0][1]+matrica[0][0]+matrica[1][0]+matrica[1][1]);
	if(nazivnik != 0) {
		this.accuracy += (matrica[0][0] + matrica[1][1]) / nazivnik;
	}
	nazivnik = (matrica[0][0]+matrica[1][0]);
	if(nazivnik != 0) {
		this.recall += matrica[0][0]/nazivnik;
	}
	
	nazivnik = (2 * matrica[0][0] + matrica[0][1] + matrica[1][0]);
	if(nazivnik != 0) {
		this.fscore += (2 * matrica[0][0]) / nazivnik;
	}
	
	System.out.println("fscore" + fscore);
}
public String toString() {
	//ispis();
	return this.ime + "Rezultati Allocation metode za " + MajorityMin.imeFajla() + ".arff:\nAccuracy: "+this.accuracy / 5+" FScore:"+this.fscore / 5;
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
