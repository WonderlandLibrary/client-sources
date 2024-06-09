package lunadevs.luna.friend;

import com.mojang.authlib.GameProfile;

import lunadevs.luna.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.StringUtils;

public class FriendManager {
    private static final File FRIEND = FileUtils.getConfigFile("Friends");
    public static ArrayList<Friend> friendsList = new ArrayList();
    public static ArrayList<String> enemys = new ArrayList();
    private final List<Friend> friends = new ArrayList();

    public static void init() {
        FriendManager.load();
        FriendManager.save();
    }

    public static void addFriend(String name, String alias) {
        friendsList.add(new Friend(name, alias));
        FriendManager.save();
    }

    public static String getAliasName(String name) {
        String alias = "";
        for (Friend friend : friendsList) {
            if (!friend.name.equalsIgnoreCase(StringUtils.stripControlCodes((String)name))) continue;
            alias = friend.alias;
            break;
        }
        if (Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().thePlayer.getGameProfile().getName() == name) {
            return name;
        }
        return alias;
    }

    public static void removeFriend(String name) {
        for (Friend friend : friendsList) {
            if (!friend.name.equalsIgnoreCase(name)) continue;
            friendsList.remove(friend);
            break;
        }
        FriendManager.save();
    }

    public static String replace(String text) {
        for (Friend friend : friendsList) {
            if (!text.contains(friend.name)) continue;
            text = friend.alias;
        }
        return text;
    }

    public static boolean isFriend(String name) {
        boolean isFriend = false;
        for (Friend friend : friendsList) {
            if (!friend.name.equalsIgnoreCase(StringUtils.stripControlCodes((String)name))) continue;
            isFriend = true;
            break;
        }
        if (Minecraft.getMinecraft().thePlayer.getGameProfile().getName() == name) {
            isFriend = true;
        }
        return isFriend;
    }

    private static void load() {
        List<String> fileContent = FileUtils.read(FRIEND);
        for (String line : fileContent) {
            try {
                String[] split = line.split(":");
                String name = split[0];
                String alias = split[1];
                FriendManager.addFriend(name, alias);
                continue;
            }
            catch (Exception split) {
                // empty catch block
            }
        }
    }

    public static void save() {
        ArrayList<String> fileContent = new ArrayList<String>();
        for (Friend friend : friendsList) {
            String alias = FriendManager.getAliasName(friend.name);
            fileContent.add(String.format("%s:%s", friend.name, alias));
        }
        FileUtils.write(FRIEND, fileContent, true);
    }
    
    public List<Friend> getFriends()
    {
      return this.friends;
    }
    

    public Friend getFriend(String username)
    {
      for (Friend friend : getFriends()) {
        if ((friend.getUsername().equalsIgnoreCase(username)) || (friend.getAlias().equalsIgnoreCase(username))) {
          return friend;
        }
      }
      return null;
    }

	public static boolean isEnemy(String name)
  {
    return enemys.contains(name);
  }
}

