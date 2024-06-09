package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Queue;

@ModInfo(Ø­áŒŠá = Category.SERVER, Ý = 0, Â = "Work in Progress.", HorizonCode_Horizon_È = "Teleport")
public class Teleport extends Mod
{
    protected final Queue<Packet> Ý;
    
    public Teleport() {
        this.Ý = new ConcurrentLinkedQueue<Packet>();
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventRender3D e) {
        if (this.Â.áŒŠà.HorizonCode_Horizon_È == MovingObjectPosition.HorizonCode_Horizon_È.Â) {
            final BlockPos var2 = this.Â.áŒŠà.HorizonCode_Horizon_È();
            final Block b = this.Â.áŒŠÆ.Â(var2).Ý();
            GL11.glDisable(2896);
            GL11.glDisable(3553);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(2929);
            GL11.glEnable(2848);
            GL11.glDepthMask(false);
            GL11.glLineWidth(0.75f);
            if (Horizon.Âµá€.equalsIgnoreCase("blue")) {
                GL11.glColor4f(0.3f, 1.0f, 1.0f, 0.6f);
            }
            else if (Horizon.Âµá€.equalsIgnoreCase("red")) {
                GL11.glColor4f(1.0f, 0.4f, 0.3f, 0.6f);
            }
            else if (Horizon.Âµá€.equalsIgnoreCase("green")) {
                GL11.glColor4f(0.3f, 1.0f, 0.4f, 0.6f);
            }
            else if (Horizon.Âµá€.equalsIgnoreCase("rainbow")) {
                GL11.glColor4f(FADE.HorizonCode_Horizon_È, FADE.Â, FADE.Ý, 0.6f);
            }
            double var3 = var2.HorizonCode_Horizon_È();
            this.Â.ÇªÓ();
            final double var4 = var3 - RenderManager.HorizonCode_Horizon_È;
            var3 = var2.Â();
            this.Â.ÇªÓ();
            final double y = var3 - RenderManager.Â;
            var3 = var2.Ý();
            this.Â.ÇªÓ();
            final double z = var3 - RenderManager.Ý;
            final double xo = 1.0;
            final double yo = 1.0;
            final double zo = 1.0;
            RenderHelper_1118140819.Ø­áŒŠá(new AxisAlignedBB(var4, y, z, var4 + xo, y + yo, z + zo));
            if (Horizon.Âµá€.equalsIgnoreCase("blue")) {
                GL11.glColor4f(0.3f, 1.0f, 1.0f, 0.2f);
            }
            else if (Horizon.Âµá€.equalsIgnoreCase("red")) {
                GL11.glColor4f(1.0f, 0.4f, 0.3f, 0.2f);
            }
            else if (Horizon.Âµá€.equalsIgnoreCase("green")) {
                GL11.glColor4f(0.3f, 1.0f, 0.4f, 0.2f);
            }
            else if (Horizon.Âµá€.equalsIgnoreCase("rainbow")) {
                GL11.glColor4f(FADE.HorizonCode_Horizon_È, FADE.Â, FADE.Ý, 0.2f);
            }
            RenderHelper_1118140819.Â(new AxisAlignedBB(var4, y, z, var4 + xo, y + yo, z + zo));
            GL11.glDepthMask(true);
            GL11.glDisable(2848);
            GL11.glEnable(2929);
            GL11.glDisable(3042);
            GL11.glEnable(2896);
            GL11.glEnable(3553);
        }
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventUpdate e) {
        if (e.Ý() == EventUpdate.HorizonCode_Horizon_È.HorizonCode_Horizon_È && Mouse.getEventButtonState() && Mouse.isButtonDown(1) && this.Â.á.Çªà¢() && this.Â.áŒŠà.HorizonCode_Horizon_È == MovingObjectPosition.HorizonCode_Horizon_È.Â) {
            final BlockPos var2 = this.Â.áŒŠà.HorizonCode_Horizon_È();
            TeleportUtil.Â(var2.HorizonCode_Horizon_È(), var2.Â() + 1, var2.Ý());
        }
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventPacketSend event) {
        this.Â.á.Çªà¢();
    }
}
