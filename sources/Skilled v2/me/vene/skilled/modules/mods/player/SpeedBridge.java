 package me.vene.skilled.modules.mods.player;

 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.utilities.StringRegistry;
 import me.vene.skilled.values.BooleanValue;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.settings.KeyBinding;
 import net.minecraft.entity.Entity;
 import net.minecraft.item.ItemBlock;
 import net.minecraft.network.Packet;
 import net.minecraft.network.play.client.C0BPacketEntityAction;
 import net.minecraft.util.BlockPos;
 import net.minecraft.util.MathHelper;
 import net.minecraftforge.fml.common.gameevent.TickEvent;
 
public class SpeedBridge extends Module
{
    private boolean sneaking;
    private boolean safewalkSneaking;
    private boolean playerIsSneaking;
    private BlockPos currentBlock;
    private Minecraft mc;
    private BooleanValue onlyBlocks;
    private BlockPos lastBlock;
    
    public SpeedBridge() {
        super(StringRegistry.register("Speed Bridge"), 0, Category.P);
        this.sneaking = false;
        this.safewalkSneaking = false;
        this.playerIsSneaking = false;
        this.mc = Minecraft.getMinecraft();
        this.addOption(this.onlyBlocks = new BooleanValue(StringRegistry.register("Only Blocks"), false));
    }
    
    @Override
    public void onDisable() {
        KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindSneak.getKeyCode(), false);
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
            if (this.onlyBlocks.getState() && (this.mc.thePlayer.getCurrentEquippedItem() == null || !(this.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock))) {
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
                this.setSafewalkSneaking(true);
            }
            else {
                this.setSafewalkSneaking(false);
            }
            this.checkSneak();
        }
    }
    
    private void doSafewalk() {
        if (this.isSneaking() != this.mc.thePlayer.isSneaking()) {
            KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindSneak.getKeyCode(), this.isSneaking());
        }
    }
    
    private boolean isSneaking() {
        return this.sneaking;
    }
    
    private boolean isSameBlock(final BlockPos pos1, final BlockPos pos2) {
        return pos1.getX() == pos2.getX() && pos1.getY() == pos2.getY() && pos1.getZ() == pos2.getZ();
    }
    
    public void setSafewalkSneaking(final boolean safewalkSneaking) {
        this.safewalkSneaking = safewalkSneaking;
    }
    
    private void checkSneak() {
        if (!this.playerIsSneaking) {
            if (this.mc.thePlayer.onGround) {
                this.sneaking = this.safewalkSneaking;
                this.doSafewalk();
            }
            else {
                KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindSneak.getKeyCode(), false);
            }
        }
        else {
            KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindSneak.getKeyCode(), true);
        }
    }
    
    public void setFakedSneakingState(final boolean sneaking) {
        if (this.mc.thePlayer != null) {
            final C0BPacketEntityAction.Action action = sneaking ? C0BPacketEntityAction.Action.START_SNEAKING : C0BPacketEntityAction.Action.STOP_SNEAKING;
            this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)this.mc.thePlayer, action));
            this.mc.thePlayer.movementInput.sneak = sneaking;
        }
    }
}
