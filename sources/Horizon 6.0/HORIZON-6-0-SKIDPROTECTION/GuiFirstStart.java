package HORIZON-6-0-SKIDPROTECTION;

import java.awt.Color;
import java.util.Random;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import org.lwjgl.opengl.Display;

public class GuiFirstStart extends GuiScreen
{
    public float HorizonCode_Horizon_È;
    public boolean Â;
    public boolean Ý;
    public boolean Ø­áŒŠá;
    public float Âµá€;
    public boolean Ó;
    public boolean à;
    public boolean Ø;
    public float áŒŠÆ;
    public boolean áˆºÑ¢Õ;
    public boolean ÂµÈ;
    public boolean á;
    public float ˆÏ­;
    public boolean £á;
    public boolean Å;
    public boolean £à;
    public int µà;
    public int ˆà;
    public boolean ¥Æ;
    public boolean Ø­à;
    public static float µÕ;
    public GuiStartButton Æ;
    
    static {
        GuiFirstStart.µÕ = 1.0f;
    }
    
    public GuiFirstStart() {
        this.HorizonCode_Horizon_È = 0.0f;
        this.Â = false;
        this.Ý = false;
        this.Ø­áŒŠá = false;
        this.Âµá€ = 0.0f;
        this.Ó = false;
        this.à = false;
        this.Ø = false;
        this.áŒŠÆ = 0.0f;
        this.áˆºÑ¢Õ = false;
        this.ÂµÈ = false;
        this.á = false;
        this.ˆÏ­ = 1.0f;
        this.£á = false;
        this.Å = false;
        this.£à = false;
        this.µà = 0;
        this.ˆà = 0;
        this.¥Æ = false;
        this.Ø­à = false;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        Display.setResizable(false);
        if (!this.¥Æ) {
            this.Ø­áŒŠá = true;
            this.Â = true;
        }
        this.ÇŽÉ.add(this.Æ = new GuiStartButton(1, GuiFirstStart.Çªà¢ / 2 - 100, GuiFirstStart.Ê / 2, 200, 50, "Start"));
        this.Æ.µà = false;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton guiButton) throws IOException {
        switch (guiButton.£à) {
            case 1: {
                Display.setResizable(true);
                final File file = new File(Horizon.à¢.áŒŠ, "client.data");
                try {
                    file.createNewFile();
                }
                catch (IOException ex) {}
                try {
                    final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
                    bufferedWriter.write("id:" + new Random().nextInt(100000));
                    bufferedWriter.close();
                }
                catch (Exception ex2) {}
                GuiFirstStart.Ñ¢á.HorizonCode_Horizon_È(new GuiMainMenu());
                break;
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int n, final int n2, final float n3) {
        Display.setTitle("Horizon: Setup");
        if (this.Ø­à) {
            if (GuiFirstStart.µÕ >= 250.0f) {
                GuiFirstStart.µÕ = 255.0f;
                this.Ø­à = false;
                return;
            }
            GuiFirstStart.µÕ += 4.0f;
        }
        if (this.Ø­áŒŠá) {
            if (this.Â) {
                if (this.HorizonCode_Horizon_È >= 250.0f) {
                    this.Ý = true;
                    this.Â = false;
                    this.HorizonCode_Horizon_È = 255.0f;
                }
                this.HorizonCode_Horizon_È += 3.0f;
            }
            if (this.Ý) {
                if (this.HorizonCode_Horizon_È <= 8.0f) {
                    this.Ý = false;
                    this.Ø­áŒŠá = false;
                    this.HorizonCode_Horizon_È = 1.0f;
                    this.£à = true;
                    this.£á = true;
                    return;
                }
                this.HorizonCode_Horizon_È -= 3.0f;
            }
        }
        if (this.£à) {
            if (this.£á) {
                if (this.ˆÏ­ >= 250.0f) {
                    this.Å = true;
                    this.£á = false;
                    this.ˆÏ­ = 255.0f;
                }
                this.ˆÏ­ += 3.0f;
            }
            if (this.Å) {
                if (this.ˆÏ­ <= 8.0f) {
                    this.Å = false;
                    this.£à = false;
                    this.ˆÏ­ = 1.0f;
                    this.Ø = true;
                    this.Ó = true;
                    return;
                }
                this.ˆÏ­ -= 3.0f;
            }
        }
        if (this.Ø) {
            if (this.Ó) {
                if (this.Âµá€ >= 230.0f) {
                    this.Âµá€ = 250.0f;
                    this.Ó = false;
                }
                this.Âµá€ += 3.0f;
            }
            if (this.à) {
                if (this.Âµá€ <= 8.0f) {
                    this.à = false;
                    this.Ø = false;
                    this.Âµá€ = 1.0f;
                    this.á = true;
                    this.áˆºÑ¢Õ = true;
                    return;
                }
                this.Âµá€ -= 3.0f;
            }
        }
        if (this.á) {
            if (this.áˆºÑ¢Õ) {
                if (this.áŒŠÆ >= 230.0f) {
                    this.áˆºÑ¢Õ = false;
                    this.áŒŠÆ = 250.0f;
                }
                this.áŒŠÆ += 3.0f;
            }
            if (this.ÂµÈ) {
                if (this.áŒŠÆ <= 8.0f) {
                    this.ÂµÈ = false;
                    this.á = false;
                    this.áŒŠÆ = 1.0f;
                    this.¥Æ = true;
                    this.Ø­à = true;
                    return;
                }
                this.áŒŠÆ -= 3.0f;
            }
        }
        Gui_1808253012.HorizonCode_Horizon_È(0, 0, GuiFirstStart.Çªà¢, GuiFirstStart.Ê, ColorUtil.HorizonCode_Horizon_È(200000000000L, 0.25f).getRGB());
        final Color color = new Color(1.0f, 1.0f, 1.0f, this.HorizonCode_Horizon_È / 255.0f);
        if (this.Ø­áŒŠá) {
            UIFonts.ÂµÈ.HorizonCode_Horizon_È("welcome to horizon", GuiScreen.Çªà¢ / 2 - (UIFonts.ÂµÈ.HorizonCode_Horizon_È("welcome to horizon") / 2 + 6), 80, color.getRGB());
        }
        final Color color2 = new Color(1.0f, 1.0f, 1.0f, this.ˆÏ­ / 255.0f);
        if (this.£à) {
            UIFonts.Ø.HorizonCode_Horizon_È("your experience starts now", GuiScreen.Çªà¢ / 2 - (UIFonts.Ø.HorizonCode_Horizon_È("your experience starts now") / 2 + 6), 80, color2.getRGB());
        }
        final Color color3 = new Color(1.0f, 1.0f, 1.0f, GuiFirstStart.µÕ / 255.0f);
        if (this.Ø­à || GuiFirstStart.µÕ == 255.0f) {
            this.Æ.µà = true;
            UIFonts.Ø.HorizonCode_Horizon_È("press to start your experience now", GuiScreen.Çªà¢ / 2 - (UIFonts.Ø.HorizonCode_Horizon_È("press to start your experience now") / 2 - 1), 60, color3.getRGB());
        }
        final Color color4 = new Color(1.0f, 1.0f, 1.0f, this.Âµá€ / 255.0f);
        final Color color5 = new Color(0.2f, 0.2f, 0.2f, this.Âµá€ / 255.0f);
        if (this.Ø) {
            final Random random = new Random();
            final int n4 = 2;
            final int n5 = 1;
            final int n6 = random.nextInt(n4 - n5 + 1) + n5;
            if (this.µà >= 360) {
                this.µà = 360;
                this.à = true;
            }
            this.µà += n6;
            ModGuiUtils.HorizonCode_Horizon_È(GuiScreen.Çªà¢ - 210, GuiScreen.Ê - 120, 40.0, this.µà, color4.getRGB());
            final int n7 = this.µà * 100 / 360;
            UIFonts.Ø.HorizonCode_Horizon_È(String.valueOf(n7) + "%", GuiScreen.Çªà¢ / 2 - (UIFonts.Ø.HorizonCode_Horizon_È(String.valueOf(n7) + "%") / 2 - 4), GuiFirstStart.Ê - 130, color4.getRGB());
            UIFonts.Ø.HorizonCode_Horizon_È("Downloading Resources...", GuiScreen.Çªà¢ / 2 - (UIFonts.Ø.HorizonCode_Horizon_È("Downloading Resources...") / 2 - 10), GuiFirstStart.Ê - 40, color4.getRGB());
        }
        final Color color6 = new Color(1.0f, 1.0f, 1.0f, this.áŒŠÆ / 255.0f);
        final Color color7 = new Color(0.2f, 0.2f, 0.2f, this.áŒŠÆ / 255.0f);
        if (this.á) {
            if (this.ˆà >= 350) {
                this.ˆà = 360;
                this.ÂµÈ = true;
            }
            this.ˆà += 4;
            ModGuiUtils.HorizonCode_Horizon_È(GuiScreen.Çªà¢ - 210, GuiScreen.Ê - 120, 40.0, this.ˆà, color6.getRGB());
            final int n8 = this.ˆà * 100 / 360 - 1;
            UIFonts.Ø.HorizonCode_Horizon_È(String.valueOf(n8) + "%", GuiScreen.Çªà¢ / 2 - (UIFonts.Ø.HorizonCode_Horizon_È(String.valueOf(n8) + "%") / 2 - 4), GuiFirstStart.Ê - 130, color6.getRGB());
            UIFonts.Ø.HorizonCode_Horizon_È("Loading Resources...", GuiScreen.Çªà¢ / 2 - (UIFonts.Ø.HorizonCode_Horizon_È("Loading Resources...") / 2 - 9), GuiFirstStart.Ê - 40, color6.getRGB());
        }
        super.HorizonCode_Horizon_È(n, n2, n3);
        if (!Horizon.à¢.Ï.HorizonCode_Horizon_È.isRunning() && Horizon.Âµà) {
            Horizon.à¢.Ï.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menusong.wav");
            Horizon.à¢.Ï.HorizonCode_Horizon_È(-28.0f);
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char c, final int n) throws IOException {
    }
}
