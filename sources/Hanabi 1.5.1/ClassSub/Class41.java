package ClassSub;

import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import cn.Hanabi.modules.Player.*;
import cn.Hanabi.*;
import java.awt.*;
import cn.Hanabi.utils.fontmanager.*;
import java.util.*;
import org.lwjgl.input.*;
import java.io.*;
import org.lwjgl.opengl.*;

public class Class41
{
    public boolean isDragging;
    public float x;
    public float y;
    public int x2;
    public int y2;
    public boolean isExtended;
    public float rectYSize;
    public float animationY;
    private Class140 handler;
    public Minecraft mc;
    public ScaledResolution sr;
    private String fileDir;
    
    
    public Class41(final Minecraft mc, final ScaledResolution sr) {
        this.isDragging = false;
        this.x = 0.0f;
        this.y = 0.0f;
        this.isExtended = false;
        this.handler = new Class140(0);
        this.fileDir = mc.mcDataDir.getAbsolutePath() + "/" + "Hanabi";
        this.mc = mc;
        this.sr = sr;
        this.loadFile();
    }
    
    public void draw() {
        this.sr = new ScaledResolution(this.mc);
        final StaffAnalyzer staffAnalyzer = Hanabi.INSTANCE.moduleManager.getModule(StaffAnalyzer.class);
        if (!staffAnalyzer.onlinemod.isEmpty()) {
            if (this.isExtended) {
                float n = 13.0f;
                final Iterator<String> iterator = staffAnalyzer.onlinemod.iterator();
                while (iterator.hasNext()) {
                    n += Hanabi.INSTANCE.fontManager.wqy16.getStringHeight(iterator.next());
                }
                this.rectYSize = n + 2.0f;
            }
            else {
                this.rectYSize = 10.0f;
            }
        }
        else if (this.isExtended) {
            this.rectYSize = 25.0f;
        }
        else {
            this.rectYSize = 10.0f;
        }
        this.animationY = (float)Class246.getAnimationState(this.animationY, this.rectYSize, Math.max(10.0f, Math.abs(this.animationY - this.rectYSize) * 30.0f) * 0.3);
        this.drawRoundedRect(this.x, this.y + 10.0f, this.x + 80.0f, this.y + this.animationY, Class120.reAlpha(Class15.WHITE.c, 0.5f), Class120.reAlpha(Class15.WHITE.c, 0.5f));
        if (!staffAnalyzer.onlinemod.isEmpty()) {
            if (this.isExtended) {
                float y = this.y + 13.0f;
                for (final String s : staffAnalyzer.onlinemod) {
                    if (this.y + this.animationY > y) {
                        Hanabi.INSTANCE.fontManager.wqy16.drawString(s, this.x + 2.0f, y, Class15.GREY.c);
                    }
                    y += Hanabi.INSTANCE.fontManager.wqy16.getStringHeight(s);
                }
            }
            else if (!this.isExtended && this.animationY > 10.0f) {
                float y2 = this.y + 13.0f;
                for (final String s2 : staffAnalyzer.onlinemod) {
                    if (this.y + this.animationY > y2) {
                        Hanabi.INSTANCE.fontManager.wqy16.drawString(s2, this.x + 2.0f, y2, Class15.GREY.c);
                    }
                    y2 += Hanabi.INSTANCE.fontManager.wqy16.getStringHeight(s2);
                }
            }
        }
        else if (this.isExtended && this.animationY + this.y > this.y + 22.0f) {
            Hanabi.INSTANCE.fontManager.wqy16.drawString("当前没有管理员在�?", this.x + 2.0f, this.y + 14.0f, Class15.GREY.c);
        }
        final int rgb = new Color(-14848033).brighter().getRGB();
        this.drawRoundedRect(this.x, this.y, this.x + 80.0f, this.y + 13.0f, rgb, rgb);
        Hanabi.INSTANCE.fontManager.wqy16.drawString("在线管理 (" + staffAnalyzer.onlinemod.size() + "/" + StaffAnalyzer.modlist.length + ")", this.x + 2.0f, this.y + 2.0f, Class15.WHITE.c);
        Hanabi.INSTANCE.fontManager.icon16.drawString(this.isExtended ? HanabiFonts.ICON_CLICKGUI_ARROW_DOWN : HanabiFonts.ICON_CLICKGUI_ARROW_UP, this.x + 70.0f, this.y + 2.0f, Class15.WHITE.c);
    }
    
