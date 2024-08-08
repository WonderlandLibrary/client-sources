package me.zeroeightsix.kami.gui.kami;

import com.mojang.realmsclient.client.Ping;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.gui.kami.component.ActiveModules;
import me.zeroeightsix.kami.gui.kami.component.Radar;
import me.zeroeightsix.kami.gui.kami.component.SettingsPanel;
import me.zeroeightsix.kami.gui.kami.theme.kami.KamiTheme;
import me.zeroeightsix.kami.gui.rgui.GUI;
import me.zeroeightsix.kami.gui.rgui.component.container.use.Frame;
import me.zeroeightsix.kami.gui.rgui.component.container.use.Scrollpane;
import me.zeroeightsix.kami.gui.rgui.component.listen.MouseListener;
import me.zeroeightsix.kami.gui.rgui.component.listen.TickListener;
import me.zeroeightsix.kami.gui.rgui.component.use.CheckButton;
import me.zeroeightsix.kami.gui.rgui.component.use.Label;
import me.zeroeightsix.kami.gui.rgui.render.theme.Theme;
import me.zeroeightsix.kami.gui.rgui.util.ContainerHelper;
import me.zeroeightsix.kami.gui.rgui.util.Docking;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by 086 on 25/06/2017.
 * Updated 1 December 2019 by hub
 */
public class KamiGUI extends GUI {

    public static final RootFontRenderer fontRenderer = new RootFontRenderer(1);

    private static final int DOCK_OFFSET = 0;
    private static final String witherSkullText = TextFormatting.DARK_GRAY + "Wither Skull";
    private static final String enderCrystalText = TextFormatting.LIGHT_PURPLE + "End Crystal";
    private static final String thrownEnderPearlText = TextFormatting.LIGHT_PURPLE + "Thrown Ender Pearl";
    private static final String minecartText = "Minecart";
    private static final String itemFrameText = "Item Frame";
    private static final String thrownEggText = "Thrown Egg";
    private static final String thrownSnowballText = "Thrown Snowball";

    public static ColourHolder primaryColour = new ColourHolder(29, 29, 29);

    public Theme theme;

    public KamiGUI() {
        super(new KamiTheme());
        theme = getTheme();
    }

    private static String getEntityName(@Nonnull Entity entity) {
        if (entity instanceof EntityItem) {
            return TextFormatting.DARK_AQUA + ((EntityItem) entity).getItem().getItem().getItemStackDisplayName(((EntityItem) entity).getItem());
        }
        if (entity instanceof EntityWitherSkull) {
            return witherSkullText;
        }
        if (entity instanceof EntityEnderCrystal) {
            return enderCrystalText;
        }
        if (entity instanceof EntityEnderPearl) {
            return thrownEnderPearlText;
        }
        if (entity instanceof EntityMinecart) {
            return minecartText;
        }
        if (entity instanceof EntityItemFrame) {
            return itemFrameText;
        }
        if (entity instanceof EntityEgg) {
            return thrownEggText;
        }
        if (entity instanceof EntitySnowball) {
            return thrownSnowballText;
        }
        return entity.getName();
    }

    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list =
                new LinkedList<>(map.entrySet());
        list.sort(Comparator.comparing(Map.Entry::getValue));

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public static void dock(Frame component) {
        Docking docking = component.getDocking();
        if (docking.isTop()) {
            component.setY(DOCK_OFFSET);
        }
        if (docking.isBottom()) {
            component.setY((Wrapper.getMinecraft().displayHeight / DisplayGuiScreen.getScale()) - component.getHeight() - DOCK_OFFSET);
        }
        if (docking.isLeft()) {
            component.setX(DOCK_OFFSET);
        }
        if (docking.isRight()) {
            component.setX((Wrapper.getMinecraft().displayWidth / DisplayGuiScreen.getScale()) - component.getWidth() - DOCK_OFFSET);
        }
        if (docking.isCenterHorizontal()) {
            component.setX((Wrapper.getMinecraft().displayWidth / (DisplayGuiScreen.getScale() * 2) - component.getWidth() / 2));
        }
        if (docking.isCenterVertical()) {
            component.setY(Wrapper.getMinecraft().displayHeight / (DisplayGuiScreen.getScale() * 2) - component.getHeight() / 2);
        }
    }

