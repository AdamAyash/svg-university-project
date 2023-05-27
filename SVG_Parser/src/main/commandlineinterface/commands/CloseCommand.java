package main.commandlineinterface.commands;

import main.commandlineinterface.CommandLineInterface;
import main.commandlineinterface.commands.base.BaseCommand;
import main.commandlineinterface.commandvalidators.BaseCommandValidator;
import main.commandlineinterface.commandvalidators.CommandValidator;
import main.svgparser.CommandProcessor;


//класът описва команда за затваряне на текущият файл
public class CloseCommand extends BaseCommand {

    //-----Constants-----

    //-----Members-----

    //-----Constructor-----
    public CloseCommand(String[] userInputCommand, int commandParameterCount ) {
        super(userInputCommand, commandParameterCount);
    }

    //-----Methods-----

    //-----Overrides----

    @Override
    public boolean executeCommand(CommandProcessor commandProcessor, CommandLineInterface commandLineInterface) {
        CommandValidator closeCommandValidator = new BaseCommandValidator();
        this.setCommandLineInterface(commandLineInterface);

        if(!closeCommandValidator.validate(this))
            return false;


        return commandProcessor.closeCurrentFile();
    }

}
