package dev.stephen.nexus.utils.mc;

import dev.stephen.nexus.utils.Utils;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;

import lombok.Setter;
import net.minecraft.world.World;

public class PlayerUtil implements Utils {
    private static int onGroundTicks;
    private static int inAirTicks;
    private static int ticksExisted;

    @Setter
    private static float timer = 1F;

    private PlayerUtil() {
    }

    public static int onGroundTicks() {
        return onGroundTicks;
    }

    public static int ticksExisted() {
        return ticksExisted;
    }

    public static int inAirTicks() {
        return inAirTicks;
    }

    public static float timer() {
        return timer;
    }

    public static void updateTicks(boolean onGround) {
        ticksExisted++;

        if (onGround) {
            onGroundTicks++;
            inAirTicks = 0;
        } else {
            inAirTicks++;
            onGroundTicks = 0;
        }
    }

    public static boolean hasMotionForAirTick(int tick) {
        return MoveUtils.getMotionY() == getMotionForAirTick(tick);
    }

    public static double getMotionForAirTick(int tick) {
        double[][] airTickMotionValues = {
                {0, -0.0784000015258789},
                {1, 0.33319999363422365},
                {2, 0.24813599859094576},
                {3, 0.16477328182606651},
                {4, 0.08307781780646721},
                {5, 0.0030162615090425808},
                {6, -0.07544406518948656},
                {7, -0.15233518685055708},
                {8, -0.22768848754498797},
                {9, -0.30153472366278034},
                {10, -0.3739040364667221},
                {11, -0.4448259643949201},
                {12, -0.5143294551172826},
                {13, -0.5824428773508716},
                {14, -0.6491940324389494},
                {15, -0.7146101656984427},
                {16, -0.7787179775404599},
                {17, -0.8415436343683963},
                {18, -0.9031127792580782},
                {19, -0.9634505424243047},
                {20, -1.0225815514780583},
                {21, -1.0805299414785714},
                {22, -1.137319364784352},
                {23, -1.1929730007071893},
                {24, -1.2475135649730786},
                {25, -1.300963318993929},
                {26, -1.3533440789538353},
                {27, -1.4046772247136274},
                {28, -1.4549837085373256},
                {29, -1.50428406364407},
                {30, -1.552598412589009},
                {31, -1.5999464754765724},
                {32, -1.6463475780094772},
                {33, -1.6918206593767546},
                {34, -1.7363842799840168},
                {35, -1.7800566290291173},
                {36, -1.8228555319262998},
                {37, -1.8647984575818628},
                {38, -1.9059025255243125},
                {39, -1.946184512891911},
                {40, -1.9856608612804754},
                {41, -2.02434768345422},
                {42, -2.0622607699223825},
                {43, -2.0994155953843165},
                {44, -2.135827325045684},
                {45, -2.1715108208083227},
                {46, -2.206480647336317},
                {47, -2.240751078000748},
                {48, -2.274336100705547},
                {49, -2.3072494235968333},
                {50, -2.339504480658066},
                {51, -2.3711144371932904},
                {52, -2.4020921952007224},
                {53, -2.4324503986388595},
                {54, -2.462201438587271},
                {55, -2.49135745830417},
                {56, -2.519930358182838},
                {57, -2.5479318006089176},
                {58, -2.5753732147205604},
                {59, -2.602265801073374},
                {60, -2.6286205362120665},
                {61, -2.654448177150662},
                {62, -2.6797592657631086},
                {63, -2.7045641330860772},
                {64, -2.7288729035357018},
                {65, -2.752695499039987},
                {66, -2.776041643088566}
        };
        for (double[] tickMotion : airTickMotionValues) {
            if (tickMotion[0] == tick) {
                return tickMotion[1];
            }
        }
        return -69;
    }

    public static BlockPos blockRelativeToPlayer(double offsetX, double offsetY, double offsetZ) {
        final double x = mc.player.getX() + offsetX;
        final double y = mc.player.getY() + offsetY;
        final double z = mc.player.getZ() + offsetZ;

        final int i = MathHelper.floor(x);
        final int j = MathHelper.floor(y);
        final int k = MathHelper.floor(z);

        return new BlockPos(i, j, k);
    }

    public static BlockState blockStateRelativeToPlayer(double offsetX, double offsetY, double offsetZ) {
        BlockPos blockPos = blockRelativeToPlayer(offsetX, offsetY, offsetZ);

        return mc.world.getBlockState(blockPos);
    }

    public static float getDistanceToGround() {
        for (int i = (int) (mc.player.getY()); i > -1; i--) {
            BlockPos pos = new BlockPos(mc.player.getBlockX(), i, mc.player.getBlockZ());
            if (!(mc.world.getBlockState(pos).getBlock() instanceof AirBlock)) {
                return (float) (mc.player.getY() - pos.getY()) - 1;
            }
        }
        return 0;
    }

    public static boolean playerOverAir() {
        return mc.world.getBlockState(mc.player.getBlockPos().down()).isAir();
    }

    public static boolean isOverVoid() {
        for (int posY = mc.player.getBlockY(); posY > 0.0; --posY) {
            Block block = mc.world.getBlockState(new BlockPos(mc.player.getBlockX(), posY, mc.player.getBlockZ())).getBlock();
            if (!(block instanceof AirBlock)) {
                return false;
            }
        }
        return true;
    }

    public static void click(boolean oneDotEight) {
        if (oneDotEight) {
            mc.player.swingHand(Hand.MAIN_HAND);
        }
        switch (mc.crosshairTarget.getType()) {
            case ENTITY:
                EntityHitResult entityHitResult = (EntityHitResult) mc.crosshairTarget;
                mc.interactionManager.attackEntity(mc.player, entityHitResult.getEntity());
                break;
            case BLOCK:
            case MISS:
            default:
        }
        if (!oneDotEight) {
            mc.player.swingHand(Hand.MAIN_HAND);
        }
    }

    public static boolean insideBlock(Box box) {
        World world = MinecraftClient.getInstance().world;

        for (int x = MathHelper.floor(box.minX); x < MathHelper.floor(box.maxX) + 1; ++x) {
            for (int y = MathHelper.floor(box.minY); y < MathHelper.floor(box.maxY) + 1; ++y) {
                for (int z = MathHelper.floor(box.minZ); z < MathHelper.floor(box.maxZ) + 1; ++z) {
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState blockState = world.getBlockState(pos);
                    Block block = blockState.getBlock();

                    if (!(block instanceof AirBlock)) {
                        // Get the collision shape (replacing old collision box methods)
                        Box collisionBox = blockState.getCollisionShape(world, pos).getBoundingBox();

                        // Check if the box intersects with the block's collision box
                        if (collisionBox != null && box.intersects(collisionBox.offset(pos))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
