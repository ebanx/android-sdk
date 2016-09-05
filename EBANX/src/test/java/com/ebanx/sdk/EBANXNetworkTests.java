package com.ebanx.sdk;

import android.content.Context;

import com.ebanx.sdk.entities.EBANXCountry;
import com.ebanx.sdk.entities.EBANXCreditCard;
import com.ebanx.sdk.entities.EBANXCreditCardType;
import com.ebanx.sdk.entities.EBANXError;
import com.ebanx.sdk.entities.EBANXToken;
import com.ebanx.sdk.network.EBANXNetwork;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


@RunWith(RobolectricTestRunner.class)
@Config(constants=BuildConfig.class, manifest=Config.NONE)
public class EBANXNetworkTests {

    Context mockedContext;
    private static boolean called;

    @Before
    public void setUp() throws Exception {
        mockedContext = ShadowApplication.getInstance().getApplicationContext();
    }

    @Test
    public void testTokenSuccess() throws Exception {
        called = false;

        final CountDownLatch signal = new CountDownLatch(1);

        EBANX.configure(mockedContext, "1231000");
        EBANXCreditCard creditCard = new EBANXCreditCard("Fulano de tal", "4111111111111111","12/2016","123", EBANXCreditCardType.Visa);

        EBANX.Token.create(creditCard, EBANXCountry.BR, new EBANXTokenRequestComplete() {
            @Override
            public void Success(EBANXToken token) {
                Assert.assertTrue(token instanceof EBANXToken);
                called = true;
                signal.countDown();
            }

            @Override
            public void APIError(EBANXError error) {
                signal.countDown();
            }

            @Override
            public void NetworkError(Exception e) {
                signal.countDown();
            }
        });

        signal.await(10, TimeUnit.SECONDS);
        EBANX.shutDown();
        Assert.assertTrue(called);
    }

    @Test
    public void testTokenPublicKeyNotSet() throws Exception {
        called = false;

        final CountDownLatch signal = new CountDownLatch(1);

        EBANX.configure(mockedContext, "");
        EBANXCreditCard creditCard = new EBANXCreditCard("Fulano de tal", "4111111111111111","12/2016","123", EBANXCreditCardType.Visa);

        EBANX.Token.create(creditCard, EBANXCountry.BR, new EBANXTokenRequestComplete() {
            @Override
            public void Success(EBANXToken token) {
                signal.countDown();
            }

            @Override
            public void APIError(EBANXError error) {
                Assert.assertEquals(error.getType(), EBANXError.ErrorType.PublicKeyNotSet);
                called = true;
                signal.countDown();
            }

            @Override
            public void NetworkError(Exception e) {
                signal.countDown();
            }
        });

        signal.await(10, TimeUnit.SECONDS);
        EBANX.shutDown();
        Assert.assertTrue(called);
    }

    @Test
    public void testSetCVVWithTokenAndTokenNotFound() throws Exception {
        called = false;

        final CountDownLatch signal = new CountDownLatch(1);

        EBANX.configure(mockedContext, "1231000");
        EBANXToken token = new EBANXToken("12345678", "4111********1111");

        EBANX.Token.setCVV(token, "123", new EBANXTokenRequestComplete() {
            @Override
            public void Success(EBANXToken token) {
                signal.countDown();
            }

            @Override
            public void APIError(EBANXError error) {
                Assert.assertEquals(error.getType(), EBANXError.ErrorType.GenericError);
                called = true;
                signal.countDown();
            }

            @Override
            public void NetworkError(Exception e) {
                signal.countDown();
            }
        });

        signal.await(10, TimeUnit.SECONDS);
        EBANX.shutDown();
        Assert.assertTrue(called);
    }

    @Test
    public void testSetCVVTokenNotFound() throws Exception {
        called = false;

        final CountDownLatch signal = new CountDownLatch(1);

        EBANX.configure(mockedContext, "1231000");

        EBANX.Token.setCVV("12345678", "123", new EBANXTokenRequestComplete() {
            @Override
            public void Success(EBANXToken token) {
                signal.countDown();
            }

            @Override
            public void APIError(EBANXError error) {
                Assert.assertEquals(error.getType(), EBANXError.ErrorType.GenericError);
                called = true;
                signal.countDown();
            }

            @Override
            public void NetworkError(Exception e) {
                signal.countDown();
            }
        });

        signal.await(10, TimeUnit.SECONDS);
        EBANX.shutDown();
        Assert.assertTrue(called);
    }

    @Test
    public void testSetCVVWithTokenPublicKeyNotSet() throws Exception {
        called = false;

        final CountDownLatch signal = new CountDownLatch(1);

        EBANX.configure(mockedContext, "");
        EBANXToken token = new EBANXToken("12345678", "4111********1111");

        EBANX.Token.setCVV(token, "123", new EBANXTokenRequestComplete() {
            @Override
            public void Success(EBANXToken token) {
                signal.countDown();
            }

            @Override
            public void APIError(EBANXError error) {
                Assert.assertEquals(error.getType(), EBANXError.ErrorType.PublicKeyNotSet);
                called = true;
                signal.countDown();
            }

            @Override
            public void NetworkError(Exception e) {
                signal.countDown();
            }
        });

        signal.await(10, TimeUnit.SECONDS);
        EBANX.shutDown();
        Assert.assertTrue(called);
    }

    @Test
    public void testSetCVVPublicKeyNotSet() throws Exception {
        called = false;

        final CountDownLatch signal = new CountDownLatch(1);

        EBANX.configure(mockedContext, "");

        EBANX.Token.setCVV("12345678", "123", new EBANXTokenRequestComplete() {
            @Override
            public void Success(EBANXToken token) {
                signal.countDown();
            }

            @Override
            public void APIError(EBANXError error) {
                Assert.assertEquals(error.getType(), EBANXError.ErrorType.PublicKeyNotSet);
                called = true;
                signal.countDown();
            }

            @Override
            public void NetworkError(Exception e) {
                signal.countDown();
            }
        });

        signal.await(10, TimeUnit.SECONDS);
        EBANX.shutDown();
        Assert.assertTrue(called);
    }
}
