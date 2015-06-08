package org.grameen.vendor.xml;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.grameen.vendor.data.ItemInfo;
import org.grameen.vendor.data.MarketInfo;
import org.grameen.vendor.data.UnitInfo;
import org.grameen.vendor.models.Vendor;
import org.grameen.vendor.remote.GetMarketVendorItems;
import org.grameen.vendor.remote.MarketItems;
import org.grameen.vendor.remote.MarketUnits;
import org.grameen.vendor.remote.RegisteredMarkets;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Irama on 6/6/2015.
 */
public class XmlParser {

    public static List<MarketInfo> getMarkets(Context context){
        List<MarketInfo> marketList = new ArrayList<>();
        try {
            AsyncTask<String, Integer, String> registeredMarkets = new RegisteredMarkets(context).execute();
            String xmlData = registeredMarkets.get().trim();
            if(xmlData.length() > 0) {
                Document doc = XmlParser.string2Doc(xmlData);
                Element rootElement = doc.getDocumentElement();
                NodeList nodeList = rootElement.getChildNodes();
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node childNode = nodeList.item(i);
                    if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element childElement = (Element) childNode;
                        int marketID = Integer.parseInt(childElement.getAttribute("id"));
                        String marketName = childElement.getTextContent();
                        marketList.add(new MarketInfo(marketID, marketName));
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return marketList;
    }

    public static List<ItemInfo> getItems(Context context){
        List<ItemInfo> itemList = new ArrayList<ItemInfo>();
        try{
            AsyncTask<String, Integer, String> marketItems = new MarketItems(context).execute();
            String xmlData = marketItems.get().trim();
            if(xmlData.length() > 0){
                Document doc = XmlParser.string2Doc(xmlData);
                Element rootElement = doc.getDocumentElement();
                NodeList nodeList = rootElement.getChildNodes();
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node childNode = nodeList.item(i);
                    if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element childElement = (Element) childNode;
                        int itemID = Integer.parseInt(childElement.getAttribute("id"));
                        String itemName= childElement.getTextContent();
                        itemList.add(new ItemInfo(itemID, itemName));
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return itemList;
    }

    public static List<ItemInfo> getMarketVendorItems(Context context, int marketID){
        List<ItemInfo> itemList = new ArrayList<>();
        try{
            AsyncTask<String, Integer, String> vendorItems = new GetMarketVendorItems(marketID).execute();
            String xmlData = vendorItems.get().trim();
            if(xmlData.length() > 0){
                Document doc = XmlParser.string2Doc(xmlData);
                Element rootElement = doc.getDocumentElement();
                NodeList nodeList = rootElement.getChildNodes();
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node childNode = nodeList.item(i);
                    if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element childElement = (Element) childNode;
                        int ID = Integer.parseInt(childElement.getAttribute("id"));
                        NodeList nodeList1 = childNode.getChildNodes();
                        List<String> data = new ArrayList<>();
                        for(int j = 0; j < nodeList1.getLength(); j++){
                            Node childNode1 = nodeList1.item(j);
                            if(childNode1.getNodeType() == Node.ELEMENT_NODE){
                                Element childElement1 = (Element)childNode1;
                                data.add(childElement1.getTextContent());
                            }
                        }
                        ItemInfo itemInfo = new ItemInfo();
                        itemInfo.setID(ID);
                        itemInfo.setItemName(data.get(0));
                        itemInfo.setPrice(Integer.parseInt(data.get(1)));
                        itemInfo.setUnit(data.get(2));
                        itemInfo.setVendorName(data.get(3));
                        itemInfo.setStallNumber(data.get(4));
                        itemList.add(itemInfo);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return itemList;
    }

    public static List<ItemInfo> getVendorItems(Context context){
        List<ItemInfo> itemList = new ArrayList<>();
        Vendor vendor = new Vendor(context);
        String appId = vendor.getAppID();
        try{
            AsyncTask<String, Integer, String> vendorItems = new GetMarketVendorItems(appId).execute();
            String xmlData = vendorItems.get().trim();
            if(xmlData.length() > 0){
                Document doc = XmlParser.string2Doc(xmlData);
                Element rootElement = doc.getDocumentElement();
                NodeList nodeList = rootElement.getChildNodes();
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node childNode = nodeList.item(i);
                    if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element childElement = (Element) childNode;
                        int ID = Integer.parseInt(childElement.getAttribute("id"));
                        NodeList nodeList1 = childNode.getChildNodes();
                        List<String> data = new ArrayList<>();
                        for(int j = 0; j < nodeList1.getLength(); j++){
                            Node childNode1 = nodeList1.item(j);
                            if(childNode1.getNodeType() == Node.ELEMENT_NODE){
                                Element childElement1 = (Element)childNode1;
                                data.add(childElement1.getTextContent());
                            }
                        }
                        ItemInfo itemInfo = new ItemInfo();
                        itemInfo.setID(ID);
                        itemInfo.setItemName(data.get(0));
                        itemInfo.setPrice(Integer.parseInt(data.get(1)));
                        itemInfo.setUnit(data.get(2));
                        itemList.add(itemInfo);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return itemList;
    }

    public static List<UnitInfo> getUnits(Context context){
        List<UnitInfo> unitList = new ArrayList<UnitInfo>();

        try{
            AsyncTask<String, Integer, String> marketUnits = new MarketUnits(context).execute();
            String xmlData = marketUnits.get().trim();
            if(xmlData.length() > 0){
                Document doc = XmlParser.string2Doc(xmlData);
                Element rootElement = doc.getDocumentElement();
                NodeList nodeList = rootElement.getChildNodes();
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node childNode = nodeList.item(i);
                    if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element childElement = (Element) childNode;
                        int unitID = Integer.parseInt(childElement.getAttribute("id"));
                        String unitName= childElement.getTextContent();
                        unitList.add(new UnitInfo(unitID, unitName));
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return unitList;
    }

    protected static Document string2Doc(String xml) throws Exception{
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new InputSource(new StringReader(xml)));
        return doc;
    }
}
