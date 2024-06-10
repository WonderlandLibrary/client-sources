 package me.vene.skilled.modules.mods.player;

 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.utilities.StringRegistry;
 import me.vene.skilled.values.BooleanValue;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.entity.EntityPlayerSP;
 import net.minecraft.init.Blocks;
 import net.minecraft.potion.Potion;
 import net.minecraft.util.BlockPos;
 import net.minecraft.util.MathHelper;
 import net.minecraft.util.MovingObjectPosition;
 import net.minecraftforge.client.event.MouseEvent;
 import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
 import net.minecraftforge.fml.common.gameevent.TickEvent;
 import org.lwjgl.input.Mouse;
 
public class BlockFly extends Module
{
    private boolean sneaking;
    private boolean safewalkSneaking;
    private boolean playerIsSneaking;
    private BlockPos currentBlock;
    private Minecraft mc;
    private BlockPos lastBlock;
    private BooleanValue clearBlocks;
    
    public BlockFly() {
        super(StringRegistry.register("BlockFly"), 0, Category.P);
        this.sneaking = false;
        this.safewalkSneaking = false;
        this.playerIsSneaking = false;
        this.mc = Minecraft.getMinecraft();
        this.lastBlock = null;
        this.clearBlocks = new BooleanValue(StringRegistry.register("Clear Blocks"), false);
    }
    
    public void swing() {
        final EntityPlayerSP p = this.mc.thePlayer;
        final int armSwingEnd = p.isPotionActive(Potion.digSpeed) ? (6 - (1 + p.getActivePotionEffect(Potion.digSpeed).getAmplifier())) : (p.isPotionActive(Potion.digSlowdown) ? (6 + (1 + p.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) * 2) : 6);
        if (!p.isSwingInProgress || p.swingProgressInt >= armSwingEnd / 2 || p.swingProgressInt < 0) {
            p.swingProgressInt = -1;
            p.isSwingInProgress = true;
        }
    }
    
    @Override
    public void onDisable() {
        if (this.lastBlock != null) {
            this.mc.theWorld.setBlockState(this.lastBlock, Blocks.air.getDefaultState());
        }
    }
    
    @SubscribeEvent
    public void m(final MouseEvent e) {
        if (e.buttonstate && (e.button == 0 || e.button == 1)) {
            final MovingObjectPosition mop = this.mc.objectMouseOver;
            if (mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                final int x = mop.getBlockPos().getX();
                final int z = mop.getBlockPos().getZ();
                final BlockPos pos = this.currentBlock;
                if (pos.getX() == x && pos.getZ() == z) {
                    e.setCanceled(true);
                    if (e.button == 0) {
                        this.swing();
                    }
                    Mouse.poll();
                }
            }
        }
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.theWorld != null) {
            if (this.mc.thePlayer.prevPosY - this.mc.thePlayer.posY > 0.4) {
                return;
            }
            if (this.mc.thePlayer.capabilities.isFlying) {
                return;
            }
            final double blockX = this.mc.thePlayer.posX;
            final double blockY = this.mc.thePlayer.posY - 1.0;
            final double blockZ = this.mc.thePlayer.posZ;
            final BlockPos thisBlock = new BlockPos(MathHelper.floor_double(blockX), MathHelper.floor_double(blockY), MathHelper.floor_double(blockZ));
            if (this.currentBlock == null || !this.isSameBlock(thisBlock, this.currentBlock)) {
                this.currentBlock = thisBlock;
            }
            if (this.mc.theWorld.isAirBlock(this.currentBlock)) {
                this.mc.theWorld.setBlockState(thisBlock, Blocks.barrier.getDefaultState());
                if (this.lastBlock != null) {
                    this.mc.theWorld.setBlockState(this.lastBlock, Blocks.air.getDefaultState());
                }
                this.lastBlock = thisBlock;
            }
        }
    }
    
    private boolean isSameBlock(final BlockPos pos1, final BlockPos pos2) {
        return pos1.getX() == pos2.getX() && pos1.getY() == pos2.getY() && pos1.getZ() == pos2.getZ();
    }
}
