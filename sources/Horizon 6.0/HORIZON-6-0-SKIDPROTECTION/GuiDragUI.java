package HORIZON-6-0-SKIDPROTECTION;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

public class GuiDragUI extends GuiScreen
{
    private ArrayList<Panel> HorizonCode_Horizon_È;
    
    public ArrayList<Panel> Âµá€() {
        return this.HorizonCode_Horizon_È;
    }
    
    public GuiDragUI() {
        this.HorizonCode_Horizon_È = new ArrayList<Panel>();
        GuiDragUI.Çªà¢ = 100;
        GuiDragUI.Ê = 18;
        final Panel player;
        this.HorizonCode_Horizon_È.add(player = new Panel("Player", Category.Ø­áŒŠá, 104, 32, GuiDragUI.Çªà¢, GuiDragUI.Ê, false, false) {
            @Override
            public void HorizonCode_Horizon_È() {
                final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
                for (final Mod module : ModuleManager.HorizonCode_Horizon_È) {
                    if (module.ÂµÈ().Ø­áŒŠá() == this.Ý()) {
                        this.áˆºÑ¢Õ().add(new ItemButton(module, this.Ý()));
                    }
                }
            }
        });
        final Panel movement;
        this.HorizonCode_Horizon_È.add(movement = new Panel("Movement", Category.Ó, 206, 32, GuiDragUI.Çªà¢, GuiDragUI.Ê, false, false) {
            @Override
            public void HorizonCode_Horizon_È() {
                final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
                for (final Mod module : ModuleManager.HorizonCode_Horizon_È) {
                    if (module.ÂµÈ().Ø­áŒŠá() == this.Ý()) {
                        this.áˆºÑ¢Õ().add(new ItemButton(module, this.Ý()));
                    }
                }
            }
        });
        final Panel combat;
        this.HorizonCode_Horizon_È.add(combat = new Panel("Combat", Category.Â, 104, 64, GuiDragUI.Çªà¢, GuiDragUI.Ê, false, false) {
            @Override
            public void HorizonCode_Horizon_È() {
                final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
                for (final Mod module : ModuleManager.HorizonCode_Horizon_È) {
                    if (module.ÂµÈ().Ø­áŒŠá() == this.Ý()) {
                        this.áˆºÑ¢Õ().add(new ItemButton(module, this.Ý()));
                    }
                }
            }
        });
        final Panel render;
        this.HorizonCode_Horizon_È.add(render = new Panel("Render", Category.Ý, 206, 64, GuiDragUI.Çªà¢, GuiDragUI.Ê, false, false) {
            @Override
            public void HorizonCode_Horizon_È() {
                final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
                for (final Mod module : ModuleManager.HorizonCode_Horizon_È) {
                    if (module.ÂµÈ().Ø­áŒŠá() == this.Ý()) {
                        this.áˆºÑ¢Õ().add(new ItemButton(module, this.Ý()));
                    }
                }
            }
        });
        final Panel server;
        this.HorizonCode_Horizon_È.add(server = new Panel("Server", Category.Âµá€, 104, 96, GuiDragUI.Çªà¢, GuiDragUI.Ê, false, false) {
            @Override
            public void HorizonCode_Horizon_È() {
                final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
                for (final Mod module : ModuleManager.HorizonCode_Horizon_È) {
                    if (module.ÂµÈ().Ø­áŒŠá() == this.Ý()) {
                        this.áˆºÑ¢Õ().add(new ItemButton(module, this.Ý()));
                    }
                }
            }
        });
        final Panel minigames;
        this.HorizonCode_Horizon_È.add(minigames = new Panel("Minigames", Category.à, 206, 96, GuiDragUI.Çªà¢, GuiDragUI.Ê, false, false) {
            @Override
            public void HorizonCode_Horizon_È() {
                final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
                for (final Mod module : ModuleManager.HorizonCode_Horizon_È) {
                    if (module.ÂµÈ().Ø­áŒŠá() == this.Ý()) {
                        this.áˆºÑ¢Õ().add(new ItemButton(module, this.Ý()));
                    }
                }
            }
        });
        final Panel glidesettings;
        this.HorizonCode_Horizon_È.add(glidesettings = new Panel("Settings: Glide", null, 210, 64, GuiDragUI.Çªà¢, GuiDragUI.Ê, false, false) {
            @Override
            public void HorizonCode_Horizon_È() {
                this.áˆºÑ¢Õ().add(new UILabel(UIFonts.Ý, "Glide"));
                final List<String> glidemodes = new ArrayList<String>();
                glidemodes.add("old");
                glidemodes.add("new");
                final ArrayList<Item> áˆºÑ¢Õ = this.áˆºÑ¢Õ();
                final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
                áˆºÑ¢Õ.add(new UIComboBox(ModuleManager.HorizonCode_Horizon_È(Glide.class), glidemodes));
                this.áˆºÑ¢Õ().add(new UILabel(UIFonts.Ý, ""));
                this.áˆºÑ¢Õ().add(new ItemCheckBox("PushUp") {
                    @Override
                    public void HorizonCode_Horizon_È() {
                        Horizon.á = this.à();
                    }
                }.HorizonCode_Horizon_È(Horizon.á));
            }
        });
        final Panel laddersettings;
        this.HorizonCode_Horizon_È.add(laddersettings = new Panel("Settings: Ladders", null, 320, 32, GuiDragUI.Çªà¢, GuiDragUI.Ê, false, false) {
            @Override
            public void HorizonCode_Horizon_È() {
                this.áˆºÑ¢Õ().add(new UILabel(UIFonts.Ý, "Ladders"));
                final List<String> laddermodes = new ArrayList<String>();
                laddermodes.add("nonskip");
                laddermodes.add("skip");
                final ArrayList<Item> áˆºÑ¢Õ = this.áˆºÑ¢Õ();
                final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
                áˆºÑ¢Õ.add(new UIComboBox(ModuleManager.HorizonCode_Horizon_È(Ladders.class), laddermodes));
            }
        });
        final Panel phasesettings;
        this.HorizonCode_Horizon_È.add(phasesettings = new Panel("Settings: Phase", null, 320, 64, GuiDragUI.Çªà¢, GuiDragUI.Ê, false, false) {
            @Override
            public void HorizonCode_Horizon_È() {
                this.áˆºÑ¢Õ().add(new UILabel(UIFonts.Ý, "Phase"));
                final List<String> phaseModes = new ArrayList<String>();
                phaseModes.add("instant");
                phaseModes.add("skip");
                phaseModes.add("motionclip");
                phaseModes.add("silent");
                phaseModes.add("vanilla");
                final ArrayList<Item> áˆºÑ¢Õ = this.áˆºÑ¢Õ();
                final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
                áˆºÑ¢Õ.add(new UIComboBox(ModuleManager.HorizonCode_Horizon_È(Phase.class), phaseModes));
            }
        });
        final Panel stepsettings;
        this.HorizonCode_Horizon_È.add(stepsettings = new Panel("Settings: Step", null, 210, 96, GuiDragUI.Çªà¢, GuiDragUI.Ê, false, false) {
            @Override
            public void HorizonCode_Horizon_È() {
                this.áˆºÑ¢Õ().add(new UILabel(UIFonts.Ý, "Step"));
                this.áˆºÑ¢Õ().add(new ItemSlider("Step Height", 10.0f) {
                    @Override
                    public void HorizonCode_Horizon_È() {
                        Horizon.Ï­Ðƒà = this.à();
                    }
                }.HorizonCode_Horizon_È(Horizon.Ï­Ðƒà / 10.0f));
            }
        });
        final Panel zootsettings;
        this.HorizonCode_Horizon_È.add(zootsettings = new Panel("Settings: Zoot", null, 320, 96, GuiDragUI.Çªà¢, GuiDragUI.Ê, false, false) {
            @Override
            public void HorizonCode_Horizon_È() {
                this.áˆºÑ¢Õ().add(new UILabel(UIFonts.Ý, "Zoot"));
                this.áˆºÑ¢Õ().add(new ItemCheckBox("Anti Fire") {
                    @Override
                    public void HorizonCode_Horizon_È() {
                        Horizon.¥Æ = this.à();
                    }
                }.HorizonCode_Horizon_È(Horizon.¥Æ));
                this.áˆºÑ¢Õ().add(new ItemCheckBox("Anti Potions") {
                    @Override
                    public void HorizonCode_Horizon_È() {
                        Horizon.ˆà = this.à();
                    }
                }.HorizonCode_Horizon_È(Horizon.ˆà));
            }
        });
        final Panel nametagssettings;
        this.HorizonCode_Horizon_È.add(nametagssettings = new Panel("Settings: NameTags", null, 320, 379, GuiDragUI.Çªà¢, GuiDragUI.Ê, false, false) {
            @Override
            public void HorizonCode_Horizon_È() {
                final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
                final NameTags mod = (NameTags)ModuleManager.HorizonCode_Horizon_È(NameTags.class);
                this.áˆºÑ¢Õ().add(new UILabel(UIFonts.Ý, "NameTags"));
                this.áˆºÑ¢Õ().add(new ItemCheckBox("Armor") {
                    @Override
                    public void HorizonCode_Horizon_È() {
                        mod.Ø­áŒŠá = this.à();
                    }
                }.HorizonCode_Horizon_È(mod.Ø­áŒŠá));
                this.áˆºÑ¢Õ().add(new ItemCheckBox("Health") {
                    @Override
                    public void HorizonCode_Horizon_È() {
                        mod.Ý = this.à();
                    }
                }.HorizonCode_Horizon_È(mod.Ý));
                this.áˆºÑ¢Õ().add(new ItemCheckBox("Ping") {
                    @Override
                    public void HorizonCode_Horizon_È() {
                        mod.Âµá€ = this.à();
                    }
                }.HorizonCode_Horizon_È(mod.Âµá€));
            }
        });
        final Panel extendsettings;
        this.HorizonCode_Horizon_È.add(extendsettings = new Panel("Settings: Hitbox", null, 320, 128, GuiDragUI.Çªà¢, GuiDragUI.Ê, false, false) {
            @Override
            public void HorizonCode_Horizon_È() {
                this.áˆºÑ¢Õ().add(new UILabel(UIFonts.Ý, "HitBox Extend"));
                this.áˆºÑ¢Õ().add(new ItemSlider("Extend", 1.0f) {
                    @Override
                    public void HorizonCode_Horizon_È() {
                        Horizon.à¢.áˆºÑ¢Õ = this.à();
                    }
                }.HorizonCode_Horizon_È(Horizon.à¢.áˆºÑ¢Õ / 1.0f));
            }
        });
        final Panel speedsettings;
        this.HorizonCode_Horizon_È.add(speedsettings = new Panel("Settings: Speed", null, 210, 128, GuiDragUI.Çªà¢, GuiDragUI.Ê, false, false) {
            @Override
            public void HorizonCode_Horizon_È() {
                this.áˆºÑ¢Õ().add(new UILabel(UIFonts.Ý, "Speed"));
                this.áˆºÑ¢Õ().add(new ItemSlider("Speed", 10.0f) {
                    @Override
                    public void HorizonCode_Horizon_È() {
                        Horizon.Õ = this.à();
                    }
                }.HorizonCode_Horizon_È((float)(Horizon.Õ / 10.0)));
                final List<String> speedModes = new ArrayList<String>();
                speedModes.add("normal");
                speedModes.add("sprintjump");
                final ArrayList<Item> áˆºÑ¢Õ = this.áˆºÑ¢Õ();
                final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
                áˆºÑ¢Õ.add(new UIComboBox(ModuleManager.HorizonCode_Horizon_È(Speed.class), speedModes));
            }
        });
        final Panel killsettings;
        this.HorizonCode_Horizon_È.add(killsettings = new Panel("Settings: KillAura", null, 210, 32, GuiDragUI.Çªà¢, GuiDragUI.Ê, false, false) {
            @Override
            public void HorizonCode_Horizon_È() {
                this.áˆºÑ¢Õ().add(new UILabel(UIFonts.Ý, "KillAura"));
                final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
                final KillAura aura = (KillAura)ModuleManager.HorizonCode_Horizon_È(KillAura.class);
                this.áˆºÑ¢Õ().add(new ItemCheckBox("Swing") {
                    @Override
                    public void HorizonCode_Horizon_È() {
                        KillAura.á = this.à();
                    }
                }.HorizonCode_Horizon_È(KillAura.á));
                this.áˆºÑ¢Õ().add(new ItemCheckBox("AutoSword") {
                    @Override
                    public void HorizonCode_Horizon_È() {
                        KillAura.£á = this.à();
                    }
                }.HorizonCode_Horizon_È(KillAura.£á));
                this.áˆºÑ¢Õ().add(new ItemCheckBox("Players") {
                    @Override
                    public void HorizonCode_Horizon_È() {
                        KillAura.Ø­áŒŠá = this.à();
                    }
                }.HorizonCode_Horizon_È(KillAura.Ø­áŒŠá));
                this.áˆºÑ¢Õ().add(new ItemCheckBox("Mobs") {
                    @Override
                    public void HorizonCode_Horizon_È() {
                        KillAura.Ý = this.à();
                    }
                }.HorizonCode_Horizon_È(KillAura.Ý));
                this.áˆºÑ¢Õ().add(new ItemCheckBox("Animals") {
                    @Override
                    public void HorizonCode_Horizon_È() {
                        KillAura.Âµá€ = this.à();
                    }
                }.HorizonCode_Horizon_È(KillAura.Âµá€));
                this.áˆºÑ¢Õ().add(new ItemCheckBox("AutoBlock") {
                    @Override
                    public void HorizonCode_Horizon_È() {
                        KillAura.ˆÏ­ = this.à();
                    }
                }.HorizonCode_Horizon_È(KillAura.ˆÏ­));
                this.áˆºÑ¢Õ().add(new ItemSlider("Range", 10.0f) {
                    @Override
                    public void HorizonCode_Horizon_È() {
                        KillAura.Ø = this.à();
                    }
                }.HorizonCode_Horizon_È((float)(KillAura.Ø / 10.0)));
                this.áˆºÑ¢Õ().add(new ItemCheckBox("Random Delay") {
                    @Override
                    public void HorizonCode_Horizon_È() {
                        Horizon.áƒ = this.à();
                    }
                }.HorizonCode_Horizon_È(Horizon.áƒ));
                this.áˆºÑ¢Õ().add(new ItemSlider("AttackDelay", 200.0f) {
                    @Override
                    public void HorizonCode_Horizon_È() {
                        Horizon.á€ = (long)this.à();
                    }
                }.HorizonCode_Horizon_È(Horizon.á€ / 200L));
            }
        });
        final Panel settings;
        this.HorizonCode_Horizon_È.add(settings = new Panel("Settings", null, 104, 126, GuiDragUI.Çªà¢, GuiDragUI.Ê, false, false) {
            @Override
            public void HorizonCode_Horizon_È() {
                this.áˆºÑ¢Õ().add(new ItemButtonV2(killsettings));
                this.áˆºÑ¢Õ().add(new ItemButtonV2(glidesettings));
                this.áˆºÑ¢Õ().add(new ItemButtonV2(stepsettings));
                this.áˆºÑ¢Õ().add(new ItemButtonV2(speedsettings));
                this.áˆºÑ¢Õ().add(new ItemButtonV2(laddersettings));
                this.áˆºÑ¢Õ().add(new ItemButtonV2(phasesettings));
                this.áˆºÑ¢Õ().add(new ItemButtonV2(zootsettings));
                this.áˆºÑ¢Õ().add(new ItemButtonV2(extendsettings));
                this.áˆºÑ¢Õ().add(new ItemButtonV2(nametagssettings));
            }
        });
        final Panel style;
        this.HorizonCode_Horizon_È.add(style = new StylePanel(206, 126, GuiDragUI.Çªà¢, GuiDragUI.Ê, false, false));
        this.HorizonCode_Horizon_È.add(new Panel("Hub Panel", null, 2, 38, GuiDragUI.Çªà¢, GuiDragUI.Ê, false, true) {
            @Override
            public void HorizonCode_Horizon_È() {
                this.áˆºÑ¢Õ().add(new ItemButtonV2(player));
                this.áˆºÑ¢Õ().add(new ItemButtonV2(movement));
                this.áˆºÑ¢Õ().add(new ItemButtonV2(combat));
                this.áˆºÑ¢Õ().add(new ItemButtonV2(render));
                this.áˆºÑ¢Õ().add(new ItemButtonV2(server));
                this.áˆºÑ¢Õ().add(new ItemButtonV2(minigames));
                this.áˆºÑ¢Õ().add(new ItemButtonV2(settings));
                this.áˆºÑ¢Õ().add(new ItemButtonV2(style));
            }
        });
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int i, final int j, final float k) {
        Gui_1808253012.HorizonCode_Horizon_È(0, 0, GuiDragUI.Çªà¢, GuiDragUI.Ê, 536870912, -1879048192);
        for (final Panel panel : this.HorizonCode_Horizon_È) {
            panel.HorizonCode_Horizon_È(i, j, k);
        }
        super.HorizonCode_Horizon_È(i, j, k);
    }
    
    public void HorizonCode_Horizon_È(final int i, final int j, final int k) {
        for (final Panel panel : this.HorizonCode_Horizon_È) {
            panel.HorizonCode_Horizon_È(i, j, k);
        }
        try {
            super.HorizonCode_Horizon_È(i, j, k);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void Â(final int i, final int j, final int k) {
        for (final Panel panel : this.HorizonCode_Horizon_È) {
            panel.Â(i, j, k);
        }
        super.Â(i, j, k);
    }
    
    @Override
    public boolean Ø­áŒŠá() {
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.ÇŽÉ.add(new GuiMainMenuButton(1, GuiDragUI.Çªà¢ - 100, GuiDragUI.Ê - 20, 100, 20, "Reset"));
        super.HorizonCode_Horizon_È();
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        switch (button.£à) {
            case 1: {
                GuiDragUI.Ñ¢á.HorizonCode_Horizon_È((GuiScreen)null);
                final File f = new File(Horizon.à¢.áŒŠ, "panel.ini");
                f.delete();
                Horizon.à¢.Ñ¢Â.Â();
                Horizon.à¢.Ä = null;
                final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
                ModuleManager.HorizonCode_Horizon_È(Gui.class).ˆÏ­();
                break;
            }
        }
    }
}
