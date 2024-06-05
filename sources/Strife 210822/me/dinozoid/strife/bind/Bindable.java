package me.dinozoid.strife.bind;

public interface Bindable {

    /*
        Gets the key for the bind.
     */
    int key();

    /*
        Sets the key for the bind.
     */
    void key(int key);

    /*
        Called when the bind has been pressed.
     */
    void pressed();

}
