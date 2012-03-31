/**
 * Copyright Warwick Hunter 2012, all rights reserved.
 */
package org.computer.whunter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Invoke another web application and respond with the contents it returns.
 * This service is invoked by a cron job using the GAE cron service and is
 * run periodically. The Cloudbees system doesn't have a cron service, so
 * I use this method to make use of the GAE cron service. 
 * 
 * @author Warwick Hunter (w.hunter@computer.org)
 * @date   2012-04-01
 */
@SuppressWarnings("serial")
public class ScrapeMlcServlet extends HttpServlet {
    
    private static final String THE_URL = "http://scrapemlc.wasa.cloudbees.net/scrape";

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            URL url = new URL(THE_URL);
            URLConnection con = url.openConnection();
            con.setConnectTimeout(20 * 1000);
            con.setReadTimeout(20 * 1000);
            resp.setContentType(con.getContentType());
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                resp.getWriter().println(line);
            }
        } catch (MalformedURLException e) {
            log(e.toString());
        } catch (IOException e) {
            log(e.toString());
        }
    }
}
