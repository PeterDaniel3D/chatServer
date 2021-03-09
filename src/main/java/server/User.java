package server;

public class User {

    private String name;
    private boolean online;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
