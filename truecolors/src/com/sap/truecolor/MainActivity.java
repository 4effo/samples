package com.sap.truecolor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

public class MainActivity extends Activity implements OnClickListener {

	private static final String TAG = "TrueColors";

	public static final String RESULT = "com.sap.truecolor.SELECTION";

	public final int COUNTROWS = 5;
	public final int COUNTCOLS = 4;
	public final int COUNTAttributesPerBulk = 6;

	int ScoreTable[][] = new int[COUNTROWS][COUNTCOLS];
	double rankTable[][] = new double[COUNTROWS][COUNTCOLS];

	public final double totalSumResult = 50;
	public final double minScore = 5;
	public final double maxScore = 20;

	private double orangeScore = 0;
	private double greenScore = 0;
	private double blueScore = 0;
	private double goldScore = 0;

	Boolean[] attributeCheckedList;

	ListView listView;

	private void initializeDataStructure() {
		attributeCheckedList = new Boolean[attributeList.length];
		for (int i = 0; i < attributeCheckedList.length; i++) {
			attributeCheckedList[i] = false;
		}
	}

	private void clearScoreTable() {
		for (int row = 0; row < COUNTROWS; row++) {
			for (int col = 0; col < COUNTCOLS; col++) {
				ScoreTable[row][col] = 0;
			}
		}
	}

	private void printOutScoreTable() {
		for (int row = 0; row < COUNTROWS; row++) {
			for (int col = 0; col < COUNTCOLS; col++) {
				Log.v(TAG, "Score Table " + "r:" + row + "c:" + col + "v:"
						+ ScoreTable[row][col]);
			}
		}
	}

	private void printOutDataStructure() {
		for (int i = 0; i < attributeCheckedList.length; i++) {
			Log.v(TAG, "  " + i + ": " + attributeList[i] + " "
					+ attributeCheckedList[i]);
		}
	}

	private void calculateScoreTable() {
		for (int i = 0; i < attributeCheckedList.length; i++) {
			if (attributeCheckedList[i]) {
				int row = i / (COUNTAttributesPerBulk * COUNTCOLS);
				int col = (i / COUNTAttributesPerBulk) % COUNTCOLS;

				ScoreTable[row][col]++;
			}
		}
	}

	private double calculateRank(int v, int i1, int i2, int i3, int i4) {
		double rank = 4.5;

		if (v < i1)
			rank--;
		if (v < i2)
			rank--;
		if (v < i3)
			rank--;
		if (v < i4)
			rank--;

		if (v == i1)
			rank = rank - 0.5;
		if (v == i2)
			rank = rank - 0.5;
		if (v == i3)
			rank = rank - 0.5;
		if (v == i4)
			rank = rank - 0.5;

		return rank;
	}

	private void calculateRankTable() {
		double totalSum = 0;
		for (int row = 0; row < COUNTROWS; row++) {
			for (int col = 0; col < COUNTCOLS; col++) {
				rankTable[row][col] = calculateRank(ScoreTable[row][col],
						ScoreTable[row][0], ScoreTable[row][1],
						ScoreTable[row][2], ScoreTable[row][3]);
				totalSum = totalSum + rankTable[row][col];
			}
		}

		if (totalSum != totalSumResult)
			Log.v(TAG, "Inconsistency: totalSumResult <> " + totalSumResult);
	}

	private void printOutRankTable() {
		for (int row = 0; row < COUNTROWS; row++) {
			for (int col = 0; col < COUNTCOLS; col++) {
				Log.v(TAG, "Rank Table " + "r:" + row + "c:" + col + "v:"
						+ rankTable[row][col]);
			}
		}
	}

