package org.jsoup.select;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.helper.Validate;
import org.jsoup.internal.Normalizer;
import org.jsoup.internal.StringUtil;
import org.jsoup.parser.TokenQueue;

public class QueryParser {
   private static final String[] combinators = new String[]{",", ">", "+", "~", " "};
   private static final String[] AttributeEvals = new String[]{"=", "!=", "^=", "$=", "*=", "~="};
   private final TokenQueue tq;
   private final String query;
   private final List<Evaluator> evals = new ArrayList<>();
   private static final Pattern NTH_AB = Pattern.compile("(([+-])?(\\d+)?)n(\\s*([+-])?\\s*\\d+)?", 2);
   private static final Pattern NTH_B = Pattern.compile("([+-])?(\\d+)");

   private QueryParser(String query) {
      Validate.notEmpty(query);
      query = query.trim();
      this.query = query;
      this.tq = new TokenQueue(query);
   }

   public static Evaluator parse(String query) {
      try {
         QueryParser p = new QueryParser(query);
         return p.parse();
      } catch (IllegalArgumentException var2) {
         throw new Selector.SelectorParseException(var2.getMessage());
      }
   }

   Evaluator parse() {
      this.tq.consumeWhitespace();
      if (this.tq.matchesAny(combinators)) {
         this.evals.add(new StructuralEvaluator.Root());
         this.combinator(this.tq.consume());
      } else {
         this.findElements();
      }

      while (!this.tq.isEmpty()) {
         boolean seenWhite = this.tq.consumeWhitespace();
         if (this.tq.matchesAny(combinators)) {
            this.combinator(this.tq.consume());
         } else if (seenWhite) {
            this.combinator(' ');
         } else {
            this.findElements();
         }
      }

      return (Evaluator)(this.evals.size() == 1 ? this.evals.get(0) : new CombiningEvaluator.And(this.evals));
   }

   private void combinator(char combinator) {
      this.tq.consumeWhitespace();
      String subQuery = this.consumeSubQuery();
      Evaluator newEval = parse(subQuery);
      boolean replaceRightMost = false;
      Evaluator rootEval;
      Evaluator currentEval;
      if (this.evals.size() == 1) {
         rootEval = currentEval = this.evals.get(0);
         if (rootEval instanceof CombiningEvaluator.Or && combinator != ',') {
            currentEval = ((CombiningEvaluator.Or)currentEval).rightMostEvaluator();

            assert currentEval != null;

            replaceRightMost = true;
         }
      } else {
         rootEval = currentEval = new CombiningEvaluator.And(this.evals);
      }

      this.evals.clear();
      switch (combinator) {
         case ' ':
            currentEval = new CombiningEvaluator.And(new StructuralEvaluator.Parent(currentEval), newEval);
            break;
         case '+':
            currentEval = new CombiningEvaluator.And(new StructuralEvaluator.ImmediatePreviousSibling(currentEval), newEval);
            break;
         case ',':
            CombiningEvaluator.Or or;
            if (currentEval instanceof CombiningEvaluator.Or) {
               or = (CombiningEvaluator.Or)currentEval;
            } else {
               or = new CombiningEvaluator.Or();
               or.add(currentEval);
            }

            or.add(newEval);
            currentEval = or;
            break;
         case '>':
            currentEval = new CombiningEvaluator.And(new StructuralEvaluator.ImmediateParent(currentEval), newEval);
            break;
         case '~':
            currentEval = new CombiningEvaluator.And(new StructuralEvaluator.PreviousSibling(currentEval), newEval);
            break;
         default:
            throw new Selector.SelectorParseException("Unknown combinator '%s'", combinator);
      }

      if (replaceRightMost) {
         ((CombiningEvaluator.Or)rootEval).replaceRightMostEvaluator(currentEval);
      } else {
         rootEval = currentEval;
      }

      this.evals.add(rootEval);
   }

   private String consumeSubQuery() {
      StringBuilder sq = StringUtil.borrowBuilder();

      while (!this.tq.isEmpty()) {
         if (this.tq.matches("(")) {
            sq.append("(").append(this.tq.chompBalanced('(', ')')).append(")");
         } else if (this.tq.matches("[")) {
            sq.append("[").append(this.tq.chompBalanced('[', ']')).append("]");
         } else if (this.tq.matchesAny(combinators)) {
            if (sq.length() > 0) {
               break;
            }

            this.tq.consume();
         } else {
            sq.append(this.tq.consume());
         }
      }

      return StringUtil.releaseBuilder(sq);
   }

