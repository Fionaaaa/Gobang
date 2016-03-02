package com.example.fiona.gobang;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

/**
 * 五子棋的棋子
 * Created by fiona on 15-12-13.
 */
public class Chess {

    int row;        //在棋盘的哪行
    int column;     //在棋盘的哪列
    int color;      //颜色
    Paint p;        //画笔
    static Paint pDis = new Paint();     //定位画笔

    public Chess(int color, int row, int column) {

        this.color = color;
        this.column = column;
        this.row = row;
        p = new Paint();
        p.setAntiAlias(true);
        p.setColor(color);
    }

    /**
     * 落棋子
     *
     * @param canvas
     * @param r
     */
    public void drawChess(Canvas canvas, float x, float y, float r) {
        canvas.drawCircle(x, y, r, p);
    }

    /**
     * 定位
     *
     * @param canvas
     * @param point
     */
    static public void drawPitch(Canvas canvas, PointF point, float r) {
        pDis.setColor(Color.RED);
        pDis.setAntiAlias(true);
        pDis.setStrokeWidth(2);
        float x = Chessboard.x + point.y * Chessboard.len;
        float y = Chessboard.y + point.x * Chessboard.len;
//        float inc = (float) (r * Math.sqrt(2) / 2);
        /*canvas.drawLine(x + inc, y - inc, x + inc + r/2, y - inc - r/2, pDis);
        canvas.drawLine(x + inc, y + inc, x + inc + r/2, y + inc + r/2, pDis);
        canvas.drawLine(x - inc, y + inc, x - inc - r/2, y + inc + r/2, pDis);
        canvas.drawLine(x - inc, y - inc, x - inc - r/2, y - inc - r/2, pDis);*/

        canvas.drawLine(x + r / 4, y - r / 4, x + r, y - r / 4, pDis);
        canvas.drawLine(x + r / 4, y - r / 4, x + r / 4, y - r, pDis);

        canvas.drawLine(x + r / 4, y + r / 4, x + r, y + r / 4, pDis);
        canvas.drawLine(x + r / 4, y + r / 4, x + r / 4, y + r, pDis);

        canvas.drawLine(x - r / 4, y + r / 4, x - r / 4, y + r, pDis);
        canvas.drawLine(x - r / 4, y + r / 4, x - r, y + r / 4, pDis);

        canvas.drawLine(x - r / 4, y - r / 4, x - r, y - r / 4, pDis);
        canvas.drawLine(x - r / 4, y - r / 4, x - r / 4, y - r, pDis);
    }

    static public void drawRect(Canvas canvas, PointF point, float r) {
        float i = r / 5;
        pDis.setColor(Color.RED);
        pDis.setAntiAlias(true);
        pDis.setStrokeWidth(2);
        pDis.setStyle(Paint.Style.STROKE);
        float x = Chessboard.x + point.y * Chessboard.len;
        float y = Chessboard.y + point.x * Chessboard.len;
//        canvas.drawRect(x-r,y-r,x+r,y+r,pDis);
        canvas.drawLine(x + i, y - i - r, x + r + i, y - r - i, pDis);
        canvas.drawLine(x + i + r, y - i, x + r + i, y - r - i, pDis);

        canvas.drawLine(x + i + r, y + i, x + r + i, y + r + i, pDis);
        canvas.drawLine(x + i, y + i + r, x + r + i, y + r + i, pDis);

        canvas.drawLine(x - i, y + i + r, x - r - i, y + r + i, pDis);
        canvas.drawLine(x - i - r, y + i, x - r - i, y + r + i, pDis);

        canvas.drawLine(x - i, y - i - r, x - r - i, y - r - i, pDis);
        canvas.drawLine(x - i - r, y - i, x - r - i, y - r - i, pDis);
    }
}

