package main.commandlineinterface.commands;

import main.commandlineinterface.commandresult.CommandResult;
import main.commandlineinterface.commands.base.BaseCommand;
import main.commandlineinterface.commandvalidators.CloseCommandValidator;
import main.commandlineinterface.commandvalidators.CommandValidator;


//класът описва команда за затваряне на текущият файл
public class CloseCommand extends BaseCommand {

    //-----Constants-----

    //-----Members-----

    //-----Constructor-----
    public CloseCommand(String[] userInputCommand, int commandParameterCount) {
        super(userInputCommand, commandParameterCount);
    }

    //-----Methods-----

    //-----Overrides----

    @Override
    public CommandResult executeCommand() {
        CommandValidator closeCommandValidator = new  CloseCommandValidator();

        return closeCommandValidator.validate(this);
    }

}
