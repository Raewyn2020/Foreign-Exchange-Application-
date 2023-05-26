package asm2.view;
import asm2.model.fiat;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.controlsfx.control.WorldMapView;
import java.util.*;

@SuppressWarnings("unchecked")
public class startWindow {
    private fiat model =new fiat();

    /**
     * Add userBalance as a parameter in order to load the initial scene.
     */
    private UserBalance userBalance = new UserBalance();

    private final MenuBar menuBar = new MenuBar();
    private Stage beginall =new Stage();
    private Stage choseMap = new Stage();
    private final music m = new music();
    class XCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Delete");

        public XCell() {
            super();
            hbox.getChildren().addAll(label, pane, button);
            HBox.setHgrow(pane, Priority.ALWAYS);
            button.setOnAction(event -> {
                model.Del(getItem());
                thebegin();
            });
        }
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            setGraphic(null);
            if (item != null && !empty) {
                label.setText(item);
                setGraphic(hbox);
            }
        }
    }

    /**
     * Load the initial scene, and give the balance set by the user to the
     * model used here.
     */
    public void balance(){
        userBalance.setModel(model);
        userBalance.askForTheBalance();
        model = userBalance.getModel();
    }

    public void playMusic(){
        if(!model.isHasBalance()){
            return;
        }
        m.playMusic();
        model.setIsPlaying(true);
    }
    public Stage SpinningProgressIndicator(){
        Stage stage = new Stage();
        Group root = new Group();
        Scene scene = new Scene(root, 100, 80);
        stage.setScene(scene);

        ProgressIndicator p1 = new ProgressIndicator();
        Label label = new Label("Loading...");
        VBox g =new VBox(label,p1);
        scene.setRoot(g);
        return stage;
    }
    public void thebegin() {
        if(!model.isHasBalance()){
            return;
        }
        TextArea textBalance= new TextArea();
        textBalance.setEditable(false);
        textBalance.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        textBalance.setPrefSize(800,10);
        textBalance.setPrefHeight(10);
        textBalance.setText("Balance: "+ model.getBalance());
        Label label1 = new Label("Information about the selected currencies");
        label1.setFont(Font.font("Arial", FontWeight.NORMAL, 12));

        Label label2 = new Label("selected countries");
        label2.setFont(Font.font("Arial", FontWeight.NORMAL, 12));

        Button button1 = new Button();
        button1.setLayoutX(100);
        button1.setLayoutY(100);

        button1.setText("add currency");
        button1.setOnAction((event) -> {
            WorldMapView map =new WorldMapView();
            BorderPane borderPane = new BorderPane();
            Scene scene =new Scene(borderPane,1040 ,800);
            WorldMapView smallMapView = new WorldMapView();
            smallMapView.setPrefSize(200, 120);
            smallMapView.getSelectedCountries().addListener(new ListChangeListener<WorldMapView.Country>() {
                @Override
                public void onChanged(Change<? extends WorldMapView.Country> c) {
                    if(c.next()){
                        if(c.getAddedSubList().size()>0) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information Dialog");
                            alert.setHeaderText(null);
                            alert.setContentText(c.getAddedSubList().get(0).getLocale().getDisplayCountry(Locale.ENGLISH));
                            alert.showAndWait();
                            c.getAddedSubList().clear();
                        }
                    }
                }
            });
            map.getSelectedCountries().addListener(new ListChangeListener<WorldMapView.Country>() {
                @Override
                public void onChanged(Change<? extends WorldMapView.Country> c) {
                    if(c.next()){

                        if(c.getAddedSubList().size()>0) {
                            smallMapView.getCountries().setAll(c.getAddedSubList().get(0));
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation Dialog");
                            alert.setHeaderText(c.getAddedSubList().get(0).getLocale().getDisplayCountry(Locale.ENGLISH));
                            alert.setContentText("Are you sure you want to add this " +
                                    "country？");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                // ... user chose OK

                                if(!model.IfDataContainsCountry(c.getAddedSubList().get(0).getLocale().getDisplayCountry(Locale.ENGLISH))) {
                                    Stage stage = SpinningProgressIndicator();
                                    stage.show();
                                    Thread t = new Thread(() -> {
                                        model.SaveInfoInListView(c.getAddedSubList().get(0).getLocale().getDisplayCountry(Locale.ENGLISH));
                                        //Here write all actions that you want execute on background
                                        Platform.runLater(() -> {
                                            stage.close();
                                            choseMap.close();
                                            thebegin();
                                            //Here the action where is finished the actions on background
                                        });
                                    });
                                    t.start();
// need thread!
                                }else{
                                    Alert alertadd =
                                            new Alert(Alert.AlertType.CONFIRMATION);
                                    alertadd.setTitle("Confirmation Dialog " +
                                            "with Custom Actions");
                                    alertadd.setHeaderText("You have already " +
                                            "added this country.");
                                    alertadd.setContentText("Choose your " +
                                            "option.");

                                    ButtonType buttonTypeOne = new ButtonType("Return to the home page");
                                    ButtonType buttonTypeTwo =
                                            new ButtonType("Select another " +
                                                    "country");

                                    alertadd.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

                                    Optional<ButtonType> resultadd =
                                            alertadd.showAndWait();
                                    if (resultadd.get() == buttonTypeOne){
                                        choseMap.close();
//                                        thebegin();
                                        // ... user chose "One"
                                    } else{
//                                        c.getAddedSubList().clear();
                                        return;
                                    }
                                        // ... user chose "Two"
                                }
                            } else {
                                // ... user chose CANCEL or closed the dialog
                            }
                        }
                    }
                }
            });
            List<WorldMapView.Country> countries = new ArrayList<>();
            Collections.addAll(countries, WorldMapView.Country.values());
            Collections.sort(countries, (c1, c2) -> c1.getLocale().getDisplayCountry().compareTo(c2.getLocale().getDisplayCountry()));
            ListView<WorldMapView.Country> listView = new ListView<>();
            listView.getItems().setAll(countries);
            listView.setCellFactory(list -> new ListCell<WorldMapView.Country>() {
                @Override
                protected void updateItem(WorldMapView.Country item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(item.getLocale().getDisplayCountry(Locale.ENGLISH));
                    }
                }
            });
