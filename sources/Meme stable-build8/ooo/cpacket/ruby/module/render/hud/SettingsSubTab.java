package ooo.cpacket.ruby.module.render.hud;

import ooo.cpacket.lemongui.settings.Setting;

public class SettingsSubTab{
    private String text;
    private SettingsSubTab.TabActionListener listener;
    private Setting object;

    public SettingsSubTab(String text, SettingsSubTab.TabActionListener listener, Setting object) {
        this.text = text;
        this.listener = listener;
        this.object = object;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TabActionListener getListener() {
        return listener;
    }

    public void setListener(TabActionListener listener) {
        this.listener = listener;
    }

    public Setting getObject() {
        return object;
    }

    public void setObject(Setting object) {
        this.object = object;
    }

    public void press() {
        if (listener != null) {
            listener.onClick(this);
        }
    }

    public interface TabActionListener {
        void onClick(SettingsSubTab tab);
    }
}
