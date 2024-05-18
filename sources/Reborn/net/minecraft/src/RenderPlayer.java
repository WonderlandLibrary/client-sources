package net.minecraft.src;

import org.lwjgl.opengl.*;

public class RenderPlayer extends RenderLiving
{
    private ModelBiped modelBipedMain;
    private ModelBiped modelArmorChestplate;
    private ModelBiped modelArmor;
    private static final String[] armorFilenamePrefix;
    
    static {
        armorFilenamePrefix = new String[] { "cloth", "chain", "iron", "diamond", "gold" };
    }
    
    public RenderPlayer() {
        super(new ModelBiped(0.0f), 0.5f);
        this.modelBipedMain = (ModelBiped)this.mainModel;
        this.modelArmorChestplate = new ModelBiped(1.0f);
        this.modelArmor = new ModelBiped(0.5f);
    }
    
    protected void func_98191_a(final EntityPlayer par1EntityPlayer) {
        this.loadDownloadableImageTexture(par1EntityPlayer.skinUrl, par1EntityPlayer.getTexture());
    }
    
    protected int setArmorModel(final EntityPlayer par1EntityPlayer, final int par2, final float par3) {
        final ItemStack var4 = par1EntityPlayer.inventory.armorItemInSlot(3 - par2);
        if (var4 != null) {
            final Item var5 = var4.getItem();
            if (var5 instanceof ItemArmor) {
                final ItemArmor var6 = (ItemArmor)var5;
                this.loadTexture("/armor/" + RenderPlayer.armorFilenamePrefix[var6.renderIndex] + "_" + ((par2 == 2) ? 2 : 1) + ".png");
                final ModelBiped var7 = (par2 == 2) ? this.modelArmor : this.modelArmorChestplate;
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
    
    protected void func_82439_b(final EntityPlayer par1EntityPlayer, final int par2, final float par3) {
        final ItemStack var4 = par1EntityPlayer.inventory.armorItemInSlot(3 - par2);
        if (var4 != null) {
            final Item var5 = var4.getItem();
            if (var5 instanceof ItemArmor) {
                final ItemArmor var6 = (ItemArmor)var5;
                this.loadTexture("/armor/" + RenderPlayer.armorFilenamePrefix[var6.renderIndex] + "_" + ((par2 == 2) ? 2 : 1) + "_b.png");
                final float var7 = 1.0f;
                GL11.glColor3f(var7, var7, var7);
            }
        }
    }
    
    public void renderPlayer(final EntityPlayer par1EntityPlayer, final double par2, final double par4, final double par6, final float par8, final float par9) {
        final float var10 = 1.0f;
        GL11.glColor3f(var10, var10, var10);
        final ItemStack var11 = par1EntityPlayer.inventory.getCurrentItem();
        final ModelBiped modelArmorChestplate = this.modelArmorChestplate;
        final ModelBiped modelArmor = this.modelArmor;
        final ModelBiped modelBipedMain = this.modelBipedMain;
        final boolean heldItemRight;
        final boolean b = heldItemRight = (((var11 != null) ? 1 : 0) != 0);
        modelBipedMain.heldItemRight = (b ? 1 : 0);
        modelArmor.heldItemRight = (b ? 1 : 0);
        modelArmorChestplate.heldItemRight = (heldItemRight ? 1 : 0);
        if (var11 != null && par1EntityPlayer.getItemInUseCount() > 0) {
            final EnumAction var12 = var11.getItemUseAction();
            if (var12 == EnumAction.block) {
                final ModelBiped modelArmorChestplate2 = this.modelArmorChestplate;
                final ModelBiped modelArmor2 = this.modelArmor;
                final ModelBiped modelBipedMain2 = this.modelBipedMain;
                final int heldItemRight2 = 3;
                modelBipedMain2.heldItemRight = heldItemRight2;
                modelArmor2.heldItemRight = heldItemRight2;
                modelArmorChestplate2.heldItemRight = heldItemRight2;
            }
            else if (var12 == EnumAction.bow) {
                final ModelBiped modelArmorChestplate3 = this.modelArmorChestplate;
                final ModelBiped modelArmor3 = this.modelArmor;
                final ModelBiped modelBipedMain3 = this.modelBipedMain;
                final boolean aimedBow = true;
                modelBipedMain3.aimedBow = aimedBow;
                modelArmor3.aimedBow = aimedBow;
                modelArmorChestplate3.aimedBow = aimedBow;
            }
        }
        final ModelBiped modelArmorChestplate4 = this.modelArmorChestplate;
        final ModelBiped modelArmor4 = this.modelArmor;
        final ModelBiped modelBipedMain4 = this.modelBipedMain;
        final boolean sneaking = par1EntityPlayer.isSneaking();
        modelBipedMain4.isSneak = sneaking;
        modelArmor4.isSneak = sneaking;
        modelArmorChestplate4.isSneak = sneaking;
        double var13 = par4 - par1EntityPlayer.yOffset;
        if (par1EntityPlayer.isSneaking() && !(par1EntityPlayer instanceof EntityPlayerSP)) {
            var13 -= 0.125;
        }
        super.doRenderLiving(par1EntityPlayer, par2, var13, par6, par8, par9);
        final ModelBiped modelArmorChestplate5 = this.modelArmorChestplate;
        final ModelBiped modelArmor5 = this.modelArmor;
        final ModelBiped modelBipedMain5 = this.modelBipedMain;
        final boolean aimedBow2 = false;
        modelBipedMain5.aimedBow = aimedBow2;
        modelArmor5.aimedBow = aimedBow2;
        modelArmorChestplate5.aimedBow = aimedBow2;
        final ModelBiped modelArmorChestplate6 = this.modelArmorChestplate;
        final ModelBiped modelArmor6 = this.modelArmor;
        final ModelBiped modelBipedMain6 = this.modelBipedMain;
        final boolean isSneak = false;
        modelBipedMain6.isSneak = isSneak;
        modelArmor6.isSneak = isSneak;
        modelArmorChestplate6.isSneak = isSneak;
        final ModelBiped modelArmorChestplate7 = this.modelArmorChestplate;
        final ModelBiped modelArmor7 = this.modelArmor;
        final ModelBiped modelBipedMain7 = this.modelBipedMain;
        final boolean heldItemRight3 = false;
        modelBipedMain7.heldItemRight = (heldItemRight3 ? 1 : 0);
        modelArmor7.heldItemRight = (heldItemRight3 ? 1 : 0);
        modelArmorChestplate7.heldItemRight = (heldItemRight3 ? 1 : 0);
    }
    
    protected void renderSpecials(final EntityPlayer par1EntityPlayer, final float par2) {
        final float var3 = 1.0f;
        GL11.glColor3f(var3, var3, var3);
        super.renderEquippedItems(par1EntityPlayer, par2);
        super.renderArrowsStuckInEntity(par1EntityPlayer, par2);
        final ItemStack var4 = par1EntityPlayer.inventory.armorItemInSlot(3);
        if (var4 != null) {
            GL11.glPushMatrix();
            this.modelBipedMain.bipedHead.postRender(0.0625f);
            if (var4.getItem().itemID < 256) {
                if (RenderBlocks.renderItemIn3d(Block.blocksList[var4.itemID].getRenderType())) {
                    final float var5 = 0.625f;
                    GL11.glTranslatef(0.0f, -0.25f, 0.0f);
                    GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
                    GL11.glScalef(var5, -var5, -var5);
                }
                this.renderManager.itemRenderer.renderItem(par1EntityPlayer, var4, 0);
            }
            else if (var4.getItem().itemID == Item.skull.itemID) {
                final float var5 = 1.0625f;
                GL11.glScalef(var5, -var5, -var5);
                String var6 = "";
                if (var4.hasTagCompound() && var4.getTagCompound().hasKey("SkullOwner")) {
                    var6 = var4.getTagCompound().getString("SkullOwner");
                }
                TileEntitySkullRenderer.skullRenderer.func_82393_a(-0.5f, 0.0f, -0.5f, 1, 180.0f, var4.getItemDamage(), var6);
            }
            GL11.glPopMatrix();
        }
        if (par1EntityPlayer.username.equals("deadmau5") && this.loadDownloadableImageTexture(par1EntityPlayer.skinUrl, null)) {
            for (int var7 = 0; var7 < 2; ++var7) {
                final float var8 = par1EntityPlayer.prevRotationYaw + (par1EntityPlayer.rotationYaw - par1EntityPlayer.prevRotationYaw) * par2 - (par1EntityPlayer.prevRenderYawOffset + (par1EntityPlayer.renderYawOffset - par1EntityPlayer.prevRenderYawOffset) * par2);
                final float var9 = par1EntityPlayer.prevRotationPitch + (par1EntityPlayer.rotationPitch - par1EntityPlayer.prevRotationPitch) * par2;
                GL11.glPushMatrix();
                GL11.glRotatef(var8, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(var9, 1.0f, 0.0f, 0.0f);
                GL11.glTranslatef(0.375f * (var7 * 2 - 1), 0.0f, 0.0f);
                GL11.glTranslatef(0.0f, -0.375f, 0.0f);
                GL11.glRotatef(-var9, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(-var8, 0.0f, 1.0f, 0.0f);
                final float var10 = 1.3333334f;
                GL11.glScalef(var10, var10, var10);
                this.modelBipedMain.renderEars(0.0625f);
                GL11.glPopMatrix();
            }
        }
        if (this.loadDownloadableImageTexture(par1EntityPlayer.cloakUrl, null) && !par1EntityPlayer.isInvisible() && !par1EntityPlayer.getHideCape()) {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, 0.0f, 0.125f);
            final double var11 = par1EntityPlayer.field_71091_bM + (par1EntityPlayer.field_71094_bP - par1EntityPlayer.field_71091_bM) * par2 - (par1EntityPlayer.prevPosX + (par1EntityPlayer.posX - par1EntityPlayer.prevPosX) * par2);
            final double var12 = par1EntityPlayer.field_71096_bN + (par1EntityPlayer.field_71095_bQ - par1EntityPlayer.field_71096_bN) * par2 - (par1EntityPlayer.prevPosY + (par1EntityPlayer.posY - par1EntityPlayer.prevPosY) * par2);
            final double var13 = par1EntityPlayer.field_71097_bO + (par1EntityPlayer.field_71085_bR - par1EntityPlayer.field_71097_bO) * par2 - (par1EntityPlayer.prevPosZ + (par1EntityPlayer.posZ - par1EntityPlayer.prevPosZ) * par2);
            final float var14 = par1EntityPlayer.prevRenderYawOffset + (par1EntityPlayer.renderYawOffset - par1EntityPlayer.prevRenderYawOffset) * par2;
            final double var15 = MathHelper.sin(var14 * 3.1415927f / 180.0f);
            final double var16 = -MathHelper.cos(var14 * 3.1415927f / 180.0f);
            float var17 = (float)var12 * 10.0f;
            if (var17 < -6.0f) {
                var17 = -6.0f;
            }
            if (var17 > 32.0f) {
                var17 = 32.0f;
            }
            float var18 = (float)(var11 * var15 + var13 * var16) * 100.0f;
            final float var19 = (float)(var11 * var16 - var13 * var15) * 100.0f;
            if (var18 < 0.0f) {
                var18 = 0.0f;
            }
            final float var20 = par1EntityPlayer.prevCameraYaw + (par1EntityPlayer.cameraYaw - par1EntityPlayer.prevCameraYaw) * par2;
            var17 += MathHelper.sin((par1EntityPlayer.prevDistanceWalkedModified + (par1EntityPlayer.distanceWalkedModified - par1EntityPlayer.prevDistanceWalkedModified) * par2) * 6.0f) * 32.0f * var20;
            if (par1EntityPlayer.isSneaking()) {
                var17 += 25.0f;
            }
            GL11.glRotatef(6.0f + var18 / 2.0f + var17, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(var19 / 2.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(-var19 / 2.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
            this.modelBipedMain.renderCloak(0.0625f);
            GL11.glPopMatrix();
        }
        ItemStack var21 = par1EntityPlayer.inventory.getCurrentItem();
        if (var21 != null) {
            GL11.glPushMatrix();
            this.modelBipedMain.bipedRightArm.postRender(0.0625f);
            GL11.glTranslatef(-0.0625f, 0.4375f, 0.0625f);
            if (par1EntityPlayer.fishEntity != null) {
                var21 = new ItemStack(Item.stick);
            }
            EnumAction var22 = null;
            if (par1EntityPlayer.getItemInUseCount() > 0) {
                var22 = var21.getItemUseAction();
            }
            if (var21.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[var21.itemID].getRenderType())) {
                float var9 = 0.5f;
                GL11.glTranslatef(0.0f, 0.1875f, -0.3125f);
                var9 *= 0.75f;
                GL11.glRotatef(20.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
                GL11.glScalef(-var9, -var9, var9);
            }
            else if (var21.itemID == Item.bow.itemID) {
                final float var9 = 0.625f;
                GL11.glTranslatef(0.0f, 0.125f, 0.3125f);
                GL11.glRotatef(-20.0f, 0.0f, 1.0f, 0.0f);
                GL11.glScalef(var9, -var9, var9);
                GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            }
            else if (Item.itemsList[var21.itemID].isFull3D()) {
                final float var9 = 0.625f;
                if (Item.itemsList[var21.itemID].shouldRotateAroundWhenRendering()) {
                    GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
                    GL11.glTranslatef(0.0f, -0.125f, 0.0f);
                }
                if (par1EntityPlayer.getItemInUseCount() > 0 && var22 == EnumAction.block) {
                    GL11.glTranslatef(0.05f, 0.0f, -0.1f);
                    GL11.glRotatef(-50.0f, 0.0f, 1.0f, 0.0f);
                    GL11.glRotatef(-10.0f, 1.0f, 0.0f, 0.0f);
                    GL11.glRotatef(-60.0f, 0.0f, 0.0f, 1.0f);
                }
                GL11.glTranslatef(0.0f, 0.1875f, 0.0f);
                GL11.glScalef(var9, -var9, var9);
                GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            }
            else {
                final float var9 = 0.375f;
                GL11.glTranslatef(0.25f, 0.1875f, -0.1875f);
                GL11.glScalef(var9, var9, var9);
                GL11.glRotatef(60.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(20.0f, 0.0f, 0.0f, 1.0f);
            }
            if (var21.getItem().requiresMultipleRenderPasses()) {
                for (int var23 = 0; var23 <= 1; ++var23) {
                    final int var24 = var21.getItem().getColorFromItemStack(var21, var23);
                    final float var25 = (var24 >> 16 & 0xFF) / 255.0f;
                    final float var26 = (var24 >> 8 & 0xFF) / 255.0f;
                    final float var14 = (var24 & 0xFF) / 255.0f;
                    GL11.glColor4f(var25, var26, var14, 1.0f);
                    this.renderManager.itemRenderer.renderItem(par1EntityPlayer, var21, var23);
                }
            }
            else {
                final int var23 = var21.getItem().getColorFromItemStack(var21, 0);
                final float var10 = (var23 >> 16 & 0xFF) / 255.0f;
                final float var25 = (var23 >> 8 & 0xFF) / 255.0f;
                final float var26 = (var23 & 0xFF) / 255.0f;
                GL11.glColor4f(var10, var25, var26, 1.0f);
                this.renderManager.itemRenderer.renderItem(par1EntityPlayer, var21, 0);
            }
            GL11.glPopMatrix();
        }
    }
    
    protected void renderPlayerScale(final EntityPlayer par1EntityPlayer, final float par2) {
        final float var3 = 0.9375f;
        GL11.glScalef(var3, var3, var3);
    }
    
    protected void func_96450_a(final EntityPlayer par1EntityPlayer, final double par2, double par4, final double par6, final String par8Str, final float par9, final double par10) {
        if (par10 < 100.0) {
            final Scoreboard var12 = par1EntityPlayer.getWorldScoreboard();
            final ScoreObjective var13 = var12.func_96539_a(2);
            if (var13 != null) {
                final Score var14 = var12.func_96529_a(par1EntityPlayer.getEntityName(), var13);
                if (par1EntityPlayer.isPlayerSleeping()) {
                    this.renderLivingLabel(par1EntityPlayer, String.valueOf(var14.func_96652_c()) + " " + var13.getDisplayName(), par2, par4 - 1.5, par6, 64);
                }
                else {
                    this.renderLivingLabel(par1EntityPlayer, String.valueOf(var14.func_96652_c()) + " " + var13.getDisplayName(), par2, par4, par6, 64);
                }
                par4 += this.getFontRendererFromRenderManager().FONT_HEIGHT * 1.15f * par9;
            }
        }
        super.func_96449_a(par1EntityPlayer, par2, par4, par6, par8Str, par9, par10);
    }
    
    public void renderFirstPersonArm(final EntityPlayer par1EntityPlayer) {
        final float var2 = 1.0f;
        GL11.glColor3f(var2, var2, var2);
        this.modelBipedMain.onGround = 0.0f;
        this.modelBipedMain.setRotationAngles(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f, par1EntityPlayer);
        this.modelBipedMain.bipedRightArm.render(0.0625f);
    }
    
    protected void renderPlayerSleep(final EntityPlayer par1EntityPlayer, final double par2, final double par4, final double par6) {
        if (par1EntityPlayer.isEntityAlive() && par1EntityPlayer.isPlayerSleeping()) {
            super.renderLivingAt(par1EntityPlayer, par2 + par1EntityPlayer.field_71079_bU, par4 + par1EntityPlayer.field_71082_cx, par6 + par1EntityPlayer.field_71089_bV);
        }
        else {
            super.renderLivingAt(par1EntityPlayer, par2, par4, par6);
        }
    }
    
    protected void rotatePlayer(final EntityPlayer par1EntityPlayer, final float par2, final float par3, final float par4) {
        if (par1EntityPlayer.isEntityAlive() && par1EntityPlayer.isPlayerSleeping()) {
            GL11.glRotatef(par1EntityPlayer.getBedOrientationInDegrees(), 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(this.getDeathMaxRotation(par1EntityPlayer), 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(270.0f, 0.0f, 1.0f, 0.0f);
        }
        else {
            super.rotateCorpse(par1EntityPlayer, par2, par3, par4);
        }
    }
    
    @Override
    protected void func_96449_a(final EntityLiving par1EntityLiving, final double par2, final double par4, final double par6, final String par8Str, final float par9, final double par10) {
        this.func_96450_a((EntityPlayer)par1EntityLiving, par2, par4, par6, par8Str, par9, par10);
    }
    
    @Override
    protected void preRenderCallback(final EntityLiving par1EntityLiving, final float par2) {
        this.renderPlayerScale((EntityPlayer)par1EntityLiving, par2);
    }
    
    @Override
    protected void func_82408_c(final EntityLiving par1EntityLiving, final int par2, final float par3) {
        this.func_82439_b((EntityPlayer)par1EntityLiving, par2, par3);
    }
    
    @Override
    protected int shouldRenderPass(final EntityLiving par1EntityLiving, final int par2, final float par3) {
        return this.setArmorModel((EntityPlayer)par1EntityLiving, par2, par3);
    }
    
    @Override
    protected void renderEquippedItems(final EntityLiving par1EntityLiving, final float par2) {
        this.renderSpecials((EntityPlayer)par1EntityLiving, par2);
    }
    
    @Override
    protected void rotateCorpse(final EntityLiving par1EntityLiving, final float par2, final float par3, final float par4) {
        this.rotatePlayer((EntityPlayer)par1EntityLiving, par2, par3, par4);
    }
    
    @Override
    protected void renderLivingAt(final EntityLiving par1EntityLiving, final double par2, final double par4, final double par6) {
        this.renderPlayerSleep((EntityPlayer)par1EntityLiving, par2, par4, par6);
    }
    
    @Override
    protected void func_98190_a(final EntityLiving par1EntityLiving) {
        this.func_98191_a((EntityPlayer)par1EntityLiving);
    }
    
    @Override
    public void doRenderLiving(final EntityLiving par1EntityLiving, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderPlayer((EntityPlayer)par1EntityLiving, par2, par4, par6, par8, par9);
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderPlayer((EntityPlayer)par1Entity, par2, par4, par6, par8, par9);
    }
}
