package projetJava;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class webScrap4 {
	private static final String baseUrl = "https://www.moncallcenter.ma";

	private static ArrayList<String[]> dataLines = new ArrayList<>();

	private static final String siteName = "marocannonces";

	public void runWebScraping() throws IOException {
		String databaseUrl = "jdbc:MySQL://localhost:3306/projetjava";
		String user = "root";
		String password = "";
		try {
			Connection myConn = DriverManager.getConnection(databaseUrl, user, password);
			Statement myStmt = myConn.createStatement();
			
			String truncateTableSQL = "TRUNCATE TABLE `poste_moncallcenter`";
            myStmt.executeUpdate(truncateTableSQL);
			try {
				int nombre_de_pages = 20;
				int nombre_offres = 0;
				for (int i = 0; i < nombre_de_pages; i++) {
					String url = baseUrl;

					if (i != 0) {
						url = baseUrl + "/offres-emploi/" + i + "/";
					}

					Document doc = Jsoup
							.connect(url)
							.get();
					Elements offers = doc.select("div.offres");
					for (Element offer : offers) {
						nombre_offres += 1;
						String lien = baseUrl + offer.select("a").attr("href");

						Document offerDetails = Jsoup
								.connect(lien)
								.get();

						String titre = offerDetails.select("h1").text();
						String entreprise = offerDetails.select("h2").text();
						String dateDePublication = offerDetails.select("div.offredetails span").text().split("\\|")[0].strip();
						String localisationAndLanguages = offerDetails.select("div.offredetails span").text().split("\\|")[1].strip();

						String localisationRegex = "^(.*?)(?=\\s*Langue\\(s\\))";
						Pattern localisationPattern = Pattern.compile(localisationRegex);
						Matcher localisationMatcher = localisationPattern.matcher(localisationAndLanguages);
						String localisation = "";
						if (localisationMatcher.find()) {
							localisation = localisationMatcher.group(1);
						}

						String languesRegex = "\\s*Langue\\(s\\)\\s*:\\s*(.*?)$";
						Pattern languesPattern = Pattern.compile(languesRegex);
						Matcher languesMatcher = languesPattern.matcher(localisationAndLanguages);
						String langues = "";
						if (languesMatcher.find()) {
							langues = languesMatcher.group(1);
						}
						
						String all = offerDetails.select("#statuts div.offredetails").text();

						String descriptionRegex = "Descriptif du poste(.*?)Profil Recherché";
						Pattern descriptionPattern = Pattern.compile(descriptionRegex);
						Matcher descriptionMatcher = descriptionPattern.matcher(all);
						String description = "";
						if (descriptionMatcher.find()) {
							description = descriptionMatcher.group(1).strip();
						}

						String profilRechercheRegex = "Profil Recherché(.*?)Avantages sociaux et autres";
						Pattern profilRecherchePattern = Pattern.compile(profilRechercheRegex);
						Matcher profilRechercheMatcher = profilRecherchePattern.matcher(all);
						String profilRecherche = "";
						if (profilRechercheMatcher.find()) {
							profilRecherche = profilRechercheMatcher.group(1).strip();
						}

						String avantagesRegex = "Avantages sociaux et autres(.*?)Amplitude horaire";
						Pattern avantagesPattern = Pattern.compile(avantagesRegex);
						Matcher avantagesMatcher = avantagesPattern.matcher(all);
						String avantages = "";
						if (avantagesMatcher.find()) {
							avantages = avantagesMatcher.group(1).strip();
						}

						String amplitudeHoraireRegex = "Amplitude horaire(.*?)Salaire Net \\+ primes";
						Pattern amplitudeHorairePattern = Pattern.compile(amplitudeHoraireRegex);
						Matcher amplitudeHoraireMatcher = amplitudeHorairePattern.matcher(all);
						String amplitudeHoraire = "";
						if (amplitudeHoraireMatcher.find()) {
							amplitudeHoraire = amplitudeHoraireMatcher.group(1).strip();
						}

						String salaireRegex = "Salaire Net \\+ primes(.*)";
						Pattern salairePattern = Pattern.compile(salaireRegex);
						Matcher salaireMatcher = salairePattern.matcher(all);
						String salaire = "";
						if (salaireMatcher.find()) {
							salaire = salaireMatcher.group(1).strip();
						}

						

						String sql = String.format("INSERT INTO `poste_moncallcenter`" 
						+ "(`titre`, `ville`, `entreprise`, `DatePublication`, `langues`, `description`, `profil`, `avantages`, `horaires`, `salaire`)"
								+ " values ( \"%s\", \"%s\" , \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\" );",
						titre, localisation , entreprise, dateDePublication , langues,
						description , profilRecherche ,
						avantages,amplitudeHoraire,salaire);
						try {
							myStmt.executeUpdate(sql);
						} catch (Exception e) {
							System.out.println(
							"---------------------------------------------------------------");
						    System.out.println(sql);
						}

					}
					System.out.println("Page " + (i + 1) + " succefully scrapped !");
					System.out.println(
							"---------------------------------------------------------------");

				}
				System.out.println("Nombre d'offres collectées : " + nombre_offres);

			} catch (Exception e) {
				System.out.println(e);
			}

			myConn.close();
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}
}
