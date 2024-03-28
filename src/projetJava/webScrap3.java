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
public class webScrap3 {
	
	public void runWebScraping() throws ScrapingException, SQLException {
        try {
            int pageNumber = 1;
            int childNumber = 6;
            
            try (java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjava", "root", "")) {
                String truncateCompanyTable = "TRUNCATE TABLE `company emploima`";
                try (PreparedStatement truncateCompanyStatement = con.prepareStatement(truncateCompanyTable)) {
                    truncateCompanyStatement.executeUpdate();
                }

                String truncatePosteTable = "TRUNCATE TABLE `poste emploima`";
                try (PreparedStatement truncatePosteStatement = con.prepareStatement(truncatePosteTable)) {
                    truncatePosteStatement.executeUpdate();
                }
            }
            
            while (true) {
                String url = "https://www.emploi.ma/recherche-jobs-maroc?page="+pageNumber;  
                final Document document = Jsoup.connect(url).get();

                for (Element row : document.select(".job-title.col-xs-12.col-sm-5.col-md-5.col-lg-5")) {
                	Elements titleElements = row.select("h5 a");
                    Elements dateAndCompanyName = row.select(".job-recruiter");
                    String dateAndComapnyNameText = dateAndCompanyName.text();
                    String dateRegex = "(\\d{2}\\.\\d{2}\\.\\d{4})";
                    String companyRegex = "\\|\\s*(.*)";
                    Pattern datePattern = Pattern.compile(dateRegex);
                    Pattern companyPattern = Pattern.compile(companyRegex);
                    Matcher dateMatcher = datePattern.matcher(dateAndComapnyNameText);
                    Matcher companyMatcher = companyPattern.matcher(dateAndComapnyNameText);
                    String date = dateMatcher.find() ? dateMatcher.group(1) : "";
                    String companyName = companyMatcher.find() ? companyMatcher.group(1) : "";
                    

        
                    if (!titleElements.isEmpty()) {
                        String link = titleElements.attr("abs:href");
                        System.out.println("Link: " + link); 
                        Document jobDetailDocument = Jsoup.connect(link).get();
                        String jobTitleContent = titleElements.text();
                        Elements secteurActivité = jobDetailDocument.select(".sector-title");
                        String secteurActivitéText = secteurActivité.text();
                        Elements typeContrat = jobDetailDocument.select(".field-label-hidden.field-type-taxonomy-term-reference.field-name-field-offre-contrat-type.field > .field-items > .even.field-item");
                        String typeContratText = typeContrat.text();
                        Elements ville = jobDetailDocument.select("tr:nth-of-type(5)");
                        String villeText = ville.text();
                        String regexVille = "Ville : (.+)";
                        Pattern patternVille = Pattern.compile(regexVille);
                        Matcher matcherVille = patternVille.matcher(villeText);
                        String city = ""; 
                        if (matcherVille.find()) {
                            city = matcherVille.group(1);
                        }
                        Elements experience = jobDetailDocument.select(".field-label-hidden.field-type-taxonomy-term-reference.field-name-field-offre-niveau-experience.field > .field-items > .even.field-item");
                        String experienceText = experience.text();
                        Elements niveauÉtude = jobDetailDocument.select(".field-label-hidden.field-type-taxonomy-term-reference.field-name-field-offre-niveau-etude.field > .field-items > .even.field-item");
                        String niveauÉtudeText = niveauÉtude.text();
                        Elements description_poste = jobDetailDocument.select(".clearfix.inner > .clearfix.content");
                        String description_posteText = description_poste.text();
                        String regexPostDescription = "Poste proposé : (.+?)\\s+Profil recherché pour le poste :";
                        Pattern pattern = Pattern.compile(regexPostDescription,Pattern.DOTALL);
                        Matcher matcher = pattern.matcher(description_posteText);
                        String postePropose = "";
                        if (matcher.find()) {
                            postePropose = matcher.group(1);
                        }
                        String profilRegex = "Profil recherché pour le poste : (.+?)\\s+Critères de l'annonce pour le poste :";
                        Pattern profilPattern = Pattern.compile(profilRegex);
                        Matcher profilMatcher = profilPattern.matcher(description_posteText);
                        String profil = "";
                        if(profilMatcher.find()) {
                        	profil = profilMatcher.group(1);
                        }
                        Elements hardSkills = jobDetailDocument.select(".field-label-hidden.field-type-taxonomy-term-reference.field-name-field-offre-tags.field > .field-items");
                        String hardSkillsText = hardSkills.text(); 
                        Elements language = jobDetailDocument.select(".field-label-hidden.field-type-taxonomy-term-reference.field-name-field-offre-niveau-langue.field > .field-items > .even.field-item");
                        String languageText = language.text();
                        
                        Elements companyDescriptionLink = jobDetailDocument.select(".job-ad-company-description a");
                        String companyDescriptionText = "";
                        if(!companyDescriptionLink.isEmpty()) {
                        	String link2 = companyDescriptionLink.attr("abs:href");
                            Document jobDetailDocument2 = Jsoup.connect(link2).get();
                            
                            Elements companyDescription = jobDetailDocument2.select(".company-profile-description > p");
                            companyDescriptionText = companyDescription.text();
                        }
                        
                        
                        System.out.println(jobTitleContent);
                        System.out.println(date);
                        System.out.println(companyName);
                        System.out.println(secteurActivitéText);
                        System.out.println(typeContratText);
                        System.out.println(city);
                        System.out.println(experienceText);
                        System.out.println(niveauÉtudeText);
                        System.out.println(postePropose);
                        System.out.println(hardSkillsText);
                        System.out.println(languageText);
                        System.out.println(profil);
                        System.out.println(companyDescriptionText);

                        System.out.println("----------------------------------------------");
                        
                        
                        try {
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjava", "root", "");
                            String sql = "INSERT INTO `poste emploima` (`Company Name`,`Sector Activity`, `Title`, `Date Publication`, `Poste Description`, `Region`, `Type Contrat`, `Niveau Étude`, `Experience`, `Hard skills`, `Profil`, `Langue`, `Link`) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                            	preparedStatement.setString(1, companyName);
                                preparedStatement.setString(2, secteurActivitéText);
                                preparedStatement.setString(3, jobTitleContent);
                                preparedStatement.setString(4, date);
                                preparedStatement.setString(5, postePropose);
                                preparedStatement.setString(6, city);
                                preparedStatement.setString(7, typeContratText);
                                preparedStatement.setString(8, niveauÉtudeText);
                                preparedStatement.setString(9, experienceText);
                                preparedStatement.setString(10, hardSkillsText);
                                preparedStatement.setString(11, profil);
                                preparedStatement.setString(12, languageText);
                                preparedStatement.setString(13, link);

                                preparedStatement.executeUpdate();
                            }
                            String checkCompanyQuery = "SELECT * FROM `company emploima` WHERE `Name` = ?";
                            try (PreparedStatement checkCompanyStatement = con.prepareStatement(checkCompanyQuery)) {
                                checkCompanyStatement.setString(1, companyName);
                                ResultSet resultSet = checkCompanyStatement.executeQuery();

                                if (resultSet.next()) {
                                    String updateCompanyQuery = "UPDATE `company emploima` SET `Location` = ?, `Description` = ?, `Sector Activity` = ? WHERE `Name` = ?";
                                    try (PreparedStatement updateCompanyStatement = con.prepareStatement(updateCompanyQuery)) {
                                        updateCompanyStatement.setString(1, city);
                                        updateCompanyStatement.setString(2, companyDescriptionText);
                                        updateCompanyStatement.setString(3, secteurActivitéText);
                                        updateCompanyStatement.setString(4, companyName);

                                        updateCompanyStatement.executeUpdate();
                                    }
                                } else {
                                    String insertCompanyQuery = "INSERT INTO `company emploima` (`Location`, `Name`, `Sector Activity`, `Description`) VALUES(?, ?, ?, ?)";
                                    try (PreparedStatement insertCompanyStatement = con.prepareStatement(insertCompanyQuery)) {
                                        insertCompanyStatement.setString(1, city);
                                        insertCompanyStatement.setString(2, companyName);
                                        insertCompanyStatement.setString(3, secteurActivitéText);
                                        insertCompanyStatement.setString(4, companyDescriptionText);

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
                
                Elements nextButton = document.select(".pager > li.active.pager-item:nth-of-type("+childNumber+")");
                String nextButtonText = nextButton.text().split("\\s+")[0];
                System.out.println(nextButtonText);
                int result = Integer.parseInt(nextButtonText);
                if (pageNumber == result) {
                    break;
                }
                pageNumber++;
                childNumber++;
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