//        WorldMapView smallMapView = new WorldMapView();
            map.setPrefSize(780, 720);
            smallMapView.setPrefSize(220, 200);
//        listView.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);
            listView.getSelectionModel().getSelectedItems().addListener((Observable it) -> {
                smallMapView.getCountries().setAll(listView.getSelectionModel().getSelectedItems());
                map.getSelectedCountries().clear();
                if(listView.getSelectionModel().getSelectedItems().size()>0) {
                    map.getSelectedCountries().add(listView.getSelectionModel().getSelectedItems().get(0));
                }
            });

            Button clearButton = new Button("Clear / Show World");
            clearButton.setMaxWidth(Double.MAX_VALUE);
            clearButton.setOnAction((event4) -> {
                if(listView.getSelectionModel().getSelectedItems().size()>=1) {
                    listView.getSelectionModel().clearSelection();

                }
                map.getSelectedCountries().clear();
                smallMapView.getCountries().setAll(map.getCountries());
            });
            Button back = new Button();
            back.setText("back to the home page");
            back.setOnAction((event6) -> {
                choseMap.close();
                this.thebegin();
            });

            Slider zoomSlider = new Slider();
            zoomSlider.setMin(1);
            zoomSlider.setMax(10);
            Bindings.bindBidirectional(zoomSlider.valueProperty(),
                    map.zoomFactorProperty());
            ComboBox<WorldMapView.SelectionMode> selectionModeComboBox = new ComboBox<>();
            selectionModeComboBox.setTooltip(new Tooltip("Selection Mode (Countries & Locations)"));
            selectionModeComboBox.getItems().addAll(WorldMapView.SelectionMode.values());
            Bindings.bindBidirectional(selectionModeComboBox.valueProperty(),
                    map.countrySelectionModeProperty());
            Bindings.bindBidirectional(selectionModeComboBox.valueProperty(),
                    map.locationSelectionModeProperty());
            VBox optionsBox = new VBox(10);
            optionsBox.getChildren().addAll(map, zoomSlider);
            VBox right  = new VBox(10);
            right.getChildren().addAll(listView,smallMapView,back);
            BorderPane.setMargin(clearButton, new Insets(0, 0, 10, 0));
            BorderPane.setMargin(optionsBox, new Insets(10, 0, 10, 0));
            borderPane.setTop(clearButton);
            borderPane.setRight(right);
            borderPane.setLeft(optionsBox);
            choseMap.setTitle("JavaFX and Google Maps");
            choseMap.setScene(scene);
            choseMap.show();

        });

        ListView<String> listView = new ListView<>();
        listView.setPrefSize(650, 530);
        listView.setCellFactory(param -> new XCell());
        listView.getItems().setAll(model.getAllInfo());
        listView.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> ov, String old_val,
                 String new_val) -> {
                    System.out.println(new_val);
                });
        ListView<String> listView2 = new ListView<>();
        listView2.setPrefSize(250, 530);

        listView2.setCellFactory(param -> new XCell());
        listView2.getItems().setAll(model.getThedata());
        listView2.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> ov, String old_val,
                 String new_val) -> {
                    System.out.println(new_val);
                });

        Button button3 = new Button();
        button3.setText("Convert two currencies");
        button3.setOnAction((event0) -> {
            // Create a new stage and show it
            Stage stage3 = new Stage();
            stage3.setTitle("Convert two currencies");
            Scene scene1 = new Scene(new Group(), 700, 700);
            ComboBox baseComboBox = new ComboBox();
            baseComboBox.getItems().addAll(model.getCurrData());
            baseComboBox.setPromptText("Base currency (From)");
            TextArea textbase = new TextArea();
            TextArea textsymbol = new TextArea();
            baseComboBox.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue ov, String t, String t1) {
                    textbase.setText(t1);
                }
            });
            ComboBox symbolComboBox = new ComboBox();
            symbolComboBox.getItems().addAll(model.getCurrData());
            symbolComboBox.setPromptText("Symbol currency (To)");
