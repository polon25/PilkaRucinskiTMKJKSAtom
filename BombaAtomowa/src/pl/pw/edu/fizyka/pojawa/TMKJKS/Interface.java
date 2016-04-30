package pl.pw.edu.fizyka.pojawa.TMKJKS;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class Interface extends JFrame { //by Antoni Ruciñski & Jacek Pi³ka
	
	private static final long serialVersionUID = 185723979423401295L;
	Random rand = new Random();
	static JFrame window = new Interface();

//interface constructor: creating main frame with a menu
	public Interface() throws HeadlessException {
		
		setSize(800,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setTitle("Symulator Bomby Atomowej");
		
		
		JPanel manePanel = new JPanel();
		//JPanel bottomPanel = new JPanel();
		
		add(BorderLayout.CENTER, manePanel);
		//add(BorderLayout.SOUTH, bottomPanel);
		
		manePanel.setLayout(new GridLayout(1,2));
		//bottomPanel.setLayout(new GridLayout(1, 5));
		
		//podzia³ na kolumny
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
		
		MenuPanel menuPanel = new MenuPanel();
		f.setJMenuBar(menuPanel.createMenu());
		
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
		
		menuPanel.startStopMenuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ExecutorService exec = Executors.newFixedThreadPool(1);
				Simulation simulation=new Simulation(menuPanel.options.element, menuPanel.options.m, 
					menuPanel.options.V, menuPanel.options.materialShape, menuPanel.options.a);
				for(int i=0; i<20; i++){
					exec.execute(simulation);
					if(simulation.neutrons.size()<1){
						System.out.println("Brak neutronów!");
						break;
					}
					Energy.setText(Float.toString(simulation.energy));
					maxEnergy.setText(Float.toString(simulation.maxEnergy));
				}
				exec.shutdown();
				System.out.println("Koniec symulacji!");
				System.out.println("Energia maksymalna: "+simulation.maxEnergy+"J "
					+simulation.maxEnergy*Math.pow(4.184, -1)*Math.pow(10, -9));
			}
		});
	}
}