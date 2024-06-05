package me.superskidder.lune.utils.client;

/**
 * @description: 网络上获取的插件信息
 * @author: QianXia
 * @create: 2021/06/25 21:32
 **/
public class WebPlugin {
    public String pluginName;
    public String author;
    public String version;
    public String webFileCode;

    public WebPlugin(String pluginName, String author, String version, String webFileCode) {
        this.pluginName = pluginName;
        this.author = author;
        this.version = version;
        this.webFileCode = webFileCode;
    }
}
