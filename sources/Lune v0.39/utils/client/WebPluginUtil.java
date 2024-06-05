package me.superskidder.lune.utils.client;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 工具类
 * @author: QianXia
 * @create: 2021/06/25 21:40
 **/
public class WebPluginUtil {
    public static List<WebPlugin> getWebPluginListByString(String webPluginList){
        List<WebPlugin> webPlugins = new ArrayList<>();

        String[] split = webPluginList.split("\r");
        for (String s : split) {
            String[] pluginMessages = s.split("->");
            WebPlugin webPlugin = new WebPlugin(pluginMessages[0], pluginMessages[1], pluginMessages[2], pluginMessages[3]);
            webPlugins.add(webPlugin);
        }

        return webPlugins;
    }
}
