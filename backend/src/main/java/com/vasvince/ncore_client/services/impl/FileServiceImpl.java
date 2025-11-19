package com.vasvince.ncore_client.services.impl;

import com.vasvince.ncore_client.entities.FileInfoResponse;
import com.vasvince.ncore_client.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Value("${torrent.folder.path}")
    private String folderPath;

    @Override
    public List<FileInfoResponse> getAllFiles() throws IOException {
        List<FileInfoResponse> responseList = new ArrayList<>();

        File folder = new File(Path.of(folderPath).toUri());
        File[] torrents = folder.listFiles();

        if (torrents != null) {
            for (File torrent : torrents) {
                long size = calculateFolderSize(torrent);
                FileInfoResponse fileInfo = new FileInfoResponse();
                fileInfo.setTorrentName(torrent.getName());
                fileInfo.setFolderSize(humanReadableSize(size));
                responseList.add(fileInfo);
            }
        } else {
            logger.error("There are no files in the configured folder.");
        }

        return responseList;
    }

    private long calculateFolderSize(File file) throws IOException {
        if (file.isFile()) {
            return Files.readAttributes(file.toPath(), BasicFileAttributes.class).size();
        } else if (file.isDirectory()) {
            long size = 0;
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    size += calculateFolderSize(f);
                }
            }
            return size;
        }
        return 0;
    }

    public String humanReadableSize(long bytes) {
        String[] units = {"B", "KB", "MB", "GB", "TB"};
        int unitIndex = 0;
        double size = bytes;

        while (size >= 1024 && unitIndex < units.length - 1) {
            size /= 1024;
            unitIndex++;
        }

        return String.format("%.2f %s", size, units[unitIndex]);
    }
}
