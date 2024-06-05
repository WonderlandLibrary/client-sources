package ClassSub;

import javax.swing.*;

final class Class207 extends Class116.Class354
{
    final String[][] val$options;
    final String val$currentValue;
    final String val$description;
    
    
    Class207(final String s, final String s2, final String[][] val$options, final String val$currentValue, final String val$description) {
        this.val$options = val$options;
        this.val$currentValue = val$currentValue;
        this.val$description = val$description;
        super(s, s2);
    }
    
    @Override
    public void showDialog() {
        int selectedIndex = -1;
        final DefaultComboBoxModel<Object> defaultComboBoxModel = new DefaultComboBoxModel<Object>();
        for (int i = 0; i < this.val$options.length; ++i) {
            defaultComboBoxModel.addElement(this.val$options[i][0]);
            if (this.getValue(i).equals(this.val$currentValue)) {
                selectedIndex = i;
            }
        }
        final JComboBox comboBox = new JComboBox(defaultComboBoxModel);
        comboBox.setSelectedIndex(selectedIndex);
        if (this.showValueDialog(comboBox, this.val$description)) {
            this.value = this.getValue(comboBox.getSelectedIndex());
        }
    }
    
    private String getValue(final int n) {
        if (this.val$options[n].length == 1) {
            return this.val$options[n][0];
        }
        return this.val$options[n][1];
    }
    
    @Override
    public String toString() {
        for (int i = 0; i < this.val$options.length; ++i) {
            if (this.getValue(i).equals(this.value)) {
                return this.val$options[i][0].toString();
            }
        }
        return "";
    }
    
    @Override
    public Object getObject() {
        return this.value;
    }
}
