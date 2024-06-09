package com.client.glowclient.modules.render;

import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import com.client.glowclient.modules.other.*;
import net.minecraft.client.renderer.entity.*;
import com.client.glowclient.events.*;
import java.util.function.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import com.client.glowclient.modules.*;
import com.client.glowclient.utils.*;
import com.client.glowclient.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.item.*;
import java.util.*;

public class EntityESP extends ModuleContainer
{
    public static BooleanValue players;
    public static BooleanValue items;
    public static BooleanValue everything;
    public static nB mode;
    public static BooleanValue passive;
    public static BooleanValue hostile;
    public static final NumberValue width;
    
    public void M(final EntityLivingBase entityLivingBase, double n, double n2, final double n3, final ca ca) {
        final RenderManager renderManager = Wrapper.mc.getRenderManager();
        n2 += (entityLivingBase.isSneaking() ? 0.5 : 0.7);
        final float n4 = 1.0f * 0.01f;
        GL11.glPushMatrix();
        GlStateManager.translate((double)(float)n, (float)n2 + 1.4, (double)(float)n3);
        final float n5 = 1.0f;
        final float n6 = 0.0f;
        GL11.glNormal3f(n6, n5, n6);
        final float n7 = n4;
        final float n8 = -renderManager.playerViewY;
        final float n9 = 1.0f;
        final float n10 = 0.0f;
        GL11.glRotatef(n8, n10, n9, n10);
        GL11.glScalef(-n7, -n4, n4 / 100.0f);
        fd.M(2896, false);
        fd.M(2929, false);
        final int n11 = 117;
        fd.M(3042, true);
        final int n12 = -1;
        final int n13 = 0;
        ba.M(n12, n12, n12, n12, ga.M(n13, n13, n13, n13));
        GL11.glBlendFunc(770, 771);
        n = -n11 / 2;
        n2 = -Ia.M(HUD.F) - 1.0;
        int n14 = ga.M(HUD.red.M(), HUD.green.M(), HUD.blue.M(), 150);
        if (Va.M().M(entityLivingBase.getName())) {
            final int n15 = 0;
            final int n16 = 255;
            n14 = ga.M(n15, n16, n16, 150);
        }
        if (wa.M().M(entityLivingBase.getName())) {
            final int n17 = 220;
            final int n18 = 0;
            n14 = ga.M(n17, n18, n18, 150);
        }
        if (HUD.F != null) {
            final int n19 = 7;
            final int n20 = 3;
            final ca m = ca.K().d().M(ca::D).M(ca::d).M(ca::k).M();
            final int n21 = 0;
            m.D(ga.M(n21, n21, n21, 255)).k(n - 3.0, n2 + 22.0 - 3.0, 45.0, n20).k(n - 3.0, n2 + 22.0 - 3.0 + n20, n20, 45 - n20).k(n - 3.0, n2 + 180.0 + 3.0, n20, 45 - n20).k(n - 3.0, n2 + 180.0 + 3.0 + 45.0 - n20, 45.0, n20).k(n + 110.0 + 3.0 + 4.0, n2 + 180.0 + 3.0, n20, 45 - n20).k(n + 110.0 + 3.0 + 4.0 - 45.0 + n20, n2 + 180.0 + 3.0 + 45.0 - n20, 45.0, n20).k(n + 110.0 + 3.0 + 4.0 - 45.0 + n20, n2 + 22.0 - 3.0, 45.0, n20).k(n + 110.0 + 3.0 + 4.0, n2 + 22.0 - 3.0 + n20, n20, 45 - n20).D(n14).k(n, n2 + 22.0, 45.0, n19).k(n, n2 + 22.0 + n19, n19, 45 - n19).k(n, n2 + 180.0, n19, 45 - n19).k(n, n2 + 180.0 + 45.0 - n19, 45.0, n19).k(n + 110.0, n2 + 180.0, n19, 45 - n19).k(n + 110.0 - 45.0 + n19, n2 + 180.0 + 45.0 - n19, 45.0, n19).k(n + 110.0 - 45.0 + n19, n2 + 22.0, 45.0, n19).k(n + 110.0, n2 + 22.0 + n19, n19, 45 - n19).A().M(ca::E).M(ca::A).M(ca::a).B();
        }
        GL11.glPushMatrix();
        GL11.glPopMatrix();
        final float n22 = 1.0f;
        GL11.glColor4f(n22, n22, n22, 0.0f);
        GL11.glPopMatrix();
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void D(final EventRenderWorld eventRenderWorld) {
        try {
            Wrapper.mc.world.loadedEntityList.stream().filter(If::A).filter(Y::i).filter(If::D).filter(Y::a).filter(Y::j).filter(If::M).map(If::M).forEach(this::M);
        }
        catch (Exception ex) {}
    }
    
    private static boolean A(final Entity entity) {
        return entity != Wrapper.mc.world.getEntityByID(-100);
    }
    
    static {
        EntityESP.players = ValueFactory.M("EntityESP", "Players", "Shows Players", true);
        EntityESP.hostile = ValueFactory.M("EntityESP", "Hostile", "Shows Mobs", false);
        EntityESP.passive = ValueFactory.M("EntityESP", "Passive", "Shows Passives", false);
        EntityESP.items = ValueFactory.M("EntityESP", "Items", "Show Items", true);
        EntityESP.everything = ValueFactory.M("EntityESP", "Everything", "Outlines All Entities - white", false);
        EntityESP.mode = ValueFactory.M("EntityESP", "Mode", "Mode of EntityESP", "Box", "Box", "Outline", "CSGO");
        final String s = "EntityESP";
        final String s2 = "Width";
        final String s3 = "Width of outline";
        final double n = 3.0;
        final double n2 = 1.0;
        width = ValueFactory.M(s, s2, s3, n, n2, n2, 10.0);
    }
    
    private static EntityLivingBase M(final Entity entity) {
        return (EntityLivingBase)entity;
    }
    
    private void M(double n, double n2, double n3, final EntityLivingBase entityLivingBase) {
        if (entityLivingBase == null) {
            return;
        }
        if (entityLivingBase instanceof EntityBat) {
            return;
        }
        if (!EntityESP.players.M() && entityLivingBase instanceof EntityPlayer) {
            return;
        }
        if (!EntityESP.hostile.M()) {
            if (entityLivingBase instanceof EntityMob) {
                return;
            }
            if (entityLivingBase instanceof EntityGhast) {
                return;
            }
            if (entityLivingBase instanceof EntityMagmaCube) {
                return;
            }
            if (entityLivingBase instanceof EntitySlime) {
                return;
            }
        }
        if (!EntityESP.passive.M()) {
            if (entityLivingBase instanceof EntityAnimal) {
                return;
            }
            if (entityLivingBase instanceof EntityArmorStand) {
                return;
            }
            if (entityLivingBase instanceof EntityGolem) {
                return;
            }
            if (entityLivingBase instanceof EntityIronGolem) {
                return;
            }
            if (entityLivingBase instanceof EntitySquid) {
                return;
            }
        }
        n = entityLivingBase.lastTickPosX + (entityLivingBase.posX - entityLivingBase.lastTickPosX) * Wrapper.mc.getRenderPartialTicks() - n;
        n2 = entityLivingBase.lastTickPosY + (entityLivingBase.posY - entityLivingBase.lastTickPosY) * Wrapper.mc.getRenderPartialTicks() - n2;
        n3 = entityLivingBase.lastTickPosZ + (entityLivingBase.posZ - entityLivingBase.lastTickPosZ) * Wrapper.mc.getRenderPartialTicks() - n3;
        if (EntityESP.mode.e().equals("CSGO")) {
            this.M(entityLivingBase, n, n2, n3, ca.m());
        }
    }
    
    @Override
    public String M() {
        if (EntityESP.mode.e().equals("Outline")) {
            return "Outline";
        }
        if (EntityESP.mode.e().equals("Box")) {
            return "Box";
        }
        if (EntityESP.mode.e().equals("CSGO")) {
            return "CSGO";
        }
        return "";
    }
    
    public EntityESP() {
        super(Category.RENDER, "EntityESP", false, -1, "Draws outline around entities");
    }
    
    private static boolean D(final Entity entity) {
        return !Objects.equals(Wrapper.mc.player, entity);
    }
    
    private static boolean M(final Entity entity) {
        return !EntityUtils.D(entity);
    }
    
    @SubscribeEvent
    public void M(final EventRenderWorld eventRenderWorld) {
        if (this.k()) {
            final Iterator<Entity> iterator = Wrapper.mc.world.loadedEntityList.iterator();
        Label_0022:
            while (true) {
                Iterator<Entity> iterator2 = iterator;
                while (iterator2.hasNext()) {
                    final Entity entity = iterator.next();
                    if (EntityESP.mode.e().equals("Box")) {
                        if (EntityESP.hostile.M() && entity instanceof EntityMob) {
                            final Entity entity2 = entity;
                            final int n = 255;
                            final int n2 = 0;
                            Ma.M(entity2, n, n2, n2);
                        }
                        if (EntityESP.passive.M() && entity instanceof EntityAnimal) {
                            final Entity entity3 = entity;
                            final int n3 = 255;
                            final int n4 = 0;
                            Ma.M(entity3, n4, n3, n4);
                        }
                        if (entity instanceof EntityPlayerSP) {
                            iterator2 = iterator;
                            continue;
                        }
                        if (EntityESP.players.M() && entity instanceof EntityPlayer) {
                            final Entity entity4 = entity;
                            final int n5 = 0;
                            Ma.M(entity4, n5, n5, 255);
                        }
                        if (EntityESP.everything.M() && entity instanceof Entity) {
                            final Entity entity5 = entity;
                            final int n6 = 255;
                            Ma.M(entity5, n6, n6, n6);
                        }
                    }
                    if (EntityESP.items.M() && entity instanceof EntityItem) {
                        final Entity entity6 = entity;
                        final int n7 = 0;
                        final int n8 = 255;
                        Ma.M(entity6, n7, n8, n8);
                        continue Label_0022;
                    }
                    continue Label_0022;
                }
                break;
            }
        }
    }
}
