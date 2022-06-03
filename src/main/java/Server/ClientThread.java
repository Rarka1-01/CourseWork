package main.java.Server;

import java.io.*;
import java.net.Socket;
import static main.java.Server.Main.clientList;
import static main.java.Server.Main.db;

public class ClientThread extends Thread
{
    public Socket client;
    private BufferedReader in;
    private BufferedWriter out;

    ClientThread(Socket client)
    {
        try
        {
            this.client = client;
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        this.writeToClient("getState");
        start();
    }

    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                String word = in.readLine();

                if(word.contains("STATE"))
                {
                    int c_r = 0, c_wf = 0, c_wm = 0, c = 0;
                    float h_f = -1, h_m = -1, ah = -1;

                    c_r = Integer.parseInt(word.substring(word.indexOf('r') + 1, word.indexOf('f')));
                    c_wf = Integer.parseInt(word.substring(word.indexOf('f') + 1, word.indexOf('m')));
                    c_wm = Integer.parseInt(word.substring(word.indexOf('m') + 1, word.indexOf('F')));

                    c = c_r + c_wf + c_wm;

                    h_f = Float.parseFloat(word.substring(word.indexOf('F') + 1, word.indexOf('M')).replace(',','.'));
                    h_m = Float.parseFloat(word.substring(word.indexOf('M') + 1).replace(',','.'));

                    if(h_f != -1 && h_m != -1)
                        ah = (h_f + h_m) / 2;
                    else ah = Math.max(h_f, h_m);

                    if(!word.contains("STATEUPP"))
                        db.insert(this.hashCode() + ", " + c + ", " + c_r + ", " + c_wf + ", " +
                            c_wm + ", " + h_f + ", " + h_m + ", " + ah);
                    else db.update(this.hashCode(), c, c_r, c_wf, c_wm, h_f, h_m, ah);
                }
            }
            catch (Exception e) {
                if(e.getMessage().equals("Connection reset"))
                {
                    clientList.remove(this);
                    break;
                }
                else System.out.println(e.getMessage());
            }

        }
    }
    public void writeToClient(String word)
    {
        try
        {
            this.out.write(word + "\n");
            this.out.flush();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
