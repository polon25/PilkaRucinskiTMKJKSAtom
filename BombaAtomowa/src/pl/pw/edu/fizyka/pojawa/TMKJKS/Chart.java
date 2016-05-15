package pl.pw.edu.fizyka.pojawa.TMKJKS;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Chart {

	 ChartPanel chartPanel;
	 XYSeries series = new XYSeries("Data");
	 XYSeriesCollection dataset = new XYSeriesCollection(series);
	 
	 Chart(Simulation simulation){
		 
		 new Timer(100, new ActionListener() {
			 @Override
			 public void actionPerformed(ActionEvent e) {
				 series.add(simulation.time, simulation.energy);
				 if(simulation.isCancelled())
			    	((Timer)e.getSource()).stop();
			 }
		 }).start();
		 JFreeChart lineGraph = ChartFactory.createXYLineChart("Energia", "Czas [us]",
				 "Energia [J]", dataset, PlotOrientation.VERTICAL, false, false, false);
	        
		 final XYPlot plot = lineGraph.getXYPlot();
		 ValueAxis xaxis = plot.getDomainAxis();
		 xaxis.setAutoRange(true);
	        
		 chartPanel = new ChartPanel(lineGraph);
		 }
}
