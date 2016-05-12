package pl.pw.edu.fizyka.pojawa.TMKJKS;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ScheduledExecutorService;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Interface extends JFrame { //by Antoni Rucinski & Jacek Pilka
	
	private static final long serialVersionUID = 185723979423401295L;
	Random rand = new Random();
	static Interface window = new Interface();
	private ResourceBundle resourceBundle = ResourceBundle.getBundle(
			"pl/pw/edu/fizyka/pojawa/TMKJKS/labels",new Locale(ChooseLanguage.getLocal()));
	ScheduledExecutorService exec;
	boolean simulationStart=false;
	Simulation simulation;
	
	JTextField maxEnergy = new JTextField("");
	JTextField energy = new JTextField("");

//interface constructor: creating main frame with a menu
	public Interface() throws HeadlessException {
		
		setSize(800,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setTitle(resourceBundle.getString("interface.title"));
		
		
		JPanel manePanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		
		add(BorderLayout.CENTER, manePanel);
		add(BorderLayout.SOUTH, bottomPanel);
		
		manePanel.setLayout(new GridLayout(1,2));
		bottomPanel.setLayout(new GridLayout(1, 5));
		
		//podzial na kolumny
		manePanel.add(new Mock("Wykres zmian energii od czasu"));
		manePanel.add(new Mock("Wykres zmian energii od czasu"));
		
		//final JTextField maxEnergy = new JTextField("");
		//final JTextField energy = new JTextField("");
		
		bottomPanel.add(new JLabel(resourceBundle.getString("interface.maxEnergy")));
		bottomPanel.add(maxEnergy);
		bottomPanel.add(new JLabel());
		bottomPanel.add(new JLabel(resourceBundle.getString("interface.currentEnergy")));
		bottomPanel.add(energy);
		
		final MenuPanel menuPanel = new MenuPanel();
		setJMenuBar(menuPanel.createMenu());
		
		menuPanel.saveMenuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Save save=new Save(simulation, menuPanel.options);
				save.save();
			}
		});
		
		menuPanel.startStopMenuItem.addActionListener(new ActionListener(){//Start simulation
			public void actionPerformed(ActionEvent e) {
				if (!simulationStart)
					simulationStart=true;
				else
					simulationStart=false;
				if (simulationStart){
					System.out.println("Simulation start");
					simulation=new Simulation(menuPanel.options, window);
					simulation.execute();
				}
				else{
					simulation.cancel(true);
					System.out.println("Simulation end");
				}
			}
		});
	}

	public static void main(String[] args) {
		ChooseLanguage.ChooseLanguage();
		JFrame f = new Interface();
		f.setVisible(true);
	}
}



