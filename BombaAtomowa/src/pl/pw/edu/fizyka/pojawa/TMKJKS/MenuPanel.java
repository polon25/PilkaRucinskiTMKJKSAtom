
package pl.pw.edu.fizyka.pojawa.TMKJKS;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * 
 * @authors Jacek Piłka & Antoni Ruciński
 *
 *	Menu panel
 *
 */

public class MenuPanel extends JFrame { //by Antoni Rucinski & Jacek Piłka

	private ResourceBundle resourceBundle = ResourceBundle.getBundle(
			"pl/pw/edu/fizyka/pojawa/TMKJKS/labels",new Locale(ChooseLanguage.getLocal()));

	private static final long serialVersionUID = 1L;
	public static Options options;
	JMenuItem startStopMenuItem = new JMenuItem("Start / Stop");
	JMenuItem saveMenuItem = new JMenuItem(resourceBundle.getString("menu.saveMenuItem"));
	JMenuItem simulationOptionMenuItem = 
			new JMenuItem(resourceBundle.getString("menu.symulationOptionsMenuItem"));

	public JMenuBar createMenu(){
			
		JMenuBar menuBar = new JMenuBar();	//building main menu
		JMenu menu = new JMenu(resourceBundle.getString("menu.mainMenu"));
		menuBar.add(menu);
		
		options = new Options();

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
		menu.add(simulationOptionMenuItem);

		menuBar.add(menu);

//Listeners
		ActionListener menuListener=new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource()==closeMenuItem){
					System.out.println("Closing Program");
		            System.exit(1);
				}
				else if(e.getSource()==simulationOptionMenuItem){
					options.setVisible(true);
				}
			}
		};
		
		closeMenuItem.addActionListener(menuListener);
	
		simulationOptionMenuItem.addActionListener(menuListener);

		return menuBar;
	}
	
}

