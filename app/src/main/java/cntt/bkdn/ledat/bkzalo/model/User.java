package cntt.bkdn.ledat.bkzalo.model;

public class User {
    public String id;
    public String name;
    public String pass;
    public String status;

    public User() {
    }

    public User(String id, String name, String pass) {
        this.id= id;
        this.name = name;
        this.pass = pass;
    }
    public User(String id,String name, String pass,String status) {
        this.id= id;
        this.name = name;
        this.pass = pass;
        this.status=status;
    }



    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
