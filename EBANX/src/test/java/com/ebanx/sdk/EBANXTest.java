package com.ebanx.sdk;

import android.content.Context;
import android.content.SharedPreferences;


import com.ebanx.sdk.entities.EBANXCountry;
import com.ebanx.sdk.entities.EBANXCreditCard;
import com.ebanx.sdk.entities.EBANXCreditCardType;
import com.ebanx.sdk.entities.EBANXError;
import com.ebanx.sdk.entities.EBANXToken;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.util.StringJoiner;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class EBANXTest {

    private Context mockedContext;
    private SharedPreferences mockedSharedPreference;
    private SharedPreferences.Editor mockedEditor;

    @Before
    public void setUp() throws Exception {
        mockedContext = Mockito.mock(Context.class);
        mockedSharedPreference = Mockito.mock(SharedPreferences.class);
        mockedEditor = Mockito.mock(SharedPreferences.Editor.class);

        Mockito.when(mockedContext.getSharedPreferences(Mockito.anyString(), Mockito.anyInt())).thenReturn(mockedSharedPreference);
        Mockito.when(mockedSharedPreference.contains(Mockito.anyString())).thenReturn(true);
        Mockito.when(mockedSharedPreference.getString(Mockito.anyString(), Mockito.anyString())).thenReturn("");
        Mockito.when(mockedSharedPreference.edit()).thenReturn(mockedEditor);
    }

    @Test
    public void testConfigurePublicKeySuccess() throws Exception {
        String publicKey = "123456";
        EBANX.configure(mockedContext, publicKey);
        Assert.assertEquals(EBANX.getPublicKey(),publicKey);

        EBANX.shutDown();
    }

    @Test
    public void testTestModeDev() throws Exception {
        String publicKey = "123456";
        EBANX.configure(mockedContext, publicKey, true);
        Assert.assertTrue(EBANX.getTestMode());

        EBANX.shutDown();
    }

    @Test
    public void testTestModeProd() throws Exception {
        String publicKey = "123456";
        EBANX.configure(mockedContext, publicKey);
        Assert.assertFalse(EBANX.getTestMode());

        EBANX.shutDown();
    }

    @Test
    public void testCreateCard() throws Exception {
        String name = "Fulano de tal";
        String number = "4111111111111111";
        String dueDate = "12/2016";
        String cvv = "123";
        EBANXCreditCardType type = EBANXCreditCardType.Visa;

        EBANXCreditCard creditCard = new EBANXCreditCard("Fulano de tal", "4111111111111111","12/2016","123", EBANXCreditCardType.Visa);
        Assert.assertEquals(creditCard.getName(), name);
        Assert.assertEquals(creditCard.getNumber(), number);
        Assert.assertEquals(creditCard.getDueDate(), dueDate);
        Assert.assertEquals(creditCard.getCvv(), cvv);
        Assert.assertEquals(creditCard.getType(), type);

        creditCard.setName("New name");
        creditCard.setNumber("4222222222222222");
        creditCard.setDueDate("11/2016");
        creditCard.setCvv("321");
        creditCard.setType(EBANXCreditCardType.Mastercard);

        Assert.assertEquals(creditCard.getName(), "New name");
        Assert.assertEquals(creditCard.getNumber(), "4222222222222222");
        Assert.assertEquals(creditCard.getDueDate(), "11/2016");
        Assert.assertEquals(creditCard.getCvv(), "321");
        Assert.assertEquals(creditCard.getType(), EBANXCreditCardType.Mastercard);
    }

    @Test
    public void testCreateToken() throws Exception {
        EBANXToken token = new EBANXToken("12345", "4111111111111111");
        Assert.assertNotNull(token.toString());

        token.setCardNumberMasked("4222222222222222");
        token.setToken("654321");

        Assert.assertEquals(token.getCardNumberMasked(), "4222222222222222");
        Assert.assertEquals(token.getToken(), "654321");
    }

    @Test
    public void testCreateErrorAndErrorType() throws Exception {
        EBANXError error = new EBANXError("ERROR", "DA-1", "Invalid integration key");
        EBANXError genericError = new EBANXError("ERROR", "DA-1", "GenericError");
        EBANXError parseError = EBANXError.createParseError();
        EBANXError publicKeyNotSet = EBANXError.createPublicKeyNotSetError();

        Assert.assertNotNull(error.toString());
        Assert.assertEquals(error.getStatus(), "ERROR");
        Assert.assertEquals(error.getCode(), "DA-1");
        Assert.assertEquals(error.getMessage(), "Invalid integration key");
        Assert.assertEquals(error.getType(), EBANXError.ErrorType.InvalidPublicKey);

        Assert.assertNotNull(EBANXError.ErrorType.values());
        Assert.assertNotNull(EBANXError.ErrorType.valueOf("PublicKeyNotSet"));

        Assert.assertEquals(genericError.getType(), EBANXError.ErrorType.GenericError);
        Assert.assertEquals(parseError.getType(), EBANXError.ErrorType.ParseError);
        Assert.assertEquals(publicKeyNotSet.getType(), EBANXError.ErrorType.PublicKeyNotSet);

        genericError.setStatus("ERROR_2");
        genericError.setCode("DB-2");
        genericError.setMessage("Change message");

        Assert.assertEquals(genericError.getStatus(), "ERROR_2");
        Assert.assertEquals(genericError.getCode(), "DB-2");
        Assert.assertEquals(genericError.getMessage(), "Change message");

    }

    @Test
    public void testCountry() throws Exception {
        EBANXCountry country = EBANXCountry.BR;
        Assert.assertEquals(country.description(), EBANXCountry.BR.description());

        Assert.assertNotNull(EBANXCountry.values());
        Assert.assertNotNull(EBANXCountry.valueOf("BR"));
    }

    @Test
    public void testCreditCardType() throws Exception {
        EBANXCreditCardType creditCardType = EBANXCreditCardType.Visa;
        Assert.assertEquals(creditCardType.description(), EBANXCreditCardType.Visa.description());

        Assert.assertNotNull(EBANXCreditCardType.values());
        Assert.assertNotNull(EBANXCreditCardType.valueOf("Visa"));
    }
}