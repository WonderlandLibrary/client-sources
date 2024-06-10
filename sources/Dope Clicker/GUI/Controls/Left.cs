using System;
using System.Threading;
using System.Windows.Forms;
using AnyDesk.GUI.Helpers;

namespace AnyDesk.GUI.Controls
{
    public partial class Left : UserControl
    {
        Helper _guiHelper = new Helper();

        public Left()
        {
            InitializeComponent();
        }

        public void SliderAnimation(ColorSlider.ColorSlider slider)
        {
            slider.Value = 0;

            for (int i = 0; i < 50; i++)
            {
                /// <summary>
                /// Ignores any errors
                /// </summary>
                try
                {
                    slider.Value += 2;
                    Thread.Sleep(15);
                }
                catch { } 
            }
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
