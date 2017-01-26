import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.parasoft.api.*;
import com.parasoft.tool.*;
import webtool.test.*;


public String setup(Object input, ExtensionToolContext context)
{

    Application.showMessage("Clean DB");
	final String r1 =  cleanDb(input, context);
	Application.showMessage("Clean DB Ok");
	Application.showMessage("Create User");
	final String r2 =  createUesr(input, context);
	Application.showMessage("Create User Ok");
	Application.showMessage("Staring Coverage Agent Ok");
	final String r3 = startCoverageAgentTest(input, context);
	Application.showMessage(r3);
	
	return r1 + r2 + r3;	
}

public String teardown(Object input, ExtensionToolContext context)
{
	Application.showMessage("Stopping Coverage Agent");
	final String r = stopCoverageAgent(input, context);
	Application.showMessage("Stopping Coverage Agent Ok");
	Application.showMessage(r);
	return r;
}


public String stopCoverageAgent(Object input, ExtensionToolContext context)
{
	String COVERAGE_AGENT = context.getEnvironmentVariableValue("COVERAGE_AGENT");
	if(COVERAGE_AGENT == null || COVERAGE_AGENT.isEmpty())
	{
		return "'COVERAGE_AGENT' is not set or empty. Set an appropriate value";
	}
	String url = "" + COVERAGE_AGENT + "test/stop";
	String contentType = "application/json; charset=UTF-8";

    Test test = (Test) context.getParentContext();
    Tool tool = test.getTool();
    String id = TestUtil.getWkId(tool,null);
	String payload = "";
	
	String ret = sendGetRequest(new URL(url), payload, contentType);
	
	return ret;


}

public String startCoverageAgentTest(Object input, ExtensionToolContext context)
{
	String COVERAGE_AGENT = context.getEnvironmentVariableValue("COVERAGE_AGENT");
	if(COVERAGE_AGENT == null || COVERAGE_AGENT.isEmpty())
	{
		return "'COVERAGE_AGENT' is not set or empty. Set an appropriate value";
	}
	String url = "" + COVERAGE_AGENT + "test/start";
	String contentType = "application/json; charset=UTF-8";

    Test test = (Test) context.getParentContext();
    Tool tool = test.getTool();
    String id = TestUtil.getWkId(tool,null);
	String payload = "{\"test\":\"" + id + "\"}";
	
	Application.showMessage("Coverage Agent: Starting Test [id: " + id +"]");
	String ret = sendPostRequest(new URL(url), payload, contentType);
	
	return ret;
}

public String sendPostRequest(URL url, String payload, String contentType)
{
	try
	{
	// Opens a HTTP Connection.
	HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	
	// Set the do DoInput/DoOutput flag to true if you intend to use URL connection for input/output.
	connection.setDoInput(true);
	connection.setDoOutput(true);
	
	// Set REST Method.
	connection.setRequestMethod("POST");
	
	// Add Request Header.
	connection.setRequestProperty("Content-Type", contentType);
	
	// Writes the payload into the request.
	OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
	writer.write(payload);
	writer.close();
	
	// Obtains the response code.
	int responseCode = connection.getResponseCode();
	
	// Obtains the response header.
	StringBuffer responseHeader = new StringBuffer();
	Map<String, List<String>> map = connection.getHeaderFields();
	for (Map.Entry<String, List<String>> entry : map.entrySet())
	{
		responseHeader.append(entry.getKey() + ": " + entry.getValue());
	}
	String responseHeaderString = responseHeader.toString();
	responseHeaderString = responseHeaderString.replaceAll("null: ", "");
	responseHeaderString = responseHeaderString.replaceAll("\\[", "");
	responseHeaderString = responseHeaderString.replaceAll("\\]", " ");
	
	// Obtains the response body.
	BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	String inputLine;
	StringBuffer responseBody = new StringBuffer();
	while ((inputLine = reader.readLine()) != null)
	{
		responseBody.append(inputLine);
	}
	reader.close();
	
	// Disconnects connection.
	connection.disconnect();
	
	// Prints response to console.
	//Application.showMessage("ResponseHeader: " + responseHeaderString);
	//Application.showMessage("ResponseBody: " + responseBody.toString());
	return responseBody;
	}
	catch (Exception e)
	{
	// Prints error message to console.
	Application.showMessage(e.getMessage());
	}
}


