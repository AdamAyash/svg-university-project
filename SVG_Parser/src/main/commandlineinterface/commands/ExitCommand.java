package main.commandlineinterface.commands;

import main.commandlineinterface.CommandLineInterface;
import main.commandlineinterface.commandresult.CommandResult;
import main.commandlineinterface.commands.base.BaseCommand;
import main.commandlineinterface.commandvalidators.CommandValidator;
import main.commandlineinterface.commandvalidators.ExitCommandValidator;
import main.svgparser.CommandProcessor;


//класът описва команда за изход от програмата
public class ExitCommand extends BaseCommand {
    //-----Constants-----

    //-----Members-----

    //-----Constructor-----

    public ExitCommand(String[] userInputCommand, int commandParameterCount) {
        super(userInputCommand, commandParameterCount);
    }

    //-----Methods-----

    //-----Overrides-----
    @Override
    public boolean executeCommand(CommandProcessor commandProcessor, CommandLineInterface commandLineInterface) {
        CommandValidator exitCommandValidator = new ExitCommandValidator();
        this.setCommandLineInterface(commandLineInterface);

        if(exitCommandValidator.validate(this))
             commandLineInterface.setRunning(false);
        else
            return false;

        return true;

    }
}
