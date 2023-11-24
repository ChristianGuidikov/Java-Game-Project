package com.example.firstjavaproject;

import com.example.firstjavaproject.Player;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public class DrawManager {

    private Pane group = new Pane ();
    private ObservableList<Object> elements = FXCollections.observableArrayList();

    public DrawManager() {
        elements.addListener(new ListChangeListener<Object>() {

            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Object> c) {
                while (c.next()) {
                    for (Object remitem : c.getRemoved()) {
                        Shape shape = remitem.getShape();

                        group.getChildren().remove(shape);
                    }
                    for (Object additem : c.getAddedSubList()) {
                        Shape shape = additem.getShape();

                        group.getChildren().add(shape);

                        shape.relocate(additem.getPosition().x, additem.getPosition().y);
                    }
                }
            }
        });
    }

    public ObservableList<Object> getElements() {
        return elements;
    }

    public Pane getPane() {
        return group;
    }
}