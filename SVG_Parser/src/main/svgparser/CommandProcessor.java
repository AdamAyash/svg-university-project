package main.svgparser;
import main.commandlineinterface.PrintWriter.PrintWriter;
import main.commandlineinterface.commandresult.CommandResult;
import main.commandlineinterface.commands.*;
import main.commandlineinterface.commands.base.Command;
import main.commandlineinterface.commands.shapes.*;
import main.commandlineinterface.commands.shapes.supportedshapes.SupportedShapes;
import main.commandlineinterface.commands.EraseCommand;
import main.errorlogger.ErrorLogger;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//класът служи за обработка на генерираните команди
public class CommandProcessor {

    //-----Constants-----

    //оказва името на главния таг в svg файла
    private final String ROOT_ELEMENT = "svg";

    //-----Members-----
    //оказва дали има отворен файл в момента
    private boolean isFileOpened;
    private List<Element> shapesList;

    //представкява буфер където ще се зареждат данните от svg/xml файловете
    private Document currentDocument;

    //предтавлява файл или директория
    private  File currentFile;

    //-----Constructor-----
   public CommandProcessor(){
       shapesList = new ArrayList<>();
       this.isFileOpened = false;
   }

    //-----Methods-----

    //създава или отваря вече съществуващ файл
    public boolean openFile(final Command currentCommand){
        OpenCommand openCommand = (OpenCommand) currentCommand;
       if(!isFileOpened) {
           currentFile = new File(((OpenCommand) currentCommand).getFilePath());
           DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
           DocumentBuilder documentBuilder;
           try {
               documentBuilder = documentBuilderFactory.newDocumentBuilder();
               if (currentFile.exists()) {
                   currentDocument = documentBuilder.parse(openCommand.getFilePath());
                   if(!loadAllShapes())
                       return false;
               } else {
                   currentDocument = documentBuilder.newDocument();
                   Element svgRootElement = currentDocument.createElement(ROOT_ELEMENT);
                   currentDocument.appendChild(svgRootElement);
               }
           } catch (ParserConfigurationException | IOException | SAXException |
                    IllegalArgumentException | NullPointerException e) {

               ErrorLogger.logError(e.toString());
               return false;
           }
           isFileOpened = true;
           openCommand.getCommandLineInterface().PrintCommandResult(CommandResult.FILE_SUCCESSFULLY_OPENED, openCommand.getFilePath());
       }else {
           openCommand.getCommandLineInterface().PrintCommandResult(CommandResult.FILE_ALREADY_OPENED);
       }
        return true;
    }


