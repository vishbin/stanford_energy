
package edu.stanford.widget.dao;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Connection;



@Repository("baseDAOHibernateImpl")
public class BaseDAOHibernateImpl
   
{
	@Resource(name="sessionFactory")
	protected SessionFactory sessionFactory;
    public BaseDAOHibernateImpl()
    {
    }

    public BaseDAOHibernateImpl( SessionFactory sessionFactory)
    {
    	this.sessionFactory=sessionFactory;
    }
    public SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    protected Session getSession()
    {
        return SessionFactoryUtils.doGetSession(sessionFactory, true);
    }

    protected void releaseSession(Session session)
    {
        SessionFactoryUtils.releaseSession(session, sessionFactory);
    }
    
    

    public Connection getConn() {
    	return getSession().connection();
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}



  
    /** The conn. */
	protected Connection conn ;
}