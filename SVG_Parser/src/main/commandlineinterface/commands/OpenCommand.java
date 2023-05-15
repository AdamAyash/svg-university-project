package main.commandlineinterface.commands;

import main.commandlineinterface.commandresult.CommandResult;
import main.commandlineinterface.commands.base.BaseCommand;
import main.commandlineinterface.commandvalidators.OpenCommandValidator;

public class OpenCommand extends BaseCommand {

    //-----Constants-----

    //оказва индекса на пътя на файла
    private final int COMMAND_PATH_INDEX = 1;

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

    //-----Overrides----
    @Override
    public CommandResult executeCommand() {
        OpenCommandValidator openCommandValidator = new OpenCommandValidator();
        CommandResult cResult = openCommandValidator.validate(this);

        if(cResult == CommandResult.COMMAND_SUCCESSFUL)
            filePath = getUserInputCommand()[COMMAND_PATH_INDEX];

        return cResult;

    }
}
