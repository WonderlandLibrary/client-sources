package Focus.Beta.API.GUI.skeet;

import Focus.Beta.API.GUI.skeet.comp.Component;
import Focus.Beta.IMPL.font.CFontRenderer;
import Focus.Beta.IMPL.font.FontLoaders;
import Focus.Beta.UTILS.Math.MathUtils;
import Focus.Beta.UTILS.render.LockedResolution;
import Focus.Beta.UTILS.render.RenderUtil;
import Focus.Beta.UTILS.world.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class SkeetUI extends GuiScreen {
    public static final int GROUP_BOX_MARGIN = 8;
    public static final int GROUP_BOX_LEFT_MARGIN = 3;
    public static final CFontRenderer ICONS_RENDERER;
    public static final CFontRenderer FONT_RENDERER;
    public static final CFontRenderer KEYBIND_FONT_RENDERER;
    public static final int ENABLE_BUTTON_Y_OFFSET = 6;
    public static final int ENABLE_BUTTON_Y_GAP = 4;
    private static final int WIDTH = 370;
    private static final int HEIGHT = 350;
    private static final float TOTAL_BORDER_WIDTH = 3.5F;
    private static final float RAINBOW_BAR_WIDTH = 1.5F;
    private static final int TAB_SELECTOR_WIDTH = 48;
    public static final float USABLE_AREA_WIDTH = 315.0F;
    public boolean closed;
    public static final float GROUP_BOX_WIDTH = 94.333336F;
    public static final float HALF_GROUP_BOX = 40.166668F;
    private static final ResourceLocation BACKGROUND_IMAGE = new ResourceLocation("/assets/skeetchainmail.png", 0);
    private static final char[] ICONS = new char[] { 'A', 'G', 'F', 'C', 'J', 'E', 'I', 'H' };
    private static final float USABLE_AREA_HEIGHT = 341.5F;
    private static final int TAB_SELECTOR_HEIGHT = 321 / ICONS.length; private static double alpha; private static boolean open;
    private final Component rootComponent;
    private double targetAlpha;

    static {
        ICONS_RENDERER = FontLoaders.NovICON38;

        FONT_RENDERER = FontLoaders.Tahoma12;

        KEYBIND_FONT_RENDERER = FontLoaders.Tahoma9;
    }
    private boolean dragging; private float prevX; private float prevY;

    @Override
    public void initGui() {

        alpha = 0.0D;
        this.targetAlpha = 255.0D;
        open = true;
        this.closed = false;super.initGui();

    }

    public SkeetUI(){

        alpha = 0.0D;
         this.targetAlpha = 255.0D;
        open = true;
       this.closed = false;
        this.rootComponent = new Component(null, 0.0F, 0.0F, 370.0f, 350.0f){
            public void drawComponent(LockedResolution lockedResolution, int mouseX, int mouseY){
                if(SkeetUI.this.dragging){
                    setX(Math.max(0, Math.min(lockedResolution.getWidth() - getWidth(), mouseX - SkeetUI.this.prevX)));
                    setY(Math.max(0, Math.min(lockedResolution.getWidth() - getWidth(), mouseY - SkeetUI.this.prevY)));
                }
                float borderX = getX();
                float borderY = getY();
                float width = getWidth();
                float height = getHeight();
                Gui.drawRect(borderX, borderY, borderX + width, borderY + height, SkeetUI.getColor(1052942));
                Gui.drawRect(borderX + 0.5F, borderY + 0.5F, borderX + width - 0.5F, borderY + height - 0.5F, SkeetUI.getColor(3619386));
                Gui.drawRect(borderX + 1.0F, borderY + 1.0F, borderX + width - 1.0F, borderY + height - 1.0F, SkeetUI.getColor(2302755));
                Gui.drawRect(borderX + 3.0F, borderY + 3.0F, borderX + width - 3.0F, borderY + height - 3.0F, SkeetUI.getColor(3092271));
                float left = borderX + 3.5F;
                float top = borderY + 3.5F;
                float right = borderX + width - 3.5F;
                float bottom = borderY + height - 3.5F;

                Gui.drawRect(left, top, right, bottom, SkeetUI.getColor(1381653));
                if (SkeetUI.alpha > 20.0D) {
                            GL11.glEnable(3089);
                               RenderUtil.startScissorBox(lockedResolution, (int)left, (int)top, (int)(right - left), (int)(bottom - top));
                    RenderUtil.drawImage(left, top, 325.0F, 275.0F, 1.0F, 1.0F, 1.0F, SkeetUI.BACKGROUND_IMAGE);
                    RenderUtil.drawImage(left + 325.0F, top + 1.0F, 325.0F, 275.0F, 1.0F, 1.0F, 1.0F, SkeetUI.BACKGROUND_IMAGE);
                    RenderUtil.drawImage(left + 1.0F, top + 275.0F, 325.0F, 275.0F, 1.0F, 1.0F, 1.0F, SkeetUI.BACKGROUND_IMAGE);
                    RenderUtil.drawImage(left + 326.0F, top + 276.0F, 325.0F, 275.0F, 1.0F, 1.0F, 1.0F, SkeetUI.BACKGROUND_IMAGE);
                                 GL11.glDisable(3089);
                               }
            }
        };
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if(mouseButton == 0){
            SkeetUI.this.dragging = true;
            SkeetUI.this.prevX = mouseX - rootComponent.getX();
            SkeetUI.this.prevY = mouseY - rootComponent.getY();
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        dragging = false;
        super.mouseReleased(mouseX, mouseY, state);
    }

    public void drawComponent(LockedResolution resolution, int mouseX, int mouseY) {

    }
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
            alpha = linearAnimation(alpha, this.targetAlpha, 10.0D);
            this.rootComponent.drawComponent(RenderUtil.getLockedResolution(), mouseX, mouseY);

    }


    private static boolean isVisible() {
        return (open || alpha > 0.0D);
    }

    public static int getColor(int color) {
        int r = color >> 16 & 0xFF;
       int g = color >> 8 & 0xFF;
       int b = color & 0xFF;
       int a = (int)alpha;

        return (r & 0xFF) << 16 | (
                    g & 0xFF) << 8 |
                    b & 0xFF | (
                       a & 0xFF) << 24;
    }

    public static double linearAnimation(double now, double desired, double speed) {
        double dif = Math.abs(now - desired);
        int fps = Minecraft.getDebugFPS();
        if (dif > 0.0D) {
            double animationSpeed = MathUtils.roundToDecimalPlace(Math.min(10.0D,
                    Math.max(0.005D, 144.0D / fps * speed)), 0.005D);
            if (dif != 0.0D && dif < animationSpeed)
                animationSpeed = dif;
            if (now < desired)
                return now + animationSpeed;
            if (now > desired)
                return now - animationSpeed;
        }
        return now;
    }
}
