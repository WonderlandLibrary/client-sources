using System;
using System.Drawing;
using System.Windows.Forms;

namespace Xh0kO1ZCmA
{
	internal class FlatLabel : Label
	{
		public FlatLabel()
		{
			base.SetStyle(ControlStyles.SupportsTransparentBackColor, true);
			this.Font = new System.Drawing.Font("Segoe UI", 8f);
			this.ForeColor = Color.White;
			this.BackColor = Color.Transparent;
			this.Text = this.Text;
		}

		protected override void OnTextChanged(EventArgs e)
		{
			base.OnTextChanged(e);
			base.Invalidate();
		}
	}
}