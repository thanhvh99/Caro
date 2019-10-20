package com.ai.caro;

public class Board {

    static int VALUE_EMPTY = 0;
    static int VALUE_X = 1;
    static int VALUE_O = 2;

    private int row;
    private int col;
    private int[][] matrix;

    public Board() {

    }

    public void reset() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                matrix[i][j] = VALUE_EMPTY;
            }
        }
    }

    public int getValue(int x, int y) {
        if (isInRange(x, y)) {
            return matrix[y][x];
        }
        return VALUE_EMPTY;
    }

    public void setValue(int x, int y, int value) {
        if (isInRange(x, y)) {
            matrix[y][x] = value;
        }
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void initialize(int row, int col) {
        this.row = row;
        this.col = col;
        matrix = new int[row][col];
        reset();
    }

    public boolean select(int x, int y, int value) {
        if (isInRange(x, y) && matrix[y][x] == VALUE_EMPTY) {
            matrix[y][x] = value;
            return true;
        }
        return false;
    }

    public boolean isInRange(int x, int y) {
        return x >= 0 && x < col && y >= 0 && y < row;
    }

    public int checkWin(int x, int y) {
        int r = 0;
        int c = 0;
        boolean human;
        boolean computer;

        while (c < col - 5) {
            human = true;
            computer = true;
            for (int i = 0; i < 5; i++) {
                if (matrix[y][c + i] != VALUE_O) {
                    human = false;
                }
                if (matrix[y][c + i] != VALUE_X) {
                    computer = false;
                }
            }
            if (human) {
                return VALUE_O;
            }
            if (computer) {
                return VALUE_X;
            }
            c++;
        }

        while (r < row - 5) {
            human = true;
            computer = true;
            for (int i = 0; i < 5; i++) {
                if (matrix[r + i][x] != VALUE_O) {
                    human = false;
                }
                if (matrix[r + i][x] != VALUE_X) {
                    computer = false;
                }
            }
            if (human) {
                return VALUE_O;
            }
            if (computer) {
                return VALUE_X;
            }
            r++;
        }

        r = y;
        c = x;
        int min = Math.min(y, x);
        r -= min;
        c -= min;
        while (r < row - 5 && c < col - 5) {
            human = true;
            computer = true;
            for (int i = 0; i < 5; i++) {
                if (matrix[r + i][c + i] != VALUE_O) {
                    human = false;
                }
                if (matrix[r + i][c + i] != VALUE_X) {
                    computer = false;
                }
            }
            if (human) {
                return VALUE_O;
            }
            if (computer) {
                return VALUE_X;
            }
            c++;
            r++;
        }

        r = y;
        c = x;
        min = Math.min(c, row - r - 1);
        r += min;
        c -= min;
        while (r > 3 && c < col - 4) {
            human = true;
            computer = true;
            for (int i = 0; i < 5; i++) {
                if (matrix[r - i][c + i] != VALUE_O) {
                    human = false;
                }
                if (matrix[r - i][c + i] != VALUE_X) {
                    computer = false;
                }
            }
            if (human) {
                return VALUE_O;
            }
            if (computer) {
                return VALUE_X;
            }
            r--;
            c++;
        }
        return VALUE_EMPTY;
    }

}
