/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.combat;

import cc.diablo.Main;
import cc.diablo.event.impl.OverlayEvent;
import cc.diablo.event.impl.UpdateEvent;
import cc.diablo.font.TTFFontRenderer;
import cc.diablo.helpers.DiscordRPCHelper;
import cc.diablo.helpers.MathHelper;
import cc.diablo.helpers.PacketHelper;
import cc.diablo.helpers.Stopwatch;
import cc.diablo.helpers.player.EntityHelper;
import cc.diablo.helpers.player.KillAuraHelper;
import cc.diablo.helpers.render.ChatHelper;
import cc.diablo.helpers.render.ColorHelper;
import cc.diablo.helpers.render.RenderUtils;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.setting.impl.BooleanSetting;
import cc.diablo.setting.impl.ModeSetting;
import cc.diablo.setting.impl.NumberSetting;
import com.google.common.eventbus.Subscribe;
import java.awt.Color;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class KillAura
extends Module {
    public ModeSetting rotationMode = new ModeSetting("Rotations", "Smooth", "Smooth", "Watchdog", "Aids", "Legit", "None");
    public ModeSetting autoBlockMode = new ModeSetting("Auto Block", "Fake", "Hypixel", "Fake", "Verus", "Legit");
    public BooleanSetting autoBlock = new BooleanSetting("Auto Block", false);
    public BooleanSetting blockAttack = new BooleanSetting("Block & Attack", false);
    public NumberSetting range = new NumberSetting("Range", 3.7, 3.0, 6.0, 0.1);
    public NumberSetting minCPS = new NumberSetting("Min CPS", 9.0, 1.0, 20.0, 1.0);
    public NumberSetting maxCPS = new NumberSetting("Max CPS", 14.0, 1.0, 20.0, 1.0);
    public NumberSetting block_range = new NumberSetting("Block Range", 5.0, 3.0, 10.0, 1.0);
    public static BooleanSetting stopSprint = new BooleanSetting("Stop Sprint", false);
    public static BooleanSetting targetInvis = new BooleanSetting("Target Invis", true);
    public static BooleanSetting targethud = new BooleanSetting("Targethud", true);
    public NumberSetting speed = new NumberSetting("Auto Block Ticks", 6.0, 1.0, 20.0, 1.0);
    public static EntityLivingBase target = null;
    private final Stopwatch timer = new Stopwatch();
    public static boolean blocking = false;
    public static float setYaw;
    public static float setPitch;
    public static int killCount;

    public KillAura() {
        super("KillAura", "Automatically Attacks Entities.", 0, Category.Combat);
        this.addSettings(this.rotationMode, this.autoBlockMode, this.autoBlock, this.blockAttack, this.range, this.block_range, stopSprint, this.minCPS, this.maxCPS, targetInvis, targethud, this.speed);
    }

    @Override
    public void onEnable() {
        target = null;
        blocking = false;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        PacketHelper.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, -1, 0), EnumFacing.DOWN));
        target = null;
        blocking = false;
        super.onDisable();
    }

    @Subscribe
    public void onUpdate(UpdateEvent e) {
        this.setDisplayName("Kill Aura\u00a77 " + this.minCPS.getVal() + ":" + this.maxCPS.getVal() + " | " + this.range.getVal());
        if (target != null && KillAura.target.isDead) {
            ++DiscordRPCHelper.killCount;
            DiscordRPCHelper.updateRPC();
        }
        target = KillAuraHelper.getClosestPlayerEntity(this.range.getVal());
        EntityLivingBase autoBlockRange = KillAuraHelper.getClosestEntity(this.block_range.getVal());
        if (this.autoBlock.isChecked()) {
            if (autoBlockRange != null) {
                if (KillAura.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword && (double)KillAura.mc.thePlayer.ticksExisted % this.speed.getVal() == 0.0) {
                    switch (this.autoBlockMode.getMode()) {
                        case "Fake": {
                            blocking = true;
                            break;
                        }
                        case "Hypixel": {
                            if (e.isPost() && target != null && blocking) {
                                PacketHelper.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, KillAura.mc.thePlayer.getHeldItem(), 0.0f, 0.0f, 0.0f));
                            } else if (e.isPre()) {
                                PacketHelper.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(-1, -1, -1), EnumFacing.DOWN));
                            }
                            blocking = true;
                            break;
                        }
                        case "Verus": {
                            if (blocking) break;
                            blocking = true;
                            PacketHelper.sendPacket(new C08PacketPlayerBlockPlacement(KillAura.mc.thePlayer.inventory.getCurrentItem()));
                            break;
                        }
                        case "Legit": {
                            ChatHelper.addChat(KillAura.target.hurtTime + " " + blocking);
                            if (KillAura.target.hurtTime > 0 && !blocking) {
                                blocking = true;
                                KillAura.mc.playerController.onPlayerRightClick(KillAura.mc.thePlayer, Minecraft.theWorld, KillAura.mc.thePlayer.getHeldItem(), new BlockPos(0.0, KillAura.mc.thePlayer.posY - 1.0, 0.0), Block.getFacingDirection(target.getPosition()), target.getLookVec());
                                break;
                            }
                            blocking = false;
                        }
                    }
                }
            } else {
                if (blocking) {
                    PacketHelper.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(-1, -1, -1), EnumFacing.DOWN));
                }
                blocking = false;
            }
        }
        if (target != null && KillAuraHelper.canAttack(target)) {
            if (KillAura.stopSprint.checked) {
                KillAura.mc.thePlayer.setSprinting(false);
            }
            if (e.isPre()) {
                float[] rotations = EntityHelper.getAngles(target);
                float rot0 = (float)((double)rotations[0] + MathHelper.randomNumber(1.0, 2.0));
                float rot1 = rotations[1] + MathHelper.getRandom();
                switch (this.rotationMode.getMode()) {
                    case "Smooth": {
                        KillAuraHelper.setRotations(e, rot0, rot1);
                        break;
                    }
                    case "Watchdog": {
                        if (KillAura.mc.thePlayer.swingProgressInt == 1) {
                            setPitch = rot0;
                            setYaw = (float)MathHelper.round(rot1, 25);
                        }
                        KillAuraHelper.setRotations(e, setPitch, setYaw);
                        break;
                    }
                    case "Legit": {
                        float[] rots = EntityHelper.getAnglesAGC(target);
                        float yaw = rots[0] + MathHelper.getRandomInRange(-6.8f, 6.8f);
                        float pitch = rots[1] + MathHelper.getRandomInRange(-6.8f, 6.8f);
                        float sens = KillAuraHelper.getSensitivityMultiplier();
                        float yawGCD = (float)Math.round(yaw / sens) * sens;
                        float pitchGCD = (float)Math.round(pitch / sens) * sens;
                        KillAuraHelper.setRotations(e, yaw, pitch);
                        break;
                    }
                    case "Aids": {
                        KillAuraHelper.setRotations(e, KillAura.mc.thePlayer.ticksExisted * 30, -90.0f);
                        break;
                    }
                }
                if (this.timer.hasReached(1000 / MathHelper.getRandInt((int)this.minCPS.getVal(), (int)this.maxCPS.getVal()))) {
                    if (this.blockAttack.isChecked()) {
                        this.attack(target);
                        this.timer.reset();
                    } else if (!blocking) {
                        this.attack(target);
                        this.timer.reset();
                    }
                }
            }
        } else {
            target = null;
        }
    }

    @Subscribe
    public void targetHud(OverlayEvent e) {
        if (target != null && targethud.isChecked()) {
            RenderUtils.drawEsp(target, ColorHelper.getColor(0));
            RenderUtils.drawEsp(target, new Color(18, 18, 18).getRGB());
            ScaledResolution scaledResolution = new ScaledResolution(mc);
            TTFFontRenderer fr = Main.getInstance().getSFUI(20);
            int width = 125;
            int height = 35;
            int x = scaledResolution.getScaledWidth() / 6 * 4 - width;
            int y = scaledResolution.getScaledHeight() / 4 * 3 - height;
            int healthBarX = x + 37;
            int healthBarWidth = healthBarX + KillAura.mc.fontRendererObj.getStringWidth(target.getName()) + 5;
            int healtBarHeight = 15;
            int healhBarY = y + height - healtBarHeight - 3;
            int healthPercent = (int)EntityHelper.getEntityHealthPercent((EntityPlayer)target);
            float var28 = target.getHealth() + target.getAbsorptionAmount();
            float var32 = target.getMaxHealth() + target.getAbsorptionAmount();
            float var37 = 35 + KillAura.mc.fontRendererObj.getStringWidth("aaaaaaaaaaaaaa40");
            float var42 = (float)((double)Math.round((double)var28 * 100.0) / 100.0);
            float var46 = 100.0f / var32;
            float var48 = var42 * var46;
            float var51 = (var37 - 50.0f) / 100.0f;
            RenderUtils.drawRect(x - 1, y - 3, x + 41 + KillAura.mc.fontRendererObj.getStringWidth("aaaaaaaaaaaaaa"), y + height + 1, RenderUtils.transparency(new Color(37, 37, 37).getRGB(), 0.6f));
            RenderUtils.drawRect(x, y, x + 40 + KillAura.mc.fontRendererObj.getStringWidth("aaaaaaaaaaaaaa"), y + height, RenderUtils.transparency(new Color(45, 45, 45, 255).getRGB(), 0.8f));
            RenderUtils.drawRect(x, y - 2, x + 40 + KillAura.mc.fontRendererObj.getStringWidth("aaaaaaaaaaaaaa"), y, RenderUtils.transparency(ColorHelper.getColor(0), 0.8f));
            RenderUtils.drawHead((AbstractClientPlayer)target, x + 2, y + 2, 32, 32);
            KillAura.mc.fontRendererObj.drawStringWithShadow(target.getName(), x + 37, y + 4, -1);
            try {
                RenderUtils.drawRect(healthBarX, healhBarY, (float)healthBarX + var48 * var51 + 3.0f, healhBarY + healtBarHeight, RenderUtils.transparency(ColorHelper.getColor(0), 0.8f));
                KillAura.mc.fontRendererObj.drawStringWithShadow(String.format("%.1f", Float.valueOf(var48)) + "%", x + 39, healhBarY + 3, -1);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void attack(Entity entity) {
        KillAura.mc.thePlayer.swingItem();
        KillAura.mc.playerController.attackEntity(KillAura.mc.thePlayer, entity);
    }

    static {
        killCount = 0;
    }
}

