package pl.pw.edu.fizyka.pojawa.TMKJKS;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.Timer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class Chart {//by Jacek Pi�ka

	
	private ResourceBundle resourceBundle = ResourceBundle.getBundle(
			"pl/pw/edu/fizyka/pojawa/TMKJKS/labels",new Locale(ChooseLanguage.getLocal()));

	 ChartPanel chartPanel;
	 XYSeries series = new XYSeries("Data");
	 XYSeriesCollection dataset = new XYSeriesCollection(series);
	 
	 int i=0;
	 
	 Chart(final Simulation simulation, boolean first){
		 
		 new Timer(1, new ActionListener() {
			 @Override
			 public void actionPerformed(ActionEvent e) {
				 if(first){
					 for(int j=0; j<simulation.energies.size(); j++){
						 series.add(j+1, simulation.energies.get(j));
					 }
				 }
				 series.add(simulation.time, simulation.energy);
				 if(simulation.isCancelled())
			    	((Timer)e.getSource()).stop();
				 i++;
			 }
		 }).start();
		 JFreeChart lineGraph = ChartFactory.createXYLineChart(resourceBundle.getString("chart.energy"), 
				 resourceBundle.getString("chart.X"),
				 resourceBundle.getString("chart.Y"),
				 dataset, PlotOrientation.VERTICAL, false, false, false);
	        
		 final XYPlot plot = lineGraph.getXYPlot();
		 ValueAxis xaxis = plot.getDomainAxis();
		 xaxis.setAutoRange(true);
	        
		 chartPanel = new ChartPanel(lineGraph);
		 }
}
