package com.vasvince.ncore_client.components;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class NcoreAdapterTest {

    @Test
    void adapterTest() throws IOException, InterruptedException {
        NcoreAdapter ncoreAdapter = new NcoreAdapter();
        System.out.println(ncoreAdapter.login());
    }
}
