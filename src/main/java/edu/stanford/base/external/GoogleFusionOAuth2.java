package edu.stanford.base.external;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.fusiontables.Fusiontables;
import com.google.api.services.fusiontables.Fusiontables.Query.Sql;
import com.google.api.services.fusiontables.Fusiontables.Table.Delete;
import com.google.api.services.fusiontables.FusiontablesScopes;
import com.google.api.services.fusiontables.model.Column;
import com.google.api.services.fusiontables.model.Sqlresponse;
import com.google.api.services.fusiontables.model.Table;
import com.google.api.services.fusiontables.model.TableList;
//import com.google.api.client.util.Lists;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

import edu.stanford.base.config.BeanFactory;

public class GoogleFusionOAuth2 {

	private static Logger logger = Logger.getLogger(GoogleFusionOAuth2.class.getName());

	/**
	 * Be sure to specify the name of your application. If the application name is {@code null} or blank, the application will log a warning. Suggested format is
	 * "MyCompany-ProductName/1.0".
	 */
	private static final String APPLICATION_NAME = "your.app.name";

	/** E-mail address of the service account. Something in the form xxxxx@developer.gserviceaccount.com */
	private static final String SERVICE_ACCOUNT_EMAIL = "574781697884-le64ibr99mtrs44f764u4mb5mvr4v60l@developer.gserviceaccount.com";

	/** Global instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT;

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();

	private static final String DEBUG_TAG = null;

	/**
	 * Used to interact with Fusiontables API
	 */
	private static Fusiontables fusiontables;

	/**
	 * Used to store tables id for deletion once completed
	 */
	private static ArrayList<String> tablesId = new ArrayList<String>();

	private boolean authenticated;

	private String BASE_URL;
	@Autowired
	public	static Properties properties;

	static {

		try {
			properties = (Properties)BeanFactory.getInstance().getBean("properties");
			 
			final String keyFileName=properties.getProperty("key_file_name");
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			// check for valid setup
			if (SERVICE_ACCOUNT_EMAIL.startsWith("Enter ")) {
				System.err.println(SERVICE_ACCOUNT_EMAIL);
				// System.exit(1);
			}
			
			//final String keyFileName = "/home/tcdev/tomcat/conf/bee87348c2d53111c998631e7c87ec60af6c9627-privatekey.p12";

			//final String keyFileName = "C:\\bee87348c2d53111c998631e7c87ec60af6c9627-privatekey.p12";
			String p12Content = Files.readFirstLine(new File(keyFileName), Charset.defaultCharset());
			if (p12Content.startsWith("Please")) {
				System.err.println(p12Content);
				// System.exit(1);
			}
			ArrayList<String> scopes = new ArrayList<String>();
			scopes.add(FusiontablesScopes.FUSIONTABLES);
			// service account credential (uncomment setServiceAccountUser for domain-wide delegation)
			GoogleCredential credential = new GoogleCredential.Builder().setTransport(HTTP_TRANSPORT).setJsonFactory(JSON_FACTORY).setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
					.setServiceAccountScopes(scopes).setServiceAccountPrivateKeyFromP12File(new File(keyFileName))
					// .setServiceAccountUser("user@example.com")
					.build();

			// set up global FusionTables instance
			fusiontables = new Fusiontables.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();

		} catch (Exception e) {
			// FIX ME Add Logger
			e.printStackTrace();
		}
	}
	private static final GoogleFusionOAuth2 INSTANCE = new GoogleFusionOAuth2();

	// Private constructor prevents instantiation from other classes
	private GoogleFusionOAuth2() {
	}

	public static GoogleFusionOAuth2 getInstance() {
		return INSTANCE;
	}

	public static void main(String[] args) throws GeneralSecurityException, IOException {

		GoogleFusionOAuth2 googleFusionOAuth2 = new GoogleFusionOAuth2();
		googleFusionOAuth2.listTables();
		String tableId = null;
		// tableId = createTable("mytable123");

		tableId = "1J6Xjb6YZjtbGLWx4U8lf2Ar2Ruj6QpYFXIZExXc";
		// insertData(tableId);
		googleFusionOAuth2.showRows(tableId);
		// deleteTables();
		logger.info("All operations completed successfully!!!!");

	}

	/** List tables for the authenticated user. */
	public ArrayList<String> listTables() throws IOException {
		logger.info("Listing My Tables");

		// Fetch the table list
		Fusiontables.Table.List listTables = fusiontables.table().list();
		TableList tablelist = listTables.execute();

		if (tablelist.getItems() == null || tablelist.getItems().isEmpty()) {
			logger.info("No tables found!");
			return null;
		}

		tablesId = new ArrayList<String>();
		for (Table table : tablelist.getItems()) {
			tablesId.add(table.getTableId());
			logger.info(table);
			// View.separator();
		}
		return tablesId;
	}

