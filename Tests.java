import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.jar.Attributes;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.google.gson.Gson;

public class Tests {

	static final int count = 10000000;

	public static void main(String argv[]) {

		int testCount = count;

		double Gson = 0.0, SAX = 0.0;

		while (testCount > 0) {
			testCount--;
			System.out.println("Test " + testCount);
			Gson = testGson(Gson);
			SAX = testSAX(SAX);
		}

		System.out.println("Total: " + (double) Gson);
		System.out.println("Total: " + (double) SAX);
	}

	private static double testSAX(double SAX) {
		try {

			final book b = new book();

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {

				boolean type, pages, title, description, rating, coverType,
						genre, author, publisher, copyright;

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {

					if (qName.equalsIgnoreCase("type"))
						type = true;
					else if (qName.equalsIgnoreCase("pages"))
						pages = true;
					else if (qName.equalsIgnoreCase("title"))
						title = true;
					else if (qName.equalsIgnoreCase("description"))
						description = true;
					else if (qName.equalsIgnoreCase("rating"))
						rating = true;
					else if (qName.equalsIgnoreCase("coverType"))
						coverType = true;
					else if (qName.equalsIgnoreCase("genre"))
						genre = true;
					else if (qName.equalsIgnoreCase("author"))
						author = true;
					else if (qName.equalsIgnoreCase("publisher"))
						publisher = true;
					else if (qName.equalsIgnoreCase("copyright"))
						copyright = true;
				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {

					if (qName.equalsIgnoreCase("type"))
						type = false;
					else if (qName.equalsIgnoreCase("pages"))
						pages = false;
					else if (qName.equalsIgnoreCase("title"))
						title = false;
					else if (qName.equalsIgnoreCase("description"))
						description = false;
					else if (qName.equalsIgnoreCase("rating"))
						rating = false;
					else if (qName.equalsIgnoreCase("coverType"))
						coverType = false;
					else if (qName.equalsIgnoreCase("genre"))
						genre = false;
					else if (qName.equalsIgnoreCase("author"))
						author = false;
					else if (qName.equalsIgnoreCase("publisher"))
						publisher = false;
					else if (qName.equalsIgnoreCase("copyright"))
						copyright = false;
				}

				public void characters(char ch[], int start, int length)
						throws SAXException {

					if (type)
						b.type = new String(ch, start, length);
					else if (pages)
						b.pages = new String(ch, start, length);
					else if (title)
						b.title = new String(ch, start, length);
					else if (description)
						b.description = new String(ch, start, length);
					else if (rating)
						b.rating = new String(ch, start, length);
					else if (coverType)
						b.coverType = new String(ch, start, length);
					else if (genre)
						b.genre = new String(ch, start, length);
					else if (author)
						b.author = new String(ch, start, length);
					else if (publisher)
						b.publisher = new String(ch, start, length);
					else if (copyright)
						b.copyright = new String(ch, start, length);

				}

			};

			File file = new File(
					"...");
			InputStream inputStream = new FileInputStream(file);
			Reader reader = new InputStreamReader(inputStream, "UTF-8");

			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");

			long start = System.nanoTime();

			saxParser.parse(is, handler);
			long end = System.nanoTime();

			long e = end - start;

			if (SAX != 0.0f) {
				SAX += (double) e / 1000000000.0;
				SAX = SAX / 2.0;
			} else
				SAX += (double) e / 1000000000.0;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SAX;
	}

	private static double testGson(double Gson) {
		Gson gson = new Gson();

		try {

			BufferedReader br = new BufferedReader(new FileReader(
					"..."));

			long start = System.nanoTime();
			;

			// convert the json string back to object
			book obj = gson.fromJson(br, book.class);

			long end = System.nanoTime();

			long e = end - start;

			if (Gson != 0.0f) {
				Gson += (double) e / 1000000000.0;
				Gson = Gson / 2.0;
			} else
				Gson += (double) e / 1000000000.0;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Gson;
	}

}