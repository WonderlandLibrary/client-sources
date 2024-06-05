package me.satisfactory.base.gui;

import java.io.*;

public class Asset
{
    private String label;
    
    public Asset(final String label) {
        this.label = label;
    }
    
    public InputStream asInputStream() {
        if (this.getClass().getClassLoader().getResourceAsStream(this.label) != null) {
            return this.getClass().getClassLoader().getResourceAsStream(this.label);
        }
        return this.getClass().getResourceAsStream(this.label);
    }
}
