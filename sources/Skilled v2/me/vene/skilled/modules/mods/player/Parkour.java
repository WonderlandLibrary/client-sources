 package me.vene.skilled.modules.mods.player;
 
 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.utilities.StringRegistry;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.settings.KeyBinding;
 import net.minecraft.util.BlockPos;
 import net.minecraft.util.MathHelper;
 import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
 import net.minecraftforge.fml.common.gameevent.InputEvent;
 import net.minecraftforge.fml.common.gameevent.TickEvent;
 import org.lwjgl.input.Keyboard;
 
public class Parkour extends Module
{
    private BlockPos currentBlock;
    private Minecraft mc;
    private static boolean jumpDown;
    
    public Parkour() {
        super(StringRegistry.register("Parkour"), 0, Category.P);
        this.mc = Minecraft.getMinecraft();
    }
    
    @SubscribeEvent
    public void onKeyPress(final InputEvent.KeyInputEvent event) {
        if (Keyboard.isKeyDown(this.mc.gameSettings.keyBindJump.getKeyCode()) && Minecraft.getMinecraft() != null && Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().theWorld != null) {
            Parkour.jumpDown = true;
        }
        else {
            Parkour.jumpDown = false;
        }
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.theWorld != null) {
            if (this.mc.thePlayer.prevPosY - this.mc.thePlayer.posY > 0.4) {
                return;
            }
            final int jumpKey = this.mc.gameSettings.keyBindJump.getKeyCode();
            KeyBinding.setKeyBindState(jumpKey, false);
            KeyBinding.onTick(jumpKey);
            final double blockX = this.mc.thePlayer.posX;
            final double blockY = this.mc.thePlayer.posY - 1.0;
            final double blockZ = this.mc.thePlayer.posZ;
            final BlockPos thisBlock = new BlockPos(MathHelper.floor_double(blockX), MathHelper.floor_double(blockY), MathHelper.floor_double(blockZ));
            if (this.currentBlock == null || !this.isSameBlock(thisBlock, this.currentBlock)) {
                this.currentBlock = thisBlock;
            }
            if (this.mc.theWorld.isAirBlock(this.currentBlock) && this.mc.thePlayer.onGround && !this.mc.thePlayer.isSneaking() && !Parkour.jumpDown) {
                KeyBinding.setKeyBindState(jumpKey, true);
                KeyBinding.onTick(jumpKey);
            }
        }
    }
    
    private boolean isSameBlock(final BlockPos pos1, final BlockPos pos2) {
        return pos1.getX() == pos2.getX() && pos1.getY() == pos2.getY() && pos1.getZ() == pos2.getZ();
    }
    
    static {
        Parkour.jumpDown = false;
    }
}
