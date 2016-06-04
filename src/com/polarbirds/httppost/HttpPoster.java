package com.polarbirds.httppost;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by Harald on 03.06.2016.
 */
public class HttpPoster {

  private HttpPoster() {
  }

  /**
   * Parses the given map to a String of the format key=value&key=value...
   *
   * @param values Map with the values
   * @return The Map as a string of the format listed above.
   */
  public static String parseMap(Map<?, ?> values) {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry entry : values.entrySet()) {
      sb.append(entry.getKey().toString());
      sb.append("=");
      sb.append(entry.getValue().toString());
      sb.append("&");
    }
    sb.deleteCharAt(sb.length() - 1);
    return sb.toString();
  }

  /**
   * Parses the given Map to a String and uses sendData(url, valueString) with the parsed data.
   *
   * @param url    URL to send data to
   * @param values Map with values to send
   */
  public static String sendData(String url, Map values) {
    return sendData(url, parseMap(values));
  }

  /**
   * Sends the given value-string to the URL as an HTTP POST-request. Use sendData(String, Map) if
   * the data you have is in a map.
   *
   * @param urlString   The URL the POST-request will be sent to.
   * @param valueString String sent to the URL. Format: key=value&key=value...
   * @return The response-codes from the request
   */
  public static String sendData(String urlString, String valueString) {
    try {
      URL url = new URL(urlString);
      byte[] data = valueString.getBytes();
      HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
      httpCon.setDoOutput(true);
      httpCon.setRequestMethod("POST");
      DataOutputStream out = new DataOutputStream(httpCon.getOutputStream());
      out.write(data);
      String returnValues = String.valueOf(httpCon.getResponseCode()) + ": "
          + httpCon.getResponseMessage();
      out.close();
      return returnValues;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "failed";
  }
}
