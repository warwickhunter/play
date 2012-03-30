<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0071)https://grandcentral.cloudbees.com/?josso_assertion_id=066BD2814ED7EE17 -->
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Application under development</title>
  <meta charset="utf-8">
  <link rel="Shortcut Icon" type="image/ico" href="https://di388e0fcqllf.cloudfront.net/images/favicon.png">


<style type="text/css">
* {
    padding: 0;
    margin: 0;
}
body, html {
    margin: 0;
    padding: 0;
    width: 100%;
}
body {
    background-color: white;
    font-family: arial,sans-serif;
    font-size: 13px;
    line-height: 1.4em;
}

.text-xxxl { font-size: 2.143em; } /* 30px */
.text-xxl { font-size: 1.714em; } /* 24px */
.text-xl  { font-size: 1.428em; } /* 20px */
.text-l   { font-size: 1.214em; } /* 17px */
.text-m   { font-size: 1em; }     /* 14px */
.text-s   { font-size: 0.928em; } /* 13px */
.text-xs  { font-size: 0.857em; } /* 12px */
.text-xxs { font-size: 0.786em; } /* 11px */

h1 { font-size: 2.143em; } /* 30px */
h2 { font-size: 1.714em; } /* 24px */
h3 { font-size: 1.428em; } /* 20px */

h1,h2,h3,h4,h5 {
    color: #333;
    font-weight: bold;
    line-height: 1em;
	margin-bottom: 1em;
}
p,ul,ol,fieldset,table,pre {
	margin-bottom: 1.4em;
}
ul li {
    margin-left: 16px;
    list-style: disc;
}

ol li {
    margin-left: 30px;
}


/*============ Links =============*/

a {
	color: #E15200;
	outline: none;
	text-decoration: none;
}

a:hover {
	text-decoration: underline;
}

a:active {
	outline: none;
	text-decoration: none;
}

