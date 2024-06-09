package lunadevs.luna.module.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import lunadevs.luna.category.Category;
import lunadevs.luna.main.Luna;
import lunadevs.luna.manage.ModuleManager;
import lunadevs.luna.module.Module;
import lunadevs.luna.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class CircleESP extends Module{

	public float partialTicks;
	public static boolean active;
    private double scale;
    private boolean armor;
    private Character formatChar = new Character('\247');
    public static Map<EntityLivingBase, double[]> entityPositions;
	
	public CircleESP() {
		super("CircleESP", 0, Category.RENDER, true);
	}
	
	private double distance = 100;
    private HashMap<Integer, Integer> colors = new HashMap<>();
    @Override
	public void onRender() {
		if(!this.isEnabled) return;
		colors.put(10,0xFF3c8c48);
        colors.put(9, 0xFF508c3c);
        colors.put(8, 0xFF688c3c);
        colors.put(7, 0xFF7e8c3c);
        colors.put(6, 0xFF8c853c);
        colors.put(5, 0xFF8c7d3c);
        colors.put(4, 0xFFbc7838);
        colors.put(3, 0xFFbc5f38);
        colors.put(2, 0xFFc1372a);
        colors.put(1, 0xFFd62020);
        colors.put(0, 0xFFff0019);
		GlStateManager.pushMatrix();
        ArrayList<Entity> fk = (ArrayList<Entity>) z.mc().theWorld.loadedEntityList;
        fk.sort(((o1, o2) -> {
            double dist2 = o2.getDistanceToEntity(z.p());
            double dist = o1.getDistanceToEntity(z.p());
            if (dist > dist2) {
                return -1;
            }
            if (dist < dist2) {
                return 1;
            }
            return 0;
        }));
        for (Object ent1 : fk) {
            Entity ent = (Entity) ent1;
            if (z.p().getDistanceToEntity(ent) > 100F) {
                continue;
            }
            if (ent == mc.thePlayer) {
                continue;
            }
            if (ent instanceof EntityPlayer && !ent.isInvisible()) {
                final double posX = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * mc.timer.renderPartialTicks - RenderManager.renderPosX;
                final double posY = ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * mc.timer.renderPartialTicks - RenderManager.renderPosY + ent.height + 0.5;
                final double posZ = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * mc.timer.renderPartialTicks - RenderManager.renderPosZ;
                String str = "";
                if (ent.isSneaking()) {
                    str = "" + "";

                }
                final float dist = z.mc().thePlayer.getDistanceToEntity(ent);
                float scale = 0.03f;
                final float factor = (float) ((dist <= 8.0f) ? (8.0 * 0.1) : (dist * 0.1));
                scale *= factor;
                GlStateManager.pushMatrix();
                GlStateManager.disableDepth();
                GlStateManager.translate(posX, posY, posZ);
                GL11.glNormal3f(0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(-z.mc().renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
                GlStateManager.pushMatrix();
                GlStateManager.scale(-scale, -scale, scale);
                RenderUtils.disableGL3D();
                GlStateManager.disableAlpha();
                GlStateManager.clear(256);
                GL11.glEnable(32823);
                GL11.glPolygonOffset(1.0f, -1100000.0f);
                Minecraft.getMinecraft().fontRendererObj.drawString(str,-Minecraft.getMinecraft().fontRendererObj.getStringWidth(str) / 2, -10, -1);
                GL11.glDisable(32823);
                GL11.glPolygonOffset(1.0f, 1100000.0f);
                GlStateManager.disableBlend();
                GlStateManager.enableAlpha();
                RenderUtils.enableGL3D(0.5f);
                GlStateManager.popMatrix();
                GlStateManager.scale(-0.03f, -0.03f, 0.03f);
                GlStateManager.disableLighting();
                GlStateManager.enableBlend();
                int color = 0xFF3c8c48;
                try {
                    color = colors.get((int) ((EntityPlayer) ent).getHealth() / 2);
                } catch (Exception e1) {}
                RenderUtils.DrawArc(0,44,40,0,(((EntityPlayer) ent).getHealth() / 3.1),100, 6, color);
                RenderUtils.DrawArc(0,44,39.5f,0,6.3,100, 1, -1);


                GlStateManager.func_179098_w();
                GlStateManager.disableLighting();
                GlStateManager.popMatrix();

            }
            GlStateManager.disableBlend();
        }
        GlStateManager.popMatrix();

}
    
    @Override
    public void onEnable(){
    	z.addChatMessageP("§7Keep in mind that CircleESP might cause Render Bugs §7while §7its §7on.");
    }
    
    private static void drawEnchantTag(String text, int x, int y)
    {
        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();
        x = (int)(x * 1.75D);
        GL11.glScalef(0.57F, 0.57F, 0.57F);
        y -= 4;
        Luna.fontRendererGUI.drawString(text, x, -36 - y, 64250);
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
    }
	public String getValue() {
		return "Beta";
	}
	
}
