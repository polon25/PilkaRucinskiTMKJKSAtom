package pl.pw.edu.fizyka.pojawa.TMKJKS;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Options extends JFrame { //by Jacek Pilka
	
	static JFrame window = new Options();
	public double V=0;
	double densityU = 19050;
	double densityP = 19816;
	public double m=0;
	public float r=0;
	public float a=0;
	String shapeText = "Promien";
	public String materialShape = "Kula";
	public String element = "Uran";
	public boolean reflectMaterial = false;
	
	private ResourceBundle resourceBundle = ResourceBundle.getBundle(
			"pl/pw/edu/fizyka/pojawa/TMKJKS/labels",new Locale(ChooseLanguage.getLocal()));
	
	public Options(){
		setSize(500,300);
		setTitle(resourceBundle.getString("options.title"));
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
		leftPanelA.add(new JLabel(resourceBundle.getString("options.element")));
		leftPanelA.add(new JLabel(resourceBundle.getString("options.shape")));
		leftPanelA.add(new JLabel());
		
		rightPanelA.setLayout(new GridLayout(4,1));
		rightPanelA.add(new JLabel());
		rightPanelA.add(new JLabel(resourceBundle.getString("options.surroundingMaterial")));
		rightPanelA.add(new JLabel(resourceBundle.getString("options.weight")));
		
		manePanel.add(leftPanelA);
		manePanel.add(leftPanel);
		manePanel.add(rightPanelA);
		manePanel.add(rightPanel);
		
		leftPanel.setLayout(new GridLayout(4,1));
		rightPanel.setLayout(new GridLayout(4,1));
		
		String stringElements[]={resourceBundle.getString("options.name1"), resourceBundle.getString("options.name2")};
		final JComboBox elements = new JComboBox(stringElements);
		String stringShapes[]={resourceBundle.getString("options.shape1"), resourceBundle.getString("options.shape2")};
		final JComboBox shapes = new JComboBox(stringShapes);
		String stringmaterials[]={resourceBundle.getString("options.yes"), resourceBundle.getString("options.no")};
		final JComboBox materials = new JComboBox(stringmaterials);
		
		final JTextField mass = new JTextField(resourceBundle.getString("options.giveMass"));
		final JButton dimensions = new JButton(resourceBundle.getString("options.dimension"));
		final JTextField shape = new JTextField("");
		final JLabel label = new JLabel (resourceBundle.getString("options.radius"));
		
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
		    		element=(String) elements.getSelectedItem();
		    	}
		    	else if(e.getSource()==shapes){
		    		if(shapes.getSelectedItem()==resourceBundle.getString("options.shape1")){
	    				shapeText=resourceBundle.getString("options.radius");
	    			}
	    			else if(shapes.getSelectedItem()==resourceBundle.getString("options.shape2")){
	    				shapeText=resourceBundle.getString("options.edge");
	    			}
		    		label.setText(shapeText);
		    		materialShape=(String) shapes.getSelectedItem();
		    	}
		    	else if(e.getSource()==materials){
		    		if(materials.getSelectedItem()==resourceBundle.getString("options.no")){
		    			reflectMaterial=false;
		    		}
		    		else if(materials.getSelectedItem()==resourceBundle.getString("options.yes")){
		    			reflectMaterial=true;
		    		}
		    	}
		    	else if(e.getSource()==mass){
		    		m=Float.parseFloat(mass.getText());
		    		if(elements.getSelectedItem()==resourceBundle.getString("options.name1"))
		    			V=m/densityU;
		    		else if(elements.getSelectedItem()==resourceBundle.getString("options.name2"))
		    			V=m/densityP;
		    		if(shapes.getSelectedItem()==resourceBundle.getString("options.shape1")){
	    				r=(float) Math.sqrt(V/3.14);
	    				shape.setText(String.valueOf(r));
	    			}
	    			else if(shapes.getSelectedItem()==resourceBundle.getString("options.shape2")){
	    				a=(float) Math.cbrt(V);
	    				shape.setText(String.valueOf(a));
	    			}
		    	}
		    	else if(e.getSource()==shape){
		    		if(shapes.getSelectedItem()==resourceBundle.getString("options.shape1")){
		    			r = Float.parseFloat(shape.getText());
		    			V=3.14*r*r;
		    			System.out.println(V);
		    		}
		    		else if(shapes.getSelectedItem()==resourceBundle.getString("options.shape2")){
		    			a = Float.parseFloat(shape.getText());
		    			V=a*a*a;
		    			System.out.println(V);
		    		}
		    		if(elements.getSelectedItem()==resourceBundle.getString("options.name1")){
	    				m=V*densityU;
	    			}
	    			else if(elements.getSelectedItem()==resourceBundle.getString("options.name2")){
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