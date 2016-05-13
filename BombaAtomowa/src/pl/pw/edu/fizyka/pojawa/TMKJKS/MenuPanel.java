
package pl.pw.edu.fizyka.pojawa.TMKJKS;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;



public class MenuPanel extends JFrame { //by Antoni Rucinski

	private ResourceBundle resourceBundle = ResourceBundle.getBundle(
			"pl/pw/edu/fizyka/pojawa/TMKJKS/labels",new Locale(ChooseLanguage.getLocal()));

	private static final long serialVersionUID = 1L;
	public static  Options options = new Options();
	JMenuItem startStopMenuItem = new JMenuItem("Start / Stop");
	JMenuItem saveMenuItem = new JMenuItem(resourceBundle.getString("menu.saveMenuItem"));

	public JMenuBar createMenu(){
			
		JMenuBar menuBar = new JMenuBar();	//building main menu
		JMenu menu = new JMenu(resourceBundle.getString("menu.mainMenu"));
		menuBar.add(menu);

		//save menu Item
		menu.add(saveMenuItem);
		menu.addSeparator();
		
		//close menu item
		final JMenuItem closeMenuItem = new JMenuItem(resourceBundle.getString("menu.closeMenuItem"));
		menu.add(closeMenuItem);
		
		//simulation menu
		menu = new JMenu(resourceBundle.getString("menu.simulationMenu"));
		
		//start stop menu item
		menu.add(startStopMenuItem);
		menu.addSeparator();
	
		//simulation options menu item
		final JMenuItem symulationOptionMenuItem = new JMenuItem(resourceBundle.getString("menu.symulationOptionsMenuItem"));
		menu.add(symulationOptionMenuItem);

		menuBar.add(menu);

//Listeners
		ActionListener menuListener=new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				if(e.getSource()==closeMenuItem){
					System.out.println("Zamknięto program");
		            System.exit(1);
				}
				else if(e.getSource()==saveMenuItem){
					System.out.println("Plik zapisano");
					//TODO Make a saving instruction
				}

				else if(e.getSource()==symulationOptionMenuItem){
					System.out.println("Uruchomiono okienko opcji symulacji"); 
					options.setVisible(true);
				}
			}
		};
		
		closeMenuItem.addActionListener(menuListener);
		saveMenuItem.addActionListener(menuListener);
	
		symulationOptionMenuItem.addActionListener(menuListener);
		//startStopMenuItem.addActionListener(menuListener);

		return menuBar;
	}
	
}

