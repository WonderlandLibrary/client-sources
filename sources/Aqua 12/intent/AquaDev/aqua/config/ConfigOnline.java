// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.config;

import java.io.IOException;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.gui.ConfigScreen;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import java.util.Scanner;
import java.net.URL;

public class ConfigOnline
{
    public void loadConfigOnline(final String name) {
        final String url = "https://aquaclient.github.io/configs/" + name.toLowerCase() + ".txt";
        try (final Scanner scanner = new Scanner(new URL(url).openConnection().getInputStream()).useDelimiter("\\A")) {
            while (scanner.hasNextLine()) {
                final String[] args = scanner.nextLine().split(" ");
                if (args[0].equalsIgnoreCase("MODULE")) {
                    if (args[1].equalsIgnoreCase("TOGGLE")) {
                        final String modName = args[2];
                        final boolean val = Boolean.parseBoolean(args[3]);
                        try {
                            Aqua.moduleManager.getModuleByName(modName).setState(val);
                        }
                        catch (Exception ex) {}
                    }
                    else {
                        if (!args[1].equalsIgnoreCase("SET")) {
                            continue;
                        }
                        final String settingName = args[2];
                        try {
                            final Setting.Type settingType = Setting.Type.valueOf(args[3]);
                            final String value = args[4];
                            if (!ConfigScreen.loadVisuals && Aqua.setmgr.getSetting(settingName).getModule().getCategory() == Category.Visual) {
                                continue;
                            }
                            switch (settingType) {
                                case BOOLEAN: {
                                    Aqua.setmgr.getSetting(settingName).setState(Boolean.parseBoolean(value));
                                }
                                case NUMBER: {
                                    Aqua.setmgr.getSetting(settingName).setCurrentNumber(Double.parseDouble(value));
                                }
                                case STRING: {
                                    Aqua.setmgr.getSetting(settingName).setCurrentMode(value);
                                }
                                case COLOR: {
                                    Aqua.setmgr.getSetting(settingName).color = Integer.parseInt(value);
                                    continue;
                                }
                            }
                        }
                        catch (Exception ex2) {}
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
