package main.commandlineinterface.commands.shapes;

//базов клас за фигура описващ цвета на фигурата
public class BasicShape {

    //-----Constants-----

    //-----Members-----
    //цветът на фигурата
    private String color;

    //-----Constructor-----

    //-----Methods-----

    public String getColor() {
        return color;
    }

    public void setColor(String color, boolean isColorless) {
        if(!isColorless)
            this.color = color;
    }

    //-----Overrides----




}
