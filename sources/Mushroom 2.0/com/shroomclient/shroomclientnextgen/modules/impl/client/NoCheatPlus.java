package com.shroomclient.shroomclientnextgen.modules.impl.client;

import com.shroomclient.shroomclientnextgen.config.ConfigHide;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.*;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.RenderUtil;
import com.shroomclient.shroomclientnextgen.util.TargetUtil;
import com.shroomclient.shroomclientnextgen.util.ThemeUtil;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.Getter;
import net.minecraft.block.AirBlock;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

@RegisterModule(
    name = "Anti Cheat",
    description = "Detects Other Hackers",
    uniqueId = "ncp",
    category = ModuleCategory.Client
)
public class NoCheatPlus extends Module {

    @ConfigHide
    @ConfigOption(
        name = "X Position",
        description = "Open Chat And Drag It.",
        min = 1,
        max = 1000,
        order = 11
    )
    public static Integer xPos = 8;

    @ConfigHide
    @ConfigOption(
        name = "Y Position",
        description = "Open Chat And Drag It.",
        min = 1,
        max = 1000,
        order = 12
    )
    public static Integer yPos = 35;

    public static ArrayList<PlayersChecked> failurePeople = new ArrayList<>();

    @ConfigOption(
        name = "Show Table",
        description = "Shows List Of Hackers",
        order = 0.1
    )
    public Boolean table = false;

    @ConfigOption(
        name = "Only Flag Once",
        description = "Only Sends One Chat Notif Per Person, Per Flag",
        order = 1
    )
    public Boolean onlyOneFlag = false;

    @ConfigOption(
        name = "Glow",
        description = "Renders A Glow Around The Table",
        order = 2
    )
    public Boolean glow = true;

    @ConfigOption(
        name = "Only Flag High VL",
        description = "Some Checks Can Silent Flag",
        order = 3
    )
    public Boolean onlyFlagHighVl = true;

    @ConfigOption(
        name = "No Slow",
        description = "Checks For No Slow Use",
        order = 4
    )
    public Boolean getNoSlow = true;

    @ConfigOption(
        name = "Auto Block",
        description = "Checks For Auto Block",
        order = 5
    )
    public Boolean getAB = true;

    @ConfigOption(
        name = "Scaffold",
        description = "Detects Scaffold",
        order = 6
    )
    public Boolean ScaffoldCheck = false;

    @ConfigOption(
        name = "Kill Aura",
        description = "Checks For Kill Aura",
        order = 7
    )
    public Boolean killauraCheck = false;

    @ConfigOption(
        name = "No Fall",
        description = "Checks For Blink No Fall",
        order = 9
    )
    public Boolean noFall = false;

    @ConfigOption(
        name = "Anti-Cheat",
        description = "What The Flags Look Like In Chat",
        order = 10
    )
    public Antihake mode = Antihake.NCP;

    boolean dragging = false;
    double mouseXold = 0;
    double mouseYold = 0;
    double oldHudX = 0;
    double oldHudY = 0;
    int highVLnumber = 10;

    // hashmap $$$
    public static final HashMap<Integer, EntityData> entityData =
        new HashMap<>();

