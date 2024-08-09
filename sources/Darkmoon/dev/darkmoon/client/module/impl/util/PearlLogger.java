package dev.darkmoon.client.module.impl.util;

import com.darkmagician6.eventapi.EventTarget;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.utility.misc.ChatUtility;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;

@ModuleAnnotation(name = "PearlLogger", category = Category.UTIL)
public class PearlLogger
        extends Module {
    private boolean canSend;
    EntityPlayer throwerEntity;

    @Override
    public void onEnable() {
        this.canSend = true;
        super.onEnable();
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        String str;
        if (PearlLogger.mc.world == null || PearlLogger.mc.player == null) {
            return;
        }
        Entity enderPearl = null;
        for (Entity e : PearlLogger.mc.world.loadedEntityList) {
            if (!(e instanceof EntityEnderPearl)) continue;
            enderPearl = e;
            break;
        }
        if (enderPearl == null) {
            this.canSend = true;
            return;
        }
        for (EntityPlayer entity : PearlLogger.mc.world.playerEntities) {
            if (entity == PearlLogger.mc.player || this.throwerEntity != null && this.throwerEntity.getDistanceSq(enderPearl) <= entity.getDistanceSq(enderPearl)) continue;
            this.throwerEntity = entity;
        }
        String facing = enderPearl.getHorizontalFacing().toString();
        if (facing.equals("west")) {
            facing = "east";
        } else if (facing.equals("east")) {
            facing = "west";
        }
        if (this.throwerEntity == PearlLogger.mc.player || this.throwerEntity == null) {
            return;
        }
        if (this.throwerEntity.getName().equals(PearlLogger.mc.player.getName())) {
            return;
        }
        String pos = (Object)((Object) ChatFormatting.GOLD) + facing.toUpperCase() + (Object)((Object)ChatFormatting.WHITE) + " | " + (Object)((Object)ChatFormatting.LIGHT_PURPLE) + enderPearl.getPosition().getX() + " " + enderPearl.getPosition().getY() + " " + enderPearl.getPosition().getZ();
        String string = str = DarkMoon.getInstance().getFriendManager().isFriend(this.throwerEntity.getName()) ? (Object)((Object)ChatFormatting.GREEN) + this.throwerEntity.getName() + (Object)((Object)ChatFormatting.WHITE) + " thrown pearl on " + pos : (Object)((Object)ChatFormatting.RED) + this.throwerEntity.getName() + (Object)((Object)ChatFormatting.WHITE) + " thrown pearl on " + pos;
        if (this.canSend) {
            ChatUtility.addChatMessage(str);
            this.canSend = false;
        }
    }
}

