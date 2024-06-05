package net.minecraft.src;

public class ChestItemRenderHelper
{
    public static ChestItemRenderHelper instance;
    private TileEntityChest theChest;
    private TileEntityEnderChest theEnderChest;
    
    static {
        ChestItemRenderHelper.instance = new ChestItemRenderHelper();
    }
    
    public ChestItemRenderHelper() {
        this.theChest = new TileEntityChest();
        this.theEnderChest = new TileEntityEnderChest();
    }
    
    public void renderChest(final Block par1Block, final int par2, final float par3) {
        if (par1Block.blockID == Block.enderChest.blockID) {
            TileEntityRenderer.instance.renderTileEntityAt(this.theEnderChest, 0.0, 0.0, 0.0, 0.0f);
        }
        else {
            TileEntityRenderer.instance.renderTileEntityAt(this.theChest, 0.0, 0.0, 0.0, 0.0f);
        }
    }
}
