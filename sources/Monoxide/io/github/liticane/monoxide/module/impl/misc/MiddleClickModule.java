package io.github.liticane.monoxide.module.impl.misc;

import io.github.liticane.monoxide.component.ComponentManager;
import io.github.liticane.monoxide.component.impl.EntityComponent;
import io.github.liticane.monoxide.component.impl.entity.IgnoreList;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.listener.event.minecraft.player.movement.UpdateMotionEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import io.github.liticane.monoxide.util.interfaces.Methods;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjglx.input.Mouse;

import java.util.ArrayList;
import java.util.List;

@ModuleData(name = "MiddleClick", description = "Friend other players with a mouse click", category = ModuleCategory.MISCELLANEOUS)
public class MiddleClickModule extends Module implements IgnoreList {
    private final List<Entity> friends = new ArrayList<>();

    private boolean clicked;

    public MiddleClickModule() {
        ComponentManager.getInstance().getByClass(EntityComponent.class).addIgnoreList(this);
    }

    @Listen
    public void onUpdateMotion(UpdateMotionEvent updateMotionEvent) {
        if(Mouse.isButtonDown(2)) {
            if(Methods.mc.pointedEntity != null && !clicked && Methods.mc.pointedEntity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) Methods.mc.pointedEntity;

                String message;

                if(friends.contains(Methods.mc.pointedEntity)) {
                    friends.remove(player);
                    message = "§c" + player.getCommandSenderName() + " is no longer a friend!";
                } else {
                    friends.add(player);
                    message = "§a" + player.getCommandSenderName() + " is now a friend!";
                }

                this.sendMessage(message, true);
            }
            clicked = true;
        } else {
            clicked = false;
        }
    }

    @Override
    public void onEnable() {
        this.clicked = false;
    }

    @Override
    public void onDisable() {
        this.clicked = false;
    }

    @Override
    public boolean shouldSkipEntity(Entity entity) {
        return friends.contains(entity);
    }
}
