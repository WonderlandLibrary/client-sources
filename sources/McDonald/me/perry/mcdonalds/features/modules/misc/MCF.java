// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.misc;

import net.minecraft.entity.Entity;
import me.perry.mcdonalds.features.command.Command;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.perry.mcdonalds.McDonalds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.input.Mouse;
import me.perry.mcdonalds.features.modules.Module;

public class MCF extends Module
{
    private boolean clicked;
    
    public MCF() {
        super("MCF", "Middleclick Friends.", Category.MISC, true, false, false);
        this.clicked = false;
    }
    
    @Override
    public void onUpdate() {
        if (Mouse.isButtonDown(2)) {
            if (!this.clicked && MCF.mc.currentScreen == null) {
                this.onClick();
            }
            this.clicked = true;
        }
        else {
            this.clicked = false;
        }
    }
    
    private void onClick() {
        final RayTraceResult result = MCF.mc.objectMouseOver;
        final Entity entity;
        if (result != null && result.typeOfHit == RayTraceResult.Type.ENTITY && (entity = result.entityHit) instanceof EntityPlayer) {
            if (McDonalds.friendManager.isFriend(entity.getName())) {
                McDonalds.friendManager.removeFriend(entity.getName());
                Command.sendMessage(ChatFormatting.RED + entity.getName() + ChatFormatting.RED + " has been unfriended.");
            }
            else {
                McDonalds.friendManager.addFriend(entity.getName());
                Command.sendMessage(ChatFormatting.AQUA + entity.getName() + ChatFormatting.AQUA + " has been friended.");
            }
        }
        this.clicked = true;
    }
}
