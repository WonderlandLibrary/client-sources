using System;
using System.Drawing;
using System.Windows.Forms;

namespace Meth
{
	// Token: 0x0200002C RID: 44
	internal class FlatLabel : Label
	{
		// Token: 0x06000318 RID: 792 RVA: 0x00015314 File Offset: 0x00013514
		protected override void OnTextChanged(EventArgs e)
		{
			base.OnTextChanged(e);
			base.Invalidate();
		}

		// Token: 0x06000319 RID: 793 RVA: 0x00015324 File Offset: 0x00013524
		public FlatLabel()
		{
			base.SetStyle(ControlStyles.SupportsTransparentBackColor, true);
			this.Font = new Font("Segoe UI", 8f);
			this.ForeColor = Color.White;
			this.BackColor = Color.Transparent;
			this.Text = this.Text;
		}
	}
}
