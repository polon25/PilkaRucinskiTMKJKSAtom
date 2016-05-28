package pl.pw.edu.fizyka.pojawa.TMKJKS;

/**
 * 
 * @author Antoni Ruciński & Jacek Piłka
 * 
 * Choosing language window
 * 
 */

import java.awt.HeadlessException;
import java.util.Locale;

import javax.swing.JOptionPane;

public class ChooseLanguage {
	
	static int l;
	public static String local="pl";
	
	public ChooseLanguage() throws HeadlessException {
	
		  Object[] options = { "Polski", "English" };
		  
		  l = JOptionPane.showOptionDialog(null, "Wybierz język / Choose your language", "Język / Language",
		  JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
		  null, options, options[0]);
		  
		  if(l==0){
			  local="pl";
			  Locale.setDefault(new Locale("pl", "PL"));
		  }
		  else if(l==1){
			  local="en";
			  Locale.setDefault(new Locale("en", "GB"));
		  }
	}
	
	static String getLocal(){
		return local;
	}
	
}
