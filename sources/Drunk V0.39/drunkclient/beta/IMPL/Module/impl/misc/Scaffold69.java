/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module.impl.misc;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.render.EventRender2D;
import drunkclient.beta.API.events.world.EventPreUpdate;
import drunkclient.beta.API.events.world.EventTick;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import drunkclient.beta.IMPL.font.FontLoaders;
import drunkclient.beta.IMPL.managers.ModuleManager;
import drunkclient.beta.IMPL.set.Mode;
import drunkclient.beta.IMPL.set.Numbers;
import drunkclient.beta.IMPL.set.Option;
import drunkclient.beta.UTILS.Math.RotationUtils;
import drunkclient.beta.UTILS.ScaffoldUtils;
import drunkclient.beta.UTILS.world.MovementUtil;
import drunkclient.beta.UTILS.world.PacketUtil;
import drunkclient.beta.UTILS.world.Timer;
import java.util.concurrent.ThreadLocalRandom;
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
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class Scaffold69
extends Module {
    public Mode<Enum> modes = new Mode("Mode", "Mode", (Enum[])Modes.values(), (Enum)Modes.Normal);
    public Mode<Enum> Placemodes = new Mode("Place", "Place", (Enum[])PlaceModes.values(), (Enum)PlaceModes.Verus);
    public Mode<Enum> rotationsMode = new Mode("Rotations", "Rotations", (Enum[])Rotations.values(), (Enum)Rotations.None);
    public Mode<Enum> swingMode = new Mode("Swing", "Swing", (Enum[])SwingMode.values(), (Enum)SwingMode.Client);
    public Mode<Enum> spoofMode = new Mode("Spoof", "Spoof", (Enum[])SpoofMode.values(), (Enum)SpoofMode.Client);
    public Mode<Enum> towerMode = new Mode("Tower", "Tower", (Enum[])TowerMode.values(), (Enum)TowerMode.NCP);
    public Numbers<Float> timer = new Numbers<Float>("Timer", "Timer", Float.valueOf(1.0f), Float.valueOf(0.5f), Float.valueOf(6.0f), Float.valueOf(0.05f));
    public Option<Boolean> keepy = new Option<Boolean>("KeepY", "KeepY", false);
    public Option<Boolean> tower = new Option<Boolean>("Tower", "Tower", true);
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

    public Scaffold69() {
        super("Scaffold", new String[0], Type.MISC, "Placing blocks under you");
        this.addValues(this.modes, this.Placemodes, this.rotationsMode, this.swingMode, this.spoofMode, this.towerMode, this.keepy, this.tower);
    }

    @Override
    public void onEnable() {
        this.changed = false;
        this.shouldRotate = false;
        this.firstBlockPlaced = false;
        super.onEnable();
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void onDisable() {
        var1_1 = this.spoofMode.getModeAsString();
        var2_2 = -1;
        switch (var1_1.hashCode()) {
            case 2021122027: {
                if (!var1_1.equals("Client")) break;
                var2_2 = 0;
                break;
            }
            case -1821959325: {
                if (!var1_1.equals("Server")) break;
                var2_2 = 1;
                break;
            }
        }
        switch (var2_2) {
            case 0: {
                Minecraft.thePlayer.inventory.currentItem = this.itemBeforeSwap;
                break;
            }
            case 1: {
                if (Minecraft.thePlayer.inventory.currentItem != this.itemBeforeSwap) ** GOTO lbl28
                if (Minecraft.thePlayer.getCurrentEquippedItem() == null) ** GOTO lbl29
                if (!(Minecraft.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock)) ** GOTO lbl29
lbl28:
                // 2 sources

                Minecraft.thePlayer.inventory.currentItem = this.itemBeforeSwap;
lbl29:
                // 3 sources

                PacketUtil.sendPacket(new C09PacketHeldItemChange(this.itemBeforeSwap));
                break;
            }
        }
        this.resetSneaking();
        this.firstBlockPlaced = false;
        Scaffold69.mc.timer.timerSpeed = 1.0f;
        super.onDisable();
    }

    @EventHandler
    public void onTick(EventTick e) {
    }

    /*
     * Exception decompiling
     */
    @EventHandler
    public void onPre(EventPreUpdate e) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter$FailedRewriteException: Block member is not a case, it's a class org.benf.cfr.reader.bytecode.analysis.structured.statement.StructuredComment
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.rewriteSwitch(SwitchStringRewriter.java:236)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.rewriteComplex(SwitchStringRewriter.java:207)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.rewrite(SwitchStringRewriter.java:73)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:881)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         *     at the.bytecode.club.bytecodeviewer.decompilers.impl.CFRDecompiler.decompileToZip(CFRDecompiler.java:306)
         *     at the.bytecode.club.bytecodeviewer.resources.ResourceDecompiling.lambda$null$5(ResourceDecompiling.java:159)
         *     at java.lang.Thread.run(Unknown Source)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    @EventHandler
    public void a(EventRender2D e) {
        int blockCount = Scaffold69.getBlockCount();
        FontLoaders.arial16.drawStringWithShadowNew("Blocks: " + blockCount, 352.0, 290.0, -1);
    }

    public static int getBlockCount() {
        int blockCount = 0;
        int i = 0;
        while (i < 45) {
            Minecraft.getMinecraft();
            if (Minecraft.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                Minecraft.getMinecraft();
                ItemStack is = Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack();
                Item item = is.getItem();
                if (is.getItem() instanceof ItemBlock && ScaffoldUtils.canIItemBePlaced(item)) {
                    blockCount += is.stackSize;
                }
            }
            ++i;
        }
        return blockCount;
    }

    public void place() {
        double dir = MovementUtil.getDirection();
        double posy = Minecraft.thePlayer.posY - 1.0;
        double expandX = Minecraft.thePlayer.motionX;
        double expandZ = Minecraft.thePlayer.motionZ;
        BlockPos pos = new BlockPos(Minecraft.thePlayer.posX + expandX, posy, Minecraft.thePlayer.posZ + expandZ);
        if (!(Scaffold69.mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir)) return;
        this.rotated = true;
        this.setPosAndFace(pos);
        if (this.cPos == null) return;
        if (this.cFacing == null) return;
        this.shouldRotate = true;
        if (!this.hasBlockOnHotbar()) return;
        this.firstBlockPlaced = true;
        switch (this.swingMode.getModeAsString()) {
            case "Client": {
                Minecraft.thePlayer.swingItem();
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
        if (this.spoofMode.getModeAsString().equalsIgnoreCase("Silent")) {
            for (int i = 0; i < 9; ++i) {
                if (Minecraft.thePlayer.inventory.getStackInSlot(i) == null) continue;
                if (!(Minecraft.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemBlock)) continue;
                PacketUtil.sendPacket(new C09PacketHeldItemChange(this.slotWithBlock));
                break;
            }
        }
        Vec3 vec = this.getVec(this.cPos, this.cFacing);
        this.placeBlock(Minecraft.thePlayer.inventory.getStackInSlot(this.slotWithBlock), this.cPos, this.cFacing, vec);
        if (!this.spoofMode.getModeAsString().equalsIgnoreCase("Silent")) return;
        PacketUtil.sendPacket(new C09PacketHeldItemChange(Minecraft.thePlayer.inventory.currentItem));
    }

    public void placeBlock(ItemStack stack, BlockPos pos, EnumFacing facing, Vec3 vecHit) {
        Scaffold69.mc.playerController.onPlayerRightClick(Minecraft.thePlayer, Scaffold69.mc.theWorld, stack, pos, facing, vecHit);
    }

    public float[] getRotationsToVec(Vec3 vec) {
        double x = vec.xCoord;
        double y = vec.yCoord;
        double z = vec.zCoord;
        return RotationUtils.getRotsByPos(x, y, z);
    }

    public void resetSneaking() {
        Scaffold69.mc.gameSettings.keyBindSneak.pressed = GameSettings.isKeyDown(Scaffold69.mc.gameSettings.keyBindSneak);
    }

    public void setSneaking(boolean sneaking) {
        Scaffold69.mc.gameSettings.keyBindSneak.pressed = sneaking;
    }

    public Vec3 getVec(BlockPos pos, EnumFacing facing) {
        ThreadLocalRandom randomThread = ThreadLocalRandom.current();
        return new Vec3(pos.getX(), pos.getY(), pos.getZ());
    }

    public float[] getHypixelRotations(BlockPos pos, EnumFacing facing) {
        float yaw = this.getYaw(pos, facing);
        float[] rots2 = this.getDirectionToBlock(pos.getX(), pos.getY(), pos.getZ(), facing);
        return new float[]{(float)((double)yaw + ThreadLocalRandom.current().nextDouble(-1.0, 1.0)), Math.min(90.0f, rots2[1])};
    }

    public boolean hasBlockOnHotbar() {
        int a = 0;
        while (a < 9) {
            if (Minecraft.thePlayer.inventory.getStackInSlot(a) != null) {
                if (Minecraft.thePlayer.inventory.getStackInSlot(a).getItem() instanceof ItemBlock) {
                    return true;
                }
            }
            ++a;
        }
        return false;
    }

    public static boolean getOnRealGround(EntityPlayerSP entity, double y) {
        if (Scaffold69.mc.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, entity.getEntityBoundingBox().offset(0.0, -y, 0.0)).isEmpty()) return false;
        return true;
    }

    public static float getPitch(Entity var0) {
        double var = var0.posX - Minecraft.thePlayer.posX;
        double var2 = var0.posZ - Minecraft.thePlayer.posZ;
        double var3 = var0.posY - 1.6 + (double)var0.getEyeHeight() - Minecraft.thePlayer.posY;
        double var4 = MathHelper.sqrt_double(var * var + var2 * var2);
        double var5 = -Math.toDegrees(Math.atan(var3 / var4));
        return -MathHelper.wrapAngleTo180_float(Minecraft.thePlayer.rotationPitch - (float)var5);
    }

    public float getYaw(BlockPos block, EnumFacing face) {
        Vec3 vecToModify = new Vec3(block.getX(), block.getY(), block.getZ());
        switch (1.$SwitchMap$net$minecraft$util$EnumFacing[face.ordinal()]) {
            case 1: 
            case 2: {
                Vec3 vec3 = vecToModify;
                vec3.zCoord += 0.5;
                break;
            }
            case 3: 
            case 4: {
                Vec3 vec4 = vecToModify;
                vec4.xCoord += 0.5;
                break;
            }
            case 5: 
            case 6: {
                Vec3 vec5 = vecToModify;
                vec5.xCoord += 0.5;
                Vec3 vec6 = vecToModify;
                vec6.zCoord += 0.5;
                break;
            }
        }
        double x = vecToModify.xCoord - Minecraft.thePlayer.posX;
        double z = vecToModify.zCoord - Minecraft.thePlayer.posZ;
        float yaw = (float)(Math.atan2(z, x) * 180.0 / Math.PI) - 90.0f;
        if (!(yaw < 0.0f)) return yaw;
        yaw -= 360.0f;
        return yaw;
    }

    public float[] getDirectionToBlock(int var0, int var1, int var2, EnumFacing var3) {
        EntityEgg var4 = new EntityEgg(Scaffold69.mc.theWorld);
        var4.posX = (double)var0 + 0.5;
        var4.posY = (double)var1 + 0.5;
        var4.posZ = (double)var2 + 0.5;
        EntityEgg entityEgg = var4;
        entityEgg.posX += (double)var3.getDirectionVec().getX() * 0.25;
        EntityEgg entityEgg2 = var4;
        entityEgg2.posY += (double)var3.getDirectionVec().getY() * 0.25;
        EntityEgg entityEgg3 = var4;
        entityEgg3.posZ += (double)var3.getDirectionVec().getZ() * 0.25;
        return Scaffold69.getDirectionToEntity(var4);
    }

    public static float[] getDirectionToEntity(Entity var0) {
        float[] fArray = new float[2];
        fArray[0] = Scaffold69.getYaw(var0) + Minecraft.thePlayer.rotationYaw;
        fArray[1] = Scaffold69.getPitch(var0) + Minecraft.thePlayer.rotationPitch;
        return fArray;
    }

    public static float getYaw(Entity var0) {
        double var3;
        double var = var0.posX - Minecraft.thePlayer.posX;
        double var2 = var0.posZ - Minecraft.thePlayer.posZ;
        double degrees = Math.toDegrees(Math.atan(var2 / var));
        if (var2 < 0.0 && var < 0.0) {
            var3 = 90.0 + degrees;
            return MathHelper.wrapAngleTo180_float(-(Minecraft.thePlayer.rotationYaw - (float)var3));
        }
        if (var2 < 0.0 && var > 0.0) {
            var3 = -90.0 + degrees;
            return MathHelper.wrapAngleTo180_float(-(Minecraft.thePlayer.rotationYaw - (float)var3));
        }
        var3 = Math.toDegrees(-Math.atan(var / var2));
        return MathHelper.wrapAngleTo180_float(-(Minecraft.thePlayer.rotationYaw - (float)var3));
    }

    private void setPosAndFace(BlockPos var1) {
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(0, -1, 0)).getBlock() != Blocks.air) {
            if (!((Boolean)this.keepy.getValue()).booleanValue()) {
                this.cPos = var1.add(0, -1, 0);
                this.cFacing = EnumFacing.UP;
                return;
            }
            boolean towering = GameSettings.isKeyDown(Scaffold69.mc.gameSettings.keyBindJump) && !MovementUtil.isMoving();
            boolean speedToggled = ModuleManager.getModuleByName("Speed").isEnabled();
            if (!towering) {
                if (speedToggled) return;
            }
            this.cPos = var1.add(0, -1, 0);
            this.cFacing = EnumFacing.UP;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(-1, 0, 0)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-1, 0, 0);
            this.cFacing = EnumFacing.EAST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(1, 0, 0)).getBlock() != Blocks.air) {
            this.cPos = var1.add(1, 0, 0);
            this.cFacing = EnumFacing.WEST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(0, 0, -1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(0, 0, -1);
            this.cFacing = EnumFacing.SOUTH;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(0, 0, 1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(0, 0, 1);
            this.cFacing = EnumFacing.NORTH;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(-1, 0, -1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-1, 0, -1);
            this.cFacing = EnumFacing.EAST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(-1, 0, 1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-1, 0, 1);
            this.cFacing = EnumFacing.EAST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(1, 0, -1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(1, 0, -1);
            this.cFacing = EnumFacing.WEST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(1, 0, 1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(1, 0, 1);
            this.cFacing = EnumFacing.WEST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(-2, 0, 0)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-2, 0, 0);
            this.cFacing = EnumFacing.EAST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(2, 0, 0)).getBlock() != Blocks.air) {
            this.cPos = var1.add(2, 0, 0);
            this.cFacing = EnumFacing.WEST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(0, 0, -2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(0, 0, -2);
            this.cFacing = EnumFacing.SOUTH;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(0, 0, 2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(0, 0, 2);
            this.cFacing = EnumFacing.NORTH;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(-2, 0, -2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-2, 0, -2);
            this.cFacing = EnumFacing.EAST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(-2, 0, 2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-2, 0, 2);
            this.cFacing = EnumFacing.EAST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(2, 0, -2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(2, 0, -2);
            this.cFacing = EnumFacing.WEST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(2, 0, 2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(2, 0, 2);
            this.cFacing = EnumFacing.WEST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(-1, -1, 0)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-1, -1, 0);
            this.cFacing = EnumFacing.EAST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(1, -1, 0)).getBlock() != Blocks.air) {
            this.cPos = var1.add(1, -1, 0);
            this.cFacing = EnumFacing.WEST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(0, -1, -1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(0, -1, -1);
            this.cFacing = EnumFacing.SOUTH;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(0, -1, 1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(0, -1, 1);
            this.cFacing = EnumFacing.NORTH;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(-1, -1, -1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-1, -1, -1);
            this.cFacing = EnumFacing.EAST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(-1, -1, 1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-1, -1, 1);
            this.cFacing = EnumFacing.EAST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(1, -1, -1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(1, -1, -1);
            this.cFacing = EnumFacing.WEST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(1, -1, 1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(1, -1, 1);
            this.cFacing = EnumFacing.WEST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(-2, -1, 0)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-2, -1, 0);
            this.cFacing = EnumFacing.EAST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(2, -1, 0)).getBlock() != Blocks.air) {
            this.cPos = var1.add(2, -1, 0);
            this.cFacing = EnumFacing.WEST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(0, -1, -2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(0, -1, -2);
            this.cFacing = EnumFacing.SOUTH;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(0, -1, 2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(0, -1, 2);
            this.cFacing = EnumFacing.NORTH;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(-2, -1, -2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-2, -1, -2);
            this.cFacing = EnumFacing.EAST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(-2, -1, 2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-2, -1, 2);
            this.cFacing = EnumFacing.EAST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(2, -1, -2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(2, -1, -2);
            this.cFacing = EnumFacing.WEST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(2, -1, 2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(2, -1, 2);
            this.cFacing = EnumFacing.WEST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(-3, -1, 0)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-3, -1, 0);
            this.cFacing = EnumFacing.EAST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(3, -1, 0)).getBlock() != Blocks.air) {
            this.cPos = var1.add(3, -1, 0);
            this.cFacing = EnumFacing.WEST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(0, -1, -3)).getBlock() != Blocks.air) {
            this.cPos = var1.add(0, -1, -3);
            this.cFacing = EnumFacing.SOUTH;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(0, -1, 3)).getBlock() != Blocks.air) {
            this.cPos = var1.add(0, -1, 3);
            this.cFacing = EnumFacing.NORTH;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(-3, -1, -3)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-3, -1, -3);
            this.cFacing = EnumFacing.EAST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(-3, -1, 3)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-3, -1, 3);
            this.cFacing = EnumFacing.EAST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(3, -1, -3)).getBlock() != Blocks.air) {
            this.cPos = var1.add(3, -1, -3);
            this.cFacing = EnumFacing.WEST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(3, -1, 3)).getBlock() != Blocks.air) {
            this.cPos = var1.add(3, -1, 3);
            this.cFacing = EnumFacing.WEST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(-1, -2, 0)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-1, -2, 0);
            this.cFacing = EnumFacing.EAST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(1, -2, 0)).getBlock() != Blocks.air) {
            this.cPos = var1.add(1, -2, 0);
            this.cFacing = EnumFacing.WEST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(0, -2, -1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(0, -2, -1);
            this.cFacing = EnumFacing.SOUTH;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(0, -2, 1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(0, -2, 1);
            this.cFacing = EnumFacing.NORTH;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(-1, -2, -1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-1, -2, -1);
            this.cFacing = EnumFacing.EAST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(-1, -2, 1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-1, -2, 1);
            this.cFacing = EnumFacing.EAST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(1, -2, -1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(1, -2, -1);
            this.cFacing = EnumFacing.WEST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(1, -2, 1)).getBlock() != Blocks.air) {
            this.cPos = var1.add(1, -2, 1);
            this.cFacing = EnumFacing.WEST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(-2, -2, 0)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-2, -2, 0);
            this.cFacing = EnumFacing.EAST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(2, -2, 0)).getBlock() != Blocks.air) {
            this.cPos = var1.add(2, -2, 0);
            this.cFacing = EnumFacing.WEST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(0, -2, -2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(0, -2, -2);
            this.cFacing = EnumFacing.SOUTH;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(0, -2, 2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(0, -2, 2);
            this.cFacing = EnumFacing.NORTH;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(-2, -2, -2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-2, -2, -2);
            this.cFacing = EnumFacing.EAST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(-2, -2, 2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(-2, -2, 2);
            this.cFacing = EnumFacing.EAST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(2, -2, -2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(2, -2, -2);
            this.cFacing = EnumFacing.WEST;
            return;
        }
        if (Scaffold69.mc.theWorld.getBlockState(var1.add(2, -2, 2)).getBlock() != Blocks.air) {
            this.cPos = var1.add(2, -2, 2);
            this.cFacing = EnumFacing.WEST;
            return;
        }
        this.cPos = null;
        this.cFacing = null;
    }

    static enum TowerMode {
        NCP,
        Verus;

    }

    static enum SpoofMode {
        Client,
        Server,
        Silent;

    }

    static enum SwingMode {
        Server,
        Client,
        Spam,
        None;

    }

    static enum Rotations {
        NCP,
        None,
        Verus;

    }

    static enum PlaceModes {
        Verus,
        Pre;

    }

    static enum Modes {
        Normal,
        Slow,
        KoksCraft;

    }
}

