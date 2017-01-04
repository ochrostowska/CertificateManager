package pl.oldzi.assecoTask.network_calls;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import okhttp3.*;
import pl.oldzi.assecoTask.model.*;
import pl.oldzi.assecoTask.presenter.CertDataCommunicator;
import pl.oldzi.assecoTask.presenter.LoginCommunicator;
import pl.oldzi.assecoTask.presenter.UserDataCommunicator;
import sun.security.provider.X509Factory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.Base64;

public class BaseNetworkManager {

    private static final String API_BASE_URL = "http://213.222.200.96";
    private static final String USERNAME = "java_developerbj2BgPih";
    private static final String PASSWORD = "yBIQPyVS";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String TOKENFORTESTING = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImphdmFfZGV2ZWxvcGVyYmoyQmdQaWgiLCJleHAiOjQ2MzU0MjE1MDF9.dMINLcuuETVYN-E-dOABWH4PQaH8mJSk-UkNFqva4jU";
    private String accessToken = null;
    private boolean tokenValid = false;
    private static OkHttpClient client;
    private static Gson gson;
    private LoginCommunicator loginCommunicator;
    private UserDataCommunicator userDataCommunicator;
    private CertDataCommunicator certDataCommunicator;

    private static BaseNetworkManager ourInstance;

