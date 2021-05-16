package org.int32_t.BusinessLayer;

import java.util.HashMap;
import java.util.Map;

public class UserManagement {
    private static Map<String,User> users = new HashMap<>(); //The key is the username

    public boolean addUser(User newUser){
        if(users.get(newUser.getName()) == null) {
            users.put(newUser.getName(), newUser);
            return true;
        }else{
            return false;
        }
    }

    public void removeUser(User userToDelete){
        users.remove(userToDelete.getName());
    }

    public boolean validateLogin(User user){
        if(users.get(user.getName()) == null) {System.out.println("User not found"); return false;}
        if(users.get(user.getName()).getRights() != user.getRights()) {System.out.println("User does not have the correct rights");return false;}
        return users.get(user.getName()).getPassword().equals(user.getPassword());
    }
}
