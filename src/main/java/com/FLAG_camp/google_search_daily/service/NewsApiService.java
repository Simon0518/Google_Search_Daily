package com.FLAG_camp.google_search_daily.service;

import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;
import javax.net.ssl.HttpsURLConnection;

import org.assertj.core.util.VisibleForTesting;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.FLAG_camp.google_search_daily.model.News;

import static com.FLAG_camp.google_search_daily.GoogleSearchDailyApplication.logger;


@Service("newsApiService")
public class NewsApiService {

//	private final String SUBSCRIPTION_KEY = "08578fb106fd45299541397fd22e25aa";
//	private final String SUBSCRIPTION_KEY = "6108c845cd8d4ab68a1fb83bb3b8ef3c";
	private final String SUBSCRIPTION_KEY = "2ff3c655aeb4416fb0785c02b8427304";
	private final String HOST = "https://api.cognitive.microsoft.com";
	private final String PATH = "/bing/v7.0/news";
	private final String SUB_PATH = "";
	private final String DEFAULT_KEYWORD = "";
	
	/*
	@VisibleForTesting
	public List<News> getGeneralNews() throws Exception {
		// create connection
		String subPath = SUB_PATH;
		String queryKeyword = "sailing+dinghies";
		HttpsURLConnection connection = createConnection(subPath, 0L, queryKeyword);
		connection.connect();
		
		int responseCode = connection.getResponseCode();
	    if (responseCode != 200) {
	    	return null;
	    }

	    JSONArray news = parseResponseToJsonArray(connection);
	    return convertJsonArrayToNewsList(news);
	}
	*/
	
	@VisibleForTesting
	public List<News> getTodayTopNews(Long offset) throws Exception {
		String subPath = "/search";
		String queryKeyword = "";
		HttpsURLConnection connection = createConnection(subPath, offset, queryKeyword);
		connection.connect();
		
		int responseCode = connection.getResponseCode();
	    if (responseCode != 200) {
	    	return null;
	    }
	    
	    JSONArray news = parseResponseToJsonArray(connection);
	    return convertJsonArrayToNewsList(news);
	}
	
	@VisibleForTesting
	public List<News> getQueryNews(String queryKeyword, Long offset) throws Exception {
		String subPath = "/search";
		
		// add "+" when seeing spaces - pengzhao
		String[] parts = queryKeyword.split("\\s+");
		queryKeyword = "";
		for (int i = 0; i < parts.length - 1; i++) {
			queryKeyword += parts[i];
			queryKeyword += "+";
		}
		queryKeyword += parts[parts.length - 1];
		logger.info("queryKeyword becomes {}", queryKeyword);
		
		HttpsURLConnection connection = createConnection(subPath, offset, queryKeyword);
		connection.connect();
		
		int responseCode = connection.getResponseCode();
		System.out.println(responseCode);
	    if (responseCode != 200) {
	    	return null;
	    }
	    JSONArray news = parseResponseToJsonArray(connection);
		return convertJsonArrayToNewsList(news);
	}
	
	@VisibleForTesting
	public List<News> getQueryNewsByGeoLocation(String queryKeyword, Long offset, double lat, double lon, int radius) throws Exception {
		String subPath = "/search";
		
		// add "+" when seeing spaces - pengzhao
		String[] parts = queryKeyword.split("\\s+");
		queryKeyword = "";
		for (int i = 0; i < parts.length - 1; i++) {
			queryKeyword += parts[i];
			queryKeyword += "+";
		}
		queryKeyword += parts[parts.length - 1];
		logger.info("queryKeyword becomes {}", queryKeyword);
		
		HttpsURLConnection connection = createGeoBasedConnection(subPath, offset, queryKeyword, lat, lon, radius);
		connection.connect();
		
		int responseCode = connection.getResponseCode();
	    if (responseCode != 200) {
	    	return null;
	    }
	    JSONArray news = parseResponseToJsonArray(connection);
		return convertJsonArrayToNewsList(news);
	}
	
	@VisibleForTesting
	public List<News> getCategoryNews(String category, Long offset) throws Exception {
		String subPath = SUB_PATH;
		HttpsURLConnection connection = createCategoryQueryConnection(subPath, offset, category);
		connection.connect();
		
		int responseCode = connection.getResponseCode();
	    if (responseCode != 200) {
	    	return null;
	    }
	    JSONArray news = parseResponseToJsonArray(connection);
		return convertJsonArrayToNewsList(news);
	}
	
	private HttpsURLConnection createConnection(String subPath, Long offset, String queryKeyword) throws Exception {
		if (offset == null) {
			offset = 0L;
		}
		URL url = new URL(HOST + PATH + subPath + "?" + "offset=" + offset + "&count=12&" + "q=" +  URLEncoder.encode(queryKeyword, "UTF-8"));
		logger.info("pengzha1 url = {}", url);
		//url = new URL(HOST + PATH + subPath + "?q=" +  URLEncoder.encode(queryKeyword, "UTF-8"));
		
		HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
	    connection.setRequestProperty("Ocp-Apim-Subscription-Key", SUBSCRIPTION_KEY);
	    connection.setRequestProperty("X-Search-ClientIP", "999.999.999.999");
	    connection.setRequestMethod("GET");
	    logger.info("connection url = {}", connection);
	    return connection;
	}
	
