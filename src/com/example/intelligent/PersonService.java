package com.example.intelligent;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.sax.Element;
import android.util.Xml;

public class PersonService {
	public static List<Person> getPersons(String path) throws Exception {
		List<Person> persons = null;
		Person person = null;
		File xmlFile = new File(path);
		Element xmlElement = null;
		if (xmlFile.exists()) {
			
			InputStream slideInputStream = new FileInputStream(path);
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser pullParser = factory.newPullParser();
			pullParser.setInput(slideInputStream,"UTF-8");
			int event = pullParser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					persons = new ArrayList<Person>();
					break;
				case XmlPullParser.START_TAG:
					if ("beacon".equals(pullParser.getName())) {
						person = new Person();
					}
					if ("major".equals(pullParser.getName())) {
						int major = Integer.valueOf(pullParser.nextText());
						person.setMajor(major);
					}
					if ("minor".equals(pullParser.getName())) {
						int minor = Integer.valueOf(pullParser.nextText());
						person.setMinor(minor);
					}
					if ("url".equals(pullParser.getName())) {
						String url = pullParser.nextText();
						person.setUrl(url);
					}
					break;
				case XmlPullParser.END_TAG:
					if ("beacon".equals(pullParser.getName())) {
						persons.add(person);
						person = null;
					}
					break;
				}
				event = pullParser.next();
			}
		}
		return persons;
	}
}
