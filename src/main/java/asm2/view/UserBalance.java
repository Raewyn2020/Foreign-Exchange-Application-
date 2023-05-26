package asm2.view;

import asm2.model.fiat;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;
/**
 * This is the initial scene once the app is running, firstly a Text Input Dialog will be displayed
 * asking the user to set a balance between 10 and 1000.
 * If the user's input meets the requirements the balance will be stored in the model and
 * an Information Dialog will be displayed telling the user that the balance has been successfully set.
 * If the user's input does not meet the requirements an Error Dialog will be displayed.
 */
public class UserBalance {
    /**
     * Add a model as a parameter in order to be able to store the balance.
     */
    private fiat model;


    /**
     * Set the model used by this view.
     * @param model The model used by this view - used for storing the balance.
     */
    public void setModel(fiat model){
        this.model = model;
    }


    /**
     * Get the model used by this view.
     * @return  return the model used by this view.
     */
    public fiat getModel() {
        return model;
    }

    /**
     * A Text Input Dialog will be displayed for the user to set the balance. If the balance entered meets the requirements,
     * the balance will be stored in the model, if not, an Error Dialog will be displayed.
     */
    public void askForTheBalance(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Please enter a balance between 10 and 1000");
        dialog.setHeaderText("Please enter a balance between 10 and 1000");
        dialog.setContentText("Please enter the balance:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            if(!result.get().equals("")) {
                if (model.ifInteger(result.get())) {
                    if (model.ifAcceptableNum(result.get())) {
                        model.setBalance(Float.valueOf(result.get().toString()));
                        model.setHasBalance(true);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("You have successfully set up your balance");
                        alert.setHeaderText("You have successfully set up your balance");
                        alert.setContentText("You have successfully set up " +
                                "your balance!\nThe balance you set is "+result.get());

                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Number Error");
                        alert.setHeaderText("Number Error");
                        alert.setContentText("Ooops, the entered balance is " +
                                "not between 10 and 1000!");
                        alert.showAndWait();
                        this.askForTheBalance();

                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Number Error");
                    alert.setHeaderText("Number Error");
                    alert.setContentText("Ooops, the entered balance is not a " +
                            "integer number!");
                    alert.showAndWait();
                    this.askForTheBalance();
                }
            }
            else if(result.get().equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Empty Input Error");
                alert.setHeaderText("Empty Input Error");
                alert.setContentText("Ooops, the balance is not filled!");
                alert.showAndWait();
                this.askForTheBalance();
            }
        }else{
            dialog.close();
        }
    }
}
