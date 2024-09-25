/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 */
package skizzle.friends;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import skizzle.Client;
import skizzle.files.FileManager;
import skizzle.friends.Friend;
import skizzle.ui.notifications.Notification;

public class FriendManager {
    public static ArrayList<Friend> friends;
    public static Minecraft mc;
    public static FileManager fm;

    public static boolean isFriend(Friend Nigga) {
        for (Friend Nigga2 : friends) {
            if (!Nigga2.name.equals(Nigga.name)) continue;
            return true;
        }
        return false;
    }

    public static Friend[] getFriendsFromFile() {
        Gson Nigga = new Gson();
        File Nigga2 = new File(String.valueOf(String.valueOf(Minecraft.getMinecraft().mcDataDir.getAbsolutePath())) + File.separator + Client.name + File.separator + Qprot0.0("\u024c\u71d9\u3937\u1356\u9944\u87f4\u8c3c\u6e49\ue3bf\u3213\u0dd2\uaf02"));
        try {
            BufferedReader Nigga3 = new BufferedReader(new FileReader(Nigga2));
            Friend[] Nigga4 = (Friend[])Nigga.fromJson((Reader)Nigga3, Friend[].class);
            return Nigga4;
        }
        catch (Exception Nigga5) {
            System.out.println(Nigga5);
            return null;
        }
    }

    public static boolean removeFriend(Friend Nigga) {
        Gson Nigga2 = new Gson();
        if (friends.remove(Nigga)) {
            Client.notifications.notifs.add(new Notification(Qprot0.0("\u026c\u71d9\u3937\ud9d1\ua3c7\u87f4\u8c3c"), Qprot0.0("\u0278\u71ce\u3933\ud9db\ua3df\u87f5\u8c2b\u6e47") + Nigga.getNickname() + Qprot0.0("\u020a\u71cd\u392c\ud9db\ua3c4\u87b0\u8c36\u6e08\u2927\u0891\u0d9d\uaf0a\u80ce\u0c74\ud7f5\u9e41\u42ed\ub436\u6971"), Float.intBitsToFloat(1.05706477E9f ^ 0x7F0D4B8D), Float.intBitsToFloat(2.13244109E9f ^ 0x7F1A77FD), Notification.notificationType.INFO));
            fm.writeFile(Nigga2.toJson(friends, ArrayList.class), File.separator, Qprot0.0("\u024c\u71d9\u3937\ud9d1\ua3c7\u87f4\u8c3c\u6e49\u2938\u0890\u0dd2\uaf02"));
        }
        if (friends.size() == 0) {
            fm.writeFile("", File.separator, Qprot0.0("\u024c\u71d9\u3937\ud9d1\ua3c7\u87f4\u8c3c\u6e49\u2938\u0890\u0dd2\uaf02"));
        }
        return true;
    }

    public static boolean addFriend(Friend Nigga) {
        Gson Nigga2 = new Gson();
        if (FriendManager.isFriend(Nigga)) {
            return false;
        }
        if (friends.add(Nigga)) {
            Client.notifications.notifs.add(new Notification(Qprot0.0("\u026c\u71d9\u3937\u170c\u9da2\u87f4\u8c3c"), Qprot0.0("\u026b\u71cf\u393a\u170c\u9da8\u87b0") + Nigga.getNickname() + Qprot0.0("\u020a\u71df\u3931\u1749\u9db5\u87ff\u8c3a\u6e15\ue7af\u36e0\u0dcf\uaf05\u80d9\uc2ae\ue991\u9e5c\u42a8"), Float.intBitsToFloat(1.05861222E9f ^ 0x7F15E8DA), Float.intBitsToFloat(2.11571763E9f ^ 0x7E1B4A37), Notification.notificationType.INFO));
            fm.writeFile(Nigga2.toJson(friends, ArrayList.class), File.separator, Qprot0.0("\u024c\u71d9\u3937\u170c\u9da2\u87f4\u8c3c\u6e49\ue7e5\u36f5\u0dd2\uaf02"));
            return true;
        }
        return false;
    }

    public FriendManager() {
        FriendManager Nigga;
    }

    public static Friend getFriend(String Nigga) {
        for (Friend Nigga2 : friends) {
            if (!Nigga2.getName().equals(Nigga) && !Nigga2.getNickname().equals(Nigga)) continue;
            return Nigga2;
        }
        return null;
    }

    public static ArrayList<Friend> setFriends() {
        ArrayList<Friend> Nigga = new ArrayList<Friend>();
        if (FriendManager.getFriendsFromFile() != null) {
            for (Friend Nigga2 : FriendManager.getFriendsFromFile()) {
                Nigga.add(Nigga2);
            }
            if (Nigga.size() != 0) {
                return Nigga;
            }
        }
        return Nigga;
    }

    static {
        mc = Minecraft.getMinecraft();
        fm = new FileManager();
        friends = new ArrayList();
    }
}

