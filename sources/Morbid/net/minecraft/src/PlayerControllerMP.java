package net.minecraft.src;

import net.minecraft.client.*;

public class PlayerControllerMP
{
    private final Minecraft mc;
    private final NetClientHandler netClientHandler;
    private int currentBlockX;
    private int currentBlockY;
    private int currentblockZ;
    private ItemStack field_85183_f;
    private float curBlockDamageMP;
    private float stepSoundTickCounter;
    private int blockHitDelay;
    private boolean isHittingBlock;
    private EnumGameType currentGameType;
    private int currentPlayerItem;
    
    public PlayerControllerMP(final Minecraft par1Minecraft, final NetClientHandler par2NetClientHandler) {
        this.currentBlockX = -1;
        this.currentBlockY = -1;
        this.currentblockZ = -1;
        this.field_85183_f = null;
        this.curBlockDamageMP = 0.0f;
        this.stepSoundTickCounter = 0.0f;
        this.blockHitDelay = 0;
        this.isHittingBlock = false;
        this.currentGameType = EnumGameType.SURVIVAL;
        this.currentPlayerItem = 0;
        this.mc = par1Minecraft;
        this.netClientHandler = par2NetClientHandler;
    }
    
    public static void clickBlockCreative(final Minecraft par0Minecraft, final PlayerControllerMP par1PlayerControllerMP, final int par2, final int par3, final int par4, final int par5) {
        if (!Minecraft.theWorld.extinguishFire(Minecraft.thePlayer, par2, par3, par4, par5)) {
            par1PlayerControllerMP.onPlayerDestroyBlock(par2, par3, par4, par5);
        }
    }
    
    public void setPlayerCapabilities(final EntityPlayer par1EntityPlayer) {
        this.currentGameType.configurePlayerCapabilities(par1EntityPlayer.capabilities);
    }
    
    public boolean enableEverythingIsScrewedUpMode() {
        return false;
    }
    
    public void setGameType(final EnumGameType par1EnumGameType) {
        (this.currentGameType = par1EnumGameType).configurePlayerCapabilities(Minecraft.thePlayer.capabilities);
    }
    
    public void flipPlayer(final EntityPlayer par1EntityPlayer) {
        par1EntityPlayer.rotationYaw = -180.0f;
    }
    
    public boolean shouldDrawHUD() {
        return this.currentGameType.isSurvivalOrAdventure();
    }
    
    public boolean onPlayerDestroyBlock(final int par1, final int par2, final int par3, final int par4) {
        if (this.currentGameType.isAdventure() && !Minecraft.thePlayer.canCurrentToolHarvestBlock(par1, par2, par3)) {
            return false;
        }
        final WorldClient var5 = Minecraft.theWorld;
        final Block var6 = Block.blocksList[var5.getBlockId(par1, par2, par3)];
        if (var6 == null) {
            return false;
        }
        var5.playAuxSFX(2001, par1, par2, par3, var6.blockID + (var5.getBlockMetadata(par1, par2, par3) << 12));
        final int var7 = var5.getBlockMetadata(par1, par2, par3);
        final boolean var8 = var5.setBlockToAir(par1, par2, par3);
        if (var8) {
            var6.onBlockDestroyedByPlayer(var5, par1, par2, par3, var7);
        }
        this.currentBlockY = -1;
        if (!this.currentGameType.isCreative()) {
            final ItemStack var9 = Minecraft.thePlayer.getCurrentEquippedItem();
            if (var9 != null) {
                var9.onBlockDestroyed(var5, var6.blockID, par1, par2, par3, Minecraft.thePlayer);
                if (var9.stackSize == 0) {
                    Minecraft.thePlayer.destroyCurrentEquippedItem();
                }
            }
        }
        return var8;
    }
    
