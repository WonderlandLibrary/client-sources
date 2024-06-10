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
    public partial class ZoomCloudMeetings : Form
    {
        public ZoomCloudMeetings()
        {
            InitializeComponent();
        }

        private void guna2Button2_Click(object sender, EventArgs e)
        {
            Application.Exit();

        }

        private void guna2Button1_Click(object sender, EventArgs e)
        {
            if (WindowState == FormWindowState.Normal)
            {
                WindowState = FormWindowState.Minimized;
            }
        }

        private void guna2Button3_Click(object sender, EventArgs e)
        {
            
        }

        private void ZoomCloudMeetings_Load(object sender, EventArgs e)
        {

        }

        private void guna2Button4_Click(object sender, EventArgs e)
        {
            signin rr = new signin();
            rr.Show();
            this.Hide();
        }
    }
}
