package pl.pw.edu.fizyka.pojawa.TMKJKS;
import java.awt.HeadlessException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClosingWarning { //by Antoni Rucinski
	
	static int l;
	public static int local;
	
	private static ResourceBundle resourceBundle = ResourceBundle.getBundle(
			"pl/pw/edu/fizyka/pojawa/TMKJKS/labels",new Locale(ChooseLanguage.getLocal()));
	
	
	
	public ClosingWarning() throws HeadlessException {
	
		  Object[] options = { "OK", resourceBundle.getString("cWarning.Correct")};
		  
		  l = JOptionPane.showOptionDialog(null, resourceBundle.getString("cWarning.message"), 
				  resourceBundle.getString("cWarning.title"),
		  JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
		  null, options, options[0]);
		  
		  System.out.println("Źle wypełnione okienko opcji: "+l);
		  //OK
		  if(l==0){
			  local=0;
			  System.out.println("Closing Warning: Akceptacja źle uzupełnionych opcji.");
		  }
		  //Popraw dane
		  else if(l==1){
			  System.out.println("ClosingWarning: Ponowne uzupełnienie opcji");
				System.out.println("Uruchomiono okienko opcji symulacji"); 
				MenuPanel.options.setVisible(true);
				MenuPanel.options.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		  }
	}
	
	static int getLocal(){
		return local;
	}
	
}
