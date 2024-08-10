package cc.slack.features.friends;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;

public class FriendManager {
    public final ArrayList<String> friends;
    public final List<String> targetList = new ArrayList();


    public FriendManager() {
        this.friends = new ArrayList<>();
    }

    public void addFriend(String name) {
        friends.add(name);
    }

    public void removeFriend(String name) {
        friends.remove(name);
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public boolean isFriend(Entity entity) {
        return entity instanceof EntityPlayer && friends.contains(entity.getCommandSenderName());
    }

    public boolean isTarget(EntityLivingBase entity) {
        return this.targetList.contains(entity.getCommandSenderName()) || this.targetList.contains(entity.getDisplayName().getUnformattedText());
    }
}
