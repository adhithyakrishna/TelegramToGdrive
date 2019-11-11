package com.TelegramToGdrive;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import com.TelegramToGdrive.Builder.ConfigObj;
import com.TelegramToGdrive.HttpConnector.MakeRequestUtil;

public class IntelliBot extends TelegramLongPollingBot {

	ConfigObj configObj;

	IntelliBot(ConfigObj configObj) {
		this.configObj = configObj;
	}

	public void onUpdateReceived(Update update) {
		List<PhotoSize> imageInfo = update.getMessage().getPhoto();
		List<String> listIds = new ArrayList<String>();

		for (PhotoSize photoInfo : imageInfo) {
			listIds.add(photoInfo.getFileId());
		}

		MakeRequestUtil fileUrl = new MakeRequestUtil();

		Map<String, String> UrlList = fileUrl.constructURl(listIds, this.configObj.getBotToken());

		String folderName = generateUniqueFolder();
		new File(folderName).mkdirs();
		for (String listidentifiers : listIds) {

			String fileRequestURl = UrlList.get(listidentifiers);
			try {
				URL url = new URL(fileRequestURl);

				try {
					String fileName = fileRequestURl.substring(fileRequestURl.lastIndexOf('/')+1, fileRequestURl.length());
					String extension = fileName.split("\\.")[1];
					File outputfile = new File(folderName+fileName);
					Image image = ImageIO.read(url);
					BufferedImage bufferedImage = (BufferedImage) image;
					ImageIO.write(bufferedImage, extension, outputfile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		
		UploadToDrive uploadToDrive = new UploadToDrive();
		boolean isUploadingDone = uploadToDrive.uploadFolderToDrive(folderName);
		
	}

	public String generateUniqueFolder() {
		return "uploadImages/";
		
	}
	public String getBotUsername() {
		return this.configObj.getBotName();
	}

	@Override
	public String getBotToken() {
		return this.configObj.getBotToken();
	}

}