	/** Create a table for the authenticated user. */
	public static String createTable(String tableName) throws IOException {
		logger.info("Create Sample Table");
		// Create a new table
		Table table = new Table();
		table.setName(tableName);
		table.setIsExportable(false);
		table.setDescription(tableName);

		// Set columns for new table
		table.setColumns(Arrays.asList(new Column().setName("Text").setType("STRING"), new Column().setName("Number").setType("NUMBER"),
				new Column().setName("Location").setType("LOCATION"), new Column().setName("Date").setType("DATETIME")));

		// Adds a new column to the table.
		Fusiontables.Table.Insert t = fusiontables.table().insert(table);
		Table r = t.execute();
		logger.info(r);
		tablesId.add(table.getTableId());
		return r.getTableId();
	}

	/** Inserts a row in the newly created table for the authenticated user. */
	private static void insertData(String tableId) throws IOException {
		Sql sql = fusiontables.query().sql(
				"INSERT INTO " + tableId + " (Text,Number,Location,Date) " + "VALUES (" + "'Google Inc', " + "1, " + "'1600 Amphitheatre Parkway Mountain View, "
						+ "CA 94043, USA','" + new DateTime(new Date()) + "')");

		try {
			Sqlresponse response = sql.execute();
			logger.info("Executed query to insert data");
			logger.info("Executed query is " + sql + "\n");
			logger.info("Results from query are:" + "\n");
			logger.info(response.toPrettyString());

		} catch (IllegalArgumentException e) {
			logger.info(e);
		}
	}

	/** Inserts a row in the newly created table for the authenticated user. */
	public String runQuery(String query) throws IOException {
		Sql sql = fusiontables.query().sql(query);
		Sqlresponse response = new Sqlresponse();
		try {
			response = sql.execute();
			logger.info("Executed query to insert data");
			logger.info("Executed query is " + sql + "\n");
			logger.info("Results from query are:" + "\n");
			logger.info(response.toPrettyString());

		} catch (IllegalArgumentException e) {
			logger.info(e);
		}
		return response.toPrettyString();
	}

	/**
	 * Shows the rows from specified table
	 * 
	 * @param tableId
	 *            The table ID
	 * @throws IOException
	 */
	public String showRows(String tableId) throws IOException {
		logger.info("Showing Rows From Table");
		Sql sql = fusiontables.query().sql("SELECT * FROM " + tableId);
		StringBuilder rowBuffer = new StringBuilder();
		try {
			Sqlresponse response = sql.execute();
			logger.info("Showing rows from last query");
			List<List<Object>> rows = response.getRows();
			Iterator<List<Object>> rowsIterator = rows.iterator();

			while (rowsIterator.hasNext()) {
				logger.info("Next Row elements");
				logger.info("*****************");
				List<Object> currentRow = rowsIterator.next();
				Iterator<Object> rowIterator = currentRow.iterator();
				StringBuilder buffer = new StringBuilder();
				while (rowIterator.hasNext()) {
					Object currentRowElement = rowIterator.next();
					buffer.append(currentRowElement.toString());
					buffer.append("|");
				}
				rowBuffer.append(buffer.toString());

				logger.info(buffer.toString());
			}
		} catch (IllegalArgumentException e) {
			logger.info(e);
		}

		return rowBuffer.toString();
	}

	private static void deleteTables() throws IOException {
		logger.info("Delete All Tables");
		// Deletes all tables
		for (String id : tablesId) {
			Delete delete = fusiontables.table().delete(id);
			logger.info("Deleting table with ID " + id);
			delete.execute();
			logger.info("Latest status code from deletion is " + delete.getLastStatusCode());
			logger.info("Latest status message from deletion is " + delete.getLastStatusMessage());
		}
	}

