package uz.limon.chatsecurity.helper;

public enum MessageType {
    TEXT,
    IMAGE;


    @Override
    public String toString() {
        return this.name();
    }
}
