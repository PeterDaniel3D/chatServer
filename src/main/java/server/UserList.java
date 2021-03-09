package server;

import java.util.ArrayList;
import java.util.List;

public class UserList {

    private List<User> userList;

    public UserList() {
        this.userList = new ArrayList<>();
    }

    public void addUser(User user) {
        userList.add(user);
    }

    public boolean findUser(String name) {
        for (User user : userList) {
            if (user.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean getStatus(String name) {
        for (User user : userList) {
            if (user.getName().equals(name)) {
                return user.isOnline();
            }
        }
        return false;
    }

    public void changeStatus(String name, boolean connected) {
        for (User user : userList) {
            if (user.getName().equals(name)) {
                user.setOnline(connected);
                break;
            }
        }
    }
}
