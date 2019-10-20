package com.ai.caro;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

public class GameController extends SurfaceView implements SurfaceHolder.Callback {

    private Board board;
    private Computer computer;
    private Renderer renderer;
    private boolean drag = false;
    private int dragTick = 0;
    private boolean continueFlag = true;
    private Point lastTouchPosition = new Point();

    public GameController(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        board = new Board();
        board.initialize(19, 19);
        computer = new Computer(board);
        renderer = new Renderer(board);
    }

    public void reset() {
        continueFlag = true;
        board.reset();
        renderer.resetOffset();
        draw();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        final Canvas canvas = holder.lockCanvas();
        renderer.initialize(canvas.getWidth(), canvas.getHeight());
        holder.unlockCanvasAndPost(canvas);
        draw();
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastTouchPosition.set((int) event.getX(), (int) event.getY());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        dragTick++;
                        drag = dragTick > 3;
                        if (drag) {
                            renderer.calculateOffset((int) (event.getX() - lastTouchPosition.x) / 2, (int) (event.getY() - lastTouchPosition.y) / 2);
                            lastTouchPosition.set((int) event.getX(), (int) event.getY());
                            draw();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        dragTick = 0;
                        if (drag) {
                            drag = false;
                        } else {
                            update((int) event.getX(), (int) event.getY());
                        }
                        draw();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void update(int x, int y) {
        if (continueFlag) {
            x = (x + renderer.getXOffset()) / renderer.getBlockSize();
            y = (y + renderer.getYOffset()) / renderer.getBlockSize();
            if (board.select(x, y, Board.VALUE_O)) {
                if (board.checkWin(x, y) == Board.VALUE_O) {
                    Toast.makeText(getContext(), "You win", Toast.LENGTH_SHORT).show();
                    continueFlag = false;
                    return;
                }
                Point computerMove = computer.AI();
                System.out.println(computerMove.x + " " + computerMove.y);
                board.select(computerMove.x, computerMove.y, Board.VALUE_X);
                if (board.checkWin(computerMove.x, computerMove.y) == Board.VALUE_X) {
                    Toast.makeText(getContext(), "Computer win", Toast.LENGTH_SHORT).show();
                    continueFlag = false;
                }
            }
        }
    }

    public void draw() {
        Canvas canvas = null;
        try {
            canvas = getHolder().lockCanvas();
            synchronized (canvas)  {
                renderer.draw(canvas);
            }
        } catch (Exception e)  {
            e.printStackTrace();
        } finally {
            if(canvas!= null)  {
                getHolder().unlockCanvasAndPost(canvas);
            }
        }
    }

    public void setDifficulty(int depth) {
        computer.setDepth(depth);
    }
}
