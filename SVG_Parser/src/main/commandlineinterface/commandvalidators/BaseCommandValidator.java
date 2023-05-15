package main.commandlineinterface.commandvalidators;

import main.commandlineinterface.commandresult.CommandResult;
import main.commandlineinterface.commands.base.BaseCommand;
import main.commandlineinterface.commands.base.Command;

//класът служи за базовата валидация на една команда за в момента броя на параметрите
public abstract class BaseCommandValidator implements CommandValidator{

    //-----Constants-----

    //-----Members-----

    //-----Constructor-----

    //-----Methods-----

    //-----Overrides----
    @Override
    public CommandResult validate(Command command) {
        CommandResult cResult = CommandResult.COMMAND_SUCCESSFUL;
        BaseCommand baseCommand = (BaseCommand)command;

        if(!(baseCommand.getCommandParameterCount() == baseCommand.getUserInputCommand().length - 1))
            cResult = CommandResult.COMMAND_FAILED;

        return cResult;
    }
}
