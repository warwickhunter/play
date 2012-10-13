package fxmlexample;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

/**
 * Java FX FXML tutorial
 * 
 * http://docs.oracle.com/javafx/2/get_started/fxml_tutorial.htm 
 * @author Warwick Hunter
 * @since  2012-10-13
 */
public class FXMLExampleController implements Initializable {
    
    @FXML 
    private Text actiontarget;
    
    @FXML 
    protected void handleSubmitButtonAction(ActionEvent event) {
        actiontarget.setText("Sign in button pressed");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
