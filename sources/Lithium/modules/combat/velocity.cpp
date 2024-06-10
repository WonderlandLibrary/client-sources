#include "..\..\klynt.h"

static double defaultvelocity = 8000.0;

namespace v
{
    namespace modules
    {
        namespace velocity
        {
            void enable() {
                double newvelocity = 800000.0 / (double)v::utils::random_float(v::modules::velocity::min, v::modules::velocity::max);
                double buffer;
                for (size_t x = 0; x < v::modules::velocity::locations.size(); x++) {

                    if (ReadProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::velocity::locations[x], &buffer, sizeof(double), nullptr)) {
                        if (buffer <= 800000.0 && buffer >= 8000.0) {
                            WriteProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::velocity::locations[x], &newvelocity, sizeof(double), nullptr);
                        }
                        else {
                            v::modules::velocity::locations.erase(v::modules::velocity::locations.begin() + x);
                            x = 0;
                        }
                    }
                    else {
                        v::modules::velocity::locations.erase(v::modules::velocity::locations.begin() + x);
                        x = 0;
                    }
                }

            }

            void disable() {
                double buffer;
                for (size_t x = 0; x < v::modules::velocity::locations.size(); x++) {
                    if (ReadProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::velocity::locations[x], &buffer, sizeof(double), 0)) {
                        if (buffer <= 800000.0 && buffer >= 8000.0) {
                            WriteProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::velocity::locations[x], &defaultvelocity, sizeof(double), 0);
                        }
                        else {
                            v::modules::velocity::locations.erase(v::modules::velocity::locations.begin() + x);
                            x = 0;
                        }
                    }
                    else {
                        v::modules::velocity::locations.erase(v::modules::velocity::locations.begin() + x);
                        x = 0;
                    }
                }
            }

            void thread() {
                while (v::info::verified) {
                    if (v::modules::velocity::enabled) {
                        v::modules::velocity::used = true;
                        enable();
                    }
                    else {
                        if (v::modules::velocity::used) {
                            disable();
                            v::modules::velocity::used = false;
                        }

                    }

                    if (v::modules::velocity::enabled) {
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