a.call-action {
    background: #F96C00;
    border-radius: 15px;
    -moz-border-radius: 15px;
    -webkit-border-radius: 15px;
    color: white;
    display: inline-block;
    padding: 4px 10px;
    text-decoration: none;
    text-shadow: 0 -1px 0 rgba(0,0,0,0.2);
    /* add a gradient */
    background: -webkit-gradient(linear, left top, left bottom, from(#FB9649), to(#F96C00));
    background: -moz-linear-gradient(top, #FB9649, #F96C00);
    filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#FB9649', endColorstr='#F96C00');
}

a.call-action:hover {
    background: #FA8E3A;
    box-shadow: 0 0 3px rgba(255,255,255,1);
    -moz-box-shadow: 0 0 3px rgba(255,255,255,1);
    -webkit-box-shadow: 0 0 3px rgba(255,255,255,1);
    text-decoration: none;
}

a.call-action:active {
    background: #F96C00;
    box-shadow: inset 0 1px 2px rgba(0,0,0,0.3);
    -moz-box-shadow: inset 0 1px 2px rgba(0,0,0,0.3);
    -webkit-box-shadow: inset 0 1px 2px rgba(0,0,0,0.3);
    text-decoration: none;
}

a.call-action.text-s {
    padding: 2px 10px;
}

a.call-action.text-l {
    border-radius: 20px;
    -moz-border-radius: 20px;
    -webkit-border-radius: 20px;
    line-height: 1.5em;
    padding: 5px 15px;
}



/*============= Font classes ============*/

.special-font {
	font-family: 'PTSansRegular','Lucida Grande',Arial, sans-serif;
}

.serif {
    font-family: Georgia, serif;
}

.count {
	font-family: Georgia, serif;
	font-size: 1.714em;
}

.color {
	color: #18B2B2;
}

.light {
	color: #666;
}
.light2,
.info {
    color: #999;
}

.italic {
    font-style: italic;
}

.thin {
    font-weight: normal;
}

.or {
    color: #999;
    padding: 0 3px;
}

h3.number {
    line-height: 30px;
}

h3.number span {
    background: #0CC;
    border-radius: 15px;
    -moz-border-radius: 15px;
    -webkit-border-radius: 15px;
    color: white;
    height: 30px;
    display: inline-block;
    text-align: center;
    text-shadow: 0 -1px 1px rgba(0,0,0,0.3);
    width: 30px;
}

.nowrap {
    white-space: nowrap;
}



/*============ Layout classes =============*/

.block,
.show {
	display: block;
}
.left {
	float: left;
	display: inline; /* avoid IE double-margin */
}
.right {
	float: right;
	display: inline; /* avoid IE double-margin */
}
.hide {
    display: none;
}
.inline {
    display: inline;
}

.clear {
    clear: both;
}
.clear-left {
    clear: left;
}
.clear-right {
    clear: right;
}
.clearfix:after {
	clear: both;
	content: ".";
	display: block;
	height: 0;
	visibility: hidden;
}
.clearfix { /* IE workaround */
	display: inline-block;
}
.clearfix { /* IE workaround */
	display: block;
}
* html .clearfix { /* IE workaround */
	height: 1px;
}

img.va {
    vertical-align: middle;
}

li.sep {
    margin-bottom: 0.5em;
}

.align-center {
    text-align: center;
}

.prepend-top {
    margin-top: 30px;
}


/*============ Tables =============*/

table {
    border-collapse: collapse;
    border-spacing: 0;
}

th,td {
	/*borders and padding to make the table readable*/
	/* border: 1px solid #666;*/
	padding: .5em;
	text-align: left;
	vertical-align: top;
}
th {
	/*distinguishing table headers from data cells*/
	font-weight: bold;
	text-align: left;
}

table.t-data1 td,
table.t-data1 th {
    border-bottom: 1px solid #EEE;
    padding: .5em 2em .5em 0;
}

table.color-row tr:nth-child(even) {
    background: #F2F2F2;
}

table.color-row td {
    padding-left: .5em;
}
table.color-row thead th {
    border-bottom: 1px solid #333;
    padding-bottom: 2px;
    padding-left: .5em;
}

/*============ Boxes =============*/

.box,
.notes {
	background: #EEE;
	border-radius: 4px;
	-moz-border-radius: 4px;
	-webkit-border-radius: 4px;
	margin-bottom: 20px;
	padding: 19px;
    width:auto;
}

.notes {
    background: #FFFBEB;
    width:760px;
}

.light-box {
    background: white;
    border: 4px solid #EEE;
    margin-bottom: 40px;
    padding: 16px;
}

.color-box {
    background: white;
    border: 4px solid #0CC;
    margin-bottom: 40px;
    padding: 16px;
    width:auto;
}



#main-content {
    margin-left: 20px;
}


a img, :link img, :visited img {
	border: 0;
}


html {
    background: #EEE;
}


/* all rules prefixed with class="cb-hf" will only be applied to the header and footer */
.cb-hf {
    color: #333;
    font: 13px arial,helvetica,sans-serif;
	line-height: 1.4em;
	text-align: left;
}

.cb-hf ul {
    list-style: none;
    padding: 0;
}

.cb-hf a {
    text-decoration: none;
}
.cb-hf a:hover {
    text-decoration: underline;
}
.cb-hf a:active {
    text-decoration: none;
}

#main-nav li.selected a {
    font-size: 1.3em;
}

.cb-hf .clearfix:after {
	clear: both;
	content: ".";
	display: block;
	height: 0;
	visibility: hidden;
}
.cb-hf .clearfix { /* IE workaround */
	display: inline-block;
}
.cb-hf .clearfix { /* IE workaround */
	display: block;
}
* html .cb-hf .clearfix { /* IE workaround */
	height: 1px;
}


/* ======= header ======= */

#cb-header {
    background: #333 url(/images/bg_header.png) left bottom repeat-x;
    padding: 0 20px;
    text-shadow: 0 -1px 1px rgba(0,0,0,0.8);
}

