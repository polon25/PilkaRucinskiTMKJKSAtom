package pl.pw.edu.fizyka.pojawa.TMKJKS;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;

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
		JFileChooser chooser = new JFileChooser(); // Stworzenie klasy
        chooser.setDialogTitle(resourceBundle.getString("save.title")); // Ustawienie tytu³u okienka
        int result = chooser.showDialog(null, resourceBundle.getString("save.button")); //Otwarcie okienka. Metoda ta blokuje siê do czasu wybrania pliku lub zamkniêcia okna
        if (JFileChooser.APPROVE_OPTION == result){ //Jeœli u¿tytkownik wybra³ plik
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
			out.println("Time [us] \t Energy [J]");
			for(int i=0; i<simulation.data.size();i++)
				out.println(simulation.data.get(i));
		    out.close();
		} 
			catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
