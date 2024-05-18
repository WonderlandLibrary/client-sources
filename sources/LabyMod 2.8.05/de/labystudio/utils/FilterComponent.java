package de.labystudio.utils;

public class FilterComponent
{
    private String filterName;
    private String[] wordsContains;
    private String[] wordsContainsNot;
    private boolean playSound;
    private String soundPath;
    private boolean highlightMessage;
    private java.awt.Color highlightColor;
    private boolean hideMessage;
    private boolean displayInSecondChat;

    public FilterComponent(String filterName, String[] wordsContains, String[] wordsContainsNot, boolean playSound, String soundPath, boolean highlightMessage, java.awt.Color highlightColor, boolean hideMessage, boolean displayInSecondChat)
    {
        this.filterName = filterName;
        this.wordsContains = wordsContains;
        this.wordsContainsNot = wordsContainsNot;
        this.playSound = playSound;
        this.soundPath = soundPath;
        this.highlightMessage = highlightMessage;
        this.highlightColor = highlightColor;
        this.hideMessage = hideMessage;
        this.displayInSecondChat = displayInSecondChat;
    }

    public FilterComponent(FilterComponent component)
    {
        this(component.getFilterName(), component.getWordsContains(), component.getWordsContainsNot(), component.isPlaySound(), component.getSoundPath(), component.isHighlightMessage(), component.getHighlightColor(), component.isHideMessage(), component.isDisplayInSecondChat());
    }

    public String getFilterName()
    {
        return this.filterName;
    }

    public java.awt.Color getHighlightColor()
    {
        return this.highlightColor;
    }

    public String[] getWordsContains()
    {
        return this.wordsContains;
    }

    public String[] getWordsContainsNot()
    {
        return this.wordsContainsNot;
    }

    public boolean isDisplayInSecondChat()
    {
        return this.displayInSecondChat;
    }

    public boolean isHideMessage()
    {
        return this.hideMessage;
    }

    public boolean isHighlightMessage()
    {
        return this.highlightMessage;
    }

    public boolean isPlaySound()
    {
        return this.playSound;
    }

    public String getSoundPath()
    {
        return this.soundPath;
    }

    public void setDisplayInSecondChat(boolean displayInSecondChat)
    {
        this.displayInSecondChat = displayInSecondChat;
    }

    public void setFilterName(String filterName)
    {
        this.filterName = filterName;
    }

    public void setHideMessage(boolean hideMessage)
    {
        this.hideMessage = hideMessage;
    }

    public void setHighlightColor(java.awt.Color highlightColor)
    {
        this.highlightColor = highlightColor;
    }

    public void setHighlightMessage(boolean highlightMessage)
    {
        this.highlightMessage = highlightMessage;
    }

    public void setPlaySound(boolean playSound)
    {
        this.playSound = playSound;
    }

    public void setSoundPath(String soundPath)
    {
        this.soundPath = soundPath;
    }

    public void setWordsContains(String[] wordsContains)
    {
        this.wordsContains = wordsContains;
    }

    public void setWordsContainsNot(String[] wordsContainsNot)
    {
        this.wordsContainsNot = wordsContainsNot;
    }
}
