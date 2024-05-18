package com.krazzzzymonkey.catalyst.module.modules.render;

import java.util.Iterator;
import com.krazzzzymonkey.catalyst.utils.visual.ColorUtils;
import com.krazzzzymonkey.catalyst.utils.visual.RenderUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.gui.ScaledResolution;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import com.krazzzzymonkey.catalyst.module.ModuleCategory;
import java.util.Arrays;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import com.krazzzzymonkey.catalyst.module.Modules;

public class PlayerRadar extends Modules
{

    public PlayerRadar() {
        super("PlayerRadar", ModuleCategory.RENDER);
    }

    @Override
    public void onRenderGameOverlay(final RenderGameOverlayEvent.Text event) {
        int int1 = 2;
        final ScaledResolution scaledResolution = new ScaledResolution(Wrapper.INSTANCE.mc());
        final double entityList = (double)Wrapper.INSTANCE.world().loadedEntityList.iterator();
        while (((Iterator)entityList).hasNext()) {
            final Object obj1 = ((Iterator<Object>)entityList).next();
            if (obj1 instanceof EntityPlayer) {
                final EntityPlayer entityPlayer = (EntityPlayer)obj1;
                final float distance = Wrapper.INSTANCE.player().getDistance((Entity)entityPlayer);
                final float health = entityPlayer.getHealth();
                String str1 = String.valueOf(new StringBuilder().append(" §2[").append(RenderUtils.DF(health, 0)).append("]"));
                if ((dcmpl(health, 12.0)) >= 0) {
                    str1 = String.valueOf(new StringBuilder().append(" §2[").append(RenderUtils.DF(health, 0)).append("]"));;
                }
                else if ((dcmpl(health, 4.0)) >= 0) {
                    str1 = String.valueOf(new StringBuilder().append(" §6[").append(RenderUtils.DF(health, 0)).append("]"));
                }
                else {
                    str1 = String.valueOf(new StringBuilder().append(" §4[").append(RenderUtils.DF(health, 0)).append("]"));
                }
                final String name = entityPlayer.getGameProfile().getName();
                final String str2 = String.valueOf(new StringBuilder().append(name).append(str1).append(" §7[").append(RenderUtils.DF(distance, 0)).append("]"));
                int color2 = 0;
                if (entityPlayer.isInvisible()) {
                    final int color = ColorUtils.color(155, 155, 155, 255);
                }
                else {
                    color2 = ColorUtils.color(255, 255, 255, 255);
                }
                Wrapper.INSTANCE.fontRenderer().drawString(str2, scaledResolution.getScaledWidth() - Wrapper.INSTANCE.fontRenderer().getStringWidth(str2), int1, color2);
                int1 += 12;
            }
        }
        super.onRenderGameOverlay(event);
    }

}
