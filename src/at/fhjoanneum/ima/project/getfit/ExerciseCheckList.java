package at.fhjoanneum.ima.project.getfit;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.ListFragment;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import at.fhjoanneum.ima.project.database.MyDataBaseHelper;
import at.fhjoanneum.ima.project.database.tables.ExerciseTable;

public class ExerciseCheckList extends ListFragment {
	public ListView listView;
	private ArrayAdapter<String> adapter;
	private ArrayList<Integer> checkedPosition = new ArrayList<Integer>();
	private HashMap<Integer,Integer> exercisesIDs = new HashMap<Integer,Integer>();
	private MyDataBaseHelper MyHelper;
	private void openDB() {
		MyHelper = new MyDataBaseHelper(getActivity());
		try {
			MyHelper.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}

		try {
			MyHelper.openDataBase(false);
		} catch (SQLException sqle) {
			throw new Error("sqlite");
		}
	}

	@Override
	public void onActivityCreated(Bundle saveInstanceState) {

		super.onActivityCreated(saveInstanceState);

		openDB();
		ExerciseTable allExercieses = new ExerciseTable(MyHelper.getData("select * from t_exercises ORDER BY NAME COLLATE NOCASE"));
		ArrayList<String> name = new ArrayList<String>();
		for (int i = 0; i < allExercieses.getValues().size(); i++) {
			name.add(allExercieses.getValues().get(i).getName()); // Names for ListAdapter
			exercisesIDs.put(i,allExercieses.getValues().get(i).getId()); // Hashmap key = position value = ExerciseID
		}
		
		adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_multiple_choice,android.R.id.text1, name);
		setListAdapter(adapter);

		listView = getListView();
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos,
					long id) {
				
				if (checkedPosition.contains(Integer.valueOf(pos)))
					checkedPosition.remove(Integer.valueOf(pos));
				else
					checkedPosition.add(Integer.valueOf(pos));
				
				/// KEVIIIIIINNN WAS IS DASSSSSSS
				SparseBooleanArray checked = listView.getCheckedItemPositions();
				ArrayList<String> selectedItems = new ArrayList<String>();

				for (int i = 0; i < checked.size(); i++) { // Item
					int position = checked.keyAt(i); // Add sport if it
					if (checked.valueAt(i))
						selectedItems.add(adapter.getItem(position));
				}

				String[] outputStrArr = new String[selectedItems.size()];

				for (int i = 0; i < selectedItems.size(); i++) {
					outputStrArr[i] = selectedItems.get(i);
				}
			}
		});

	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		MyHelper.close();
	}


	public ArrayList<Integer> getExePerDay() {
		ArrayList<Integer> t = new ArrayList<Integer>();
		//NEW TODO
		for (Integer pos : checkedPosition) {
			t.add(exercisesIDs.get(pos));
		}
		//t.addAll(exercises);
		checkedPosition.clear();
		return t;
	}


	public void setExePerDay(ArrayList<Integer> exPerDay) {
		checkedPosition.clear();
		ArrayList<Integer> t = new ArrayList<Integer>(exercisesIDs.values());
		for (Integer id : exPerDay) {
			for (Integer value : t) {
				if(id == value){
					listView.setItemChecked(t.indexOf(value), true);
					checkedPosition.add(t.indexOf(value));
				}
			}
			
		}
		
		//OLD TODO
		/*exercises = oneDay;
		for (Integer i : exercises) {
			listView.setItemChecked(i, true);
		}*/
	}

}
