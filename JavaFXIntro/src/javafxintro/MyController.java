package javafxintro;

/*
 * Copyright (c) Visma Software
 * 
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author cristian.gherghinesc
 */
public class MyController implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void okAction(ActionEvent event) {
        System.out.println("Ok");
        printTexts();
    }

    public void cancelAction(ActionEvent event) {
        System.out.println("Cancel");
        printTexts();
    }

    private void printTexts() {
        System.out.println("U: " + username.getText() + " P: " + password.getText());
    }

}