    @SubscribeEvent
    public void motionPre(MotionEvent.Pre e) {
        if (C.w() == null) return;

        for (AbstractClientPlayerEntity player : C.w().getPlayers()) {
            if (
                TargetUtil.isBot(player) ||
                player.getId() == C.p().getId() ||
                player.age < 50 ||
                C.p().age < 50
            ) continue;

            double xVelo = player.getX() - player.lastRenderX;
            double yVelo = player.getY() - player.lastRenderY;
            double zVelo = player.getZ() - player.lastRenderZ;

            // crazy noslow check 2024
            // for some reason when using mushroom we stay sprint even with noslow and sprint off when blocking but whatever
            if (
                getNoSlow && player.isSprinting() && player.isUsingItem()
            ) addVL(player.getName().getString(), 1f, "No Slow", player);

            // always gets the hakors
            if (
                getAB &&
                player.isUsingItem() &&
                player.handSwingProgress > 0 &&
                player.handSwingProgress <= 0.2 &&
                player.hurtTime != 0
            ) addVL(player.getName().getString(), 1f, "Autoblock-B", player);
            else if (
                getAB &&
                player.isUsingItem() &&
                player.handSwingProgress > 0 &&
                player.handSwingProgress <= 0.2 &&
                player.isSprinting()
            ) addVL(player.getName().getString(), 2f, "Autoblock-A", player);

            if (
                noFall &&
                yVelo < -8 &&
                xVelo < 3 &&
                zVelo < 3 &&
                zVelo > -3 &&
                xVelo > -3
            ) {
                addVL(
                    player.getName().getString(),
                    Math.round(yVelo * -1),
                    "NoFall-A",
                    player
                );
            }

            Item heldItem = player.getHandItems().iterator().next().getItem();
            EntityData data = entityData.get(player.getId());

            if (data == null) {
                entityData.put(player.getId(), new EntityData(player));
            } else {
                entityData.get(player.getId()).update();
            }

            // speed check for bridging
            if (heldItem instanceof BlockItem) {
                if (data != null && ScaffoldCheck) {
                    if (data.voidTicks > 6) {
                        // 6 bps scaffold is cold.
                        addVL(
                            player.getName().getString(),
                            3f,
                            "Scaffold-SPRINT",
                            player
                        );
                    } else if (data.voidTicks > 4) {
                        addVL(
                            player.getName().getString(),
                            1f,
                            "Scaffold-A",
                            player
                        );
                    }
                }
            } else {
                // very good check!!! can false flag high sense people
                if (
                    killauraCheck &&
                    (player.getYaw() - player.prevYaw > 60 ||
                        player.getYaw() - player.prevYaw < -60)
                ) addVL(
                    player.getName().getString(),
                    0.3f,
                    "Killaura-A",
                    player
                );
                // these checks bad (dont really detect often and false way too often)
                //else if (killauraCheck && pitchChange > 0 && pitchChange <= 0.01 && player.handSwinging)
                //    addVL(player.getName().getString(), (float) (0.1-(pitchChange)) * 3, "KILLAURA-B", player);
                //else if (killauraCheck && yawChange > 0 && yawChange <= 0.01 && player.handSwinging)
                //    addVL(player.getName().getString(), (float) (0.1-(yawChange)) * 3, "KILLAURA-C", player);
            }
            //C.p().sendMessage(Text.of(""+));
        }
    }

    @SubscribeEvent
    public void ChatEvent(ScreenClickedEvent e) {
        if (C.mc.currentScreen instanceof ChatScreen) {
            float[] size = antihakeSquare();

            if (
                e.mouseX >= xPos &&
                e.mouseX <= xPos + size[2] &&
                e.mouseY >= yPos &&
                e.mouseY <= yPos + size[3]
            ) {
                dragging = e.button == 0;
                mouseXold = e.mouseX;
                mouseYold = e.mouseY;

                oldHudX = xPos;
                oldHudY = yPos;
            }

            if (!e.down && e.button == 0) {
                dragging = false;
            }
        }
    }

    @SubscribeEvent
    public void onDrawScreen(RenderScreenEvent e) {
        if (C.mc.currentScreen instanceof ChatScreen) {
            float[] square = antihakeSquare();

            double mouseMovedX = e.mouseX - mouseXold;
            double mouseMovedY = e.mouseY - mouseYold;

            if (dragging) {
                xPos = (int) (oldHudX + mouseMovedX);
                yPos = (int) (oldHudY + mouseMovedY);

                RenderUtil.drawRoundedGlow(
                    square[0],
                    square[1],
                    square[2],
                    square[3],
                    5,
                    5,
                    new Color(255, 255, 255),
                    200,
                    false,
                    false,
                    false,
                    false
                );
            }
        }
    }

    public float[] antihakeSquare() {
        String titleText = "§lMushroom Anticheat Flags";
        float widest = RenderUtil.getFontWidth(titleText);
        int height = 0;

        for (PlayersChecked people : failurePeople) {
            String fails = String.valueOf(people.failedChecks.size());
            //for (String failed : people.failedChecks) fails += failed + ", ";
            //fails = fails.substring(0, fails.length() - 2);

            String peopleString =
                people.name +
                ": failed " +
                fails +
                " checks VL: " +
                ((int) people.vlLevel);

            if (people.vlLevel < 1) continue;

            height++;

            if (RenderUtil.getFontWidth(peopleString) > widest) widest =
                RenderUtil.getFontWidth(peopleString);
        }

        height = height * 15 + 20;

        return new float[] { xPos, yPos, widest + 10, height };
    }

