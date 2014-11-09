package com.example.game1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter.LengthFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private TextView gameScore;
	private int score = 0;
	private static MainActivity mainActivity = null;
	public MainActivity() {
		mainActivity = this;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gameScore = (TextView) findViewById(R.id.gameScore);
		showScore();
	}
	public void clearScore()
	{
		score = 0;
		showScore();
	}
	public void showScore()
	{
		gameScore.setText(": "+score+"");
	}
	public void addScore(int num)
	{
		score+=num;
		showScore();
	}
	public static MainActivity getMainActivity() {
		return mainActivity;
	}
}
