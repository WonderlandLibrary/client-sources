package com.client.glowclient.modules.render;

import net.minecraft.client.network.*;
import net.minecraft.inventory.*;
import net.minecraft.nbt.*;
import net.minecraft.util.math.*;
import org.lwjgl.opengl.*;
import com.client.glowclient.modules.other.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import java.util.stream.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraftforge.client.event.*;
import com.client.glowclient.events.*;
import java.util.function.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import com.client.glowclient.utils.*;
import java.net.*;
import java.io.*;
import java.util.*;
import com.client.glowclient.*;
import com.google.gson.*;
import com.client.glowclient.modules.*;

public class Nametags extends ModuleContainer
{
    public static BooleanValue passive;
    public static BooleanValue visibility;
    public static BooleanValue shulkerInv;
    public static BooleanValue players;
    private static boolean M;
    public static final NumberValue scale;
    public static BooleanValue health;
    public static BooleanValue renderItems;
    public static BooleanValue mobs;
    public static BooleanValue heldItemName;
    public static nB scaling;
    
    private String M(final UUID uuid) {
        final NetworkPlayerInfo playerInfo;
        if ((playerInfo = Wrapper.mc.getConnection().getPlayerInfo(uuid)) != null) {
            return playerInfo.getGameProfile().getName();
        }
        return uuid.toString();
    }
    
    private static boolean M(final ItemStack itemStack) {
        return !itemStack.isEmpty();
    }
    
    private static boolean A(final Entity entity) {
        return !EntityUtils.D(entity);
    }
    
    public static List<ItemStack> M(final ItemStack itemStack) {
        final ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        final NBTTagCompound tagCompound;
        final NBTTagCompound compoundTag;
        if ((tagCompound = itemStack.getTagCompound()) != null && tagCompound.hasKey("BlockEntityTag", 10) && (compoundTag = tagCompound.getCompoundTag("BlockEntityTag")).hasKey("Items", 9)) {
            final NonNullList withSize = NonNullList.withSize(27, (Object)ItemStack.EMPTY);
            final NBTTagCompound nbtTagCompound = compoundTag;
            final NonNullList list2 = withSize;
            ItemStackHelper.loadAllItems(nbtTagCompound, list2);
            return (List<ItemStack>)list2;
        }
        return list;
    }
    
