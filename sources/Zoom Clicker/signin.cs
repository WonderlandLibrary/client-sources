using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace Zoom
{
    public partial class signin : Form
    {
        public signin()
        {
            InitializeComponent();
        }

        private void label1_Click(object sender, EventArgs e)
        {
            main rr = new main();
            rr.Show();
            this.Hide();
        }

        private void guna2Button4_Click(object sender, EventArgs e)
        {
            ZoomCloudMeetings rr = new ZoomCloudMeetings();
            rr.Show();
            this.Hide();
        }

        private void linkLabel1_LinkClicked(object sender, LinkLabelLinkClickedEventArgs e)
        {
            main rr = new main();
            rr.Show();
            this.Hide();
        }
    }
}
