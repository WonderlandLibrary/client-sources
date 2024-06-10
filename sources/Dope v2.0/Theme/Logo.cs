using System;
using System.Drawing;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Dope.Theme
{
    class Logo : PictureBox
    {
        public Image Small_;
        public Image Small
        { 
            get { return Small_; }
            set
            {
                Small_ = value;
            } 
        }
        public Image Normal_;
        public Image Normal
        {
            get { return Normal_; }
            set
            {
                Normal_ = value;
                Image = Normal_;
                Invalidate();
            }
        }

        private async void Shake()
        {
            for (int i = 1; i < 8; i++)
            {
                await Task.Delay(1);
                Location = new Point(Location.X, Location.Y + 1);
                Size = new Size(Size.Width, Size.Height - 1);
            }
            for (int i = 1; i < 8; i++)
            {
                await Task.Delay(1);
                Location = new Point(Location.X, Location.Y - 1);
                Size = new Size(Size.Width, Size.Height + 1);
            }
        }

        protected override void OnResize(EventArgs e)
        {
            base.OnResize(e);
            if (Width >= 100)
            {
                Image = Normal;
            }
            else
            {
                Image = Small;
            }
            Invalidate();
        }

        protected override void OnClick(EventArgs e)
        {
            Shake();
            base.OnClick(e);
        }

    }
}
