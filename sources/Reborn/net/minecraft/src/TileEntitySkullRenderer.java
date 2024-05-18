package net.minecraft.src;

import org.lwjgl.opengl.*;

public class TileEntitySkullRenderer extends TileEntitySpecialRenderer
{
    public static TileEntitySkullRenderer skullRenderer;
    private ModelSkeletonHead field_82396_c;
    private ModelSkeletonHead field_82395_d;
    
    public TileEntitySkullRenderer() {
        this.field_82396_c = new ModelSkeletonHead(0, 0, 64, 32);
        this.field_82395_d = new ModelSkeletonHead(0, 0, 64, 64);
    }
    
    public void renderTileEntitySkullAt(final TileEntitySkull par1TileEntitySkull, final double par2, final double par4, final double par6, final float par8) {
        this.func_82393_a((float)par2, (float)par4, (float)par6, par1TileEntitySkull.getBlockMetadata() & 0x7, par1TileEntitySkull.func_82119_b() * 360 / 16.0f, par1TileEntitySkull.getSkullType(), par1TileEntitySkull.getExtraType());
    }
    
    @Override
    public void setTileEntityRenderer(final TileEntityRenderer par1TileEntityRenderer) {
        super.setTileEntityRenderer(par1TileEntityRenderer);
        TileEntitySkullRenderer.skullRenderer = this;
    }
    
    public void func_82393_a(final float par1, final float par2, final float par3, final int par4, float par5, final int par6, final String par7Str) {
        ModelSkeletonHead var8 = this.field_82396_c;
        switch (par6) {
            default: {
                this.bindTextureByName("/mob/skeleton.png");
                break;
            }
            case 1: {
                this.bindTextureByName("/mob/skeleton_wither.png");
                break;
            }
            case 2: {
                this.bindTextureByName("/mob/zombie.png");
                var8 = this.field_82395_d;
                break;
            }
            case 3: {
                if (par7Str != null && par7Str.length() > 0) {
                    final String var9 = "http://mcdata0.craftlandia.com.br/mcskins/" + StringUtils.stripControlCodes(par7Str) + ".png";
                    if (!TileEntitySkullRenderer.skullRenderer.tileEntityRenderer.renderEngine.hasImageData(var9)) {
                        TileEntitySkullRenderer.skullRenderer.tileEntityRenderer.renderEngine.obtainImageData(var9, new ImageBufferDownload());
                    }
                    this.bindTextureByURL(var9, "/mob/char.png");
                    break;
                }
                this.bindTextureByName("/mob/char.png");
                break;
            }
            case 4: {
                this.bindTextureByName("/mob/creeper.png");
                break;
            }
        }
        GL11.glPushMatrix();
        GL11.glDisable(2884);
        if (par4 != 1) {
            switch (par4) {
                case 2: {
                    GL11.glTranslatef(par1 + 0.5f, par2 + 0.25f, par3 + 0.74f);
                    break;
                }
                case 3: {
                    GL11.glTranslatef(par1 + 0.5f, par2 + 0.25f, par3 + 0.26f);
                    par5 = 180.0f;
                    break;
                }
                case 4: {
                    GL11.glTranslatef(par1 + 0.74f, par2 + 0.25f, par3 + 0.5f);
                    par5 = 270.0f;
                    break;
                }
                default: {
                    GL11.glTranslatef(par1 + 0.26f, par2 + 0.25f, par3 + 0.5f);
                    par5 = 90.0f;
                    break;
                }
            }
        }
        else {
            GL11.glTranslatef(par1 + 0.5f, par2, par3 + 0.5f);
        }
        final float var10 = 0.0625f;
        GL11.glEnable(32826);
        GL11.glScalef(-1.0f, -1.0f, 1.0f);
        GL11.glEnable(3008);
        var8.render(null, 0.0f, 0.0f, 0.0f, par5, 0.0f, var10);
        GL11.glPopMatrix();
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity par1TileEntity, final double par2, final double par4, final double par6, final float par8) {
        this.renderTileEntitySkullAt((TileEntitySkull)par1TileEntity, par2, par4, par6, par8);
    }
}
