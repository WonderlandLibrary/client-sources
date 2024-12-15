package rip.vantage.network.handler;

import com.alan.clients.Client;
import com.alan.clients.event.impl.other.BackendPacketEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.impl.render.ClickGUI;
import com.alan.clients.ui.theme.Themes;
import com.alan.clients.util.interfaces.ThreadAccess;
import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.value.Mode;
import com.alan.clients.value.Value;
import com.alan.clients.value.impl.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.Session;
import rip.vantage.commons.handler.api.IServerPacketHandler;
import rip.vantage.commons.packet.impl.client.protection.C2SPacketConfirmServer;
import rip.vantage.commons.packet.impl.server.community.*;
import rip.vantage.commons.packet.impl.server.general.S2CPacketKeepAlive;
import rip.vantage.commons.packet.impl.server.management.S2CPacketCrash;
import rip.vantage.commons.packet.impl.server.protection.*;
import rip.vantage.commons.util.time.StopWatch;
import rip.vantage.network.core.Network;

import java.awt.*;
import java.util.HashMap;
import java.util.regex.Pattern;

import static com.alan.clients.util.Accessor.mc;

public final class ServerPacketHandler implements IServerPacketHandler {

    private long time;
    private final StopWatch stopWatch = new StopWatch();

    @Override
    public void handle(S2CPacketAuthenticationFinish packet) {
        Client.INSTANCE.getEventBus().handle(new BackendPacketEvent(packet));
        time = packet.getD();
        stopWatch.reset();

        NetHandlerPlayClient netHandler = mc.getNetHandler();
        if (netHandler != null) {
            NetworkManager networkManager = netHandler.getNetworkManager();
            String ip = networkManager.getRemoteAddress().toString().split(":")[0];
            int port = Integer.parseInt(networkManager.getRemoteAddress().toString().split(":")[1]);

            Network.getInstance().getClient().sendMessage(new C2SPacketConfirmServer(ip, port, mc.getSession().getUsername()).export());
        }
    }

