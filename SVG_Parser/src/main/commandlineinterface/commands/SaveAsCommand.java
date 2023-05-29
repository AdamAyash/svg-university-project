package main.commandlineinterface.commands;

import main.commandlineinterface.CommandLineInterface;
import main.commandlineinterface.commands.base.BaseCommand;
import main.commandlineinterface.commandvalidators.OpenCommandValidator;
import main.svgparser.CommandProcessor;

public class SaveAsCommand extends OpenCommand {
    public SaveAsCommand(String[] userInputCommand, int commandParameterCount) {
        super(userInputCommand, commandParameterCount);
    }

    @Override
    public boolean executeCommand(CommandProcessor commandProcessor, CommandLineInterface commandLineInterface) {
        OpenCommandValidator openCommandValidator = new OpenCommandValidator();
        this.setCommandLineInterface(commandLineInterface);

        if(!openCommandValidator.validate(this))
            return false;

        setFilePath(getUserInputCommand()[COMMAND_PATH_INDEX]);

        return commandProcessor.saveFile(getFilePath());

    }
}