    public boolean checkIfShapeIsWithinAnotherShape(final Command command){
        if(!isFileOpened) {
            PrintWriter.print(CommandResult.FILE_NOT_OPENED.getCommandResultMessage());
            return false;
        }
        WithinCommand withinCommand = (WithinCommand)command;

        BasicShape shape = withinCommand.getShape();

        for(int i = 0; i < shapesList.size(); i++) {
                Element currentShape = (Element) shapesList.get(i);

                if(currentShape.getTagName().equals(SupportedShapes.RECTANGLE.getSvgTag())) {
                        double x = Double.parseDouble(currentShape.getAttribute("x"));
                        double y = Double.parseDouble(currentShape.getAttribute("y"));
                        double width = Double.parseDouble(currentShape.getAttribute("width"));
                        double height = Double.parseDouble(currentShape.getAttribute("height"));

                            if(shape instanceof  RectangleShape) {
                                if (ShapeUtilities.isRectangleWithinRectangle(x, y, width, height, ((RectangleShape) shape).getXCoordinate(), ((RectangleShape) shape).getYCoordinate(), ((RectangleShape) shape).getWidth(), ((RectangleShape) shape).getHeight())) {
                                    print(i);
                                }
                            }

                            if(shape instanceof  CircleShape) {
                             if (ShapeUtilities.isRectangleWithinCircle(((CircleShape) shape).getCenterXCoordinate(), ((CircleShape) shape).getCenterYCoordinate(), ((CircleShape) shape).getRadius(), x, y, width, height)) {
                                 print(i);
                             }
                         }
                }
                   if(currentShape.getTagName().equals(SupportedShapes.CIRCLE.getSvgTag())) {
                       double cx = Double.parseDouble(currentShape.getAttribute("cx"));
                       double cy = Double.parseDouble(currentShape.getAttribute("cy"));
                       double radius = Double.parseDouble(currentShape.getAttribute("r"));

                       if(shape instanceof RectangleShape) {
                           double closestX = Math.max((((RectangleShape) shape).getXCoordinate()), Math.min(cx, ((RectangleShape) shape).getXCoordinate() + ((RectangleShape) shape).getWidth()));
                           double closestY = Math.max(((RectangleShape) shape).getYCoordinate(), Math.min(cy, ((RectangleShape) shape).getYCoordinate() + ((RectangleShape) shape).getHeight()));

                           double distance =  Math.sqrt(Math.pow(cx - closestX, 2) + Math.pow(cy - closestY, 2));

                           if (distance <= radius)
                               print(i);
                       }

                       if(shape instanceof CircleShape){
                           double distance = Math.sqrt(Math.pow(((CircleShape) shape).getCenterXCoordinate() - cx, 2) + Math.pow(((CircleShape) shape).getCenterYCoordinate() - cy, 2));

                           if(distance + radius <= ((CircleShape) shape).getRadius())
                               print(i);
                       }
                   }

                   if(currentShape.getTagName().equals(SupportedShapes.LINE.getSvgTag())){

                       double firstXCoordinate = Double.parseDouble(currentShape.getAttribute("x1"));
                       double secondXCoordinate = Double.parseDouble(currentShape.getAttribute("x2"));
                       double  firstYCoordinate = Double.parseDouble(currentShape.getAttribute("y1"));
                       double secondYCoordinate = Double.parseDouble(currentShape.getAttribute("y2"));

                       if(shape instanceof RectangleShape) {
                           if (ShapeUtilities.isLineWithinRectangle(((RectangleShape) shape).getXCoordinate(), ((RectangleShape) shape).getYCoordinate(), ((RectangleShape) shape).getWidth(), ((RectangleShape) shape).getHeight(), firstXCoordinate, secondXCoordinate, firstYCoordinate, secondYCoordinate))
                               print(i);
                       }

                       if(shape instanceof CircleShape){
                           double startDistanceSquared = Math.pow(firstXCoordinate - ((CircleShape) shape).getCenterXCoordinate(), 2) + Math.pow(secondXCoordinate - ((CircleShape) shape).getCenterYCoordinate(), 2);
                           double endDistanceSquared = Math.pow(secondXCoordinate - ((CircleShape) shape).getCenterXCoordinate(), 2) + Math.pow(secondYCoordinate - ((CircleShape) shape).getCenterYCoordinate(), 2);

                           if (startDistanceSquared <= Math.pow(((CircleShape) shape).getRadius(), 2) && endDistanceSquared <= Math.pow(((CircleShape) shape).getRadius(), 2))
                               print(i);
                           }
                       }
                    }
        return true;
    }

    private void checkShapeTypeAndTranslate(Element currentShape, TranslateCommand translateCommand){
        switch (currentShape.getTagName()){
            case "rect":
                currentShape.setAttribute("y", String.valueOf(translateCommand.getVerticalParameterValue() + Double.parseDouble(currentShape.getAttribute("y"))));
                currentShape.setAttribute("x", String.valueOf(translateCommand.getHorizontalParameterValue() + Double.parseDouble(currentShape.getAttribute("x"))));
                break;
            case "circle":
                currentShape.setAttribute("cy", String.valueOf(translateCommand.getVerticalParameterValue() + Double.parseDouble(currentShape.getAttribute("cy"))));
                currentShape.setAttribute("cx", String.valueOf(translateCommand.getHorizontalParameterValue() + Double.parseDouble(currentShape.getAttribute("cx"))));
                break;
            case "line":
                currentShape.setAttribute("x1", String.valueOf(translateCommand.getVerticalParameterValue() + Double.parseDouble(currentShape.getAttribute("x1"))));
                currentShape.setAttribute("x2", String.valueOf(translateCommand.getHorizontalParameterValue() + Double.parseDouble(currentShape.getAttribute("x2"))));
                currentShape.setAttribute("y1", String.valueOf(translateCommand.getVerticalParameterValue() + Double.parseDouble(currentShape.getAttribute("y1"))));
                currentShape.setAttribute("y2", String.valueOf(translateCommand.getHorizontalParameterValue() + Double.parseDouble(currentShape.getAttribute("y2"))));
                break;
        }
    }

