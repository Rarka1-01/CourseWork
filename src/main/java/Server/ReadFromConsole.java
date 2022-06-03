package main.java.Server;

import java.util.Scanner;

import static main.java.Server.Main.*;

public class ReadFromConsole extends Thread
{
    Scanner console = new Scanner(System.in);
    ReadFromConsole()
    {
        start();
    }

    private void checkCommand(String command)
    {
        try {
            switch (command) {
                case "/size" -> System.out.println(clientList.size());
                case "/state" ->  getStateClient();
                case "/update" -> updateAllDate();
                case "/writeTo" -> writeTo();
                case "/clear" -> db.clear();
                case "/help" -> writeHelp();
                case "/closeAllClients" -> closeClients();
                case "/stop" -> stopProgram();
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void writeHelp()
    {
        System.out.println("/size - current count clients\n" +
                "/state - write state of client\n" +
                "/update - update data base of clients\n" +
                "/writeTo - write massage to client\n" +
                "/clear - clear data base\n" +
                "/closeAllClients - close all modulate of clients\n" +
                "/stop - stop server and clear data base");
    }

    private void closeClients() throws Exception
    {
        synchronized (clientList)
        {
            for(ClientThread cl: clientList)
                cl.writeToClient("close");

            db.clear();
        }
    }
    private void stopProgram() throws Exception
    {
        serv.close();
        db.clear();
        db.close();
        System.exit(0);
    }
    private void updateAllDate()
    {
        synchronized (clientList)
        {
            for(ClientThread cl: clientList)
                cl.writeToClient("getStateUpp");
        }
    }

    private void getStateClient() {
        System.out.print("Write number client(1 to size): ");
        int count = console.nextInt();

        if (count > clientList.size() || count < 1)
            System.out.println("Error! Number isn't right");
        else {
            try {
                System.out.println("Info about this client from DataBase: ");
                db.getInfo(clientList.get(count - 1).hashCode());
            }catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    private void writeTo()
    {
        System.out.print("Write number client(1 to size): ");
        int count = console.nextInt();
        console.nextLine();

        if(count > clientList.size() || count < 1)
            System.out.println("Error! Number isn't right");
        else
        {
            System.out.print("Your string for this client: ");
            String word = console.nextLine();
            clientList.get(count - 1).writeToClient(word);
            System.out.println("Good!");
        }
    }

    @Override
    public void run()
    {
        while(true)
        {
            String command = console.nextLine();
            checkCommand(command);
            if(command.equals("/stop"))
                break;
        }
    }
}