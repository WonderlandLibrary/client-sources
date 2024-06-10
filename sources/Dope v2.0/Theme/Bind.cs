using System;
using System.ComponentModel;
using System.Windows.Forms;

namespace Dope.Theme
{

    public class Bind : Label
    {
        int Key_;

        public int Key
        {
            get { return Key_; }
            set
            {
                Key_ = value;
            }
        }

        [Browsable(true)]
        [Category("Action")]
        [Description("When Key change")]
        public event EventHandler KeyChange;
        protected override void OnClick(EventArgs e)
        {
            base.OnClick(e);
            Text = "[...]";
            Focus();
        }

        protected override void OnCreateControl()
        {
            base.OnCreateControl();
            Text = "[Bind]";
        }

        protected override void OnKeyDown(KeyEventArgs e)
        {
            base.OnKeyDown(e);
            if (e.KeyCode != Keys.Back)
            {
                Key = (int)e.KeyCode;
                Text = "[" + new KeysConverter().ConvertToString(e.KeyCode).ToUpper() + "]";
            }
            else
            {
                Key = 0;
                Text = "[Bind]";
            }
            FindForm().ActiveControl = null;
            KeyChange?.Invoke(this, new EventArgs());
        }
    }
}
