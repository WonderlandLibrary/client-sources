package Focus.Beta.IMPL.Module.impl.misc;

import Focus.Beta.API.EventHandler;
import Focus.Beta.API.events.render.EventRender2D;
import Focus.Beta.API.events.world.EventPreUpdate;
import Focus.Beta.API.events.world.EventTick;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;
import Focus.Beta.IMPL.Module.impl.render.HUD;
import Focus.Beta.IMPL.font.FontLoaders;
import Focus.Beta.IMPL.managers.ModuleManager;
import Focus.Beta.IMPL.set.Mode;
import Focus.Beta.IMPL.set.Numbers;
import Focus.Beta.IMPL.set.Option;
import Focus.Beta.UTILS.Math.RotationUtils;
import Focus.Beta.UTILS.ScaffoldUtils;
import Focus.Beta.UTILS.render.RenderUtil2;
import Focus.Beta.UTILS.world.MovementUtil;
import Focus.Beta.UTILS.world.PacketUtil;
import Focus.Beta.UTILS.world.Timer;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class Scaffold69 extends Module {

    public Mode<Enum> modes = new Mode<>("Mode", "Mode", Modes.values(), Modes.Normal);
    public Mode<Enum> Placemodes = new Mode<>("Place", "Place", PlaceModes.values(), PlaceModes.Verus);
    public Mode<Enum> rotationsMode = new Mode<>("Rotations", "Rotations", Rotations.values(), Rotations.None);
    public Mode<Enum> swingMode = new Mode<>("Swing", "Swing", SwingMode.values(), SwingMode.Client);
    public Mode<Enum> spoofMode = new Mode<>("Spoof", "Spoof", SpoofMode.values(), SpoofMode.Client);
    public Mode<Enum> towerMode = new Mode<>("Tower", "Tower", TowerMode.values(), TowerMode.NCP);
    public Numbers<Float> timer = new Numbers<Float>("Timer", "Timer", 1.0F, 0.5F, 6.0F, 0.05F);
    public Option<Boolean> keepy = new Option<>("KeepY", "KeepY", false);
    public Option<Boolean> tower = new Option<>("Tower", "Tower", true);
    private BlockPos cPos;
    private EnumFacing cFacing;
    float[] renderRotations;
    private boolean rotated;
    private boolean shouldRotate;
    boolean watchdogJumped;
    int watchdogState;
    float[] onplacerotupdate;
    boolean changed;
    int slotWithBlock;
    int itemBeforeSwap;
    boolean firstBlockPlaced;
    Timer timeere = new Timer();
    public Scaffold69(){
        super("Scaffold", new String[0], Type.MISC, "Placing blocks under you");
        this.addValues(modes, Placemodes, rotationsMode, swingMode, spoofMode, towerMode, timer,  keepy, tower);
    }

    @Override
    public void onEnable(){
        this.changed = false;
        this.shouldRotate = false;
        this.firstBlockPlaced = false;
        super.onEnable();
    }

    @Override
    public void onDisable(){
        switch(spoofMode.getModeAsString()){
            case "Client":
                this.mc.thePlayer.inventory.currentItem = this.itemBeforeSwap;
                break;
            case "Server":
                if(this.mc.thePlayer.inventory.currentItem != this.itemBeforeSwap || mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock){
                    this.mc.thePlayer.inventory.currentItem = this.itemBeforeSwap;
                }
                PacketUtil.sendPacket(new C09PacketHeldItemChange(this.itemBeforeSwap));
                break;
        }
        this.resetSneaking();
        this.firstBlockPlaced = false;
        this.mc.timer.timerSpeed = 1.0f;
        super.onDisable();
    }

    @EventHandler
    public void onTick(EventTick e){

    }

    @EventHandler
    public void onPre(EventPreUpdate e){
            if(rotationsMode.getModeAsString().equalsIgnoreCase("Verus")){
                mc.timer.timerSpeed = 1.5f;
            }else{
                mc.timer.timerSpeed = 1.0f;
            }
        mc.thePlayer.setSprinting(false);
        switch (spoofMode.getModeAsString()){
            case "Server":
                if(mc.thePlayer.getCurrentEquippedItem() == null || !(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof  ItemBlock)){
                    for (int a = 0; a< 9; ++a){
                        if (mc.thePlayer.inventory.getStackInSlot(a) != null){
                            final boolean isSafeToSpoof = mc.thePlayer.inventory.getStackInSlot(a).getItem() instanceof ItemBlock && !this.changed;
                            if(isSafeToSpoof){
                                this.itemBeforeSwap = this.mc.thePlayer.inventory.currentItem;
                                this.slotWithBlock = a;
                                PacketUtil.sendPacketSilent(new C09PacketHeldItemChange(this.slotWithBlock));
                                this.changed = true;
                                break;
                            }
                        }
                    }
                    break;
                }
                break;
            case "Client":
                if(mc.thePlayer.getCurrentEquippedItem() == null || !(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof  ItemBlock)) {
                    for (int a = 0; a < 9; ++a) {
                        if (mc.thePlayer.inventory.getStackInSlot(a) != null) {
                            final boolean isSafeToSpoof = mc.thePlayer.inventory.getStackInSlot(a).getItem() instanceof ItemBlock && !this.changed;
                            if (isSafeToSpoof) {
                                this.itemBeforeSwap = this.mc.thePlayer.inventory.currentItem;
                                this.slotWithBlock = a;
                                mc.thePlayer.inventory.currentItem = this.slotWithBlock;
                                this.changed = true;
                                break;
                            }
                        }
                    }
                    break;
                }
                break;
        }

        boolean shouldTower = GameSettings.isKeyDown(this.mc.gameSettings.keyBindJump) && !MovementUtil.isMoving();
        if(towerMode.getModeAsString().equalsIgnoreCase("Verus")){
            shouldTower = GameSettings.isKeyDown(this.mc.gameSettings.keyBindJump);
        }
        if(shouldTower && tower.getValue()){
            switch(towerMode.getModeAsString()){
                case "NCP":
                    mc.thePlayer.motionY += 0.4;
                    break;
                case "Verus":
                    if(getOnRealGround(mc.thePlayer, 0.01) && mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically){
                        this.watchdogState = 0;
                        this.watchdogJumped = true;
                    }
                    if(this.watchdogJumped){
                        MovementUtil.setMotion(MovementUtil.getSpeed());
                        switch (this.watchdogState){
                            case 0:
                                 mc.thePlayer.isAirBorne = true;
                                 this.mc.thePlayer.triggerAchievement(StatList.jumpStat);
                                 mc.thePlayer.motionY = 0.41999998688697815;
                                 ++this.watchdogState;
                            break;
                            case 1:
                                ++this.watchdogState;
                            case 2:
                                ++this.watchdogState;
                            case 3:
                                e.setOnground(true);
                                mc.thePlayer.motionY = 0.0;
                                ++this.watchdogState;
                                break;
                            case 4:
                                ++this.watchdogState;


                        }
                        this.watchdogJumped = false;
                    }
                    this.watchdogJumped = true;
                    break;
            }
        }else{
            this.watchdogState = -1;
        }
        switch (modes.getModeAsString()){
            case "Slow":
                if(mc.thePlayer.onGround){

                    final EntityPlayerSP localPlayer7 = mc.thePlayer;
                    localPlayer7.motionZ *= 0.86;
                    final EntityPlayerSP localPlayer8 = mc.thePlayer;
                    localPlayer8.motionX *= 0.86;
                }
                break;
        }

        this.rotated = false;
        if (this.cPos != null && this.cFacing != null) {
            final float[] rots = this.getHypixelRotations(this.cPos, this.cFacing);
            float rotationYaw = this.mc.thePlayer.rotationYaw;
            if (this.mc.thePlayer.moveForward < 0.0f) {
                rotationYaw += 180.0f;
            }
            float forward = 1.0f;
            if (this.mc.thePlayer.moveForward < 0.0f) {
                forward = -0.5f;
            } else if (this.mc.thePlayer.moveForward > 0.0f) {
                forward = 0.5f;
            }
            if (this.mc.thePlayer.moveStrafing > 0.0f) {
                rotationYaw -= 90.0f * forward;
            }
            if (this.mc.thePlayer.moveStrafing < 0.0f) {
                rotationYaw += 90.0f * forward;
            }
            switch (rotationsMode.getModeAsString()){
                case "NCP":
                    if (this.shouldRotate) {

                        float[] facing = BlockUtils.getDirectionToBlock(this.cPos.getX(), this.cPos.getY(), this.cPos.getZ(), this.cFacing);
                        float yaw = facing[0];
                        float pitch = Math.min(90.0F, facing[1] + 9.0F);
                        this.rotated = true;
                        e.setYaw(yaw);
                        e.setPitch(pitch);
                        break;
                    }
                    break;
                case "Verus":
                    if (this.shouldRotate) {
                     e.setPitch(90.0f);
                     break;
                    }
                    break;
            }
        }
        this.shouldRotate = false;
        switch (Placemodes.getModeAsString()){
            case "Verus":
            case "Pre":
                place();
                break;
        }
    }
    @EventHandler
    public void a(EventRender2D e) {
        int blockCount = getBlockCount();
        RenderUtil2.drawRoundedRect(350, 280, 400, 310, 4, new Color(0,0,0, 100).getRGB());
        RenderUtil2.drawRoundedRect(350, 280, 400, 283, 4, HUD.color);
        FontLoaders.arial16.drawStringWithShadowNew("Blocks: " + blockCount, 352, 290, -1);
    }
    public static int getBlockCount() {
        int blockCount = 0;
        for (int i = 0; i < 45; ++i) {
            if (Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getStack();
                final Item item = is.getItem();
                if (is.getItem() instanceof ItemBlock && ScaffoldUtils.canIItemBePlaced(item)) {
                    blockCount += is.stackSize;
                }
            }
        }
        return blockCount;
    }

    public void place() {
        final double dir = MovementUtil.getDirection();
        final double posy = this.mc.thePlayer.posY - 1.0;
        final double expandX = mc.thePlayer.motionX;
        final double expandZ = mc.thePlayer.motionZ;
        final BlockPos pos = new BlockPos(this.mc.thePlayer.posX + expandX, posy, this.mc.thePlayer.posZ + expandZ);
        if (this.mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) {
            this.rotated = true;
            this.setPosAndFace(pos);
            if (this.cPos != null && this.cFacing != null) {
                this.shouldRotate = true;
                if (this.hasBlockOnHotbar()) {
                    this.firstBlockPlaced = true;
                    switch (swingMode.getModeAsString()) {
                        case "Client": {
                            mc.thePlayer.swingItem();
                            break;
                        }
                        case "Server": {
                            PacketUtil.sendPacketSilent(new C0APacketAnimation());
                            break;
                        }
                        case "Spam": {
                            for (int a = 0; a < 5; ++a) {
                                PacketUtil.sendPacketSilent(new C0APacketAnimation());
                            }
                            break;
                        }
                    }
                    if (spoofMode.getModeAsString().equalsIgnoreCase("Silent")) {
                        for (int i = 0; i < 9; ++i) {
                            if (mc.thePlayer.inventory.getStackInSlot(i) != null && mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemBlock) {
                                PacketUtil.sendPacket(new C09PacketHeldItemChange(this.slotWithBlock));
                                break;
                            }
                        }
                    }
                    final Vec3 vec = this.getVec(this.cPos, this.cFacing);
                    this.placeBlock(this.mc.thePlayer.inventory.getStackInSlot(this.slotWithBlock), this.cPos, this.cFacing, vec);
                    if (spoofMode.getModeAsString().equalsIgnoreCase("Silent")) {
                        PacketUtil.sendPacket(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                    }
                }
            }
        }
    }


    public void placeBlock(final ItemStack stack, final BlockPos pos, final EnumFacing facing, final Vec3 vecHit) {
        this.mc.playerController.onPlayerRightClick(this.mc.thePlayer, this.mc.theWorld, stack, pos, facing, vecHit);
    }

    public float[] getRotationsToVec(final Vec3 vec) {
        final double x = vec.xCoord;
        final double y = vec.yCoord;
        final double z = vec.zCoord;
        return RotationUtils.getRotsByPos(x, y, z);
    }

    public void resetSneaking() {
        this.mc.gameSettings.keyBindSneak.pressed = GameSettings.isKeyDown(this.mc.gameSettings.keyBindSneak);
    }

    public void setSneaking(final boolean sneaking) {
        this.mc.gameSettings.keyBindSneak.pressed = sneaking;
    }
    public Vec3 getVec(final BlockPos pos, final EnumFacing facing) {
        final ThreadLocalRandom randomThread = ThreadLocalRandom.current();
        final Vec3 vecToModify = new Vec3(pos.getX(), pos.getY(), pos.getZ());

        return vecToModify;
    }
    public float[] getHypixelRotations(final BlockPos pos, final EnumFacing facing) {
        final float yaw = this.getYaw(pos, facing);
        final float[] rots2 = this.getDirectionToBlock(pos.getX(), pos.getY(), pos.getZ(), facing);
        return new float[] { (float)(yaw + ThreadLocalRandom.current().nextDouble(-1.0, 1.0)), Math.min(90.0f, rots2[1]) };
    }

    public boolean hasBlockOnHotbar() {
        for (int a = 0; a < 9; ++a) {
            if (this.mc.thePlayer.inventory.getStackInSlot(a) != null && this.mc.thePlayer.inventory.getStackInSlot(a).getItem() instanceof ItemBlock) {
                return true;
            }
        }
        return false;
    }
    public static boolean getOnRealGround(final EntityPlayerSP entity, final double y) {
        return !mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, entity.getEntityBoundingBox().offset(0.0, -y, 0.0)).isEmpty();
    }
    public static float getPitch(final Entity var0) {
        final double var = var0.posX - mc.thePlayer.posX;
        final double var2 = var0.posZ - mc.thePlayer.posZ;
        final double var3 = var0.posY - 1.6 + var0.getEyeHeight() - mc.thePlayer.posY;
        final double var4 = MathHelper.sqrt_double(var * var + var2 * var2);
        final double var5 = -Math.toDegrees(Math.atan(var3 / var4));
        return -MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationPitch - (float)var5);
    }

    public float getYaw(final BlockPos block, final EnumFacing face) {
        final Vec3 vecToModify = new Vec3(block.getX(), block.getY(), block.getZ());
        switch (face) {
            case EAST:
            case WEST: {
                final Vec3 vec3 = vecToModify;
                vec3.zCoord += 0.5;
                break;
            }
            case SOUTH:
            case NORTH: {
                final Vec3 vec4 = vecToModify;
                vec4.xCoord += 0.5;
                break;
            }
            case UP:
            case DOWN: {
                final Vec3 vec5 = vecToModify;
                vec5.xCoord += 0.5;
                final Vec3 vec6 = vecToModify;
                vec6.zCoord += 0.5;
                break;
            }
        }
        final double x = vecToModify.xCoord - this.mc.thePlayer.posX;
        final double z = vecToModify.zCoord - this.mc.thePlayer.posZ;
        float yaw = (float)(Math.atan2(z, x) * 180.0 / 3.141592653589793) - 90.0f;
        if (yaw < 0.0f) {
            yaw -= 360.0f;
        }
        return yaw;
    }

    public float[] getDirectionToBlock(final int var0, final int var1, final int var2, final EnumFacing var3) {
        final EntityEgg var4 = new EntityEgg(this.mc.theWorld);
        var4.posX = var0 + 0.5;
        var4.posY = var1 + 0.5;
        var4.posZ = var2 + 0.5;
        final EntityEgg entityEgg = var4;
        entityEgg.posX += var3.getDirectionVec().getX() * 0.25;
        final EntityEgg entityEgg2 = var4;
        entityEgg2.posY += var3.getDirectionVec().getY() * 0.25;
        final EntityEgg entityEgg3 = var4;
        entityEgg3.posZ += var3.getDirectionVec().getZ() * 0.25;
        return getDirectionToEntity(var4);
    }

    public static float[] getDirectionToEntity(final Entity var0) {
        return new float[] { getYaw(var0) + mc.thePlayer.rotationYaw, getPitch(var0) + mc.thePlayer.rotationPitch };
    }

    public static float getYaw(final Entity var0) {
        final double var = var0.posX - mc.thePlayer.posX;
        final double var2 = var0.posZ - mc.thePlayer.posZ;
        final double degrees = Math.toDegrees(Math.atan(var2 / var));
        double var3;
        if (var2 < 0.0 && var < 0.0) {
            var3 = 90.0 + degrees;
        }
        else if (var2 < 0.0 && var > 0.0) {
            var3 = -90.0 + degrees;
        }
        else {
            var3 = Math.toDegrees(-Math.atan(var / var2));
        }
        return MathHelper.wrapAngleTo180_float(-(mc.thePlayer.rotationYaw - (float)var3));
    }

    private void setPosAndFace(final BlockPos var1) {
        if (this.mc.theWorld.getBlockState(var1.add(0, -1, 0)).getBlock() != Blocks.air) {
            if (this.keepy.getValue()) {
                final boolean towering = GameSettings.isKeyDown(this.mc.gameSettings.keyBindJump) && !MovementUtil.isMoving();
                final boolean speedToggled = ModuleManager.getModuleByName("Speed").isEnabled();
               if (towering || !speedToggled) {
                    this.cPos = var1.add(0, -1, 0);
                    this.cFacing = EnumFacing.UP;
                }
            }
            else {
                this.cPos = var1.add(0, -1, 0);
                this.cFacing = EnumFacing.UP;
            }
        }
        else if (this.mc.theWorld.getBlockState(var1.add(-1, 0, 0)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-1, 0, 0);
            this.cFacing = EnumFacing.EAST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(1, 0, 0)).getBlock() != Blocks.air) {
            this.cPos = var1.add(1, 0, 0);
            this.cFacing = EnumFacing.WEST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(0, 0, -1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(0, 0, -1);
            this.cFacing = EnumFacing.SOUTH;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(0, 0, 1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(0, 0, 1);
            this.cFacing = EnumFacing.NORTH;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(-1, 0, -1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-1, 0, -1);
            this.cFacing = EnumFacing.EAST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(-1, 0, 1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-1, 0, 1);
            this.cFacing = EnumFacing.EAST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(1, 0, -1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(1, 0, -1);
            this.cFacing = EnumFacing.WEST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(1, 0, 1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(1, 0, 1);
            this.cFacing = EnumFacing.WEST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(-2, 0, 0)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-2, 0, 0);
            this.cFacing = EnumFacing.EAST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(2, 0, 0)).getBlock() != Blocks.air) {
            this.cPos = var1.add(2, 0, 0);
            this.cFacing = EnumFacing.WEST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(0, 0, -2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(0, 0, -2);
            this.cFacing = EnumFacing.SOUTH;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(0, 0, 2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(0, 0, 2);
            this.cFacing = EnumFacing.NORTH;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(-2, 0, -2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-2, 0, -2);
            this.cFacing = EnumFacing.EAST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(-2, 0, 2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-2, 0, 2);
            this.cFacing = EnumFacing.EAST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(2, 0, -2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(2, 0, -2);
            this.cFacing = EnumFacing.WEST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(2, 0, 2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(2, 0, 2);
            this.cFacing = EnumFacing.WEST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(-1, -1, 0)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-1, -1, 0);
            this.cFacing = EnumFacing.EAST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(1, -1, 0)).getBlock() != Blocks.air) {
            this.cPos = var1.add(1, -1, 0);
            this.cFacing = EnumFacing.WEST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(0, -1, -1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(0, -1, -1);
            this.cFacing = EnumFacing.SOUTH;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(0, -1, 1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(0, -1, 1);
            this.cFacing = EnumFacing.NORTH;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(-1, -1, -1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-1, -1, -1);
            this.cFacing = EnumFacing.EAST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(-1, -1, 1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-1, -1, 1);
            this.cFacing = EnumFacing.EAST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(1, -1, -1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(1, -1, -1);
            this.cFacing = EnumFacing.WEST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(1, -1, 1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(1, -1, 1);
            this.cFacing = EnumFacing.WEST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(-2, -1, 0)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-2, -1, 0);
            this.cFacing = EnumFacing.EAST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(2, -1, 0)).getBlock() != Blocks.air) {
            this.cPos = var1.add(2, -1, 0);
            this.cFacing = EnumFacing.WEST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(0, -1, -2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(0, -1, -2);
            this.cFacing = EnumFacing.SOUTH;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(0, -1, 2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(0, -1, 2);
            this.cFacing = EnumFacing.NORTH;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(-2, -1, -2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-2, -1, -2);
            this.cFacing = EnumFacing.EAST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(-2, -1, 2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-2, -1, 2);
            this.cFacing = EnumFacing.EAST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(2, -1, -2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(2, -1, -2);
            this.cFacing = EnumFacing.WEST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(2, -1, 2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(2, -1, 2);
            this.cFacing = EnumFacing.WEST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(-3, -1, 0)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-3, -1, 0);
            this.cFacing = EnumFacing.EAST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(3, -1, 0)).getBlock() != Blocks.air) {
            this.cPos = var1.add(3, -1, 0);
            this.cFacing = EnumFacing.WEST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(0, -1, -3)).getBlock() != Blocks.air) {
            this.cPos = var1.add(0, -1, -3);
            this.cFacing = EnumFacing.SOUTH;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(0, -1, 3)).getBlock() != Blocks.air) {
            this.cPos = var1.add(0, -1, 3);
            this.cFacing = EnumFacing.NORTH;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(-3, -1, -3)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-3, -1, -3);
            this.cFacing = EnumFacing.EAST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(-3, -1, 3)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-3, -1, 3);
            this.cFacing = EnumFacing.EAST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(3, -1, -3)).getBlock() != Blocks.air) {
            this.cPos = var1.add(3, -1, -3);
            this.cFacing = EnumFacing.WEST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(3, -1, 3)).getBlock() != Blocks.air) {
            this.cPos = var1.add(3, -1, 3);
            this.cFacing = EnumFacing.WEST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(-1, -2, 0)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-1, -2, 0);
            this.cFacing = EnumFacing.EAST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(1, -2, 0)).getBlock() != Blocks.air) {
            this.cPos = var1.add(1, -2, 0);
            this.cFacing = EnumFacing.WEST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(0, -2, -1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(0, -2, -1);
            this.cFacing = EnumFacing.SOUTH;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(0, -2, 1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(0, -2, 1);
            this.cFacing = EnumFacing.NORTH;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(-1, -2, -1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-1, -2, -1);
            this.cFacing = EnumFacing.EAST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(-1, -2, 1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-1, -2, 1);
            this.cFacing = EnumFacing.EAST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(1, -2, -1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(1, -2, -1);
            this.cFacing = EnumFacing.WEST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(1, -2, 1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(1, -2, 1);
            this.cFacing = EnumFacing.WEST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(-2, -2, 0)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-2, -2, 0);
            this.cFacing = EnumFacing.EAST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(2, -2, 0)).getBlock() != Blocks.air) {
            this.cPos = var1.add(2, -2, 0);
            this.cFacing = EnumFacing.WEST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(0, -2, -2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(0, -2, -2);
            this.cFacing = EnumFacing.SOUTH;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(0, -2, 2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(0, -2, 2);
            this.cFacing = EnumFacing.NORTH;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(-2, -2, -2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-2, -2, -2);
            this.cFacing = EnumFacing.EAST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(-2, -2, 2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-2, -2, 2);
            this.cFacing = EnumFacing.EAST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(2, -2, -2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(2, -2, -2);
            this.cFacing = EnumFacing.WEST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(2, -2, 2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(2, -2, 2);
            this.cFacing = EnumFacing.WEST;
        }
        else {
            this.cPos = null;
            this.cFacing = null;
        }
    }

    enum Modes{
        Normal, Slow
    }
    enum PlaceModes{
        Verus,  Pre
    }
    enum Rotations{
         NCP, None, Verus
    }
    enum SwingMode{
        Server, Client, None
    }
    enum SpoofMode{
        Client, Server, Silent
    }
    enum TowerMode{
        NCP, Verus
    }
}