    public void clickBlock(final int par1, final int par2, final int par3, final int par4) {
        if (!this.currentGameType.isAdventure() || Minecraft.thePlayer.canCurrentToolHarvestBlock(par1, par2, par3)) {
            if (this.currentGameType.isCreative()) {
                this.netClientHandler.addToSendQueue(new Packet14BlockDig(0, par1, par2, par3, par4));
                clickBlockCreative(this.mc, this, par1, par2, par3, par4);
                this.blockHitDelay = 5;
            }
            else if (!this.isHittingBlock || !this.sameToolAndBlock(par1, par2, par3)) {
                if (this.isHittingBlock) {
                    this.netClientHandler.addToSendQueue(new Packet14BlockDig(1, this.currentBlockX, this.currentBlockY, this.currentblockZ, par4));
                }
                this.netClientHandler.addToSendQueue(new Packet14BlockDig(0, par1, par2, par3, par4));
                final int var5 = Minecraft.theWorld.getBlockId(par1, par2, par3);
                if (var5 > 0 && this.curBlockDamageMP == 0.0f) {
                    Block.blocksList[var5].onBlockClicked(Minecraft.theWorld, par1, par2, par3, Minecraft.thePlayer);
                }
                if (var5 > 0 && Block.blocksList[var5].getPlayerRelativeBlockHardness(Minecraft.thePlayer, Minecraft.thePlayer.worldObj, par1, par2, par3) >= 1.0f) {
                    this.onPlayerDestroyBlock(par1, par2, par3, par4);
                }
                else {
                    this.isHittingBlock = true;
                    this.currentBlockX = par1;
                    this.currentBlockY = par2;
                    this.currentblockZ = par3;
                    this.field_85183_f = Minecraft.thePlayer.getHeldItem();
                    this.curBlockDamageMP = 0.0f;
                    this.stepSoundTickCounter = 0.0f;
                    Minecraft.theWorld.destroyBlockInWorldPartially(Minecraft.thePlayer.entityId, this.currentBlockX, this.currentBlockY, this.currentblockZ, (int)(this.curBlockDamageMP * 10.0f) - 1);
                }
            }
        }
    }
    
    public void resetBlockRemoving() {
        if (this.isHittingBlock) {
            this.netClientHandler.addToSendQueue(new Packet14BlockDig(1, this.currentBlockX, this.currentBlockY, this.currentblockZ, -1));
        }
        this.isHittingBlock = false;
        this.curBlockDamageMP = 0.0f;
        Minecraft.theWorld.destroyBlockInWorldPartially(Minecraft.thePlayer.entityId, this.currentBlockX, this.currentBlockY, this.currentblockZ, -1);
    }
    
    public void onPlayerDamageBlock(final int par1, final int par2, final int par3, final int par4) {
        this.syncCurrentPlayItem();
        if (this.blockHitDelay > 0) {
            --this.blockHitDelay;
        }
        else if (this.currentGameType.isCreative()) {
            this.blockHitDelay = 5;
            this.netClientHandler.addToSendQueue(new Packet14BlockDig(0, par1, par2, par3, par4));
            clickBlockCreative(this.mc, this, par1, par2, par3, par4);
        }
        else if (this.sameToolAndBlock(par1, par2, par3)) {
            final int var5 = Minecraft.theWorld.getBlockId(par1, par2, par3);
            if (var5 == 0) {
                this.isHittingBlock = false;
                return;
            }
            final Block var6 = Block.blocksList[var5];
            this.curBlockDamageMP += var6.getPlayerRelativeBlockHardness(Minecraft.thePlayer, Minecraft.thePlayer.worldObj, par1, par2, par3);
            if (this.stepSoundTickCounter % 4.0f == 0.0f && var6 != null) {
                this.mc.sndManager.playSound(var6.stepSound.getStepSound(), par1 + 0.5f, par2 + 0.5f, par3 + 0.5f, (var6.stepSound.getVolume() + 1.0f) / 8.0f, var6.stepSound.getPitch() * 0.5f);
            }
            ++this.stepSoundTickCounter;
            if (this.curBlockDamageMP >= 1.0f) {
                this.isHittingBlock = false;
                this.netClientHandler.addToSendQueue(new Packet14BlockDig(2, par1, par2, par3, par4));
                this.onPlayerDestroyBlock(par1, par2, par3, par4);
                this.curBlockDamageMP = 0.0f;
                this.stepSoundTickCounter = 0.0f;
                this.blockHitDelay = 5;
            }
            Minecraft.theWorld.destroyBlockInWorldPartially(Minecraft.thePlayer.entityId, this.currentBlockX, this.currentBlockY, this.currentblockZ, (int)(this.curBlockDamageMP * 10.0f) - 1);
        }
        else {
            this.clickBlock(par1, par2, par3, par4);
        }
    }
    
