package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import org.lwjgl.opengl.GL11;

@ModInfo(Ø­áŒŠá = Category.DISPLAY, Ý = -1671646, Â = "Dat ESP doe...", HorizonCode_Horizon_È = "ESP")
public class ESP extends Mod
{
    public static boolean Ý;
    byte Ø­áŒŠá;
    public static double Âµá€;
    public static double Ó;
    public static double à;
    public static double Ø;
    public static double áŒŠÆ;
    public static double áˆºÑ¢Õ;
    TileEntity ÂµÈ;
    Entity á;
    
    static {
        ESP.Ý = false;
    }
    
    public ESP() {
        this.Ø­áŒŠá = 0;
    }
    
    @Handler
    private void HorizonCode_Horizon_È(final EventRender3D event) {
        for (final Object entity : this.Â.áŒŠÆ.Â) {
            if (entity instanceof EntityItem) {
                final EntityItem item = (EntityItem)entity;
                ESP.áˆºÑ¢Õ = item.ŒÏ - RenderManager.HorizonCode_Horizon_È;
                ESP.áŒŠÆ = item.Çªà¢ - RenderManager.Â;
                ESP.Ø = item.Ê - RenderManager.Ý;
                final Box dbox = new Box(ESP.áˆºÑ¢Õ - 0.3, ESP.áŒŠÆ + 0.03, ESP.Ø - 0.3, ESP.áˆºÑ¢Õ + 0.3, ESP.áŒŠÆ + 0.7, ESP.Ø + 0.3);
                if (((EntityItem)entity).v_().contains("dia") || ((EntityItem)entity).v_().contains("Dia") || ((EntityItem)entity).v_().contains("iron") || ((EntityItem)entity).v_().contains("Iron") || ((EntityItem)entity).v_().contains("eisen") || ((EntityItem)entity).v_().contains("Eisen")) {
                    int color = ColorUtil.HorizonCode_Horizon_È(200000000L, 1.0f).getRGB();
                    color = ColorUtil.HorizonCode_Horizon_È(color, 0.4);
                    OGLManager.Ø­áŒŠá(color);
                    OGLManager.Ý(dbox);
                    color = ColorUtil.HorizonCode_Horizon_È(color, 0.8);
                    OGLManager.HorizonCode_Horizon_È(dbox);
                }
                else {
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.8f);
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.4f);
                    OGLManager.Ý(dbox);
                    OGLManager.HorizonCode_Horizon_È(dbox);
                }
            }
        }
        final FontRenderer var12 = Render.Ý();
        final Iterator var13 = Minecraft.áŒŠà().áŒŠÆ.Ø­áŒŠá.iterator();
        while (var13.hasNext()) {
            this.ÂµÈ = var13.next();
            if (this.HorizonCode_Horizon_È(this.ÂµÈ)) {
                ESP.à = this.ÂµÈ.á().HorizonCode_Horizon_È() - RenderManager.HorizonCode_Horizon_È;
                ESP.Ó = this.ÂµÈ.á().Â() - RenderManager.Â;
                ESP.Âµá€ = this.ÂµÈ.á().Ý() - RenderManager.Ý;
                Box box = new Box(ESP.à, ESP.Ó, ESP.Âµá€, ESP.à + 1.0, ESP.Ó + 1.0, ESP.Âµá€ + 1.0);
                if (this.ÂµÈ instanceof TileEntityChest) {
                    final TileEntityChest chest = TileEntityChest.class.cast(this.ÂµÈ);
                    if (chest.áŒŠÆ != null) {
                        box = new Box(ESP.à + 0.0625, ESP.Ó, ESP.Âµá€ + 0.0625, ESP.à + 0.9375, ESP.Ó + 0.875, ESP.Âµá€ + 1.9375);
                    }
                    else if (chest.à != null) {
                        box = new Box(ESP.à + 0.0625, ESP.Ó, ESP.Âµá€ + 0.0625, ESP.à + 1.9375, ESP.Ó + 0.875, ESP.Âµá€ + 0.9375);
                    }
                    else {
                        if (chest.áŒŠÆ != null || chest.à != null || chest.Ó != null) {
                            continue;
                        }
                        if (chest.Ø != null) {
                            continue;
                        }
                        if (Horizon.Âµá€.equalsIgnoreCase("rainbow")) {
                            int color = ColorUtil.HorizonCode_Horizon_È(200000000L, 1.0f).getRGB();
                            box = new Box(ESP.à + 0.0625, ESP.Ó, ESP.Âµá€ + 0.0625, ESP.à + 0.9375, ESP.Ó + 0.875, ESP.Âµá€ + 0.9375);
                            color = ColorUtil.HorizonCode_Horizon_È(color, 0.3);
                            OGLManager.Ø­áŒŠá(color);
                            OGLManager.Ý(box);
                            color = ColorUtil.HorizonCode_Horizon_È(color, 0.8);
                            OGLManager.HorizonCode_Horizon_È(box);
                        }
                        else {
                            box = new Box(ESP.à + 0.0625, ESP.Ó, ESP.Âµá€ + 0.0625, ESP.à + 0.9375, ESP.Ó + 0.875, ESP.Âµá€ + 0.9375);
                            GL11.glColor4f(0.0f, 1.0f, 0.0f, 0.6f);
                            GL11.glColor4f(0.0f, 1.0f, 0.0f, 0.2f);
                            OGLManager.Ý(box);
                            OGLManager.HorizonCode_Horizon_È(box);
                        }
                    }
                }
                else if (this.ÂµÈ instanceof TileEntityEnderChest) {
                    if (Horizon.Âµá€.equalsIgnoreCase("rainbow")) {
                        int color2 = ColorUtil.HorizonCode_Horizon_È(200000000L, 1.0f).getRGB();
                        box = new Box(ESP.à + 0.0625, ESP.Ó, ESP.Âµá€ + 0.0625, ESP.à + 0.9375, ESP.Ó + 0.875, ESP.Âµá€ + 0.9375);
                        color2 = ColorUtil.HorizonCode_Horizon_È(color2, 0.3);
                        OGLManager.Ø­áŒŠá(color2);
                        OGLManager.Ý(box);
                        color2 = ColorUtil.HorizonCode_Horizon_È(color2, 0.8);
                        OGLManager.HorizonCode_Horizon_È(box);
                    }
                    else {
                        box = new Box(ESP.à + 0.0625, ESP.Ó, ESP.Âµá€ + 0.0625, ESP.à + 0.9375, ESP.Ó + 0.875, ESP.Âµá€ + 0.9375);
                        GL11.glColor4f(1.0f, 0.0f, 0.0f, 0.6f);
                        GL11.glColor4f(1.0f, 0.0f, 0.0f, 0.2f);
                        OGLManager.Ý(box);
                        OGLManager.HorizonCode_Horizon_È(box);
                    }
                }
                else if (this.ÂµÈ instanceof TileEntityBrewingStand) {
                    box = new Box(ESP.à + 0.0625, ESP.Ó, ESP.Âµá€ + 0.0625, ESP.à + 0.9375, ESP.Ó + 0.875, ESP.Âµá€ + 0.9375);
                    final FontRenderer fr = this.Â.µà;
                    GL11.glColor4f(1.0f, 0.5f, 0.0f, 0.6f);
                    GL11.glColor4f(1.0f, 0.5f, 0.0f, 0.2f);
                    OGLManager.Ý(box);
                    OGLManager.HorizonCode_Horizon_È(box);
                }
                else {
                    if (!(this.ÂµÈ instanceof TileEntityEnchantmentTable)) {
                        continue;
                    }
                    box = new Box(ESP.à + 0.0, ESP.Ó, ESP.Âµá€ + 0.0, ESP.à + 1.0, ESP.Ó + 0.754, ESP.Âµá€ + 1.0);
                    final FontRenderer fr = this.Â.µà;
                    GL11.glColor4f(0.7f, 0.0f, 0.5f, 0.6f);
                    GL11.glColor4f(0.7f, 0.0f, 0.5f, 0.2f);
                    OGLManager.Ý(box);
                    OGLManager.HorizonCode_Horizon_È(box);
                }
            }
        }
    }
    
    private final boolean HorizonCode_Horizon_È(final TileEntity tileEntity) {
        return tileEntity instanceof TileEntityEnderChest || tileEntity instanceof TileEntityChest || tileEntity instanceof TileEntityBrewingStand || tileEntity instanceof TileEntityEnchantmentTable;
    }
}
