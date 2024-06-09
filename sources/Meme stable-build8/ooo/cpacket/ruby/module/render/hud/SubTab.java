package ooo.cpacket.ruby.module.render.hud;

import ooo.cpacket.ruby.module.Module;

public class SubTab {
    private String text;
    private TabActionListener<Module> listener;
    private Module object;

    public SubTab(String text, TabActionListener<Module> listener, Module object) {
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

    public TabActionListener<Module> getListener() {
        return listener;
    }

    public void setListener(TabActionListener<Module> listener) {
        this.listener = listener;
    }

    public Module getObject() {
        return object;
    }

    public void setObject(Module object) {
        this.object = object;
    }

    public void press() {
        if (listener != null) {
            listener.onClick(this);
        }
    }

    public interface TabActionListener<T> {
        void onClick(SubTab tab);
    }
}
