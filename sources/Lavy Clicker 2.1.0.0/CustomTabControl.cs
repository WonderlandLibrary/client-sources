using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Lavy
{
    public partial class TabControlWithoutHeader : TabControl
    {
        public TabControlWithoutHeader()
        {
            if (!this.DesignMode) this.Multiline = true;

            this.ItemSize = new Size(1, 1);
        }

        protected override void WndProc(ref Message m)
        {
            if (m.Msg == 0x1328 && !this.DesignMode)
                m.Result = new IntPtr(1);
            else
                base.WndProc(ref m);
        }
    }
}
