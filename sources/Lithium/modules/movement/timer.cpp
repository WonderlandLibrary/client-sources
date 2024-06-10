#include "..\..\klynt.h"

static double defaulttimer = 1000.0;
namespace v
{
    namespace modules
    {
        namespace timer
        {
            void enable() {
                double newtimer = defaulttimer / v::modules::timer::multiplier;
                double buffer;
                for (size_t x = 0; x < v::modules::timer::locations.size(); x++) {

                    if (ReadProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::timer::locations[x], &buffer, sizeof(double), nullptr)) {
                        if (buffer <= 1000.0 && buffer >= 200.0) {
                            WriteProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::timer::locations[x], &newtimer, sizeof(double), nullptr);
                        }
                        else {
                            v::modules::timer::locations.erase(v::modules::timer::locations.begin() + x);
                            x = 0;
                        }
                    }
                    else {
                        v::modules::timer::locations.erase(v::modules::timer::locations.begin() + x);
                        x = 0;
                    }
                }

            }

            void disable() {
                double buffer;
                for (size_t x = 0; x < v::modules::timer::locations.size(); x++) {
                    if (ReadProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::timer::locations[x], &buffer, sizeof(double), nullptr)) {
                        if (buffer <= 1000.0 && buffer >= 200.0) {
                            WriteProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::timer::locations[x], &defaulttimer, sizeof(double), nullptr);
                        }
                        else {
                            v::modules::timer::locations.erase(v::modules::timer::locations.begin() + x);
                            x = 0;
                        }
                    }
                    else {
                        v::modules::timer::locations.erase(v::modules::timer::locations.begin() + x);
                        x = 0;
                    }
                }
            }

            void thread() {
                while (v::info::verified) {
                    if (v::modules::timer::enabled) {
                        v::modules::timer::used = true;
                        enable();
                    }
                    else {
                        if (v::modules::timer::used) {
                            disable();
                            v::modules::timer::used = false;
                        }

                    }

                    if (v::modules::timer::enabled) {
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