package main.errorlogger;

import main.commandlineinterface.commandresult.CommandResult;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

//клас служещ за логване на грешки
public final class ErrorLogger {


    //-----Constants-----
    private static final String FILE_NAME = "log.txt";

    //-----Members-----

    //-----Constructor-----

    //-----Methods-----
    public static void logError(final String exceptionMessage){
        try {
            Date currentDate = new Date();
            FileWriter fileWriter = new FileWriter(FILE_NAME, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println("[" + currentDate.toString() +"]" + " " + exceptionMessage +  "\n" );
            printWriter.close();

        }
        catch(IOException e){
            main.commandlineinterface.PrintWriter.PrintWriter.print(CommandResult.ERROR_LOGGER_FAILED.getCommandResultMessage());
        }
    //-----Overrides----

    }
}
