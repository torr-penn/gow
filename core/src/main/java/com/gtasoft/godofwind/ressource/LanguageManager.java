package com.gtasoft.godofwind.ressource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.HashMap;

public class LanguageManager {
    private static final String LANGUAGES_FILE = "messages.xml";
    private static final String LANGUAGES_PATH = "data";
    private static final String DEFAULT_LANGUAGE = "en_UK";
    private static LanguageManager _instance = null;
    // private HashMap<String, HashMap<String, String>> _strings = null;
    private HashMap<String, String> _language = null;
    private String _languageName = null;
    private String _lgfile = null;

    private LanguageManager() {
        _lgfile = LANGUAGES_PATH + "/" + LANGUAGES_FILE;
        // Create language map
        _language = new HashMap<String, String>();

        _languageName = java.util.Locale.getDefault().toString();
        if (!loadLanguage(_languageName)) {
            loadLanguage(DEFAULT_LANGUAGE);
            _languageName = DEFAULT_LANGUAGE;
        }
    }

    public static LanguageManager getInstance() {
        if (_instance == null) {
            _instance = new LanguageManager();
        }

        return _instance;
    }

    public String getLanguage() {
        return _languageName;
    }

    public String getString(String key) {
        String string;

        if (_language != null) {
            // Look for string in selected language
            string = _language.get(key);

            if (string != null) {
                return string;
            }
        }

        // Key not found, return the key itself
        return key;
    }

    private void checkfile(String s) {
        System.out.println(" ____________ check for " + s);

        FileHandle fh2 = Gdx.files.classpath(s);
        System.out.println(" gdx classpath is null ?:" + (fh2 == null) + " and exist : " + Gdx.files.classpath(s).exists());
        FileHandle fh3 = Gdx.files.internal(s);
        System.out.println(" gdx internal is null ?:" + (fh3 == null) + " and exist : " + Gdx.files.internal(s).exists());
        FileHandle fh4 = Gdx.files.external(s);
        System.out.println(" gdx external is null ?:" + (fh4 == null) + " and exist : " + Gdx.files.external(s).exists());


    }

    private void checkDir(String s) {
        FileHandle[] files = Gdx.files.local(s).list();
        System.out.println(" ______ Dir listing of : " + s);
        for (FileHandle file : files) {
            System.out.println("  FILE found : " + file.path() + " + " + file.name());
            // do something interesting here
        }

    }

    public String getString(String key, Object... args) {
        return String.format(getString(key), args);
    }

    public boolean loadLanguage(String languageName) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
//            FileHandle fileHandle = Gdx.files.classpath(_lgfile);
            FileHandle fileHandle = Gdx.files.internal(_lgfile);


            Document doc = db.parse(fileHandle.read());
            //           Document doc = db.parse(in);

            Element root = doc.getDocumentElement();

            NodeList languages = root.getElementsByTagName("language");
            int numLanguages = languages.getLength();
            //System.out.println(" number of L found : " + numLanguages);


            for (int i = 0; i < numLanguages; ++i) {
                Node language = languages.item(i);

                if (language.getAttributes().getNamedItem("name")
                        .getTextContent().equals(languageName)) {
                    _language.clear();
                    Element languageElement = (Element) language;
                    NodeList strings = languageElement
                            .getElementsByTagName("string");
                    int numStrings = strings.getLength();
                    for (int j = 0; j < numStrings; ++j) {

                        NamedNodeMap attributes = strings.item(j)
                                .getAttributes();
                        String key = attributes.getNamedItem("key")
                                .getTextContent();
                        //          System.out.println(" key : " + j + " is  " + key);
                        String value = attributes.getNamedItem("value")
                                .getTextContent();
                        value = value.replace("<br />", "\n");
                        _language.put(key, value);
                    }
                    //    System.out.println(" number of strings  found : " + numStrings + "  last is :" + strings.item(numStrings - 1).getAttributes().getNamedItem("key").getTextContent() + " :");


                    return true;
                }
            }
        } catch (Exception e) {
            System.err
                    .println("Error loading languages file " + _lgfile + " because of " + e);
            return false;
        }

        return false;
    }
}
