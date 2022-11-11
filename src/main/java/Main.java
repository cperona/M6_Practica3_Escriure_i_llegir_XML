import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, TransformerException, SAXException {
        String[] moduls = {"Acces a Dades", "Programació de serveis i processos"," Desenvolupament d' interficies", "Programació multimedia i dispositius mobils", "Sistemes de gestio empresarial", "Formacio i orientacio laboral"};

        boolean[] permetDUAL = {false, true, false, false, true, true};
        int[] hores = {96, 93, 96, 95, 55, 63};
        double[] notes = {8.45, 9.0, 8.0, 7.34, 8.2, 7.4};

        //----Part 1
        FileWriter fileWriter = new FileWriter("src/main/resources/moduls.xml");
        DocumentBuilder builderEscriptura = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document documentEscriptura = builderEscriptura.newDocument();

        //Creació d'elements
        //-Element pare "moduls":
        Element elementModuls = documentEscriptura.createElement("moduls");
        documentEscriptura.appendChild(elementModuls);
        //-Elements fills "modul":
        for (int i = 0; i < moduls.length; i++) {
            Element elementModul = documentEscriptura.createElement("modul");
            elementModuls.appendChild(elementModul);

            elementModul.setAttribute("hores", Integer.toString(hores[i]));
            elementModul.setAttribute("permetDUAL", Boolean.toString(permetDUAL[i]));

            Element elementModulNom = documentEscriptura.createElement("nom");
            Text textNom = documentEscriptura.createTextNode(moduls[i]);
            elementModulNom.appendChild(textNom);
            elementModul.appendChild(elementModulNom);

            Element elementModulNota = documentEscriptura.createElement("nota");
            Text textNota = documentEscriptura.createTextNode(Double.toString(notes[i]));
            elementModulNota.appendChild(textNota);
            elementModul.appendChild(elementModulNota);
        }

        //Escriure el resultat a l'arxiu xml "moduls.xml"
        Source source = new DOMSource(documentEscriptura);
        Result result = new StreamResult(fileWriter);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT ,"yes");
        transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "5");
        transformer.transform(source, result);


        //----Part 2
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/monaco_2017.xml");
        DocumentBuilder builderLectura = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document documentLectura = builderLectura.parse(fileInputStream);

        //Guardem totes les tag <Result> a un NodeList
        NodeList monacoResultNodeList = documentLectura.getElementsByTagName("Result");
        //Bucle que s'executa per a cada etiqueta <Result> dins del monacoResultNodeList
        for (int i1 = 0; i1 < monacoResultNodeList.getLength(); i1++){
            //Guardem <Result> a un Node, el convertim a Element i guardem l'atribut "position" a la variable finalPos
            Node resultNode = monacoResultNodeList.item(i1);
            Element resultElement = (Element) resultNode;
            String finalPos = resultElement.getAttribute("position");

            //Guardem els textNodes de les etiquetes que son child directes de <Result>
            String initialPos = getNodeValue("Grid", resultElement);
            String completedLaps = getNodeValue("Laps", resultElement);
            boolean finisher = (getNodeValue("Status", resultElement)).equals("Finished");
            String timeMillis = getNodeValue("Time", resultElement);

            //Accedim als nodes child de <Result>
            NodeList resultChildsNodeList = resultNode.getChildNodes();

            //Guardem el valor de l'atribut "rank" de l'etiqueta <FastestLap> a la variable "rankFastestLap
            Node fastestLapNode = resultChildsNodeList.item(resultChildsNodeList.getLength()-1); //Última pos del NodeList
            Element fastestLapElement = (Element) fastestLapNode;
            String rankFastestLap = fastestLapElement.getAttribute("rank");

            //Accedim a l'etiqueta <Driver>
            Node driverNode = resultChildsNodeList.item(0);
            Element driverElement = (Element) driverNode;
            //Guardem el text de les etiquetes <GivenName>, <FamilyName>, <PermanentNumber> i <Nationality>
            String name = getNodeValue("GivenName", driverElement);
            String surname = getNodeValue("FamilyName", driverElement);
            String number = getNodeValue("PermanentNumber", driverElement);
            String nationality = getNodeValue("Nationality", driverElement);

            //Accedim a l'etiqueta <Constructor>
            Node constructorNode = resultChildsNodeList.item(1);
            Element constructorElement = (Element) constructorNode;
            //Guardem l'etiqueta <Name> a la variable constructor
            String constructor = getNodeValue("Name", constructorElement);

            //Imprimim tot per pantalla
            System.out.println("finalPos = " + finalPos);
            System.out.println("initialPos = " + initialPos);
            System.out.println("completedLaps = " + completedLaps);
            System.out.println("finisher = " + finisher);
            System.out.println("timeMillis = " + timeMillis);
            System.out.println("rankFastesLap = " + rankFastestLap);
            System.out.println("name = " + name);
            System.out.println("surname = " + surname);
            System.out.println("number = " + number);
            System.out.println("nationality = " + nationality);
            System.out.println("constructor = " + constructor);
            System.out.println();
        }
    }

    private static String getNodeValue(String etiqueta, Element element) {
        NodeList nodeList = element.getElementsByTagName(etiqueta).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }
}
