/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketClientSettings;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraft.network.play.client.CPacketConfirmTransaction;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketCloseWindow;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketOpenWindow;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldType;
import org.lwjgl.input.Mouse;
import ru.govno.client.Client;
import ru.govno.client.clickgui.ClickGuiScreen;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventMove2;
import ru.govno.client.event.events.EventReceivePacket;
import ru.govno.client.event.events.EventSendPacket;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.CommandGui;
import ru.govno.client.module.modules.ElytraBoost;
import ru.govno.client.module.modules.HitAura;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Math.TimerHelper;
import ru.govno.client.utils.Movement.MoveMeHelp;

public class Bypass
extends Module {
    public static Bypass get;
    public Settings NCPWinclick;
    public Settings VulcanStrafe;
    public Settings VulcanLiquid;
    public Settings MatrixElySpoofs;
    public Settings NoServerGround;
    public Settings NoServerRotate;
    public Settings CloseScreens;
    public Settings FixPearlFlag;
    public Settings InvPopHitFix;
    public Settings RegionUsingItem;
    public Settings FixSettingsKick;
    public Settings FixStackFlags;
    public Settings FixShieldCooldown;
    public Settings SpawnGodmode;
    public Settings ClientSpoof;
    public int flagCPS;
    public int flagReduceTicks;
    boolean strafeHacked = false;
    TimerHelper timer = new TimerHelper();
    Vec3d lastVecNote = Vec3d.ZERO;
    boolean vulcanStatusHacked;
    boolean noted;
    public boolean doReduceDestack;
    boolean usingStartPlob;
    private final TimerHelper openContainerOutTime = new TimerHelper();
    float sYaw;
    float sPitch;
    boolean callSRS;
    double gdX;
    double gdY;
    double gdZ;
    float gdYaw;
    float gdPitch;

    public Bypass() {
        super("Bypass", 0, Module.Category.MISC);
        this.NCPWinclick = new Settings("NCPWinclick", true, (Module)this);
        this.settings.add(this.NCPWinclick);
        this.VulcanStrafe = new Settings("VulcanStrafe", false, (Module)this);
        this.settings.add(this.VulcanStrafe);
        this.VulcanLiquid = new Settings("VulcanLiquid", false, (Module)this);
        this.settings.add(this.VulcanLiquid);
        this.MatrixElySpoofs = new Settings("MatrixElySpoofs", false, (Module)this);
        this.settings.add(this.MatrixElySpoofs);
        this.NoServerGround = new Settings("NoServerGround", false, (Module)this);
        this.settings.add(this.NoServerGround);
        this.NoServerRotate = new Settings("NoServerRotate", true, (Module)this);
        this.settings.add(this.NoServerRotate);
        this.CloseScreens = new Settings("CloseScreens", true, (Module)this);
        this.settings.add(this.CloseScreens);
        this.FixPearlFlag = new Settings("FixPearlFlag", true, (Module)this);
        this.settings.add(this.FixPearlFlag);
        this.InvPopHitFix = new Settings("InvPopHitFix", false, (Module)this);
        this.settings.add(this.InvPopHitFix);
        this.RegionUsingItem = new Settings("RegionUsingItem", true, (Module)this);
        this.settings.add(this.RegionUsingItem);
        this.FixSettingsKick = new Settings("FixSettingsKick", true, (Module)this);
        this.settings.add(this.FixSettingsKick);
        this.FixStackFlags = new Settings("FixStackFlags", false, (Module)this);
        this.settings.add(this.FixStackFlags);
        this.FixShieldCooldown = new Settings("FixShieldCooldown", false, (Module)this);
        this.settings.add(this.FixShieldCooldown);
        this.SpawnGodmode = new Settings("SpawnGodmode", false, (Module)this);
        this.settings.add(this.SpawnGodmode);
        this.ClientSpoof = new Settings("ClientSpoof", false, (Module)this);
        this.settings.add(this.ClientSpoof);
        get = this;
    }

    private void sendPacket(Packet packet) {
        mc.getConnection().sendPacket(packet);
    }

    public void setStrafeHacked(boolean hack) {
        this.strafeHacked = hack;
    }

    public boolean getIsStrafeHacked() {
        return this.strafeHacked || !this.actived || !this.VulcanStrafe.bValue;
    }

    public boolean canWinClickEdit() {
        return this.actived && this.NCPWinclick.bValue;
    }

    public boolean rayTrace(Entity me, double x, double y, double z) {
        return Bypass.mc.world.rayTraceBlocks(new Vec3d(me.posX, me.posY, me.posZ), new Vec3d(x, y, z), false, true, false) == null || Bypass.mc.world.rayTraceBlocks(new Vec3d(me.posX, me.posY + 1.0, me.posZ), new Vec3d(x, y + 1.0, z), false, true, false) == null;
    }

    public boolean statusVulcanDisabler() {
        return !this.actived || this.vulcanStatusHacked;
    }

    private final BlockPos waterNeared() {
        float r = 5.0f;
        float min = 4.5f;
        float max = 5.5f;
        for (float x = -5.0f; x < 5.0f; x += 1.0f) {
            for (float y = -5.0f; y < 5.0f; y += 1.0f) {
                for (float z = -5.0f; z < 5.0f; z += 1.0f) {
                    BlockPos pos = new BlockPos((float)((int)Minecraft.player.posX) + x + 0.5f, (float)((int)Minecraft.player.posY) + y, (float)((int)Minecraft.player.posZ) + z + 0.5f);
                    if (pos == null || Bypass.mc.world.getBlockState(pos).getBlock() != Blocks.WATER || Bypass.mc.world.getBlockState(pos.up()).getBlock() != Blocks.WATER && Bypass.mc.world.getBlockState(pos.up()).getBlock() != Blocks.AIR || Bypass.mc.world.getBlockState(pos.up().up()).getBlock() != Blocks.WATER && Bypass.mc.world.getBlockState(pos.up().up()).getBlock() != Blocks.AIR) continue;
                    Vec3d vec3d = new Vec3d(Minecraft.player.posX + (double)x + 0.5, Minecraft.player.posY + (double)y, Minecraft.player.posZ + (double)z + 0.5);
                    if (!(Minecraft.player.getDistanceToVec3d(vec3d) > 4.5)) continue;
                    Vec3d vec3d2 = new Vec3d(Minecraft.player.posX + (double)x + 0.5, Minecraft.player.posY + (double)y, Minecraft.player.posZ + (double)z + 0.5);
                    if (!(Minecraft.player.getDistanceToVec3d(vec3d2) < 5.5) || !this.rayTrace(Minecraft.player, pos.getX(), pos.getY(), pos.getZ()) || Bypass.mc.world.getBlockState(pos.up()).getBlock() != Blocks.AIR || Bypass.mc.world.getBlockState(pos.down()).getBlock() == Blocks.WATER) continue;
                    return pos;
                }
            }
        }
        return null;
    }

    private final void note() {
        float x = (float)this.waterNeared().getX() + 0.5f;
        float y = (float)this.waterNeared().getY() + 0.2f;
        float z = (float)this.waterNeared().getZ() + 0.5f;
        if (Minecraft.player.fallDistance == 0.0f || (double)Minecraft.player.fallDistance < 0.4 || MoveMeHelp.getSpeed() > 0.1) {
            return;
        }
        this.lastVecNote = new Vec3d(x, y, z);
        Minecraft.player.connection.preSendPacket(new CPacketPlayer.Position(x, (double)y + 0.19, z, true));
        Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77[\u00a7lDisabler\u00a7r\u00a77]: \u043f\u044b\u0442\u0430\u044e\u0441\u044c \u0432\u044b\u043a\u043b\u044e\u0447\u0438\u0442\u044c Vulcan.", false);
        this.noted = true;
    }

    public double getDistanceAtVec3dToVec3d(Vec3d first, Vec3d second) {
        double xDiff = first.xCoord - second.xCoord;
        double yDiff = first.yCoord - second.yCoord;
        double zDiff = first.zCoord - second.zCoord;
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
    }

    public Vec3d getEntityVecPosition(Entity entityIn) {
        return new Vec3d(entityIn.posX, entityIn.posY, entityIn.posZ);
    }

    @EventTarget
    public void onMovementState(EventMove2 event) {
        if (!this.doReduceDestack) {
            return;
        }
        event.motion().xCoord = 0.0;
        event.motion().zCoord = 0.0;
    }

    void reduceStackFlags() {
        if (this.doReduceDestack) {
            if (this.flagReduceTicks > 0) {
                Minecraft.player.motionX = 0.0;
                Minecraft.player.motionY = 0.0;
                Minecraft.player.motionZ = 0.0;
                Minecraft.player.jumpMovementFactor /= 4.0f;
                --this.flagReduceTicks;
            } else {
                this.doReduceDestack = false;
            }
        } else if (this.flagReduceTicks != 14) {
            this.flagReduceTicks = 14;
        }
    }

    @Override
    public void onUpdate() {
        if (this.SpawnGodmode.bValue && Bypass.mc.world.playerEntities.size() <= 2) {
            if (this.gdX != 0.0 || this.gdY != 0.0 || this.gdZ != 0.0) {
                Minecraft.player.setPosition(this.gdX, this.gdY, this.gdZ);
                Minecraft.player.multiplyMotionXZ(0.0f);
            }
            if (this.gdYaw != 0.0f || this.gdPitch != 0.0f) {
                Minecraft.player.rotationYawHead = this.gdYaw;
                Minecraft.player.renderYawOffset = this.gdYaw;
                Minecraft.player.rotationPitchHead = this.gdPitch;
            }
        }
        if (this.FixStackFlags.bValue) {
            if (this.flagCPS > 3) {
                this.doReduceDestack = true;
                this.flagCPS = 0;
            }
            this.reduceStackFlags();
            if (this.flagCPS > 0 && Minecraft.player.ticksExisted % 5 == 0) {
                --this.flagCPS;
            }
        }
        if (this.VulcanLiquid.bValue) {
            if (Minecraft.player.ticksExisted == 1) {
                this.noted = false;
                this.strafeHacked = false;
            }
            if (Minecraft.player.ticksExisted > 30 && !this.noted && !this.strafeHacked && this.waterNeared() != null) {
                this.note();
            } else if (!(this.noted || Minecraft.player.ticksExisted != 5 || Bypass.mc.world == null || Bypass.mc.world != null && Bypass.mc.world.getWorldType() == WorldType.FLAT)) {
                Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77[\u00a7lDisabler\u00a7r\u00a77]: \u043f\u043e\u0434\u043e\u0439\u0434\u0438 \u043a \u0432\u043e\u0434\u0435.", false);
            }
        } else if (this.noted || this.strafeHacked) {
            this.noted = false;
            this.strafeHacked = false;
        }
        if (this.VulcanStrafe.bValue) {
            if (Minecraft.player.ticksExisted % 11 == 7) {
                this.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, BlockPos.ORIGIN.down(61), Minecraft.player.getHorizontalFacing().getOpposite()));
            }
            this.setStrafeHacked(!(Minecraft.player.ticksExisted <= 8 || Bypass.mc.playerController.isHittingBlock && Bypass.mc.playerController.curBlockDamageMP > 0.0f));
        }
        if (!(!this.MatrixElySpoofs.bValue || Minecraft.player.ticksExisted % 6 != 5 || ElytraBoost.get.actived && ElytraBoost.canElytra())) {
            this.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.START_FALL_FLYING));
        }
    }

    @EventTarget
    public void onSending(EventSendPacket event) {
        CPacketPlayer packet;
        Packet packet2;
        CPacketUseEntity use;
        Packet trans;
        if (!this.actived) {
            return;
        }
        if ((event.getPacket() instanceof CPacketPlayer || event.getPacket() instanceof CPacketConfirmTransaction) && this.SpawnGodmode.bValue) {
            boolean nolo = Bypass.mc.world.playerEntities.size() <= 2;
            Packet packet3 = event.getPacket();
            if (packet3 instanceof CPacketConfirmTransaction && ((CPacketConfirmTransaction)(trans = (CPacketConfirmTransaction)packet3)).getWindowId() != 0) {
                nolo = false;
            }
            if (nolo) {
                event.cancel();
            }
        }
        if (!this.usingStartPlob && event.getPacket() instanceof CPacketPlayerTryUseItem && this.RegionUsingItem.bValue) {
            boolean bl = this.usingStartPlob = !(Minecraft.player.getHeldItemMainhand().getItem() instanceof ItemBlock) && !(Minecraft.player.getHeldItemOffhand().getItem() instanceof ItemBlock);
        }
        if (this.usingStartPlob && Minecraft.player != null && (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock || (trans = event.getPacket()) instanceof CPacketUseEntity && (use = (CPacketUseEntity)trans).getAction() != CPacketUseEntity.Action.ATTACK || Minecraft.player.getHeldItemMainhand().getItem() instanceof ItemBlock || Minecraft.player.getHeldItemOffhand().getItem() instanceof ItemBlock)) {
            if (!(Minecraft.player.getHeldItemMainhand().getItem() instanceof ItemBlock) && !(Minecraft.player.getHeldItemOffhand().getItem() instanceof ItemBlock)) {
                event.cancel();
            }
            this.usingStartPlob = false;
        }
        if (Bypass.mc.world != null && Bypass.mc.world.getWorldType() == WorldType.DEFAULT && (trans = event.getPacket()) instanceof CPacketUseEntity) {
            EntityLivingBase base;
            Entity used;
            CPacketUseEntity useE = (CPacketUseEntity)trans;
            if (this.InvPopHitFix.bValue && useE.getAction() == CPacketUseEntity.Action.ATTACK && (used = useE.getEntityFromWorld(Bypass.mc.world)) != null && used instanceof EntityLivingBase && (base = (EntityLivingBase)used).isEntityAlive() && (Minecraft.player.openContainer == null || Minecraft.player.openContainer instanceof ContainerPlayer) && !(Bypass.mc.currentScreen instanceof GuiInventory) && this.openContainerOutTime.hasReached(HitAura.get.msCooldown() * 4.0f)) {
                Minecraft.player.connection.preSendPacket(new CPacketCloseWindow(0));
                this.openContainerOutTime.reset();
            }
        }
        if ((packet2 = event.getPacket()) instanceof CPacketPlayer && (packet = (CPacketPlayer)packet2).isOnGround() && this.NoServerGround.bValue) {
            packet.onGround = false;
        }
        if ((packet2 = event.getPacket()) instanceof CPacketClientSettings) {
            CPacketClientSettings packet4 = (CPacketClientSettings)packet2;
            if (Minecraft.player != null && Minecraft.player.ticksExisted > 5 && this.FixSettingsKick.bValue) {
                event.cancel();
            }
        }
    }

    public boolean canCancelServerRots() {
        return this.actived && this.NoServerRotate.bValue && !this.doReduceDestack;
    }

    public void callServerRotsSpoof(float sYaw1, float sPitch1) {
        this.sYaw = sYaw1;
        this.sPitch = sPitch1;
        this.callSRS = true;
    }

    public boolean canFixPearlFlag() {
        return this.actived && this.FixPearlFlag.bValue;
    }

    @EventTarget
    public void onSend(EventSendPacket event) {
        block4: {
            CPacketPlayer packet;
            block6: {
                block5: {
                    Packet packet2;
                    if (!this.callSRS || !((packet2 = event.getPacket()) instanceof CPacketPlayer)) break block4;
                    packet = (CPacketPlayer)packet2;
                    if (!(packet instanceof CPacketPlayer.Rotation)) break block5;
                    CPacketPlayer.Rotation fPacket = (CPacketPlayer.Rotation)packet;
                    break block6;
                }
                if (!(packet instanceof CPacketPlayer.PositionRotation)) break block4;
                CPacketPlayer.PositionRotation positionRotation = (CPacketPlayer.PositionRotation)packet;
            }
            if (this.sYaw != 0.0f || this.sPitch != 0.0f) {
                packet.setRotation(this.sYaw, this.sPitch);
                this.callSRS = false;
            }
        }
    }

    @EventTarget
    public void onReceive(EventReceivePacket event) {
        Object openned;
        Object packetVecFlag;
        Object SP;
        Object object;
        SPacketPlayerPosLook look;
        if (!this.actived) {
            return;
        }
        Packet packet = event.getPacket();
        if (packet instanceof SPacketPlayerPosLook) {
            look = (SPacketPlayerPosLook)packet;
            if (this.SpawnGodmode.bValue) {
                this.gdX = look.getX();
                this.gdY = look.getY();
                this.gdZ = look.getZ();
                this.gdYaw = look.getYaw();
                this.gdPitch = look.getPitch();
            }
        }
        if ((object = event.getPacket()) instanceof SPacketEntityStatus) {
            SPacketEntityStatus status = (SPacketEntityStatus)object;
            if (Bypass.mc.world != null && status.getEntity(Bypass.mc.world) != null && (object = status.getEntity(Bypass.mc.world)) instanceof EntityPlayerSP) {
                SP = (EntityPlayerSP)object;
                if (this.FixShieldCooldown.bValue && status.getOpCode() == 30 && ((EntityLivingBase)SP).isBlocking()) {
                    ((EntityPlayer)SP).getCooldownTracker().setCooldown(Items.SHIELD, 100);
                }
            }
        }
        if (this.FixStackFlags.bValue && (SP = event.getPacket()) instanceof SPacketPlayerPosLook) {
            look = (SPacketPlayerPosLook)SP;
            if (Minecraft.player != null && Minecraft.player.getDistance(look.getX(), look.getY(), look.getZ()) < 2.0) {
                ++this.flagCPS;
            }
        }
        if ((SP = event.getPacket()) instanceof SPacketPlayerPosLook) {
            look = (SPacketPlayerPosLook)SP;
            if (this.actived && this.VulcanLiquid.bValue) {
                packetVecFlag = new Vec3d(look.x, look.y, look.z);
                Vec3d badVec = this.lastVecNote;
                if (this.getDistanceAtVec3dToVec3d((Vec3d)packetVecFlag, badVec) < 0.2 && this.getDistanceAtVec3dToVec3d((Vec3d)packetVecFlag, this.getEntityVecPosition(Minecraft.player)) > 0.1) {
                    this.noted = false;
                    Minecraft.player.ticksExisted = 0;
                    this.strafeHacked = false;
                    Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77[\u00a7lBypass\u00a7r\u00a77]: \u0432\u044b\u043a\u043b\u044e\u0447\u0438\u0442\u044c Vulcan \u043d\u0435 \u0443\u0434\u0430\u043b\u043e\u0441\u044c.", false);
                    Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77[\u00a7lBypass\u00a7r\u00a77]: \u043f\u043e\u043f\u044b\u0442\u0430\u044e\u0441\u044c \u0435\u0449\u0451 \u0440\u0430\u0437.", false);
                } else if (this.noted && !this.strafeHacked) {
                    this.strafeHacked = true;
                    Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77[\u00a7lBypass\u00a7r\u00a77]: \u0430\u043d\u0442\u0438\u0447\u0438\u0442 Vulcan \u0432\u044b\u043a\u043b\u044e\u0447\u0435\u043d.", false);
                }
            }
        }
        if ((packetVecFlag = event.getPacket()) instanceof SPacketOpenWindow) {
            SPacketOpenWindow open = (SPacketOpenWindow)packetVecFlag;
            if (Minecraft.player != null && this.CloseScreens.bValue && (openned = Bypass.mc.currentScreen) != null && (openned instanceof GuiChat && !CommandGui.isHoveredToPanel(false) && (!open.getGuiId().endsWith("container") || Bypass.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY()) == null) || openned instanceof ClickGuiScreen || openned instanceof GuiInventory)) {
                this.sendPacket(new CPacketCloseWindow(open.getWindowId()));
                event.setCancelled(true);
            }
        }
        if ((openned = event.getPacket()) instanceof SPacketCloseWindow) {
            SPacketCloseWindow close = (SPacketCloseWindow)openned;
            if (Minecraft.player != null && this.CloseScreens.bValue && (openned = Bypass.mc.currentScreen) != null && (openned instanceof GuiChat && !CommandGui.isHoveredToPanel(false) || openned instanceof ClickGuiScreen || openned instanceof GuiInventory || openned instanceof GuiOptions || openned instanceof GuiScreenResourcePacks || openned instanceof GuiIngameMenu)) {
                event.setCancelled(true);
            }
        }
    }

    @Override
    public void onToggled(boolean actived) {
        if (actived) {
            this.doReduceDestack = false;
            this.flagCPS = 0;
            this.flagReduceTicks = 0;
        } else {
            this.doReduceDestack = false;
            this.flagCPS = 0;
            this.flagReduceTicks = 0;
            this.setStrafeHacked(false);
        }
        super.onToggled(actived);
    }
}