    @Override
    public void drawGUI() {
        super.drawGUI();
    }

    @Override
    public void initializeGUI() {
        HashMap<Module.Category, Pair<Scrollpane, SettingsPanel>> categoryScrollpaneHashMap = new HashMap<>();
        for (Module module : ModuleManager.getModules()) {
            if (module.getCategory().isHidden()) {
                continue;
            }
            Module.Category moduleCategory = module.getCategory();
            if (!categoryScrollpaneHashMap.containsKey(moduleCategory)) {
                Stretcherlayout stretcherlayout = new Stretcherlayout(1);
                stretcherlayout.setComponentOffsetWidth(0);
                Scrollpane scrollpane = new Scrollpane(getTheme(), stretcherlayout, 300, 260);
                scrollpane.setMaximumHeight(180);
                categoryScrollpaneHashMap.put(moduleCategory, new Pair<>(scrollpane, new SettingsPanel(getTheme(), null)));
            }

            Pair<Scrollpane, SettingsPanel> pair = categoryScrollpaneHashMap.get(moduleCategory);
            Scrollpane scrollpane = pair.getKey();
            CheckButton checkButton = new CheckButton(module.getName());
            checkButton.setToggled(module.isEnabled());

            checkButton.addTickListener(() -> { // dear god
                checkButton.setToggled(module.isEnabled());
                checkButton.setName(module.getName());
            });

            checkButton.addMouseListener(new MouseListener() {
                @Override
                public void onMouseDown(MouseButtonEvent event) {
                    if (event.getButton() == 1) { // Right click
                        pair.getValue().setModule(module);
                        pair.getValue().setX(event.getX() + checkButton.getX());
                        pair.getValue().setY(event.getY() + checkButton.getY());
                    }
                }

                @Override
                public void onMouseRelease(MouseButtonEvent event) {

                }

                @Override
                public void onMouseDrag(MouseButtonEvent event) {

                }

                @Override
                public void onMouseMove(MouseMoveEvent event) {

                }

                @Override
                public void onScroll(MouseScrollEvent event) {

                }
            });
            checkButton.addPoof(new CheckButton.CheckButtonPoof<CheckButton, CheckButton.CheckButtonPoof.CheckButtonPoofInfo>() {
                @Override
                public void execute(CheckButton component, CheckButtonPoofInfo info) {
                    if (info.getAction().equals(CheckButton.CheckButtonPoof.CheckButtonPoofInfo.CheckButtonPoofInfoAction.TOGGLE)) {
                        module.setEnabled(checkButton.isToggled());
                    }
                }
            });
            scrollpane.addChild(checkButton);
        }

        int x = 10;
        int y = 10;
        int nexty = y;
        for (Map.Entry<Module.Category, Pair<Scrollpane, SettingsPanel>> entry : categoryScrollpaneHashMap.entrySet()) {
            Stretcherlayout stretcherlayout = new Stretcherlayout(1);
            stretcherlayout.COMPONENT_OFFSET_Y = 1;
            Frame frame = new Frame(getTheme(), stretcherlayout, entry.getKey().getName());
            Scrollpane scrollpane = entry.getValue().getKey();
            frame.addChild(scrollpane);
            frame.addChild(entry.getValue().getValue());
            scrollpane.setOriginOffsetY(0);
            scrollpane.setOriginOffsetX(0);
            frame.setCloseable(false);

            frame.setX(x);
            frame.setY(y);

            addChild(frame);

            nexty = Math.max(y + frame.getHeight() + 10, nexty);
            x += frame.getWidth() + 10;
            if (x > Wrapper.getMinecraft().displayWidth / 1.2f) {
                y = nexty;
                nexty = y;
            }
        }

        this.addMouseListener(new MouseListener() {
            private boolean isNotBetweenNullAnd(int val, int max) {
                return val > max || val < 0;
            }

            @Override
            public void onMouseDown(MouseButtonEvent event) {
                List<SettingsPanel> panels = ContainerHelper.getAllChildren(SettingsPanel.class, KamiGUI.this);
                for (SettingsPanel settingsPanel : panels) {
                    if (!settingsPanel.isVisible()) {
                        continue;
                    }
                    int[] real = GUI.calculateRealPosition(settingsPanel);
                    int pX = event.getX() - real[0];
                    int pY = event.getY() - real[1];
                    if (isNotBetweenNullAnd(pX, settingsPanel.getWidth()) || isNotBetweenNullAnd(pY, settingsPanel.getHeight())) {
                        settingsPanel.setVisible(false);
                    }
                }
            }

            @Override
            public void onMouseRelease(MouseButtonEvent event) {

            }

            @Override
            public void onMouseDrag(MouseButtonEvent event) {

            }

            @Override
            public void onMouseMove(MouseMoveEvent event) {

            }

            @Override
            public void onScroll(MouseScrollEvent event) {

            }
        });

        ArrayList<Frame> frames = new ArrayList<>();

        Frame frame = new Frame(getTheme(), new Stretcherlayout(1), "Active modules");
        frame.setCloseable(false);
        frame.addChild(new ActiveModules());
        frame.setPinneable(true);
        frames.add(frame);

        frame = new Frame(getTheme(), new Stretcherlayout(1), "Info");
        frame.setCloseable(false);
        frame.setPinneable(true);
        Label information = new Label("");
        information.setShadow(true);
        information.addTickListener(() -> {
            information.setText("");
            information.addLine(KamiMod.getInstance().guiManager.getTextColor() +"\u00A7fFPS "+ Wrapper.getMinecraft().debugFPS + ChatFormatting.WHITE.toString() + "");
            int ms = (Minecraft.getMinecraft().getCurrentServerData() == null ) ? 0 : (int)(Minecraft.getMinecraft().getCurrentServerData()).pingToServer;
            information.addLine("PING "+ms +Command.SECTIONSIGN()+"8");


        });
        frame.addChild(information);
        information.setFontRenderer(fontRenderer);
        frames.add(frame);

        frame = new Frame(getTheme(), new Stretcherlayout(1), "Text Radar");
        Label list = new Label("");
        DecimalFormat dfHealth = new DecimalFormat("#.#");
        dfHealth.setRoundingMode(RoundingMode.CEILING);
        StringBuilder healthSB = new StringBuilder();
        StringBuilder potsSB = new StringBuilder();
        list.addTickListener(() -> {
            if (!list.isVisible()) {
                return;
            }
            list.setText("");

            Minecraft mc = Wrapper.getMinecraft();

            if (mc.player == null) {
                return;
            }

            List<EntityPlayer> entityList = mc.world.playerEntities;

            Map<String, Integer> players = new HashMap<>();
            int playerStep = 0;

            for (Entity entity : entityList) {

                if (playerStep >= KamiMod.getInstance().guiManager.getTextRadarPlayers()) {
                    break;
                }

                if (entity.getName().equals(mc.player.getName())) {
                    continue;
                }

                EntityPlayer entityPlayer = (EntityPlayer) entity;

                String posString = (entityPlayer.posY > mc.player.posY ? ChatFormatting.DARK_GREEN.toString() + "+" : (entityPlayer.posY == mc.player.posY ? " " : ChatFormatting.DARK_RED.toString() + "-"));
                float hpRaw = entityPlayer.getHealth() + ((EntityLivingBase) entityPlayer).getAbsorptionAmount();
                String hp = dfHealth.format(hpRaw);

                if (hpRaw >= 20) {
                    healthSB.append(ChatFormatting.GREEN.toString());
                } else if (hpRaw >= 10) {
                    healthSB.append(ChatFormatting.YELLOW.toString());
                } else if (hpRaw >= 5) {
                    healthSB.append(ChatFormatting.GOLD.toString());
                } else {
                    healthSB.append(ChatFormatting.RED.toString());
                }
                healthSB.append(hp);
                healthSB.append(" ");

                if (KamiMod.getInstance().guiManager.isTextRadarPots()) {
                    PotionEffect effectStrength = entityPlayer.getActivePotionEffect(MobEffects.STRENGTH);
                    if (effectStrength != null && entityPlayer.isPotionActive(MobEffects.STRENGTH)) {
                        int duration = effectStrength.getDuration();
                        if (duration > 0) {
                            potsSB.append(ChatFormatting.RED);
                            potsSB.append(" S ");
                            potsSB.append(ChatFormatting.GRAY);
                            potsSB.append(Potion.getPotionDurationString(effectStrength, 1.0f));
                        }
                    }

                    PotionEffect effectweakness = entityPlayer.getActivePotionEffect(MobEffects.WEAKNESS);
                    if (effectweakness != null && entityPlayer.isPotionActive(MobEffects.WEAKNESS)) {
                        int duration = effectweakness.getDuration();
                        if (duration > 0) {
                            potsSB.append(ChatFormatting.GOLD);
                            potsSB.append(" W ");
                            potsSB.append(ChatFormatting.GRAY);
                            potsSB.append(Potion.getPotionDurationString(effectweakness, 1.0f));
                        }
                    }
                }

                String nameColor;
                if (Friends.isFriend(entity.getName())) {
                    nameColor = ChatFormatting.GREEN.toString();
                } else {
                    nameColor = ChatFormatting.GRAY.toString();
                }

                players.put(ChatFormatting.GRAY.toString() + posString + " " + healthSB.toString() + nameColor + entityPlayer.getName() + potsSB.toString(), (int) mc.player.getDistance(entityPlayer));

                healthSB.setLength(0);
                potsSB.setLength(0);

                playerStep++;

            }

            if (players.isEmpty()) {
                list.setText("");
                return;
            }

            players = sortByValue(players);

            for (Map.Entry<String, Integer> player : players.entrySet()) {
                list.addLine(player.getKey() + " " + ChatFormatting.DARK_GRAY.toString() + player.getValue());
            }

        });
        frame.setCloseable(false);
        frame.setPinneable(true);
        frame.setMinimumWidth(75);
        list.setShadow(true);
        frame.addChild(list);
        list.setFontRenderer(fontRenderer);
        frames.add(frame);

        frame = new Frame(getTheme(), new Stretcherlayout(1), "Entities");
        Label entityLabel = new Label("");
        frame.setCloseable(false);
        entityLabel.addTickListener(new TickListener() {
            Minecraft mc = Wrapper.getMinecraft();

            @Override
            public void onTick() {
                if (mc.player == null || !entityLabel.isVisible()) {
                    return;
                }

                final List<Entity> entityList = new ArrayList<>(mc.world.loadedEntityList);
                if (entityList.size() <= 1) {
                    entityLabel.setText("");
                    return;
                }
                final Map<String, Integer> entityCounts = entityList.stream()
                        .filter(Objects::nonNull)
                        .filter(e -> !(e instanceof EntityPlayer))
                        .collect(Collectors.groupingBy(KamiGUI::getEntityName,
                                Collectors.reducing(0, ent -> {
                                    if (ent instanceof EntityItem) {
                                        return ((EntityItem) ent).getItem().getCount();
                                    }
                                    return 1;
                                }, Integer::sum)
                        ));

                entityLabel.setText("");
                entityCounts.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue())
                        .map(entry -> TextFormatting.GRAY + entry.getKey() + " " + TextFormatting.DARK_GRAY + "x" + entry.getValue())
                        .forEach(entityLabel::addLine);

                //entityLabel.getParent().setHeight(entityLabel.getLines().length * (entityLabel.getTheme().getFontRenderer().getFontHeight()+1) + 3);
            }
        });
        frame.addChild(entityLabel);
        frame.setPinneable(true);
        entityLabel.setShadow(true);
        entityLabel.setFontRenderer(fontRenderer);
        frames.add(frame);

