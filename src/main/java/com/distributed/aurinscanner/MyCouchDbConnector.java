/* 
 * This file defines CouchDB connector.
 * 
 * Connector information is stored in a separated properties file. (ex: couchdb.properties)
 * 
 * 
 * Authers: Team 10
 *      Ziren Wang, zirenw@student.unimelb.edu.au, 720128
 *      Xiang Xiang, xxiang2@student.unimelb.edu.au, 720138
 *      Yongchul Kim, yongchulk@student.unimelb.edu.au, 750659
 *      Own Daghagheleh, odaghagheleh@student.unimelb.edu.au, 816273
 *      Wen Pin Wu, w.wu47@student.unimelb.edu.au, 871702
 */

package com.distributed.aurinscanner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;

public class MyCouchDbConnector {
    private static HttpClient httpClient = null;
    private static CouchDbInstance dbInstance = null;
    private static CouchDbConnector dbConnector = null;

    public static void initCouchDbConnector(String configFileName) {
        BufferedReader br = null;
        try {
            String line = null;
            String[] lines = new String[4];
            int linesIndex = 0;
            br = new BufferedReader(new FileReader(configFileName));

            while ((line = br.readLine()) != null) {
                if (line.startsWith("#"))
                    continue;

                if (linesIndex == 4) {
                    createCouchDbConnector(lines);
                    linesIndex = 0;
                }
                lines[linesIndex] = line;
                ++linesIndex;
            }
            if (linesIndex == 4) {
                createCouchDbConnector(lines);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void createCouchDbConnector(String[] lines) {
        try {
            String databaseName = null;
            String url = null;
            String username = null;
            String password = null;

            for (int i = 0; i < lines.length; ++i) {
                String[] input = lines[i].split("=");
                if (input[0].equalsIgnoreCase("couchdb.url")) {
                    url = input[1];
                }
                if (input[0].equalsIgnoreCase("couchdb.username")) {
                    username = input[1];
                }
                if (input[0].equalsIgnoreCase("couchdb.password")) {
                    password = input[1];
                }
                if (input[0].equalsIgnoreCase("couchdb.databaseName")) {
                    databaseName = input[1];
                }
            }

            Util.log(String.format("createCouchDbConnector() : %s %s %s %s\n", url, username, password, databaseName));

            httpClient = new StdHttpClient.Builder().url(url).username(username).password(password).build();
            dbInstance = new StdCouchDbInstance(httpClient);

            if (dbInstance.checkIfDbExists(databaseName) == false) {
                dbInstance.createDatabase(databaseName);
            }

            dbConnector = dbInstance.createConnector(databaseName, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CouchDbConnector getCouchDbConnector() {
        return dbConnector;
    }

}
