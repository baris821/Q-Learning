package QLearning;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class QLearning {
	
	public DosyaIslemleri di;
	
	public int[][] r;
	public double[][] q;
	private double kSayi;
	public int basOda;
	public int bitOda;
	int iterasyon;
	public ArrayList<Integer> yol;
	
	public QLearning(DosyaIslemleri d , int basOda , int bitOda , int iterasyon){
		
		di = d;
		
		kSayi = 0.8;
		
		yol = new ArrayList<>();
		
		r = new int[di.satir][di.satir];
		q = new double[di.satir][di.satir];
		
		this.basOda = basOda;
		this.bitOda = bitOda;
		this.iterasyon = iterasyon;
		
		rOlustur();		
		qOlustur(iterasyon);
		yolOlustur();
		
	}
	
	private void rOlustur(){
		
		for(int i = 0;i<di.satir;i++)
			for(int j = 0;j<di.satir;j++)
				r[i][j] = -1;
		
		String[] s;
		
		try {
			for(int i = 0;i<di.satir;i++){
				s = di.br.readLine().split(",");
				for(String j : s){
					if(i == bitOda)
						r[i][Integer.parseInt(j)] = 100;
					else if(Integer.parseInt(j) == bitOda)
						r[i][Integer.parseInt(j)] = 100;
					else
						r[i][Integer.parseInt(j)] = 0;
				}
					
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void qOlustur(int iterasyon){
		
		int durum = basOda , sonrakiDurum;
		int aksiyom;
		
		for(int ite = 0;ite<iterasyon;ite++){
			while(true){
				aksiyom = aksiyomSec(r[durum]);
				sonrakiDurum = aksiyom;
				q[durum][aksiyom] = (double)r[durum][aksiyom] + kSayi*max(q[sonrakiDurum]);
				durum = sonrakiDurum;
				if(aksiyom==bitOda){
					break;
				}
			}	
		}
		
		
		
	}

	

	private int aksiyomSec(int[] aksiyomlar) {
		
		ArrayList<Integer> l = new ArrayList<Integer>();
		
		for(int i = 0;i<aksiyomlar.length;i++){
			if(aksiyomlar[i]>-1){
				l.add(i);
			}
		}
		
		Random r = new Random();
		
		return l.get(r.nextInt(l.size()));
	}

	private  double max(double[] s) {
		
		double enB = s[0];
		
		for(int i = 1;i<s.length;i++){
			if(enB<s[i]){
				enB = s[i];
			}
		}
		
		return enB;
	}
	
	private void yolOlustur(){
		
		int suAn = basOda;
		int index = 0;
		yol.add(suAn);

		while(suAn != bitOda){
			
			for(int i = 0;i<di.satir;i++){
				if(q[suAn][index] < q[suAn][i]){
					index = i;
				}
			}

			suAn = index;
			yol.add(suAn);		
		}

	}
	

	
}
