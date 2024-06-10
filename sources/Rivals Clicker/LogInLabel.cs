using System;
using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;

namespace svchost
{
	// Token: 0x0200001D RID: 29
	public class LogInLabel : Label
	{
		// Token: 0x170000AF RID: 175
		// (get) Token: 0x060001FB RID: 507 RVA: 0x0000D290 File Offset: 0x0000B490
		// (set) Token: 0x060001FC RID: 508 RVA: 0x00002E1B File Offset: 0x0000101B
		[Category("Colours")]
		public Color FontColour
		{
			get
			{
				return this._FontColour;
			}
			set
			{
				this._FontColour = value;
			}
		}

		// Token: 0x060001FD RID: 509 RVA: 0x00002E25 File Offset: 0x00001025
		protected override void OnTextChanged(EventArgs e)
		{
			base.OnTextChanged(e);
			base.Invalidate();
		}

		// Token: 0x060001FE RID: 510 RVA: 0x0000D2A8 File Offset: 0x0000B4A8
		public LogInLabel()
		{
			this._FontColour = Color.FromArgb(255, 255, 255);
			base.SetStyle(ControlStyles.SupportsTransparentBackColor, true);
			this.Font = new Font("Segoe UI", 9f);
			this.ForeColor = this._FontColour;
			this.BackColor = Color.Transparent;
			this.Text = this.Text;
		}

		// Token: 0x040000C9 RID: 201
		private Color _FontColour;
	}
}