        frame = new Frame(getTheme(), new Stretcherlayout(1), "Coordinates");
        frame.setCloseable(false);
        frame.setPinneable(true);
        Label coordsLabel = new Label("");
        coordsLabel.addTickListener(new TickListener() {
            Minecraft mc = Minecraft.getMinecraft();

            @Override
            public void onTick() {
                boolean inHell = (mc.world.getBiome(mc.player.getPosition()).getBiomeName().equals("Hell"));

                int posX = (int) mc.player.posX;
                int posY = (int) mc.player.posY;
                int posZ = (int) mc.player.posZ;

                float f = !inHell ? 0.125f : 8;
                int hposX = (int) (mc.player.posX * f);
                int hposZ = (int) (mc.player.posZ * f);

                coordsLabel.setText(String.format(" %sf%,d%s7, %sf%,d%s7, %sf%,d %s7(%sf%,d%s7, %sf%,d%s7, %sf%,d%s7)",
                        Command.SECTIONSIGN(),
                        posX,
                        Command.SECTIONSIGN(),
                        Command.SECTIONSIGN(),
                        posY,
                        Command.SECTIONSIGN(),
                        Command.SECTIONSIGN(),
                        posZ,
                        Command.SECTIONSIGN(),
                        Command.SECTIONSIGN(),
                        hposX,
                        Command.SECTIONSIGN(),
                        Command.SECTIONSIGN(),
                        posY,
                        Command.SECTIONSIGN(),
                        Command.SECTIONSIGN(),
                        hposZ,
                        Command.SECTIONSIGN()
                ));
            }
        });
        frame.addChild(coordsLabel);
        coordsLabel.setFontRenderer(fontRenderer);
        coordsLabel.setShadow(true);
        frame.setHeight(20);
        frames.add(frame);