    @Override
    public void handle(S2CPacketLoadConfig packet) {
        ThreadAccess.threadPool.execute(() -> {
            HashMap<String, Module> moduleMap = new HashMap<>();

            for (Module module : Client.INSTANCE.getModuleManager().getAll()) {
                module.setEnabled(false);
                moduleMap.put(module.getModuleInfo().aliases()[0], module);

                for (Value<?> value : module.getAllValues()) {
                    value.setValueAsObject(value.getDefaultValue());
                }
            }

            boolean theme = false;
            for (String line : packet.getConfig().split("\n")) {
                if (!theme) {
                    try {
                        Client.INSTANCE.getThemeManager().setTheme(Themes.valueOf(line.split("th_")[1]));
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    theme = true;
                    continue;
                }

                String moduleName = line.split("_")[0];

                if (!moduleMap.containsKey(moduleName)) {
                    continue;
                }

                Module module = moduleMap.get(moduleName);

                String valueName = line.split("_")[1].split("_")[0];

                switch (valueName) {
                    case "e1":
                        continue;

                    case "kc":
                        continue;

                    default:
                        String propertyName = line.split("_" + Pattern.quote(valueName) + "_")[1].split("_")[0];

                        int index = 0;
                        for (Value<?> value : module.getAllValues()) {
                            index++;
                            String indexedName = value.getName() + " in " + (value.getParent() != null ? (value.getParent() instanceof Module ? ((Module) value.getParent()).getModuleInfo().aliases()[0] + " Module" :
                                    ((Mode<?>) value.getParent()).getName() + " Mode") : "Unknown");

                            // Remove after a while this is just for compatibility with old configs
                            if (valueName.contains("*")) {
                                indexedName = value.getName() + "*" + index;
                            }

                            if (!indexedName.equalsIgnoreCase(valueName)) {
                                continue;
                            }

                            if (value instanceof ModeValue) {
                                ModeValue modeValue = (ModeValue) value;
                                modeValue.setValue(line.split("_" + Pattern.quote(propertyName) + "_")[1]);
                            } else if (value instanceof BooleanValue) {
                                BooleanValue booleanValue = (BooleanValue) value;
                                booleanValue.setValue(Boolean.parseBoolean(line.split("_" + Pattern.quote(propertyName) + "_")[1]));
                            } else if (value instanceof StringValue) {
                                StringValue stringValue = (StringValue) value;
                                if (line.contains("_" + Pattern.quote(propertyName) + "_")) {
                                    stringValue.setValue(line.split("_" + Pattern.quote(propertyName) + "_")[1].replaceAll("<percentsign>", "%"));
                                }
                            } else if (value instanceof NumberValue) {
                                NumberValue numberValue = (NumberValue) value;
                                numberValue.setValue(Double.parseDouble(line.split("_" + Pattern.quote(propertyName) + "_")[1]));
                            } else if (value instanceof BoundsNumberValue) {
                                BoundsNumberValue boundsNumberValue = (BoundsNumberValue) value;
                                double parsedValue = Double.parseDouble(line.split("_" + Pattern.quote(propertyName) + "_")[1]);
                                switch (propertyName) {
                                    case "first":
                                        boundsNumberValue.setValue(parsedValue);
                                        break;

                                    case "second":
                                        boundsNumberValue.setSecondValue(parsedValue);
                                        break;
                                }
                            } else if (value instanceof ColorValue) {
                                ColorValue colorValue = (ColorValue) value;
                                Color color = colorValue.getValue();
                                int parsedValue = Integer.parseInt(line.split("_" + Pattern.quote(propertyName) + "_")[1]);
                                switch (propertyName) {
                                    case "red":
                                        colorValue.setValue(new Color(parsedValue, color.getGreen(), color.getBlue(), color.getAlpha()));
                                        break;

                                    case "green":
                                        colorValue.setValue(new Color(color.getRed(), parsedValue, color.getBlue(), color.getAlpha()));
                                        break;

                                    case "blue":
                                        colorValue.setValue(new Color(color.getRed(), color.getGreen(), parsedValue, color.getAlpha()));
                                        break;

                                    case "alpha":
                                        colorValue.setValue(new Color(color.getRed(), color.getGreen(), color.getBlue(), parsedValue));
                                        break;
                                }
                            } else if (value instanceof DragValue) {
                                DragValue dragValue = (DragValue) value;
                                double parsedValue = Double.parseDouble(line.split("_" + Pattern.quote(propertyName) + "_")[1]);
                                switch (propertyName) {
                                    case "positionX":
                                        dragValue.setPosition(new Vector2d(parsedValue, dragValue.position.y));
                                        break;

                                    case "positionY":
                                        dragValue.setPosition(new Vector2d(dragValue.position.x, parsedValue));
                                        break;

                                    case "scaleX":
                                        dragValue.setScale(new Vector2d(parsedValue, dragValue.scale.y));
                                        break;

                                    case "scaleY":
                                        dragValue.setScale(new Vector2d(dragValue.scale.x, parsedValue));
                                        break;
                                }
                            } else if (value instanceof ListValue) {

                                ListValue<?> listValue = (ListValue<?>) value;
                                for (Object mode : listValue.getModes()) {
                                    if (mode.toString().equalsIgnoreCase(line.split("_" + Pattern.quote(propertyName) + "_")[1])) {
                                        listValue.setValueAsObject(mode);
                                    }
                                }
                            }
                        }
                        break;
                }
            }

            for (String line : packet.getConfig().split("\n")) {
                String moduleName = line.split("_")[0];

                if (!moduleMap.containsKey(moduleName)) {
                    continue;
                }

                Module module = moduleMap.get(moduleName);

                String valueName = line.split("_")[1].split("_")[0];

                switch (valueName) {
                    case "e1":
                        if (!(module instanceof ClickGUI)) {
                            module.setEnabled(Boolean.parseBoolean(line.split("_e1_")[1]));
                        }
                        continue;

                    case "kc":
                        module.setKey(Integer.parseInt(line.split("_kc_")[1]));
                }
            }

        });
    }

    @Override
    public void handle(S2CPacketJoinServer packet) {
    }

    @Override
    public void handle(S2CPacketAltLogin packet) {
        Minecraft.getMinecraft().session = new Session(packet.getUsername(), packet.getUuid(), packet.getAccessToken(), "microsoft");
        Client.INSTANCE.getEventBus().handle(new BackendPacketEvent(packet));
    }

    @Override
    public void handle(S2CPacketCrash packet) {
        Minecraft.getMinecraft().ingameGUI.lastSystemTime = -50L;
    }

    @Override
    public void handle(S2CPacketIRCMessage packet) {
        Client.INSTANCE.getEventBus().handle(new BackendPacketEvent(packet));
    }

    @Override
    public void handle(S2CPacketTabIRC packet) {
        Client.INSTANCE.getEventBus().handle(new BackendPacketEvent(packet));
    }

    @Override
    public void handle(S2CPacketEntities packet) {
        Client.INSTANCE.getEventBus().handle(new BackendPacketEvent(packet));
    }

    @Override
    public void handle(S2CPacketTitleIRC packet) {
        Client.INSTANCE.getEventBus().handle(new BackendPacketEvent(packet));
    }

    @Override
    public void handle(S2CPacketCommunityInfo packet) {
        Client.INSTANCE.getEventBus().handle(new BackendPacketEvent(packet));
    }

    @Override
    public void handle(S2CPacketTroll packet) {
        Client.INSTANCE.getEventBus().handle(new BackendPacketEvent(packet));
    }

    public void handle(S2CPacketKeepAlive packet) {
        WebSocketClient.keepAlive.reset();
        time += stopWatch.getElapsedTime();
        stopWatch.reset();
//
//        if (Math.abs(packet.getA() - time) > 240000) {
//            System.out.println(packet.getA() + " " + time);
//            System.out.println("Server is lagging behind by " + (packet.getA() - time) + "ms");
//            Client.INSTANCE.setClickGUI(new BuggedRiseClickGUI());
//        }
    }
}