package at.fhjoanneum.ima.project.getfit;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.LinearLayout;
import at.fhjoanneum.ima.project.database.tables.StatsBodyMeasurementTable;

public class StatsTabBodyGraph extends FragmentActivity {

	private String dataSelect;
	private StatsBodyMeasurementTable measurements;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_tabbody_graph);

        dataSelect = getIntent().getStringExtra(StatsTabExercises.DATASELECT);
        setTitle("My Stats "+dataSelect);
		this.measurements = new StatsBodyMeasurementTable(this, dataSelect);
		if (dataSelect.contains("bmi")) openChartBmi();
		else openChartBody();
    }

	private void openChartBody() {
		List<Double> xData = measurements.getDateDayDiff();
		List<Double> yData = measurements.getBodyDataList();
		List<String> xTimestamp = measurements.getDateAsString();

		GraphRenderer graphRenderer = new GraphRenderer(xData, yData,xTimestamp, this.getResources(), this);
		LinearLayout layout = (LinearLayout) findViewById(R.id.chartbody);
		layout.addView(graphRenderer.getGraphicalView());
	}

	private void openChartBmi() {
		List<Double> xData = measurements.getDateDayDiff();
		List<Double> yData = measurements.getBmiList();
		List<String> xTimestamp = measurements.getDateAsString();

		GraphRenderer graphRenderer = new GraphRenderer(xData, yData,xTimestamp, this.getResources(), this);
		LinearLayout layout = (LinearLayout) findViewById(R.id.chartbody);
		layout.addView(graphRenderer.getGraphicalView());
	}
}