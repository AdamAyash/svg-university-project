package main.commandlineinterface.commandvalidators;

import main.commandlineinterface.commandresult.CommandResult;
import main.commandlineinterface.commands.base.Command;

public class ExitCommandValidator extends BaseCommandValidator{
    //-----Constants-----

    //-----Members-----

    //-----Constructor-----

    //-----Methods-----

    //-----Overrides----
    @Override
    public boolean validate(Command command) {
        CommandResult cResult = CommandResult.EXIT_PROGRAM;

        if(!(super.validate(command)))
            return false;

        return true;
    }
}
