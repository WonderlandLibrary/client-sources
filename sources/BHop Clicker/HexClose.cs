using System;
using System.Drawing;
using System.Windows.Forms;

namespace shit_temple
{
	// Token: 0x02000016 RID: 22
	internal class HexClose : Control
	{
		// Token: 0x0600008F RID: 143 RVA: 0x00002834 File Offset: 0x00000A34
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this._State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x06000090 RID: 144 RVA: 0x0000284A File Offset: 0x00000A4A
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this._State = MouseState.None;
			base.Invalidate();
		}

		// Token: 0x06000091 RID: 145 RVA: 0x00002860 File Offset: 0x00000A60
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this._State = MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x06000092 RID: 146 RVA: 0x00002876 File Offset: 0x00000A76
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this._State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x06000093 RID: 147 RVA: 0x0000288C File Offset: 0x00000A8C
		protected override void OnClick(EventArgs e)
		{
			base.OnClick(e);
			Environment.Exit(0);
		}

		// Token: 0x06000094 RID: 148 RVA: 0x0000289B File Offset: 0x00000A9B
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Size = new Size(12, 12);
		}

		// Token: 0x06000095 RID: 149 RVA: 0x000028B3 File Offset: 0x00000AB3
		public HexClose()
		{
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			base.Size = new Size(12, 12);
		}

		// Token: 0x06000096 RID: 150 RVA: 0x000039C0 File Offset: 0x00001BC0
		protected override void OnPaint(PaintEventArgs e)
		{
			base.OnPaint(e);
			Graphics graphics = e.Graphics;
			graphics.Clear(Color.FromArgb(47, 51, 60));
			StringFormat stringFormat = new StringFormat();
			stringFormat.Alignment = StringAlignment.Center;
			stringFormat.LineAlignment = StringAlignment.Center;
			graphics.DrawString("r", new Font("Marlett", 11f), Brushes.White, new RectangleF(0f, 0f, (float)base.Width, (float)base.Height), stringFormat);
			MouseState state = this._State;
			if (state == MouseState.Down)
			{
				graphics.DrawString("r", new Font("Marlett", 11f), new SolidBrush(Color.FromArgb(40, Color.Black)), new RectangleF(0f, 0f, (float)base.Width, (float)base.Height), stringFormat);
			}
		}

		// Token: 0x04000033 RID: 51
		private MouseState _State;
	}
}
