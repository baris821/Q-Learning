package GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import QLearning.DosyaIslemleri;
import QLearning.QLearning;

public class GUI extends JFrame implements ActionListener {
	
	
	JTextField iterasyon;
	JPanel p;
	JButton b;
	JButton b2;
	JLabel l;
	DosyaIslemleri seciliDosya = null;
	QLearning ql;
	JFileChooser jf;
	int[][] koordinatlar;
	
	public GUI() {
		
		super();
		setTitle("GUI");
		setSize(1200 , 900);
		setLocation(400 , 200);
		
		p = new JPanel();
		b = new JButton("Dosya Seç");
		iterasyon = new JTextField();
		iterasyon.setColumns(10);
		l = new JLabel();
		
		
		b.addActionListener(this);
				
		p.add(l);
		p.add(iterasyon);	
		p.add(b);
		
		this.add(p);
		
		setVisible(true);
		
	
	}

	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
	    Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(5));

		int ilk_x = 250;
		int ilk_y = 150;
		
		int x = ilk_x;
		int y = ilk_y;
		
		int h = 600;
		
		g2.drawRect(x, y, h, h);
		
		
		double satir;
		
		
		
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
					
					///g.fillOval(koordinatlar[(int) ((satir*j)+i)][0], koordinatlar[(int) ((satir*j)+i)][1], 4, 4);
				}
				y = (int) (ilk_y + (h/2)/satir);
				x = (int) (x + h/satir);
			}
			
			g.setColor(Color.RED);
			
			int oda , sonrakiOda;
			
			for(int i = 0;i<ql.yol.size()-1;i++){
				
				oda = ql.yol.get(i);
				sonrakiOda = ql.yol.get(i+1);
				
				g.drawLine(koordinatlar[oda][0], koordinatlar[oda][1], koordinatlar[sonrakiOda][0], koordinatlar[sonrakiOda][1]);
				
			}
			

		}
		
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("Dosya Seç")){
			if(iterasyon.getSize()!=null){
				jf = new JFileChooser();
				int gDosya = jf.showOpenDialog(null);
				if(gDosya == JFileChooser.APPROVE_OPTION){
					
					seciliDosya = new DosyaIslemleri(jf.getSelectedFile());
					ql = new QLearning(seciliDosya, 2, 12,Integer.parseInt(iterasyon.getText()));
					l.setText(jf.getSelectedFile().getName());
					repaint();
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "lütfen iterasyon deðerini giriniz." , "Eksik Giriþ" , 1);
			}
			
			
		}
		else if(e.getActionCommand().equals("kare")){
			
		}
				
	}
	
	
	
}
