package net.minecraft.client.entity;

import net.minecraft.client.network.*;
import net.minecraft.client.*;
import net.minecraft.network.*;
import net.minecraft.command.server.*;
import net.minecraft.entity.passive.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.*;
import net.minecraft.tileentity.*;
import net.minecraft.item.*;
import YSK.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.stats.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.audio.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.init.*;
import net.minecraft.client.gui.*;

public class EntityPlayerSP extends AbstractClientPlayer
{
    public float renderArmPitch;
    private boolean serverSprintState;
    private String clientBrand;
    public float prevTimeInPortal;
    public final NetHandlerPlayClient sendQueue;
    private boolean serverSneakState;
    protected Minecraft mc;
    public float prevRenderArmYaw;
    private float horseJumpPower;
    public int sprintingTicksLeft;
    private double lastReportedPosY;
    public float prevRenderArmPitch;
    private final StatFileWriter statWriter;
    private float lastReportedPitch;
    private boolean hasValidHealth;
    private double lastReportedPosX;
    protected int sprintToggleTimer;
    private static final String[] I;
    private int horseJumpPowerCounter;
    public float timeInPortal;
    public MovementInput movementInput;
    private int positionUpdateTicks;
    private double lastReportedPosZ;
    private float lastReportedYaw;
    public float renderArmYaw;
    
    @Override
    public void sendPlayerAbilities() {
        this.sendQueue.addToSendQueue(new C13PacketPlayerAbilities(this.capabilities));
    }
    
    public void setClientBrand(final String clientBrand) {
        this.clientBrand = clientBrand;
    }
    
    public StatFileWriter getStatFileWriter() {
        return this.statWriter;
    }
    
