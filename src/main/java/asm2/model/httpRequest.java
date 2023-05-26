package asm2.model;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class httpRequest {
    public String PASTEBIN_API_KEY = System.getenv("PASTEBIN_API_KEY");
    public String INPUT_API_KEY = System.getenv("INPUT_API_KEY");
    public Map<String, currency> getCurrenciesRequest(String type) {
//        long requestTime = System.currentTimeMillis();

//        long responseTime = System.currentTimeMillis();


        try {
            HttpRequest request = HttpRequest.newBuilder(new URI("https://api" +
                            ".currencyscoop.com/v1/currencies?api_key="+INPUT_API_KEY+"&type="+type))
//                    .POST(HttpRequest.BodyPublishers.ofString(token)).POST(HttpRequest.BodyPublishers.ofString(type))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

//            System.out.println("Response status code was: " + response.statusCode());
//            System.out.println("Response headers were: " + response.headers());
//            System.out.println("Response body was:\n" + response.body());
            String s = Integer.toString(response.statusCode());
            String str = s.substring(0, 1);
            if(!str.equals("2") && !str.equals("1") && !str.equals("3")){
//                System.out.println("wrong");
                return null;
            }
            Gson gson = new Gson();

            int index1 = response.body().indexOf(String.valueOf("fiats"));

            String json = response.body().substring(index1+7,
                    response.body().length()-2);
            Map<String, currency> decoded = gson.fromJson(json,
                    new TypeToken<Map<String, currency
                                                >>(){}.getType());
//            long responseTime = System.currentTimeMillis();
            return decoded;

        } catch (IOException | InterruptedException e) {
//            System.out.println("Something went wrong with our request!");
            System.out.println(e.getMessage());
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
        }
        return null;
    }
//    public String getDateRequest(String base,String symbols) {
//        try {
//            HttpRequest request = HttpRequest.newBuilder(new URI("https://api" +
//                            ".currencyscoop.com/v1/latest?api_key="+INPUT_API_KEY+
//                            "&base="+base+"&symbols="+symbols))
////                    .POST(HttpRequest.BodyPublishers.ofString(token)).POST(HttpRequest.BodyPublishers.ofString(type))
//                    .GET()
//                    .build();
//
//            HttpClient client = HttpClient.newBuilder().build();
//            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//            System.out.println("Response status code was: " + response.statusCode());
//            System.out.println("Response headers were: " + response.headers());
//            System.out.println("Response body was:\n" + response.body());
//            String s = Integer.toString(response.statusCode());
//            String str = s.substring(0, 1);
//            if(!str.equals("2") && !str.equals("1") && !str.equals("3")){
//                System.out.println("wrong");
//                return "wrong";
//            }
//            Gson gson = new Gson();
//            latest post = gson.fromJson(response.body(), latest.class);
////            convert post = gson.fromJson(response.body(), convert.class);
//            System.out.println(post);
//            return post.toString();
//        } catch (IOException | InterruptedException e) {
//            System.out.println("Something went wrong with our request!");
//            System.out.println(e.getMessage());
//        } catch (URISyntaxException ignored) {
//            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
//            // hard-coded and so needs a way to be checked for correctness at runtime.
//        }
//        return "wrong";
//    }

    public String getLatestRequest(String base,String symbols) {
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI("https://api" +
                            ".currencyscoop.com/v1/latest?api_key="+INPUT_API_KEY+
                            "&base="+base+"&symbols="+symbols))
//                    .POST(HttpRequest.BodyPublishers.ofString(token)).POST(HttpRequest.BodyPublishers.ofString(type))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Response status code was: " + response.statusCode());
            System.out.println("Response headers were: " + response.headers());
            System.out.println("Response body was:\n" + response.body());
            String s = Integer.toString(response.statusCode());
            String str = s.substring(0, 1);
            if(!str.equals("2") && !str.equals("1") && !str.equals("3")){
//                System.out.println("wrong");
                return "wrong";
            }
            Gson gson = new Gson();
            latest post = gson.fromJson(response.body(), latest.class);

            String rate = response.body();
            int index1 = rate.indexOf(String.valueOf(symbols));
            System.out.println(index1);
            if (index1 != -1) {
                rate = rate.substring(index1+5,rate.length()-3);
                if(rate.equals("null")){
                    return "0";
                }
            }
//            rate = rate.substring(4,rate.length());
            if(base.equals(symbols)){
                rate ="1";
            }

            return rate;

        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
            System.out.println(e.getMessage());
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
        }
        return "0";
    }

    public String getConvertRequest(String from,
                                       String to,String amount) {
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.currencyscoop.com/v1/convert?api_key="+INPUT_API_KEY+
                            "&from="+from+"&to="+to+
                            "&amount="+amount))
//                    .POST(HttpRequest.BodyPublishers.ofString(token)).POST(HttpRequest.BodyPublishers.ofString(base)).POST(HttpRequest.BodyPublishers.ofString(date)).POST(HttpRequest.BodyPublishers.ofString(symbols))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Response status code was: " + response.statusCode());
            System.out.println("Response headers were: " + response.headers());
            System.out.println("Response body was:\n" + response.body());
            String s = Integer.toString(response.statusCode());
            String str = s.substring(0, 1);
            if(!str.equals("2") && !str.equals("1") && !str.equals("3")){
//                System.out.println("wrong");
                return "wrong";
            }
            Gson gson = new Gson();
            convert post = gson.fromJson(response.body(), convert.class);
            System.out.println(post);
            return post.toString();

        } catch (IOException | InterruptedException e) {
//            System.out.println("Something went wrong with our request!");
            System.out.println(e.getMessage());
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
        }
        return "wrong";
    }
    public String output(String api_paste_code) {
        try {

            URL url = new URL("https://pastebin.com/api/api_post.php");
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");

            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            httpConn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
            writer.write("api_dev_key="+PASTEBIN_API_KEY +"&api_paste_code="+api_paste_code+
                    "&api_option" +
                    "=paste");
            writer.flush();
            writer.close();
            httpConn.getOutputStream().close();

            InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                    ? httpConn.getInputStream()
                    : httpConn.getErrorStream();
            Scanner s = new Scanner(responseStream).useDelimiter("\\A");
            String response = s.hasNext() ? s.next() : "";
            System.out.println("Response status code was: " + httpConn.getResponseCode());
            return response.toString();

        } catch (MalformedURLException ignored) {
            System.out.println("Something went wrong with our request!");
            System.out.println(ignored.getMessage());
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "wrong";
    }

}
