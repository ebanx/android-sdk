package com.ebanx.sdk;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import com.ebanx.sdk.entities.EBANXToken;
import com.ebanx.sdk.storage.EBANXStorage;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EBANXTokenTests {

    private static final String TAG = "EBANXTokenTests";

    @Mock
    private Context mContextMock;

    private EBANXStorage storage;

    @Before
    public void setUp() throws Exception {
        mContextMock = Mockito.mock(Context.class);
        Mockito.when(mContextMock.getApplicationContext()).thenReturn(mContextMock);
        System.out.print(mContextMock.getApplicationContext());
        storage = new EBANXStorage(mContextMock);

    }

    @Test
    public void testAddToken() throws Exception {
//        EBANXToken token = new EBANXToken("12345678","4111********1111");
//        storage.saveToken(token);
//        EBANXToken retrieveToken = storage.getToken("4111********1111");
//
//        Log.d(TAG, token.toString());
//
//        Assert.assertEquals(retrieveToken, token);
    }
}
