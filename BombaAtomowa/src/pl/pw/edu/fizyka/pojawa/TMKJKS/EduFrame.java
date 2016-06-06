package pl.pw.edu.fizyka.pojawa.TMKJKS;

import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * 
 * @author Antoni Ruciński
 * 
 * Education Frame
 *
 */

public class EduFrame {
	
	private static ResourceBundle resourceBundle = ResourceBundle.getBundle(
			"pl/pw/edu/fizyka/pojawa/TMKJKS/labels",new Locale(ChooseLanguage.getLocal()));
	
	public EduFrame(){

	    JFrame f = new JFrame(resourceBundle.getString("edu.title"));
	    final JPanel upperPanel = new JPanel();
	    JPanel lowerPanel = new JPanel();
	    f.getContentPane().add(upperPanel, "North");
	    f.getContentPane().add(lowerPanel, "South");
	    f.setSize(250,300);
	    
		JButton buttonA=new JButton(resourceBundle.getString("edu.buttonA"));
		JButton buttonB=new JButton(resourceBundle.getString("edu.buttonB"));
		JButton buttonC=new JButton(resourceBundle.getString("edu.buttonC"));

	    lowerPanel.add(buttonA);
	    lowerPanel.add(buttonB);
	    lowerPanel.add(buttonC);

	    final JTextArea ta = new JTextArea(content, 20, 30);
	    ta.setLineWrap(true);
	    upperPanel.add(new JScrollPane(ta));

	    ta.setLineWrap(true);
	    ta.setWrapStyleWord(true);
	    upperPanel.add(new JScrollPane(ta));
	    ta.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
	    ta.setEditable(false);
	    

		/**
		 * Focus Listeners
		 */
	   	ActionListener buttonAListener = new ActionListener() {
	   	public void actionPerformed(ActionEvent e) {
	   		ta.setText(null);
	   		ta.append(content);	
	   		}
	   	};
	   	
	   	ActionListener buttonBListener = new ActionListener() {
	   	//instrukcja po wciśnięciu przycisku
	   	public void actionPerformed(ActionEvent e) {
	   		ta.setText(null);
	   		ta.append(content2);
	   		
	   		}
	   	};
	   	
	   	ActionListener buttonCListener = new ActionListener() {
	   	public void actionPerformed(ActionEvent e) {
	   		ta.setText(null);
	   		ta.append(content3);
	   		
	   		}
	   	};
	   		
	   	//dodanie akcji do dodanego przycisku
	   	buttonA.addActionListener(buttonAListener);
	   	buttonB.addActionListener(buttonBListener);
	   	buttonC.addActionListener(buttonCListener);

	    f.pack();
	    f.setVisible(true);
	    f.setLocationRelativeTo(null);
	    f.setResizable(false);
		
	}

  public static void main(String[] args) {
	 new EduFrame(); 

  }

  static String content = resourceBundle.getString("edu.nuclearWeapon");
  static String content2 = resourceBundle.getString("edu.criticalMass");
  static String content3 = resourceBundle.getString("edu.chainReaction");

}