package application;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Parsercat extends DefaultHandler {

    private static final String DESCRIPTION = "description";
    private static final String EXERCISE = "exercise";
    private static final String EXERCISES = "exercises";
    private static final String CLASSES = "classes";
    private static final String CLASS = "class";
    private static final String TESTS = "tests";
    private static final String TEST = "test";
    private static final String CONFIG = "config";
    private static final String BABYSTEPS = "babysteps";
    private static final String TIMETRACKING = "timetracking";

    private ArrayList<String> mTags;

    private Catalog mCatalog;
    private Exercise mExercise;
    private String mDescription;
    private String mClassContent;
    private String mTestContent;

    public Catalog parse(File file) throws ParserConfigurationException, SAXException, IOException {
        mCatalog = new Catalog();

        mTags = new ArrayList<>();

        clearCurrentExercise();

        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser saxParser = spf.newSAXParser();

        XMLReader xmlReader = saxParser.getXMLReader();
        xmlReader.setContentHandler(this);
        xmlReader.parse(convertToFileURL(file));

        return mCatalog;
    }

    private static String convertToFileURL(File file) {
        String path = file.getAbsolutePath();
        if (File.separatorChar != '/') {
            path = path.replace(File.separatorChar, '/');
        }

        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return "file:" + path;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case EXERCISE:
                mExercise = new Exercise(attributes.getValue("name"));
                break;
            case CLASS:
                mExercise.addClassName(attributes.getValue("name"));
                break;
            case TEST:
                mExercise.addTestName(attributes.getValue("name"));
                break;
            case BABYSTEPS:
                String babystepsEnabled = attributes.getValue("value");
                if (Boolean.parseBoolean(babystepsEnabled)) {
                    String timeString = attributes.getValue("time");
                    Date time = null;
                    try {
                        time = new SimpleDateFormat("mm:ss").parse(timeString);
                    } catch (ParseException e) {
                        e.printStackTrace(); // TODO throw
                    }
                    mExercise.addBabystepTime(time);
                }
                break;
            case TIMETRACKING:
                String trackingEnabled = attributes.getValue("value");
                boolean enabled = Boolean.parseBoolean(trackingEnabled);
                mExercise.setTracking(enabled);
                break;
            default:
                //System.out.println("Skipping unknown tag: " + qName);
        }
        mTags.add(qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String substring = new String(ch).substring(start, start + length);
        if (substring.trim().length() == 0)
            return;

        switch (mTags.get(mTags.size() -1)) {
            case DESCRIPTION:
                mDescription += substring;
                break;
            case CLASS:
                mClassContent += substring;
                break;
            case TEST:
                mTestContent += substring;
                break;
            default:
                System.out.println("Can't handle characters: " + substring);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case DESCRIPTION:
                mExercise.addDescription(mDescription);
                break;
            case CLASS:
                mExercise.addClassContent(mClassContent);
                break;
            case TEST:
                mExercise.addTestContent(mTestContent);
                break;
            case EXERCISE:
                mCatalog.addExercise(mExercise);
                clearCurrentExercise();
                mExercise = null;
                break;
        }
        mTags.remove(mTags. size() - 1);
    }

    private void clearCurrentExercise() {
        mExercise = null;
        mDescription = "";
        mClassContent = "";
        mTestContent = "";
    }
}
