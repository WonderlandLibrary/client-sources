package dev.client.tenacity.ui.sidegui;

import dev.client.tenacity.Tenacity;
import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.utils.font.FontUtil;
import dev.client.tenacity.utils.misc.HoveringUtil;
import dev.utils.misc.MathUtils;
import dev.client.tenacity.utils.misc.NetworkingUtils;
import dev.client.tenacity.utils.objects.Scroll;
import dev.client.tenacity.utils.render.ColorUtil;
import dev.client.tenacity.utils.render.RenderUtil;
import dev.client.tenacity.utils.render.RoundedUtil;
import dev.utils.render.StencilUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class ScriptPanel extends GuiPanel {

    private final Scroll scriptScroll = new Scroll();
    public float x, rawY, rectHeight, rectWidth;
    public boolean reInit;
    private List<ScriptRect> scriptRects;
    private Button scriptingDocs;
    private Button reloadScripts;
    private Button openFolder;

    @Override
    public void initGui() {
        if (scriptRects == null || reInit) {
            scriptRects = new ArrayList<>();
            for (Module module : Tenacity.INSTANCE.getModuleCollection().getModules()) {
                if (module.getCategory().equals(Category.SCRIPTS)) {
                    scriptRects.add(new ScriptRect(module));
                }
            }
            reInit = false;
        }

        scriptRects.forEach(ScriptRect::initGui);

        scriptingDocs = new Button("Scripting Docs");
        reloadScripts = new Button("Reload Scripts");
        openFolder = new Button("Open Folder");
        scriptingDocs.initGui();
        reloadScripts.initGui();
        openFolder.initGui();
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        scriptRects.forEach(scriptRect -> scriptRect.keyTyped(typedChar, keyCode));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks, int alpha) {
        boolean hoveringPanel = HoveringUtil.isHovering(x, rawY + 55, rectWidth, rectHeight - 55, mouseX, mouseY);
        if (hoveringPanel) scriptScroll.onScroll(150);
        float y = rawY + scriptScroll.getScroll();

        StencilUtil.initStencilToWrite();
        Gui.drawRect2(x, rawY + 51, rectWidth, rectHeight - 55, -1);
        StencilUtil.readStencilBuffer(1);

        RenderUtil.resetColor();
        mc.getTextureManager().bindTexture(new ResourceLocation("Tenacity/cedoscript.png"));
        GL11.glEnable(GL11.GL_BLEND);

        double thing = (MathUtils.roundToHalf(y) + 70);

        RoundedUtil.drawRoundTextured(x + 25, (float) thing, rectWidth - 50, 73.5f, 12, alpha / 255f);

        int textColor = ColorUtil.applyOpacity(-1, alpha / 255f);
        FontUtil.tenacityBoldFont40.drawCenteredString("cedoscript", x + rectWidth / 2f, y + 77, textColor);


        scriptingDocs.x = x + 25;
        scriptingDocs.y = y + 70 + 75 + 10;
        scriptingDocs.drawScreen(mouseX, mouseY, partialTicks, alpha);
        scriptingDocs.clickAction = () -> NetworkingUtils.openLink("https://scripting.tenacity.dev");

        reloadScripts.x = scriptingDocs.x + scriptingDocs.width + 10;
        reloadScripts.y = scriptingDocs.y;
        reloadScripts.drawScreen(mouseX, mouseY, partialTicks, alpha);
        reloadScripts.clickAction = () -> {
            //TODO: implemtnt scripting
            //Tenacity.INSTANCE.getScriptManager().reloadScripts(Tenacity.INSTANCE.moduleCollection.getModules());
            reInit = true;
        };
        if (reInit) return;

        openFolder.x = reloadScripts.x + reloadScripts.width + 10;
        openFolder.y = reloadScripts.y;
        openFolder.drawScreen(mouseX, mouseY, partialTicks, alpha);
        openFolder.clickAction = this::openScriptFolder;


        FontUtil.tenacityBoldFont32.drawCenteredString("Scripts", x + rectWidth / 2f, openFolder.y + 40, ColorUtil.applyOpacity(-1, alpha / 255f));

        float newY = openFolder.y + 40 + FontUtil.tenacityBoldFont32.getHeight();
        int count = 0;
        int seperation = 0;
        int seperationY = 0;
        for (ScriptRect scriptRect : scriptRects) {
            if (count != 0 && count % 3 == 0) {
                seperationY += 110 + 10;
                seperation = 0;
            }
            scriptRect.x = x + 25 + seperation;
            scriptRect.y = newY + 20 + seperationY;
            scriptRect.height = 110;
            scriptRect.width = 160;
            scriptRect.drawScreen(mouseX, mouseY, partialTicks, alpha);
            seperation += scriptRect.width + 10;
            count++;
        }


        StencilUtil.uninitStencilBuffer();
    }

    public void openScriptFolder() {
        /*try {
            Desktop.getDesktop().open(ScriptManager.scriptDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        scriptingDocs.mouseClicked(mouseX, mouseY, button);
        reloadScripts.mouseClicked(mouseX, mouseY, button);
        openFolder.mouseClicked(mouseX, mouseY, button);

        scriptRects.forEach(scriptRect -> scriptRect.mouseClicked(mouseX, mouseY, button));
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {

    }

}
