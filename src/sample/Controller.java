package sample;

import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
import javafx.scene.transform.Scale;
import javafx.stage.WindowEvent;

import java.util.ArrayList;

public class Controller
{
    //Elements du FXML
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
    //Listeners
    private EventHandler selectListener;
    private EventHandler ellipseListener;
    private EventHandler rectangleListener;
    private EventHandler lineListener;
    private EventHandler colorListener;
    private EventHandler deleteListener;
    private EventHandler cloneListener;

    private String currentShape = "";
    private Color color = Color.valueOf("#13e1dd");
    private Line lineDrawn;
    private Rectangle rectangleDrawn;
    private Ellipse ellipseDrawn;
    private Shape selection;
    //position initiale des cliques
    private double x, y;

    @FXML
    public void initialize()
    {
        selectListener = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                currentShape = "select";
            }
        };
        rectangleListener = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                currentShape = "rectangle";
            }
        };
        ellipseListener = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                currentShape = "ellipse";
            }
        };
        lineListener = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                currentShape = "line";
            }
        };
        colorListener = new EventHandler() {
            @Override
            public void handle(Event event) {
                color = colorpicker.getValue();
                selection.setFill(color);
            }
        };
        deleteListener = new EventHandler() {
            @Override
            public void handle(Event event) {
                canvas.getChildren().remove(selection);
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



        //Ecouteur pour la création des formes
        canvas.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getX();
            y = mouseEvent.getY();
            switch (currentShape) {
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
        //Ecouteur pour agrandir la forme créée
        canvas.setOnMouseDragged(e -> {
            switch (currentShape) {
                case "ellipse":
                    ellipseDrawn.setRadiusX(Math.abs(e.getX()-x));
                    ellipseDrawn.setRadiusY(Math.abs(e.getY()-y));
                    break;
                case "rectangle":
                    rectangleDrawn.setHeight(Math.abs(e.getX()-x));
                    rectangleDrawn.setWidth(Math.abs(e.getY()-y));
                    break;
                case "line":
                    lineDrawn.setEndX(e.getX());
                    lineDrawn.setEndY(e.getY());
                    break;
            }
        });
    }

    //Permet de créer un rectangle à la position du clique dans canvas
    //Ajoute un écouteur pour pouvoir le sélectionner
    public void createRectangle(MouseEvent event, Color color)
    {
        rectangleDrawn = new Rectangle(event.getX(), event.getY(), 0,0);
        rectangleDrawn.setStroke(Color.BLACK);
        rectangleDrawn.setFill(color);
        selectRect(rectangleDrawn);
        canvas.getChildren().add(rectangleDrawn);
    }

    //Permet de mettre en surbrillance (agrandissement) un rectangle lorsqu'on clique dessus
    //Problème : on peut sélectionner plusieurs formes en même temps
    public void selectRect(Rectangle rect)
    {
        double scale;

        if(rect.getScaleX() == 1)
        {
            scale = 1.3;
            selection = rect;
        }
        else scale = 1;

        rect.setOnMouseClicked(mouseEvent -> {
            if(currentShape == "select")
            {
                //Si la forme est sélectionnée
                if(scale == 1.3)
                {
                    uniqueSelection();
                }
                rect.setScaleX(scale);
                rect.setScaleY(scale);
                selectRect(rect);
            }
        });
    }

    //Permet de créer une ellipse à la position du clique dans canvas
    //Ajoute un écouteur pour pouvoir le sélectionner
    public void createEllipse(MouseEvent event, Color color)
    {
        ellipseDrawn = new Ellipse(event.getX(), event.getY(), 0, 0);
        ellipseDrawn.setStroke(Color.BLACK);
        ellipseDrawn.setFill(color);
        selectElli(ellipseDrawn);
        canvas.getChildren().add(ellipseDrawn);
    }

    //Permet de mettre en surbrillance (agrandissement) une ellipse lorsqu'on clique dessus
    //Problème : on peut sélectionner plusieurs formes en même temps
    public void selectElli(Ellipse elli)
    {
        double scale;
        if(elli.getScaleX() == 1)
        {
            scale = 1.3;
            selection = elli;
        }
        else scale = 1;

        elli.setOnMouseClicked(mouseEvent -> {
            if(currentShape == "select")
            {
                //Si la forme est sélectionnée
                if(scale == 1.3)
                {
                    uniqueSelection();
                }
                elli.setScaleX(scale);
                elli.setScaleY(scale);
                selectElli(elli);
            }
        });
    }

    //permet de créer une ligne à la position du clique dans canvas
    public void createLine(MouseEvent event, Color color)
    {
        lineDrawn = new Line(event.getX(), event.getY(), event.getX(), event.getY());
        lineDrawn.setStroke(color);
        canvas.getChildren().add(lineDrawn);
    }

    //Désélectionne une autre forme qui serait sélectionnée
    //Il y a un bug quand resélectionne une forme qui a été désélectionnée de cette façon
    public void uniqueSelection()
    {
        for(Node s : canvas.getChildren())
        {
            if(s.getScaleX() == 1.3)
            {
                s.setScaleX(1);
                s.setScaleY(1);
            }
        }
    }
}
