using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace daidesign
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        
        public void off()
        {
            Color forecolor = Color.FromArgb(122, 123, 123);
            foreach(Control ctrls in this.Controls)
            {
                if(ctrls.Name == "label1" || ctrls.Name == "label2" || ctrls.Name == "label3")
                {
                    ctrls.ForeColor = forecolor;
                }
                else if(ctrls.Name.Contains("guna2Separator"))
                {
                    ctrls.Visible = false;
                }
            }
        }
        private void label1_Click(object sender, EventArgs e)
        {
            off();
            guna2Transition1.HideSync(guna2VSeparator1);
            clicker1.BringToFront();
            label1.ForeColor = Color.White;
            guna2Separator1.Visible = true;
            guna2VSeparator1.Size = new Size(10, 30);
            guna2Transition1.ShowSync(guna2VSeparator1);
        }

        private void clicker1_Load(object sender, EventArgs e)
        {

        }

        private void label2_Click(object sender, EventArgs e)
        {
            off();
            guna2Transition1.HideSync(guna2VSeparator1);
            cheats1.BringToFront();
            label2.ForeColor = Color.White;
            guna2Separator2.Visible = true;
            guna2VSeparator1.Size = new Size(10, 97);
            guna2Transition1.ShowSync(guna2VSeparator1);
        }

        private void label3_Click(object sender, EventArgs e)
        {
            off();
            guna2Transition1.HideSync(guna2VSeparator1);
            macro1.BringToFront();
            label3.ForeColor = Color.White;
            guna2Separator3.Visible = true;
            guna2VSeparator1.Size = new Size(10, 164);
            guna2Transition1.ShowSync(guna2VSeparator1);
        }

        private void macro1_Load(object sender, EventArgs e)
        {

        }
    }
}
