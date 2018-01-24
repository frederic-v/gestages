package fr.laerce.gestionstages.service;

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
  private Map<String, Niveau> dicoNiveaux;
  private String fileName;

  public ImportFromSTSBis() {
  }

  public void setFileName(String fileName) {
    try {
      this.fileName = fileName;
      this.dicoNiveaux = buildNiveaux();
    } catch (Exception e) {
      throw new STSImportException(e.getMessage());
    }
  }

  public Map<String, Niveau> getDicoNiveaux() {
    return dicoNiveaux;
  }

  public String getFileName() {
    return fileName;
  }

  private Map<String, Niveau> buildNiveaux() throws ParserConfigurationException, IOException, SAXException {

    final String xPathNiveaux = "/STS_EDT/NOMENCLATURES/MEFSTATS4/*";

    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
    XPathFactory xPathFactory = XPathFactory.newInstance();
    XPath xPath = xPathFactory.newXPath();

    Document document = documentBuilder.parse(new FileInputStream(fileName));

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
}
