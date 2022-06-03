package main.java.Server;


import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Main
{
    public static ServerSocket serv;
    static ReadFromConsole rfc = new ReadFromConsole();
    public static final ArrayList<ClientThread> clientList = new ArrayList<>();
    public static final String DB_URL = "jdbc:h2:C:/Users/Rarka/Desktop/РГР + КУРСАЧИ/ProgKurse/db/clients";
    public static DataBase db;

    public static void main(String[] args)
    {

        try {
            try {
                db = new DataBase(DB_URL);
                serv = new ServerSocket(4004);
                db.createTable();
                System.out.println("Start server!");

                while(true)
                {
                    Socket client = serv.accept();
                    clientList.add(new ClientThread(client));
                }

            } finally {
                serv.close();
                db.close();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
