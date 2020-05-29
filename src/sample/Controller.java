package sample;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class Controller
{
    @FXML
    private RadioButton select;
    @FXML
    private RadioButton ellipse;
    @FXML
    private RadioButton rectangle;
    @FXML
    private RadioButton line;
    @FXML
    private ColorPicker colorpicker;
    @FXML
    private Button delete;
    @FXML
    private Button clone;
    @FXML
    private Pane canvas;

    private EventHandler selectListener;
    private EventHandler ellipseListener;
    private EventHandler rectangleListener;
    private EventHandler lineListener;
    private EventHandler colorListener;
    private EventHandler deleteListener;
    private EventHandler cloneListener;

    private String shape = "";
    private Color color = Color.valueOf("#13e1dd");
    private Line lineDrawn;
    private Rectangle rectangleDrawn;
    private Ellipse ellipseDrawn;
    private double x, y;
    private ArrayList<Shape> shapesList;

    @FXML
    public void initialize()
    {
        selectListener = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                shape = "select";
            }
        };
        rectangleListener = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                shape = "rectangle";
            }
        };
        ellipseListener = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                shape = "ellipse";
            }
        };
        lineListener = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                shape = "line";
            }
        };
        colorListener = new EventHandler() {
            @Override
            public void handle(Event event) {
                color = colorpicker.getValue();
            }
        };
        deleteListener = new EventHandler() {
            @Override
            public void handle(Event event) {
                System.out.println("delete");
            }
        };
        cloneListener = new EventHandler() {
            @Override
            public void handle(Event event) {
                System.out.println("clone");
            }
        };

        this.select.setOnMouseClicked(selectListener);
        this.rectangle.setOnMouseClicked(rectangleListener);
        this.ellipse.setOnMouseClicked(ellipseListener);
        this.line.setOnMouseClicked(lineListener);
        this.colorpicker.setOnAction(colorListener);
        this.delete.setOnAction(deleteListener);
        this.clone.setOnAction(cloneListener);

        canvas.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getX();
            y = mouseEvent.getY();
            switch (shape) {
                case "select":
                    for(Shape s : shapesList)
                    {
                        //if on est sur une surface
                    }
                    break;
                case "ellipse":
                    createEllipse(mouseEvent, color);
                    break;
                case "rectangle":
                    createRectangle(mouseEvent, color);
                    break;
                case "line":
                    createLine(mouseEvent, color);
                    break;
            }
        });

        canvas.setOnMouseDragged(e -> {
            switch (shape) {
                case "select":

                    break;
                case "ellipse":
                    ellipseDrawn.setRadiusX(Math.abs(e.getX()-x));
                    ellipseDrawn.setRadiusY(Math.abs(e.getY()-y));
                    shapesList.add(ellipseDrawn);
                    break;
                case "rectangle":
                    rectangleDrawn.setHeight(Math.abs(e.getX()-x));
                    rectangleDrawn.setWidth(Math.abs(e.getY()-y));
                    shapesList.add(rectangleDrawn);
                    break;
                case "line":
                    lineDrawn.setEndX(e.getX());
                    lineDrawn.setEndY(e.getY());
                    shapesList.add(lineDrawn);
                    break;
            }
        });
    }

    public void createRectangle(MouseEvent event, Color color)
    {
        rectangleDrawn = new Rectangle(event.getX(), event.getY(), 0,0);
        rectangleDrawn.setStroke(color);
        rectangleDrawn.setFill(color);
        canvas.getChildren().add(rectangleDrawn);
    }

    public void createEllipse(MouseEvent event, Color color)
    {
        ellipseDrawn = new Ellipse(event.getX(), event.getY(), 0, 0);
        ellipseDrawn.setStroke(color);
        ellipseDrawn.setFill(color);
        canvas.getChildren().add(ellipseDrawn);
    }

    public void createLine(MouseEvent event, Color color)
    {
        lineDrawn = new Line(event.getX(), event.getY(), event.getX(), event.getY());
        lineDrawn.setStroke(color);
        canvas.getChildren().add(lineDrawn);
    }

    public void clickShape(Shape s,MouseEvent event){
        s.setTranslateX(event.getX());
        s.setTranslateY(event.getY());
    }
}
