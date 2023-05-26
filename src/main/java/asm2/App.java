package asm2;

import asm2.view.startWindow;
import javafx.application.Application;
import javafx.stage.Stage;
@SuppressWarnings("unchecked")
public class App extends Application {

    public startWindow thewindow =new startWindow();
    //        request.getAllLatestRequest("cfcf9fae4e2e936961584bd28099af7a","CNY");
    //        request.output("7VmIfqhCcy9VQE_7SRuxIVb4LzKHahjW","test");
    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println(System.getenv("PASTEBIN_API_KEY"));
        System.out.println(System.getenv("INPUT_API_KEY"));
        thewindow.balance();
        thewindow.thebegin();
        thewindow.playMusic();
    }
}
