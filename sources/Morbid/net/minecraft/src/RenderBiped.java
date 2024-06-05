package net.minecraft.src;

import org.lwjgl.opengl.*;

public class RenderBiped extends RenderLiving
{
    protected ModelBiped modelBipedMain;
    protected float field_77070_b;
    protected ModelBiped field_82423_g;
    protected ModelBiped field_82425_h;
    private static final String[] bipedArmorFilenamePrefix;
    
    static {
        bipedArmorFilenamePrefix = new String[] { "cloth", "chain", "iron", "diamond", "gold" };
    }
    
    public RenderBiped(final ModelBiped par1ModelBiped, final float par2) {
        this(par1ModelBiped, par2, 1.0f);
    }
    
    public RenderBiped(final ModelBiped par1ModelBiped, final float par2, final float par3) {
        super(par1ModelBiped, par2);
        this.modelBipedMain = par1ModelBiped;
        this.field_77070_b = par3;
        this.func_82421_b();
    }
    
    protected void func_82421_b() {
        this.field_82423_g = new ModelBiped(1.0f);
        this.field_82425_h = new ModelBiped(0.5f);
    }
    
    @Override
    protected int shouldRenderPass(final EntityLiving par1EntityLiving, final int par2, final float par3) {
        final ItemStack var4 = par1EntityLiving.getCurrentArmor(3 - par2);
        if (var4 != null) {
            final Item var5 = var4.getItem();
            if (var5 instanceof ItemArmor) {
                final ItemArmor var6 = (ItemArmor)var5;
                this.loadTexture("/armor/" + RenderBiped.bipedArmorFilenamePrefix[var6.renderIndex] + "_" + ((par2 == 2) ? 2 : 1) + ".png");
                final ModelBiped var7 = (par2 == 2) ? this.field_82425_h : this.field_82423_g;
                var7.bipedHead.showModel = (par2 == 0);
                var7.bipedHeadwear.showModel = (par2 == 0);
                var7.bipedBody.showModel = (par2 == 1 || par2 == 2);
                var7.bipedRightArm.showModel = (par2 == 1);
                var7.bipedLeftArm.showModel = (par2 == 1);
                var7.bipedRightLeg.showModel = (par2 == 2 || par2 == 3);
                var7.bipedLeftLeg.showModel = (par2 == 2 || par2 == 3);
                this.setRenderPassModel(var7);
                if (var7 != null) {
                    var7.onGround = this.mainModel.onGround;
                }
                if (var7 != null) {
                    var7.isRiding = this.mainModel.isRiding;
                }
                if (var7 != null) {
                    var7.isChild = this.mainModel.isChild;
                }
                final float var8 = 1.0f;
                if (var6.getArmorMaterial() == EnumArmorMaterial.CLOTH) {
                    final int var9 = var6.getColor(var4);
                    final float var10 = (var9 >> 16 & 0xFF) / 255.0f;
                    final float var11 = (var9 >> 8 & 0xFF) / 255.0f;
                    final float var12 = (var9 & 0xFF) / 255.0f;
                    GL11.glColor3f(var8 * var10, var8 * var11, var8 * var12);
                    if (var4.isItemEnchanted()) {
                        return 31;
                    }
                    return 16;
                }
                else {
                    GL11.glColor3f(var8, var8, var8);
                    if (var4.isItemEnchanted()) {
                        return 15;
                    }
                    return 1;
                }
            }
        }
        return -1;
    }
    
    @Override
    protected void func_82408_c(final EntityLiving par1EntityLiving, final int par2, final float par3) {
        final ItemStack var4 = par1EntityLiving.getCurrentArmor(3 - par2);
        if (var4 != null) {
            final Item var5 = var4.getItem();
            if (var5 instanceof ItemArmor) {
                final ItemArmor var6 = (ItemArmor)var5;
                this.loadTexture("/armor/" + RenderBiped.bipedArmorFilenamePrefix[var6.renderIndex] + "_" + ((par2 == 2) ? 2 : 1) + "_b.png");
                final float var7 = 1.0f;
                GL11.glColor3f(var7, var7, var7);
            }
        }
    }
    
    @Override
    public void doRenderLiving(final EntityLiving par1EntityLiving, final double par2, final double par4, final double par6, final float par8, final float par9) {
        final float var10 = 1.0f;
        GL11.glColor3f(var10, var10, var10);
        final ItemStack var11 = par1EntityLiving.getHeldItem();
        this.func_82420_a(par1EntityLiving, var11);
        double var12 = par4 - par1EntityLiving.yOffset;
        if (par1EntityLiving.isSneaking() && !(par1EntityLiving instanceof EntityPlayerSP)) {
            var12 -= 0.125;
        }
        super.doRenderLiving(par1EntityLiving, par2, var12, par6, par8, par9);
        final ModelBiped field_82423_g = this.field_82423_g;
        final ModelBiped field_82425_h = this.field_82425_h;
        final ModelBiped modelBipedMain = this.modelBipedMain;
        final boolean aimedBow = false;
        modelBipedMain.aimedBow = aimedBow;
        field_82425_h.aimedBow = aimedBow;
        field_82423_g.aimedBow = aimedBow;
        final ModelBiped field_82423_g2 = this.field_82423_g;
        final ModelBiped field_82425_h2 = this.field_82425_h;
        final ModelBiped modelBipedMain2 = this.modelBipedMain;
        final boolean isSneak = false;
        modelBipedMain2.isSneak = isSneak;
        field_82425_h2.isSneak = isSneak;
        field_82423_g2.isSneak = isSneak;
        final ModelBiped field_82423_g3 = this.field_82423_g;
        final ModelBiped field_82425_h3 = this.field_82425_h;
        final ModelBiped modelBipedMain3 = this.modelBipedMain;
        final boolean heldItemRight = false;
        modelBipedMain3.heldItemRight = (heldItemRight ? 1 : 0);
        field_82425_h3.heldItemRight = (heldItemRight ? 1 : 0);
        field_82423_g3.heldItemRight = (heldItemRight ? 1 : 0);
    }
    