    public static BaseNetworkManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new BaseNetworkManager();
            client = new OkHttpClient();
            gson = new GsonBuilder().create();
        }
        return ourInstance;
    }

    public void setCommunicator(LoginCommunicator loginCommunicator) {
        this.loginCommunicator = loginCommunicator;
    }

    public void setCommunicator(UserDataCommunicator userDataCommunicator) {
        this.userDataCommunicator = userDataCommunicator;
    }

    public void setCommunicator(CertDataCommunicator certDataCommunicator) {
        this.certDataCommunicator = certDataCommunicator;
    }

    public void getToken(String username, String password) {
        if (loginCommunicator != null) {
            String credentials = username + ":" + password;
            final String basic = Base64.getEncoder().encodeToString(credentials.getBytes());

            final Request request = new Request.Builder()
                    .url(API_BASE_URL + "/auth")
                    .addHeader("Authorization", "Basic " + basic)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    if(e.getClass() == ConnectException.class) {
                        loginCommunicator.noInternetConnection();
                        return;
                    }
                    loginCommunicator.tokenNotRetrieved();
                }

                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        loginCommunicator.tokenNotRetrieved();
                        throw new IOException("Unexpected error :( " + response);
                    }

                    String responseString = response.body().string();
                    System.out.println("Response body is : " + responseString);
                    accessToken = gson.fromJson(responseString, Properties.class).getProperty("token");
                    System.out.println("Access token is : " + accessToken);
                    if (accessToken == null) {
                        loginCommunicator.tokenNotRetrieved();
                    } else {
                        loginCommunicator.tokenRetrieved(username, password);
                    }
                }
            });
        }
    }

    public boolean verifyToken() {
        tokenValid = false;
        if (loginCommunicator != null) {
            if (accessToken == null) {
                return false;
            }
            final Request request = new Request.Builder()
                    .url(API_BASE_URL + "/auth/" + accessToken)
                    .addHeader("Authorization", "Token " + accessToken)
                    .build();


            client.newCall(request).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) loginCommunicator.tokenValid(false);
                    String responseString = response.body().string();
                    System.out.println("Response body is : " + responseString);
                    String tokenStatus = gson.fromJson(responseString, Properties.class).getProperty("status");
                    if (tokenStatus.equals("OK")) {
                        tokenValid = true;
                    } else {
                        loginCommunicator.tokenValid(false);
                    }
                }
            });
        }
        return tokenValid;
    }

    public void logOut() {
        if (loginCommunicator != null) {
            final Request request = new Request.Builder()
                    .delete()
                    .url(API_BASE_URL + "/auth/" + accessToken)
                    .addHeader("Authorization", "Token " + accessToken)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) throw new IOException("Unexpected error in onResponse:( " + response);

                    String responseString = response.body().string();
                    System.out.println("Response body is : " + responseString);
                    String tokenStatus = gson.fromJson(responseString, Properties.class).getProperty("status");
                    System.out.println("Status is : " + tokenStatus);
                }
            });
        }
    }

    public void getUsers() {
        if(userDataCommunicator!=null) {
            Request request = new Request.Builder()
                    .url(API_BASE_URL + "/user")
                    .addHeader("Authorization", "Token " + TOKENFORTESTING)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    if(e.getClass() == ConnectException.class) {
                        userDataCommunicator.noInternetConnection();
                        return;
                    }
                    e.printStackTrace();
                }

                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) throw new IOException("Unexpected error in onResponse:( " + response);

                    String responseString = response.body().string();
                    System.out.println("Response body is : " + responseString);
                    List<LinkedHashMap<String, String>> users = gson
                            .fromJson(responseString, new TypeToken<List<LinkedHashMap<String, String>>>() {
                            }.getType());

                    ObservableList<User> userList = FXCollections.observableArrayList();
                    for (LinkedHashMap<String, String> personData : users) {
                        User user = new User(personData);
                        userList.add(user);
                    }
                    if (userDataCommunicator != null) {
                        userDataCommunicator.userDataRetrieved(userList);
                    }
                }
            });
        }
    }

    public void addUser(String username, String password, String firstName, String lastName, String age) {
        if(userDataCommunicator!=null) {
            String json =
                    " { \"username\": \"" + username + "\"," +
                            "\"password\": \"" + password + "\"," +
                            "\"firstName\": \"" + firstName + "\"," +
                            "\"lastName\": \"" + lastName + "\"," +
                            "\"age\": \"" + age + "\"}";

            RequestBody requestBody = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(API_BASE_URL + "/user")
                    .addHeader("Authorization", "Token " + TOKENFORTESTING)
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    if (e.getClass() == ConnectException.class) {
                        userDataCommunicator.noInternetConnection();
                        return;
                    }
                    e.printStackTrace();
                }

                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        userDataCommunicator.userAdded(false);
                    }
                    String responseString = response.body().string();
                    System.out.println("Response body is : " + responseString);
                    userDataCommunicator.userAdded(true);
                }
            });
        }
    }

    public void editUser(String username, String firstName, String lastName, String age) {
        if(userDataCommunicator!=null) {
            String json =
                    " { \"firstName\": \"" + firstName + "\"," +
                            "\"lastName\": \"" + lastName + "\"," +
                            "\"age\": \"" + age + "\"}";

            RequestBody requestBody = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(API_BASE_URL + "/user/" + username)
                    .addHeader("Authorization", "Token " + TOKENFORTESTING)
                    .put(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    if(e.getClass() == ConnectException.class) {
                        userDataCommunicator.noInternetConnection();
                        return;
                    }
                    e.printStackTrace();
                }

                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        userDataCommunicator.userAdded(false);
                    }
                    String responseString = response.body().string();
                    System.out.println("Response body is : " + responseString);
                    userDataCommunicator.userAdded(true);
                }
            });
        }
    }

    public void deleteUser(String username) {
        if(userDataCommunicator!=null) {
            Request request = new Request.Builder()
                    .url(API_BASE_URL + "/user/" + username)
                    .addHeader("Authorization", "Token " + TOKENFORTESTING)
                    .delete()
                    .build();

            client.newCall(request).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    if(e.getClass() == ConnectException.class) {
                        userDataCommunicator.noInternetConnection();
                        return;
                    }
                    e.printStackTrace();
                }

                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) throw new IOException("Unexpected error in onResponse:( " + response);
                    String responseString = response.body().string();
                    System.out.println("Response body is : " + responseString);
                    userDataCommunicator.userDeleted(true);
                }
            });
        }
    }

    public void getCerts() {
        if(certDataCommunicator!=null) {
        Request request = new Request.Builder()
                .url(API_BASE_URL + "/cert")
                .addHeader("Authorization", "Token " + TOKENFORTESTING)
                .build();

        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                if(e.getClass() == ConnectException.class) {
                    certDataCommunicator.noInternetConnection();
                    return;
                }
                e.printStackTrace();
            }

            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected error in onResponse:( " + response);

                String responseString = response.body().string();
                List<LinkedHashMap<String, String>> certificates = gson
                        .fromJson(responseString, new TypeToken<List<LinkedHashMap<String, String>>>() {
                        }.getType());

                ObservableList<Cert> certList = FXCollections.observableArrayList();
                for (LinkedHashMap<String, String> certData : certificates) {
                    Cert c = new Cert(certData);
                    String certWithoutHeaders = c.getRaw_bytes().replaceAll(X509Factory.BEGIN_CERT, "").replaceAll(X509Factory.END_CERT, "");
                    try {
                        byte[] decoded = com.sun.org.apache.xml.internal.security.utils.Base64.decode(certWithoutHeaders);
                        X509Certificate x = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(decoded));
                        if (x != null) c.setEncodedCertificate(x);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    certList.add(c);
                }
                if (certDataCommunicator != null) {
                    certDataCommunicator.certDataRetrieved(certList);
                }
            }
        });
    }
    }

    public void addCert(String certInPem) {
            if(certDataCommunicator!=null) {
                String json =
                        " { \"raw_bytes\": \"" + certInPem + "\"}";

                System.out.println(json);
                RequestBody requestBody = RequestBody.create(JSON, json);
                Request request = new Request.Builder()
                        .url(API_BASE_URL + "/cert")
                        .addHeader("Authorization", "Token " + TOKENFORTESTING)
                        .post(requestBody)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    public void onFailure(Call call, IOException e) {
                        if (e.getClass() == ConnectException.class) {
                            certDataCommunicator.noInternetConnection();
                            return;
                        }
                        e.printStackTrace();
                    }

                    public void onResponse(Call call, Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            certDataCommunicator.certAdded(false);
                        }
                        String responseString = response.body().string();
                        System.out.println("Response body is : " + responseString);
                        certDataCommunicator.certAdded(true);
                    }
                });
            }
    }

    public void editCert(String id, String certInPem) {
        if(certDataCommunicator!=null) {
            String json =
                    " { \"raw_bytes\": \"" + certInPem + "\"}";

            RequestBody requestBody = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(API_BASE_URL + "/cert/" + id)
                    .addHeader("Authorization", "Token " + TOKENFORTESTING)
                    .put(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    if(e.getClass() == ConnectException.class) {
                        certDataCommunicator.noInternetConnection();
                        return;
                    }
                    e.printStackTrace();
                }

                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        certDataCommunicator.certAdded(false);
                    }
                    String responseString = response.body().string();
                    System.out.println("Response body is : " + responseString);
                    certDataCommunicator.certAdded(true);
                }
            });
        }
    }

    public void deleteCert(String id) {
        if (certDataCommunicator != null) {
            Request request = new Request.Builder()
                    .url(API_BASE_URL + "/cert/" + id)
                    .addHeader("Authorization", "Token " + TOKENFORTESTING)
                    .delete()
                    .build();

            client.newCall(request).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    if(e.getClass() == ConnectException.class) {
                        certDataCommunicator.noInternetConnection();
                        return;
                    }
                    e.printStackTrace();
                }

                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        certDataCommunicator.certDeleted(false);
                        throw new IOException("Unexpected error in onResponse:( " + response);
                    }
                    String responseString = response.body().string();
                    System.out.println("Response body is : " + responseString);
                    certDataCommunicator.certDeleted(true);
                }
            });
        }
    }

}