	/**
	 * Inserts a set of rows into the specified table, taking into account the Fusion Table Limits:
	 * 
	 * @param tableId
	 *            The table ID in which data will be inserted
	 * @param sqlStatements
	 *            The SQL statemenst to insert data, this has to be an INSERT
	 * @return TRUE if insertion was correct
	 * @throws IOException
	 *             If an error occurs trying to insert data
	 */
	public boolean insertRow(String tableId, ArrayList<String> sqlStatements) throws IOException {
		if (authenticated) {
			// check fields are okay
			if (tableId != null) {
				if (!tableId.isEmpty()) {
					if (sqlStatements != null) {
						if (!sqlStatements.isEmpty()) {
							AbstractMap<String, Object> tables = new HashMap<String, Object>();
							// check that the provided tableId exists in hashMap
							if (tables.containsKey(tableId)) {
								logger.info("Number of rows to insert is: " + sqlStatements.size());
								// check if the INSERT statemens are more than 500
								if (sqlStatements.size() > 500) {
									// use guava to partition the list in smaller lists of 500 elements max (last one probably will be smaller)
									List<List<String>> bulkList = Lists.partition(sqlStatements, 500);
									// Now iterate the meta-list and for each list
									Iterator<List<String>> iterator = bulkList.iterator();
									String sqlStatement = new String();
									while (iterator.hasNext()) {
										StringBuffer insertBuffer = new StringBuffer();
										// Pack all the strings into ; separeted INSERTS
										List<String> insertList = iterator.next();
										Iterator<String> insertIterator = insertList.iterator();
										while (insertIterator.hasNext()) {
											String currentInsert = insertIterator.next();
											insertBuffer.append(currentInsert);
											insertBuffer.append(";");
										}
										// Store the pack of INSERTs
										sqlStatement = insertBuffer.toString();
										// Execute the query
										HttpContent content = ByteArrayContent.fromString(null, "sql=" + sqlStatement);
										GenericUrl url = new GenericUrl(BASE_URL);
										HttpRequest httpRequest = fusiontables.getRequestFactory().buildPostRequest(url, content);
										try {
											HttpResponse response = httpRequest.execute();
											logger.info(response.toString());

										} catch (IllegalArgumentException e) {
											logger.info("There was an error executing SQL statement to insert row. Aborting");
											return false;
										}
									}
									// All rows inserted correctly
									logger.info("All rows have been inserted correctly!");
									return true;
								}
							} else {
								logger.info("There is no table with ID " + tableId + ".Create the table first.");
								return false;
							}
						} else {
							logger.info("Provided SQL statements are empty. Cannot insert data.");
							return false;
						}
					} else {
						logger.info("Provided SQL statements are invalid. Cannot insert data.");
						return false;
					}
				} else {
					logger.info("Provided table ID is empty. Cannot insert data.");
					return false;
				}
			} else {
				logger.info("Provided table ID is invalid. Cannot insert data.");
				return false;
			}
		}
		return authenticated;
	}

	/**
	 * Inserts a set of rows into the specified table, taking into account the Fusion Table Limits:
	 * 
	 * @param tableId
	 *            The table ID in which data will be inserted
	 * @param sqlStatements
	 *            The SQL statemenst to insert data, this has to be an INSERT
	 * @return TRUE if insertion was correct
	 * @throws IOException
	 *             If an error occurs trying to insert data
	 */
	public boolean insertRows(String tableId, ArrayList<String> sqlStatements) throws IOException {
		if (authenticated) {
			// check fields are okay
			if (tableId != null) {
				if (!tableId.isEmpty()) {
					if (sqlStatements != null) {
						if (!sqlStatements.isEmpty()) {
							AbstractMap<String, Object> tables = new HashMap<String, Object>();
							// check that the provided tableId exists in hashMap
							if (tables.containsKey(tableId)) {
								logger.info("Number of rows to insert is: " + sqlStatements.size());
								// check if the INSERT statemens are more than 500
								if (sqlStatements.size() > 500) {
									// use guava to partition the list in smaller lists of 500 elements max (last one probably will be smaller)
									List<List<String>> bulkList = Lists.partition(sqlStatements, 500);
									// Now iterate the meta-list and for each list
									Iterator<List<String>> iterator = bulkList.iterator();
									String sqlStatement = new String();
									while (iterator.hasNext()) {
										StringBuffer insertBuffer = new StringBuffer();
										// Pack all the strings into ; separeted INSERTS
										List<String> insertList = iterator.next();
										Iterator<String> insertIterator = insertList.iterator();
										while (insertIterator.hasNext()) {
											String currentInsert = insertIterator.next();
											insertBuffer.append(currentInsert);
											insertBuffer.append(";");
										}
										// Store the pack of INSERTs
										sqlStatement = insertBuffer.toString();
										// Execute the query
										HttpContent content = ByteArrayContent.fromString(null, "sql=" + sqlStatement);
										GenericUrl url = new GenericUrl(BASE_URL);
										HttpRequest httpRequest = fusiontables.getRequestFactory().buildPostRequest(url, content);
										try {
											HttpResponse response = httpRequest.execute();
											logger.info(response.toString());

										} catch (IllegalArgumentException e) {
											logger.info("There was an error executing SQL statement to insert row. Aborting");
											return false;
										}
									}
									// All rows inserted correctly
									logger.info("All rows have been inserted correctly!");
									return true;
								}
							} else {
								logger.info("There is no table with ID " + tableId + ".Create the table first.");
								return false;
							}
						} else {
							logger.info("Provided SQL statements are empty. Cannot insert data.");
							return false;
						}
					} else {
						logger.info("Provided SQL statements are invalid. Cannot insert data.");
						return false;
					}
				} else {
					logger.info("Provided table ID is empty. Cannot insert data.");
					return false;
				}
			} else {
				logger.info("Provided table ID is invalid. Cannot insert data.");
				return false;
			}
		}
		return authenticated;
	}

}