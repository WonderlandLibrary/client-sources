package club.bluezenith.util.player;

import club.bluezenith.util.MinecraftInstance;

public class RotationUtil extends MinecraftInstance {
    //TODO: fix this shid
    public static boolean isFacingPlayer(float yaw, float pitch) {
        return false;
    }
    public static float getStaticYaw(){
        float v = 0;
        switch (mc.thePlayer.getHorizontalFacing()){
            case NORTH:
                v = -180;
                break;
            case EAST:
                v = -90;
                break;
            case SOUTH:
                v = 0;
                break;
            case WEST:
                v = 90;
                break;
        }
        v -= mc.thePlayer.movementInput.moveStrafe * 45f;
        return v;
    }
    public static float getNonNegativeDegree(float deg){
        if(deg < 0){
            return 360 + deg;
        }else{
            return deg;
        }
    }
    public static float ew(float deg){
        if(deg > 180) return (deg - 360);
        return deg;
    }
    public static float getDirectionYaw(){
        float s = getNonNegativeDegree(mc.thePlayer.rotationYaw);
        float v = mc.thePlayer.movementInput.moveStrafe * 22.5f;
        return mc.thePlayer.movementInput.moveForward < 0 ? s + 180 + v : s - v;
    }
    public static float getStrafeYaw(){
        float s = getNonNegativeDegree(mc.thePlayer.rotationYaw);
        float v = mc.thePlayer.movementInput.moveStrafe * 90f;
        return mc.thePlayer.movementInput.moveForward < 0 ? s + 180 + v : s - v;
    }
}
