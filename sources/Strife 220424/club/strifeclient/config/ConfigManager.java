package club.strifeclient.config;

import club.strifeclient.Client;
import club.strifeclient.module.Module;
import club.strifeclient.setting.Setting;
import club.strifeclient.setting.implementations.ColorSetting;
import club.strifeclient.setting.implementations.MultiSelectSetting;
import club.strifeclient.util.callback.ReturnVariableCallback;
import lombok.Getter;
import lombok.Setter;
import org.lwjglx.input.Keyboard;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Getter
public class ConfigManager {
    @Setter
    private Config loadedConfig;
    private final Map<Config, File> fileConfigs = new HashMap<>();
    public static Path CONFIG_DIRECTORY = null;
    private XMLParser xmlParser;

    public void init() {
        CONFIG_DIRECTORY = Paths.get(String.valueOf(Client.DIRECTORY), "configs");
        xmlParser = new XMLParser();
        try {
            xmlParser.setupParser();
            if(!CONFIG_DIRECTORY.toFile().mkdirs())
                refreshFileConfigs();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deInit() {
        xmlParser.dispose();
        fileConfigs.clear();
    }

    public void add(Config config) {
        fileConfigs.put(config, config.getPath().toFile());
    }

    public void refreshFileConfigs() throws IOException {
        fileConfigs.clear();
        Files.list(CONFIG_DIRECTORY).map(Path::toFile).forEach(this::readConfigInfo);
    }

    public Path getPathForConfig(String name) {
        return CONFIG_DIRECTORY.resolve(name.concat(".conf"));
    }

    public Config getFileConfig(String name) {
        return fileConfigs.keySet().stream().filter(config -> config.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
    public File getFileForConfig(Config config) {
        return fileConfigs.get(config);
    }

    private void readConfigInfo(File file) {
        try {
            xmlParser.readFile(file.getPath(), document -> {
                Node metaNode = document.getElementsByTagName("meta").item(0);
                if (metaNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element meta = (Element) metaNode;
                    final String name = file.getName().replace(".conf", "");
                    final String author = meta.getElementsByTagName("author").item(0).getTextContent();
                    final String description = meta.getElementsByTagName("description").item(0).getTextContent();
                    final double version = Double.parseDouble(meta.getElementsByTagName("version").item(0).getTextContent());
                    fileConfigs.put(new Config(name, author, description, version), file);
                }
            });
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }
    }
    private void readConfigFromFile(Config config) throws Exception {
        readConfigFromFile(config.getPath().toFile().getPath());
    }
    public void readConfigFromFile(String path) throws Exception {
        xmlParser.readFile(path, document -> {
            NodeList list = document.getElementsByTagName("modules").item(0).getChildNodes();
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element moduleElement = (Element) node;
                    final Module module = Client.INSTANCE.getModuleManager().getModule(moduleElement.getTagName());
                    final int keybind = Keyboard.getKeyIndex(moduleElement.getElementsByTagName("keybind").item(0).getTextContent());
                    final boolean enabled = Boolean.parseBoolean(moduleElement.getElementsByTagName("enabled").item(0).getTextContent());
                    final boolean hidden = Boolean.parseBoolean(moduleElement.getElementsByTagName("hidden").item(0).getTextContent());
                    NodeList settingsList = moduleElement.getElementsByTagName("settings").item(0).getChildNodes();
                    module.setKeybind(keybind);
                    module.setEnabled(enabled);
                    module.setHidden(hidden);
                    for (int j = 0; j < settingsList.getLength(); j++) {
                        Node settingNode = settingsList.item(j);
                        if (settingNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element settingElement = (Element) settingNode;
                            final String name = settingElement.getTagName();
                            final Setting<?> setting = module.getSettingByName(name.replaceAll("-", " "));
                            if (setting instanceof ColorSetting) {
                                String red = settingElement.getElementsByTagName("red").item(0).getTextContent();
                                String green = settingElement.getElementsByTagName("green").item(0).getTextContent();
                                String blue = settingElement.getElementsByTagName("blue").item(0).getTextContent();
                                String opacity = settingElement.getElementsByTagName("opacity").item(0).getTextContent();
                                setting.parse(new String[]{red, green, blue, opacity});
                            } else if (setting instanceof MultiSelectSetting) {
                                final MultiSelectSetting<?> multiSelectSetting = (MultiSelectSetting<?>) setting;
                                final Map<String, Boolean> multiSettingMap = new HashMap<>();
                                NodeList multiSettingList = settingElement.getChildNodes();
                                for (int l = 0; l < multiSettingList.getLength(); l++) {
                                    Node multiSettingNode = multiSettingList.item(l);
                                    if (multiSettingNode.getNodeType() == Node.ELEMENT_NODE) {
                                        Element multiSettingElement = (Element) multiSettingNode;
                                        String value = multiSettingElement.getChildNodes().item(0).getTextContent();
                                        multiSettingMap.put(multiSettingElement.getTagName().replaceAll("-", "_"), Boolean.parseBoolean(value));
                                    }
                                }
                                multiSelectSetting.parse(multiSettingMap);
                            } else {
                                setting.parse(settingElement.getChildNodes().item(0).getTextContent());
                            }
                        }
                    }
                }
            }
        });
    }
    public void writeConfigToFile(Config config) throws Exception {
        xmlParser.writeFile(config.getPath().toFile().getPath(), new ReturnVariableCallback<Document>() {
            private Document document;
            @Override
            public Document getCallback() {
                return document;
            }
            @Override
            public void callback(Document document) {
                final Element root = document.createElement("config");

                final Element meta = document.createElement("meta");

                final Element authorElement = document.createElement("author");
                authorElement.appendChild(document.createTextNode(config.getAuthor()));

                final Element descriptionElement = document.createElement("description");
                descriptionElement.appendChild(document.createTextNode(config.getDescription()));

                final Element versionElement = document.createElement("version");
                versionElement.appendChild(document.createTextNode(String.valueOf(config.getVersion())));

                meta.appendChild(authorElement);
                meta.appendChild(descriptionElement);
                meta.appendChild(versionElement);

                root.appendChild(meta);

                final Element modulesElement = document.createElement("modules");
                Client.INSTANCE.getModuleManager().getModules().forEach(module -> {
                    Element moduleElement = document.createElement(module.getName().toLowerCase());
                    final int keybind = module.getKeybind();
                    final boolean enabled = module.isEnabled();
                    final boolean hidden = module.isHidden();

                    final Element keybindElement = document.createElement("keybind");
                    keybindElement.appendChild(document.createTextNode(Keyboard.getKeyName(keybind)));

                    final Element enabledElement = document.createElement("enabled");
                    enabledElement.appendChild(document.createTextNode(String.valueOf(enabled)));

                    final Element hiddenElement = document.createElement("hidden");
                    hiddenElement.appendChild(document.createTextNode(String.valueOf(hidden)));

                    moduleElement.appendChild(keybindElement);
                    moduleElement.appendChild(enabledElement);
                    moduleElement.appendChild(hiddenElement);

                    Element settingsElement = document.createElement("settings");
                    module.getSettings().forEach(setting -> {
                        Element settingElement = document.createElement(setting.getSerializedName().toLowerCase());
                        if (setting instanceof ColorSetting) {
                            final ColorSetting colorSetting = (ColorSetting) setting;
                            final Color color = colorSetting.getValue();

                            final Element redElement = document.createElement("red");
                            redElement.appendChild(document.createTextNode(String.valueOf(color.getRed())));

                            final Element greenElement = document.createElement("green");
                            greenElement.appendChild(document.createTextNode(String.valueOf(color.getGreen())));

                            final Element blueElement = document.createElement("blue");
                            blueElement.appendChild(document.createTextNode(String.valueOf(color.getGreen())));

                            final Element opacityElement = document.createElement("opacity");
                            opacityElement.appendChild(document.createTextNode(String.valueOf(colorSetting.getOpacity())));

                            settingElement.appendChild(redElement);
                            settingElement.appendChild(greenElement);
                            settingElement.appendChild(blueElement);
                            settingElement.appendChild(opacityElement);
                        } else if (setting instanceof MultiSelectSetting<?>) {
                            final MultiSelectSetting<?> multiSelectSetting = (MultiSelectSetting<?>) setting;
                            multiSelectSetting.getValue().forEach((enumType, selected) -> {
                                final Element nameElement = document.createElement(enumType.name().replaceAll("_", "-").toLowerCase());
                                nameElement.appendChild(document.createTextNode(String.valueOf(selected)));

                                settingElement.appendChild(nameElement);
                            });
                        } else settingElement.appendChild(document.createTextNode(setting.toString()));
                        settingsElement.appendChild(settingElement);
                    });
                    moduleElement.appendChild(settingsElement);
                    modulesElement.appendChild(moduleElement);
                });
                root.appendChild(modulesElement);
                document.appendChild(root);
                this.document = document;
            }
        });
    }
}
