package pl.pw.edu.fizyka.pojawa.TMKJKS;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Options extends JFrame { //by Jacek Pilka
	
	static JFrame window = new Options();
	double V=0;
	double densityU = 19050;
	double densityP = 19816;
	double m=0;
	float r=0;
	float a=0;
	String shapeText = "Promieñ [m]";
	
	public Options(){
		setSize(500,300);
		setTitle("Opcje symulacji");
		setLayout(new BorderLayout());
		
		JPanel manePanel = new JPanel();
		
		add(BorderLayout.CENTER, manePanel);
		
		manePanel.setLayout(new GridLayout(1,4));
		
		JPanel leftPanel = new JPanel();
		JPanel leftPanelA = new JPanel();
		JPanel rightPanel = new JPanel();
		JPanel rightPanelA = new JPanel();
		
		leftPanelA.setLayout(new GridLayout(4,1));
		leftPanelA.add(new JLabel());
		leftPanelA.add(new JLabel("Pierwiastek"));
		leftPanelA.add(new JLabel("Kszta³t próbki"));
		leftPanelA.add(new JLabel());
		
		rightPanelA.setLayout(new GridLayout(4,1));
		rightPanelA.add(new JLabel());
		rightPanelA.add(new JLabel("Materia³ otaczaj¹cy"));
		rightPanelA.add(new JLabel("Masa próbki [kg]"));
		
		manePanel.add(leftPanelA);
		manePanel.add(leftPanel);
		manePanel.add(rightPanelA);
		manePanel.add(rightPanel);
		
		leftPanel.setLayout(new GridLayout(4,1));
		rightPanel.setLayout(new GridLayout(4,1));
		
		String stringElements[]={"Uran", "Pluton"};
		final JComboBox elements = new JComboBox(stringElements);
		String stringShapes[]={"Kula", "Szeœcian"};
		final JComboBox shapes = new JComboBox(stringShapes);
		String stringmaterials[]={"Pró¿nia", "Woda"};
		final JComboBox materials = new JComboBox(stringmaterials);
		
		final JTextField mass = new JTextField("Podaj masê próbki");
		final JButton dimensions = new JButton("Wymiary");
		final JTextField shape = new JTextField("");
		final JLabel label = new JLabel (shapeText);
		
		leftPanel.add(new JLabel());
		leftPanel.add(elements);
		leftPanel.add(shapes);
		leftPanel.add(label);
		rightPanel.add(new JLabel());
		rightPanel.add(materials);
		rightPanel.add(mass);
		rightPanelA.add(shape);
		rightPanel.add(new JLabel());
		add(BorderLayout.SOUTH, new JPanel());
		
		ActionListener listener = new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	if(e.getSource()==elements){
		    		
		    	}
		    	else if(e.getSource()==shapes){
		    		if(shapes.getSelectedItem()=="Kula"){
	    				shapeText="Promieñ [m]";
	    			}
	    			else if(shapes.getSelectedItem()=="Szeœcian"){
	    				shapeText="KrawêdŸ [m]";
	    			}
		    		label.setText(shapeText);
		    	}
		    	else if(e.getSource()==materials){
		    		
		    	}
		    	else if(e.getSource()==mass){
		    		m=Float.parseFloat(mass.getText());
		    		V=m/densityU;
		    		if(shapes.getSelectedItem()=="Kula"){
	    				r=(float) Math.sqrt(V/3.14);
	    				shape.setText(String.valueOf(r));
	    			}
	    			else if(shapes.getSelectedItem()=="Szeœcian"){
	    				a=(float) Math.cbrt(V);
	    				shape.setText(String.valueOf(a));
	    			}
		    	}
		    	else if(e.getSource()==shape){
		    		if(shapes.getSelectedItem()=="Kula"){
		    			r = Float.parseFloat(shape.getText());
		    			V=3.14*r*r;
		    			System.out.println(V);
		    		}
		    		else if(shapes.getSelectedItem()=="Szeœcian"){
		    			a = Float.parseFloat(shape.getText());
		    			V=a*a*a;
		    			System.out.println(V);
		    		}
		    		if(elements.getSelectedItem()=="Uran"){
	    				m=V*densityU;
	    			}
	    			else if(elements.getSelectedItem()=="Pluton"){
	    				m=V*densityP;
	    			}
		    		mass.setText(String.valueOf(m));
		    	}
		    }
		};
		
		elements.addActionListener(listener);
		shapes.addActionListener(listener);
		materials.addActionListener(listener);
		mass.addActionListener(listener);
		shape.addActionListener(listener);
	}
}
