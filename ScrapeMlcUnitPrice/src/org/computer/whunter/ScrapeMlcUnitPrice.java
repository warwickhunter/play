/**
 * Copyright 2012 Warwick Hunter, all rights reserved.
 */
package org.computer.whunter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;

/**
 * Scrape unit prices from the MLC web site
 * @author Warwick Hunter
 * @since  2012-03-28
 */
public class ScrapeMlcUnitPrice {

    public static void main(String[] args) {
        try {
            String url = "https://www.mlc.com.au/masterkeyWeb/execute/FramesetUnitPrices";
     
            HttpUnitOptions.setScriptingEnabled(false);
            WebConversation client = new WebConversation();
            client.getResponse(url);
            WebResponse frame = client.getFrameContents("selection");
        
            Pattern pattern = Pattern.compile("\"MLC Platinum Global Fund \\(closed\\),MasterKey Unit Trust,([^,]+),([^,]+),");
            Matcher matcher = pattern.matcher(frame.getText());
            if (matcher.find() && matcher.groupCount() > 1) {
                System.out.printf("%s $%s %n", matcher.group(1), matcher.group(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
