package fr.dog.util.render.model;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.Vec3;

public class VecUtil {

    public static Vec3 fixVec3(Vec3 vec){
        vec = new Vec3(vec.xCoord  - RenderManager.renderPosX, vec.yCoord - RenderManager.renderPosY, vec.zCoord  - RenderManager.renderPosZ);
        return vec;
    }

}
