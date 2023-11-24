package com.example.firstjavaproject;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import utils.Vector2D;

import java.util.ArrayList;
import java.util.Random;

public class Obstacle extends Object {
    Vector2D velocity = new Vector2D(0,0);
    int type;
    int rotation = 270;
    Vector2D position;
    int canvasWidth = 900;
    int canvasHeight = 700;

    public Vector2D getPosition() {
    return this.position;
  }

    public int getRotation() { return this.rotation; }

    Obstacle() {
        this.position = getRandomPosition();
    }

    public boolean isObstacle() {
        return true;
    }

    public boolean isCoin() {
        return false;
    }

    public Vector2D getRandomPosition() {
        Random r = new Random();
        int side = r.nextInt(4);
        int randWidth = r.nextInt(canvasWidth);
        int randHeight = r.nextInt(canvasHeight);
        int delay = 50;
        Vector2D newPosition = new Vector2D(0,0);

        switch (side) {
            case 0 -> {
                newPosition = new Vector2D(canvasWidth + delay, randHeight);
                this.velocity = new Vector2D(-2,0);
            } // RIGHT
            case 1 -> {
                newPosition = new Vector2D(randWidth, canvasHeight + delay);
                this.velocity = new Vector2D(0,-2);
            } // DOWN
            case 2 -> {
                newPosition = new Vector2D(-delay, randHeight);
                this.velocity = new Vector2D(2,0);
            } // LEFT
            case 3 -> {
                newPosition = new Vector2D(randWidth, -delay);
                this.velocity = new Vector2D(0,2);
            } // UP
        }

        type = r.nextInt(3)+1;

        return newPosition;
    }


    public Vector2D getVelocity() {
        return this.velocity;
    }

    public Rectangle getHitbox() {
        Rectangle rect = new Rectangle();
        rect.setX(this.getPosition().x+8);
        rect.setY(this.getPosition().y);
        rect.setFill(Color.RED);
        rect.setWidth(50);
        rect.setHeight(50);

        return rect;
    }

    public SVGPath getShape() {
        String path = "M -28 -24 L 16 -24 C 40 -16 40 16 16 24 L -28 24 L -20 16 L -" +
            "28 8 L -20 0 L -28 -8 L -20 -16 L -28 -24 M 12 -18 A 2 2 90 0 0 8 -18 " +
            "A 2 2 90 0 0 12 -18 M 12 18 A 2 2 90 0 0 8 18 A 2 2 90 0 0 12 18 M 6 -2 C 4 -2 4 2 6 2"; // Cute ghost T_T

        Color color = Color.WHITE;
        SVGPath obstacle = new SVGPath();
        switch (type) {
            case 1 -> color = Color.BLUEVIOLET;
            case 2 -> color = Color.CORAL;
            case 3 -> color = Color.LIGHTSEAGREEN;
        }

        obstacle.setContent(path);
        obstacle.setLayoutX(getPosition().x);
        obstacle.setLayoutY(getPosition().y);
        obstacle.setFill(color);
        obstacle.setRotate(getRotation()); // modulo 360
        obstacle.setStrokeWidth(1);
        obstacle.setStroke(Color.BLACK);

        return obstacle;
    }

    public void update() {
        Random r = new Random();
        switch (type) {
            case 1 -> this.position = this.position.plus(this.velocity);
            case 2 -> this.position = this.position.plus(this.velocity.times((r.nextInt(3)+1)*2));
            case 3 -> this.position = this.position.plus(this.velocity.times(2));
        }
    }

}
