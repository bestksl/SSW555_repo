package ssw555_refectory.bean;

import java.util.ArrayList;

public class Family {

    private String id;
    private String married;
    private String divorced;
    private String husbandID;
    private String husbandName;
    private String wifeID;
    private String wifeName;
    private String date;
    private ArrayList<String> children = new ArrayList<>();

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarried() {
        return married;
    }

    public void setMarried(String married) {
        this.married = married;
    }

    public String getDivorced() {
        return divorced;
    }

    public void setDivorced(String divorced) {
        this.divorced = divorced;
    }

    public String getHusbandID() {
        return husbandID;
    }

    public void setHusbandID(String husbandID) {
        this.husbandID = husbandID;
    }

    public String getHusbandName() {
        return husbandName;
    }

    public void setHusbandName(String husbandName) {
        this.husbandName = husbandName;
    }

    public String getWifeID() {
        return wifeID;
    }

    public void setWifeID(String wifeID) {
        this.wifeID = wifeID;
    }

    public String getWifeName() {
        return wifeName;
    }

    public void setWifeName(String wifeName) {
        this.wifeName = wifeName;
    }

    public ArrayList<String> getChildren() {
        return children;
    }

    public void addChildren(String childrenId) {
        this.children.add(childrenId);
    }

}