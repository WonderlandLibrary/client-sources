 package me.vene.skilled.modules.mods.render;
 
 import java.lang.reflect.Field;
 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.modules.mods.combat.AntiBot;
 import me.vene.skilled.utilities.StringRegistry;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.entity.EntityPlayerSP;
 import net.minecraft.client.renderer.GlStateManager;
 import net.minecraft.client.renderer.entity.RenderManager;
 import net.minecraft.entity.player.EntityPlayer;
 import net.minecraftforge.client.event.RenderWorldLastEvent;
 import org.lwjgl.opengl.GL11;
 
public class Tracers extends Module
{
    private boolean bobbing;
    private Minecraft mc;
    
    public Tracers() {
        super("Tracers", 0, Category.R);
        this.mc = Minecraft.getMinecraft();
    }
    
    @Override
    public void onEnable() {
        this.bobbing = this.mc.gameSettings.viewBobbing;
        this.mc.gameSettings.viewBobbing = false;
    }
    
    @Override
    public void onDisable() {
        this.mc.gameSettings.viewBobbing = this.bobbing;
    }
    
    @Override
    public void onRenderEvent(final RenderWorldLastEvent event) {
        if (this.mc.gameSettings.viewBobbing) {
            this.mc.gameSettings.viewBobbing = false;
        }
        final EntityPlayerSP player = this.mc.thePlayer;
        GL11.glPushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GL11.glEnable(2848);
        GlStateManager.blendFunc(770, 771);
        GL11.glLineWidth(1.0f);
        for (final EntityPlayer p : this.mc.theWorld.playerEntities) {
            if (AntiBot.bots.contains(p)) {
                return;
            }
            if (p.equals((Object)player)) {
                continue;
            }
            if (p.isDead) {
                continue;
            }
            final String Xstring = new String(new char[] { 'f', 'i', 'e', 'l', 'd', '_', '7', '8', '7', '2', '5', '_', 'b' });
            final String Ystring = new String(new char[] { 'f', 'i', 'e', 'l', 'd', '_', '7', '8', '7', '2', '6', '_', 'c' });
            final String Zstring = new String(new char[] { 'f', 'i', 'e', 'l', 'd', '_', '7', '8', '7', '2', '3', '_', 'd' });
            final double renderPosX = GetFieldByReflection(RenderManager.class, this.mc.getRenderManager(), StringRegistry.register(Xstring));
            final double renderPosY = GetFieldByReflection(RenderManager.class, this.mc.getRenderManager(), StringRegistry.register(Ystring));
            final double renderPosZ = GetFieldByReflection(RenderManager.class, this.mc.getRenderManager(), StringRegistry.register(Zstring));
            final double x = p.lastTickPosX + (p.posX - p.lastTickPosX) * event.partialTicks - renderPosX;
            final double y = p.lastTickPosY + (p.posY - p.lastTickPosY) * event.partialTicks - renderPosY + 1.0;
            final double z = p.lastTickPosZ + (p.posZ - p.lastTickPosZ) * event.partialTicks - renderPosZ;
            GlStateManager.color(1.0f, 0.0f, 0.0f, 1.0f);
            GL11.glBegin(1);
            GL11.glVertex3d(0.0, (double)player.getEyeHeight(), 0.0);
            GL11.glVertex3d(x, y, z);
            GL11.glEnd();
        }
        GlStateManager.disableBlend();
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GL11.glDisable(2848);
        GL11.glLineWidth(1.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    public static <T, E> T GetFieldByReflection(final Class<? super E> classToAccess, final E instance, final String... fieldNames) {
        Field field = null;
        for (final String fieldName : fieldNames) {
            try {
                field = classToAccess.getDeclaredField(fieldName);
            }
            catch (NoSuchFieldException ex) {}
            if (field != null) {
                break;
            }
        }
        if (field != null) {
            field.setAccessible(true);
            T fieldT = null;
            try {
                fieldT = (T)field.get(instance);
            }
            catch (IllegalArgumentException ex2) {}
            catch (IllegalAccessException ex3) {}
            return fieldT;
        }
        return null;
    }
}
