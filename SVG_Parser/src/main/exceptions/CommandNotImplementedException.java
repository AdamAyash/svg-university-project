package main.exceptions;

//класът описва изключение за неподдържана команда
public class CommandNotImplementedException extends Exception{

    //-----Constants-----

    //-----Members-----

    //-----Constructor-----
    public CommandNotImplementedException(String errorMessage){
        super(errorMessage);
    }
    //-----Methods-----

    //-----Overrides-----
}
