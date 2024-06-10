using System;
using System.Drawing;
using System.Windows.Forms;

namespace GHOSTBYTES
{
	// Token: 0x0200002B RID: 43
	internal class FlatLabel : Label
	{
		// Token: 0x06000227 RID: 551 RVA: 0x00003CF0 File Offset: 0x00001EF0
		protected override void OnTextChanged(EventArgs e)
		{
			base.OnTextChanged(e);
			base.Invalidate();
		}

		// Token: 0x06000228 RID: 552 RVA: 0x0000A69C File Offset: 0x0000889C
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
