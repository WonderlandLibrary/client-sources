 package me.vene.skilled.gui.parts;
 
 import java.awt.Color;
 import java.util.ArrayList;
 import java.util.Collections;
 import java.util.List;
 import me.vene.skilled.SkilledClient;
 import me.vene.skilled.command.Command;
 import me.vene.skilled.command.CommandManager;
 import me.vene.skilled.gui.component.Component;
 import me.vene.skilled.gui.component.Frame;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.gui.Gui;
 import org.lwjgl.opengl.GL11;
 
  public class ConsolePart extends Component
{
    public Frame parent;
    public int offset;
    private boolean clicked;
    private String textful;
    
    public ConsolePart(final Frame parent, final int offset) {
        this.textful = "";
        this.parent = parent;
        this.offset = offset;
        this.clicked = false;
    }
    
    @Override
    public void render() {
        Gui.drawRect(this.parent.getX() + 1, this.parent.getY() - 2 + this.offset, this.parent.getX() + this.parent.getWidth() - 2, this.parent.getY() + 12 + this.offset, new Color(10, 10, 10, 230).getRGB());
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.textful, (float)((this.parent.getX() + 5) * 2), (float)((this.parent.getY() + this.offset + 4) * 2 - 2), -1);
        GL11.glPopMatrix();
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        this.clicked = (this.isMouseOnButton(mouseX, mouseY) && button == 0);
    }
    
    @Override
    public void keyTyped(final char typedChar, final int key) {
        if (this.clicked) {
            if (key != 28) {
                if (key == 14) {
                    if (this.textful.equalsIgnoreCase("")) {
                        return;
                    }
                    if (this.textful.length() - 1 >= 0) {
                        this.textful = this.textful.substring(0, this.textful.length() - 1);
                        return;
                    }
                }
                if (this.textful.length() < 34) {
                    this.textful += typedChar;
                    this.textful = this.textful.replaceAll("\\P{Print}", "");
                }
            }
            else {
                final List<String> splitList = new ArrayList<String>();
                Collections.addAll(splitList, this.textful.split(" "));
                final String commandName = splitList.get(0);
                for (final Class<? extends Command> commandClass : CommandManager.COMMANDS) {
                    final Command command = SkilledClient.getInstance().getCommandManager().getCommand(commandClass);
                    if (command.getCommandName().equalsIgnoreCase(commandName)) {
                        splitList.remove(0);
                        command.execute(SkilledClient.getInstance(), splitList.toArray(new String[0]));
                        break;
                    }
                }
                this.textful = "";
                splitList.clear();
            }
        }
    }
    
    private boolean isMouseOnButton(final int x, final int y) {
        return x > this.parent.getX() && x < this.parent.getX() + this.parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 14 + this.offset;
    }
}
