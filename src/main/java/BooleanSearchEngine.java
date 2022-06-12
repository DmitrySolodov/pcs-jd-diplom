import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {
    private Map<String, List<PageEntry>> map = new HashMap<>();

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        for (File item : pdfsDir.listFiles()) {
            var doc = new PdfDocument(new PdfReader(item));
                for (int i = 1; i <= doc.getNumberOfPages(); i++) {
                    PdfPage page = doc.getPage(i);
                    var text = PdfTextExtractor.getTextFromPage(page);
                    var words = text.split("\\P{IsAlphabetic}+");
                    Map<String, Integer> freqs = new HashMap<>();
                    for (var word : words) {
                        if (word.isEmpty()) {
                            continue;
                        }
                        freqs.put(word.toLowerCase(), freqs.getOrDefault(word.toLowerCase(), 0) + 1);
                    }
                    for (Map.Entry<String, Integer> entry : freqs.entrySet()) {
                        PageEntry temp = new PageEntry(item.getName(), i, entry.getValue());
                        if (map.containsKey(entry.getKey())) {
                            List<PageEntry> tempList = map.get(entry.getKey());
                            tempList.add(temp);
                            map.put(entry.getKey(), tempList);
                        } else {
                            List<PageEntry> newList = new ArrayList<>();
                            newList.add(temp);
                            map.put(entry.getKey(), newList);
                        }
                    }
                }
        }
    }

    @Override
    public List<PageEntry> search(String word) {
        String searchWord = word.toLowerCase();
        if (map.containsKey(searchWord)) {
            List<PageEntry> list = map.get(searchWord);
            Collections.sort(list);
            return list;
        } else {
            throw new NullPointerException ("Слово " + "'" + word + "'" + " не встретилось ни в одном документе");
        }
    }
}
