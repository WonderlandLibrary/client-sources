 package me.vene.skilled.gui;
 
 import java.util.ArrayList;
 import java.util.Iterator;
 import javax.vecmath.Vector2f;
 import javax.vecmath.Vector4f;
 import me.vene.skilled.SkilledClient;
 import me.vene.skilled.gui.component.Component;
 import me.vene.skilled.gui.component.Frame;
 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.modules.mods.main.CombatGUI;
 import me.vene.skilled.modules.mods.main.OtherGUI;
 import me.vene.skilled.modules.mods.main.PlayerGUI;
 import me.vene.skilled.modules.mods.main.RenderGUI;
 import me.vene.skilled.modules.mods.main.UtilityGUI;
 import me.vene.skilled.modules.mods.other.ClickGUI;
 import me.vene.skilled.modules.mods.utility.Array;
 import me.vene.skilled.modules.mods.utility.InfoTab;
 import me.vene.skilled.utilities.RenderUtil;
 import me.vene.skilled.utilities.TextUtil;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.gui.GuiScreen;
 import net.minecraft.util.ResourceLocation;
 import org.lwjgl.opengl.GL11;

  public class GUI extends GuiScreen
     {
    private ArrayList<Frame> frames;
    public static int arrayXPos;
    public static int arrayYPos;
    public static int mouseX;
    public static int mouseY;
    public static boolean renderShit;
    public static boolean renderInfo;
    public static int infoXPos;
    public static int infoYPos;
    public static Frame frameful;
    private Frame frameYes;
    private Frame consoleYes;
    private boolean firstOpen;
    private boolean firstYes;
    private boolean firstFul;
    private boolean firstMain;
    private boolean firstFriend;
    private boolean firstRender;
    private boolean firstUtil;
    
    public GUI() {
        this.frames = new ArrayList<Frame>();
        this.frameYes = null;
        this.consoleYes = null;
        this.firstOpen = false;
        this.firstYes = false;
        this.firstFul = false;
        this.firstMain = false;
        this.firstFriend = false;
        this.firstRender = false;
        this.firstUtil = false;
        int frameX = 5;
        for (final Category category : Category.values()) {
            final Frame frame = new Frame(category);
            frame.setX(frameX);
            this.frames.add(frame);
            frameX += frame.getWidth() + 1;
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final TextUtil font = SkilledClient.getInstance().bebas_font;
        this.drawDefaultBackground();
        final String brian = new String(new char[] { 'S', 'k', 'i', 'l', 'l', 'e', 'd', ' ', 'C', 'l', 'i', 'e', 'n', 't', ' ', 'v', '3', ' ', 'B', 'U', 'I', 'L', 'D', ' ', 'S', 'n', 'a', 'k', 'e' });
        GL11.glPushMatrix();
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        GL11.glPopMatrix();
        GUI.mouseX = mouseX;
        GUI.mouseY = mouseY;
        for (final Frame frame : this.frames) {
            frame.updatePosition(mouseX, mouseY);
            for (final Component comp : frame.getComponents()) {
                comp.updateComponent(mouseX, mouseY);
            }
            if (frame.category.getID() == 7) {
                if (!this.firstMain) {
                    frame.setOpen(this.firstMain = true);
                }
                (this.frameYes = frame).renderFrame();
                RenderUtil.drawTexturedRectangle(new ResourceLocation("1341faxdvf3"), frame.getX() + frame.getWidth() / 2 - 40, frame.getY() - 2, 80.0f, 17.0f, ClickGUI.getColor());
            }
            if (frame.category.getID() == 0 && Module.getModule(CombatGUI.class).getState()) {
                frame.renderFrame();
                RenderUtil.drawTexturedRectangle(new ResourceLocation("5hajfah432"), frame.getX() + 1, frame.getY() - 2, 22.0f, 14.0f, ClickGUI.getColor());
            }
            if (frame.category.getID() == 2 && Module.getModule(OtherGUI.class).getState()) {
                frame.renderFrame();
            }
            if (frame.category.getID() == 3 && Module.getModule(PlayerGUI.class).getState()) {
                frame.renderFrame();
                RenderUtil.drawTexturedRectangle(new ResourceLocation("7wjiu482ab"), frame.getX() + 1, frame.getY() - 2, 14.0f, 14.0f, ClickGUI.getColor());
            }
            if (frame.category.getID() == 4) {
                if (!this.firstRender) {
                    this.firstRender = true;
                    frame.setX(this.frameYes.getX() + 358);
                    frame.setY(this.frameYes.getY() + this.frameYes.getHeight() + 60);
                }
                if (Module.getModule(RenderGUI.class).getState()) {
                    frame.renderFrame();
                    RenderUtil.drawTexturedRectangle(new ResourceLocation("3fkahj23314"), frame.getX() + 1, frame.getY() - 2, 18.0f, 14.0f, ClickGUI.getColor());
                }
            }
            if (frame.category.getID() == 5) {
                if (!this.firstUtil) {
                    this.firstUtil = true;
                    frame.setX(this.frameYes.getX() + 358);
                    frame.setY(this.frameYes.getY() + this.frameYes.getHeight() + 160);
                }
                if (Module.getModule(UtilityGUI.class).getState()) {
                    frame.renderFrame();
                    RenderUtil.drawTexturedRectangle(new ResourceLocation("19t8u984d"), frame.getX() + 1, frame.getY() - 2, 14.0f, 14.0f, ClickGUI.getColor());
                }
            }
            if (frame.category.getID() == 1) {
                if (!this.firstYes) {
                    this.firstYes = true;
                    frame.setX(this.frameYes.getX());
                    frame.setY(this.frameYes.getY() + this.frameYes.getHeight() + 190);
                    frame.setOpen(true);
                }
                GUI.arrayXPos = frame.getX();
                GUI.arrayYPos = frame.getY();
                GUI.renderShit = frame.isOpen();
                if (GUI.renderShit && !Module.getModule(Array.class).getState()) {
                    GL11.glPushMatrix();
                    Minecraft.getMinecraft().fontRendererObj.drawString("Arraylist is disabled!", GUI.arrayXPos + 2, GUI.arrayYPos + 16, -1);
                    GL11.glPopMatrix();
                }
                frame.renderFrame();
            }
            if (frame.category.getID() == 6) {
                if (!this.firstOpen) {
                    this.firstOpen = true;
                    frame.setX(this.frameYes.getX());
                    frame.setY(this.frameYes.getY() + this.frameYes.getHeight() + 120);
                }
                if (Module.getModule(InfoTab.class).getState()) {
                    GUI.infoXPos = frame.getX();
                    GUI.infoYPos = frame.getY();
                    GUI.renderInfo = frame.isOpen();
                    GUI.frameful = frame;
                }
                frame.renderFrame();
            }
            if (frame.category.getID() == 8) {
                if (!this.firstFul) {
                    this.firstFul = true;
                    (this.consoleYes = frame).setOpen(true);
                    frame.setX(this.frameYes.getX());
                    frame.setY(this.frameYes.getY() + this.frameYes.getHeight() + 80);
                }
                frame.renderFrame();
            }
            if (frame.category.getID() == 9) {
                if (!this.firstFriend) {
                    frame.setOpen(this.firstFriend = true);
                    frame.setX(this.consoleYes.getX() + this.consoleYes.getWidth() + 5);
                    frame.setY(this.consoleYes.getY());
                }
                if (frame.isOpen()) {
                    GL11.glPushMatrix();
                    if (SkilledClient.getInstance().getFriendManager().getFriendsList().isEmpty()) {
                        Minecraft.getMinecraft().fontRendererObj.drawString("You dont have friends!", frame.getX() + 2, frame.getY() + 15, -1);
                    }
                    final Iterator<String> i = SkilledClient.getInstance().getFriendManager().getFriendsList().iterator();
                    int y = 18;
                    while (i.hasNext()) {
                        final String friend = i.next();
                        Minecraft.getMinecraft().fontRendererObj.drawString(friend, frame.getX() + 2, frame.getY() + y, -1);
                        y += 10;
                    }
                    GL11.glPopMatrix();
                }
                frame.renderFrame();
            }
        }
        font.renderCenteredText("Test", new Vector2f((float)(this.width / 2), (float)(this.height / 2)), new Vector4f(255.0f, 255.0f, 0.0f, 0.0f), 20.0);
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        for (final Frame frame : this.frames) {
            if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
                frame.setDrag(true);
                frame.dragX = mouseX - frame.getX();
                frame.dragY = mouseY - frame.getY();
            }
            if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
                frame.setOpen(!frame.isOpen());
            }
            if (frame.isOpen()) {
                if (frame.getComponents().isEmpty()) {
                    continue;
                }
                for (final Component component : frame.getComponents()) {
                    component.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
        }
    }
    
    protected void keyTyped(final char typedChar, final int keyCode) {
        for (final Frame frame : this.frames) {
            if (frame.isOpen() && keyCode != 1) {
                if (frame.getComponents().isEmpty()) {
                    continue;
                }
                for (final Component component : frame.getComponents()) {
                    component.keyTyped(typedChar, keyCode);
                }
            }
        }
        if (keyCode == 1) {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
    }
    
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        for (final Frame frame : this.frames) {
            frame.setDrag(false);
        }
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    static {
        GUI.arrayXPos = 0;
        GUI.arrayYPos = 0;
        GUI.renderShit = false;
        GUI.renderInfo = false;
        GUI.infoXPos = 0;
        GUI.infoYPos = 0;
        GUI.frameful = null;
    }
}
