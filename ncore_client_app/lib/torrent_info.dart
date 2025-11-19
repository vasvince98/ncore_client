class TorrentInfoResponse {
  final String folderSize;
  final String torrentName;

  TorrentInfoResponse({
    required this.folderSize,
    required this.torrentName,
  });

  factory TorrentInfoResponse.fromJson(Map<String, dynamic> json) {
    return TorrentInfoResponse(
      folderSize: json['folderSize'] as String,
      torrentName: json['torrentName'] as String,
    );
  }
}