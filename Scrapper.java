/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rok.nba.scrapper;

import java.util.Scanner;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Rok
 */
public class Scrapper {
    public static void main(String[] args) {
        String playerName = "", playerSur  = "", playerInitial = "", url = "";
        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter player name: (Name Surname)");
        
        String playerFullName = myObj.nextLine();
        
        //Convert v array charov
        char[] charFullName = new char[playerFullName.length()];
        for (int i = 0; i < playerFullName.length(); i++) {
            charFullName[i] = playerFullName.charAt(i);
        }
        
        //Split na ime in priimek
        for (int i = 0; i < playerFullName.length(); i++) { 
            if(charFullName[i] == ' '){      
                for (int j = playerName.length()+1; j < playerFullName.length(); j++) {
                    if(charFullName[j] == '-' || charFullName[j] == '\''){
                        continue;
                    }
                    playerSur += String.valueOf(charFullName[j]);   
                }
                break;  
            }              
            playerName += String.valueOf(charFullName[i]);   
        }      
        
        //Create url
        playerSur = playerSur.substring(0, 5).toLowerCase();
        playerName = playerName.substring(0,2).toLowerCase();
        playerInitial = playerSur.substring(0,1).toLowerCase();
        
        url = "https://www.basketball-reference.com/players/" + playerInitial + "/" + playerSur + playerName + "01.html";
        
        
        //Scrapping
        try{
           Document doc = Jsoup.connect(url).get();
       
           Elements regular = doc.select("tbody > tr[id~=per_game.*]");
           Elements playoffs = doc.select("tbody > tr[id~=playoffs_per_game.*]");
           
           int numberOfSeasons = regular.size() - playoffs.size();


            for (int i = 0; i < numberOfSeasons; i++) {
                Element test = doc.select("td[data-stat=fg3a_per_g]").get(i);
                Element seasons = doc.select("tbody > tr > th[data-stat=season]").get(i);
                System.out.println(seasons.text() + " " + test.text());
            } 
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
