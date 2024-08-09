package ru.FecuritySQ.option;

public class Option {

    public String name;

    public Object option;

    public Option(String name, Object option){
        this.name = name;
        this.option = option;
    }


    public Object getOption(){
        return this.option;
    }

    public String getName(){
        return this.name;
    }

}
