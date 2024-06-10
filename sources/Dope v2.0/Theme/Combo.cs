using System;
using System.Collections.Generic;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using static Dope.Theme.ThemeUtils;

namespace Dope.Theme
{
    class Combo : ComboBox
    {

        public Combo()
        {
			SetStyle(ControlStyles.AllPaintingInWmPaint | ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.OptimizedDoubleBuffer, true);
			DoubleBuffered = true;
			DrawMode = DrawMode.OwnerDrawFixed;
			DropDownStyle = ComboBoxStyle.DropDownList;
			StartIndex = 0;
		}

		private int x;
		private int y;
		private int _StartIndex = 0;
		private MouseState State = MouseState.None;

		public int StartIndex
		{
			get { return _StartIndex; }
			set
			{
				_StartIndex = value;
				try
				{
					base.SelectedIndex = value;
				}
				catch
				{
				}
				Invalidate();
			}
		}

		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			State = MouseState.Down;
			Invalidate();
		}

		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			State = MouseState.Over;
			Invalidate();
		}

		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			State = MouseState.Over;
			Invalidate();
		}

		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			State = MouseState.None;
			Invalidate();
		}

		protected override void OnMouseMove(MouseEventArgs e)
		{
			base.OnMouseMove(e);
			x = e.Location.X;
			y = e.Location.Y;
			Invalidate();
			if (e.X < Width - 41)
				Cursor = Cursors.IBeam;
			else
				Cursor = Cursors.Hand;
		}

		protected override void OnDrawItem(DrawItemEventArgs e)
		{
			base.OnDrawItem(e);
			if (e.Index < 0)
				return;
			e.DrawBackground();
			e.DrawFocusRectangle();

			e.Graphics.SmoothingMode = SmoothingMode.HighQuality;
			e.Graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
			e.Graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;

			//-- Text
			e.Graphics.DrawString(base.GetItemText(base.Items[e.Index]), Font, Brushes.White, new Rectangle(e.Bounds.X + 2, e.Bounds.Y + 2, e.Bounds.Width, e.Bounds.Height));


			e.Graphics.Dispose();
		}

		protected override void OnClick(EventArgs e)
		{
			base.OnClick(e);
			Invalidate();
		}

        protected override void OnResize(EventArgs e)
        {
            base.OnResize(e);
			Height = 18;
        }

        protected override void OnPaint(PaintEventArgs e)
        {
			Bitmap B = new Bitmap(Width, Height);
			Graphics G = Graphics.FromImage(B);
			G.SmoothingMode = SmoothingMode.HighQuality;
			G.PixelOffsetMode = PixelOffsetMode.HighQuality;
			G.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			G.Clear(FindForm().BackColor);

			int W = Width - 1;
			int H = Height - 1;

			//base
			G.DrawPath(new Pen(Color.FromArgb(255,220,220,220)), RoundRec(new Rectangle(0,0,W,H), 5));

			G.DrawString(DroppedDown ? "-" : "+", Font, new SolidBrush(ForeColor), (W - 20), 4);

			G.DrawString(Text, Font, new SolidBrush(ForeColor), 3,3);

			base.OnPaint(e);
			G.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(B, 0, 0);
			B.Dispose();
		}
    }
}