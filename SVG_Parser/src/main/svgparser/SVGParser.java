package main.svgparser;

import main.commandlineinterface.CommandLineInterface;
import main.commandlineinterface.PrintWriter.PrintWriter;
import main.commandlineinterface.commandresult.CommandResult;
public class SVGParser {

    //-----Constants-----

    //-----Members-----

    //грижи се за генерирането на командите
    private  CommandLineInterface commandLineInterface;
    //грижи се за обработката на командите
    private CommandProcessor commandProcessor;

    //-----Constructor-----
    public SVGParser(){
        this.commandLineInterface = new CommandLineInterface();
        this.commandProcessor = new CommandProcessor();
    }

    //-----Methods-----
    public void start(){

        while(commandLineInterface.isRunning())
        {
            commandLineInterface.run(commandProcessor);
        }
        PrintWriter.print(CommandResult.EXIT_PROGRAM.getCommandResultMessage());
    }

    //-----Overrides-----
}
