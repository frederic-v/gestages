package fr.laerce.gestionstages.service;

import fr.laerce.gestionstages.domain.Discipline;
import fr.laerce.gestionstages.domain.Division;
import fr.laerce.gestionstages.domain.Niveau;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImportFromSTSBis {
  private String fileName;
  private DocumentBuilder documentBuilder;
  private Document document;
  private XPath xPath;

  private Map<String, Niveau> dicoNiveaux;
  private Map<String, Discipline> dicoDisciplines;
  private Map<String, Division> dicoDivisions;

  public ImportFromSTSBis() {

  }

  public void parse(String fileName) {
    try {
      this.fileName = fileName;

      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
      documentBuilder = documentBuilderFactory.newDocumentBuilder();
      XPathFactory xPathFactory = XPathFactory.newInstance();
      xPath = xPathFactory.newXPath();
      document = documentBuilder.parse(new FileInputStream(fileName));

      this.dicoNiveaux = buildNiveaux();
      this.dicoDisciplines = buildDisciplines();
      this.dicoDivisions = buildDivisions();

    } catch (Exception e) {
      throw new GSImportSTSException(e.getMessage());
    }
  }

  public Map<String, Niveau> getDicoNiveaux() {
    return dicoNiveaux;
  }

  public Map<String, Discipline> getDicoDisciplines() {
    return dicoDisciplines;
  }

  public String getFileName() {
    return fileName;
  }

  private Map<String, Niveau> buildNiveaux() throws ParserConfigurationException, IOException, SAXException {

    final String xPathNiveaux = "/STS_EDT/NOMENCLATURES/MEFSTATS4/*";

    Map<String, Niveau> niveaux = new HashMap<>();
    try {
      XPathExpression expression = xPath.compile(xPathNiveaux);
      NodeList nl1 = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
      for (int i = 0; i < nl1.getLength(); i++) {
        Node n1 = nl1.item(i);
        Niveau niveau = new Niveau();
        if (n1.getNodeType() == Node.ELEMENT_NODE) {
          NamedNodeMap nnm = n1.getAttributes();
          String code = nnm.getNamedItem("CODE").getTextContent();
          niveau.setCode(code);
          NodeList nl2 = n1.getChildNodes();
          for (int j = 0; j < nl2.getLength(); j++) {
            Node n2 = nl2.item(j);
            if (n2.getNodeType() == Node.ELEMENT_NODE) {
              switch (n2.getNodeName()) {
                case "LIBELLE_COURT":
                  niveau.setLibelleCourt(n2.getTextContent());
                  break;
                case "LIBELLE_LONG":
                  niveau.setLibelleLong(n2.getTextContent());
                  break;
              }
            }
          }
          niveaux.put(code, niveau);
        }
      }
    } catch (XPathExpressionException e) {
      e.printStackTrace();
    }
    return niveaux;
  }

  private Map<String, Discipline> buildDisciplines() {
    final String xPathDisciplines = "/STS_EDT/DONNEES/INDIVIDUS/*/DISCIPLINES/*";
    Map<String, Discipline> disciplines = new HashMap<>();
    try {
      XPathExpression expression = xPath.compile(xPathDisciplines);
      NodeList nl1 = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
      for (int i = 0; i < nl1.getLength(); i++) {
        Node n1 = nl1.item(i);
        if (n1.getNodeType() == Node.ELEMENT_NODE) {
          NamedNodeMap nnm = n1.getAttributes();
          String code = nnm.getNamedItem("CODE").getTextContent();
          if (!disciplines.containsKey(code)) {
            Discipline discipline = new Discipline();
            discipline.setCode(code);
            NodeList nl2 = n1.getChildNodes();
            for (int j = 0; j < nl2.getLength(); j++) {
              Node n2 = nl2.item(j);
              if (n2.getNodeType() == Node.ELEMENT_NODE) {
                switch (n2.getNodeName()) {
                  case "LIBELLE_COURT":
                    discipline.setLibelle(n2.getTextContent());
                    break;
                }
              }
            }
            disciplines.put(discipline.getCode(), discipline);
          }
        }
      }
    } catch (XPathExpressionException e) {
      e.printStackTrace();
    }
    return disciplines;
  }

  private Map<String,Division> buildDivisions(){
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
                  // il faut récupérer le code du premier MEF
                  String codeMEF = "31120009210"; // pour tester le xpath
                  final String xPathValNiveau = "/STS_EDT/NOMENCLATURES/MEFS/MEF[attribute::CODE='"+codeMEF+"']/MEFSTAT4";
                  XPathExpression expression2 = xPath.compile(xPathValNiveau);
                  Node nNiveau = (Node) expression2.evaluate(document, XPathConstants.NODE);
                  System.out.println("codeNiveau = " + nNiveau.getTextContent());
                  // codeNiveau permet d'atteindre l'objet Niveau du dictionnaire des niveaux
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