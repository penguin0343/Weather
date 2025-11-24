/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db_connect;

import com.google.gson.Gson;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
class Endpoint {

    public String id;
    public String uri;
}

class TunnelBase {

    public String id;
    public String public_url;
    public String proto;
    public String region;
    public Endpoint endpoint;
    public String forwards_to;
}

class TunnelResponseJson {

    public List<TunnelBase> tunnels;
    public String uri;
}

public class NgrokConnector {

    private String api_key = "";
    private String apiId = "ak_35u5M7hsOytH14ucnqBrZ3xX8uk";
    private String tunnelPublicUrl = null;

    public String getTunnelPublicUrl() {
        return tunnelPublicUrl;
    }

    public void setTunnelPublicUrl(String tunnelPublicUrl) {
        this.tunnelPublicUrl = tunnelPublicUrl;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getApiId() {
        return apiId;
    }

    public NgrokConnector() {
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("API_AUTH_KEY");
        setApi_key(apiKey);

//        Try to connect to ngrok through api key, make sure apikey is existed
        try {
            String urlStr = "https://api.ngrok.com/api_keys/" + getApiId();
            URL apiIdUrl = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) apiIdUrl.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + getApi_key());
            conn.setRequestProperty("ngrok-version", "2");

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                System.out.println("Kiem tra ket noi toi ngrok..." + " Code: " + responseCode);

//                Connect to tunnel
                String tunnelUrlStr = " https://api.ngrok.com/tunnels";
                URL tunnelUrl = new URL(tunnelUrlStr);
                HttpURLConnection tunnelConn = (HttpURLConnection) tunnelUrl.openConnection();

                tunnelConn.setRequestMethod("GET");
                tunnelConn.setRequestProperty("Authorization", "Bearer " + getApi_key());
                tunnelConn.setRequestProperty("ngrok-version", "2");

                int tunnelResponseCode = tunnelConn.getResponseCode();
                if (tunnelResponseCode == 200) {
                    
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(tunnelConn.getInputStream())
                    );
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    
                    
                    Gson gson = new Gson();
                    TunnelResponseJson tunnelRes = gson.fromJson(response.toString(), TunnelResponseJson.class);
                    if (tunnelRes.tunnels.size() > 0) {
                        System.out.println("Ket noi thanh cong toi tunnel: " + tunnelRes.tunnels.get(0).id);
                        setTunnelPublicUrl(tunnelRes.tunnels.get(0).public_url);
                    } else {
                        System.out.println("Cannot find any tunnels!!!!");
                    }
                    
                    
                } else {
                    System.out.println("Co van de khi ket noi voi tunnel cua ban!!!" + " Code: " + responseCode);
                }

            } else {
                System.out.println("Co van de voi key cua ban!!!" + " Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Map<String, String> decodePublicUrl(String publicUrl) {
//        Lấy ra địa chỉ ip
        String[] parts = publicUrl.split(":");
        String ip = parts[1];
        ip = ip.replace("//", "");
//        Lấy ra port
        String port = parts[parts.length - 1];
        
        Map<String, String> result = new HashMap<String, String>();
        result.put("port", port);
        result.put("ip", ip);
        
        return result;
    }
}
