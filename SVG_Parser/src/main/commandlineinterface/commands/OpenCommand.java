package main.commandlineinterface.commands;

import main.commandlineinterface.CommandLineInterface;
import main.commandlineinterface.commandresult.CommandResult;
import main.commandlineinterface.commands.base.BaseCommand;
import main.commandlineinterface.commandvalidators.OpenCommandValidator;
import main.svgparser.CommandProcessor;

public class OpenCommand extends BaseCommand {

    //-----Constants-----

    //оказва индекса на пътя на файла
    protected final int COMMAND_PATH_INDEX = 1;

    //-----Members-----

    //оказва пътя на файла, който се опитваме да отворим
    private String filePath;

    //-----Constructor-----
    public OpenCommand(String[] userInputCommand, int commandParameterCount) {
        super(userInputCommand, commandParameterCount);
    }

    //-----Methods-----

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    //-----Overrides----
    @Override
    public boolean executeCommand(CommandProcessor commandProcessor, CommandLineInterface commandLineInterface) {
        OpenCommandValidator openCommandValidator = new OpenCommandValidator();
        this.setCommandLineInterface(commandLineInterface);

        if(!openCommandValidator.validate(this))
            return false;

            filePath = getUserInputCommand()[COMMAND_PATH_INDEX];

        return commandProcessor.openFile(this);

    }
}
