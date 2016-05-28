package pl.pw.edu.fizyka.pojawa.TMKJKS;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;

/**
 * 
 * @author Jacek Pi³ka
 * 
 * Saving data to text file
 *
 */

public class Save {
	
	private ResourceBundle resourceBundle = ResourceBundle.getBundle(
			"pl/pw/edu/fizyka/pojawa/TMKJKS/labels",new Locale(ChooseLanguage.getLocal()));
	
	Simulation simulation;
	Options options;
	
	public Save(Simulation simulation, Options options){
		this.simulation=simulation;
		this.options=options;
	}
	
	public File fileChooser(){
		JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(resourceBundle.getString("save.title"));
        int result = chooser.showDialog(null, resourceBundle.getString("save.button"));
        if (JFileChooser.APPROVE_OPTION == result){
        	return chooser.getSelectedFile();
        }
        else {
            System.out.println("Nie wybrano pliku");
            return null;
        }
	}
	public void save(){
		try {
			File plik = fileChooser();
			PrintWriter out = new PrintWriter(plik);
			if(options.element.equals(resourceBundle.getString("options.name1")))
				out.println("Element: "+resourceBundle.getString("options.name1"));
			else if(options.element.equals(resourceBundle.getString("options.name2")))
				out.println("Element: "+resourceBundle.getString("options.name2"));
			if(simulation.shape.equals("Cube"))
				out.println("Shape: "+simulation.shape+"\t a: "+simulation.a);
			else if(simulation.shape.equals("Ball"))
				out.println("Shape: "+simulation.shape+"\t r: "+simulation.r);
			out.println("Time [us] \t Energy [J] \t Atoms \t Neutrons \t Fissions");
			for(int i=0; i<simulation.data.size();i++)
				out.println(simulation.data.get(i));
		    out.close();
		} 
			catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
