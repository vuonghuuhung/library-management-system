package com.libsystem.librarymanagementsystem.service;

import com.libsystem.librarymanagementsystem.database.BookDAO;
import com.libsystem.librarymanagementsystem.database.UserDAO;
import com.libsystem.librarymanagementsystem.model.Book;
import com.libsystem.librarymanagementsystem.model.User;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.*;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class UserService {
    public static User authenticate(String username, String password) throws Exception {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.findUsername(username);
        if (user == null) {
            return null;
        } else {
            String salt = user.getSalt();
            String calculatedHash = getEncryptedPassword(password, salt);
            if (calculatedHash.equals(user.getPassword())) {
                return user;
            }
        }
        return null;
    }

//    private void signUp(String userName, String password) throws Exception {
//        String salt = getNewSalt();
//        String encryptedPassword = getEncryptedPassword(password, salt);
//        UserInfo user = new UserInfo();
//        user.userEncryptedPassword = encryptedPassword;
//        user.userName = userName;
//        user.userSalt = salt;
//        saveUser(user);
//    }

    public static String getEncryptedPassword(String password, String salt) throws Exception {
        String algorithm = "PBKDF2WithHmacSHA1";
        int derivedKeyLength = 160; // for SHA1
        int iterations = 20000; // NIST specifies 10000

        byte[] saltBytes = Base64.getDecoder().decode(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, iterations, derivedKeyLength);
        SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);

        byte[] encBytes = f.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(encBytes);
    }

    public static boolean updateUser(String fullName, String username, String password, String dateOfBirth, String gender, String phoneNumber, User user) throws Exception {
        String salt = getNewSalt();
        String passwordEncrypted = getEncryptedPassword(password, salt);
        user.setFullName(fullName);
        user.setUsername(username);
        user.setPassword(passwordEncrypted);
        user.setSalt(salt);
        user.setDateOfBirth(dateOfBirth);
        user.setPhoneNumber(phoneNumber);
        user.setGender(gender);
        UserDAO userDAO = new UserDAO();

        return userDAO.updateUser(user);
    }

    public static String getNewSalt() throws Exception {
        // Don't use Random!
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        // NIST recommends minimum 4 bytes. We use 8.
        byte[] salt = new byte[8];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

//    private void saveUser(UserInfo user) {
//        userDatabase.put(user.userName, user);
//    }

    public static ArrayList<User> findUser(String findingPhrase) {
        UserDAO userDAO = new UserDAO();
        return userDAO.findUser(findingPhrase);
    }

    public static boolean deleteUser(int userID) {
        UserDAO userDAO = new UserDAO();
        return userDAO.deleteUser(userID);
    }

    public static ArrayList<User> showAllUser() {
        UserDAO userDAO = new UserDAO();
        return userDAO.showAllUser();
    }

    public static ArrayList<String> getAllRoles() {
        UserDAO userDAO = new UserDAO();
        return userDAO.getAllRoles();
    }

    public static boolean addUser(String fullName, String username, String password, String role, String dateOfBirth, String phoneNumber, String gender) throws Exception {
        UserDAO userDAO = new UserDAO();
        String salt = getNewSalt();
        String passwordEncrypted = getEncryptedPassword(password, salt);
        User user = new User(username, passwordEncrypted, role, fullName, salt, dateOfBirth, phoneNumber, gender);
        return userDAO.addUser(user);
    }
}
