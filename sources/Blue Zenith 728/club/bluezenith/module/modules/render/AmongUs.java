package club.bluezenith.module.modules.render;

import club.bluezenith.BlueZenith;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

//credit: orange cat (real)
public class AmongUs extends Module {
    public static AmongUs instance;

    public static ModelRenderer idk;
    public static ModelRenderer leftLeg;
    public static ModelRenderer rightLeg;
    public static ModelRenderer visor;
    public static ModelRenderer backpack;
    private static ModelBase model;

    public AmongUs(ModelBase model) {
        super("AmongUs", ModuleCategory.RENDER);
        if(instance == null)
            instance = this;
        AmongUs.model = model;
        initAgain();
    }

    public static boolean state() {
        return BlueZenith.getBlueZenith().getModuleManager().getModule(AmongUs.class).getState();
    }

    public static void initAgain() {
        if (model != null) {
            idk = new ModelRenderer(model, 0, 0);
            idk.addBox(-6, -5, -4.0F, 12, 14, 8, 0);
            idk.setRotationPoint(0.0F, 0.0F, 0.0F);

            leftLeg = new ModelRenderer(model, 0, 0);
            leftLeg.addBox(-6, 8, -4.0F, 4, 6, 8, 0);
            rightLeg = new ModelRenderer(model, 0, 0);
            rightLeg.addBox(2, 8, -4.0F, 4, 6, 8, 0);

            visor = new ModelRenderer(model, 0, 0);
            visor.addBox(-4, -2, -6.0F, 8, 6, 2, 0);
            visor.setRotationPoint(0.0F, 0.0F, 0.0F);

            backpack = new ModelRenderer(model, 0, 0);
            backpack.addBox(-4, -2, 4.0F, 8, 8, 2, 0);
            backpack.setRotationPoint(0.0F, 0.0F, 0.0F);
        }
    }

    EntityPlayer entity = null;

    public void setAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float scaleFactor, Entity entityIn) {
        float rotateAngleY = headYaw / (180F / (float) Math.PI);
        idk.rotateAngleY = rotateAngleY;
        leftLeg.rotateAngleY = rotateAngleY;
        rightLeg.rotateAngleY = rotateAngleY;
        backpack.rotateAngleY = rotateAngleY;
        visor.rotateAngleY = rotateAngleY;

        leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * limbSwingAmount * 0.5f;
        rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * limbSwingAmount * 0.5f;

        if (entityIn instanceof EntityPlayer) {
            this.entity = (EntityPlayer) entityIn;
        } else {
            this.entity = null;
        }
    }

    public void render(float scale) {
        GlStateManager.bindTexture(0);
        GlStateManager.translate(0, 0.5, 0);
        GlStateManager.color(1, 0, 0, 1);
        idk.render(scale);
        leftLeg.render(scale);
        rightLeg.render(scale);
        backpack.render(scale);
        GlStateManager.color(0, 0.5f, 1, 1);
        visor.render(scale);
    }
}