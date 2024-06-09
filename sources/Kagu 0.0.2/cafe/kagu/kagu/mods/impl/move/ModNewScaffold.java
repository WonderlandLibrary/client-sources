package cafe.kagu.kagu.mods.impl.move;

import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventPlayerUpdate;
import cafe.kagu.kagu.eventBus.impl.EventRender3D;
import cafe.kagu.kagu.eventBus.impl.EventTick;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.settings.impl.BooleanSetting;
import cafe.kagu.kagu.settings.impl.DoubleSetting;
import cafe.kagu.kagu.settings.impl.ModeSetting;
import cafe.kagu.kagu.utils.ChatUtils;
import cafe.kagu.kagu.utils.DrawUtils3D;
import cafe.kagu.kagu.utils.RotationUtils;
import cafe.kagu.kagu.utils.SpoofUtils;
import cafe.kagu.kagu.utils.WorldUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldSettings;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Vector3d;
import java.util.Arrays;

public class ModNewScaffold extends Module {

    public ModNewScaffold() {
        super("New Scaffold", Category.MOVEMENT);
        setSettings(prePostMode, c08PrePostMode,
                rotationMode, extend,
                silentRotations, sprint,
                raytrace);
    }

    private ModeSetting prePostMode = new ModeSetting("Timing", "PRE", "PRE", "POST");
    private ModeSetting c08PrePostMode = new ModeSetting("Item Switch Timing", "PRE", "PRE", "POST");
    private ModeSetting rotationMode = new ModeSetting("Rotations", "None", "None",
            "Flick", "Snap", "Stare", "Verus", "Test");
    private DoubleSetting extend = new DoubleSetting("Extend Distance", 0, 0.1, 6, 0.1);
    private BooleanSetting silentRotations = new BooleanSetting("Silent Rots", true);
    private BooleanSetting sprint = new BooleanSetting("Sprint", true);
    private BooleanSetting raytrace = new BooleanSetting("Raytrace", false);

    private WorldUtils.PlaceOnInfo placeOnInfo = null;
    private WorldUtils.PlaceOnInfo lastPlaceOnInfo = null;
    private float[] vecPlacement = new float[3];
    private float[] rotations, lastRotations;

    @Override
    public void onEnable() {
        lastRotations = new float[] {mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch};
        placeOnInfo = null;
        lastPlaceOnInfo = null;
    }

    @EventHandler
    private Handler<EventTick> onTick = e -> {
        SpoofUtils.setSpoofSneakMovement(true);
    };

    @EventHandler
    private Handler<EventRender3D> onRender3D = e -> {
        WorldUtils.PlaceOnInfo placeInfo;
        if (placeOnInfo != null) {
            placeInfo = new WorldUtils.PlaceOnInfo(
                    placeOnInfo.getPlaceOn().offset(placeOnInfo.getPlaceFacing()),
                    placeOnInfo.getPlaceFacing()
            );
        }else if (lastPlaceOnInfo != null){
            placeInfo = new WorldUtils.PlaceOnInfo(
                    lastPlaceOnInfo.getPlaceOn().offset(lastPlaceOnInfo.getPlaceFacing()),
                    lastPlaceOnInfo.getPlaceFacing());
        }else{
            placeInfo = null;
        }

        if (placeInfo != null) {
            GlStateManager.pushMatrix();
            GlStateManager.pushAttrib();
            GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
            GL11.glPolygonOffset(1.0f, -1099998.0f);

            DrawUtils3D.drawColored3DWorldBox(placeInfo.getPlaceOn().getX(), placeInfo.getPlaceOn().getY(),
                    placeInfo.getPlaceOn().getZ(), placeInfo.getPlaceOn().getX() + 1,
                    placeInfo.getPlaceOn().getY() + 1, placeInfo.getPlaceOn().getZ() + 1, 0x90ffffff);

            GlStateManager.popAttrib();
            GlStateManager.popMatrix();
        }

    };

