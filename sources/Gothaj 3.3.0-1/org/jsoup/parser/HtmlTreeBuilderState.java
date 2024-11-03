package org.jsoup.parser;

import java.util.ArrayList;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;

enum HtmlTreeBuilderState {
   Initial {
      @Override
      boolean process(Token t, HtmlTreeBuilder tb) {
         if (HtmlTreeBuilderState.isWhitespace(t)) {
            return true;
         } else {
            if (t.isComment()) {
               tb.insert(t.asComment());
            } else {
               if (!t.isDoctype()) {
                  tb.transition(BeforeHtml);
                  return tb.process(t);
               }

               Token.Doctype d = t.asDoctype();
               DocumentType doctype = new DocumentType(tb.settings.normalizeTag(d.getName()), d.getPublicIdentifier(), d.getSystemIdentifier());
               doctype.setPubSysKey(d.getPubSysKey());
               tb.getDocument().appendChild(doctype);
               tb.onNodeInserted(doctype, t);
               if (d.isForceQuirks()) {
                  tb.getDocument().quirksMode(Document.QuirksMode.quirks);
               }

               tb.transition(BeforeHtml);
            }

            return true;
         }
      }
   },
   BeforeHtml {
      @Override
      boolean process(Token t, HtmlTreeBuilder tb) {
         if (t.isDoctype()) {
            tb.error(this);
            return false;
         } else {
            if (t.isComment()) {
               tb.insert(t.asComment());
            } else if (HtmlTreeBuilderState.isWhitespace(t)) {
               tb.insert(t.asCharacter());
            } else {
               if (!t.isStartTag() || !t.asStartTag().normalName().equals("html")) {
                  if (t.isEndTag() && StringUtil.inSorted(t.asEndTag().normalName(), HtmlTreeBuilderState.Constants.BeforeHtmlToHead)) {
                     return this.anythingElse(t, tb);
                  }

                  if (t.isEndTag()) {
                     tb.error(this);
                     return false;
                  }

                  return this.anythingElse(t, tb);
               }

               tb.insert(t.asStartTag());
               tb.transition(BeforeHead);
            }

            return true;
         }
      }

      private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
         tb.insertStartTag("html");
         tb.transition(BeforeHead);
         return tb.process(t);
      }
   },
   BeforeHead {
      @Override
      boolean process(Token t, HtmlTreeBuilder tb) {
         if (HtmlTreeBuilderState.isWhitespace(t)) {
            tb.insert(t.asCharacter());
         } else if (t.isComment()) {
            tb.insert(t.asComment());
         } else {
            if (t.isDoctype()) {
               tb.error(this);
               return false;
            }

            if (t.isStartTag() && t.asStartTag().normalName().equals("html")) {
               return InBody.process(t, tb);
            }

            if (!t.isStartTag() || !t.asStartTag().normalName().equals("head")) {
               if (t.isEndTag() && StringUtil.inSorted(t.asEndTag().normalName(), HtmlTreeBuilderState.Constants.BeforeHtmlToHead)) {
                  tb.processStartTag("head");
                  return tb.process(t);
               }

               if (t.isEndTag()) {
                  tb.error(this);
                  return false;
               }

               tb.processStartTag("head");
               return tb.process(t);
            }

            Element head = tb.insert(t.asStartTag());
            tb.setHeadElement(head);
            tb.transition(InHead);
         }

         return true;
      }
   },
   InHead {
      @Override
      boolean process(Token t, HtmlTreeBuilder tb) {
         if (HtmlTreeBuilderState.isWhitespace(t)) {
            tb.insert(t.asCharacter());
            return true;
         } else {
            switch (t.type) {
               case Comment:
                  tb.insert(t.asComment());
                  break;
               case Doctype:
                  tb.error(this);
                  return false;
               case StartTag:
                  Token.StartTag start = t.asStartTag();
                  String namex = start.normalName();
                  if (namex.equals("html")) {
                     return InBody.process(t, tb);
                  }

                  if (StringUtil.inSorted(namex, HtmlTreeBuilderState.Constants.InHeadEmpty)) {
                     Element el = tb.insertEmpty(start);
                     if (namex.equals("base") && el.hasAttr("href")) {
                        tb.maybeSetBaseUri(el);
                     }
                  } else if (namex.equals("meta")) {
                     tb.insertEmpty(start);
                  } else if (namex.equals("title")) {
                     HtmlTreeBuilderState.handleRcData(start, tb);
                  } else if (StringUtil.inSorted(namex, HtmlTreeBuilderState.Constants.InHeadRaw)) {
                     HtmlTreeBuilderState.handleRawtext(start, tb);
                  } else if (namex.equals("noscript")) {
                     tb.insert(start);
                     tb.transition(InHeadNoscript);
                  } else if (namex.equals("script")) {
                     tb.tokeniser.transition(TokeniserState.ScriptData);
                     tb.markInsertionMode();
                     tb.transition(Text);
                     tb.insert(start);
                  } else {
                     if (namex.equals("head")) {
                        tb.error(this);
                        return false;
                     }

                     if (!namex.equals("template")) {
                        return this.anythingElse(t, tb);
                     }

                     tb.insert(start);
                     tb.insertMarkerToFormattingElements();
                     tb.framesetOk(false);
                     tb.transition(InTemplate);
                     tb.pushTemplateMode(InTemplate);
                  }
                  break;
               case EndTag:
                  Token.EndTag end = t.asEndTag();
                  String name = end.normalName();
                  if (name.equals("head")) {
                     tb.pop();
                     tb.transition(AfterHead);
                  } else {
                     if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InHeadEnd)) {
                        return this.anythingElse(t, tb);
                     }

                     if (!name.equals("template")) {
                        tb.error(this);
                        return false;
                     }

                     if (!tb.onStack(name)) {
                        tb.error(this);
                     } else {
                        tb.generateImpliedEndTags(true);
                        if (!name.equals(tb.currentElement().normalName())) {
                           tb.error(this);
                        }

                        tb.popStackToClose(name);
                        tb.clearFormattingElementsToLastMarker();
                        tb.popTemplateMode();
                        tb.resetInsertionMode();
                     }
                  }
                  break;
               default:
                  return this.anythingElse(t, tb);
            }

            return true;
         }
      }

      private boolean anythingElse(Token t, TreeBuilder tb) {
         tb.processEndTag("head");
         return tb.process(t);
      }
   },
   InHeadNoscript {
      @Override
      boolean process(Token t, HtmlTreeBuilder tb) {
         if (t.isDoctype()) {
            tb.error(this);
         } else {
            if (t.isStartTag() && t.asStartTag().normalName().equals("html")) {
               return tb.process(t, InBody);
            }

            if (!t.isEndTag() || !t.asEndTag().normalName().equals("noscript")) {
               if (!HtmlTreeBuilderState.isWhitespace(t)
                  && !t.isComment()
                  && (!t.isStartTag() || !StringUtil.inSorted(t.asStartTag().normalName(), HtmlTreeBuilderState.Constants.InHeadNoScriptHead))) {
                  if (t.isEndTag() && t.asEndTag().normalName().equals("br")) {
                     return this.anythingElse(t, tb);
                  }

                  if ((!t.isStartTag() || !StringUtil.inSorted(t.asStartTag().normalName(), HtmlTreeBuilderState.Constants.InHeadNoscriptIgnore))
                     && !t.isEndTag()) {
                     return this.anythingElse(t, tb);
                  }

                  tb.error(this);
                  return false;
               }

               return tb.process(t, InHead);
            }

            tb.pop();
            tb.transition(InHead);
         }

         return true;
      }

      private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
         tb.error(this);
         tb.insert(new Token.Character().data(t.toString()));
         return true;
      }
   },
   AfterHead {
      @Override
      boolean process(Token t, HtmlTreeBuilder tb) {
         if (HtmlTreeBuilderState.isWhitespace(t)) {
            tb.insert(t.asCharacter());
         } else if (t.isComment()) {
            tb.insert(t.asComment());
         } else if (t.isDoctype()) {
            tb.error(this);
         } else if (t.isStartTag()) {
            Token.StartTag startTag = t.asStartTag();
            String name = startTag.normalName();
            if (name.equals("html")) {
               return tb.process(t, InBody);
            }

            if (name.equals("body")) {
               tb.insert(startTag);
               tb.framesetOk(false);
               tb.transition(InBody);
            } else if (name.equals("frameset")) {
               tb.insert(startTag);
               tb.transition(InFrameset);
            } else if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InBodyStartToHead)) {
               tb.error(this);
               Element head = tb.getHeadElement();
               tb.push(head);
               tb.process(t, InHead);
               tb.removeFromStack(head);
            } else {
               if (name.equals("head")) {
                  tb.error(this);
                  return false;
               }

               this.anythingElse(t, tb);
            }
         } else if (t.isEndTag()) {
            String namex = t.asEndTag().normalName();
            if (StringUtil.inSorted(namex, HtmlTreeBuilderState.Constants.AfterHeadBody)) {
               this.anythingElse(t, tb);
            } else {
               if (!namex.equals("template")) {
                  tb.error(this);
                  return false;
               }

               tb.process(t, InHead);
            }
         } else {
            this.anythingElse(t, tb);
         }

         return true;
      }

      private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
         tb.processStartTag("body");
         tb.framesetOk(true);
         return tb.process(t);
      }
   },
   InBody {
      private static final int MaxStackScan = 24;

      @Override
      boolean process(Token t, HtmlTreeBuilder tb) {
         switch (t.type) {
            case Comment:
               tb.insert(t.asComment());
               break;
            case Doctype:
               tb.error(this);
               return false;
            case StartTag:
               return this.inBodyStartTag(t, tb);
            case EndTag:
               return this.inBodyEndTag(t, tb);
            case Character:
               Token.Character c = t.asCharacter();
               if (c.getData().equals(HtmlTreeBuilderState.nullString)) {
                  tb.error(this);
                  return false;
               }

               if (tb.framesetOk() && HtmlTreeBuilderState.isWhitespace(c)) {
                  tb.reconstructFormattingElements();
                  tb.insert(c);
               } else {
                  tb.reconstructFormattingElements();
                  tb.insert(c);
                  tb.framesetOk(false);
               }
               break;
            case EOF:
               if (tb.templateModeSize() > 0) {
                  return tb.process(t, InTemplate);
               }
         }

         return true;
      }

      private boolean inBodyStartTag(Token t, HtmlTreeBuilder tb) {
         Token.StartTag startTag = t.asStartTag();
         String name = startTag.normalName();
         switch (name) {
            case "a": {
               if (tb.getActiveFormattingElement("a") != null) {
                  tb.error(this);
                  tb.processEndTag("a");
                  Element remainingA = tb.getFromStack("a");
                  if (remainingA != null) {
                     tb.removeFromActiveFormattingElements(remainingA);
                     tb.removeFromStack(remainingA);
                  }
               }

               tb.reconstructFormattingElements();
               Element el = tb.insert(startTag);
               tb.pushActiveFormattingElements(el);
               break;
            }
            case "span":
               tb.reconstructFormattingElements();
               tb.insert(startTag);
               break;
            case "li":
               tb.framesetOk(false);
               ArrayList<Element> stack = tb.getStack();

               for (int i = stack.size() - 1; i > 0; i--) {
                  Element elx = stack.get(i);
                  if (elx.normalName().equals("li")) {
                     tb.processEndTag("li");
                     break;
                  }

                  if (tb.isSpecial(elx) && !StringUtil.inSorted(elx.normalName(), HtmlTreeBuilderState.Constants.InBodyStartLiBreakers)) {
                     break;
                  }
               }

               if (tb.inButtonScope("p")) {
                  tb.processEndTag("p");
               }

               tb.insert(startTag);
               break;
            case "html":
               tb.error(this);
               if (tb.onStack("template")) {
                  return false;
               }

               ArrayList<Element> stack = tb.getStack();
               if (stack.size() > 0) {
                  Element html = tb.getStack().get(0);
                  if (startTag.hasAttributes()) {
                     for (Attribute attribute : startTag.attributes) {
                        if (!html.hasAttr(attribute.getKey())) {
                           html.attributes().put(attribute);
                        }
                     }
                  }
               }
               break;
            case "body":
               tb.error(this);
               ArrayList<Element> stack = tb.getStack();
               if (stack.size() == 1 || stack.size() > 2 && !stack.get(1).normalName().equals("body") || tb.onStack("template")) {
                  return false;
               }

               tb.framesetOk(false);
               Element body;
               if (startTag.hasAttributes() && (body = tb.getFromStack("body")) != null) {
                  for (Attribute attributex : startTag.attributes) {
                     if (!body.hasAttr(attributex.getKey())) {
                        body.attributes().put(attributex);
                     }
                  }
               }
               break;
            case "frameset":
               tb.error(this);
               ArrayList<Element> stackx = tb.getStack();
               if (stackx.size() == 1 || stackx.size() > 2 && !stackx.get(1).normalName().equals("body")) {
                  return false;
               }

               if (!tb.framesetOk()) {
                  return false;
               }

               Element second = stackx.get(1);
               if (second.parent() != null) {
                  second.remove();
               }

               while (stackx.size() > 1) {
                  stackx.remove(stackx.size() - 1);
               }

               tb.insert(startTag);
               tb.transition(InFrameset);
               break;
            case "form":
               if (tb.getFormElement() != null && !tb.onStack("template")) {
                  tb.error(this);
                  return false;
               }

               if (tb.inButtonScope("p")) {
                  tb.closeElement("p");
               }

               tb.insertForm(startTag, true, true);
               break;
            case "plaintext":
               if (tb.inButtonScope("p")) {
                  tb.processEndTag("p");
               }

               tb.insert(startTag);
               tb.tokeniser.transition(TokeniserState.PLAINTEXT);
               break;
            case "button":
               if (tb.inButtonScope("button")) {
                  tb.error(this);
                  tb.processEndTag("button");
                  tb.process(startTag);
               } else {
                  tb.reconstructFormattingElements();
                  tb.insert(startTag);
                  tb.framesetOk(false);
               }
               break;
            case "nobr": {
               tb.reconstructFormattingElements();
               if (tb.inScope("nobr")) {
                  tb.error(this);
                  tb.processEndTag("nobr");
                  tb.reconstructFormattingElements();
               }

               Element el = tb.insert(startTag);
               tb.pushActiveFormattingElements(el);
               break;
            }
            case "table":
               if (tb.getDocument().quirksMode() != Document.QuirksMode.quirks && tb.inButtonScope("p")) {
                  tb.processEndTag("p");
               }

               tb.insert(startTag);
               tb.framesetOk(false);
               tb.transition(InTable);
               break;
            case "input": {
               tb.reconstructFormattingElements();
               Element el = tb.insertEmpty(startTag);
               if (!el.attr("type").equalsIgnoreCase("hidden")) {
                  tb.framesetOk(false);
               }
               break;
            }
            case "hr":
               if (tb.inButtonScope("p")) {
                  tb.processEndTag("p");
               }

               tb.insertEmpty(startTag);
               tb.framesetOk(false);
               break;
            case "image":
               if (tb.getFromStack("svg") == null) {
                  return tb.process(startTag.name("img"));
               }

               tb.insert(startTag);
               break;
            case "isindex":
               tb.error(this);
               if (tb.getFormElement() != null) {
                  return false;
               }

               tb.processStartTag("form");
               if (startTag.hasAttribute("action")) {
                  Element form = tb.getFormElement();
                  if (form != null && startTag.hasAttribute("action")) {
                     String action = startTag.attributes.get("action");
                     form.attributes().put("action", action);
                  }
               }

               tb.processStartTag("hr");
               tb.processStartTag("label");
               String prompt = startTag.hasAttribute("prompt") ? startTag.attributes.get("prompt") : "This is a searchable index. Enter search keywords: ";
               tb.process(new Token.Character().data(prompt));
               Attributes inputAttribs = new Attributes();
               if (startTag.hasAttributes()) {
                  for (Attribute attr : startTag.attributes) {
                     if (!StringUtil.inSorted(attr.getKey(), HtmlTreeBuilderState.Constants.InBodyStartInputAttribs)) {
                        inputAttribs.put(attr);
                     }
                  }
               }

               inputAttribs.put("name", "isindex");
               tb.processStartTag("input", inputAttribs);
               tb.processEndTag("label");
               tb.processStartTag("hr");
               tb.processEndTag("form");
               break;
            case "textarea":
               tb.insert(startTag);
               if (!startTag.isSelfClosing()) {
                  tb.tokeniser.transition(TokeniserState.Rcdata);
                  tb.markInsertionMode();
                  tb.framesetOk(false);
                  tb.transition(Text);
               }
               break;
            case "xmp":
               if (tb.inButtonScope("p")) {
                  tb.processEndTag("p");
               }

               tb.reconstructFormattingElements();
               tb.framesetOk(false);
               HtmlTreeBuilderState.handleRawtext(startTag, tb);
               break;
            case "iframe":
               tb.framesetOk(false);
               HtmlTreeBuilderState.handleRawtext(startTag, tb);
               break;
            case "noembed":
               HtmlTreeBuilderState.handleRawtext(startTag, tb);
               break;
            case "select":
               tb.reconstructFormattingElements();
               tb.insert(startTag);
               tb.framesetOk(false);
               if (!startTag.selfClosing) {
                  HtmlTreeBuilderState state = tb.state();
                  if (!state.equals(InTable) && !state.equals(InCaption) && !state.equals(InTableBody) && !state.equals(InRow) && !state.equals(InCell)) {
                     tb.transition(InSelect);
                  } else {
                     tb.transition(InSelectInTable);
                  }
               }
               break;
            case "math":
               tb.reconstructFormattingElements();
               tb.insert(startTag);
               break;
            case "svg":
               tb.reconstructFormattingElements();
               tb.insert(startTag);
               break;
            case "h1":
            case "h2":
            case "h3":
            case "h4":
            case "h5":
            case "h6":
               if (tb.inButtonScope("p")) {
                  tb.processEndTag("p");
               }

               if (StringUtil.inSorted(tb.currentElement().normalName(), HtmlTreeBuilderState.Constants.Headings)) {
                  tb.error(this);
                  tb.pop();
               }

               tb.insert(startTag);
               break;
            case "pre":
            case "listing":
               if (tb.inButtonScope("p")) {
                  tb.processEndTag("p");
               }

               tb.insert(startTag);
               tb.reader.matchConsume("\n");
               tb.framesetOk(false);
               break;
            case "dd":
            case "dt":
               tb.framesetOk(false);
               ArrayList<Element> stack = tb.getStack();
               int bottom = stack.size() - 1;
               int upper = bottom >= 24 ? bottom - 24 : 0;

               for (int i = bottom; i >= upper; i--) {
                  Element elxx = stack.get(i);
                  if (StringUtil.inSorted(elxx.normalName(), HtmlTreeBuilderState.Constants.DdDt)) {
                     tb.processEndTag(elxx.normalName());
                     break;
                  }

                  if (tb.isSpecial(elxx) && !StringUtil.inSorted(elxx.normalName(), HtmlTreeBuilderState.Constants.InBodyStartLiBreakers)) {
                     break;
                  }
               }

               if (tb.inButtonScope("p")) {
                  tb.processEndTag("p");
               }

               tb.insert(startTag);
               break;
            case "optgroup":
            case "option":
               if (tb.currentElementIs("option")) {
                  tb.processEndTag("option");
               }

               tb.reconstructFormattingElements();
               tb.insert(startTag);
               break;
            case "rp":
            case "rt":
               if (tb.inScope("ruby")) {
                  tb.generateImpliedEndTags();
                  if (!tb.currentElementIs("ruby")) {
                     tb.error(this);
                     tb.popStackToBefore("ruby");
                  }

                  tb.insert(startTag);
               }
               break;
            case "area":
            case "br":
            case "embed":
            case "img":
            case "keygen":
            case "wbr":
               tb.reconstructFormattingElements();
               tb.insertEmpty(startTag);
               tb.framesetOk(false);
               break;
            case "b":
            case "big":
            case "code":
            case "em":
            case "font":
            case "i":
            case "s":
            case "small":
            case "strike":
            case "strong":
            case "tt":
            case "u": {
               tb.reconstructFormattingElements();
               Element el = tb.insert(startTag);
               tb.pushActiveFormattingElements(el);
               break;
            }
            default:
               if (!Tag.isKnownTag(name)) {
                  tb.insert(startTag);
               } else if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InBodyStartPClosers)) {
                  if (tb.inButtonScope("p")) {
                     tb.processEndTag("p");
                  }

                  tb.insert(startTag);
               } else {
                  if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InBodyStartToHead)) {
                     return tb.process(t, InHead);
                  }

                  if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InBodyStartApplets)) {
                     tb.reconstructFormattingElements();
                     tb.insert(startTag);
                     tb.insertMarkerToFormattingElements();
                     tb.framesetOk(false);
                  } else if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InBodyStartMedia)) {
                     tb.insertEmpty(startTag);
                  } else {
                     if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InBodyStartDrop)) {
                        tb.error(this);
                        return false;
                     }

                     tb.reconstructFormattingElements();
                     tb.insert(startTag);
                  }
               }
         }

         return true;
      }

      private boolean inBodyEndTag(Token t, HtmlTreeBuilder tb) {
         Token.EndTag endTag = t.asEndTag();
         String name = endTag.normalName();
         switch (name) {
            case "template":
               tb.process(t, InHead);
               break;
            case "sarcasm":
            case "span":
               return this.anyOtherEndTag(t, tb);
            case "li":
               if (!tb.inListItemScope(name)) {
                  tb.error(this);
                  return false;
               }

               tb.generateImpliedEndTags(name);
               if (!tb.currentElementIs(name)) {
                  tb.error(this);
               }

               tb.popStackToClose(name);
               break;
            case "body":
               if (!tb.inScope("body")) {
                  tb.error(this);
                  return false;
               }

               this.anyOtherEndTag(t, tb);
               tb.transition(AfterBody);
               break;
            case "html":
               boolean notIgnored = tb.processEndTag("body");
               if (notIgnored) {
                  return tb.process(endTag);
               }
               break;
            case "form":
               if (!tb.onStack("template")) {
                  Element currentForm = tb.getFormElement();
                  tb.setFormElement(null);
                  if (currentForm == null || !tb.inScope(name)) {
                     tb.error(this);
                     return false;
                  }

                  tb.generateImpliedEndTags();
                  if (!tb.currentElementIs(name)) {
                     tb.error(this);
                  }

                  tb.removeFromStack(currentForm);
               } else {
                  if (!tb.inScope(name)) {
                     tb.error(this);
                     return false;
                  }

                  tb.generateImpliedEndTags();
                  if (!tb.currentElementIs(name)) {
                     tb.error(this);
                  }

                  tb.popStackToClose(name);
               }
               break;
            case "p":
               if (!tb.inButtonScope(name)) {
                  tb.error(this);
                  tb.processStartTag(name);
                  return tb.process(endTag);
               }

               tb.generateImpliedEndTags(name);
               if (!tb.currentElementIs(name)) {
                  tb.error(this);
               }

               tb.popStackToClose(name);
               break;
            case "dd":
            case "dt":
               if (!tb.inScope(name)) {
                  tb.error(this);
                  return false;
               }

               tb.generateImpliedEndTags(name);
               if (!tb.currentElementIs(name)) {
                  tb.error(this);
               }

               tb.popStackToClose(name);
               break;
            case "h1":
            case "h2":
            case "h3":
            case "h4":
            case "h5":
            case "h6":
               if (!tb.inScope(HtmlTreeBuilderState.Constants.Headings)) {
                  tb.error(this);
                  return false;
               }

               tb.generateImpliedEndTags(name);
               if (!tb.currentElementIs(name)) {
                  tb.error(this);
               }

               tb.popStackToClose(HtmlTreeBuilderState.Constants.Headings);
               break;
            case "br":
               tb.error(this);
               tb.processStartTag("br");
               return false;
            default:
               if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InBodyEndAdoptionFormatters)) {
                  return this.inBodyEndTagAdoption(t, tb);
               }

               if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InBodyEndClosers)) {
                  if (!tb.inScope(name)) {
                     tb.error(this);
                     return false;
                  }

                  tb.generateImpliedEndTags();
                  if (!tb.currentElementIs(name)) {
                     tb.error(this);
                  }

                  tb.popStackToClose(name);
               } else {
                  if (!StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InBodyStartApplets)) {
                     return this.anyOtherEndTag(t, tb);
                  }

                  if (!tb.inScope("name")) {
                     if (!tb.inScope(name)) {
                        tb.error(this);
                        return false;
                     }

                     tb.generateImpliedEndTags();
                     if (!tb.currentElementIs(name)) {
                        tb.error(this);
                     }

                     tb.popStackToClose(name);
                     tb.clearFormattingElementsToLastMarker();
                  }
               }
         }

         return true;
      }

      boolean anyOtherEndTag(Token t, HtmlTreeBuilder tb) {
         String name = t.asEndTag().normalName;
         ArrayList<Element> stack = tb.getStack();
         Element elFromStack = tb.getFromStack(name);
         if (elFromStack == null) {
            tb.error(this);
            return false;
         } else {
            for (int pos = stack.size() - 1; pos >= 0; pos--) {
               Element node = stack.get(pos);
               if (node.normalName().equals(name)) {
                  tb.generateImpliedEndTags(name);
                  if (!tb.currentElementIs(name)) {
                     tb.error(this);
                  }

                  tb.popStackToClose(name);
                  break;
               }

               if (tb.isSpecial(node)) {
                  tb.error(this);
                  return false;
               }
            }

            return true;
         }
      }

      private boolean inBodyEndTagAdoption(Token t, HtmlTreeBuilder tb) {
         Token.EndTag endTag = t.asEndTag();
         String name = endTag.normalName();
         ArrayList<Element> stack = tb.getStack();

         for (int i = 0; i < 8; i++) {
            Element formatEl = tb.getActiveFormattingElement(name);
            if (formatEl == null) {
               return this.anyOtherEndTag(t, tb);
            }

            if (!tb.onStack(formatEl)) {
               tb.error(this);
               tb.removeFromActiveFormattingElements(formatEl);
               return true;
            }

            if (!tb.inScope(formatEl.normalName())) {
               tb.error(this);
               return false;
            }

            if (tb.currentElement() != formatEl) {
               tb.error(this);
            }

            Element furthestBlock = null;
            Element commonAncestor = null;
            boolean seenFormattingElement = false;
            int stackSize = stack.size();
            int bookmark = -1;

            for (int si = 1; si < stackSize && si < 64; si++) {
               Element el = stack.get(si);
               if (el == formatEl) {
                  commonAncestor = stack.get(si - 1);
                  seenFormattingElement = true;
                  bookmark = tb.positionOfElement(el);
               } else if (seenFormattingElement && tb.isSpecial(el)) {
                  furthestBlock = el;
                  break;
               }
            }

            if (furthestBlock == null) {
               tb.popStackToClose(formatEl.normalName());
               tb.removeFromActiveFormattingElements(formatEl);
               return true;
            }

            Element node = furthestBlock;
            Element lastNode = furthestBlock;

            for (int j = 0; j < 3; j++) {
               if (tb.onStack(node)) {
                  node = tb.aboveOnStack(node);
               }

               if (!tb.isInActiveFormattingElements(node)) {
                  tb.removeFromStack(node);
               } else {
                  if (node == formatEl) {
                     break;
                  }

                  Element replacement = new Element(tb.tagFor(node.nodeName(), ParseSettings.preserveCase), tb.getBaseUri());
                  tb.replaceActiveFormattingElement(node, replacement);
                  tb.replaceOnStack(node, replacement);
                  node = replacement;
                  if (lastNode == furthestBlock) {
                     bookmark = tb.positionOfElement(replacement) + 1;
                  }

                  if (lastNode.parent() != null) {
                     lastNode.remove();
                  }

                  replacement.appendChild(lastNode);
                  lastNode = replacement;
               }
            }

            if (commonAncestor != null) {
               if (StringUtil.inSorted(commonAncestor.normalName(), HtmlTreeBuilderState.Constants.InBodyEndTableFosters)) {
                  if (lastNode.parent() != null) {
                     lastNode.remove();
                  }

                  tb.insertInFosterParent(lastNode);
               } else {
                  if (lastNode.parent() != null) {
                     lastNode.remove();
                  }

                  commonAncestor.appendChild(lastNode);
               }
            }

            Element adopter = new Element(formatEl.tag(), tb.getBaseUri());
            adopter.attributes().addAll(formatEl.attributes());
            adopter.appendChildren(furthestBlock.childNodes());
            furthestBlock.appendChild(adopter);
            tb.removeFromActiveFormattingElements(formatEl);
            tb.pushWithBookmark(adopter, bookmark);
            tb.removeFromStack(formatEl);
            tb.insertOnStackAfter(furthestBlock, adopter);
         }

         return true;
      }
   },
   Text {
      @Override
      boolean process(Token t, HtmlTreeBuilder tb) {
         if (t.isCharacter()) {
            tb.insert(t.asCharacter());
         } else {
            if (t.isEOF()) {
               tb.error(this);
               tb.pop();
               tb.transition(tb.originalState());
               return tb.process(t);
            }

            if (t.isEndTag()) {
               tb.pop();
               tb.transition(tb.originalState());
            }
         }

         return true;
      }
   },
   InTable {
      @Override
      boolean process(Token t, HtmlTreeBuilder tb) {
         if (t.isCharacter() && StringUtil.inSorted(tb.currentElement().normalName(), HtmlTreeBuilderState.Constants.InTableFoster)) {
            tb.newPendingTableCharacters();
            tb.markInsertionMode();
            tb.transition(InTableText);
            return tb.process(t);
         } else if (t.isComment()) {
            tb.insert(t.asComment());
            return true;
         } else if (t.isDoctype()) {
            tb.error(this);
            return false;
         } else if (!t.isStartTag()) {
            if (t.isEndTag()) {
               Token.EndTag endTag = t.asEndTag();
               String name = endTag.normalName();
               if (name.equals("table")) {
                  if (!tb.inTableScope(name)) {
                     tb.error(this);
                     return false;
                  }

                  tb.popStackToClose("table");
                  tb.resetInsertionMode();
               } else {
                  if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InTableEndErr)) {
                     tb.error(this);
                     return false;
                  }

                  if (!name.equals("template")) {
                     return this.anythingElse(t, tb);
                  }

                  tb.process(t, InHead);
               }

               return true;
            } else if (t.isEOF()) {
               if (tb.currentElementIs("html")) {
                  tb.error(this);
               }

               return true;
            } else {
               return this.anythingElse(t, tb);
            }
         } else {
            Token.StartTag startTag = t.asStartTag();
            String name = startTag.normalName();
            if (name.equals("caption")) {
               tb.clearStackToTableContext();
               tb.insertMarkerToFormattingElements();
               tb.insert(startTag);
               tb.transition(InCaption);
            } else if (name.equals("colgroup")) {
               tb.clearStackToTableContext();
               tb.insert(startTag);
               tb.transition(InColumnGroup);
            } else {
               if (name.equals("col")) {
                  tb.clearStackToTableContext();
                  tb.processStartTag("colgroup");
                  return tb.process(t);
               }

               if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InTableToBody)) {
                  tb.clearStackToTableContext();
                  tb.insert(startTag);
                  tb.transition(InTableBody);
               } else {
                  if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InTableAddBody)) {
                     tb.clearStackToTableContext();
                     tb.processStartTag("tbody");
                     return tb.process(t);
                  }

                  if (name.equals("table")) {
                     tb.error(this);
                     if (!tb.inTableScope(name)) {
                        return false;
                     }

                     tb.popStackToClose(name);
                     if (!tb.resetInsertionMode()) {
                        tb.insert(startTag);
                        return true;
                     }

                     return tb.process(t);
                  }

                  if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InTableToHead)) {
                     return tb.process(t, InHead);
                  }

                  if (name.equals("input")) {
                     if (!startTag.hasAttributes() || !startTag.attributes.get("type").equalsIgnoreCase("hidden")) {
                        return this.anythingElse(t, tb);
                     }

                     tb.insertEmpty(startTag);
                  } else {
                     if (!name.equals("form")) {
                        return this.anythingElse(t, tb);
                     }

                     tb.error(this);
                     if (tb.getFormElement() != null || tb.onStack("template")) {
                        return false;
                     }

                     tb.insertForm(startTag, false, false);
                  }
               }
            }

            return true;
         }
      }

      boolean anythingElse(Token t, HtmlTreeBuilder tb) {
         tb.error(this);
         tb.setFosterInserts(true);
         tb.process(t, InBody);
         tb.setFosterInserts(false);
         return true;
      }
   },
   InTableText {
      @Override
      boolean process(Token t, HtmlTreeBuilder tb) {
         if (t.type == Token.TokenType.Character) {
            Token.Character c = t.asCharacter();
            if (c.getData().equals(HtmlTreeBuilderState.nullString)) {
               tb.error(this);
               return false;
            } else {
               tb.getPendingTableCharacters().add(c.getData());
               return true;
            }
         } else {
            if (tb.getPendingTableCharacters().size() > 0) {
               for (String character : tb.getPendingTableCharacters()) {
                  if (!HtmlTreeBuilderState.isWhitespace(character)) {
                     tb.error(this);
                     if (StringUtil.inSorted(tb.currentElement().normalName(), HtmlTreeBuilderState.Constants.InTableFoster)) {
                        tb.setFosterInserts(true);
                        tb.process(new Token.Character().data(character), InBody);
                        tb.setFosterInserts(false);
                     } else {
                        tb.process(new Token.Character().data(character), InBody);
                     }
                  } else {
                     tb.insert(new Token.Character().data(character));
                  }
               }

               tb.newPendingTableCharacters();
            }

            tb.transition(tb.originalState());
            return tb.process(t);
         }
      }
   },
   InCaption {
      @Override
      boolean process(Token t, HtmlTreeBuilder tb) {
         if (t.isEndTag() && t.asEndTag().normalName().equals("caption")) {
            Token.EndTag endTag = t.asEndTag();
            String name = endTag.normalName();
            if (!tb.inTableScope(name)) {
               tb.error(this);
               return false;
            }

            tb.generateImpliedEndTags();
            if (!tb.currentElementIs("caption")) {
               tb.error(this);
            }

            tb.popStackToClose("caption");
            tb.clearFormattingElementsToLastMarker();
            tb.transition(InTable);
         } else {
            if ((!t.isStartTag() || !StringUtil.inSorted(t.asStartTag().normalName(), HtmlTreeBuilderState.Constants.InCellCol))
               && (!t.isEndTag() || !t.asEndTag().normalName().equals("table"))) {
               if (t.isEndTag() && StringUtil.inSorted(t.asEndTag().normalName(), HtmlTreeBuilderState.Constants.InCaptionIgnore)) {
                  tb.error(this);
                  return false;
               }

               return tb.process(t, InBody);
            }

            tb.error(this);
            boolean processed = tb.processEndTag("caption");
            if (processed) {
               return tb.process(t);
            }
         }

         return true;
      }
   },
   InColumnGroup {
      @Override
      boolean process(Token t, HtmlTreeBuilder tb) {
         if (HtmlTreeBuilderState.isWhitespace(t)) {
            tb.insert(t.asCharacter());
            return true;
         } else {
            switch (t.type) {
               case Comment:
                  tb.insert(t.asComment());
                  break;
               case Doctype:
                  tb.error(this);
                  break;
               case StartTag:
                  Token.StartTag startTag = t.asStartTag();
                  String var8 = startTag.normalName();
                  switch (var8) {
                     case "html":
                        return tb.process(t, InBody);
                     case "col":
                        tb.insertEmpty(startTag);
                        return true;
                     case "template":
                        tb.process(t, InHead);
                        return true;
                     default:
                        return this.anythingElse(t, tb);
                  }
               case EndTag:
                  Token.EndTag endTag = t.asEndTag();
                  String name = endTag.normalName();
                  switch (name) {
                     case "colgroup":
                        if (!tb.currentElementIs(name)) {
                           tb.error(this);
                           return false;
                        }

                        tb.pop();
                        tb.transition(InTable);
                        return true;
                     case "template":
                        tb.process(t, InHead);
                        return true;
                     default:
                        return this.anythingElse(t, tb);
                  }
               case Character:
               default:
                  return this.anythingElse(t, tb);
               case EOF:
                  if (tb.currentElementIs("html")) {
                     return true;
                  }

                  return this.anythingElse(t, tb);
            }

            return true;
         }
      }

      private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
         if (!tb.currentElementIs("colgroup")) {
            tb.error(this);
            return false;
         } else {
            tb.pop();
            tb.transition(InTable);
            tb.process(t);
            return true;
         }
      }
   },
   InTableBody {
      @Override
      boolean process(Token t, HtmlTreeBuilder tb) {
         switch (t.type) {
            case StartTag:
               Token.StartTag startTag = t.asStartTag();
               String namex = startTag.normalName();
               if (!namex.equals("tr")) {
                  if (StringUtil.inSorted(namex, HtmlTreeBuilderState.Constants.InCellNames)) {
                     tb.error(this);
                     tb.processStartTag("tr");
                     return tb.process(startTag);
                  }

                  if (StringUtil.inSorted(namex, HtmlTreeBuilderState.Constants.InTableBodyExit)) {
                     return this.exitTableBody(t, tb);
                  }

                  return this.anythingElse(t, tb);
               }

               tb.clearStackToTableBodyContext();
               tb.insert(startTag);
               tb.transition(InRow);
               break;
            case EndTag:
               Token.EndTag endTag = t.asEndTag();
               String name = endTag.normalName();
               if (!StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InTableEndIgnore)) {
                  if (name.equals("table")) {
                     return this.exitTableBody(t, tb);
                  }

                  if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InTableBodyEndIgnore)) {
                     tb.error(this);
                     return false;
                  }

                  return this.anythingElse(t, tb);
               }

               if (!tb.inTableScope(name)) {
                  tb.error(this);
                  return false;
               }

               tb.clearStackToTableBodyContext();
               tb.pop();
               tb.transition(InTable);
               break;
            default:
               return this.anythingElse(t, tb);
         }

         return true;
      }

      private boolean exitTableBody(Token t, HtmlTreeBuilder tb) {
         if (!tb.inTableScope("tbody") && !tb.inTableScope("thead") && !tb.inScope("tfoot")) {
            tb.error(this);
            return false;
         } else {
            tb.clearStackToTableBodyContext();
            tb.processEndTag(tb.currentElement().normalName());
            return tb.process(t);
         }
      }

      private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
         return tb.process(t, InTable);
      }
   },
   InRow {
      @Override
      boolean process(Token t, HtmlTreeBuilder tb) {
         if (t.isStartTag()) {
            Token.StartTag startTag = t.asStartTag();
            String name = startTag.normalName();
            if (!StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InCellNames)) {
               if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InRowMissing)) {
                  return this.handleMissingTr(t, tb);
               }

               return this.anythingElse(t, tb);
            }

            tb.clearStackToTableRowContext();
            tb.insert(startTag);
            tb.transition(InCell);
            tb.insertMarkerToFormattingElements();
         } else {
            if (!t.isEndTag()) {
               return this.anythingElse(t, tb);
            }

            Token.EndTag endTag = t.asEndTag();
            String name = endTag.normalName();
            if (name.equals("tr")) {
               if (!tb.inTableScope(name)) {
                  tb.error(this);
                  return false;
               }

               tb.clearStackToTableRowContext();
               tb.pop();
               tb.transition(InTableBody);
            } else {
               if (name.equals("table")) {
                  return this.handleMissingTr(t, tb);
               }

               if (!StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InTableToBody)) {
                  if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InRowIgnore)) {
                     tb.error(this);
                     return false;
                  }

                  return this.anythingElse(t, tb);
               }

               if (!tb.inTableScope(name) || !tb.inTableScope("tr")) {
                  tb.error(this);
                  return false;
               }

               tb.clearStackToTableRowContext();
               tb.pop();
               tb.transition(InTableBody);
            }
         }

         return true;
      }

      private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
         return tb.process(t, InTable);
      }

      private boolean handleMissingTr(Token t, TreeBuilder tb) {
         boolean processed = tb.processEndTag("tr");
         return processed ? tb.process(t) : false;
      }
   },
   InCell {
      @Override
      boolean process(Token t, HtmlTreeBuilder tb) {
         if (t.isEndTag()) {
            Token.EndTag endTag = t.asEndTag();
            String name = endTag.normalName();
            if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InCellNames)) {
               if (!tb.inTableScope(name)) {
                  tb.error(this);
                  tb.transition(InRow);
                  return false;
               } else {
                  tb.generateImpliedEndTags();
                  if (!tb.currentElementIs(name)) {
                     tb.error(this);
                  }

                  tb.popStackToClose(name);
                  tb.clearFormattingElementsToLastMarker();
                  tb.transition(InRow);
                  return true;
               }
            } else if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InCellBody)) {
               tb.error(this);
               return false;
            } else if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InCellTable)) {
               if (!tb.inTableScope(name)) {
                  tb.error(this);
                  return false;
               } else {
                  this.closeCell(tb);
                  return tb.process(t);
               }
            } else {
               return this.anythingElse(t, tb);
            }
         } else if (!t.isStartTag() || !StringUtil.inSorted(t.asStartTag().normalName(), HtmlTreeBuilderState.Constants.InCellCol)) {
            return this.anythingElse(t, tb);
         } else if (!tb.inTableScope("td") && !tb.inTableScope("th")) {
            tb.error(this);
            return false;
         } else {
            this.closeCell(tb);
            return tb.process(t);
         }
      }

      private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
         return tb.process(t, InBody);
      }

      private void closeCell(HtmlTreeBuilder tb) {
         if (tb.inTableScope("td")) {
            tb.processEndTag("td");
         } else {
            tb.processEndTag("th");
         }
      }
   },
   InSelect {
      @Override
      boolean process(Token t, HtmlTreeBuilder tb) {
         switch (t.type) {
            case Comment:
               tb.insert(t.asComment());
               break;
            case Doctype:
               tb.error(this);
               return false;
            case StartTag:
               Token.StartTag start = t.asStartTag();
               String name = start.normalName();
               if (name.equals("html")) {
                  return tb.process(start, InBody);
               }

               if (name.equals("option")) {
                  if (tb.currentElementIs("option")) {
                     tb.processEndTag("option");
                  }

                  tb.insert(start);
               } else {
                  if (!name.equals("optgroup")) {
                     if (name.equals("select")) {
                        tb.error(this);
                        return tb.processEndTag("select");
                     }

                     if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InSelectEnd)) {
                        tb.error(this);
                        if (!tb.inSelectScope("select")) {
                           return false;
                        }

                        tb.processEndTag("select");
                        return tb.process(start);
                     }

                     if (!name.equals("script") && !name.equals("template")) {
                        return this.anythingElse(t, tb);
                     }

                     return tb.process(t, InHead);
                  }

                  if (tb.currentElementIs("option")) {
                     tb.processEndTag("option");
                  }

                  if (tb.currentElementIs("optgroup")) {
                     tb.processEndTag("optgroup");
                  }

                  tb.insert(start);
               }
               break;
            case EndTag:
               Token.EndTag end = t.asEndTag();
               String name = end.normalName();
               switch (name) {
                  case "optgroup":
                     if (tb.currentElementIs("option")
                        && tb.aboveOnStack(tb.currentElement()) != null
                        && tb.aboveOnStack(tb.currentElement()).normalName().equals("optgroup")) {
                        tb.processEndTag("option");
                     }

                     if (tb.currentElementIs("optgroup")) {
                        tb.pop();
                     } else {
                        tb.error(this);
                     }

                     return true;
                  case "option":
                     if (tb.currentElementIs("option")) {
                        tb.pop();
                     } else {
                        tb.error(this);
                     }

                     return true;
                  case "select":
                     if (!tb.inSelectScope(name)) {
                        tb.error(this);
                        return false;
                     }

                     tb.popStackToClose(name);
                     tb.resetInsertionMode();
                     return true;
                  case "template":
                     return tb.process(t, InHead);
                  default:
                     return this.anythingElse(t, tb);
               }
            case Character:
               Token.Character c = t.asCharacter();
               if (c.getData().equals(HtmlTreeBuilderState.nullString)) {
                  tb.error(this);
                  return false;
               }

               tb.insert(c);
               break;
            case EOF:
               if (!tb.currentElementIs("html")) {
                  tb.error(this);
               }
               break;
            default:
               return this.anythingElse(t, tb);
         }

         return true;
      }

      private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
         tb.error(this);
         return false;
      }
   },
   InSelectInTable {
      @Override
      boolean process(Token t, HtmlTreeBuilder tb) {
         if (t.isStartTag() && StringUtil.inSorted(t.asStartTag().normalName(), HtmlTreeBuilderState.Constants.InSelectTableEnd)) {
            tb.error(this);
            tb.popStackToClose("select");
            tb.resetInsertionMode();
            return tb.process(t);
         } else if (t.isEndTag() && StringUtil.inSorted(t.asEndTag().normalName(), HtmlTreeBuilderState.Constants.InSelectTableEnd)) {
            tb.error(this);
            if (tb.inTableScope(t.asEndTag().normalName())) {
               tb.popStackToClose("select");
               tb.resetInsertionMode();
               return tb.process(t);
            } else {
               return false;
            }
         } else {
            return tb.process(t, InSelect);
         }
      }
   },
   InTemplate {
      @Override
      boolean process(Token t, HtmlTreeBuilder tb) {
         switch (t.type) {
            case Comment:
            case Doctype:
            case Character:
               tb.process(t, InBody);
               break;
            case StartTag:
               String name = t.asStartTag().normalName();
               if (!StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InTemplateToHead)) {
                  if (StringUtil.inSorted(name, HtmlTreeBuilderState.Constants.InTemplateToTable)) {
                     tb.popTemplateMode();
                     tb.pushTemplateMode(InTable);
                     tb.transition(InTable);
                     return tb.process(t);
                  }

                  if (name.equals("col")) {
                     tb.popTemplateMode();
                     tb.pushTemplateMode(InColumnGroup);
                     tb.transition(InColumnGroup);
                     return tb.process(t);
                  }

                  if (name.equals("tr")) {
                     tb.popTemplateMode();
                     tb.pushTemplateMode(InTableBody);
                     tb.transition(InTableBody);
                     return tb.process(t);
                  }

                  if (!name.equals("td") && !name.equals("th")) {
                     tb.popTemplateMode();
                     tb.pushTemplateMode(InBody);
                     tb.transition(InBody);
                     return tb.process(t);
                  }

                  tb.popTemplateMode();
                  tb.pushTemplateMode(InRow);
                  tb.transition(InRow);
                  return tb.process(t);
               }

               tb.process(t, InHead);
               break;
            case EndTag:
               String name = t.asEndTag().normalName();
               if (!name.equals("template")) {
                  tb.error(this);
                  return false;
               }

               tb.process(t, InHead);
               break;
            case EOF:
               if (!tb.onStack("template")) {
                  return true;
               }

               tb.error(this);
               tb.popStackToClose("template");
               tb.clearFormattingElementsToLastMarker();
               tb.popTemplateMode();
               tb.resetInsertionMode();
               if (tb.state() != InTemplate && tb.templateModeSize() < 12) {
                  return tb.process(t);
               }

               return true;
         }

         return true;
      }
   },
   AfterBody {
      @Override
      boolean process(Token t, HtmlTreeBuilder tb) {
         if (HtmlTreeBuilderState.isWhitespace(t)) {
            tb.insert(t.asCharacter());
         } else if (t.isComment()) {
            tb.insert(t.asComment());
         } else {
            if (t.isDoctype()) {
               tb.error(this);
               return false;
            }

            if (t.isStartTag() && t.asStartTag().normalName().equals("html")) {
               return tb.process(t, InBody);
            }

            if (t.isEndTag() && t.asEndTag().normalName().equals("html")) {
               if (tb.isFragmentParsing()) {
                  tb.error(this);
                  return false;
               }

               if (tb.onStack("html")) {
                  tb.popStackToClose("html");
               }

               tb.transition(AfterAfterBody);
            } else if (!t.isEOF()) {
               tb.error(this);
               tb.resetBody();
               return tb.process(t);
            }
         }

         return true;
      }
   },
   InFrameset {
      @Override
      boolean process(Token t, HtmlTreeBuilder tb) {
         if (HtmlTreeBuilderState.isWhitespace(t)) {
            tb.insert(t.asCharacter());
         } else if (t.isComment()) {
            tb.insert(t.asComment());
         } else {
            if (t.isDoctype()) {
               tb.error(this);
               return false;
            }

            if (t.isStartTag()) {
               Token.StartTag start = t.asStartTag();
               String var4 = start.normalName();
               switch (var4) {
                  case "html":
                     return tb.process(start, InBody);
                  case "frameset":
                     tb.insert(start);
                     break;
                  case "frame":
                     tb.insertEmpty(start);
                     break;
                  case "noframes":
                     return tb.process(start, InHead);
                  default:
                     tb.error(this);
                     return false;
               }
            } else if (t.isEndTag() && t.asEndTag().normalName().equals("frameset")) {
               if (tb.currentElementIs("html")) {
                  tb.error(this);
                  return false;
               }

               tb.pop();
               if (!tb.isFragmentParsing() && !tb.currentElementIs("frameset")) {
                  tb.transition(AfterFrameset);
               }
            } else {
               if (!t.isEOF()) {
                  tb.error(this);
                  return false;
               }

               if (!tb.currentElementIs("html")) {
                  tb.error(this);
                  return true;
               }
            }
         }

         return true;
      }
   },
   AfterFrameset {
      @Override
      boolean process(Token t, HtmlTreeBuilder tb) {
         if (HtmlTreeBuilderState.isWhitespace(t)) {
            tb.insert(t.asCharacter());
         } else if (t.isComment()) {
            tb.insert(t.asComment());
         } else {
            if (t.isDoctype()) {
               tb.error(this);
               return false;
            }

            if (t.isStartTag() && t.asStartTag().normalName().equals("html")) {
               return tb.process(t, InBody);
            }

            if (t.isEndTag() && t.asEndTag().normalName().equals("html")) {
               tb.transition(AfterAfterFrameset);
            } else {
               if (t.isStartTag() && t.asStartTag().normalName().equals("noframes")) {
                  return tb.process(t, InHead);
               }

               if (!t.isEOF()) {
                  tb.error(this);
                  return false;
               }
            }
         }

         return true;
      }
   },
   AfterAfterBody {
      @Override
      boolean process(Token t, HtmlTreeBuilder tb) {
         if (t.isComment()) {
            tb.insert(t.asComment());
         } else {
            if (t.isDoctype() || t.isStartTag() && t.asStartTag().normalName().equals("html")) {
               return tb.process(t, InBody);
            }

            if (HtmlTreeBuilderState.isWhitespace(t)) {
               tb.insert(t.asCharacter());
            } else if (!t.isEOF()) {
               tb.error(this);
               tb.resetBody();
               return tb.process(t);
            }
         }

         return true;
      }
   },
   AfterAfterFrameset {
      @Override
      boolean process(Token t, HtmlTreeBuilder tb) {
         if (t.isComment()) {
            tb.insert(t.asComment());
         } else {
            if (t.isDoctype() || HtmlTreeBuilderState.isWhitespace(t) || t.isStartTag() && t.asStartTag().normalName().equals("html")) {
               return tb.process(t, InBody);
            }

            if (!t.isEOF()) {
               if (t.isStartTag() && t.asStartTag().normalName().equals("noframes")) {
                  return tb.process(t, InHead);
               }

               tb.error(this);
               return false;
            }
         }

         return true;
      }
   },
   ForeignContent {
      @Override
      boolean process(Token t, HtmlTreeBuilder tb) {
         return true;
      }
   };

   private static final String nullString = String.valueOf('\u0000');

   private HtmlTreeBuilderState() {
   }

   abstract boolean process(Token var1, HtmlTreeBuilder var2);

   private static boolean isWhitespace(Token t) {
      if (t.isCharacter()) {
         String data = t.asCharacter().getData();
         return StringUtil.isBlank(data);
      } else {
         return false;
      }
   }

   private static boolean isWhitespace(String data) {
      return StringUtil.isBlank(data);
   }

   private static void handleRcData(Token.StartTag startTag, HtmlTreeBuilder tb) {
      tb.tokeniser.transition(TokeniserState.Rcdata);
      tb.markInsertionMode();
      tb.transition(Text);
      tb.insert(startTag);
   }

   private static void handleRawtext(Token.StartTag startTag, HtmlTreeBuilder tb) {
      tb.tokeniser.transition(TokeniserState.Rawtext);
      tb.markInsertionMode();
      tb.transition(Text);
      tb.insert(startTag);
   }

   static final class Constants {
      static final String[] InHeadEmpty = new String[]{"base", "basefont", "bgsound", "command", "link"};
      static final String[] InHeadRaw = new String[]{"noframes", "style"};
      static final String[] InHeadEnd = new String[]{"body", "br", "html"};
      static final String[] AfterHeadBody = new String[]{"body", "br", "html"};
      static final String[] BeforeHtmlToHead = new String[]{"body", "br", "head", "html"};
      static final String[] InHeadNoScriptHead = new String[]{"basefont", "bgsound", "link", "meta", "noframes", "style"};
      static final String[] InBodyStartToHead = new String[]{
         "base", "basefont", "bgsound", "command", "link", "meta", "noframes", "script", "style", "template", "title"
      };
      static final String[] InBodyStartPClosers = new String[]{
         "address",
         "article",
         "aside",
         "blockquote",
         "center",
         "details",
         "dir",
         "div",
         "dl",
         "fieldset",
         "figcaption",
         "figure",
         "footer",
         "header",
         "hgroup",
         "menu",
         "nav",
         "ol",
         "p",
         "section",
         "summary",
         "ul"
      };
      static final String[] Headings = new String[]{"h1", "h2", "h3", "h4", "h5", "h6"};
      static final String[] InBodyStartLiBreakers = new String[]{"address", "div", "p"};
      static final String[] DdDt = new String[]{"dd", "dt"};
      static final String[] InBodyStartApplets = new String[]{"applet", "marquee", "object"};
      static final String[] InBodyStartMedia = new String[]{"param", "source", "track"};
      static final String[] InBodyStartInputAttribs = new String[]{"action", "name", "prompt"};
      static final String[] InBodyStartDrop = new String[]{"caption", "col", "colgroup", "frame", "head", "tbody", "td", "tfoot", "th", "thead", "tr"};
      static final String[] InBodyEndClosers = new String[]{
         "address",
         "article",
         "aside",
         "blockquote",
         "button",
         "center",
         "details",
         "dir",
         "div",
         "dl",
         "fieldset",
         "figcaption",
         "figure",
         "footer",
         "header",
         "hgroup",
         "listing",
         "menu",
         "nav",
         "ol",
         "pre",
         "section",
         "summary",
         "ul"
      };
      static final String[] InBodyEndAdoptionFormatters = new String[]{
         "a", "b", "big", "code", "em", "font", "i", "nobr", "s", "small", "strike", "strong", "tt", "u"
      };
      static final String[] InBodyEndTableFosters = new String[]{"table", "tbody", "tfoot", "thead", "tr"};
      static final String[] InTableToBody = new String[]{"tbody", "tfoot", "thead"};
      static final String[] InTableAddBody = new String[]{"td", "th", "tr"};
      static final String[] InTableToHead = new String[]{"script", "style", "template"};
      static final String[] InCellNames = new String[]{"td", "th"};
      static final String[] InCellBody = new String[]{"body", "caption", "col", "colgroup", "html"};
      static final String[] InCellTable = new String[]{"table", "tbody", "tfoot", "thead", "tr"};
      static final String[] InCellCol = new String[]{"caption", "col", "colgroup", "tbody", "td", "tfoot", "th", "thead", "tr"};
      static final String[] InTableEndErr = new String[]{"body", "caption", "col", "colgroup", "html", "tbody", "td", "tfoot", "th", "thead", "tr"};
      static final String[] InTableFoster = new String[]{"table", "tbody", "tfoot", "thead", "tr"};
      static final String[] InTableBodyExit = new String[]{"caption", "col", "colgroup", "tbody", "tfoot", "thead"};
      static final String[] InTableBodyEndIgnore = new String[]{"body", "caption", "col", "colgroup", "html", "td", "th", "tr"};
      static final String[] InRowMissing = new String[]{"caption", "col", "colgroup", "tbody", "tfoot", "thead", "tr"};
      static final String[] InRowIgnore = new String[]{"body", "caption", "col", "colgroup", "html", "td", "th"};
      static final String[] InSelectEnd = new String[]{"input", "keygen", "textarea"};
      static final String[] InSelectTableEnd = new String[]{"caption", "table", "tbody", "td", "tfoot", "th", "thead", "tr"};
      static final String[] InTableEndIgnore = new String[]{"tbody", "tfoot", "thead"};
      static final String[] InHeadNoscriptIgnore = new String[]{"head", "noscript"};
      static final String[] InCaptionIgnore = new String[]{"body", "col", "colgroup", "html", "tbody", "td", "tfoot", "th", "thead", "tr"};
      static final String[] InTemplateToHead = new String[]{"base", "basefont", "bgsound", "link", "meta", "noframes", "script", "style", "template", "title"};
      static final String[] InTemplateToTable = new String[]{"caption", "colgroup", "tbody", "tfoot", "thead"};
   }
}
