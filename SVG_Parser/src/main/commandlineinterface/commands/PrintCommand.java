package main.commandlineinterface.commands;

import main.commandlineinterface.commandresult.CommandResult;
import main.commandlineinterface.commands.base.BaseCommand;
import main.commandlineinterface.commandvalidators.BaseCommandValidator;
import main.commandlineinterface.commandvalidators.CommandValidator;
import main.svgparser.CommandProcessor;

//команда за принтиране на подържаните фигури на конзолата
public class PrintCommand extends BaseCommand {

    //-----Constants-----

    //-----Members-----

    //-----Constructor-----
    public PrintCommand(String[] userInputCommand, int commandParameterCount) {
        super(userInputCommand, commandParameterCount);
    }

    //-----Methods-----

    //-----Overrides----
    @Override
    public CommandResult executeCommand(CommandProcessor commandProcessor) {
        CommandValidator printCommandValidator = new BaseCommandValidator();

       CommandResult cResult =  printCommandValidator.validate(this);
       if(cResult != CommandResult.COMMAND_SUCCESSFUL)
           return cResult;

        return commandProcessor.printAllShapes();
    }
}
