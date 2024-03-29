import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) throws Exception {
    HashMap<String, DictEntry> index = new HashMap<String, DictEntry>(); // the inverted index aka dictionary

    // for each file: create a dictionary entry for each word in the file
    for (int i = 1; i <= 10; i++) {
      File file = new File("wordFile-" + i + ".txt");
      Scanner fileScanner = new Scanner(file);

      // for each word in the file
      while (fileScanner.hasNext()) {
        String word = fileScanner.next();
        DictEntry entry = index.get(word);

        // if the word is not in the dictionary
        if (entry == null) {
          // create a new dictionary entry for the word
          entry = new DictEntry();
          entry.docFreq = 1; // first document containing the term
          entry.termFreq = 1; // first occurrence of the term in the collection of documents
          Posting posting = new Posting();
          posting.docId = i;
          entry.postingsList = posting;
          index.put(word, entry);
        } else { // otherwise, the word is already in the dictionary
          entry.termFreq++; // increment term frequency, number of times the term appears in the collection
                            // of documents

          // since we are scanning the files in order, we can assume that the last posting
          // in the linked list is for the current document
          Posting posting = entry.postingsList;
          while (posting.next != null) {
            // go to the last posting in the linked list
            posting = posting.next;
          }

          // if the last posting in the linked list is for the current document
          if (posting.docId == i) {
            posting.dtf++; // increment document term frequency, number of times the term appears in the
                           // document
          } else {
            // otherwise, add a new posting to the linked list
            Posting newPosting = new Posting();
            newPosting.docId = i;
            posting.next = newPosting;
            entry.docFreq++; // increment document frequency, number of documents containing the term
          }
        }
      }
    }

    Scanner scanner = new Scanner(System.in);

    // search for a word
    System.out.print("Enter a word: ");
    String word = scanner.next();

    DictEntry entry = index.get(word);
    if (entry != null) {
      System.out.println("the word \"" + word + "\" is found");
      System.out.println("number of times the word appeared in all documents: " + entry.termFreq);
      System.out.println("number of documents containing the word: " + entry.docFreq);
      Posting posting = entry.postingsList;
      while (posting != null) {
        System.out.println(
            "docId: " + posting.docId + ", number of times the word appeared in this document: " + posting.dtf);
        posting = posting.next;
      }
    } else {
      System.out.println("word " + word + " is not found");
    }
  }
}
