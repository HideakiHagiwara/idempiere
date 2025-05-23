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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for C_PaySelection
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="C_PaySelection")
public class X_C_PaySelection extends PO implements I_C_PaySelection, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20241222L;

    /** Standard Constructor */
    public X_C_PaySelection (Properties ctx, int C_PaySelection_ID, String trxName)
    {
      super (ctx, C_PaySelection_ID, trxName);
      /** if (C_PaySelection_ID == 0)
        {
			setC_BankAccount_ID (0);
			setC_PaySelection_ID (0);
			setIsApproved (false);
			setName (null);
// @#Date@
			setPayDate (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setProcessed (false);
			setProcessing (false);
			setTotalAmt (Env.ZERO);
        } */
    }

    /** Standard Constructor */
    public X_C_PaySelection (Properties ctx, int C_PaySelection_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, C_PaySelection_ID, trxName, virtualColumns);
      /** if (C_PaySelection_ID == 0)
        {
			setC_BankAccount_ID (0);
			setC_PaySelection_ID (0);
			setIsApproved (false);
			setName (null);
// @#Date@
			setPayDate (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setProcessed (false);
			setProcessing (false);
			setTotalAmt (Env.ZERO);
        } */
    }

    /** Standard Constructor */
    public X_C_PaySelection (Properties ctx, String C_PaySelection_UU, String trxName)
    {
      super (ctx, C_PaySelection_UU, trxName);
      /** if (C_PaySelection_UU == null)
        {
			setC_BankAccount_ID (0);
			setC_PaySelection_ID (0);
			setIsApproved (false);
			setName (null);
// @#Date@
			setPayDate (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setProcessed (false);
			setProcessing (false);
			setTotalAmt (Env.ZERO);
        } */
    }

    /** Standard Constructor */
    public X_C_PaySelection (Properties ctx, String C_PaySelection_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, C_PaySelection_UU, trxName, virtualColumns);
      /** if (C_PaySelection_UU == null)
        {
			setC_BankAccount_ID (0);
			setC_PaySelection_ID (0);
			setIsApproved (false);
			setName (null);
// @#Date@
			setPayDate (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setProcessed (false);
			setProcessing (false);
			setTotalAmt (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_C_PaySelection (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 1 - Org
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
      StringBuilder sb = new StringBuilder ("X_C_PaySelection[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_BankAccount getC_BankAccount() throws RuntimeException
	{
		return (org.compiere.model.I_C_BankAccount)MTable.get(getCtx(), org.compiere.model.I_C_BankAccount.Table_ID)
			.getPO(getC_BankAccount_ID(), get_TrxName());
	}

	/** Set Bank Account.
		@param C_BankAccount_ID Account at the Bank
	*/
	public void setC_BankAccount_ID (int C_BankAccount_ID)
	{
		if (C_BankAccount_ID < 1)
			set_Value (COLUMNNAME_C_BankAccount_ID, null);
		else
			set_Value (COLUMNNAME_C_BankAccount_ID, Integer.valueOf(C_BankAccount_ID));
	}

	/** Get Bank Account.
		@return Account at the Bank
	  */
	public int getC_BankAccount_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankAccount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Payment Selection.
		@param C_PaySelection_ID Payment Selection
	*/
	public void setC_PaySelection_ID (int C_PaySelection_ID)
	{
		if (C_PaySelection_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_PaySelection_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_PaySelection_ID, Integer.valueOf(C_PaySelection_ID));
	}

	/** Get Payment Selection.
		@return Payment Selection
	  */
	public int getC_PaySelection_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaySelection_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_PaySelection_UU.
		@param C_PaySelection_UU C_PaySelection_UU
	*/
	public void setC_PaySelection_UU (String C_PaySelection_UU)
	{
		set_Value (COLUMNNAME_C_PaySelection_UU, C_PaySelection_UU);
	}

	/** Get C_PaySelection_UU.
		@return C_PaySelection_UU	  */
	public String getC_PaySelection_UU()
	{
		return (String)get_Value(COLUMNNAME_C_PaySelection_UU);
	}

	/** Set Create lines from.
		@param CreateFrom Process which will generate a new document lines based on an existing document
	*/
	public void setCreateFrom (String CreateFrom)
	{
		set_Value (COLUMNNAME_CreateFrom, CreateFrom);
	}

	/** Get Create lines from.
		@return Process which will generate a new document lines based on an existing document
	  */
	public String getCreateFrom()
	{
		return (String)get_Value(COLUMNNAME_CreateFrom);
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

	/** Set Approved.
		@param IsApproved Indicates if this document requires approval
	*/
	public void setIsApproved (boolean IsApproved)
	{
		set_Value (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
	}

	/** Get Approved.
		@return Indicates if this document requires approval
	  */
	public boolean isApproved()
	{
		Object oo = get_Value(COLUMNNAME_IsApproved);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set One Payment Per Invoice.
		@param IsOnePaymentPerInvoice One Payment Per Invoice
	*/
	public void setIsOnePaymentPerInvoice (boolean IsOnePaymentPerInvoice)
	{
		set_Value (COLUMNNAME_IsOnePaymentPerInvoice, Boolean.valueOf(IsOnePaymentPerInvoice));
	}

	/** Get One Payment Per Invoice.
		@return One Payment Per Invoice	  */
	public boolean isOnePaymentPerInvoice()
	{
		Object oo = get_Value(COLUMNNAME_IsOnePaymentPerInvoice);
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

	/** Set Payment date.
		@param PayDate Date Payment made
	*/
	public void setPayDate (Timestamp PayDate)
	{
		set_Value (COLUMNNAME_PayDate, PayDate);
	}

	/** Get Payment date.
		@return Date Payment made
	  */
	public Timestamp getPayDate()
	{
		return (Timestamp)get_Value(COLUMNNAME_PayDate);
	}

	/** Set Processed.
		@param Processed The document has been processed
	*/
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed()
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Process Now.
		@param Processing Process Now
	*/
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing()
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Reverse Prepare Payment.
		@param ReversePreparePayment Reverse Prepare Payment
	*/
	public void setReversePreparePayment (String ReversePreparePayment)
	{
		set_Value (COLUMNNAME_ReversePreparePayment, ReversePreparePayment);
	}

	/** Get Reverse Prepare Payment.
		@return Reverse Prepare Payment	  */
	public String getReversePreparePayment()
	{
		return (String)get_Value(COLUMNNAME_ReversePreparePayment);
	}

	/** Set Total Amount.
		@param TotalAmt Total Amount
	*/
	public void setTotalAmt (BigDecimal TotalAmt)
	{
		set_Value (COLUMNNAME_TotalAmt, TotalAmt);
	}

	/** Get Total Amount.
		@return Total Amount
	  */
	public BigDecimal getTotalAmt()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}