package me.protocol_client.thanks_slicky.properties;

import java.util.ArrayList;
import java.util.List;

public abstract class ListManager<T> {
	protected List<T> contents;
	protected List<T> scontents;

    public ListManager(ArrayList<Object> arrayList) {
    	this.contents = (List<T>) arrayList;
    }

    public List<T> getContents() {
        return contents;
    }
    public List<T> getSliderContents() {
        return scontents;
    }

    public void setup() {
    }
}
