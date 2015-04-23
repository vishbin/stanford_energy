package edu.stanford.base.config;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.PropertyConfigurator;


/**
 * The Class Log4jInit.
 */
public class Log4jInit extends HttpServlet {

  /* (non-Javadoc)
   * @see javax.servlet.GenericServlet#init()
   */
  public
  void init() {
    String prefix =  getServletContext().getRealPath("/");
    String file = getInitParameter("log4j-init-file");
    // if the log4j-init-file is not set, then no point in trying
    if(file != null) {
      PropertyConfigurator.configure(prefix+file);
    }else{
    	PropertyConfigurator.configure("log4j.properties");
    }
  }

  /* (non-Javadoc)
   * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public
  void doGet(HttpServletRequest req, HttpServletResponse res) {
  }
}