        frame = new Frame(getTheme(), new Stretcherlayout(1), "Radar");
        frame.setCloseable(false);
        frame.setMinimizeable(true);
        frame.setPinneable(true);
        frame.addChild(new Radar());
        frame.setWidth(100);
        frame.setHeight(100);
        frames.add(frame);

        frame = new Frame(getTheme(), new Stretcherlayout(1), "Greeter");
        frame.setCloseable(false);
        frame.setMinimizeable(true);
        frame.setPinneable(true);
        Label greeter = new Label("");
        greeter.addTickListener(() -> {
            if (Wrapper.getMinecraft().player == null) {
                return;
            }
            greeter.setText("");
            greeter.addLine(ChatFormatting.DARK_GRAY.toString() + "Welcome " + KamiMod.getInstance().guiManager.getTextColor() + Wrapper.getMinecraft().player.getDisplayNameString() + ChatFormatting.DARK_GRAY.toString() + " \u300c\u00b0\u0296\u00b0\u300d");
        });
        frame.addChild(greeter);
        greeter.setFontRenderer(fontRenderer);
        greeter.setShadow(true);
        frames.add(frame);



        for (Frame frame1 : frames) {
            frame1.setX(x);
            frame1.setY(y);

            nexty = Math.max(y + frame1.getHeight() + 10, nexty);
            x += frame1.getWidth() + 10;
            if (x * DisplayGuiScreen.getScale() > Wrapper.getMinecraft().displayWidth / 1.2f) {
                y = nexty;
                nexty = y;
                x = 10;
            }

            addChild(frame1);
        }

    }

    @Override
    public void destroyGUI() {
        kill();
    }

}
