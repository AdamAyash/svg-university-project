package main.commandlineinterface;
import main.commandlineinterface.PrintWriter.PrintWriter;
import main.commandlineinterface.commandfactory.CommandFactory;
import main.commandlineinterface.commandresult.CommandResult;
import main.commandlineinterface.commands.base.Command;
import main.commandlineinterface.inputmanager.InputManager;
import main.errorlogger.ErrorLogger;
import main.exceptions.CommandNotImplementedException;
import main.svgparser.CommandProcessor;

//класът описва работата и функционалностите на интерфейса
public final class CommandLineInterface {
    //-----Constants-----

    //-----Members-----
    //член променлива описваща обект от тип InputManager
    private InputManager inputManager;


    //член променлиа описваща дали програмата работи
    private boolean isRunning;

    //член променлива описваща обект от тип CommandFactory, служещ за създаването на съответните команди.
    private CommandFactory commandFactory;

    private Command currentCommand;


    //-----Constructor-----
    public  CommandLineInterface(){
        this.inputManager = new InputManager();
        this.commandFactory = new CommandFactory();
        isRunning = true;
    }
    //-----Methods-----
    public InputManager getInputManager() {
        return inputManager;
    }

    //стартираща фунция на класът
    public void run(CommandProcessor commandProcessor) {
        inputManager.readUserInput();
        boolean result;
        try {
           Command currentCommand =  commandFactory.createCommand(inputManager.getCommand());
            result = currentCommand.executeCommand(commandProcessor, this);

            if(!result)
                PrintWriter.print(CommandResult.COMMAND_FAILED.getCommandResultMessage());
        }
        catch (CommandNotImplementedException | ArrayIndexOutOfBoundsException e){
            ErrorLogger.logError(e.toString());
            PrintWriter.print(e.getMessage());
        }
    }

    public Command getCurrentCommand() {
        return currentCommand;
    }

    public void PrintCommandResult(CommandResult cResult, String additionalInformation){
        PrintWriter.print(cResult.getCommandResultMessage() + additionalInformation);
    }

    public void PrintCommandResult(CommandResult cResult){
        PrintWriter.print(cResult.getCommandResultMessage());
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }



    //-----Overrides-----


}
