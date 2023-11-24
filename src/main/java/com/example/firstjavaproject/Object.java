package com.example.firstjavaproject;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import utils.Vector2D;

public abstract class Object {

        public abstract Vector2D getPosition();

        public abstract SVGPath getShape();

        public abstract void update();

        public abstract boolean isObstacle();

        public abstract boolean isCoin();

        public abstract Rectangle getHitbox();

        public Rectangle getScore(int points) {
//                Text pointsText = new Text();
//                pointsText.setX(0);
//                pointsText.setY(0);
//                pointsText.setText("Points: " + points);
//                pointsText.setFill(Color.WHITE);
//
//                return pointsText;
                Rectangle rect = new Rectangle();
        rect.setX(this.getPosition().x+8);
        rect.setY(this.getPosition().y);
        rect.setFill(Color.RED);
        rect.setWidth(50);
        rect.setHeight(50);

        return rect;
        }
}
