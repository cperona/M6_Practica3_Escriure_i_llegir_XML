/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**

<Result number="44" position="7" positionText="7" points="6">
    <Driver driverId="hamilton" code="HAM" url="http://en.wikipedia.org/wiki/Lewis_Hamilton">
        <PermanentNumber>44</PermanentNumber>
        <GivenName>Lewis</GivenName>
        <FamilyName>Hamilton</FamilyName>
        <DateOfBirth>1985-01-07</DateOfBirth>
        <Nationality>British</Nationality>
    </Driver>
    <Constructor constructorId="mercedes" url="http://en.wikipedia.org/wiki/Mercedes-Benz_in_Formula_One">
        <Name>Mercedes</Name>
        <Nationality>German</Nationality>
    </Constructor>
    <Grid>13</Grid>
    <Laps>78</Laps>
    <Status statusId="1">Finished</Status>
    <Time millis="6300141">+15.801</Time>
    <FastestLap rank="5" lap="54">
        <Time>1:15.825</Time>
        <AverageSpeed units="kph">158.433</AverageSpeed>
    </FastestLap>
</Result>
*/
public class ResultadoCarrera {
    private Driver d;
    private String constructor;
    private int initialPos;
    private int finalPos;
    private long timeMillis;
    private int completedLaps;
    private int rankFastestLap;
    private boolean finisher;
    
    public ResultadoCarrera(Element resultElement){
        this.finalPos = Integer.parseInt(resultElement.getAttribute("position"));

        //Guardem els textNodes de les etiquetes que son child directes de <Result>
        this.initialPos = Integer.parseInt(getNodeValue("Grid", resultElement));
        this.completedLaps = Integer.parseInt(getNodeValue("Laps", resultElement));
        this.finisher = (getNodeValue("Status", resultElement)).equals("Finished");
        Element timeElement = (Element) resultElement.getElementsByTagName("Time").item(0);
        if (finisher){
            this.timeMillis = Long.parseLong(timeElement.getAttribute("millis"));
        }

        //Guardem el valor de l'atribut "rank" de l'etiqueta <FastestLap> a la variable "rankFastestLap
        Element fastestLapElement = (Element) resultElement.getElementsByTagName("FastestLap").item(0);
        this.rankFastestLap = Integer.parseInt(fastestLapElement.getAttribute("rank"));

        //Accedim a l'etiqueta <Driver>
        Element driverElement = (Element) resultElement.getElementsByTagName("Driver").item(0);
        //Guardem el text de les etiquetes <GivenName>, <FamilyName>, <PermanentNumber> i <Nationality>
        String name = getNodeValue("GivenName", driverElement);
        String surname = getNodeValue("FamilyName", driverElement);
        String number = getNodeValue("PermanentNumber", driverElement);
        String nationality = getNodeValue("Nationality", driverElement);
        d = new Driver(driverElement);

        //Accedim a l'etiqueta <Constructor>
        Element constructorElement = (Element) resultElement.getElementsByTagName("Constructor").item(0);
        //Guardem l'etiqueta <Name> a la variable constructor
        this.constructor = getNodeValue("Name", constructorElement);
    }

    @Override
    public String toString() {
        String resul= "Resultado de Carrera:\n\t" + d.getName() + " " + d.getSurname() + " conduciendo un " + constructor + 
                "\n\tParte de la posicion: " + initialPos + " y termina en la " + finalPos + 
                "\n\tHa completado "+  completedLaps + " vueltas";
        if (this.finisher)
            resul+= " tardando " +  toHHMMSSmmm(timeMillis);
        else
            resul += " sin completar la carrera";
                
        resul+="\n\tSu clasificación en vuelta rápida personal=" + rankFastestLap;
        return resul;
    }
    
    public static String toHHMMSSmmm(long millis){
        long mmm=millis%1000;
        long seconds=millis/1000;
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        long h = (seconds / (60 * 60)) % 24;
        return String.format("%02d:%02d:%02d:%03d", h,m,s,mmm);
    }

    private static String getNodeValue(String etiqueta, Element element) {
        NodeList nodeList = element.getElementsByTagName(etiqueta).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    public Driver getD() {
        return d;
    }

    public String getConstructor() {
        return constructor;
    }

    public int getInitialPos() {
        return initialPos;
    }

    public int getFinalPos() {
        return finalPos;
    }

    public long getTimeMillis() {
        return timeMillis;
    }

    public int getCompletedLaps() {
        return completedLaps;
    }

    public int getRankFastestLap() {
        return rankFastestLap;
    }

    public boolean isFinisher() {
        return finisher;
    }
    
      
      
}
