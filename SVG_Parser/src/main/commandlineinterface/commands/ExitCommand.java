package main.commandlineinterface.commands;

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
    public CommandResult executeCommand(CommandProcessor commandProcessor) {
        CommandValidator exitCommandValidator = new ExitCommandValidator();

        return  exitCommandValidator.validate(this);
    }
}
