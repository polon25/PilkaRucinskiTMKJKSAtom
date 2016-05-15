package pl.pw.edu.fizyka.pojawa.TMKJKS;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

public class Table {//Jacek Piłka

	private ResourceBundle resourceBundle = ResourceBundle.getBundle(
			"pl/pw/edu/fizyka/pojawa/TMKJKS/labels",new Locale(ChooseLanguage.getLocal()));
	
	private String[] header = {resourceBundle.getString("table.time"),
		resourceBundle.getString("table.energy"),
		resourceBundle.getString("table.numberOfAtoms"),
		resourceBundle.getString("table.numberOfNeutrons"),
		resourceBundle.getString("table.numberOfFissions")};
	
	JTable table;
	DefaultTableModel dtm;
	Simulation simulation;
	
	Table(Simulation simulation){
		this.simulation=simulation;
		table = new JTable();
		dtm = new DefaultTableModel(0,0);
		dtm.setColumnIdentifiers(header);
		table.setModel(dtm);
	}
	
	public void addData(boolean first){
		if(first){
			for(int j=0; j<simulation.energies.size(); j++){
				dtm.addRow(new Object[]{j+1, simulation.energies.get(j), simulation.numbersOfAtoms.get(j), 
							simulation.numbersOfNeutrons.get(j), simulation.numbersOfFissions.get(j)});
			}
		}
		dtm.addRow(new Object[]{simulation.time, simulation.energy, simulation.numberOfAtoms, 
			simulation.numberOfNeutrons, simulation.numberOfFission});

	}
}

