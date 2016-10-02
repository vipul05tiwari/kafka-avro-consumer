/*******************************************************************************************************
 * 
 * Copyright (C) TrendSutra Platform Services Private Limited,
 * All Rights Reserved Unauthorized copying of this file, 
 * via any medium is strictly prohibited Proprietary and confidential.
 * 
 *******************************************************************************************************/

package com.kac;

import org.json.JSONObject;

/**
* TODO : insert comment here
* 
* @author vipul
* @date 02-Oct-2016
*/

public interface MessageParser 
{
	public JSONObject parse(byte[] receivedMessage) throws ParseException;
}
