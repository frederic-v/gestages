package fr.laerce.gestionstages.service;

import fr.laerce.gestionstages.domain.Discipline;
import fr.laerce.gestionstages.domain.Division;
import fr.laerce.gestionstages.domain.Individu;
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
import java.util.*;

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
            for(Niveau niveau: parseNiveaux().values()){
                System.out.println(niveau);
            }
            for(Division division: parseDivisions().values()){
                System.out.println(division);
            }
            for(Discipline discipline: parseDisciplines().values()){
                System.out.println(discipline);
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

    private Map<String,Niveau> parseNiveaux(){
        final String xPathNiveaux = "/STS_EDT/NOMENCLATURES/MEFSTATS4/*";
        Map<String,Niveau> niveaux = new HashMap<>();
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
                    niveaux.put(niveau.getCode(),niveau);
                }
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return niveaux;
    }

    private Map<String,Individu> parseIndividus(){
        final String xPathIndividus = "/STS_EDT/DONNEES/INDIVIDUS/*";
        Map<String,Individu> individus = new HashMap<>();
        try {
            XPathExpression expression = xPath.compile(xPathIndividus);
            NodeList nl1 = (NodeList) expression.evaluate(expression, XPathConstants.NODESET);
            for (int i = 0; i < nl1.getLength() ; i++){
                Node n1 = nl1.item(i);
                if(n1.getNodeType() == Node.ELEMENT_NODE){

                }
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return individus;
    }

    private Map<String,Discipline> parseDisciplines(){
        final String xPathDisciplines = "/STS_EDT/DONNEES/INDIVIDUS/*/DISCIPLINES/*";
        Map<String,Discipline> disciplines = new HashMap<>();
        try {
            XPathExpression expression = xPath.compile(xPathDisciplines);
            NodeList nl1 = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
            for(int i = 0 ; i < nl1.getLength() ; i++){
                Node n1 = nl1.item(i);
                Discipline discipline = new Discipline();
                if(n1.getNodeType() == Node.ELEMENT_NODE){
                    NamedNodeMap nnm = n1.getAttributes();
                    discipline.setCode(nnm.getNamedItem("CODE").getTextContent());
                    NodeList nl2 = n1.getChildNodes();
                    for(int j = 0 ; j < nl2.getLength() ; j++){
                        Node n2 = nl2.item(j);
                        if(n2.getNodeType() == Node.ELEMENT_NODE){
                            switch (n2.getNodeName()){
                                case "LIBELLE_COURT":
                                    discipline.setLibelle(n2.getTextContent());
                                    break;
                            }
                        }

                    }
                }
                disciplines.put(discipline.getCode(),discipline);
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return disciplines;
    }

    private Map<String,Division> parseDivisions(){
        final String xPathDivisions = "/STS_EDT/DONNEES/STRUCTURE/DIVISIONS/*";
        Map<String,Division> divisions = new HashMap<>();
        try {
            XPathExpression expression = xPath.compile(xPathDivisions);
            NodeList nl1 = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
            for(int i = 0; i < nl1.getLength() ; i++){
                Node n1 = nl1.item(i);
                Division division = new Division();
                if(n1.getNodeType() == Node.ELEMENT_NODE){
                    NamedNodeMap nnm = n1.getAttributes();
                    division.setCode(nnm.getNamedItem("CODE").getTextContent());
                    NodeList nl2 = n1.getChildNodes();
                    for(int j = 0 ; j < nl2.getLength() ; j++){
                        Node n2 = nl2.item(j);
                        if(n2.getNodeType() == Node.ELEMENT_NODE){
                            switch (n2.getNodeName()){
                                case "LIBELLE_LONG":
                                    division.setLibelle(n2.getTextContent());
                                    break;
                                case "MEFS_APPARTENANCE":
                                    break;
                            }
                        }
                    }
                }
                divisions.put(division.getCode(),division);
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return divisions;
    }




}


