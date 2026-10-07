package api_tests;

import dto.UserLombok;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.BaseApi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static utils.UserFactory.*;
import static utils.PropertiesReader.*;

public class RegistrationLoginApiTests implements BaseApi {

    @Test
    public void registrationApiPositiveTest() {
        UserLombok user = positiveUser();
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 200);
    }

    @Test
    public void registrationApiWrongPasswordNegativeTest() {
        UserLombok user = positiveUser();
        user.setPassword("qwerfs123!");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 400);
    }

    @Test
    public void registrationApiDuplicateUserNegativeTest() {
        UserLombok user = positiveUser();
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            OK_HTTP_CLIENT.newCall(request).execute();
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 409);
    }

    @Test
    public void registrationApiWrongFormatNegativeTest() {
        UserLombok user = positiveUser();
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), TEXT);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 500);
    }

    @Test
    public void loginApiPositiveTest() {
        UserLombok user = UserLombok.builder()
                .username(getProperty("base.properties", "email"))
                .password(getProperty("base.properties", "password"))
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 200);
    }

    @Test
    public void loginApiWrongPasswordNegativeTest() {
        UserLombok user = UserLombok.builder()
                .username(getProperty("base.properties", "email"))
                .password("Msjdhr1234!")
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 401);
    }


    // HW_13_01
    @Test
    public void loginApiEmptyPasswordAndEmailNegativeTest() {
        UserLombok user = UserLombok.builder()
                .username("")
                .password("")
                .build();
        RequestBody requestBody = RequestBody.create
                (GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 401);
    }

    // HW_13_02
    @Test
    public void loginApiEmptyEmailNegativeTest() {
        UserLombok user = UserLombok.builder()
                .username("")
                .password("123RenC!")
                .build();
        RequestBody requestBody = RequestBody.create
                (GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 401);
    }

    // HW_13_03
    @Test
    public void loginApiEmptyPasswordNegativeTest() {
        UserLombok user = UserLombok.builder()
                .username("renate.certoka1@gmail.com")
                .password("")
                .build();
        RequestBody requestBody = RequestBody.create
                (GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 401);
    }

    // HW_13_04
    @Test
    public void loginApiWrongEmailFormatNegativeTest() {
        UserLombok user = UserLombok.builder()
                .username("renate.certoka1@gmailcom")
                .password("123RenC!")
                .build();
        RequestBody requestBody = RequestBody.create
                (GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 401);
    }

    // HW_13_05
    @Test
    public void loginApiUnregistratedUserNegativeTest() {
        UserLombok user = UserLombok.builder()
                .username("renate.certoka3@gmai.lcom")
                .password("123RenC!")
                .build();
        RequestBody requestBody = RequestBody.create
                (GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 401);
    }


    // HW_13_06
    @Test
    public void registrationApiEmptyPasswordAndEmailNegativeTest() {
        UserLombok user = positiveUser();
        user.setUsername("");
        user.setPassword("");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 400);
    }

    // HW_13_07
    @Test
    public void registrationApiEmptyEmailNegativeTest() {
        UserLombok user = positiveUser();
        user.setUsername("");
        user.setPassword("Ashety1234!");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 400);
    }

    // HW_13_08
    @Test
    public void registrationApiEmptyPasswordNegativeTest() {
        UserLombok user = positiveUser();
        user.setUsername("renate.test@gmail.com");
        user.setPassword("");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 400);
    }

    // HW_13_09
    @Test
    public void registrationApiWrongEmailFormatNegativeTest() {
        UserLombok user = positiveUser();
        user.setUsername("rengmail.com");
        user.setPassword("Ashety1234!");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 400);
    }

    // HW_13_10
    @Test
    public void registrationApiWrongPasswordFormatWithoutSymbolsNegativeTest() {
        UserLombok user = positiveUser();
        user.setUsername("rasasddfgrhj1@gmail.com");
        user.setPassword("123456Asdfge");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 400);
    }

    // HW_13_11
    @Test
    public void registrationApiWrongPasswordFormatWithoutUpperLetterNegativeTest() {
        UserLombok user = positiveUser();
        user.setUsername("rasasddfgrhj2@gmail.com");
        user.setPassword("qrwetahsjdysg123!");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 400);
    }

    // HW_13_12
    @Test
    public void registrationApiWrongPasswordFormatWithoutDigitsNegativeTest() {
        UserLombok user = positiveUser();
        user.setUsername("rasasddfgrhj3@gmail.com");
        user.setPassword("adfdgeRWQWga$!");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 400);
    }

    // HW_13_13
    @Test
    public void registrationApiWrongPasswordFormatLettersAndSymbolNegativeTest() {
        UserLombok user = positiveUser();
        user.setUsername("rasasddfgrhj4@gmail.com");
        user.setPassword("qrwetahsjdysga!");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 400);
    }


    // HW_13_15
    @Test
    public void registrationApiWrongPasswordFormatTooShortNegativeTest() {
        UserLombok user = positiveUser();
        user.setUsername("rasasddfgrhj6@gmail.com");
        user.setPassword("Asd1s3!");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 400);
    }

    // HW_13_16
    @Test
    public void registrationApiWrongPasswordFormatTooLongNegativeTest() {
        UserLombok user = positiveUser();
        user.setUsername("rasasddfgrhj7@gmail.com");
        user.setPassword("AadfdghWRsd12345567$234!!!sfdg4563QWE");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 409);
    }

    // HW_13_17
    @Test
    public void registrationApiWrongBodyNegativeTest() {
        //UserLombok user = positiveUser();
        RequestBody requestBody = RequestBody.create
                ("wrong request body", JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 500);
    }

// dataProviderWrongPasswordOrEmail

    @Test
public void loginApiWrongKeyNegativeTest() {
    UserLombok user = UserLombok.builder()
            .username(getProperty("base.properties", "email"))
            .password(getProperty("base.properties", "password"))
            .build();
    Map<String, String> invalidJson = new HashMap<>();
    invalidJson.put("email", user.getUsername());
    invalidJson.put("password", user.getPassword());

    RequestBody requestBody = RequestBody.create(GSON.toJson(invalidJson), JSON);
    Request request = new Request.Builder()
            .url(BASE_URL + LOGIN_URL)
            .post(requestBody)
            .build();
    Response response;
    try {
        response = OK_HTTP_CLIENT.newCall(request).execute();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
    System.out.println(response);
    Assert.assertEquals(response.code(), 400);
}




}
