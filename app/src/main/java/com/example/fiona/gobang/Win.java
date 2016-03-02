package com.example.fiona.gobang;

import java.util.ArrayList;

/**
 * 五子棋判赢
 * Created by fiona on 15-12-14.
 */
public class Win {

    private static int n = Chessboard.count;

    private Win() {

    }

    /**
     * 棋子判赢方法
     *
     * @param c
     * @param chesses
     * @return
     */
    public static boolean isWin(Chess c, ArrayList<Chess> chesses) {
        if (doHorizontal(c, chesses) || doVertical(c, chesses) || doDiagonalRight(c, chesses)||doDiagonalLeft(c,chesses)) {
            return true;
        }
        return false;
    }

    /**
     * 水平方向
     *
     * @param c
     * @param chesses
     */
    static private boolean doHorizontal(Chess c, ArrayList<Chess> chesses) {
        int count = 0;
        for (int i = c.column + 1; i <= n; i++) {
            if (getColor(c.row, i, chesses) == c.color) {
                count++;
            } else {
                break;
            }
        }
        for (int i = c.column - 1; i >= 0; i--) {
            if (getColor(c.row, i, chesses) == c.color) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 4) {
            return true;
        }
        return false;
    }

    /**
     * 垂直方向
     *
     * @param c
     * @param chesses
     * @return
     */
    static private boolean doVertical(Chess c, ArrayList<Chess> chesses) {
        int count = 0;
        for (int i = c.row + 1; i <= n; i++) {
            if (getColor(i, c.column, chesses) == c.color) {
                count++;
            } else {
                break;
            }
        }
        for (int i = c.row - 1; i >= 0; i--) {
            if (getColor(i, c.column, chesses) == c.color) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 4) {
            return true;
        }
        return false;
    }

    /**
     * 斜向右
     *
     * @param c
     * @param chesses
     * @return
     */
    static private boolean doDiagonalRight(Chess c, ArrayList<Chess> chesses) {
        int count = 0;
        for (int i = c.row - 1, j = c.column + 1; j <= n && i >= 0; i--, j++) {
            if (getColor(i, j, chesses) == c.color) {
                count++;
            } else {
                break;
            }
        }
        for (int i = c.row + 1, j = c.column - 1; j >= 0 && i <= n; i++, j--) {
            if (getColor(i, j, chesses) == c.color) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 4) {
            return true;
        }
        return false;
    }

    /**
     * 斜向左
     *
     * @param c
     * @param chesses
     * @return
     */
    static private boolean doDiagonalLeft(Chess c, ArrayList<Chess> chesses) {
        int count = 0;
        for (int i = c.row - 1, j = c.column - 1; j >= 0 && i >= 0; i--, j--) {
            if (getColor(i, j, chesses) == c.color) {
                count++;
            } else {
                break;
            }
        }
        for (int i = c.row + 1, j = c.column + 1; j <= n && i <= n; i++, j++) {
            if (getColor(i, j, chesses) == c.color) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 4) {
            return true;
        }
        return false;
    }

    /**
     * 获得指定行和列的棋子颜色，不存在则返回404
     *
     * @param row
     * @param column
     * @param chesses
     * @return
     */
    static private int getColor(int row, int column, ArrayList<Chess> chesses) {
        for (Chess c : chesses) {
            if (row == c.row && column == c.column) {
                return c.color;
            }
        }
        return 404;
    }
}