   private void findElements() {
      if (this.tq.matchChomp("#")) {
         this.byId();
      } else if (this.tq.matchChomp(".")) {
         this.byClass();
      } else if (this.tq.matchesWord() || this.tq.matches("*|")) {
         this.byTag();
      } else if (this.tq.matches("[")) {
         this.byAttribute();
      } else if (this.tq.matchChomp("*")) {
         this.allElements();
      } else if (this.tq.matchChomp(":lt(")) {
         this.indexLessThan();
      } else if (this.tq.matchChomp(":gt(")) {
         this.indexGreaterThan();
      } else if (this.tq.matchChomp(":eq(")) {
         this.indexEquals();
      } else if (this.tq.matches(":has(")) {
         this.has();
      } else if (this.tq.matches(":contains(")) {
         this.contains(false);
      } else if (this.tq.matches(":containsOwn(")) {
         this.contains(true);
      } else if (this.tq.matches(":containsWholeText(")) {
         this.containsWholeText(false);
      } else if (this.tq.matches(":containsWholeOwnText(")) {
         this.containsWholeText(true);
      } else if (this.tq.matches(":containsData(")) {
         this.containsData();
      } else if (this.tq.matches(":matches(")) {
         this.matches(false);
      } else if (this.tq.matches(":matchesOwn(")) {
         this.matches(true);
      } else if (this.tq.matches(":matchesWholeText(")) {
         this.matchesWholeText(false);
      } else if (this.tq.matches(":matchesWholeOwnText(")) {
         this.matchesWholeText(true);
      } else if (this.tq.matches(":not(")) {
         this.not();
      } else if (this.tq.matchChomp(":nth-child(")) {
         this.cssNthChild(false, false);
      } else if (this.tq.matchChomp(":nth-last-child(")) {
         this.cssNthChild(true, false);
      } else if (this.tq.matchChomp(":nth-of-type(")) {
         this.cssNthChild(false, true);
      } else if (this.tq.matchChomp(":nth-last-of-type(")) {
         this.cssNthChild(true, true);
      } else if (this.tq.matchChomp(":first-child")) {
         this.evals.add(new Evaluator.IsFirstChild());
      } else if (this.tq.matchChomp(":last-child")) {
         this.evals.add(new Evaluator.IsLastChild());
      } else if (this.tq.matchChomp(":first-of-type")) {
         this.evals.add(new Evaluator.IsFirstOfType());
      } else if (this.tq.matchChomp(":last-of-type")) {
         this.evals.add(new Evaluator.IsLastOfType());
      } else if (this.tq.matchChomp(":only-child")) {
         this.evals.add(new Evaluator.IsOnlyChild());
      } else if (this.tq.matchChomp(":only-of-type")) {
         this.evals.add(new Evaluator.IsOnlyOfType());
      } else if (this.tq.matchChomp(":empty")) {
         this.evals.add(new Evaluator.IsEmpty());
      } else if (this.tq.matchChomp(":root")) {
         this.evals.add(new Evaluator.IsRoot());
      } else {
         if (!this.tq.matchChomp(":matchText")) {
            throw new Selector.SelectorParseException("Could not parse query '%s': unexpected token at '%s'", this.query, this.tq.remainder());
         }

         this.evals.add(new Evaluator.MatchText());
      }
   }

   private void byId() {
      String id = this.tq.consumeCssIdentifier();
      Validate.notEmpty(id);
      this.evals.add(new Evaluator.Id(id));
   }

   private void byClass() {
      String className = this.tq.consumeCssIdentifier();
      Validate.notEmpty(className);
      this.evals.add(new Evaluator.Class(className.trim()));
   }

   private void byTag() {
      String tagName = Normalizer.normalize(this.tq.consumeElementSelector());
      Validate.notEmpty(tagName);
      if (tagName.startsWith("*|")) {
         String plainTag = tagName.substring(2);
         this.evals.add(new CombiningEvaluator.Or(new Evaluator.Tag(plainTag), new Evaluator.TagEndsWith(tagName.replace("*|", ":"))));
      } else {
         if (tagName.contains("|")) {
            tagName = tagName.replace("|", ":");
         }

         this.evals.add(new Evaluator.Tag(tagName));
      }
   }

   private void byAttribute() {
      TokenQueue cq = new TokenQueue(this.tq.chompBalanced('[', ']'));
      String key = cq.consumeToAny(AttributeEvals);
      Validate.notEmpty(key);
      cq.consumeWhitespace();
      if (cq.isEmpty()) {
         if (key.startsWith("^")) {
            this.evals.add(new Evaluator.AttributeStarting(key.substring(1)));
         } else {
            this.evals.add(new Evaluator.Attribute(key));
         }
      } else if (cq.matchChomp("=")) {
         this.evals.add(new Evaluator.AttributeWithValue(key, cq.remainder()));
      } else if (cq.matchChomp("!=")) {
         this.evals.add(new Evaluator.AttributeWithValueNot(key, cq.remainder()));
      } else if (cq.matchChomp("^=")) {
         this.evals.add(new Evaluator.AttributeWithValueStarting(key, cq.remainder()));
      } else if (cq.matchChomp("$=")) {
         this.evals.add(new Evaluator.AttributeWithValueEnding(key, cq.remainder()));
      } else if (cq.matchChomp("*=")) {
         this.evals.add(new Evaluator.AttributeWithValueContaining(key, cq.remainder()));
      } else {
         if (!cq.matchChomp("~=")) {
            throw new Selector.SelectorParseException("Could not parse attribute query '%s': unexpected token at '%s'", this.query, cq.remainder());
         }

         this.evals.add(new Evaluator.AttributeWithValueMatching(key, Pattern.compile(cq.remainder())));
      }
   }

