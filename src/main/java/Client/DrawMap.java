package main.java.Client;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DrawMap extends Thread
{
    DrawMap(Animal[][] map, ImageView[][] image_Map)
    {
        this.map = map;
        this.image_Map = image_Map;
        this.updateMap();

        start();
    }

    private void allowMove()
    {
        for(int i = 0; i <size_map; i++)
            for(int j = 0; j < size_map; j++)
                map[i][j].noMove();
    }

    private void updateMap()
    {
        synchronized (this.image_Map)
        {
            for (int i = 0; i < size_map; i++)
                for (int j = 0; j < size_map; j++) {
                    switch (map[i][j].getType()) {
                        case 'A' -> image_Map[i][j].setImage(new Image("/main/resources/place.png"));
                        case 'R' -> image_Map[i][j].setImage(new Image("/main/resources/rabbit.png"));
                        case 'F' -> image_Map[i][j].setImage(new Image("/main/resources/wolfF.png"));
                        case 'M' -> image_Map[i][j].setImage(new Image("/main/resources/wolfM.png"));
                    }
                }
        }
    }

    private void updateRabbit()
    {
        synchronized (this.map)
        {
            for (int i = 0; i < size_map; i++)
                for (int j = 0; j < size_map; j++)
                    if (map[i][j].getType() == 'R' && !map[i][j].isMove()) {
                        int[] tmp = map[i][j].move(i, j, map);
                        map[i][j].yesMove();
                        if (tmp[0] != i || tmp[1] != j) {
                            map[tmp[0]][tmp[1]] = map[i][j];

                            if (Math.random() <= 0.2)
                                map[i][j] = new Rabbit();
                            else
                                map[i][j] = new Animal();
                        }

                    }
        }
    }

    public void updateWolfF()
    {
        synchronized (this.map) {
            for (int i = 0; i < size_map; i++)
                for (int j = 0; j < size_map; j++)
                    if (map[i][j].getType() == 'F' && !map[i][j].isMove()) {
                        if (!map[i][j].isDeath()) {
                            int[] tmp = map[i][j].move(i, j, map);
                            map[i][j].yesMove();

                            if (tmp[0] != i || tmp[1] != j) {
                                if (this.map[tmp[0]][tmp[1]].getType() == 'R')
                                    this.map[i][j].eat();


                                map[tmp[0]][tmp[1]] = map[i][j];
                                map[i][j] = new Animal();
                            }
                        } else
                            map[i][j] = new Animal();

                    }
        }
    }

    private void updateWolfM()
    {
        synchronized (this.map) {
            for (int i = 0; i < this.size_map; i++)
                for (int j = 0; j < this.size_map; j++)
                    if (this.map[i][j].getType() == 'M' && !this.map[i][j].isMove()) {
                        if (!this.map[i][j].isDeath()) {
                            int[] tmp = this.map[i][j].move(i, j, this.map);
                            this.map[i][j].yesMove();

                            if (tmp[0] != i || tmp[1] != j) {
                                if (this.map[tmp[0]][tmp[1]].getType() != 'F') {
                                    if (this.map[tmp[0]][tmp[1]].getType() == 'R')
                                        this.map[i][j].eat();

                                    this.map[tmp[0]][tmp[1]] = this.map[i][j];
                                    this.map[i][j] = new Animal();
                                } else {
                                    if (i == 0) {
                                        if (Math.random() > 0.5)
                                            this.map[i + 1][j] = new FWolf();

                                        else
                                            this.map[i + 1][j] = new MWolf();
                                    } else {
                                        if (Math.random() > 0.5)
                                            this.map[i - 1][j] = new FWolf();
                                        else
                                            this.map[i - 1][j] = new MWolf();

                                    }
                                }
                            }
                        } else
                            this.map[i][j] = new Animal();

                    }
        }
    }

    @Override
    public void run()
    {
        while(true)
        {
            updateRabbit();
            updateMap();
            try {
                sleep(800);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            updateWolfF();
            updateMap();
            try {
                sleep(800);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            updateWolfM();
            updateMap();
            try {
                sleep(800);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            allowMove();

        }

    }

    private final int size_map = 20;
    private final Animal[][] map;
    private final ImageView[][] image_Map;

}
