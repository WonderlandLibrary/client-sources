using Dope.Theme;
using Dope.utils;
using System;
using System.Collections.Generic;
using System.Windows.Forms;

namespace Dope.views
{
    public partial class Left : Form
    {
        Config cfg;
        Main parent;
        List<Slots> Slist;

        public Left(Config c, Main m)
        {
            InitializeComponent();
            cfg = c;
            parent = m;
            Slist = new List<Slots>(){S1,S2,S3,S4,S5,S6,S7,S8,S9};
        }

        private void RightSwitch_Click(object sender, EventArgs e)
        {
            parent.ChangeViews(5);
        }

        private void switch1_Click(object sender, EventArgs e)
        {
            cfg.left_Click = LeftToggle.Checked;
        }

        private void LeftMax_Scroll(object sender)
        {
            if (LeftMax.Value <= LeftMin.Value)
            {
                LeftMax.Value = LeftMin.Value + 1;
            }
            cfg.left_Max = LeftMax.RawValue();
        }

        private void LeftMin_Scroll(object sender)
        {
            if (LeftMin.Value >= LeftMax.Value)
            {
                LeftMin.Value = LeftMax.Value - 1;
            }
            cfg.left_Min = LeftMin.RawValue();
        }

        private void LeftBind_TextChanged(object sender, EventArgs e)
        {
            cfg.left_Key = LeftBind.Key;
        }

        private void checkbox1_Click(object sender, EventArgs e)
        {
            cfg.Left_playing = checkbox1.Checked;
        }

        private void checkbox2_Click(object sender, EventArgs e)
        {
            cfg.left_IA = checkbox2.Checked;
        }

        private void checkbox3_Click(object sender, EventArgs e)
        {
            cfg.left_Break = checkbox3.Checked;
        }

        private void checkbox4_Click(object sender, EventArgs e)
        {
            cfg.left_focus = checkbox4.Checked;
        }

        private void checkbox5_Click(object sender, EventArgs e)
        {
            cfg.Left_Slots = checkbox5.Checked;
        }

        private void SlotsBoolChange(object sender, EventArgs e)
        {
            Slots c = (Slots)sender;
            int index = Slist.IndexOf(c);
            cfg.Left_SlotList.SetBoolIndex(index, c.Checked);
        }

        private void SlotsKeyChange(object sender, EventArgs e)
        {
            Slots c = (Slots)sender;
            int index = Slist.IndexOf(c);
            cfg.Left_SlotList.setKeyIndex(index, c.Key);
        }
    }
}
