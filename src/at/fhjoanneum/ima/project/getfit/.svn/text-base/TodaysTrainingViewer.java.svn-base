package at.fhjoanneum.ima.project.getfit;


import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import at.fhjoanneum.ima.project.database.tables.ExerciseTable;
import at.fhjoanneum.ima.project.database.tables.HistoryTable;
import at.fhjoanneum.ima.project.userClasses.Exercise;

public class TodaysTrainingViewer extends Activity {
	private int position = 0;
	private int totalLength;
	private HistoryTable todaysTraining;
	private AnimationDrawable frameAnimation;
	
	private static class ViewHolder{
		public TextView Title;
		public ImageView animationView;
		public EditText weight;
		public EditText iteration;
		public EditText sets;
		public ImageButton prev;
		public ImageButton next;
	}
	private ViewHolder views = new ViewHolder();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todays_training_viewer);
		setupActionBar();
		views.Title = (TextView) findViewById(R.id.training_exec_title);
		views.weight = (EditText) findViewById(R.id.input_exec_weight);
		views.iteration = (EditText) findViewById(R.id.input_exec_repetitions);
		views.sets = (EditText) findViewById(R.id.input_exec_sets);
		views.animationView = (ImageView) findViewById(R.id.training_animated_image);
		views.prev = (ImageButton) findViewById(R.id.prev_exec);
		views.prev.setClickable(false);
		views.next = (ImageButton) findViewById(R.id.next_exec);
		todaysTraining = new HistoryTable((ExerciseTable) getIntent().getSerializableExtra("ExerciseTable"),this);
		totalLength = todaysTraining.getHistory().size()-1;
		if(totalLength == 0)views.next.setClickable(false);
		updateScreen();
	}

	private void updateScreen() {
		frameAnimation = new AnimationDrawable();
		Exercise exercise = todaysTraining.getHistory().get(position).getExercise();
		views.Title.setText(exercise.getName());
		views.iteration.setText(String.valueOf(todaysTraining.getHistory().get(position).getIterations()));
		views.sets.setText(String.valueOf(todaysTraining.getHistory().get(position).getSets()));
		views.weight.setText(String.valueOf(todaysTraining.getHistory().get(position).getValue()));
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
		}else {
			views.animationView.setImageDrawable(getResources().getDrawable(R.drawable.user_icon_blue));
		}
	
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
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todays_training_viewer, menu);
		return true;
	}
	public void moveTo(View view){
		if(view.getTag().equals("prev")){
			stopAnimation();
			saveCurrentValues();
			position--;
			updateScreen();
			if (position==0)views.prev.setClickable(false);
			else if(!views.prev.isClickable())views.prev.setClickable(true);
			if(!views.next.isClickable())views.next.setClickable(true);
		}
		else if(view.getTag().equals("next")){
			stopAnimation();
			saveCurrentValues();
			position++;
			updateScreen();
			if (position==totalLength)views.next.setClickable(false);
			else if(!views.next.isClickable())views.next.setClickable(true);
			if(!views.prev.isClickable())views.prev.setClickable(true);
		}
	}

	private void stopAnimation() {
		frameAnimation.stop();
		for(int i = 0; i < frameAnimation.getNumberOfFrames(); i++){
			Drawable frame = frameAnimation.getFrame(i);
			if(frame instanceof BitmapDrawable){
				((BitmapDrawable) frame).getBitmap().recycle();
			}
			frame.setCallback(null);
		}
		frameAnimation.setCallback(null);
	}

	private void saveCurrentValues() {
		todaysTraining.getHistory().get(position).setValue(Float.valueOf(views.weight.getText().toString()));
		todaysTraining.getHistory().get(position).setSets(Integer.valueOf(views.sets.getText().toString()));
		todaysTraining.getHistory().get(position).setIterations(Integer.valueOf(views.iteration.getText().toString()));
	}
	private void saveAllValues() {
		todaysTraining.addToDatabase(this);
	}

	public void alertUser(View view) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				TodaysTrainingViewer.this);
		alertDialog.setTitle("Finish Workout");
		alertDialog
				.setMessage("All changes will be saved and today\'s workout schedule will be closed! Are you sure you want to continue?");
		alertDialog.setNegativeButton("Continue",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						saveCurrentValues();
						saveAllValues();
						Intent intent2 = new Intent(TodaysTrainingViewer.this, MainActivity.class);
						startActivity(intent2);

					}

					
				});
		alertDialog.setPositiveButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});

		alertDialog.show();
	}
	
	@Override
	public void onBackPressed() {
		Intent i_back_to_todays = new Intent(this, TodaysTraining.class);
		startActivity(i_back_to_todays);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent i_back_to_todays = new Intent(this, TodaysTraining.class);
			startActivity(i_back_to_todays);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
