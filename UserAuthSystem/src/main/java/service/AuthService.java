package service;

import model.User;
import util.HashUtil;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private final Map<String, User> users = new HashMap<>();
    private static final String USERS_FILE = "users.dat";

    public AuthService() {
        loadUsers();
    }

    public boolean register(String username, String password, String email){
        if (users.containsKey(username)){
            return false; // username exists
        }

        String hashedPassword = HashUtil.hashPassword(password);
        User newUser = new User(username,hashedPassword,email);
        users.put(username,newUser);
        saveUsers();

        return true;
    }

    public boolean login(String username, String password){
        User user = users.get(username);
        if (user==null){
            return false;
        }

        String hashedInput = HashUtil.hashPassword(password);
        return hashedInput.equals(user.getPasswordHash());
    }

    public void saveUsers(){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            out.writeObject(users);
        }catch (IOException e){
            System.out.println("Failed to save users: "+e.getMessage());
        }
    }

    public void loadUsers(){
        File file = new File(USERS_FILE);
        if (!file.exists()){
            return;
        }

        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            Map<String, User> loaded = (Map<String, User>) in.readObject();
            users.putAll(loaded);
        }catch (IOException | ClassNotFoundException e){
            System.out.println("Failed to load users: "+e.getMessage());
        }
    }

    public void printAllUsers(){
        users.values().forEach(System.out::println);
    }

    public boolean resetPassword(String username, String email, String newPassword){
        User user = users.get(username);
        if (user==null || !user.getEmail().equalsIgnoreCase(email)){
            return false;
        }

        user.setPasswordHash(HashUtil.hashPassword(newPassword));
        saveUsers();
        return true;
    }
}