    @EventHandler
    private Handler<EventPlayerUpdate> onPlayerUpdate = e -> {
        if (prePostMode.is("PRE") ? e.isPost() : e.isPre())
            return;

        setPlaceOnInfo();
        boolean canPlace = doRotates(e);
        boolean passesRaytrace = raytrace.isDisabled() || passesRaytrace();
        if (placeOnInfo != null && canPlace && passesRaytrace)
            place();

    };

    /**
     * Places the block
     */
    private void place(){
        ItemStack currentItem = mc.thePlayer.inventory.getCurrentItem();
        if (currentItem == null)
            return;

        IBlockState placeOnState = mc.theWorld.getBlockState(placeOnInfo.getPlaceOn());
        boolean placeAllowed = !placeOnState.getBlock().onBlockActivated(
                mc.theWorld, placeOnInfo.getPlaceOn(),
                placeOnState, mc.thePlayer,
                placeOnInfo.getPlaceFacing(), vecPlacement[0],
                vecPlacement[1], vecPlacement[2]
        );
        if (!placeAllowed) {
            return;
        }
        mc.getNetHandler().getNetworkManager().sendPacket(
                new C08PacketPlayerBlockPlacement(
                        placeOnInfo.getPlaceOn(), placeOnInfo.getPlaceFacing().getIndex(),
                        currentItem, vecPlacement[0], vecPlacement[1], vecPlacement[2]
                )
        );
        if (mc.playerController.getCurrentGameType().isCreative()) {
            int i = currentItem.getMetadata();
            int j = currentItem.stackSize;
            currentItem.onItemUse(
                    mc.thePlayer, mc.theWorld,
                    placeOnInfo.getPlaceOn(), placeOnInfo.getPlaceFacing(),
                    vecPlacement[0], vecPlacement[1], vecPlacement[2]);
            currentItem.setItemDamage(i);
            currentItem.stackSize = j;
        } else {
            currentItem.onItemUse(
                    mc.thePlayer, mc.theWorld,
                    placeOnInfo.getPlaceOn(), placeOnInfo.getPlaceFacing(),
                    vecPlacement[0], vecPlacement[1], vecPlacement[2]);
        }
        swing();
    }

    /**
     * Springs the player's item
     */
    private void swing(){
        mc.thePlayer.swingItem();
    }

    private boolean passesRaytrace(){
        if (placeOnInfo == null)
            return true;
        float partialTicks = mc.getTimer().getRenderPartialTicks();
        double reach = 7;
        Vec3 eyePos = new Vec3(
                mc.thePlayer.posX,
                mc.thePlayer.posY + mc.thePlayer.getEyeHeight(),
                mc.thePlayer.posZ
        );

        Vec3 look;
        if (prePostMode.is("PRE"))
            look = RotationUtils.getLook(rotations[0], rotations[1], lastRotations[0], lastRotations[1]);
        else
            look = RotationUtils.getLook(lastRotations[0], lastRotations[1], lastRotations[0], lastRotations[1]);

        Vec3 vec32 = eyePos.addVector(look.xCoord * reach, look.yCoord * reach, look.zCoord * reach);

        BlockPos placeOn = placeOnInfo.getPlaceOn();
        WorldClient theWorld = mc.theWorld;
        IBlockState placeOnState = theWorld.getBlockState(placeOn);
        AxisAlignedBB collisionBox = placeOnState.getBlock().getCollisionBoundingBox(theWorld, placeOn, placeOnState);


        MovingObjectPosition rayTrace = null;
        try {
            rayTrace = collisionBox.calculateIntercept(eyePos, vec32);
        }catch(Exception e1) {

        }

        // Process the results
        if (rayTrace == null || rayTrace.typeOfHit == MovingObjectPosition.MovingObjectType.MISS
                || rayTrace.sideHit != placeOnInfo.getPlaceFacing()) {
            return false;
        }
        Vec3 hitVec = rayTrace.hitVec;
        vecPlacement = new float[] {
                (float) hitVec.xCoord - placeOnInfo.getPlaceOn().getX(),
                (float) hitVec.yCoord - placeOnInfo.getPlaceOn().getY(),
                (float) hitVec.zCoord - placeOnInfo.getPlaceOn().getZ()
        };
        return true;
    }

