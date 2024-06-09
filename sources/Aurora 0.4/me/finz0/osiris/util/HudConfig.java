package me.finz0.osiris.util;

import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.gui.editor.anchor.AnchorPoint;
import me.finz0.osiris.gui.editor.component.DraggableHudComponent;
import me.finz0.osiris.gui.editor.component.HudComponent;

import java.io.*;

public final class HudConfig extends Configurable {

    public static boolean FIRST_HUD_RUN = true;

    public HudConfig() {
        super(AuroraMod.getInstance().configUtils.Aurora + "Hud.cfg");
    }

    @Override
    public void load() {
        try {
            final File file = new File(this.getPath());

            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            final BufferedReader reader = new BufferedReader(new FileReader(this.getPath()));

            String line;
            while ((line = reader.readLine()) != null) {
                final String[] split = line.split(":");

                if (split[0].equals("FIRST_HUD_RUN")) {
                    FIRST_HUD_RUN = Boolean.valueOf(split[1]);
                    continue;
                }

                final HudComponent component = AuroraMod.getInstance().hudManager.findComponent(split[0]);
                if (component != null) {
                    if (!split[1].equals("")) {
                        component.setX(Float.valueOf(split[1]));
                    }
                    if (!split[2].equals("")) {
                        component.setY(Float.valueOf(split[2]));
                    }
                    if (!split[3].equals("")) {
                        component.setVisible(Boolean.valueOf(split[3]));
                    }
                    if (!split[4].equals("")) {
                        final DraggableHudComponent draggable = (DraggableHudComponent) component;
                        if (!split[4].equals("NULL_ANCHOR")) {
                            for (AnchorPoint anchorPoint : AuroraMod.getInstance().hudManager.getAnchorPoints()) {
                                if (anchorPoint.getPoint().equals(AnchorPoint.Point.valueOf(split[4]))) {
                                    draggable.setAnchorPoint(anchorPoint);
                                }
                            }
                        }
                        if (!split[5].equals("NULL_GLUED") && !split[6].equals("NULL_GLUE_SIDE")) {
                            draggable.setGlued((DraggableHudComponent) AuroraMod.getInstance().hudManager.findComponent(split[5]));
                            draggable.setGlueSide(DraggableHudComponent.GlueSide.valueOf(split[6]));
                        }
                    }
                }
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() {
        try {
            final File file = new File(this.getPath());

            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            final BufferedWriter writer = new BufferedWriter(new FileWriter(this.getPath()));

            writer.write("FIRST_HUD_RUN" + ":" + FIRST_HUD_RUN);
            writer.newLine();

            if (AuroraMod.getInstance().hudManager.getComponentList() != null) {
                for (HudComponent component : AuroraMod.getInstance().hudManager.getComponentList()) {
                    writer.write(component.getName() + ":" + component.getX() + ":" + component.getY() + ":" + component.isVisible());
                    if (component instanceof DraggableHudComponent) {
                        final DraggableHudComponent draggable = (DraggableHudComponent) component;

                        // Anchor Point
                        if (draggable.getAnchorPoint() != null) {
                            writer.write(":" + draggable.getAnchorPoint().getPoint().name());
                        } else {
                            writer.write(":" + "NULL_ANCHOR");
                        }

                        // Glued
                        if (draggable.getGlued() != null) {
                            writer.write(":" + draggable.getGlued().getName() + ":" + draggable.getGlueSide());
                        } else {
                            writer.write(":" + "NULL_GLUED" + ":" + "NULL_GLUE_SIDE");
                        }
                    }
                    writer.newLine();
                }
            }

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
