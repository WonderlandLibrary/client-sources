package client.metaware.api.module.bind;

public interface Bindable {

    /*
        Gets the key for the bind.
     */
    int getKey();

    /*
        Sets the key for the bind.
     */
    void setKey(int key);

    /*
        Called when the bind has been pressed.
     */
    void pressed();

}