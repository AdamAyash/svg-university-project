package main.commandlineinterface.commands.shapes.supportedcolors;


//енумерация описваща
public enum SupportedColors {
    RED("red");

    //конкретният цвят
    private final String color;

    private SupportedColors(String color){
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