    /**
     * Sets the yaw and pitch according to the correct rotations
     * @param e The event
     * @return Whether the player is able to click at this time
     */
    private boolean doRotates(EventPlayerUpdate e){

        float[] rotations = new float[]{e.getRotationYaw(), e.getRotationPitch()};
        if (placeOnInfo != null){
            rotations = getRotsForInfo(placeOnInfo, lastRotations == null ? rotations : lastRotations);
        }

        boolean canHit = true;
        switch (rotationMode.getMode()){
            case "None":return canHit;
            case "Snap":{
                if (placeOnInfo != null)
                    break;
                rotations[0] = lastRotations[0];
                rotations[1] = lastRotations[1];
            }break;
            case "Stare":{
                if (placeOnInfo != null || lastPlaceOnInfo == null)
                    break;
                float[] lastInfoRotations = getRotsForInfo(lastPlaceOnInfo, lastRotations);
                rotations[0] = lastInfoRotations[0];
                rotations[1] = lastInfoRotations[1];
            }break;
            case "Verus":{
                if (lastPlaceOnInfo == null)
                    break;

                if (placeOnInfo == null)
                    rotations = lastRotations;

                float deltaYaw = lastRotations[0] - rotations[0];
                float deltaPitch = lastRotations[1] - rotations[1];
                float minDistanceSize = 12f;

                rotations[0] = lastRotations[0] - deltaYaw * 0.4f;
                rotations[1] = lastRotations[1] - deltaPitch * 0.9f;

                float rotationTargetSize = 18;
                float newDeltaYaw = lastRotations[0] - rotations[0];
                float newDeltaPitch = lastRotations[1] - rotations[1];

                canHit = Math.abs(newDeltaYaw) <= rotationTargetSize && Math.abs(newDeltaPitch) <= rotationTargetSize;
            }break;
            case "Test":{
                if (lastPlaceOnInfo == null)
                    break;

                if (placeOnInfo == null){
                    rotations = lastRotations;
                    break;
                }

                float deltaYaw = lastRotations[0] - rotations[0];
                float deltaPitch = lastRotations[1] - rotations[1];
                float minDistanceSize = 12f;

                rotations[0] = lastRotations[0] - deltaYaw * 0.9f;
                rotations[1] = lastRotations[1] - deltaPitch * 0.9f;

                float rotationTargetSize = 18;
                float newDeltaYaw = lastRotations[0] - rotations[0];
                float newDeltaPitch = lastRotations[1] - rotations[1];

                canHit = Math.abs(newDeltaYaw) <= rotationTargetSize && Math.abs(newDeltaPitch) <= rotationTargetSize;
            }break;
        }

        RotationUtils.applyGCD(rotations, lastRotations);
        lastRotations = rotations;
        this.rotations = rotations;
        SpoofUtils.setSpoofedYaw(rotations[0]);
        SpoofUtils.setSpoofedPitch(rotations[1]);
        e.setRotationYaw(rotations[0]);
        e.setRotationPitch(rotations[1]);
        if (!silentRotations.isEnabled()){
            mc.thePlayer.rotationYaw = rotations[0];
            mc.thePlayer.rotationPitch = rotations[1];
        }
        return canHit;
    }

