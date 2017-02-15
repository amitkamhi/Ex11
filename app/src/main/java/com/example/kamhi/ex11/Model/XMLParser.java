package com.example.kamhi.ex11.Model;


import java.io.InputStream;

import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;

import org.xmlpull.v1.XmlPullParserFactory;

public class XMLParser {
	final static String KEY_COUNTRY="country";
	final static String KEY_NAME="name";
	final static String KEY_FLAG="flag";
	final static String KEY_ANTHEM="anthem";
	final static String KEY_SHORT="short";
	final static String KEY_DETAILS="details";


	public static ArrayList<Country> parseFromStream(InputStream in){
		ArrayList<Country> data = null;

		XmlPullParserFactory xmlFactoryObject;
		try {
				xmlFactoryObject = XmlPullParserFactory.newInstance();
				XmlPullParser parser = xmlFactoryObject.newPullParser();
		
				parser.setInput(in, null);
		        int eventType = parser.getEventType();
		        Country currentCountry = null;
		        String inTag = "";
		        String strTagText = null;
		
		        while (eventType != XmlPullParser.END_DOCUMENT){
		        	inTag = parser.getName();
		            switch (eventType){
		                case XmlPullParser.START_DOCUMENT:
		                	data = new ArrayList<Country>();
		                    break;
		                case XmlPullParser.START_TAG:
		                	if (inTag.equalsIgnoreCase(KEY_COUNTRY))
		                    	currentCountry = new Country();
		                    break;
		                case XmlPullParser.TEXT:
		                	strTagText = parser.getText();
		                	break;
		                case XmlPullParser.END_TAG:
		                	if (inTag.equalsIgnoreCase(KEY_COUNTRY))
		                    	data.add(currentCountry);
		                	else if (inTag.equalsIgnoreCase(KEY_NAME))
		                    	currentCountry.setName(strTagText);
		                	else if (inTag.equalsIgnoreCase(KEY_SHORT))
		                    	currentCountry.setShortDetails(strTagText);
		                	else if (inTag.equalsIgnoreCase(KEY_FLAG))
		                    	currentCountry.setFlag(strTagText);
		                	else if (inTag.equalsIgnoreCase(KEY_ANTHEM))
		                    	currentCountry.setAnthem(strTagText);
		                	else if (inTag.equalsIgnoreCase(KEY_DETAILS))
		                    	currentCountry.setDetails(strTagText);
		            		inTag ="";
		                	break;	                    
		            }//switch
		            eventType = parser.next();
		        }//while
			} catch (Exception e) {e.printStackTrace();}
		return data;
	}
}
