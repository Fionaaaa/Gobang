package com.example.fiona.gobang;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by fiona on 15-12-13.
 */
public class Chessboard extends View {

    Paint p;
    float w = 0;     //五子棋盘的宽
    float h = 0;     //五子棋盘的高
    static int len = 120;   //一个格子的边长
    static int count = 20;  //格子的个数
    static float x;         //棋盘x坐标
    static float y;         //棋盘y坐标

    PointF point;           //  当前点击的点

    ArrayList<Chess> chesses = new ArrayList<>();     //棋子总数

    public Chessboard(Context context) {
        this(context, null);
    }

    public Chessboard(Context context, AttributeSet attrs) {
        super(context, attrs);
        p = new Paint();
        p.setAntiAlias(true);
        p.setStrokeWidth(2);
        point = new PointF(0, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        w = count * len;
        h = count * len;
        x = getWidth() / 2 - w / 2;
        y = getHeight() / 2 - h / 2;

        /**
         * 画行
         */
        p.setColor(Color.BLACK);
        for (int i = 0; i <= count; i++) {
            canvas.drawLine(x, y + len * i, x + w, y + len * i, p);
        }

        /**
         * 画列
         */
        for (int i = 0; i <= count; i++) {
            canvas.drawLine(x + len * i, y, x + len * i, y + h, p);
        }

        /**
         * 棋盘中心
         */
        p.setColor(Color.RED);
        canvas.drawLine(x + len * (count / 2 - 1) + len / 2, y + len * (count / 2 - 1) + len / 2, x + len * (count / 2 + 1) - len / 2, y + len * (count / 2 + 1) - len / 2, p);
        canvas.drawLine(x + len * (count / 2 - 1) + len / 2, y + len * (count / 2 + 1) - len / 2, x + len * (count / 2 + 1) - len / 2, y + len * (count / 2 - 1) + len / 2, p);
        Chess.drawPitch(canvas, new PointF(count / 2, count / 2),len/5*2);

        /**
         * 背景
         */
        canvas.drawARGB(128, 204, 153, 102);

        /**
         * 画棋子
         */
        for (Chess chess : chesses) {
            chess.drawChess(canvas, x + len * chess.column, y + len * chess.row, len / 5 * 2);
        }

        /**
         * 定位
         */
        if (point.x != 0) {
            boolean isExist = false;
            for (Chess c : chesses) {
                if (point.x == c.row && point.y == c.column) {
                    isExist = true;
                }
            }
            if (isExist) {
                Chess.drawRect(canvas, point,len/5*2);
            } else {
                Chess.drawPitch(canvas, point,len/5*2);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 设置view的大小
         */
        setMeasuredDimension(len * 5 * count, len * 5 * count);
    }
}
