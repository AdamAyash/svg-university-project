package main.commandlineinterface.commands.base;

import main.commandlineinterface.commandresult.CommandResult;
import main.svgparser.CommandProcessor;


public interface Command {

    //описва поведението на командата която ще имплементира съответният interface
    CommandResult executeCommand(CommandProcessor commandProcessor);
}