    @SubscribeEvent
    public void onRenderTick(RenderTickEvent e) {
        if (!C.isInGame()) return;
        if (!table) return;

        if (!failurePeople.isEmpty()) {
            failurePeople = (ArrayList<PlayersChecked>) failurePeople
                .stream()
                .sorted(Comparator.comparingDouble(n -> n.vlLevel))
                .collect(Collectors.toList());

            RenderUtil.setContext(e.drawContext);

            String titleText = "§lMushroom Anticheat Flags";
            float widest = RenderUtil.getFontWidth(titleText);
            int height = 0;

            for (PlayersChecked people : failurePeople) {
                String fails = String.valueOf(people.failedChecks.size());
                //for (String failed : people.failedChecks) fails += failed + ", ";
                //fails = fails.substring(0, fails.length() - 2);

                String peopleString =
                    people.name +
                    ": failed " +
                    fails +
                    " checks VL: " +
                    ((int) people.vlLevel);

                if (people.vlLevel < 5) continue;

                height++;

                if (RenderUtil.getFontWidth(peopleString) > widest) widest =
                    RenderUtil.getFontWidth(peopleString);
            }

            if (height < 1) return;

            RenderUtil.drawRoundedRect2(
                xPos,
                yPos,
                widest + 10,
                height * 15 + 20,
                5,
                new Color(23, 23, 23, 100),
                false,
                false,
                false,
                false
            );
            if (glow) RenderUtil.drawRoundedGlow(
                xPos,
                yPos,
                widest + 10,
                height * 15 + 20,
                5,
                5,
                ThemeUtil.themeColors()[0],
                false,
                false,
                false,
                false
            );
            RenderUtil.drawTextShadow(
                "§lMushroom Anticheat Flags",
                (int) (xPos +
                    5 +
                    (widest / 2f -
                        RenderUtil.getFontWidth("§lMushroom Anticheat Flags") /
                            2f)),
                yPos + 4,
                ThemeUtil.themeColors()[0]
            );

            int p = 0;
            for (PlayersChecked person : failurePeople) {
                if (person.vlLevel < 5) continue;

                String fails = String.valueOf(person.failedChecks.size());
                //for (String failed : person.failedChecks) fails += failed + ", ";
                //fails = fails.substring(0, fails.length() - 2);

                // wild $$$$
                String[] colorGrad = {
                    "a",
                    "2",
                    "e",
                    "e",
                    "6",
                    "6",
                    "6",
                    "c",
                    "c",
                    "c",
                };

                int intVL = (int) person.vlLevel;
                String peopleString =
                    person.name +
                    ": §cfailed " +
                    fails +
                    " checks §5VL: §" +
                    (intVL < colorGrad.length
                            ? colorGrad[intVL] + intVL
                            : "4" + intVL);

                RenderUtil.drawText(
                    peopleString,
                    xPos + 5,
                    (p * 15) + yPos + 21,
                    new Color(255, 255, 255)
                );

                p++;
            }
        }
    }

