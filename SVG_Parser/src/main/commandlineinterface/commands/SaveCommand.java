package main.commandlineinterface.commands;

import main.commandlineinterface.commandresult.CommandResult;
import main.commandlineinterface.commands.base.BaseCommand;
import main.commandlineinterface.commandvalidators.CommandValidator;
import main.commandlineinterface.commandvalidators.SaveCommandValidator;

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
    public CommandResult executeCommand() {
        CommandValidator saveCommandValidator = new SaveCommandValidator();
        return saveCommandValidator.validate(this);
    }
}
