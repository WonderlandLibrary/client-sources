package com.krazzzzymonkey.catalyst.module.modules.render;

import java.util.Iterator;
import com.krazzzzymonkey.catalyst.managers.HackManager;
import com.krazzzzymonkey.catalyst.managers.FriendManager;
import net.minecraft.entity.Entity;
import com.krazzzzymonkey.catalyst.utils.visual.RenderUtils;
import com.krazzzzymonkey.catalyst.managers.EnemyManager;
import com.krazzzzymonkey.catalyst.utils.Utils;
import net.minecraft.entity.player.EntityPlayer;
import com.krazzzzymonkey.catalyst.utils.ValidUtils;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.EntityLivingBase;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import com.krazzzzymonkey.catalyst.module.ModuleCategory;
import com.krazzzzymonkey.catalyst.module.Modules;

public class ESP extends Modules
{

    public ESP() {
        super("ESP", ModuleCategory.RENDER);
    }
    
    
    @Override
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        final Exception exception = (Exception)Wrapper.INSTANCE.world().loadedEntityList.iterator();
        while (((Iterator)exception).hasNext()) {
            final Object obj = ((Iterator<Object>)exception).next();
            if ((obj instanceof EntityLivingBase) && !((obj instanceof EntityArmorStand) == 0)) {
                final EntityLivingBase entityLivingBase = (EntityLivingBase)obj;
                this.render(entityLivingBase, event.getPartialTicks());
            }

        }
        super.onRenderWorldLast(event);
    }
    
    void render(final EntityLivingBase entityLivingBase, final float inp) {
        if ((ValidUtils.isValidEntity(entityLivingBase)) || (entityLivingBase == Wrapper.INSTANCE.player())) {
            return;
        }
        if (entityLivingBase instanceof EntityPlayer) {
            final EntityPlayer entityPlayer = (EntityPlayer)entityLivingBase;
            final String name = Utils.getPlayerName(entityPlayer);
            if (EnemyManager.enemysList.contains(name)) {
                RenderUtils.drawESP((Entity)entityLivingBase, 0.8f, 0.3f, 0.0f, 1.0f, inp);
                return;
            }
            if (FriendManager.friendsList.contains(name)) {
                RenderUtils.drawESP((Entity)entityLivingBase, 0.0f, 0.7f, 1.0f, 1.0f, inp);
                return;
            }
        }
        if (HackManager.getHack("Targets").isToggledValue("Murder")) {
            if (Utils.isMurder(entityLivingBase)) {
                RenderUtils.drawESP((Entity)entityLivingBase, 1.0f, 0.0f, 0.8f, 1.0f, inp);
                return;
            }
            if (Utils.isDetect(entityLivingBase)) {
                RenderUtils.drawESP((Entity)entityLivingBase, 0.0f, 0.0f, 1.0f, 1.0f, inp);
                return;
            }
        }
        if (entityLivingBase.isInvisible()) {
            RenderUtils.drawESP((Entity)entityLivingBase, 0.0f, 0.0f, 0.0f, 1.0f, inp);
            return;
        }
        if (entityLivingBase.hurtTime > 0) {
            RenderUtils.drawESP((Entity)entityLivingBase, 1.0f, 0.0f, 0.0f, 1.0f, inp);
            return;
        }
        RenderUtils.drawESP((Entity)entityLivingBase, 1.0f, 1.0f, 1.0f, 1.0f, inp);
    }
    
}
