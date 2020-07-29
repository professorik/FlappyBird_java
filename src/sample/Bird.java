package sample;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


import java.util.Date;

public class Bird extends Pane {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public Point2D velocity;
    private ImageView rectangle2;

    private Circle circle;

    private long x = 0;

    //TODO: доделать скелет

    public Bird() {
        velocity = new Point2D(0, 0);
        setTranslateX(300);
        setTranslateY(100);
        rectangle2 = new ImageView("/resources/output_pT747T.gif");

        circle = new Circle(rectangle2.getImage().getWidth()/2 , rectangle2.getImage().getHeight()/2  , 35 , Color.TRANSPARENT);
        getChildren().addAll(rectangle2, circle);
    }

    public void moveY(int value) {
        boolean downMove = value > 0 ? true : false;
        for (int i = 0; i < Math.abs(value); i++) {
            for (Wall wall : Main.walls) {
                if (this.getBoundsInParent().intersects(wall.getBoundsInParent())) {
                    Main.isLose = true;
                    if (downMove) {
                        setTranslateY(getTranslateY() - 1);
                        return;
                    } else {
                        setTranslateY(getTranslateY() + 1);
                        return;
                    }
                }
            }
            if (!downMove){
                rectangle2.setRotate(-15);
                x = new Date().getTime();
            }else{
                rectangle2.setRotate(new Date().getTime() - x > 890? 90: (new Date().getTime() - x)/10);
            }
            if (getTranslateY() < 0) {
                setTranslateY(0);
            }
            if (getTranslateY() > Main.HEIGHT - 20 - rectangle2.getImage().getHeight()/2) {
                setTranslateY(Main.HEIGHT - 20 - rectangle2.getImage().getHeight()/2);
            }
            setTranslateY(getTranslateY() + (downMove ? 1 : -1));
        }
    }

    public void moveX(int value) {
        for (int i = 0; i < value; i++) {
            setTranslateX(getTranslateX() + 1);
            for (Wall wall : Main.walls) {
                if (this.getBoundsInParent().intersects(wall.getBoundsInParent())) {
                    Main.isLose = true;
                    System.out.println("lar");
                    if (getTranslateX() + rectangle2.getImage().getWidth() == wall.getTranslateX()) {
                        setTranslateX(getTranslateX() - 1);
                        return;
                    }
                    if (velocity.getY() < 0) {
                        setTranslateY(getTranslateY() - 1);
                        return;
                    } else {
                        setTranslateY(getTranslateY() + 1);
                        return;
                    }
                }
                if (getTranslateX() == wall.getTranslateX()) {
                    Main.score++;
                    return;
                }
            }
        }
    }

    public void jump() {
        velocity = new Point2D(3, -15);
    }
}
