package pl.pw.edu.fizyka.pojawa.TMKJKS;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @author Jacek Piłka & Antoni Ruciński
 * 
 * Option window
 *
 */

public class Options extends JFrame implements FocusListener {

	private static final long serialVersionUID = 1L;
	
	private ResourceBundle resourceBundle = ResourceBundle.getBundle(
			"pl/pw/edu/fizyka/pojawa/TMKJKS/labels",new Locale(ChooseLanguage.getLocal()));

	static JFrame window = new Options();
	public double V=0;
	double densityU = 19050;
	double densityP = 19816;
	public double m=0;
	public float r=0;
	public float a=0;
	public double accuracyFactor = 3.5;
	String shapeText = resourceBundle.getString("options.radius");
	public String materialShape = resourceBundle.getString("options.shape1");
	public String element = resourceBundle.getString("options.element1");
	public boolean reflectMaterial = true;
	public static boolean correctOrNoCorrect=false;
	
	JLabel simulationTypeLabel = new JLabel(resourceBundle.getString("options.accuracy"));
	JLabel elementLabel = new JLabel(resourceBundle.getString("options.element"));
	JLabel shapeLabel = new JLabel(resourceBundle.getString("options.shape"));
	JLabel sMaterialLabel = new JLabel(resourceBundle.getString("options.surroundingMaterial"));
	JLabel weightLabel = new JLabel(resourceBundle.getString("options.weight"));
	final JLabel radiusLabel = new JLabel (resourceBundle.getString("options.radius"));
	
	final JTextField mass = new JTextField(resourceBundle.getString("options.giveMass"));
	final JTextField shape = new JTextField(resourceBundle.getString("options.giveRadius"));
	
	String stringSimulationType[]={resourceBundle.getString("options.accuracy1"), 
			resourceBundle.getString("options.accuracy2"), 
			resourceBundle.getString("options.accuracy3")};
	final JComboBox simulationType = new JComboBox(stringSimulationType);
	String stringElements[]={resourceBundle.getString("options.element1"),
			resourceBundle.getString("options.element2")};
	final JComboBox elements = new JComboBox(stringElements);
	String stringShapes[]={resourceBundle.getString("options.shape1"), 
			resourceBundle.getString("options.shape2")};
	final JComboBox shapes = new JComboBox(stringShapes);
	String stringSMaterials[]={resourceBundle.getString("options.yes"), 
			resourceBundle.getString("options.no")};
	final JComboBox sMaterials = new JComboBox(stringSMaterials);
	
	
	public Options(){
		
		setSize(500,300);
		setTitle(resourceBundle.getString("options.title"));
		setLayout(new BorderLayout());
		
		JPanel manePanel = new JPanel();
		
		add(BorderLayout.CENTER, manePanel);
		
		manePanel.setLayout(new GridLayout(1,4));
		
		JPanel leftPanelA = new JPanel();
		leftPanelA.setLayout(new GridLayout(4,1));
		leftPanelA.add(new JLabel());
		leftPanelA.add(elementLabel);
		leftPanelA.add(shapeLabel);
		leftPanelA.add(radiusLabel);
		manePanel.add(leftPanelA);
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(4,1));
		leftPanel.add(new JLabel());
		leftPanel.add(elements);
		leftPanel.add(shapes);
		leftPanel.add(shape);
		manePanel.add(leftPanel);
		
