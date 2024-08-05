package fr.dog.module.impl.render;

import fr.dog.Dog;
import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.render.Render2DEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.property.impl.BooleanProperty;
import fr.dog.property.impl.ModeProperty;
import fr.dog.property.impl.NumberProperty;
import fr.dog.theme.Theme;
import fr.dog.util.player.MoveUtil;
import fr.dog.util.render.ColorUtil;
import fr.dog.util.render.RenderUtil;
import fr.dog.util.render.font.Fonts;
import fr.dog.util.render.font.TTFFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;



public class HUD extends Module {
    private final BooleanProperty watermark = BooleanProperty.newInstance("Watermark", true);
    private final BooleanProperty arraylist = BooleanProperty.newInstance("Arraylist", true);
    private final BooleanProperty potions = BooleanProperty.newInstance("Potions", true);
    private final BooleanProperty fps = BooleanProperty.newInstance("FPS Counter", true);
    private final BooleanProperty bps = BooleanProperty.newInstance("BPS Counter", true);
    private final ModeProperty watermarkMode = ModeProperty.newInstance("Watermark Mode", new String[]{"Monoxide", "Text", "Icon", "Ouuuuu","exhi"}, "Text", watermark::getValue);
    private final NumberProperty fontSize = NumberProperty.newInstance("Font Size", 5f, 20f, 40f, 1f, arraylist::getValue);
    private final ModeProperty font = ModeProperty.newInstance("Font", new String[]{"OpenSans Medium", "SanFrancisco", "Roboto Medium", "Minecraft", "Stratum2 Medium","CS"}, "OpenSans Medium", arraylist::getValue);
    final BooleanProperty background = BooleanProperty.newInstance("Background", true, arraylist::getValue);
    private final NumberProperty backgrounoppacity = NumberProperty.newInstance("Background Opacity", 0f, 40f, 100f, 0.1f, background::getValue);
    final BooleanProperty sidebar = BooleanProperty.newInstance("Side Bar", true, arraylist::getValue);
    private final BooleanProperty render = BooleanProperty.newInstance("Hide Visual Category", true, arraylist::getValue);
    private final BooleanProperty player = BooleanProperty.newInstance("Hide Player Category", false, arraylist::getValue);
    public final BooleanProperty glow = BooleanProperty.newInstance("Client Glow", false);
    public final BooleanProperty blur = BooleanProperty.newInstance("Client Blur", false);
    private List<Module> arrayListModule = new ArrayList<>();
    //Find a better way to do it plz
    private String oldFont = "";
    private float oldFontSize = 0;
    public static int scoreboardOffset = 0;
    private TTFFontRenderer arrayListFont;

    public HUD() {
        super("HUD", ModuleCategory.RENDER);

        this.registerProperties(watermark, watermarkMode, arraylist,fontSize, font, sidebar,render, player, background, backgrounoppacity,fps,bps,potions,glow,blur);
    }

    @Override
    protected void onEnable() {
        super.onEnable();
        setFont();
        setArraylist();
    }

    public void setArraylist(){
        if(arrayListFont == null)
            return;

        arrayListModule = Dog.getInstance().getModuleManager().getObjects().stream()
                .sorted(Comparator.comparingDouble(module -> {
                    String text = module.getCustomName();

                    if (!module.getSuffix().isEmpty()) {
                        text += " §f" + module.getSuffix();
                    }

                    return -arrayListFont.getWidth(text);
                })).toList();
    }

    private void setFont(){
        arrayListFont = switch (font.getValue().toLowerCase()) {
            case "opensans medium" -> Fonts.getOpenSansMedium(fontSize.getValue().intValue());
            case "sanfrancisco" -> Fonts.getSanFrancisco(fontSize.getValue().intValue());
            case "roboto medium" -> Fonts.getRobotoMedium(fontSize.getValue().intValue());
            case "minecraft" -> Fonts.getMinecraft(fontSize.getValue().intValue());
            case "stratum2 medium" -> Fonts.getStratum2Medium(fontSize.getValue().intValue());
            case "cs" -> Fonts.getESPIcons(fontSize.getValue().intValue());
            case"mojang" -> Fonts.getMojang(fontSize.getValue().intValue());
            default -> null;
        };
    }

