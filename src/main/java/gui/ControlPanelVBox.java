/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 *
 * @author marti
 */
class ControlPanelVBox extends VBox{
    public ControlPanelVBox () {
        setSpacing(10);
        setPadding(new Insets(10));
    }
    
    public void addLabel (String text) {
        Label label = new Label(text);
        getChildren().add(label);
    }
    
    public void addButton (String text, EventHandler<ActionEvent> action) {
        Button button = new Button(text);
        button.setOnAction(action);
        button.setPrefWidth(100);
        getChildren().add(button);
    }
}
