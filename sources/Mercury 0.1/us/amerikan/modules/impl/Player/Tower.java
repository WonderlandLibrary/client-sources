/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.modules.impl.Player;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import de.Hero.settings.Setting;
import de.Hero.settings.SettingsManager;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Timer;
import net.minecraft.util.Vec3;
import us.amerikan.amerikan;
import us.amerikan.events.MotionUpdateEvent;
import us.amerikan.modules.Category;
import us.amerikan.modules.Module;

public class Tower
extends Module {
    public Tower() {
        super("Tower", "Tower", 0, Category.PLAYER);
        ArrayList<String> options = new ArrayList<String>();
        options.add("Fast");
        options.add("Legit");
        options.add("CC");
        amerikan.setmgr.rSetting(new Setting("TowerMode", this, "Legit", options));
    }

    @EventTarget
    public void onUpdate(MotionUpdateEvent e2) {
        this.setAddon(amerikan.setmgr.getSettingByName("TowerMode").getValString());
        if (e2.getState() != MotionUpdateEvent.State.PRE) {
            return;
        }
        BlockPos playerBlock = new BlockPos(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - 1.0, Minecraft.thePlayer.posZ);
        if (amerikan.setmgr.getSettingByName("TowerMode").getValString().equalsIgnoreCase("Fast")) {
            if (Minecraft.thePlayer.onGround) {
                Minecraft.thePlayer.setPositionAndUpdate(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 1.0, Minecraft.thePlayer.posZ);
                this.place(playerBlock, EnumFacing.DOWN);
                Tower.mc.gameSettings.keyBindSneak.pressed = true;
                e2.setPitch(90.0f);
                Minecraft.thePlayer.motionY = 0.10000000149011612;
                Tower.mc.rightClickDelayTimer = 0;
                Timer.timerSpeed = 1.1f;
            }
        }
        if (amerikan.setmgr.getSettingByName("TowerMode").getValString().equalsIgnoreCase("Legit")) {
            this.place(playerBlock.add(0.0, 1.58, 0.0), EnumFacing.DOWN);
            if (Minecraft.thePlayer.onGround) {
                e2.setPitch(90.0f);
                Minecraft.thePlayer.jump();
                Tower.mc.rightClickDelayTimer = 0;
            }
        }
        if (amerikan.setmgr.getSettingByName("TowerMode").getValString().equalsIgnoreCase("CC")) {
            this.place(playerBlock, EnumFacing.DOWN);
            Tower.mc.gameSettings.keyBindSneak.pressed = true;
            e2.setPitch(90.0f);
            Minecraft.thePlayer.motionY = 0.8;
            Tower.mc.rightClickDelayTimer = 0;
            Timer.timerSpeed = 1.2f;
        }
    }

    private void place(BlockPos pos, EnumFacing face) {
        if (Minecraft.thePlayer.hurtTime == 0) {
            Minecraft.thePlayer.swingItem();
            Tower.mc.playerController.func_178890_a(Minecraft.thePlayer, Tower.mc.theWorld, Minecraft.thePlayer.getHeldItem(), pos, face, new Vec3(pos.getX(), pos.getY(), pos.getZ()));
        }
    }

    @Override
    public void onEnable() {
        EventManager.register(this);
    }

    @Override
    public void onDisable() {
        EventManager.unregister(this);
        Minecraft.thePlayer.motionY = 0.01;
        Tower.mc.gameSettings.keyBindUseItem.pressed = false;
        Tower.mc.gameSettings.keyBindSneak.pressed = false;
        Timer.timerSpeed = 1.0f;
    }
}

