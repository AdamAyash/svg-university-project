package main.commandlineinterface.commands;

import main.commandlineinterface.CommandLineInterface;
import main.commandlineinterface.commandresult.CommandResult;
import main.commandlineinterface.commands.base.BaseCommand;
import main.commandlineinterface.commandvalidators.BaseCommandValidator;
import main.commandlineinterface.commandvalidators.EraseCommandValidator;
import main.svgparser.CommandProcessor;


//класът описва команда за изтриване на фигура
public class EraseCommand extends BaseCommand {

    //-----Constants-----

    //-----Members-----

    //описва индекса на фигурата която ще се изтрива
    private int eraseShapeIndex;

    //-----Constructor-----
    public EraseCommand(String[] userInputCommand, int commandParameterCount) {
        super(userInputCommand, commandParameterCount);
    }
    //-----Methods-----

    public int getEraseShapeIndex() {
        return eraseShapeIndex;
    }

    public void setEraseShapeIndex(int eraseShapeIndex) {
        this.eraseShapeIndex = eraseShapeIndex;
    }

    //-----Overrides----

    @Override
    public boolean executeCommand( CommandProcessor commandProcessor, CommandLineInterface commandLineInterface) {
        BaseCommandValidator eraseCommandValidator = new EraseCommandValidator();
        this.setCommandLineInterface(commandLineInterface);

     if(!eraseCommandValidator.validate(this))
            return false;

        return commandProcessor.eraseShape(this);

    }
}
