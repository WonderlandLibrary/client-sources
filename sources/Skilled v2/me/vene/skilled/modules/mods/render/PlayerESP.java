package me.vene.skilled.modules.mods.render;
 
 import java.lang.reflect.Field;
 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.modules.mods.combat.AntiBot;
 import me.vene.skilled.modules.mods.other.ClickGUI;
 import me.vene.skilled.utilities.RenderUtil;
 import me.vene.skilled.utilities.StringRegistry;
 import me.vene.skilled.values.BooleanValue;
 import me.vene.skilled.values.NumberValue;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.entity.EntityOtherPlayerMP;
 import net.minecraft.client.renderer.Tessellator;
 import net.minecraft.client.renderer.WorldRenderer;
 import net.minecraft.client.renderer.entity.RenderManager;
 import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
 import net.minecraft.entity.player.EntityPlayer;
 import net.minecraft.util.AxisAlignedBB;
 import net.minecraft.util.MathHelper;
 import net.minecraftforge.client.event.RenderWorldLastEvent;
 import org.lwjgl.opengl.GL11;
 
public class PlayerESP extends Module
{
    private Minecraft mc;
    private NumberValue redful;
    private NumberValue greenful;
    private NumberValue blueful;
    private BooleanValue showInvis;
    
    public PlayerESP() {
        super(StringRegistry.register("Player ESP"), 0, Category.R);
        this.mc = Minecraft.getMinecraft();
        this.addOption(this.showInvis = new BooleanValue("Show Invisible", true));
        this.addValue(this.redful = new NumberValue(StringRegistry.register(new String(new char[] { 'R', 'e', 'd' })), 35.0, 1.0, 255.0));
        this.addValue(this.greenful = new NumberValue(StringRegistry.register(new String(new char[] { 'G', 'r', 'e', 'e', 'n' })), 245.0, 1.0, 255.0));
        this.addValue(this.blueful = new NumberValue(StringRegistry.register(new String(new char[] { 'B', 'l', 'u', 'e' })), 15.0, 1.0, 255.0));
    }
    
    @Override
    public void onRenderEvent(final RenderWorldLastEvent event) {
        for (final Object o : this.mc.theWorld.loadedEntityList) {
            if (!(o instanceof EntityOtherPlayerMP)) {
                continue;
            }
            final EntityOtherPlayerMP player = (EntityOtherPlayerMP)o;
            if (AntiBot.bots.contains(player)) {
                return;
            }
            if (!this.showInvis.getState() && player.isInvisible()) {
                return;
            }
            final double d = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.partialTicks;
            final double d2 = player.lastTickPosY + (player.posY - player.lastTickPosY) * event.partialTicks;
            final double d3 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.partialTicks;
            final String Xstring = new String(new char[] { 'f', 'i', 'e', 'l', 'd', '_', '7', '8', '7', '2', '5', '_', 'b' });
            final String Ystring = new String(new char[] { 'f', 'i', 'e', 'l', 'd', '_', '7', '8', '7', '2', '6', '_', 'c' });
            final String Zstring = new String(new char[] { 'f', 'i', 'e', 'l', 'd', '_', '7', '8', '7', '2', '3', '_', 'd' });
            final double renderPosX = GetFieldByReflection(RenderManager.class, this.mc.getRenderManager(), StringRegistry.register(Xstring));
            final double renderPosY = GetFieldByReflection(RenderManager.class, this.mc.getRenderManager(), StringRegistry.register(Ystring));
            final double renderPosZ = GetFieldByReflection(RenderManager.class, this.mc.getRenderManager(), StringRegistry.register(Zstring));
            if (!(player instanceof EntityPlayer)) {
                continue;
            }
            this.drawPlayerESP(d - renderPosX, d2 - renderPosY, d3 - renderPosZ, player.rotationYaw, 1.0f, 0.0f, 0.0f);
        }
    }
    
    private void drawOutlinedBoundingBox(final AxisAlignedBB aa) {
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(3, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldrenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldrenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        worldrenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldrenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        tessellator.draw();
        worldrenderer.begin(3, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldrenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        worldrenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldrenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        worldrenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        tessellator.draw();
        worldrenderer.begin(1, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldrenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldrenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldrenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        worldrenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        worldrenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldrenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldrenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        tessellator.draw();
    }
    
    private void drawPlayerESP(final double d, final double d1, final double d2, final float angleToRotate, final float r, final float g, final float b) {
        final float x = System.currentTimeMillis() % 2000L / 1000.0f;
        final float red = 0.5f + 0.5f * MathHelper.sin(x * 3.1415927f);
        final float green = 0.5f + 0.5f * MathHelper.sin((x + 1.3333334f) * 3.1415927f);
        final float blue = 0.5f + 0.5f * MathHelper.sin((x + 2.6666667f) * 3.1415927f);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glDepthMask(false);
        GL11.glEnable(2848);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2848);
        RenderUtil.hexESP(ClickGUI.rgb((int)this.redful.getValue(), (int)this.greenful.getValue(), (int)this.blueful.getValue()));
        this.drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1 + 0.1, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.2f);
        GL11.glDepthMask(true);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
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
