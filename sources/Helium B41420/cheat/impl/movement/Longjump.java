package rip.helium.cheat.impl.movement;

import me.hippo.systems.lwjeb.annotation.Collect;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import rip.helium.Helium;
import rip.helium.cheat.Cheat;
import rip.helium.cheat.CheatCategory;
import rip.helium.cheat.impl.player.Phase;
import rip.helium.event.Stage;
import rip.helium.event.minecraft.PlayerJumpEvent;
import rip.helium.event.minecraft.PlayerMoveEvent;
import rip.helium.event.minecraft.PlayerSlowdownEvent;
import rip.helium.event.minecraft.PlayerUpdateEvent;
import rip.helium.event.minecraft.SendPacketEvent;
import rip.helium.notification.mgmt.NotificationManager;
import rip.helium.utils.MoveUtils;
import rip.helium.utils.PlayerUtils;
import rip.helium.utils.Stopwatch;
import rip.helium.utils.property.impl.StringsProperty;


public class Longjump extends Cheat {

    private final Stopwatch boostTimer = new Stopwatch();
    private int stage;
    private double moveSpeed, lastDist;
    private boolean damaged;

    public Longjump() {
        super("LongJump", "epico gamero", CheatCategory.MOVEMENT);
    }

    @Override
    public void onEnable() {
        this.stage = 0;
        this.lastDist = 0;
        this.moveSpeed = 0;
        this.damaged = false;
        this.boostTimer.reset();
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1.0F;
        mc.thePlayer.motionX = 0.0D;
        mc.thePlayer.motionZ = 0.0D;
    }

    @Collect
    public final void onMove(PlayerMoveEvent moveEvent) {
    }
}