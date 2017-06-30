package Clases;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.FileChooserUI;

//import com.sun.org.apache.xalan.internal.lib.Extensions;
import com.sun.pdfview.*;
import java.awt.GridLayout;

public class Principal extends JFrame {
	private FileNameExtensionFilter filter = new FileNameExtensionFilter("archivos pdf","pdf");
	PagePanel panelpdf;
	JFileChooser selector;
	PDFFile pdffile;
	int indice=1;
	JButton banterior=new JButton("Anterior");
	JButton bsiguiente=new JButton("Siguiente");
	int paginas;

	
	public Principal(){
		
		
		panelpdf=new PagePanel();
		 
		JMenuBar barra=new JMenuBar();
		JMenu archivo=new JMenu("Archivo");
		JMenuItem ver=new JMenuItem("Buscar Archivo");
		ver.addActionListener(new ActionListener()
		
		{

			

			

			@Override
			public void actionPerformed(ActionEvent e) {
					   
			       
				indice=1;
				JFileChooser selector=new JFileChooser();
				selector.setFileFilter(filter);
				int op=selector.showOpenDialog(Principal.this);
				if(op==JFileChooser.APPROVE_OPTION){
					try{
					
						
					File file = selector.getSelectedFile();
					
					RandomAccessFile raf = new RandomAccessFile(file, "r");
		            FileChannel channel = raf.getChannel();
			       ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY,0, channel.size());
			       pdffile = new PDFFile(buf);
			       paginas=pdffile.getNumPages();
			       
			        PDFPage page = pdffile.getPage(indice);
			        panelpdf.showPage(page);
			        
			        repaint();
			        bsiguiente.setEnabled(true);
			        banterior.setEnabled(true);
			      
			       }catch(IOException ioe){
						JOptionPane.showMessageDialog(null, "Error al abrir el archivo");
					}
				}
			}

			
			
		});
		
		JPanel pabajo=new JPanel();
		bsiguiente.setEnabled(false);
		
		bsiguiente.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(indice <paginas){
					indice++;
					
				
				PDFPage page = pdffile.getPage(indice);
			    panelpdf.showPage(page);}
			}
							
		});
		banterior.setEnabled(false);
		
		banterior.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				if (indice>1){	
					
				indice--;
				
				PDFPage page = pdffile.getPage(indice);
			    panelpdf.showPage(page);}
				
			}
			
		});
		pabajo.setLayout(new GridLayout(0, 2, 0, 0));
		pabajo.add(banterior);
		pabajo.add(bsiguiente);
		archivo.add(ver);
		barra.add(archivo);
		setJMenuBar(barra);
		getContentPane().add(panelpdf);
		getContentPane().add(pabajo,BorderLayout.SOUTH);
		
	
	}
	
	
	public static void main(String arg[]){
		Principal p=new Principal();
		p.setDefaultCloseOperation(EXIT_ON_CLOSE);
		p.setVisible(true);
		p.setBounds(0, 0, 600, 600);
		p.setLocationRelativeTo(null);
	}

}