    public float getBlockReachDistance() {
        return this.currentGameType.isCreative() ? 5.0f : 4.5f;
    }
    
    public void updateController() {
        this.syncCurrentPlayItem();
        this.mc.sndManager.playRandomMusicIfReady();
    }
    
    private boolean sameToolAndBlock(final int par1, final int par2, final int par3) {
        final ItemStack var4 = Minecraft.thePlayer.getHeldItem();
        boolean var5 = this.field_85183_f == null && var4 == null;
        if (this.field_85183_f != null && var4 != null) {
            var5 = (var4.itemID == this.field_85183_f.itemID && ItemStack.areItemStackTagsEqual(var4, this.field_85183_f) && (var4.isItemStackDamageable() || var4.getItemDamage() == this.field_85183_f.getItemDamage()));
        }
        return par1 == this.currentBlockX && par2 == this.currentBlockY && par3 == this.currentblockZ && var5;
    }
    
    public void syncCurrentPlayItem() {
        final int var1 = Minecraft.thePlayer.inventory.currentItem;
        if (var1 != this.currentPlayerItem) {
            this.currentPlayerItem = var1;
            this.netClientHandler.addToSendQueue(new Packet16BlockItemSwitch(this.currentPlayerItem));
        }
    }
    
    public boolean onPlayerRightClick(final EntityPlayer par1EntityPlayer, final World par2World, final ItemStack par3ItemStack, final int par4, final int par5, final int par6, final int par7, final Vec3 par8Vec3) {
        this.syncCurrentPlayItem();
        final float var9 = (float)par8Vec3.xCoord - par4;
        final float var10 = (float)par8Vec3.yCoord - par5;
        final float var11 = (float)par8Vec3.zCoord - par6;
        boolean var12 = false;
        if (!par1EntityPlayer.isSneaking() || par1EntityPlayer.getHeldItem() == null) {
            final int var13 = par2World.getBlockId(par4, par5, par6);
            if (var13 > 0 && Block.blocksList[var13].onBlockActivated(par2World, par4, par5, par6, par1EntityPlayer, par7, var9, var10, var11)) {
                var12 = true;
            }
        }
        if (!var12 && par3ItemStack != null && par3ItemStack.getItem() instanceof ItemBlock) {
            final ItemBlock var14 = (ItemBlock)par3ItemStack.getItem();
            if (!var14.canPlaceItemBlockOnSide(par2World, par4, par5, par6, par7, par1EntityPlayer, par3ItemStack)) {
                return false;
            }
        }
        this.netClientHandler.addToSendQueue(new Packet15Place(par4, par5, par6, par7, par1EntityPlayer.inventory.getCurrentItem(), var9, var10, var11));
        if (var12) {
            return true;
        }
        if (par3ItemStack == null) {
            return false;
        }
        if (this.currentGameType.isCreative()) {
            final int var13 = par3ItemStack.getItemDamage();
            final int var15 = par3ItemStack.stackSize;
            final boolean var16 = par3ItemStack.tryPlaceItemIntoWorld(par1EntityPlayer, par2World, par4, par5, par6, par7, var9, var10, var11);
            par3ItemStack.setItemDamage(var13);
            par3ItemStack.stackSize = var15;
            return var16;
        }
        return par3ItemStack.tryPlaceItemIntoWorld(par1EntityPlayer, par2World, par4, par5, par6, par7, var9, var10, var11);
    }
    
