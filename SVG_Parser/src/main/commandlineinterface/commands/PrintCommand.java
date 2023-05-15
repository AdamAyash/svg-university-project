package main.commandlineinterface.commands;

import main.commandlineinterface.commandresult.CommandResult;
import main.commandlineinterface.commands.base.BaseCommand;
import main.commandlineinterface.commandvalidators.CommandValidator;
import main.commandlineinterface.commandvalidators.PrintCommandValidator;

//команда за принтиране на подържаните фигури на конзолата
public class PrintCommand extends BaseCommand {

    //-----Constants-----

    //-----Members-----

    //-----Constructor-----
    public PrintCommand(String[] userInputCommand, int commandParameterCount) {
        super(userInputCommand, commandParameterCount);
    }

    //-----Methods-----

    //-----Overrides----
    @Override
    public CommandResult executeCommand() {
        CommandValidator printCommandValidator = new PrintCommandValidator();
        return printCommandValidator.validate(this);
    }
}
