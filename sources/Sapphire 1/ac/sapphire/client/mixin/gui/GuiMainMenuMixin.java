package ac.sapphire.client.mixin.gui;

import ac.sapphire.client.ui.CreditUI;
import me.tireman.hexa.alts.GuiAltManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Calendar;
import java.util.Date;

@Mixin(GuiMainMenu.class)
public abstract class GuiMainMenuMixin extends GuiScreen {
    @Shadow
    private GuiButton realmsButton;

    @Shadow
    private DynamicTexture viewportTexture;

    @Shadow
    private ResourceLocation backgroundTexture;

    @Shadow
    private String splashText = "Merry X-mas!";
    @Shadow
    @Final
    private Object threadLock;
    @Shadow
    private String openGLWarning1;
    @Shadow
    private String openGLWarning2;
    @Shadow
    private int field_92024_r;
    @Shadow
    private int field_92023_s;
    @Shadow
    private int field_92022_t;
    @Shadow
    private int field_92021_u;
    @Shadow
    private int field_92020_v;
    @Shadow
    private int field_92019_w;
    @Shadow
    private boolean field_183502_L;
    @Shadow
    private GuiScreen field_183503_M;

    @Shadow
    protected abstract void addDemoButtons(int p_73972_1_, int p_73972_2_);

    @Shadow
    protected abstract boolean func_183501_a();

    /**
     * @author Starlight Technology LLC
     */
    @Overwrite
    public void initGui() {
        this.viewportTexture = new DynamicTexture(256, 256);
        this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24) {
            this.splashText = "Merry X-mas!";
        } else if (calendar.get(2) + 1 == 1 && calendar.get(5) == 1) {
            this.splashText = "Happy new year!";
        } else if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31) {
            this.splashText = "OOoooOOOoooo! Spooky!";
        }

        int i = 24;
        int j = this.height / 4 + 48;

        if (this.mc.isDemo()) {
            this.addDemoButtons(j, 24);
        } else {
            this.addSingleplayerMultiplayerButtons(j, 24);
        }

        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, j + 72, 98, 20, I18n.format("menu.options")));
        this.buttonList.add(new GuiButton(4, this.width / 2 + 2, j + 72, 98, 20, I18n.format("menu.quit")));
        this.buttonList.add(new GuiButton(5, this.width / 2 - 52, j + 72 + 12 + 12, 98, 20, "Credit"));

        synchronized (this.threadLock) {
            this.field_92023_s = this.fontRendererObj.getStringWidth(this.openGLWarning1);
            this.field_92024_r = this.fontRendererObj.getStringWidth(this.openGLWarning2);
            int k = Math.max(this.field_92023_s, this.field_92024_r);
            this.field_92022_t = (this.width - k) / 2;
            this.field_92021_u = this.buttonList.get(0).yPosition - 24;
            this.field_92020_v = this.field_92022_t + k;
            this.field_92019_w = this.field_92021_u + 24;
        }

        this.mc.setConnectedToRealms(false);

        if (Minecraft.getMinecraft().gameSettings.getOptionOrdinalValue(GameSettings.Options.REALMS_NOTIFICATIONS) && !this.field_183502_L) {
            RealmsBridge realmsbridge = new RealmsBridge();
            this.field_183503_M = realmsbridge.getNotificationScreen(this);
            this.field_183502_L = true;
        }

        if (this.func_183501_a()) {
            this.field_183503_M.setGuiSize(this.width, this.height);
            this.field_183503_M.initGui();
        }
    }

    /**
     * @author Starlight Technology LLC
     */
    @Overwrite
    private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_) {
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, p_73969_1_, I18n.format("menu.singleplayer")));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, p_73969_1_ + p_73969_2_, I18n.format("menu.multiplayer")));
        this.buttonList.add(this.realmsButton = new GuiButton(14, this.width / 2 + 2, p_73969_1_ + p_73969_2_ * 2, 98, 20, "Alts"));
        this.buttonList.add(new GuiButton(6, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 2, 98, 20, I18n.format("fml.menu.mods")));
    }

    /**
     * @author Starlight Technology LLC
     */
    @Overwrite
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }

        if (button.id == 5) {
            this.mc.displayGuiScreen(new CreditUI());
        }

        if (button.id == 1) {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }

        if (button.id == 2) {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }

        if (button.id == 14 && this.realmsButton.visible) {
            this.mc.displayGuiScreen(new GuiAltManager());
        }

        if (button.id == 4) {
            this.mc.shutdown();
        }

        if (button.id == 6) {
            this.mc.displayGuiScreen(new net.minecraftforge.fml.client.GuiModList(this));
        }

        if (button.id == 11) {
            this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
        }

        if (button.id == 12) {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

            if (worldinfo != null) {
                GuiYesNo guiyesno = GuiSelectWorld.makeDeleteWorldYesNo(this, worldinfo.getWorldName(), 12);
                this.mc.displayGuiScreen(guiyesno);
            }
        }
    }
}