    public void mouseListener(final int n, final int n2) {
        this.moveWindow(n, n2);
        if (this.isHovering(n, n2, this.x + 70.0f, this.y + 2.0f, this.x + 78.0f, this.y + 12.0f) && this.handler.canExcecute()) {
            this.isExtended = !this.isExtended;
        }
    }
    
    public void moveWindow(final int n, final int n2) {
        if (this.isHovering(n, n2, this.x + 70.0f, this.y + 2.0f, this.x + 78.0f, this.y + 12.0f)) {
            return;
        }
        if (this.isHoveringWindow(n, n2) && this.handler.canExcecute()) {
            this.isDragging = true;
            this.x2 = (int)(n - this.x);
            this.y2 = (int)(n2 - this.y);
        }
        if (this.isDragging) {
            this.x = n - this.x2;
            this.y = n2 - this.y2;
        }
        if (!Mouse.isButtonDown(0) && this.isDragging) {
            this.isDragging = false;
            this.saveFile();
        }
    }
    
    public void loadFile() {
        final File file = new File(this.fileDir + "/modCheckerCoord.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String[] array = line.split(":");
                this.x = Float.valueOf(array[0]);
                this.y = Float.valueOf(array[1]);
            }
            bufferedReader.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Loaded ModChecker Coord File!");
    }
    
    public void saveFile() {
        final File file = new File(this.fileDir + "/modCheckerCoord.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            final PrintWriter printWriter = new PrintWriter(file);
            printWriter.write(String.valueOf(this.x) + ":" + String.valueOf(this.y));
            printWriter.close();
            System.out.println("Saved ModChecker Coords File!");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void drawRoundedRect(float n, float n2, float n3, float n4, final int n5, final int n6) {
        this.enableGL2D();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        this.drawVLine(n *= 2.0f, (n2 *= 2.0f) + 1.0f, (n4 *= 2.0f) - 2.0f, n5);
        this.drawVLine((n3 *= 2.0f) - 1.0f, n2 + 1.0f, n4 - 2.0f, n5);
        this.drawHLine(n + 2.0f, n3 - 3.0f, n2, n5);
        this.drawHLine(n + 2.0f, n3 - 3.0f, n4 - 1.0f, n5);
        this.drawHLine(n + 1.0f, n + 1.0f, n2 + 1.0f, n5);
        this.drawHLine(n3 - 2.0f, n3 - 2.0f, n2 + 1.0f, n5);
        this.drawHLine(n3 - 2.0f, n3 - 2.0f, n4 - 2.0f, n5);
        this.drawHLine(n + 1.0f, n + 1.0f, n4 - 2.0f, n5);
        Class246.drawRect(n + 1.0f, n2 + 1.0f, n3 - 1.0f, n4 - 1.0f, n6);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        this.disableGL2D();
    }
    
    public void drawHLine(float n, float n2, final float n3, final int n4) {
        if (n2 < n) {
            final float n5 = n;
            n = n2;
            n2 = n5;
        }
        Class246.drawRect(n, n3, n2 + 1.0f, n3 + 1.0f, n4);
    }
    
    public void drawVLine(final float n, float n2, float n3, final int n4) {
        if (n3 < n2) {
            final float n5 = n2;
            n2 = n3;
            n3 = n5;
        }
        Class246.drawRect(n, n2 + 1.0f, n + 1.0f, n3, n4);
    }
    
    public void color(final int n) {
        GL11.glColor4f((n >> 16 & 0xFF) / 255.0f, (n >> 8 & 0xFF) / 255.0f, (n & 0xFF) / 255.0f, (n >> 24 & 0xFF) / 255.0f);
    }
    
    public void enableGL2D() {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
    }
    
    public void disableGL2D() {
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }
    
    private boolean isHovering(final int n, final int n2, final float n3, final float n4, final float n5, final float n6) {
        return n > n3 && n < n5 && n2 > n4 && n2 < n6;
    }
    
    private boolean isHoveringWindow(final int n, final int n2) {
        return n >= this.x && n <= this.x + 80.0f && n2 >= this.y && n2 <= this.y + 10.0f;
    }
}
