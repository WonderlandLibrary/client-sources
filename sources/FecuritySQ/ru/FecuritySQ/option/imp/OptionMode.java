package ru.FecuritySQ.option.imp;


import ru.FecuritySQ.option.Option;

public class OptionMode extends Option {

    public String[] modes;
    public int index;

    public OptionMode(String name, String[] modes, int index) {
        super(name, modes);
        this.index = index;
        this.modes = modes;
    }

    public int getIndex() {
        return index;
    }


    public String[] getModes() {
        return modes;
    }

    public String current(){
        return modes[index];
    }

    public String get(){
        return current();
    }

    public void set(String s){
        int count = 0;
        for(String f1 : modes) {
            {
                if(s == f1) {
                    index = count;
                }
                count++;
            }
        }
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
