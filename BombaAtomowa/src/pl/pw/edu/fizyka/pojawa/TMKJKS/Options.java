package pl.pw.edu.fizyka.pojawa.TMKJKS;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Options extends JFrame implements FocusListener { // by Jacek Piłka & Antoni Ruciński 

	private static final long serialVersionUID = 1L;

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
	public boolean reflectMaterial = true;
	public static boolean correctOrNoCorrect=false;
	
	private ResourceBundle resourceBundle = ResourceBundle.getBundle(
			"pl/pw/edu/fizyka/pojawa/TMKJKS/labels",new Locale(ChooseLanguage.getLocal()));
	
	JLabel elementLabel= new JLabel(resourceBundle.getString("options.element"));
	JLabel shapeLabel=new JLabel(resourceBundle.getString("options.shape"));
	JLabel sMaterialLabel=new JLabel(resourceBundle.getString("options.surroundingMaterial"));
	JLabel weightLabel=new JLabel(resourceBundle.getString("options.weight"));
	final JLabel radiusLabel = new JLabel (resourceBundle.getString("options.radius"));
	
	final JTextField mass = new JTextField(resourceBundle.getString("options.giveMass"));
	final JTextField shape = new JTextField(resourceBundle.getString("options.giveRadius"));
	
	String stringElements[]={resourceBundle.getString("options.name1"), resourceBundle.getString("options.name2")};
	final JComboBox elements = new JComboBox(stringElements);
	String stringShapes[]={resourceBundle.getString("options.shape1"), resourceBundle.getString("options.shape2")};
	final JComboBox shapes = new JComboBox(stringShapes);
	String stringmaterials[]={resourceBundle.getString("options.yes"), resourceBundle.getString("options.no")};
	final JComboBox materials = new JComboBox(stringmaterials);
	
	
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
		leftPanelA.add(elementLabel);
		leftPanelA.add(shapeLabel);
		leftPanelA.add(new JLabel());
		
		rightPanelA.setLayout(new GridLayout(4,1));
		rightPanelA.add(new JLabel());
		rightPanelA.add(sMaterialLabel);
		rightPanelA.add(weightLabel);
		
		weightLabel.setForeground(Color.RED);
		radiusLabel.setForeground(Color.RED);
		
		
		manePanel.add(leftPanelA);
		manePanel.add(leftPanel);
		manePanel.add(rightPanelA);
		manePanel.add(rightPanel);
		
		leftPanel.setLayout(new GridLayout(4,1));
		rightPanel.setLayout(new GridLayout(4,1));
		
		//String stringElements[]={resourceBundle.getString("options.name1"), resourceBundle.getString("options.name2")};
		//final JComboBox elements = new JComboBox(stringElements);
		
		
		//String stringShapes[]={resourceBundle.getString("options.shape1"), resourceBundle.getString("options.shape2")};
		//final JComboBox shapes = new JComboBox(stringShapes);
		//String stringmaterials[]={resourceBundle.getString("options.yes"), resourceBundle.getString("options.no")};
		//final JComboBox materials = new JComboBox(stringmaterials);
		
		//final JTextField mass = new JTextField(resourceBundle.getString("options.giveMass"));
		final JButton dimensions = new JButton(resourceBundle.getString("options.dimension"));
		//final JTextField shape = new JTextField("");
		//final JLabel radiusLabel = new JLabel (resourceBundle.getString("options.radius"));
		
		leftPanel.add(new JLabel());
		leftPanel.add(elements);
		leftPanel.add(shapes);
		leftPanel.add(radiusLabel);
		rightPanel.add(new JLabel());
		rightPanel.add(materials);
		rightPanel.add(mass);
		rightPanelA.add(shape);
		rightPanel.add(new JLabel());
		add(BorderLayout.SOUTH, new JPanel());
		
//Listeners
		//elementsListener
		ActionListener elementsListener = new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	if(e.getSource()==elements){
		    		element=(String) elements.getSelectedItem();
		    	}
		    }
		};
	
		//shapesListener
		ActionListener shapesListener = new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	if(e.getSource()==shapes){
		    		if(shapes.getSelectedItem()==resourceBundle.getString("options.shape1")){
	    				shapeText=resourceBundle.getString("options.radius");
	    			}
	    			else if(shapes.getSelectedItem()==resourceBundle.getString("options.shape2")){
	    				shapeText=resourceBundle.getString("options.edge");
	    			}
		    		radiusLabel.setText(shapeText);
		    		materialShape=(String) shapes.getSelectedItem();
		    	}
		    	
		    }
		};


		//materialListener
		ActionListener materialListener = new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	if(e.getSource()==materials){
		    		if(materials.getSelectedItem()==resourceBundle.getString("options.no")){
		    			reflectMaterial=false;
		    			System.out.print("\n Materiał otaczający: nie");
		    		}
		    		else if(materials.getSelectedItem()==resourceBundle.getString("options.yes")){
		    			reflectMaterial=true;
		    			System.out.print("\n Materiał otaczający: tak");
		    		}
		    	}
		    }
		};
		    	
		//massListener	
		ActionListener massListener = new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	if(e.getSource()==mass){
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
		    }
		};
		    	
		    
		//shapeListener
		ActionListener shapeListener = new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	if(e.getSource()==shape){
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
		
		elements.addActionListener(elementsListener);
		shapes.addActionListener(shapesListener);
		materials.addActionListener(materialListener);
		mass.addActionListener(massListener);
		shape.addActionListener(shapeListener);
		
		elements.addFocusListener(this);
		mass.addFocusListener(this);
		shape.addFocusListener(this);
		shapes.addFocusListener(this);
		materials.addFocusListener(this);
		
		
	
	    addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
            	if(correctOrNoCorrect==true){
                    System.out.println("Options: okienko opcji zamknięto poprawnie");
                    e.getWindow().dispose();
            	}
            	else if(correctOrNoCorrect==false){
            		ClosingWarning.ClosingWarning();
            		
            	}
            }
        });
	
	
	}

	
	
	@Override
	public void focusGained(FocusEvent fe) {
		if(fe.getSource()==mass || fe.getSource()==shape){
		weightLabel.setForeground(Color.GREEN);
		radiusLabel.setForeground(Color.GREEN);
		mass.setText("");
		shape.setText("");
		correctOrNoCorrect=true;
		}
		else if(fe.getSource()==elements || fe.getSource()==shapes || fe.getSource()==materials){
			weightLabel.setForeground(Color.RED); 
			radiusLabel.setForeground(Color.RED);
			mass.setText(resourceBundle.getString("options.giveMass"));
			shape.setText(resourceBundle.getString("options.giveRadius"));
			correctOrNoCorrect=false;
			
		}
	
	}

	@Override
	public void focusLost(FocusEvent fe) {
		if(fe.getSource()==mass){
			m=Double.parseDouble(mass.getText());
			mass.setText(Double.toString(m));
			System.out.println("Masa: "+m);
		}
	}
	

}