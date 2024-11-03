package org.jsoup.parser;

enum TokeniserState {
   Data {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         switch (r.current()) {
            case '\u0000':
               t.error(this);
               t.emit(r.consume());
               break;
            case '&':
               t.advanceTransition(CharacterReferenceInData);
               break;
            case '<':
               t.advanceTransition(TagOpen);
               break;
            case '\uffff':
               t.emit(new Token.EOF());
               break;
            default:
               String data = r.consumeData();
               t.emit(data);
         }
      }
   },
   CharacterReferenceInData {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         TokeniserState.readCharRef(t, Data);
      }
   },
   Rcdata {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         switch (r.current()) {
            case '\u0000':
               t.error(this);
               r.advance();
               t.emit('�');
               break;
            case '&':
               t.advanceTransition(CharacterReferenceInRcdata);
               break;
            case '<':
               t.advanceTransition(RcdataLessthanSign);
               break;
            case '\uffff':
               t.emit(new Token.EOF());
               break;
            default:
               String data = r.consumeData();
               t.emit(data);
         }
      }
   },
   CharacterReferenceInRcdata {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         TokeniserState.readCharRef(t, Rcdata);
      }
   },
   Rawtext {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         TokeniserState.readRawData(t, r, this, RawtextLessthanSign);
      }
   },
   ScriptData {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         TokeniserState.readRawData(t, r, this, ScriptDataLessthanSign);
      }
   },
   PLAINTEXT {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         switch (r.current()) {
            case '\u0000':
               t.error(this);
               r.advance();
               t.emit('�');
               break;
            case '\uffff':
               t.emit(new Token.EOF());
               break;
            default:
               String data = r.consumeTo('\u0000');
               t.emit(data);
         }
      }
   },
   TagOpen {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         switch (r.current()) {
            case '!':
               t.advanceTransition(MarkupDeclarationOpen);
               break;
            case '/':
               t.advanceTransition(EndTagOpen);
               break;
            case '?':
               t.createBogusCommentPending();
               t.transition(BogusComment);
               break;
            default:
               if (r.matchesAsciiAlpha()) {
                  t.createTagPending(true);
                  t.transition(TagName);
               } else {
                  t.error(this);
                  t.emit('<');
                  t.transition(Data);
               }
         }
      }
   },
   EndTagOpen {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         if (r.isEmpty()) {
            t.eofError(this);
            t.emit("</");
            t.transition(Data);
         } else if (r.matchesAsciiAlpha()) {
            t.createTagPending(false);
            t.transition(TagName);
         } else if (r.matches('>')) {
            t.error(this);
            t.advanceTransition(Data);
         } else {
            t.error(this);
            t.createBogusCommentPending();
            t.commentPending.append('/');
            t.transition(BogusComment);
         }
      }
   },
   TagName {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         String tagName = r.consumeTagName();
         t.tagPending.appendTagName(tagName);
         char c = r.consume();
         switch (c) {
            case '\u0000':
               t.tagPending.appendTagName(TokeniserState.replacementStr);
               break;
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ':
               t.transition(BeforeAttributeName);
               break;
            case '/':
               t.transition(SelfClosingStartTag);
               break;
            case '<':
               r.unconsume();
               t.error(this);
            case '>':
               t.emitTagPending();
               t.transition(Data);
               break;
            case '\uffff':
               t.eofError(this);
               t.transition(Data);
               break;
            default:
               t.tagPending.appendTagName(c);
         }
      }
   },
   RcdataLessthanSign {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         if (r.matches('/')) {
            t.createTempBuffer();
            t.advanceTransition(RCDATAEndTagOpen);
         } else if (r.matchesAsciiAlpha() && t.appropriateEndTagName() != null && !r.containsIgnoreCase(t.appropriateEndTagSeq())) {
            t.tagPending = t.createTagPending(false).name(t.appropriateEndTagName());
            t.emitTagPending();
            t.transition(TagOpen);
         } else {
            t.emit("<");
            t.transition(Rcdata);
         }
      }
   },
   RCDATAEndTagOpen {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         if (r.matchesAsciiAlpha()) {
            t.createTagPending(false);
            t.tagPending.appendTagName(r.current());
            t.dataBuffer.append(r.current());
            t.advanceTransition(RCDATAEndTagName);
         } else {
            t.emit("</");
            t.transition(Rcdata);
         }
      }
   },
   RCDATAEndTagName {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         if (r.matchesAsciiAlpha()) {
            String name = r.consumeLetterSequence();
            t.tagPending.appendTagName(name);
            t.dataBuffer.append(name);
         } else {
            char c = r.consume();
            switch (c) {
               case '\t':
               case '\n':
               case '\f':
               case '\r':
               case ' ':
                  if (t.isAppropriateEndTagToken()) {
                     t.transition(BeforeAttributeName);
                  } else {
                     this.anythingElse(t, r);
                  }
                  break;
               case '/':
                  if (t.isAppropriateEndTagToken()) {
                     t.transition(SelfClosingStartTag);
                  } else {
                     this.anythingElse(t, r);
                  }
                  break;
               case '>':
                  if (t.isAppropriateEndTagToken()) {
                     t.emitTagPending();
                     t.transition(Data);
                  } else {
                     this.anythingElse(t, r);
                  }
                  break;
               default:
                  this.anythingElse(t, r);
            }
         }
      }

      private void anythingElse(Tokeniser t, CharacterReader r) {
         t.emit("</");
         t.emit(t.dataBuffer);
         r.unconsume();
         t.transition(Rcdata);
      }
   },
   RawtextLessthanSign {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         if (r.matches('/')) {
            t.createTempBuffer();
            t.advanceTransition(RawtextEndTagOpen);
         } else {
            t.emit('<');
            t.transition(Rawtext);
         }
      }
   },
   RawtextEndTagOpen {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         TokeniserState.readEndTag(t, r, RawtextEndTagName, Rawtext);
      }
   },
   RawtextEndTagName {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         TokeniserState.handleDataEndTag(t, r, Rawtext);
      }
   },
   ScriptDataLessthanSign {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         switch (r.consume()) {
            case '!':
               t.emit("<!");
               t.transition(ScriptDataEscapeStart);
               break;
            case '/':
               t.createTempBuffer();
               t.transition(ScriptDataEndTagOpen);
               break;
            case '\uffff':
               t.emit("<");
               t.eofError(this);
               t.transition(Data);
               break;
            default:
               t.emit("<");
               r.unconsume();
               t.transition(ScriptData);
         }
      }
   },
   ScriptDataEndTagOpen {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         TokeniserState.readEndTag(t, r, ScriptDataEndTagName, ScriptData);
      }
   },
   ScriptDataEndTagName {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         TokeniserState.handleDataEndTag(t, r, ScriptData);
      }
   },
   ScriptDataEscapeStart {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         if (r.matches('-')) {
            t.emit('-');
            t.advanceTransition(ScriptDataEscapeStartDash);
         } else {
            t.transition(ScriptData);
         }
      }
   },
   ScriptDataEscapeStartDash {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         if (r.matches('-')) {
            t.emit('-');
            t.advanceTransition(ScriptDataEscapedDashDash);
         } else {
            t.transition(ScriptData);
         }
      }
   },
   ScriptDataEscaped {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         if (r.isEmpty()) {
            t.eofError(this);
            t.transition(Data);
         } else {
            switch (r.current()) {
               case '\u0000':
                  t.error(this);
                  r.advance();
                  t.emit('�');
                  break;
               case '-':
                  t.emit('-');
                  t.advanceTransition(ScriptDataEscapedDash);
                  break;
               case '<':
                  t.advanceTransition(ScriptDataEscapedLessthanSign);
                  break;
               default:
                  String data = r.consumeToAny('-', '<', '\u0000');
                  t.emit(data);
            }
         }
      }
   },
   ScriptDataEscapedDash {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         if (r.isEmpty()) {
            t.eofError(this);
            t.transition(Data);
         } else {
            char c = r.consume();
            switch (c) {
               case '\u0000':
                  t.error(this);
                  t.emit('�');
                  t.transition(ScriptDataEscaped);
                  break;
               case '-':
                  t.emit(c);
                  t.transition(ScriptDataEscapedDashDash);
                  break;
               case '<':
                  t.transition(ScriptDataEscapedLessthanSign);
                  break;
               default:
                  t.emit(c);
                  t.transition(ScriptDataEscaped);
            }
         }
      }
   },
   ScriptDataEscapedDashDash {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         if (r.isEmpty()) {
            t.eofError(this);
            t.transition(Data);
         } else {
            char c = r.consume();
            switch (c) {
               case '\u0000':
                  t.error(this);
                  t.emit('�');
                  t.transition(ScriptDataEscaped);
                  break;
               case '-':
                  t.emit(c);
                  break;
               case '<':
                  t.transition(ScriptDataEscapedLessthanSign);
                  break;
               case '>':
                  t.emit(c);
                  t.transition(ScriptData);
                  break;
               default:
                  t.emit(c);
                  t.transition(ScriptDataEscaped);
            }
         }
      }
   },
   ScriptDataEscapedLessthanSign {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         if (r.matchesAsciiAlpha()) {
            t.createTempBuffer();
            t.dataBuffer.append(r.current());
            t.emit("<");
            t.emit(r.current());
            t.advanceTransition(ScriptDataDoubleEscapeStart);
         } else if (r.matches('/')) {
            t.createTempBuffer();
            t.advanceTransition(ScriptDataEscapedEndTagOpen);
         } else {
            t.emit('<');
            t.transition(ScriptDataEscaped);
         }
      }
   },
   ScriptDataEscapedEndTagOpen {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         if (r.matchesAsciiAlpha()) {
            t.createTagPending(false);
            t.tagPending.appendTagName(r.current());
            t.dataBuffer.append(r.current());
            t.advanceTransition(ScriptDataEscapedEndTagName);
         } else {
            t.emit("</");
            t.transition(ScriptDataEscaped);
         }
      }
   },
   ScriptDataEscapedEndTagName {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         TokeniserState.handleDataEndTag(t, r, ScriptDataEscaped);
      }
   },
   ScriptDataDoubleEscapeStart {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         TokeniserState.handleDataDoubleEscapeTag(t, r, ScriptDataDoubleEscaped, ScriptDataEscaped);
      }
   },
   ScriptDataDoubleEscaped {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         char c = r.current();
         switch (c) {
            case '\u0000':
               t.error(this);
               r.advance();
               t.emit('�');
               break;
            case '-':
               t.emit(c);
               t.advanceTransition(ScriptDataDoubleEscapedDash);
               break;
            case '<':
               t.emit(c);
               t.advanceTransition(ScriptDataDoubleEscapedLessthanSign);
               break;
            case '\uffff':
               t.eofError(this);
               t.transition(Data);
               break;
            default:
               String data = r.consumeToAny('-', '<', '\u0000');
               t.emit(data);
         }
      }
   },
   ScriptDataDoubleEscapedDash {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         char c = r.consume();
         switch (c) {
            case '\u0000':
               t.error(this);
               t.emit('�');
               t.transition(ScriptDataDoubleEscaped);
               break;
            case '-':
               t.emit(c);
               t.transition(ScriptDataDoubleEscapedDashDash);
               break;
            case '<':
               t.emit(c);
               t.transition(ScriptDataDoubleEscapedLessthanSign);
               break;
            case '\uffff':
               t.eofError(this);
               t.transition(Data);
               break;
            default:
               t.emit(c);
               t.transition(ScriptDataDoubleEscaped);
         }
      }
   },
   ScriptDataDoubleEscapedDashDash {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         char c = r.consume();
         switch (c) {
            case '\u0000':
               t.error(this);
               t.emit('�');
               t.transition(ScriptDataDoubleEscaped);
               break;
            case '-':
               t.emit(c);
               break;
            case '<':
               t.emit(c);
               t.transition(ScriptDataDoubleEscapedLessthanSign);
               break;
            case '>':
               t.emit(c);
               t.transition(ScriptData);
               break;
            case '\uffff':
               t.eofError(this);
               t.transition(Data);
               break;
            default:
               t.emit(c);
               t.transition(ScriptDataDoubleEscaped);
         }
      }
   },
   ScriptDataDoubleEscapedLessthanSign {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         if (r.matches('/')) {
            t.emit('/');
            t.createTempBuffer();
            t.advanceTransition(ScriptDataDoubleEscapeEnd);
         } else {
            t.transition(ScriptDataDoubleEscaped);
         }
      }
   },
   ScriptDataDoubleEscapeEnd {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         TokeniserState.handleDataDoubleEscapeTag(t, r, ScriptDataEscaped, ScriptDataDoubleEscaped);
      }
   },
   BeforeAttributeName {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         char c = r.consume();
         switch (c) {
            case '\u0000':
               r.unconsume();
               t.error(this);
               t.tagPending.newAttribute();
               t.transition(AttributeName);
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ':
               break;
            case '"':
            case '\'':
            case '=':
               t.error(this);
               t.tagPending.newAttribute();
               t.tagPending.appendAttributeName(c);
               t.transition(AttributeName);
               break;
            case '/':
               t.transition(SelfClosingStartTag);
               break;
            case '<':
               r.unconsume();
               t.error(this);
            case '>':
               t.emitTagPending();
               t.transition(Data);
               break;
            case '\uffff':
               t.eofError(this);
               t.transition(Data);
               break;
            default:
               t.tagPending.newAttribute();
               r.unconsume();
               t.transition(AttributeName);
         }
      }
   },
   AttributeName {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         String name = r.consumeToAnySorted(attributeNameCharsSorted);
         t.tagPending.appendAttributeName(name);
         char c = r.consume();
         switch (c) {
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ':
               t.transition(AfterAttributeName);
               break;
            case '"':
            case '\'':
            case '<':
               t.error(this);
               t.tagPending.appendAttributeName(c);
               break;
            case '/':
               t.transition(SelfClosingStartTag);
               break;
            case '=':
               t.transition(BeforeAttributeValue);
               break;
            case '>':
               t.emitTagPending();
               t.transition(Data);
               break;
            case '\uffff':
               t.eofError(this);
               t.transition(Data);
               break;
            default:
               t.tagPending.appendAttributeName(c);
         }
      }
   },
   AfterAttributeName {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         char c = r.consume();
         switch (c) {
            case '\u0000':
               t.error(this);
               t.tagPending.appendAttributeName('�');
               t.transition(AttributeName);
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ':
               break;
            case '"':
            case '\'':
            case '<':
               t.error(this);
               t.tagPending.newAttribute();
               t.tagPending.appendAttributeName(c);
               t.transition(AttributeName);
               break;
            case '/':
               t.transition(SelfClosingStartTag);
               break;
            case '=':
               t.transition(BeforeAttributeValue);
               break;
            case '>':
               t.emitTagPending();
               t.transition(Data);
               break;
            case '\uffff':
               t.eofError(this);
               t.transition(Data);
               break;
            default:
               t.tagPending.newAttribute();
               r.unconsume();
               t.transition(AttributeName);
         }
      }
   },
   BeforeAttributeValue {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         char c = r.consume();
         switch (c) {
            case '\u0000':
               t.error(this);
               t.tagPending.appendAttributeValue('�');
               t.transition(AttributeValue_unquoted);
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ':
               break;
            case '"':
               t.transition(AttributeValue_doubleQuoted);
               break;
            case '&':
               r.unconsume();
               t.transition(AttributeValue_unquoted);
               break;
            case '\'':
               t.transition(AttributeValue_singleQuoted);
               break;
            case '<':
            case '=':
            case '`':
               t.error(this);
               t.tagPending.appendAttributeValue(c);
               t.transition(AttributeValue_unquoted);
               break;
            case '>':
               t.error(this);
               t.emitTagPending();
               t.transition(Data);
               break;
            case '\uffff':
               t.eofError(this);
               t.emitTagPending();
               t.transition(Data);
               break;
            default:
               r.unconsume();
               t.transition(AttributeValue_unquoted);
         }
      }
   },
   AttributeValue_doubleQuoted {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         String value = r.consumeAttributeQuoted(false);
         if (value.length() > 0) {
            t.tagPending.appendAttributeValue(value);
         } else {
            t.tagPending.setEmptyAttributeValue();
         }

         char c = r.consume();
         switch (c) {
            case '\u0000':
               t.error(this);
               t.tagPending.appendAttributeValue('�');
               break;
            case '"':
               t.transition(AfterAttributeValue_quoted);
               break;
            case '&':
               int[] ref = t.consumeCharacterReference('"', true);
               if (ref != null) {
                  t.tagPending.appendAttributeValue(ref);
               } else {
                  t.tagPending.appendAttributeValue('&');
               }
               break;
            case '\uffff':
               t.eofError(this);
               t.transition(Data);
               break;
            default:
               t.tagPending.appendAttributeValue(c);
         }
      }
   },
   AttributeValue_singleQuoted {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         String value = r.consumeAttributeQuoted(true);
         if (value.length() > 0) {
            t.tagPending.appendAttributeValue(value);
         } else {
            t.tagPending.setEmptyAttributeValue();
         }

         char c = r.consume();
         switch (c) {
            case '\u0000':
               t.error(this);
               t.tagPending.appendAttributeValue('�');
               break;
            case '&':
               int[] ref = t.consumeCharacterReference('\'', true);
               if (ref != null) {
                  t.tagPending.appendAttributeValue(ref);
               } else {
                  t.tagPending.appendAttributeValue('&');
               }
               break;
            case '\'':
               t.transition(AfterAttributeValue_quoted);
               break;
            case '\uffff':
               t.eofError(this);
               t.transition(Data);
               break;
            default:
               t.tagPending.appendAttributeValue(c);
         }
      }
   },
   AttributeValue_unquoted {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         String value = r.consumeToAnySorted(attributeValueUnquoted);
         if (value.length() > 0) {
            t.tagPending.appendAttributeValue(value);
         }

         char c = r.consume();
         switch (c) {
            case '\u0000':
               t.error(this);
               t.tagPending.appendAttributeValue('�');
               break;
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ':
               t.transition(BeforeAttributeName);
               break;
            case '"':
            case '\'':
            case '<':
            case '=':
            case '`':
               t.error(this);
               t.tagPending.appendAttributeValue(c);
               break;
            case '&':
               int[] ref = t.consumeCharacterReference('>', true);
               if (ref != null) {
                  t.tagPending.appendAttributeValue(ref);
               } else {
                  t.tagPending.appendAttributeValue('&');
               }
               break;
            case '>':
               t.emitTagPending();
               t.transition(Data);
               break;
            case '\uffff':
               t.eofError(this);
               t.transition(Data);
               break;
            default:
               t.tagPending.appendAttributeValue(c);
         }
      }
   },
   AfterAttributeValue_quoted {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         char c = r.consume();
         switch (c) {
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ':
               t.transition(BeforeAttributeName);
               break;
            case '/':
               t.transition(SelfClosingStartTag);
               break;
            case '>':
               t.emitTagPending();
               t.transition(Data);
               break;
            case '\uffff':
               t.eofError(this);
               t.transition(Data);
               break;
            default:
               r.unconsume();
               t.error(this);
               t.transition(BeforeAttributeName);
         }
      }
   },
   SelfClosingStartTag {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         char c = r.consume();
         switch (c) {
            case '>':
               t.tagPending.selfClosing = true;
               t.emitTagPending();
               t.transition(Data);
               break;
            case '\uffff':
               t.eofError(this);
               t.transition(Data);
               break;
            default:
               r.unconsume();
               t.error(this);
               t.transition(BeforeAttributeName);
         }
      }
   },
   BogusComment {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         t.commentPending.append(r.consumeTo('>'));
         char next = r.current();
         if (next == '>' || next == '\uffff') {
            r.consume();
            t.emitCommentPending();
            t.transition(Data);
         }
      }
   },
   MarkupDeclarationOpen {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         if (r.matchConsume("--")) {
            t.createCommentPending();
            t.transition(CommentStart);
         } else if (r.matchConsumeIgnoreCase("DOCTYPE")) {
            t.transition(Doctype);
         } else if (r.matchConsume("[CDATA[")) {
            t.createTempBuffer();
            t.transition(CdataSection);
         } else {
            t.error(this);
            t.createBogusCommentPending();
            t.transition(BogusComment);
         }
      }
   },
   CommentStart {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         char c = r.consume();
         switch (c) {
            case '\u0000':
               t.error(this);
               t.commentPending.append('�');
               t.transition(Comment);
               break;
            case '-':
               t.transition(CommentStartDash);
               break;
            case '>':
               t.error(this);
               t.emitCommentPending();
               t.transition(Data);
               break;
            case '\uffff':
               t.eofError(this);
               t.emitCommentPending();
               t.transition(Data);
               break;
            default:
               r.unconsume();
               t.transition(Comment);
         }
      }
   },
   CommentStartDash {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         char c = r.consume();
         switch (c) {
            case '\u0000':
               t.error(this);
               t.commentPending.append('�');
               t.transition(Comment);
               break;
            case '-':
               t.transition(CommentEnd);
               break;
            case '>':
               t.error(this);
               t.emitCommentPending();
               t.transition(Data);
               break;
            case '\uffff':
               t.eofError(this);
               t.emitCommentPending();
               t.transition(Data);
               break;
            default:
               t.commentPending.append(c);
               t.transition(Comment);
         }
      }
   },
   Comment {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         char c = r.current();
         switch (c) {
            case '\u0000':
               t.error(this);
               r.advance();
               t.commentPending.append('�');
               break;
            case '-':
               t.advanceTransition(CommentEndDash);
               break;
            case '\uffff':
               t.eofError(this);
               t.emitCommentPending();
               t.transition(Data);
               break;
            default:
               t.commentPending.append(r.consumeToAny('-', '\u0000'));
         }
      }
   },
   CommentEndDash {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         char c = r.consume();
         switch (c) {
            case '\u0000':
               t.error(this);
               t.commentPending.append('-').append('�');
               t.transition(Comment);
               break;
            case '-':
               t.transition(CommentEnd);
               break;
            case '\uffff':
               t.eofError(this);
               t.emitCommentPending();
               t.transition(Data);
               break;
            default:
               t.commentPending.append('-').append(c);
               t.transition(Comment);
         }
      }
   },
   CommentEnd {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         char c = r.consume();
         switch (c) {
            case '\u0000':
               t.error(this);
               t.commentPending.append("--").append('�');
               t.transition(Comment);
               break;
            case '!':
               t.transition(CommentEndBang);
               break;
            case '-':
               t.commentPending.append('-');
               break;
            case '>':
               t.emitCommentPending();
               t.transition(Data);
               break;
            case '\uffff':
               t.eofError(this);
               t.emitCommentPending();
               t.transition(Data);
               break;
            default:
               t.commentPending.append("--").append(c);
               t.transition(Comment);
         }
      }
   },
   CommentEndBang {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         char c = r.consume();
         switch (c) {
            case '\u0000':
               t.error(this);
               t.commentPending.append("--!").append('�');
               t.transition(Comment);
               break;
            case '-':
               t.commentPending.append("--!");
               t.transition(CommentEndDash);
               break;
            case '>':
               t.emitCommentPending();
               t.transition(Data);
               break;
            case '\uffff':
               t.eofError(this);
               t.emitCommentPending();
               t.transition(Data);
               break;
            default:
               t.commentPending.append("--!").append(c);
               t.transition(Comment);
         }
      }
   },
   Doctype {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         char c = r.consume();
         switch (c) {
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ':
               t.transition(BeforeDoctypeName);
               break;
            case '\uffff':
               t.eofError(this);
            case '>':
               t.error(this);
               t.createDoctypePending();
               t.doctypePending.forceQuirks = true;
               t.emitDoctypePending();
               t.transition(Data);
               break;
            default:
               t.error(this);
               t.transition(BeforeDoctypeName);
         }
      }
   },
   BeforeDoctypeName {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         if (r.matchesAsciiAlpha()) {
            t.createDoctypePending();
            t.transition(DoctypeName);
         } else {
            char c = r.consume();
            switch (c) {
               case '\u0000':
                  t.error(this);
                  t.createDoctypePending();
                  t.doctypePending.name.append('�');
                  t.transition(DoctypeName);
               case '\t':
               case '\n':
               case '\f':
               case '\r':
               case ' ':
                  break;
               case '\uffff':
                  t.eofError(this);
                  t.createDoctypePending();
                  t.doctypePending.forceQuirks = true;
                  t.emitDoctypePending();
                  t.transition(Data);
                  break;
               default:
                  t.createDoctypePending();
                  t.doctypePending.name.append(c);
                  t.transition(DoctypeName);
            }
         }
      }
   },
   DoctypeName {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         if (r.matchesLetter()) {
            String name = r.consumeLetterSequence();
            t.doctypePending.name.append(name);
         } else {
            char c = r.consume();
            switch (c) {
               case '\u0000':
                  t.error(this);
                  t.doctypePending.name.append('�');
                  break;
               case '\t':
               case '\n':
               case '\f':
               case '\r':
               case ' ':
                  t.transition(AfterDoctypeName);
                  break;
               case '>':
                  t.emitDoctypePending();
                  t.transition(Data);
                  break;
               case '\uffff':
                  t.eofError(this);
                  t.doctypePending.forceQuirks = true;
                  t.emitDoctypePending();
                  t.transition(Data);
                  break;
               default:
                  t.doctypePending.name.append(c);
            }
         }
      }
   },
   AfterDoctypeName {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         if (r.isEmpty()) {
            t.eofError(this);
            t.doctypePending.forceQuirks = true;
            t.emitDoctypePending();
            t.transition(Data);
         } else {
            if (r.matchesAny('\t', '\n', '\r', '\f', ' ')) {
               r.advance();
            } else if (r.matches('>')) {
               t.emitDoctypePending();
               t.advanceTransition(Data);
            } else if (r.matchConsumeIgnoreCase("PUBLIC")) {
               t.doctypePending.pubSysKey = "PUBLIC";
               t.transition(AfterDoctypePublicKeyword);
            } else if (r.matchConsumeIgnoreCase("SYSTEM")) {
               t.doctypePending.pubSysKey = "SYSTEM";
               t.transition(AfterDoctypeSystemKeyword);
            } else {
               t.error(this);
               t.doctypePending.forceQuirks = true;
               t.advanceTransition(BogusDoctype);
            }
         }
      }
   },
   AfterDoctypePublicKeyword {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         char c = r.consume();
         switch (c) {
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ':
               t.transition(BeforeDoctypePublicIdentifier);
               break;
            case '"':
               t.error(this);
               t.transition(DoctypePublicIdentifier_doubleQuoted);
               break;
            case '\'':
               t.error(this);
               t.transition(DoctypePublicIdentifier_singleQuoted);
               break;
            case '>':
               t.error(this);
               t.doctypePending.forceQuirks = true;
               t.emitDoctypePending();
               t.transition(Data);
               break;
            case '\uffff':
               t.eofError(this);
               t.doctypePending.forceQuirks = true;
               t.emitDoctypePending();
               t.transition(Data);
               break;
            default:
               t.error(this);
               t.doctypePending.forceQuirks = true;
               t.transition(BogusDoctype);
         }
      }
   },
   BeforeDoctypePublicIdentifier {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         char c = r.consume();
         switch (c) {
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ':
               break;
            case '"':
               t.transition(DoctypePublicIdentifier_doubleQuoted);
               break;
            case '\'':
               t.transition(DoctypePublicIdentifier_singleQuoted);
               break;
            case '>':
               t.error(this);
               t.doctypePending.forceQuirks = true;
               t.emitDoctypePending();
               t.transition(Data);
               break;
            case '\uffff':
               t.eofError(this);
               t.doctypePending.forceQuirks = true;
               t.emitDoctypePending();
               t.transition(Data);
               break;
            default:
               t.error(this);
               t.doctypePending.forceQuirks = true;
               t.transition(BogusDoctype);
         }
      }
   },
   DoctypePublicIdentifier_doubleQuoted {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         char c = r.consume();
         switch (c) {
            case '\u0000':
               t.error(this);
               t.doctypePending.publicIdentifier.append('�');
               break;
            case '"':
               t.transition(AfterDoctypePublicIdentifier);
               break;
            case '>':
               t.error(this);
               t.doctypePending.forceQuirks = true;
               t.emitDoctypePending();
               t.transition(Data);
               break;
            case '\uffff':
               t.eofError(this);
               t.doctypePending.forceQuirks = true;
               t.emitDoctypePending();
               t.transition(Data);
               break;
            default:
               t.doctypePending.publicIdentifier.append(c);
         }
      }
   },
   DoctypePublicIdentifier_singleQuoted {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         char c = r.consume();
         switch (c) {
            case '\u0000':
               t.error(this);
               t.doctypePending.publicIdentifier.append('�');
               break;
            case '\'':
               t.transition(AfterDoctypePublicIdentifier);
               break;
            case '>':
               t.error(this);
               t.doctypePending.forceQuirks = true;
               t.emitDoctypePending();
               t.transition(Data);
               break;
            case '\uffff':
               t.eofError(this);
               t.doctypePending.forceQuirks = true;
               t.emitDoctypePending();
               t.transition(Data);
               break;
            default:
               t.doctypePending.publicIdentifier.append(c);
         }
      }
   },
   AfterDoctypePublicIdentifier {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         char c = r.consume();
         switch (c) {
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ':
               t.transition(BetweenDoctypePublicAndSystemIdentifiers);
               break;
            case '"':
               t.error(this);
               t.transition(DoctypeSystemIdentifier_doubleQuoted);
               break;
            case '\'':
               t.error(this);
               t.transition(DoctypeSystemIdentifier_singleQuoted);
               break;
            case '>':
               t.emitDoctypePending();
               t.transition(Data);
               break;
            case '\uffff':
               t.eofError(this);
               t.doctypePending.forceQuirks = true;
               t.emitDoctypePending();
               t.transition(Data);
               break;
            default:
               t.error(this);
               t.doctypePending.forceQuirks = true;
               t.transition(BogusDoctype);
         }
      }
   },
   BetweenDoctypePublicAndSystemIdentifiers {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         char c = r.consume();
         switch (c) {
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ':
               break;
            case '"':
               t.error(this);
               t.transition(DoctypeSystemIdentifier_doubleQuoted);
               break;
            case '\'':
               t.error(this);
               t.transition(DoctypeSystemIdentifier_singleQuoted);
               break;
            case '>':
               t.emitDoctypePending();
               t.transition(Data);
               break;
            case '\uffff':
               t.eofError(this);
               t.doctypePending.forceQuirks = true;
               t.emitDoctypePending();
               t.transition(Data);
               break;
            default:
               t.error(this);
               t.doctypePending.forceQuirks = true;
               t.transition(BogusDoctype);
         }
      }
   },
   AfterDoctypeSystemKeyword {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         char c = r.consume();
         switch (c) {
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ':
               t.transition(BeforeDoctypeSystemIdentifier);
               break;
            case '"':
               t.error(this);
               t.transition(DoctypeSystemIdentifier_doubleQuoted);
               break;
            case '\'':
               t.error(this);
               t.transition(DoctypeSystemIdentifier_singleQuoted);
               break;
            case '>':
               t.error(this);
               t.doctypePending.forceQuirks = true;
               t.emitDoctypePending();
               t.transition(Data);
               break;
            case '\uffff':
               t.eofError(this);
               t.doctypePending.forceQuirks = true;
               t.emitDoctypePending();
               t.transition(Data);
               break;
            default:
               t.error(this);
               t.doctypePending.forceQuirks = true;
               t.emitDoctypePending();
         }
      }
   },
   BeforeDoctypeSystemIdentifier {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         char c = r.consume();
         switch (c) {
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ':
               break;
            case '"':
               t.transition(DoctypeSystemIdentifier_doubleQuoted);
               break;
            case '\'':
               t.transition(DoctypeSystemIdentifier_singleQuoted);
               break;
            case '>':
               t.error(this);
               t.doctypePending.forceQuirks = true;
               t.emitDoctypePending();
               t.transition(Data);
               break;
            case '\uffff':
               t.eofError(this);
               t.doctypePending.forceQuirks = true;
               t.emitDoctypePending();
               t.transition(Data);
               break;
            default:
               t.error(this);
               t.doctypePending.forceQuirks = true;
               t.transition(BogusDoctype);
         }
      }
   },
   DoctypeSystemIdentifier_doubleQuoted {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         char c = r.consume();
         switch (c) {
            case '\u0000':
               t.error(this);
               t.doctypePending.systemIdentifier.append('�');
               break;
            case '"':
               t.transition(AfterDoctypeSystemIdentifier);
               break;
            case '>':
               t.error(this);
               t.doctypePending.forceQuirks = true;
               t.emitDoctypePending();
               t.transition(Data);
               break;
            case '\uffff':
               t.eofError(this);
               t.doctypePending.forceQuirks = true;
               t.emitDoctypePending();
               t.transition(Data);
               break;
            default:
               t.doctypePending.systemIdentifier.append(c);
         }
      }
   },
   DoctypeSystemIdentifier_singleQuoted {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         char c = r.consume();
         switch (c) {
            case '\u0000':
               t.error(this);
               t.doctypePending.systemIdentifier.append('�');
               break;
            case '\'':
               t.transition(AfterDoctypeSystemIdentifier);
               break;
            case '>':
               t.error(this);
               t.doctypePending.forceQuirks = true;
               t.emitDoctypePending();
               t.transition(Data);
               break;
            case '\uffff':
               t.eofError(this);
               t.doctypePending.forceQuirks = true;
               t.emitDoctypePending();
               t.transition(Data);
               break;
            default:
               t.doctypePending.systemIdentifier.append(c);
         }
      }
   },
   AfterDoctypeSystemIdentifier {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         char c = r.consume();
         switch (c) {
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ':
               break;
            case '>':
               t.emitDoctypePending();
               t.transition(Data);
               break;
            case '\uffff':
               t.eofError(this);
               t.doctypePending.forceQuirks = true;
               t.emitDoctypePending();
               t.transition(Data);
               break;
            default:
               t.error(this);
               t.transition(BogusDoctype);
         }
      }
   },
   BogusDoctype {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         char c = r.consume();
         switch (c) {
            case '>':
               t.emitDoctypePending();
               t.transition(Data);
               break;
            case '\uffff':
               t.emitDoctypePending();
               t.transition(Data);
         }
      }
   },
   CdataSection {
      @Override
      void read(Tokeniser t, CharacterReader r) {
         String data = r.consumeTo("]]>");
         t.dataBuffer.append(data);
         if (r.matchConsume("]]>") || r.isEmpty()) {
            t.emit(new Token.CData(t.dataBuffer.toString()));
            t.transition(Data);
         }
      }
   };

   static final char nullChar = '\u0000';
   static final char[] attributeNameCharsSorted = new char[]{'\t', '\n', '\f', '\r', ' ', '"', '\'', '/', '<', '=', '>'};
   static final char[] attributeValueUnquoted = new char[]{'\u0000', '\t', '\n', '\f', '\r', ' ', '"', '&', '\'', '<', '=', '>', '`'};
   private static final char replacementChar = '�';
   private static final String replacementStr = String.valueOf('�');
   private static final char eof = '\uffff';

   private TokeniserState() {
   }

   abstract void read(Tokeniser var1, CharacterReader var2);

   private static void handleDataEndTag(Tokeniser t, CharacterReader r, TokeniserState elseTransition) {
      if (r.matchesLetter()) {
         String name = r.consumeLetterSequence();
         t.tagPending.appendTagName(name);
         t.dataBuffer.append(name);
      } else {
         boolean needsExitTransition = false;
         if (t.isAppropriateEndTagToken() && !r.isEmpty()) {
            char c = r.consume();
            switch (c) {
               case '\t':
               case '\n':
               case '\f':
               case '\r':
               case ' ':
                  t.transition(BeforeAttributeName);
                  break;
               case '/':
                  t.transition(SelfClosingStartTag);
                  break;
               case '>':
                  t.emitTagPending();
                  t.transition(Data);
                  break;
               default:
                  t.dataBuffer.append(c);
                  needsExitTransition = true;
            }
         } else {
            needsExitTransition = true;
         }

         if (needsExitTransition) {
            t.emit("</");
            t.emit(t.dataBuffer);
            t.transition(elseTransition);
         }
      }
   }

   private static void readRawData(Tokeniser t, CharacterReader r, TokeniserState current, TokeniserState advance) {
      switch (r.current()) {
         case '\u0000':
            t.error(current);
            r.advance();
            t.emit('�');
            break;
         case '<':
            t.advanceTransition(advance);
            break;
         case '\uffff':
            t.emit(new Token.EOF());
            break;
         default:
            String data = r.consumeRawData();
            t.emit(data);
      }
   }

   private static void readCharRef(Tokeniser t, TokeniserState advance) {
      int[] c = t.consumeCharacterReference(null, false);
      if (c == null) {
         t.emit('&');
      } else {
         t.emit(c);
      }

      t.transition(advance);
   }

   private static void readEndTag(Tokeniser t, CharacterReader r, TokeniserState a, TokeniserState b) {
      if (r.matchesAsciiAlpha()) {
         t.createTagPending(false);
         t.transition(a);
      } else {
         t.emit("</");
         t.transition(b);
      }
   }

   private static void handleDataDoubleEscapeTag(Tokeniser t, CharacterReader r, TokeniserState primary, TokeniserState fallback) {
      if (r.matchesLetter()) {
         String name = r.consumeLetterSequence();
         t.dataBuffer.append(name);
         t.emit(name);
      } else {
         char c = r.consume();
         switch (c) {
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ':
            case '/':
            case '>':
               if (t.dataBuffer.toString().equals("script")) {
                  t.transition(primary);
               } else {
                  t.transition(fallback);
               }

               t.emit(c);
               break;
            default:
               r.unconsume();
               t.transition(fallback);
         }
      }
   }
}
