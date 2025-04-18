/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.util;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.Adempiere;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Message;
import org.compiere.model.MSysConfig;

/**
 *	Contain static methods to access AD_Message, AD_Element and its translations.
 *
 *  @author     Jorg Janke
 *  @version    $Id: Msg.java,v 1.2 2006/07/30 00:54:36 jjanke Exp $
 */
public final class Msg
{
	/** Initial size of HashMap     */
	private static final int 		MAP_SIZE = 2000;
	/**  Separator between Msg and optional Tip     */
	private static final String     SEPARATOR = Env.NL + Env.NL;

	/**	Singleton						*/
	private static	Msg				s_msg = null;

	/**	Logger							*/
	private static CLogger			s_log = CLogger.getCLogger (Msg.class);

	/**
	 * 	Get the singleton Message instance
	 *	@return Msg
	 */
	public static synchronized Msg get()
	{
		if (s_msg == null)
			s_msg = new Msg();
		return s_msg;
	}	//	get
	
	/**
	 *	Private constructor, use {@link #get()} to access the singleton instance 
	 */
	private Msg()
	{
	}	//	Mag

	/**  Language:(Message Key:Message Text)            */
	private Map<String,CCache<String,String>> m_languages 
		= new HashMap<String, CCache<String,String>>();
	
	/** Language:(Element Column Name, Element Name) */
	private Map<String,CCache<String,String>> m_elementNameCache 
		= new HashMap<String,CCache<String,String>>();
	
	/** Language:(Element Column Name, Element Print Name) */
	private Map<String,CCache<String,String>> m_elementPrintNameCache 
		= new HashMap<String,CCache<String,String>>();

	/**
	 *  Get Language specific Message Map
	 *  @param ad_language Language Key
	 *  @return HashMap of Language
	 */
	public synchronized CCache<String,String> getMsgMap (String ad_language)
	{
		String AD_Language = ad_language;
		if (AD_Language == null || AD_Language.length() == 0)
			AD_Language = Language.getBaseAD_Language();
		//  Do we have the language ?
		CCache<String,String> retValue = (CCache<String,String>)m_languages.get(AD_Language);
		if (retValue != null && retValue.size() > 0)
			return retValue;

		//  Load Language
		retValue = initMsg(AD_Language, retValue);
		if (retValue != null)
		{
			m_languages.put(AD_Language, retValue);
			return retValue;
		}
		return retValue;
	}   //  getMsgMap
	
	/**
	 * Get language specific translation map for AD_Element
	 * @param ad_language
	 * @return ad_element map
	 */
	public synchronized CCache<String,String> getElementMap (String ad_language)
	{
		String AD_Language = ad_language;
		if (AD_Language == null || AD_Language.length() == 0)
			AD_Language = Language.getBaseAD_Language();
		//  Do we have the language ?
		CCache<String,String> retValue = (CCache<String,String>)m_elementNameCache.get(AD_Language);
		if (retValue != null)
			return retValue;

		retValue = new CCache<String, String>(I_AD_Element.Table_Name, I_AD_Element.Table_Name + "|" + AD_Language, 100, 0, false, 0);
		m_elementNameCache.put(AD_Language, retValue);
		return retValue;
	}
	
	/**
	 * Get language specific translation map for AD_Element print name
	 * @param ad_language
	 * @return map of element column name:print name
	 */
	public synchronized CCache<String,String> getElementPrintNameMap (String ad_language)
	{
		String AD_Language = ad_language;
		if (AD_Language == null || AD_Language.length() == 0)
			AD_Language = Language.getBaseAD_Language();
		//  Do we have the language ?
		CCache<String,String> retValue = (CCache<String,String>)m_elementPrintNameCache.get(AD_Language);
		if (retValue != null)
			return retValue;

		retValue = new CCache<String, String>(I_AD_Element.Table_Name, I_AD_Element.Table_Name + "|" + AD_Language, 100, 0, false, 0);
		m_elementPrintNameCache.put(AD_Language, retValue);
		return retValue;
	}

