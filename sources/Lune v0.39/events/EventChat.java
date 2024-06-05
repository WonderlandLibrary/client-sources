package me.superskidder.lune.events;

import me.superskidder.lune.manager.event.Event;

/**
 * @description: 聊天消息事件
 * @author: QianXia
 * @create: 2020/9/10 20-45
 **/
public class EventChat extends Event {
    public String msg;
    public boolean cancelled;

    public EventChat(String msg){
        this.msg = msg;
    }

    public String getMessage(){
        return msg;
    }

    public void setCancelled(boolean cancelled){
        this.cancelled = cancelled;
    }

    public boolean getCancelled(){
        return this.cancelled;
    }
}
