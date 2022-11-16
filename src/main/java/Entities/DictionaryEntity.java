package Entities;

import java.io.Serializable;
import java.util.Objects;

public class DictionaryEntity implements Serializable {
    public String getWord() {
        return word;
    }

    public String getDefinition() {
        return definition;
    }

    private final String word;
    private final String definition;

    DictionaryEntity(String word, String definition){
        this.word = word;
        this.definition = definition;
    }

    DictionaryEntity(DictionaryEntity dictionaryEntity){
        this.word = dictionaryEntity.word;
        this.definition = dictionaryEntity.definition;
    }
    DictionaryEntity(){
        this("###WORD###","###DEFINITION###");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DictionaryEntity that = (DictionaryEntity) o;
        return Objects.equals(word, that.word) && Objects.equals(definition, that.definition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, definition);
    }
}