	/**
	 *	Populate message HashMap.
	 *  @param AD_Language Language
	 *  @param msg cache map to append to
	 *  @return Cache HashMap
	 */
	private CCache<String,String> initMsg (String AD_Language, CCache<String,String> msg)
	{
		if (msg == null)
			msg = new CCache<String,String>(I_AD_Message.Table_Name, I_AD_Message.Table_Name + "|" + AD_Language, MAP_SIZE, 0, false, 0);
		//
		if (!DB.isConnected())
		{
			s_log.log(Level.SEVERE, "No DB Connection");
			return null;
		}
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			if (AD_Language == null || AD_Language.length() == 0 || Env.isBaseLanguage(AD_Language, "AD_Language"))
				pstmt = DB.prepareStatement("SELECT Value, MsgText, MsgTip FROM AD_Message WHERE IsActive ='Y'",  null);
			else
			{
				pstmt = DB.prepareStatement("SELECT m.Value, t.MsgText, t.MsgTip "
					+ "FROM AD_Message_Trl t, AD_Message m "
					+ "WHERE m.AD_Message_ID=t.AD_Message_ID"
					+ " AND t.AD_Client_ID = 0" // load only translated messages at System level (using Value as key)
					+ " AND m.IsActive ='Y' AND t.IsActive ='Y'"
					+ " AND t.AD_Language=?", null);
				pstmt.setString(1, AD_Language);
			}
			rs = pstmt.executeQuery();
			addMessagesInCache(rs, msg);
			DB.close(rs, pstmt);

			// load translated messages at tenant level (using AD_Client_ID|Value as key)
			pstmt = DB.prepareStatement("SELECT t.AD_Client_ID || '|' || m.Value, t.MsgText, t.MsgTip"
					+ " FROM AD_Message_Trl t, AD_Message m"
					+ " WHERE m.AD_Message_ID=t.AD_Message_ID"
					+ " AND t.AD_Client_ID != 0"
					+ " AND m.IsActive ='Y' AND t.IsActive ='Y'"
					+ " AND t.AD_Language=?", null);
			pstmt.setString(1, AD_Language);
			rs = pstmt.executeQuery();
			addMessagesInCache(rs, msg);
		}
		catch (SQLException e)
		{
			s_log.log(Level.SEVERE, "initMsg", e);
			return null;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		//
		if (msg.size() < 100)
		{
			s_log.log(Level.SEVERE, "Too few (" + msg.size() + ") Records found for " + AD_Language);
			return null;
		}
		if (s_log.isLoggable(Level.INFO)) s_log.info("Records=" + msg.size() + " - " + AD_Language);
		return msg;
	}	//	initMsg

	/**
	 * Add AD_Message record from result set to msg
	 * @param rs
	 * @param msg
	 * @throws SQLException
	 */
	private void addMessagesInCache(ResultSet rs, CCache<String,String> msg) throws SQLException {
		//	get values
		while (rs.next())
		{
			String AD_Message = rs.getString(1);
			StringBuilder MsgText = new StringBuilder();
			MsgText.append(rs.getString(2));
			String MsgTip = rs.getString(3);
			//
			if (MsgTip != null)			//	messageTip on next line, if exists
				MsgText.append(" ").append(SEPARATOR).append(MsgTip);
			msg.put(AD_Message, MsgText.toString());
		}
	}
	
	/**
	 *  Reset/clear message cache
	 */
	public synchronized void reset()
	{
		if (m_languages == null)
			return;

		//  clear all languages
		Iterator<CCache<String, String>> iterator = m_languages.values().iterator();
		while (iterator.hasNext())
		{
			CCache<String, String> hm = iterator.next();
			hm.reset();
		}
	}   //  reset

	/**
	 *  Return an array of the installed Languages
	 *  @return Array of loaded Languages or null
	 */
	public synchronized String[] getLanguages()
	{
		if (m_languages == null)
			return null;
		String[] retValue = new String[m_languages.size()];
		m_languages.keySet().toArray(retValue);
		return retValue;
	}   //  getLanguages

	/**
	 *  Check if Language is loaded
	 *  @param language Language code
	 *  @return true, if language is loaded
	 */
	public synchronized boolean isLoaded (String language)
	{
		if (m_languages == null)
			return false;
		return m_languages.get(language) != null && !m_languages.get(language).isEmpty();
	}   //  isLoaded

