package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

public class ParserURL {

	static String googleUrl = "https://www.google.pl/url?sa=t&rct=j&q=&esrc=s&source=web&cd=2&cad=rja&uact=8&ved=0ahUKEwjsu8_77uLTAhXM2SwKHX_RB8QQjBAIKTAB&url=http%3A%2F%2Fwww.tvn24.pl%2Fnajnowsze%2C49&usg=AFQjCNHsYeQqQl1YiEzdXEoWeTXjZkneNw";
	
	public static void main(String[] args) throws MalformedURLException, IOException {
		// TODO Auto-generated method stub
		WczytajAdresURL();
		ZapytanieParsowanie();
	}

	private static void WczytajAdresURL() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    System.out.println("Przyklad: ");
	    System.out.println(googleUrl);
	    System.out.println("Podaj adres URL(referencja od google): ");
	    googleUrl = br.readLine();
	}

	private static void ZapytanieParsowanie() throws MalformedURLException, IOException {
		System.out.println("Pobrany adres od google: " + googleUrl);
		URL oryginalnyURL = new URL(googleUrl);

		System.out.println("Pobrana czesc przedstawiajaca parametry oryginalnego adresu URL: " + oryginalnyURL.getQuery());
		
		String parametryURL = oryginalnyURL.getQuery();
	    parametryURL = URLDecoder.decode(parametryURL.substring(parametryURL.indexOf("url=") + 4, parametryURL.lastIndexOf("&")), "UTF-8");
	    System.out.println("Pobrana wartosc(witryna) referencji/parametru 'url': " + parametryURL);
	     
	    URL referencja = new URL(parametryURL);
	    System.out.println("Host: " + referencja.getHost());
		System.out.println("Protokol: " + referencja.getProtocol());
		System.out.println("Sciezka: " + referencja.getPath());
		System.out.println("Domyslny port dla protokolu: " + referencja.getDefaultPort());
	}
}