public String sendGetRequest(URL url, String payload, String contentType)
{
	try
	{
	// Opens a HTTP Connection.
	HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	
	// Set the do DoInput/DoOutput flag to true if you intend to use URL connection for input/output.
	connection.setDoInput(true);
	connection.setDoOutput(true);
	
	// Set REST Method.
	connection.setRequestMethod("GET");
	
	// Add Request Header.
	connection.setRequestProperty("Content-Type", contentType);
	
	// Writes the payload into the request.
	OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
	writer.write(payload);
	writer.close();
	
	// Obtains the response code.
	int responseCode = connection.getResponseCode();
	
	// Obtains the response header.
	StringBuffer responseHeader = new StringBuffer();
	Map<String, List<String>> map = connection.getHeaderFields();
	for (Map.Entry<String, List<String>> entry : map.entrySet())
	{
		responseHeader.append(entry.getKey() + ": " + entry.getValue());
	}
	String responseHeaderString = responseHeader.toString();
	responseHeaderString = responseHeaderString.replaceAll("null: ", "");
	responseHeaderString = responseHeaderString.replaceAll("\\[", "");
	responseHeaderString = responseHeaderString.replaceAll("\\]", " ");
	
	// Obtains the response body.
	BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	String inputLine;
	StringBuffer responseBody = new StringBuffer();
	while ((inputLine = reader.readLine()) != null)
	{
		responseBody.append(inputLine);
	}
	reader.close();
	
	// Disconnects connection.
	connection.disconnect();
	
	// Prints response to console.
	//Application.showMessage("ResponseHeader: " + responseHeaderString);
	//Application.showMessage("ResponseBody: " + responseBody.toString());
	return responseBody;
	}
	catch (Exception e)
	{
	// Prints error message to console.
	Application.showMessage(e.getMessage());
	}
}


public String createUesr(Object input, ExtensionToolContext context) throws Exception {

	String url = context.getEnvironmentVariableValue("URL") + "/parabank/register.htm";
	URL obj = new URL(url);
	HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	
	//add reuqest header
	con.setRequestMethod("POST");
	String USER_AGENT = "Mozilla/5.0";
	con.setRequestProperty("User-Agent", USER_AGENT);
	con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	//Application.showMessage("Step 2");

	String customerUserName = context.getEnvironmentVariableValue("customer.username");
	String customerPassword = context.getEnvironmentVariableValue("customer.password");
	
	String customerFirstName = context.getEnvironmentVariableValue("customer.firstname");
	String customerLastName = context.getEnvironmentVariableValue("customer.lastname");

	String urlParameters = 
			String.format(
			   "customer.firstName=%s"
			+ "&customer.lastName=%s"
			+ "&customer.address.street=%s"
			+ "&customer.address.city=%s"
			+ "&customer.address.state=%s"
			+ "&customer.address.zipCode=%s"
			+ "&customer.phoneNumber=%s"
			+ "&customer.ssn=%s"
			+ "&customer.username=%s"
			+ "&customer.password=%s"
			+ "&repeatedPassword=%s", customerFirstName, customerLastName, "street", "city", "state", "zipcode", "phone", "ssn", customerUserName, customerPassword, customerPassword);
			
	Application.showMessage(urlParameters);
	
	// Send post request
	con.setDoOutput(true);
	DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	wr.writeBytes(urlParameters);
	wr.flush();
	wr.close();
	//Application.showMessage("Step 3");

	int responseCode = con.getResponseCode();

	BufferedReader inp = new BufferedReader(
	        new InputStreamReader(con.getInputStream()));
	String inputLine;
	StringBuffer response = new StringBuffer();

	while ((inputLine = inp.readLine()) != null) {
		response.append(inputLine);
	}
	inp.close();
	
	//print result
	System.out.println(response.toString());
	
	//Application.showMessage("Step 4");
	Application.showMessage(response.toString());
	return response.toString();

}




public String cleanDb(Object input, ExtensionToolContext context) throws Exception {

	//Application.showMessage("Step 1");
	String url = context.getEnvironmentVariableValue("URL") + "/parabank/db.htm";
	URL obj = new URL(url);
	HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	
	//add reuqest header
	con.setRequestMethod("POST");
	String USER_AGENT = "Mozilla/5.0";
	con.setRequestProperty("User-Agent", USER_AGENT);
	con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	//Application.showMessage("Step 2");

	String urlParameters = "action=CLEAN";
	
	// Send post request
	con.setDoOutput(true);
	DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	wr.writeBytes(urlParameters);
	wr.flush();
	wr.close();
	//Application.showMessage("Step 3");

	int responseCode = con.getResponseCode();

	BufferedReader inp = new BufferedReader(
	        new InputStreamReader(con.getInputStream()));
	String inputLine;
	StringBuffer response = new StringBuffer();

	while ((inputLine = inp.readLine()) != null) {
		response.append(inputLine);
	}
	inp.close();
	
	//print result
	System.out.println(response.toString());
	
	//Application.showMessage("Step 4");
	Application.showMessage(response.toString());
	return response.toString();

}