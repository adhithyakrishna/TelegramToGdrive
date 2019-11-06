package com.TelegramToGdrive.HttpConnector;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.TelegramToGdrive.Builder.ConfigObj;
import com.TelegramToGdrive.Builder.FileNameResponseObj;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MakeRequestUtil {

	public MakeRequestUtil() {

		System.out.println("Initialise and waiting to make a request");
	}

	public Map<String, String> constructURl(List<String> listIds, String botToken) 
	{
		String fileRequesturl = "https://api.telegram.org";
		Map<String, String> fileIDsAndName = new HashMap<String, String>();
		CloseableHttpClient httpclient = HttpClients.createDefault();

		for (String fileIds : listIds) {
			String requestUrl = fileRequesturl + "/bot" + botToken; 
			String fileNameRequestUrl = requestUrl + "/getFile?file_id=" + fileIds;

			System.out.println(requestUrl);

			HttpGet httpget = new HttpGet(fileNameRequestUrl);
			try {
				HttpResponse httpresponse = httpclient.execute(httpget);
				if (httpresponse.getStatusLine().getStatusCode() == 200) {
					HttpEntity httpEntity = httpresponse.getEntity();
					String apiOutput = EntityUtils.toString(httpEntity);

					ObjectMapper objectMapper = new ObjectMapper()
							.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
					objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

					FileNameResponseObj fileNameResponseObj = objectMapper.readValue(apiOutput,
							FileNameResponseObj.class);
					String filePath = fileNameResponseObj.getResult().getFile_path();
					fileIDsAndName.put(fileIds, fileRequesturl + "/file/bot" + botToken +"/" +filePath);
				}

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return fileIDsAndName;
	}

}