	/**
	 *  Lookup translated text from message map
	 *  @param AD_Language language
	 *  @param text message key
	 *  @return translated message text or null
	 */
	private String lookup (String AD_Language, String text)
	{
		if (text == null)
			return null;
		if (AD_Language == null || AD_Language.length() == 0)
			return text;
		//  hardcoded trl
		if (text.equals("/") || text.equals("\\"))
			return File.separator;
		if (text.equals(";") || text.equals(":"))
			return File.pathSeparator;
		if (text.equals("IDEMPIERE_HOME"))
			return Adempiere.getAdempiereHome();
		if (text.equals("bat") || text.equals("sh"))
		{
			if (System.getProperty("os.name").startsWith("Win"))
				return "bat";
			return "sh";
		}
		if (text.equals("CopyRight"))
			return Adempiere.COPYRIGHT;
		//
		CCache<String, String> langMap = getMsgMap(AD_Language);
		if (langMap == null)
			return null;

		if (MSysConfig.getBooleanValue(MSysConfig.MESSAGES_AT_TENANT_LEVEL, false, Env.getAD_Client_ID(Env.getCtx()))) {
			String msg = (String) langMap.get(Env.getAD_Client_ID(Env.getCtx()) + "|" + text);
			if (!Util.isEmpty(msg))
				return msg;
		}

		return (String)langMap.get(text);
	}   //  lookup
	
	/**
	 *	Get translated text for AD_Message
	 *  @param  ad_language - Language
	 *  @param	AD_Message - Message Key
	 *  @return translated text
	 */
	public static String getMsg (String ad_language, String AD_Message)
	{
		if (AD_Message == null || AD_Message.length() == 0)
			return "";
		//
		String AD_Language = ad_language;
		if (AD_Language == null || AD_Language.length() == 0)
			AD_Language = Language.getBaseAD_Language();
		//
		String retStr = get().lookup (AD_Language, AD_Message);
		//
		if (retStr == null || retStr.length() == 0)
		{
			s_log.warning("NOT found: " + AD_Message);
			return AD_Message;
		}

		return retStr;
	}	//	getMsg

	/**
	 *  Get translated text message for AD_Message
	 *  @param  ctx Context to retrieve language
	 *  @param	AD_Message - Message Key
	 *  @return translated text
	 */
	public static String getMsg (Properties ctx, String AD_Message)
	{
		return getMsg (Env.getAD_Language(ctx), AD_Message);
	}   //  getMeg

	/**
	 *  Get translated text message for AD_Message
	 *  @param  language Language
	 *  @param	AD_Message - Message Key
	 *  @return translated text
	 */
	public static String getMsg (Language language, String AD_Message)
	{
		return getMsg (language.getAD_Language(), AD_Message);
	}   //  getMeg

	/**
	 * Text returned by the getMsg/translator function is usually in the format message + SEPARATOR + tip.<br/>
	 * This function is used for separating these parts <br/>
	 * This function help to separate it 
	 * @param msgFull a message received from getMsg or translate function
	 * @return a (Msg,Tip) ValueNamePair
	 */
	public static ValueNamePair splitToMsgTip (String msgFull) {
		int pos = msgFull.indexOf(SEPARATOR);
		String msg = "";
		String tip = "";
		
		//  No Tip
		if (pos == -1){
			msg = msgFull;
		}else{    //  with Tip
			msg = msgFull.substring (0, pos);
			int start = pos + SEPARATOR.length();
			tip = msgFull.substring (start);
		}
		
		return new ValueNamePair(msg, tip);
	}
	/**
	 *  Get translated text message for AD_Message
	 *  @param  ad_language - Language
	 *  @param	AD_Message - Message Key
	 *  @param  getText if true only return Text, if false only return Tip
	 *  @return translated text
	 */
	public static String getMsg (String ad_language, String AD_Message, boolean getText)
	{
		String retStr = getMsg (ad_language, AD_Message);
		ValueNamePair msgTipPair = splitToMsgTip (retStr);
		if (getText)
			return msgTipPair.getValue();
		else
			return msgTipPair.getName();
	}	//	getMsg

	/**
	 *  Get translated text message for AD_Message
	 *  @param  ctx Context to retrieve language
	 *  @param	AD_Message  Message Key
	 *  @param  getText if true only return Text, if false only return Tip
	 *  @return translated text
	 */
	public static String getMsg (Properties ctx, String AD_Message, boolean getText)
	{
		return getMsg (Env.getAD_Language(ctx), AD_Message, getText);
	}   //  getMsg

