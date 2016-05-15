package pl.pw.edu.fizyka.pojawa.TMKJKS;





import java.awt.HeadlessException;


import javax.swing.JOptionPane;

public class ChooseLanguage { //by Antoni Rucinski
	
	static int l;
	public static String local="pl";
	
	public ChooseLanguage() throws HeadlessException {
	
		  Object[] options = { "Polski", "English" };
		  
		  l = JOptionPane.showOptionDialog(null, "Wybierz Język / Choose your language", "Język",
		  JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
		  null, options, options[0]);
		  
		  if(l==0){
			  local="pl";
		  }
		  else if(l==1){
			  local="en";
		  }
	}
	
	static String getLocal(){
		return local;
	}
	
}
