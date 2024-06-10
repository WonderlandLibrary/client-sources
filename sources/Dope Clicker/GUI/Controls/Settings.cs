using System;
using System.Drawing;
using Guna.UI2.WinForms;
using System.Windows.Forms;

namespace AnyDesk.GUI.Controls
{
    public partial class Settings : UserControl
    {
        int _red, _green, _blue;

        public Settings()
        {
            InitializeComponent();
        }

        private void colorSlider1_Scroll(object sender, ScrollEventArgs e)
        {
            Form instance = this.ParentForm;
            opc.Text = colorSlider1.Value.ToString();
            if (colorSlider1.Value == 10)
            {
                instance.Opacity = 1;
            }
            else { instance.Opacity = (double)(colorSlider1.Value / 10); }
        }
    }
}
