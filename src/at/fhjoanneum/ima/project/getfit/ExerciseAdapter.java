package at.fhjoanneum.ima.project.getfit;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ExerciseAdapter extends SimpleAdapter {
	private final List<Map<String, String>> data;
	private final Activity context;
	public int _id;
	private int listEntry;

	static class ViewHolder {
		public TextView _id;
		public TextView name;
		public TextView muscles;
		public ImageView image;
	}

	public ExerciseAdapter(Activity context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);

		this.data = (List<Map<String, String>>) data;
		this.context = context;
//		this.layoutID = resource;
		this.listEntry = from.length;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.activity_listadapter_exercises,
					null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.name = (TextView) rowView
					.findViewById(R.id.exerciseName);
			viewHolder.muscles = (TextView) rowView
					.findViewById(R.id.exerciseMuscles);
			viewHolder.image = (ImageView) rowView
					.findViewById(R.id.exercisePic);
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
		if (this.listEntry == 4) {
			_id = Integer.parseInt(data.get(position).get("id"));
			rowView.setId(_id);
		}

		holder.name.setText(data.get(position).get("name"));
		holder.muscles.setText(data.get(position).get("muscles"));

		String imageName = data.get(position).get("path").split(" ")[0];
		if (!imageName.isEmpty()) {
			imageName += "_icon";
//			Context context = this.context;
			int imageId = this.context.getResources().getIdentifier(imageName, "drawable",
					context.getPackageName());
			holder.image.setImageResource(imageId);
		} else 
			holder.image.setImageResource(R.drawable.user_icon_blue);
		return rowView;
	}

}
