package app.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import app.model.Cell;
import java.util.LinkedList;

public class CellComponent extends Canvas implements Redrawable {

    int startX;
    int startY;
    double widthBlock;
    double heightBlock;
    String[] moves = null;
    LinkedList<Cell> cells = new LinkedList<>();

    public void showPoints(String movesSt, int startX, int startY) {
        moves = movesSt.split(",");
        this.startX = startX;
        this.startY = startY;

        cells.clear();

        double x = startX;
        double y = startY;

        for (int i = 0; i < moves.length; i++) {
            cells.add(new Cell((int) x, (int) y * -1));
            String move = moves[i];
            switch (move) {
                case "Left": {
                    x--;
                    break;
                }
                case "Down": {
                    y++;
                    break;
                }
                case "Right": {
                    x++;
                    break;
                }
                case "Up": {
                    y--;
                    break;
                }
            }
        }
        cells.add(new Cell((int) x, (int) y * -1));
    }

    public LinkedList<Cell> getCells() {
        return cells;
    }

    @Override
    public void setBlockSize(double widthBlock, double heightBlock) {
        this.heightBlock = heightBlock;
        this.widthBlock = widthBlock;
    }

    @Override
    public void redraw() {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.clearRect(0, 0, this.getWidth(), this.getHeight());
        if (moves != null) {
            double x = startX;
            double y = startY;
            for (int i = 0; i < moves.length; i++) {
                String move = moves[i];
                gc.setStroke(Color.BLACK);
                switch (move) {
                    case "Left": {
                        gc.strokeLine(x * widthBlock, y * heightBlock, (x - 1) * widthBlock, y * heightBlock);
                        x--;
                        break;
                    }
                    case "Down": {
                        gc.strokeLine(x * widthBlock, y * heightBlock, x * widthBlock, (y + 1) * heightBlock);
                        y++;
                        break;
                    }
                    case "Right": {
                        gc.strokeLine(x * widthBlock, y * heightBlock, (x + 1) * widthBlock, y * heightBlock);
                        x++;
                        break;
                    }
                    case "Up": {
                        gc.strokeLine(x * widthBlock, y * heightBlock, x * widthBlock, (y - 1) * heightBlock);
                        y--;
                        break;
                    }
                }
            }
        }
    }
}
