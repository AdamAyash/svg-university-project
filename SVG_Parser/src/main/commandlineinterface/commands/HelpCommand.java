package main.commandlineinterface.commands;

import main.commandlineinterface.CommandLineInterface;
import main.commandlineinterface.commands.base.BaseCommand;
import main.commandlineinterface.commandvalidators.BaseCommandValidator;
import main.commandlineinterface.commandvalidators.CommandValidator;
import main.svgparser.CommandProcessor;

public class HelpCommand extends BaseCommand {
    public HelpCommand(String[] userInputCommand, int commandParameterCount) {
        super(userInputCommand, commandParameterCount);
    }

    @Override
    public boolean executeCommand(CommandProcessor commandProcessor, CommandLineInterface commandLineInterface) {
        CommandValidator printCommandValidator = new BaseCommandValidator();
        this.setCommandLineInterface(commandLineInterface);

        if(!printCommandValidator.validate(this))
            return false;

        commandProcessor.help();

        return true;

    }
}