    /**
     * Gets the rotations for a block info
     * @param placeOnInfo The place on block info
     * @param lastRotations The last rotations, for smoothing purposes
     * @return The rotations to the center of the place on's block face
     */
    private float[] getRotsForInfo(WorldUtils.PlaceOnInfo placeOnInfo, float[] lastRotations){
        BlockPos placeOnBlock = placeOnInfo.getPlaceOn();
        Vector3d placeOn = new Vector3d(placeOnBlock.getX(), placeOnBlock.getY(), placeOnBlock.getZ());
        correctPlaceOnAndVec(placeOnInfo, placeOn);
        float[] rotations = RotationUtils.getRotations(placeOn);
        RotationUtils.makeRotationValuesLoopCorrectly(rotations, lastRotations);
        return rotations;
    }

    /**
     * We offset the placeOn values depending on the facing
     * so we can aim at the correct face
     * @param placeOn The placeon vector
     */
    private void correctPlaceOnAndVec(WorldUtils.PlaceOnInfo placeOnInfo, Vector3d placeOn){
        vecPlacement = new float[3];
        switch (placeOnInfo.getPlaceFacing()) {
            case UP: {
                placeOn.x += 0.5;
                placeOn.y += 1;
                placeOn.z += 0.5;
                vecPlacement[0] += 0.5f;
                vecPlacement[1] += 1;
                vecPlacement[2] += 0.5f;
            }break;
            case NORTH: {
                placeOn.x += 0.5;
                placeOn.y += 0.5;
                vecPlacement[0] += 0.5f;
                vecPlacement[1] += 0.5f;
            }break;
            case EAST: {
                placeOn.x += 1;
                placeOn.y += 0.5;
                placeOn.z += 0.5;
                vecPlacement[0] += 1;
                vecPlacement[1] += 0.5f;
                vecPlacement[2] += 0.5f;
            }break;
            case SOUTH: {
                placeOn.x += 0.5;
                placeOn.y += 0.5;
                placeOn.z += 1;
                vecPlacement[0] += 0.5f;
                vecPlacement[1] += 0.5f;
                vecPlacement[2] += 1;
            }break;
            case WEST: {
                placeOn.y += 0.5;
                placeOn.z += 0.5;
                vecPlacement[1] += 0.5f;
                vecPlacement[2] += 0.5f;
            }break;
            case DOWN: {
                placeOn.x += 0.5;
                placeOn.z += 0.5;
                vecPlacement[0] += 0.5f;
                vecPlacement[2] += 0.5f;
            }break;
        }
    }

    private void setPlaceOnInfo(){
        if (mc.playerController.getCurrentGameType() == WorldSettings.GameType.SPECTATOR || mc.playerController.getCurrentGameType() == WorldSettings.GameType.ADVENTURE) {
            placeOnInfo = null;
            return;
        }

        double[] playerPos = new double[] {mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ};
        BlockPos placePos = new BlockPos(playerPos[0], playerPos[1] - 1, playerPos[2]);
        for (double d = 0; d < extend.getValue(); d += 0.1) {
            double[] pos = WorldUtils.extendPosition(d, playerPos[0], playerPos[1] - 1, playerPos[2]);
            BlockPos newPos = new BlockPos(pos[0], pos[1], pos[2]);
            if (!WorldUtils.isBlockSolid(newPos)) {
                placePos = newPos;
                break;
            }
        }

        if (WorldUtils.isBlockSolid(placePos)) {
            placePos = null;
            placeOnInfo = null;
            return;
        }

        WorldUtils.PlaceOnInfo placeOn = WorldUtils.getPlaceOn(placePos, 4.5);
        if (placeOn != null) {
//            WorldUtils.PlaceOnInfo oldPlaceOn = lastPlaceOnInfo;
            lastPlaceOnInfo = placeOnInfo == null ? placeOn : placeOnInfo;
//            if ((!placeOn.equals(oldPlaceOn)) || placeOn.getPlaceFacing() == EnumFacing.UP)
            placeOnInfo = placeOn;
        }
    }

    public BooleanSetting getSprint() {
        return sprint;
    }

}
