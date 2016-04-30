package pl.pw.edu.fizyka.pojawa.TMKJKS;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuPanel extends JFrame { //by Antoni Rucinski

	private static final long serialVersionUID = 1L;

	public JMenuBar createMenu(){
		
		
		JMenuBar menuBar = new JMenuBar();	//building main menu
		JMenu menu = new JMenu("Plik");
		menuBar.add(menu);

		final JMenuItem saveMenuItem = new JMenuItem("Zapisz");
		menu.add(saveMenuItem);
		menu.addSeparator();
		
		JMenu submenu = new JMenu("Język");
		
		final JMenuItem polishLanguageMenuItem = new JMenuItem("polski");
		submenu.add(polishLanguageMenuItem);
		submenu.addSeparator();	
		
		final JMenuItem englishLanguageMenuItem= new JMenuItem("angielski");
		submenu.add(englishLanguageMenuItem);
		menu.add(submenu);
		
		menu.addSeparator();
		final JMenuItem closeMenuItem = new JMenuItem("Zamknij");
		menu.add(closeMenuItem);
		
		menu = new JMenu("Symulacja");
		
		final JMenuItem startStopMenuItem = new JMenuItem("Start / Stop");
		menu.add(startStopMenuItem);
		submenu.addSeparator();
		
		final JMenuItem symulationOptionMenuItem = new JMenuItem("Opcje Symulacji");
		menu.add(symulationOptionMenuItem);

		menuBar.add(menu);
		
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
				else if(e.getSource()==polishLanguageMenuItem){
					System.out.println("Zmiana języka na polski");  
					//TODO Make a changing language instruction
				}
				else if(e.getSource()==englishLanguageMenuItem){
					System.out.println("Zmiana języka na polski");  
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
	
}
