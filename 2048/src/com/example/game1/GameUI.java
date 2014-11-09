package com.example.game1;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

public class GameUI extends GridLayout {

	private Card[][] cardView = new Card[5][5];
	private List<Point> emptyPoint = new ArrayList<Point>();

	public GameUI(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initGameUI();
	}

	public GameUI(Context context, AttributeSet attrs) {
		super(context, attrs);
		initGameUI();
	}

	public GameUI(Context context) {
		super(context);
		initGameUI();
	}

	private void initGameUI() {
		setColumnCount(4);
		setBackgroundColor(0xffbbada0);
		setOnTouchListener(new View.OnTouchListener() {
			private float startX, startY, endX, endY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = event.getX();
					startY = event.getY();
					break;
				case MotionEvent.ACTION_UP:
					endX = event.getX() - startX;
					endY = event.getY() - startY;
					if (Math.abs(endX) > Math.abs(endY)) {
						if (endX < -5) {
							goLeft();
						} else if (endX > 5) {
							goRight();
						}
					} else {
						if (endY < -5) {
							goUp();
						} else if (endY > 5) {
							goDown();
						}
					}
					break;
				}
				return true;
			}
		});
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		int cardW = (Math.min(w, h) - 10) / 4;
		addCard(cardW);
		runGame();
	}

	private void addCard(int cardW) {
		Card card;
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++) {
				card = new Card(getContext());
				card.setNum(0);
				addView(card, cardW, cardW);
				cardView[i][j] = card;
			}
	}

	private void runGame() {
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				cardView[i][j].setNum(0);
		addRandomNum();
		addRandomNum();
	}

	public boolean check() {
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++) {
				if (i != 0
						&& cardView[i][j].getNum() == cardView[i - 1][j]
								.getNum())
					return false;
				if (j != 0
						&& cardView[i][j].getNum() == cardView[i][j - 1]
								.getNum())
					return false;
				if (i != 3
						&& cardView[i][j].getNum() == cardView[i + 1][j]
								.getNum())
					return false;
				if (j != 3
						&& cardView[i][j].getNum() == cardView[i][j + 1]
								.getNum())
					return false;
			}
		return true;
	}

	private void addRandomNum() {
		emptyPoint.clear();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (cardView[i][j].getNum() <= 0)
					emptyPoint.add(new Point(i, j));
			}
		}
		Point tp = emptyPoint.remove((int) (Math.random() * emptyPoint.size()));
		cardView[tp.x][tp.y].setNum(Math.random() > 0.1 ? 2 : 4);
		if (emptyPoint.size() == 0 && check()) {
			new AlertDialog.Builder(getContext())
					.setTitle("你好!")
					.setMessage("游戏结束！")
					.setPositiveButton("再来一次",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									runGame();
								}
							}).show();
		}
	}

	private int num[] = new int[5];
	private int flag = 0;

	private void goLeft() {
		flag = 0;
		for (int i = 0; i < 4; i++) {
			int g = 0;
			for (int j = 3; j >= 0; j--) {
				if (cardView[i][j].getNum() > 0)
					num[g++] = cardView[i][j].getNum();
			}
			for (int j = 0; j < 4; j++) {
				if (g <= 0)
					cardView[i][j].setNum(0);
				else {
					--g;
					if (flag == 0 && cardView[i][j].getNum() != num[g])
						flag = 1;
					cardView[i][j].setNum(num[g]);
				}
				if (j > 0
						&& cardView[i][j].getNum() == cardView[i][j - 1]
								.getNum()) {
					cardView[i][j - 1].setNum(cardView[i][j].getNum() * 2);
					MainActivity.getMainActivity().addScore(
							cardView[i][j - 1].getNum());
					flag = 1;
					if (g <= 0)
						cardView[i][j].setNum(0);
					else {
						--g;
						if (flag == 0 && cardView[i][j].getNum() != num[g])
							flag = 1;
						cardView[i][j].setNum(num[g]);
					}
				}
			}
		}
		if (flag != 0) {
			addRandomNum();
			System.out.println("lalalla");
		}

	}

	private void goRight() {
		flag = 0;
		for (int i = 0; i < 4; i++) {
			int g = 0;
			for (int j = 0; j < 4; j++) {
				if (cardView[i][j].getNum() > 0)
					num[g++] = cardView[i][j].getNum();
			}
			for (int j = 3; j >= 0; j--) {
				if (g <= 0)
					cardView[i][j].setNum(0);
				else {
					--g;
					if (flag == 0 && cardView[i][j].getNum() != num[g]) {
						flag = 1;
					}
					cardView[i][j].setNum(num[g]);
				}
				if (j < 3
						&& cardView[i][j].getNum() == cardView[i][j + 1]
								.getNum()) {
					cardView[i][j + 1].setNum(cardView[i][j].getNum() * 2);
					MainActivity.getMainActivity().addScore(
							cardView[i][j + 1].getNum());
					flag = 1;
					if (g <= 0) {
						cardView[i][j].setNum(0);
					} else {
						--g;
						if (flag == 0 && cardView[i][j].getNum() != num[g]) {
							flag = 1;
						}
						cardView[i][j].setNum(num[g]);
					}
				}
			}
		}
		if (flag != 0) {
			addRandomNum();
			System.out.println("lalalla");
		}

	}

	private void goUp() {
		flag = 0;
		for (int j = 0; j < 4; j++) {
			int g = 0;
			for (int i = 3; i >= 0; i--) {
				if (cardView[i][j].getNum() > 0) {
					num[g++] = cardView[i][j].getNum();
				}
			}
			for (int i = 0; i < 4; i++) {
				if (g <= 0)
					cardView[i][j].setNum(0);
				else {
					--g;
					if (flag == 0 && cardView[i][j].getNum() != num[g])
						flag = 1;
					cardView[i][j].setNum(num[g]);
				}
				if (i > 0
						&& cardView[i][j].getNum() == cardView[i - 1][j]
								.getNum()) {
					cardView[i - 1][j].setNum(cardView[i][j].getNum() * 2);
					MainActivity.getMainActivity().addScore(
							cardView[i - 1][j].getNum());
					flag = 1;
					if (g <= 0) {
						cardView[i][j].setNum(0);
					} else {
						--g;
						if (flag == 0 && cardView[i][j].getNum() != num[g]) {
							flag = 1;
						}
						cardView[i][j].setNum(num[g]);
					}
				}
			}
		}
		if (flag != 0) {
			addRandomNum();
			System.out.println("lalalla");
		}

	}

	private void goDown() {
		flag = 0;
		for (int j = 0; j < 4; j++) {
			int g = 0;
			for (int i = 0; i < 4; i++) {
				if (cardView[i][j].getNum() > 0) {
					num[g++] = cardView[i][j].getNum();
				}
			}
			for (int i = 3; i >= 0; i--) {
				if (g <= 0) {
					cardView[i][j].setNum(0);
				} else {
					--g;
					if (flag == 0 && cardView[i][j].getNum() != num[g]) {
						flag = 1;
					}
					cardView[i][j].setNum(num[g]);
				}
				if (i < 3
						&& cardView[i][j].getNum() == cardView[i + 1][j]
								.getNum()) {
					cardView[i + 1][j].setNum(cardView[i][j].getNum() * 2);
					MainActivity.getMainActivity().addScore(
							cardView[i + 1][j].getNum());
					flag = 1;
					if (g <= 0) {
						cardView[i][j].setNum(0);
					} else {
						--g;
						if (flag == 0 && cardView[i][j].getNum() != num[g]) {
							flag = 1;
						}
						cardView[i][j].setNum(num[g]);
					}
				}
			}
		}
		if (flag != 0) {
			addRandomNum();
			System.out.println("lalalla");
		}

	}
}
