package com.ibm.icu.impl.number;

import java.io.*;

public class Properties implements Serializable
{
    private static final long serialVersionUID = 4095518955889349243L;
    private transient DecimalFormatProperties instance;
    
    public DecimalFormatProperties getInstance() {
        return this.instance;
    }
    
    private void readObject(final ObjectInputStream ois) throws IOException, ClassNotFoundException {
        if (this.instance == null) {
            this.instance = new DecimalFormatProperties();
        }
        this.instance.readObjectImpl(ois);
    }
    
    private void writeObject(final ObjectOutputStream oos) throws IOException {
        if (this.instance == null) {
            this.instance = new DecimalFormatProperties();
        }
        this.instance.writeObjectImpl(oos);
    }
}
