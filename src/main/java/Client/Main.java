package main.java.Client;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class Main extends Application {

    public Animal[][] map = new Animal[20][20];
    public ImageView[][] imageMap = new ImageView[20][20];
    public Client client;
    DrawMap dm;

    public void initMap()
    {
        for(int i = 0; i < 20; i++)
            for(int j = 0; j < 20; j++)
            {
                if(Math.random() >= 0.3)
                    map[i][j] = new Animal();
                else if(Math.random() >= 0.2)
                    map[i][j] = new Rabbit();
                else if(Math.random() >= 0.4)
                    map[i][j] = new FWolf();
                else
                    map[i][j] = new MWolf();


                imageMap[i][j] = new ImageView(new Image("/main/resources/place.png"));
            }
    }

    @Override
    public void start(final Stage pS)
    {
        initMap();
        client = new Client(map);
        Group root = new Group();

        for(int i = 0; i < 20; i++)
            for(int j = 0; j < 20; j++)
            {
                imageMap[i][j].setFitHeight(32);
                imageMap[i][j].setFitWidth(32);
                imageMap[i][j].setLayoutX(i * 32);
                imageMap[i][j].setLayoutY(j * 32);
                root.getChildren().add(imageMap[i][j]);
            }

        Scene scene = new Scene(root);
        pS.setScene(scene);

        dm = new DrawMap(map, imageMap);

        pS.setHeight(679);
        pS.setWidth(660);
        pS.setResizable(false);
        pS.setTitle("Simulate");
        pS.show();
    }

    public static void main(String[] args) {
        launch();
    }
}