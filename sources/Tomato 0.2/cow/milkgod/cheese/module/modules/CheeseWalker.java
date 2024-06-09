/*
 * Decompiled with CFR 0_115.
 */
package cow.milkgod.cheese.module.modules;

import com.darkmagician6.eventapi.EventTarget;
import cow.milkgod.cheese.Cheese;
import cow.milkgod.cheese.commands.Command;
import cow.milkgod.cheese.commands.CommandManager;
import cow.milkgod.cheese.events.EventChatSend;
import cow.milkgod.cheese.events.EventPreMotionUpdates;
import cow.milkgod.cheese.events.EventTick;
import cow.milkgod.cheese.files.FileManager;
import cow.milkgod.cheese.module.Category;
import cow.milkgod.cheese.module.Module;
import cow.milkgod.cheese.properties.Property;
import cow.milkgod.cheese.utils.Logger;
import cow.milkgod.cheese.utils.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MovementInput;

public class CheeseWalker extends Module
{
    private boolean nc;
    private Property<Boolean> damage;
    private boolean glide;
    private double speed;
    private double glideSpeed;
    
    public CheeseWalker() {
        super("SkyWalker", 0, Category.MOVEMENT, 16776960, true, "Be the almighty Cheese god and walk amongst the potatoes", new String[] { "cwalker", "cw" });
        this.damage = new Property<Boolean>(this, "damage", false);
        this.speed = 0.8;
        this.glideSpeed = 0.035;
        this.glide = false;
        this.nc = false;
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (this.damage.value && Wrapper.mc.thePlayer.isCollidedVertically) {
            Cheese.getInstance();
            Cheese.commandManager.getCommandbyName("Damage").Toggle();
            Wrapper.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.mc.thePlayer.posX, Wrapper.mc.thePlayer.posY + 0.01, Wrapper.mc.thePlayer.posZ, false));
        }
        if (this.damage.value) {
            this.setDisplayName("SkyWalker§7 - Damage");
        }
        else {
            this.setDisplayName("SkyWalker§7 - No Damage");
        }
    }
    
    @EventTarget
    public void onEvent(final EventPreMotionUpdates pre) {
        if (this.nc) {
            if (!Wrapper.mc.thePlayer.movementInput.jump && !Wrapper.mc.thePlayer.movementInput.sneak && this.glide && Wrapper.mc.thePlayer.movementInput.moveForward == 0.0 && Wrapper.mc.thePlayer.movementInput.moveStrafe == 0.0) {
                final EntityPlayerSP thePlayer = Wrapper.mc.thePlayer;
                final EntityPlayerSP thePlayer2 = Wrapper.mc.thePlayer;
                final EntityPlayerSP thePlayer3 = Wrapper.mc.thePlayer;
                final double motionX = 0.0;
                thePlayer3.motionY = 0.0;
                thePlayer2.motionZ = 0.0;
                thePlayer.motionX = 0.0;
                pre.setCancelled(true);
            }
            else if (Wrapper.mc.thePlayer.movementInput.jump) {
                Wrapper.mc.thePlayer.motionY = this.speed;
            }
            else if (Wrapper.mc.thePlayer.movementInput.sneak) {
                Wrapper.mc.thePlayer.motionY = -this.speed;
            }
            else if (this.glide) {
                Wrapper.mc.thePlayer.motionY = -this.glideSpeed;
            }
            else {
                Wrapper.mc.thePlayer.motionY = 0.0;
            }
        }
        else if (Wrapper.mc.thePlayer.isSneaking()) {
            Wrapper.mc.thePlayer.motionY = -0.4;
        }
        else if (Wrapper.mc.gameSettings.keyBindJump.getIsKeyPressed()) {
            Wrapper.mc.thePlayer.motionY = 0.4;
        }
        else if (this.glide) {
            Wrapper.mc.thePlayer.motionY = -this.glideSpeed;
        }
        else {
            Wrapper.mc.thePlayer.motionY = 0.0;
        }
    }
    
    @Override
    protected void addCommand() {
        Cheese.getInstance();
        final CommandManager commandManager = Cheese.commandManager;
        CommandManager.addCommand(new Command("SkyWalker", "Unknown Option! ", null, "<NoCheat | Normal | Damage>", "Cheeselord shines down cheese walking shoes unto you") {
            @EventTarget
            public void onTick(final EventTick ev) {
                Label_0495: {
                    try {
                        final String nigger = EventChatSend.getMessage().split(" ")[1];
                        Label_0431: {
                            Label_0426: {
                                Label_0410: {
                                    Label_0342: {
                                        Label_0326: {
                                            final String s;
                                            final String s2;
                                            switch (s2 = (s = nigger)) {
                                                case "Normal": {
                                                    break Label_0326;
                                                }
                                                case "actual": {
                                                    break Label_0426;
                                                }
                                                case "damage": {
                                                    break Label_0342;
                                                }
                                                case "normal": {
                                                    break Label_0326;
                                                }
                                                case "values": {
                                                    break Label_0426;
                                                }
                                                case "vphase": {
                                                    break Label_0342;
                                                }
                                                case "Nocheat": {
                                                    break Label_0410;
                                                }
                                                case "sp": {
                                                    break Label_0326;
                                                }
                                                case "HCF": {
                                                    break Label_0495;
                                                }
                                                case "Skip": {
                                                    break Label_0495;
                                                }
                                                case "norm": {
                                                    break Label_0410;
                                                }
                                                case "skip": {
                                                    break Label_0495;
                                                }
                                                case "Damage": {
                                                    break Label_0342;
                                                }
                                                case "nocheat": {
                                                    break Label_0410;
                                                }
                                                default:
                                                    break;
                                            }
                                            break Label_0431;
                                        }
                                        CheeseWalker.access$0(CheeseWalker.this, false);
                                        Logger.logChat("Set SkyWalker mode to §eNormal");
                                        break Label_0495;
                                    }
                                    CheeseWalker.this.damage.setValue(!CheeseWalker.this.damage.value);
                                    Logger.logChat("Set SkyWalker damage to §e " + CheeseWalker.this.damage.value);
                                    break Label_0495;
                                }
                                CheeseWalker.access$0(CheeseWalker.this, true);
                                Logger.logChat("Set SkyWalker mode to §eNoCheat");
                                break Label_0495;
                            }
                            Logger.logChat("Current CheeseWalker mode:  §e");
                        }
                        Logger.logChat(String.valueOf(String.valueOf(this.getErrorMessage())) + this.getArguments());
                    }
                    catch (Exception ex) {
                        Logger.logChat(String.valueOf(String.valueOf(this.getErrorMessage())) + this.getArguments());
                    }
                }
                if (CheeseWalker.this.damage.value) {
                    CheeseWalker.this.setDisplayName("SkyWalker§7 - Damage");
                }
                else {
                    CheeseWalker.this.setDisplayName("SkyWalker§7 - No Damage");
                }
                Cheese.getInstance();
                Cheese.fileManager.saveFiles();
                this.Toggle();
            }
        });
    }
    
    static /* synthetic */ void access$0(final CheeseWalker cheeseWalker, final boolean nc) {
        cheeseWalker.nc = nc;
    }
}
