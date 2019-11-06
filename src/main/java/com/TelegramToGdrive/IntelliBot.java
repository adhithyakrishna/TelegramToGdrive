package com.TelegramToGdrive;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import com.TelegramToGdrive.Builder.ConfigObj;

public class IntelliBot extends TelegramLongPollingBot {

	ConfigObj configObj;

	IntelliBot(ConfigObj configObj) {
		this.configObj = configObj;
	}

	public void onUpdateReceived(Update update) {
		List<PhotoSize> imageInfo = update.getMessage().getPhoto();
		List<String> listIds = new ArrayList<String>();
		for(PhotoSize photoInfo : imageInfo)
		{
			listIds.add(photoInfo.getFileId());
		}
		getFilesFromFileIds(listIds);
	}

	private void getFilesFromFileIds(List<String> listIds) {
		
		 
		
	}

	public String getBotUsername() {
		return this.configObj.getBotName();
	}

	@Override
	public String getBotToken() {
		return this.configObj.getBotToken();
	}

}
