using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace AnyDesk.GUI.Controls
{
    public partial class Right : UserControl
    {
        public Right()
        {
            InitializeComponent();
        }

        private void cpsSlider_Scroll(object sender, ScrollEventArgs e)
        {
            float value = (int)cpsSlider.Value / 10f;
            cpsCount.Text = value.ToString("0.00");
        }

        private void jitterSlider_Scroll(object sender, ScrollEventArgs e)
        {
            float value = (int)jitterSlider.Value / 10f;
            jitterCount.Text = value.ToString("0.00");
        }

        private void guna2CheckBox2_CheckedChanged(object sender, EventArgs e)
        {
            if (guna2CheckBox2.Checked == true)
            {
                cpsSlider.Maximum = 400;
            }
            else
            {
                if (cpsSlider.Value > 200)
                {
                    cpsSlider.Value = 200;
                }
                cpsSlider.Maximum = 200;
                float value = (int)cpsSlider.Value / 10f;
                cpsCount.Text = value.ToString("0.00");
            }
        }
    }
}
