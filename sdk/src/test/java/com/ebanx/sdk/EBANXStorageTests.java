package com.ebanx.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;

import com.ebanx.sdk.entities.EBANXToken;
import com.ebanx.sdk.storage.EBANXStorage;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EBANXStorageTests {

    private Context mockedContext;
    private SharedPreferences mockedSharedPreference;
    private SharedPreferences.Editor mockedEditor;

    @Before
    public void setUp() throws Exception {
        mockedContext = Mockito.mock(Context.class);
        mockedSharedPreference = Mockito.mock(SharedPreferences.class);
        mockedEditor = Mockito.mock(SharedPreferences.Editor.class);
    }

    @Test
    public void testSaveTokenInStorage() throws Exception {
        Mockito.when(mockedContext.getSharedPreferences(Mockito.anyString(), Mockito.anyInt())).thenReturn(mockedSharedPreference);
        Mockito.when(mockedSharedPreference.contains(Mockito.anyString())).thenReturn(true);
        Mockito.when(mockedSharedPreference.getString(Mockito.anyString(), Mockito.anyString())).thenReturn("");
        Mockito.when(mockedSharedPreference.edit()).thenReturn(mockedEditor);

        EBANX.configure(mockedContext, "123456");

        EBANXToken token = new EBANXToken("123456", "4111********1111");
        EBANX.Token.saveToke(token);
        Mockito.verify(mockedEditor).commit();
        
        EBANX.shutDown();
    }

    @Test
    public void testSaveTokenInStorageWithTokens() throws Exception {
        Mockito.when(mockedContext.getSharedPreferences(Mockito.anyString(), Mockito.anyInt())).thenReturn(mockedSharedPreference);
        Mockito.when(mockedSharedPreference.contains(Mockito.anyString())).thenReturn(true);
        Mockito.when(mockedSharedPreference.getString(Mockito.anyString(), Mockito.anyString())).thenReturn("{\"4111********1111\":\"123456\"}");
        Mockito.when(mockedSharedPreference.edit()).thenReturn(mockedEditor);

        EBANX.configure(mockedContext, "123456");

        EBANXToken token = new EBANXToken("123456", "4111********1111");
        EBANX.Token.saveToke(token);
        Mockito.verify(mockedEditor).commit();

        EBANX.shutDown();
    }

    @Test
    public void testDeleteAllTokensInStorage() throws Exception {
        Mockito.when(mockedContext.getSharedPreferences(Mockito.anyString(), Mockito.anyInt())).thenReturn(mockedSharedPreference);
        Mockito.when(mockedSharedPreference.contains(Mockito.anyString())).thenReturn(true);
        Mockito.when(mockedSharedPreference.getString(Mockito.anyString(), Mockito.anyString())).thenReturn("");
        Mockito.when(mockedSharedPreference.edit()).thenReturn(mockedEditor);

        EBANX.configure(mockedContext, "123456");

        EBANX.Token.deleteAllTokens();
        Mockito.verify(mockedEditor).remove("tokens");
        Mockito.verify(mockedEditor).commit();

        EBANX.shutDown();
    }

    @Test
    public void testGetTokenSuccess() throws Exception {
        Mockito.when(mockedContext.getSharedPreferences(Mockito.anyString(), Mockito.anyInt())).thenReturn(mockedSharedPreference);
        Mockito.when(mockedSharedPreference.contains(Mockito.anyString())).thenReturn(true);
        Mockito.when(mockedSharedPreference.getString(Mockito.anyString(), Mockito.anyString())).thenReturn("{\"4111********1111\":\"123456\"}");
        Mockito.when(mockedSharedPreference.edit()).thenReturn(mockedEditor);

        EBANX.configure(mockedContext, "123456");

        EBANXToken token = EBANX.Token.getToken("4111********1111");
        Assert.assertNotNull(token);

        EBANX.shutDown();
    }

    @Test
    public void testGetTokenFail() throws Exception {
        Mockito.when(mockedContext.getSharedPreferences(Mockito.anyString(), Mockito.anyInt())).thenReturn(mockedSharedPreference);
        Mockito.when(mockedSharedPreference.contains(Mockito.anyString())).thenReturn(true);
        Mockito.when(mockedSharedPreference.getString(Mockito.anyString(), Mockito.anyString())).thenReturn("");
        Mockito.when(mockedSharedPreference.edit()).thenReturn(mockedEditor);

        EBANX.configure(mockedContext, "123456");

        EBANXToken token = EBANX.Token.getToken("4111********1111");
        Assert.assertNull(token);

        EBANX.shutDown();
    }

    @Test
    public void testGetTokens() throws Exception {
        Mockito.when(mockedContext.getSharedPreferences(Mockito.anyString(), Mockito.anyInt())).thenReturn(mockedSharedPreference);
        Mockito.when(mockedSharedPreference.contains(Mockito.anyString())).thenReturn(true);
        Mockito.when(mockedSharedPreference.getString(Mockito.anyString(), Mockito.anyString())).thenReturn("{\"4111********1111\":\"123456\"}");
        Mockito.when(mockedSharedPreference.edit()).thenReturn(mockedEditor);

        EBANX.configure(mockedContext, "123456");

        Assert.assertNotNull(EBANX.Token.getTokens());

        EBANX.shutDown();
    }

    @Test
    public void testDeleteToken() throws Exception {
        Mockito.when(mockedContext.getSharedPreferences(Mockito.anyString(), Mockito.anyInt())).thenReturn(mockedSharedPreference);
        Mockito.when(mockedSharedPreference.contains(Mockito.anyString())).thenReturn(true);
        Mockito.when(mockedSharedPreference.getString(Mockito.anyString(), Mockito.anyString())).thenReturn("{\"4111********1111\":\"123456\", \"4222********2222\":\"654321\"}");
        Mockito.when(mockedSharedPreference.edit()).thenReturn(mockedEditor);

        EBANX.configure(mockedContext, "123456");

        EBANXToken token = new EBANXToken("123456", "4111********1111");
        EBANX.Token.deleteToken(token);

        Mockito.verify(mockedEditor).commit();
        Assert.assertNull(EBANX.Token.getToken(token.getCardNumberMasked()));

        EBANX.shutDown();
    }

    @Test
    public void testThrowsExceptionInCreateStorageToken() throws Exception {
        Mockito.when(mockedContext.getSharedPreferences(Mockito.anyString(), Mockito.anyInt())).thenReturn(mockedSharedPreference);
        Mockito.when(mockedSharedPreference.contains(Mockito.anyString())).thenReturn(true);
        Mockito.when(mockedSharedPreference.getString(Mockito.anyString(), Mockito.anyString())).thenReturn("{");
        Mockito.when(mockedSharedPreference.edit()).thenReturn(mockedEditor);
        try {
            EBANX.configure(mockedContext, "123456");
        } catch (Exception e) {
            Assert.assertTrue(true);
        }

        EBANX.shutDown();
    }
}
