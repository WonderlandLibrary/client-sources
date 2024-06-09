package net.andrewsnetwork.icarus.values;

import net.andrewsnetwork.icarus.utilities.*;
import java.util.*;
import net.andrewsnetwork.icarus.*;
import net.andrewsnetwork.icarus.module.*;
import net.andrewsnetwork.icarus.command.*;

public class ValueManager
{
    private List<Value> values;
    
    public final Value getValueUsingName(final String name) {
        for (final Value value : this.values) {
            if (value.getName().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }
    
    public void addValue(final Value value) {
        if (!this.values.contains(value)) {
            this.values.add(value);
        }
    }
    
    public void removeValue(final Value value) {
        if (this.values.contains(value)) {
            this.values.remove(value);
        }
    }
    
    public void setupValues() {
        this.values = new ArrayList<Value>();
    }
    
    public void organizeValues() {
        Logger.writeConsole("Starting to load up the values.");
        Collections.sort(this.values, new Comparator<Value>() {
            @Override
            public int compare(final Value value1, final Value value2) {
                return value1.getName().compareTo(value2.getName());
            }
        });
        Logger.writeConsole("Successfully loaded " + this.values.size() + " values.");
        for (final Module module : Icarus.getModuleManager().getModules()) {
            Icarus.getCommandManager().getCommands().add(new Command(
              module.getName(), 
              "<value>")
              {
                public void run(String message)
                {
                  boolean shouldArgs = false;
                  boolean secondCancel = true;
                  boolean thirdCancel = true;
                  boolean cancel = true;
                  boolean say = true;
                  String arguments = "Wrong arguments! Try this:";
                  String secondArguments = "Wrong arguments! Try this:";
                  for (Value value : ValueManager.this.values) {
                    if ((value instanceof ModeValue))
                    {
                      if (module.equals(value.getModule()))
                      {
                        ModeValue v = (ModeValue)value;
                        say = false;
                        cancel = false;
                        if (message.split(" ")[1].equals("mode"))
                        {
                          if (message.split(" ").length == 3)
                          {
                            shouldArgs = true;
                            for (int i = 0; i < v.getStringValues().length; i++)
                            {
                              if (message.split(" ")[2].equalsIgnoreCase(v.getStringValues()[i].replace("_", "")))
                              {
                                v.setStringValue(v.getStringValues()[i]);
                                Logger.writeChat("Value " + v.getName().replace(
                                  v.getModule().getName().toLowerCase(), "").replace("_", "") + " set to " + v.getStringValue().replace("_", "") + ".");
                                shouldArgs = false;
                                thirdCancel = false;
                              }
                              secondArguments = secondArguments + " " + v.getStringValues()[i].replace("_", "") + ",";
                            }
                          }
                          else
                          {
                            shouldArgs = false;
                            secondCancel = false;
                            Logger.writeChat("Wrong arguments! " + v.getModule().getName() + " mode " + "<value>");
                          }
                        }
                        else
                        {
                          shouldArgs = false;
                          cancel = true;
                          arguments = "Wrong arguments! Try this: mode.";
                        }
                      }
                    }
                    else if ((value.getValue() instanceof Boolean))
                    {
                      if (module.equals(value.getModule()))
                      {
                        say = false;
                        cancel = false;
                        if (message.split(" ")[1].equalsIgnoreCase(value.getCommandName()))
                        {
                          if (message.split(" ").length == 3)
                          {
                            if ((message.split(" ")[2].equalsIgnoreCase("true")) || (message.split(" ")[2].equalsIgnoreCase("false")) || 
                              (message.split(" ")[2].equalsIgnoreCase("-d")))
                            {
                              if (message.split(" ")[2].equalsIgnoreCase("-d")) {
                                value.setValue(value.getDefaultValue());
                              } else {
                                value.setValue(Boolean.valueOf(Boolean.parseBoolean(message.split(" ")[2])));
                              }
                              Logger.writeChat(
                                "Value " + value.getName().replace(value.getModule().getName().toLowerCase(), "").replace("_", "") + " set to " + value.getValue() + ".");
                              thirdCancel = false;
                            }
                            else
                            {
                              thirdCancel = false;
                              Logger.writeChat("Boolean value couldn't be parsed! Check your spelling, this is supposed to be a true/false statement.");
                            }
                          }
                          else
                          {
                            secondCancel = false;
                            Logger.writeChat("Wrong arguments! " + value.getModule().getName() + " " + value.getCommandName() + " <value>");
                          }
                        }
                        else
                        {
                          cancel = true;
                          arguments = arguments + " " + value.getCommandName() + ",";
                        }
                      }
                    }
                    else if (((value.getValue() instanceof Float)) && 
                      ((value instanceof ConstrainedValue)))
                    {
                      if (module.equals(value.getModule()))
                      {
                        say = false;
                        cancel = false;
                        if (message.split(" ")[1].equalsIgnoreCase(value.getCommandName()))
                        {
                          if (message.split(" ").length == 3)
                          {
                            try
                            {
                              if (message.split(" ")[2].equalsIgnoreCase("-d")) {
                                value.setValue(value.getDefaultValue());
                              } else {
                                value.setValue(Float.valueOf(Float.parseFloat(message.split(" ")[2])));
                              }
                              if (((Float)value.getValue()).floatValue() > ((Float)((ConstrainedValue)value).getMax()).floatValue()) {
                                value.setValue(((ConstrainedValue)value).getMax());
                              } else if (((Float)value.getValue()).floatValue() < ((Float)((ConstrainedValue)value).getMin()).floatValue()) {
                                value.setValue(((ConstrainedValue)value).getMin());
                              }
                              Logger.writeChat(
                                "Value " + value.getName().replace(value.getModule().getName().toLowerCase(), "").replace("_", "") + " set to " + value.getValue() + ".");
                              thirdCancel = false;
                            }
                            catch (Exception e)
                            {
                              thirdCancel = false;
                              Logger.writeChat("Float value couldn't be parsed! Check your spelling, this is supposed to be a number.");
                            }
                          }
                          else
                          {
                            secondCancel = false;
                            Logger.writeChat("Wrong arguments! " + value.getModule().getName() + " " + value.getCommandName() + " <value>");
                          }
                        }
                        else
                        {
                          cancel = true;
                          arguments = arguments + " " + value.getCommandName() + ",";
                        }
                      }
                    }
                    else if (((value.getValue() instanceof Long)) && 
                      ((value instanceof ConstrainedValue)))
                    {
                      if (module.equals(value.getModule()))
                      {
                        say = false;
                        cancel = false;
                        if (message.split(" ")[1].equalsIgnoreCase(value.getCommandName()))
                        {
                          if (message.split(" ").length == 3)
                          {
                            try
                            {
                              if (message.split(" ")[2].equalsIgnoreCase("-d")) {
                                value.setValue(value.getDefaultValue());
                              } else {
                                value.setValue(Long.valueOf(Long.parseLong(message.split(" ")[2])));
                              }
                              if (((Long)value.getValue()).longValue() > ((Long)((ConstrainedValue)value).getMax()).longValue()) {
                                value.setValue(((ConstrainedValue)value).getMax());
                              } else if (((Long)value.getValue()).longValue() < ((Long)((ConstrainedValue)value).getMin()).longValue()) {
                                value.setValue(((ConstrainedValue)value).getMin());
                              }
                              Logger.writeChat(
                                "Value " + value.getName().replace(value.getModule().getName().toLowerCase(), "").replace("_", "") + " set to " + value.getValue() + ".");
                              thirdCancel = false;
                            }
                            catch (Exception e)
                            {
                              thirdCancel = false;
                              Logger.writeChat("Long value couldn't be parsed! Check your spelling, this is supposed to be a number.");
                            }
                          }
                          else
                          {
                            secondCancel = false;
                            Logger.writeChat("Wrong arguments! " + value.getModule().getName() + " " + value.getCommandName() + " <value>");
                          }
                        }
                        else
                        {
                          cancel = true;
                          arguments = arguments + " " + value.getCommandName() + ",";
                        }
                      }
                    }
                    else if (((value.getValue() instanceof Byte)) && 
                      ((value instanceof ConstrainedValue)))
                    {
                      if (module.equals(value.getModule()))
                      {
                        say = false;
                        cancel = false;
                        if (message.split(" ")[1].equalsIgnoreCase(value.getCommandName()))
                        {
                          if (message.split(" ").length == 3)
                          {
                            try
                            {
                              if (message.split(" ")[2].equalsIgnoreCase("-d")) {
                                value.setValue(value.getDefaultValue());
                              } else {
                                value.setValue(Byte.valueOf(Byte.parseByte(message.split(" ")[2])));
                              }
                              if (((Byte)value.getValue()).byteValue() > ((Byte)((ConstrainedValue)value).getMax()).byteValue()) {
                                value.setValue(((ConstrainedValue)value).getMax());
                              } else if (((Byte)value.getValue()).byteValue() < ((Byte)((ConstrainedValue)value).getMin()).byteValue()) {
                                value.setValue(((ConstrainedValue)value).getMin());
                              }
                              Logger.writeChat(
                                "Value " + value.getName().replace(value.getModule().getName().toLowerCase(), "").replace("_", "") + " set to " + value.getValue() + ".");
                              thirdCancel = false;
                            }
                            catch (Exception e)
                            {
                              thirdCancel = false;
                              Logger.writeChat("Byte value couldn't be parsed! Check your spelling, this is supposed to be a number.");
                            }
                          }
                          else
                          {
                            secondCancel = false;
                            Logger.writeChat("Wrong arguments! " + value.getModule().getName() + " " + value.getCommandName() + " <value>");
                          }
                        }
                        else
                        {
                          cancel = true;
                          arguments = arguments + " " + value.getCommandName() + ",";
                        }
                      }
                    }
                    else if (((value.getValue() instanceof Double)) && 
                      ((value instanceof ConstrainedValue)))
                    {
                      if (module.equals(value.getModule()))
                      {
                        say = false;
                        cancel = false;
                        if (message.split(" ")[1].equalsIgnoreCase(value.getCommandName()))
                        {
                          if (message.split(" ").length == 3)
                          {
                            try
                            {
                              if (message.split(" ")[2].equalsIgnoreCase("-d")) {
                                value.setValue(value.getDefaultValue());
                              } else {
                                value.setValue(Double.valueOf(Double.parseDouble(message.split(" ")[2])));
                              }
                              if (((Double)value.getValue()).doubleValue() > ((Double)((ConstrainedValue)value).getMax()).doubleValue()) {
                                value.setValue(((ConstrainedValue)value).getMax());
                              } else if (((Double)value.getValue()).doubleValue() < ((Double)((ConstrainedValue)value).getMin()).doubleValue()) {
                                value.setValue(((ConstrainedValue)value).getMin());
                              }
                              Logger.writeChat(
                                "Value " + value.getName().replace(value.getModule().getName().toLowerCase(), "").replace("_", "") + " set to " + value.getValue() + ".");
                              thirdCancel = false;
                            }
                            catch (Exception e)
                            {
                              thirdCancel = false;
                              Logger.writeChat("Double value couldn't be parsed! Check your spelling, this is supposed to be a number.");
                            }
                          }
                          else
                          {
                            secondCancel = false;
                            Logger.writeChat("Wrong arguments! " + value.getModule().getName() + " " + value.getCommandName() + " <value>");
                          }
                        }
                        else
                        {
                          cancel = true;
                          arguments = arguments + " " + value.getCommandName() + ",";
                        }
                      }
                    }
                    else if (((value.getValue() instanceof Integer)) && 
                      ((value instanceof ConstrainedValue)) && 
                      (module.equals(value.getModule())))
                    {
                      say = false;
                      cancel = false;
                      if (message.split(" ")[1].equalsIgnoreCase(value.getCommandName()))
                      {
                        if (message.split(" ").length == 3)
                        {
                          try
                          {
                            if (message.split(" ")[2].equalsIgnoreCase("-d")) {
                              value.setValue(value.getDefaultValue());
                            } else {
                              value.setValue(Integer.valueOf(Integer.parseInt(message.split(" ")[2])));
                            }
                            if (((Integer)value.getValue()).intValue() > ((Integer)((ConstrainedValue)value).getMax()).intValue()) {
                              value.setValue(((ConstrainedValue)value).getMax());
                            } else if (((Integer)value.getValue()).intValue() < ((Integer)((ConstrainedValue)value).getMin()).intValue()) {
                              value.setValue(((ConstrainedValue)value).getMin());
                            }
                            Logger.writeChat(
                              "Value " + value.getName().replace(value.getModule().getName().toLowerCase(), "").replace("_", "") + " set to " + value.getValue() + ".");
                            thirdCancel = false;
                          }
                          catch (Exception e)
                          {
                            thirdCancel = false;
                            Logger.writeChat("Integer value couldn't be parsed! Check your spelling, this is supposed to be a number.");
                          }
                        }
                        else
                        {
                          secondCancel = false;
                          Logger.writeChat("Wrong arguments! " + value.getModule().getName() + " " + value.getCommandName() + " <value>");
                        }
                      }
                      else
                      {
                        cancel = true;
                        arguments = arguments + " " + value.getCommandName() + ",";
                      }
                    }
                  }
                  if (say) {
                    Logger.writeChat("This module doesn't have values.");
                  }
                  if ((cancel) && (secondCancel) && (thirdCancel) && (!say)) {
                    Logger.writeChat(arguments.substring(0, arguments.length() - 1) + ".");
                  }
                  if (shouldArgs) {
                    Logger.writeChat(secondArguments.substring(0, secondArguments.length() - 1) + ".");
                  }
                }
              });
          }
        }
    
    public List<Value> getValues() {
        return this.values;
    }
}
