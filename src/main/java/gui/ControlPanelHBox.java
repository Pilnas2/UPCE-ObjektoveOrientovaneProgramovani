/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 *
 * @author marti
 */
class ControlPanelHBox extends HBox{
    public ControlPanelHBox () {
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
        getChildren().add(button);
    }
            
    public void addComboBox (String promptText, Enum[] seznam, EventHandler action) {
        ComboBox comboBox = new ComboBox();
        comboBox.getItems().add(null);
        comboBox.getItems().addAll(FXCollections.observableArrayList(Arrays.asList(seznam)));
        comboBox.setPromptText(promptText);
        comboBox.setOnAction(action);
        getChildren().add(comboBox);
    }
}
