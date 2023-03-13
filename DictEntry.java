public class DictEntry {
  int docFreq; // document frequency, number of documents containing the term
  int termFreq; // term frequency, number of times the term appears in the collection of documents
  Posting postingsList = null; // postings linked list
}
