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
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.ValueNamePair;

/** Generated Model for A_RegistrationValue
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="A_RegistrationValue")
public class X_A_RegistrationValue extends PO implements I_A_RegistrationValue, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20241222L;

    /** Standard Constructor */
    public X_A_RegistrationValue (Properties ctx, int A_RegistrationValue_ID, String trxName)
    {
      super (ctx, A_RegistrationValue_ID, trxName);
      /** if (A_RegistrationValue_ID == 0)
        {
			setA_RegistrationAttribute_ID (0);
			setA_Registration_ID (0);
			setName (null);
        } */
    }

    /** Standard Constructor */
    public X_A_RegistrationValue (Properties ctx, int A_RegistrationValue_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, A_RegistrationValue_ID, trxName, virtualColumns);
      /** if (A_RegistrationValue_ID == 0)
        {
			setA_RegistrationAttribute_ID (0);
			setA_Registration_ID (0);
			setName (null);
        } */
    }

    /** Standard Constructor */
    public X_A_RegistrationValue (Properties ctx, String A_RegistrationValue_UU, String trxName)
    {
      super (ctx, A_RegistrationValue_UU, trxName);
      /** if (A_RegistrationValue_UU == null)
        {
			setA_RegistrationAttribute_ID (0);
			setA_Registration_ID (0);
			setName (null);
        } */
    }

    /** Standard Constructor */
    public X_A_RegistrationValue (Properties ctx, String A_RegistrationValue_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, A_RegistrationValue_UU, trxName, virtualColumns);
      /** if (A_RegistrationValue_UU == null)
        {
			setA_RegistrationAttribute_ID (0);
			setA_Registration_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_A_RegistrationValue (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org
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
      StringBuilder sb = new StringBuilder ("X_A_RegistrationValue[")
        .append(get_UUID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_A_RegistrationAttribute getA_RegistrationAttribute() throws RuntimeException
	{
		return (org.compiere.model.I_A_RegistrationAttribute)MTable.get(getCtx(), org.compiere.model.I_A_RegistrationAttribute.Table_ID)
			.getPO(getA_RegistrationAttribute_ID(), get_TrxName());
	}

	/** Set Registration Attribute.
		@param A_RegistrationAttribute_ID Asset Registration Attribute
	*/
	public void setA_RegistrationAttribute_ID (int A_RegistrationAttribute_ID)
	{
		if (A_RegistrationAttribute_ID < 1)
			set_ValueNoCheck (COLUMNNAME_A_RegistrationAttribute_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_A_RegistrationAttribute_ID, Integer.valueOf(A_RegistrationAttribute_ID));
	}

	/** Get Registration Attribute.
		@return Asset Registration Attribute
	  */
	public int getA_RegistrationAttribute_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_RegistrationAttribute_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record UU/ColumnName
        @return UU/ColumnName pair
      */
    public ValueNamePair getValueNamePair()
    {
        return new ValueNamePair(get_UUID(), String.valueOf(getA_RegistrationAttribute_ID()));
    }

	/** Set A_RegistrationValue_UU.
		@param A_RegistrationValue_UU A_RegistrationValue_UU
	*/
	public void setA_RegistrationValue_UU (String A_RegistrationValue_UU)
	{
		set_Value (COLUMNNAME_A_RegistrationValue_UU, A_RegistrationValue_UU);
	}

	/** Get A_RegistrationValue_UU.
		@return A_RegistrationValue_UU	  */
	public String getA_RegistrationValue_UU()
	{
		return (String)get_Value(COLUMNNAME_A_RegistrationValue_UU);
	}

	public org.compiere.model.I_A_Registration getA_Registration() throws RuntimeException
	{
		return (org.compiere.model.I_A_Registration)MTable.get(getCtx(), org.compiere.model.I_A_Registration.Table_ID)
			.getPO(getA_Registration_ID(), get_TrxName());
	}

	/** Set Registration.
		@param A_Registration_ID User Asset Registration
	*/
	public void setA_Registration_ID (int A_Registration_ID)
	{
		if (A_Registration_ID < 1)
			set_ValueNoCheck (COLUMNNAME_A_Registration_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_A_Registration_ID, Integer.valueOf(A_Registration_ID));
	}

	/** Get Registration.
		@return User Asset Registration
	  */
	public int getA_Registration_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Registration_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
}