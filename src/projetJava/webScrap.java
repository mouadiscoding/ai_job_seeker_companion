package projetJava;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class webScrap {
	
	public void runWebScraping() throws ScrapingException, SQLException {
        try {
            int pageNumber = 1; 
            
            try (java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjava", "root", "")) {
                String truncateCompanyTable = "TRUNCATE TABLE `company`";
                try (PreparedStatement truncateCompanyStatement = con.prepareStatement(truncateCompanyTable)) {
                    truncateCompanyStatement.executeUpdate();
                }

                String truncatePosteTable = "TRUNCATE TABLE `poste`";
                try (PreparedStatement truncatePosteStatement = con.prepareStatement(truncatePosteTable)) {
                    truncatePosteStatement.executeUpdate();
                }
            }
            
            while (true) {
                String url = "https://www.rekrute.com/offres.html?s=1&p="+pageNumber+"&o=1";  
                final Document document = Jsoup.connect(url).get();

                for (Element row : document.select(".post-id")) {
                    Elements titleElements = row.select("div > .col-xs-12.col-sm-10 > .section > h2 > .titreJob");
                    Elements date = row.select("div > .col-xs-12.col-sm-10 > .section > .holder > .date");
                    String datetext = date.text();
                    String patternDate = "du (\\d{2}/\\d{2}/\\d{4}) au (\\d{2}/\\d{2}/\\d{4})";
                    Pattern pattern0 = Pattern.compile(patternDate);
                    Matcher matcher0 = pattern0.matcher(datetext );
                    String datePublication = "";
                    String datePostulation = "";
                    Elements photos = row.select(".photo");
                    String companyName = photos.attr("title");
                    if (matcher0.find()) {
                    	datePublication = matcher0.group(1);
                    	datePostulation = matcher0.group(2);
                    }
                    if (!titleElements.isEmpty()) {
                        String link = titleElements.attr("abs:href");
                        System.out.println("Link: " + link); 
                        Document jobDetailDocument = Jsoup.connect(link).get();
                        Elements jobDetailContentElements = jobDetailDocument.select("div.blc.col-md-12:nth-of-type(5)");
                        String jobDetailContent = jobDetailContentElements.text();
                        Elements jobDetailContentElements2 = jobDetailDocument.select("div.blc.col-md-12:nth-of-type(6)");
                        String jobDetailContent2 = jobDetailContentElements2.text();
                        String jobTitleContent = titleElements.text();
                        Pattern pattern = Pattern.compile("Profil recherché :");
                        Matcher matcher1 = pattern.matcher(jobDetailContent);
                        boolean matchFound1 = matcher1.find();
                        Matcher matcher2 = pattern.matcher(jobDetailContent2);
                        boolean matchFound2 = matcher2.find();
                        String Profil = "";
                        if(matchFound1) {
                        	Profil = jobDetailContent;
                        }
                        else if(matchFound2) {
                        	Profil = jobDetailContent2;
                        }
                        String Profil2 = Profil.replace("Profil recherché :", "");
                        System.out.println(jobTitleContent);
                        System.out.println(Profil2);
                        Elements experience = jobDetailDocument.select("ul.featureInfo:nth-of-type(1) > li:nth-of-type(1)");
                        String experienceText = experience.text();
                        Elements region = jobDetailDocument.select("ul.featureInfo:nth-of-type(1) > li:nth-of-type(2)");
                        String regionText = region.text();
                        String patternRegion = "sur\\s(.*?)\\s-\\s(.*?)$";
                        String patternRegion2 = "^(.*?) - (.*?)$";
                        Pattern regionPattern = Pattern.compile(patternRegion);
                        Matcher matcher3 = regionPattern.matcher(regionText);
                        Pattern regionPattern2 = Pattern.compile(patternRegion2);
                        Matcher matcherRegion = regionPattern2.matcher(regionText);
                        String regionText2 = "";
                        if (matcher3.find()) {
                        	regionText2 = matcher3.group(1) + " - " + matcher3.group(2);
                        }
                        else if(matcherRegion.find()) {
                        	regionText2 = regionText;
                        }
                        Elements N_étude = jobDetailDocument.select("ul.featureInfo:nth-of-type(1) > li:nth-of-type(3)");
                        String N_étudeText = N_étude.text();
                        Elements Poste_description1 = jobDetailDocument.select("div.blc.col-md-12:nth-of-type(4)");
                        String Poste_description1Text = Poste_description1.text();
                        Elements Poste_description2 = jobDetailDocument.select("div.blc.col-md-12:nth-of-type(5)");
                        String Poste_description2Text = Poste_description2.text();
                        Pattern pattern2 = Pattern.compile("Poste :");
                        Matcher matcher4 = pattern2.matcher(Poste_description1Text);
                        boolean matchFound4 = matcher4.find();
                        Matcher matcher5 = pattern2.matcher(Poste_description2Text);
                        boolean matchFound5 = matcher5.find();
                        String DescriptionPost = "";
                        if(matchFound4) {
                        	DescriptionPost = Poste_description1Text;
                        }
                        else if(matchFound5) {
                        	DescriptionPost = Poste_description2Text;
                        }
                        String DescriptionPost2 = DescriptionPost.replace("Poste :", "");
                        Elements typeContratTéleTravail = jobDetailDocument.select(".tagContrat");
                        String typeContratTéleTravailText = typeContratTéleTravail.text();
                        String regexTypeContrat = "([A-Za-z]+) Télétravail : ([A-Za-z]+)";
                        Pattern patternTypeContrat = Pattern.compile(regexTypeContrat);
                        Matcher matcher6 = patternTypeContrat.matcher(typeContratTéleTravailText);
                        String typeContart = "";
                        String téléTravail = "";
                        if (matcher6.find()) {
                        	typeContart = matcher6.group(1);
                        	téléTravail = matcher6.group(2);
                        }
                        Elements softSkills1 = jobDetailDocument.select(".col-xs-12.col-sm-12.col-md-10 > .blc.col-md-12");
                        String softSkillsText1 = softSkills1.text();
                        Elements softSkills2 = jobDetailDocument.select("div.col-xs-12.col-sm-12.col-md-12 > .blc.col-md-12 > p");
                        String softSkillsText2 = softSkills2.text();
                        String softSkills = "";
                        if(!softSkillsText1.isEmpty()) {
                        	softSkills = softSkillsText1;
                        }
                        else {
                        	softSkills = softSkillsText2;
                        }
                        Elements adresse1 = jobDetailDocument.select("div.blc.col-md-12:nth-of-type(6) > #address");
                        Elements adresse2 = jobDetailDocument.select("div.blc.col-md-12:nth-of-type(7) > #address");
                        String adresseText1 = adresse1.text();
                        String adresseText2 = adresse2.text();
                        String adresse = "";
                        if(!adresseText1.isEmpty()) {
                        	adresse = adresseText1;
                        }
                        else {
                        	adresse = adresseText2;
                        }
                        Elements companyDescription = jobDetailDocument.select("#recruiterDescription");
                        String companyDescriptionText = companyDescription.text();
                        String companyDescription2 = companyDescriptionText.replace("Entreprise :", "");
                        Elements secteurActivité = jobDetailDocument.select(".h2italic");
                        String secteurActivitéText = secteurActivité.text();

                        System.out.println(experienceText);
                        System.out.println(regionText2);
                        System.out.println(N_étudeText);
                        System.out.println(DescriptionPost2);
                        System.out.println(typeContart);
                        System.out.println(téléTravail);
                        System.out.println(datePublication);
                        System.out.println(datePostulation);
                        System.out.println(softSkills);
                        System.out.println(adresse);
                        System.out.println(companyDescription2);
                        System.out.println(secteurActivitéText);
                        System.out.println(companyName);
                        System.out.println("----------------------------------------------");
                        
                        
                        try {
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetjava", "root", "");
                            String sql = "INSERT INTO `poste` (`Company Name`,`Sector Activity`,`Title`, `Date Publication`, `Date Postulation`, `Poste Description`, `Region`, `Type Contrat`, `Niveau Étude`, `Experience`, `Soft skills`, `Hard skills`, `Télétravail`, `Link`) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                            	preparedStatement.setString(1, companyName);
                            	preparedStatement.setString(2, secteurActivitéText);
                                preparedStatement.setString(3, jobTitleContent);
                                preparedStatement.setString(4, datePublication);
                                preparedStatement.setString(5, datePostulation);
                                preparedStatement.setString(6, DescriptionPost2);
                                preparedStatement.setString(7, regionText2);
                                preparedStatement.setString(8, typeContart);
                                preparedStatement.setString(9, N_étudeText);
                                preparedStatement.setString(10, experienceText);
                                preparedStatement.setString(11, softSkills);
                                preparedStatement.setString(12, Profil2);
                                preparedStatement.setString(13, téléTravail);
                                preparedStatement.setString(14, link);

                                preparedStatement.executeUpdate();
                            }
                            String checkCompanyQuery = "SELECT * FROM `company` WHERE `Name` = ?";
                            try (PreparedStatement checkCompanyStatement = con.prepareStatement(checkCompanyQuery)) {
                                checkCompanyStatement.setString(1, companyName);
                                ResultSet resultSet = checkCompanyStatement.executeQuery();

                                if (resultSet.next()) {
                                    String updateCompanyQuery = "UPDATE `company` SET `Address` = ?, `Description` = ?, `Sector Activity` = ? WHERE `Name` = ?";
                                    try (PreparedStatement updateCompanyStatement = con.prepareStatement(updateCompanyQuery)) {
                                        updateCompanyStatement.setString(1, adresse);
                                        updateCompanyStatement.setString(2, companyDescription2);
                                        updateCompanyStatement.setString(3, secteurActivitéText);
                                        updateCompanyStatement.setString(4, companyName);

                                        updateCompanyStatement.executeUpdate();
                                    }
                                } else {
                                    String insertCompanyQuery = "INSERT INTO `company` (`Address`, `Name`, `Description`, `Sector Activity`) VALUES(?, ?, ?, ?)";
                                    try (PreparedStatement insertCompanyStatement = con.prepareStatement(insertCompanyQuery)) {
                                        insertCompanyStatement.setString(1, adresse);
                                        insertCompanyStatement.setString(2, companyName);
                                        insertCompanyStatement.setString(3, companyDescription2);
                                        insertCompanyStatement.setString(4, secteurActivitéText);

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
                Element nextButton = document.select(".jobs").first();
                String nextButtonText = nextButton.text();
                Pattern pattern = Pattern.compile("sur\\s*(\\d+)");
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
	public class ScrapingException extends Exception {
	    public ScrapingException(String message, Throwable cause) {
	        super(message, cause);
	    }
	}
}
