package ca.on.moh.idms.xml;

import java.io.Reader;

import org.apache.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public abstract class SaxXmlParser extends DefaultHandler {


	/** Create a logger for this class */
	protected static Logger log = Logger.getLogger("ca.on.moh.idms.xml.SaxXmlParser");

	/** Default parser name. */
	private static final String DEFAULT_PARSER_NAME = "org.apache.xerces.parsers.SAXParser";

	private static boolean setNameSpaces = true;
	private static boolean setSchemaSupport = true;
	private static boolean setSchemaFullSupport = false;

	/**
	 * config the XML parser. Takes the parser and file location as arguments.
	 * 
	 * @param parser a child concrete class of WadeSaxXmlParser
	 * @param xmlLocation The location of the XML file to be parsed
	 * @param validate Indicates if the XML should be validated before parsing
	 */
	public static void readAndCacheMetadataXml(
		SaxXmlParser parser,
		String xmlLocation,
		boolean validate) {

		log.info("Method called succesfully.");

		try {
			//parser.setCache(cache);
			XMLReader reader = XMLReaderFactory.createXMLReader(DEFAULT_PARSER_NAME);
			reader.setContentHandler(parser);
			reader.setErrorHandler(parser);

			if (reader instanceof XMLReader) { 
				reader.setFeature("http://xml.org/sax/features/validation", validate);
				reader.setFeature("http://xml.org/sax/features/namespaces", setNameSpaces);
				reader.setFeature("http://apache.org/xml/features/validation/schema",setSchemaSupport);
				reader.setFeature("http://apache.org/xml/features/validation/schema-full-checking", setSchemaFullSupport);
			}
			reader.parse(xmlLocation);
		} catch (SAXParseException spe) {
			log.error("Error in parsing metadata: ", spe);
		} catch (SAXException se) {
			log.error("Error in parsing metadata: ", se);
		} catch (Exception e) {
			log.error("Error in parsing metadata: ", e);
		}
	}
	/**
	 * config the XML parser. Takes the parser and file location as arguments.
	 * 
	 * @param parser a child concrete class of WadeSaxXmlParser
	 * @param xmlLocation The location of the XML file to be parsed
	 * @param validate Indicates if the XML should be validated before parsing
	 */
	public static void readAndCacheMetadataXml(
		SaxXmlParser parser,
		Reader input,
		boolean validate) {

		log.info("Method called succesfully.");

		try {
			//parser.setCache(cache);
			XMLReader reader = XMLReaderFactory.createXMLReader(DEFAULT_PARSER_NAME);
			reader.setContentHandler(parser);
			reader.setErrorHandler(parser);

			if (reader instanceof XMLReader) { 
				reader.setFeature("http://xml.org/sax/features/validation", validate);
				reader.setFeature("http://xml.org/sax/features/namespaces", setNameSpaces);
				reader.setFeature("http://apache.org/xml/features/validation/schema",setSchemaSupport);
				reader.setFeature("http://apache.org/xml/features/validation/schema-full-checking", setSchemaFullSupport);
			}
			InputSource is = new InputSource(input);
			reader.parse(is);
		} catch (SAXParseException spe) {
			log.error("Error in parsing metadata: ", spe);
		} catch (SAXException se) {
			log.error("Error in parsing metadata: ", se);
		} catch (Exception e) {
			log.error("Error in parsing metadata: ", e);
		}
	}

}
