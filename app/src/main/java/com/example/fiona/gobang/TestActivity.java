package com.example.fiona.gobang;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class TestActivity extends AppCompatActivity {
    Chessboard chess;
    GestureDetector gesture;

    double nLenStart = 0;

    float x = 0;        //Chessboard视图的起点
    float y = 0;

    boolean isBlack = true;   //是否是黑子

    boolean isWin = false;      //是否已经赢了

    DisplayMetrics dm;

    RelativeLayout relativeLayout;      //选项按钮

    float sursorX;
    float surosrY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        relativeLayout = (RelativeLayout) findViewById(R.id.option);
        chess = (Chessboard) findViewById(R.id.chessboard);
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        x = dm.widthPixels / 2 - chess.len * chess.count * 5/2;
        y = dm.heightPixels / 2 - chess.len * chess.count * 5/2;
        sursorX=x;
        surosrY=y;
        chess.setX(x);
        chess.setY(y);
        gesture = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener(
        ) {
            boolean isremove = false;

            /**
             * 移动棋盘
             * @param e1
             * @param e2
             * @param distanceX
             * @param distanceY
             * @return
             */
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

                isWin = false;
                /**
                 * 如果是滑动则撤销棋子
                 */
                if (isremove&&chess.chesses.size()>0) {
                    chess.chesses.remove(chess.chesses.size() - 1);
                    isremove = false;
                    isBlack = !isBlack;
                    chess.postInvalidate();
                }
                if (Math.abs(distanceX) >= 5 || Math.abs(distanceY) >= 5 && Math.abs(distanceX) >= 20 && Math.abs(distanceY) >= 20) {
                    if (distanceX < 0) {
                        //向右
                        if (chess.x + x <= chess.len) {
                            x -= distanceX;
                        }
                    }
                    if (distanceX > 0) {
                        //向左
                        if (chess.x + chess.count * chess.len + x >= dm.widthPixels - chess.len) {
                            x -= distanceX;
                        }
                    }
                    if (distanceY < 0) {
                        //向下
                        if (chess.y + y <= chess.len) {
                            y -= distanceY;
                        }
                    }
                    if (distanceY > 0) {
                        //向上
                        if (chess.y + chess.count * chess.len + y >= dm.heightPixels - chess.len) {
                            y -= distanceY;
                        }
                    }
                    chess.setX(x);
                    chess.setY(y);
                }
                return true;
            }

            /**
             * 点击下棋
             * @param e
             * @return
             */
            @Override
            public boolean onDown(MotionEvent e) {

                boolean unExist = true;     //判断该位置是否已有棋子
                /**
                 * 点击在棋盘内
                 */
                if (e.getX() >= chess.x - Math.abs(x) &&
                        e.getX() <= chess.x + chess.len * chess.count - Math.abs(x) &&
                        e.getY() >= chess.y - Math.abs(y) &&
                        e.getY() <= chess.y + chess.count * chess.len - Math.abs(y)) {

                    float disX = e.getX() - (chess.x - Math.abs(x));
                    float disY = e.getY() - 60 - (chess.y - Math.abs(y));     //状态栏的长度要计算
                    int column = Math.round(disX / chess.len);
                    int row = Math.round(disY / chess.len);
                    for (Chess ch : chess.chesses) {
                        if (column == ch.column && row == ch.row) {
                            unExist = false;
                        }
                    }
                    if (unExist && isWin == false) {
                        isremove = true;
                        Chess c = new Chess(isBlack ? Color.BLACK : Color.WHITE, row, column);
                        isBlack = !isBlack;
                        chess.chesses.add(c);
                        if (Win.isWin(c, chess.chesses)) {
                            isWin = true;
                            new ToastWin().execute(c.color);
                        }
                    } else {
                        isremove = false;         //  如果棋子已存在，则提醒滑动不销棋子
                    }

                    /**
                     * 定位
                     */
                    chess.point.x=row;
                    chess.point.y=column;
                }
                chess.invalidate();
                return true;
            }

            /**
             * 短按进入选择
             * @param e
             */
            @Override
            public void onShowPress(MotionEvent e) {
                super.onShowPress(e);
                chess.chesses.remove(chess.chesses.size() - 1);       //撤销长按出现的棋子
                chess.invalidate();
                isWin = false;

                relativeLayout.setVisibility(View.VISIBLE);
                relativeLayout.setBackgroundColor(Color.argb(0, 204, 153, 102));
                Button btRegret = (Button) findViewById(R.id.button_regret);     //悔棋
                Button btRevert = (Button) findViewById(R.id.button_revert);     //重新开始
                Button btReturn = (Button) findViewById(R.id.button_return);     //返回
                btRevert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        relativeLayout.setVisibility(View.GONE);
                        chess.chesses.removeAll(chess.chesses);
                        x = dm.widthPixels / 2 - chess.len * chess.count * 5/2;
                        y = dm.heightPixels / 2 - chess.len * chess.count * 5/2;
                        chess.setX(x);
                        chess.setY(y);
                        chess.invalidate();
                    }
                });
                btRegret.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        relativeLayout.setVisibility(View.GONE);
                        if(chess.chesses.size()>0) {
                            chess.point.x=chess.chesses.get(chess.chesses.size()-1).row;
                            chess.point.y=chess.chesses.get(chess.chesses.size()-1).column;
                            chess.chesses.remove(chess.chesses.size() - 1);
                            chess.invalidate();
                        }else{
                            Toast.makeText(TestActivity.this, "无棋可悔", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                btReturn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        relativeLayout.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    /**
     * 棋盘缩放  以及交由手势处理Event
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int nCnt = event.getPointerCount();
        if (event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN && 2 == nCnt)  // 2表示两个手指
        {
            int xlen = Math.abs((int) event.getX(0) - (int) event.getX(1));
            int ylen = Math.abs((int) event.getY(0) - (int) event.getY(1));

            nLenStart = Math.sqrt((double) xlen * xlen + (double) ylen * ylen);

        } else if (event.getActionMasked() == MotionEvent.ACTION_MOVE && 2 == nCnt) {
            int xlen = Math.abs((int) event.getX(0) - (int) event.getX(1));
            int ylen = Math.abs((int) event.getY(0) - (int) event.getY(1));

            double nLenEnd = Math.sqrt((double) xlen * xlen + (double) ylen * ylen);

            if ((nLenEnd - nLenStart) > 5) {
                nLenStart = nLenEnd;
                if (chess.len <= 600) {
                    chess.len += (3 * chess.len / 100);
                }
            } else if ((nLenStart - nLenEnd) > 5) {
                nLenStart = nLenEnd;
                if (chess.len >= 50) {
                    chess.len -= (3 * chess.len / 100);
                }
            }
            chess.invalidate();     //重绘棋盘
        }

        return gesture.onTouchEvent(event);
    }

    /**
     * 判赢延时200毫秒，以免因手势滑动以及短按造成的误赢
     */
    class ToastWin extends AsyncTask<Integer, Void, Void> {
        int color;

        @Override
        protected Void doInBackground(Integer... params) {
            color = params[0];
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (isWin) {
//                Toast.makeText(TestActivity.this, color + "is win", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(TestActivity.this)
                        .setCancelable(false)
                        .setTitle("输赢已定")
                        .setMessage(color == Color.WHITE ? "白子赢了" : "黑子赢了")
                        .setPositiveButton("重新开始", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                isWin = false;
                                chess.chesses.removeAll(chess.chesses);
                                x = dm.widthPixels / 2 - chess.len * chess.count * 5/2;
                                y = dm.heightPixels / 2 - chess.len * chess.count * 5/2;
                                chess.setX(x);
                                chess.setY(y);
                                chess.invalidate();
                            }
                        })
                        .setNegativeButton("继续", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                isWin = false;
                            }
                        })
                        .create()
                        .show();
            }
        }
    }
}
