using System;
using System.Drawing;
using System.Windows.Forms;

namespace shit_temple
{
	// Token: 0x02000017 RID: 23
	internal class HexMini : Control
	{
		// Token: 0x06000097 RID: 151 RVA: 0x000028DD File Offset: 0x00000ADD
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this._State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x06000098 RID: 152 RVA: 0x000028F3 File Offset: 0x00000AF3
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this._State = MouseState.None;
			base.Invalidate();
		}

		// Token: 0x06000099 RID: 153 RVA: 0x00002909 File Offset: 0x00000B09
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this._State = MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x0600009A RID: 154 RVA: 0x0000291F File Offset: 0x00000B1F
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this._State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x0600009B RID: 155 RVA: 0x00002935 File Offset: 0x00000B35
		protected override void OnClick(EventArgs e)
		{
			base.OnClick(e);
			base.FindForm().WindowState = FormWindowState.Minimized;
		}

		// Token: 0x0600009C RID: 156 RVA: 0x0000289B File Offset: 0x00000A9B
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Size = new Size(12, 12);
		}

		// Token: 0x0600009D RID: 157 RVA: 0x000028B3 File Offset: 0x00000AB3
		public HexMini()
		{
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			base.Size = new Size(12, 12);
		}

		// Token: 0x0600009E RID: 158 RVA: 0x00003A94 File Offset: 0x00001C94
		protected override void OnPaint(PaintEventArgs e)
		{
			base.OnPaint(e);
			Graphics graphics = e.Graphics;
			graphics.Clear(Color.FromArgb(47, 51, 60));
			StringFormat stringFormat = new StringFormat();
			stringFormat.Alignment = StringAlignment.Center;
			stringFormat.LineAlignment = StringAlignment.Center;
			graphics.DrawString("0", new Font("Marlett", 11f), Brushes.White, new RectangleF(0f, 0f, (float)base.Width, (float)base.Height), stringFormat);
			MouseState state = this._State;
			if (state == MouseState.Down)
			{
				graphics.DrawString("0", new Font("Marlett", 11f), new SolidBrush(Color.FromArgb(40, Color.Black)), new RectangleF(0f, 0f, (float)base.Width, (float)base.Height), stringFormat);
			}
		}

		// Token: 0x04000034 RID: 52
		private MouseState _State;
	}
}
