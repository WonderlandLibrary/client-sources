/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client;

import dev.intave.viamcp.ViaMCP;
import ru.govno.client.Client;
import ru.govno.client.module.modules.Bypass;
import ru.govno.client.utils.Command.impl.Panic;

public class ClientBrandRetriever {
    public static boolean doHideTrueName() {
        return !Panic.stop && (Bypass.get == null || !Bypass.get.ClientSpoof.bValue);
    }

    public static String getTrueClientName() {
        return Client.name + " " + ClientBrandRetriever.getVersionStr() + "(" + Client.version + ")";
    }

    public static String getFalseClientPrefix() {
        return "Minecraft " + ClientBrandRetriever.getVersionStr() + " ";
    }

    public static String getVersionStr() {
        String str = ViaMCP.INSTANCE.getViaPanel().INSTANCE.getCurrentProtocol().getName();
        str = str.replace("/1", "").replace("/2", "").replace("/3", "").replace("/4", "").replace("/5", "").replace(".x", "");
        str = str.replace("1.16.4", "1.16.5").replace("1.8.x", "1.8.9");
        return str;
    }

    public static String getReleaseTypePrefix() {
        String str = "";
        if (ClientBrandRetriever.doHideTrueName()) {
            str = "ForgeOptifine ";
        } else {
            String versionStr;
            switch (versionStr = ViaMCP.INSTANCE.getViaPanel().INSTANCE.getCurrentProtocol().getName()) {
                case "1.13": 
                case "1.17": {
                    str = "Optifine ";
                    break;
                }
                case "1.15": 
                case "1.15.1": 
                case "1.16": 
                case "1.20.2": {
                    str = "";
                    break;
                }
                default: {
                    str = "ForgeOptifine ";
                }
            }
        }
        return str;
    }

    public static String getVersionSuffix() {
        String versionName = ViaMCP.INSTANCE.getViaPanel().INSTANCE.getCurrentProtocol().getName();
        String str = "";
        switch (versionName) {
            case "1.7.2-1.7.5": 
            case "1.7.6-1.7.10": {
                str = "";
                break;
            }
            case "1.8.x": {
                str = "/fml/Forge";
                break;
            }
            case "1.9": 
            case "1.9.1": 
            case "1.9.2": 
            case "1.9.3/4": {
                str = "/fml,forge/Forge";
                break;
            }
            case "1.10.x": 
            case "1.11": 
            case "1.11/2": 
            case "1.12": 
            case "1.12.1": 
            case "1.12.2": {
                str = "/fml,forge/Forge";
                break;
            }
            case "1.13": 
            case "1.13.1": 
            case "1.13.2": 
            case "1.14": 
            case "1.14.1": 
            case "1.14.2": 
            case "1.14.3": {
                str = "/vanilla/modified";
                break;
            }
            case "1.14.4": {
                str = "/forge/modified";
                break;
            }
            case "1.15": 
            case "1.15.1": {
                str = "/vanilla";
                break;
            }
            case "1.15.2": {
                str = "/forge/modified";
                break;
            }
            case "1.16": {
                str = "/vanilla";
                break;
            }
            case "1.16.1": 
            case "1.16.2": 
            case "1.16.3": 
            case "1.16.4/5": {
                str = "/forge/modified";
                break;
            }
            case "1.17": {
                str = "/vanilla/modified";
                break;
            }
            case "1.17.1": {
                str = "/forge/modified";
                break;
            }
            case "1.18": 
            case "1.18.1": {
                str = "/forge/modified";
                break;
            }
            case "1.19": 
            case "1.19.1/2": 
            case "1.19.3": 
            case "1.19.4": {
                str = "/forge/modified";
                break;
            }
            case "1.20/1.20.1": {
                str = "/forge/modified";
                break;
            }
            case "1.20.2": {
                str = "/vanilla";
                break;
            }
            default: {
                str = "/fml,forge,Forge";
            }
        }
        return str;
    }

    public static String getClientModName() {
        return ClientBrandRetriever.getReleaseTypePrefix() + ClientBrandRetriever.getVersionStr() + ClientBrandRetriever.getVersionSuffix();
    }

    public static String getDebugVersionString() {
        return ClientBrandRetriever.doHideTrueName() ? ClientBrandRetriever.getTrueClientName() : ClientBrandRetriever.getFalseClientPrefix() + (String)(ClientBrandRetriever.getVersionSuffix().isEmpty() ? "" : "(" + ClientBrandRetriever.getClientModName() + ")");
    }
}