   private void allElements() {
      this.evals.add(new Evaluator.AllElements());
   }

   private void indexLessThan() {
      this.evals.add(new Evaluator.IndexLessThan(this.consumeIndex()));
   }

   private void indexGreaterThan() {
      this.evals.add(new Evaluator.IndexGreaterThan(this.consumeIndex()));
   }

   private void indexEquals() {
      this.evals.add(new Evaluator.IndexEquals(this.consumeIndex()));
   }

   private void cssNthChild(boolean backwards, boolean ofType) {
      String argS = Normalizer.normalize(this.tq.chompTo(")"));
      Matcher mAB = NTH_AB.matcher(argS);
      Matcher mB = NTH_B.matcher(argS);
      int a;
      int b;
      if ("odd".equals(argS)) {
         a = 2;
         b = 1;
      } else if ("even".equals(argS)) {
         a = 2;
         b = 0;
      } else if (mAB.matches()) {
         a = mAB.group(3) != null ? Integer.parseInt(mAB.group(1).replaceFirst("^\\+", "")) : 1;
         b = mAB.group(4) != null ? Integer.parseInt(mAB.group(4).replaceFirst("^\\+", "")) : 0;
      } else {
         if (!mB.matches()) {
            throw new Selector.SelectorParseException("Could not parse nth-index '%s': unexpected format", argS);
         }

         a = 0;
         b = Integer.parseInt(mB.group().replaceFirst("^\\+", ""));
      }

      if (ofType) {
         if (backwards) {
            this.evals.add(new Evaluator.IsNthLastOfType(a, b));
         } else {
            this.evals.add(new Evaluator.IsNthOfType(a, b));
         }
      } else if (backwards) {
         this.evals.add(new Evaluator.IsNthLastChild(a, b));
      } else {
         this.evals.add(new Evaluator.IsNthChild(a, b));
      }
   }

   private int consumeIndex() {
      String indexS = this.tq.chompTo(")").trim();
      Validate.isTrue(StringUtil.isNumeric(indexS), "Index must be numeric");
      return Integer.parseInt(indexS);
   }

   private void has() {
      this.tq.consume(":has");
      String subQuery = this.tq.chompBalanced('(', ')');
      Validate.notEmpty(subQuery, ":has(selector) sub-select must not be empty");
      this.evals.add(new StructuralEvaluator.Has(parse(subQuery)));
   }

   private void contains(boolean own) {
      String query = own ? ":containsOwn" : ":contains";
      this.tq.consume(query);
      String searchText = TokenQueue.unescape(this.tq.chompBalanced('(', ')'));
      Validate.notEmpty(searchText, query + "(text) query must not be empty");
      this.evals.add((Evaluator)(own ? new Evaluator.ContainsOwnText(searchText) : new Evaluator.ContainsText(searchText)));
   }

   private void containsWholeText(boolean own) {
      String query = own ? ":containsWholeOwnText" : ":containsWholeText";
      this.tq.consume(query);
      String searchText = TokenQueue.unescape(this.tq.chompBalanced('(', ')'));
      Validate.notEmpty(searchText, query + "(text) query must not be empty");
      this.evals.add((Evaluator)(own ? new Evaluator.ContainsWholeOwnText(searchText) : new Evaluator.ContainsWholeText(searchText)));
   }

   private void containsData() {
      this.tq.consume(":containsData");
      String searchText = TokenQueue.unescape(this.tq.chompBalanced('(', ')'));
      Validate.notEmpty(searchText, ":containsData(text) query must not be empty");
      this.evals.add(new Evaluator.ContainsData(searchText));
   }

   private void matches(boolean own) {
      String query = own ? ":matchesOwn" : ":matches";
      this.tq.consume(query);
      String regex = this.tq.chompBalanced('(', ')');
      Validate.notEmpty(regex, query + "(regex) query must not be empty");
      this.evals.add((Evaluator)(own ? new Evaluator.MatchesOwn(Pattern.compile(regex)) : new Evaluator.Matches(Pattern.compile(regex))));
   }

   private void matchesWholeText(boolean own) {
      String query = own ? ":matchesWholeOwnText" : ":matchesWholeText";
      this.tq.consume(query);
      String regex = this.tq.chompBalanced('(', ')');
      Validate.notEmpty(regex, query + "(regex) query must not be empty");
      this.evals.add((Evaluator)(own ? new Evaluator.MatchesWholeOwnText(Pattern.compile(regex)) : new Evaluator.MatchesWholeText(Pattern.compile(regex))));
   }

   private void not() {
      this.tq.consume(":not");
      String subQuery = this.tq.chompBalanced('(', ')');
      Validate.notEmpty(subQuery, ":not(selector) subselect must not be empty");
      this.evals.add(new StructuralEvaluator.Not(parse(subQuery)));
   }

   @Override
   public String toString() {
      return this.query;
   }
}
