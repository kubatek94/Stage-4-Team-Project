package ui.utilities;

import backend.Block;
import backend.Section;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Created by Adam Cattermole
 * 24/02/2016
 */

public class UiSection extends UiBlock {
//    private static Image image = new Image("ui/resources/section.png");

    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;

    public UiSection() {
        super(WIDTH, HEIGHT);
    }

    public UiSection(double x, double y) {
        super(x, y, WIDTH, HEIGHT);
        block = new Section();
    }
    
    public UiSection(double x, double y, Block block) {
    	super(x, y);
    	this.block = block;
    }

    @Override
    protected void draw() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(STROKE_SIZE);
        gc.strokeLine(0, HEIGHT/2, WIDTH, HEIGHT/2);
    }
}