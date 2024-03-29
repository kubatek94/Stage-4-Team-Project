package ui.utilities;

import backend.Block;
import backend.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by Adam Cattermole
 * 24/02/2016
 */

public class UiPointUp extends UiBlock {

    private static final int WIDTH = 60;
    private static final int HEIGHT = 60;

    public UiPointUp() {
        super(WIDTH, HEIGHT);
    }

    public UiPointUp(double x, double y) {
        super(x, y, WIDTH, HEIGHT, new Point(Point.Orientation.UP));
    }
    
    public UiPointUp(double x, double y, Block block) {
    	super(x, y, WIDTH, HEIGHT, block);
    }

    @Override
    /**
     * Initially draw straight line
     * first bezier curve draws from quarter to midpoint
     * second from midpoint to three quarters
     * line connects to end*/
    protected void draw() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.beginPath();
        gc.setStroke(color);
        gc.setLineWidth(STROKE_SIZE);
        gc.strokeLine(0, HEIGHT*.75, WIDTH, HEIGHT*.75);
        gc.bezierCurveTo(WIDTH*.25, HEIGHT*.75, WIDTH*.5, HEIGHT*.75, WIDTH*.5, HEIGHT*.5);
        gc.bezierCurveTo(WIDTH*.5, HEIGHT*.5, WIDTH*.5, HEIGHT*.25, WIDTH*.75, HEIGHT*.25);
        gc.lineTo(WIDTH, HEIGHT*.25);
        gc.stroke();
        
        gc.setLineWidth(3);
        gc.strokeLine(0, (HEIGHT*.75)+(HEIGHT*0.05), 0, (HEIGHT*.75)-(HEIGHT*0.05));
        gc.strokeLine(WIDTH, (HEIGHT*.75)+(HEIGHT*0.05), WIDTH, (HEIGHT*.75)-(HEIGHT*0.05));
        gc.strokeLine(WIDTH, (HEIGHT*.25)+(HEIGHT*0.05), WIDTH, (HEIGHT*.25)-(HEIGHT*0.05));
        
        gc.closePath();
    }
}
