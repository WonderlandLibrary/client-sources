using Dope.utils;
using System;
using System.Windows.Forms;

namespace Dope.views
{
    public partial class Settings : Form
    {
        Config cfg;
        Main parent;
        public Settings(Config c, Main m)
        {
            InitializeComponent();
            cfg = c;
            parent = m;
        }

        private void switch1_Click(object sender, EventArgs e)
        {
            cfg.Hide = switch1.Checked;
            if (cfg.Hide_Key != 0 && switch1.Checked)
            {
                ActiveForm.Opacity = Convert.ToInt32(!cfg.Hide);
            }
            else
            {
                MessageBox.Show("Add a bind before");
                switch1.Checked = false;
            }
        }

        private void bind1_TextChanged(object sender, EventArgs e)
        {
            cfg.Hide_Key = bind1.Key;
        }

        private void checkbox1_Click(object sender, EventArgs e)
        {
            cfg.AlwaysOnTop = checkbox1.Checked;
            ActiveForm.TopMost = cfg.AlwaysOnTop;
        }
    }
}
