using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Dope.Theme
{
    public partial class Side : UserControl
    {
        int MinCol_ = 100;
        int MaxCol_ = 200;
        int index_;
        List<button> buttons = new List<button>();

        public Side()
        {
            InitializeComponent();
            foreach (Control c in Controls)
            {
                if (c is button)
                    buttons.Add((button)c);
            }
            buttons.Reverse();
        }

        [Browsable(true)]
        [Category("Action")]
        [Description("When index change")]
        public event EventHandler IndexChange;

        #region Settings
        public int MinCol
        {
            get{ return MinCol_; }
            set
            {
                MinCol_ = value;
            }
        }

        public int MaxCol
        {
            get { return MaxCol_; }
            set
            {
                MaxCol_ = value;
            }
        }

        public int Index
        {
            get { return index_; }
            set
            {
                index_ = value;
            }
        }
        #endregion

        #region event
        private void collapse1_Click(object sender, EventArgs e)
        {
            if (collapse1.Checked)
            {
                Task.Run(() =>
                {
                    while (Width > MinCol)
                    {
                        Width -= 2;
                        Refresh();
                    }
                });
            }
            else
            {
                Task.Run(() =>
                {
                    while (Width < MaxCol)
                    {
                        Width += 2;
                        Refresh();
                    }
                });
            }
        }

        private void Side_Resize(object sender, EventArgs e)
        {
            collapse1.Location = new Point(collapse1.Location.X, Size.Height - collapse1.Height);
        }

        private void IndexSwitch(object sender, EventArgs e)
        {
            button b = (button)sender;
            Index = buttons.IndexOf(b);

            IndexChange?.Invoke(this,e);
        }
        #endregion
    }
}