	/**
	 *  Get translated text message for AD_Message
	 *  @param  language Language
	 *  @param	AD_Message  Message Key
	 *  @param  getText if true only return Text, if false only return Tip
	 *  @return translated text
	 */
	public static String getMsg (Language language, String AD_Message, boolean getText)
	{
		return getMsg (language.getAD_Language(), AD_Message, getText);
	}   //  getMsg

	/**
	 *	Get translated text for AD_Message with parameters
	 *  @param  ctx Context to retrieve language
	 *  @param AD_Message   Message key
	 *  @param args         MessageFormat arguments
	 *  @return translated text
	 *  @see java.text.MessageFormat for formatting options
	 */
	public static String getMsg(Properties ctx, String AD_Message, Object[] args)
	{
		return getMsg (Env.getAD_Language(ctx), AD_Message, args);
	}	//	getMsg

	/**
	 *	Get translated text for AD_Message with parameters
	 *  @param  language Language
	 *  @param AD_Message   Message key
	 *  @param args         MessageFormat arguments
	 *  @return translated text
	 *  @see java.text.MessageFormat for formatting options
	 */
	public static String getMsg(Language language, String AD_Message, Object[] args)
	{
		return getMsg (language.getAD_Language(), AD_Message, args);
	}	//	getMsg

	/**
	 *	Get translated text for AD_Message with parameters
	 *  @param ad_language  Language
	 *  @param AD_Message   Message key
	 *  @param args         MessageFormat arguments
	 *  @return translated text
	 *  @see java.text.MessageFormat for formatting options
	 */
	public static String getMsg (String ad_language, String AD_Message, Object[] args)
	{
		String msg = getMsg(ad_language, AD_Message);
		String retStr = msg;
		try
		{
			retStr = new MessageFormat(msg, Language.getLocale(ad_language)).format(args);	//	format string
		}
		catch (Exception e)
		{
			s_log.log(Level.SEVERE, msg, e);
		}
		return retStr;
	}	//	getMsg

	/**
	 * 	Get Amount in Words
	 * 	@param language language
	 * 	@param amount numeric amount (352.80)
	 * 	@return amount in words (three*five*two 80/100)
	 */
	public static String getAmtInWords (Language language, String amount)
	{
		if (amount == null || language == null)
			return amount;
		//	Try to find Class
		String className = "org.compiere.util.AmtInWords_";
		try
		{
			className += language.getLanguageCode().toUpperCase();
			Class<?> clazz = Class.forName(className);
			AmtInWords aiw = (AmtInWords)clazz.getDeclaredConstructor().newInstance();
			return aiw.getAmtInWords(amount);
		}
		catch (ClassNotFoundException e)
		{
			s_log.warning("Class not found: " + className);
		}
		catch (Exception e)
		{
			s_log.log(Level.SEVERE, className, e);
		}
		
		//	Fallback
		StringBuilder sb = new StringBuilder();
		int pos = amount.lastIndexOf('.');
		int pos2 = amount.lastIndexOf(',');
		if (pos2 > pos)
			pos = pos2;
		for (int i = 0; i < amount.length(); i++)
		{
			if (pos == i)	//	we are done
			{
				String cents = amount.substring(i+1);
				sb.append(' ').append(cents).append("/100");
				break;
			}
			else
			{
				char c = amount.charAt(i);
				if (c == ',' || c == '.')	//	skip thousand separator
					continue;
				if (sb.length() > 0)
					sb.append("*");
				sb.append(getMsg(language, String.valueOf(c)));
			}
		}
		return sb.toString();
	}	//	getAmtInWords
	
	/**
	 *  Get Translation for Element
	 *  @param ad_language language
	 *  @param ColumnName element column name
	 *  @param isSOTrx if false PO terminology is used (if exists)
	 *  @return Translated Element Name or "" if not found
	 */
	public static String getElement (String ad_language, String ColumnName, boolean isSOTrx)
	{
		return getElement(ad_language, ColumnName, isSOTrx, false);
	}

