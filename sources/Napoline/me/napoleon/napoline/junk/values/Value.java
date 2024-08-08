package me.napoleon.napoline.junk.values;

import me.napoleon.napoline.Napoline;
import me.napoleon.napoline.events.EventChangeValue;
import me.napoleon.napoline.manager.EventManager;
import me.napoleon.napoline.utils.client.DevUtils;

public class Value<V> {
    public String name;
    public V value;
    public float optionAnim = 0;//present
    public float optionAnimNow = 0;//present
    public float animX1;
    public float animX;
    public boolean drag;


    public Value(String name){
    	if(Napoline.flag < 0) {
    		name = DevUtils.lol(name);
    	}
        this.name = name;

    }

    public String getName(){
        return this.name;
    }

    public V getValue(){
        return this.value;
    }

    public void setValue(V val){
        EventChangeValue eventBefore = new EventChangeValue(EventChangeValue.Mode.BEFORE, this.name, this);
        EventChangeValue eventAfter = new EventChangeValue(EventChangeValue.Mode.AFTER, this.name, this);

        EventManager.call(eventBefore);

        if(!eventBefore.isCancelled()){
            this.value = val;
        } else {
            return;
        }

        EventManager.call(eventAfter);
    }
}
