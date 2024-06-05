package net.minecraft.src;

import org.lwjgl.input.*;
import org.lwjgl.opengl.*;

public class GuiEditSign extends GuiScreen
{
    private static final String allowedCharacters;
    protected String screenTitle;
    private TileEntitySign entitySign;
    private int updateCounter;
    private int editLine;
    private GuiButton doneBtn;
    
    static {
        allowedCharacters = ChatAllowedCharacters.allowedCharacters;
    }
    
    public GuiEditSign(final TileEntitySign par1TileEntitySign) {
        this.screenTitle = "Edit sign message:";
        this.editLine = 0;
        this.entitySign = par1TileEntitySign;
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        this.buttonList.add(this.doneBtn = new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120, "Done"));
        this.entitySign.setEditable(false);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        final NetClientHandler var1 = this.mc.getNetHandler();
        if (var1 != null) {
            var1.addToSendQueue(new Packet130UpdateSign(this.entitySign.xCoord, this.entitySign.yCoord, this.entitySign.zCoord, this.entitySign.signText));
        }
        this.entitySign.setEditable(true);
    }
    
    @Override
    public void updateScreen() {
        ++this.updateCounter;
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled && par1GuiButton.id == 0) {
            this.entitySign.onInventoryChanged();
            this.mc.displayGuiScreen(null);
        }
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) {
        if (par2 == 200) {
            this.editLine = (this.editLine - 1 & 0x3);
        }
        if (par2 == 208 || par2 == 28) {
            this.editLine = (this.editLine + 1 & 0x3);
        }
        if (par2 == 14 && this.entitySign.signText[this.editLine].length() > 0) {
            this.entitySign.signText[this.editLine] = this.entitySign.signText[this.editLine].substring(0, this.entitySign.signText[this.editLine].length() - 1);
        }
        if (GuiEditSign.allowedCharacters.indexOf(par1) >= 0 && this.entitySign.signText[this.editLine].length() < 15) {
            this.entitySign.signText[this.editLine] = String.valueOf(this.entitySign.signText[this.editLine]) + par1;
        }
        if (par2 == 1) {
            this.actionPerformed(this.doneBtn);
        }
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 40, 16777215);
        GL11.glPushMatrix();
        GL11.glTranslatef(this.width / 2, 0.0f, 50.0f);
        final float var4 = 93.75f;
        GL11.glScalef(-var4, -var4, -var4);
        GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
        final Block var5 = this.entitySign.getBlockType();
        if (var5 == Block.signPost) {
            final float var6 = this.entitySign.getBlockMetadata() * 360 / 16.0f;
            GL11.glRotatef(var6, 0.0f, 1.0f, 0.0f);
            GL11.glTranslatef(0.0f, -1.0625f, 0.0f);
        }
        else {
            final int var7 = this.entitySign.getBlockMetadata();
            float var8 = 0.0f;
            if (var7 == 2) {
                var8 = 180.0f;
            }
            if (var7 == 4) {
                var8 = 90.0f;
            }
            if (var7 == 5) {
                var8 = -90.0f;
            }
            GL11.glRotatef(var8, 0.0f, 1.0f, 0.0f);
            GL11.glTranslatef(0.0f, -1.0625f, 0.0f);
        }
        if (this.updateCounter / 6 % 2 == 0) {
            this.entitySign.lineBeingEdited = this.editLine;
        }
        TileEntityRenderer.instance.renderTileEntityAt(this.entitySign, -0.5, -0.75, -0.5, 0.0f);
        this.entitySign.lineBeingEdited = -1;
        GL11.glPopMatrix();
        super.drawScreen(par1, par2, par3);
    }
}
