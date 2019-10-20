package com.ai.caro;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Renderer {

    private Board board;
    private int xOffset;
    private int yOffset;
    private int width;
    private int height;
    private int blockSize;
    private int tickSize;
    private int tickPadding;
    private Rect rect = new Rect();
    private Paint linePaint = new Paint();

    public Renderer(Board board) {
        this.board = board;
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(3.0f);
    }

    public int getXOffset() {
        return xOffset;
    }

    public int getYOffset() {
        return yOffset;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        int row = board.getRow();
        int col = board.getCol();
        int[][] matrix = board.getMatrix();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] != Board.VALUE_EMPTY) {
                    int x = j * blockSize + tickPadding - xOffset;
                    int y = i * blockSize + tickPadding - yOffset;
                    rect.set(x, y, x + tickSize, y + tickSize);
                    canvas.drawBitmap(matrix[i][j] == Board.VALUE_O ? BitmapManager.getO() : BitmapManager.getX(), null, rect, null);
                }
            }
        }
        for (int i = 1; i < row; i++) {
            canvas.drawLine(i * blockSize - xOffset, -yOffset, i * blockSize - xOffset, row * blockSize - yOffset, linePaint);
            canvas.drawLine(-xOffset, i * blockSize - yOffset, col * blockSize - xOffset, i * blockSize - yOffset, linePaint);
        }
    }

    public void calculateOffset(int x, int y) {
        if (board.getRow() * blockSize > height) {
            yOffset -= y;
            yOffset = Math.max(0, yOffset);
            yOffset = Math.min(board.getRow() * blockSize - height, yOffset);
        }
        if (board.getCol() * blockSize > width) {
            xOffset -= x;
            xOffset = Math.max(0, xOffset);
            xOffset = Math.min(board.getCol() * blockSize - width, xOffset);
        }
    }

    public void resetOffset() {
        xOffset = (blockSize * board.getCol() - width) / 2;
        yOffset = (blockSize * board.getRow() - height) / 2;
    }

    public void initialize(int width, int height) {
        this.width = width;
        this.height = height;
        blockSize = Math.round(Math.min((float) width / 10, (float) height / 10));
        tickSize = blockSize * 3 / 4;
        tickPadding = (blockSize - tickSize) / 2;
        resetOffset();
    }
}
