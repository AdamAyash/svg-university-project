package main.commandlineinterface.commands.shapes.supportedcolors;


//енумерация описваща
public enum SupportedColors {
    RED("red"),
    GREEN("green"),
    BLUE("blue"),
    YELLOW("yellow"),
    PINK("pink"),
    BLACK("black");


    //конкретният цвят
    private final String color;

    private SupportedColors(String color){
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
