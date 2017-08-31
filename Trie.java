/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*Trie implementation in java.
  methods supported:
insert(), search(), delete(), displayAllWords(), 
displayWordsStartingWith(String pre)
longestCommonPrefix() of inserted words in Trie
 *
 * @author Ashish
 */
public class Trie {    
    class TrieNode{
        Map<Character,TrieNode> trieMap;
        boolean endOfWord;
        TrieNode(){
            trieMap = new HashMap();
            endOfWord = false;
        }
    }
    
    final TrieNode root;
    List<String> listOfWords;
    Trie(){
        root = new TrieNode();
    }
    public void insert(String word){
        TrieNode curr = root;
        for(int i=0;i<word.length();i++){
            char ch = word.charAt(i);
            if(!curr.trieMap.containsKey(ch)){
                TrieNode newNode = new TrieNode();
                curr.trieMap.put(ch, newNode);
            }
            curr = curr.trieMap.get(ch);            
        }
        curr.endOfWord = true;
    }
    
    public boolean search(String word){
        TrieNode curr = root;
        for(int i=0;i<word.length();i++){
            char ch = word.charAt(i);
            if(curr.trieMap.containsKey(ch))
                curr = curr.trieMap.get(ch);
            else
                return false;
        }
        return curr.endOfWord;
    }
    
    public void delete(String word){
        delete(root,word,0);
    }
    
    private boolean delete(TrieNode curr, String word, int index) {
        if(word.length() == index){
            if(!curr.endOfWord)
                return false;   //means the word is not present in the true. return false
            curr.endOfWord = false;
            return curr.trieMap.isEmpty();
        }
        char ch = word.charAt(index);
        if(!curr.trieMap.containsKey(ch))
            return false;   //means word not present in trie. return false
        boolean deleteThisNode = delete(curr.trieMap.get(ch), word, index + 1);
        if(deleteThisNode){
            curr.trieMap.remove(ch);
            return curr.trieMap.isEmpty();
        }            
        return false;
    }
    
    public void displayAllWords(){
        listOfWords = new ArrayList<String>();
        display("",root);
        for(String s : listOfWords)
            System.out.println(s);
    }
    
    private void display(String prefix, TrieNode curr){
        if(curr.endOfWord)//to put in a list, declare a list inside this class and add 
            //'prefix' to that list and retrieve that list in main method
            //System.out.println(prefix);
            listOfWords.add(prefix);
        Iterator it = curr.trieMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry m = (Map.Entry) it.next();
            TrieNode node = (TrieNode) m.getValue();
            String s = prefix + (char)m.getKey();
            display(s,node);
        }
    }
    
    public void displayWordsStartingWith(String pre){
        listOfWords = new ArrayList<String>();
        displayWordsStartingWith(pre, "", root);
        if(listOfWords.isEmpty()){
            System.out.println("No elements with prefix '"+pre+"' in Trie!");
            return;
        }
        for(String s : listOfWords)
            System.out.println(s);
    }
    
    private void displayWordsStartingWith(String pre, String currPrefix, TrieNode curr){
        if(pre.equals(currPrefix))
            display(pre, curr);
        Iterator it = curr.trieMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry m = (Map.Entry) it.next();
            TrieNode node = (TrieNode) m.getValue();
            String s = currPrefix + (char)m.getKey();
            displayWordsStartingWith(pre, s, node);
        }        
    }
    
    public String longestCommonPrefix(){
        TrieNode curr = root;
        String res = "";
        while(curr.trieMap.size() == 1 && curr.endOfWord == false){
            char ch = curr.trieMap.entrySet().iterator().next().getKey();
            curr = curr.trieMap.get(ch);
            res = res + ch;
        }
        return res;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Trie t = new Trie();
        t.insert("c");
        t.insert("cc");
        t.insert("ccc");
        //t.insert("lmn");
        //t.displayAllWords();
        //System.out.println(t.search("abc"));
//        System.out.println(t.search("ab"));
//        System.out.println(t.search("cdf"));
        //t.delete("abc");
//        System.out.println(t.search("abc"));
        //t.insert("ab");
        //t.insert("a");
        //t.delete("ab");
//        System.out.println(t.search("a"));
        //t.displayAllWords();
        //t.displayWordsStartingWith("abx");
        System.out.println(t.longestCommonPrefix());
    }
    
}
