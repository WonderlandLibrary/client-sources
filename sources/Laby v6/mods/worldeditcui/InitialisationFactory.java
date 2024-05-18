package mods.worldeditcui;

import mods.worldeditcui.exceptions.InitialisationException;

public interface InitialisationFactory
{
    void initialise() throws InitialisationException;
}
