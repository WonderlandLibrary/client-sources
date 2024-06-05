package org.jsoup.parser;

import java.util.*;

enum TokeniserState
{
    Data {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            switch (r.current()) {
                case '&': {
                    t.advanceTransition(TokeniserState$1.CharacterReferenceInData);
                    break;
                }
                case '<': {
                    t.advanceTransition(TokeniserState$1.TagOpen);
                    break;
                }
                case '\0': {
                    t.error(this);
                    t.emit(r.consume());
                    break;
                }
                case '\uffff': {
                    t.emit(new Token.EOF());
                    break;
                }
                default: {
                    final String data = r.consumeData();
                    t.emit(data);
                    break;
                }
            }
        }
    }, 
    CharacterReferenceInData {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            readCharRef(t, TokeniserState$2.Data);
        }
    }, 
    Rcdata {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            switch (r.current()) {
                case '&': {
                    t.advanceTransition(TokeniserState$3.CharacterReferenceInRcdata);
                    break;
                }
                case '<': {
                    t.advanceTransition(TokeniserState$3.RcdataLessthanSign);
                    break;
                }
                case '\0': {
                    t.error(this);
                    r.advance();
                    t.emit('\ufffd');
                    break;
                }
                case '\uffff': {
                    t.emit(new Token.EOF());
                    break;
                }
                default: {
                    final String data = r.consumeToAny('&', '<', '\0');
                    t.emit(data);
                    break;
                }
            }
        }
    }, 
    CharacterReferenceInRcdata {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            readCharRef(t, TokeniserState$4.Rcdata);
        }
    }, 
    Rawtext {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            readData(t, r, this, TokeniserState$5.RawtextLessthanSign);
        }
    }, 
    ScriptData {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            readData(t, r, this, TokeniserState$6.ScriptDataLessthanSign);
        }
    }, 
    PLAINTEXT {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            switch (r.current()) {
                case '\0': {
                    t.error(this);
                    r.advance();
                    t.emit('\ufffd');
                    break;
                }
                case '\uffff': {
                    t.emit(new Token.EOF());
                    break;
                }
                default: {
                    final String data = r.consumeTo('\0');
                    t.emit(data);
                    break;
                }
            }
        }
    }, 
    TagOpen {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            switch (r.current()) {
                case '!': {
                    t.advanceTransition(TokeniserState$8.MarkupDeclarationOpen);
                    break;
                }
                case '/': {
                    t.advanceTransition(TokeniserState$8.EndTagOpen);
                    break;
                }
                case '?': {
                    t.advanceTransition(TokeniserState$8.BogusComment);
                    break;
                }
                default: {
                    if (r.matchesLetter()) {
                        t.createTagPending(true);
                        t.transition(TokeniserState$8.TagName);
                        break;
                    }
                    t.error(this);
                    t.emit('<');
                    t.transition(TokeniserState$8.Data);
                    break;
                }
            }
        }
    }, 
    EndTagOpen {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            if (r.isEmpty()) {
                t.eofError(this);
                t.emit("</");
                t.transition(TokeniserState$9.Data);
            }
            else if (r.matchesLetter()) {
                t.createTagPending(false);
                t.transition(TokeniserState$9.TagName);
            }
            else if (r.matches('>')) {
                t.error(this);
                t.advanceTransition(TokeniserState$9.Data);
            }
            else {
                t.error(this);
                t.advanceTransition(TokeniserState$9.BogusComment);
            }
        }
    }, 
    TagName {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            final String tagName = r.consumeTagName();
            t.tagPending.appendTagName(tagName);
            switch (r.consume()) {
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ': {
                    t.transition(TokeniserState$10.BeforeAttributeName);
                    break;
                }
                case '/': {
                    t.transition(TokeniserState$10.SelfClosingStartTag);
                    break;
                }
                case '>': {
                    t.emitTagPending();
                    t.transition(TokeniserState$10.Data);
                    break;
                }
                case '\0': {
                    t.tagPending.appendTagName(TokeniserState.replacementStr);
                    break;
                }
                case '\uffff': {
                    t.eofError(this);
                    t.transition(TokeniserState$10.Data);
                    break;
                }
            }
        }
    }, 
    RcdataLessthanSign {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            if (r.matches('/')) {
                t.createTempBuffer();
                t.advanceTransition(TokeniserState$11.RCDATAEndTagOpen);
            }
            else if (r.matchesLetter() && t.appropriateEndTagName() != null && !r.containsIgnoreCase("</" + t.appropriateEndTagName())) {
                t.tagPending = t.createTagPending(false).name(t.appropriateEndTagName());
                t.emitTagPending();
                r.unconsume();
                t.transition(TokeniserState$11.Data);
            }
            else {
                t.emit("<");
                t.transition(TokeniserState$11.Rcdata);
            }
        }
    }, 
    RCDATAEndTagOpen {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            if (r.matchesLetter()) {
                t.createTagPending(false);
                t.tagPending.appendTagName(r.current());
                t.dataBuffer.append(r.current());
                t.advanceTransition(TokeniserState$12.RCDATAEndTagName);
            }
            else {
                t.emit("</");
                t.transition(TokeniserState$12.Rcdata);
            }
        }
    }, 
    RCDATAEndTagName {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            if (r.matchesLetter()) {
                final String name = r.consumeLetterSequence();
                t.tagPending.appendTagName(name);
                t.dataBuffer.append(name);
                return;
            }
            final char c = r.consume();
            switch (c) {
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ': {
                    if (t.isAppropriateEndTagToken()) {
                        t.transition(TokeniserState$13.BeforeAttributeName);
                        break;
                    }
                    this.anythingElse(t, r);
                    break;
                }
                case '/': {
                    if (t.isAppropriateEndTagToken()) {
                        t.transition(TokeniserState$13.SelfClosingStartTag);
                        break;
                    }
                    this.anythingElse(t, r);
                    break;
                }
                case '>': {
                    if (t.isAppropriateEndTagToken()) {
                        t.emitTagPending();
                        t.transition(TokeniserState$13.Data);
                        break;
                    }
                    this.anythingElse(t, r);
                    break;
                }
                default: {
                    this.anythingElse(t, r);
                    break;
                }
            }
        }
        
        private void anythingElse(final Tokeniser t, final CharacterReader r) {
            t.emit("</" + t.dataBuffer.toString());
            r.unconsume();
            t.transition(TokeniserState$13.Rcdata);
        }
    }, 
    RawtextLessthanSign {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            if (r.matches('/')) {
                t.createTempBuffer();
                t.advanceTransition(TokeniserState$14.RawtextEndTagOpen);
            }
            else {
                t.emit('<');
                t.transition(TokeniserState$14.Rawtext);
            }
        }
    }, 
    RawtextEndTagOpen {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            readEndTag(t, r, TokeniserState$15.RawtextEndTagName, TokeniserState$15.Rawtext);
        }
    }, 
    RawtextEndTagName {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            handleDataEndTag(t, r, TokeniserState$16.Rawtext);
        }
    }, 
    ScriptDataLessthanSign {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            switch (r.consume()) {
                case '/': {
                    t.createTempBuffer();
                    t.transition(TokeniserState$17.ScriptDataEndTagOpen);
                    break;
                }
                case '!': {
                    t.emit("<!");
                    t.transition(TokeniserState$17.ScriptDataEscapeStart);
                    break;
                }
                default: {
                    t.emit("<");
                    r.unconsume();
                    t.transition(TokeniserState$17.ScriptData);
                    break;
                }
            }
        }
    }, 
    ScriptDataEndTagOpen {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            readEndTag(t, r, TokeniserState$18.ScriptDataEndTagName, TokeniserState$18.ScriptData);
        }
    }, 
    ScriptDataEndTagName {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            handleDataEndTag(t, r, TokeniserState$19.ScriptData);
        }
    }, 
    ScriptDataEscapeStart {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            if (r.matches('-')) {
                t.emit('-');
                t.advanceTransition(TokeniserState$20.ScriptDataEscapeStartDash);
            }
            else {
                t.transition(TokeniserState$20.ScriptData);
            }
        }
    }, 
    ScriptDataEscapeStartDash {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            if (r.matches('-')) {
                t.emit('-');
                t.advanceTransition(TokeniserState$21.ScriptDataEscapedDashDash);
            }
            else {
                t.transition(TokeniserState$21.ScriptData);
            }
        }
    }, 
    ScriptDataEscaped {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            if (r.isEmpty()) {
                t.eofError(this);
                t.transition(TokeniserState$22.Data);
                return;
            }
            switch (r.current()) {
                case '-': {
                    t.emit('-');
                    t.advanceTransition(TokeniserState$22.ScriptDataEscapedDash);
                    break;
                }
                case '<': {
                    t.advanceTransition(TokeniserState$22.ScriptDataEscapedLessthanSign);
                    break;
                }
                case '\0': {
                    t.error(this);
                    r.advance();
                    t.emit('\ufffd');
                    break;
                }
                default: {
                    final String data = r.consumeToAny('-', '<', '\0');
                    t.emit(data);
                    break;
                }
            }
        }
    }, 
    ScriptDataEscapedDash {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            if (r.isEmpty()) {
                t.eofError(this);
                t.transition(TokeniserState$23.Data);
                return;
            }
            final char c = r.consume();
            switch (c) {
                case '-': {
                    t.emit(c);
                    t.transition(TokeniserState$23.ScriptDataEscapedDashDash);
                    break;
                }
                case '<': {
                    t.transition(TokeniserState$23.ScriptDataEscapedLessthanSign);
                    break;
                }
                case '\0': {
                    t.error(this);
                    t.emit('\ufffd');
                    t.transition(TokeniserState$23.ScriptDataEscaped);
                    break;
                }
                default: {
                    t.emit(c);
                    t.transition(TokeniserState$23.ScriptDataEscaped);
                    break;
                }
            }
        }
    }, 
    ScriptDataEscapedDashDash {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            if (r.isEmpty()) {
                t.eofError(this);
                t.transition(TokeniserState$24.Data);
                return;
            }
            final char c = r.consume();
            switch (c) {
                case '-': {
                    t.emit(c);
                    break;
                }
                case '<': {
                    t.transition(TokeniserState$24.ScriptDataEscapedLessthanSign);
                    break;
                }
                case '>': {
                    t.emit(c);
                    t.transition(TokeniserState$24.ScriptData);
                    break;
                }
                case '\0': {
                    t.error(this);
                    t.emit('\ufffd');
                    t.transition(TokeniserState$24.ScriptDataEscaped);
                    break;
                }
                default: {
                    t.emit(c);
                    t.transition(TokeniserState$24.ScriptDataEscaped);
                    break;
                }
            }
        }
    }, 
    ScriptDataEscapedLessthanSign {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            if (r.matchesLetter()) {
                t.createTempBuffer();
                t.dataBuffer.append(r.current());
                t.emit("<" + r.current());
                t.advanceTransition(TokeniserState$25.ScriptDataDoubleEscapeStart);
            }
            else if (r.matches('/')) {
                t.createTempBuffer();
                t.advanceTransition(TokeniserState$25.ScriptDataEscapedEndTagOpen);
            }
            else {
                t.emit('<');
                t.transition(TokeniserState$25.ScriptDataEscaped);
            }
        }
    }, 
    ScriptDataEscapedEndTagOpen {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            if (r.matchesLetter()) {
                t.createTagPending(false);
                t.tagPending.appendTagName(r.current());
                t.dataBuffer.append(r.current());
                t.advanceTransition(TokeniserState$26.ScriptDataEscapedEndTagName);
            }
            else {
                t.emit("</");
                t.transition(TokeniserState$26.ScriptDataEscaped);
            }
        }
    }, 
    ScriptDataEscapedEndTagName {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            handleDataEndTag(t, r, TokeniserState$27.ScriptDataEscaped);
        }
    }, 
    ScriptDataDoubleEscapeStart {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            handleDataDoubleEscapeTag(t, r, TokeniserState$28.ScriptDataDoubleEscaped, TokeniserState$28.ScriptDataEscaped);
        }
    }, 
    ScriptDataDoubleEscaped {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            final char c = r.current();
            switch (c) {
                case '-': {
                    t.emit(c);
                    t.advanceTransition(TokeniserState$29.ScriptDataDoubleEscapedDash);
                    break;
                }
                case '<': {
                    t.emit(c);
                    t.advanceTransition(TokeniserState$29.ScriptDataDoubleEscapedLessthanSign);
                    break;
                }
                case '\0': {
                    t.error(this);
                    r.advance();
                    t.emit('\ufffd');
                    break;
                }
                case '\uffff': {
                    t.eofError(this);
                    t.transition(TokeniserState$29.Data);
                    break;
                }
                default: {
                    final String data = r.consumeToAny('-', '<', '\0');
                    t.emit(data);
                    break;
                }
            }
        }
    }, 
    ScriptDataDoubleEscapedDash {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            final char c = r.consume();
            switch (c) {
                case '-': {
                    t.emit(c);
                    t.transition(TokeniserState$30.ScriptDataDoubleEscapedDashDash);
                    break;
                }
                case '<': {
                    t.emit(c);
                    t.transition(TokeniserState$30.ScriptDataDoubleEscapedLessthanSign);
                    break;
                }
                case '\0': {
                    t.error(this);
                    t.emit('\ufffd');
                    t.transition(TokeniserState$30.ScriptDataDoubleEscaped);
                    break;
                }
                case '\uffff': {
                    t.eofError(this);
                    t.transition(TokeniserState$30.Data);
                    break;
                }
                default: {
                    t.emit(c);
                    t.transition(TokeniserState$30.ScriptDataDoubleEscaped);
                    break;
                }
            }
        }
    }, 
    ScriptDataDoubleEscapedDashDash {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            final char c = r.consume();
            switch (c) {
                case '-': {
                    t.emit(c);
                    break;
                }
                case '<': {
                    t.emit(c);
                    t.transition(TokeniserState$31.ScriptDataDoubleEscapedLessthanSign);
                    break;
                }
                case '>': {
                    t.emit(c);
                    t.transition(TokeniserState$31.ScriptData);
                    break;
                }
                case '\0': {
                    t.error(this);
                    t.emit('\ufffd');
                    t.transition(TokeniserState$31.ScriptDataDoubleEscaped);
                    break;
                }
                case '\uffff': {
                    t.eofError(this);
                    t.transition(TokeniserState$31.Data);
                    break;
                }
                default: {
                    t.emit(c);
                    t.transition(TokeniserState$31.ScriptDataDoubleEscaped);
                    break;
                }
            }
        }
    }, 
    ScriptDataDoubleEscapedLessthanSign {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            if (r.matches('/')) {
                t.emit('/');
                t.createTempBuffer();
                t.advanceTransition(TokeniserState$32.ScriptDataDoubleEscapeEnd);
            }
            else {
                t.transition(TokeniserState$32.ScriptDataDoubleEscaped);
            }
        }
    }, 
    ScriptDataDoubleEscapeEnd {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            handleDataDoubleEscapeTag(t, r, TokeniserState$33.ScriptDataEscaped, TokeniserState$33.ScriptDataDoubleEscaped);
        }
    }, 
    BeforeAttributeName {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            final char c = r.consume();
            switch (c) {
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ': {
                    break;
                }
                case '/': {
                    t.transition(TokeniserState$34.SelfClosingStartTag);
                    break;
                }
                case '>': {
                    t.emitTagPending();
                    t.transition(TokeniserState$34.Data);
                    break;
                }
                case '\0': {
                    t.error(this);
                    t.tagPending.newAttribute();
                    r.unconsume();
                    t.transition(TokeniserState$34.AttributeName);
                    break;
                }
                case '\uffff': {
                    t.eofError(this);
                    t.transition(TokeniserState$34.Data);
                    break;
                }
                case '\"':
                case '\'':
                case '<':
                case '=': {
                    t.error(this);
                    t.tagPending.newAttribute();
                    t.tagPending.appendAttributeName(c);
                    t.transition(TokeniserState$34.AttributeName);
                    break;
                }
                default: {
                    t.tagPending.newAttribute();
                    r.unconsume();
                    t.transition(TokeniserState$34.AttributeName);
                    break;
                }
            }
        }
    }, 
    AttributeName {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            final String name = r.consumeToAnySorted(TokeniserState.attributeNameCharsSorted);
            t.tagPending.appendAttributeName(name);
            final char c = r.consume();
            switch (c) {
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ': {
                    t.transition(TokeniserState$35.AfterAttributeName);
                    break;
                }
                case '/': {
                    t.transition(TokeniserState$35.SelfClosingStartTag);
                    break;
                }
                case '=': {
                    t.transition(TokeniserState$35.BeforeAttributeValue);
                    break;
                }
                case '>': {
                    t.emitTagPending();
                    t.transition(TokeniserState$35.Data);
                    break;
                }
                case '\0': {
                    t.error(this);
                    t.tagPending.appendAttributeName('\ufffd');
                    break;
                }
                case '\uffff': {
                    t.eofError(this);
                    t.transition(TokeniserState$35.Data);
                    break;
                }
                case '\"':
                case '\'':
                case '<': {
                    t.error(this);
                    t.tagPending.appendAttributeName(c);
                    break;
                }
            }
        }
    }, 
    AfterAttributeName {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            final char c = r.consume();
            switch (c) {
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ': {
                    break;
                }
                case '/': {
                    t.transition(TokeniserState$36.SelfClosingStartTag);
                    break;
                }
                case '=': {
                    t.transition(TokeniserState$36.BeforeAttributeValue);
                    break;
                }
                case '>': {
                    t.emitTagPending();
                    t.transition(TokeniserState$36.Data);
                    break;
                }
                case '\0': {
                    t.error(this);
                    t.tagPending.appendAttributeName('\ufffd');
                    t.transition(TokeniserState$36.AttributeName);
                    break;
                }
                case '\uffff': {
                    t.eofError(this);
                    t.transition(TokeniserState$36.Data);
                    break;
                }
                case '\"':
                case '\'':
                case '<': {
                    t.error(this);
                    t.tagPending.newAttribute();
                    t.tagPending.appendAttributeName(c);
                    t.transition(TokeniserState$36.AttributeName);
                    break;
                }
                default: {
                    t.tagPending.newAttribute();
                    r.unconsume();
                    t.transition(TokeniserState$36.AttributeName);
                    break;
                }
            }
        }
    }, 
    BeforeAttributeValue {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            final char c = r.consume();
            switch (c) {
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ': {
                    break;
                }
                case '\"': {
                    t.transition(TokeniserState$37.AttributeValue_doubleQuoted);
                    break;
                }
                case '&': {
                    r.unconsume();
                    t.transition(TokeniserState$37.AttributeValue_unquoted);
                    break;
                }
                case '\'': {
                    t.transition(TokeniserState$37.AttributeValue_singleQuoted);
                    break;
                }
                case '\0': {
                    t.error(this);
                    t.tagPending.appendAttributeValue('\ufffd');
                    t.transition(TokeniserState$37.AttributeValue_unquoted);
                    break;
                }
                case '\uffff': {
                    t.eofError(this);
                    t.emitTagPending();
                    t.transition(TokeniserState$37.Data);
                    break;
                }
                case '>': {
                    t.error(this);
                    t.emitTagPending();
                    t.transition(TokeniserState$37.Data);
                    break;
                }
                case '<':
                case '=':
                case '`': {
                    t.error(this);
                    t.tagPending.appendAttributeValue(c);
                    t.transition(TokeniserState$37.AttributeValue_unquoted);
                    break;
                }
                default: {
                    r.unconsume();
                    t.transition(TokeniserState$37.AttributeValue_unquoted);
                    break;
                }
            }
        }
    }, 
    AttributeValue_doubleQuoted {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            final String value = r.consumeToAny(TokeniserState.attributeDoubleValueCharsSorted);
            if (value.length() > 0) {
                t.tagPending.appendAttributeValue(value);
            }
            else {
                t.tagPending.setEmptyAttributeValue();
            }
            final char c = r.consume();
            switch (c) {
                case '\"': {
                    t.transition(TokeniserState$38.AfterAttributeValue_quoted);
                    break;
                }
                case '&': {
                    final int[] ref = t.consumeCharacterReference('\"', true);
                    if (ref != null) {
                        t.tagPending.appendAttributeValue(ref);
                        break;
                    }
                    t.tagPending.appendAttributeValue('&');
                    break;
                }
                case '\0': {
                    t.error(this);
                    t.tagPending.appendAttributeValue('\ufffd');
                    break;
                }
                case '\uffff': {
                    t.eofError(this);
                    t.transition(TokeniserState$38.Data);
                    break;
                }
            }
        }
    }, 
    AttributeValue_singleQuoted {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            final String value = r.consumeToAny(TokeniserState.attributeSingleValueCharsSorted);
            if (value.length() > 0) {
                t.tagPending.appendAttributeValue(value);
            }
            else {
                t.tagPending.setEmptyAttributeValue();
            }
            final char c = r.consume();
            switch (c) {
                case '\'': {
                    t.transition(TokeniserState$39.AfterAttributeValue_quoted);
                    break;
                }
                case '&': {
                    final int[] ref = t.consumeCharacterReference('\'', true);
                    if (ref != null) {
                        t.tagPending.appendAttributeValue(ref);
                        break;
                    }
                    t.tagPending.appendAttributeValue('&');
                    break;
                }
                case '\0': {
                    t.error(this);
                    t.tagPending.appendAttributeValue('\ufffd');
                    break;
                }
                case '\uffff': {
                    t.eofError(this);
                    t.transition(TokeniserState$39.Data);
                    break;
                }
            }
        }
    }, 
    AttributeValue_unquoted {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            final String value = r.consumeToAnySorted(TokeniserState.attributeValueUnquoted);
            if (value.length() > 0) {
                t.tagPending.appendAttributeValue(value);
            }
            final char c = r.consume();
            switch (c) {
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ': {
                    t.transition(TokeniserState$40.BeforeAttributeName);
                    break;
                }
                case '&': {
                    final int[] ref = t.consumeCharacterReference('>', true);
                    if (ref != null) {
                        t.tagPending.appendAttributeValue(ref);
                        break;
                    }
                    t.tagPending.appendAttributeValue('&');
                    break;
                }
                case '>': {
                    t.emitTagPending();
                    t.transition(TokeniserState$40.Data);
                    break;
                }
                case '\0': {
                    t.error(this);
                    t.tagPending.appendAttributeValue('\ufffd');
                    break;
                }
                case '\uffff': {
                    t.eofError(this);
                    t.transition(TokeniserState$40.Data);
                    break;
                }
                case '\"':
                case '\'':
                case '<':
                case '=':
                case '`': {
                    t.error(this);
                    t.tagPending.appendAttributeValue(c);
                    break;
                }
            }
        }
    }, 
    AfterAttributeValue_quoted {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            final char c = r.consume();
            switch (c) {
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ': {
                    t.transition(TokeniserState$41.BeforeAttributeName);
                    break;
                }
                case '/': {
                    t.transition(TokeniserState$41.SelfClosingStartTag);
                    break;
                }
                case '>': {
                    t.emitTagPending();
                    t.transition(TokeniserState$41.Data);
                    break;
                }
                case '\uffff': {
                    t.eofError(this);
                    t.transition(TokeniserState$41.Data);
                    break;
                }
                default: {
                    t.error(this);
                    r.unconsume();
                    t.transition(TokeniserState$41.BeforeAttributeName);
                    break;
                }
            }
        }
    }, 
    SelfClosingStartTag {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            final char c = r.consume();
            switch (c) {
                case '>': {
                    t.tagPending.selfClosing = true;
                    t.emitTagPending();
                    t.transition(TokeniserState$42.Data);
                    break;
                }
                case '\uffff': {
                    t.eofError(this);
                    t.transition(TokeniserState$42.Data);
                    break;
                }
                default: {
                    t.error(this);
                    r.unconsume();
                    t.transition(TokeniserState$42.BeforeAttributeName);
                    break;
                }
            }
        }
    }, 
    BogusComment {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            r.unconsume();
            final Token.Comment comment = new Token.Comment();
            comment.bogus = true;
            comment.data.append(r.consumeTo('>'));
            t.emit(comment);
            t.advanceTransition(TokeniserState$43.Data);
        }
    }, 
    MarkupDeclarationOpen {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            if (r.matchConsume("--")) {
                t.createCommentPending();
                t.transition(TokeniserState$44.CommentStart);
            }
            else if (r.matchConsumeIgnoreCase("DOCTYPE")) {
                t.transition(TokeniserState$44.Doctype);
            }
            else if (r.matchConsume("[CDATA[")) {
                t.transition(TokeniserState$44.CdataSection);
            }
            else {
                t.error(this);
                t.advanceTransition(TokeniserState$44.BogusComment);
            }
        }
    }, 
    CommentStart {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            final char c = r.consume();
            switch (c) {
                case '-': {
                    t.transition(TokeniserState$45.CommentStartDash);
                    break;
                }
                case '\0': {
                    t.error(this);
                    t.commentPending.data.append('\ufffd');
                    t.transition(TokeniserState$45.Comment);
                    break;
                }
                case '>': {
                    t.error(this);
                    t.emitCommentPending();
                    t.transition(TokeniserState$45.Data);
                    break;
                }
                case '\uffff': {
                    t.eofError(this);
                    t.emitCommentPending();
                    t.transition(TokeniserState$45.Data);
                    break;
                }
                default: {
                    t.commentPending.data.append(c);
                    t.transition(TokeniserState$45.Comment);
                    break;
                }
            }
        }
    }, 
    CommentStartDash {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            final char c = r.consume();
            switch (c) {
                case '-': {
                    t.transition(TokeniserState$46.CommentStartDash);
                    break;
                }
                case '\0': {
                    t.error(this);
                    t.commentPending.data.append('\ufffd');
                    t.transition(TokeniserState$46.Comment);
                    break;
                }
                case '>': {
                    t.error(this);
                    t.emitCommentPending();
                    t.transition(TokeniserState$46.Data);
                    break;
                }
                case '\uffff': {
                    t.eofError(this);
                    t.emitCommentPending();
                    t.transition(TokeniserState$46.Data);
                    break;
                }
                default: {
                    t.commentPending.data.append(c);
                    t.transition(TokeniserState$46.Comment);
                    break;
                }
            }
        }
    }, 
    Comment {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            final char c = r.current();
            switch (c) {
                case '-': {
                    t.advanceTransition(TokeniserState$47.CommentEndDash);
                    break;
                }
                case '\0': {
                    t.error(this);
                    r.advance();
                    t.commentPending.data.append('\ufffd');
                    break;
                }
                case '\uffff': {
                    t.eofError(this);
                    t.emitCommentPending();
                    t.transition(TokeniserState$47.Data);
                    break;
                }
                default: {
                    t.commentPending.data.append(r.consumeToAny('-', '\0'));
                    break;
                }
            }
        }
    }, 
    CommentEndDash {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            final char c = r.consume();
            switch (c) {
                case '-': {
                    t.transition(TokeniserState$48.CommentEnd);
                    break;
                }
                case '\0': {
                    t.error(this);
                    t.commentPending.data.append('-').append('\ufffd');
                    t.transition(TokeniserState$48.Comment);
                    break;
                }
                case '\uffff': {
                    t.eofError(this);
                    t.emitCommentPending();
                    t.transition(TokeniserState$48.Data);
                    break;
                }
                default: {
                    t.commentPending.data.append('-').append(c);
                    t.transition(TokeniserState$48.Comment);
                    break;
                }
            }
        }
    }, 
    CommentEnd {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            final char c = r.consume();
            switch (c) {
                case '>': {
                    t.emitCommentPending();
                    t.transition(TokeniserState$49.Data);
                    break;
                }
                case '\0': {
                    t.error(this);
                    t.commentPending.data.append("--").append('\ufffd');
                    t.transition(TokeniserState$49.Comment);
                    break;
                }
                case '!': {
                    t.error(this);
                    t.transition(TokeniserState$49.CommentEndBang);
                    break;
                }
                case '-': {
                    t.error(this);
                    t.commentPending.data.append('-');
                    break;
                }
                case '\uffff': {
                    t.eofError(this);
                    t.emitCommentPending();
                    t.transition(TokeniserState$49.Data);
                    break;
                }
                default: {
                    t.error(this);
                    t.commentPending.data.append("--").append(c);
                    t.transition(TokeniserState$49.Comment);
                    break;
                }
            }
        }
    }, 
    CommentEndBang {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            final char c = r.consume();
            switch (c) {
                case '-': {
                    t.commentPending.data.append("--!");
                    t.transition(TokeniserState$50.CommentEndDash);
                    break;
                }
                case '>': {
                    t.emitCommentPending();
                    t.transition(TokeniserState$50.Data);
                    break;
                }
                case '\0': {
                    t.error(this);
                    t.commentPending.data.append("--!").append('\ufffd');
                    t.transition(TokeniserState$50.Comment);
                    break;
                }
                case '\uffff': {
                    t.eofError(this);
                    t.emitCommentPending();
                    t.transition(TokeniserState$50.Data);
                    break;
                }
                default: {
                    t.commentPending.data.append("--!").append(c);
                    t.transition(TokeniserState$50.Comment);
                    break;
                }
            }
        }
    }, 
    Doctype {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            final char c = r.consume();
            switch (c) {
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ': {
                    t.transition(TokeniserState$51.BeforeDoctypeName);
                    break;
                }
                case '\uffff': {
                    t.eofError(this);
                }
                case '>': {
                    t.error(this);
                    t.createDoctypePending();
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(TokeniserState$51.Data);
                    break;
                }
                default: {
                    t.error(this);
                    t.transition(TokeniserState$51.BeforeDoctypeName);
                    break;
                }
            }
        }
    }, 
    BeforeDoctypeName {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            if (r.matchesLetter()) {
                t.createDoctypePending();
                t.transition(TokeniserState$52.DoctypeName);
                return;
            }
            final char c = r.consume();
            switch (c) {
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ': {
                    break;
                }
                case '\0': {
                    t.error(this);
                    t.createDoctypePending();
                    t.doctypePending.name.append('\ufffd');
                    t.transition(TokeniserState$52.DoctypeName);
                    break;
                }
                case '\uffff': {
                    t.eofError(this);
                    t.createDoctypePending();
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(TokeniserState$52.Data);
                    break;
                }
                default: {
                    t.createDoctypePending();
                    t.doctypePending.name.append(c);
                    t.transition(TokeniserState$52.DoctypeName);
                    break;
                }
            }
        }
    }, 
    DoctypeName {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            if (r.matchesLetter()) {
                final String name = r.consumeLetterSequence();
                t.doctypePending.name.append(name);
                return;
            }
            final char c = r.consume();
            switch (c) {
                case '>': {
                    t.emitDoctypePending();
                    t.transition(TokeniserState$53.Data);
                    break;
                }
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ': {
                    t.transition(TokeniserState$53.AfterDoctypeName);
                    break;
                }
                case '\0': {
                    t.error(this);
                    t.doctypePending.name.append('\ufffd');
                    break;
                }
                case '\uffff': {
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(TokeniserState$53.Data);
                    break;
                }
                default: {
                    t.doctypePending.name.append(c);
                    break;
                }
            }
        }
    }, 
    AfterDoctypeName {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            if (r.isEmpty()) {
                t.eofError(this);
                t.doctypePending.forceQuirks = true;
                t.emitDoctypePending();
                t.transition(TokeniserState$54.Data);
                return;
            }
            if (r.matchesAny('\t', '\n', '\r', '\f', ' ')) {
                r.advance();
            }
            else if (r.matches('>')) {
                t.emitDoctypePending();
                t.advanceTransition(TokeniserState$54.Data);
            }
            else if (r.matchConsumeIgnoreCase("PUBLIC")) {
                t.doctypePending.pubSysKey = "PUBLIC";
                t.transition(TokeniserState$54.AfterDoctypePublicKeyword);
            }
            else if (r.matchConsumeIgnoreCase("SYSTEM")) {
                t.doctypePending.pubSysKey = "SYSTEM";
                t.transition(TokeniserState$54.AfterDoctypeSystemKeyword);
            }
            else {
                t.error(this);
                t.doctypePending.forceQuirks = true;
                t.advanceTransition(TokeniserState$54.BogusDoctype);
            }
        }
    }, 
    AfterDoctypePublicKeyword {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            final char c = r.consume();
            switch (c) {
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ': {
                    t.transition(TokeniserState$55.BeforeDoctypePublicIdentifier);
                    break;
                }
                case '\"': {
                    t.error(this);
                    t.transition(TokeniserState$55.DoctypePublicIdentifier_doubleQuoted);
                    break;
                }
                case '\'': {
                    t.error(this);
                    t.transition(TokeniserState$55.DoctypePublicIdentifier_singleQuoted);
                    break;
                }
                case '>': {
                    t.error(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(TokeniserState$55.Data);
                    break;
                }
                case '\uffff': {
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(TokeniserState$55.Data);
                    break;
                }
                default: {
                    t.error(this);
                    t.doctypePending.forceQuirks = true;
                    t.transition(TokeniserState$55.BogusDoctype);
                    break;
                }
            }
        }
    }, 
    BeforeDoctypePublicIdentifier {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            final char c = r.consume();
            switch (c) {
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ': {
                    break;
                }
                case '\"': {
                    t.transition(TokeniserState$56.DoctypePublicIdentifier_doubleQuoted);
                    break;
                }
                case '\'': {
                    t.transition(TokeniserState$56.DoctypePublicIdentifier_singleQuoted);
                    break;
                }
                case '>': {
                    t.error(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(TokeniserState$56.Data);
                    break;
                }
                case '\uffff': {
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(TokeniserState$56.Data);
                    break;
                }
                default: {
                    t.error(this);
                    t.doctypePending.forceQuirks = true;
                    t.transition(TokeniserState$56.BogusDoctype);
                    break;
                }
            }
        }
    }, 
    DoctypePublicIdentifier_doubleQuoted {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            final char c = r.consume();
            switch (c) {
                case '\"': {
                    t.transition(TokeniserState$57.AfterDoctypePublicIdentifier);
                    break;
                }
                case '\0': {
                    t.error(this);
                    t.doctypePending.publicIdentifier.append('\ufffd');
                    break;
                }
                case '>': {
                    t.error(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(TokeniserState$57.Data);
                    break;
                }
                case '\uffff': {
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(TokeniserState$57.Data);
                    break;
                }
                default: {
                    t.doctypePending.publicIdentifier.append(c);
                    break;
                }
            }
        }
    }, 
    DoctypePublicIdentifier_singleQuoted {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            final char c = r.consume();
            switch (c) {
                case '\'': {
                    t.transition(TokeniserState$58.AfterDoctypePublicIdentifier);
                    break;
                }
                case '\0': {
                    t.error(this);
                    t.doctypePending.publicIdentifier.append('\ufffd');
                    break;
                }
                case '>': {
                    t.error(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(TokeniserState$58.Data);
                    break;
                }
                case '\uffff': {
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(TokeniserState$58.Data);
                    break;
                }
                default: {
                    t.doctypePending.publicIdentifier.append(c);
                    break;
                }
            }
        }
    }, 
    AfterDoctypePublicIdentifier {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            final char c = r.consume();
            switch (c) {
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ': {
                    t.transition(TokeniserState$59.BetweenDoctypePublicAndSystemIdentifiers);
                    break;
                }
                case '>': {
                    t.emitDoctypePending();
                    t.transition(TokeniserState$59.Data);
                    break;
                }
                case '\"': {
                    t.error(this);
                    t.transition(TokeniserState$59.DoctypeSystemIdentifier_doubleQuoted);
                    break;
                }
                case '\'': {
                    t.error(this);
                    t.transition(TokeniserState$59.DoctypeSystemIdentifier_singleQuoted);
                    break;
                }
                case '\uffff': {
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(TokeniserState$59.Data);
                    break;
                }
                default: {
                    t.error(this);
                    t.doctypePending.forceQuirks = true;
                    t.transition(TokeniserState$59.BogusDoctype);
                    break;
                }
            }
        }
    }, 
    BetweenDoctypePublicAndSystemIdentifiers {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            final char c = r.consume();
            switch (c) {
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ': {
                    break;
                }
                case '>': {
                    t.emitDoctypePending();
                    t.transition(TokeniserState$60.Data);
                    break;
                }
                case '\"': {
                    t.error(this);
                    t.transition(TokeniserState$60.DoctypeSystemIdentifier_doubleQuoted);
                    break;
                }
                case '\'': {
                    t.error(this);
                    t.transition(TokeniserState$60.DoctypeSystemIdentifier_singleQuoted);
                    break;
                }
                case '\uffff': {
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(TokeniserState$60.Data);
                    break;
                }
                default: {
                    t.error(this);
                    t.doctypePending.forceQuirks = true;
                    t.transition(TokeniserState$60.BogusDoctype);
                    break;
                }
            }
        }
    }, 
    AfterDoctypeSystemKeyword {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            final char c = r.consume();
            switch (c) {
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ': {
                    t.transition(TokeniserState$61.BeforeDoctypeSystemIdentifier);
                    break;
                }
                case '>': {
                    t.error(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(TokeniserState$61.Data);
                    break;
                }
                case '\"': {
                    t.error(this);
                    t.transition(TokeniserState$61.DoctypeSystemIdentifier_doubleQuoted);
                    break;
                }
                case '\'': {
                    t.error(this);
                    t.transition(TokeniserState$61.DoctypeSystemIdentifier_singleQuoted);
                    break;
                }
                case '\uffff': {
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(TokeniserState$61.Data);
                    break;
                }
                default: {
                    t.error(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    break;
                }
            }
        }
    }, 
    BeforeDoctypeSystemIdentifier {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            final char c = r.consume();
            switch (c) {
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ': {
                    break;
                }
                case '\"': {
                    t.transition(TokeniserState$62.DoctypeSystemIdentifier_doubleQuoted);
                    break;
                }
                case '\'': {
                    t.transition(TokeniserState$62.DoctypeSystemIdentifier_singleQuoted);
                    break;
                }
                case '>': {
                    t.error(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(TokeniserState$62.Data);
                    break;
                }
                case '\uffff': {
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(TokeniserState$62.Data);
                    break;
                }
                default: {
                    t.error(this);
                    t.doctypePending.forceQuirks = true;
                    t.transition(TokeniserState$62.BogusDoctype);
                    break;
                }
            }
        }
    }, 
    DoctypeSystemIdentifier_doubleQuoted {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            final char c = r.consume();
            switch (c) {
                case '\"': {
                    t.transition(TokeniserState$63.AfterDoctypeSystemIdentifier);
                    break;
                }
                case '\0': {
                    t.error(this);
                    t.doctypePending.systemIdentifier.append('\ufffd');
                    break;
                }
                case '>': {
                    t.error(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(TokeniserState$63.Data);
                    break;
                }
                case '\uffff': {
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(TokeniserState$63.Data);
                    break;
                }
                default: {
                    t.doctypePending.systemIdentifier.append(c);
                    break;
                }
            }
        }
    }, 
    DoctypeSystemIdentifier_singleQuoted {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            final char c = r.consume();
            switch (c) {
                case '\'': {
                    t.transition(TokeniserState$64.AfterDoctypeSystemIdentifier);
                    break;
                }
                case '\0': {
                    t.error(this);
                    t.doctypePending.systemIdentifier.append('\ufffd');
                    break;
                }
                case '>': {
                    t.error(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(TokeniserState$64.Data);
                    break;
                }
                case '\uffff': {
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(TokeniserState$64.Data);
                    break;
                }
                default: {
                    t.doctypePending.systemIdentifier.append(c);
                    break;
                }
            }
        }
    }, 
    AfterDoctypeSystemIdentifier {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            final char c = r.consume();
            switch (c) {
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ': {
                    break;
                }
                case '>': {
                    t.emitDoctypePending();
                    t.transition(TokeniserState$65.Data);
                    break;
                }
                case '\uffff': {
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(TokeniserState$65.Data);
                    break;
                }
                default: {
                    t.error(this);
                    t.transition(TokeniserState$65.BogusDoctype);
                    break;
                }
            }
        }
    }, 
    BogusDoctype {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            final char c = r.consume();
            switch (c) {
                case '>': {
                    t.emitDoctypePending();
                    t.transition(TokeniserState$66.Data);
                    break;
                }
                case '\uffff': {
                    t.emitDoctypePending();
                    t.transition(TokeniserState$66.Data);
                    break;
                }
            }
        }
    }, 
    CdataSection {
        @Override
        void read(final Tokeniser t, final CharacterReader r) {
            final String data = r.consumeTo("]]>");
            t.emit(data);
            r.matchConsume("]]>");
            t.transition(TokeniserState$67.Data);
        }
    };
    
    static final char nullChar = '\0';
    private static final char[] attributeSingleValueCharsSorted;
    private static final char[] attributeDoubleValueCharsSorted;
    private static final char[] attributeNameCharsSorted;
    private static final char[] attributeValueUnquoted;
    private static final char replacementChar = '\ufffd';
    private static final String replacementStr;
    private static final char eof = '\uffff';
    
    abstract void read(final Tokeniser p0, final CharacterReader p1);
    
    private static void handleDataEndTag(final Tokeniser t, final CharacterReader r, final TokeniserState elseTransition) {
        if (r.matchesLetter()) {
            final String name = r.consumeLetterSequence();
            t.tagPending.appendTagName(name);
            t.dataBuffer.append(name);
            return;
        }
        boolean needsExitTransition = false;
        if (t.isAppropriateEndTagToken() && !r.isEmpty()) {
            final char c = r.consume();
            switch (c) {
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ': {
                    t.transition(TokeniserState.BeforeAttributeName);
                    break;
                }
                case '/': {
                    t.transition(TokeniserState.SelfClosingStartTag);
                    break;
                }
                case '>': {
                    t.emitTagPending();
                    t.transition(TokeniserState.Data);
                    break;
                }
                default: {
                    t.dataBuffer.append(c);
                    needsExitTransition = true;
                    break;
                }
            }
        }
        else {
            needsExitTransition = true;
        }
        if (needsExitTransition) {
            t.emit("</" + t.dataBuffer.toString());
            t.transition(elseTransition);
        }
    }
    
    private static void readData(final Tokeniser t, final CharacterReader r, final TokeniserState current, final TokeniserState advance) {
        switch (r.current()) {
            case '<': {
                t.advanceTransition(advance);
                break;
            }
            case '\0': {
                t.error(current);
                r.advance();
                t.emit('\ufffd');
                break;
            }
            case '\uffff': {
                t.emit(new Token.EOF());
                break;
            }
            default: {
                final String data = r.consumeToAny('<', '\0');
                t.emit(data);
                break;
            }
        }
    }
    
    private static void readCharRef(final Tokeniser t, final TokeniserState advance) {
        final int[] c = t.consumeCharacterReference(null, false);
        if (c == null) {
            t.emit('&');
        }
        else {
            t.emit(c);
        }
        t.transition(advance);
    }
    
    private static void readEndTag(final Tokeniser t, final CharacterReader r, final TokeniserState a, final TokeniserState b) {
        if (r.matchesLetter()) {
            t.createTagPending(false);
            t.transition(a);
        }
        else {
            t.emit("</");
            t.transition(b);
        }
    }
    
    private static void handleDataDoubleEscapeTag(final Tokeniser t, final CharacterReader r, final TokeniserState primary, final TokeniserState fallback) {
        if (r.matchesLetter()) {
            final String name = r.consumeLetterSequence();
            t.dataBuffer.append(name);
            t.emit(name);
            return;
        }
        final char c = r.consume();
        switch (c) {
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ':
            case '/':
            case '>': {
                if (t.dataBuffer.toString().equals("script")) {
                    t.transition(primary);
                }
                else {
                    t.transition(fallback);
                }
                t.emit(c);
                break;
            }
            default: {
                r.unconsume();
                t.transition(fallback);
                break;
            }
        }
    }
    
    static {
        attributeSingleValueCharsSorted = new char[] { '\'', '&', '\0' };
        attributeDoubleValueCharsSorted = new char[] { '\"', '&', '\0' };
        attributeNameCharsSorted = new char[] { '\t', '\n', '\r', '\f', ' ', '/', '=', '>', '\0', '\"', '\'', '<' };
        attributeValueUnquoted = new char[] { '\t', '\n', '\r', '\f', ' ', '&', '>', '\0', '\"', '\'', '<', '=', '`' };
        replacementStr = String.valueOf('\ufffd');
        Arrays.sort(TokeniserState.attributeSingleValueCharsSorted);
        Arrays.sort(TokeniserState.attributeDoubleValueCharsSorted);
        Arrays.sort(TokeniserState.attributeNameCharsSorted);
        Arrays.sort(TokeniserState.attributeValueUnquoted);
    }
}