	/**
	 *  Get Translation for Element
	 *  @param ad_language language
	 *  @param ColumnName element column name
	 *  @param isSOTrx if false PO terminology is used (if exists)
	 *  @param isPrintName if true, return element PrintName instead of element Name
	 *  @return Translated Element Name/PrintName of the Column or "" if not found
	 */
	public static String getElement (String ad_language, String ColumnName, boolean isSOTrx, boolean isPrintName)
	{
		if (ColumnName == null || ColumnName.equals(""))
			return "";
		String AD_Language = ad_language;
		if (AD_Language == null || AD_Language.length() == 0)
			AD_Language = Language.getBaseAD_Language();
		
		Msg msg = get();
		CCache<String, String> cache = isPrintName ? msg.getElementPrintNameMap(AD_Language) : msg.getElementMap(AD_Language);
		String key = ColumnName+"|"+isSOTrx;
		String retStr = cache.get(key);
		if (retStr != null)
			return retStr;

		//	Check AD_Element
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			if (AD_Language == null || AD_Language.length() == 0 || Env.isBaseLanguage(AD_Language, "AD_Element")) {
				StringBuilder sql = new StringBuilder("SELECT")
						.append(isPrintName ? " PrintName, PO_PrintName" : " Name, PO_Name")
						.append(" FROM AD_Element WHERE UPPER(ColumnName)=?");
				pstmt = DB.prepareStatement(sql.toString(), null);
			}
			else
			{
				StringBuilder sql = new StringBuilder("SELECT")
						.append(isPrintName ? " t.PrintName, t.PO_PrintName" : " t.Name, t.PO_Name")
						.append(" FROM AD_Element_Trl t, AD_Element e")
						.append(" WHERE t.AD_Element_ID=e.AD_Element_ID AND UPPER(e.ColumnName)=?")
						.append(" AND t.AD_Language=?");
				pstmt = DB.prepareStatement(sql.toString(), null);
				pstmt.setString(2, AD_Language);
			}

			pstmt.setString(1, ColumnName.toUpperCase());
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				retStr = rs.getString(1);
				if (!isSOTrx)
				{
					String temp = rs.getString(2);
					if (temp != null && temp.length() > 0)
						retStr = temp;
				}
			}
		}
		catch (SQLException e)
		{
			s_log.log(Level.SEVERE, "getElement", e);
			return "";
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		retStr = retStr == null ? "" : retStr.trim();
		cache.put(key, retStr);
		return retStr;
		
	}   //  getElement

	/**
	 *  Get Translation for Element using Sales terminology
	 *  @param ctx context
	 *  @param ColumnName element column name
	 *  @return Translated Element Name of the Column or "" if not found
	 */
	public static String getElement (Properties ctx, String ColumnName)
	{
		return getElement (Env.getAD_Language(ctx), ColumnName, true);
	}   //  getElement

	/**
	 *  Get Translation for Element
	 *  @param ctx context
	 *  @param ColumnName element column name
	 *  @param isSOTrx sales transaction
	 *  @return Translated Element Name of the Column or "" if not found
	 */
	public static String getElement (Properties ctx, String ColumnName, boolean isSOTrx)
	{
		return getElement (Env.getAD_Language(ctx), ColumnName, isSOTrx);
	}   //  getElement

	/**
	 *	"Translate" text.
	 *  <pre>
	 *		- Check AD_Message.AD_Message 	->	MsgText
	 *		- Check AD_Element.ColumnName	->	Name
	 *  </pre>
	 *  If checking AD_Element, the SO terminology is used.
	 *  @param ad_language  Language
	 *  @param isSOTrx sales order context
	 *  @param text	Text - Message Value or Element Column Name
	 *  @return translated text or original text if not found
	 */
	public static String translate(String ad_language, boolean isSOTrx, String text)
	{
		return translate(ad_language, isSOTrx, text, false);
	}

	/**
	 *	"Translate" text.
	 *  <pre>
	 *		- Check AD_Message.AD_Message 	->	MsgText
	 *		- Check AD_Element.ColumnName	->	Name/PrintName
	 *  </pre>
	 *  If checking AD_Element, the SO terminology is used.
	 *  @param ad_language  Language
	 *  @param isSOTrx sales order context
	 *  @param text	Text - Message Value or Element Column Name
	 *  @param isPrintName if true, return element PrintName instead of element Name
	 *  @return translated text or original text if not found
	 */
	public static String translate(String ad_language, boolean isSOTrx, String text, boolean isPrintName)
	{
		if (text == null || text.equals(""))
			return "";
		String AD_Language = ad_language;
		if (AD_Language == null || AD_Language.length() == 0)
			AD_Language = Language.getBaseAD_Language();

		//	Check AD_Message
		String retStr = get().lookup (AD_Language, text);
		if (retStr != null)
			return retStr;

		//	Check AD_Element
		retStr = getElement(AD_Language, text, isSOTrx, isPrintName);
		if (!retStr.equals(""))
			return retStr.trim();

		//	Nothing found
		if (!text.startsWith("*"))
			s_log.warning("NOT found: " + text);
		return text;
	}	//	translate

	/**
	 *	"Translate" text (SO Context).
	 *  <pre>
	 *		- Check AD_Message.AD_Message 	->	MsgText
	 *		- Check AD_Element.ColumnName	->	Name
	 *  </pre>
	 *  If checking AD_Element, the SO terminology is used.
	 *  @param ad_language  Language
	 *  @param text	Text - Message Value or Element Column Name
	 *  @return translated text or original text if not found
	 */
	public static String translate(String ad_language, String text)
	{
		return translate (ad_language, true, text);
	}	//	translate

	/**
	 *	"Translate" text.
	 *  <pre>
	 *		- Check Context
	 *		- Check AD_Message.AD_Message 	->	MsgText
	 *		- Check AD_Element.ColumnName	->	Name
	 *  </pre>
	 *  @param ctx  Context
	 *  @param text	Text - Message Value or Element Column Name
	 *  @return translated text or original text if not found
	 */
	public static String translate(Properties ctx, String text)
	{
		if (text == null || text.length() == 0)
			return text;
		String s = (String)ctx.getProperty(text);
		if (s != null && s.length() > 0)
			return s;
		return translate (Env.getAD_Language(ctx), Env.isSOTrx(ctx), text);
	}   //  translate

	/**
	 *	"Translate" text.
	 *  <pre>
	 *		- Check AD_Message.AD_Message 	->	MsgText
	 *		- Check AD_Element.ColumnName	->	Name
	 *  </pre>
	 *  @param language Language
	 *  @param text Message Value or Element Column Name
	 *  @return translated text or original text if not found
	 */
	public static String translate(Language language, String text)
	{
		return translate (language.getAD_Language(), false, text);
	}   //  translate

	/**
	 *	Translate elements enclosed in "@" (at sign)
	 *  @param ctx      Context
	 *  @param text     Text
	 *  @return translated text or original text if not found
	 */
	public static String parseTranslation(Properties ctx, String text)
	{
		if (text == null || text.length() == 0)
			return text;

		String inStr = text;
		String token;
		StringBuilder outStr = new StringBuilder();

		int i = inStr.indexOf('@');
		while (i != -1)
		{
			outStr.append(inStr.substring(0, i));			// up to @
			inStr = inStr.substring(i+1, inStr.length());	// from first @

			int j = inStr.indexOf('@');						// next @
			if (j < 0)										// no second tag
			{
				inStr = "@" + inStr;
				break;
			}

			token = inStr.substring(0, j);
			outStr.append(translate(ctx, token));			// replace context

			inStr = inStr.substring(j+1, inStr.length());	// from second @
			i = inStr.indexOf('@');
		}

		outStr.append(inStr);           					//	add remainder
		return outStr.toString();
	}   //  parseTranslation

	/**
	 * Is translation exists for text 
	 * @param adLanguage
	 * @param text
	 * @return true if translation exists for text and adLanguage
	 */
	public static boolean hasTranslation(String adLanguage, String text)
	{
		if (Util.isEmpty(text, true))
			return false;
		
		String AD_Language = adLanguage;
		if (AD_Language == null || AD_Language.length() == 0)
			AD_Language = Language.getBaseAD_Language();

		//	Check AD_Message
		String retStr = get().lookup (AD_Language, text);
		if (!Util.isEmpty(retStr, true))
			return true;

		//	Check AD_Element
		retStr = getElement(AD_Language, text, false);
		if (!Util.isEmpty(retStr, true))
			return true;
		
		return false;
	}
	
	/**
	 *  Get translated text message for AD_Message, ampersand cleaned (used to indicate shortcut)
	 *  @param  ctx Context to retrieve language
	 *  @param	string AD_Message - Message Key
	 *  @return translated text
	 */
	public static String getCleanMsg(Properties ctx, String string) {
		return Util.cleanAmp(getMsg(Env.getAD_Language(ctx), string));
	}

}	//	Msg
