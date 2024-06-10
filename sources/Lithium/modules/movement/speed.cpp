#include "..\..\klynt.h"

static float defaultspeed = 0.16277136f;
namespace v
{
    namespace modules
    {
        namespace speed
        {
            void enable() {
                float newspeed = defaultspeed * v::modules::speed::multiplier;
                float buffer;
                for (size_t x = 0; x < v::modules::speed::locations.size(); x++) {

                    if (ReadProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::speed::locations[x], &buffer, sizeof(float), nullptr)) {\
                        if (buffer <= 3.2554272f && buffer >= 0.16277136f) {
                            WriteProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::speed::locations[x], &newspeed, sizeof(float), nullptr);
                        }
                        else {
                            v::modules::speed::locations.erase(v::modules::speed::locations.begin() + x);
                            x = 0;
                        }
                    }
                    else {
                        v::modules::speed::locations.erase(v::modules::speed::locations.begin() + x);
                        x = 0;
                    }
                }

            }

            void disable() {
                float buffer;
                for (size_t x = 0; x < v::modules::speed::locations.size(); x++) {
                    if (ReadProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::speed::locations[x], &buffer, sizeof(float), nullptr)) {
                        if (buffer <= 3.2554272f && buffer >= 0.16277136f) {
                            WriteProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::speed::locations[x], &defaultspeed, sizeof(float), nullptr);
                        }
                        else {
                            v::modules::speed::locations.erase(v::modules::speed::locations.begin() + x);
                            x = 0;
                        }
                    }
                    else {
                        v::modules::speed::locations.erase(v::modules::speed::locations.begin() + x);
                        x = 0;
                    }
                }
            }

            void thread() {
                while (v::info::verified) {
                    if (v::modules::speed::enabled) {
                        v::modules::speed::used = true;
                        enable();
                    }
                    else {
                        if (v::modules::speed::used) {
                            disable();
                            v::modules::speed::used = false;
                        }

                    }

                    if (v::modules::speed::enabled) {
                        std::this_thread::sleep_for(std::chrono::milliseconds(500));
                    }
                    else {
                        std::this_thread::sleep_for(std::chrono::milliseconds(50));
                    }

                }

            }
        }
    }
}