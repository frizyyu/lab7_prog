//package client;
import commandHelpers.CommandInitializator;
import commandHelpers.ExecuteScript;
import helpers.*;
import jsonHelper.ReadFromJson;
import org.checkerframework.checker.units.qual.C;
import supportive.MusicBand;

import java.io.IOException;
import java.net.InetAddress;
import java.net.PortUnreachableException;
import java.nio.channels.ClosedChannelException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        //Важно! Команды и их аргументы должны представлять из себя объекты классов. Недопустим обмен "простыми" строками.
        // Так, для команды add или её аналога необходимо сформировать объект, содержащий тип команды и объект, который должен храниться в вашей коллекции.
        // то есть, нужно сделать класс, в котором есть поле команда и аргумент
        //но как для add формировать сразу все аргументы, если они вводятся по очереди?? с сервера посылать сообщение, что нужно ввести?
        LinkedHashSet<MusicBand> collection = new LinkedHashSet<MusicBand>();
        //collection.add(new MusicBand());
        ClientToServer clientToServer = null;
        Request req;
        Scanner input = new Scanner(System.in);
        ArrayList<String> needCrateMbObject = new ArrayList<>();

/*
        //while (true){
            //enter username
            while (true) {
                System.out.println("Enter your username");
                System.out.print(">>> ");
                CreateUser.userName = input.nextLine().trim();
                if (!CreateUser.userName.contains("|"))
                    break;
                else
                    System.out.println("Invalid char \"|\"");
            }

            //enter the password
            System.out.println("Enter your password");
            System.out.print(">>> ");
            CreateUser.pswd = input.nextLine().trim();
            //реквест для подтверждения логина, иначе процесс регистрации
            req = new Request("CHECKUSEREXIST", CreateUser.pswd, null, CreateUser.userName, null);
            collection = clientToServer.send(req);

            if (req login returns false){
                System.out.println("Incorrect login or password. Register? y/n");
                            ContinueAction cont = new ContinueAction();
                            System.out.printf("%s >>> ", CreateUser.userName);
                            int c = cont.continueAction("User has been registered\n", "User hasn`t been registered\n", "Action skipped. Invalid answer\n");
                            if (c == 1) {
                                //req with send login and pswd
                                break;
                            }
            }
        }
         */


        while (true) {
            try {
                //System.out.println(CreateUser.userName);


                while (true) {
                    System.out.println("Enter your username");
                    System.out.print(">>> ");
                    CreateUser.userName = input.nextLine().trim();
                    if (CreateUser.userName.length() >= 40){
                        System.out.println("Login lenght must be less than 40 charachters");
                        continue;
                    }
                    if (!CreateUser.userName.contains("|"))
                        break;
                    else
                        System.out.println("Invalid char \"|\"");
                }

                //enter the password

                while (true) {
                    System.out.println("Enter your password");
                    System.out.print(">>> ");
                    String pswd = input.nextLine().trim();
                    if (pswd.length() >= 6) {
                        PasswordHasher ph = new PasswordHasher(pswd);
                        CreateUser.pswd = ph.hashing();
                        break;
                    }
                    else
                        System.out.println("Password lenght must be bigger than 6 charachters");
                }



                clientToServer = new ClientToServer(InetAddress.getByName("127.0.0.1"), 63342, collection);
                clientToServer.connect();
                //clientToServer.send(new Request("IWANNASALT", "", null, CreateUser.userName, null));
                clientToServer.send(new Request("CHECKUSEREXIST", CreateUser.pswd, null, CreateUser.userName, "password", null)); // "password" -> CreateUser.pswd, просто по ощущениям, если так паролем раскидываться ирл, то украдут
                if (CreateUser.userName.equals("|"))
                    clientToServer.disconnect();
                String nameOfFile;
                if (args.length != 0)
                    nameOfFile = args[0];
                else
                    nameOfFile = "";
                while (true) {
                    req = new Request("CONNECTCLIENT", nameOfFile, null, CreateUser.userName, "password", null);
                    collection = clientToServer.send(req); // load collection to client
                    //System.out.println(collection);
                    if (collection != null) {
                        System.out.println("Data has been loaded");
                        break;
                    } else {
                        System.out.println("Incorrect termination of the program was detected. Restore the data? y/n");
                        ContinueAction cont = new ContinueAction();
                        System.out.printf("%s >>> ", CreateUser.userName);
                        int c = cont.continueAction("Load data\n", "Data has not been loaded\n", "Action skipped. Invalid answer\n");
                        if (c == 1) {
                            System.out.println("Enter file name to save collection in future");
                            System.out.printf("%s >>> ", CreateUser.userName);
                            ReadFromJson.fileName = input.nextLine();
                            //System.out.println(ReadFromJson.fileName);
                            nameOfFile = String.format("LOAD|%s.json", ReadFromJson.fileName);
                        } else {
                            while (true) {
                                System.out.println("Enter file name:"); //create file, if not exist
                                System.out.printf("%s >>> ", CreateUser.userName);
                                nameOfFile = input.nextLine();
                                if (Objects.equals(nameOfFile, "tmp")) {
                                    System.out.println("Sorry, this name cannot be assigned");
                                } else {
                                    nameOfFile = String.format("%s|Y", nameOfFile);
                                    break;
                                }
                            }
                        }
                    }
                }
                break;
            } catch (PortUnreachableException portError) {
                System.out.println("Can not send data to server, check server status. Retry? y/n");
                System.out.printf("%s >>> ", CreateUser.userName);
                if (!Objects.equals(input.nextLine(), "y")) {
                    System.out.println("Reconnection cancelled. closing program");
                    System.exit(0);
                }
                try {
                    assert clientToServer != null;
                    clientToServer.disconnect();
                } catch (ClosedChannelException ignored) {
                }
            } catch (ClosedChannelException er){
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
        needCrateMbObject.add("add");
        needCrateMbObject.add("update");
        needCrateMbObject.add("add_if_max");
        needCrateMbObject.add("remove_greater");
        needCrateMbObject.add("remove_lower");
        //System.out.println(collection);
        //System.out.println(ReadFromJson.fileName);
        //req = new Request("NEWUSER", CreateUser.userName, collection, CreateUser.userName);
        //clientToServer.send(req);
        CommandInitializator commandsInitializator = new CommandInitializator(collection);
        String[] currCommand;
        String command;
        //Scanner input = new Scanner(System.in);
        while (true) { // the main loop with a call to the command handler
            //System.out.println(ReadFromJson.fileName);
            System.out.printf("%s >>> ", CreateUser.userName);
            //System.out.println(CreateUser.userName);
            command = input.nextLine();
            try {
                //while (true) {
                    currCommand = command.replaceFirst(" ", "|").split("\\|");
                    if (currCommand.length > 1)
                        req = new Request(currCommand[0], currCommand[1], null, CreateUser.userName, "password", null);
                    else
                        req = new Request(currCommand[0], "", null, CreateUser.userName, "password", null);
                    if (commandsInitializator.validate(command)) {
                        if ((currCommand.length == 1 && needCrateMbObject.contains(currCommand[0])) || (currCommand[0].equals("update") && !currCommand[1].contains("{")) && !req.getArgs().contains("-h")) {
                            //делать тут проверку, запускать юзер френдли креатор, из него везвращать строку-жсон и её передавать на сервер
                            UserFriendlyCreateObject ufco = new UserFriendlyCreateObject();
                            String arg = ufco.createString();
                            //System.out.println(arg);
                            if (currCommand[0].equals("update")) {
                                req.setArgs(String.format("%s %s", req.getArgs().split(" ")[0], arg));
                            } else
                                req.setArgs(arg);
                        }

                        if (needCrateMbObject.contains(currCommand[0]) && !req.getArgs().contains("-h")) {
                            ClassObjectCreator coc = new ClassObjectCreator(collection);
                            //System.out.println(req.getArgs());
                            MusicBand mb;
                            if (Objects.equals(currCommand[0], "update")) {
                                mb = coc.create(req.getArgs().replaceFirst(" ", "|").split("\\|")[1]);
                                mb = coc.setId(currCommand[1].replaceFirst(" ", "|").split("\\|")[0], mb);
                            } else
                                mb = coc.create(req.getArgs());
                            req.setMbElement(mb);
                            //System.out.println(mb.getName());
                        }

                        if (command.equals("exit"))
                            req.setArgs(String.format("%sexit|%s", CreateUser.userName, ReadFromJson.fileName));
                        if (currCommand[0].equals("execute_script")) {
                            ExecuteScriptReader esr = new ExecuteScriptReader();

                            //for с отправкой команд из execute script
                            //for (int i = 0; i < currCommand[1].split("\n").length; i++){
                            //System.out.println("QQ");
                            ExecuteScript es = new ExecuteScript(collection, null);
                            es.execute(esr.read(currCommand[1]), clientToServer, CreateUser.userName, needCrateMbObject);
                            //}
                            req.setArgs(esr.read(currCommand[1]));
                            continue;
                        }
                        clientToServer.send(req);
                        commandsInitializator = new CommandInitializator(clientToServer.getCollection());
                    }
                //}
            } catch (PortUnreachableException portError) {
                System.out.println("Can not send data to server, check server status and your internet connection. Retry? y/n");
                System.out.printf("%s >>> ", CreateUser.userName);
                if (!Objects.equals(input.nextLine(), "y")) {
                    System.out.println("Reconnection cancelled. closing program");
                    System.exit(0);
                } else
                    try {
                        //clientToServer.send(new Request("send collection", ReadFromJson.fileName, clientToServer.getCollection(), CreateUser.userName, null));
                        clientToServer.send(new Request("CONNECTCLIENT", ReadFromJson.fileName, null, CreateUser.userName, "password", null));
                    } catch (PortUnreachableException ignored) {
                    }
            }
        }
    }
}
