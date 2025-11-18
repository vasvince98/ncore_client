package com.vasvince.ncore_client.services;

import com.vasvince.ncore_client.entities.FileInfoResponse;

import java.io.IOException;
import java.util.List;

public interface NcoreService {
    List<FileInfoResponse> getAllFiles() throws IOException;
}
