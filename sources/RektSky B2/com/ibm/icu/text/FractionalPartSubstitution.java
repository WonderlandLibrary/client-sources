package com.ibm.icu.text;

import com.ibm.icu.impl.number.*;
import java.text.*;

class FractionalPartSubstitution extends NFSubstitution
{
    private final boolean byDigits;
    private final boolean useSpaces;
    
    FractionalPartSubstitution(final int pos, final NFRuleSet ruleSet, final String description) {
        super(pos, ruleSet, description);
        if (description.equals(">>") || description.equals(">>>") || ruleSet == this.ruleSet) {
            this.byDigits = true;
            this.useSpaces = !description.equals(">>>");
        }
        else {
            this.byDigits = false;
            this.useSpaces = true;
            this.ruleSet.makeIntoFractionRuleSet();
        }
    }
    
    @Override
    public void doSubstitution(final double number, final StringBuilder toInsertInto, final int position, final int recursionCount) {
        if (!this.byDigits) {
            super.doSubstitution(number, toInsertInto, position, recursionCount);
        }
        else {
            final DecimalQuantity_DualStorageBCD fq = new DecimalQuantity_DualStorageBCD(number);
            fq.roundToInfinity();
            boolean pad = false;
            int mag = fq.getLowerDisplayMagnitude();
            while (mag < 0) {
                if (pad && this.useSpaces) {
                    toInsertInto.insert(position + this.pos, ' ');
                }
                else {
                    pad = true;
                }
                this.ruleSet.format(fq.getDigit(mag++), toInsertInto, position + this.pos, recursionCount);
            }
        }
    }
    
    @Override
    public long transformNumber(final long number) {
        return 0L;
    }
    
    @Override
    public double transformNumber(final double number) {
        return number - Math.floor(number);
    }
    
    @Override
    public Number doParse(final String text, final ParsePosition parsePosition, final double baseValue, final double upperBound, final boolean lenientParse, final int nonNumericalExecutedRuleMask) {
        if (!this.byDigits) {
            return super.doParse(text, parsePosition, baseValue, 0.0, lenientParse, nonNumericalExecutedRuleMask);
        }
        String workText = text;
        final ParsePosition workPos = new ParsePosition(1);
        final DecimalQuantity_DualStorageBCD fq = new DecimalQuantity_DualStorageBCD();
        int totalDigits = 0;
        while (workText.length() > 0 && workPos.getIndex() != 0) {
            workPos.setIndex(0);
            int digit = this.ruleSet.parse(workText, workPos, 10.0, nonNumericalExecutedRuleMask).intValue();
            if (lenientParse && workPos.getIndex() == 0) {
                final Number n = this.ruleSet.owner.getDecimalFormat().parse(workText, workPos);
                if (n != null) {
                    digit = n.intValue();
                }
            }
            if (workPos.getIndex() != 0) {
                fq.appendDigit((byte)digit, 0, true);
                ++totalDigits;
                parsePosition.setIndex(parsePosition.getIndex() + workPos.getIndex());
                workText = workText.substring(workPos.getIndex());
                while (workText.length() > 0 && workText.charAt(0) == ' ') {
                    workText = workText.substring(1);
                    parsePosition.setIndex(parsePosition.getIndex() + 1);
                }
            }
        }
        fq.adjustMagnitude(-totalDigits);
        double result = fq.toDouble();
        result = this.composeRuleValue(result, baseValue);
        return new Double(result);
    }
    
    @Override
    public double composeRuleValue(final double newRuleValue, final double oldRuleValue) {
        return newRuleValue + oldRuleValue;
    }
    
    @Override
    public double calcUpperBound(final double oldUpperBound) {
        return 0.0;
    }
    
    @Override
    char tokenChar() {
        return '>';
    }
}
