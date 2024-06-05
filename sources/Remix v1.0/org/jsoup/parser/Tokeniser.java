package org.jsoup.parser;

import org.jsoup.helper.*;
import org.jsoup.nodes.*;
import java.util.*;

final class Tokeniser
{
    static final char replacementChar = '\ufffd';
    private static final char[] notCharRefCharsSorted;
    private final CharacterReader reader;
    private final ParseErrorList errors;
    private TokeniserState state;
    private Token emitPending;
    private boolean isEmitPending;
    private String charsString;
    private StringBuilder charsBuilder;
    StringBuilder dataBuffer;
    Token.Tag tagPending;
    Token.StartTag startPending;
    Token.EndTag endPending;
    Token.Character charPending;
    Token.Doctype doctypePending;
    Token.Comment commentPending;
    private String lastStartTag;
    private boolean selfClosingFlagAcknowledged;
    private final int[] codepointHolder;
    private final int[] multipointHolder;
    
    Tokeniser(final CharacterReader reader, final ParseErrorList errors) {
        this.state = TokeniserState.Data;
        this.isEmitPending = false;
        this.charsString = null;
        this.charsBuilder = new StringBuilder(1024);
        this.dataBuffer = new StringBuilder(1024);
        this.startPending = new Token.StartTag();
        this.endPending = new Token.EndTag();
        this.charPending = new Token.Character();
        this.doctypePending = new Token.Doctype();
        this.commentPending = new Token.Comment();
        this.selfClosingFlagAcknowledged = true;
        this.codepointHolder = new int[1];
        this.multipointHolder = new int[2];
        this.reader = reader;
        this.errors = errors;
    }
    
    Token read() {
        if (!this.selfClosingFlagAcknowledged) {
            this.error("Self closing flag not acknowledged");
            this.selfClosingFlagAcknowledged = true;
        }
        while (!this.isEmitPending) {
            this.state.read(this, this.reader);
        }
        if (this.charsBuilder.length() > 0) {
            final String str = this.charsBuilder.toString();
            this.charsBuilder.delete(0, this.charsBuilder.length());
            this.charsString = null;
            return this.charPending.data(str);
        }
        if (this.charsString != null) {
            final Token token = this.charPending.data(this.charsString);
            this.charsString = null;
            return token;
        }
        this.isEmitPending = false;
        return this.emitPending;
    }
    
    void emit(final Token token) {
        Validate.isFalse(this.isEmitPending, "There is an unread token pending!");
        this.emitPending = token;
        this.isEmitPending = true;
        if (token.type == Token.TokenType.StartTag) {
            final Token.StartTag startTag = (Token.StartTag)token;
            this.lastStartTag = startTag.tagName;
            if (startTag.selfClosing) {
                this.selfClosingFlagAcknowledged = false;
            }
        }
        else if (token.type == Token.TokenType.EndTag) {
            final Token.EndTag endTag = (Token.EndTag)token;
            if (endTag.attributes != null) {
                this.error("Attributes incorrectly present on end tag");
            }
        }
    }
    
    void emit(final String str) {
        if (this.charsString == null) {
            this.charsString = str;
        }
        else {
            if (this.charsBuilder.length() == 0) {
                this.charsBuilder.append(this.charsString);
            }
            this.charsBuilder.append(str);
        }
    }
    
    void emit(final char[] chars) {
        this.emit(String.valueOf(chars));
    }
    
    void emit(final int[] codepoints) {
        this.emit(new String(codepoints, 0, codepoints.length));
    }
    
    void emit(final char c) {
        this.emit(String.valueOf(c));
    }
    
    TokeniserState getState() {
        return this.state;
    }
    
    void transition(final TokeniserState state) {
        this.state = state;
    }
    
    void advanceTransition(final TokeniserState state) {
        this.reader.advance();
        this.state = state;
    }
    
    void acknowledgeSelfClosingFlag() {
        this.selfClosingFlagAcknowledged = true;
    }
    
