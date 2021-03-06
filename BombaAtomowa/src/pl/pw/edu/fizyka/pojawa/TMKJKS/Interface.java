package pl.pw.edu.fizyka.pojawa.TMKJKS;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
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
	
	SIPrefixes prefixes=new SIPrefixes();
	
	ChartPanel chartOfEnergy, chartOfAtoms, chartOfNeutrons, chartOfFissions;
	Table table;
	
	boolean simulationStart=false;//if simulation work -> stop, else start
	boolean isChart=false;//if there's chart -> automatically remove when new simulation starts
	boolean first=true;//if it's first iteration -> update previous data in chart and table
	
	JPanel chartPanel = new JPanel(new BorderLayout());
	JPanel tablePanel = new JPanel(new BorderLayout());
	
	JPanel chartPanelEnergy = new JPanel(new BorderLayout());
	JPanel chartPanelAtoms = new JPanel(new BorderLayout());
	JPanel chartPanelNeutrons = new JPanel(new BorderLayout());
	JPanel chartPanelFissions = new JPanel(new BorderLayout());
	
	JTextField maxEnergy = new JTextField("");
	JTextField energy = new JTextField("");
	JTextField maxEnergykTNT = new JTextField("");
	JTextField energykTNT = new JTextField("");
	
	MenuPanel menuPanel = new MenuPanel();

	public Interface() throws HeadlessException {
		
		setSize(850,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setTitle(resourceBundle.getString("interface.title"));
		
		JPanel bottomPanel = new JPanel();
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		tabbedPane.addTab(resourceBundle.getString("interface.chart"), chartPanel);
		tabbedPane.addTab(resourceBundle.getString("interface.table"), tablePanel);
		
		JTabbedPane tabbedPaneChart = new JTabbedPane();
		tabbedPaneChart.addTab(resourceBundle.getString("interface.chartEnergy"), chartPanelEnergy);
		tabbedPaneChart.addTab(resourceBundle.getString("interface.chartAtoms"), chartPanelAtoms);
		tabbedPaneChart.addTab(resourceBundle.getString("interface.chartNeutrons"), chartPanelNeutrons);
		tabbedPaneChart.addTab(resourceBundle.getString("interface.chartFissions"), chartPanelFissions);
		
		chartPanel.add(BorderLayout.CENTER, tabbedPaneChart);
		
		add(BorderLayout.CENTER, tabbedPane);
		add(BorderLayout.SOUTH, bottomPanel);
		
		bottomPanel.setLayout(new GridLayout(2, 5));
		
		bottomPanel.add(new JLabel(resourceBundle.getString("interface.maxEnergy")));
		bottomPanel.add(maxEnergy);
		bottomPanel.add(new JLabel());
		bottomPanel.add(new JLabel(resourceBundle.getString("interface.currentEnergy")));
		bottomPanel.add(energy);
		
		bottomPanel.add(new JLabel(resourceBundle.getString("interface.maxEnergykTNT")));
		bottomPanel.add(maxEnergykTNT);
		bottomPanel.add(new JLabel());
		bottomPanel.add(new JLabel(resourceBundle.getString("interface.currentEnergykTNT")));
		bottomPanel.add(energykTNT);
		
		setJMenuBar(menuPanel.createMenu());

		menuPanel.startStopMenuItem.setEnabled(false);
		menuPanel.saveMenuItem.setEnabled(false);

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
				menuPanel.saveMenuItem.setEnabled(false);
				menuPanel.simulationOptionMenuItem.setEnabled(false);
				simulation=new Simulation(MenuPanel.options, window);
				if(isChart){//Remove old chart & table
					removeChartAndTable();
				}//Add new chart & table
				addChartAndTable();
				simulation.execute();
				first=false;
				timer();
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
	
	private void addChartAndTable(){
		chartOfEnergy=new Chart(simulation, simulation.energies, first).chartPanel;
		chartOfAtoms=new Chart(simulation, simulation.numbersOfAtoms, first).chartPanel;
		chartOfNeutrons=new Chart(simulation, simulation.numbersOfNeutrons, first).chartPanel;
		chartOfFissions=new Chart(simulation, simulation.numbersOfFissions, first).chartPanel;
		
		chartPanelEnergy.add(BorderLayout.CENTER, chartOfEnergy);
		chartPanelAtoms.add(BorderLayout.CENTER, chartOfAtoms);
		chartPanelNeutrons.add(BorderLayout.CENTER, chartOfNeutrons);
		chartPanelFissions.add(BorderLayout.CENTER, chartOfFissions);
		
		table = new Table(simulation);
		tablePanel.add(table.table);
		
		isChart=true;
	}
	
	private void removeChartAndTable(){
		chartPanelEnergy.remove(chartOfEnergy);
		chartPanelAtoms.remove(chartOfAtoms);
		chartPanelNeutrons.remove(chartOfNeutrons);
		chartPanelFissions.remove(chartOfFissions);
		tablePanel.remove(table.table);
	}
	
	private void timer(){
		new Timer(100, new ActionListener() {//Adding data to TextFields and Tables
			 @Override
			 public void actionPerformed(ActionEvent e) {
				 if(simulation.isCancelled()){//End that timer
				    simulationStart=false;//Now simulations officially end
				    System.out.println("Simulation end");
				    JOptionPane.showMessageDialog(
	                        window, resourceBundle.getString("interface.endSimulationDialogMessage"),
	                        resourceBundle.getString("interface.endSimulationDialogTitle"),
	                        JOptionPane.INFORMATION_MESSAGE);
				    menuPanel.saveMenuItem.setEnabled(true);
				    menuPanel.simulationOptionMenuItem.setEnabled(true);
				    first=true;
				    ((Timer)e.getSource()).stop();
				 }
				 tablePanel.remove(tablePanel.getComponent(0));
				 table.addData(first);
				 tablePanel.add(BorderLayout.CENTER, table.table);
				 JScrollPane scrollPane = new JScrollPane(table.table);
			     tablePanel.add(scrollPane);
				 energy.setText(prefixes.format(simulation.energy));
				 maxEnergy.setText(prefixes.format(simulation.maxEnergy));
				 energykTNT.setText(prefixes.format(simulation.energykTNT));
				 maxEnergykTNT.setText(prefixes.format(simulation.maxEnergykTNT));
				 validate();
			 }
		 }).start();
	}

	public static void main(String[] args) {
		new ChooseLanguage();
		JFrame f = new Interface();
		f.setVisible(true);
		f.setLocationRelativeTo(null);
	}
}