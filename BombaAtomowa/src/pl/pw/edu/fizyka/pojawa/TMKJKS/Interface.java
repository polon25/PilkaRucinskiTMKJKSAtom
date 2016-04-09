package pl.pw.edu.fizyka.pojawa.TMKJKS;

import java.awt.HeadlessException;

import javax.swing.JFrame;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;


import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



public class Interface extends JFrame {
	
	private static final long serialVersionUID = 185723979423401295L;
	Random rand = new Random();

//creating main menu method
	public JMenuBar createMenu(){
		
		JMenuBar menuBar;
		JMenu menu;
		JMenu submenu;
		
		
		JMenuItem closeMenuItem;
		JMenuItem saveMenuItem;
		
		JMenuItem polishLanguageMenuItem;
		JMenuItem englishLanguageMenuItem;
		
		JMenuItem symulationOptionMenuItem;
		JMenuItem startStopMenuItem;

		
		
		
		//creating the menu bar
		menuBar = new JMenuBar();
	    
		//building main menu
		menu = new JMenu("Plik");
		menuBar.add(menu);

	
		saveMenuItem = new JMenuItem("Zapisz");
		menu.add(saveMenuItem);
		menu.addSeparator();
		
		submenu = new JMenu("Język");
		
		polishLanguageMenuItem = new JMenuItem("polski");
		submenu.add(polishLanguageMenuItem);
		submenu.addSeparator();	
		
		englishLanguageMenuItem= new JMenuItem("angielski");
		submenu.add(englishLanguageMenuItem);
		menu.add(submenu);
		
		menu.addSeparator();
		closeMenuItem = new JMenuItem("Zamknij");
		menu.add(closeMenuItem);
		
		menu = new JMenu("Symulacja");
		
		startStopMenuItem = new JMenuItem("Start / Stop");
		menu.add(startStopMenuItem);
		submenu.addSeparator();
		
		symulationOptionMenuItem = new JMenuItem("Opcje Symulacji");
		menu.add(symulationOptionMenuItem);

		

		
		

		
		
		menuBar.add(menu);
		
		
//listeners
		//close button listener
		closeMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            System.out.println("Zamknięto system!");
	            System.exit(1);
			}
		});
		
		//save button listener
		saveMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            System.out.println("Plik zapisano");
	            
	            //msc na instrukcje do zapisu pliku
	            
			}
		});
		
		//polish language button listener
		polishLanguageMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            System.out.println("zmiana jezyka na polski");
	            
	            //msc na instrukcje do zmiany jezyka na polski
	            
			}
		});
		
		
		//English language button listener
		englishLanguageMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            System.out.println("zmiana jezyka na angielksi");
	            
	            // msc na instrukcje do zmiany jezyka na angielki
	            
	            
			}
		});
		
		//symulation option button listener
		symulationOptionMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            System.out.println("uruchomiono okienko opcji symulacji");
	            
	            // msc na instrukcje otworzenia okienka symulacji
	            
	            
			}
		});
		
		//start stop symulation button listener
		startStopMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            System.out.println("uruchomiono okienko opcji symulacji");
	            
	            // msc na instrukcje start stop symulacji
	            
	            
			}
		});
		
		
		return menuBar;
	}

	
	private static final int CONTROL_PANEL_FILLER = 5;
	
	JPanel leftPanel = new JPanel();
	JPanel rightPanel = new JPanel();

//interface constructor	
	public Interface() throws HeadlessException {
		
		super();
		setSize(800,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		setJMenuBar(createMenu());
		
		//podział na kolumny
		GridLayout gLayout = new GridLayout(1,2); 
		setLayout(gLayout);
		add(leftPanel);
		add(rightPanel);
		
		//lewa kolumna
		leftPanel.setLayout(new BorderLayout());
		leftPanel.add(new Mock("Wykres zmian energii od czasu"), BorderLayout.CENTER);

		rightPanel.setLayout(new BorderLayout());
		rightPanel.add(new Mock("Symulacja ruchu atomów"), BorderLayout.CENTER);

	}

	public static void main(String[] args) {
		JFrame f = new Interface();
		f.setVisible(true);
      //dodanie rysowania myszy
		
	    KeyClass keyClass=new KeyClass();

	    f.addKeyListener(keyClass);
	    f.setFocusable(true);
		
		
	}
	
	



}





//obsługa klawiatury

class KeyClass extends JPanel implements KeyListener{

    public void keyTyped(KeyEvent e) {
    	if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {  
    		System.out.println("System zamknięto!");
            System.exit(1);
        }
    }
    public void keyPressed(KeyEvent e) {
    	if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {  
    		System.out.println("System zamknięto!");
            System.exit(1);
        }
    }
	
    public void keyReleased(KeyEvent e) {
    	if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {  
    		System.out.println("System zamknięto!");
            System.exit(1);
        }


    }
}