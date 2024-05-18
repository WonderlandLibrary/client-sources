package pw.latematt.xiv.command.commands;

import pw.latematt.xiv.command.CommandHandler;
import pw.latematt.xiv.utils.ChatLogger;
import pw.latematt.xiv.utils.RenderUtils;

/**
 * @author Matthew
 */
public class Render implements CommandHandler {
    @Override
    public void onCommandRan(String message) {
        String[] arguments = message.split(" ");
        if (arguments.length >= 2) {
            String action = arguments[1];
            switch (action) {
                case "linewidth":
                case "lw":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            RenderUtils.getLineWidth().setValue(RenderUtils.getLineWidth().getDefault());
                        } else {
                            Float width = Float.parseFloat(arguments[2]);
                            RenderUtils.getLineWidth().setValue(width);
                            if (RenderUtils.getLineWidth().getValue() > RenderUtils.getLineWidth().getMax())
                                RenderUtils.getLineWidth().setValue(RenderUtils.getLineWidth().getMax());
                            else if (RenderUtils.getLineWidth().getValue() < RenderUtils.getLineWidth().getMin())
                                RenderUtils.getLineWidth().setValue(RenderUtils.getLineWidth().getMin());
                        }
                        ChatLogger.print(String.format("Render Line Width set to: %s", RenderUtils.getLineWidth().getValue()));
                    } else {
                        ChatLogger.print("Invalid arguments, valid: linewidth <float>");
                    }
                    break;
                case "antialiasing":
                case "aa":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            RenderUtils.getAntiAliasing().setValue(RenderUtils.getAntiAliasing().getDefault());
                        } else {
                            RenderUtils.getAntiAliasing().setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        RenderUtils.getAntiAliasing().setValue(!RenderUtils.getAntiAliasing().getValue());
                    }
                    ChatLogger.print(String.format("Render mods will %s use antialiasing.", RenderUtils.getAntiAliasing().getValue() ? "now" : "no longer"));
                    break;
                case "worldbobbing":
                case "wb":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            RenderUtils.getWorldBobbing().setValue(RenderUtils.getWorldBobbing().getDefault());
                        } else {
                            RenderUtils.getWorldBobbing().setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        RenderUtils.getWorldBobbing().setValue(!RenderUtils.getWorldBobbing().getValue());
                    }
                    ChatLogger.print(String.format("Render mods will %s render world bobbing.", RenderUtils.getWorldBobbing().getValue() ? "now" : "no longer"));
                    break;
                case "tracerentity":
                case "te":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            RenderUtils.getTracerEntity().setValue(RenderUtils.getTracerEntity().getDefault());
                        } else {
                            RenderUtils.getTracerEntity().setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        RenderUtils.getTracerEntity().setValue(!RenderUtils.getTracerEntity().getValue());
                    }
                    ChatLogger.print(String.format("Render mods will %s start from tracer entity.", RenderUtils.getTracerEntity().getValue() ? "no longer" : "now"));
                    break;
                case "nametagopacity":
                case "nto":
                    if (arguments.length >= 3) {
                        String newNametagOpacityString = arguments[2];
                        try {
                            if (arguments[2].equalsIgnoreCase("-d")) {
                                RenderUtils.getNametagOpacity().setValue(RenderUtils.getNametagOpacity().getDefault());
                            } else {
                                float newNametagOpacity = Float.parseFloat(newNametagOpacityString);
                                RenderUtils.getNametagOpacity().setValue(newNametagOpacity);
                                if (RenderUtils.getNametagSize().getValue() > RenderUtils.getNametagSize().getMax())
                                    RenderUtils.getNametagSize().setValue(RenderUtils.getNametagSize().getMax());
                                else if (RenderUtils.getLineWidth().getValue() < RenderUtils.getNametagSize().getMin())
                                    RenderUtils.getNametagSize().setValue(RenderUtils.getNametagSize().getMin());
                            }
                            ChatLogger.print(String.format("Nametag Opacity set to %s", RenderUtils.getNametagOpacity().getValue()));
                        } catch (NumberFormatException e) {
                            ChatLogger.print(String.format("\"%s\" is not a number.", newNametagOpacityString));
                        }
                    } else {
                        ChatLogger.print("Invalid arguments, valid: render nametagopacity <number>");
                    }
                    break;
                case "nametagsize":
                case "nts":
                    if (arguments.length >= 3) {
                        String newNametagSizeString = arguments[2];
                        try {
                            if (arguments[2].equalsIgnoreCase("-d")) {
                                RenderUtils.getNametagSize().setValue(RenderUtils.getNametagSize().getDefault());
                            } else {
                                Float newNametagSize = Float.parseFloat(newNametagSizeString);
                                RenderUtils.getNametagSize().setValue(newNametagSize);
                                if (RenderUtils.getNametagSize().getValue() > RenderUtils.getNametagSize().getMax())
                                    RenderUtils.getNametagSize().setValue(RenderUtils.getNametagSize().getMax());
                                else if (RenderUtils.getLineWidth().getValue() < RenderUtils.getNametagSize().getMin())
                                    RenderUtils.getNametagSize().setValue(RenderUtils.getNametagSize().getMin());
                            }
                            ChatLogger.print(String.format("Nametag Size set to %s", RenderUtils.getNametagSize().getValue()));
                        } catch (NumberFormatException e) {
                            ChatLogger.print(String.format("\"%s\" is not a number.", newNametagSizeString));
                        }
                    } else {
                        ChatLogger.print("Invalid arguments, valid: render nametagsize <number>");
                    }
                    break;
                case "showtags":
                case "st":
                    if (arguments.length >= 3) {
                        if (arguments[2].equalsIgnoreCase("-d")) {
                            RenderUtils.getShowTags().setValue(RenderUtils.getShowTags().getDefault());
                        } else {
                            RenderUtils.getShowTags().setValue(Boolean.parseBoolean(arguments[2]));
                        }
                    } else {
                        RenderUtils.getShowTags().setValue(!RenderUtils.getShowTags().getValue());
                    }
                    ChatLogger.print(String.format("ArrayList will %s show mod tags.", RenderUtils.getShowTags().getValue() ? "now" : "no longer"));
                    break;
                default:
                    ChatLogger.print("Invalid action, valid: linewidth, antialiasing, worldbobbing, nametagsize, nametagopacity, tracerentity, showtags");
                    break;
            }
        } else {
            ChatLogger.print("Invalid arguments, valid: render <action>");
        }
    }
}
