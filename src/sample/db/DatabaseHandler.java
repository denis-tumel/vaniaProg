package sample.db;

import sample.config.Const;
import sample.db.base.Mysql;
import sample.model.Model;
import sample.objects.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseHandler extends Mysql {
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;

    public static void blockUser(User user) throws SQLException {
        String sql = "UPDATE " + Const.USERS_TABLE + " SET " + Const.USERS_ROLE_ID + " = ? WHERE id = ?";
        preparedStatement = connect().prepareStatement(sql);
        preparedStatement.setInt(1, Const.BLOCK_USERS);
        preparedStatement.setInt(2, user.getId());

        preparedStatement.executeUpdate();
    }

    public static void unlockUser(User user) throws SQLException {
        String sql = "UPDATE " + Const.USERS_TABLE + " SET " + Const.USERS_ROLE_ID + " = ? WHERE id = ?";
        preparedStatement = connect().prepareStatement(sql);
        preparedStatement.setInt(1, Const.UNLOCK_USERS);
        preparedStatement.setInt(2, user.getId());

        preparedStatement.executeUpdate();
    }

    public static ResultSet login(User user) {
        try {
            String sql = "SELECT * FROM " + Const.USERS_TABLE + " WHERE email = ? AND password = ?";
            preparedStatement = connect().prepareStatement(sql);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());

            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static User register(User user) {
        try {
            String sql = "INSERT INTO " + Const.USERS_TABLE + "( " + Const.USERS_ROLE_ID + ", " + Const.USERS_NAME + ", " + Const.USERS_SURNAME + ", " + Const.USERS_EMAIL + ", " + Const.USERS_PASSWORD + ") " +
                    " VALUES ( ? , ? , ? , ? , ? ) ";
            preparedStatement = connect().prepareStatement(sql);
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getSurname());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


    public static ArrayList<User> viewUser() {
        ArrayList<User> users = new ArrayList<>();
        User user;
        try {
            String sql = "SELECT * FROM " + Const.USERS_TABLE;

            preparedStatement = connect().prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user = new User();
                user.setName(resultSet.getString(Const.USERS_NAME));
                user.setSurname(resultSet.getString(Const.USERS_SURNAME));
                user.setRole(resultSet.getInt(Const.USERS_ROLE_ID));
                user.setEmail(resultSet.getString(Const.USERS_EMAIL));
                user.setPassword(resultSet.getString(Const.USERS_PASSWORD));
                user.setId(resultSet.getInt(Const.USERS_ID));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static ArrayList<Cofe> viewCofe() {
        ArrayList<Cofe> cofes = new ArrayList<>();
        Cofe cofe;
        try {
            String sql = "SELECT * FROM " + Const.COFES_TABLE;

            preparedStatement = connect().prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                cofe = new Cofe();
                cofe.setName(resultSet.getString(Const.USERS_NAME));
                cofe.setPrice(resultSet.getInt(Const.SERVICES_PRICE));
                cofe.setId(resultSet.getInt(Const.USERS_ID));

                cofes.add(cofe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cofes;
    }

    public static void addCofe(Cofe cofe) {
        try {
            String sql = "INSERT INTO " + Const.COFES_TABLE + "( " + Const.USERS_NAME + ", " + Const.SERVICES_PRICE + ") " +
                    " VALUES ( ? , ? ) ";
            preparedStatement = connect().prepareStatement(sql);
            preparedStatement.setString(1, cofe.getName());
            preparedStatement.setInt(2, cofe.getPrice());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editCofe(Cofe cofe) throws SQLException {
        String sql = "UPDATE " + Const.COFES_TABLE + " SET " + Const.USERS_NAME + " = ?, " + Const.SERVICES_PRICE + " = ? WHERE id = ?";
        preparedStatement = connect().prepareStatement(sql);
        preparedStatement.setString(1, cofe.getName());
        preparedStatement.setInt(2, cofe.getPrice());
        preparedStatement.setInt(3, cofe.getId());

        preparedStatement.executeUpdate();
    }

    public static void deleteCofe(Cofe cofe) throws SQLException {
        String sql = "DELETE FROM " + Const.COFES_TABLE + " WHERE id = ? ";
        preparedStatement = connect().prepareStatement(sql);
        preparedStatement.setInt(1, cofe.getId());

        preparedStatement.executeUpdate();
    }

    public static ArrayList<Staff> viewStaff() {
        ArrayList<Staff> staffs = new ArrayList<>();
        Staff staff;
        try {
            String sql = "SELECT " + Const.STAFF_TABLE + ".id, " + Const.STAFF_TABLE + ".name, " + Const.STAFF_TABLE + ".surname, " + Const.STAFF_TABLE + ".salary, " + Const.POSITION_TABLE + ".name, " + Const.POSITION_TABLE + ".id FROM " + Const.STAFF_TABLE + " JOIN " + Const.POSITION_TABLE + " ON " + Const.STAFF_TABLE + ".position_id = " + Const.POSITION_TABLE + ".id ";

            preparedStatement = connect().prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                staff = new Staff();
                staff.setName(resultSet.getString(Const.STAFF_TABLE+"."+Const.USERS_NAME));
                staff.setSurname(resultSet.getString(Const.USERS_SURNAME));
                staff.setId(resultSet.getInt(Const.USERS_ID));
                Position position = new Position();
                position.setName(resultSet.getString(Const.POSITION_TABLE+"."+Const.USERS_NAME));
                position.setId(resultSet.getInt(Const.POSITION_TABLE+"."+Const.USERS_ID));

                staff.setPositionName(resultSet.getString(Const.POSITION_TABLE+"."+Const.USERS_NAME));
                staff.setPosition(position);
                staff.setSalary(resultSet.getInt(Const.STAFF_SALARY));

                staffs.add(staff);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return staffs;
    }

    public static ArrayList<Position> viewPositionsBox() {
        ArrayList<Position> positions = new ArrayList<Position>();
        Position position;
        try {
            String sql = "SELECT * FROM " + Const.POSITION_TABLE ;

            preparedStatement = connect().prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                position = new Position();

                position.setId(resultSet.getInt(Const.USERS_ID));
                position.setName(resultSet.getString(Const.USERS_NAME));

                positions.add(position);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return positions;
    }

    public static void addStaff(Staff staff) {
        try {
            String sql = "INSERT INTO " + Const.STAFF_TABLE+ "( " + Const.USERS_NAME + ", " + Const.USERS_SURNAME + ", " + Const.POSITION_ID + ", " + Const.STAFF_SALARY + ") " +
                    " VALUES ( ? , ? , ? , ? ) ";
            preparedStatement = connect().prepareStatement(sql);
            preparedStatement.setString(1, staff.getName());
            preparedStatement.setString(2, staff.getSurname());
            preparedStatement.setInt(3, staff.getPosition().getId());
            preparedStatement.setInt(4, staff.getSalary());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editStaff(Staff staff) throws SQLException {
        String sql = "UPDATE " + Const.STAFF_TABLE + " SET " + Const.USERS_NAME + " = ?, " + Const.USERS_SURNAME + " = ?, " + Const.POSITION_ID + " = ?, " + Const.STAFF_SALARY + " = ?   WHERE id = ?";
        preparedStatement = connect().prepareStatement(sql);
        preparedStatement.setString(1, staff.getName());
        preparedStatement.setString(2, staff.getSurname());
        preparedStatement.setInt(3, staff.getPosition().getId());
        preparedStatement.setInt(4, staff.getSalary());
        preparedStatement.setInt(5, staff.getId());

        preparedStatement.executeUpdate();
    }

    public static void deleteStaff(Staff staff) throws SQLException {
        String sql = "DELETE FROM " + Const.STAFF_TABLE + " WHERE id = ? ";
        preparedStatement = connect().prepareStatement(sql);
        preparedStatement.setInt(1, staff.getId());

        preparedStatement.executeUpdate();
    }

    public static void updateOrder() {
        try {
            String sql = "DELETE FROM " + Const.ORDER_TABLE;
            preparedStatement = connect().prepareStatement(sql);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void order(Model model) throws SQLException {
        String sql = "INSERT INTO " + Const.ORDER_TABLE + "( " + Const.TIME_ID + ", " + Const.COUNT_COFFE + ", " + Const.USER_ID + ", " + Const.COFE_ID + " ) " +
                " VALUES ( ? , ? , ? , ? ) ";
        preparedStatement = connect().prepareStatement(sql);

        preparedStatement.setInt(1, model.getTimeObject().getId());
        preparedStatement.setInt(2, model.getOrderObject().getCountCofe());
        preparedStatement.setInt(3, model.getUserObject().getId());
        preparedStatement.setInt(4, model.getCofeObject().getId());

        preparedStatement.executeUpdate();
    }

    public static ArrayList<Time> viewTime() {
        ArrayList<Time> times = new ArrayList<>();
        Time time;
        try {
            String sql = "SELECT * FROM " + Const.ORDER_TIME ;
            preparedStatement = connect().prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                time = new Time();
                time.setId(resultSet.getInt(Const.USERS_ID));
                time.setName(resultSet.getString(Const.USERS_NAME));

                times.add(time);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return times;
    }

    public static ArrayList<Order> viewOrder() {
        ArrayList<Order> orders = new ArrayList<>();
        try {
            String sql = "SELECT " + Const.ORDER_TABLE + ".id, " + Const.ORDER_TIME + ".name, " + Const.ORDER_TABLE + ".countCoffe, " + Const.USERS_TABLE + ".name, " + Const.USERS_TABLE + ".id, " + Const.COFES_TABLE + ".name, " + Const.COFES_TABLE +".price  FROM " + Const.ORDER_TABLE + " JOIN " + Const.USERS_TABLE + " ON " + Const.ORDER_TABLE + ".user_id = " + Const.USERS_TABLE + ".id JOIN  "+ Const.COFES_TABLE + " ON " + Const.ORDER_TABLE + ".coffe_id = " + Const.COFES_TABLE + ".id JOIN "+ Const.ORDER_TIME + " ON " + Const.ORDER_TABLE + ".time_id = " + Const.ORDER_TIME + ".id ";
            preparedStatement = connect().prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();

            addOrderInOrders(orders);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    private static void addOrderInOrders(ArrayList<Order> orders) throws SQLException {
        Order order;
        while (resultSet.next()) {
            order = new Order();
            order.setTime(resultSet.getString(Const.ORDER_TIME+"."+Const.USERS_NAME));
            order.setCountCofe(resultSet.getInt(Const.COUNT_COFFE));
            order.setId(resultSet.getInt(Const.USERS_ID));
            Cofe cofe = new Cofe();
            cofe.setName(resultSet.getString(Const.COFES_TABLE+"."+Const.USERS_NAME));
            cofe.setPrice(resultSet.getInt(Const.SERVICES_PRICE));
            order.setCofe(cofe);
            order.setUserID(resultSet.getInt(Const.USERS_TABLE+"."+Const.USERS_ID));
            order.setUserName(resultSet.getString(Const.USERS_TABLE+"."+Const.USERS_NAME));

            orders.add(order);
        }
    }
}
