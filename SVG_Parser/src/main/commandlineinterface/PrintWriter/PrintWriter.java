package main.commandlineinterface.PrintWriter;

import main.commandlineinterface.CommandLineInterface;
import main.commandlineinterface.commandresult.CommandResult;
import main.commandlineinterface.commands.CloseCommand;
import main.commandlineinterface.commands.OpenCommand;
import main.commandlineinterface.commands.base.Command;

//класът служи за принтиране на конзолата
public class PrintWriter {
    //-----Constants-----

    //-----Members-----

    //-----Constructor-----

    //-----Methods-----

    //принтира съобщението подадено, като параметър на конзолата
    public static void print(String text){
        System.out.println(text);
    }

    //принтира съобщение в зависимост от подадения параметър CommandResult и командата
    public static void print(CommandResult cResult, String additionalInfo){

        switch (cResult)
        {
            case FILE_SUCCESSFULLY_OPENED:
                System.out.println(cResult.getCommandResultMessage() + additionalInfo);
                break;
        }



    }
    //-----Overrides-----
}
