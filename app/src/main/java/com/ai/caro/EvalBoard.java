package com.ai.caro;

import android.graphics.Point;

public class EvalBoard {

    public int height, width;
    public int[][] EBoard;
    public int evaluationBoard = 0;
    public EvalBoard(int height, int width) {
        this.height = height;
        this.width = width;
        EBoard = new int[height][width];
        // ResetBoard();
    }

    public void resetBoard() {
        for (int r = 0; r < height; r++)
            for (int c = 0; c < width; c++)
                EBoard[r][c] = Board.VALUE_EMPTY;
    }

    public Point maxPos() {
        int max = 0;
        Point p = new Point();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (EBoard[i][j] > max) {
                    p.y = i;
                    p.x = j;
                    max = EBoard[i][j];
                }
            }
        }
        if (max == 0) {
            return null;
        }
        evaluationBoard = max;
        return p;
    }
}
