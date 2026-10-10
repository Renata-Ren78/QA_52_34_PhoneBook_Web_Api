package api_tests;

import dto.ContactDto;
import dto.ResponseMessageDto;
import dto.TokenDto;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseApi;

import static utils.ContactFactory.*;

import utils.ILogin;

import java.io.IOException;
import data_providers.ContactDataProvider;

public class AddNewContactApiTests implements BaseApi, ILogin {
    TokenDto tokenDto;
    SoftAssert softAssert = new SoftAssert();

    @BeforeClass
    public void login() {
        tokenDto = loginGetToken();
    }

    @Test
    public void addNewContactPositiveTest() {
        ContactDto contact = positiveContact();
        System.out.println(contact);
        System.out.println(tokenDto.getToken());
        RequestBody requestBody = RequestBody.create(GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_CONTACT)
                .addHeader(AUTH, tokenDto.getToken())
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(response.code(), 200);

    }

    @Test
    public void addNewContactWithSoftAssertPositiveTest() {
        ContactDto contact = positiveContact();
        System.out.println(contact);
        System.out.println(tokenDto.getToken());
        RequestBody requestBody = RequestBody.create(GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_CONTACT)
                .addHeader(AUTH, tokenDto.getToken())
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ResponseMessageDto responseMessageDto;
        try {
             responseMessageDto = GSON.fromJson(response.body().string()
                    ,ResponseMessageDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(responseMessageDto);
        softAssert.assertEquals(response.code(),200,"validate status code");
        softAssert.assertTrue(responseMessageDto.getMessage()
                .contains("Contact was added!"),"validate message");
        softAssert.assertAll();

    }

    @Test
    public void addNewContactWrongTokenNegativeTest() {
        ContactDto contact = positiveContact();
        System.out.println(contact);
        System.out.println(tokenDto.getToken());
        RequestBody requestBody = RequestBody.create(GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_CONTACT)
                .addHeader(AUTH, "tokenDto.getToken()")
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(response.code(), 401);

    }

    @Test
    public void addNewContactWOTokenNegativeTest() {
        ContactDto contact = positiveContact();
        System.out.println(contact);
        System.out.println(tokenDto.getToken());
        RequestBody requestBody = RequestBody.create(GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_CONTACT)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(response.code(), 403);

    }

    // HW_14_01
    @Test
    public void addNewContactWithEmptyNameNegativeTest() {
        ContactDto contact = positiveContact();
        contact.setName("");
        System.out.println(contact);
        System.out.println(tokenDto.getToken());
        RequestBody requestBody = RequestBody.create
                (GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_CONTACT)
                .addHeader(AUTH, tokenDto.getToken())
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(response.code(), 400);
    }

    // HW_14_02
    @Test
    public void addNewContactWithEmptyLastNameNegativeTest() {
        ContactDto contact = positiveContact();
        contact.setLastName("");
        System.out.println(contact);
        System.out.println(tokenDto.getToken());
        RequestBody requestBody = RequestBody.create
                (GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_CONTACT)
                .addHeader(AUTH, tokenDto.getToken())
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(response.code(), 400);
    }

    // HW_14_03
    @Test
    public void addNewContactWithInvalidEmailNegativeTest() {
        ContactDto contact = positiveContact();
        contact.setEmail("john@@gmail.com");
        System.out.println(contact);
        System.out.println(tokenDto.getToken());
        RequestBody requestBody = RequestBody.create
                (GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_CONTACT)
                .addHeader(AUTH, tokenDto.getToken())
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(response.code(), 400);
    }

    // HW_14_04
    @Test
    public void addNewContactWithEmptyPhoneNegativeTest() {
        ContactDto contact = positiveContact();
        contact.setPhone("");
        System.out.println(contact);
        System.out.println(tokenDto.getToken());
        RequestBody requestBody = RequestBody.create
                (GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_CONTACT)
                .addHeader(AUTH, tokenDto.getToken())
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(response.code(), 400);
    }

    // HW_14_05
    @Test
    public void addNewContactWithInvalidPhoneTooShortNegativeTest() {
        ContactDto contact = positiveContact();
        contact.setPhone("052123456");
        System.out.println(contact);
        System.out.println(tokenDto.getToken());
        RequestBody requestBody = RequestBody.create
                (GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_CONTACT)
                .addHeader(AUTH, tokenDto.getToken())
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(response.code(), 400);
    }

    // HW_14_06
    @Test
    public void addNewContactWithInvalidPhoneTooLongNegativeTest() {
        ContactDto contact = positiveContact();
        contact.setPhone("0521234567777777");
        System.out.println(contact);
        System.out.println(tokenDto.getToken());
        RequestBody requestBody = RequestBody.create
                (GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_CONTACT)
                .addHeader(AUTH, tokenDto.getToken())
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(response.code(), 400);
    }

    // HW_14_07
    @Test
    public void addNewContactWithEmptyAddressNegativeTest() {
        ContactDto contact = positiveContact();
        contact.setAddress("");
        System.out.println(contact);
        System.out.println(tokenDto.getToken());
        RequestBody requestBody = RequestBody.create
                (GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_CONTACT)
                .addHeader(AUTH, tokenDto.getToken())
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(response.code(), 400);
    }

    // HW_14_08
    @Test(dataProvider = "dataProviderInvalidContactData",
            dataProviderClass = ContactDataProvider.class)
    public void addNewContactInvalidDataNegativeTest(ContactDto contact) {

        System.out.println(contact);

        RequestBody requestBody =
                RequestBody.create(GSON.toJson(contact), JSON);

        Request request = new Request.Builder()
                .url(BASE_URL + ADD_CONTACT)
                .addHeader(AUTH, tokenDto.getToken())
                .post(requestBody)
                .build();

        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Assert.assertEquals(response.code(), 400);
    }

    // HW_14_09
    @Test(dataProvider = "dataProviderInvalidContactData",
            dataProviderClass = ContactDataProvider.class)
    public void addNewContactInvalidDataNegativeTest2(ContactDto contact) {

        System.out.println(contact);

        RequestBody requestBody = RequestBody.create(GSON.toJson(contact), TEXT);

        Request request = new Request.Builder()
                .url(BASE_URL + ADD_CONTACT)
                .addHeader(AUTH, tokenDto.getToken())
                .post(requestBody)
                .build();

        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Assert.assertEquals(response.code(), 500);
    }

    // HW_14_09
    @Test
    public void addNewContactWithoutTokenNegativeTest() {
        ContactDto contact = positiveContact();

        RequestBody requestBody =
                RequestBody.create(GSON.toJson(contact), JSON);

        Request request = new Request.Builder()
                .url(BASE_URL + ADD_CONTACT)
                .post(requestBody)
                .build();

        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Assert.assertEquals(response.code(), 403);
    }




}


