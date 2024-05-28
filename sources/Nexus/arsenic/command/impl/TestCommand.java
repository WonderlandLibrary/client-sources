package arsenic.command.impl;

import arsenic.command.Command;
import arsenic.command.CommandInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

@CommandInfo(name = "addPlayerEntity")
public class TestCommand extends Command {

    private final Minecraft mc = Minecraft.getMinecraft();
    @Override
    public void execute(String[] args) {
        //i dont know why this gives an error
        // this is just ment for testing purposes so i can summon a player in singleplayer
        EntityPlayer entityPlayer = new EntityPlayer(mc.thePlayer.worldObj, mc.thePlayer.getGameProfile()) {
            @Override
            public boolean isSpectator() {
                return false;
            }
        };

        entityPlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
        mc.theWorld.addEntityToWorld(184792, entityPlayer);

    }
}
