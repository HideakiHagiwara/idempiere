/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2012 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.idempiere.webservices.model;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Model for WS_WebServiceMethod
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="WS_WebServiceMethod")
public class X_WS_WebServiceMethod extends PO implements I_WS_WebServiceMethod, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20241222L;

    /** Standard Constructor */
    public X_WS_WebServiceMethod (Properties ctx, int WS_WebServiceMethod_ID, String trxName)
    {
      super (ctx, WS_WebServiceMethod_ID, trxName);
      /** if (WS_WebServiceMethod_ID == 0)
        {
			setName (null);
			setValue (null);
			setWS_WebServiceMethod_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_WS_WebServiceMethod (Properties ctx, int WS_WebServiceMethod_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, WS_WebServiceMethod_ID, trxName, virtualColumns);
      /** if (WS_WebServiceMethod_ID == 0)
        {
			setName (null);
			setValue (null);
			setWS_WebServiceMethod_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_WS_WebServiceMethod (Properties ctx, String WS_WebServiceMethod_UU, String trxName)
    {
      super (ctx, WS_WebServiceMethod_UU, trxName);
      /** if (WS_WebServiceMethod_UU == null)
        {
			setName (null);
			setValue (null);
			setWS_WebServiceMethod_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_WS_WebServiceMethod (Properties ctx, String WS_WebServiceMethod_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, WS_WebServiceMethod_UU, trxName, virtualColumns);
      /** if (WS_WebServiceMethod_UU == null)
        {
			setName (null);
			setValue (null);
			setWS_WebServiceMethod_ID (0);
        } */
    }

    /** Load Constructor */
    public X_WS_WebServiceMethod (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 4 - System
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuilder sb = new StringBuilder ("X_WS_WebServiceMethod[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	/** Set Description.
		@param Description Optional short description of the record
	*/
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription()
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Comment/Help.
		@param Help Comment or Hint
	*/
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp()
	{
		return (String)get_Value(COLUMNNAME_Help);
	}

	/** Set Name.
		@param Name Alphanumeric identifier of the entity
	*/
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName()
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Set Search Key.
		@param Value Search key for the record in the format required - must be unique
	*/
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue()
	{
		return (String)get_Value(COLUMNNAME_Value);
	}

	/** Set Web Service Method.
		@param WS_WebServiceMethod_ID Web Service Method
	*/
	public void setWS_WebServiceMethod_ID (int WS_WebServiceMethod_ID)
	{
		if (WS_WebServiceMethod_ID < 1)
			set_ValueNoCheck (COLUMNNAME_WS_WebServiceMethod_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_WS_WebServiceMethod_ID, Integer.valueOf(WS_WebServiceMethod_ID));
	}

	/** Get Web Service Method.
		@return Web Service Method	  */
	public int getWS_WebServiceMethod_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WS_WebServiceMethod_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set WS_WebServiceMethod_UU.
		@param WS_WebServiceMethod_UU WS_WebServiceMethod_UU
	*/
	public void setWS_WebServiceMethod_UU (String WS_WebServiceMethod_UU)
	{
		set_Value (COLUMNNAME_WS_WebServiceMethod_UU, WS_WebServiceMethod_UU);
	}

	/** Get WS_WebServiceMethod_UU.
		@return WS_WebServiceMethod_UU	  */
	public String getWS_WebServiceMethod_UU()
	{
		return (String)get_Value(COLUMNNAME_WS_WebServiceMethod_UU);
	}

	public I_WS_WebService getWS_WebService() throws RuntimeException
	{
		return (I_WS_WebService)MTable.get(getCtx(), I_WS_WebService.Table_ID)
			.getPO(getWS_WebService_ID(), get_TrxName());
	}

	/** Set Web Service.
		@param WS_WebService_ID Web Service
	*/
	public void setWS_WebService_ID (int WS_WebService_ID)
	{
		if (WS_WebService_ID < 1)
			set_ValueNoCheck (COLUMNNAME_WS_WebService_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_WS_WebService_ID, Integer.valueOf(WS_WebService_ID));
	}

	/** Get Web Service.
		@return Web Service	  */
	public int getWS_WebService_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WS_WebService_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair()
    {
        return new KeyNamePair(get_ID(), String.valueOf(getWS_WebService_ID()));
    }
}