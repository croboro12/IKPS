package paket;

public class ProglasiNajboljeg {
	public static void najboljiJe() {
		int[] poljeNajboljihF = new int[] {0, 0, 0, 0};
		int[] poljeNajboljihA = new int[] {0, 0, 0, 0};
		//alloc, os, ada, bag
		for(int i = 0; i < 6; i++) {
			int indexF = findMaxFs(i);
			poljeNajboljihF[indexF]++;
			
			int indexA = findMaxAc(i);
			poljeNajboljihA[indexA]++;
		}
		int max = poljeNajboljihF[0];
		int indeksF = 0;
		for(int i = 0; i < 4; i++) {
			if(max < poljeNajboljihF[i]) {
				max =  poljeNajboljihF[i];
				indeksF = i;
			}
		}
		ispisi(indeksF);
		
		int indeksA = 0;
		max = poljeNajboljihA[0];
		for(int i = 0; i < 4; i++) {
			if(max < poljeNajboljihA[i]) {
				max =  poljeNajboljihA[i];
				indeksA = i;
			}
		}
		ispisi(indeksA);
	}
	private static int findMaxFs(int in) {
		int maxi = 0;
		double maxv = MajorityMin.rezultati[in].fscore;
		
		if(maxv < KlasaZaOsnovnoRac.rezultati[in].fscore) {
			maxv = KlasaZaOsnovnoRac.rezultati[in].fscore;
			maxi = 1;
		}
		
		if(maxv < AdaBoost.rezultati[in].fscore) {
			maxv = AdaBoost.rezultati[in].fscore;
			maxi = 2;
		}
		
		if(maxv < Baging.rezultati[in].fscore) {
			maxv = Baging.rezultati[in].fscore;
			maxi = 3;
		}
		return maxi;
	}
	private static int findMaxAc(int in) {
		int maxi = 0;
		double maxv = MajorityMin.rezultati[in].accuracy;
		
		if(maxv < KlasaZaOsnovnoRac.rezultati[in].accuracy) {
			maxv = KlasaZaOsnovnoRac.rezultati[in].accuracy;
			maxi = 1;
		}
		
		if(maxv < AdaBoost.rezultati[in].accuracy) {
			maxv = AdaBoost.rezultati[in].accuracy;
			maxi = 2;
		}
		
		if(maxv < Baging.rezultati[in].accuracy) {
			maxv = Baging.rezultati[in].accuracy;
			maxi = 3;
		}
		return maxi;
	}
	private static void ispisi(int indeksF) {
		if(indeksF == 0) {
			System.out.println("Najbolji Fsore ima Allocation metoda");
		}
		if(indeksF == 1) {
			System.out.println("Najbolji Fsore imaju osnovne metode");
		}
		if(indeksF == 2) {
			System.out.println("Najbolji Fsore ima AdaBoost metoda");
		}
		if(indeksF == 3) {
			System.out.println("Najbolji Fsore ima Bagging metoda");
		}
	}
}
