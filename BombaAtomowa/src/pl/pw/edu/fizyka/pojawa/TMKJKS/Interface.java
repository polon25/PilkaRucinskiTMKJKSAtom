package pl.pw.edu.fizyka.pojawa.TMKJKS;

import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Interface extends JFrame { //by Antoni Ruciñski & Jacek Pi³ka
	
	private static final long serialVersionUID = 185723979423401295L;
	Random rand = new Random();
	static JFrame window = new Interface();

//interface constructor	
	public Interface() throws HeadlessException {
		
		setSize(800,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setTitle("Symulator Bomby Atomowej");
		
		setJMenuBar(createMenu());
		
		JPanel manePanel = new JPanel();
		//JPanel bottomPanel = new JPanel();
		
		add(BorderLayout.CENTER, manePanel);
		//add(BorderLayout.SOUTH, bottomPanel);
		
		manePanel.setLayout(new GridLayout(1,2));
		//bottomPanel.setLayout(new GridLayout(1, 5));
		
		//podziaÅ‚ na kolumny
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
	
	public JMenuBar createMenu(){

		JMenuBar menuBar = new JMenuBar();	//building main menu
		JMenu menu = new JMenu("Plik");
		menuBar.add(menu);

		JMenuItem saveMenuItem = new JMenuItem("Zapisz");
		menu.add(saveMenuItem);
		menu.addSeparator();
		
		JMenu submenu = new JMenu("Jêzyk");
		
		JMenuItem polishLanguageMenuItem = new JMenuItem("polski");
		submenu.add(polishLanguageMenuItem);
		submenu.addSeparator();	
		
		JMenuItem englishLanguageMenuItem= new JMenuItem("angielski");
		submenu.add(englishLanguageMenuItem);
		menu.add(submenu);
		
		menu.addSeparator();
		JMenuItem closeMenuItem = new JMenuItem("Zamknij");
		menu.add(closeMenuItem);
		
		menu = new JMenu("Symulacja");
		
		JMenuItem startStopMenuItem = new JMenuItem("Start / Stop");
		menu.add(startStopMenuItem);
		submenu.addSeparator();
		
		JMenuItem symulationOptionMenuItem = new JMenuItem("Opcje Symulacji");
		menu.add(symulationOptionMenuItem);

		menuBar.add(menu);
		
		ActionListener menuListener=new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource()==closeMenuItem){
					System.out.println("Zamkniêto program");
		            System.exit(1);
				}
				else if(e.getSource()==saveMenuItem){
					System.out.println("Plik zapisano");
					//TODO Make a saving instruction
				}
				else if(e.getSource()==polishLanguageMenuItem){
					System.out.println("Zmiana jêzyka na polski");  
					//TODO Make a changing language instruction
				}
				else if(e.getSource()==englishLanguageMenuItem){
					System.out.println("Zmiana jêzyka na polski");  
					//TODO Make a changing language instruction
				}
				else if(e.getSource()==symulationOptionMenuItem){
					System.out.println("Uruchomiono okienko opcji symulacji"); 
					Options opcje = new Options();
					opcje.setVisible(true);
				}
				else if(e.getSource()==startStopMenuItem){
					System.out.println("uruchomiono okienko opcji symulacji");
					//TODO Make an option window instruction
				}
			}
		};
		
		closeMenuItem.addActionListener(menuListener);
		saveMenuItem.addActionListener(menuListener);
		polishLanguageMenuItem.addActionListener(menuListener);
		englishLanguageMenuItem.addActionListener(menuListener);
		symulationOptionMenuItem.addActionListener(menuListener);
		startStopMenuItem.addActionListener(menuListener);

		return menuBar;
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