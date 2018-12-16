package sample.objects;

import java.io.Serializable;

public class Order implements Serializable {
    private static final long serialVersionUID = 1113799434508676095L;

    private String time;
    private int countCofe;
    private String userName;
    private Cofe cofe;
    private int id;
    private int userID;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCountCofe() {
        return countCofe;
    }

    public void setCountCofe(int countCofe) {
        this.countCofe = countCofe;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Cofe getCofe() {
        return cofe;
    }

    public void setCofe(Cofe cofe) {
        this.cofe = cofe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
