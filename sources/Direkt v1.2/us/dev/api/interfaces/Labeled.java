package us.dev.api.interfaces;

/**
 * Created by Foundry on 11/15/2015.
 */
public interface Labeled {
    String getLabel();

    interface Writable extends Labeled {
        void setLabel(String label);
    }
}
