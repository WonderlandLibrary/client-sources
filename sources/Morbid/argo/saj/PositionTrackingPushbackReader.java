package argo.saj;

import java.io.*;

final class PositionTrackingPushbackReader implements ThingWithPosition
{
    private final PushbackReader pushbackReader;
    private int characterCount;
    private int lineCount;
    private boolean lastCharacterWasCarriageReturn;
    
    public PositionTrackingPushbackReader(final Reader par1Reader) {
        this.characterCount = 0;
        this.lineCount = 1;
        this.lastCharacterWasCarriageReturn = false;
        this.pushbackReader = new PushbackReader(par1Reader);
    }
    
    public void unread(final char par1) throws IOException {
        --this.characterCount;
        if (this.characterCount < 0) {
            this.characterCount = 0;
        }
        this.pushbackReader.unread(par1);
    }
    
    public void uncount(final char[] par1ArrayOfCharacter) throws IOException {
        this.characterCount -= par1ArrayOfCharacter.length;
        if (this.characterCount < 0) {
            this.characterCount = 0;
        }
    }
    
    public int read() throws IOException {
        final int var1 = this.pushbackReader.read();
        this.updateCharacterAndLineCounts(var1);
        return var1;
    }
    
    public int read(final char[] par1ArrayOfCharacter) throws IOException {
        final int var2 = this.pushbackReader.read(par1ArrayOfCharacter);
        for (final char var5 : par1ArrayOfCharacter) {
            this.updateCharacterAndLineCounts(var5);
        }
        return var2;
    }
    
    private void updateCharacterAndLineCounts(final int par1) {
        if (13 == par1) {
            this.characterCount = 0;
            ++this.lineCount;
            this.lastCharacterWasCarriageReturn = true;
        }
        else {
            if (10 == par1 && !this.lastCharacterWasCarriageReturn) {
                this.characterCount = 0;
                ++this.lineCount;
            }
            else {
                ++this.characterCount;
            }
            this.lastCharacterWasCarriageReturn = false;
        }
    }
    
    @Override
    public int getColumn() {
        return this.characterCount;
    }
    
    @Override
    public int getRow() {
        return this.lineCount;
    }
}
