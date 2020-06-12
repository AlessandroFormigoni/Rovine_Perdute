package it.unibs.ing.arnaldo.rovineperdute;

import java.io.FileOutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

public class WriteFile {

	static String version = "1.0";
	static String encoding = "UTF-8";
	static String filename = "Routes.xml";
	static XMLOutputFactory xmlof = null;
	static XMLStreamWriter xmlw = null;
	
	/**<b>set up instance of </b> {@linkplain XMLStreamWriter}   
	 *<b> to initialize file reader </b>
	 */
	public static void initializeWriter() {

		try {
			xmlof = XMLOutputFactory.newInstance();
			xmlw = xmlof.createXMLStreamWriter(new FileOutputStream(filename),encoding);
			xmlw.writeStartDocument(encoding, version);
		} catch (Exception e) { 
			System.out.println("Errore nell'inizializzazione del writer:");
			System.out.println(e.getMessage());
		}
	}
	
	public static void printFile() {
		
		Dijkstra.dijkstra(ReadFile.getGraph(), ReadFile.getGraph().cityFromID(0), 0); // planar
		
		String[] autori = {"Simone Macobatti, ", "Alessandro Formigoni, ", "Francesca Gambi "}; 
		
		try { 
			xmlw.writeComment("LISTA AUTORI: " + autori[0] + autori[1] + autori[2]);
			xmlw.writeStartElement("routes"); 
			xmlw.writeStartElement("route");
			xmlw.writeAttribute("team", "Tonatiuh"); 
			xmlw.writeAttribute("cost", Double.toString(Dijkstra.getDist()[ReadFile.size - 1]));
			xmlw.writeAttribute("cities", Integer.toString(Dijkstra.getRoute().size()));
			for (City city : Dijkstra.getRoute()) {
				xmlw.writeStartElement("city"); 
				xmlw.writeAttribute("id", Integer.toString(city.getId())); 
				xmlw.writeAttribute("name", city.getName());
				xmlw.writeEndElement();
			}
			xmlw.writeEndElement();
		} catch (Exception e) {
			System.out.println("Errore nella scrittura");
		}
		
		Dijkstra.dijkstra(ReadFile.getGraph(), ReadFile.getGraph().cityFromID(0), 1); // vertical
		
		try {  
			xmlw.writeStartElement("route");
			xmlw.writeAttribute("team", "Metztli"); 
			xmlw.writeAttribute("cost", Double.toString(Dijkstra.getDist()[ReadFile.size - 1]));
			xmlw.writeAttribute("cities", Integer.toString(Dijkstra.getRoute().size()));
			for (City city : Dijkstra.getRoute()) {
				xmlw.writeStartElement("city"); 
				xmlw.writeAttribute("id", Integer.toString(city.getId())); 
				xmlw.writeAttribute("name", city.getName());
				xmlw.writeEndElement();
			}
			xmlw.writeEndElement();
			xmlw.writeEndElement();
			xmlw.flush();
			xmlw.close();
		} catch (Exception e) {
			System.out.println("Errore nella scrittura");
		}
	}
}
