package com.vehicletracking.model;

import java.io.InputStream;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CommonMessagesDAO {
	
	protected final Log logger = LogFactory.getLog(CommonMessagesDAO.class);
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * @method getCommonMessages to get All Messages which need to send user dynamically.
	 * @param  {No parameters}
	 * @return CommonMessages object
	 */
	public CommonMessages getCommonMessages() {
		logger.info("@@@ inside getCommonMessages DAO method......");
		CommonMessages cMessages = null;
		String propFileName_messages = "/messages.properties";
		Properties properties_messages = new Properties();
		InputStream stream_messages = getClass().getClassLoader()
				.getResourceAsStream(propFileName_messages);
		try {
			properties_messages.load(stream_messages);
			/*logger.info(MessageFormat.format((String)properties_messages.getProperty("invite_Message") , "Manjunath","www.bpatech.com"));
			logger.info(MessageFormat.format((String)properties_messages.getProperty("share_Message") , "Kiran","www.kiran.com"));
			logger.info(MessageFormat.format((String)properties_messages.getProperty("add_Driver_Message") , "Yugander","www.truckindia.com"));*/
			if(StringUtils.isNotBlank((String)properties_messages.getProperty("invite_Message")) || 
					StringUtils.isNotBlank((String)properties_messages.getProperty("share_Message")) ||
					StringUtils.isNotBlank((String)properties_messages.getProperty("add_Driver_Message"))) {
				cMessages = new CommonMessages();
				logger.info("@@@ inside setting messages to Object");
				cMessages.setInvite_Message((String)properties_messages.getProperty("invite_Message"));
				cMessages.setShare_Message((String)properties_messages.getProperty("share_Message"));
				cMessages.setAdd_Driver_Message((String)properties_messages.getProperty("add_Driver_Message"));
			}
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		
		return cMessages;
	}
}
