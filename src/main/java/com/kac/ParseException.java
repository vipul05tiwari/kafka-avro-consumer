/*******************************************************************************************************
 * 
 * Copyright (C) Vipul Tiwari,
 * All Rights Reserved Unauthorized copying of this file, 
 * via any medium is strictly prohibited Proprietary and confidential.
 * 
 *******************************************************************************************************/

package com.kac;

import java.io.IOException;

/**
* 
* @author vipul
* @date 02-Oct-2016
*/

public class ParseException extends Exception 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParseException(IOException e) 
	{
		super(e);
	}
}