#cb-header #logo {
    left: 20px;
    position: absolute;
    top: 8px;
}

#cb-header #main-nav {
    color: white;
    font-size: 1.143em;
    padding: 22px 0 0 140px;
}

#cb-header #main-nav ul {
    margin: 0;
    padding: 0;
}

#cb-header #main-nav li {
    display: inline; /* IE */
    float: left;
    margin: 0 20px 0 0;
    padding: 0;
    position: relative;
}

#cb-header #main-nav a {
    border-bottom: 4px solid #333;
    color: white;
    display: block;
    margin: 0 10px;
    padding: 5px 0 10px 0;
}

#cb-header #main-nav a:hover {
    border-bottom: 4px solid white;
    text-decoration: none;
}

#cb-header #main-nav li.selected a {
    border-bottom: 4px solid white;
    font-weight: bold;
}

#cb-header #main-nav a span.sf-sub-indicator {
    background: url(/images/nav_sf_indicator.png) right center no-repeat;
    display: block;
    float: right;
    height: 12px;
    padding: 5px 0 0 4px;
    text-indent: -7777px;
    width: 12px;
}

#cb-header #main-nav li.open {
    background: white;
    border-top-left-radius: 3px;
    border-top-right-radius: 3px;
    -moz-border-radius-topleft: 3px;
    -moz-border-radius-topright: 3px;
    -webkit-border-top-left-radius: 3px;
    -webkit-border-top-right-radius: 3px;
    text-shadow: none;
}

#cb-header #main-nav li.open a {
    border-bottom: 3px solid white;
    padding-left: 10px;
    padding-right: 10px;
}

#cb-header #main-nav ul ul {
    background: white;
    border: 1px solid #333;
    border-top: 0;
    box-shadow: 1px 2px 2px rgba(0,0,0,0.1);
    -moz-box-shadow: 1px 2px 2px rgba(0,0,0,0.1);
    -webkit-box-shadow: 1px 2px 2px rgba(0,0,0,0.1);
    display: none;
    font-size: 0.8em;
    left: -1px;
    padding: 0;
    position: absolute;
    top: 28px;
    z-index: 2000;
}

#cb-header #main-nav ul ul li {
    float: none;
    margin: 0;
}

#cb-header #main-nav ul ul a,
#cb-header #main-nav li.selected ul a,
#cb-header #main-nav li.open ul a {
    border: 0;
    color: #333;
    font-weight: normal;
    padding: 3px 10px;
}

#cb-header #main-nav ul ul a:hover {
    background: #F2F2F2;
    color: #18B2B2;
}

#cb-header #main-nav li.open a {
    color: #333;
    margin: 0;
    text-shadow: none;
}

#cb-header #top-nav {
    border-top: 2px solid #0CC;
    font-size: 0.85em;
    position: absolute;
    right: 20px;
    top: 0;
}

#cb-header #top-nav ul {
    padding: 0;
}

#cb-header #top-nav li {
    display: inline; /* IE */
    float: left;
    margin: 2px 20px 0 0;
}

#cb-header #top-nav li:last-child {
    margin-right: 0;
}

#cb-header #top-nav a {
    color: white;
    display: block;
}

#cb-header #top-nav li.selected a {
    text-decoration: underline;
}

#cb-header #top-nav a.user {
    background: url(/images/top_nav_user.png) left 3px no-repeat;
    font-style: italic;
    padding-left: 35px;
}


/* ======= apps ======= */

#cb-app-container {
    background: white;
    padding: 20px 0 50px 0;
    width: 100%;
}


/* ======= footer ======= */


#cb-footer {
    background: #EEE;
    border-top: 1px solid #DEDEDE;
    color: #666;
    padding: 20px;
    text-shadow: 0 1px 0 white;
}

#cb-footer a,
#cb-footer a:link {
    color: #333;
}

