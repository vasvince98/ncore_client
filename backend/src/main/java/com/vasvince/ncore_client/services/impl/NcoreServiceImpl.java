package com.vasvince.ncore_client.services.impl;

import com.vasvince.ncore_client.components.NcoreAdapter;
import com.vasvince.ncore_client.services.NcoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class NcoreServiceImpl implements NcoreService {

    private final NcoreAdapter ncoreAdapter;

    @Autowired
    public NcoreServiceImpl(final NcoreAdapter ncoreAdapter) {
        Objects.requireNonNull(ncoreAdapter, "ncoreAdapter is null");
        this.ncoreAdapter = ncoreAdapter;
    }

    @Override
    public String fetchTorrentInfo(String torrentName) {
        return "";
    }
}
