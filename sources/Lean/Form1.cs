using System;
using System.ComponentModel;
using System.Drawing;
using System.Net;
using System.Threading;
using System.Windows.Forms;
using Bunifu.Framework.UI;
using oxybitch.Utils;

namespace oxybitch
{
	// Token: 0x02000003 RID: 3
	public partial class Form1 : Form
	{
		// Token: 0x06000004 RID: 4 RVA: 0x000020ED File Offset: 0x000002ED
		public Form1()
		{
			this.InitializeComponent();
		}

		// Token: 0x06000005 RID: 5 RVA: 0x00002110 File Offset: 0x00000310
		private void panel1_Paint(object sender, PaintEventArgs e)
		{
		}

		// Token: 0x06000006 RID: 6 RVA: 0x00002110 File Offset: 0x00000310
		private void panel2_Paint(object sender, PaintEventArgs e)
		{
		}

		// Token: 0x06000007 RID: 7 RVA: 0x00002110 File Offset: 0x00000310
		private void bunifuCustomLabel4_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000008 RID: 8 RVA: 0x00002114 File Offset: 0x00000314
		private void bunifuFlatButton1_Click(object sender, EventArgs e)
		{
			string b = "CheatReleaseItaly";
			string b2 = "cracked";
			bool flag = this.textBox1.Text == b && this.textBox2.Text == b2;
			if (flag)
			{
				MessageBox.Show("Logged.");
				base.Hide();
				Form2 form = new Form2();
				form.Closed += delegate(object s, EventArgs args)
				{
					base.Close();
				};
				form.Show();
			}
			else
			{
				MessageBox.Show("Errou.");
				Application.Exit();
			}
		}

		// Token: 0x06000009 RID: 9 RVA: 0x000021A2 File Offset: 0x000003A2
		private void bunifuCustomLabel4_Click_1(object sender, EventArgs e)
		{
			
		}

		// Token: 0x0600000A RID: 10 RVA: 0x000021D2 File Offset: 0x000003D2
		private void bunifuImageButton2_Click(object sender, EventArgs e)
		{
			Environment.Exit(0);
		}

		// Token: 0x0600000B RID: 11 RVA: 0x000021DC File Offset: 0x000003DC
		private void bunifuImageButton3_Click(object sender, EventArgs e)
		{
			base.WindowState = FormWindowState.Minimized;
		}

		// Token: 0x04000001 RID: 1
		private static WebClient wc2 = new WebClient();

		// Token: 0x04000002 RID: 2
		private static WebClient wc = new WebClient();

		// Token: 0x04000003 RID: 3
		private static WebProxy wp = new WebProxy();

        private void BunifuCustomLabel6_Click(object sender, EventArgs e)
        {
            Environment.Exit(0);
        }
    }
}
