package Entities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CrosswordDictionary {
    private Set<DictionaryEntity> data;

    public CrosswordDictionary(){
        data = new HashSet<>();
    }
    public CrosswordDictionary(CrosswordDictionary dictionary){
        data = new HashSet<>(dictionary.data);
    }


    public List<DictionaryEntity> getWordEntityList() {
        return data.stream().toList();
    }
    public void addWordEntity(DictionaryEntity dictionaryEntity){
        data.add(dictionaryEntity);
    }
    public void removeWordEntity(DictionaryEntity dictionaryEntity){
        data.remove(dictionaryEntity);
    }

    public void load(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        int cntr=0;
        String word=null;
        String def=null;
        while(scanner.hasNextLine())
        {
            if(cntr == 0)
                word = scanner.nextLine();
            else{
                def = scanner.nextLine();
                data.add(new DictionaryEntity(word,def));
            }
            cntr = (cntr+1)%2;
        }
    }
    public DictionaryEntity getRandom(long seed){
        Random random = new Random(seed);
        int rnd = random.nextInt()%data.size();
        if(rnd<0)
            rnd+=data.size();
        return new DictionaryEntity(data.stream().toList().get(rnd));
    }

    public int getSize(){
        if(data == null)
            return -1;
        return data.size();
    }
}
