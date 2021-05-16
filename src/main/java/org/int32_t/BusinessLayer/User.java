package org.int32_t.BusinessLayer;

public class User {
    private String Name;
    private String Password;
    private int Rights;  //admin = 0,employee = 1,client = 2;

    public User(String name, String password, int rights) {
        Name = name;
        Password = password;
        Rights = rights;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getRights() {
        return Rights;
    }

    public void setRights(int rights) {
        Rights = rights;
    }
}
