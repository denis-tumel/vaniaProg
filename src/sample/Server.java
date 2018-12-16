package sample;

import sample.config.Const;
import sample.db.DatabaseHandler;
import sample.model.Model;
import sample.objects.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Server extends Thread{


    private ServerSocket serverSocket = null;
    private Socket clientSocket = null;
    private boolean keepGoing = true;
    private static int connectCount = 0;
    private ResultSet result = null;

    public static void main(String[] arg) {
        Server server = new Server();
        server.start();
    }

    @Override
    public void run() {
        startServer();
    }

    private void startServer() {
        try {
            serverSocket = new ServerSocket(Const.PORT);
            System.out.println("start started in Thread - " + Thread.currentThread().getName() + "\n" +
                    "Waiting for connection....");

            while (keepGoing) {
                System.out.println(connectCount + " - connect");
                clientSocket = serverSocket.accept();
                connectCount++;
                System.out.println("connection " + connectCount + " established....");

                ClientThread client = new ClientThread(clientSocket);
                client.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("IOException in Server.run()");
        }
    }

    class ClientThread extends Thread {
        Socket clientSocket;
        ObjectInputStream input = null;
        ObjectOutputStream output = null;
        Model modelObj;
        User user;
        Cofe cofe;
        Staff staff;

        ClientThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                output = new ObjectOutputStream(clientSocket.getOutputStream());
                input = new ObjectInputStream(clientSocket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            boolean flag = true;
            while (flag) {
                try {
                    modelObj = (Model) input.readObject();

                    switch (modelObj.getTypeObject()){
                        case "user":
                            user = modelObj.getUserObject();

                            switch (user.getType()) {
                                case "login":
                                    result = DatabaseHandler.login(user);
                                    if (result.next()) {
                                        register(getUser());
                                    } else {
                                        output.writeObject(null);
                                        output.flush();
                                    }
                                    break;
                                case "logout":
                                    flag = false;
                                    break;
                                case "register":
                                    register(DatabaseHandler.register(user));
                                    break;
                                case "view_users":
                                    ArrayList<User> users = DatabaseHandler.viewUser();
                                    output.writeObject(users);
                                    output.flush();
                                    break;
                                case "block_users":
                                    DatabaseHandler.blockUser(modelObj.getUserObject());
                                    break;
                                case "unlock_users":
                                    DatabaseHandler.unlockUser(modelObj.getUserObject());
                                    break;
                                case "view_cofes":
                                    ArrayList<Cofe> cofes = DatabaseHandler.viewCofe();
                                    output.writeObject(cofes);
                                    output.flush();
                                    break;
                                case "view_staff":
                                    ArrayList<Staff> staffs = DatabaseHandler.viewStaff();
                                    output.writeObject(staffs);
                                    output.flush();
                                    break;
                                case "view_position_box":
                                    ArrayList<Position> positions = DatabaseHandler.viewPositionsBox();
                                    output.writeObject(positions);
                                    output.flush();
                                    break;
                                case "view_order":
                                    ArrayList<Order> orders = DatabaseHandler.viewOrder();
                                    output.writeObject(orders);
                                    output.flush();
                                    break;
                                case "view_time":
                                    ArrayList<Time> times = DatabaseHandler.viewTime();
                                    output.writeObject(times);
                                    output.flush();
                                    break;
                            }
                            break;

                        case "cofe":
                            cofe = modelObj.getCofeObject();

                            switch (cofe.getType()){
                                case "add_cofe":
                                    DatabaseHandler.addCofe(cofe);
                                    break;
                                case "edit_cofe":
                                    DatabaseHandler.editCofe(cofe);
                                    break;
                                case "delete_cofe":
                                    DatabaseHandler.deleteCofe(cofe);
                                    break;
                                case "update_all":
                                    DatabaseHandler.updateOrder();
                                    break;
                                case "confirm":
                                    DatabaseHandler.order(modelObj);
                                    break;
                            }
                            break;

                        case "staff":
                            staff = modelObj.getStaffObject();

                            switch (staff.getType()){
                                case "add_staff":
                                    DatabaseHandler.addStaff(staff);
                                    break;
                                case "edit_staff":
                                    DatabaseHandler.editStaff(staff);
                                    break;
                                case "delete_staff":
                                    DatabaseHandler.deleteStaff(staff);
                                    break;
                            }
                            break;
                    }
                } catch (IOException | ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
            }
            System.err.println("Client disconnect");
            close();
        }

        private void register(User user) throws IOException {
            output.writeObject(user);
            output.flush();
        }

        private User getUser() {
            try {
                user.setName(result.getString(Const.USERS_NAME));
                user.setSurname(result.getString(Const.USERS_SURNAME));
                user.setEmail(result.getString(Const.USERS_EMAIL));
                user.setPassword(result.getString(Const.USERS_PASSWORD));
                user.setRole(result.getInt(Const.USERS_ROLE_ID));
                user.setId(result.getInt(Const.USERS_ID));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return user;
        }

        private void close() {

            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (clientSocket != null) {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