	private HttpsURLConnection createGeoBasedConnection(String subPath, Long offset, String queryKeyword, double lat, double lon, int radius) throws Exception {
		if (offset == null) {
			offset = 0L;
		}
		URL url = new URL(HOST + PATH + subPath + "?" + "offset=" + offset + "&count=12&" + "q=" +  URLEncoder.encode(queryKeyword, "UTF-8"));
		logger.info("pengzhao url = {}", url);
		//URL url = new URL(HOST + PATH + subPath + "?q=" +  URLEncoder.encode(queryKeyword, "UTF-8"));
		
		String LAT = "lat:" + lat + ";";
		String LONG = "long:" + lon + ";";
		String RAD = "re:" + radius;
		String GEO_LOCATION = LAT + LONG + RAD;
		System.out.println(GEO_LOCATION);
		HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
	    connection.setRequestProperty("Ocp-Apim-Subscription-Key", SUBSCRIPTION_KEY);
	    connection.setRequestProperty("X-Search-Location", GEO_LOCATION);
	    connection.setRequestProperty("X-Search-ClientIP", "999.999.999.999");
	    connection.setRequestMethod("GET");
	    return connection;
	}
	
	private HttpsURLConnection createCategoryQueryConnection(String subPath, Long offset, String queryKeyword) throws Exception {
		//URL url = new URL(HOST + PATH + SUB_PATH + "?category=" +  URLEncoder.encode(queryKeyword, "UTF-8"));
		URL url = new URL(HOST + PATH + SUB_PATH + "?" + "offset=" + offset + "&count=12&" + "category=" +  URLEncoder.encode(queryKeyword, "UTF-8"));
		logger.info("pengzhao category news url is {}", url);
		HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
	    connection.setRequestProperty("Ocp-Apim-Subscription-Key", SUBSCRIPTION_KEY);
	    connection.setRequestProperty("X-Search-ClientIP", "999.999.999.999");
	    connection.setRequestMethod("GET");
	    return connection;
	}
	
	private JSONArray parseResponseToJsonArray(HttpsURLConnection connection) throws Exception {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		StringBuilder response = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			response.append(line);
		}
		reader.close();
		
		JSONObject obj = new JSONObject(response.toString());
		if (!obj.isNull("value")) {
			return obj.getJSONArray("value");
		}	
		return new JSONArray();
	}
	
	private List<News> convertJsonArrayToNewsList(JSONArray allNews) throws JSONException {	
		if (allNews == null) {
			return null;
		}
		List<News> newsList = new ArrayList<>();
		for (int i = 0; i < allNews.length(); ++i) {
			JSONObject newsObj = allNews.getJSONObject(i);
			News newNews = new News();
			if (!newsObj.isNull("name")) {
				newNews.setTitle(newsObj.getString("name"));
			}
			if (!newsObj.isNull("url")) {
				newNews.setNewsUrl(newsObj.getString("url"));
			}
			if (!newsObj.isNull("image")) {
				JSONObject imgObj = newsObj.getJSONObject("image");
				if (!imgObj.isNull("thumbnail")) {
					JSONObject imgThumbnailObj = imgObj.getJSONObject("thumbnail");
					if (!imgThumbnailObj.isNull("contentUrl")) {
						newNews.setImgUrl(imgThumbnailObj.getString("contentUrl"));
					}
					if (!imgThumbnailObj.isNull("width")) {
						newNews.setImgWidth(imgThumbnailObj.getInt("width"));
					}
					if (!imgThumbnailObj.isNull("height")) {
						newNews.setImgHeight(imgThumbnailObj.getInt("height"));
					}
				}
			}
			if (!newsObj.isNull("description")) {
				newNews.setContent(newsObj.getString("description"));
			}
			if (!newsObj.isNull("provider")) {
				JSONArray providerArray = newsObj.getJSONArray("provider");
				JSONObject providerObj = providerArray.getJSONObject(0);
				if (providerObj!= null && providerObj.length()!= 0) {
					if (!providerObj.isNull("name")) {
						newNews.setNewsProviderName(providerObj.getString("name"));
					} 
				} 
			}
			if (!newsObj.isNull("datePublished")) {
				newNews.setDatePublished(dateTimeFormatter(newsObj.getString("datePublished")));
			}
			if (!newsObj.isNull("category")) {
				newNews.setCategory(newsObj.getString("category"));
			}
			
			newsList.add(newNews);
		}
		return newsList;
	}
	
	private LocalDateTime dateTimeFormatter(String dateTimeStr) {
		String input = dateTimeStr.replace ( "T" , " " );
		String inputModified = input.replace ( "Z" , "" );
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSS");
		LocalDateTime dateTime = LocalDateTime.parse(inputModified, formatter);
		return dateTime;
	}
    
}

