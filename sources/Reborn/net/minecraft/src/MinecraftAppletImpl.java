package net.minecraft.src;

import net.minecraft.client.*;
import java.awt.*;
import org.lwjgl.*;

public class MinecraftAppletImpl extends Minecraft
{
    final MinecraftApplet mainFrame;
    
    public MinecraftAppletImpl(final MinecraftApplet par1MinecraftApplet, final Canvas par2Canvas, final MinecraftApplet par3MinecraftApplet, final int par4, final int par5, final boolean par6) {
        super(par2Canvas, par3MinecraftApplet, par4, par5, par6);
        this.mainFrame = par1MinecraftApplet;
    }
    
    @Override
    public void displayCrashReportInternal(final CrashReport par1CrashReport) {
        this.mainFrame.removeAll();
        this.mainFrame.setLayout(new BorderLayout());
        this.mainFrame.add(new PanelCrashReport(par1CrashReport), "Center");
        this.mainFrame.validate();
    }
    
    @Override
    public void startGame() throws LWJGLException {
        this.mcDataDir = Minecraft.getMinecraftDir();
        this.gameSettings = new GameSettings(this, this.mcDataDir);
        if (this.gameSettings.overrideHeight > 0 && this.gameSettings.overrideWidth > 0 && this.mainFrame.getParent() != null && this.mainFrame.getParent().getParent() != null) {
            this.mainFrame.getParent().getParent().setSize(this.gameSettings.overrideWidth, this.gameSettings.overrideHeight);
        }
        super.startGame();
    }
}
