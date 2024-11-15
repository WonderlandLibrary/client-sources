package exhibition;

import java.util.concurrent.Callable;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;

public class CustomEntityRenderer extends EntityRenderer {

    /** Previous frame time in milliseconds */
    private long prevFrameTime = Minecraft.getSystemTime();
    /** End time of last render (ns) */
    private long renderEndNanoTime;
    /** Smooth cam yaw */
    private float smoothCamYaw;
    /** Smooth cam pitch */
    private float smoothCamPitch;
    /** Smooth cam filter X */
    private float smoothCamFilterX;
    /** Smooth cam filter Y */
    private float smoothCamFilterY;
    /** Smooth cam partial ticks */
    private float smoothCamPartialTicks;
    private ShaderGroup theShaderGroup;
    private boolean useShader;
	public CustomEntityRenderer(Minecraft mcIn, IResourceManager resourceManagerIn) {
		super(mcIn, resourceManagerIn);
	}

	@Override
    public void updateCameraAndRender(float p_181560_1_, long p_181560_2_)
    {
        boolean flag = Display.isActive();

        if (!flag && Wrapper.getMinecraft().gameSettings.pauseOnLostFocus && (!Wrapper.getMinecraft().gameSettings.touchscreen || !Mouse.isButtonDown(1)))
        {
            if (Minecraft.getSystemTime() - this.prevFrameTime > 500L)
            {
                Wrapper.getMinecraft().displayInGameMenu();
            }
        }
        else
        {
            this.prevFrameTime = Minecraft.getSystemTime();
        }

        Wrapper.getMinecraft().mcProfiler.startSection("mouse");

        if (flag && Minecraft.isRunningOnMac && Wrapper.getMinecraft().inGameHasFocus && !Mouse.isInsideWindow())
        {
            Mouse.setGrabbed(false);
            Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
            Mouse.setGrabbed(true);
        }

        if (Wrapper.getMinecraft().inGameHasFocus && flag)
        {
            Wrapper.getMinecraft().mouseHelper.mouseXYChange();
            float f = Wrapper.getMinecraft().gameSettings.mouseSensitivity * 0.6F + 0.2F;
            float f1 = f * f * f * 8.0F;
            float f2 = (float)Wrapper.getMinecraft().mouseHelper.deltaX * f1;
            float f3 = (float)Wrapper.getMinecraft().mouseHelper.deltaY * f1;
            int i = 1;

            if (Wrapper.getMinecraft().gameSettings.invertMouse)
            {
                i = -1;
            }

            if (Wrapper.getMinecraft().gameSettings.smoothCamera)
            {
                this.smoothCamYaw += f2;
                this.smoothCamPitch += f3;
                float f4 = p_181560_1_ - this.smoothCamPartialTicks;
                this.smoothCamPartialTicks = p_181560_1_;
                f2 = this.smoothCamFilterX * f4;
                f3 = this.smoothCamFilterY * f4;
                Wrapper.getMinecraft().thePlayer.setAngles(f2, f3 * (float)i);
            }
            else
            {
                this.smoothCamYaw = 0.0F;
                this.smoothCamPitch = 0.0F;
                Wrapper.getMinecraft().thePlayer.setAngles(f2, f3 * (float)i);
            }
        }

        Wrapper.getMinecraft().mcProfiler.endSection();

        if (!Wrapper.getMinecraft().skipRenderWorld)
        {
            anaglyphEnable = Wrapper.getMinecraft().gameSettings.anaglyph;
            final ScaledResolution scaledresolution = new ScaledResolution(Wrapper.getMinecraft());
            int i1 = scaledresolution.getScaledWidth();
            int j1 = scaledresolution.getScaledHeight();
            final int k1 = Mouse.getX() * i1 / Wrapper.getMinecraft().displayWidth;
            final int l1 = j1 - Mouse.getY() * j1 / Wrapper.getMinecraft().displayHeight - 1;
            int i2 = Wrapper.getMinecraft().gameSettings.limitFramerate;

            if (Wrapper.getMinecraft().theWorld != null)
            {
                Wrapper.getMinecraft().mcProfiler.startSection("level");
                int j = Math.min(Minecraft.getDebugFPS(), i2);
                j = Math.max(j, 60);
                long k = System.nanoTime() - p_181560_2_;
                long l = Math.max((long)(1000000000 / j / 4) - k, 0L);
                this.renderWorld(p_181560_1_, System.nanoTime() + l);

                if (OpenGlHelper.shadersSupported)
                {
                    Wrapper.getMinecraft().renderGlobal.renderEntityOutlineFramebuffer();

                    if (this.theShaderGroup != null && this.useShader)
                    {
                        GlStateManager.matrixMode(5890);
                        GlStateManager.pushMatrix();
                        GlStateManager.loadIdentity();
                        this.theShaderGroup.loadShaderGroup(p_181560_1_);
                        GlStateManager.popMatrix();
                    }

                    Wrapper.getMinecraft().getFramebuffer().bindFramebuffer(true);
                }

                this.renderEndNanoTime = System.nanoTime();
                Wrapper.getMinecraft().mcProfiler.endStartSection("gui");

                if (!Wrapper.getMinecraft().gameSettings.hideGUI || Wrapper.getMinecraft().currentScreen != null)
                {
                    GlStateManager.alphaFunc(516, 0.1F);
                    Wrapper.getMinecraft().ingameGUI.renderGameOverlay(p_181560_1_);
                }

                Wrapper.getMinecraft().mcProfiler.endSection();
            }
            else
            {
                GlStateManager.viewport(0, 0, Wrapper.getMinecraft().displayWidth, Wrapper.getMinecraft().displayHeight);
                GlStateManager.matrixMode(5889);
                GlStateManager.loadIdentity();
                GlStateManager.matrixMode(5888);
                GlStateManager.loadIdentity();
                this.setupOverlayRendering();
                this.renderEndNanoTime = System.nanoTime();
            }

            if (Wrapper.getMinecraft().currentScreen != null)
            {
                GlStateManager.clear(256);

                try
                {
                    net.minecraftforge.client.ForgeHooksClient.drawScreen(Wrapper.getMinecraft().currentScreen, k1, l1, p_181560_1_);
                }
                catch (Throwable throwable)
                {
                    CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering screen");
                    CrashReportCategory crashreportcategory = crashreport.makeCategory("Screen render details");
                    crashreportcategory.addCrashSectionCallable("Screen name", new Callable<String>()
                    {
                        public String call() throws Exception
                        {
                            return Wrapper.getMinecraft().currentScreen.getClass().getCanonicalName();
                        }
                    });
                    crashreportcategory.addCrashSectionCallable("Mouse location", new Callable<String>()
                    {
                        public String call() throws Exception
                        {
                            return String.format("Scaled: (%d, %d). Absolute: (%d, %d)", new Object[] {Integer.valueOf(k1), Integer.valueOf(l1), Integer.valueOf(Mouse.getX()), Integer.valueOf(Mouse.getY())});
                        }
                    });
                    crashreportcategory.addCrashSectionCallable("Screen size", new Callable<String>()
                    {
                        public String call() throws Exception
                        {
                            return String.format("Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %d", new Object[] {Integer.valueOf(scaledresolution.getScaledWidth()), Integer.valueOf(scaledresolution.getScaledHeight()), Integer.valueOf(Wrapper.getMinecraft().displayWidth), Integer.valueOf(Wrapper.getMinecraft().displayHeight), Integer.valueOf(scaledresolution.getScaleFactor())});
                        }
                    });
                    throw new ReportedException(crashreport);
                }
            }
            //Client.getClickGui().drawMenu(j1, k1, p_181560_1_);
        }
    }
}
