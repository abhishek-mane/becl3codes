<%@ page 
import="java.io.*,java.util.*" 

import ="java.io.FileInputStream"
import ="java.io.IOException"
import ="java.io.InputStream"
import ="java.util.ArrayList"
import ="java.util.StringTokenizer"


%>
<html>
	<head>
		<title>Plagiarism Report</title>
	</head>
	
	<body>
		<h1>Plagiarism Report</h1>
		<ul>
		<li><p>
		<b>percentage plagiarized :</b>
		   <% 
		   	
	String userInput = request.getParameter("newData");
        String originalData = null;
        float totalCount = 0;
        float plagiarizedCount = 0;

        StringTokenizer st1 = new StringTokenizer(userInput, ".");
        totalCount = st1.countTokens();

        ArrayList<String> userLines = new ArrayList<String>();

        while (st1.hasMoreElements()) {
            userLines.add(st1.nextElement().toString());
        }
        //out.println(new File(".").getAbsolutePath());
        InputStream is = new FileInputStream("originalData.txt");
        int originalDataSize = is.available();
        for (int i = 0; i < originalDataSize; i++) {
            originalData += (char) is.read();
        }
        StringTokenizer st2 = new StringTokenizer(originalData, ".");

        ArrayList<String> originalLines = new ArrayList<String>();

        while (st2.hasMoreElements()) {
            originalLines.add(st2.nextElement().toString());
        }

        for (int i = 0; i < userLines.size(); i++) {
            for (int j = 0; j < originalLines.size(); j++) {
                if (userLines.get(i).equals(originalLines.get(j))) {
                    plagiarizedCount++;
                }
            }
        }

        out.println("PLAGARISM : " + ((plagiarizedCount / totalCount) * 100) + "%");

		    	
		    	
		    	
		    	
		    	
		    %>
		</p></li>
		</ul>
	</body>
</html>
