package utils;

import java.math.*;

public class Vector2D {

    public double x;
    public double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double length() {
        return Math.sqrt(x*x + y*y);
    }

    public Vector2D plus(Vector2D other){
        return new Vector2D(x+other.x, y+other.y);
    }

    public Vector2D minus(Vector2D other) {
        return new Vector2D(x-other.x, y-other.y);
    }

    public Vector2D times(double d) {
        return new Vector2D(x*d, y*d);
    }

    public Vector2D divide(double d) {
        return this.times(1/d);
    }

    public Vector2D limit(double i) {
        return new Vector2D(this.x*i/this.length(), this.y*i/this.length());
    }

    public String toString() {
        return String.format("X = %f, Y = %f", this.x, this.y);
    }

}
