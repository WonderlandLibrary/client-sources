package me.finz0.osiris.module.modules.combat;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

import me.finz0.osiris.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.text.TextComponentString;


public class str2detect extends Module {
    public str2detect() {
        super("StrengthDetect", Category.COMBAT, "Tells you in chat when someone has str 2/1");
    }
    private Set<EntityPlayer> str = Collections.newSetFromMap(new WeakHashMap());
    public static final Minecraft mc = Minecraft.getMinecraft();

    public void onUpdate() {
        for (EntityPlayer player : str2detect.mc.world.playerEntities) {
            if (player.equals(str2detect.mc.player)) continue;
            if (player.isPotionActive(MobEffects.STRENGTH) && !this.str.contains(player)) {
                Minecraft.getMinecraft().player.sendMessage(new TextComponentString("\u00A74[Aurora] " + player.getDisplayNameString() + " Has Strength"));
                this.str.add(player);
            }
            if (!this.str.contains(player) || player.isPotionActive(MobEffects.STRENGTH)) continue;
            Minecraft.getMinecraft().player.sendMessage(new TextComponentString(("\u00A7a[Aurora] " + player.getDisplayNameString() + " Has Ran Out Of Strength")));
            this.str.remove(player);
        }
    }
}