/*******************************************************************************************************
 * 
 * Copyright (C) Vipul Tiwari,
 * All Rights Reserved Unauthorized copying of this file, 
 * via any medium is strictly prohibited Proprietary and confidential.
 * 
 *******************************************************************************************************/

package com.kac;

import org.json.JSONObject;

/**
* {@link MessageParser} interface implemented by different type of kafka message parser.
* 
* @author vipul
* @date 02-Oct-2016
*/

public interface MessageParser 
{
	/**
	 * This method will take byte[] as input parse them and return {@link JSONObject}
	 * @param receivedMessage
	 * @return
	 * @throws ParseException
	 */
	public JSONObject parse(byte[] receivedMessage) throws ParseException;
}
