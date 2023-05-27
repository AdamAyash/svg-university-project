package main.commandlineinterface.commands.base;

import main.commandlineinterface.CommandLineInterface;
import main.svgparser.CommandProcessor;

import java.util.PrimitiveIterator;

//абстрактен клас описващ всичко общо за една команда
public abstract class BaseCommand implements Command{
    //-----Constants-----

    //константа описваща командата въведена от потребителя
    private final String[] USER_INPUT_COMMAND;

    private  CommandLineInterface commandLineInterface;

    //-----Members-----

    // член променлива описваща броя на параметрите на командата
    private int commandParameterCount;

    //-----Constructor-----
    public BaseCommand(String[] userInputCommand, int commandParameterCount){
        this.USER_INPUT_COMMAND = userInputCommand;
        this.commandParameterCount = commandParameterCount;
    }

    //-----Methods-----
    public String[] getUserInputCommand() {
        return USER_INPUT_COMMAND;
    }

    public int getCommandParameterCount(){
        return commandParameterCount;
    }

    public CommandLineInterface getCommandLineInterface() {
        return commandLineInterface;
    }

    public void setCommandLineInterface(CommandLineInterface commandLineInterface) {
        this.commandLineInterface = commandLineInterface;
    }

    //-----Overrides-----
}
