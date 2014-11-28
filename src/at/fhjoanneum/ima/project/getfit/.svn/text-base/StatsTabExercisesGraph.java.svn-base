package at.fhjoanneum.ima.project.getfit;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.LinearLayout;
import at.fhjoanneum.ima.project.database.tables.StatsExerciseMeasurementTable;

public class StatsTabExercisesGraph extends FragmentActivity {
	private String dataSelect;
	private StatsExerciseMeasurementTable measurements;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_tabexercises_graph);

        dataSelect = getIntent().getStringExtra(StatsTabExercises.DATASELECT);
        setTitle("My Stats "+dataSelect);
		this.measurements = new StatsExerciseMeasurementTable(this, dataSelect);
		openChartWeight();
		openChartRepositions();
		openChartSets();
    }

	private void openChartWeight() {
		List<Double> xData = measurements.getDateDayDiff();
		List<Double> yData = measurements.getExerciseWeightList();
		List<String> xTimestamp = measurements.getDateAsString();

		GraphRenderer graphRenderer = new GraphRenderer(xData, yData,xTimestamp, this.getResources(), this);
		LinearLayout layout = (LinearLayout) findViewById(R.id.chartweight);
		layout.addView(graphRenderer.getGraphicalView());

	}

	private void openChartRepositions() {
		List<Double> xData = measurements.getDateDayDiff();
		List<Double> yData = measurements.getExerciseRepetitionesList();
		List<String> xTimestamp = measurements.getDateAsString();

		GraphRenderer graphRenderer = new GraphRenderer(xData, yData,xTimestamp, this.getResources(), this);

		LinearLayout layout = (LinearLayout) findViewById(R.id.chartrepositions);

		layout.addView(graphRenderer.getGraphicalView());

	}
	private void openChartSets() {
		List<Double> xData = measurements.getDateDayDiff();
		List<Double> yData = measurements.getExerciseSets();
		List<String> xTimestamp = measurements.getDateAsString();

		GraphRenderer graphRenderer = new GraphRenderer(xData, yData,xTimestamp, this.getResources(), this);
		LinearLayout layout = (LinearLayout) findViewById(R.id.chartstets);
		layout.addView(graphRenderer.getGraphicalView());

	}
}
