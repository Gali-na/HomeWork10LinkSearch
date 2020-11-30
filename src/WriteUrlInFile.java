import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WriteUrlInFile {

    public static void serchLinc(String url) {
        StringBuilder buffer = new StringBuilder();
        try {
            URL urlOne = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlOne.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String currentString = "";
            for (; (currentString = bufferedReader.readLine()) != null; ) {
                buffer.append(currentString).append(System.lineSeparator());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder file = new StringBuilder();
        Document doc = Jsoup.parse(buffer.toString());
        Elements links = doc.select("a[href]");
        for (Element link : links) {
            String linkHref = link.attr("href");
            if (linkHref.startsWith("https")) {
                file.append(linkHref).append(System.lineSeparator());
            }
        }
        String listUrl = file.toString();
        writeUrls(listUrl);
    }
    private static void writeUrls(String listUrl) {
        try (FileWriter file = new FileWriter("file.txt")) {
            file.write(listUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
