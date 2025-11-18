package com.vasvince.ncore_client.controllers;

import com.vasvince.ncore_client.entities.FileInfoResponse;
import com.vasvince.ncore_client.services.NcoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api")
public class NcoreRESTController {

    private final NcoreService ncoreService;

    @Autowired
    public NcoreRESTController(final NcoreService ncoreService) {
        Objects.requireNonNull(ncoreService, "ncore service is null");
        this.ncoreService = ncoreService;
    }

    @GetMapping("listFiles")
    public List<FileInfoResponse> getAllFiles() throws IOException {
        return ncoreService.getAllFiles();
    }

}
