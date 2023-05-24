package main.commandlineinterface.commands;

import main.commandlineinterface.commandresult.CommandResult;
import main.commandlineinterface.commands.base.BaseCommand;
import main.commandlineinterface.commandvalidators.BaseCommandValidator;
import main.commandlineinterface.commandvalidators.CommandValidator;
import main.svgparser.CommandProcessor;


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
    public CommandResult executeCommand(CommandProcessor commandProcessor) {
        CommandValidator closeCommandValidator = new BaseCommandValidator();
        CommandResult cResult = closeCommandValidator.validate(this);

        if(cResult != CommandResult.COMMAND_SUCCESSFUL)
            return cResult;

        return commandProcessor.closeCurrentFile();
    }

}
