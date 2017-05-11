/* 
 * This file defines Aurin data scanner.
 * 
 *The data will be read from a json file
 *The required data extracted and stored in new json object
 *The new created data then uploaded to couchdb
 * 
 * Authers: Team 10
 *      Ziren Wang, zirenw@student.unimelb.edu.au, 720128
 *      Xiang Xiang, xxiang2@student.unimelb.edu.au, 720138
 *      Yongchul Kim, yongchulk@student.unimelb.edu.au, 750659
 *      Own Daghagheleh, odaghagheleh@student.unimelb.edu.au, 816273
 *      Wen Pin Wu, w.wu47@student.unimelb.edu.au, 871702
 */

package com.distributed.aurinscanner;

import com.fasterxml.jackson.databind.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.*;
import org.json.simple.parser.*;


public class aurinTest {


    public static void main(String[] args) throws UnsupportedEncodingException, IOException, ParseException {
        
        try {
            //Please specify the Aurin file to proccess
            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("Aurin json file location- Please change me"), "UTF-8"));
                ObjectMapper mapper = new ObjectMapper();
                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(input);
                JSONArray jsonArray = (JSONArray) json.get("features");
                MyCouchDbConnector.initCouchDbConnector(Settings.getCouchDbProperties());
                
                //Coucnt will be used as an id in couchdb
                int count = 10000;
                
            //In order to use any data please uncomment the relavant sections
/////////////////////Mlebourn map//////////////////////////////////////////////////////////////////////////////////////////////////     
//                  for(Object o:jsonArray){
//                    
//                    
//                    JSONObject jsonIn =(JSONObject) o;
//                    
//                    
//                    JSONObject geometry =(JSONObject) jsonIn.get("geometry");
//                    JSONObject properties =(JSONObject) jsonIn.get("properties");
//                    String pc_pid = properties.get("pc_pid").toString();
//                    
//                        count++;
//                        String postcode = properties.get("postcode").toString();
//
//                        JSONObject dataOut = new JSONObject();
//                        dataOut.put("pc_pid", pc_pid);
//                        dataOut.put("geometry", geometry);
//                        dataOut.put("postcode", postcode);
//                        System.out.println(dataOut.toJSONString());
//                    
//                   MyCouchDbConnector.getCouchDbConnector().create(String.valueOf(count), dataOut);
/////////////////////Mlebourn map//////////////////////////////////////////////////////////////////////////////////////////////////    
/////////////////////////////PSMA_Postcodes__Polygon___August_2016_///////////////////////////////////////////////////////////////
//                for(Object o:jsonArray){
//                    
//                    
//                    JSONObject jsonIn =(JSONObject) o;
//                    
//                    
//                    JSONObject geometry =(JSONObject) jsonIn.get("geometry");
//                    JSONObject properties =(JSONObject) jsonIn.get("properties");
//                    String gid = properties.get("gid").toString();
//                    
//                        count++;
//                        String allvehs = properties.get("allvehs").toString();
//                        String trucks = properties.get("trucks").toString();
//
//                        JSONObject dataOut = new JSONObject();
//                        dataOut.put("gid", gid);
//                        dataOut.put("geometry", geometry);
//                        dataOut.put("allvehs", allvehs);
//                        dataOut.put("trucks", trucks);
//                        System.out.println(dataOut.toJSONString());
//                    
//                    MyCouchDbConnector.getCouchDbConnector().create(String.valueOf(count), dataOut);
                    
  ////////////////////////////PSMA_Postcodes__Polygon___August_2016_///////////////////////////////////////////////////////////////                  
 /////////////////////Black_Spots_2007_-_2012_for_Victoria//////////////////////////////////////////////////////////////////////////////////////////////////                  
//                    JSONObject jsonIn =(JSONObject) o;
//                    
//                    count++;
//                    JSONObject geometry =(JSONObject) jsonIn.get("geometry");
//                    JSONObject properties =(JSONObject) jsonIn.get("properties");
//                    String id = properties.get("id").toString();
//                    String accidents = properties.get("accidents").toString();
//                    
//                    JSONObject dataOut = new JSONObject();
//                    dataOut.put("pointId", id);
//                    dataOut.put("geometry", geometry);
//                    dataOut.put("accidents", accidents);
//                    System.out.println(dataOut.toJSONString());
                    //MyCouchDbConnector.getCouchDbConnector().create(String.valueOf(count), dataOut);
 /////////////////////Black_Spots_2007_-_2012_for_Victoria//////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////Arterial_Road_Annual_Average_Daily_Traffic_Volumes_2001__2008-2011_for_Victoria.json//////////////////////////////////////////////////
//                    JSONObject jsonIn =(JSONObject) o;
//                    JSONObject properties =(JSONObject) jsonIn.get("properties");
//                    JSONObject geometry =(JSONObject) jsonIn.get("geometry");
//                    String gid = properties.get("gid").toString();
//                    
//                    if(properties.get("aadt_2011") != null && properties.get("directio_1") != null){
//                        count++;
////                        String aadt2011 = properties.get("aadt_2011").toString();
////                        System.out.println(count+"  "+gid+"    "+aadt2011);
////                       System.out.println(properties.get("directio_1").toString());
//                        
//                        
//                       Long id = Long.valueOf(gid.replaceAll("\\s", ""));
//                       
//                        JSONObject dataOut = new JSONObject();
//                        dataOut.put("id", gid);
//                        dataOut.put("geometry", geometry);
//                        dataOut.put("aadt_2011", properties.get("aadt_2011"));
//                        dataOut.put("directio_1", properties.get("directio_1"));
//                        System.out.println(dataOut.toJSONString());
//                      // MyCouchDbConnector.getCouchDbConnector().create(String.valueOf(count), dataOut);
 /////////////////////Arterial_Road_Annual_Average_Daily_Traffic_Volumes_2001__2008-2011_for_Victoria.json//////////////////////////////////////////////////                      
//                    }
                   // System.out.println(properties);   
//                }
                System.out.println(count);   
        }catch (FileNotFoundException ex) {
        }
    }
    
}
