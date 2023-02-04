package uz.limon.chatsecurity.security.roles;

public enum UserPermissions {
    READ("READ"),
    EDIT("EDIT"),
    DELETE("DELETE"),
    POST("POST"),
    PIN("PIN"),
    CHAT("CHAT");

    private final String name;

    UserPermissions(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
