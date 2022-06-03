package main.java.Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client extends Thread
{
    Client(Animal[][] map)
    {
        this.map = map;

        try
        {
            client = new Socket("localhost", 4004);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        System.out.println("Client start!");
        start();
    }

    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                String word = this.in.readLine();
                System.out.println(word);

                switch (word)
                {
                    case "getState" -> reQuestState("STATE");
                    case "getStateUpp" -> reQuestState("STATEUPP");
                    case "close" -> System.exit(0);
                }

            }
            catch(Exception e){
                if(e.getMessage().equals("Connection reset") || e.getMessage().equals("Cannot invoke \"java.io.BufferedReader.readLine()\" because \"this.in\" is null"))
                {
                    System.out.println("Server is close");
                    break;
                }
                else System.out.println(e.getMessage());
            }
        }
    }

    private void reQuestState(String state)
    {
        synchronized (this.map)
        {
            int c_wf = 0, c_wm = 0, c_r = 0;
            float h_wf = 0, h_wm = 0;

            for(int i = 0; i < 20; i++)
                for(int j = 0; j < 20; j++)
                    switch (this.map[i][j].getType())
                    {
                        case 'F' -> {
                            c_wf++;
                            h_wf += this.map[i][j].currentHungry();
                        }

                        case 'M' -> {
                            c_wm++;
                            h_wm += this.map[i][j].currentHungry();
                        }

                        case 'R' -> c_r++;
                    }

            if(c_wf != 0)
                h_wf /= c_wf;
            else h_wf = -1;

            if(c_wm != 0)
                h_wm /= c_wm;
            else h_wm = -1;

            writeToServer(state + "r"+ c_r + "f" + c_wf + "m" + c_wm +"F" + String.format("%.1f", h_wf) + "M" + String.format("%.1f",h_wm));
        }
    }

    private void writeToServer(String word)
    {
        try
        {
            this.out.write(word + "\n");
            this.out.flush();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private final Animal[][] map;
    private Socket client;
    private BufferedReader in;
    private BufferedWriter out;
}
