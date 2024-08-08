package net.futureclient.client.modules.render.skeleton;

import net.futureclient.client.events.Event;
import java.util.Map;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.futureclient.client.modules.render.Skeleton;
import net.futureclient.client.qF;
import net.futureclient.client.n;

public class Listener1 extends n<qF.zd>
{
    public final Skeleton k;
    
    public Listener1(final Skeleton k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final qF.zd qf) {
        if (qf.M() instanceof EntityPlayer) {
            final EntityPlayer entityPlayer = (EntityPlayer)qf.M();
            if (qf.M() instanceof ModelBiped) {
                final ModelBiped modelBiped = (ModelBiped)qf.M();
                final Map m = Skeleton.M(this.k);
                final EntityPlayer entityPlayer2 = entityPlayer;
                final float[][] array = new float[5][];
                final float[] array2 = new float[3];
                final int n = 0;
                array2[n] = modelBiped.bipedHead.rotateAngleX;
                array2[1] = modelBiped.bipedHead.rotateAngleY;
                array2[2] = modelBiped.bipedHead.rotateAngleZ;
                array[n] = array2;
                final float[] array3 = { modelBiped.bipedRightArm.rotateAngleX, 0.0f, 0.0f };
                final int n2 = 1;
                array3[n2] = modelBiped.bipedRightArm.rotateAngleY;
                array3[2] = modelBiped.bipedRightArm.rotateAngleZ;
                array[n2] = array3;
                final float[] array4 = { modelBiped.bipedLeftArm.rotateAngleX, modelBiped.bipedLeftArm.rotateAngleY, 0.0f };
                final int n3 = 2;
                array4[n3] = modelBiped.bipedLeftArm.rotateAngleZ;
                array[n3] = array4;
                array[3] = new float[] { modelBiped.bipedRightLeg.rotateAngleX, modelBiped.bipedRightLeg.rotateAngleY, modelBiped.bipedRightLeg.rotateAngleZ };
                array[4] = new float[] { modelBiped.bipedLeftLeg.rotateAngleX, modelBiped.bipedLeftLeg.rotateAngleY, modelBiped.bipedLeftLeg.rotateAngleZ };
                m.put(entityPlayer2, array);
            }
        }
    }
    
    public void M(final Event event) {
        this.M((qF.zd)event);
    }
}
