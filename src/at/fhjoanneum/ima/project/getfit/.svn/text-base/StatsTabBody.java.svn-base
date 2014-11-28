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
import at.fhjoanneum.ima.project.database.tables.StatsBodyTable;

public class StatsTabBody extends ListFragment {
	private List<Map<String, ?>> data;
	static String DATASELECT = "DATASELECT";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_stats_tabbody,container, false);
		StatsBodyTable statsBodyTable = new StatsBodyTable(view.getContext());
		this.data = statsBodyTable.getDataBody();
		SimpleAdapter simpleAdapter = new SimpleAdapter(
				getActivity().getBaseContext(),
				data,
				R.layout.activity_stats_tabbody_listadapter,
				new String[] {"name","images"},
				new int[] { R.id.statsTabBodyName,R.id.statsTabBodyPic});
		setListAdapter(simpleAdapter);
		if(data.isEmpty()){
			view = inflater.inflate(R.layout.activity_stats_tabempty,container, false);
			TextView textView = (TextView) view.findViewById(R.id.statsTabEmpty);	
			textView.setText("NO DATA");
			return view;
		}
		return view;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		String dataSelect=data.get(position).get("name").toString();
		
		Intent intent = new Intent(getActivity(), StatsTabBodyGraph.class);
		intent.putExtra(DATASELECT, dataSelect);
		startActivity(intent);
		}

	

}
