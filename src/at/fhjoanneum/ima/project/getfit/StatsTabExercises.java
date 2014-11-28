package at.fhjoanneum.ima.project.getfit;

import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import at.fhjoanneum.ima.project.database.tables.StatsExercisesTable;

public class StatsTabExercises extends ListFragment {
	private List<Map<String, ?>> data;
	public static int statsTabExercisePosition;
	static String DATASELECT = "DATASELECT";
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.activity_stats_tabexercises,container, false);
		StatsExercisesTable statsExercisesTable = new StatsExercisesTable(view.getContext());
		this.data = statsExercisesTable.getDataExercise();
		SimpleAdapter simpleAdapter = new SimpleAdapter(
				getActivity().getBaseContext(),
				data,
				R.layout.activity_stats_tabexercises_listadapter,
				new String[] { "name", "muscle group", "picture" },
				new int[] { R.id.statsTabExerciseName,R.id.statsTabExerciseMuscles, R.id.statsTabexercisePic });
		if(data.isEmpty()){
			view = inflater.inflate(R.layout.activity_stats_tabempty,container, false);
			TextView textView = (TextView) view.findViewById(R.id.statsTabEmpty);	
			textView.setText("NO DATA");
			return view;
		}
		setListAdapter(simpleAdapter);
		return view;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		String dataSelect=data.get(position).get("name").toString();
				Intent intent = new Intent(getActivity(), StatsTabExercisesGraph.class);
		intent.putExtra(DATASELECT, dataSelect);
		startActivity(intent);
		}

}
