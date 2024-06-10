// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.friend;

import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;

import me.kaktuswasser.client.utilities.Logger;
import net.minecraft.util.StringUtils;
import java.util.Map;

public class FriendManager
{
    private Map<String, String> friends;
    
    public boolean isFriend(final String name) {
        return this.friends.containsKey(StringUtils.stripControlCodes(name));
    }
    
    public void addFriend(final String name, final String alias) {
        this.friends.put(name, alias);
    }
    
    public void removeFriend(final String name) {
        this.friends.remove(name);
    }
    
    public String replaceNames(String message, final boolean color) {
        if (!message.contains(Logger.getPrefix("Icarus"))) {
            for (final String name : this.friends.keySet()) {
                message = message.replaceAll("(?i)" + name, Matcher.quoteReplacement(color ? ("§3" + this.friends.get(name) + "§r") : this.friends.get(name)));
            }
        }
        return message;
    }
    
    public void setupFriends() {
        this.friends = new HashMap<String, String>();
    }
    
    public void organizeFriends() {
        Logger.writeConsole("Started loading up friends.");
        Logger.writeConsole("Loaded up " + this.friends.size() + "friends.");
    }
    
    public Map<String, String> getFriends() {
        return this.friends;
    }
}
