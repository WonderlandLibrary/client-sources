using System;
using System.Drawing;
using System.Drawing.Text;
using System.Windows.Forms;

namespace svchost
{
	// Token: 0x02000027 RID: 39
	public class LogInContextMenu : ContextMenuStrip
	{
		// Token: 0x170000EF RID: 239
		// (get) Token: 0x06000296 RID: 662 RVA: 0x0000F4F0 File Offset: 0x0000D6F0
		// (set) Token: 0x06000297 RID: 663 RVA: 0x00003237 File Offset: 0x00001437
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

		// Token: 0x06000298 RID: 664 RVA: 0x0000F508 File Offset: 0x0000D708
		public LogInContextMenu()
		{
			this._FontColour = Color.FromArgb(55, 255, 255);
			base.Renderer = new ToolStripProfessionalRenderer(new LogInColourTable());
			base.ShowCheckMargin = false;
			base.ShowImageMargin = false;
			base.ForeColor = Color.FromArgb(255, 255, 255);
		}

		// Token: 0x06000299 RID: 665 RVA: 0x00003241 File Offset: 0x00001441
		protected override void OnPaint(PaintEventArgs e)
		{
			e.Graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			base.OnPaint(e);
		}

		// Token: 0x04000104 RID: 260
		private Color _FontColour;
	}
}
