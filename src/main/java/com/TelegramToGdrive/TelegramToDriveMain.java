package com.TelegramToGdrive;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.TelegramConfigReader.ConfigReader;
import com.TelegramToGdrive.Builder.ConfigObj;

public class TelegramToDriveMain {

	public static void main(String args[]) {
		ConfigReader read = new ConfigReader();
		ConfigObj configObj = read.readConfig();

		// Initializing the bot
		ApiContextInitializer.init();
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		try {

			telegramBotsApi.registerBot(new IntelliBot(configObj));

		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}
