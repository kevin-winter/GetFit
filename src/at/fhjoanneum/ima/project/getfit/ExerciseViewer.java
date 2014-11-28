package at.fhjoanneum.ima.project.getfit;

import android.os.Bundle;
import android.app.Activity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.annotation.TargetApi;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import at.fhjoanneum.ima.project.userClasses.Exercise;

public class ExerciseViewer extends Activity {
	
	private static class ViewHolder{
		public TextView name;
		public TextView equipment;
		public TextView steps;
		public TextView tips;
		public TextView muscles;
		public TextView tipsTitle;
		public TextView equipmentTitle;
		public ImageView animationView;
	}
	private ViewHolder views = new ViewHolder();
	private Exercise exercise;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise_viewer);
		views.name = (TextView) findViewById(R.id.exersice_name);
		views.equipment = (TextView) findViewById(R.id.exersice_equipment);
		views.steps = (TextView) findViewById(R.id.exersice_steps);
		views.tips = (TextView) findViewById(R.id.exersice_tips);
		views.muscles = (TextView) findViewById(R.id.exercise_muscles);
		views.tipsTitle = (TextView) findViewById(R.id.title_tips);
		views.equipmentTitle = (TextView) findViewById(R.id.title_equipment);
		views.animationView = (ImageView) findViewById(R.id.animationView);
		
		this.exercise = (Exercise)getIntent().getSerializableExtra(Exercises.EXERCISE);
		
		String orderedSteps = "";	
		Integer n = 1;
		for (String step : exercise.getSteps().split("\\.")){
			orderedSteps+=n++ + ".) " + step + ".\n";
		}
		views.name.setText(exercise.getName());
		
		if (exercise.getTips().isEmpty() || exercise.getTips()==null){
			views.tipsTitle.setVisibility(View.INVISIBLE);
		} else {
			views.tips.setText(exercise.getTips());
		}
		
		if (exercise.getEquipment().isEmpty() || exercise.getEquipment()==null){
			views.equipmentTitle.setVisibility(View.INVISIBLE);	
		} else {
			views.equipment.setText(exercise.getEquipment());
		}
		if (!(exercise.getSteps().isEmpty() || exercise.getSteps()==null)){
			views.steps.setText(orderedSteps);
		}
		if (exercise.getPrimMuscleGroups() !=null) {
		views.muscles.setText(exercise.getPrimMuscleGroups()+" "+exercise.getSecMuscleGroups());
		}
		if (exercise.getImages().isEmpty()) views.animationView.setImageDrawable(getResources().getDrawable(R.drawable.user_icon_blue));
		AnimationDrawable frameAnimation = new AnimationDrawable();
		
		if (exercise.getImages().length()>1){
		String image1Path = exercise.getImages().split(" ")[0];
		String image2Path = exercise.getImages().split(" ")[exercise.getImages().split(" ").length-1];
		
		int image1Id = getResources().getIdentifier(image1Path, "drawable", getPackageName());
		int image2Id = getResources().getIdentifier(image2Path, "drawable", getPackageName());
		frameAnimation.addFrame(getResources().getDrawable(image1Id), 500);
		frameAnimation.addFrame(getResources().getDrawable(image2Id), 500);
		frameAnimation.setOneShot(false);
		views.animationView.setImageDrawable(frameAnimation);
		frameAnimation.start();
		}
		
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			super.onBackPressed();
            return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

}