    protected void func_82420_a(final EntityLiving par1EntityLiving, final ItemStack par2ItemStack) {
        final ModelBiped field_82423_g = this.field_82423_g;
        final ModelBiped field_82425_h = this.field_82425_h;
        final ModelBiped modelBipedMain = this.modelBipedMain;
        final boolean heldItemRight;
        final boolean b = heldItemRight = (((par2ItemStack != null) ? 1 : 0) != 0);
        modelBipedMain.heldItemRight = (b ? 1 : 0);
        field_82425_h.heldItemRight = (b ? 1 : 0);
        field_82423_g.heldItemRight = (heldItemRight ? 1 : 0);
        final ModelBiped field_82423_g2 = this.field_82423_g;
        final ModelBiped field_82425_h2 = this.field_82425_h;
        final ModelBiped modelBipedMain2 = this.modelBipedMain;
        final boolean sneaking = par1EntityLiving.isSneaking();
        modelBipedMain2.isSneak = sneaking;
        field_82425_h2.isSneak = sneaking;
        field_82423_g2.isSneak = sneaking;
    }
    
    @Override
    protected void renderEquippedItems(final EntityLiving par1EntityLiving, final float par2) {
        final float var3 = 1.0f;
        GL11.glColor3f(var3, var3, var3);
        super.renderEquippedItems(par1EntityLiving, par2);
        final ItemStack var4 = par1EntityLiving.getHeldItem();
        final ItemStack var5 = par1EntityLiving.getCurrentArmor(3);
        if (var5 != null) {
            GL11.glPushMatrix();
            this.modelBipedMain.bipedHead.postRender(0.0625f);
            if (var5.getItem().itemID < 256) {
                if (RenderBlocks.renderItemIn3d(Block.blocksList[var5.itemID].getRenderType())) {
                    final float var6 = 0.625f;
                    GL11.glTranslatef(0.0f, -0.25f, 0.0f);
                    GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
                    GL11.glScalef(var6, -var6, -var6);
                }
                this.renderManager.itemRenderer.renderItem(par1EntityLiving, var5, 0);
            }
            else if (var5.getItem().itemID == Item.skull.itemID) {
                final float var6 = 1.0625f;
                GL11.glScalef(var6, -var6, -var6);
                String var7 = "";
                if (var5.hasTagCompound() && var5.getTagCompound().hasKey("SkullOwner")) {
                    var7 = var5.getTagCompound().getString("SkullOwner");
                }
                TileEntitySkullRenderer.skullRenderer.func_82393_a(-0.5f, 0.0f, -0.5f, 1, 180.0f, var5.getItemDamage(), var7);
            }
            GL11.glPopMatrix();
        }
        if (var4 != null) {
            GL11.glPushMatrix();
            if (this.mainModel.isChild) {
                final float var6 = 0.5f;
                GL11.glTranslatef(0.0f, 0.625f, 0.0f);
                GL11.glRotatef(-20.0f, -1.0f, 0.0f, 0.0f);
                GL11.glScalef(var6, var6, var6);
            }
            this.modelBipedMain.bipedRightArm.postRender(0.0625f);
            GL11.glTranslatef(-0.0625f, 0.4375f, 0.0625f);
            if (var4.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[var4.itemID].getRenderType())) {
                float var6 = 0.5f;
                GL11.glTranslatef(0.0f, 0.1875f, -0.3125f);
                var6 *= 0.75f;
                GL11.glRotatef(20.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
                GL11.glScalef(-var6, -var6, var6);
            }
            else if (var4.itemID == Item.bow.itemID) {
                final float var6 = 0.625f;
                GL11.glTranslatef(0.0f, 0.125f, 0.3125f);
                GL11.glRotatef(-20.0f, 0.0f, 1.0f, 0.0f);
                GL11.glScalef(var6, -var6, var6);
                GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            }
            else if (Item.itemsList[var4.itemID].isFull3D()) {
                final float var6 = 0.625f;
                if (Item.itemsList[var4.itemID].shouldRotateAroundWhenRendering()) {
                    GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
                    GL11.glTranslatef(0.0f, -0.125f, 0.0f);
                }
                this.func_82422_c();
                GL11.glScalef(var6, -var6, var6);
                GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            }
            else {
                final float var6 = 0.375f;
                GL11.glTranslatef(0.25f, 0.1875f, -0.1875f);
                GL11.glScalef(var6, var6, var6);
                GL11.glRotatef(60.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(20.0f, 0.0f, 0.0f, 1.0f);
            }
            this.renderManager.itemRenderer.renderItem(par1EntityLiving, var4, 0);
            if (var4.getItem().requiresMultipleRenderPasses()) {
                this.renderManager.itemRenderer.renderItem(par1EntityLiving, var4, 1);
            }
            GL11.glPopMatrix();
        }
    }
    
    protected void func_82422_c() {
        GL11.glTranslatef(0.0f, 0.1875f, 0.0f);
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.doRenderLiving((EntityLiving)par1Entity, par2, par4, par6, par8, par9);
    }
}
