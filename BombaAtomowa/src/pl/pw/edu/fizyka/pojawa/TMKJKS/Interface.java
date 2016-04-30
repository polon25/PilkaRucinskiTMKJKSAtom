package pl.pw.edu.fizyka.pojawa.TMKJKS;

import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.util.Random;


import javax.swing.JPanel;
import javax.swing.JTextField;

public class Interface extends JFrame { //by Antoni Ruciński & Jacek Piłka
	
	private static final long serialVersionUID = 185723979423401295L;
	Random rand = new Random();
	static JFrame window = new Interface();

//interface constructor: creating main frame with a menu
	public Interface() throws HeadlessException {
		
		setSize(800,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setTitle("Symulator Bomby Atomowej");
		
		//create a menu
		MenuPanel menuPanel = new MenuPanel();
		setJMenuBar(menuPanel.createMenu());
		
		
		
		JPanel manePanel = new JPanel();
		//JPanel bottomPanel = new JPanel();
		
		add(BorderLayout.CENTER, manePanel);
		//add(BorderLayout.SOUTH, bottomPanel);
		
		manePanel.setLayout(new GridLayout(1,2));
		//bottomPanel.setLayout(new GridLayout(1, 5));
		
		//podział na kolumny
		manePanel.add(new Mock("Wykres zmian energii od czasu"));
		manePanel.add(new Mock("Wykres zmian energii od czasu"));
		
		//JTextField maxEnergy = new JTextField("");
		//JTextField Energy = new JTextField("");
		
		/**bottomPanel.add(new JLabel("Energia maksymalna[J]:"));
		bottomPanel.add(maxEnergy);
		bottomPanel.add(new JLabel());
		bottomPanel.add(new JLabel("Energia obecnie[J]:"));
		bottomPanel.add(Energy);**/
		
	}
	

	public static void main(String[] args) {
		JFrame f = new Interface();
		
		
		
		JPanel bottomPanel = new JPanel();
		f.add(BorderLayout.SOUTH, bottomPanel);
		bottomPanel.setLayout(new GridLayout(1, 5));
		
		JTextField maxEnergy = new JTextField("");
		JTextField Energy = new JTextField("");
		
		bottomPanel.add(new JLabel("Energia maksymalna[J]:"));
		bottomPanel.add(maxEnergy);
		bottomPanel.add(new JLabel());
		bottomPanel.add(new JLabel("Energia obecnie[J]:"));
		bottomPanel.add(Energy);
		
		f.setVisible(true);
		
		Simulation simulation=new Simulation("Uran", 52, 0.0027296590402, "Kwadrat", 0.13975568);
		//ExecutorService exec = Executors.newFixedThreadPool(1);
		for(int i=0; i<40; i++){
			//exec.execute(simulation);
			simulation.simulate();
			if(simulation.neutrons.size()<1){
				System.out.println("Brak neutronów!");
				break;
			}
			Energy.setText(Float.toString(simulation.energy));
			maxEnergy.setText(Float.toString(simulation.maxEnergy));
		}
		//exec.shutdown();
		System.out.println("Koniec symulacji!");
		System.out.println("Energia maksymalna: "+simulation.maxEnergy+"J "+simulation.maxEnergy*Math.pow(4.184, -1)*Math.pow(10, -9));
	}
}