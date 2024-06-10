using Dope.Theme;
using Dope.utils;
using System;
using System.Collections.Generic;
using System.Windows.Forms;

namespace Dope.views
{
    public partial class Right : Form
    {
        Config cfg;
        Main parent;
        List<Slots> Slist;
        public Right(Config c, Main m)
        {
            InitializeComponent();
            cfg = c;
            parent = m;
            Slist = new List<Slots>() { S1, S2, S3, S4, S5, S6, S7, S8, S9 };
        }

        private void LeftSwitch_Click(object sender, EventArgs e)
        {
            parent.ChangeViews(0);
        }

        private void RightToggle_Click(object sender, EventArgs e)
        {
            cfg.right_Click = RightToggle.Checked;
        }

        private void Rbind_TextChanged(object sender, EventArgs e)
        {
            cfg.right_Key = Rbind.Key;
        }

        private void RMax_Scroll(object sender)
        {
            if (RMax.Value <= Rmin.Value)
            {
                RMax.Value = Rmin.Value + 1;
            }
            cfg.right_Max = RMax.RawValue();
        }

        private void Rmin_Scroll(object sender)
        {
            if (Rmin.Value >= RMax.Value)
            {
                Rmin.Value = RMax.Value - 1;
            }
            cfg.right_Min = Rmin.RawValue();
        }

        private void checkbox1_Click(object sender, EventArgs e)
        {
            cfg.right_playing = checkbox1.Checked;
        }

        private void checkbox2_Click(object sender, EventArgs e)
        {
            cfg.right_IA = checkbox2.Checked;
        }

        private void checkbox4_Click(object sender, EventArgs e)
        {
            cfg.right_focus = checkbox4.Checked;
        }

        private void checkbox5_Click(object sender, EventArgs e)
        {
            cfg.right_Slots = checkbox5.Checked;
        }

        private void slots2_Click(object sender, EventArgs e)
        {
            Slots c = (Slots)sender;
            int index = Slist.IndexOf(c);
            cfg.right_SlotList.setKeyIndex(index, c.Key);
        }
    }
}
