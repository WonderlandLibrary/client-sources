using System;
using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;
using FastPot.Properties;

namespace FastPot
{
	// Token: 0x02000002 RID: 2
	public partial class MainForm : Form
	{
		// Token: 0x06000001 RID: 1 RVA: 0x00002050 File Offset: 0x00000250
		public MainForm()
		{
			MainForm.MainFr = this;
			this.InitializeComponent();
			this.button1.Click += this.OnClick;
			this.button2.Click += this.OnClick;
			this.button3.Click += this.OnClick;
			this.button4.Click += this.OnClick;
			this.button5.Click += this.OnClick;
			this.button6.Click += this.OnClick;
			this.button1.KeyUp += this.OnRelease;
			this.button2.KeyUp += this.OnRelease;
			this.button3.KeyUp += this.OnRelease;
			this.button4.KeyUp += this.OnRelease;
			this.button5.KeyUp += this.OnRelease;
			this.button6.KeyUp += this.OnRelease;
		}

		// Token: 0x06000002 RID: 2 RVA: 0x0000219C File Offset: 0x0000039C
		private void OnClick(object sender, EventArgs e)
		{
			Button button = sender as Button;
			button.Text = "...";
			bool flag = button.Name == "button5";
			if (flag)
			{
				this.ThrowPot.IsReadyToPot = true;
			}
		}

		// Token: 0x06000003 RID: 3 RVA: 0x000021DE File Offset: 0x000003DE
		private void MainForm_Load(object sender, EventArgs e)
		{
			this.ThrowPot = new ThrowPot();
		}

		// Token: 0x06000004 RID: 4 RVA: 0x000021EC File Offset: 0x000003EC
		private void button1_KeyDown(object sender, KeyEventArgs e)
		{
			this.ThrowPot.KeyBind = Utility.ConvertToBindable(e);
			this.button1.Text = (this.ThrowPot.KeyBind.ToString().StartsWith("KEY_") ? string.Format("TOGGLE KEY [{0}]", this.ThrowPot.KeyBind.ToString().Substring(4)) : string.Format("TOGGLE KEY [{0}]", this.ThrowPot.KeyBind.ToString()));
		}

		// Token: 0x06000005 RID: 5 RVA: 0x0000228C File Offset: 0x0000048C
		private void button5_KeyDown(object sender, KeyEventArgs e)
		{
			this.ThrowPot.ThrowPotKey = Utility.ConvertToBindable(e);
			this.button5.Text = (this.ThrowPot.ThrowPotKey.ToString().StartsWith("KEY_") ? string.Format("THROW POT [{0}]", this.ThrowPot.ThrowPotKey.ToString().Substring(4)) : string.Format("THROW POT [{0}]", this.ThrowPot.ThrowPotKey.ToString()));
		}

		// Token: 0x06000006 RID: 6 RVA: 0x0000232B File Offset: 0x0000052B
		private void OnRelease(object sender, KeyEventArgs e)
		{
			this.ThrowPot.IsReadyToPot = false;
		}

		// Token: 0x06000007 RID: 7 RVA: 0x0000233C File Offset: 0x0000053C
		private void button2_KeyDown(object sender, KeyEventArgs e)
		{
			this.ThrowPot.InventoryKey = Utility.ConvertToBindable(e);
			this.button2.Text = (this.ThrowPot.InventoryKey.ToString().StartsWith("KEY_") ? string.Format("INVENTORY KEY [{0}]", this.ThrowPot.InventoryKey.ToString().Substring(4)) : string.Format("INVENTORY KEY [{0}]", this.ThrowPot.InventoryKey.ToString()));
		}

		// Token: 0x06000008 RID: 8 RVA: 0x000023DC File Offset: 0x000005DC
		private void button4_KeyDown(object sender, KeyEventArgs e)
		{
			this.ThrowPot.FirstPot = Utility.ConvertToBindable(e);
			this.button4.Text = (this.ThrowPot.FirstPot.ToString().StartsWith("KEY_") ? string.Format("FIRST POTION [{0}]", this.ThrowPot.FirstPot.ToString().Substring(4)) : string.Format("FIRST POTION [{0}]", this.ThrowPot.FirstPot.ToString()));
		}

		// Token: 0x06000009 RID: 9 RVA: 0x0000247C File Offset: 0x0000067C
		private void button3_KeyDown(object sender, KeyEventArgs e)
		{
			this.ThrowPot.LastPot = Utility.ConvertToBindable(e);
			this.button3.Text = (this.ThrowPot.LastPot.ToString().StartsWith("KEY_") ? string.Format("LAST POTION [{0}]", this.ThrowPot.LastPot.ToString().Substring(4)) : string.Format("LAST POTION [{0}]", this.ThrowPot.LastPot.ToString()));
		}

		// Token: 0x0600000A RID: 10 RVA: 0x0000251C File Offset: 0x0000071C
		private void button6_KeyDown(object sender, KeyEventArgs e)
		{
			this.ThrowPot.Sword = Utility.ConvertToBindable(e);
			this.button6.Text = (this.ThrowPot.Sword.ToString().StartsWith("KEY_") ? string.Format("SWORD [{0}]", this.ThrowPot.Sword.ToString().Substring(4)) : string.Format("SWORD [{0}]", this.ThrowPot.Sword.ToString()));
		}

		// Token: 0x04000001 RID: 1
		public static MainForm MainFr;

		// Token: 0x04000002 RID: 2
		private ThrowPot ThrowPot;
	}
}
