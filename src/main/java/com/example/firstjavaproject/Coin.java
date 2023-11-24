package com.example.firstjavaproject;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import utils.Vector2D;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class Coin extends Object{
    Vector2D position;
    int canvasWidth = 900;
    int canvasHeight = 700;


    public Vector2D getPosition() {
        return this.position;
    }

    Coin() {
        this.position = this.getRandomPosition();
    }

     public Vector2D getRandomPosition() {
        Random r = new Random();
        int margin = 100;
        int randWidth = margin + r.nextInt(canvasWidth - 2*margin);
        int randHeight = margin + r.nextInt(canvasHeight - 2*margin);
        return new Vector2D(randWidth,randHeight);
    }


    public SVGPath getShape() {
        String path = "M -7 0 A 1 1 0 0 0 7 0 A 1 1 0 0 0 -7 0 M 0 -4 C 2 -4 2 4 0 4 C -2 4 -2 -4 0 -4";
        SVGPath coin = new SVGPath();

        coin.setContent(path);
        coin.setLayoutX(this.getPosition().x);
        coin.setLayoutY(this.getPosition().y);
        coin.setFill(Color.YELLOW);
        coin.setRotate(0); // modulo 360
        coin.setStrokeWidth(1);
        coin.setStroke(Color.BLACK);

        return coin;
    }

    public void update() {

    }

    public boolean isObstacle() {
        return false;
    }

    public boolean isCoin() {
        return true;
    }

    public Rectangle getHitbox() {
        Rectangle coinBox = new Rectangle();
        coinBox.setX(this.getPosition().x);
        coinBox.setY(this.getPosition().y);
        coinBox.setFill(Color.RED);
        coinBox.setWidth(17);
        coinBox.setHeight(17);
        coinBox.setRotate(0);

        return coinBox;
    }
}
