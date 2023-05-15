package main.commandlineinterface;

import com.sun.source.tree.ReturnTree;
import main.commandlineinterface.PrintWriter.PrintWriter;
import main.commandlineinterface.commandfactory.CommandFactory;
import main.commandlineinterface.commandresult.CommandResult;
import main.commandlineinterface.commands.base.Command;
import main.commandlineinterface.inputmanager.InputManager;
import main.errorlogger.ErrorLogger;
import main.exceptions.CommandNotImplementedException;

//класът описва работата и функционалностите на интерфейса
public final class CommandLineInterface {
    //-----Constants-----

    //-----Members-----
    //член променлива описваща обект от тип InputManager
    private InputManager inputManager;

    //член променлива описваща обект от тип CommandFactory, служещ за създаването на съответните команди.
    private CommandFactory commandFactory;

    private Command currentCommand;


    //-----Constructor-----
    public  CommandLineInterface(){
        this.inputManager = new InputManager();
        this.commandFactory = new CommandFactory();
    }
    //-----Methods-----
    public InputManager getInputManager() {
        return inputManager;
    }

    //стартираща фунция на класът
    public CommandResult run() {
        inputManager.readUserInput();
        CommandResult cResult;
        try {
           Command currentCommand =  commandFactory.createCommand(inputManager.getCommand());
           cResult = currentCommand.executeCommand();

           if(cResult == CommandResult.COMMAND_SUCCESSFUL){
               this.currentCommand = currentCommand;
           }
        }
        catch (CommandNotImplementedException | ArrayIndexOutOfBoundsException e){
            ErrorLogger.logError(e.toString());
            PrintWriter.print(e.getMessage());
            cResult = CommandResult.COMMAND_FAILED;
        }

        return cResult;
    }

    public Command getCurrentCommand() {
        return currentCommand;
    }

    //-----Overrides-----


}
