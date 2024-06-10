using ac.ac;
using Bunifu.Framework.UI;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace _418418481
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
            MaximizeBox = false;
        }

        private Autoclicker a;

        private void Form1_Load(object sender, EventArgs e)
        {
            this.a = new Autoclicker();
            this.a.en = this.enabledtag;
        }

        private void cpsslider_ValueChanged(object sender, EventArgs e)
        {
            BunifuTrackbar trackBar = (BunifuTrackbar)sender;
            this.cpsdis.Text = this.cpsslider.Value.ToString();
            this.a.cps = this.cpsslider.Value;
        }

        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {
            this.a.stop();
        }

        private void enabledtag_Click(object sender, EventArgs e)
        {

        }
    }
}
