package cow.milkgod.cheese.module.modules;

import cow.milkgod.cheese.properties.*;
import cow.milkgod.cheese.module.*;
import net.minecraft.client.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.util.Timer;
import net.minecraft.network.play.client.*;
import com.darkmagician6.eventapi.*;
import net.minecraft.network.*;
import net.minecraft.client.entity.*;
import cow.milkgod.cheese.*;
import cow.milkgod.cheese.events.*;
import cow.milkgod.cheese.utils.*;
import cow.milkgod.cheese.commands.*;

public class Phase extends Module
{
    private int ticks;
    private int packetTicks;
    public static Property<PhaseMode> mode;
    private int delay;
    
    public Phase() {
        super("Phase", 47, Category.EXPLOITS, 15120384, true, "Exploits NCP to glitch through thin blocks", new String[] { "phse", "pase", "faze", "noclip" });
        Phase.mode = new Property<PhaseMode>(this, "Mode", PhaseMode.NoFlag);
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (Phase.mode.value == PhaseMode.vPhase) {
            this.setDisplayName("Phase§7 - Vertical");
        }
        if (Phase.mode.value == PhaseMode.NoFlag) {
            this.setDisplayName("Phase§7 - NoFlag");
        }
        if (Phase.mode.value == PhaseMode.Spider) {
            this.setDisplayName("Phase§7 - Spider");
        }
        if (Phase.mode.value == PhaseMode.Skip) {
            this.setDisplayName("Phase§7 - Skip");
        }
        if (Phase.mode.value == PhaseMode.Normal) {
            this.setDisplayName("Phase§7 - Normal");
        }
    }
    
    @Override
    public void toggleModule() {
        super.toggleModule();
        if (!this.getState()) {
            Timer.timerSpeed = 1.0f;
        }
    }
    