	private void calculateColorScores() {
		orangeScore = rankTable[0][0] + rankTable[1][3] + rankTable[2][2]
				+ rankTable[3][1] + rankTable[4][2];
		greenScore = rankTable[0][3] + rankTable[1][0] + rankTable[2][3]
				+ rankTable[3][3] + rankTable[4][0];
		blueScore = rankTable[0][2] + rankTable[1][1] + rankTable[2][1]
				+ rankTable[3][2] + rankTable[4][1];
		goldScore = rankTable[0][1] + rankTable[1][2] + rankTable[2][0]
				+ rankTable[3][0] + rankTable[4][3];

		if (orangeScore < minScore)
			Log.v(TAG, "Inconsistency: orangeScore < minScore");
		if (greenScore < minScore)
			Log.v(TAG, "Inconsistency: greenScore < minScore");
		if (blueScore < minScore)
			Log.v(TAG, "Inconsistency: blueScore < minScore");
		if (goldScore < minScore)
			Log.v(TAG, "Inconsistency: goldScore < minScore");

		if (orangeScore > maxScore)
			Log.v(TAG, "Inconsistency: orangeScore > maxScore");
		if (greenScore > maxScore)
			Log.v(TAG, "Inconsistency: greenScore > maxScore");
		if (blueScore > maxScore)
			Log.v(TAG, "Inconsistency: blueScore > maxScore");
		if (goldScore > maxScore)
			Log.v(TAG, "Inconsistency: goldScore > maxScore");

		Log.v(TAG, "orangeScore " + orangeScore);
		Log.v(TAG, "greenScore " + greenScore);
		Log.v(TAG, "blueScore " + blueScore);
		Log.v(TAG, "goldScore " + goldScore);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initializeDataStructure();
		setContentView(R.layout.activity_main);

		listView = (ListView) findViewById(R.id.listView);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice,
				attributeList);

		listView.setAdapter(adapter);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Log.v(TAG, "setOnItemClickListener" + position);
				attributeCheckedList[position] = !attributeCheckedList[position];
			}
		});

		clearScoreTable();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {

		Log.v(TAG, "onClick");

		for (int i = 0; i < listView.getChildCount(); i++) {
			CheckedTextView c = (CheckedTextView) listView.getChildAt(i);
			if (c.isChecked()) {
				String checkedAttributeName = c.getText().toString();
				Log.v(TAG, "result: " + checkedAttributeName);
			}
		}

		printOutDataStructure();
		calculateScoreTable();
		printOutScoreTable();
		calculateRankTable();
		printOutRankTable();
		calculateColorScores();

		Intent intent = new Intent(this, ResultActivity.class);
		int[] array = new int[] { (int) orangeScore, (int) greenScore,
				(int) blueScore, (int) goldScore };
		intent.putExtra( RESULT, array);

		startActivity(intent);

	}

	private String[] attributeList = new String[] { "Active", "Variety",
			"Sports", "Opportunities", "Spontaneous", "Flexible", "Organized",
			"Planned", "Neat", "Parental", "Traditional", "Responsible",
			"Warm", "Helpful", "Friends", "Authentic", "Harmonious",
			"Compassionate", "Learning", "Science", "Quiet", "Versatile",
			"Inventive", "Competent", "Curious", "Ideas", "Questions",
			"Conceptual", "Knowledge", "Problem Solver", "Caring",
			"People Oriented", "Feelings", "Unique", "Empathetic",
			"Communicative", "Orderly", "On-Time", "Honest", "Stable",
			"Sensible", "Dependable", "Action", "Challenges", "Competetive",
			"Impetuous", "Impactful", "Doing", "Helpful", "Trustworthy",
			"Dependable", "Loyal", "Conservative", "Organized", "Kind",
			"Understanding", "Giving", "Devoted", "Warm", "Poetic", "Playful",
			"Quick", "Adventurous", "Confrontive", "Open Minded",
			"Independent", "Exploring", "Competent", "Theoretical",
			"Why Questions", "Ingenious", "Independent", "Follow Rules",
			"Useful", "Save Money", "Concerned", "Procedural", "Cooperative",
			"Active", "Free", "Winning", "Daring", "Impulsive", "Risk Taker",
			"Sharing", "Getting Along", "Feelings", "Tender", "Inspirational",
			"Dramatic", "Thinking", "Solving Problems", "Perfectionistic",
			"Determinded", "Complex", "Composed", "Puzzles", "Seeking Info",
			"Making Sense", "Philosophical", "Principled", "Rational",
			"Social Causes", "Easy Going", "Happy Endings", "Approachable",
			"Affectionate", "Sympathetic", "Exciting", "Lively", "Hands on",
			"Courageous", "Skillful", "On Stage", "Pride", "Tradition",
			"Do Things Right", "Orderly", "Convetional", "Careful" };

}
