import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

public class FileHistoryService implements HistoryService {
    
    private static FileHistoryService instance;
    private String path = "src/main/resources/history.txt";

    private FileHistoryService() {

    }

    public static FileHistoryService getInstance() {
        return instance == null ?
                new FileHistoryService() : instance;
    }
    
    @Override
    public void save(List<String> chat) {
        try {
            Files.write(Paths.get(path), chat, StandardOpenOption.CREATE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<String> load() {
        try {
            return Files.newBufferedReader(Paths.get(path))
                    .lines()
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("File not found");
        }
    }
}
