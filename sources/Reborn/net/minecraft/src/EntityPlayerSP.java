package net.minecraft.src;

import net.minecraft.client.*;
import me.enrythebest.reborn.cracked.*;
import me.enrythebest.reborn.cracked.mods.manager.*;

public class EntityPlayerSP extends EntityPlayer
{
    public MovementInput movementInput;
    protected Minecraft mc;
    protected int sprintToggleTimer;
    public int sprintingTicksLeft;
    public float renderArmYaw;
    public float renderArmPitch;
    public float prevRenderArmYaw;
    public float prevRenderArmPitch;
    private MouseFilter field_71162_ch;
    private MouseFilter field_71160_ci;
    private MouseFilter field_71161_cj;
    public float timeInPortal;
    public float prevTimeInPortal;
    
    public EntityPlayerSP(final Minecraft par1Minecraft, final World par2World, final Session par3Session, final int par4) {
        super(par2World);
        this.sprintToggleTimer = 0;
        this.sprintingTicksLeft = 0;
        this.field_71162_ch = new MouseFilter();
        this.field_71160_ci = new MouseFilter();
        this.field_71161_cj = new MouseFilter();
        this.mc = par1Minecraft;
        this.dimension = par4;
        if (par3Session != null && par3Session.username != null && par3Session.username.length() > 0) {
            this.skinUrl = "http://skins.minecraft.net/MinecraftSkins/" + StringUtils.stripControlCodes(par3Session.username) + ".png";
        }
        this.username = par3Session.username;
    }
    
    @Override
    public void moveEntity(final double par1, final double par3, final double par5) {
        super.moveEntity(par1, par3, par5);
    }
    
    public void updateEntityActionState() {
        super.updateEntityActionState();
        this.moveStrafing = this.movementInput.moveStrafe;
        this.moveForward = this.movementInput.moveForward;
        this.isJumping = this.movementInput.jump;
        this.prevRenderArmYaw = this.renderArmYaw;
        this.prevRenderArmPitch = this.renderArmPitch;
        this.renderArmPitch += (float)((this.rotationPitch - this.renderArmPitch) * 0.5);
        this.renderArmYaw += (float)((this.rotationYaw - this.renderArmYaw) * 0.5);
    }
    
