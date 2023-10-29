import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AssignmentServlet", urlPatterns = {"/assignment/*"})
public class AssignmentServlet extends HttpServlet {

	
	    @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
	        // Handle GET requests (e.g., to list customers)
	        String cmd = request.getParameter("cmd");

	        if ("get_customer_list".equals(cmd)) {
	            // Get the bearer token from the request header
	            String bearerToken = request.getHeader("Authorization");
	            if (bearerToken == null || bearerToken.isEmpty()) {
	                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	                return;
	            }

	            try {
	                // Make a GET request to fetch the customer list
	                URL url = new URL("https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list");
	                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	                connection.setRequestMethod("GET");
	                connection.setRequestProperty("Authorization", bearerToken);

	                int responseCode = connection.getResponseCode();

	                if (responseCode == HttpURLConnection.HTTP_OK) {
	                    // Read the response and send it to the client
	                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	                    String line;
	                    PrintWriter out = response.getWriter();
	                    while ((line = reader.readLine()) != null) {
	                        out.println(line);
	                    }
	                    reader.close();
	                } else {
	                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	                }

	                connection.disconnect();
	            } catch (Exception e) {
	                e.printStackTrace();
	                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	            }
	        } else {
	            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Invalid command
	        }
	    }

	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
	        // Handle POST requests (e.g., create, update, delete customers)
	        String cmd = request.getParameter("cmd");

	        if ("create".equals(cmd)) {
	            // Handle creating a new customer
	            // Extract request parameters and body
	            String bearerToken = request.getHeader("Authorization");
	            if (bearerToken == null || bearerToken.isEmpty()) {
	                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	                return;
	            }

	            // Read the request body, parse JSON, and create a new customer
	            // Send appropriate response codes and messages
	            // ...

	        } else if ("update".equals(cmd)) {
	            // Handle updating a customer
	            // Extract parameters and body, validate, and perform the update
	            // Send appropriate response codes and messages
	            // ...

	        } else if ("delete".equals(cmd)) {
	            // Handle deleting a customer
	            // Extract parameters, validate, and perform the delete
	            // Send appropriate response codes and messages
	            // ...

	        } else {
	            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Invalid command
	        }
	    }
	}

    

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
            String cmd = request.getParameter("cmd");
            String bearerToken = request.getHeader("Authorization");

            if (bearerToken == null || bearerToken.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            if ("create".equals(cmd)) {
                // Handle creating a new customer
                try {
                    URL url = new URL("https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=create");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Authorization", bearerToken);
                    connection.setRequestProperty("Content-Type", "application/json");

                    // Extract and read the request body from the client
                    BufferedReader reader = request.getReader();
                    StringBuilder requestBody = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        requestBody.append(line);
                    }

                    // Send the request body to the external API
                    connection.setDoOutput(true);
                    try (OutputStream os = connection.getOutputStream()) {
                        byte[] input = requestBody.toString().getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_CREATED) {
                        response.setStatus(HttpServletResponse.SC_CREATED);
                    } else {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    }

                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            } else if ("update".equals(cmd) || "delete".equals(cmd)) {
                // Handle updating or deleting a customer
                String uuid = request.getParameter("uuid");
                if (uuid == null || uuid.isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }

                try {
                    URL url = new URL("https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=" + cmd + "&uuid=" + uuid);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Authorization", bearerToken);

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        response.setStatus(HttpServletResponse.SC_OK);
                    } else if (responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    } else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    }

                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Invalid command
            }
        }
    }