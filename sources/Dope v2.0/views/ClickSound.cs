using Dope.utils;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Dope.views
{
    public partial class ClickSound : Form
    {
        Config cfg;
        Main parent;
        public ClickSound(Config c, Main m)
        {
            InitializeComponent();
            cfg = c;
            parent = m;
        }

        private void switch1_Click(object sender, EventArgs e)
        {
            cfg.Sound = switch1.Checked;
        }

        private void bind1_TextChanged(object sender, EventArgs e)
        {
            cfg.Sound_Key = bind1.Key;
        }

        private void slider1_Scroll(object sender)
        {
            cfg.Sound_Volume = (int)slider1.RawValue();
            Utils.UpdateVolume(cfg.Sound_Volume);
        }

        private void combo1_SelectedIndexChanged(object sender, EventArgs e)
        {
            cfg.Sound_preset = combo1.GetItemText(combo1.SelectedItem);
        }
    }
}
