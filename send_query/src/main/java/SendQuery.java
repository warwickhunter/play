/**
 * Copyright Warwick Hunter 2017. All rights reserved.
 */

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

/**
 * An example of how to send a query to a server using an HTTP POST.
 * 
 * You can create a fake server to test against with this Linux command
 * that uses netcat(1).
 * 
 *  while true; do
 *      echo -e "HTTP/1.1 200 OK\n\n $(date)" | nc -l -p 8080; 
 *  done
 * 
 * @author Warwick Hunter (w.hunter@computer.org)
 * @date   2017-11-14
 */
public class SendQuery {

    
    public static void main(String... args) {
        String urlAddress   = "http://localhost:8080";
        String agentId      = "agent99";
        String token        = "token123";
        String queryTerm    = "shoes";
        String queryCommand = "search";

        try {
            new SendQuery().run(urlAddress, agentId, token, queryTerm, queryCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void run(String urlAddress, String agentId, String token, String queryTerm, String queryCommand) throws IOException {

        HttpURLConnection con = null;
        try {
            // Set up the HTTP connection
            URL url = new URL(urlAddress);
            con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setConnectTimeout(2000);
            con.setReadTimeout(2000);
            System.out.printf("Sending request to %s%n", url.toExternalForm());
            
            // Encode the arguments in a format suitable for a www-form-urlencoded POST and write them into the body.
            String body = encodeArguments(agentId, token, queryTerm, queryCommand);
            con.getOutputStream().write(body.getBytes());
            
            // Check the response from the server
            System.out.printf("Response code=%d %s%n", con.getResponseCode(), con.getResponseMessage());
            if (con.getResponseCode() != 200) {
                return;
            }
            
            // Read the response from the server
            try (Scanner scanner = new Scanner(con.getInputStream())) {
                while (scanner.hasNextLine()) {
                    System.out.println(scanner.nextLine());
                }
            }
        } finally { 
            if (con != null) {
                con.disconnect();
            }
        }
    }

    /** Encode the arguments as a single string with each name=value and each pair concatenated with '&' */
    private String encodeArguments(String agentId, String token, String queryTerm, String queryCommand) throws UnsupportedEncodingException {
        StringBuilder s = new StringBuilder();
        s.append("agentID=").append(URLEncoder.encode(agentId, "UTF-8")).append("&");
        s.append("token=").append(URLEncoder.encode(token, "UTF-8")).append("&");
        s.append("query-term=").append(URLEncoder.encode(queryTerm, "UTF-8")).append("&");
        s.append("query-command=").append(URLEncoder.encode(queryCommand, "UTF-8"));
        return s.toString();
    }
}
