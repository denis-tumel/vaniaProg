package sample.model;

import sample.objects.*;

import java.io.Serializable;

public class Model implements Serializable {

    private static final long serialVersionUID = 1113799434508676095L;

    private String typeObject;
    private User userObject;
    private Cofe cofeObject;
    private Staff staffObject;
    private Order orderObject;
    private Time timeObject;

    public String getTypeObject() {
        return typeObject;
    }

    public void setTypeObject(String typeObject) {
        this.typeObject = typeObject;
    }

    public User getUserObject() {
        return userObject;
    }

    public void setUserObject(User userObject) {
        this.userObject = userObject;
    }

    public void setCofeObject(Cofe cofe){
        this.cofeObject = cofe;
    }
    public Cofe getCofeObject() {
        return cofeObject;
    }

    public void setStaffObject(Staff staff){
        this.staffObject = staff;
    }
    public Staff getStaffObject() {
        return staffObject;
    }

    public Order getOrderObject() {
        return orderObject;
    }

    public Time getTimeObject() {
        return timeObject;
    }

    public void setTimeObject(Time timeObject) {
        this.timeObject = timeObject;
    }


    public void setOrderObject(Order orderObject) {
        this.orderObject = orderObject;
    }
}
