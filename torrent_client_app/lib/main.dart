import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

void main() {
  runApp(const MyApp());
}

class FileInfoResponse {
  final String folderSize;
  final String torrentName;

  FileInfoResponse({required this.folderSize, required this.torrentName});

  factory FileInfoResponse.fromJson(Map<String, dynamic> json) {
    return FileInfoResponse(
      folderSize: json['folderSize'],
      torrentName: json['torrentName'],
    );
  }
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData.dark(),
      home: const HomePage(),
    );
  }
}

/// -----------------------------
/// Home with BottomNavigationBar
/// -----------------------------
class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  int _selectedIndex = 0;

  final List<Widget> _pages = [
    const FileListScreen(),
    const StatusScreen(),
  ];

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _pages[_selectedIndex],
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: _selectedIndex,
        onTap: _onItemTapped,
        items: const [
          BottomNavigationBarItem(
            icon: Icon(Icons.folder),
            label: "Fájlok",
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.info),
            label: "Status",
          ),
        ],
      ),
    );
  }
}

/// -----------------------------
/// FILE LIST SCREEN (eredeti főoldal)
/// -----------------------------
class FileListScreen extends StatefulWidget {
  const FileListScreen({super.key});

  @override
  State<FileListScreen> createState() => _FileListScreenState();
}

class _FileListScreenState extends State<FileListScreen> {
  late Future<List<FileInfoResponse>> futureFiles;

  Future<List<FileInfoResponse>> fetchFiles() async {
    final response = await http.get(Uri.parse("http://172.20.10.4:9000/api/listFiles"));

    if (response.statusCode == 200) {
      final List<dynamic> jsonList = jsonDecode(response.body);
      return jsonList.map((json) => FileInfoResponse.fromJson(json)).toList();
    } else {
      throw Exception("Failed to load files: ${response.statusCode}");
    }
  }

  @override
  void initState() {
    super.initState();
    futureFiles = fetchFiles();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Fájlok")),
      body: FutureBuilder<List<FileInfoResponse>>(
        future: futureFiles,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          }

          if (snapshot.hasError) {
            return Center(child: Text("Hiba történt: ${snapshot.error}"));
          }

          final files = snapshot.data!;

          return ListView.builder(
            padding: const EdgeInsets.all(12),
            itemCount: files.length,
            itemBuilder: (context, index) {
              final file = files[index];

              return Card(
                shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
                elevation: 4,
                child: ListTile(
                  leading: const SizedBox(
                    width: 40,
                    child: Icon(Icons.folder, size: 28),
                  ),
                  title: Text(file.torrentName),
                  subtitle: Text("Méret: ${file.folderSize}"),
                ),
              );
            },
          );
        },
      ),
    );
  }
}

/// -----------------------------
/// STATUS SCREEN (új oldal)
/// -----------------------------
class StatusScreen extends StatelessWidget {
  const StatusScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Status")),
      body: const Center(
        child: Text(
          "Status oldal",
          style: TextStyle(fontSize: 22),
        ),
      ),
    );
  }
}
