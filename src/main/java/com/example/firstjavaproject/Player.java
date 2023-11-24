package com.example.firstjavaproject;

import javafx.application.Application;
import javafx.scene.effect.MotionBlur;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import utils.Vector2D;
import com.example.firstjavaproject.Main;

import java.math.*;
import java.util.Random;
import java.util.Random.*;

import static java.lang.Math.atan;

public class Player extends Object {

  Vector2D position;
  double velocity = 0.5;
  Vector2D acceleration = new Vector2D(2,0);
  double maxAccel = 8;
  double minAccel = 3;
  Vector2D centForce = new Vector2D(-this.acceleration.x/3, -this.acceleration.y/3);
  boolean rotatingRight = false;
  boolean rotatingLeft = false;
  boolean slowingDown = false;
  boolean noGrip = false;
  int rotation = 0;
  int canvasWidth = 900;
  int canvasHeight = 700;

  Player(Vector2D initPosition) {
    this.position = initPosition;
  }

  public boolean isObstacle() {
        return false;
  }

  public boolean isCoin() {
        return false;
  }

  public Rectangle getHitbox() {
      Rectangle playerBox = new Rectangle();
      playerBox.setX(this.getPosition().x+8);
      playerBox.setY(this.getPosition().y);
      playerBox.setFill(Color.RED);
      playerBox.setWidth(20);
      playerBox.setHeight(20);
      playerBox.setRotate(this.getRotation());

      return playerBox;
  }

  public Vector2D getPosition() {
    return this.position;
  }

  public int getRotation() {
    return this.rotation%360;
  }

  public SVGPath getShape() {
    SVGPath boid = new SVGPath();
//    String path = "M 0 0 L 0 10.8 C 4.8 9.6 4.8 1.2 0 0 L 19.2 4.8 L 0 10.8"; // Smaller player
    String path = "M 0 0 L 0 19 C 5 15 5 3 0 0 L 31 9 L 0 19"; // Bigger player
//    String path = "M -28 -24 L 16 -24 C 40 -16 40 16 16 24 L -28 24 L -20 16 L -" +
//            "28 8 L -20 0 L -28 -8 L -20 -16 L -28 -24 M 12 -18 A 2 2 90 0 0 8 -18 " +
//            "A 2 2 90 0 0 12 -18 M 12 18 A 2 2 90 0 0 8 18 A 2 2 90 0 0 12 18 M 6 -2 C 4 -2 4 2 6 2"; // Cute ghost T_T

    boid.setContent(path);
    boid.setLayoutX(getPosition().x);
    boid.setLayoutY(getPosition().y);
    boid.setFill(Color.WHITE);
    boid.setRotate(getRotation()); // modulo 360
    boid.setStrokeWidth(1);
    boid.setStroke(Color.BLACK);

    MotionBlur mb = new MotionBlur();
    mb.setRadius((this.limitAcceleration().x-3)*5);
    mb.setAngle(Math.cos(this.getRotation() * Math.PI / 180) + Math.sin(this.getRotation())*Math.PI/180);
    boid.setEffect(mb);

    return boid;
  }

  public double limitVelocity() {
      if (this.velocity > 6)
        this.velocity = 6;
      if (this.velocity <= 0)
        this.velocity = 0;

    return this.velocity;
  }


  public Vector2D limitAcceleration() {
      if (this.acceleration.x > maxAccel)
        this.acceleration.x = maxAccel;
      if (this.acceleration.y > maxAccel)
        this.acceleration.y = maxAccel;
      if (this.acceleration.x <= minAccel)
        this.acceleration.x = minAccel;
      if (this.acceleration.y <= minAccel)
        this.acceleration.y = minAccel;

    return this.acceleration;
  }

  public Vector2D getCentForce() {
    double accelLength = Math.sqrt(Math.pow(this.acceleration.x,2) + Math.pow(this.acceleration.y,2));

    if (this.centForce.x <= 0.1)
      this.centForce.x = 0.1;
    if (this.centForce.y <= 0.1)
      this.centForce.y = 0.1;
    if (this.centForce.x > accelLength) {
      this.noGrip = true;
      this.centForce.x = accelLength;
    } else
      this.noGrip = false;
    if (this.centForce.y > accelLength) {
      this.centForce.y = accelLength;
      this.noGrip = true;
    } else
      this.noGrip = false;

    return this.centForce;
  }


   public void wrapEdges() {
     if (this.position.x > canvasWidth) {
       this.position = new Vector2D(0, this.position.y);
     }
     if (this.position.x < 0) {
       this.position = new Vector2D(canvasWidth, this.position.y);
     }
     if (this.position.y > canvasHeight) {
       this.position = new Vector2D(this.position.x, 0);
     }
     if (this.position.y < 0) {
       this.position = new Vector2D(this.position.x, canvasHeight);
     }
   }

   public void rotateRight() {
    if (rotatingRight)
      this.rotation += 5;
   }

   public void rotateLeft() {
    if (rotatingLeft)
      this.rotation -= 5;
   }

   public void slowDown() {
    if (slowingDown)
      this.acceleration = this.acceleration.times(0.5);
   }

  public void update() {
      this.acceleration = this.acceleration.times(1.1);
      double newX = Math.cos(this.getRotation() * Math.PI / 180) * limitAcceleration().x;
      double newY = Math.sin(this.getRotation() * Math.PI / 180) * limitAcceleration().y;

      if (!rotatingRight && !rotatingLeft && !this.noGrip) {
        this.acceleration = this.acceleration.times(1.1);
        this.position = this.position.plus(new Vector2D(newX, newY));

      } else if (rotatingRight && !this.noGrip) {
        this.acceleration = this.acceleration.times(0.9);
        if (this.limitAcceleration().x > 7 || this.limitAcceleration().y > 7) {
            newX = Math.cos(this.getRotation() * Math.PI / 180) * limitAcceleration().x + Math.cos((rotation - 90) * Math.PI / 180) * getCentForce().x;
            newY = Math.sin(this.getRotation() * Math.PI / 180) * limitAcceleration().y - Math.sin((rotation - 90) * Math.PI / 180) * getCentForce().y;
        }
        this.position = this.position.plus(new Vector2D(newX, newY));
        this.centForce = this.centForce.times(0.9);

      } else if (rotatingLeft && !this.noGrip) {
        this.acceleration = this.acceleration.times(0.9);
        if (this.limitAcceleration().x > 7 || this.limitAcceleration().y > 7) {
            newX = Math.cos(this.getRotation() * Math.PI / 180) * limitAcceleration().x + Math.cos((rotation+90) * Math.PI / 180) * getCentForce().x;
            newY = Math.sin(this.getRotation() * Math.PI / 180) * limitAcceleration().y - Math.sin((rotation+90) * Math.PI / 180) * getCentForce().y;
        }
        this.position = this.position.plus(new Vector2D(newX, newY));
        this.centForce = this.centForce.times(0.9);

      } else {

      }

      rotateRight();
      rotateLeft();
      slowDown();

    this.rotation = getRotation();
    wrapEdges();
  }


}

