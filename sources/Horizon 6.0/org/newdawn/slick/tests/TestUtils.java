package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.SoundStore;
import org.lwjgl.input.Keyboard;
import java.io.File;
import HORIZON-6-0-SKIDPROTECTION.AudioLoader;
import java.io.IOException;
import java.io.InputStream;
import HORIZON-6-0-SKIDPROTECTION.TextureLoader;
import java.io.FileInputStream;
import HORIZON-6-0-SKIDPROTECTION.TrueTypeFont;
import HORIZON-6-0-SKIDPROTECTION.Log;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import HORIZON-6-0-SKIDPROTECTION.Font;
import HORIZON-6-0-SKIDPROTECTION.Audio;
import HORIZON-6-0-SKIDPROTECTION.Texture;

public class TestUtils
{
    private Texture HorizonCode_Horizon_È;
    private Audio Â;
    private Audio Ý;
    private Audio Ø­áŒŠá;
    private Audio Âµá€;
    private Audio Ó;
    private Font à;
    
    public void HorizonCode_Horizon_È() {
        this.HorizonCode_Horizon_È(800, 600);
        this.Â();
        while (true) {
            this.Ý();
            GL11.glClear(16384);
            this.Ø­áŒŠá();
            Display.update();
            Display.sync(100);
            if (Display.isCloseRequested()) {
                System.exit(0);
            }
        }
    }
    
    private void HorizonCode_Horizon_È(final int width, final int height) {
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();
            Display.setVSyncEnabled(true);
        }
        catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        GL11.glEnable(3553);
        GL11.glShadeModel(7425);
        GL11.glDisable(2929);
        GL11.glDisable(2896);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClearDepth(1.0);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glViewport(0, 0, width, height);
        GL11.glMatrixMode(5888);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, (double)width, (double)height, 0.0, 1.0, -1.0);
        GL11.glMatrixMode(5888);
    }
    
    public void Â() {
        Log.HorizonCode_Horizon_È(false);
        final java.awt.Font awtFont = new java.awt.Font("Times New Roman", 1, 16);
        this.à = new TrueTypeFont(awtFont, false);
        try {
            this.HorizonCode_Horizon_È = TextureLoader.HorizonCode_Horizon_È("PNG", new FileInputStream("testdata/rocks.png"));
            System.out.println("Texture loaded: " + this.HorizonCode_Horizon_È);
            System.out.println(">> Image width: " + this.HorizonCode_Horizon_È.Ó());
            System.out.println(">> Image height: " + this.HorizonCode_Horizon_È.Ó());
            System.out.println(">> Texture width: " + this.HorizonCode_Horizon_È.áˆºÑ¢Õ());
            System.out.println(">> Texture height: " + this.HorizonCode_Horizon_È.à());
            System.out.println(">> Texture ID: " + this.HorizonCode_Horizon_È.Ø());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.Â = AudioLoader.HorizonCode_Horizon_È("OGG", new FileInputStream("testdata/restart.ogg"));
            this.Âµá€ = AudioLoader.HorizonCode_Horizon_È("OGG", new File("testdata/bongos.ogg").toURL());
            (this.Ó = AudioLoader.HorizonCode_Horizon_È("MOD", new File("testdata/SMB-X.XM").toURL())).Â(1.0f, 1.0f, true);
            this.Ø­áŒŠá = AudioLoader.HorizonCode_Horizon_È("AIF", new FileInputStream("testdata/burp.aif"));
            this.Ý = AudioLoader.HorizonCode_Horizon_È("WAV", new FileInputStream("testdata/cbrown01.wav"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void Ý() {
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (Keyboard.getEventKey() == 16) {
                    this.Â.HorizonCode_Horizon_È(1.0f, 1.0f, false);
                }
                if (Keyboard.getEventKey() == 17) {
                    this.Âµá€.Â(1.0f, 1.0f, true);
                }
                if (Keyboard.getEventKey() == 18) {
                    this.Ó.Â(1.0f, 1.0f, true);
                }
                if (Keyboard.getEventKey() == 19) {
                    this.Ø­áŒŠá.HorizonCode_Horizon_È(1.0f, 1.0f, false);
                }
                if (Keyboard.getEventKey() != 20) {
                    continue;
                }
                this.Ý.HorizonCode_Horizon_È(1.0f, 1.0f, false);
            }
        }
        SoundStore.Å().Âµá€(0);
    }
    
    public void Ø­áŒŠá() {
        Color.Ý.HorizonCode_Horizon_È();
        this.HorizonCode_Horizon_È.Ý();
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex2f(100.0f, 100.0f);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex2f((float)(100 + this.HorizonCode_Horizon_È.áˆºÑ¢Õ()), 100.0f);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex2f((float)(100 + this.HorizonCode_Horizon_È.áˆºÑ¢Õ()), (float)(100 + this.HorizonCode_Horizon_È.à()));
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex2f(100.0f, (float)(100 + this.HorizonCode_Horizon_È.à()));
        GL11.glEnd();
        this.à.HorizonCode_Horizon_È(150.0f, 300.0f, "HELLO LWJGL WORLD", Color.Ø­áŒŠá);
    }
    
    public static void main(final String[] argv) {
        final TestUtils utils = new TestUtils();
        utils.HorizonCode_Horizon_È();
    }
}