//            symbolComboBox.setEditable(true);
            symbolComboBox.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue ov, String t, String t1) {
                    textsymbol.setText(t1);
                }
            });
            TextArea text1 = new TextArea();
            text1.setEditable(false);
            text1.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            text1.setMaxSize(800,800);
            text1.setPrefSize(500,400);
            TextArea textArea0 = new TextArea();
            textArea0.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            textArea0.setMaxSize(300,20);
            textArea0.setText("0");
            textArea0.setEditable(true);
            TextArea text3 = new TextArea();
            text3.setEditable(false);
            text3.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            text3.setMaxSize(800,100);
            Button button2 = new Button ("Convert");
            button2.setOnAction((event2) -> {
                if(textbase.getText()!=null && textsymbol.getText()!= null&&!textbase.getText().equals("") &&!textsymbol.getText().equals("") && textArea0.getText()!=null&&  !textArea0.getText().equals("") && model.IfDigital(textArea0.getText())) {
                    if(model.ifOutOfMoney(textArea0.getText())){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Out of money");
                        alert.setHeaderText("Out of money");
                        alert.setContentText("Out of money");
                        alert.showAndWait();

                    }else if(model.IfCache(textbase.getText(),
                            textsymbol.getText())){
                        Alert alertc = new Alert(Alert.AlertType.CONFIRMATION);
                        alertc.setTitle("cache hit for this data");
                        alertc.setHeaderText("cache hit for this data – use " +
                                "cache, or request fresh data from the API?");
                        alertc.setContentText("Choose your option.");

                        ButtonType buttonTypeOne = new ButtonType("Use cache");
                        ButtonType buttonTypeTwo = new ButtonType("request fresh data from the API");
                        alertc.getButtonTypes().setAll(buttonTypeOne,
                                buttonTypeTwo);

                        Optional<ButtonType> result = alertc.showAndWait();
                        if (result.get() == buttonTypeOne){
                            Stage stage2 = SpinningProgressIndicator();
                            stage2.show();
//need threads!
                            model.updateBalance(textArea0.getText());
                            textBalance.setText("Balance: "+ model.getBalance());
                            Thread t3 = new Thread(() -> {
                                //Here write all actions that you want execute on background
                                String cacheInfo =
                                        model.UseCache(textbase.getText(),
                                                textsymbol.getText(),textArea0.getText());
                                Platform.runLater(() -> {
                                    stage2.close();
                                    text1.setText(cacheInfo);
                                    text1.setEditable(false);
                                    //Here the action where is finished the actions on background
                                });
                            });
                            t3.start();
                        } else if (result.get() == buttonTypeTwo) {
                            Stage stage4 = SpinningProgressIndicator();
                            stage4.show();
                            model.updateBalance(textArea0.getText());
                            textBalance.setText("Balance: "+ model.getBalance());
                            Thread t3 = new Thread(() -> {
                                //Here write all actions that you want execute on background
                                String writeInfo =
                                        model.WriteReport(textbase.getText(),
                                                textsymbol.getText(),textArea0.getText());
                                Platform.runLater(() -> {
                                    stage4.close();
                                    text1.setText(writeInfo);
                                    text1.setEditable(false);
                                    //Here the action where is finished the actions on background
                                });
                            });
                            t3.start();
//need threads!
                        } else {
                            // ... user chose CANCEL or closed the dialog
                        }
                    }else{
                        Stage stage5 = SpinningProgressIndicator();
                        stage5.show();
                        model.updateBalance(textArea0.getText());
                        textBalance.setText("Balance: "+ model.getBalance());
                        Thread t3 = new Thread(() -> {
                            //Here write all actions that you want execute on background
                            String writeInfo =
                                    model.WriteReport(textbase.getText(),
                                            textsymbol.getText(),textArea0.getText());
                            Platform.runLater(() -> {
                                stage5.close();
                                text1.setText(writeInfo);
                                text1.setEditable(false);
                                //Here the action where is finished the actions on background
                            });
                        });
                        t3.start();
//need threads!
                    }

                }else if(!model.IfDigital(textArea0.getText())){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Convert Error");
                    alert.setHeaderText("Convert Error");
                    alert.setContentText("Ooops, the amount is not a number!");
                    alert.showAndWait();
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Convert Error");
                    alert.setHeaderText("Convert Error");
                    alert.setContentText("Ooops, some necessary parameters " +
                            "are not filled!");
                    alert.showAndWait();
                }
            });
            Button button4 = new Button ("create output report");
            button4.setOnAction((event2) -> {
                if(text1.getText()!=null && !text1.getText().equals("")) {
//thread!
                    Stage stage6 = SpinningProgressIndicator();
                    stage6.show();
                    Thread t2 = new Thread(() -> {
                        //Here write all actions that you want execute on background
                        String info = model.outPut(text1.getText());
                        Platform.runLater(() -> {
                            stage6.close();
                            if(model.IfPastebinLink(info)){
                                text3.setText("Report successfully created!\nCopy " +
                                        "this" +
                                        " link and open it from your browser to see " +
                                        "the report:\n\n"+info);
                                text3.setEditable(false);
                            }else{
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Create Report Error");
                                alert.setHeaderText("Create Report Error");
                                alert.setContentText("create report failed for this " +
                                        "reason: "+info);
                                alert.showAndWait();
                                text3.setText("create report failed for this " +
                                        "reason: "+info);
                                text3.setEditable(false);
                            }
                            //Here the action where is finished the actions on background
                        });
                    });
                    t2.start();
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Create Report Error");
                    alert.setHeaderText("Create Report Error");
                    alert.setContentText("Ooops, the report is empty!");
                    alert.showAndWait();
                }
            });

            GridPane grid = new GridPane();
            grid.setVgap(4);
            grid.setHgap(10);
            grid.setPadding(new Insets(7, 7, 7, 7));
            grid.add(new Label("Base (From): "), 0, 0);
            grid.add(baseComboBox, 1, 0);
            grid.add(new Label("Symbol (To): "), 2, 0);
            grid.add(symbolComboBox, 3, 0);
            grid.add(new Label("Amount: "), 0, 1);
            grid.add(textArea0, 1, 1, 3, 1);
            grid.add(text1, 0, 2, 7, 2);
            grid.add(text3, 0, 4, 7, 3);
            grid.add(button2, 0, 7);
            grid.add(button4, 1, 7);
            Button buttonback =new Button();
            buttonback.setText("back");
            buttonback.setOnAction((event2) -> {
                stage3.close();
            });
            Button clear1 =new Button();
            clear1.setText("Clear cache");
            clear1.setOnAction((eventc) -> {
                model.clearCache();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Clear cache successfully");
                alert.setHeaderText("Clear cache successfully!");
                alert.showAndWait();
            });
            grid.add (buttonback, 2, 7, 3, 1);
            grid.add (clear1, 3, 7, 3, 1);
            Group root = (Group)scene1.getRoot();
            root.getChildren().add(grid);
            stage3.setScene(scene1);
            stage3.showAndWait();
        });
        Button buttonmusic =new Button();
        buttonmusic.setText("stop the music");
        buttonmusic.setOnAction((eventm) -> {
            if(model.getIsPlaying()){
                m.stopMusic();
                model.setIsPlaying(false);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Background music has been stopped");
                alert.setHeaderText("Background music has been stopped");
                alert.setContentText("Background music has been successfully " +
                        "stopped.");
                alert.showAndWait();
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Background music has already been stopped");
                alert.setHeaderText("Background music has already been stopped");
                alert.setContentText("The background music has already " +
                        "stopped playing.");
                alert.showAndWait();
            }
        });
        Button buttoncon =new Button();
        buttoncon.setText("continue the music");
        buttoncon.setOnAction((eventc) -> {
            if(!model.getIsPlaying()){
                this.playMusic();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Background music is playing");
                alert.setHeaderText("Background music is playing");
                alert.setContentText("The background music is:\n" +
                        "Bet On Me (Walk Off the Earth & D Smoke)\n" +
                        "Hope you like it!");

                alert.showAndWait();
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Background music is already playing");
                alert.setHeaderText("Background music is already playing");
                alert.setContentText("The background music is:\n" +
                        "Bet On Me (Walk Off the Earth & D Smoke)\n" +
                        "Hope you like it!");
                alert.showAndWait();
            }
        });
        Button clear =new Button();
        clear.setText("Clear the list");
        clear.setOnAction((eventc) -> {
            model.clearAll();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Clear the list successfully");
            alert.setHeaderText("Clear the list successfully!");
            alert.showAndWait();
            thebegin();
        });
        Button clearcache =new Button();
        clearcache.setText("Clear cache");
        clearcache.setOnAction((eventc) -> {
            model.clearCache();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Clear cache successfully");
            alert.setHeaderText("Clear cache successfully!");
            alert.showAndWait();
        });
        Menu actionMenu = new Menu("Menu");
        MenuItem aboutItm = new MenuItem("about");
        aboutItm.setOnAction((event)-> {
            Stage stageabout = new Stage();
            stageabout.setTitle("About");

            Scene sceneabout = new Scene(new Group(), 640, 420);
            TextArea textnew = new TextArea();
            textnew.setText("About");
            textnew.setEditable(false);

            textnew.setFont(Font.font("Arial", FontWeight.BOLD, 24));
            textnew.setPrefSize(540,10);
            TextArea textnew2 = new TextArea();
            textnew2.setText(model.getAbout());
            textnew2.setEditable(false);

            textnew2.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            textnew2.setPrefSize(590,340);
            VBox vBox = new VBox(textnew,textnew2);
            Scene sceneabou = new Scene(vBox ,640,420);
            stageabout.setScene(sceneabou);
            stageabout.showAndWait();

        });
        actionMenu.getItems().add(aboutItm);
        if(menuBar.getMenus().size()<1) {
            menuBar.getMenus().add(actionMenu);
        }
        Pane spacer = new Pane();
        Pane spacer2 = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMinSize(10, 1);
        HBox hBox = new HBox(button3,button1,clear,clearcache,spacer,
                buttonmusic,
                buttoncon);
        VBox curinfo = new VBox(label1,listView);
        VBox cuninfo = new VBox(label2,listView2);
        HBox hb = new HBox(curinfo,cuninfo);
        VBox vBox = new VBox(menuBar,spacer2,textBalance,hb,hBox);
        Scene scene = new Scene(vBox,1000,900);
        beginall.setTitle("App");
        beginall.setScene(scene);
        beginall.show();
    }
}
