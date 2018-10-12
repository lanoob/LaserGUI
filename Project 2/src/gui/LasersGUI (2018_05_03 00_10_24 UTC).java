package gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;

import model.*;
import ptui.LasersPTUI;

/**
 * The main class that implements the JavaFX UI.   This class represents
 * the view/controller portion of the UI.  It is connected to the model
 * and receives updates from it.
 *
 * @author Sean Strout @ RIT CS
 * @author
 */



public class LasersGUI extends Application implements Observer {

    GridPane grid = new GridPane();
    BorderPane border = new BorderPane();

    /** The UI's connection to the model */
    private LasersModel model;

    /** this can be removed - it is used to demonstrates the button toggle */
    private static boolean status = true;

    @Override
    public void init() throws Exception {
        // the init method is run before start.  the file name is extracted
        // here and then the model is created.
        try {
            Parameters params = getParameters();
            String filename = params.getRaw().get(0);
            this.model = new LasersModel(filename);
        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe.getMessage());
            System.exit(-1);
        }
        this.model.addObserver(this);
    }

    /**
     * A private utility function for setting the background of a button to
     * an image in the resources subdirectory.
     *
     * @param button the button control
     * @param bgImgName the name of the image file
     */
    private void setButtonBackground(Button button, String bgImgName) {
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image( getClass().getResource("resources/" + bgImgName).toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        button.setBackground(background);
    }

    /**
     * This is a private demo method that shows how to create a button
     * and attach a foreground image with a background image that
     * toggles from yellow to red each time it is pressed.
     *
     * @param stage the stage to add components into
     */
    private void buttonDemo(Stage stage) {
        // this demonstrates how to create a button and attach a foreground and
        // background image to it.
        Button button = new Button();
        Image laserImg = new Image(getClass().getResourceAsStream("resources/laser.png"));
        ImageView laserIcon = new ImageView(laserImg);
        button.setGraphic(laserIcon);
        setButtonBackground(button, "yellow.png");
        button.setOnAction(e -> {
            // toggles background between yellow and red
            if (!status) {
                setButtonBackground(button, "yellow.png");
            } else {
                setButtonBackground(button, "red.png");
            }
            status = !status;
        });

        Scene scene = new Scene(button);
        stage.setScene(scene);
    }

    /**
     * The
     * @param stage the stage to add UI components into
     */
    private void init(Stage stage) {
        // TODO
        buttonDemo(stage);  // this can be removed/altered
    }

    public GridPane makeGrid(){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        for(int x = 0; x< this.model.getRows(); x++){
            for(int y=0; y< this.model.getColumns(); y++){
                Button button = new Button();
                Rectangle rectangle = new Rectangle(25,25);
                rectangle.setFill(Color.WHITE);
                button.setShape(rectangle);
                button.setMinSize(50,50);
                grid.add(button,x,y);
            }
        }
        grid.setGridLinesVisible(true);
        return grid;
    }



    @Override
    public void start(Stage primaryStage) throws Exception {

        init(primaryStage);  // do all your UI initialization here

        primaryStage.setTitle("Lasers");
        primaryStage.setResizable(false);

        //top
        Label message = new Label("Change Message");
        border.setTop(message);

        //Center
        border.setCenter(this.makeGrid());

        //Botton
        Button reset = new Button("Reset");
        reset.setOnAction(event -> model.reset());

        Button load = new Button("Load");
        load.setOnAction(event -> model);

        Button check = new Button("Check");

        Button hint = new Button("Hint");

        Button solve = new Button("Solve");
        //solve.setOnAction(event -> this.model.bac);



        Scene scene = new Scene(border, this.model.getColumns() * 75, this.model.getColumns() * 75);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.show();
    }

    @Override
    public void update(Observable o, Object arg) {
        // TODO
    }
}
