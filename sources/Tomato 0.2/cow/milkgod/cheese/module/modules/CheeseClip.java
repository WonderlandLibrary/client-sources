/*
 * Decompiled with CFR 0_115.
 */
package cow.milkgod.cheese.module.modules;

import com.darkmagician6.eventapi.EventTarget;
import cow.milkgod.cheese.Cheese;
import cow.milkgod.cheese.commands.Command;
import cow.milkgod.cheese.commands.CommandManager;
import cow.milkgod.cheese.events.EventBoundingBox;
import cow.milkgod.cheese.events.EventChatSend;
import cow.milkgod.cheese.events.EventPreMotionUpdates;
import cow.milkgod.cheese.events.EventTick;
import cow.milkgod.cheese.module.Category;
import cow.milkgod.cheese.module.Module;
import cow.milkgod.cheese.utils.Logger;
import cow.milkgod.cheese.utils.Timer;
import cow.milkgod.cheese.utils.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.world.World;

public class CheeseClip extends Module
{
    private boolean vanilla;
    private boolean spider;
    private Timer timer;
    private int resetNext;
    
    public CheeseClip() {
        super("SkipPhase", 0, Category.EXPLOITS, 26316, true, "Skippy phase", new String[] { "sclip", "clip" });
        this.timer = new Timer();
    }
    
