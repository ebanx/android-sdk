package com.ebanx.sdk;

import android.content.Context;


import org.junit.Before;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class EBANXTest {

    private Context mContextMock;

    @Before
    public void setUp() throws Exception {
        mContextMock = mock(Context.class);
        when(mContextMock.getPackageName()).thenReturn("com.ebanx.sdk");
    }
}