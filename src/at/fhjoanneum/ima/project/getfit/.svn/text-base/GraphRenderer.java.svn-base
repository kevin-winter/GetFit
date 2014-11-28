package at.fhjoanneum.ima.project.getfit;

import java.util.Collections;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class GraphRenderer {
	// XYSERIESRENDERER settings of graph line
	private XYSeriesRenderer renderer = new XYSeriesRenderer();
	// XYMultipleSeriesRenderer settings of complete graph
	private XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
	private GraphicalView graphicalView;
	/**
	 * The Constructor is used to built the a Graph with the given Data
	 * 
	 *  @param xData	data of x axis
	 *  @param yData 	data of y axis
	 *  @param xTimestamp 	data of x Labels
	 *  @param res 	resources of application
	 *  @param fragmentActivity to built graph
	 */

	public GraphRenderer(List<Double> xData, List<Double> yData,List<String> xTimestamp, Resources res,
			FragmentActivity fragmentActivity) {
		DisplayMetrics metrics = res.getDisplayMetrics();
		float textSizeSP = TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP, 10, metrics);

		XYSeries xYSeries = new XYSeries("XY Series");
		for (int i = 0; i < xData.size(); i++) {
			xYSeries.add(xData.get(i), Math.rint(yData.get(i)*100)/100);
		}

		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(xYSeries);

		renderer.setChartValuesSpacing(20);
		renderer.setChartValuesTextAlign(Paint.Align.CENTER);
		renderer.setColor(Color.parseColor("#00bcd0"));
		renderer.setPointStyle(PointStyle.DIAMOND);
		renderer.setDisplayChartValues(true);
		renderer.setChartValuesTextSize(textSizeSP);
		renderer.setLineWidth(2);
		renderer.setFillPoints(true);

		multiRenderer.setXLabelsColor(Color.parseColor("#00bcd0"));
		multiRenderer.setXLabels(0);
		multiRenderer.setXLabelsAlign(Paint.Align.CENTER);
		multiRenderer.setYLabels(12);
		multiRenderer.setYLabelsAlign(Paint.Align.RIGHT);
		multiRenderer.setYLabelsPadding(6);
		multiRenderer.setYLabelsColor(0, Color.parseColor("#00bcd0"));
		multiRenderer.setXAxisMax(Collections.max(xData) + Collections.min(xData)*-1/25);
		multiRenderer.setXAxisMin(Collections.min(xData) - Collections.min(xData)*-1/25);
		multiRenderer.setYAxisMax(Collections.max(yData) + Collections.min(yData)/25);
		multiRenderer.setYAxisMin(Collections.min(yData) - Collections.min(yData)/25);
		multiRenderer.setLabelsTextSize(textSizeSP);
		multiRenderer.setZoomButtonsVisible(false);
		multiRenderer.setZoomEnabled(true, true);
		multiRenderer.setShowLegend(false);
		multiRenderer.setShowGrid(true);
		multiRenderer.setAxesColor(Color.parseColor("#00bcd0"));
		multiRenderer.setGridColor(Color.parseColor("#00bcd0"));
		multiRenderer.setMarginsColor(Color.WHITE);
		multiRenderer.setMargins(new int[] { 0, 60, 0, 10 });

		int j = 0;
		for (int i = Collections.min(xData).intValue(); i <= Collections.max(xData).intValue() + 1; i++) {
			if (i == xData.get(j)) {
				if(i==Collections.min(xData).intValue())multiRenderer.addXTextLabel(i, xTimestamp.get(j));
				if(i==Collections.max(xData).intValue())multiRenderer.addXTextLabel(i, xTimestamp.get(j));
				if(j==(xData.size()/2))multiRenderer.addXTextLabel(i, xTimestamp.get(j));
				if(i==xData.get(xData.size()-1)) break;
				j++;
				
				}
		}
		multiRenderer.addSeriesRenderer(renderer);
		graphicalView = ChartFactory.getLineChartView(fragmentActivity,
				dataset, multiRenderer);
	}
	/**get access of the graphical view
	 * 
	 * @return graphicalView 	returns a GraphicalView
	 */

	public GraphicalView getGraphicalView() {
		return graphicalView;
	}
}
