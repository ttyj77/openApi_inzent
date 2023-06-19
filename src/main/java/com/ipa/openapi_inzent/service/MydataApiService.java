package com.ipa.openapi_inzent.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ipa.openapi_inzent.dao.GetDataDAO;
import com.ipa.openapi_inzent.dao.MydataApiDAO;
import com.ipa.openapi_inzent.dao.MydataDAO;
import com.ipa.openapi_inzent.model.DataDTO;
import com.ipa.openapi_inzent.model.GetDataDTO;
import com.ipa.openapi_inzent.model.MdProviderDTO;
import com.ipa.openapi_inzent.model.MdReqInfoDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

@Service
public class MydataApiService {

    private MydataApiDAO mydataApiDAO;

    public MydataApiService(MydataApiDAO mydataApiDAO) {
        this.mydataApiDAO = mydataApiDAO;
    }

    public void providerHistoryInsert(MdProviderDTO mdProviderDTO) {
        System.out.println("MydataApiService.providerHistoryInsert");
        System.out.println("dataDTO = " + mdProviderDTO);
        mydataApiDAO.providerHistoryInsert(mdProviderDTO);
    }

    public int reqInfoInsert(MdReqInfoDTO mdReqInfoDTO) {
        System.out.println("MydataApiService.reqInfoInsert");
        System.out.println(mdReqInfoDTO);
        mydataApiDAO.reqInfoInsert(mdReqInfoDTO);
        return mdReqInfoDTO.getId();
    }

    private static HttpURLConnection getHttpURLConnection(StringBuilder urlBuilder) throws IOException {
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        return con;
    }

    /*금투-001*/
    public JsonObject invest001(String org_code, String limit, String token) {
        JsonObject jsonObject = new JsonObject();
        String x_api_tran_id = "1168119031SAA202303171424";
        String x_api_type = "user-search";
        StringBuffer response = null;
        try {
            StringBuilder urlBuilder = new StringBuilder("https://developers.mydatakorea.org:9443/v1/invest/accounts"); //URL enc: "UTF-8") + "=" + org_code); // org_code enc: "UTF-8") + "=" + limit); // limit
            urlBuilder.append("?" + URLEncoder.encode("org_code", "UTF-8") + "=" + org_code);
            urlBuilder.append("&" + URLEncoder.encode("limit", "UTF-8") + "=" + limit);

            HttpURLConnection con = getHttpURLConnection(urlBuilder);

            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Bearer" + token);
            con.setRequestProperty("x-api-tran-id", x_api_tran_id);
            con.setRequestProperty("x-api-type", x_api_type);
            con.setRequestProperty("X-FSI-SVC-DATA-KEY", "Y");
            con.setRequestProperty("accept", "application/json; charset=UTF-8");
            int responseCode = con.getResponseCode();

            System.out.println("responseCode = " + responseCode);

            BufferedReader br;
            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else { //
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }


            String inputLine;
            response = new StringBuffer();

            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }

            JsonParser jsonParser = new JsonParser();
            jsonObject = (JsonObject) jsonParser.parse(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /*금투-002*/
    public JsonObject invest002(String body, String token) {
        System.out.println(body);
        System.out.println("token = " + token);

        String x_api_tran_id = "1168119031SAA202303171424";
        String x_api_type = "user-search";
        StringBuffer response = null;
        JsonObject jsonObject = null;
        try {
            StringBuilder urlBuilder = new StringBuilder("https://developers.mydatakorea.org:9443/v1/invest/accounts/basic");
            HttpURLConnection con = getHttpURLConnection(urlBuilder);
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer" + token);
            con.setRequestProperty("x-api-tran-id", x_api_tran_id);
            con.setRequestProperty("x-api-type", x_api_type);
            con.setRequestProperty("User-Agent", "PostmanRuntime/7.32.2");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("accept", "application/json; charset=UTF-8");
            con.setRequestProperty("X-FSI-SVC-DATA-KEY", "Y");
            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            os.write(body.getBytes("utf-8")); // request body
            os.flush();
            os.close();

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else { // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            String inputLine;
            response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }


            br.close();
            String result = response.toString();
            JsonParser parser = new JsonParser();
            jsonObject = (JsonObject) parser.parse(result);

            System.out.println(jsonObject);

//            JsonObject org_code = (JsonObject) parser.parse(body); // org_code
//            DataDTO dataDTO = new DataDTO();
//            dataDTO.setResponse(jsonObject.get("basic_list").toString());
//            dataDTO.setOrg_code(org_code.get("org_code").toString());
//            dataDTO.setX_api_type(x_api_type);
//            dataDTO.setX_api_tran_id(x_api_tran_id);

//          DB insert
//          dataService.insert(dataDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    /* 금투-003 */
    public JsonObject invest003(String body, String token) {
        System.out.println("body = " + body);
        System.out.println("token = " + token);

        String x_api_tran_id = "1168119031SAA202303171424";
        String x_api_type = "user-search";
        StringBuffer response = null;
        JsonObject jsonObject = null;
        try {
            StringBuilder urlBuilder = new StringBuilder("https://developers.mydatakorea.org:9443/v1/invest/accounts/transactions"); //URL

            HttpURLConnection con = getHttpURLConnection(urlBuilder);
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer" + token);
            con.setRequestProperty("x-api-tran-id", x_api_tran_id);
            con.setRequestProperty("x-api-type", x_api_type);
            con.setRequestProperty("User-Agent", "PostmanRuntime/7.32.2");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("accept", "application/json; charset=UTF-8");
            con.setRequestProperty("X-FSI-SVC-DATA-KEY", "Y");
            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            os.write(body.getBytes("utf-8")); //request body

            os.flush();
            os.close();

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // H 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else { // 2
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            String inputLine;
            response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            String result = response.toString();
            JsonParser parser = new JsonParser();
            jsonObject = (JsonObject) parser.parse(result); // response

            System.out.println(jsonObject);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void insertResult(GetDataDTO getDataDTO) {
        mydataApiDAO.dataInsert(getDataDTO);
    }
}
