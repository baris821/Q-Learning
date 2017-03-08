package QLearning;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DosyaIslemleri {
	
	private File d;
	public BufferedReader br;
	public int satir;
	
	public DosyaIslemleri(File dosya){
		
		d = dosya;
		try {
			br = new BufferedReader(new FileReader(d));
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
			
			br.close();
			br = new BufferedReader(new FileReader(d));
	
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return satir;
	}
	
	public void DosyaKapat()
	{
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
