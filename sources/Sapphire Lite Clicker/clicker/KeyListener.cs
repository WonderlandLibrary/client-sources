using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Sapphire_LITE.clicker {
    public static class KeyListener {

        [DllImport("user32.dll")] public static extern short GetAsyncKeyState(int vKey);

        public static Dictionary<Keys, Action> keybinds = new Dictionary<Keys, Action>();
        public static HashSet<Keys> keysToCheck = new HashSet<Keys> { };
        public static Dictionary<Keys, bool> keyStates = new Dictionary<Keys, bool>();

        #region Single activation key press detection

        public static void ListenForKeyPress() {
            while (true) {
                try {
                    // Check if any of the keys to check are pressed
                    foreach (var key in keysToCheck) {
                        short keyState = GetAsyncKeyState((int)key);
                        if ((keyState & 0x8000) != 0) {
                            if (!keyStates[key]) {
                                // The key was just pressed
                                keyStates[key] = true;

                                if (keybinds.TryGetValue(key, out var action)) {
                                    action();
                                }
                            }
                        } else
                            keyStates[key] = false;
                    }
                    Thread.Sleep(1);
                } catch { }
            }
        }

        #endregion

        #region Is key actively pressed detection

        public static bool isKeyPressed(Keys key) {
            return (GetAsyncKeyState((int)key) & 0x8000) != 0;
        }

        #endregion

        #region Menu bind listener

        public static void setupBindListener() {
            // Num for all key values for binds
            var values = Enum.GetValues(typeof(Keys)).Cast<Keys>().ToList();

            // remove left/right button so you cannot bind the clicker to them
            values.Remove(Keys.LButton);
            values.Remove(Keys.RButton);

            foreach (Keys key in values)
                keyList.Add(key);
        }

        public static List<Keys> keyList = new List<Keys>();

        public static async Task<Keys> getBind() {
            Keys bind = Keys.None;
            await Task.Run(() => {
                while (bind == Keys.None) {
                    foreach (Keys key in keyList) {
                        if (isKeyPressed(key)) {
                            bind = key;
                            break;
                        }
                    }
                }
            });
            return bind;
        }

        #endregion
    }
}
