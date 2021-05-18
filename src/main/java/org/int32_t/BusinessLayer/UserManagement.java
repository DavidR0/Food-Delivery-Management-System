package org.int32_t.BusinessLayer;

import org.int32_t.DataLayer.Serializator;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that handles all the users credentials
 */
public class UserManagement {
    private static Map<String,User> users = new HashMap<>(); //The key is the username
    private final String serialFileName = "userAccounts.txt";
    private static boolean loadedSerial = false;

    public UserManagement() {
        if(!loadedSerial) {
            readFromSerial();
            loadedSerial = true;
        }
    }

    /**
     * Adds a new user to the list of users
     * @param newUser user to be added
     * @return boolean if the user was successfully added
     */
    public boolean addUser(User newUser){
        if(users.get(newUser.getName()) == null) {
            users.put(newUser.getName(), newUser);
            updateSerial();
            return true;
        }else{
            return false;
        }
    }

    /**
     * Removes a user
     * @param userToDelete user that is to be deleted
     */
    public void removeUser(User userToDelete){
        users.remove(userToDelete.getName());
        updateSerial();
    }

    /**
     * Checks if some login info is correct
     * @param user The credentials
     * @return boolean if the credentials are correct
     */
    public boolean validateLogin(User user){
        if(users.get(user.getName()) == null) {System.out.println("User not found"); return false;}
        if(users.get(user.getName()).getRights() != user.getRights()) {System.out.println("User does not have the correct rights");return false;}
        return users.get(user.getName()).getPassword().equals(user.getPassword());
    }

    /**
     * Writes the users list to a file
     */
    private void updateSerial(){
        Serializator<Map<String,User>> sr = new Serializator<>();
        sr.toSerial(users,serialFileName);
    }

    /**
     * Reads the users list from a file
     */
    private void readFromSerial(){
        Serializator<Map<String,User>> sr = new Serializator<>();
        users = sr.deserialize(users,serialFileName);
    }
}
