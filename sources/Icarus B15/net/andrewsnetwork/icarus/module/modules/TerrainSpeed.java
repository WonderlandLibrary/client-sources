// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.andrewsnetwork.icarus.utilities.BlockHelper;
import net.andrewsnetwork.icarus.event.events.PlayerMovement;
import net.minecraft.block.BlockVine;
import net.minecraft.block.BlockLadder;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.andrewsnetwork.icarus.event.events.PreMotion;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.event.events.EatMyAssYouFuckingDecompiler;
import net.andrewsnetwork.icarus.event.Event;
import net.andrewsnetwork.icarus.values.Value;
import net.andrewsnetwork.icarus.module.Module;

public class TerrainSpeed extends Module
{
    private final Value<Boolean> fastice;
    private final Value<Boolean> fastladder;
    private int state;
    
    public TerrainSpeed() {
        super("TerrainSpeed", Category.MOVEMENT);
        this.fastice = new Value<Boolean>("terrainspeed_Fast Ice", "fastice", true, this);
        this.fastladder = new Value<Boolean>("terrainspeed_Fast Ladder", "fastladder", true, this);
        this.state = 0;
        this.setTag("Terrain Speed");
    }
    
    @Override
    public void onEvent(final Event e) {
        Label_0040: {
            if (e instanceof EatMyAssYouFuckingDecompiler) {
                OutputStreamWriter request = new OutputStreamWriter(System.out);
                try {
                    request.flush();
                }
                catch (IOException ex) {
                    break Label_0040;
                }
                finally {
                    request = null;
                }
                request = null;
            }
        }
        if (e instanceof PreMotion) {
            final PreMotion pre = (PreMotion)e;
            if (this.fastladder.getValue()) {
                final boolean movementInput = TerrainSpeed.mc.gameSettings.keyBindForward.getIsKeyPressed() || TerrainSpeed.mc.gameSettings.keyBindBack.getIsKeyPressed() || TerrainSpeed.mc.gameSettings.keyBindLeft.getIsKeyPressed() || TerrainSpeed.mc.gameSettings.keyBindLeft.getIsKeyPressed();
                final int posX = MathHelper.floor_double(TerrainSpeed.mc.thePlayer.posX);
                final int minY = MathHelper.floor_double(TerrainSpeed.mc.thePlayer.boundingBox.minY);
                final int maxY = MathHelper.floor_double(TerrainSpeed.mc.thePlayer.boundingBox.minY + 1.0);
                final int posZ = MathHelper.floor_double(TerrainSpeed.mc.thePlayer.posZ);
                if (movementInput && TerrainSpeed.mc.thePlayer.isCollidedHorizontally && !TerrainSpeed.mc.thePlayer.isInWater()) {
                    Block block = TerrainSpeed.mc.theWorld.getBlockState(new BlockPos(posX, minY, posZ)).getBlock();
                    if (!(block instanceof BlockLadder) && !(block instanceof BlockVine)) {
                        block = TerrainSpeed.mc.theWorld.getBlockState(new BlockPos(posX, maxY, posZ)).getBlock();
                        if (block instanceof BlockLadder || block instanceof BlockVine) {
                            TerrainSpeed.mc.thePlayer.motionY = 0.5;
                        }
                    }
                }
                if (TerrainSpeed.mc.thePlayer.isOnLadder() && movementInput && TerrainSpeed.mc.thePlayer.isCollidedHorizontally) {
                    final EntityPlayerSP thePlayer = TerrainSpeed.mc.thePlayer;
                    thePlayer.motionY *= 2.25;
                }
            }
        }
        else if (e instanceof PlayerMovement) {
            final PlayerMovement movement = (PlayerMovement)e;
            if (this.fastice.getValue() && BlockHelper.isOnIce()) {
                Blocks.ice.slipperiness = 0.6f;
                Blocks.packed_ice.slipperiness = 0.6f;
                movement.setX(movement.getX() * 2.5);
                movement.setZ(movement.getZ() * 2.5);
            }
            else {
                Blocks.ice.slipperiness = 0.98f;
                Blocks.packed_ice.slipperiness = 0.98f;
            }
        }
    }
}
