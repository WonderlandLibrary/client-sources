/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.movement;

import cc.diablo.event.impl.PacketEvent;
import cc.diablo.event.impl.UpdateEvent;
import cc.diablo.helpers.HypixelHelper;
import cc.diablo.helpers.Stopwatch;
import cc.diablo.helpers.player.EntityHelper;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.setting.impl.BooleanSetting;
import cc.diablo.setting.impl.ModeSetting;
import cc.diablo.setting.impl.NumberSetting;
import com.google.common.eventbus.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.Timer;

public class LongJump
extends Module {
    public ModeSetting mode = new ModeSetting("Longjump Mode", "Verus", "Vanilla", "Watchdog", "WatchdogBow", "HypixelSlime", "Verus");
    public BooleanSetting damage = new BooleanSetting("Damage", true);
    public NumberSetting speed = new NumberSetting("Speed", 0.75, 0.25, 8.0, 0.1);
    public double moveSpeed;
    public boolean isChangedVelocity;
    public Stopwatch stopwatch = new Stopwatch();
    int ticks;

    public LongJump() {
        super("LongJump", "Longjump", 0, Category.Movement);
        this.addSettings(this.mode, this.damage, this.speed);
    }

    @Override
    public void onEnable() {
        String mode;
        this.ticks = 0;
        this.stopwatch.reset();
        float x = (float)LongJump.mc.thePlayer.posX;
        float y = (float)LongJump.mc.thePlayer.posY;
        float z = (float)LongJump.mc.thePlayer.posZ;
        float pitch = LongJump.mc.thePlayer.rotationPitch;
        float yaw = LongJump.mc.thePlayer.rotationYaw;
        this.moveSpeed = this.speed.getVal();
        this.isChangedVelocity = false;
        if (this.damage.isChecked() && this.mode.getMode() != "WatchdogBow") {
            if (this.mode.getMode() != "Watchdog") {
                LongJump.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, (double)y + 3.1, z, false));
                LongJump.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, (double)y + 1.0E-4, z, false));
                LongJump.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, (double)y + 0.01, z, true));
                LongJump.mc.thePlayer.motionY = 0.425;
                EntityHelper.setMotion(0.4);
            } else {
                EntityHelper.setMotion(LongJump.mc.thePlayer.getActivePotionEffects().toString().contains("moveSpeed") ? (double)0.43f : (double)0.37f);
            }
        }
        switch (mode = this.mode.getMode()) {
            case "HypixelSlime": {
                HypixelHelper.slimeDisable();
                break;
            }
            case "Verus": {
                LongJump.mc.thePlayer.motionY = 0.6;
                EntityHelper.setMotion(this.moveSpeed);
                break;
            }
            case "Vanilla": {
                LongJump.mc.thePlayer.motionY = 0.41;
                EntityHelper.setMotion(this.moveSpeed);
                break;
            }
            case "WatchdogBow": {
                LongJump.mc.thePlayer.motionY = 0.255;
            }
        }
        super.onEnable();
    }

    @Subscribe
    public void onMove(UpdateEvent e) {
        String mode;
        double x = LongJump.mc.thePlayer.posX;
        double y = LongJump.mc.thePlayer.posY;
        double z = LongJump.mc.thePlayer.posZ;
        float pitch = LongJump.mc.thePlayer.rotationPitch;
        float yaw = LongJump.mc.thePlayer.rotationYaw;
        this.setDisplayName("Longjump\u00a77 " + this.mode.getMode());
        switch (mode = this.mode.getMode()) {
            case "Verus": 
            case "Vanilla": {
                if (LongJump.mc.thePlayer.isCollided && LongJump.mc.thePlayer.onGround) {
                    this.setToggled(false);
                }
                EntityHelper.setMotion(this.moveSpeed);
                break;
            }
            case "Watchdog": {
                ++this.ticks;
                if (this.ticks > 52) {
                    int i = 0;
                    while ((double)i <= 64.0) {
                        mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(LongJump.mc.thePlayer.posX, LongJump.mc.thePlayer.posY + 0.0625, LongJump.mc.thePlayer.posZ, false));
                        mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(LongJump.mc.thePlayer.posX, LongJump.mc.thePlayer.posY + 0.0625, LongJump.mc.thePlayer.posZ, false));
                        mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(LongJump.mc.thePlayer.posX, LongJump.mc.thePlayer.posY, LongJump.mc.thePlayer.posZ, (double)i == 64.0));
                        if (LongJump.mc.thePlayer.fallDistance < 0.0f && LongJump.mc.thePlayer.isCollided && LongJump.mc.thePlayer.onGround) {
                            this.setToggled(false);
                        }
                        EntityHelper.setMotion(0.4);
                        i = (short)(i + 1);
                    }
                    break;
                }
                LongJump.mc.thePlayer.setSpeed(0.0);
                break;
            }
            case "WatchdogBow": {
                Timer.timerSpeed = 0.75f;
                this.moveSpeed = 0.33f;
                EntityHelper.setMotion(this.moveSpeed);
                LongJump.mc.thePlayer.motionY = 0.0;
                LongJump.mc.thePlayer.motionY = LongJump.mc.thePlayer.motionY - (LongJump.mc.thePlayer.ticksExisted % 3 == 0 ? 0.08 : 0.0);
                if (!this.stopwatch.hasReached(750L)) break;
                this.setToggled(false);
            }
        }
    }

    @Subscribe
    public void onPacket(PacketEvent event) {
        String mode;
        switch (mode = this.mode.getMode()) {
            case "HypixelSlime": {
                if (!(event.getPacket() instanceof S12PacketEntityVelocity) || LongJump.mc.thePlayer.getEntityId() != ((S12PacketEntityVelocity)event.getPacket()).getEntityID()) break;
                this.isChangedVelocity = true;
            }
        }
    }

    public static int getBestBow(Entity target) {
        int originalSlot = Minecraft.getMinecraft().thePlayer.inventory.currentItem;
        int weaponSlot = -1;
        float weaponDamage = 1.0f;
        for (int slot = 0; slot < 9; slot = (int)((byte)(slot + 1))) {
            Minecraft.getMinecraft().thePlayer.inventory.currentItem = slot;
            ItemStack itemStack = Minecraft.getMinecraft().thePlayer.getHeldItem();
            if (itemStack == null || !(itemStack.getItem() instanceof ItemBow)) continue;
            weaponSlot = slot;
        }
        if (weaponSlot != -1) {
            return weaponSlot;
        }
        return originalSlot;
    }

    public static float getItemDamage(ItemStack itemStack) {
        if (itemStack.getItem() instanceof ItemBow) {
            double damage = 4.0 + (double)((ItemSword)itemStack.getItem()).getDamageVsEntity();
            return (float)(damage += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemStack) * 1.25);
        }
        return 1.0f;
    }
}

