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
import org.compiere.util.KeyNamePair;

/** Generated Model for AD_Reference
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="AD_Reference")
public class X_AD_Reference extends PO implements I_AD_Reference, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20241222L;

    /** Standard Constructor */
    public X_AD_Reference (Properties ctx, int AD_Reference_ID, String trxName)
    {
      super (ctx, AD_Reference_ID, trxName);
      /** if (AD_Reference_ID == 0)
        {
			setAD_Reference_ID (0);
			setEntityType (null);
// @SQL=SELECT CASE WHEN '@P|AdempiereSys:N@'='Y' THEN 'D' ELSE get_sysconfig('DEFAULT_ENTITYTYPE','U',0,0) END FROM Dual
			setName (null);
			setShowInactive (null);
// N
			setValidationType (null);
        } */
    }

    /** Standard Constructor */
    public X_AD_Reference (Properties ctx, int AD_Reference_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, AD_Reference_ID, trxName, virtualColumns);
      /** if (AD_Reference_ID == 0)
        {
			setAD_Reference_ID (0);
			setEntityType (null);
// @SQL=SELECT CASE WHEN '@P|AdempiereSys:N@'='Y' THEN 'D' ELSE get_sysconfig('DEFAULT_ENTITYTYPE','U',0,0) END FROM Dual
			setName (null);
			setShowInactive (null);
// N
			setValidationType (null);
        } */
    }

    /** Standard Constructor */
    public X_AD_Reference (Properties ctx, String AD_Reference_UU, String trxName)
    {
      super (ctx, AD_Reference_UU, trxName);
      /** if (AD_Reference_UU == null)
        {
			setAD_Reference_ID (0);
			setEntityType (null);
// @SQL=SELECT CASE WHEN '@P|AdempiereSys:N@'='Y' THEN 'D' ELSE get_sysconfig('DEFAULT_ENTITYTYPE','U',0,0) END FROM Dual
			setName (null);
			setShowInactive (null);
// N
			setValidationType (null);
        } */
    }

    /** Standard Constructor */
    public X_AD_Reference (Properties ctx, String AD_Reference_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, AD_Reference_UU, trxName, virtualColumns);
      /** if (AD_Reference_UU == null)
        {
			setAD_Reference_ID (0);
			setEntityType (null);
// @SQL=SELECT CASE WHEN '@P|AdempiereSys:N@'='Y' THEN 'D' ELSE get_sysconfig('DEFAULT_ENTITYTYPE','U',0,0) END FROM Dual
			setName (null);
			setShowInactive (null);
// N
			setValidationType (null);
        } */
    }

    /** Load Constructor */
    public X_AD_Reference (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_AD_Reference[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Element getAD_Element() throws RuntimeException
	{
		return (org.compiere.model.I_AD_Element)MTable.get(getCtx(), org.compiere.model.I_AD_Element.Table_ID)
			.getPO(getAD_Element_ID(), get_TrxName());
	}

	/** Set System Element.
		@param AD_Element_ID System Element enables the central maintenance of column description and help.
	*/
	public void setAD_Element_ID (int AD_Element_ID)
	{
		if (AD_Element_ID < 1)
			set_Value (COLUMNNAME_AD_Element_ID, null);
		else
			set_Value (COLUMNNAME_AD_Element_ID, Integer.valueOf(AD_Element_ID));
	}

	/** Get System Element.
		@return System Element enables the central maintenance of column description and help.
	  */
	public int getAD_Element_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Element_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Reference.
		@param AD_Reference_ID System Reference and Validation
	*/
	public void setAD_Reference_ID (int AD_Reference_ID)
	{
		if (AD_Reference_ID < 1)
			set_ValueNoCheck (COLUMNNAME_AD_Reference_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_AD_Reference_ID, Integer.valueOf(AD_Reference_ID));
	}

	/** Get Reference.
		@return System Reference and Validation
	  */
	public int getAD_Reference_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Reference_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set AD_Reference_UU.
		@param AD_Reference_UU AD_Reference_UU
	*/
	public void setAD_Reference_UU (String AD_Reference_UU)
	{
		set_Value (COLUMNNAME_AD_Reference_UU, AD_Reference_UU);
	}

	/** Get AD_Reference_UU.
		@return AD_Reference_UU	  */
	public String getAD_Reference_UU()
	{
		return (String)get_Value(COLUMNNAME_AD_Reference_UU);
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

	/** EntityType AD_Reference_ID=389 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	/** Set Entity Type.
		@param EntityType Dictionary Entity Type; Determines ownership and synchronization
	*/
	public void setEntityType (String EntityType)
	{

		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	/** Get Entity Type.
		@return Dictionary Entity Type; Determines ownership and synchronization
	  */
	public String getEntityType()
	{
		return (String)get_Value(COLUMNNAME_EntityType);
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

	/** Set Order By Value.
		@param IsOrderByValue Order list using the value column instead of the name column
	*/
	public void setIsOrderByValue (boolean IsOrderByValue)
	{
		set_Value (COLUMNNAME_IsOrderByValue, Boolean.valueOf(IsOrderByValue));
	}

	/** Get Order By Value.
		@return Order list using the value column instead of the name column
	  */
	public boolean isOrderByValue()
	{
		Object oo = get_Value(COLUMNNAME_IsOrderByValue);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair()
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** ShowInactive AD_Reference_ID=200230 */
	public static final int SHOWINACTIVE_AD_Reference_ID=200230;
	/** No = N */
	public static final String SHOWINACTIVE_No = "N";
	/** Yes = Y */
	public static final String SHOWINACTIVE_Yes = "Y";
	/** Set Show Inactive.
		@param ShowInactive Show Inactive Records
	*/
	public void setShowInactive (String ShowInactive)
	{

		set_Value (COLUMNNAME_ShowInactive, ShowInactive);
	}

	/** Get Show Inactive.
		@return Show Inactive Records
	  */
	public String getShowInactive()
	{
		return (String)get_Value(COLUMNNAME_ShowInactive);
	}

	/** Set Value Format.
		@param VFormat Format of the value; Can contain fixed format elements, Variables: &quot;_lLoOaAcCa09&quot;, or ~regex
	*/
	public void setVFormat (String VFormat)
	{
		set_Value (COLUMNNAME_VFormat, VFormat);
	}

	/** Get Value Format.
		@return Format of the value; Can contain fixed format elements, Variables: &quot;_lLoOaAcCa09&quot;, or ~regex
	  */
	public String getVFormat()
	{
		return (String)get_Value(COLUMNNAME_VFormat);
	}

	/** ValidationType AD_Reference_ID=2 */
	public static final int VALIDATIONTYPE_AD_Reference_ID=2;
	/** DataType = D */
	public static final String VALIDATIONTYPE_DataType = "D";
	/** List Validation = L */
	public static final String VALIDATIONTYPE_ListValidation = "L";
	/** Table Validation = T */
	public static final String VALIDATIONTYPE_TableValidation = "T";
	/** Set Validation type.
		@param ValidationType Different method of validating data
	*/
	public void setValidationType (String ValidationType)
	{

		set_Value (COLUMNNAME_ValidationType, ValidationType);
	}

	/** Get Validation type.
		@return Different method of validating data
	  */
	public String getValidationType()
	{
		return (String)get_Value(COLUMNNAME_ValidationType);
	}
}