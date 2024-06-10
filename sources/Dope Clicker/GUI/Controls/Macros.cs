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
    public partial class Macros : UserControl
    {
        public Macros()
        {
            InitializeComponent();
        }

        private void wtapSlider_Scroll(object sender, ScrollEventArgs e)
        {
            float value = (int)wtapSlider.Value / 10f;
            wtapcount.Text = value.ToString("0.00" + "ms");
        }

        private void stapSlider_Scroll(object sender, ScrollEventArgs e)
        {
            float value = (int)stapSlider.Value / 10f;
            stapcount.Text = value.ToString("0.00" + "ms");
        }
    }
}
