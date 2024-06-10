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
    public partial class Sounds : UserControl
    {
        public Sounds()
        {
            InitializeComponent();
        }

        private void volume_Scroll(object sender, ScrollEventArgs e)
        {
            volumeCount.Text = volume.Value.ToString() + "%";
        }

        private void gunaLabel9_Click(object sender, EventArgs e)
        {

        }
    }
}
