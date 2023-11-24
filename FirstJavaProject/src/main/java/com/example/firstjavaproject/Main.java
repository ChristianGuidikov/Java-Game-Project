package com.example.firstjavaproject;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.Ticker;
import utils.Vector2D;
import javafx.scene.canvas.Canvas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {

    public boolean timeOn = true;

    public int points = 0;

    public int getCW() {
        return 900;
    }

    public int getCH() {
        return 700;
    }

    @Override
    public void start(Stage stage) throws IOException {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root,getCW(),getCH());
        stage.setScene(scene);

        Color canvasColor = Color.color(0.015625, 0, 0.25); // RGB coloring!
        BackgroundFill[] bgfill = {new BackgroundFill(canvasColor, CornerRadii.EMPTY, Insets.EMPTY)};

        Canvas canvas = new Canvas(this.getCW(), this.getCH());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        DrawManager dm = new DrawManager();
        Pane pane = new Pane(); // What is later called the "canvas" for the simulation is actually a Pane, as it allows for click interactions


        Player newBoid = new Player(new Vector2D(getCW()/2.0,getCH()/2.0));
        Coin coin = new Coin();

        Text pointsText = new Text();
        pointsText.setX(10);
        pointsText.setY(30);
        pointsText.setText("Points: " + points);
        pointsText.setFill(Color.WHITE);
        pointsText.setFont(Font.font(null, FontWeight.BOLD, 20));

        ArrayList<Object> objList = new ArrayList<Object>();
        objList.add(newBoid);
        objList.add(coin);
        dm.getElements().add(newBoid);
        dm.getPane().setBackground(new Background(bgfill));
        pane.setBackground(new Background(bgfill));
        root.setCenter(dm.getPane());

        int delay = 50;

        Runnable animate = () -> {
            pointsText.setText("Points: " + points);
            Random r = new Random();
            int chance = r.nextInt(100)+1;

            if (chance == 1) {
                Obstacle obst = new Obstacle();
                objList.add(obst);
            }
            dm.getPane().getChildren().clear();
            objList.forEach(object -> {

//                if (object.isObstacle()) // To show hitboxes
//                    dm.getPane().getChildren().add(object.getHitbox());
                if (!object.isCoin() && !object.isObstacle()) {
                    dm.getPane().getChildren().add(pointsText);
                }


                Rectangle playerBox = newBoid.getHitbox();
                Rectangle rect = object.getHitbox();

                dm.getElements().remove(object);

                if (!object.isCoin())
                    object.update();
                dm.getElements().add(object);
                if (object.isObstacle() && (object.getPosition().x > getCW() + delay || object.getPosition().x < -delay || object.getPosition().y > getCH() + delay || object.getPosition().y < -delay )) {
                    dm.getElements().remove(object);
                    objList.remove(object);
                }

                if (object.isObstacle() && (playerBox.getX()+20 >= rect.getX() && playerBox.getX() <= rect.getX()+50) && (playerBox.getY()+20 >= rect.getY() && playerBox.getY() <= rect.getY()+50)) {
                    Event.fireEvent(scene, new MouseEvent(MouseEvent.MOUSE_PRESSED, 0,
                    0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                    true, true, true, true, true, true, null));
                }

                if (object.isCoin() && (playerBox.getX()+20 >= rect.getX() && playerBox.getX() <= rect.getX()+17) && (playerBox.getY()+20 >= rect.getY() && playerBox.getY() <= rect.getY()+17)) {
                    dm.getElements().remove(object);
                    objList.remove(object);
                    Coin newCoin = new Coin();
                    objList.add(newCoin);
                    points += 1;
                }

            });

        };

        Ticker ticker;
        ticker = new Ticker(animate);
        ticker.start();

        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                timeOn = !timeOn;
                if (timeOn)
                    ticker.start();
                else
                    ticker.stop();
            }
        });


        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event!=null){
                    switch (event.getCode()) {
                        case A -> {
                            newBoid.centForce = newBoid.centForce.plus(new Vector2D(2,2));
                            newBoid.rotatingLeft = true;
                            newBoid.rotatingRight = false;
                        }
                        case S -> {
                            newBoid.slowingDown = true;
                            newBoid.rotatingRight = false;
                            newBoid.rotatingLeft = false;
                        }
                        case D -> {
                            newBoid.centForce = newBoid.centForce.plus(new Vector2D(2,2));
                            newBoid.rotatingRight = true;
                            newBoid.rotatingLeft = false;
                        }
                        case SPACE -> {
                            Vector2D maxAcc = new Vector2D(newBoid.maxAccel, newBoid.maxAccel);
                            double newX = Math.cos(newBoid.getRotation() * Math.PI / 180) * maxAcc.x;
                            double newY = Math.sin(newBoid.getRotation() * Math.PI / 180) * maxAcc.y;
                            newBoid.position = newBoid.position.plus(new Vector2D(newX, newY).times(15));
                        }
                    }
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event!=null){
                    switch (event.getCode()) {
                        case A -> {
                            newBoid.rotatingLeft = false;
                        }
                        case S -> {
                            newBoid.slowingDown = false;
                        }
                        case D -> {
                            newBoid.rotatingRight = false;
                        }

                    }
                }
            }
        });


        stage.setTitle("Start!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}