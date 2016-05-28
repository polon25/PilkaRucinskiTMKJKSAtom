package pl.pw.edu.fizyka.pojawa.TMKJKS;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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

/**
 * 
 * @author Jacek Piłka
 * 
 * Dynamic chart
 *
 */

public class Chart {

	
	private ResourceBundle resourceBundle = ResourceBundle.getBundle(
			"pl/pw/edu/fizyka/pojawa/TMKJKS/labels",new Locale(ChooseLanguage.getLocal()));

	 ChartPanel chartPanel;
	 XYSeries series1 = new XYSeries("Data");
	 XYSeriesCollection dataset1 = new XYSeriesCollection(series1);
	 
	 int i=0;
	 
	 Chart(final Simulation simulation, ArrayList<Double> dataList, boolean first){
		 
		 new Timer(1, new ActionListener() {
			 @Override
			 public void actionPerformed(ActionEvent e) {
				 if(first){//Synchronize old data
					 for(int j=0; j<dataList.size(); j++){
						 series1.add(j+1, dataList.get(j));
					 }
				 }
				 series1.add(simulation.time, dataList.get(dataList.size()-1));
				 if(simulation.isCancelled())
			    	((Timer)e.getSource()).stop();
				 i++;
			 }
		 }).start();
		 JFreeChart lineGraph = ChartFactory.createXYLineChart(resourceBundle.getString("chart.energy"), 
				 resourceBundle.getString("chart.X"),
				 resourceBundle.getString("chart.Y"),
				 dataset1, PlotOrientation.VERTICAL, false, false, false);
	        
		 final XYPlot plot = lineGraph.getXYPlot(); 
		 ValueAxis xaxis = plot.getDomainAxis();
		 xaxis.setAutoRange(true);
	        
		 chartPanel = new ChartPanel(lineGraph);
		 }
}
