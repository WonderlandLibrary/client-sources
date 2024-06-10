using System;
using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;
using Bunifu.Framework.UI;
using Guna.UI.WinForms;

namespace Steam_Client_Bootstrapper
{
	// Token: 0x02000004 RID: 4
	public partial class Form3 : Form
	{
		// Token: 0x06000013 RID: 19 RVA: 0x000020AF File Offset: 0x000002AF
		public Form3()
		{
			this.InitializeComponent();
		}

		// Token: 0x06000014 RID: 20 RVA: 0x000020C7 File Offset: 0x000002C7
		private void label1_Click(object sender, EventArgs e)
		{
			Application.Exit();
		}

		// Token: 0x06000015 RID: 21 RVA: 0x000020D0 File Offset: 0x000002D0
		private void label2_Click(object sender, EventArgs e)
		{
			base.WindowState = FormWindowState.Minimized;
		}

		// Token: 0x06000016 RID: 22 RVA: 0x00003688 File Offset: 0x00001888
		private void AbrirFormInPanel(object Formhijo)
		{
			bool flag = this.panel3.Controls.Count > 0;
			if (flag)
			{
				this.panel3.Controls.RemoveAt(0);
			}
			Form form = Formhijo as Form;
			form.TopLevel = false;
			form.Dock = DockStyle.Fill;
			this.panel3.Controls.Add(form);
			this.panel3.Tag = form;
			form.Show();
		}

		// Token: 0x06000017 RID: 23 RVA: 0x000020DB File Offset: 0x000002DB
		private void btb1_Click(object sender, EventArgs e)
		{
			this.AbrirFormInPanel(new Clicker());
		}

		// Token: 0x06000018 RID: 24 RVA: 0x000020EA File Offset: 0x000002EA
		private void btb3_Click(object sender, EventArgs e)
		{
			this.AbrirFormInPanel(new Config());
		}

		// Token: 0x06000019 RID: 25 RVA: 0x000020F9 File Offset: 0x000002F9
		private void gunaAdvenceTileButton1_Click(object sender, EventArgs e)
		{
			MessageBox.Show(" Usuário logado: Fearth", "Log", MessageBoxButtons.OK, MessageBoxIcon.Asterisk);
		}
	}
}