    public boolean translate(final Command command){
        if(!isFileOpened) {
            PrintWriter.print(CommandResult.FILE_NOT_OPENED.getCommandResultMessage());
            return false;
        }

        try{
            TranslateCommand translateCommand = (TranslateCommand)command;

            Element rootElement = getRootElement();
            if(rootElement == null)
                return false;

            Element currentShape;
            NodeList shapes = rootElement.getChildNodes();
            if(translateCommand.isTranslateAllShapes()){
                for(int i = 0; i < this.shapesList.size(); i++){
                     currentShape = this.shapesList.get(i);
                    checkShapeTypeAndTranslate(currentShape, translateCommand);
                }
                PrintWriter.print(CommandResult.SUCCESSFULLY_TRANSLATED_ALL_FIGURES.getCommandResultMessage());
            }else {
                currentShape = this.shapesList.get(translateCommand.getIndexOfShapeTobeErased() - 1);
                checkShapeTypeAndTranslate(currentShape, translateCommand);
                PrintWriter.print(CommandResult.SUCCESSFULLY_TRANSLATED_SHAPE.getCommandResultMessage() + "(" + translateCommand.getIndexOfShapeTobeErased() + ")");
            }
        }
        catch (IndexOutOfBoundsException | NullPointerException e){
            ErrorLogger.logError(e.toString());
            return false;
        }

        return true;
    }

    //методът прави проверка кава ще бъде фигурата която ще се създава и изиква съответният метод
    public boolean createShape(final Command command){
        if(!isFileOpened) {
            PrintWriter.print(CommandResult.FILE_NOT_OPENED.getCommandResultMessage());
            return false;
        }

        CreateCommand createCommand = (CreateCommand)command;

        if(createCommand.getShape() instanceof RectangleShape)
            return createRectangle(createCommand);

        if(createCommand.getShape() instanceof CircleShape)
            return createCircle(createCommand);

        if(createCommand.getShape() instanceof LineShape)
            return createLine(createCommand);

       return false;
    }

    //методът създава правоъгълник и го добавя в отворения файл
    private boolean createRectangle(CreateCommand command){

        Element rootElement = getRootElement();
        if(rootElement == null)
            return false;

        RectangleShape rectangleShape =  (RectangleShape)command.getShape();

        Element rectangleElement = currentDocument.createElement("rect");
        rectangleElement.setAttribute("x", String.valueOf(rectangleShape.getXCoordinate()));
        rectangleElement.setAttribute("y", String.valueOf(rectangleShape.getYCoordinate()));
        rectangleElement.setAttribute("width", String.valueOf(rectangleShape.getWidth()));
        rectangleElement.setAttribute("height", String.valueOf(rectangleShape.getHeight()));
        rectangleElement.setAttribute("fill", rectangleShape.getColor());
        rootElement.appendChild(rectangleElement);
        if(!this.shapesList.add(rectangleElement))
            return  false;

        PrintWriter.print(CommandResult.SHAPE_SUCCESSFULLY_CREATED.getCommandResultMessage() + "rectangle" + " (" + shapesList.size() + ")");
        return true;
    }

    private boolean createLine(final CreateCommand command){

        Element rootElement = getRootElement();
        if(rootElement == null)
            return false;

        LineShape lineShape =  (LineShape)command.getShape();

        Element circleElement = currentDocument.createElement("line");
        circleElement.setAttribute("x1", String.valueOf(lineShape.getFirstXCoordinate()));
        circleElement.setAttribute("y1", String.valueOf(lineShape.getFirstYCoordinate()));
        circleElement.setAttribute("x2", String.valueOf(lineShape.getSecondXCoordinate()));
        circleElement.setAttribute("y2", String.valueOf(lineShape.getSecondYCoordinate()));
        circleElement.setAttribute("stroke", lineShape.getColor());
        rootElement.appendChild(circleElement);
        if(!this.shapesList.add(circleElement))
            return false;
        PrintWriter.print(CommandResult.SHAPE_SUCCESSFULLY_CREATED.getCommandResultMessage() + "line" + " (" + shapesList.size() + ")");
        return true;
    }

    private boolean createCircle(final CreateCommand command){
        Element rootElement = getRootElement();
        if(rootElement == null)
            return false;

        CircleShape circleShape =  (CircleShape)command.getShape();

        Element circleElement = currentDocument.createElement("circle");
        circleElement.setAttribute("cx", String.valueOf(circleShape.getCenterXCoordinate()));
        circleElement.setAttribute("cy", String.valueOf(circleShape.getCenterYCoordinate()));
        circleElement.setAttribute("r", String.valueOf(circleShape.getRadius()));
        circleElement.setAttribute("fill", circleShape.getColor());
        rootElement.appendChild(circleElement);
       if(!this.shapesList.add(circleElement))
           return false;

        PrintWriter.print(CommandResult.SHAPE_SUCCESSFULLY_CREATED.getCommandResultMessage() + "circle" + " (" + shapesList.size() + ")");
        return true;
    }

