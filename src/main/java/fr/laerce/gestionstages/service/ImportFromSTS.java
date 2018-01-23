package fr.laerce.gestionstages.service;

import fr.laerce.gestionstages.domain.Niveau;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Projet gestionstages
 * Pour LAERCE SAS
 * <p>
 * Créé le  23/01/2018.
 *
 * @author fred
 */
public class ImportFromSTS {
    private DocumentBuilder documentBuilder;
    private Document document;
    private XPath xPath;
    private final String xPathNiveaux = "/STS_EDT/NOMENCLATURES/MEFSTATS4/*";


    public ImportFromSTS() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            XPathFactory xPathFactory = XPathFactory.newInstance();
            xPath = xPathFactory.newXPath();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

    }


    public int parse(InputStream is) {
        int retVal = 0;
        try {
            document = documentBuilder.parse(is);
            for(Niveau niveau: parseNiveaux()){
                System.out.println(niveau);
            }
        } catch (SAXException e) {
            e.printStackTrace();
            retVal = 1;
        } catch (IOException e) {
            e.printStackTrace();
            retVal = 2;
        }

        return retVal;
    }

    private List<Niveau> parseNiveaux(){
        List<Niveau> niveaux = new ArrayList<>();
        try {
            XPathExpression expression = xPath.compile(xPathNiveaux);
            NodeList nl1 = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
            for(int i = 0; i < nl1.getLength() ; i++ ){
                Node n1 = nl1.item(i);
                Niveau niveau = new Niveau();
                if(n1.getNodeType()== Node.ELEMENT_NODE) {
                    NamedNodeMap nnm = n1.getAttributes();
                    String code = nnm.getNamedItem("CODE").getTextContent();
                    niveau.setCode(code);
                    NodeList nl2 = n1.getChildNodes();
                    for(int j=0; j < nl2.getLength() ; j++){
                        Node n2 = nl2.item(j);
                        if(n2.getNodeType()== Node.ELEMENT_NODE){
                            switch (n2.getNodeName()){
                                case "LIBELLE_COURT":
                                    niveau.setLibelleCourt(n2.getTextContent());
                                    break;
                                case "LIBELLE_LONG":
                                    niveau.setLibelleLong(n2.getTextContent());
                                    break;
                            }
                        }
                    }
                    niveaux.add(niveau);
                }
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return niveaux;
    }

    private int parseIndividus(){
        return 0;
    }

    private int parseDisciplines(){
        return 0;
    }

    private int parseDivisions(){
        return 0;
    }




}