    protected void sendHorseJump() {
        this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.RIDING_JUMP, (int)(this.getHorseJumpPower() * 100.0f)));
    }
    
    public void setXPStats(final float experience, final int experienceTotal, final int experienceLevel) {
        this.experience = experience;
        this.experienceTotal = experienceTotal;
        this.experienceLevel = experienceLevel;
    }
    
    @Override
    public void setSprinting(final boolean sprinting) {
        super.setSprinting(sprinting);
        int length;
        if (sprinting) {
            length = 150 + 134 - 128 + 444;
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else {
            length = "".length();
        }
        this.sprintingTicksLeft = length;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    @Override
    public void openEditCommandBlock(final CommandBlockLogic commandBlockLogic) {
        this.mc.displayGuiScreen(new GuiCommandBlock(commandBlockLogic));
    }
    
    public String getClientBrand() {
        return this.clientBrand;
    }
    
    private boolean isOpenBlockSpace(final BlockPos blockPos) {
        if (!this.worldObj.getBlockState(blockPos).getBlock().isNormalCube() && !this.worldObj.getBlockState(blockPos.up()).getBlock().isNormalCube()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean isRidingHorse() {
        if (this.ridingEntity != null && this.ridingEntity instanceof EntityHorse && ((EntityHorse)this.ridingEntity).isHorseSaddled()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void displayGUIChest(final IInventory inventory) {
        String guiID;
        if (inventory instanceof IInteractionObject) {
            guiID = ((IInteractionObject)inventory).getGuiID();
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else {
            guiID = EntityPlayerSP.I[" ".length()];
        }
        final String s = guiID;
        if (EntityPlayerSP.I["  ".length()].equals(s)) {
            this.mc.displayGuiScreen(new GuiChest(this.inventory, inventory));
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        else if (EntityPlayerSP.I["   ".length()].equals(s)) {
            this.mc.displayGuiScreen(new GuiHopper(this.inventory, inventory));
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else if (EntityPlayerSP.I[0x3D ^ 0x39].equals(s)) {
            this.mc.displayGuiScreen(new GuiFurnace(this.inventory, inventory));
            "".length();
            if (2 == -1) {
                throw null;
            }
        }
        else if (EntityPlayerSP.I[0x53 ^ 0x56].equals(s)) {
            this.mc.displayGuiScreen(new GuiBrewingStand(this.inventory, inventory));
            "".length();
            if (3 == 2) {
                throw null;
            }
        }
        else if (EntityPlayerSP.I[0x5A ^ 0x5C].equals(s)) {
            this.mc.displayGuiScreen(new GuiBeacon(this.inventory, inventory));
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        else if (!EntityPlayerSP.I[0xC4 ^ 0xC3].equals(s) && !EntityPlayerSP.I[0x5 ^ 0xD].equals(s)) {
            this.mc.displayGuiScreen(new GuiChest(this.inventory, inventory));
            "".length();
            if (4 <= 3) {
                throw null;
            }
        }
        else {
            this.mc.displayGuiScreen(new GuiDispenser(this.inventory, inventory));
        }
    }
    
    @Override
    public void playSound(final String s, final float n, final float n2) {
        this.worldObj.playSound(this.posX, this.posY, this.posZ, s, n, n2, "".length() != 0);
    }
    
    @Override
    public void onUpdate() {
        if (this.worldObj.isBlockLoaded(new BlockPos(this.posX, 0.0, this.posZ))) {
            super.onUpdate();
            if (this.isRiding()) {
                this.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
                this.sendQueue.addToSendQueue(new C0CPacketInput(this.moveStrafing, this.moveForward, this.movementInput.jump, this.movementInput.sneak));
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else {
                this.onUpdateWalkingPlayer();
            }
        }
    }
    
    @Override
    public boolean isUser() {
        return " ".length() != 0;
    }
    
    @Override
    protected void damageEntity(final DamageSource damageSource, final float n) {
        if (!this.isEntityInvulnerable(damageSource)) {
            this.setHealth(this.getHealth() - n);
        }
    }
    
    @Override
    protected boolean pushOutOfBlocks(final double n, final double n2, final double n3) {
        if (this.noClip) {
            return "".length() != 0;
        }
        final BlockPos blockPos = new BlockPos(n, n2, n3);
        final double n4 = n - blockPos.getX();
        final double n5 = n3 - blockPos.getZ();
        if (!this.isOpenBlockSpace(blockPos)) {
            int n6 = -" ".length();
            double n7 = 9999.0;
            if (this.isOpenBlockSpace(blockPos.west()) && n4 < n7) {
                n7 = n4;
                n6 = "".length();
            }
            if (this.isOpenBlockSpace(blockPos.east()) && 1.0 - n4 < n7) {
                n7 = 1.0 - n4;
                n6 = " ".length();
            }
            if (this.isOpenBlockSpace(blockPos.north()) && n5 < n7) {
                n7 = n5;
                n6 = (0x7 ^ 0x3);
            }
            if (this.isOpenBlockSpace(blockPos.south()) && 1.0 - n5 < n7) {
                n6 = (0x2B ^ 0x2E);
            }
            final float n8 = 0.1f;
            if (n6 == 0) {
                this.motionX = -n8;
            }
            if (n6 == " ".length()) {
                this.motionX = n8;
            }
            if (n6 == (0x23 ^ 0x27)) {
                this.motionZ = -n8;
            }
            if (n6 == (0x16 ^ 0x13)) {
                this.motionZ = n8;
            }
        }
        return "".length() != 0;
    }
    
    @Override
    public void respawnPlayer() {
        this.sendQueue.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final int n, final String s) {
        if (n <= 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    protected boolean isCurrentViewEntity() {
        if (this.mc.getRenderViewEntity() == this) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        return "".length() != 0;
    }
    
    @Override
    public void displayVillagerTradeGui(final IMerchant merchant) {
        this.mc.displayGuiScreen(new GuiMerchant(this.inventory, merchant, this.worldObj));
    }
    
    @Override
    public void onCriticalHit(final Entity entity) {
        this.mc.effectRenderer.emitParticleAtEntity(entity, EnumParticleTypes.CRIT);
    }
    
    @Override
    public void openEditSign(final TileEntitySign tileEntitySign) {
        this.mc.displayGuiScreen(new GuiEditSign(tileEntitySign));
    }
    
    @Override
    public EntityItem dropOneItem(final boolean b) {
        C07PacketPlayerDigging.Action action;
        if (b) {
            action = C07PacketPlayerDigging.Action.DROP_ALL_ITEMS;
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else {
            action = C07PacketPlayerDigging.Action.DROP_ITEM;
        }
        this.sendQueue.addToSendQueue(new C07PacketPlayerDigging(action, BlockPos.ORIGIN, EnumFacing.DOWN));
        return null;
    }
    
    public void closeScreenAndDropStack() {
        this.inventory.setItemStack(null);
        super.closeScreen();
        this.mc.displayGuiScreen(null);
    }
    
    @Override
    public boolean isSneaking() {
        int n;
        if (this.movementInput != null) {
            n = (this.movementInput.sneak ? 1 : 0);
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        if (n != 0 && !this.sleeping) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public BlockPos getPosition() {
        return new BlockPos(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5);
    }
    
    @Override
    public void heal(final float n) {
    }
    
    @Override
    public void onEnchantmentCritical(final Entity entity) {
        this.mc.effectRenderer.emitParticleAtEntity(entity, EnumParticleTypes.CRIT_MAGIC);
    }
    
    public void setPlayerSPHealth(final float n) {
        if (this.hasValidHealth) {
            final float lastDamage = this.getHealth() - n;
            if (lastDamage <= 0.0f) {
                this.setHealth(n);
                if (lastDamage < 0.0f) {
                    this.hurtResistantTime = this.maxHurtResistantTime / "  ".length();
                    "".length();
                    if (4 <= -1) {
                        throw null;
                    }
                }
            }
            else {
                this.lastDamage = lastDamage;
                this.setHealth(this.getHealth());
                this.hurtResistantTime = this.maxHurtResistantTime;
                this.damageEntity(DamageSource.generic, lastDamage);
                final int n2 = 0x1E ^ 0x14;
                this.maxHurtTime = n2;
                this.hurtTime = n2;
                "".length();
                if (2 <= -1) {
                    throw null;
                }
            }
        }
        else {
            this.setHealth(n);
            this.hasValidHealth = (" ".length() != 0);
        }
    }
    
    public void sendChatMessage(final String s) {
        if (s.startsWith(EntityPlayerSP.I["".length()])) {
            ChatCMD.commands(s);
            return;
        }
        this.sendQueue.addToSendQueue(new C01PacketChatMessage(s));
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.sprintingTicksLeft > 0) {
            this.sprintingTicksLeft -= " ".length();
            if (this.sprintingTicksLeft == 0) {
                this.setSprinting("".length() != 0);
            }
        }
        if (this.sprintToggleTimer > 0) {
            this.sprintToggleTimer -= " ".length();
        }
        this.prevTimeInPortal = this.timeInPortal;
        if (this.inPortal) {
            if (this.mc.currentScreen != null && !this.mc.currentScreen.doesGuiPauseGame()) {
                this.mc.displayGuiScreen(null);
            }
            if (this.timeInPortal == 0.0f) {
                this.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation(EntityPlayerSP.I[0x5C ^ 0x50]), this.rand.nextFloat() * 0.4f + 0.8f));
            }
            this.timeInPortal += 0.0125f;
            if (this.timeInPortal >= 1.0f) {
                this.timeInPortal = 1.0f;
            }
            this.inPortal = ("".length() != 0);
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else if (this.isPotionActive(Potion.confusion) && this.getActivePotionEffect(Potion.confusion).getDuration() > (0xA5 ^ 0x99)) {
            this.timeInPortal += 0.006666667f;
            if (this.timeInPortal > 1.0f) {
                this.timeInPortal = 1.0f;
                "".length();
                if (4 != 4) {
                    throw null;
                }
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
            this.timeUntilPortal -= " ".length();
        }
        final boolean jump = this.movementInput.jump;
        final boolean sneak = this.movementInput.sneak;
        final float n = 0.8f;
        int n2;
        if (this.movementInput.moveForward >= n) {
            n2 = " ".length();
            "".length();
            if (4 == 3) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        final int n3 = n2;
        this.movementInput.updatePlayerMoveState();
        if (this.isUsingItem() && !this.isRiding()) {
            final MovementInput movementInput = this.movementInput;
            movementInput.moveStrafe *= 0.2f;
            final MovementInput movementInput2 = this.movementInput;
            movementInput2.moveForward *= 0.2f;
            this.sprintToggleTimer = "".length();
        }
        this.pushOutOfBlocks(this.posX - this.width * 0.35, this.getEntityBoundingBox().minY + 0.5, this.posZ + this.width * 0.35);
        this.pushOutOfBlocks(this.posX - this.width * 0.35, this.getEntityBoundingBox().minY + 0.5, this.posZ - this.width * 0.35);
        this.pushOutOfBlocks(this.posX + this.width * 0.35, this.getEntityBoundingBox().minY + 0.5, this.posZ - this.width * 0.35);
        this.pushOutOfBlocks(this.posX + this.width * 0.35, this.getEntityBoundingBox().minY + 0.5, this.posZ + this.width * 0.35);
        int n4;
        if (this.getFoodStats().getFoodLevel() <= 6.0f && !this.capabilities.allowFlying) {
            n4 = "".length();
            "".length();
            if (3 < 1) {
                throw null;
            }
        }
        else {
            n4 = " ".length();
        }
        final int n5 = n4;
        if (this.onGround && !sneak && n3 == 0 && this.movementInput.moveForward >= n && !this.isSprinting() && n5 != 0 && !this.isUsingItem() && !this.isPotionActive(Potion.blindness)) {
            if (this.sprintToggleTimer <= 0 && !this.mc.gameSettings.keyBindSprint.isKeyDown()) {
                this.sprintToggleTimer = (0x81 ^ 0x86);
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
            else {
                this.setSprinting(" ".length() != 0);
            }
        }
        if (!this.isSprinting() && this.movementInput.moveForward >= n && n5 != 0 && !this.isUsingItem() && !this.isPotionActive(Potion.blindness) && this.mc.gameSettings.keyBindSprint.isKeyDown()) {
            this.setSprinting(" ".length() != 0);
        }
        if (this.isSprinting() && (this.movementInput.moveForward < n || this.isCollidedHorizontally || n5 == 0)) {
            this.setSprinting("".length() != 0);
        }
        if (this.capabilities.allowFlying) {
            if (this.mc.playerController.isSpectatorMode()) {
                if (!this.capabilities.isFlying) {
                    this.capabilities.isFlying = (" ".length() != 0);
                    this.sendPlayerAbilities();
                    "".length();
                    if (2 < 0) {
                        throw null;
                    }
                }
            }
            else if (!jump && this.movementInput.jump) {
                if (this.flyToggleTimer == 0) {
                    this.flyToggleTimer = (0x75 ^ 0x72);
                    "".length();
                    if (2 < 2) {
                        throw null;
                    }
                }
                else {
                    final PlayerCapabilities capabilities = this.capabilities;
                    int isFlying;
                    if (this.capabilities.isFlying) {
                        isFlying = "".length();
                        "".length();
                        if (1 == 3) {
                            throw null;
                        }
                    }
                    else {
                        isFlying = " ".length();
                    }
                    capabilities.isFlying = (isFlying != 0);
                    this.sendPlayerAbilities();
                    this.flyToggleTimer = "".length();
                }
            }
        }
        if (this.capabilities.isFlying && this.isCurrentViewEntity()) {
            if (this.movementInput.sneak) {
                this.motionY -= this.capabilities.getFlySpeed() * 3.0f;
            }
            if (this.movementInput.jump) {
                this.motionY += this.capabilities.getFlySpeed() * 3.0f;
            }
        }
        if (this.isRidingHorse()) {
            if (this.horseJumpPowerCounter < 0) {
                this.horseJumpPowerCounter += " ".length();
                if (this.horseJumpPowerCounter == 0) {
                    this.horseJumpPower = 0.0f;
                }
            }
            if (jump && !this.movementInput.jump) {
                this.horseJumpPowerCounter = -(0x4 ^ 0xE);
                this.sendHorseJump();
                "".length();
                if (4 == 1) {
                    throw null;
                }
            }
            else if (!jump && this.movementInput.jump) {
                this.horseJumpPowerCounter = "".length();
                this.horseJumpPower = 0.0f;
                "".length();
                if (false) {
                    throw null;
                }
            }
            else if (jump) {
                this.horseJumpPowerCounter += " ".length();
                if (this.horseJumpPowerCounter < (0xAB ^ 0xA1)) {
                    this.horseJumpPower = this.horseJumpPowerCounter * 0.1f;
                    "".length();
                    if (4 < 2) {
                        throw null;
                    }
                }
                else {
                    this.horseJumpPower = 0.8f + 2.0f / (this.horseJumpPowerCounter - (0x7E ^ 0x77)) * 0.1f;
                    "".length();
                    if (1 >= 3) {
                        throw null;
                    }
                }
            }
        }
        else {
            this.horseJumpPower = 0.0f;
        }
        super.onLivingUpdate();
        if (this.onGround && this.capabilities.isFlying && !this.mc.playerController.isSpectatorMode()) {
            this.capabilities.isFlying = ("".length() != 0);
            this.sendPlayerAbilities();
        }
    }
    
    public void onUpdateWalkingPlayer() {
        final boolean sprinting = this.isSprinting();
        if (sprinting != this.serverSprintState) {
            if (sprinting) {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SPRINTING));
                "".length();
                if (1 < 1) {
                    throw null;
                }
            }
            else {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SPRINTING));
            }
            this.serverSprintState = sprinting;
        }
        final boolean sneaking = this.isSneaking();
        if (sneaking != this.serverSneakState) {
            if (sneaking) {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SNEAKING));
                "".length();
                if (4 == -1) {
                    throw null;
                }
            }
            else {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SNEAKING));
            }
            this.serverSneakState = sneaking;
        }
        if (this.isCurrentViewEntity()) {
            final double n = this.posX - this.lastReportedPosX;
            final double n2 = this.getEntityBoundingBox().minY - this.lastReportedPosY;
            final double n3 = this.posZ - this.lastReportedPosZ;
            final double n4 = this.rotationYaw - this.lastReportedYaw;
            final double n5 = this.rotationPitch - this.lastReportedPitch;
            int n6;
            if (n * n + n2 * n2 + n3 * n3 <= 9.0E-4 && this.positionUpdateTicks < (0xD ^ 0x19)) {
                n6 = "".length();
                "".length();
                if (4 < 2) {
                    throw null;
                }
            }
            else {
                n6 = " ".length();
            }
            int length = n6;
            int n7;
            if (n4 == 0.0 && n5 == 0.0) {
                n7 = "".length();
                "".length();
                if (2 <= 0) {
                    throw null;
                }
            }
            else {
                n7 = " ".length();
            }
            final int n8 = n7;
            if (this.ridingEntity == null) {
                if (length != 0 && n8 != 0) {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.posX, this.getEntityBoundingBox().minY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround));
                    "".length();
                    if (3 <= 0) {
                        throw null;
                    }
                }
                else if (length != 0) {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.posX, this.getEntityBoundingBox().minY, this.posZ, this.onGround));
                    "".length();
                    if (4 <= 0) {
                        throw null;
                    }
                }
                else if (n8 != 0) {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
                else {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer(this.onGround));
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                }
            }
            else {
                this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.motionX, -999.0, this.motionZ, this.rotationYaw, this.rotationPitch, this.onGround));
                length = "".length();
            }
            this.positionUpdateTicks += " ".length();
            if (length != 0) {
                this.lastReportedPosX = this.posX;
                this.lastReportedPosY = this.getEntityBoundingBox().minY;
                this.lastReportedPosZ = this.posZ;
                this.positionUpdateTicks = "".length();
            }
            if (n8 != 0) {
                this.lastReportedYaw = this.rotationYaw;
                this.lastReportedPitch = this.rotationPitch;
            }
        }
    }
    
    @Override
    public void swingItem() {
        super.swingItem();
        this.sendQueue.addToSendQueue(new C0APacketAnimation());
    }
    
    @Override
    public void addChatComponentMessage(final IChatComponent chatComponent) {
        this.mc.ingameGUI.getChatGUI().printChatMessage(chatComponent);
    }
    
    public void sendHorseInventory() {
        this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.OPEN_INVENTORY));
    }
    
    public void updateEntityActionState() {
        super.updateEntityActionState();
        if (this.isCurrentViewEntity()) {
            this.moveStrafing = this.movementInput.moveStrafe;
            this.moveForward = this.movementInput.moveForward;
            this.isJumping = this.movementInput.jump;
            this.prevRenderArmYaw = this.renderArmYaw;
            this.prevRenderArmPitch = this.renderArmPitch;
            this.renderArmPitch += (float)((this.rotationPitch - this.renderArmPitch) * 0.5);
            this.renderArmYaw += (float)((this.rotationYaw - this.renderArmYaw) * 0.5);
        }
    }
    
    public void closeScreen() {
        this.sendQueue.addToSendQueue(new C0DPacketCloseWindow(this.openContainer.windowId));
        this.closeScreenAndDropStack();
    }
    
    @Override
    public void addStat(final StatBase statBase, final int n) {
        if (statBase != null && statBase.isIndependent) {
            super.addStat(statBase, n);
        }
    }
    
    @Override
    public boolean isServerWorld() {
        return " ".length() != 0;
    }
    
    @Override
    public void mountEntity(final Entity entity) {
        super.mountEntity(entity);
        if (entity instanceof EntityMinecart) {
            this.mc.getSoundHandler().playSound(new MovingSoundMinecartRiding(this, (EntityMinecart)entity));
        }
    }
    
    @Override
    protected void joinEntityItemWithWorld(final EntityItem entityItem) {
    }
    
    public EntityPlayerSP(final Minecraft mc, final World world, final NetHandlerPlayClient sendQueue, final StatFileWriter statWriter) {
        super(world, sendQueue.getGameProfile());
        this.sendQueue = sendQueue;
        this.statWriter = statWriter;
        this.mc = mc;
        this.dimension = "".length();
    }
    
    @Override
    public void displayGui(final IInteractionObject interactionObject) {
        final String guiID = interactionObject.getGuiID();
        if (EntityPlayerSP.I[0x1D ^ 0x14].equals(guiID)) {
            this.mc.displayGuiScreen(new GuiCrafting(this.inventory, this.worldObj));
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else if (EntityPlayerSP.I[0x89 ^ 0x83].equals(guiID)) {
            this.mc.displayGuiScreen(new GuiEnchantment(this.inventory, this.worldObj, interactionObject));
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else if (EntityPlayerSP.I[0x33 ^ 0x38].equals(guiID)) {
            this.mc.displayGuiScreen(new GuiRepair(this.inventory, this.worldObj));
        }
    }
    
    public float getHorseJumpPower() {
        return this.horseJumpPower;
    }
    
    @Override
    public void displayGUIHorse(final EntityHorse entityHorse, final IInventory inventory) {
        this.mc.displayGuiScreen(new GuiScreenHorseInventory(this.inventory, inventory, entityHorse));
    }
    
    @Override
    public void addChatMessage(final IChatComponent chatComponent) {
        this.mc.ingameGUI.getChatGUI().printChatMessage(chatComponent);
    }
    
    @Override
    public void displayGUIBook(final ItemStack itemStack) {
        if (itemStack.getItem() == Items.writable_book) {
            this.mc.displayGuiScreen(new GuiScreenBook(this, itemStack, (boolean)(" ".length() != 0)));
        }
    }
    
    private static void I() {
        (I = new String[0x77 ^ 0x7A])["".length()] = I("A", "ovONc");
        EntityPlayerSP.I[" ".length()] = I("\b9! \u0010\u00171)1I\u0006?!1\u0012\f>*7", "ePOEs");
        EntityPlayerSP.I["  ".length()] = I("/\u000f;\",0\u000733u!\u000e04;", "BfUGO");
        EntityPlayerSP.I["   ".length()] = I("\u00040\r6 \u001b8\u0005'y\u00016\u0013#&\u001b", "iYcSC");
        EntityPlayerSP.I[0x6 ^ 0x2] = I("\u0000%-6-\u001f-%'t\u000b91=/\u000e)", "mLCSN");
        EntityPlayerSP.I[0x7D ^ 0x78] = I("\u0007\u0010\u0019'.\u0018\u0018\u00116w\b\u000b\u00125$\u0004\u001e(19\u000b\u0017\u0013", "jywBM");
        EntityPlayerSP.I[0x1 ^ 0x7] = I(")(-=)6 %,p&$\";%*", "DACXJ");
        EntityPlayerSP.I[0x10 ^ 0x17] = I("4\f&,.+\u0004.=w=\f;9(7\u0016-;", "YeHIM");
        EntityPlayerSP.I[0x98 ^ 0x90] = I("\u0006\u000b\u001b);\u0019\u0003\u00138b\u000f\u0010\u001a<(\u000e\u0010", "kbuLX");
        EntityPlayerSP.I[0x25 ^ 0x2C] = I("$'+7$;/#&}*<$43  \"\r3(,)7", "INERG");
        EntityPlayerSP.I[0x25 ^ 0x2F] = I("\u000f\u0007\u0016\u00176\u0010\u000f\u001e\u0006o\u0007\u0000\u001b\u001a4\f\u001a\u0011\u001c2=\u001a\u0019\u00109\u0007", "bnxrU");
        EntityPlayerSP.I[0x88 ^ 0x83] = I("8!(=\u0015') ,L4&01\u001a", "UHFXv");
        EntityPlayerSP.I[0x17 ^ 0x1B] = I("(\u00078$\u000f4F>\"\u0007?\u000f/\"", "XhJPn");
    }
}