    @EventTarget
    private void onPreUpdate(final EventPreMotionUpdates event) {
        if (this.vanilla) {
            --this.resetNext;
            double xOff = 0.0;
            double zOff = 0.0;
            final double multiplier = 2.6;
            final double mx = Math.cos(Math.toRadians(Wrapper.mc.thePlayer.rotationYaw + 90.0f));
            final double mz = Math.sin(Math.toRadians(Wrapper.mc.thePlayer.rotationYaw + 90.0f));
            xOff = Wrapper.mc.thePlayer.movementInput.moveForward * 2.6 * mx + Wrapper.mc.thePlayer.movementInput.moveStrafe * 2.6 * mz;
            zOff = Wrapper.mc.thePlayer.movementInput.moveForward * 2.6 * mz - Wrapper.mc.thePlayer.movementInput.moveStrafe * 2.6 * mx;
            if (this.isInsideBlock() && Wrapper.mc.thePlayer.isSneaking()) {
                this.resetNext = 1;
            }
            if (this.resetNext > 0) {
                Wrapper.mc.thePlayer.boundingBox.offsetAndUpdate(xOff, 0.0, zOff);
            }
        }
        else if (this.timer.delay(150.0f) && Wrapper.mc.thePlayer.isCollidedHorizontally) {
            float yaw = Wrapper.mc.thePlayer.rotationYaw;
            if (Wrapper.mc.thePlayer.moveForward < 0.0f) {
                yaw += 180.0f;
            }
            if (Wrapper.mc.thePlayer.moveStrafing > 0.0f) {
                yaw -= 90.0f * ((Wrapper.mc.thePlayer.moveForward < 0.0f) ? -0.5f : ((Wrapper.mc.thePlayer.moveForward > 0.0f) ? 0.5f : 1.0f));
            }
            if (Wrapper.mc.thePlayer.moveStrafing < 0.0f) {
                yaw += 90.0f * ((Wrapper.mc.thePlayer.moveForward < 0.0f) ? -0.5f : ((Wrapper.mc.thePlayer.moveForward > 0.0f) ? 0.5f : 1.0f));
            }
            final double horizontalMultiplier = 0.3;
            final double xOffset = (float)Math.cos((yaw + 90.0f) * 3.141592653589793 / 180.0) * 0.3;
            final double zOffset = (float)Math.sin((yaw + 90.0f) * 3.141592653589793 / 180.0) * 0.3;
            double yOffset = 0.0;
            for (int i = 0; i < 3; ++i) {
                yOffset += 0.01;
                Wrapper.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.mc.thePlayer.posX, Wrapper.mc.thePlayer.posY - yOffset, Wrapper.mc.thePlayer.posZ, Wrapper.mc.thePlayer.onGround));
                Wrapper.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.mc.thePlayer.posX + xOffset * i, Wrapper.mc.thePlayer.posY, Wrapper.mc.thePlayer.posZ + zOffset * i, Wrapper.mc.thePlayer.onGround));
            }
        }
        else if (!Wrapper.mc.thePlayer.isCollidedHorizontally) {
            this.timer.reset();
        }
    }
    
    @EventTarget
    private void onSetBB(final EventBoundingBox event) {
        if ((this.spider && this.isInsideBlock() && Wrapper.mc.gameSettings.keyBindJump.pressed) || (!this.isInsideBlock() && event.boundingBox != null && event.boundingBox.maxY > Wrapper.mc.thePlayer.boundingBox.minY && this.vanilla && Wrapper.mc.thePlayer.isSneaking())) {
            event.boundingBox = null;
        }
    }
    
    private boolean isInsideBlock() {
        for (int x = MathHelper.floor_double(Wrapper.mc.thePlayer.boundingBox.minX); x < MathHelper.floor_double(Wrapper.mc.thePlayer.boundingBox.maxX) + 1; ++x) {
            for (int y = MathHelper.floor_double(Wrapper.mc.thePlayer.boundingBox.minY); y < MathHelper.floor_double(Wrapper.mc.thePlayer.boundingBox.maxY) + 1; ++y) {
                for (int z = MathHelper.floor_double(Wrapper.mc.thePlayer.boundingBox.minZ); z < MathHelper.floor_double(Wrapper.mc.thePlayer.boundingBox.maxZ) + 1; ++z) {
                    final Block block = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (block != null && !(block instanceof BlockAir)) {
                        AxisAlignedBB boundingBox = block.getCollisionBoundingBox(Wrapper.mc.theWorld, new BlockPos(x, y, z), Wrapper.mc.theWorld.getBlockState(new BlockPos(x, y, z)));
                        if (block instanceof BlockHopper) {
                            boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
                        }
                        if (boundingBox != null && Wrapper.mc.thePlayer.boundingBox.intersectsWith(boundingBox)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    @Override
    protected void addCommand() {
        Cheese.getInstance();
        final CommandManager commandManager = Cheese.commandManager;
        CommandManager.addCommand(new Command("SkipPhase", "Unknown Option! ", null, "<Vanilla | Spider>", "Cheese into the best places") {
            @EventTarget
            public void onTick(final EventTick ev) {
                Label_0443: {
                    try {
                        final String nigger = EventChatSend.getMessage().split(" ")[1];
                        Label_0379: {
                            Label_0374: {
                                Label_0350: {
                                    Label_0326: {
                                        final String s;
                                        final String s2;
                                        switch (s2 = (s = nigger)) {
                                            case "Spider": {
                                                break Label_0326;
                                            }
                                            case "Vphase": {
                                                break Label_0443;
                                            }
                                            case "actual": {
                                                break Label_0374;
                                            }
                                            case "spider": {
                                                break Label_0326;
                                            }
                                            case "vPhase": {
                                                break Label_0443;
                                            }
                                            case "values": {
                                                break Label_0374;
                                            }
                                            case "vphase": {
                                                break Label_0443;
                                            }
                                            case "sp": {
                                                break Label_0326;
                                            }
                                            case "HCF": {
                                                break Label_0443;
                                            }
                                            case "Skip": {
                                                break Label_0443;
                                            }
                                            case "norm": {
                                                break Label_0350;
                                            }
                                            case "skip": {
                                                break Label_0443;
                                            }
                                            case "vanilla": {
                                                break Label_0350;
                                            }
                                            case "Vanilla": {
                                                break Label_0350;
                                            }
                                            default:
                                                break;
                                        }
                                        break Label_0379;
                                    }
                                    CheeseClip.access$0(CheeseClip.this, true);
                                    CheeseClip.access$1(CheeseClip.this, false);
                                    Logger.logChat("Set CheeseClip mode to §eSpider");
                                    break Label_0443;
                                }
                                CheeseClip.access$0(CheeseClip.this, false);
                                CheeseClip.access$1(CheeseClip.this, true);
                                Logger.logChat("Set CheeseClip mode toVanilla");
                                break Label_0443;
                            }
                            Logger.logChat("Current CheeseClip mode:  §e");
                        }
                        Logger.logChat(String.valueOf(String.valueOf(this.getErrorMessage())) + this.getArguments());
                    }
                    catch (Exception ex) {
                        Logger.logChat(String.valueOf(String.valueOf(this.getErrorMessage())) + this.getArguments());
                    }
                }
                this.Toggle();
            }
        });
    }
    
    static /* synthetic */ void access$0(final CheeseClip cheeseClip, final boolean spider) {
        cheeseClip.spider = spider;
    }
    
    static /* synthetic */ void access$1(final CheeseClip cheeseClip, final boolean vanilla) {
        cheeseClip.vanilla = vanilla;
    }
}
