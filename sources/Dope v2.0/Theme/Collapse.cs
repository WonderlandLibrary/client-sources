using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;
using static Dope.Theme.ThemeUtils;

namespace Dope.Theme
{
    class Collapse : Control
    {
        private MouseState State = MouseState.None;
        private bool Checked_;

        public Collapse()
        {
            SetStyle(ControlStyles.AllPaintingInWmPaint | ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.OptimizedDoubleBuffer, true);
            DoubleBuffered = true;
        }

        public bool Checked
        {
            get { return Checked_; }
            set
            {
                Checked_ = value;
                Invalidate();
            }
        }

        protected override void OnClick(EventArgs e)
        {
            Checked = !Checked;
            base.OnClick(e);
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

        protected override void OnResize(EventArgs e)
        {
            base.OnResize(e);
            Height = Width;
        }

        protected override void OnPaint(PaintEventArgs e)
        {
            ///basic setup
            Bitmap B = new Bitmap(Width, Height);
            Graphics G = Graphics.FromImage(B);
            G.SmoothingMode = SmoothingMode.HighQuality;
            G.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
            ///Render Collapse
            G.DrawPath(new Pen(Color.FromArgb(255,220,220,220), 2f), DrawCollapse(new Rectangle(0,0,Width,Height),Checked));
            if (State == MouseState.Over)
            {
                G.FillRectangle(new SolidBrush(Color.FromArgb(20, 255, 255, 255)), new Rectangle(0, 0, Width, Height));
            }
            else if (State == MouseState.Down)
            {
                G.FillRectangle(new SolidBrush(Color.FromArgb(40, 255, 255, 255)), new Rectangle(0, 0, Width, Height));
            }
            base.OnPaint(e);
            G.Dispose();
            e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
            e.Graphics.DrawImageUnscaled(B, 0, 0);
            B.Dispose();
        }
    }
}