    // not very smart ik
    public void addVL(
        String playerName,
        float vlAdd,
        String failedCheck,
        PlayerEntity entity
    ) {
        ArrayList<String> failedChecks = new ArrayList<>();
        for (PlayersChecked people : failurePeople) {
            if (Objects.equals(playerName, people.name)) {
                failurePeople.remove(
                    new PlayersChecked(
                        people.name,
                        people.vlLevel,
                        people.failedChecks
                    )
                );
                failedChecks = people.failedChecks;

                if (
                    (!onlyOneFlag ||
                        (onlyFlagHighVl &&
                            people.vlLevel + vlAdd > highVLnumber &&
                            people.vlLevel < highVLnumber)) &&
                    (!onlyFlagHighVl || people.vlLevel + vlAdd > highVLnumber)
                ) C.p()
                    .sendMessage(
                        Text.of(
                            getFailMessage(
                                failedCheck,
                                playerName,
                                vlAdd,
                                people.vlLevel,
                                entity
                            )
                        )
                    );

                if (!people.failedChecks.contains(failedCheck)) {
                    if (
                        onlyOneFlag &&
                        (!onlyFlagHighVl ||
                            people.vlLevel + vlAdd > highVLnumber)
                    ) C.p()
                        .sendMessage(
                            Text.of(
                                getFailMessage(
                                    failedCheck,
                                    playerName,
                                    vlAdd,
                                    people.vlLevel,
                                    entity
                                )
                            )
                        );
                    failedChecks.add(failedCheck);
                }

                if (
                    people.vlLevel + vlAdd >= highVLnumber &&
                    people.vlLevel < highVLnumber
                ) {
                    C.p()
                        .sendMessage(
                            Text.of(
                                getFailMessage(
                                    "Combined Improbable",
                                    playerName,
                                    0,
                                    highVLnumber,
                                    entity
                                )
                            )
                        );
                }

                failurePeople.add(
                    new PlayersChecked(
                        people.name,
                        people.vlLevel + vlAdd,
                        failedChecks
                    )
                );
                return;
            }
        }
        failedChecks.add(failedCheck);

        if (!onlyFlagHighVl) C.p()
            .sendMessage(
                Text.of(
                    getFailMessage(failedCheck, playerName, vlAdd, 0, entity)
                )
            );
        failurePeople.add(new PlayersChecked(playerName, vlAdd, failedChecks));
    }

    public String getFailMessage(
        String failedCheck,
        String playerName,
        double vlAdd,
        double vlOld,
        PlayerEntity entity
    ) {
        return switch (mode) {
            case NCP -> "§cNCP §7> §f[" +
            failedCheck.toUpperCase().replaceAll(" ", "_") +
            "] [§e" +
            playerName +
            "§f] VL=" +
            (vlOld + vlAdd) +
            "(+" +
            vlAdd +
            ") §7health=" +
            entity.getHealth() +
            "/" +
            entity.getMaxHealth();
            case Verus -> "§9§lVerus §8> §f" +
            playerName +
            " §7failed §f" +
            failedCheck.replaceAll(" ", "") +
            " §7VL[§9" +
            ((int) (vlOld + vlAdd)) +
            "§7]";
            case Mushroom -> "§a[§2Mushroom§a] §c" +
            playerName +
            " §7failed §c" +
            failedCheck +
            " §7with VL of §4" +
            (vlOld + vlAdd);
        };
    }

    @Override
    protected void onEnable() {
        failurePeople.clear();
    }

    @Override
    protected void onDisable() {
        failurePeople.clear();
    }

    @SubscribeEvent
    public void onWorldLoad(WorldLoadEvent e) {
        failurePeople.clear();
    }

    public enum Antihake {
        NCP,
        Mushroom,
        Verus,
    }

    public record PlayersChecked(
        String name,
        float vlLevel,
        ArrayList<String> failedChecks
    ) {}

    public static class EntityData {

        // amount of times player has been over void in past 5 seconds
        @Getter
        private int voidTicks;

        private boolean lastVoid;
        private final Entity entity;

        private long lastVoidTickRemoved;

        public EntityData(final Entity entity) {
            this.entity = entity;
            this.update();
        }

        public void update() {
            if (isOverAir(this.entity)) {
                if (!this.lastVoid) {
                    this.voidTicks++;
                }
                this.lastVoid = true;
            } else {
                this.lastVoid = false;
            }

            // reset every 10 seconds
            if (
                this.voidTicks > 0 &&
                System.currentTimeMillis() - this.lastVoidTickRemoved > 1000
            ) {
                this.voidTicks = 0;
                this.lastVoidTickRemoved = System.currentTimeMillis();
            }
        }
    }

    public static boolean isOverAir(Entity entity) {
        double x = entity.getX();
        double y = entity.getY() - 1D;
        double z = entity.getZ();
        BlockPos p = new BlockPos(
            MathHelper.floor(x),
            MathHelper.floor(y),
            MathHelper.floor(z)
        );
        return C.w().getBlockState(p).getBlock() instanceof AirBlock;
    }
}
