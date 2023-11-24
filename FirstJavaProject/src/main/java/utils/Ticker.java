package utils;

// JavaFX AnimationTimer is extended directly.
// Trying to extend ScalaFX AnimationTimer gives error about needing JavaFX AnimationTimer as a delegete.
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import java.util.concurrent.Callable;
import java.util.function.Consumer;


//This class calls function given as a parameter repeatedly.
public class Ticker extends AnimationTimer{
    public Runnable function;

    public Ticker(Runnable function) {
        this.function = function;
    }

    @Override
    public void handle(long now) {
        function.run();
    }

}