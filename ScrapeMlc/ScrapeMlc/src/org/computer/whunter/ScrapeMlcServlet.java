/**
 * Copyright Warwick Hunter 2012, all rights reserved.
 */
package org.computer.whunter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;


@SuppressWarnings("serial")
public class ScrapeMlcServlet extends HttpServlet {
    
    private static final String URL = "https://www.mlc.com.au/masterkeyWeb/execute/FramesetUnitPrices";

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println("MLC Unit Price");
        String price = scrape();
        resp.getWriter().println(price);
        sendMail(price);
    }

    private String scrape() {
        try {
            HttpUnitOptions.setScriptingEnabled(false);
            WebConversation client = new WebConversation();
            client.getResponse(URL);
            WebResponse frame = client.getFrameContents("selection");

            Pattern pattern = Pattern.compile("\"MLC Platinum Global Fund \\(closed\\),MasterKey Unit Trust,([^,]+),([^,]+),");
            Matcher matcher = pattern.matcher(frame.getText());
            if (matcher.find() && matcher.groupCount() > 1) {
                return String.format("%s $%s %n", matcher.group(1), matcher.group(2));
            }
        } catch (Exception e) {
            return e.toString();
        }
        return null;
    }
    
    private void sendMail(String msgBody) {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("fiona.m.hunter@gmail.com", "Fiona Hunter"));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress("fiona.m.hunter@gmail.com", "Fiona Hunter"));
            msg.addRecipient(Message.RecipientType.CC, new InternetAddress("warwickhunter@gmail.com", "Warwick Hunter"));
            msg.setSubject("MLC");
            msg.setText(msgBody);
            Transport.send(msg);
        } catch (AddressException e) {
            log(e.toString());
        } catch (MessagingException e) {
            log(e.toString());
        }
        catch (UnsupportedEncodingException e) {
            log(e.toString());
        }        
    }
}
