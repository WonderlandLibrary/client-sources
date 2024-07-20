/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.util.EnumHand;
import org.lwjgl.input.Mouse;
import ru.govno.client.Client;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventPlayerMotionUpdate;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.HitAura;
import ru.govno.client.module.modules.Notifications;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Combat.RotationUtil;
import ru.govno.client.utils.InventoryUtil;
import ru.govno.client.utils.Math.MathUtils;

public class MiddleClick
extends Module {
    public static MiddleClick get;
    public Settings NoHitFriend = new Settings("NoHitFriend", true, (Module)this);
    public Settings MCFriend;
    public Settings MCPearl;
    public Settings PearlFromInventory;
    public Settings PearlBlockInject;
    public Settings MCExpSneakPitch;
    public Settings MCExpMega;
    private boolean callThrowPearl;
    private boolean callThrowPearl2;
    private float lastEventYaw;
    private float lastEventPitch;

    public MiddleClick() {
        super("MiddleClick", 0, Module.Category.MISC);
        this.settings.add(this.NoHitFriend);
        this.MCFriend = new Settings("MCFriend", true, (Module)this);
        this.settings.add(this.MCFriend);
        this.MCPearl = new Settings("MCPearl", true, (Module)this);
        this.settings.add(this.MCPearl);
        this.PearlFromInventory = new Settings("PearlFromInventory", true, (Module)this, () -> this.MCPearl.bValue);
        this.settings.add(this.PearlFromInventory);
        this.PearlBlockInject = new Settings("PearlBlockInject", true, (Module)this, () -> this.MCPearl.bValue);
        this.settings.add(this.PearlBlockInject);
        this.MCExpSneakPitch = new Settings("MCExpSneakPitch", true, (Module)this);
        this.settings.add(this.MCExpSneakPitch);
        this.MCExpMega = new Settings("MCExpMega", true, (Module)this, () -> this.MCExpSneakPitch.bValue);
        this.settings.add(this.MCExpMega);
        get = this;
    }

    private boolean hasEXPFarm() {
        return this.MCExpSneakPitch.bValue && Minecraft.player.isSneaking() && Minecraft.player.rotationPitch > 83.0f && (InventoryUtil.getItemInHotbar(Items.EXPERIENCE_BOTTLE) != -1 || Minecraft.player.getHeldItemOffhand().getItem() == Items.EXPERIENCE_BOTTLE);
    }

    private void callThrowPearl() {
        this.callThrowPearl = true;
    }

    private int getPearlSlot() {
        if (Minecraft.player.getHeldItemOffhand().getItem() instanceof ItemEnderPearl) {
            MiddleClick.mc.playerController.processRightClick(Minecraft.player, MiddleClick.mc.world, EnumHand.OFF_HAND);
            return -2;
        }
        int s = -1;
        for (int i = 0; i <= (this.PearlFromInventory.bValue ? 36 : 8); ++i) {
            if (!(Minecraft.player.inventory.getStackInSlot(i).getItem() instanceof ItemEnderPearl)) continue;
            s = i;
            break;
        }
        return s;
    }

    private void updateThrowPearl(EventPlayerMotionUpdate event) {
        if (this.callThrowPearl2) {
            this.throwPearl();
            this.callThrowPearl2 = false;
        }
        if (this.callThrowPearl) {
            event.setYaw(Minecraft.player.rotationYaw);
            event.setPitch(Minecraft.player.rotationPitch);
            HitAura.get.noRotateTick = true;
            RotationUtil.Yaw = Minecraft.player.rotationYaw;
            RotationUtil.Pitch = Minecraft.player.rotationPitch;
            HitAura.get.rotationsCombat = new float[]{Minecraft.player.rotationYaw, Minecraft.player.rotationPitch};
            HitAura.get.rotationsVisual = HitAura.get.rotationsCombat;
            Minecraft.player.rotationYawHead = Minecraft.player.rotationYaw;
            Minecraft.player.rotationPitchHead = Minecraft.player.rotationPitch;
            this.callThrowPearl = false;
            this.callThrowPearl2 = true;
        }
    }

    private void throwPearl() {
        boolean off;
        int slot = this.getPearlSlot();
        if (slot == -1) {
            return;
        }
        boolean bl = off = slot == -2;
        if (off) {
            MiddleClick.mc.playerController.processRightClick(Minecraft.player, MiddleClick.mc.world, EnumHand.OFF_HAND);
            return;
        }
        boolean inv = slot > 8;
        Minecraft.player.activeItemStack = Minecraft.player.inventory.getStackInSlot(Minecraft.player.inventory.currentItem);
        int oldSlot = Minecraft.player.inventory.currentItem;
        if (inv) {
            MiddleClick.mc.playerController.windowClick(0, slot, Minecraft.player.inventory.currentItem, ClickType.SWAP, Minecraft.player);
        } else {
            Minecraft.player.inventory.currentItem = slot;
            MiddleClick.mc.playerController.syncCurrentPlayItem();
        }
        MiddleClick.mc.playerController.processRightClick(Minecraft.player, MiddleClick.mc.world, EnumHand.MAIN_HAND);
        if (inv) {
            MiddleClick.mc.playerController.windowClickMemory(0, slot, Minecraft.player.inventory.currentItem, ClickType.SWAP, Minecraft.player, 50);
        } else {
            Minecraft.player.inventory.currentItem = oldSlot;
            MiddleClick.mc.playerController.syncCurrentPlayItem();
        }
    }

    @Override
    public void onMouseClick(int mouseButton) {
        if (mouseButton == 2 && Minecraft.player != null && (this.MCFriend.bValue || this.MCPearl.bValue) && !this.hasEXPFarm() && MiddleClick.mc.currentScreen == null) {
            if (this.MCFriend.bValue && MiddleClick.mc.pointedEntity != null) {
                if (MiddleClick.mc.pointedEntity instanceof EntityLivingBase) {
                    String name;
                    String string = name = MiddleClick.mc.pointedEntity.getDisplayName() == null ? MiddleClick.mc.pointedEntity.getName() : MiddleClick.mc.pointedEntity.getDisplayName().getUnformattedText();
                    if (Client.friendManager.getFriends().stream().anyMatch(paramFriend -> paramFriend.getName().equals(MiddleClick.mc.pointedEntity.getName()))) {
                        Client.friendManager.getFriends().remove(Client.friendManager.getFriend(MiddleClick.mc.pointedEntity.getName()));
                        Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77[\u00a7lMiddleClick\u00a7r\u00a77]: \u00a7r" + name + " \u00a7c\u0443\u0434\u0430\u043b\u0451\u043d \u0438\u0437 \u0434\u0440\u0443\u0437\u0435\u0439!\u00a7r", false);
                        if (Notifications.get.actived) {
                            Notifications.Notify.spawnNotify(name, Notifications.type.FDEL);
                        }
                    } else {
                        Client.friendManager.addFriend(MiddleClick.mc.pointedEntity.getName());
                        Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77[\u00a7lMiddleClick\u00a7r\u00a77]: \u00a7r" + name + " \u00a7a\u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d \u0432 \u0434\u0440\u0443\u0437\u044c\u044f!\u00a7r", false);
                        if (Notifications.get.actived) {
                            Notifications.Notify.spawnNotify(name, Notifications.type.FADD);
                        }
                    }
                }
                return;
            }
            if (this.MCPearl.bValue) {
                double pitchDiff;
                double yawDiff = MathUtils.getDifferenceOf(Minecraft.player.rotationYaw, HitAura.get.rotationsCombat[0]);
                double rotDiff = Math.sqrt(yawDiff * yawDiff + (pitchDiff = MathUtils.getDifferenceOf(Minecraft.player.rotationPitch, HitAura.get.rotationsCombat[1])) * pitchDiff);
                if (rotDiff > 1.0) {
                    this.callThrowPearl();
                } else {
                    this.throwPearl();
                }
            }
        }
    }

    @EventTarget
    public void onPlayerMotionUpdate(EventPlayerMotionUpdate e) {
        if (this.isActived()) {
            this.lastEventYaw = e.getYaw();
            this.lastEventPitch = e.getPitch();
            this.updateThrowPearl(e);
        }
    }

    @Override
    public void onUpdate() {
        if (this.hasEXPFarm() && Mouse.isButtonDown(2)) {
            boolean last;
            if (Minecraft.player.getHeldItemOffhand().getItem() == Items.EXPERIENCE_BOTTLE) {
                for (int c = 0; c < (this.MCExpMega.bValue ? 3 : 1); ++c) {
                    MiddleClick.mc.playerController.processRightClick(Minecraft.player, MiddleClick.mc.world, EnumHand.OFF_HAND);
                }
                return;
            }
            int slot = InventoryUtil.getItemInHotbar(Items.EXPERIENCE_BOTTLE);
            boolean bl = last = Minecraft.player.inventory.getStackInSlot((int)slot).stackSize == 1;
            if (slot != -1) {
                boolean inHand = Minecraft.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE;
                int oldSlot = Minecraft.player.inventory.currentItem;
                if (!inHand) {
                    Minecraft.player.inventory.currentItem = slot;
                    MiddleClick.mc.playerController.syncCurrentPlayItem();
                }
                for (int c = 0; c < (this.MCExpMega.bValue ? 3 : 1); ++c) {
                    MiddleClick.mc.playerController.processRightClick(Minecraft.player, MiddleClick.mc.world, EnumHand.MAIN_HAND);
                }
                if (!inHand) {
                    Minecraft.player.inventory.currentItem = oldSlot;
                    MiddleClick.mc.playerController.syncCurrentPlayItem();
                }
            }
            if (last) {
                for (int i = 9; i < 44; ++i) {
                    if (Minecraft.player.inventory.getStackInSlot(i).getItem() != Items.EXPERIENCE_BOTTLE) continue;
                    i = i < 9 && i != -1 ? i + 36 : i;
                    MiddleClick.mc.playerController.windowClick(0, i, 1, ClickType.QUICK_MOVE, Minecraft.player);
                    break;
                }
            }
        }
    }
}

