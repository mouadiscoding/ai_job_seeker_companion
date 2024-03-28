package projetJava;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import projetJava.webScrap.ScrapingException;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class webScrap2 {
	
	public void runWebScraping() throws ScrapingException, SQLException {
        try {
        	int pageNumber = 1; 
            try (java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjava", "root", "")) {
                String truncateCompanyTable = "TRUNCATE TABLE `company m-job`";
                try (PreparedStatement truncateCompanyStatement = con.prepareStatement(truncateCompanyTable)) {
                    truncateCompanyStatement.executeUpdate();
                }

                String truncatePosteTable = "TRUNCATE TABLE `poste m-job`";
                try (PreparedStatement truncatePosteStatement = con.prepareStatement(truncatePosteTable)) {
                    truncatePosteStatement.executeUpdate();
                }
            }
        	while (true) {
                String url = "https://www.m-job.ma/recherche?page="+pageNumber;
                final Document document = Jsoup.connect(url).get();
                System.out.println("Connected Succsessfuly");
                for (Element row : document.select("div.offer-box")) {
                	Elements titleElements = row.select(".offer-title a");
                    Elements datePublication = row.select(".date-buttons > span");
                    String datePublicationText = datePublication.text();
                    if (!titleElements.isEmpty()) {
                        String link = titleElements.attr("abs:href");
                        System.out.println("Link: " + link);
                        Document jobDetailDocument = Jsoup.connect(link).get();
                        String jobTitleContent = titleElements.text();
                        Elements companyName_typeContrat = jobDetailDocument.select(".list-details > li");
                        String companyName_typeContratText = companyName_typeContrat.text();
                        String companyNameRegex = "Société: (.*?) Type de contrat:";
                        String typeContartRegex = "Type de contrat: (.*?) Salaire:";
                        String salaireRegex = "Salaire: (.*)";
                        Pattern companyNamePattern = Pattern.compile(companyNameRegex);
                        Pattern typeContartPattren = Pattern.compile(typeContartRegex);
                        Pattern salairePattern = Pattern.compile(salaireRegex);
                        Matcher companyNameMatcher = companyNamePattern.matcher(companyName_typeContratText);
                        Matcher typeContartMatcher = typeContartPattren.matcher(companyName_typeContratText);
                        Matcher salaireMatcher = salairePattern.matcher(companyName_typeContratText);
                        String companyName = "";
                        String typeContrat = "";
                        String salaireText = "";
                        if (companyNameMatcher.find()) {
                        	companyName = companyNameMatcher.group(1);
                        }
                        if (typeContartMatcher.find()) {
                            typeContrat = typeContartMatcher.group(1);
                        }
                        if(salaireMatcher.find()) {
                        	salaireText = salaireMatcher.group(1);
                        }
                        Elements ville = jobDetailDocument.select(".location > span");
                        String villeText = ville.text();
                        Elements experience = jobDetailDocument.select(".the-content > div:nth-of-type(6)");
                        String experienceText = experience.text();
                        Elements posteProfil = jobDetailDocument.select(".the-content");
                        String posteProfilText = posteProfil.text();
                        String regexrecruiter =  "Le recruteur : (.*?) Poste à occuper :";
                        String regexPost = "Poste à occuper : (.*?) Profil recherché :";
                        String regexProfil = "Profil recherché : (.*?) Secteur\\(s\\) d'activité :";
                        Pattern recruiterPattern = Pattern.compile(regexrecruiter);
                        Pattern postPattern = Pattern.compile(regexPost);
                        Pattern profilePattern = Pattern.compile(regexProfil);
                        Matcher recruiterMatcher = recruiterPattern.matcher(posteProfilText);
                        Matcher postMatcher = postPattern.matcher(posteProfilText);
                        Matcher profileMatcher = profilePattern.matcher(posteProfilText);
                        String recruiter = "";
                        String post = "";
                        String profile = "";
                        if(recruiterMatcher.find()) {
                        	recruiter = recruiterMatcher.group(1);
                        }
                        if(postMatcher.find()) {
                        	post = postMatcher.group(1);
                        }
                        if(profileMatcher .find()) {
                        	profile = profileMatcher .group(1);
                        }
                        String langueRegex = "Langue\\(s\\) exigée\\(s\\) : (.*?) L'offre a été publiée";
                        Pattern languePattern = Pattern.compile(langueRegex);
                        Matcher langueMatcher = languePattern.matcher(posteProfilText);
                        String langue = "";
                        if(langueMatcher.find()) {
                        	langue = langueMatcher.group(1);
                        }
                        String SecteurActivitéRegex = "Secteur\\(s\\) d'activité : (.*?) Métier\\(s\\) :";
                        Pattern SecteurActivitéPattern = Pattern.compile(SecteurActivitéRegex);
                        Matcher SecteurActivitéMatcher = SecteurActivitéPattern.matcher(posteProfilText);
                        String secteurActivité = "";
                        if(SecteurActivitéMatcher.find()) {
                        	secteurActivité = SecteurActivitéMatcher.group(1);
                        }
                        
                        String NiveauÉtudeRegex = "Niveau d'études exigé : (.*?) Langue\\(s\\) exigée\\(s\\) :";
                        Pattern NiveauÉtudePattern = Pattern.compile(NiveauÉtudeRegex);
                        Matcher NiveauÉtudeMatcher = NiveauÉtudePattern.matcher(posteProfilText);
                        String niveauÉtude = "";
                        if(NiveauÉtudeMatcher.find()) {
                        	niveauÉtude = NiveauÉtudeMatcher.group(1);
                        }

                        System.out.println(jobTitleContent);
                        System.out.println(companyName);
                        System.out.println(typeContrat);
                        System.out.println(villeText);
                        System.out.println(experienceText);
                        System.out.println(salaireText);
                        System.out.println(recruiter);
                        System.out.println(post);
                        System.out.println(profile);
                        System.out.println(datePublicationText);
                        System.out.println(langue);
                        System.out.println(secteurActivité);
                        System.out.println(niveauÉtude);
                        
                        System.out.println("----------------------------------------------");
                        
                        
                        try {
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjava", "root", "");
                            String sql = "INSERT INTO `poste m-job` (`Company Name`,`Sector Activity`, `Title`, `Date Publication`, `Poste Description`, `Profil`, `Type Contrat`, `Experience`, `Niveau Étude`, `City`, `Salary`, `Language`, `Link`) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                            	preparedStatement.setString(1, companyName);
                                preparedStatement.setString(2, secteurActivité);
                                preparedStatement.setString(3, jobTitleContent);
                                preparedStatement.setString(4, datePublicationText);
                                preparedStatement.setString(5, post);
                                preparedStatement.setString(6, profile);
                                preparedStatement.setString(7, typeContrat);
                                preparedStatement.setString(8, experienceText);
                                preparedStatement.setString(9, niveauÉtude);
                                preparedStatement.setString(10, villeText);
                                preparedStatement.setString(11, salaireText);
                                preparedStatement.setString(12, langue);
                                preparedStatement.setString(13, link);

                                preparedStatement.executeUpdate();
                            }
                            String checkCompanyQuery = "SELECT * FROM `company m-job` WHERE `Name` = ?";
                            try (PreparedStatement checkCompanyStatement = con.prepareStatement(checkCompanyQuery)) {
                                checkCompanyStatement.setString(1, companyName);
                                ResultSet resultSet = checkCompanyStatement.executeQuery();

                                if (resultSet.next()) {
                                    String updateCompanyQuery = "UPDATE `company m-job` SET `Location` = ?, `Description` = ?, `Sector Activity` = ? WHERE `Name` = ?";
                                    try (PreparedStatement updateCompanyStatement = con.prepareStatement(updateCompanyQuery)) {
                                        updateCompanyStatement.setString(1, villeText);
                                        updateCompanyStatement.setString(2, recruiter);
                                        updateCompanyStatement.setString(3, secteurActivité);
                                        updateCompanyStatement.setString(4, companyName);

                                        updateCompanyStatement.executeUpdate();
                                    }
                                } else {
                                    String insertCompanyQuery = "INSERT INTO `company m-job` (`Location`, `Name`, `Sector Activity`, `Description`) VALUES(?, ?, ?, ?)";
                                    try (PreparedStatement insertCompanyStatement = con.prepareStatement(insertCompanyQuery)) {
                                        insertCompanyStatement.setString(1, villeText);
                                        insertCompanyStatement.setString(2, companyName);
                                        insertCompanyStatement.setString(3, secteurActivité);
                                        insertCompanyStatement.setString(4, recruiter);

                                        insertCompanyStatement.executeUpdate();
                                    }
                                }
                            }
                            con.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                System.out.println("Iteration completed successfully for page " + pageNumber);
                Element nextButton = document.select(".offers-list-header > span").first();
                String nextButtonText = nextButton.text();
                Pattern pattern = Pattern.compile("/\\s(\\d+)");
                Matcher matcher = pattern.matcher(nextButtonText);
                int result = 0 ;
                if (matcher.find()) {
                    String integerValue = matcher.group(1);
                    result = Integer.parseInt(integerValue);
                }
                if (pageNumber == result) {
                    break;
                }
                pageNumber++;
            }
            System.out.println("Web Scraping completed successfully");
        }
        catch (IOException e) {
        	throw new ScrapingException("Error during web scraping", e);
        }
    }
	public static class ScrapingException extends Exception {
	    public ScrapingException(String message, Throwable cause) {
	        super(message, cause);
	    }
	}
}


