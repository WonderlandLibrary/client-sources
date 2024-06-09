package HORIZON-6-0-SKIDPROTECTION;

import java.util.Map;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class InputProvider
{
    private HashMap HorizonCode_Horizon_È;
    private ArrayList Â;
    private Input Ý;
    private HashMap Ø­áŒŠá;
    private boolean Âµá€;
    
    public InputProvider(final Input input) {
        this.Â = new ArrayList();
        this.Ø­áŒŠá = new HashMap();
        this.Âµá€ = true;
        (this.Ý = input).HorizonCode_Horizon_È(new Â((Â)null));
        this.HorizonCode_Horizon_È = new HashMap();
    }
    
    public List HorizonCode_Horizon_È() {
        final List uniqueCommands = new ArrayList();
        for (final Command command : this.HorizonCode_Horizon_È.values()) {
            if (!uniqueCommands.contains(command)) {
                uniqueCommands.add(command);
            }
        }
        return uniqueCommands;
    }
    
    public List HorizonCode_Horizon_È(final Command command) {
        final List controlsForCommand = new ArrayList();
        for (final Map.Entry entry : this.HorizonCode_Horizon_È.entrySet()) {
            final Control key = entry.getKey();
            final Command value = entry.getValue();
            if (value == command) {
                controlsForCommand.add(key);
            }
        }
        return controlsForCommand;
    }
    
    public void HorizonCode_Horizon_È(final boolean active) {
        this.Âµá€ = active;
    }
    
    public boolean Â() {
        return this.Âµá€;
    }
    
    public void HorizonCode_Horizon_È(final InputProviderListener listener) {
        this.Â.add(listener);
    }
    
    public void Â(final InputProviderListener listener) {
        this.Â.remove(listener);
    }
    
    public void HorizonCode_Horizon_È(final Control control, final Command command) {
        this.HorizonCode_Horizon_È.put(control, command);
        if (this.Ø­áŒŠá.get(command) == null) {
            this.Ø­áŒŠá.put(command, new HorizonCode_Horizon_È((HorizonCode_Horizon_È)null));
        }
    }
    
    public void Â(final Command command) {
        final List controls = this.HorizonCode_Horizon_È(command);
        for (int i = 0; i < controls.size(); ++i) {
            this.HorizonCode_Horizon_È(controls.get(i));
        }
    }
    
    public void HorizonCode_Horizon_È(final Control control) {
        final Command command = this.HorizonCode_Horizon_È.remove(control);
        if (command != null && !this.HorizonCode_Horizon_È.keySet().contains(command)) {
            this.Ø­áŒŠá.remove(command);
        }
    }
    
    private HorizonCode_Horizon_È à(final Command command) {
        return this.Ø­áŒŠá.get(command);
    }
    
    public boolean Ý(final Command command) {
        return this.à(command).Â();
    }
    
    public boolean Ø­áŒŠá(final Command command) {
        return this.à(command).HorizonCode_Horizon_È();
    }
    
    protected void Âµá€(final Command command) {
        InputProvider.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.à(command), true);
        InputProvider.HorizonCode_Horizon_È.Â(this.à(command), true);
        if (!this.Â()) {
            return;
        }
        for (int i = 0; i < this.Â.size(); ++i) {
            this.Â.get(i).HorizonCode_Horizon_È(command);
        }
    }
    
    protected void Ó(final Command command) {
        InputProvider.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.à(command), false);
        if (!this.Â()) {
            return;
        }
        for (int i = 0; i < this.Â.size(); ++i) {
            this.Â.get(i).Â(command);
        }
    }
    
    private class HorizonCode_Horizon_È
    {
        private boolean Â;
        private boolean Ý;
        
        public boolean HorizonCode_Horizon_È() {
            if (this.Ý) {
                this.Ý = false;
                return true;
            }
            return false;
        }
        
        public boolean Â() {
            return this.Â;
        }
        
        static /* synthetic */ void HorizonCode_Horizon_È(final HorizonCode_Horizon_È horizonCode_Horizon_È, final boolean â) {
            horizonCode_Horizon_È.Â = â;
        }
        
        static /* synthetic */ void Â(final HorizonCode_Horizon_È horizonCode_Horizon_È, final boolean ý) {
            horizonCode_Horizon_È.Ý = ý;
        }
    }
    
    private class Â extends InputAdapter
    {
        @Override
        public boolean Ý() {
            return true;
        }
        
        @Override
        public void HorizonCode_Horizon_È(final int key, final char c) {
            final Command command = InputProvider.this.HorizonCode_Horizon_È.get(new KeyControl(key));
            if (command != null) {
                InputProvider.this.Âµá€(command);
            }
        }
        
        @Override
        public void Â(final int key, final char c) {
            final Command command = InputProvider.this.HorizonCode_Horizon_È.get(new KeyControl(key));
            if (command != null) {
                InputProvider.this.Ó(command);
            }
        }
        
        @Override
        public void HorizonCode_Horizon_È(final int button, final int x, final int y) {
            final Command command = InputProvider.this.HorizonCode_Horizon_È.get(new MouseButtonControl(button));
            if (command != null) {
                InputProvider.this.Âµá€(command);
            }
        }
        
        @Override
        public void Â(final int button, final int x, final int y) {
            final Command command = InputProvider.this.HorizonCode_Horizon_È.get(new MouseButtonControl(button));
            if (command != null) {
                InputProvider.this.Ó(command);
            }
        }
        
        @Override
        public void Ý(final int controller) {
            final Command command = InputProvider.this.HorizonCode_Horizon_È.get(new ControllerDirectionControl(controller, ControllerDirectionControl.Ó));
            if (command != null) {
                InputProvider.this.Âµá€(command);
            }
        }
        
        @Override
        public void Ø­áŒŠá(final int controller) {
            final Command command = InputProvider.this.HorizonCode_Horizon_È.get(new ControllerDirectionControl(controller, ControllerDirectionControl.Ó));
            if (command != null) {
                InputProvider.this.Ó(command);
            }
        }
        
        @Override
        public void Âµá€(final int controller) {
            final Command command = InputProvider.this.HorizonCode_Horizon_È.get(new ControllerDirectionControl(controller, ControllerDirectionControl.áŒŠÆ));
            if (command != null) {
                InputProvider.this.Âµá€(command);
            }
        }
        
        @Override
        public void Ó(final int controller) {
            final Command command = InputProvider.this.HorizonCode_Horizon_È.get(new ControllerDirectionControl(controller, ControllerDirectionControl.áŒŠÆ));
            if (command != null) {
                InputProvider.this.Ó(command);
            }
        }
        
        @Override
        public void à(final int controller) {
            final Command command = InputProvider.this.HorizonCode_Horizon_È.get(new ControllerDirectionControl(controller, ControllerDirectionControl.à));
            if (command != null) {
                InputProvider.this.Âµá€(command);
            }
        }
        
        @Override
        public void Ø(final int controller) {
            final Command command = InputProvider.this.HorizonCode_Horizon_È.get(new ControllerDirectionControl(controller, ControllerDirectionControl.à));
            if (command != null) {
                InputProvider.this.Ó(command);
            }
        }
        
        @Override
        public void HorizonCode_Horizon_È(final int controller) {
            final Command command = InputProvider.this.HorizonCode_Horizon_È.get(new ControllerDirectionControl(controller, ControllerDirectionControl.Ø));
            if (command != null) {
                InputProvider.this.Âµá€(command);
            }
        }
        
        @Override
        public void Â(final int controller) {
            final Command command = InputProvider.this.HorizonCode_Horizon_È.get(new ControllerDirectionControl(controller, ControllerDirectionControl.Ø));
            if (command != null) {
                InputProvider.this.Ó(command);
            }
        }
        
        @Override
        public void HorizonCode_Horizon_È(final int controller, final int button) {
            final Command command = InputProvider.this.HorizonCode_Horizon_È.get(new ControllerButtonControl(controller, button));
            if (command != null) {
                InputProvider.this.Âµá€(command);
            }
        }
        
        @Override
        public void Â(final int controller, final int button) {
            final Command command = InputProvider.this.HorizonCode_Horizon_È.get(new ControllerButtonControl(controller, button));
            if (command != null) {
                InputProvider.this.Ó(command);
            }
        }
    }
}
