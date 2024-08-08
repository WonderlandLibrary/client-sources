// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.movement;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayer;
import me.perry.mcdonalds.event.events.PacketEvent;
import net.minecraft.client.gui.GuiChat;
import me.perry.mcdonalds.event.events.KeyPressedEvent;
import net.minecraft.util.MovementInput;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraft.item.Item;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import me.perry.mcdonalds.McDonalds;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.GuiScreenOptionsSounds;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.settings.KeyBinding;
import me.perry.mcdonalds.features.setting.Setting;
import me.perry.mcdonalds.features.modules.Module;

public class NoSlowDown extends Module
{
    public Setting<Boolean> guiMove;
    public Setting<Boolean> noSlow;
    public Setting<Boolean> soulSand;
    public Setting<Boolean> strict;
    public Setting<Boolean> sneakPacket;
    public Setting<Boolean> endPortal;
    public Setting<Boolean> webs;
    public final Setting<Double> webHorizontalFactor;
    public final Setting<Double> webVerticalFactor;
    private static NoSlowDown INSTANCE;
    private boolean sneaking;
    private static KeyBinding[] keys;
    
    public NoSlowDown() {
        super("NoSlowDown", "Prevents you from getting slowed down.", Category.MOVEMENT, true, false, false);
        this.guiMove = (Setting<Boolean>)this.register(new Setting("GuiMove", (T)true));
        this.noSlow = (Setting<Boolean>)this.register(new Setting("NoSlow", (T)true));
        this.soulSand = (Setting<Boolean>)this.register(new Setting("SoulSand", (T)true));
        this.strict = (Setting<Boolean>)this.register(new Setting("Strict", (T)false));
        this.sneakPacket = (Setting<Boolean>)this.register(new Setting("SneakPacket", (T)false));
        this.endPortal = (Setting<Boolean>)this.register(new Setting("EndPortal", (T)false));
        this.webs = (Setting<Boolean>)this.register(new Setting("Webs", (T)false));
        this.webHorizontalFactor = (Setting<Double>)this.register(new Setting("WebHSpeed", (T)2.0, (T)0.0, (T)100.0));
        this.webVerticalFactor = (Setting<Double>)this.register(new Setting("WebVSpeed", (T)2.0, (T)0.0, (T)100.0));
        this.sneaking = false;
        this.setInstance();
    }
    
    private void setInstance() {
        NoSlowDown.INSTANCE = this;
    }
    
    public static NoSlowDown getInstance() {
        if (NoSlowDown.INSTANCE == null) {
            NoSlowDown.INSTANCE = new NoSlowDown();
        }
        return NoSlowDown.INSTANCE;
    }
    
    @Override
    public void onUpdate() {
        if (this.guiMove.getValue()) {
            if (NoSlowDown.mc.currentScreen instanceof GuiOptions || NoSlowDown.mc.currentScreen instanceof GuiVideoSettings || NoSlowDown.mc.currentScreen instanceof GuiScreenOptionsSounds || NoSlowDown.mc.currentScreen instanceof GuiContainer || NoSlowDown.mc.currentScreen instanceof GuiIngameMenu) {
                for (final KeyBinding bind : NoSlowDown.keys) {
                    KeyBinding.setKeyBindState(bind.getKeyCode(), Keyboard.isKeyDown(bind.getKeyCode()));
                }
            }
            else if (NoSlowDown.mc.currentScreen == null) {
                for (final KeyBinding bind : NoSlowDown.keys) {
                    if (!Keyboard.isKeyDown(bind.getKeyCode())) {
                        KeyBinding.setKeyBindState(bind.getKeyCode(), false);
                    }
                }
            }
        }
        if (this.webs.getValue() && McDonalds.moduleManager.getModuleByClass(Flight.class).isDisabled() && McDonalds.moduleManager.getModuleByClass(PacketFly.class).isDisabled() && NoSlowDown.mc.player.isInWeb) {
            final EntityPlayerSP player4;
            final EntityPlayerSP player = player4 = NoSlowDown.mc.player;
            player4.motionX *= this.webHorizontalFactor.getValue();
            final EntityPlayerSP player5;
            final EntityPlayerSP player2 = player5 = NoSlowDown.mc.player;
            player5.motionZ *= this.webHorizontalFactor.getValue();
            final EntityPlayerSP player6;
            final EntityPlayerSP player3 = player6 = NoSlowDown.mc.player;
            player6.motionY *= this.webVerticalFactor.getValue();
        }
        final Item item = NoSlowDown.mc.player.getActiveItemStack().getItem();
        if (this.sneaking && !NoSlowDown.mc.player.isHandActive() && this.sneakPacket.getValue()) {
            NoSlowDown.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)NoSlowDown.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.sneaking = false;
        }
    }
    
    @SubscribeEvent
    public void onUseItem(final PlayerInteractEvent.RightClickItem event) {
        final Item item = NoSlowDown.mc.player.getHeldItem(event.getHand()).getItem();
        if ((item instanceof ItemFood || item instanceof ItemBow || (item instanceof ItemPotion && this.sneakPacket.getValue())) && !this.sneaking) {
            NoSlowDown.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)NoSlowDown.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            this.sneaking = true;
        }
    }
    
    @SubscribeEvent
    public void onInput(final InputUpdateEvent event) {
        if (this.noSlow.getValue() && NoSlowDown.mc.player.isHandActive() && !NoSlowDown.mc.player.isRiding()) {
            final MovementInput movementInput3;
            final MovementInput movementInput = movementInput3 = event.getMovementInput();
            movementInput3.moveStrafe *= 5.0f;
            final MovementInput movementInput4;
            final MovementInput movementInput2 = movementInput4 = event.getMovementInput();
            movementInput4.moveForward *= 5.0f;
        }
    }
    
    @SubscribeEvent
    public void onKeyEvent(final KeyPressedEvent event) {
        if (this.guiMove.getValue() && event.getStage() == 0 && !(NoSlowDown.mc.currentScreen instanceof GuiChat)) {
            event.info = event.pressed;
        }
    }
    
    @SubscribeEvent
    public void onPacket(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer && this.strict.getValue() && this.noSlow.getValue() && NoSlowDown.mc.player.isHandActive() && !NoSlowDown.mc.player.isRiding()) {
            NoSlowDown.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, new BlockPos(Math.floor(NoSlowDown.mc.player.posX), Math.floor(NoSlowDown.mc.player.posY), Math.floor(NoSlowDown.mc.player.posZ)), EnumFacing.DOWN));
        }
    }
    
    static {
        NoSlowDown.INSTANCE = new NoSlowDown();
        NoSlowDown.keys = new KeyBinding[] { NoSlowDown.mc.gameSettings.keyBindForward, NoSlowDown.mc.gameSettings.keyBindBack, NoSlowDown.mc.gameSettings.keyBindLeft, NoSlowDown.mc.gameSettings.keyBindRight, NoSlowDown.mc.gameSettings.keyBindJump, NoSlowDown.mc.gameSettings.keyBindSprint };
    }
}
