/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import javax.vecmath.Vector2f;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.Event3D;
import ru.govno.client.event.events.EventInput;
import ru.govno.client.module.Module;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.InventoryUtil;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;

public class ClickTeleport
extends Module {
    public static ClickTeleport get;
    BlockPos posBlock = null;
    boolean clickChange = false;

    public ClickTeleport() {
        super("ClickTeleport", 0, Module.Category.MOVEMENT);
        get = this;
        this.settings.add(new Settings("Mode", "Matrix", (Module)this, new String[]{"Matrix", "Vanilla", "Spartan"}));
    }

    @EventTarget
    public void onRender3D(Event3D event) {
        EntityLivingBase base = MathUtils.getPointedEntity(new Vector2f(Minecraft.player.rotationYaw, Minecraft.player.rotationPitch), 200.0, 1.0f, false);
        AxisAlignedBB axis = null;
        if (this.actived && this.posBlock != null && Minecraft.player != null && base == null) {
            axis = ClickTeleport.mc.world.getBlockState(this.posBlock).getSelectedBoundingBox(ClickTeleport.mc.world, this.posBlock);
        }
        if (this.actived && Minecraft.player != null && base != null) {
            axis = base.getRenderBoundingBox();
        }
        if (axis != null) {
            AxisAlignedBB box = axis;
            RenderUtils.setup3dForBlockPos(() -> RenderUtils.drawCanisterBox(box, true, false, true, -1, 0, ColorUtils.getColor(255, 255, 255, 50)), false);
        }
    }

    public static void matrixTp(double x, double y, double z, boolean canElytra) {
        boolean elytraEquiped;
        float f = Minecraft.player.rotationYaw * ((float)Math.PI / 180);
        double h = Minecraft.player.getDistance(Minecraft.player.posX + x, Minecraft.player.posY, Minecraft.player.posZ + z);
        int de = (int)MathUtils.clamp(Minecraft.player.getDistance(x, y += h / 100.0, z) / 11.0, 1.0, 17.0);
        int de2 = (int)(Math.abs(y / 11.0) + Math.abs(h / 2.5));
        boolean bl = elytraEquiped = Minecraft.player.inventory.armorInventory.get(2).getItem() == Items.ELYTRA;
        if (canElytra) {
            for (int i = 0; i < MathUtils.clamp(de2, 1, 17); ++i) {
                Minecraft.player.connection.sendPacket(new CPacketPlayer(false));
            }
            if (elytraEquiped) {
                Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ, false));
                Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ, false));
                Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.START_FALL_FLYING));
                Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX + x, Minecraft.player.posY + y, Minecraft.player.posZ + z, false));
                Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.START_FALL_FLYING));
            } else {
                int elytra = InventoryUtil.getElytra();
                if (elytra != -1) {
                    ClickTeleport.mc.playerController.windowClick(0, elytra < 9 ? elytra + 36 : elytra, 1, ClickType.PICKUP, Minecraft.player);
                    ClickTeleport.mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, Minecraft.player);
                }
                Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ, false));
                Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ, false));
                Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.START_FALL_FLYING));
                Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX + x, Minecraft.player.posY + y, Minecraft.player.posZ + z, false));
                Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.START_FALL_FLYING));
                if (elytra != -1) {
                    ClickTeleport.mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, Minecraft.player);
                    ClickTeleport.mc.playerController.windowClick(0, elytra < 9 ? elytra + 36 : elytra, 1, ClickType.PICKUP, Minecraft.player);
                }
            }
            Minecraft.player.setPositionAndUpdate(Minecraft.player.posX + x, Minecraft.player.posY + y, Minecraft.player.posZ + z);
        } else {
            for (int i = 0; i < MathUtils.clamp(de2 + 1, 0, 19); ++i) {
                Minecraft.player.connection.sendPacket(new CPacketPlayer(false));
            }
            Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX + x, Minecraft.player.posY + y, Minecraft.player.posZ + z, false));
            Minecraft.player.setPositionAndUpdate(Minecraft.player.posX + x, Minecraft.player.posY + y, Minecraft.player.posZ + z);
        }
        Minecraft.player.swingArm(EnumHand.MAIN_HAND);
    }

    void teleport(double x, double y, double z) {
        if (this.currentMode("Mode").equalsIgnoreCase("Vanilla")) {
            Minecraft.player.setPositionAndUpdate(x, y, z);
        } else if (this.currentMode("Mode").equalsIgnoreCase("Spartan")) {
            Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.START_SNEAKING));
            Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(x, y - 1.0, z, false));
            Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(x, y, z, false));
            Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(x, 1.0, z, false));
            Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(x, y, z, false));
            Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(x, y + 0.42, z, true));
            Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(x, y, z, false));
            Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
        if (this.currentMode("Mode").equalsIgnoreCase("Matrix")) {
            y += 2.0;
            boolean trouble = true;
            for (int i = 0; i < 45; ++i) {
                ItemStack itemStack = Minecraft.player.inventoryContainer.getSlot(i).getStack();
                if (itemStack.getItem() != Items.ELYTRA) continue;
                trouble = false;
            }
            boolean elytra = !trouble;
            ClickTeleport.matrixTp(x - Minecraft.player.posX, y - Minecraft.player.posY, z - Minecraft.player.posZ, elytra);
        }
    }

    @EventTarget
    public void onClick(EventInput e) {
        if (e.getKey() == 1) {
            if (this.posBlock == null || ClickTeleport.mc.currentScreen != null) {
                return;
            }
            EntityLivingBase base = MathUtils.getPointedEntity(new Vector2f(Minecraft.player.rotationYaw, Minecraft.player.rotationPitch), 100.0, 1.0f, false);
            if (base != null) {
                this.teleport(base.posX, base.posY - 2.0 + 0.1, base.posZ);
            } else {
                int y = this.posBlock.getY();
                this.teleport((float)this.posBlock.getX() + 0.5f, MathUtils.clamp(y, 5, 256), (float)this.posBlock.getZ() + 0.5f);
            }
        }
    }

    @Override
    public void onUpdate() {
        this.posBlock = ClickTeleport.mc.objectMouseOver.getBlockPos();
        if (this.posBlock != null) {
            BlockPos e = this.posBlock;
            for (int i = 256; i > 0; --i) {
                Material material = ClickTeleport.mc.world.getBlockState(e).getMaterial();
                boolean isReplacelable = material.isReplaceable();
                if (!isReplacelable) continue;
                e = e.down();
            }
            this.posBlock = e;
        }
    }
}

