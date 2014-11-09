package com.example.game1;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Card extends FrameLayout {

	private TextView textView;
	public Card(Context context) {
		super(context);
		
		textView = new TextView(getContext());
		textView.setTextSize(32);
		textView.setGravity(Gravity.CENTER);
		textView.setBackgroundColor(0x33ffffff);
		LayoutParams lp = new LayoutParams(-1, -1);
		lp.setMargins(10, 10, 0, 0);
		addView(textView, lp);
		setNum(0);
	}
	private int num = 0;
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
		if(num<=0)
			textView.setText("");
		else
			textView.setText(num+"");
	}
	
	public boolean equals(Card o) {
		// TODO Auto-generated method stub
		return getNum()==o.getNum();
	}
	
	
} 