    @Override
    protected boolean isClientWorld() {
        return true;
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.sprintingTicksLeft > 0) {
            --this.sprintingTicksLeft;
            if (this.sprintingTicksLeft == 0) {
                this.setSprinting(false);
            }
        }
        if (this.sprintToggleTimer > 0) {
            --this.sprintToggleTimer;
        }
        if (this.mc.playerController.enableEverythingIsScrewedUpMode()) {
            final double n = 0.5;
            this.posZ = n;
            this.posX = n;
            this.posX = 0.0;
            this.posZ = 0.0;
            this.rotationYaw = this.ticksExisted / 12.0f;
            this.rotationPitch = 10.0f;
            this.posY = 68.5;
        }
        else {
            if (!this.mc.statFileWriter.hasAchievementUnlocked(AchievementList.openInventory)) {
                this.mc.guiAchievement.queueAchievementInformation(AchievementList.openInventory);
            }
            this.prevTimeInPortal = this.timeInPortal;
            if (this.inPortal) {
                final Minecraft var10000 = this.mc;
                if (Minecraft.currentScreen != null) {
                    this.mc.displayGuiScreen(null);
                }
                if (this.timeInPortal == 0.0f) {
                    this.mc.sndManager.playSoundFX("portal.trigger", 1.0f, this.rand.nextFloat() * 0.4f + 0.8f);
                }
                this.timeInPortal += 0.0125f;
                if (this.timeInPortal >= 1.0f) {
                    this.timeInPortal = 1.0f;
                }
                this.inPortal = false;
            }
            else if (this.isPotionActive(Potion.confusion) && this.getActivePotionEffect(Potion.confusion).getDuration() > 60) {
                this.timeInPortal += 0.006666667f;
                if (this.timeInPortal > 1.0f) {
                    this.timeInPortal = 1.0f;
                }
            }
            else {
                if (this.timeInPortal > 0.0f) {
                    this.timeInPortal -= 0.05f;
                }
                if (this.timeInPortal < 0.0f) {
                    this.timeInPortal = 0.0f;
                }
            }
            if (this.timeUntilPortal > 0) {
                --this.timeUntilPortal;
            }
            final boolean var10001 = this.movementInput.jump;
            final float var10002 = 0.8f;
            final boolean var10003 = this.movementInput.moveForward >= var10002;
            this.movementInput.updatePlayerMoveState();
            Morbid.getManager();
            if (ModManager.getMod("noslowdown").isEnabled()) {
                if (this.movementInput.sneak && this.ySize < 0.2f) {
                    this.ySize = 0.2f;
                }
                this.pushOutOfBlocks(this.posX - this.width * 0.35, this.boundingBox.minY + 0.5, this.posZ + this.width * 0.35);
                this.pushOutOfBlocks(this.posX - this.width * 0.35, this.boundingBox.minY + 0.5, this.posZ - this.width * 0.35);
                this.pushOutOfBlocks(this.posX + this.width * 0.35, this.boundingBox.minY + 0.5, this.posZ - this.width * 0.35);
                this.pushOutOfBlocks(this.posX + this.width * 0.35, this.boundingBox.minY + 0.5, this.posZ + this.width * 0.35);
                final boolean var10004 = this.getFoodStats().getFoodLevel() > 6.0f || this.capabilities.allowFlying;
                if (this.onGround && !var10003 && this.movementInput.moveForward >= var10002 && !this.isSprinting() && var10004 && !this.isUsingItem() && !this.isPotionActive(Potion.blindness)) {
                    if (this.sprintToggleTimer == 0) {
                        this.sprintToggleTimer = 7;
                    }
                    else {
                        this.setSprinting(true);
                        this.sprintToggleTimer = 0;
                    }
                }
                if (this.isSneaking()) {
                    this.sprintToggleTimer = 0;
                }
                if (this.isSprinting() && (this.movementInput.moveForward < var10002 || this.isCollidedHorizontally || !var10004)) {
                    this.setSprinting(false);
                }
                if (this.capabilities.allowFlying && !var10001 && this.movementInput.jump) {
                    if (this.flyToggleTimer == 0) {
                        this.flyToggleTimer = 7;
                    }
                    else {
                        this.capabilities.isFlying = !this.capabilities.isFlying;
                        this.sendPlayerAbilities();
                        this.flyToggleTimer = 0;
                    }
                }
                if (this.capabilities.isFlying) {
                    if (this.movementInput.sneak) {
                        this.motionY -= 0.15;
                    }
                    if (this.movementInput.jump) {
                        this.motionY += 0.15;
                    }
                }
                super.onLivingUpdate();
                if (this.onGround && this.capabilities.isFlying) {
                    this.capabilities.isFlying = false;
                    this.sendPlayerAbilities();
                }
            }
            else {
                final boolean var10004 = this.movementInput.jump;
                final float var10005 = 0.8f;
                final boolean var10006 = this.movementInput.moveForward >= var10005;
                this.movementInput.updatePlayerMoveState();
                if (this.isUsingItem()) {
                    final MovementInput movementInput = this.movementInput;
                    movementInput.moveStrafe *= 0.2f;
                    final MovementInput movementInput2 = this.movementInput;
                    movementInput2.moveForward *= 0.2f;
                    this.sprintToggleTimer = 0;
                }
                if (this.movementInput.sneak && this.ySize < 0.2f) {
                    this.ySize = 0.2f;
                }
                this.pushOutOfBlocks(this.posX - this.width * 0.35, this.boundingBox.minY + 0.5, this.posZ + this.width * 0.35);
                this.pushOutOfBlocks(this.posX - this.width * 0.35, this.boundingBox.minY + 0.5, this.posZ - this.width * 0.35);
                this.pushOutOfBlocks(this.posX + this.width * 0.35, this.boundingBox.minY + 0.5, this.posZ - this.width * 0.35);
                this.pushOutOfBlocks(this.posX + this.width * 0.35, this.boundingBox.minY + 0.5, this.posZ + this.width * 0.35);
                final boolean var10007 = this.getFoodStats().getFoodLevel() > 6.0f || this.capabilities.allowFlying;
                if (this.onGround && !var10006 && this.movementInput.moveForward >= var10005 && !this.isSprinting() && var10007 && !this.isUsingItem() && !this.isPotionActive(Potion.blindness)) {
                    if (this.sprintToggleTimer == 0) {
                        this.sprintToggleTimer = 7;
                    }
                    else {
                        this.setSprinting(true);
                        this.sprintToggleTimer = 0;
                    }
                }
                if (this.isSneaking()) {
                    this.sprintToggleTimer = 0;
                }
                if (this.isSprinting() && (this.movementInput.moveForward < var10005 || this.isCollidedHorizontally || !var10007)) {
                    this.setSprinting(false);
                }
                if (this.capabilities.allowFlying && !var10004 && this.movementInput.jump) {
                    if (this.flyToggleTimer == 0) {
                        this.flyToggleTimer = 7;
                    }
                    else {
                        this.capabilities.isFlying = !this.capabilities.isFlying;
                        this.sendPlayerAbilities();
                        this.flyToggleTimer = 0;
                    }
                }
                if (this.capabilities.isFlying) {
                    if (this.movementInput.sneak) {
                        this.motionY -= 0.15;
                    }
                    if (this.movementInput.jump) {
                        this.motionY += 0.15;
                    }
                }
                super.onLivingUpdate();
                if (this.onGround && this.capabilities.isFlying) {
                    this.capabilities.isFlying = false;
                    this.sendPlayerAbilities();
                }
            }
        }
        Morbid.getHookManager().onPostUpdate();
    }
    
    public float getFOVMultiplier() {
        float var1 = 1.0f;
        if (this.capabilities.isFlying) {
            var1 *= 1.1f;
        }
        var1 *= (this.landMovementFactor * this.getSpeedModifier() / this.speedOnGround + 1.0f) / 2.0f;
        if (this.isUsingItem() && this.getItemInUse().itemID == Item.bow.itemID) {
            final int var2 = this.getItemInUseDuration();
            float var3 = var2 / 20.0f;
            if (var3 > 1.0f) {
                var3 = 1.0f;
            }
            else {
                var3 *= var3;
            }
            var1 *= 1.0f - var3 * 0.15f;
        }
        return var1;
    }
    
    @Override
    public void updateCloak() {
        this.cloakUrl = "http://skins.minecraft.net/MinecraftCloaks/" + StringUtils.stripControlCodes(this.username) + ".png";
    }
    
    public void closeScreen() {
        super.closeScreen();
        this.mc.displayGuiScreen(null);
    }
    
    @Override
    public void displayGUIEditSign(final TileEntity par1TileEntity) {
        if (par1TileEntity instanceof TileEntitySign) {
            this.mc.displayGuiScreen(new GuiEditSign((TileEntitySign)par1TileEntity));
        }
        else if (par1TileEntity instanceof TileEntityCommandBlock) {
            this.mc.displayGuiScreen(new GuiCommandBlock((TileEntityCommandBlock)par1TileEntity));
        }
    }
    
    @Override
    public void displayGUIBook(final ItemStack par1ItemStack) {
        final Item var2 = par1ItemStack.getItem();
        if (var2 == Item.writtenBook) {
            this.mc.displayGuiScreen(new GuiScreenBook(this, par1ItemStack, false));
        }
        else if (var2 == Item.writableBook) {
            this.mc.displayGuiScreen(new GuiScreenBook(this, par1ItemStack, true));
        }
    }
    
    @Override
    public void displayGUIChest(final IInventory par1IInventory) {
        this.mc.displayGuiScreen(new GuiChest(this.inventory, par1IInventory));
    }
    
    @Override
    public void displayGUIHopper(final TileEntityHopper par1TileEntityHopper) {
        this.mc.displayGuiScreen(new GuiHopper(this.inventory, par1TileEntityHopper));
    }
    
    @Override
    public void displayGUIHopperMinecart(final EntityMinecartHopper par1EntityMinecartHopper) {
        this.mc.displayGuiScreen(new GuiHopper(this.inventory, par1EntityMinecartHopper));
    }
    
    @Override
    public void displayGUIWorkbench(final int par1, final int par2, final int par3) {
        this.mc.displayGuiScreen(new GuiCrafting(this.inventory, this.worldObj, par1, par2, par3));
    }
    
    @Override
    public void displayGUIEnchantment(final int par1, final int par2, final int par3, final String par4Str) {
        this.mc.displayGuiScreen(new GuiEnchantment(this.inventory, this.worldObj, par1, par2, par3, par4Str));
    }
    
    @Override
    public void displayGUIAnvil(final int par1, final int par2, final int par3) {
        this.mc.displayGuiScreen(new GuiRepair(this.inventory, this.worldObj, par1, par2, par3));
    }
    
    @Override
    public void displayGUIFurnace(final TileEntityFurnace par1TileEntityFurnace) {
        this.mc.displayGuiScreen(new GuiFurnace(this.inventory, par1TileEntityFurnace));
    }
    
    @Override
    public void displayGUIBrewingStand(final TileEntityBrewingStand par1TileEntityBrewingStand) {
        this.mc.displayGuiScreen(new GuiBrewingStand(this.inventory, par1TileEntityBrewingStand));
    }
    
    @Override
    public void displayGUIBeacon(final TileEntityBeacon par1TileEntityBeacon) {
        this.mc.displayGuiScreen(new GuiBeacon(this.inventory, par1TileEntityBeacon));
    }
    
    @Override
    public void displayGUIDispenser(final TileEntityDispenser par1TileEntityDispenser) {
        this.mc.displayGuiScreen(new GuiDispenser(this.inventory, par1TileEntityDispenser));
    }
    
    @Override
    public void displayGUIMerchant(final IMerchant par1IMerchant, final String par2Str) {
        this.mc.displayGuiScreen(new GuiMerchant(this.inventory, par1IMerchant, this.worldObj, par2Str));
    }
    
    @Override
    public void onCriticalHit(final Entity par1Entity) {
        final EffectRenderer var10000 = this.mc.effectRenderer;
        final EntityCrit2FX var10001 = new EntityCrit2FX(Minecraft.theWorld, par1Entity);
        final Minecraft var10002 = this.mc;
        var10000.addEffect(var10001);
    }
    
    @Override
    public void onEnchantmentCritical(final Entity par1Entity) {
        final EntityCrit2FX var10000 = new EntityCrit2FX(Minecraft.theWorld, par1Entity, "magicCrit");
        final Minecraft var10001 = this.mc;
        final EntityCrit2FX var10002 = var10000;
        this.mc.effectRenderer.addEffect(var10002);
    }
    
    @Override
    public void onItemPickup(final Entity par1Entity, final int par2) {
        this.mc.effectRenderer.addEffect(new EntityPickupFX(Minecraft.theWorld, par1Entity, this, -0.5f));
    }
    
    @Override
    public boolean isSneaking() {
        return this.movementInput.sneak && !this.sleeping;
    }
    
    public void setHealth(final int par1) {
        final int var2 = this.getHealth() - par1;
        if (var2 <= 0) {
            this.setEntityHealth(par1);
            if (var2 < 0) {
                this.hurtResistantTime = this.maxHurtResistantTime / 2;
            }
        }
        else {
            this.lastDamage = var2;
            this.setEntityHealth(this.getHealth());
            this.hurtResistantTime = this.maxHurtResistantTime;
            this.damageEntity(DamageSource.generic, var2);
            final int n = 10;
            this.maxHurtTime = n;
            this.hurtTime = n;
        }
    }
    
    @Override
    public void addChatMessage(final String par1Str) {
        this.mc.ingameGUI.getChatGUI().addTranslatedMessage(par1Str, new Object[0]);
    }
    
    @Override
    public void addStat(final StatBase par1StatBase, final int par2) {
        if (par1StatBase != null) {
            if (par1StatBase.isAchievement()) {
                final Achievement var3 = (Achievement)par1StatBase;
                if (var3.parentAchievement == null || this.mc.statFileWriter.hasAchievementUnlocked(var3.parentAchievement)) {
                    if (!this.mc.statFileWriter.hasAchievementUnlocked(var3)) {
                        this.mc.guiAchievement.queueTakenAchievement(var3);
                    }
                    this.mc.statFileWriter.readStat(par1StatBase, par2);
                }
            }
            else {
                this.mc.statFileWriter.readStat(par1StatBase, par2);
            }
        }
    }
    
    private boolean isBlockTranslucent(final int par1, final int par2, final int par3) {
        return this.worldObj.isBlockNormalCube(par1, par2, par3);
    }
    
    @Override
    protected boolean pushOutOfBlocks(final double par1, final double par3, final double par5) {
        final int var7 = MathHelper.floor_double(par1);
        final int var8 = MathHelper.floor_double(par3);
        final int var9 = MathHelper.floor_double(par5);
        final double var10 = par1 - var7;
        final double var11 = par5 - var9;
        if (this.isBlockTranslucent(var7, var8, var9) || this.isBlockTranslucent(var7, var8 + 1, var9)) {
            final boolean var12 = !this.isBlockTranslucent(var7 - 1, var8, var9) && !this.isBlockTranslucent(var7 - 1, var8 + 1, var9);
            final boolean var13 = !this.isBlockTranslucent(var7 + 1, var8, var9) && !this.isBlockTranslucent(var7 + 1, var8 + 1, var9);
            final boolean var14 = !this.isBlockTranslucent(var7, var8, var9 - 1) && !this.isBlockTranslucent(var7, var8 + 1, var9 - 1);
            final boolean var15 = !this.isBlockTranslucent(var7, var8, var9 + 1) && !this.isBlockTranslucent(var7, var8 + 1, var9 + 1);
            byte var16 = -1;
            double var17 = 9999.0;
            if (var12 && var10 < var17) {
                var17 = var10;
                var16 = 0;
            }
            if (var13 && 1.0 - var10 < var17) {
                var17 = 1.0 - var10;
                var16 = 1;
            }
            if (var14 && var11 < var17) {
                var17 = var11;
                var16 = 4;
            }
            if (var15 && 1.0 - var11 < var17) {
                var17 = 1.0 - var11;
                var16 = 5;
            }
            final float var18 = 0.1f;
            if (var16 == 0) {
                this.motionX = -var18;
            }
            if (var16 == 1) {
                this.motionX = var18;
            }
            if (var16 == 4) {
                this.motionZ = -var18;
            }
            if (var16 == 5) {
                this.motionZ = var18;
            }
        }
        return false;
    }
    
    @Override
    public void setSprinting(final boolean par1) {
        super.setSprinting(par1);
        this.sprintingTicksLeft = (par1 ? 600 : 0);
    }
    
    public void setXPStats(final float par1, final int par2, final int par3) {
        this.experience = par1;
        this.experienceTotal = par2;
        this.experienceLevel = par3;
    }
    
    @Override
    public void sendChatToPlayer(final String par1Str) {
        this.mc.ingameGUI.getChatGUI().printChatMessage(par1Str);
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final int par1, final String par2Str) {
        return par1 <= 0;
    }
    
    @Override
    public ChunkCoordinates getPlayerCoordinates() {
        return new ChunkCoordinates(MathHelper.floor_double(this.posX + 0.5), MathHelper.floor_double(this.posY + 0.5), MathHelper.floor_double(this.posZ + 0.5));
    }
    
    @Override
    public ItemStack getHeldItem() {
        return this.inventory.getCurrentItem();
    }
    
    @Override
    public void playSound(final String par1Str, final float par2, final float par3) {
        this.worldObj.playSound(this.posX, this.posY - this.yOffset, this.posZ, par1Str, par2, par3, false);
    }
}
