
package edu.stanford.base.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.stanford.base.rest.commands.GetEnergyData;



public class BeanFactory
{
	/** The Constant log. */
	private static final Log log = LogFactory.getLog(BeanFactory.class);
    protected BeanFactory()
    {
        ctx = null;
        String paths[] = {
            "applicationContext.xml"
        };
        ctx = new ClassPathXmlApplicationContext(paths);
    }

    public static BeanFactory getInstance()
    {
        if(instance == null){
        	
        	log.info("*****************************************************************************");
        	log.info("******************CREATIING NEW BEANFACTORY INSTANCE*************************");
        	log.info("*****************************************************************************");
        	instance = new BeanFactory();
        	log.info("*****************************************************************************");
        	log.info("******************   "+instance+"  *************************");
        	log.info("*****************************************************************************");
        	
        	
        }
            
        return instance;
    }

    public Object getBean(String name)
    {
        return ctx.getBean(name);
    }

    protected ApplicationContext ctx;
    protected static BeanFactory instance = null;

}