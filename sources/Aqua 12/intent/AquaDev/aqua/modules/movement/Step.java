// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.movement;

import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.util.BlockPos;
import intent.AquaDev.aqua.utils.PlayerUtil;
import intent.AquaDev.aqua.modules.combat.Killaura;
import events.listeners.EventUpdate;
import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.modules.Module;

public class Step extends Module
{
    public Step() {
        super("Step", Type.Movement, "Step", 0, Category.Movement);
        Aqua.setmgr.register(new Setting("Boost", this, 0.2, 0.1, 0.85, false));
        Aqua.setmgr.register(new Setting("BlockHeight", this, 1.0, 1.0, 30.0, false));
        Aqua.setmgr.register(new Setting("Mode", this, "Watchdog", new String[] { "Watchdog", "Vanilla" }));
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        Step.mc.thePlayer.stepHeight = 0.5f;
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventUpdate) {
            if (Aqua.setmgr.getSetting("StepMode").getCurrentMode().equalsIgnoreCase("Watchdog")) {
                if (Step.mc.gameSettings.keyBindLeft.pressed || Step.mc.gameSettings.keyBindRight.pressed || Step.mc.gameSettings.keyBindBack.pressed || Killaura.target != null) {
                    return;
                }
                if (Step.mc.thePlayer.onGround && Step.mc.thePlayer.isCollidedHorizontally) {
                    Step.mc.thePlayer.motionY = 0.3700000047683716;
                }
                if (Step.mc.thePlayer.isCollidedHorizontally) {
                    PlayerUtil.setSpeed(PlayerUtil.getSpeed() + Aqua.setmgr.getSetting("StepBoost").getCurrentNumber());
                }
            }
            if (Aqua.setmgr.getSetting("StepMode").getCurrentMode().equalsIgnoreCase("Vanilla") && Step.mc.thePlayer.isCollidedHorizontally) {
                Fly.sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0.0f, 0.5f, 0.0f));
                Fly.sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(Fly.getX(), Fly.getY() - 1.5, Fly.getZ()), 1, new ItemStack(Blocks.stone.getItem(Step.mc.theWorld, new BlockPos(-1, -1, -1))), 0.0f, 0.94f, 0.0f));
                Step.mc.thePlayer.stepHeight = (float)Aqua.setmgr.getSetting("StepBlockHeight").getCurrentNumber();
            }
        }
    }
}
