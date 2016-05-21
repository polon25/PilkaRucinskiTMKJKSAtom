package pl.pw.edu.fizyka.pojawa.TMKJKS;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ScheduledExecutorService;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.Timer;

import org.jfree.chart.ChartPanel;

/**
 * 
 * @authors Jacek Piłka & Antoni Ruciński
 * 
 * Main window
 *
 */

public class Interface extends JFrame {
	
	private static final long serialVersionUID = 185723979423401295L;
	
	private ResourceBundle resourceBundle = ResourceBundle.getBundle(
			"pl/pw/edu/fizyka/pojawa/TMKJKS/labels",new Locale(ChooseLanguage.getLocal()));
	
	static Interface window = new Interface();
	Random rand = new Random();
	Simulation simulation;
	ScheduledExecutorService exec;
	
	ChartPanel chart;
	Table table;
	
	boolean simulationStart=false;//if simulation work -> stop, else start
	boolean isChart=false;//if there's chart -> automatically remove when new simulation starts
	boolean first=true;//if it's first iteration -> update previous data in chart and table
	
	JPanel chartPanel = new JPanel(new FlowLayout());
	JPanel tablePanel = new JPanel(new FlowLayout());
	
	JTextField maxEnergy = new JTextField("");
	JTextField energy = new JTextField("");

	public Interface() throws HeadlessException {
		
		setSize(800,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setTitle(resourceBundle.getString("interface.title"));
		
		JPanel bottomPanel = new JPanel();
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		tabbedPane.addTab(resourceBundle.getString("interface.chart"), chartPanel);
		tabbedPane.addTab(resourceBundle.getString("interface.table"), tablePanel);
		
		add(BorderLayout.CENTER, tabbedPane);
		add(BorderLayout.SOUTH, bottomPanel);
		
		bottomPanel.setLayout(new GridLayout(1, 5));
		
		bottomPanel.add(new JLabel(resourceBundle.getString("interface.maxEnergy")));
		bottomPanel.add(maxEnergy);
		bottomPanel.add(new JLabel());
		bottomPanel.add(new JLabel(resourceBundle.getString("interface.currentEnergy")));
		bottomPanel.add(energy);
		
		final MenuPanel menuPanel = new MenuPanel();
		setJMenuBar(menuPanel.createMenu());
		
		menuPanel.startStopMenuItem.setEnabled(false);
		
		menuPanel.saveMenuItem.addActionListener(new ActionListener(){//Save data
			public void actionPerformed(ActionEvent e){
				Save save=new Save(simulation, MenuPanel.options);
				save.save();
			}
		});
		
		menuPanel.startStopMenuItem.addActionListener(new ActionListener(){//Start simulation
			public void actionPerformed(ActionEvent e) {
					startSimulation();
			}
		});
		
		new Timer(100, new ActionListener() {//if values in options right - enable starting simulation
			 @Override
			 public void actionPerformed(ActionEvent e) {
				 if(Options.correctOrNoCorrect==true){
					 menuPanel.startStopMenuItem.setEnabled(true);
					 ((Timer)e.getSource()).stop();
				 }
			 }
		 }).start();
	}
	
	private void startSimulation(){
		if(Options.correctOrNoCorrect==true){
			if (!simulationStart)//What do user want - start or stop?
				simulationStart=true;
			else
				simulationStart=false;
			if (simulationStart){//Starting simulation
				System.out.println("Simulation start");
				simulation=new Simulation(MenuPanel.options, window);
				if(isChart){//Remove old chart & table
					chartPanel.remove(chart);
					tablePanel.remove(table.table);
				}//Add new chart & table
				chart=new Chart(simulation, first).chartPanel;
				table = new Table(simulation);
				chartPanel.add(chart);
				tablePanel.add(table.table);
				isChart=true;
				
				JScrollPane scrollPane = new JScrollPane(table.table);
		        tablePanel.add(scrollPane);
				
				simulation.execute();
				
				new Timer(100, new ActionListener() {//Adding data to TextFields and Tables
					 @Override
					 public void actionPerformed(ActionEvent e) {
						 if(simulation.isCancelled()){//End that timer
						    ((Timer)e.getSource()).stop();
						    simulationStart=false;//Now simulations officially end
						    System.out.println("Simulation end");
						 }
						 tablePanel.remove(tablePanel.getComponent(0));
						 table.addData(first);
						 tablePanel.add(table.table);
						 JScrollPane scrollPane = new JScrollPane(table.table);
					     tablePanel.add(scrollPane);
					     validate();
						 first=false;
						 energy.setText(Double.toString(simulation.energy));
						 maxEnergy.setText(Double.toString(simulation.maxEnergy));
					 }
				 }).start();
		    	validate();
			}
			else{//Stopping simulation
				simulation.cancel(true);
				System.out.println("Simulation end");
			}
		}
		else if(Options.correctOrNoCorrect==false){//If values in option aren't right
			new ClosingWarning();
		}
	}

	public static void main(String[] args) {
		new ChooseLanguage();
		JFrame f = new Interface();
		f.setVisible(true);
	}
}



