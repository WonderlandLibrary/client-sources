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
    public partial class Jitter : Form
    {
        Config cfg;
        Main parent;
        public Jitter(Config c, Main m)
        {
            InitializeComponent();
            cfg = c;
            parent = m;
        }

        private void JitterToggle_Click(object sender, EventArgs e)
        {
            cfg.Jitter = JitterToggle.Checked;
        }

        private void JBind_TextChanged(object sender, EventArgs e)
        {
            cfg.Jitter_Key = JBind.Key;
        }

        private void JStrengh_Scroll(object sender)
        {
            cfg.Jitter_strengh = (int)JStrengh.RawValue();
        }
    }
}