    public static boolean isInsideBlock() {
        for (int x = MathHelper.floor_double(Wrapper.mc.thePlayer.boundingBox.minX); x < MathHelper.floor_double(Wrapper.mc.thePlayer.boundingBox.maxX) + 1; ++x) {
            for (int y = MathHelper.floor_double(Wrapper.mc.thePlayer.boundingBox.minY); y < MathHelper.floor_double(Wrapper.mc.thePlayer.boundingBox.maxY) + 1; ++y) {
                for (int z = MathHelper.floor_double(Wrapper.mc.thePlayer.boundingBox.minZ); z < MathHelper.floor_double(Wrapper.mc.thePlayer.boundingBox.maxZ) + 1; ++z) {
                    final Block block = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    final AxisAlignedBB boundingBox;
                    if (block != null && !(block instanceof BlockAir) && (boundingBox = block.getCollisionBoundingBox(Wrapper.mc.theWorld, new BlockPos(x, y, z), Wrapper.mc.theWorld.getBlockState(new BlockPos(x, y, z)))) != null && Wrapper.mc.thePlayer.boundingBox.intersectsWith(boundingBox)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    @EventTarget
    public void onPacket(final EventPacketSent e) {
        if (e.getPacket() instanceof C03PacketPlayer) {
            final C03PacketPlayer player = (C03PacketPlayer)e.getPacket();
            isInsideBlock();
        }
    }
    
    @EventTarget
    public void onPost(final EventPostMotionUpdates event) {
        double multiplier = 0.3;
        final double mx = Math.cos(Math.toRadians(Wrapper.mc.thePlayer.rotationYaw + 90.0f));
        final double mz = Math.sin(Math.toRadians(Wrapper.mc.thePlayer.rotationYaw + 90.0f));
        final double x = Wrapper.mc.thePlayer.movementInput.moveForward * multiplier * mx + Wrapper.mc.thePlayer.movementInput.moveStrafe * multiplier * mz;
        final double z = Wrapper.mc.thePlayer.movementInput.moveForward * multiplier * mz - Wrapper.mc.thePlayer.movementInput.moveStrafe * multiplier * mx;
        if (Phase.mode.value == PhaseMode.vPhase) {
            if (Wrapper.mc.thePlayer.isCollidedHorizontally) {
                for (int i = 0; i < 20; ++i) {
                    Wrapper.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.mc.thePlayer.posX, Wrapper.mc.thePlayer.posY - 0.09, Wrapper.mc.thePlayer.posZ, true));
                }
                Wrapper.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.mc.thePlayer.posX + x, Wrapper.mc.thePlayer.posY, Wrapper.mc.thePlayer.posZ + z, false));
                Wrapper.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.mc.thePlayer.posX, Wrapper.mc.thePlayer.posY - 0.05, Wrapper.mc.thePlayer.posZ, false));
                for (int i = 0; i < 20; ++i) {
                    Wrapper.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.mc.thePlayer.posX, Wrapper.mc.thePlayer.posY - 0.05, Wrapper.mc.thePlayer.posZ, false));
                }
                Wrapper.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.mc.thePlayer.posX + x, Wrapper.mc.thePlayer.posY - 0.05, Wrapper.mc.thePlayer.posZ + z, false));
                Wrapper.mc.thePlayer.setPosition(Wrapper.mc.thePlayer.posX + x * 1.3, Wrapper.mc.thePlayer.posY - 0.09, Wrapper.mc.thePlayer.posZ + z * 1.3);
            }
        }
        else {
            multiplier = 0.2;
            final double xOff = Wrapper.mc.thePlayer.movementInput.moveForward * multiplier * mx + Wrapper.mc.thePlayer.movementInput.moveStrafe * multiplier * mz;
            final double zOff = Wrapper.mc.thePlayer.movementInput.moveForward * multiplier * mz - Wrapper.mc.thePlayer.movementInput.moveStrafe * multiplier * mx;
            if (Wrapper.mc.thePlayer.isCollidedHorizontally && !Wrapper.mc.thePlayer.isOnLadder() && !isInsideBlock() && Phase.mode.value == PhaseMode.Normal) {
                Wrapper.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.mc.thePlayer.posX + x, Wrapper.mc.thePlayer.posY, Wrapper.mc.thePlayer.posZ + z, false));
                final double posX2 = Wrapper.mc.thePlayer.posX;
                final double posY2 = Wrapper.mc.thePlayer.posY;
                Wrapper.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(posX2, posY2 - (Wrapper.isOnLiquid() ? 9000.0 : 0.09), Wrapper.mc.thePlayer.posZ, false));
                Wrapper.mc.thePlayer.setPosition(Wrapper.mc.thePlayer.posX + x, Wrapper.mc.thePlayer.posY, Wrapper.mc.thePlayer.posZ + z);
                return;
            }
            if (!Wrapper.mc.thePlayer.isCollidedHorizontally || Phase.mode.value != PhaseMode.NoFlag) {
                if (Wrapper.mc.thePlayer.isCollidedHorizontally && Phase.mode.value == PhaseMode.Skip) {
                    final EntityPlayerSP thePlayer4;
                    final EntityPlayerSP thePlayer = thePlayer4 = Wrapper.mc.thePlayer;
                    thePlayer4.motionX *= 0.5;
                    final EntityPlayerSP thePlayer5;
                    final EntityPlayerSP thePlayer2 = thePlayer5 = Wrapper.mc.thePlayer;
                    thePlayer5.motionZ *= 0.5;
                    final double[] OPOP = { -0.02500000037252903, -0.028571428997176036, -0.033333333830038704, -0.04000000059604645, -0.05000000074505806, -0.06666666766007741, -0.10000000149011612, 0.0, -0.20000000298023224, -0.04000000059604645, -0.033333333830038704, -0.028571428997176036, -0.02500000037252903 };
                    for (int j = 0; j < OPOP.length; ++j) {
                        Wrapper.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.mc.thePlayer.posX, Wrapper.mc.thePlayer.posY + OPOP[j], Wrapper.mc.thePlayer.posZ, false));
                        Wrapper.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.mc.thePlayer.posX + xOff * j, Wrapper.mc.thePlayer.boundingBox.minY, Wrapper.mc.thePlayer.posZ + zOff * j, false));
                    }
                    Wrapper.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.mc.thePlayer.posX, Wrapper.mc.thePlayer.posY, Wrapper.mc.thePlayer.posZ, true));
                    Wrapper.mc.thePlayer.setPosition(Wrapper.mc.thePlayer.posX + xOff, Wrapper.mc.thePlayer.posY, Wrapper.mc.thePlayer.posZ + zOff);
                    Wrapper.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.mc.thePlayer.posX, Wrapper.mc.thePlayer.boundingBox.minY, Wrapper.mc.thePlayer.posZ, false));
                }
                if (Phase.mode.value == PhaseMode.Spider && isInsideBlock()) {
                    if (isInsideBlock()) {
                        final EntityPlayerSP thePlayer6;
                        final EntityPlayerSP thePlayer3 = thePlayer6 = Wrapper.mc.thePlayer;
                        thePlayer6.posY += 0.1;
                    }
                    Wrapper.mc.thePlayer.motionY = 0.065;
                }
                else {
                    this.ticks = 0;
                }
                return;
            }
            if (Wrapper.mc.thePlayer.isCollidedHorizontally && !Wrapper.mc.thePlayer.isOnLadder() && !Wrapper.isInsideBlock()) {
                Wrapper.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.mc.thePlayer.posX + x, Wrapper.mc.thePlayer.posY, Wrapper.mc.thePlayer.posZ + z, false));
                for (int k = 1; k < 10; ++k) {
                    Wrapper.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.mc.thePlayer.posX, 8.988465674311579E307, Wrapper.mc.thePlayer.posZ, false));
                }
                Wrapper.mc.thePlayer.setPosition(Wrapper.mc.thePlayer.posX + x, Wrapper.mc.thePlayer.posY, Wrapper.mc.thePlayer.posZ + z);
            }
        }
    }
    
    @EventTarget
    public void onPacketSent(final EventPacketSent e) {
        if (e.getPacket() instanceof C03PacketPlayer) {
            final C03PacketPlayer player = (C03PacketPlayer)e.getPacket();
            if (isInsideBlock() && Phase.mode.value == PhaseMode.Spider && Wrapper.mc.thePlayer.ticksExisted % 2 == 0) {
                player.field_149474_g = !player.field_149474_g;
                player.field_149480_h = !player.field_149480_h;
            }
        }
    }
    
    @EventTarget
    public void onBB(final EventBoundingBox bb) {
        if (Wrapper.mc.thePlayer == null) {
            return;
        }
        Wrapper.mc.thePlayer.noClip = true;
        if (bb.getY() > Wrapper.mc.thePlayer.posY + (isInsideBlock() ? 0 : 1)) {
            bb.setBoundingBox(null);
        }
        if (Wrapper.mc.thePlayer.isCollidedHorizontally && bb.getY() > Wrapper.mc.thePlayer.boundingBox.minY - 0.4) {
            bb.setBoundingBox(null);
        }
    }
    
    @EventTarget
    public void onPreMotion(final EventPreMotionUpdates e) {
        if (isInsideBlock() && Wrapper.mc.thePlayer.ticksExisted % 2 == 0 && Phase.mode.value == PhaseMode.Spider) {
            e.setCancelled(true);
        }
    }
    
    @EventTarget
    public void onPush(final EventPushOutOfBlock push) {
        push.setCancelled(true);
    }
    
    @Override
    protected void addCommand() {
        Cheese.getInstance();
        final CommandManager commandManager = Cheese.commandManager;
        CommandManager.addCommand(new Command("Phase", "Unknown Option! ", null, "<Spider | Skip | vPhase | Normal | NoFlag>", "Manages Criticals") {
            @EventTarget
            public void onTick(final EventTick ev) {
                Label_0672: {
                    try {
                        final String nigger = EventChatSend.getMessage().split(" ")[1];
                        Label_0608: {
                            Label_0578: {
                                Label_0536: {
                                    Label_0494: {
                                        Label_0452: {
                                            Label_0410: {
                                                Label_0368: {
                                                    final String s;
                                                    final String s2;
                                                    switch (s2 = (s = nigger)) {
                                                        case "Noflag": {
                                                            break Label_0410;
                                                        }
                                                        case "Normal": {
                                                            break Label_0536;
                                                        }
                                                        case "Spider": {
                                                            break Label_0368;
                                                        }
                                                        case "Vphase": {
                                                            break Label_0494;
                                                        }
                                                        case "actual": {
                                                            break Label_0578;
                                                        }
                                                        case "noflag": {
                                                            break Label_0410;
                                                        }
                                                        case "normal": {
                                                            break Label_0536;
                                                        }
                                                        case "spider": {
                                                            break Label_0368;
                                                        }
                                                        case "vPhase": {
                                                            break Label_0494;
                                                        }
                                                        case "values": {
                                                            break Label_0578;
                                                        }
                                                        case "vphase": {
                                                            break Label_0494;
                                                        }
                                                        case "sp": {
                                                            break Label_0368;
                                                        }
                                                        case "HCF": {
                                                            break Label_0452;
                                                        }
                                                        case "Skip": {
                                                            break Label_0452;
                                                        }
                                                        case "norm": {
                                                            break Label_0536;
                                                        }
                                                        case "skip": {
                                                            break Label_0452;
                                                        }
                                                        default:
                                                            break;
                                                    }
                                                    break Label_0608;
                                                }
                                                Phase.mode.value = PhaseMode.Spider;
                                                Logger.logChat("Set Phase mode to §e" + Phase.mode.value.toString());
                                                break Label_0672;
                                            }
                                            Phase.mode.value = PhaseMode.NoFlag;
                                            Logger.logChat("Set Phase mode to §e" + Phase.mode.value.toString());
                                            break Label_0672;
                                        }
                                        Phase.mode.value = PhaseMode.Skip;
                                        Logger.logChat("Set Phase mode to §e" + Phase.mode.value.toString());
                                        break Label_0672;
                                    }
                                    Phase.mode.value = PhaseMode.vPhase;
                                    Logger.logChat("Set Phase mode to §e" + Phase.mode.value.toString());
                                    break Label_0672;
                                }
                                Phase.mode.value = PhaseMode.Normal;
                                Logger.logChat("Set Phase mode to §e" + Phase.mode.value.toString());
                                break Label_0672;
                            }
                            Logger.logChat("Current Phase mode:  §e" + Phase.mode.value.toString());
                        }
                        Logger.logChat(String.valueOf(String.valueOf(this.getErrorMessage())) + this.getArguments());
                    }
                    catch (Exception ex) {
                        Logger.logChat(String.valueOf(String.valueOf(this.getErrorMessage())) + this.getArguments());
                    }
                }
                Cheese.getInstance();
                Cheese.fileManager.saveFiles();
                if (Phase.mode.value == PhaseMode.vPhase) {
                    Phase.this.setDisplayName("Phase§7 - Vertical");
                }
                if (Phase.mode.value == PhaseMode.NoFlag) {
                    Phase.this.setDisplayName("Phase§7 - NoFlag");
                }
                if (Phase.mode.value == PhaseMode.Spider) {
                    Phase.this.setDisplayName("Phase§7 - Spider");
                }
                if (Phase.mode.value == PhaseMode.Skip) {
                    Phase.this.setDisplayName("Phase§7 - Skip");
                }
                if (Phase.mode.value == PhaseMode.Normal) {
                    Phase.this.setDisplayName("Phase§7 - Normal");
                }
                this.Toggle();
            }
        });
    }
    
    private enum PhaseMode
    {
        Spider("Spider", 0, "Spider", 0), 
        Skip("Skip", 1, "Skip", 1), 
        vPhase("vPhase", 2, "vPhase", 2), 
        Normal("Normal", 3, "Normal", 3), 
        NoFlag("NoFlag", 4, "NoFlag", 4);
        
        private PhaseMode(final String s2, final int n2, final String s, final int n) {
        }
    }
}