    //връща обкет от тип елемент, който реално е svg тагът на него добавяме самите фигури, като child nodes
    private Element getRootElement() throws NullPointerException{
        Element rootElement = null;
        NodeList rootNodeList = currentDocument.getElementsByTagName(ROOT_ELEMENT);
        Node rootNode = rootNodeList.item(0);
        if(rootNode.getNodeType() == Node.ELEMENT_NODE){
            rootElement = (Element)rootNode;
        }

        return rootElement;
    }
    private boolean isShapeSupported(String shapeTagName) {
        boolean result = false;
        for (SupportedShapes supportedShape :
                SupportedShapes.values()) {
            if (supportedShape.getSvgTag().equals(shapeTagName)) {
                result = true;
            }
        }
        return result;
    }

    private boolean loadAllShapes(){
       boolean result = true;
       try {
           Element root = getRootElement();
           NodeList shapeList = root.getChildNodes();
           for (int i = 0; i < shapeList.getLength(); i++) {
               Node currentShape = shapeList.item(i);
               if (currentShape.getNodeType() == Node.ELEMENT_NODE) {
                   Element shapeElement = (Element) currentShape;
                   if (isShapeSupported(shapeElement.getTagName())) {
                       this.shapesList.add(shapeElement);
                   }
               }
           }
       }
       catch (ClassCastException | ArrayIndexOutOfBoundsException | NullPointerException e){
           ErrorLogger.logError(e.toString());
           result = false;
       }

       return result;
    }

    public boolean eraseShape(final Command currentCommand){
        if(!isFileOpened) {
            PrintWriter.print(CommandResult.FILE_NOT_OPENED.getCommandResultMessage());
            return false;
        }
        try {
            EraseCommand eraseCommand = (EraseCommand) currentCommand;
            if(eraseCommand.getEraseShapeIndex() > shapesList.size() - 1 || eraseCommand.getEraseShapeIndex() < 0){
                PrintWriter.print(CommandResult.ERASE_SHAPE_NOT_FOUND.getCommandResultMessage() + (eraseCommand.getEraseShapeIndex() + 1));
                return false;
            }

            Element shapeToBeErased = this.shapesList.remove(eraseCommand.getEraseShapeIndex());

            shapeToBeErased.getParentNode().removeChild(shapeToBeErased);
            PrintWriter.print("Erased a " + shapeToBeErased.getNodeName() + " (" + (eraseCommand.getEraseShapeIndex() + 1) + ")");
        }
        catch (ClassCastException | NullPointerException  | IndexOutOfBoundsException e){
            ErrorLogger.logError(e.toString());
            return false;
        }

        return true;
    }


    //принтира една фигура по подаден индекс
    private void print(int index) throws ArrayIndexOutOfBoundsException , NullPointerException{
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((index+1) +  ". " + this.shapesList.get(index).getTagName() + " ");
        NamedNodeMap shapeAttributes = this.shapesList.get(index).getAttributes();
        for (int j = 0; j < shapeAttributes.getLength(); j++) {
            stringBuilder.append(shapeAttributes.item(j).getNodeValue() + " ");
        }
        PrintWriter.print(stringBuilder.toString());
    }

    public boolean printAllShapes(){
        if(!isFileOpened) {
            PrintWriter.print(CommandResult.FILE_NOT_OPENED.getCommandResultMessage());
            return false;
        }
       try {
           for (int i = 0; i < this.shapesList.size(); i++) {
               print(i);
           }
       }
       catch (ArrayIndexOutOfBoundsException  | NullPointerException e){
           ErrorLogger.logError(e.toString());
           return false;
       }
        return true;
    }

    //записва промените направени в будера във файла
    public boolean saveFile() {
        if(!isFileOpened) {
            PrintWriter.print(CommandResult.FILE_NOT_OPENED.getCommandResultMessage());
            return false;
        }
        try {
            DOMSource source = new DOMSource(currentDocument);

            Result streamResult = new StreamResult(currentFile);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, streamResult);
        }
        catch (TransformerException | NullPointerException e){
            ErrorLogger.logError(e.toString());
            return false;
        }
        PrintWriter.print(CommandResult.FILE_SAVED_SUCCESSFULLY.getCommandResultMessage() + currentFile.getName());
        closeCurrentFile();

        return true;

    }



    //затваря текущия файл, без да запазва промените по него
    public boolean closeCurrentFile(){
        if(!isFileOpened) {
            PrintWriter.print(CommandResult.FILE_NOT_OPENED.getCommandResultMessage());
            return false;
        }
        currentDocument = null;
        isFileOpened = false;
        shapesList.removeAll(shapesList);
        PrintWriter.print(CommandResult.FILE_CLOSED_SUCCESSFULLY.getCommandResultMessage() + currentFile.getName());
        return true;
    }

    //-----Overrides----
}
