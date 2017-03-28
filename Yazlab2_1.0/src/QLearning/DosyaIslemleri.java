package QLearning;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class DosyaIslemleri {
	
	private File d;
	private File outR , outQ , outPath;
	private BufferedWriter bwR , bwQ , bwP;
	private FileWriter fwR , fwQ , fwP;
	public BufferedReader br;
	public int satir;
	
	public DosyaIslemleri(File dosya){
		
		d = dosya;
		
		outR = new File("C:\\Users\\alpaydin\\Desktop\\Yazlab2_java\\Yazlab2_1.0\\dosyalar\\outR.txt");
		outQ = new File("C:\\Users\\alpaydin\\Desktop\\Yazlab2_java\\Yazlab2_1.0\\dosyalar\\outQ.txt");
		outPath = new File("C:\\Users\\alpaydin\\Desktop\\Yazlab2_java\\Yazlab2_1.0\\dosyalar\\outPath.txt");
		
		
		try {
			br = new BufferedReader(new FileReader(d));
			
			fwR = new FileWriter(outR);
			fwQ = new FileWriter(outQ);
			fwP = new FileWriter(outPath);
			
			bwR = new BufferedWriter(fwR);
			bwQ = new BufferedWriter(fwQ);
			bwP = new BufferedWriter(fwP);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		satir = SatirSayisi();
	}
	
	private int SatirSayisi(){
		
		int satir = 0;
		String s;
		try {
			s = br.readLine();
			while(s!=null){
				satir++;
				s = br.readLine();
			}
			
			acKapat();
	
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return satir;
	}
	
	public void dosyaYaz(int[][] r , double[][] q , ArrayList<Integer> p){
		
		DecimalFormat df = new DecimalFormat("0.00");

		
		try {
			for(int i = 0;i<satir;i++){
				for(int j = 0;j<satir;j++){
					bwR.write(r[i][j] + "\t" );
					bwQ.write(df.format(q[i][j]) + "\t" );	
				}
					bwR.write("\r");
					bwQ.write("\r");
			}
			for(int i : p){
				bwP.write(i + " - ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			bwQ.close();
			bwR.close();
			bwP.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void acKapat()
	{
		try {
			br.close();
			br = new BufferedReader(new FileReader(d));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
