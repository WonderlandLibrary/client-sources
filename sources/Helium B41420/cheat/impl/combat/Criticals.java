package rip.helium.cheat.impl.combat;

import me.hippo.systems.lwjeb.annotation.*;
import net.minecraft.client.*;
import net.minecraft.network.play.client.*;
import rip.helium.cheat.*;
import rip.helium.event.minecraft.*;
import rip.helium.utils.*;
import rip.helium.utils.property.impl.*;
import net.minecraft.network.*;

public class Criticals extends Cheat
{
    public StringsProperty mode;
    
    public Criticals() {
        super("Criticals", "Makes you crit", CheatCategory.COMBAT);
        this.mode = new StringsProperty("Mode", "", null, false, true, new String[] { "Watchdog" }, new Boolean[] { true });
    }
    
    @Collect
    public void onUpdate(final PlayerUpdateEvent e) {
        this.setMode(this.mode.getSelectedStrings().get(0));
    }
    
    @Collect
    public void onJump(final PlayerJumpEvent e) {
        Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch, true));
    }
}