#cb-footer #footer-nav {
    float: left;
    padding: 0;
}

#cb-footer #footer-nav li {
    display: inline; /* IE */
    float: left;
    font-size: 0.85em;
    margin: 0 20px 0 0;
}

#cb-footer .light {
    color: #666;
}

#cb-footer #powered-by {
    float: right;
    font-size: 0.857em;
    text-align: right;
}

#cb-footer #copyright {
    color: #999;
}



</style>

  
</head>

<body>

    <div class="cb-header">
        <a  href="http://www.cloudbees.com"><img src="http://di388e0fcqllf.cloudfront.net/images/cloudbees_logo.png" width="133" height="60" alt="CloudBees" /></a>
    </div>


    <div id="cb-app-container">
        <div id="wrapper" class="clearfix">
                    
            
        
            <div id="main-content">
    
        <h2 class="color text-xl">This application under development</h2>
    

    <p>Your new application is created and now running successfully in the
    CloudBees PaaS (this is it!). Well done!</p>

   <table>
     <tbody><tr>
       <td>
    <h3>What to do next:</h3>

    <ul class="index-list">
        <li class="left box light-box">
          <div class="hudson">
              <h4><a href="https://run.cloudbees.com/">Update your
        application</a></h4>	    
            The console
        development page will have instructions on how you can update
        your application. Hint: You can download and build off this
        starter app from the console if you like.<p/>

	    <p><a href="https://run.cloudbees.com/" class="call-action
	    text-l">» Take me to the Console</a></p>

          </div>
	  <p/>
            <div class="account">
            <div class="form-p">
                <a href="https://cloudbees.zendesk.com/categories/2200-run-cloud">» Support</a>
                <p class="info">(Access support, Wiki, Forum etc.)</p>
              </div>

            </div>
        </li>
    </ul>
       </td>
     
     <td>
  <h3>KnowledgeBase</h3>
  <ul id="index-list">
      <div class="left box color-box">
        
        
        
        <li><a
        href="https://cloudbees.zendesk.com/forums/326992-kbase-run-cloud">RUN@Cloud
        knowledgebase and tutorials</a></li>

	        <li><a
        href="https://cloudbees.zendesk.com/forums/193595-user-forum-run-cloud">Discussion
        forums and Q&A</a></li>

            
           
        
        <li><a
        href="https://cloudbees.zendesk.com/tickets/new">Report
        an issue or problem</a></li>

	
            
        
        <li><a
        href="https://cloudbees.zendesk.com/forums/180582-rfe-and-ideas-run-cloud">Suggest
        an idea or request a feature</a></li>
            
            
        
        <li><a
        href="https://cloudbees.zendesk.com/categories/2202-announcements">Announcements</a></li>

	        <li><a
	        href="http://blog.cloudbees.com">CloudBees
	        blog</a></li>
            

            
        
          <p><a style="float:right" href="https://cloudbees.zendesk.com">More...</a></p>
        
     </div>
  </ul>
     </td>
     </tr>
</tbody></table>


</div>
        </div>
    </div>

   
    <div id="cb-footer" class="cb-hf clearfix">
        <ul id="footer-nav" class="clearfix">
            <li><a href="http://www.cloudbees.com/company-contactus.cb">Contact Us</a></li>
            <li><a href="http://www.cloudbees.com/status.cb">Status</a></li>
            <li><a href="http://www.cloudbees.com/security.cb">Security</a></li>
            <li><a href="http://www.cloudbees.com/company-privacy.cb">Privacy Policy</a></li>
            <li><a href="http://www.cloudbees.com/company-TOS.cb">Terms of Service</a></li>
        </ul>
        <span id="powered-by" class="right">
            Application template by <a href="http://www.cloudbees.com/" title="Go to CloudBees.com">CloudBees</a>
        </span>
        <span id="copyright" class="clear block text-xxs">This template (only): © 2010 Cloud Bees, Inc.</span>
    </div>




</body></html>