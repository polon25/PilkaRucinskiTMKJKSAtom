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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Interface extends JFrame { //by Antoni Rucinski & Jacek Pilka
	
	private static final long serialVersionUID = 185723979423401295L;
	Random rand = new Random();
	static JFrame window = new Interface();
	private ResourceBundle resourceBundle = ResourceBundle.getBundle(
			"pl/pw/edu/fizyka/pojawa/TMKJKS/labels",new Locale(ChooseLanguage.getLocal()));

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
		
		final JTextField maxEnergy = new JTextField("");
		final JTextField Energy = new JTextField("");
		
		bottomPanel.add(new JLabel(resourceBundle.getString("interface.maxEnergy")));
		bottomPanel.add(maxEnergy);
		bottomPanel.add(new JLabel());
		bottomPanel.add(new JLabel(resourceBundle.getString("interface.currentEnergy")));
		bottomPanel.add(Energy);
		
		final MenuPanel menuPanel = new MenuPanel();
		setJMenuBar(menuPanel.createMenu());
		
		menuPanel.startStopMenuItem.addActionListener(new ActionListener(){//Start simulation
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						ExecutorService exec = Executors.newFixedThreadPool(1);//Multithreading
						Simulation simulation=new Simulation(menuPanel.options);
						for(int i=0; i<20; i++){
							Future<Float[]> e = exec.submit(simulation);
							//exec.execute(simulation);
							//simulation.run();
							//if(simulation.neutrons.size()<1){
								//System.out.println("Brak neutronów!");
								//break;
							//}
							try {
								Energy.setText(Float.toString(e.get()[0]));
								maxEnergy.setText(Float.toString(e.get()[1]));
								System.out.println("Energia maksymalna: "+e.get()[1]+"J "
										+e.get()[1]*Math.pow(4.184, -1)*Math.pow(10, -9));
							} catch (InterruptedException | ExecutionException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						exec.shutdown();
						System.out.println("Koniec symulacji!");
						System.out.println("Energia maksymalna: "+simulation.maxEnergy+"J "
								+simulation.maxEnergy*Math.pow(4.184, -1)*Math.pow(10, -9));
					}
				});
			}
		});
	}

	public static void main(String[] args) {
		ChooseLanguage.ChooseLanguage();
		JFrame f = new Interface();
		f.setVisible(true);
	}
}