		JPanel rightPanelA = new JPanel();
		rightPanelA.setLayout(new GridLayout(4,1));
		rightPanelA.add(new JLabel());
		rightPanelA.add(sMaterialLabel);
		rightPanelA.add(simulationTypeLabel);
		rightPanelA.add(weightLabel);
		manePanel.add(rightPanelA);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(4,1));
		rightPanel.add(new JLabel());
		rightPanel.add(sMaterials);
		rightPanel.add(simulationType);
		rightPanel.add(mass);
		manePanel.add(rightPanel);
		
		weightLabel.setForeground(Color.RED);
		radiusLabel.setForeground(Color.RED);
		
		add(BorderLayout.SOUTH, new JPanel());
		
		mass.setToolTipText(resourceBundle.getString("options.tip.mass"));
		shape.setToolTipText(resourceBundle.getString("options.tip.shape"));
		elements.setToolTipText(resourceBundle.getString("options.tip.elements"));
		shapes.setToolTipText(resourceBundle.getString("options.tip.shapes"));
		sMaterials.setToolTipText(resourceBundle.getString("options.tip.material"));

		/**
		 * 
		 * Listeners
		 * 
		 */
		
		elements.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	element=(String) elements.getSelectedItem();
		    	if(elements.getSelectedItem()==resourceBundle.getString("options.element1"))
		    		V=m/densityU;
		    	else if(elements.getSelectedItem()==resourceBundle.getString("options.element2"))
		    		V=m/densityP;
		    	if(shapes.getSelectedItem()==resourceBundle.getString("options.shape1")){
					r=(float) Math.cbrt(3*V/(3.14*4));
					shape.setText(String.valueOf(r));
				}
				else if(shapes.getSelectedItem()==resourceBundle.getString("options.shape2")){
					a=(float) Math.cbrt(V);
					shape.setText(String.valueOf(a));
				}
		    }
		});
	
		shapes.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	if(e.getSource()==shapes){
		    		if(shapes.getSelectedItem()==resourceBundle.getString("options.shape1")){
	    				shapeText=resourceBundle.getString("options.radius");
	    			}
	    			else if(shapes.getSelectedItem()==resourceBundle.getString("options.shape2")){
	    				shapeText=resourceBundle.getString("options.edge");
	    			}
		    		if(shapes.getSelectedItem()==resourceBundle.getString("options.shape1")){
						r=(float) Math.cbrt(3*V/(3.14*4));
						shape.setText(String.valueOf(r));
					}
					else if(shapes.getSelectedItem()==resourceBundle.getString("options.shape2")){
						a=(float) Math.cbrt(V);
						shape.setText(String.valueOf(a));
					}
		    		radiusLabel.setText(shapeText);
		    		materialShape=(String) shapes.getSelectedItem();
		    	}
		    	
		    }
		});

		sMaterials.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	if(sMaterials.getSelectedItem()==resourceBundle.getString("options.no")){
		    		reflectMaterial=false;
		    	}
		    	else if(sMaterials.getSelectedItem()==resourceBundle.getString("options.yes")){
		    		reflectMaterial=true;
		    	}
		    }
		});
		
		simulationType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(simulationType.getSelectedItem()==resourceBundle.getString("options.accuracy1")){
					accuracyFactor=3.5;
				}
				else if(simulationType.getSelectedItem()==resourceBundle.getString("options.accuracy2")){
					accuracyFactor=4.0;
				}
				else if(simulationType.getSelectedItem()==resourceBundle.getString("options.accuracy3")){
					accuracyFactor=4.7;
				}
			}
		});
		    	
		mass.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	setMass();
		    }
		});
		    	
		shape.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	setDimension();
		    }
		});

		mass.addFocusListener(this);
		shape.addFocusListener(this);
		
	    addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e)
            {
            	if(correctOrNoCorrect==true){
                    e.getWindow().dispose();
            	}
            	else if(correctOrNoCorrect==false){
            		new ClosingWarning();
            	}
            }
        });
	}
	
	/**
	 * Focus Listeners
	 */
	
	public void focusGained(FocusEvent fe) {
		if(fe.getSource()==mass || fe.getSource()==shape){
			if(fe.getSource()==mass)
				mass.setText("");
			else
				shape.setText("");
		}
	}

	public void focusLost(FocusEvent fe) {
		if(fe.getSource()==mass)
			setMass();
		if(fe.getSource()==shape)
    		setDimension();
	}
	
	/**
	 * Checking and setting values from TextFields
	 */
	
	void checkMass(){
		if(m>0){
			correctOrNoCorrect=true;
			weightLabel.setForeground(Color.GREEN);
			radiusLabel.setForeground(Color.GREEN);
		}
		else{
			correctOrNoCorrect=false;
			weightLabel.setForeground(Color.RED);
			radiusLabel.setForeground(Color.RED);
		}
	}
	
	void setMass(){
		double tmpM=m;
		try{
			m=Double.parseDouble(mass.getText());
		} catch (NumberFormatException e){
			m=tmpM;
			mass.setText(String.valueOf(m));
		}
		if(elements.getSelectedItem()==resourceBundle.getString("options.element1"))
			V=m/densityU;
		else if(elements.getSelectedItem()==resourceBundle.getString("options.element2"))
			V=m/densityP;
		if(shapes.getSelectedItem()==resourceBundle.getString("options.shape1")){
			r=(float) Math.cbrt(3*V/(3.14*4));
			shape.setText(String.valueOf(r));
		}
		else if(shapes.getSelectedItem()==resourceBundle.getString("options.shape2")){
			a=(float) Math.cbrt(V);
			shape.setText(String.valueOf(a));
		}
		checkMass();
	}
	
	void setDimension(){
		if(shapes.getSelectedItem()==resourceBundle.getString("options.shape1")){
			float tmpR=r;
			try{
				r=Float.parseFloat(shape.getText());
			} catch (NumberFormatException e){
				r=tmpR;
				shape.setText(String.valueOf(r));
			}
			V=4*3.14*r*r*r/3;
		}
		else if(shapes.getSelectedItem()==resourceBundle.getString("options.shape2")){
			float tmpA=a;
			try{
				a=Float.parseFloat(shape.getText());
			} catch (NumberFormatException e){
				a=tmpA;
				shape.setText(String.valueOf(a));
			}
			V=a*a*a;
		}
		if(elements.getSelectedItem()==resourceBundle.getString("options.element1")){
			m=V*densityU;
			mass.setText(String.valueOf(m));
		}
		else if(elements.getSelectedItem()==resourceBundle.getString("options.element2")){
			m=V*densityP;
			mass.setText(String.valueOf(m));
		}
		mass.setText(String.valueOf(m));
		checkMass();
	}
}
