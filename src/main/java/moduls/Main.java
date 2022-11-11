package moduls;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, TransformerException {
        String[] moduls = {"Acces a Dades", "Programació de serveis i processos"," Desenvolupament d' interficies", "Programació multimedia i dispositius mobils", "Sistemes de gestio empresarial", "Formacio i orientacio laboral"};

        boolean[] permetDUAL = {false, true, false, false, true, true};
        int[] hores = {96, 93, 96, 95, 55, 63};
        double[] notes = {8.45, 9.0, 8.0, 7.34, 8.2, 7.4};

        //----Part 1
        FileWriter fileWriter = new FileWriter("src/main/resources/moduls.xml");
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.newDocument();

        //Creació d'elements
        //-Element pare "moduls":
        Element elementModuls = document.createElement("moduls");
        document.appendChild(elementModuls);
        //-Elements fills "modul":
        for (int i = 0; i < moduls.length; i++) {
            Element elementModul = document.createElement("modul");
            elementModuls.appendChild(elementModul);

            elementModul.setAttribute("hores", Integer.toString(hores[i]));
            elementModul.setAttribute("permetDUAL", Boolean.toString(permetDUAL[i]));

            Element elementModulNom = document.createElement("nom");
            Text textNom = document.createTextNode(moduls[i]);
            elementModulNom.appendChild(textNom);
            elementModul.appendChild(elementModulNom);

            Element elementModulNota = document.createElement("nota");
            Text textNota = document.createTextNode(Double.toString(notes[i]));
            elementModulNota.appendChild(textNota);
            elementModul.appendChild(elementModulNota);
        }

        //Escriure el resultat a l'arxiu xml "moduls.xml"
        Source source = new DOMSource(document);
        Result result = new StreamResult(fileWriter);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT ,"yes");
        transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "5");
        transformer.transform(source, result);


        //----Part 2
        Source source1  = new DOMSource()

    }
}
