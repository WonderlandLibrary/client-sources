// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.commands.impl;

import net.minecraft.entity.player.EntityPlayer;
import xyz.niggfaclient.utils.other.Printer;
import xyz.niggfaclient.utils.other.PathFinder;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import xyz.niggfaclient.commands.Command;

public class Teleport extends Command
{
    public Minecraft mc;
    
    public Teleport() {
        super("Teleport", "", "", new String[] { "tp" });
        this.mc = Minecraft.getMinecraft();
    }
    
    @Override
    public void onCommand(final String[] args, final String command) {
        if (args.length == 3) {
            PathFinder.tpToLocation(500.0, 9.5, 9.0, new ArrayList<Vec3>(), new ArrayList<Vec3>(), new BlockPos(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2])));
            this.mc.thePlayer.setPosition(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]));
            Printer.addMessage("Teleported to " + Double.parseDouble(args[0]) + " " + Double.parseDouble(args[1]) + " " + Double.parseDouble(args[2]));
        }
        else {
            final EntityPlayer player = this.mc.theWorld.playerEntities.stream().filter(entity -> entity.getGameProfile().getName().equalsIgnoreCase(args[0])).findFirst().orElse(null);
            if (player != null) {
                PathFinder.tpToLocation(500.0, 9.5, 9.0, new ArrayList<Vec3>(), new ArrayList<Vec3>(), new BlockPos(player.posX, player.posY, player.posZ));
                this.mc.thePlayer.setPosition(player.posX, player.posY, player.posZ);
                Printer.addMessage("Successfully teleported to " + player.getName() + ".");
            }
            else {
                Printer.addMessage("Could not find the player!");
            }
        }
    }
}
