package GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import QLearning.DosyaIslemleri;
import QLearning.QLearning;

public class GUI extends JFrame implements ActionListener , MouseListener{
	
	
	JTextField iterasyon;
	int basOdasi;
	int bitOdasi;
		
	int ilk_x = 250;
	int ilk_y = 150;
	
	int h = 600;
	
	JPanel p;
	
	JButton dosyaSec;
	JButton tamamla;
	
	
	JLabel lite;
	DosyaIslemleri seciliDosya;
	QLearning ql;
	JFileChooser jf;
	int[][] koordinatlar = null;
	
	
	public GUI() {
		
		super();
		setTitle("Q-Learning");
		setSize(1200 , 900);
		setLocation(400 , 200);
		
		p = new JPanel();
		p.setLocation(250, 500);
		dosyaSec = new JButton("Dosya Seç");
		tamamla = new JButton("Tamamla");
		
		iterasyon = new JTextField();
		iterasyon.setColumns(10);
		
		basOdasi = -1;
		bitOdasi = -1;
		
		seciliDosya = null;
		ql = null;
		
		lite = new JLabel("iterasyon : ");
		
		dosyaSec.addActionListener(this);
		tamamla.addActionListener(this);
				
		p.add(lite);
		p.add(iterasyon);

		p.add(dosyaSec);
		p.add(tamamla);
		
		addMouseListener(this);
		
		add(p);
		
		setVisible(true);
		
	
	}

	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
	    Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(5));
				
		int x = ilk_x;
		int y = ilk_y;

		g2.drawRect(x, y, h, h);
		
		double satir = 0;
		
		if(seciliDosya!=null){
			satir = Math.sqrt((double)seciliDosya.satir);
			for(int i = 0;i<satir;i++){
				x+=h/satir;
				g2.drawLine(x, y, x, y+h);
			}
			x = ilk_x;
			for(int i = 0;i<satir;i++){
				y+=h/satir;
				g.drawLine(x, y, x+h, y);
			}
			y = ilk_y;
			
			koordinatlar = new int[seciliDosya.satir][2];
			
			x = (int) (x + (h/2)/satir);
			y = (int) (y + (h/2)/satir);
			
			
			for(int i=0;i<satir;i++){
				for(int j=0;j<satir;j++){
					koordinatlar[(int) ((satir*j)+i)][0] = (int) (x);
					koordinatlar[(int) ((satir*j)+i)][1] = (int) (y);
					
					y = (int) (y + h/satir);
				}
				y = (int) (ilk_y + (h/2)/satir);
				x = (int) (x + h/satir);
			}
			
			g2.setFont(g2.getFont().deriveFont(25.0f));
			g2.setColor(Color.BLACK);
			int yuk = (int) ((h/satir)/2);
			
			for(int i = 0;i<seciliDosya.satir;i++){
				g2.drawString(String.valueOf(i), koordinatlar[i][0] - (yuk/2) , koordinatlar[i][1] -(yuk/2));
			}	
			
			
			if(basOdasi!=-1){
				g2.setColor(Color.green);
				g2.fillRect((int)(koordinatlar[basOdasi][0] - (h/satir)/2),(int)(koordinatlar[basOdasi][1] - (h/satir)/2), (int)(h/satir), (int)(h/satir));
			}
			if(bitOdasi!=-1){
				g2.setColor(Color.red);
				g2.fillRect((int)(koordinatlar[bitOdasi][0] - (h/satir)/2),(int)(koordinatlar[bitOdasi][1] - (h/satir)/2), (int)(h/satir), (int)(h/satir));

			}
			
		
		
		}
		if(ql!=null){
			
			g2.setColor(Color.white);
			
			int fark;
			for(int i = 0;i<seciliDosya.satir;i++){
				for(int j = 0;j<seciliDosya.satir;j++){
					if(ql.r[i][j] > -1){
						if(koordinatlar[i][0] == koordinatlar[j][0]){
							fark = (koordinatlar[i][1] + koordinatlar[j][1])/2;
							g2.drawLine(koordinatlar[i][0]-25, fark, koordinatlar[i][0]+25, fark);
						}
						else if(koordinatlar[i][1] == koordinatlar[j][1]){
							fark = (koordinatlar[i][0] + koordinatlar[j][0])/2;
							g2.drawLine(fark, koordinatlar[i][1]-25, fark, koordinatlar[i][1]+25);
						}
					}
				}
					
			}
			
			
			
			
			//// YOL ÇIZDIRME KODLARI
			
			
			if(ql.yol.get(0) == basOdasi && ql.yol.get(ql.yol.size()-1) == bitOdasi ){
				g.setColor(Color.blue);
				
				int oda , sonrakiOda;
				
				for(int i = 0;i<ql.yol.size()-1;i++){
					
					oda = ql.yol.get(i);
					sonrakiOda = ql.yol.get(i+1);
					
					g.drawLine(koordinatlar[oda][0], koordinatlar[oda][1], koordinatlar[sonrakiOda][0], koordinatlar[sonrakiOda][1]);
					
				}
				
			}
			
			
		
		}
		
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("Dosya Seç")){
			if(iterasyon.getText().isEmpty()!=true){
				jf = new JFileChooser();
				int gDosya = jf.showOpenDialog(null);
				if(gDosya == JFileChooser.APPROVE_OPTION){
					
					seciliDosya = new DosyaIslemleri(jf.getSelectedFile());
					repaint();
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "lütfen iterasyon deðerini giriniz." , "Eksik Giriþ" , 1);
			}
			
			
		}
		if(e.getActionCommand().equals("Tamamla")){
			if(basOdasi!=-1 && bitOdasi != -1){
				ql = new QLearning(seciliDosya, basOdasi, bitOdasi, Integer.parseInt(iterasyon.getText()));
				repaint();
			}
			
		}
				
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		
		int secilenOda = -1;
		int satir;
		int kenar;
		
		if(e.getX()>ilk_x && e.getX()<ilk_x+h && e.getY()>ilk_y && e.getY()<ilk_y+h){
			if(koordinatlar!=null){
				satir = (int) Math.sqrt((double)seciliDosya.satir);
				kenar = h/satir;
				for(int i = 0;i<satir;i++){
					for(int j = 0;j<satir;j++){
						if(e.getX()>koordinatlar[(satir*j)+i][0]-kenar/2 && e.getX()<koordinatlar[(satir*j)+i][0]+kenar/2 
								&& e.getY()>koordinatlar[(satir*j)+i][1]-kenar/2 && e.getY()<koordinatlar[(satir*j)+i][1]+kenar/2){
							secilenOda = ((satir*j)+i);
						}
					}
				}
			}
		}		
		
		if(seciliDosya!=null){
			if(basOdasi!=-1 && bitOdasi != -1){
				basOdasi = secilenOda;
				bitOdasi = -1;
				repaint();
			}else if(basOdasi==-1){
				basOdasi = secilenOda;
				repaint();
			}else if(bitOdasi==-1){
				bitOdasi = secilenOda;
				repaint();
			}
		}
		
	}



	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	
	
	
}
