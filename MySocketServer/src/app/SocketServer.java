package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
	public static String imieMagazyn;
	public static int wiekMagazyn;
	public static boolean czyStudentMagazyn;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// włączenie funkcji, która tworzy i obsługuje serwer
		contactWithServer();	
	}
	
	public static void contactWithServer(){
		 try {
			  // tworzenie serwera na porcie 8080
		      ServerSocket ss = new ServerSocket(8080);
		      // łączenie z serwerem na porcie 8080
		      System.out.println("Listening for connection on port 8080 ...."); 
		      
		      // Tworzymy nieskończoną petlę, która oczekuje i zarządza/utrzymuje połączeniem.
		      for (;;) {
		        // Oczekiwanie na połączenie z klientem.
		    	  Socket client = ss.accept();

		        // odczyt informacji przychodzących od klienta do serwera
		        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		        // obiekty, które umożliwiają wysłanie informacji do klienta
		        PrintWriter out = new PrintWriter(client.getOutputStream());

		        // Początkowa odpowiedz serwera, używającego protokołu: HTTP 1.1
		        out.print("HTTP/1.1 200 \r\n"); // wersja i status
		        out.print("Content-Type: text/plain\r\n"); // typ danych
		        out.print("Connection: close\r\n"); // tutaj konczymy przeplyw informacji
		        out.print("\r\n"); // koniec naglowkow
		        
		        String line;
		        // przetwarza wszystkie nagłówki i informacje wysłane od klienta,przegladarki itp
		        while ((line = in.readLine()) != null) 
		        {
		          if (line.length() == 0)
		            break; 
		          	// zczytanie pierwszej linii, która zawiera adres URL
				    ZapytanieParsowanie(line, out);		        
				    break;
		        }

		        out.close(); // zamkniecie wysyłania danych przez serwer
		        in.close(); // zamkniecie przysyłania/odbierania danych przez serwer
		        client.close(); // zamkniecie komunikacji z klientem
		      } // teraz pętla ponownie oczekuje na połączenie/próbę połączenia z serewrem
		    }
		    // jeśli coś pójdzie nie tak, zostanie wyświetlona wiadomość
		    catch (Exception e) {
		      System.err.println(e);
		      System.err.println("Usage: java HttpMirror <port>");
		    }
	}
	
	private static void ZapytanieParsowanie(String parameters, PrintWriter out) throws MalformedURLException, IOException 
	{
		System.out.println("pobrane parametry:" + parameters);	
		String sciezka = parameters;
		String akcja = sciezka;
		String wartosc = "";
		
		akcja = akcja.substring(sciezka.indexOf("akcja=") + 6, sciezka.indexOf("&"));
		
		// DODANIE I POBRANIE IMIENIA
		if(akcja.equals("DodajImie")){
			out.println("Akcja: Dodaj imie");
			sciezka = sciezka.substring(sciezka.indexOf("&")+1, sciezka.length());
			wartosc = sciezka;
			wartosc = wartosc.substring(sciezka.indexOf("imie=") + 5, sciezka.indexOf("&"));
			out.println("Imie: " + wartosc);
			imieMagazyn = wartosc;
		}
		if(akcja.equals("PobierzImie")){
			out.println("Akcja: Pobierz imie");
			//sciezka = sciezka.substring(sciezka.indexOf("&")+1, sciezka.length());
			
			out.println("Pobrane imie: " + imieMagazyn);
		}
		
		// DODANIE I POBRANIE WIEKU
		if(akcja.equals("DodajWiek")){
			out.println("Akcja: Dodano wiek");
			sciezka = sciezka.substring(sciezka.indexOf("&")+1, sciezka.length());
			wartosc = sciezka;
			wartosc = wartosc.substring(sciezka.indexOf("wiek=") + 5, sciezka.indexOf("&"));
			out.println("Wiek: " + wartosc);
			wiekMagazyn = Integer.parseInt(wartosc);
		}
		if(akcja.equals("PobierzWiek")){
			out.println("Akcja: Pobierz wiek");
			out.println("Pobrany wiek: " + wiekMagazyn);
		}
		
		// DODANIE I POBRANIE PLCI
		if(akcja.equals("CzyStudent")){
			out.println("Akcja: Czy student");
			sciezka = sciezka.substring(sciezka.indexOf("&")+1, sciezka.length());
			wartosc = sciezka;
			wartosc = wartosc.substring(sciezka.indexOf("student=") + 8, sciezka.indexOf("&"));
			
			czyStudentMagazyn = Boolean.parseBoolean(wartosc);
			if (czyStudentMagazyn)
			{
				out.println("Student: tak");
			}
			else
			{
				out.println("Student: nie");
			}
		}
		if(akcja.equals("PobierzCzyStudent"))
		{
			out.println("Akcja: Pobierz czy student?");
			if (czyStudentMagazyn)
			{
				out.println(imieMagazyn + " jest studentem.");
			}
			else
			{
				out.println(imieMagazyn + " nie jest studentem.");
			}			
		}
	}
}
