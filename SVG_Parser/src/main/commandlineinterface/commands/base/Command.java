package main.commandlineinterface.commands.base;

import main.commandlineinterface.CommandLineInterface;
import main.svgparser.CommandProcessor;

public interface Command {

    //описва поведението на командата която ще имплементира съответният interface
    boolean executeCommand(CommandProcessor commandProcessor, CommandLineInterface commandLineInterface);
}
