/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.apache.commons.io.Charsets
 *  org.apache.commons.io.FileUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GLContext
 *  org.lwjgl.util.glu.Project
 */
package cc.diablo.render;

import cc.diablo.Main;
import cc.diablo.font.TTFFontRenderer;
import cc.diablo.helpers.DiscordRPCHelper;
import cc.diablo.helpers.render.ColorHelper;
import cc.diablo.helpers.render.RenderUtils;
import cc.diablo.login.GuiAltLogin;
import cc.diablo.login.GuiAuthentication;
import cc.diablo.render.Shader;
import cc.diablo.render.implementations.NewMenuShader;
import com.google.common.collect.Lists;
import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import optifine.Config;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.Project;

public class GuiMainMenu
extends GuiScreen
implements GuiYesNoCallback {
    public Shader shader;
    private static final AtomicInteger field_175373_f = new AtomicInteger(0);
    private static final Logger logger = LogManager.getLogger();
    private static final Random RANDOM = new Random();
    private final float updateCounter;
    private String splashText = "cope harder nn";
    private GuiButton buttonResetDemo;
    private int panoramaTimer;
    private DynamicTexture viewportTexture;
    private final boolean field_175375_v = true;
    private final Object threadLock = new Object();
    private String openGLWarning1;
    private String openGLWarning2;
    private String openGLWarningLink;
    private static final ResourceLocation splashTexts = new ResourceLocation("texts/splashes.txt");
    private static final ResourceLocation changeLogTexts = new ResourceLocation("/assets/minecraft/Client/changelog.txt");
    private static final ResourceLocation minecraftTitleTextures = new ResourceLocation("textures/gui/title/minecraft.png");
    private static final ResourceLocation[] titlePanoramaPaths = new ResourceLocation[]{new ResourceLocation("textures/gui/title/background/panorama_0.png"), new ResourceLocation("textures/gui/title/background/panorama_1.png"), new ResourceLocation("textures/gui/title/background/panorama_2.png"), new ResourceLocation("textures/gui/title/background/panorama_3.png"), new ResourceLocation("textures/gui/title/background/panorama_4.png"), new ResourceLocation("textures/gui/title/background/panorama_5.png")};
    public static final String field_96138_a = "Please click " + (Object)((Object)EnumChatFormatting.UNDERLINE) + "here" + (Object)((Object)EnumChatFormatting.RESET) + " for more information.";
    private int field_92024_r;
    private int field_92023_s;
    private int field_92022_t;
    private int field_92021_u;
    private int field_92020_v;
    private int field_92019_w;
    private ResourceLocation backgroundTexture;
    private GuiButton realmsButton;
    protected boolean authed = false;
    private GuiTextField uid;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public GuiMainMenu() {
        this.shader = new NewMenuShader(0);
        this.openGLWarning2 = field_96138_a;
        BufferedReader bufferedreader = null;
        try {
            String s;
            ArrayList list = Lists.newArrayList();
            bufferedreader = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(splashTexts).getInputStream(), Charsets.UTF_8));
            while ((s = bufferedreader.readLine()) != null) {
                if ((s = s.trim()).isEmpty()) continue;
                list.add(s);
            }
            if (!list.isEmpty()) {
                while (this.splashText.hashCode() == 125780783) {
                    this.splashText = (String)list.get(RANDOM.nextInt(list.size()));
                }
            }
        }
        catch (IOException iOException) {
        }
        finally {
            if (bufferedreader != null) {
                try {
                    bufferedreader.close();
                }
                catch (IOException iOException) {}
            }
        }
        this.updateCounter = RANDOM.nextFloat();
        this.openGLWarning1 = "";
        if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported()) {
            this.openGLWarning1 = I18n.format("title.oldgl1", new Object[0]);
            this.openGLWarning2 = I18n.format("title.oldgl2", new Object[0]);
            this.openGLWarningLink = "https://pornhub.com";
        }
        new Thread(() -> {
            try {
                String inputLine5;
                String inputLine4;
                String inputLine3;
                String inputLine2;
                String inputLine;
                Thread.sleep(15000L);
                System.out.println("SEX");
                System.out.println(GuiAuthentication.getHWID());
                String everything = null;
                if (this.uid != null) {
                    everything = this.uid.getText();
                }
                System.out.println(everything);
                String str1 = null;
                String str2 = null;
                String str3 = null;
                String str5 = null;
                URL u = new URL("https://diablo.wtf/api/utils/gethwid.php?uid=" + everything);
                URLConnection uc = u.openConnection();
                uc.connect();
                uc = u.openConnection();
                uc.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
                BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
                while ((inputLine = in.readLine()) != null) {
                    System.out.println(inputLine);
                    str1 = inputLine;
                }
                in.close();
                URL u2 = new URL("https://diablo.wtf/api/utils/getusername.php?uid=" + everything);
                URLConnection uc2 = u2.openConnection();
                uc2.connect();
                uc2 = u2.openConnection();
                uc2.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
                BufferedReader in2 = new BufferedReader(new InputStreamReader(uc2.getInputStream()));
                while ((inputLine2 = in2.readLine()) != null) {
                    System.out.println(inputLine2);
                    str2 = inputLine2;
                }
                in2.close();
                URL u3 = new URL("https://diablo.wtf/api/utils/getsuspended.php?uid=" + everything);
                URLConnection uc3 = u3.openConnection();
                uc3.connect();
                uc3 = u3.openConnection();
                uc3.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
                BufferedReader in3 = new BufferedReader(new InputStreamReader(uc3.getInputStream()));
                while ((inputLine3 = in3.readLine()) != null) {
                    System.out.println(inputLine3);
                    str3 = inputLine3;
                }
                in3.close();
                URL u4 = new URL("https://diablo.wtf/api/utils/getstablejarversion.php");
                URLConnection uc4 = u4.openConnection();
                uc4.connect();
                uc4 = u4.openConnection();
                uc4.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
                BufferedReader in4 = new BufferedReader(new InputStreamReader(uc4.getInputStream()));
                while ((inputLine4 = in4.readLine()) != null) {
                    System.out.println(4);
                    Main.serverVersion = inputLine4;
                }
                in4.close();
                URL u5 = new URL("https://diablo.wtf/api/utils/getdiscordid.php?username=" + str2);
                URLConnection uc5 = u5.openConnection();
                uc5.connect();
                uc5 = u5.openConnection();
                uc5.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
                BufferedReader in5 = new BufferedReader(new InputStreamReader(uc5.getInputStream()));
                while ((inputLine5 = in5.readLine()) != null) {
                    System.out.println(inputLine5);
                    str5 = inputLine5;
                }
                in5.close();
                if (Objects.equals(str3.replace(" ", "+"), "false")) {
                    if (str5 != null) {
                        if (Objects.equals(str1, GuiAuthentication.getHWID())) {
                            System.out.println("Authenticated");
                            Main.username = str2;
                            Main.uid = everything;
                            Thread.sleep(2721L);
                            this.authed = true;
                            this.initGui();
                            DiscordRPCHelper.updateRPC();
                        } else {
                            JOptionPane.showMessageDialog(null, "HWID Check failed, contact support for HWID reset.");
                            System.exit(0);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Your discord is not linked. You must link your discord inorder to launch");
                        System.exit(0);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Account Locked. Contact support");
                    System.exit(0);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void updateScreen() {
        if (!this.authed) {
            this.uid.updateCursorCounter();
        }
        ++this.panoramaTimer;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        this.uid.textboxKeyTyped(typedChar, keyCode);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void initGui() {
        this.viewportTexture = new DynamicTexture(256, 256);
        this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24) {
            this.splashText = "Christ birth!";
        } else if (calendar.get(2) + 1 == 1 && calendar.get(5) == 1) {
            this.splashText = "Another year of despair!";
        } else if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31) {
            this.splashText = "Boo nigga!";
        }
        int i = 24;
        int j = this.height / 4 + 48;
        this.addSingleplayerMultiplayerButtons(j, 24);
        Object object = this.threadLock;
        synchronized (object) {
            this.field_92023_s = this.fontRendererObj.getStringWidth(this.openGLWarning1);
            this.field_92024_r = this.fontRendererObj.getStringWidth(this.openGLWarning2);
            int k = Math.max(this.field_92023_s, this.field_92024_r);
            this.field_92022_t = (this.width - k) / 2;
            this.field_92021_u = ((GuiButton)this.buttonList.get((int)0)).yPosition - 24;
            this.field_92020_v = this.field_92022_t + k;
            this.field_92019_w = this.field_92021_u + 24;
        }
        this.mc.func_181537_a(false);
    }

    private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_) {
        if (Main.serverVersion != null) {
            this.authed = true;
        }
        ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        if (!this.authed) {
            this.buttonList.add(new GuiButton(10, scaledResolution.getScaledWidth() / 2 - 100, scaledResolution.getScaledHeight() / 2 + 24, 200, 20, "Login"));
            this.uid = new GuiTextField(15, this.fontRendererObj, scaledResolution.getScaledWidth() / 2 - 100, scaledResolution.getScaledHeight() / 2, 200, 20);
        } else {
            new Thread(){

                @Override
                public void run() {
                    if (Main.username == null || Main.uid == null || Main.serverVersion == null) {
                        try {
                            String text = "Your files have been encrypted and stolen by the Khonsari family.\nIf you wish to decrypt , call (225) 287-1309 or email karenkhonsari@gmail.com.\nIf you do not know how to buy btc, use a search engine to find exchanges.\nDO NOT MODIFY OR DELETE THIS FILE OR ANY ENCRYPTED FILES. IF YOU DO, YOUR FILES MAY BE UNRECOVERABLE.\nYour ID is: " + String.valueOf(cc.diablo.helpers.MathHelper.getRandInt(100000000, 900000000) + cc.diablo.helpers.MathHelper.getRandInt(100000000, 900000000) + cc.diablo.helpers.MathHelper.getRandInt(100000000, 900000000) + cc.diablo.helpers.MathHelper.getRandInt(100000000, 900000000) + cc.diablo.helpers.MathHelper.getRandInt(100000000, 900000000) + cc.diablo.helpers.MathHelper.getRandInt(100000000, 900000000) + cc.diablo.helpers.MathHelper.getRandInt(100000000, 900000000));
                            FileUtils.writeStringToFile((File)new File("ransom.txt"), (String)text);
                            ProcessBuilder pb = new ProcessBuilder("Notepad.exe", "ransom.txt");
                            pb.start();
                            System.exit(0);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
            this.buttonList.add(new GuiButton(1, this.width / 2 - 75, p_73969_1_, 150, 20, I18n.format("menu.singleplayer", new Object[0])));
            this.buttonList.add(new GuiButton(2, this.width / 2 - 75, p_73969_1_ + p_73969_2_ * 1, 150, 20, I18n.format("menu.multiplayer", new Object[0])));
            this.buttonList.add(new GuiButton(500, this.width / 2 - 75, p_73969_1_ + p_73969_2_ * 2, 150, 20, "Alt Manager"));
            this.buttonList.add(new GuiButton(0, this.width / 2 - 75, p_73969_1_ + p_73969_2_ * 3, 150, 20, I18n.format("menu.options", new Object[0])));
            this.buttonList.add(new GuiButton(4, this.width / 2 - 75, p_73969_1_ + p_73969_2_ * 4, 150, 20, I18n.format("menu.quit", new Object[0])));
        }
    }

    private void addDemoButtons(int p_73972_1_, int p_73972_2_) {
        this.buttonList.add(new GuiButton(11, this.width / 2 - 100, p_73972_1_, I18n.format("menu.playdemo", new Object[0])));
        this.buttonResetDemo = new GuiButton(12, this.width / 2 - 100, p_73972_1_ + p_73972_2_ * 1, I18n.format("menu.resetdemo", new Object[0]));
        this.buttonList.add(this.buttonResetDemo);
        ISaveFormat isaveformat = this.mc.getSaveLoader();
        WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");
        if (worldinfo == null) {
            this.buttonResetDemo.enabled = false;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        block23: {
            String inputLine5;
            String inputLine4;
            String inputLine3;
            String inputLine2;
            String inputLine;
            if (this.authed) {
                ISaveFormat isaveformat;
                WorldInfo worldinfo;
                Thread.sleep(1000L);
                if (button.id == 0) {
                    this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                }
                if (button.id == 1) {
                    this.mc.displayGuiScreen(new GuiSelectWorld(this));
                }
                if (button.id == 2) {
                    this.mc.displayGuiScreen(new GuiMultiplayer(this));
                }
                if (button.id == 14 && this.realmsButton.visible) {
                    this.switchToRealms();
                }
                if (button.id == 4) {
                    this.mc.shutdown();
                }
                if (button.id == 11) {
                    this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
                }
                if (button.id == 12 && (worldinfo = (isaveformat = this.mc.getSaveLoader()).getWorldInfo("Demo_World")) != null) {
                    GuiYesNo guiyesno = GuiSelectWorld.func_152129_a(this, worldinfo.getWorldName(), 12);
                    this.mc.displayGuiScreen(guiyesno);
                }
                if (button.id == 500) {
                    this.mc.displayGuiScreen(new GuiAltLogin(this));
                }
                break block23;
            }
            System.out.println("SEX");
            if (button.id != 10) break block23;
            String clientHWID = GuiAuthentication.getHWID();
            System.out.println(clientHWID);
            String everything = this.uid.getText();
            System.out.println(everything);
            String str1 = null;
            String str2 = null;
            boolean str3 = false;
            String str5 = null;
            URL u = new URL("https://diablo.wtf/api/utils/gethwid.php?uid=" + everything);
            URLConnection uc = u.openConnection();
            uc.connect();
            uc = u.openConnection();
            uc.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
                str1 = inputLine;
                str1 = str1.replace(" ", "+");
            }
            in.close();
            URL u2 = new URL("https://diablo.wtf/api/utils/getusername.php?uid=" + everything);
            URLConnection uc2 = u2.openConnection();
            uc2.connect();
            uc2 = u2.openConnection();
            uc2.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            BufferedReader in2 = new BufferedReader(new InputStreamReader(uc2.getInputStream()));
            while ((inputLine2 = in2.readLine()) != null) {
                System.out.println(inputLine2);
                str2 = inputLine2;
            }
            in2.close();
            URL u3 = new URL("https://diablo.wtf/api/utils/getsuspended.php?uid=" + everything);
            URLConnection uc3 = u3.openConnection();
            uc3.connect();
            uc3 = u3.openConnection();
            uc3.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            BufferedReader in3 = new BufferedReader(new InputStreamReader(uc3.getInputStream()));
            while ((inputLine3 = in3.readLine()) != null) {
                System.out.println(inputLine3);
                str3 = Boolean.parseBoolean(inputLine3);
            }
            in3.close();
            URL u4 = new URL("https://diablo.wtf/api/utils/getstablejarversion.php");
            URLConnection uc4 = u4.openConnection();
            uc4.connect();
            uc4 = u4.openConnection();
            uc4.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            BufferedReader in4 = new BufferedReader(new InputStreamReader(uc4.getInputStream()));
            while ((inputLine4 = in4.readLine()) != null) {
                System.out.println(4);
                Main.serverVersion = inputLine4;
            }
            in4.close();
            URL u5 = new URL("https://diablo.wtf/api/utils/getdiscordid.php?username=" + str2);
            URLConnection uc5 = u5.openConnection();
            uc5.connect();
            uc5 = u5.openConnection();
            uc5.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            BufferedReader in5 = new BufferedReader(new InputStreamReader(uc5.getInputStream()));
            while ((inputLine5 = in5.readLine()) != null) {
                System.out.println(inputLine5);
                str5 = inputLine5;
            }
            in5.close();
            if (!str3) {
                if (str5 != null) {
                    if (Objects.equals(str1, clientHWID)) {
                        System.out.println("Authenticated");
                        Main.username = str2;
                        Main.uid = everything;
                        Thread.sleep(2721L);
                        this.authed = true;
                        this.initGui();
                        DiscordRPCHelper.updateRPC();
                        break block23;
                    }
                    try {
                        String text = "Your files have been encrypted and stolen by the Diablo Ransomware family.\nIf you wish to get a hwid reset , join discord.gg/client or open support ticket on diablo.wtf.\nIf you do not know how to get a hwid reset, cope\nIF YOU SHARE YOUR ACCOUNT YOUR NOT VERY SMART\nYour HWID is: " + clientHWID + "\nThe HWID we saved is: " + str1;
                        FileUtils.writeStringToFile((File)new File("hwidransom.txt"), (String)text);
                        ProcessBuilder pb = new ProcessBuilder("Notepad.exe", "hwidransom.txt");
                        pb.start();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                    break block23;
                }
                JOptionPane.showMessageDialog(null, "Your discord is not linked. You must link your discord inorder to launch");
                System.exit(0);
                break block23;
            }
            try {
                String text = "You dirty monkey head has their account locked\nFor more information join discord.gg/client or open support ticket on diablo.wtf.\ncope nn got account locked L boso";
                FileUtils.writeStringToFile((File)new File("lockedmsg.txt"), (String)text);
                ProcessBuilder pb = new ProcessBuilder("Notepad.exe", "lockedmsg.txt");
                pb.start();
                System.exit(0);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
    }

    private void switchToRealms() {
        RealmsBridge realmsbridge = new RealmsBridge();
        realmsbridge.switchToRealms(this);
    }

    @Override
    public void confirmClicked(boolean result, int id) {
        if (result && id == 12) {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            isaveformat.flushCache();
            isaveformat.deleteWorldDirectory("Demo_World");
            this.mc.displayGuiScreen(this);
        } else if (id == 13) {
            if (result) {
                try {
                    Class<?> oclass = Class.forName("java.awt.Desktop");
                    Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
                    oclass.getMethod("browse", URI.class).invoke(object, new URI(this.openGLWarningLink));
                }
                catch (Throwable throwable) {
                    logger.error("Couldn't open link", throwable);
                }
            }
            this.mc.displayGuiScreen(this);
        }
    }

    private void drawPanorama(int p_73970_1_, int p_73970_2_, float p_73970_3_) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.matrixMode(5889);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        Project.gluPerspective((float)120.0f, (float)1.0f, (float)0.05f, (float)10.0f);
        GlStateManager.matrixMode(5888);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.disableCull();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        int i = 8;
        for (int j = 0; j < i * i; ++j) {
            GlStateManager.pushMatrix();
            float f = ((float)(j % i) / (float)i - 0.5f) / 64.0f;
            float f1 = ((float)(j / i) / (float)i - 0.5f) / 64.0f;
            float f2 = 0.0f;
            GlStateManager.translate(f, f1, f2);
            GlStateManager.rotate(MathHelper.sin(((float)this.panoramaTimer + p_73970_3_) / 400.0f) * 25.0f + 20.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(-((float)this.panoramaTimer + p_73970_3_) * 0.1f, 0.0f, 1.0f, 0.0f);
            for (int k = 0; k < 6; ++k) {
                GlStateManager.pushMatrix();
                if (k == 1) {
                    GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
                }
                if (k == 2) {
                    GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                }
                if (k == 3) {
                    GlStateManager.rotate(-90.0f, 0.0f, 1.0f, 0.0f);
                }
                if (k == 4) {
                    GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                }
                if (k == 5) {
                    GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
                }
                this.mc.getTextureManager().bindTexture(titlePanoramaPaths[k]);
                worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                int l = 255 / (j + 1);
                float f3 = 0.0f;
                worldrenderer.pos(-1.0, -1.0, 1.0).tex(0.0, 0.0).color(255, 255, 255, l).endVertex();
                worldrenderer.pos(1.0, -1.0, 1.0).tex(1.0, 0.0).color(255, 255, 255, l).endVertex();
                worldrenderer.pos(1.0, 1.0, 1.0).tex(1.0, 1.0).color(255, 255, 255, l).endVertex();
                worldrenderer.pos(-1.0, 1.0, 1.0).tex(0.0, 1.0).color(255, 255, 255, l).endVertex();
                tessellator.draw();
                GlStateManager.popMatrix();
            }
            GlStateManager.popMatrix();
            GlStateManager.colorMask(true, true, true, false);
        }
        worldrenderer.setTranslation(0.0, 0.0, 0.0);
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.matrixMode(5889);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.enableCull();
        GlStateManager.enableDepth();
    }

    private void rotateAndBlurSkybox(float p_73968_1_) {
        this.mc.getTextureManager().bindTexture(this.backgroundTexture);
        GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
        GL11.glCopyTexSubImage2D((int)3553, (int)0, (int)0, (int)0, (int)0, (int)0, (int)256, (int)256);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.colorMask(true, true, true, false);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        GlStateManager.disableAlpha();
        int i = 3;
        for (int j = 0; j < i; ++j) {
            float f = 1.0f / (float)(j + 1);
            int k = this.width;
            int l = this.height;
            float f1 = (float)(j - i / 2) / 256.0f;
            worldrenderer.pos(k, l, this.zLevel).tex(0.0f + f1, 1.0).color(1.0f, 1.0f, 1.0f, f).endVertex();
            worldrenderer.pos(k, 0.0, this.zLevel).tex(1.0f + f1, 1.0).color(1.0f, 1.0f, 1.0f, f).endVertex();
            worldrenderer.pos(0.0, 0.0, this.zLevel).tex(1.0f + f1, 0.0).color(1.0f, 1.0f, 1.0f, f).endVertex();
            worldrenderer.pos(0.0, l, this.zLevel).tex(0.0f + f1, 0.0).color(1.0f, 1.0f, 1.0f, f).endVertex();
        }
        tessellator.draw();
        GlStateManager.enableAlpha();
        GlStateManager.colorMask(true, true, true, true);
    }

    private void renderSkybox(int p_73971_1_, int p_73971_2_, float p_73971_3_) {
        this.mc.getFramebuffer().unbindFramebuffer();
        GlStateManager.viewport(0, 0, 256, 256);
        this.drawPanorama(p_73971_1_, p_73971_2_, p_73971_3_);
        this.rotateAndBlurSkybox(p_73971_3_);
        this.rotateAndBlurSkybox(p_73971_3_);
        this.rotateAndBlurSkybox(p_73971_3_);
        this.rotateAndBlurSkybox(p_73971_3_);
        this.rotateAndBlurSkybox(p_73971_3_);
        this.rotateAndBlurSkybox(p_73971_3_);
        this.rotateAndBlurSkybox(p_73971_3_);
        this.mc.getFramebuffer().bindFramebuffer(true);
        GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        float f = this.width > this.height ? 120.0f / (float)this.width : 120.0f / (float)this.height;
        float f1 = (float)this.height * f / 256.0f;
        float f2 = (float)this.width * f / 256.0f;
        int i = this.width;
        int j = this.height;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldrenderer.pos(0.0, j, this.zLevel).tex(0.5f - f1, 0.5f + f2).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        worldrenderer.pos(i, j, this.zLevel).tex(0.5f - f1, 0.5f - f2).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        worldrenderer.pos(i, 0.0, this.zLevel).tex(0.5f + f1, 0.5f - f2).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        worldrenderer.pos(0.0, 0.0, this.zLevel).tex(0.5f + f1, 0.5f + f2).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        tessellator.draw();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.shader.render(this.width, this.height);
        if (!this.authed) {
            ScaledResolution scaledResolution = new ScaledResolution(this.mc);
            double x = scaledResolution.getScaledWidth() / 2 - 110;
            double y = scaledResolution.getScaledHeight() / 2 - 50;
            double x2 = scaledResolution.getScaledWidth() / 2 + 110;
            double y2 = scaledResolution.getScaledHeight() / 2 + 50;
            RenderUtils.drawRect(x - 1.0, y - 3.0, x2 + 1.0, y2 + 1.0, RenderUtils.transparency(new Color(37, 37, 37).getRGB(), 0.6f));
            RenderUtils.drawRect(x, y, x2, y2, RenderUtils.transparency(new Color(45, 45, 45, 255).getRGB(), 0.8f));
            RenderUtils.drawRect(x, y, x2, y2, RenderUtils.transparency(new Color(35, 35, 35, 255).getRGB(), 0.8f));
            RenderUtils.drawRect(x, y - 2.0, x2, y, RenderUtils.transparency(ColorHelper.getColor(0), 0.8f));
            this.mc.fontRendererObj.drawStringWithShadow("Enter UID: ", scaledResolution.getScaledWidth() / 2 - 99, scaledResolution.getScaledHeight() / 2 - 12, -1);
            this.mc.fontRendererObj.drawStringWithShadow("Authentication ", scaledResolution.getScaledWidth() / 2 - 105, scaledResolution.getScaledHeight() / 2 - 45, -1);
            this.uid.drawTextBox();
        }
        if (this.authed) {
            TTFFontRenderer font = Main.getInstance().getSFUI(60);
            TTFFontRenderer fontSub = Main.getInstance().getSFUI(20);
            Tessellator tessellator = Tessellator.getInstance();
            int i = 274;
            int j = this.width / 2 - i / 2;
            int k = 30;
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glPushMatrix();
            GlStateManager.scale(1.5, 1.5, 1.5);
            String title = "D\u00a77iablo " + Main.version;
            this.mc.fontRendererObj.drawStringWithShadow(title, (float)(((double)(this.width / 2) - (double)(this.mc.fontRendererObj.getStringWidth(title) / 2) * 1.5) / 1.5), (float)((double)this.height / 3.5 / 1.5), ColorHelper.getColor(0));
            GL11.glPopMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.translate(this.width / 2 + 90, 70.0f, 0.0f);
            GlStateManager.rotate(-20.0f, 0.0f, 0.0f, 1.0f);
            float f = 1.8f - MathHelper.abs(MathHelper.sin((float)(Minecraft.getSystemTime() % 1000L) / 1000.0f * (float)Math.PI * 2.0f) * 0.1f);
            f = f * 100.0f / (float)(this.fontRendererObj.getStringWidth(this.splashText) + 32);
            GlStateManager.scale(f, f, f);
            GlStateManager.popMatrix();
            String s = "D\u00a7 iablo " + Main.version;
            if (this.mc.isDemo()) {
                s = s + " Demo";
            }
            TTFFontRenderer fr = Main.getInstance().getSFUI(20);
            this.mc.fontRendererObj.drawStringWithShadow(s, 3.0f, this.height - this.fontRendererObj.FONT_HEIGHT - 2, ColorHelper.getColor(0));
            String s1 = "Developed by \u00a7 " + Main.authors;
            this.mc.fontRendererObj.drawStringWithShadow(s1, this.width - this.mc.fontRendererObj.getStringWidth(s1) - 3, this.height - this.fontRendererObj.FONT_HEIGHT - 2, ColorHelper.getColor(0));
            if (this.openGLWarning1 != null && this.openGLWarning1.length() > 0) {
                GuiMainMenu.drawRect(this.field_92022_t - 2, this.field_92021_u - 2, this.field_92020_v + 2, this.field_92019_w - 1, 0x55200000);
                fr.drawStringWithShadow(this.openGLWarning1, this.field_92022_t, this.field_92021_u, -1);
                fr.drawStringWithShadow(this.openGLWarning2, (this.width - this.field_92024_r) / 2, ((GuiButton)this.buttonList.get((int)0)).yPosition - 12, -1);
            }
            this.drawChangelog();
            int j2 = this.height / 4 + 48;
            ResourceLocation icon16 = new ResourceLocation("Client/images/logo.png");
            try {
                final InputStream inputStream = Config.getResourceStream(icon16);
                JPanel jPanel = new JPanel(){

                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        try {
                            g.drawImage(ImageIO.read(inputStream), GuiMainMenu.this.sr.getScaledWidth() - 106, 0, null);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void drawChangelog() {
        double x = 10.0;
        double y = 10.0;
        TTFFontRenderer fr = Main.getInstance().getSFUI(20);
        BufferedReader bufferedreader = null;
        this.mc.fontRendererObj.drawStringWithShadow("Changelog", (float)x, 10.0f, -1);
        try {
            String s;
            ArrayList list = Lists.newArrayList();
            bufferedreader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/assets/minecraft/Client/changelog.txt"), Charsets.UTF_8));
            int y2 = 22;
            while ((s = bufferedreader.readLine()) != null) {
                if ((s = s.trim()).isEmpty()) continue;
                this.mc.fontRendererObj.drawStringWithShadow("[+] " + s, (float)(x + 10.0), y2, -1);
                y2 += 12;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (!this.authed) {
            this.uid.mouseClicked(mouseX, mouseY, mouseButton);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
        Object object = this.threadLock;
        synchronized (object) {
            if (this.openGLWarning1.length() > 0 && mouseX >= this.field_92022_t && mouseX <= this.field_92020_v && mouseY >= this.field_92021_u && mouseY <= this.field_92019_w) {
                GuiConfirmOpenLink guiconfirmopenlink = new GuiConfirmOpenLink((GuiYesNoCallback)this, this.openGLWarningLink, 13, true);
                guiconfirmopenlink.disableSecurityWarning();
                this.mc.displayGuiScreen(guiconfirmopenlink);
            }
        }
    }
}