    int[] consumeCharacterReference(final Character additionalAllowedCharacter, final boolean inAttribute) {
        if (this.reader.isEmpty()) {
            return null;
        }
        if (additionalAllowedCharacter != null && additionalAllowedCharacter == this.reader.current()) {
            return null;
        }
        if (this.reader.matchesAnySorted(Tokeniser.notCharRefCharsSorted)) {
            return null;
        }
        final int[] codeRef = this.codepointHolder;
        this.reader.mark();
        if (this.reader.matchConsume("#")) {
            final boolean isHexMode = this.reader.matchConsumeIgnoreCase("X");
            final String numRef = isHexMode ? this.reader.consumeHexSequence() : this.reader.consumeDigitSequence();
            if (numRef.length() == 0) {
                this.characterReferenceError("numeric reference with no numerals");
                this.reader.rewindToMark();
                return null;
            }
            if (!this.reader.matchConsume(";")) {
                this.characterReferenceError("missing semicolon");
            }
            int charval = -1;
            try {
                final int base = isHexMode ? 16 : 10;
                charval = Integer.valueOf(numRef, base);
            }
            catch (NumberFormatException ex) {}
            if (charval == -1 || (charval >= 55296 && charval <= 57343) || charval > 1114111) {
                this.characterReferenceError("character outside of valid range");
                codeRef[0] = 65533;
                return codeRef;
            }
            codeRef[0] = charval;
            return codeRef;
        }
        else {
            final String nameRef = this.reader.consumeLetterThenDigitSequence();
            final boolean looksLegit = this.reader.matches(';');
            final boolean found = Entities.isBaseNamedEntity(nameRef) || (Entities.isNamedEntity(nameRef) && looksLegit);
            if (!found) {
                this.reader.rewindToMark();
                if (looksLegit) {
                    this.characterReferenceError(String.format("invalid named referenece '%s'", nameRef));
                }
                return null;
            }
            if (inAttribute && (this.reader.matchesLetter() || this.reader.matchesDigit() || this.reader.matchesAny('=', '-', '_'))) {
                this.reader.rewindToMark();
                return null;
            }
            if (!this.reader.matchConsume(";")) {
                this.characterReferenceError("missing semicolon");
            }
            final int numChars = Entities.codepointsForName(nameRef, this.multipointHolder);
            if (numChars == 1) {
                codeRef[0] = this.multipointHolder[0];
                return codeRef;
            }
            if (numChars == 2) {
                return this.multipointHolder;
            }
            Validate.fail("Unexpected characters returned for " + nameRef);
            return this.multipointHolder;
        }
    }
    
    Token.Tag createTagPending(final boolean start) {
        return this.tagPending = (start ? this.startPending.reset() : this.endPending.reset());
    }
    
    void emitTagPending() {
        this.tagPending.finaliseTag();
        this.emit(this.tagPending);
    }
    
    void createCommentPending() {
        this.commentPending.reset();
    }
    
    void emitCommentPending() {
        this.emit(this.commentPending);
    }
    
    void createDoctypePending() {
        this.doctypePending.reset();
    }
    
    void emitDoctypePending() {
        this.emit(this.doctypePending);
    }
    
    void createTempBuffer() {
        Token.reset(this.dataBuffer);
    }
    
    boolean isAppropriateEndTagToken() {
        return this.lastStartTag != null && this.tagPending.name().equalsIgnoreCase(this.lastStartTag);
    }
    
    String appropriateEndTagName() {
        if (this.lastStartTag == null) {
            return null;
        }
        return this.lastStartTag;
    }
    
    void error(final TokeniserState state) {
        if (this.errors.canAddError()) {
            this.errors.add(new ParseError(this.reader.pos(), "Unexpected character '%s' in input state [%s]", new Object[] { this.reader.current(), state }));
        }
    }
    
    void eofError(final TokeniserState state) {
        if (this.errors.canAddError()) {
            this.errors.add(new ParseError(this.reader.pos(), "Unexpectedly reached end of file (EOF) in input state [%s]", new Object[] { state }));
        }
    }
    
    private void characterReferenceError(final String message) {
        if (this.errors.canAddError()) {
            this.errors.add(new ParseError(this.reader.pos(), "Invalid character reference: %s", new Object[] { message }));
        }
    }
    
    private void error(final String errorMsg) {
        if (this.errors.canAddError()) {
            this.errors.add(new ParseError(this.reader.pos(), errorMsg));
        }
    }
    
    boolean currentNodeInHtmlNS() {
        return true;
    }
    
    String unescapeEntities(final boolean inAttribute) {
        final StringBuilder builder = new StringBuilder();
        while (!this.reader.isEmpty()) {
            builder.append(this.reader.consumeTo('&'));
            if (this.reader.matches('&')) {
                this.reader.consume();
                final int[] c = this.consumeCharacterReference(null, inAttribute);
                if (c == null || c.length == 0) {
                    builder.append('&');
                }
                else {
                    builder.appendCodePoint(c[0]);
                    if (c.length != 2) {
                        continue;
                    }
                    builder.appendCodePoint(c[1]);
                }
            }
        }
        return builder.toString();
    }
    
    static {
        Arrays.sort(notCharRefCharsSorted = new char[] { '\t', '\n', '\r', '\f', ' ', '<', '&' });
    }
}