    public boolean sendUseItem(final EntityPlayer par1EntityPlayer, final World par2World, final ItemStack par3ItemStack) {
        this.syncCurrentPlayItem();
        this.netClientHandler.addToSendQueue(new Packet15Place(-1, -1, -1, 255, par1EntityPlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
        final int var4 = par3ItemStack.stackSize;
        final ItemStack var5 = par3ItemStack.useItemRightClick(par2World, par1EntityPlayer);
        if (var5 == par3ItemStack && (var5 == null || var5.stackSize == var4)) {
            return false;
        }
        par1EntityPlayer.inventory.mainInventory[par1EntityPlayer.inventory.currentItem] = var5;
        if (var5.stackSize == 0) {
            par1EntityPlayer.inventory.mainInventory[par1EntityPlayer.inventory.currentItem] = null;
        }
        return true;
    }
    
    public EntityClientPlayerMP func_78754_a(final World par1World) {
        return new EntityClientPlayerMP(this.mc, par1World, this.mc.session, this.netClientHandler);
    }
    
    public void attackEntity(final EntityPlayer par1EntityPlayer, final Entity par2Entity) {
        this.syncCurrentPlayItem();
        this.netClientHandler.addToSendQueue(new Packet7UseEntity(par1EntityPlayer.entityId, par2Entity.entityId, 1));
        par1EntityPlayer.attackTargetEntityWithCurrentItem(par2Entity);
    }
    
    public boolean func_78768_b(final EntityPlayer par1EntityPlayer, final Entity par2Entity) {
        this.syncCurrentPlayItem();
        this.netClientHandler.addToSendQueue(new Packet7UseEntity(par1EntityPlayer.entityId, par2Entity.entityId, 0));
        return par1EntityPlayer.interactWith(par2Entity);
    }
    
    public ItemStack windowClick(final int par1, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer) {
        final short var6 = par5EntityPlayer.openContainer.getNextTransactionID(par5EntityPlayer.inventory);
        final ItemStack var7 = par5EntityPlayer.openContainer.slotClick(par2, par3, par4, par5EntityPlayer);
        this.netClientHandler.addToSendQueue(new Packet102WindowClick(par1, par2, par3, par4, var7, var6));
        return var7;
    }
    
    public void sendEnchantPacket(final int par1, final int par2) {
        this.netClientHandler.addToSendQueue(new Packet108EnchantItem(par1, par2));
    }
    
    public void sendSlotPacket(final ItemStack par1ItemStack, final int par2) {
        if (this.currentGameType.isCreative()) {
            this.netClientHandler.addToSendQueue(new Packet107CreativeSetSlot(par2, par1ItemStack));
        }
    }
    
    public void func_78752_a(final ItemStack par1ItemStack) {
        if (this.currentGameType.isCreative() && par1ItemStack != null) {
            this.netClientHandler.addToSendQueue(new Packet107CreativeSetSlot(-1, par1ItemStack));
        }
    }
    
    public void onStoppedUsingItem(final EntityPlayer par1EntityPlayer) {
        this.syncCurrentPlayItem();
        this.netClientHandler.addToSendQueue(new Packet14BlockDig(5, 0, 0, 0, 255));
        par1EntityPlayer.stopUsingItem();
    }
    
    public boolean func_78763_f() {
        return true;
    }
    
    public boolean isNotCreative() {
        return !this.currentGameType.isCreative();
    }
    
    public boolean isInCreativeMode() {
        return this.currentGameType.isCreative();
    }
    
    public boolean extendedReach() {
        return this.currentGameType.isCreative();
    }
}