    @SubscribeEvent
    private void onRender2D(Render2DEvent event) {
        String info = Dog.getInstance().getUsername() + " §7[ID: " + Dog.getInstance().getUid() + "]";

        ScaledResolution sr = new ScaledResolution(mc);

        TTFFontRenderer infoFont = Fonts.getOpenSansRegular(18);
        infoFont.drawStringWithShadow(info, sr.getScaledWidth() - infoFont.getWidth(info) - 5, sr.getScaledHeight() - infoFont.getHeight(info) - 5, Color.WHITE.getRGB());

        if (watermark.getValue()) {
            TTFFontRenderer logoFont = Fonts.getOpenSansBold(24);

            switch (watermarkMode.getValue().toLowerCase()) {
                case "monoxide": {
                    Theme theme = Dog.getInstance().getThemeManager().getCurrentTheme();

                    String color = theme.chatFormatting.toString();

                    String text = String.format("§fDog%sSense §8| §7%s §f(%s) §8| §f%s",
                            color, Dog.getInstance().getUsername(), Dog.getInstance().getUid(), mc.isIntegratedServerRunning() ? "Singleplayer" : mc.getCurrentServerData().serverIP);

                    TTFFontRenderer skeetFont = Fonts.getRobotoMedium(18);

                    RenderUtil.drawRect(4.5f, 4.5f, skeetFont.getWidth(text) + 9, 18.5f, new Color(59, 59, 59));
                    RenderUtil.drawRect(5.5f, 5.5f, skeetFont.getWidth(text) + 7, 16.5f, new Color(59, 59, 59).darker());
                    RenderUtil.drawRect(7f, 7f, skeetFont.getWidth(text) + 4, 13f, new Color(25, 25, 25));
                    RenderUtil.horizontalGradient(7f, 19.5f, skeetFont.getWidth(text) + 4, 1f, theme.getColor(3, 0), theme.getColor(3, 250));

                    skeetFont.drawStringWithShadow(text, 10, 10, Color.WHITE.getRGB());
                    break;
                }
                case "text":
                    logoFont.drawStringWithShadow("Dog Client", 5, 5, Dog.getInstance().getThemeManager().getCurrentTheme().getColor1().getRGB());
                    break;
                case "icon":
                    RenderUtil.drawImage(new ResourceLocation("dogclient/icons/doglogo_official.png"), 5, 5, 64, 64);
                    break;
                case "ouuuuu":
                    RenderUtil.drawImage(new ResourceLocation("dogclient/icons/doglogo_v2.png"), 5, 5, 64, 64);
                    break;
                case "exhi":
                    mc.fontRendererObj.drawStringWithShadow('E' + "§7" + "Exhibition".substring(1) + " §7[§f1.8.x§7] " + "§7[§f" + Minecraft.getDebugFPS() + " FPS§7]", 3, 6, ColorUtil.getRainbow(3.0f, 1.0f, 1.0f, System.currentTimeMillis()).getRGB());
                    break;
                case "custom":
                    logoFont.drawStringWithShadow("Coming Soon", 5, 5, Dog.getInstance().getThemeManager().getCurrentTheme().getColor1().getRGB());
                    break;
            }
        }

        DecimalFormat df = new DecimalFormat("#.##");

        String fpsText = "§8FPS §f" + Minecraft.getDebugFPS();
        String bpsText = "§8BPS §f" + df.format(MoveUtil.getSpeed(mc.thePlayer));

        if (fps.getValue()) {
            TTFFontRenderer fr = Fonts.getSanFrancisco(21);
            fr.drawStringWithShadow(fpsText, 5, sr.getScaledHeight() - fr.getHeight(fpsText) - 5, 0xFFFFFFFF);
        }

        if (bps.getValue()) {
            TTFFontRenderer fr = Fonts.getSanFrancisco(21);
            fr.drawStringWithShadow(bpsText, 5, sr.getScaledHeight() - fr.getHeight(fpsText) - fr.getHeight(bpsText) - 5, 0xFFFFFFFF);
        }

        float offset = 0;
        if (arraylist.getValue()) {
            if(!oldFont.equals(font.getValue()) || oldFontSize != fontSize.getValue()) {
                setFont();
                setArraylist();
            }

            oldFontSize = fontSize.getValue();
            oldFont = font.getValue();

            for (Module module : arrayListModule) {
                module.animation.run(module.isEnabled() ? 1.0F : 0.0F);

                if (render.getValue() && module.getCategory() == ModuleCategory.RENDER)
                    continue;

                if (player.getValue() && module.getCategory() == ModuleCategory.PLAYER)
                    continue;

                if (!module.isEnabled() && !animation.isFinished())
                    continue;

                if (module.animation.getValue() == 0.0F)
                    continue;

                String text = module.getCustomName();

                if (!module.getSuffix().isEmpty()) {
                    text += " §f" + module.getSuffix();
                }

                Color color = Dog.getInstance().getThemeManager().getCurrentTheme().getColor(3, (int) offset * 2);

                if (background.getValue()) {
                    int opacity = (int) (backgrounoppacity.getValue() * 2.55);

                    RenderUtil.drawRect(
                            sr.getScaledWidth() - (arrayListFont.getWidth(text) + 7) * (float) (module.animation.getValue()), 5 + offset - 1,
                            arrayListFont.getWidth(text) + 4, arrayListFont.getHeight(text) + 2,
                            new Color(0, 0, 0, opacity));
                }

                if (sidebar.getValue()) {
                    RenderUtil.drawRect(
                            (float) (sr.getScaledWidth() - 3 * module.animation.getValue()), 5 + offset - 1,
                            1, arrayListFont.getHeight(text) + 2,
                            color);
                }

                arrayListFont.drawStringWithShadow(text,
                        sr.getScaledWidth() - (arrayListFont.getWidth(text) + 5) * (float) (module.animation.getValue()),
                        5 + offset,
                        color.getRGB()
                );

                offset += (arrayListFont.getHeight(text) + 2) * (float) module.animation.getValue();
            }
        }


        scoreboardOffset = (int) offset;

        if (potions.getValue()) {
            TTFFontRenderer potionFont = Fonts.getOpenSansMedium(17);

            List<PotionEffect> potions = new ArrayList<>(mc.thePlayer.getActivePotionEffects());
            potions.sort(Comparator.comparingDouble(e -> -potionFont.getWidth(I18n.format(e.getEffectName()))));

            int count = 0;
            for (PotionEffect effect : potions) {
                ++count;

                Potion potion = Potion.potionTypes[effect.getPotionID()];
                String name = I18n.format(potion.getName()) + (effect.getAmplifier() > 0 ? " " + (effect.getAmplifier() + 1) : "");

                Color color = new Color(potion.getLiquidColor());

                String str = name + " §7[" + Potion.getDurationString(effect) + "]";
                potionFont.drawStringWithShadow(str, sr.getScaledWidth() - potionFont.getWidth(str) - 5,
                        sr.getScaledHeight() - potionFont.getHeight(str) * count - 16,
                        new Color(color.getRed(), color.getGreen(), color.getBlue(), 255).getRGB());
            }
        }

    }
}
