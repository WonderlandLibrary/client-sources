package ClassSub;

import java.io.*;
import java.net.*;
import java.util.*;

public class Class194
{
    public static ArrayList<Class194> userList;
    public Class59 userType;
    public BufferedReader bufferedReader;
    public PrintWriter printWriter;
    public Class11 speakTimer;
    public Socket socket;
    public String username;
    public String prefix;
    public String inGamename;
    public boolean isRemovedFromList;
    public boolean isClientFriend;
    public boolean isStaff;
    
    
    public static Class194 getIRCUserByNameAndType(final Class59 class59, final String s) {
        final Class194 ircUserByNameAndTypeWithNull = getIRCUserByNameAndTypeWithNull(class59, s);
        if (ircUserByNameAndTypeWithNull == null) {
            return new Class194(class59, s, class59.getPrefix(s), null);
        }
        return ircUserByNameAndTypeWithNull;
    }
    
    public static Class194 getIRCUserByNameAndTypeWithNull(final Class59 class59, final String s) {
        try {
            for (final Class194 class60 : Class194.userList) {
                if (s != null) {
                    if (class60.getUsername() == null) {
                        continue;
                    }
                    if (class60.getClientType().getClientName().equals(class59.getClientName()) && class60.getUsername().equals(s)) {
                        return class60;
                    }
                    continue;
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public Class194(final Class59 userType, final String username, final String prefix, final String inGamename) {
        this.speakTimer = new Class11();
        this.isRemovedFromList = false;
        this.isStaff = false;
        this.userType = userType;
        this.username = username;
        this.prefix = prefix;
        this.inGamename = inGamename;
        this.isStaff = false;
        final Class194 ircUserByNameAndTypeWithNull = getIRCUserByNameAndTypeWithNull(userType, username);
        if (ircUserByNameAndTypeWithNull != null) {
            Class158.LOGGER.log("[" + ircUserByNameAndTypeWithNull.userType.getClientName() + "]�����ظ����ߣ���ɾ�����û���" + ircUserByNameAndTypeWithNull.getUsername());
            ircUserByNameAndTypeWithNull.isRemovedFromList = true;
            Class194.userList.remove(ircUserByNameAndTypeWithNull);
        }
        Class194.userList.add(this);
    }
    
    public Class59 getClientType() {
        return this.userType;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getPrefix() {
        return this.prefix;
    }
    
    public String getInGamename() {
        return this.inGamename;
    }
    
    public void setInGamename(final String inGamename) {
        this.inGamename = inGamename;
    }
    
    public boolean getIsClientFriend() {
        return this.isClientFriend;
    }
    
    public void setIsClientFriend(final boolean isClientFriend) {
        this.isClientFriend = isClientFriend;
    }
    
    public BufferedReader getBufferedReader() {
        return this.bufferedReader;
    }
    
    public void setBufferedReader(final BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }
    
    public PrintWriter getPrintWriter() {
        return this.printWriter;
    }
    
    public void setSocket(final Socket socket) {
        this.socket = socket;
    }
    
    public Socket getSocket() {
        return this.socket;
    }
    
    public void setPrintWriter(final PrintWriter printWriter) {
        this.printWriter = printWriter;
    }
    
    static {
        Class194.userList = new ArrayList<Class194>();
    }
}
