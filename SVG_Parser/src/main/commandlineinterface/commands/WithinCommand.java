package main.commandlineinterface.commands;

import main.commandlineinterface.commandresult.CommandResult;
import main.commandlineinterface.commands.base.BaseCommand;

public class WithinCommand extends BaseCommand {

    public WithinCommand(String[] userInputCommand, int commandParameterCount) {
        super(userInputCommand, commandParameterCount);
    }

    @Override
    public CommandResult executeCommand() {
        return null;
    }
}
