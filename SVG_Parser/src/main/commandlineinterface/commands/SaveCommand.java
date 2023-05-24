package main.commandlineinterface.commands;

import main.commandlineinterface.commandresult.CommandResult;
import main.commandlineinterface.commands.base.BaseCommand;
import main.commandlineinterface.commandvalidators.BaseCommandValidator;
import main.commandlineinterface.commandvalidators.CommandValidator;
import main.svgparser.CommandProcessor;

//калсът описва команда за запазването на промените във файла на диска
public class SaveCommand extends BaseCommand {
    //-----Constants-----

    //-----Members-----

    //-----Constructor-----
    public SaveCommand(String[] userInputCommand, int commandParameterCount) {
        super(userInputCommand, commandParameterCount);
    }
    //-----Methods-----

    //-----Overrides----
    @Override
    public CommandResult executeCommand( CommandProcessor commandProcessor) {

        CommandValidator saveCommandValidator = new BaseCommandValidator();
        CommandResult cResult = saveCommandValidator.validate(this);

        if(cResult != CommandResult.COMMAND_SUCCESSFUL)
        return cResult;

        return commandProcessor.saveFile();
    }
}
