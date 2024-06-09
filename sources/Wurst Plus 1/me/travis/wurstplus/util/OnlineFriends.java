package me.travis.wurstplus.util;

import net.minecraft.client.Minecraft;
import java.util.List;
import java.util.ArrayList;
import net.minecraft.entity.Entity;
import java.util.stream.Collectors;

public class OnlineFriends {

    public static List<Entity> entities = new ArrayList<Entity>();

    static public List<Entity> getFriends() {
        entities.clear();
        entities.addAll(Minecraft.getMinecraft().world.playerEntities.stream().filter(entityPlayer -> Friends.isFriend(entityPlayer.getName())).collect(Collectors.toList()));
     
        return entities;
    }
       
}