    public void M(final EntityLivingBase entityLivingBase, double n, double n2, double clamp) {
        try {
            final ca m = ca.m();
            n2 += (entityLivingBase.isSneaking() ? 0.5 : 0.7);
            float n3 = 0.0f;
            if (Nametags.scaling.e().equals("Auto")) {
                n3 = Wrapper.mc.player.getDistance((Entity)entityLivingBase) / 5.0f;
            }
            if (Nametags.scaling.e().equals("Custom")) {
                n3 = (float)Nametags.scale.k();
            }
            if (n3 < 1.625f) {
                n3 = 2.0f;
            }
            String s;
            if (Nametags.visibility.M()) {
                if (Wrapper.mc.player.canEntityBeSeen((Entity)entityLivingBase)) {
                    s = "§a [V]";
                }
                else {
                    s = "§c [V]";
                }
            }
            else {
                s = "";
            }
            String s2;
            EntityLivingBase entityLivingBase2;
            if (Nametags.M) {
                if (Ha.M(entityLivingBase)) {
                    s2 = "§a [S]";
                    entityLivingBase2 = entityLivingBase;
                }
                else {
                    s2 = "§c [S]";
                    entityLivingBase2 = entityLivingBase;
                }
            }
            else {
                s2 = "";
                entityLivingBase2 = entityLivingBase;
            }
            final String unformattedText = entityLivingBase2.getDisplayName().getUnformattedText();
            final float n4 = entityLivingBase.getHealth() + entityLivingBase.getAbsorptionAmount();
            String format;
            EntityLivingBase entityLivingBase3;
            if (Nametags.health.M()) {
                format = String.format("%.1f", n4);
                entityLivingBase3 = entityLivingBase;
            }
            else {
                format = "";
                entityLivingBase3 = entityLivingBase;
            }
            final float n5 = MathHelper.clamp(entityLivingBase3.getHealth(), 0.0f, entityLivingBase.getMaxHealth()) / entityLivingBase.getMaxHealth();
            final int n6 = (entityLivingBase.getHealth() + entityLivingBase.getAbsorptionAmount() > entityLivingBase.getMaxHealth()) ? ga.b : ga.M((int)((255.0f - n5) * 255.0f), (int)(255.0f * n5), 0, 255);
            final RenderManager renderManager = Wrapper.mc.getRenderManager();
            final float n7 = n3 * 0.01f;
            GL11.glPushMatrix();
            GlStateManager.translate((double)(float)n, (float)n2 - 0.5 + (entityLivingBase.getEntityBoundingBox().maxY - entityLivingBase.getEntityBoundingBox().minY), (double)(float)clamp);
            final float n8 = 1.0f;
            final float n9 = 0.0f;
            GL11.glNormal3f(n9, n8, n9);
            final float n10 = -renderManager.playerViewY;
            final float n11 = 1.0f;
            final float n12 = 0.0f;
            GL11.glRotatef(n10, n12, n11, n12);
            float n13;
            if (Wrapper.mc.gameSettings.thirdPersonView == 2) {
                n13 = n7;
                final float n14 = -renderManager.playerViewX;
                final float n15 = 1.0f;
                final float n16 = 0.0f;
                GL11.glRotatef(n14, n15, n16, n16);
            }
            else {
                final float playerViewX = renderManager.playerViewX;
                final float n17 = 1.0f;
                final float n18 = 0.0f;
                GL11.glRotatef(playerViewX, n17, n18, n18);
                n13 = n7;
            }
            GL11.glScalef(-n13, -n7, n7 / 100.0f);
            fd.M(2896, false);
            fd.M(2929, false);
            final int n19 = (int)Ia.M(HUD.F, new StringBuilder().insert(0, unformattedText).append(" ").append(format).append(s).append(s2).toString(), 1.0);
            fd.M(3042, true);
            final int n20 = -1;
            final int n21 = 1;
            final int n22 = 0;
            ba.M(n20, n20, n21, n21, ga.M(n22, n22, n22, n22));
            GL11.glBlendFunc(770, 771);
            n = -n19 / 2;
            n2 = -Ia.M(HUD.F) - 1.0;
            int n23 = ga.G;
            if (Va.M().M(entityLivingBase.getName())) {
                n23 = ga.d;
            }
            if (wa.M().M(entityLivingBase.getName())) {
                final int n24 = 0;
                final int n25 = 255;
                n23 = ga.M(n25, n24, n24, n25);
            }
            final ItemStack heldItemMainhand = entityLivingBase.getHeldItemMainhand();
            final ResourceLocation resourceLocation = new ResourceLocation("textures/gui/container/shulker_box.png");
            if (Nametags.shulkerInv.M()) {
                if (heldItemMainhand.getItem() instanceof ItemShulkerBox) {
                    final float n26 = 1.0f;
                    GL11.glColor4f(n26, n26, n26, n26);
                    GlStateManager.enableAlpha();
                    Wrapper.mc.getTextureManager().bindTexture(resourceLocation);
                    ba.M(-88, -44, 0, 160, 176, 6, 0);
                    final int n27 = -88;
                    final int n28 = -120;
                    final float n29 = 0.0f;
                    final int n30 = 176;
                    final int n31 = 76;
                    final float n32 = 256.0f;
                    Gui.drawModalRectWithCustomSizedTexture(n27, n28, n29, n29, n30, n31, n32, n32);
                    Ia.M(null, heldItemMainhand.getDisplayName(), -80.0, -115.0, false, ga.k, 1.0);
                    final List<ItemStack> i;
                    final int n33 = (i = M(heldItemMainhand)).size() / 9 + ((i.size() % 9 != 0) ? 1 : 0);
                    final int size = i.size();
                    int n34;
                    int j = n34 = 0;
                    while (j < size) {
                        final int n35 = -80;
                        final int n36 = -176;
                        GlStateManager.clear(256);
                        final RenderItem renderItem = Wrapper.mc.getRenderItem();
                        final ItemStack itemStack = i.get(n34);
                        final int n37 = n34 % 9 * 18 + n35;
                        final int n38 = n33 * 18 + (n34 / 9 + 1) * 18 + n36;
                        final int n39 = 1;
                        ++n34;
                        this.M(renderItem, itemStack, n37, n38 + n39);
                        GlStateManager.disableLighting();
                        j = n34;
                    }
                }
                else if (!heldItemMainhand.isEmpty && Nametags.heldItemName.M()) {
                    Ia.M(HUD.F, heldItemMainhand.getDisplayName(), -Ia.M(HUD.F, heldItemMainhand.getDisplayName(), 0.5) / 2.0, n2 + 10.0, true, ga.G, 0.5);
                }
            }
            else if (!heldItemMainhand.isEmpty && Nametags.heldItemName.M()) {
                Ia.M(HUD.F, heldItemMainhand.getDisplayName(), -Ia.M(HUD.F, heldItemMainhand.getDisplayName(), 0.5) / 2.0, n2 + 10.0, true, ga.G, 0.5);
            }
            final ea f = HUD.F;
            final ca ca = m;
            if (f != null) {
                final ca k = ca.K().d().M(com.client.glowclient.ca::D).M(com.client.glowclient.ca::d).M(com.client.glowclient.ca::k).M();
                final int n40 = 0;
                k.D(ga.M(n40, n40, n40, 100)).k(n - 2.0, n2 - 2.0, Ia.M(HUD.F, new StringBuilder().insert(0, unformattedText).append(" ").append(format).append(s).append(s2).toString(), 1.0) + 4.0, Ia.M(HUD.F) + 4.0).A().M(com.client.glowclient.ca::A).M(com.client.glowclient.ca::E).M(HUD.F).D(n23).M(unformattedText, n + 1.0, n2 - 1.0 + 1.0, true).D(n23).M(unformattedText, n, n2 - 1.0).D(n6).M(format, n + Ia.M(HUD.F, new StringBuilder().insert(0, unformattedText).append(" ").toString(), 1.0) + 1.0, n2 - 1.0 + 1.0, true).D(n6).M(format, n + Ia.M(HUD.F, new StringBuilder().insert(0, unformattedText).append(" ").toString(), 1.0), n2 - 1.0).D(ga.K).M(s, n + Ia.M(HUD.F, new StringBuilder().insert(0, unformattedText).append(" ").append(format).toString(), 1.0) + 1.0, n2 - 1.0 + 1.0, true).D(ga.K).M(s, n + Ia.M(HUD.F, new StringBuilder().insert(0, unformattedText).append(" ").append(format).toString(), 1.0), n2 - 1.0).D(ga.K).M(s2, n + Ia.M(HUD.F, new StringBuilder().insert(0, unformattedText).append(" ").append(format).append(s).toString(), 1.0) + 1.0, n2 - 1.0 + 1.0, true).D(ga.K).M(s2, n + Ia.M(HUD.F, new StringBuilder().insert(0, unformattedText).append(" ").append(format).append(s).toString(), 1.0), n2 - 1.0).M(com.client.glowclient.ca::E).M(com.client.glowclient.ca::A).M(com.client.glowclient.ca::a).B();
                final EntityLivingBase entityLivingBase4 = entityLivingBase;
            }
            else {
                final ca l = ca.K().d().M(com.client.glowclient.ca::D).M(com.client.glowclient.ca::d).M(com.client.glowclient.ca::k).M();
                final int n41 = 0;
                l.D(ga.M(n41, n41, n41, 100)).k(n - 2.0, n2 - 3.0, Ia.M(HUD.F, new StringBuilder().insert(0, unformattedText).append(" ").append(format).append(s).toString(), 1.0) + 4.0, Ia.M(HUD.F) + 4.0).A().M(com.client.glowclient.ca::A).M(com.client.glowclient.ca::E).M(HUD.F).D(n23).M(unformattedText, n, n2 - 1.0, true).D(n6).M(format, n + Ia.M(HUD.F, new StringBuilder().insert(0, unformattedText).append(" ").toString(), 1.0), n2 - 1.0, true).D(ga.K).M(s, n + Ia.M(HUD.F, new StringBuilder().insert(0, unformattedText).append(" ").append(format).toString(), 1.0), n2 - 1.0, true).D(ga.K).M(s2, n + Ia.M(HUD.F, new StringBuilder().insert(0, unformattedText).append(" ").append(format).append(s).toString(), 1.0), n2 - 1.0, true).M(com.client.glowclient.ca::E).M(com.client.glowclient.ca::A).M(com.client.glowclient.ca::a).B();
                final EntityLivingBase entityLivingBase4 = entityLivingBase;
            }
            EntityLivingBase entityLivingBase4;
            if (entityLivingBase4 instanceof EntityHorse || entityLivingBase instanceof EntityDonkey || entityLivingBase instanceof EntityMule || entityLivingBase instanceof EntitySkeletonHorse || entityLivingBase instanceof EntityZombieHorse) {
                clamp = MathHelper.clamp(entityLivingBase.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue(), 0.1125, 0.3375);
                final int n42 = (entityLivingBase.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() > 0.325) ? ga.d : ga.M(0, (int)(255.0 * (clamp * 2.5)), (int)((255.0 - clamp * 2.5) * 255.0), 255);
                final String format2 = String.format("%.2f", entityLivingBase.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * 42.1422829447);
                final AbstractHorse abstractHorse = (AbstractHorse)entityLivingBase;
                final IAttribute jump_STRENGTH;
                final IAttribute attribute = jump_STRENGTH = AbstractHorse.JUMP_STRENGTH;
                final double attributeValue = entityLivingBase.getEntityAttribute(jump_STRENGTH).getAttributeValue();
                final double clamp2 = MathHelper.clamp(entityLivingBase.getEntityAttribute(attribute).getAttributeValue(), 0.4, 1.0);
                final int n43 = (entityLivingBase.getEntityAttribute(jump_STRENGTH).getAttributeValue() > 0.967) ? ga.d : ga.M(0, (int)(255.0 * (clamp2 * 0.8)), (int)((255.0 - clamp2 * 0.8) * 255.0), 255);
                final String format3 = String.format("%.2f", -0.1817584952 * Math.pow(attributeValue, 3.0) + 3.689713992 * Math.pow(attributeValue, 2.0) + 2.128599134 * attributeValue - 0.343930367);
                final ca m2 = m.K().d().M(com.client.glowclient.ca::D).M(com.client.glowclient.ca::d).M(com.client.glowclient.ca::k).M();
                final int n44 = 0;
                final ca m3 = m2.D(ga.M(n44, n44, n44, 100)).k(n - 2.0, n2 - 8.0, Ia.M(HUD.F, new StringBuilder().insert(0, "Speed: ").append(format2).append("m/s").toString(), 0.7) + 4.0, 6.0).A().M(com.client.glowclient.ca::E).M(com.client.glowclient.ca::k).M();
                final int n45 = 0;
                m3.D(ga.M(n45, n45, n45, 100)).k(n - 2.0, n2 - 14.0, Ia.M(HUD.F, new StringBuilder().insert(0, "Jump: ").append(format3).append("m").toString(), 0.7) + 4.0, 6.0).A().M(com.client.glowclient.ca::E).M(com.client.glowclient.ca::E).M(com.client.glowclient.ca::A).M(com.client.glowclient.ca::a).B();
                Ia.M(HUD.F, new StringBuilder().insert(0, format2).append("m/s").toString(), n + Ia.M(HUD.F, "Speed: ", 0.7), n2 - 8.0, true, n42, 0.7);
                Ia.M(HUD.F, "Speed: ", n, n2 - 8.0, true, ga.G, 0.7);
                Ia.M(HUD.F, new StringBuilder().insert(0, format3).append("m").toString(), n + Ia.M(HUD.F, "Jump: ", 0.7), n2 - 14.0, true, n43, 0.7);
                Ia.M(HUD.F, "Jump: ", n, n2 - 14.0, true, ga.G, 0.7);
            }
            if (entityLivingBase instanceof EntityHorse || entityLivingBase instanceof EntityDonkey || entityLivingBase instanceof EntityMule || entityLivingBase instanceof EntityLlama || entityLivingBase instanceof EntityOcelot || entityLivingBase instanceof EntityParrot || entityLivingBase instanceof EntityWolf) {
                final UUID ownerId;
                if (entityLivingBase instanceof EntityWolf && ((EntityWolf)entityLivingBase).getOwnerId() != null && (ownerId = ((EntityWolf)entityLivingBase).getOwnerId()) != null) {
                    String s3 = this.M(((EntityWolf)entityLivingBase).getOwnerId());
                    if (Wrapper.mc.world.getPlayerEntityByUUID(ownerId) != null && Wrapper.mc.world.getPlayerEntityByUUID(ownerId).getName() != null) {
                        s3 = Wrapper.mc.world.getPlayerEntityByUUID(ownerId).getName();
                    }
                    final ca m4 = m.K().d().M(com.client.glowclient.ca::D).M(com.client.glowclient.ca::d).M(com.client.glowclient.ca::k).M();
                    final int n46 = 0;
                    m4.D(ga.M(n46, n46, n46, 100)).k(n - 2.0, n2 - 10.0, Ia.M(HUD.F, new StringBuilder().insert(0, "Owner: ").append(s3).toString(), 0.7) + 4.0, 8.0).A().M(com.client.glowclient.ca::E).M(com.client.glowclient.ca::E).M(com.client.glowclient.ca::A).M(com.client.glowclient.ca::a).B();
                    Ia.M(HUD.F, new StringBuilder().insert(0, "Owner: ").append(s3).toString(), n, n2 - 8.0, true, ga.G, 0.7);
                }
                final UUID ownerId2;
                if (entityLivingBase instanceof EntityOcelot && ((EntityOcelot)entityLivingBase).getOwnerId() != null && (ownerId2 = ((EntityOcelot)entityLivingBase).getOwnerId()) != null) {
                    String s4 = this.M(((EntityOcelot)entityLivingBase).getOwnerId());
                    if (Wrapper.mc.world.getPlayerEntityByUUID(ownerId2) != null && Wrapper.mc.world.getPlayerEntityByUUID(ownerId2).getName() != null) {
                        s4 = Wrapper.mc.world.getPlayerEntityByUUID(ownerId2).getName();
                    }
                    final ca m5 = m.K().d().M(com.client.glowclient.ca::D).M(com.client.glowclient.ca::d).M(com.client.glowclient.ca::k).M();
                    final int n47 = 0;
                    m5.D(ga.M(n47, n47, n47, 100)).k(n - 2.0, n2 - 10.0, Ia.M(HUD.F, new StringBuilder().insert(0, "Owner: ").append(s4).toString(), 0.7) + 4.0, 8.0).A().M(com.client.glowclient.ca::E).M(com.client.glowclient.ca::E).M(com.client.glowclient.ca::A).M(com.client.glowclient.ca::a).B();
                    Ia.M(HUD.F, new StringBuilder().insert(0, "Owner: ").append(s4).toString(), n, n2 - 8.0, true, ga.G, 0.7);
                }
                final UUID ownerId3;
                if (entityLivingBase instanceof EntityParrot && ((EntityParrot)entityLivingBase).getOwnerId() != null && (ownerId3 = ((EntityParrot)entityLivingBase).getOwnerId()) != null) {
                    String s5 = this.M(((EntityParrot)entityLivingBase).getOwnerId());
                    if (Wrapper.mc.world.getPlayerEntityByUUID(ownerId3) != null && Wrapper.mc.world.getPlayerEntityByUUID(ownerId3).getName() != null) {
                        s5 = Wrapper.mc.world.getPlayerEntityByUUID(ownerId3).getName();
                    }
                    final ca m6 = m.K().d().M(com.client.glowclient.ca::D).M(com.client.glowclient.ca::d).M(com.client.glowclient.ca::k).M();
                    final int n48 = 0;
                    m6.D(ga.M(n48, n48, n48, 100)).k(n - 2.0, n2 - 10.0, Ia.M(HUD.F, new StringBuilder().insert(0, "Owner: ").append(s5).toString(), 0.7) + 4.0, 8.0).A().M(com.client.glowclient.ca::E).M(com.client.glowclient.ca::E).M(com.client.glowclient.ca::A).M(com.client.glowclient.ca::a).B();
                    Ia.M(HUD.F, new StringBuilder().insert(0, "Owner: ").append(s5).toString(), n, n2 - 8.0, true, ga.G, 0.7);
                }
                if (entityLivingBase instanceof AbstractHorse && ((AbstractHorse)entityLivingBase).getOwnerUniqueId() != null) {
                    if (entityLivingBase instanceof EntityLlama) {
                        final UUID ownerUniqueId;
                        if ((ownerUniqueId = ((AbstractHorse)entityLivingBase).getOwnerUniqueId()) != null) {
                            String s6 = this.M(((AbstractHorse)entityLivingBase).getOwnerUniqueId());
                            if (Wrapper.mc.world.getPlayerEntityByUUID(ownerUniqueId) != null && Wrapper.mc.world.getPlayerEntityByUUID(ownerUniqueId).getName() != null) {
                                s6 = Wrapper.mc.world.getPlayerEntityByUUID(ownerUniqueId).getName();
                            }
                            final ca m7 = m.K().d().M(com.client.glowclient.ca::D).M(com.client.glowclient.ca::d).M(com.client.glowclient.ca::k).M();
                            final int n49 = 0;
                            m7.D(ga.M(n49, n49, n49, 100)).k(n - 2.0, n2 - 10.0, Ia.M(HUD.F, new StringBuilder().insert(0, "Owner: ").append(s6).toString(), 0.7) + 4.0, 8.0).A().M(com.client.glowclient.ca::E).M(com.client.glowclient.ca::E).M(com.client.glowclient.ca::A).M(com.client.glowclient.ca::a).B();
                            Ia.M(HUD.F, new StringBuilder().insert(0, "Owner: ").append(s6).toString(), n, n2 - 8.0, true, ga.G, 0.7);
                        }
                    }
                    else {
                        final UUID ownerUniqueId2;
                        if ((ownerUniqueId2 = ((AbstractHorse)entityLivingBase).getOwnerUniqueId()) != null) {
                            String s7 = this.M(((AbstractHorse)entityLivingBase).getOwnerUniqueId());
                            if (Wrapper.mc.world.getPlayerEntityByUUID(ownerUniqueId2) != null && Wrapper.mc.world.getPlayerEntityByUUID(ownerUniqueId2).getName() != null) {
                                s7 = Wrapper.mc.world.getPlayerEntityByUUID(ownerUniqueId2).getName();
                            }
                            final ca m8 = m.K().d().M(com.client.glowclient.ca::D).M(com.client.glowclient.ca::d).M(com.client.glowclient.ca::k).M();
                            final int n50 = 0;
                            m8.D(ga.M(n50, n50, n50, 100)).k(n - 2.0, n2 - 22.0, Ia.M(HUD.F, new StringBuilder().insert(0, "Owner: ").append(s7).toString(), 0.7) + 4.0, 8.0).A().M(com.client.glowclient.ca::E).M(com.client.glowclient.ca::E).M(com.client.glowclient.ca::A).M(com.client.glowclient.ca::a).B();
                            Ia.M(HUD.F, new StringBuilder().insert(0, "Owner: ").append(s7).toString(), n, n2 - 20.0, true, ga.G, 0.7);
                        }
                    }
                }
            }
            GlStateManager.clear(256);
            final List<? super Object> list;
            if (!(list = StreamSupport.stream(entityLivingBase.getEquipmentAndArmor().spliterator(), false).filter(Objects::nonNull).filter((Predicate<? super Object>)QD::M).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList())).isEmpty()) {
                final double n51 = -(16.0 * list.size() / 2.0);
                final double n52 = n2 + 10.0 - 16.0;
                int n54;
                int n53 = n54 = 0;
                while (n53 < list.size()) {
                    final double n55 = n51 + n54 * 16.0;
                    final ItemStack itemStack2 = list.get(n54);
                    if (Nametags.renderItems.M()) {
                        m.K().d().M(com.client.glowclient.ca::B).M(com.client.glowclient.ca::e).M(itemStack2, n55, n52 - 12.0).D(itemStack2, n55, n52 - 12.0).M(com.client.glowclient.ca::M).B();
                    }
                    final double n56 = n51 + n54 * 17;
                    double n57 = n52 - 15.0;
                    final List<X> m9;
                    if ((m9 = Ea.M(itemStack2.getEnchantmentTagList())) != null) {
                        final Iterator<X> iterator = m9.iterator();
                    Label_4171:
                        while (true) {
                            Iterator<X> iterator2 = iterator;
                            while (iterator2.hasNext()) {
                                final X x = iterator.next();
                                if (Nametags.renderItems.M()) {
                                    Ia.M(HUD.F, x.M(), n56, n57, true, ga.G, 0.5);
                                }
                                final double n58 = n57 -= 4.5;
                                if (n58 > n58) {
                                    continue Label_4171;
                                }
                                iterator2 = iterator;
                            }
                            break;
                        }
                    }
                    n53 = ++n54;
                }
            }
            GL11.glPushMatrix();
            GL11.glPopMatrix();
            fd.M(2929, true);
            final float n59 = 1.0f;
            GL11.glColor4f(n59, n59, n59, n59);
            GL11.glPopMatrix();
        }
        catch (Exception ex) {}
    }
    
    public static void M(final int n, final int n2, final float n3, final float n4, final int n5, final int n6, float n7, float n8) {
        n7 = 1.0f / n7;
        n8 = 1.0f / n8;
        final Tessellator instance = Tessellator.getInstance();
        final BufferBuilder buffer;
        final BufferBuilder bufferBuilder2;
        final BufferBuilder bufferBuilder = bufferBuilder2 = (buffer = instance.getBuffer());
        bufferBuilder2.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder2.pos((double)n, (double)(n2 + n6), 0.0).tex((double)(n3 * n7), (double)((n4 + n6) * n8)).endVertex();
        bufferBuilder.pos((double)(n + n5), (double)(n2 + n6), 0.0).tex((double)((n3 + n5) * n7), (double)((n4 + n6) * n8)).endVertex();
        buffer.pos((double)(n + n5), (double)n2, 0.0).tex((double)((n3 + n5) * n7), (double)(n4 * n8)).endVertex();
        buffer.pos((double)n, (double)n2, 0.0).tex((double)(n3 * n7), (double)(n4 * n8)).endVertex();
        instance.draw();
    }
    
    @SubscribeEvent
    public void M(final RenderLivingEvent$Specials$Pre renderLivingEvent$Specials$Pre) {
        if (EntityUtils.i((Entity)renderLivingEvent$Specials$Pre.getEntity())) {
            renderLivingEvent$Specials$Pre.setCanceled(true);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void M(final EventRenderWorld eventRenderWorld) {
        try {
            Wrapper.mc.world.loadedEntityList.stream().filter(QD::M).filter(Y::i).filter(QD::D).filter(Y::a).filter(Y::j).filter(QD::A).map(QD::M).forEach(this::M);
        }
        catch (Exception ex) {}
    }
    
    private void M(final RenderItem renderItem, final ItemStack itemStack, final int n, final int n2) {
        FontRenderer fontRenderer;
        if ((fontRenderer = itemStack.getItem().getFontRenderer(itemStack)) == null) {
            fontRenderer = Wrapper.mc.fontRenderer;
        }
        GlStateManager.enableDepth();
        renderItem.zLevel = 120.0f;
        RenderHelper.enableGUIStandardItemLighting();
        renderItem.renderItemAndEffectIntoGUI(itemStack, n, n2);
        final String s = (itemStack.getCount() == 1) ? "" : String.valueOf(itemStack.getCount());
        final float zLevel = 0.0f;
        renderItem.renderItemOverlayIntoGUI(fontRenderer, itemStack, n, n2, s);
        renderItem.zLevel = zLevel;
    }
    
    private void M(double n, double n2, double n3, final EntityLivingBase entityLivingBase) {
        if (entityLivingBase == null) {
            return;
        }
        if (entityLivingBase instanceof EntityBat) {
            return;
        }
        if (!Nametags.players.M() && entityLivingBase instanceof EntityPlayer) {
            return;
        }
        if (!Nametags.mobs.M()) {
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
        if (!Nametags.passive.M()) {
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
        this.M(entityLivingBase, n, n2, n3);
    }
    
    static {
        Nametags.players = ValueFactory.M("Nametags", "Players", "Shows Players", true);
        Nametags.mobs = ValueFactory.M("Nametags", "Mobs", "Shows Mobs", false);
        Nametags.passive = ValueFactory.M("Nametags", "Passive", "Shows Passive", false);
        Nametags.scaling = ValueFactory.M("Nametags", "Scaling", "Scaling source of nametags", "Auto", "Auto", "Custom");
        Nametags.visibility = ValueFactory.M("Nametags", "Visibility", "Visibility Detector", false);
        Nametags.M = false;
        Nametags.health = ValueFactory.M("Nametags", "Health", "Entity Health", true);
        Nametags.renderItems = ValueFactory.M("Nametags", "RenderItems", "Equipped items", true);
        Nametags.heldItemName = ValueFactory.M("Nametags", "HeldItemName", "Gives the name of the held item", true);
        Nametags.shulkerInv = ValueFactory.M("Nametags", "ShulkerInv", "Shows the inventory of a held shulker box above the tag.", true);
        final String s = "Nametags";
        final String s2 = "Scale";
        final String s3 = "Scale of nametag";
        final double n = 1.0;
        scale = ValueFactory.M(s, s2, s3, n, n, 0.0, 25.0);
    }
    
    private static boolean D(final Entity entity) {
        return !Objects.equals(Wrapper.mc.player, entity);
    }
    
    private static String M(final String s) {
        final LinkedHashMap<String, Long> linkedHashMap = new LinkedHashMap<String, Long>();
        try {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(new StringBuilder().insert(0, "https://api.mojang.com/user/profiles/").append(s.replace("-", "")).append("/names").toString()).openConnection().getInputStream()));
            final StringBuilder sb = new StringBuilder();
            BufferedReader bufferedReader2 = bufferedReader;
            String line;
            while ((line = bufferedReader2.readLine()) != null) {
                bufferedReader2 = bufferedReader;
                sb.append(line + "\n");
            }
            final String string = sb.toString();
            bufferedReader.close();
            final JsonArray asJsonArray = new JsonParser().parse(string).getAsJsonArray();
            final JsonObject asJsonObject;
            final String asString = (asJsonObject = asJsonArray.get(asJsonArray.size() - 1).getAsJsonObject()).get("name").getAsString();
            try {
                asJsonObject.get("changedToAt");
                final Calendar instance = Calendar.getInstance();
                final LinkedHashMap<String, Long> linkedHashMap2 = linkedHashMap;
                final Calendar calendar = instance;
                final JsonObject jsonObject = asJsonObject;
                final String s2 = "\u0003\u001e\u0001\u0018\u0007\u0013\u0004\"\u000f7\u0014";
                final Calendar calendar2 = instance;
                calendar2.setTimeInMillis(asJsonObject.get("changedToAt").getAsLong());
                final long asLong = jsonObject.get(kB.M(s2)).getAsLong();
                calendar2.get(1);
                calendar.get(2);
                calendar.get(5);
                linkedHashMap2.put(asString, asLong);
                return asString;
            }
            catch (Exception ex2) {
                return asString;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.print("fuck");
            return s;
        }
    }
    
    public Nametags() {
        super(Category.RENDER, "Nametags", false, -1, "Bigger and Better nametags");
    }
    
    private static EntityLivingBase M(final Entity entity) {
        return (EntityLivingBase)entity;
    }
    
    private static boolean M(final Entity entity) {
        return entity != Wrapper.mc.world.getEntityByID(-100);
    }
}
