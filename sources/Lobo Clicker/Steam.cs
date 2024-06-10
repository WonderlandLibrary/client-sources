using System;
using System.ComponentModel;
using System.Drawing;
using System.Threading;
using System.Windows.Forms;
using Bunifu.Framework.UI;
using Guna.UI.WinForms;
using WindowsFormsControlLibrary1;

namespace Steam_Client_Bootstrapper
{
	// Token: 0x02000005 RID: 5
	public partial class Steam : Form
	{
		// Token: 0x0600001C RID: 28 RVA: 0x0000210F File Offset: 0x0000030F
		public Steam()
		{
			this.InitializeComponent();
		}

		// Token: 0x0600001D RID: 29 RVA: 0x0000484C File Offset: 0x00002A4C
		private void bunifuFlatButton1_Click(object sender, EventArgs e)
		{
			if (this.txtuser.Text == "Expection" && this.txtsenha.Text == "Cracked")
			{
				MessageBox.Show(" Bem Vindo Ao MELHOR CLICKER BRKKK", "Logado", MessageBoxButtons.OK, MessageBoxIcon.Asterisk);
				this.cba = new Thread(new ThreadStart(this.novoform16));
				this.cba.SetApartmentState(ApartmentState.STA);
				this.cba.Start();
				base.Close();
			}
		}

		// Token: 0x0600001E RID: 30 RVA: 0x00002127 File Offset: 0x00000327
		private void novoform16()
		{
			Application.Run(new Form3());
		}

		// Token: 0x0600001F RID: 31 RVA: 0x000020C7 File Offset: 0x000002C7
		private void label6_Click(object sender, EventArgs e)
		{
			Application.Exit();
		}

		// Token: 0x06000020 RID: 32 RVA: 0x000020D0 File Offset: 0x000002D0
		private void label8_Click(object sender, EventArgs e)
		{
			base.WindowState = FormWindowState.Minimized;
		}

		// Token: 0x06000021 RID: 33 RVA: 0x000020C7 File Offset: 0x000002C7
		private void bunifuFlatButton2_Click(object sender, EventArgs e)
		{
			Application.Exit();
		}

		// Token: 0x06000022 RID: 34 RVA: 0x00002135 File Offset: 0x00000335
		private void bunifuFlatButton3_Click(object sender, EventArgs e)
		{
			this.ABK = new Thread(new ThreadStart(this.novoform220));
			this.ABK.SetApartmentState(ApartmentState.STA);
			this.ABK.Start();
			base.Close();
		}

		// Token: 0x06000023 RID: 35 RVA: 0x0000216F File Offset: 0x0000036F
		private void novoform220()
		{
			Application.Run(new SteamHelp());
		}

		// Token: 0x06000024 RID: 36 RVA: 0x0000217D File Offset: 0x0000037D
		private void bunifuFlatButton5_Click(object sender, EventArgs e)
		{
			this.JUR = new Thread(new ThreadStart(this.novoform15));
			this.JUR.SetApartmentState(ApartmentState.STA);
			this.JUR.Start();
			base.Close();
		}

		// Token: 0x06000025 RID: 37 RVA: 0x0000216F File Offset: 0x0000036F
		private void novoform15()
		{
			Application.Run(new SteamHelp());
		}

		// Token: 0x0400002D RID: 45
		private Thread cba;

		// Token: 0x0400002E RID: 46
		private Thread ABK;

		// Token: 0x0400002F RID: 47
		private Thread JUR;
	}
}
